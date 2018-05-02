package com.project.mgjandroid.ui.activity.illegalquery;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.mzule.activityrouter.router.RouterCallback;
import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.h5container.YLBSdkConstants;
import com.project.mgjandroid.h5container.view.YLBWebViewActivity;
import com.project.mgjandroid.model.IllegalAddModel;
import com.project.mgjandroid.model.IllegalBannerModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.NoticeDialog;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.CircleIndicator;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.MyBanner;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.OnBannerItemClickListener;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SunXueLiang on 2017-05-05.
 */

public class AddVehicleActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_MODELS = 101;

    @InjectView(R.id.layout_license_plate_prefix)
    private LinearLayout linearLayoutLicensePlatePrefix;
    @InjectView(R.id.tv_license_plate_prefix)
    private TextView tvLicensePlatePrefix;
    @InjectView(R.id.common_back)
    private ImageView tvBack;
    @InjectView(R.id.tv_models)
    private TextView tvModels;
    @InjectView(R.id.tv_save_add_query)
    private TextView tvSaveAddQuery;
    @InjectView(R.id.tv_del)
    private TextView tvDel;
    @InjectView(R.id.img_prompt)
    private ImageView imgPrompt;
    @InjectView(R.id.img_logo)
    private ImageView imgLogo;
    @InjectView(R.id.et_license_plate_number)
    private EditText etLicensePlateNumber;
    @InjectView(R.id.et_engine_number)
    private EditText etEngineNumber;
    @InjectView(R.id.et_frame_number)
    private EditText etFrameNumber;
    @InjectView(R.id.img_frame_prompt)
    private ImageView imgFramePrompt;
    @InjectView(R.id.layout_banner)
    private LinearLayout layoutAddVehicle;


    private PopupWindow popupWindow;
    private String brand;
    private String imgUrl;
    private int id;
    private String[] province = new String[]{"京", "津", "冀", "晋", "蒙", "辽", "吉", "黑", "沪", "苏", "浙", "皖", "闽", "赣", "鲁", "豫", "鄂", "湘", "粤", "桂", "琼", "渝", "川", "贵", "云", "藏", "陕", "甘", "青", "宁", "新", "台", "港", "澳"};
    private PopupWindow morePopupWindow;
    private MyBanner mMyBanner;
    private boolean hasBanner = false;
    private int HEADER_SIZE = 1;
    private String lsprefix;
    private String lsnum;
    private String engineno;
    private String frameno;
    private String carBrand;
    private MLoadingDialog loadingDialog;
    private CallPhoneDialog dialog;
    private List<IllegalBannerModel.ValueBean> value;
    private NoticeDialog noticeDialog;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_add_vehicle);
        Injector.get(this).inject();
        initView();
        getBanner();
    }

    private void initView() {
        imgFramePrompt.setOnClickListener(this);
        imgPrompt.setOnClickListener(this);
        tvModels.setOnClickListener(this);
        tvSaveAddQuery.setOnClickListener(this);
        linearLayoutLicensePlatePrefix.setOnClickListener(this);
        tvDel.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        lsprefix = getIntent().getStringExtra("lsprefix");
        if (lsprefix != null) {
            tvDel.setVisibility(View.VISIBLE);
            lsnum = getIntent().getStringExtra("lsnum");
            engineno = getIntent().getStringExtra("engineno");
            frameno = getIntent().getStringExtra("frameno");
            imgUrl = getIntent().getStringExtra("imgUrl");
            carBrand = getIntent().getStringExtra("carBrand");
            id = getIntent().getIntExtra("id", -1);
            etLicensePlateNumber.setEnabled(false);
            linearLayoutLicensePlatePrefix.setEnabled(false);
            etEngineNumber.setEnabled(false);
            if (CheckUtils.isNoEmptyStr(frameno)) {
                etFrameNumber.setEnabled(false);
            }
            tvLicensePlatePrefix.setText(lsprefix);
            etLicensePlateNumber.setText(lsnum);
            etEngineNumber.setText(engineno);
            etFrameNumber.setText(frameno);
            imgLogo.setVisibility(View.VISIBLE);
            ImageUtils.setImageViewBitmap(imgUrl, imgLogo, 0);
            tvModels.setText(carBrand);
        }
        loadingDialog = new MLoadingDialog();
    }

    private void promptPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.add_vehicle_prompt, null);
        View viewBg = view.findViewById(R.id.view_bg);
        viewBg.setOnClickListener(this);
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        popupWindow.setBackgroundDrawable(cd);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha = 1.0f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
        if (!popupWindow.isShowing()) {
            WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
            lp.alpha = 0.5f;
            mActivity.getWindow().setAttributes(lp);
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            popupWindow.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
    }

    private void dismissWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_prompt:
                promptPopupWindow();
                break;
            case R.id.view_bg:
                dismissWindow();
                break;
            case R.id.layout_license_plate_prefix:
                provinceList();
                break;
            case R.id.tv_models:
                BrandListActivity.toBrandListActivity(mActivity, GET_MODELS);
                break;
            case R.id.tv_save_add_query:
                if (checkCanQuery()) {
                    getQueryData();
                }
                break;
            case R.id.tv_del:
                dialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                    @Override
                    public void onSure() {
                        dialog.dismiss();
                        deleteCar();
                    }

                    @Override
                    public void onExit() {
                        dialog.dismiss();
                    }
                }, "", "是否确定删除？", "确定", "取消");
                dialog.show();

                break;
            case R.id.img_frame_prompt:
                promptPopupWindow();
                break;
            case R.id.common_back:
                back();
                break;
        }
    }

    private boolean checkCanQuery() {
        if (TextUtils.isEmpty(etLicensePlateNumber.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写正确的车牌号", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(etEngineNumber.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写发动机号", mActivity);
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case GET_MODELS:
                    brand = data.getStringExtra(SimpleExpAdapter.BRAND);
                    imgUrl = data.getStringExtra(SimpleExpAdapter.IMGURL);
                    tvModels.setText(brand);
                    imgLogo.setVisibility(View.VISIBLE);
                    ImageUtils.setImageViewBitmap(imgUrl, imgLogo, 0);
                    break;
            }
        }
    }

    private void provinceList() {
        View view = LayoutInflater.from(this).inflate(R.layout.add_vehicle_license_plate, null);
        GridView gridView = (GridView) view.findViewById(R.id.grid_view);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morePopupWindow.dismiss();
            }
        });
        AddVehicleLicensePlateAdapter adapter = new AddVehicleLicensePlateAdapter(mActivity, province);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tvLicensePlatePrefix.setText(province[i]);
                morePopupWindow.dismiss();
            }
        });
        morePopupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        morePopupWindow.setBackgroundDrawable(cd);
        morePopupWindow.setOutsideTouchable(false);
        morePopupWindow.setFocusable(true);
        morePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha = 1.0f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
        if (!morePopupWindow.isShowing()) {
            WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
            lp.alpha = 0.5f;
            mActivity.getWindow().setAttributes(lp);
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            morePopupWindow.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
    }

    private void getQueryData() {
        loadingDialog.show(getFragmentManager(), "");
        HashMap<String, Object> map = new HashMap<>();
        if (lsprefix != null) {
            map.put("id", id);
        }
        map.put("lsprefix", tvLicensePlatePrefix.getText().toString());
        map.put("lsnum", etLicensePlateNumber.getText().toString());
        map.put("frameno", etFrameNumber.getText().toString());
        map.put("engineno", etEngineNumber.getText().toString());
        map.put("imgUrl", imgUrl);
        map.put("carBrand", tvModels.getText().toString());
        VolleyOperater<IllegalAddModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CREATE_OR_MERGE_ILLEGALQUERY_CAR, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    loadingDialog.dismiss();
                    if (obj instanceof String) {
                        return;
                    }
                    IllegalAddModel model = (IllegalAddModel) obj;
                    Intent intent = new Intent(mActivity, VehicleDetailsActivity.class);
                    intent.putExtra("id", model.getValue().getId());
                    intent.putExtra("lsprefix", model.getValue().getLsprefix());
                    intent.putExtra("lsnum", model.getValue().getLsnum());
                    intent.putExtra("imgUrl", model.getValue().getImgUrl());
                    intent.putExtra("engineno", model.getValue().getEngineno());
                    intent.putExtra("frameno", model.getValue().getFrameno());
                    intent.putExtra("carBrand", model.getValue().getCarBrand());
                    startActivity(intent);
                    finish();
                }
            }
        }, IllegalAddModel.class);
    }

    private void deleteCar() {
        final Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        VolleyOperater<Object> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_DEL_ILLEGAL_QUERY_CAR_BY_ID, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    ToastUtils.displayMsg("删除成功", mActivity);
//                    startActivity(new Intent(mActivity,IllegalQueryActivity.class));
                    finish();
                }
            }
        }, Object.class);
    }

    private void getBanner() {
        VolleyOperater<IllegalBannerModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_ILLEGAL_QUERY_BANNER_LIST, null, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    IllegalBannerModel model = (IllegalBannerModel) obj;
                    value = model.getValue();
                    ArrayList<String> list = new ArrayList<String>();
                    for (IllegalBannerModel.ValueBean bean : value) {
                        list.add(bean.getPicUrl());
                    }
                    if (list.size() > 0) {
                        addBanner(list);
                    }
                }
            }
        }, IllegalBannerModel.class);
    }

    private void addBanner(ArrayList<String> list) {
        if (!hasBanner) {
            mMyBanner = (MyBanner) mInflater.inflate(R.layout.my_banner, null);
            mMyBanner.setOnBannerItemClickListener(new OnBannerItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    IllegalBannerModel.ValueBean valueBean = value.get(position);
                    int bannerType = valueBean.getBannerType();
                    switch (bannerType) {
                        case 1:
                            if (valueBean.getUrl().startsWith("maguanjia://")) {
                                if (valueBean.getUrl().replace("maguanjia://", "").startsWith("http")) {
                                    Intent intent = new Intent(mActivity, YLBWebViewActivity.class);
                                    intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, valueBean.getUrl().replace("maguanjia://", ""));
                                    startActivity(intent);
                                } else {
                                    Routers.open(mActivity, ActivitySchemeManager.SCHEME + valueBean.getUrl().replace("maguanjia://", ""), new RouterCallback() {
                                        @Override
                                        public void notFound(Context context, Uri uri) {
                                            showNoticeDialog();
                                        }

                                        @Override
                                        public void beforeOpen(Context context, Uri uri) {

                                        }

                                        @Override
                                        public void afterOpen(Context context, Uri uri) {

                                        }

                                        @Override
                                        public void error(Context context, Uri uri, Throwable e) {

                                        }
                                    });
                                }
                            } else if (valueBean.getUrl().startsWith("http")) {
//                                Intent intent = new Intent(mActivity, Banner2WebActivity.class);
//                                intent.putExtra(Banner2WebActivity.URL, valueBean.getUrl() + "?longitude=" + PreferenceUtils.getLocation(mActivity)[1] + "&latitude=" + PreferenceUtils.getLocation(mActivity)[0]);
//                                intent.putExtra("name", value.get(position).getName());
//                                startActivity(intent);
                                Intent intent = new Intent(mActivity, YLBWebViewActivity.class);
                                intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, valueBean.getUrl() + "?longitude=" + PreferenceUtils.getLocation(mActivity)[1] + "&latitude=" + PreferenceUtils.getLocation(mActivity)[0]);
                                startActivity(intent);
                            }
                            break;
                    }
                }
            });
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DipToPx.dip2px(mActivity, 115));
            CircleIndicator circleIndicator = (CircleIndicator) mMyBanner.findViewById(R.id.pageIndexor);
            circleIndicator.setVisibility(View.GONE);
            mMyBanner.setLayoutParams(layoutParams);
            mMyBanner.setBackgroundColor(mActivity.getResources().getColor(R.color.gray_bg));
            mMyBanner.setPadding(DipToPx.dip2px(mActivity, 15), DipToPx.dip2px(mActivity, 30), DipToPx.dip2px(mActivity, 15), DipToPx.dip2px(mActivity, 0));
            layoutAddVehicle.addView(mMyBanner);
            hasBanner = true;
            HEADER_SIZE = 2;
        }
        mMyBanner.setUrls(list, false, false);
    }

    private void showNoticeDialog() {
        if (noticeDialog == null) {
            noticeDialog = new NoticeDialog(mActivity, new NoticeDialog.onBtnClickListener() {
                @Override
                public void onSure() {
                    noticeDialog.dismiss();
                }
            }, "", "您当前版本过低，暂无法使用该功能。请更新到最新版本马管家。");
        }
        noticeDialog.show();
    }

}

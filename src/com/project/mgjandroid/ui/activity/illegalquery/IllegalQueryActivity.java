package com.project.mgjandroid.ui.activity.illegalquery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.github.mzule.activityrouter.router.RouterCallback;
import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.h5container.YLBSdkConstants;
import com.project.mgjandroid.h5container.view.YLBWebViewActivity;
import com.project.mgjandroid.model.IllegalBannerModel;
import com.project.mgjandroid.model.IllegalQueryListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.SmsLoginActivity;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.NoticeDialog;
import com.project.mgjandroid.ui.view.SwipeMenuLayout;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.CircleIndicator;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.MyBanner;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.OnBannerItemClickListener;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.DipToPx;
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
@Router("illegalQuery")
public class IllegalQueryActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2<ListView> {

    @InjectView(R.id.common_back)
    private ImageView imgBack;
    @InjectView(R.id.tv_add_car)
    private TextView tvAddCar;
    @InjectView(R.id.illegal_query_listview)
    private PullToRefreshListView listView;


    private boolean refreshFlag = true;
    private IllegalListAdapter adapter;
    private boolean hasBanner = false;
    private MyBanner mMyBanner;
    private int HEADER_SIZE = 1;
    private int start = 0;
    private static final int maxResults = 20;
    private IllegalQueryListModel model;

    private float[] evLoc = new float[2];

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                evLoc[0] = event.getX();
                evLoc[1] = event.getY();
            } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                double distance = Math.sqrt(Math.pow(event.getX() - evLoc[0], 2) + Math.pow(event.getY() - evLoc[1], 2));
                if (distance < 10) {
                    int pos1 = (int) v.getTag();
                    Intent intent2 = new Intent(mActivity, VehicleDetailsActivity.class);
                    intent2.putExtra("id", model.getValue().get(pos1).getId());
                    intent2.putExtra("imgUrl", model.getValue().get(pos1).getImgUrl());
                    intent2.putExtra("engineno", model.getValue().get(pos1).getEngineno());
                    intent2.putExtra("frameno", model.getValue().get(pos1).getFrameno());
                    intent2.putExtra("carBrand", model.getValue().get(pos1).getCarBrand());
                    startActivity(intent2);
                }
            }
            return false;
        }
    };
    private MLoadingDialog loadingDialog;
    private View emptyView;
    private CallPhoneDialog dialog;
    private List<IllegalBannerModel.ValueBean> value;
    private NoticeDialog noticeDialog;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_illegal_query);
        Injector.get(this).inject();
        initView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData(false);
    }

    private void initView() {
        tvAddCar.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        getBanner();
        adapter = new IllegalListAdapter(R.layout.item_illegal, mActivity, this, touchListener);
        listView.setAdapter(adapter);
        listView.setOnRefreshListener(this);
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        emptyView = mInflater.inflate(R.layout.empty_view_illegal, null);
        TextView tvEmpty = (TextView) emptyView.findViewById(R.id.tv_no_data);
        tvEmpty.setText("没有违章记录，或交管局还没来的及录入");
        loadingDialog = new MLoadingDialog();
    }

    private void getData(final boolean isLoadMore) {
        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("size", maxResults);
        VolleyOperater<IllegalQueryListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_ILLEGAL_QUERY_CAR_LIST, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {

                if (isSucceed && obj != null) {
                    loadingDialog.dismiss();
                    if (obj instanceof String) {
                        return;
                    }
                    model = (IllegalQueryListModel) obj;
                    List<IllegalQueryListModel.ValueBean> value = model.getValue();
                    listView.onRefreshComplete();
                    if (isLoadMore) {
                        List<IllegalQueryListModel.ValueBean> data = adapter.getData();
                        if (value != null && value.size() > 0) {
                            data.addAll(value);
                            adapter.setData(data);
                        } else {
                            ToastUtils.displayMsg("已经到底了~", mActivity);
                            listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        }
                    } else {
                        if (CheckUtils.isNoEmptyList(value)) {
                            listView.getRefreshableView().removeFooterView(emptyView);
                            adapter.setData(value);
                            listView.setMode(PullToRefreshBase.Mode.BOTH);
                        } else {
                            adapter.clear();
                            listView.getRefreshableView().removeFooterView(emptyView);
                            listView.getRefreshableView().addFooterView(emptyView);
                        }
                    }
                } else {
                    listView.onRefreshComplete();
                }

            }
        }, IllegalQueryListModel.class);
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
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DipToPx.dip2px(mActivity, 75));
            CircleIndicator circleIndicator = (CircleIndicator) mMyBanner.findViewById(R.id.pageIndexor);
            circleIndicator.setVisibility(View.GONE);
            mMyBanner.setLayoutParams(layoutParams);
            mMyBanner.setBackgroundColor(mActivity.getResources().getColor(R.color.gray_bg));
            mMyBanner.setPadding(DipToPx.dip2px(mActivity, 15), DipToPx.dip2px(mActivity, 0), DipToPx.dip2px(mActivity, 15), DipToPx.dip2px(mActivity, 0));
            ListView refreshableView = listView.getRefreshableView();
            refreshableView.addHeaderView(mMyBanner);
            hasBanner = true;
            HEADER_SIZE = 2;
        }
        mMyBanner.setUrls(list, false, false);
    }

    @Override
    public void onClick(final View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_modify:
                int pos1 = (int) v.getTag();
                Intent intent = new Intent(mActivity, AddVehicleActivity.class);
                intent.putExtra("lsprefix", model.getValue().get(pos1).getLsprefix());
                intent.putExtra("lsnum", model.getValue().get(pos1).getLsnum());
                intent.putExtra("engineno", model.getValue().get(pos1).getEngineno());
                intent.putExtra("frameno", model.getValue().get(pos1).getFrameno());
                intent.putExtra("imgUrl", model.getValue().get(pos1).getImgUrl());
                intent.putExtra("carBrand", model.getValue().get(pos1).getCarBrand());
                intent.putExtra("id", model.getValue().get(pos1).getId());
                startActivity(intent);
                break;
            case R.id.tv_add_car:
                if (App.isLogin()) {
                    Intent intent1 = new Intent(mActivity, AddVehicleActivity.class);
                    startActivity(intent1);
                } else {
                    startActivity(new Intent(this, SmsLoginActivity.class));
                }
                break;
            case R.id.item_delete:
                dialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                    @Override
                    public void onSure() {
                        int pos = (int) v.getTag();
                        ((SwipeMenuLayout) v.getParent()).smoothClose();
                        dialog.dismiss();
                        deleteCar(pos);
                    }

                    @Override
                    public void onExit() {
                        dialog.dismiss();
                    }
                }, "", "是否确定删除？", "确定", "取消");
                dialog.show();
                break;
            case R.id.item_illegal:
                int pos2 = (int) v.getTag();
                Intent intent2 = new Intent(mActivity, VehicleDetailsActivity.class);
                intent2.putExtra("id", model.getValue().get(pos2).getId());
                intent2.putExtra("lsprefix", model.getValue().get(pos2).getLsprefix());
                intent2.putExtra("lsnum", model.getValue().get(pos2).getLsnum());
                intent2.putExtra("imgUrl", model.getValue().get(pos2).getImgUrl());
                intent2.putExtra("engineno", model.getValue().get(pos2).getEngineno());
                intent2.putExtra("frameno", model.getValue().get(pos2).getFrameno());
                intent2.putExtra("carBrand", model.getValue().get(pos2).getCarBrand());
                startActivity(intent2);
                break;
            case R.id.common_back:
                back();
                break;
        }
    }

    private void deleteCar(final int pos) {
        final Map<String, Object> map = new HashMap<>();
        map.put("id", model.getValue().get(pos).getId());
        VolleyOperater<Object> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_DEL_ILLEGAL_QUERY_CAR_BY_ID, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    model.getValue().remove(pos);
                    ToastUtils.displayMsg("删除成功", mActivity);
                    adapter.notifyDataSetChanged();
                    if (model.getValue().size() == 0) {
                        listView.getRefreshableView().addFooterView(emptyView);
                    }
                }
            }
        }, Object.class);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        start = 0;
        getData(false);
    }

    @Override
    public void onPullDownValue(PullToRefreshBase<ListView> refreshView, int value) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        start = adapter.getCount();
        getData(true);
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

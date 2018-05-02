package com.project.mgjandroid.ui.activity.renthouse;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.Agent;
import com.project.mgjandroid.bean.information.InformationFreeStandard;
import com.project.mgjandroid.bean.information.InformationType;
import com.project.mgjandroid.bean.information.OrderType;
import com.project.mgjandroid.chooseimage.ChoosePhotoModel;
import com.project.mgjandroid.chooseimage.UploadPhoto;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ChooseCityModel;
import com.project.mgjandroid.model.InformationAreaModel;
import com.project.mgjandroid.model.SendSmsModel;
import com.project.mgjandroid.model.SmsLoginModel;
import com.project.mgjandroid.model.information.InformationLeaseRentModel;
import com.project.mgjandroid.model.information.PositionInformationModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.CameraActivity;
import com.project.mgjandroid.ui.activity.ImageUploadActivity;
import com.project.mgjandroid.ui.activity.UploadPhotoActivity;
import com.project.mgjandroid.ui.activity.information.ChoosePublishAreaActivity;
import com.project.mgjandroid.ui.activity.information.InformationCategoryChooseActivity;
import com.project.mgjandroid.ui.activity.information.MyPublishInformationActivity;
import com.project.mgjandroid.ui.activity.information.PayActivity;
import com.project.mgjandroid.ui.view.materialdialog.MaterialDialog;
import com.project.mgjandroid.utils.BitmapUtil;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.PayUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PublishLeaseActivity extends BaseActivity {
    private static final int GET_AREA = 106;
    private static final int GET_CATEGORY_TYPE = 1005;
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.photo_layout)
    private RelativeLayout photoLayout;
    @InjectView(R.id.house_lease_img)
    private ImageView ivImage;
    @InjectView(R.id.house_lease_photo)
    private ImageView ivPhoto;
    @InjectView(R.id.house_lease_photo_2)
    private ImageView ivPhoto2;
    @InjectView(R.id.tv_take_photo)
    private TextView tvPhoto;
    @InjectView(R.id.house_lease_name)
    private EditText etName;
    @InjectView(R.id.house_lease_category)
    private TextView tvCategory;
    @InjectView(R.id.house_lease_type)
    private EditText etType;
    @InjectView(R.id.house_lease_acreage)
    private EditText etAcreage;
    @InjectView(R.id.tv_price)
    private TextView tvPrice;
    @InjectView(R.id.house_lease_price)
    private TextView etPrice;
    @InjectView(R.id.goods_price_layout)
    private LinearLayout priceLayout;
    @InjectView(R.id.house_lease_price_max)
    private EditText etPriceMax;
    @InjectView(R.id.house_lease_location)
    private EditText etLocation;
    @InjectView(R.id.house_lease_area)
    private TextView etArea;
    @InjectView(R.id.house_lease_content)
    private EditText etContent;
    @InjectView(R.id.tv_content_length)
    private TextView tvContentLength;
    @InjectView(R.id.house_lease_phone)
    private EditText etPhone;
    @InjectView(R.id.house_lease_get_code)
    private TextView tvGetCode;
    @InjectView(R.id.house_lease_code)
    private EditText etCode;
    @InjectView(R.id.house_lease_publish)
    private TextView tvPublish;
    @InjectView(R.id.house_lease_change)
    private TextView tvChange;
    @InjectView(R.id.house_lease_get_code_line)
    private View vBottomLine;
    @InjectView(R.id.house_lease_code_label)
    private LinearLayout llBottomLabel;
    @InjectView(R.id.house_lease_choose_category)
    private RelativeLayout rlChooseType;
    @InjectView(R.id.house_lease_choose_area)
    private RelativeLayout rlChooseArea;
    @InjectView(R.id.house_lease_area_label)
    private LinearLayout llAreaLabel;
    @InjectView(R.id.house_lease_area_line)
    private View areaLine;

    private long mPositionCategory = -1;
    private long mProvince = 0;
    private long mCity = 0;
    private long mDistrict = 0;

    private String mCityCode = "";
    private String mPhone;
    private String mPrePhone;
    private boolean timeTick = false;

    private MaterialDialog mMaterialDialog;
    private CustomDialog mDialog;
    private PopupWindow mPopupWindow;
    private ArrayList<UploadPhoto> mSelectFiles = new ArrayList<>();
    private StringBuffer imageUrls = new StringBuffer();
    private int type;
    private long agentId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_house_lease_info);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        SmsLoginModel.ValueEntity.AppUserEntity userInfo = App.getInstance().getUserInfo();
        if (userInfo != null) {
            mPhone = userInfo.getMobile();
            changeGetCodeLabel(mPhone);
        } else {
            changeGetCodeLabel(null);
        }

        ivBack.setOnClickListener(this);

        type = 1;
        tvTitle.setText("发布房屋租赁信息");
        ChoosePhotoModel.getInstance().setMaxCount(12);
        ChoosePhotoModel.getInstance().setCurrentActivity(this.getClass());
        llAreaLabel.setVisibility(View.VISIBLE);
        areaLine.setVisibility(View.VISIBLE);

        ivImage.setOnClickListener(this);
        ivPhoto.setOnClickListener(this);
        ivPhoto2.setOnClickListener(this);
        tvPhoto.setOnClickListener(this);
        tvPublish.setOnClickListener(this);
        tvChange.setOnClickListener(this);
        tvGetCode.setOnClickListener(this);
        rlChooseType.setOnClickListener(this);
        rlChooseArea.setOnClickListener(this);
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvContentLength.setText(s.toString().trim().length() + "/500字");
                if (s.toString().trim().length() > 500) {
                    ToastUtils.displayMsg("您输入的字数已超过500字", mActivity);
                }
            }
        });

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!timeTick) {
                    tvGetCode.setEnabled(s.length() == 11);
                }
                if (!TextUtils.isEmpty(mPhone) && s.toString().equals(mPhone)) {
                    tvGetCode.setVisibility(View.GONE);
                    tvChange.setVisibility(View.VISIBLE);
                    llBottomLabel.setVisibility(View.GONE);
                } else {
                    tvGetCode.setVisibility(View.VISIBLE);
                    tvChange.setVisibility(View.GONE);
                    llBottomLabel.setVisibility(View.VISIBLE);
                }

                if (s.length() != 11 || !s.toString().equals(mPrePhone)) {
                    etCode.setText("");
                }
            }
        });

        etAcreage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString().trim();
                int len = temp.length();
                if (s.length() > 0) {
                    if (!temp.equals(".") && new BigDecimal(Double.parseDouble(temp)).compareTo(new BigDecimal(1000000)) >= 0) {
                        s.delete(len - 1, len);
                    }
                    if (len > 1 && temp.charAt(0) == "0".charAt(0) && temp.charAt(1) != ".".charAt(0)) {
                        s.delete(0, 1);
                    }
                }

                int d = temp.indexOf(".");
                if (d >= 0) {
                    if (temp.length() - d - 1 > 1) {
                        s.delete(d + 2, d + 3);
                    } else if (d == 0) {
                        s.delete(d, d + 1);
                    }
                }
            }
        });

        etPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString().trim();
                int len = temp.length();
                if (s.length() > 0) {
                    if (!temp.equals(".") && new BigDecimal(Double.parseDouble(temp)).compareTo(new BigDecimal(10000000)) >= 0) {
                        s.delete(len - 1, len);
                    }
                    if (len > 1 && temp.charAt(0) == "0".charAt(0) && temp.charAt(1) != ".".charAt(0)) {
                        s.delete(0, 1);
                    }
                }

                int d = temp.indexOf(".");
                if (d >= 0) {
                    if (temp.length() - d - 1 > 2) {
                        s.delete(d + 3, d + 4);
                    } else if (d == 0) {
                        s.delete(d, d + 1);
                    }
                }
            }
        });

        etPriceMax.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString().trim();
                int len = temp.length();
                if (s.length() > 0) {
                    if (!temp.equals(".") && new BigDecimal(Double.parseDouble(temp)).compareTo(new BigDecimal(10000000)) >= 0) {
                        s.delete(len - 1, len);
                    }
                    if (len > 1 && temp.charAt(0) == "0".charAt(0) && temp.charAt(1) != ".".charAt(0)) {
                        s.delete(0, 1);
                    }
                }

                int d = temp.indexOf(".");
                if (d >= 0) {
                    if (temp.length() - d - 1 > 2) {
                        s.delete(d + 3, d + 4);
                    } else if (d == 0) {
                        s.delete(d, d + 1);
                    }
                }
            }
        });

        String data = PreferenceUtils.getInformationArea(mActivity);

        if (CheckUtils.isNoEmptyStr(data)) {
            InformationAreaModel.ValueBean valueBean = JSON.parseObject(data, InformationAreaModel.ValueBean.class);
            if (CheckUtils.isNoEmptyStr(valueBean.getName())) {
                agentId = PreferenceUtils.getLongPreference("issueAgentId", 0, mActivity);
                mProvince = valueBean.getProvince();
                mCity = valueBean.getCity();
                mDistrict = valueBean.getDistrict();
                etArea.setText(valueBean.getName());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.house_lease_img:
                CommonUtils.hideKeyBoard(mActivity);
                return;
            case R.id.house_lease_photo_2:
                Intent dealPhoto = new Intent(mActivity, UploadPhotoActivity.class);
                dealPhoto.putExtra("from", mActivity.getClass().toString());
                startActivity(dealPhoto);
                return;

            case R.id.house_lease_photo:
            case R.id.tv_take_photo:
                CommonUtils.hideKeyBoard(mActivity);
                showPopupWindow();
                return;

            case R.id.house_lease_publish:
                CommonUtils.hideKeyBoard(mActivity);
                tvPublish.setEnabled(false);
                if (checkCanPublish()) {
//                    publish();
                    getInformationFeeList(null, agentId, InformationType.Lease.getValue(), mPositionCategory, OrderType.SendInformation.getValue());
                } else {
                    tvPublish.setEnabled(true);
                }
                return;

            case R.id.house_lease_change:
                changeGetCodeLabel("");
                return;

            case R.id.house_lease_get_code:
                mPrePhone = etPhone.getText().toString().trim();
                if (!CheckUtils.isNoEmptyStr(etPhone.getText().toString().trim())) {
                    ToastUtils.displayMsg("请输入手机号", this);
                    return;
                }
                getCheckCode();
                return;

            case R.id.house_lease_choose_category:
                CommonUtils.hideKeyBoard(mActivity);
                InformationCategoryChooseActivity.toChooseInformationCategory(mActivity, InformationType.Lease.getValue(),
                        InformationCategoryChooseActivity.FROM_INFORMATION_PUBLISH_PAGE, GET_CATEGORY_TYPE);
                return;

            case R.id.house_lease_choose_area:
                CommonUtils.hideKeyBoard(mActivity);
                ChoosePublishAreaActivity.toChoosePublishArea(mActivity, GET_AREA);
                return;

            case R.id.take_photo:
                if (!CheckUtils.hasCamera(mActivity)) {
                    toast("您的手机上没有检测到相机");
                    return;
                }
                Intent camera = new Intent(mActivity, CameraActivity.class);
                ChoosePhotoModel.getInstance().setFrom(mActivity.getClass().getName());
                startActivity(camera);
                dismissPopupWindow();
                return;
            case R.id.select_photo:
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + ActivitySchemeManager.URL_GET_IMAGE_LIST);
                ChoosePhotoModel.getInstance().setFrom(mActivity.getClass().getName());
                dismissPopupWindow();
                return;
            case R.id.feed_back_cancel:
                dismissPopupWindow();
                return;
        }
        super.onClick(v);
    }

    private void showUploadDialog() {
        if (mMaterialDialog == null) {
            mMaterialDialog = new MaterialDialog(mActivity);
            mMaterialDialog.setCanceledOnTouchOutside(true);
        }
        mMaterialDialog.setMessage("正在提交...");
        mMaterialDialog.show();
    }

    private void showPopupWindow() {
        if (mPopupWindow == null) {
            initPopupWindow();
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        } else if (!mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
    }

    private void initPopupWindow() {
        LinearLayout linearLayout = (LinearLayout) mInflater.inflate(R.layout.layout_select_photo, null);
        TextView tvTakePhoto = (TextView) linearLayout.findViewById(R.id.take_photo);
        TextView tvSelectPhoto = (TextView) linearLayout.findViewById(R.id.select_photo);
        TextView tvCancel = (TextView) linearLayout.findViewById(R.id.feed_back_cancel);
        tvTakePhoto.setOnClickListener(this);
        tvSelectPhoto.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        mPopupWindow = new PopupWindow(linearLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOutsideTouchable(false);
    }

    private void dismissPopupWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSelectFiles = ChoosePhotoModel.getInstance().getmUploadPhotoList();
        MLog.d(mSelectFiles.size() + "");
        if (mSelectFiles.size() > 0) {
            Bitmap bitmap = BitmapUtil.compressBitmap(mSelectFiles.get(0).getPath(), 1280);
            ivImage.setImageBitmap(bitmap);
            ivPhoto.setVisibility(View.GONE);
            ivPhoto2.setVisibility(View.VISIBLE);
            tvPhoto.setVisibility(View.GONE);
        } else {
            ivImage.setImageResource(R.drawable.house_lease_default);
            ivPhoto.setVisibility(View.VISIBLE);
            ivPhoto2.setVisibility(View.GONE);
            tvPhoto.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        ChoosePhotoModel.getInstance().clear();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case GET_AREA:
                    Agent agent = (Agent) data.getSerializableExtra(ChoosePublishAreaActivity.AGENT);
                    agentId = agent.getId();
                    String area = "";
                    if (agent.getProvinceName() != null) area = agent.getProvinceName();
                    if (agent.getCityName() != null && !area.equals(agent.getCityName()))
                        area += agent.getCityName();
                    if (agent.getAgentType() == 1 && agent.getDistrictName() != null)
                        area += agent.getDistrictName();
                    etArea.setText(area);
                    if (agent.getAgentType() == 1 && agent.getDistrict() != null) {
                        mDistrict = agent.getDistrict();
                    } else {
                        mDistrict = 0;
                    }
                    if (agent.getCity() != null) {
                        mCity = agent.getCity();
                    } else {
                        mCity = 0;
                    }
                    if (agent.getProvince() != null) {
                        mProvince = agent.getProvince();
                    } else {
                        mProvince = 0;
                    }
                    mCityCode = "";
                    return;

                case GET_CATEGORY_TYPE:
                    mPositionCategory = data.getLongExtra(InformationCategoryChooseActivity.CATEGORY_ID, -1);
                    String name1 = data.getStringExtra(InformationCategoryChooseActivity.CATEGORY_NAME);
                    tvCategory.setText(name1);
                    return;
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean checkCanPublish() {
        if (TextUtils.isEmpty(etName.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写标题", mActivity);
            return false;
        }
//        else if(!CommonUtils.checkJobname(etName.getText().toString().trim())){
//            ToastUtils.displayMsg("标题只能是汉字、字母或数字",mActivity);
//            return false;
//        }
        if (mPositionCategory == -1) {
            ToastUtils.displayMsg("请选择房屋类别", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(etType.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写户型", mActivity);
            return false;
        }
        if (type == 1 && TextUtils.isEmpty(etAcreage.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写房屋面积", mActivity);
            return false;
        }
        if (type == 1 && TextUtils.isEmpty(etPrice.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写价格", mActivity);
            return false;
        } else if (type == 2 && (TextUtils.isEmpty(etPrice.getText().toString().trim()) || TextUtils.isEmpty(etPriceMax.getText().toString().trim()))) {
            ToastUtils.displayMsg("请填写价格区间", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(etLocation.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写地段", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(mCityCode) && (mProvince == 0 || mCity == 0)) {
            ToastUtils.displayMsg("请选择发布区域", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(etContent.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写详细信息", mActivity);
            return false;
        }
        if (etContent.getText().toString().length() < 50) {
            ToastUtils.displayMsg("详细信息不得少于50字", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(etPhone.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写联系电话", mActivity);
            return false;
        }
        if (llBottomLabel.getVisibility() == View.VISIBLE && TextUtils.isEmpty(etCode.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写验证码", mActivity);
            return false;
        }
//        if(type == 2 && TextUtils.isEmpty(imageUrls.toString())){
//            ToastUtils.displayMsg("请选择照片",mActivity);
//            return false;
//        }else
        if (type == 1) {
            imageUrls.setLength(0);
            for (int i = 0, size = mSelectFiles.size(); i < size; i++) {
                if (TextUtils.isEmpty(mSelectFiles.get(i).getUrl())) continue;
                if (i == size - 1) {
                    imageUrls.append(mSelectFiles.get(i).getUrl());
                } else {
                    imageUrls.append(mSelectFiles.get(i).getUrl() + ";");
                }
            }
            if (TextUtils.isEmpty(imageUrls.toString())) {
                ToastUtils.displayMsg("请选择照片", mActivity);
                return false;
            }
            String[] urls = imageUrls.toString().split(";");
            if (urls.length == 0) {
                toast("您选取的照片还没有上传，快去上传吧！");
                return false;
            }
            if (urls.length < mSelectFiles.size()) {
                showMyDialog();
                return false;
            }
        }
        return true;
    }

    private void showMyDialog() {
        if (mDialog == null) {
            mDialog = new CustomDialog(mActivity, new CustomDialog.onBtnClickListener() {
                @Override
                public void onSure() {
                    Intent dealPhoto = new Intent(mActivity, UploadPhotoActivity.class);
                    startActivity(dealPhoto);
                    mDialog.dismiss();
                }

                @Override
                public void onExit() {
//                    publish();
                    getInformationFeeList(null, agentId, InformationType.Lease.getValue(), mPositionCategory, OrderType.SendInformation.getValue());
                    mDialog.dismiss();
                }

            }, "去上传", "仍要发布", "", "您选取的照片还有没上传的哦~~");
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.show();
        } else {
            mDialog.show();
        }
    }

    private void changeGetCodeLabel(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            etPhone.setEnabled(false);
            etPhone.setText(phone);
            tvGetCode.setVisibility(View.GONE);
            tvChange.setVisibility(View.VISIBLE);
            vBottomLine.setVisibility(View.GONE);
            llBottomLabel.setVisibility(View.GONE);
        } else {
            etPhone.setEnabled(true);
            etPhone.setText("");
            etPhone.requestFocus();
            tvGetCode.setVisibility(View.VISIBLE);
            tvGetCode.setEnabled(false);
            tvChange.setVisibility(View.GONE);
            vBottomLine.setVisibility(View.VISIBLE);
            llBottomLabel.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 发布信息
     */
    private void publish(final InformationFreeStandard informationFreeStandard) {
        showUploadDialog();
        Map<String, Object> map = new HashMap<>();
        map.put("informationType", InformationType.Lease.getValue());
        map.put("title", etName.getText().toString().trim());
//        map.put("houseLeaseCategoryId", mPositionCategory );
        map.put("mobile", etPhone.getText().toString().trim());
        map.put("type", type);
        map.put("categoryId", mPositionCategory);
        if (informationFreeStandard != null) {
            map.put("freeStandardId", informationFreeStandard.getId());
            map.put("days", informationFreeStandard.getDays());
            map.put("price", informationFreeStandard.getPrice());
            if (informationFreeStandard.getOriginPrice() == null) {
                map.put("originalPrice", informationFreeStandard.getPrice());
            } else {
                map.put("originalPrice", informationFreeStandard.getOriginPrice());
            }
        } else {
            map.put("freeStandardId", 0);
            map.put("days", 0);
            map.put("price", 0);
            map.put("originalPrice", 0);
        }
        map.put("description", etContent.getText().toString().trim());
        map.put("sectorArea", etLocation.getText().toString().trim());
        map.put("houseType", etType.getText().toString().trim());
        if (type == 1) {
            map.put("houseArea", etAcreage.getText().toString().trim());
        }
        map.put("type", type);
        if (type == 2) {
            if (new BigDecimal(Double.parseDouble(etPrice.getText().toString())).compareTo(new BigDecimal(Double.parseDouble(etPriceMax.getText().toString()))) <= 0) {
                map.put("minAmt", etPrice.getText().toString());
                map.put("maxAmt", etPriceMax.getText().toString());
            } else {
                map.put("minAmt", etPriceMax.getText().toString());
                map.put("maxAmt", etPrice.getText().toString());
            }
        } else {
            map.put("amt", etPrice.getText().toString());
        }
        map.put("imgs", imageUrls.toString());
        if (TextUtils.isEmpty(mCityCode)) {
            if (mProvince != 0) {
                map.put("province", mProvince);
            }
            if (mCity != 0) {
                map.put("city", mCity);
            }
            if (mDistrict != 0) {
                map.put("district", mDistrict);
            }
        } else {
            try {
                map.put("cityCode", Long.parseLong(mCityCode));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        map.put("agentId", agentId);
        if (llBottomLabel.getVisibility() == View.VISIBLE) {
            map.put("code", etCode.getText().toString().trim());
        }
        VolleyOperater<InformationLeaseRentModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CREATE_INFORMATION, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMaterialDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    InformationLeaseRentModel model = (InformationLeaseRentModel) obj;
                    if (model.getCode() == 0) {
                        ChooseCityModel.getInstance().setHasChanged(true);
                        if (informationFreeStandard == null) {
                            toast("已发布");
                        } else {
                            if (BigDecimal.ZERO.compareTo(informationFreeStandard.getPrice()) < 0) {
                                PayActivity.toPay(mActivity, model.getValue().getId(), model.getValue().getInformationOrder().getId(),
                                        InformationType.Lease.getValue(), model.getValue().getAgentId());
                            } else {
//                                InformationDetailActivity.toInformationDetail(mActivity, model.getValue().getId(), InformationType.Position.getValue());
                                MyPublishInformationActivity.toMyPublishInformationList(mActivity, InformationType.Lease.getValue());
                            }
                        }
                        finish();
                    }
                }
            }
        }, InformationLeaseRentModel.class);
    }

    /**
     * 获取验证码
     */
    private void getCheckCode() {
        timeTick = true;
        tvPublish.setEnabled(false);
        etPhone.setEnabled(false);
        tvGetCode.setEnabled(false);
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", etPhone.getText().toString().trim());
        VolleyOperater<SendSmsModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_NEW_SEND_RELEASE_INFORMATION_SMS, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                tvPublish.setEnabled(true);
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg((String) obj, mActivity);
                        return;
                    }

                    SendSmsModel sendSmsModel = (SendSmsModel) obj;
                    if (sendSmsModel.getCode() == 0) {
//                        ToastUtils.displayMsg(getString(R.string.send_code_success), mActivity);
                        new SmsCountDown(60000, 1000).start();
                    } else {
                        tvGetCode.setEnabled(true);
                        ToastUtils.displayMsg(getString(R.string.send_code_failed), mActivity);
                    }
                } else {
                    tvGetCode.setEnabled(true);
                }
            }
        }, SendSmsModel.class);
    }

    private class SmsCountDown extends CountDownTimer {

        public SmsCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvGetCode.setText("重新获取(" + millisUntilFinished / 1000 + "s)");
        }

        @Override
        public void onFinish() {
            timeTick = false;
            etPhone.setEnabled(true);
            tvGetCode.setEnabled(true);
            tvGetCode.setText("重新获取");
        }
    }

    @Override
    protected void showList(final ArrayList<InformationFreeStandard> value) {
        tvPublish.setEnabled(true);
        if (CheckUtils.isEmptyList(value)) {
            publish(null);
            return;
        }
//        if (value.size() == 1 && BigDecimal.ZERO.compareTo(value.get(0).getPrice()) == 0) {
//            ToastUtils.displayMsg("本次发布信息免费", mActivity);
//            publish(value.get(0));
//            return;
//        }
        PayUtils.showWindow(mActivity, value, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PayUtils.hideWindow();
                if (position < value.size()) {
                    publish(value.get(position));
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        CommonUtils.hideKeyBoard(mActivity);
        return super.onTouchEvent(event);
    }
}
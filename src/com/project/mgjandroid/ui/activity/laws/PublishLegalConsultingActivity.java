package com.project.mgjandroid.ui.activity.laws;

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
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.Agent;
import com.project.mgjandroid.bean.ProvinceBean;
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
import com.project.mgjandroid.model.information.InformationLawModel;
import com.project.mgjandroid.model.information.RecruitInformationModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.CameraActivity;
import com.project.mgjandroid.ui.activity.ChooseCityActivity;
import com.project.mgjandroid.ui.activity.ImageUploadActivity;
import com.project.mgjandroid.ui.activity.UploadPhotoActivity;
import com.project.mgjandroid.ui.activity.employment.PublishRecruitmentActivity;
import com.project.mgjandroid.ui.activity.information.ChoosePropertyActivity;
import com.project.mgjandroid.ui.activity.information.ChooseProvinceActivity;
import com.project.mgjandroid.ui.activity.information.ChoosePublishAreaActivity;
import com.project.mgjandroid.ui.activity.information.InformationCategoryChooseActivity;
import com.project.mgjandroid.ui.activity.information.MyPublishInformationActivity;
import com.project.mgjandroid.ui.activity.information.PayActivity;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.materialdialog.MaterialDialog;
import com.project.mgjandroid.utils.BitmapUtil;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.PayUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xueliang on 2016/11/17.
 */

public class PublishLegalConsultingActivity extends ImageUploadActivity {

    private static final int GET_AREA = 100;
    private static final int GET_TIME = 101;
    private static final int GET_LAW_CATEGORY = 102;
    private static final int GET_LOCAL_AREA = 103;

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.tv_take_photo)
    private TextView tvTakePhoto;

    @InjectView(R.id.et_name)
    private EditText etName;
    @InjectView(R.id.et_company)
    private EditText etCompany;
    @InjectView(R.id.tv_practicing_time)
    private TextView tvPracticingTime;
    @InjectView(R.id.tv_category)
    private TextView tvCategory;
    @InjectView(R.id.et_field)
    private TextView etField;
    @InjectView(R.id.tv_local_area)
    private TextView tvLocalArea;
    @InjectView(R.id.tv_release_area)
    private TextView tvReleaseArea;
    @InjectView(R.id.et_requirements)
    private EditText etRequirements;
    @InjectView(R.id.tv_profiles_length)
    private TextView tvLawLength;
    @InjectView(R.id.et_phone)
    private EditText etPhone;
    @InjectView(R.id.tv_get_code)
    private TextView tvGetCode;
    @InjectView(R.id.et_code)
    private EditText etCode;
    @InjectView(R.id.tv_publish_info)
    private TextView tvPublish;
    @InjectView(R.id.tv_change)
    private TextView tvChange;
    @InjectView(R.id.get_code_line)
    private View vBottomLine;
    @InjectView(R.id.code_label)
    private LinearLayout llBottomLabel;

    private long mPositionType = -1;
    private long mProvince = 0;
    private long mCity = 0;
    private long mDistrict = 0;
    private String mCityCode = "";

    private String phone;
    private PopupWindow mPopupWindow;
    private boolean timeTick = false;
    private ArrayList<UploadPhoto> mSelectFiles = new ArrayList<>();
    private StringBuffer imageUrls = new StringBuffer();
    private CustomDialog mDialog;
    private MaterialDialog mMaterialDialog;
    private long agentId = 0;
    private String mPrePhone;
    private String mPhone;
    private int whereProvince;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_publish_legalconsulting);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        tvTitle.setText("发布法律咨询信息");
        SmsLoginModel.ValueEntity.AppUserEntity userInfo = App.getInstance().getUserInfo();
        if (userInfo != null) {
            mPhone = userInfo.getMobile();
            changeGetCodeLabel(mPhone);
        } else {
            changeGetCodeLabel(null);
        }

        ChoosePhotoModel.getInstance().setMaxCount(12);
        ChoosePhotoModel.getInstance().setCurrentActivity(this.getClass());

        ivAvatar = (CornerImageView) findViewById(R.id.iv_avatar);
        ivBack.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
        tvTakePhoto.setOnClickListener(this);
        tvPracticingTime.setOnClickListener(this);
        tvCategory.setOnClickListener(this);
        etField.setOnClickListener(this);
        tvLocalArea.setOnClickListener(this);
        tvReleaseArea.setOnClickListener(this);
        tvGetCode.setOnClickListener(this);
        tvChange.setOnClickListener(this);
        tvPublish.setOnClickListener(this);
        etRequirements.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvLawLength.setText(s.toString().trim().length() + "/500");
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

        String data = PreferenceUtils.getInformationArea(mActivity);

        if (CheckUtils.isNoEmptyStr(data)) {
            InformationAreaModel.ValueBean valueBean = JSON.parseObject(data, InformationAreaModel.ValueBean.class);
            if (CheckUtils.isNoEmptyStr(valueBean.getName())) {
                agentId = PreferenceUtils.getLongPreference("issueAgentId", 0, mActivity);
                mProvince = valueBean.getProvince();
                mCity = valueBean.getCity();
                mDistrict = valueBean.getDistrict();
                tvReleaseArea.setText(valueBean.getName());
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSelectFiles = ChoosePhotoModel.getInstance().getmUploadPhotoList();
        List<String> list = new ArrayList<>();
        if (CheckUtils.isNoEmptyList(mSelectFiles)) {
            for (int i = 0, size = mSelectFiles.size(); i < size; i++) {
                list.add(mSelectFiles.get(i).getPath());
            }
        }
    }

    @Override
    protected void onDestroy() {
        ChoosePhotoModel.getInstance().clear();
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                onBackPressed();
                break;
            case R.id.tv_practicing_time:
                CommonUtils.hideKeyBoard(mActivity);
                ChoosePropertyActivity.toChooseProperty(mActivity, "执业时间", -1, 0, GET_TIME);
                break;
            case R.id.tv_category:
                CommonUtils.hideKeyBoard(mActivity);
                InformationCategoryChooseActivity.toChooseInformationCategory(mActivity, InformationType.Law.getValue(), InformationCategoryChooseActivity.FROM_INFORMATION_PUBLISH_PAGE, GET_LAW_CATEGORY);
                break;
            case R.id.tv_local_area:
                CommonUtils.hideKeyBoard(mActivity);
                ChooseProvinceActivity.toChooseProvince(mActivity, GET_LOCAL_AREA);
                break;
            case R.id.tv_release_area:
                CommonUtils.hideKeyBoard(mActivity);
                ChoosePublishAreaActivity.toChoosePublishArea(mActivity, GET_AREA);
                break;
            case R.id.tv_get_code:
                mPrePhone = etPhone.getText().toString().trim();
                if (!CheckUtils.isNoEmptyStr(etPhone.getText().toString().trim())) {
                    ToastUtils.displayMsg("请输入手机号", this);
                    return;
                }
                getCheckCode();
                break;
            case R.id.tv_publish_info:
                CommonUtils.hideKeyBoard(mActivity);
                if (checkCanPublish()) {
                    getInformationFeeList(null, agentId, InformationType.Law.getValue(), mPositionType, OrderType.SendInformation.getValue());
                }
                break;
            case R.id.tv_change:
                changeGetCodeLabel("");
                break;
            case R.id.take_photo:
                if (!CheckUtils.hasCamera(mActivity)) {
                    toast("您的手机上没有检测到相机");
                    return;
                }
                Intent camera = new Intent(mActivity, CameraActivity.class);
                ChoosePhotoModel.getInstance().setFrom(mActivity.getClass().getName());
                startActivity(camera);
                dismissPopupWindow();
                break;
            case R.id.select_photo:
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + ActivitySchemeManager.URL_GET_IMAGE_LIST);
                ChoosePhotoModel.getInstance().setFrom(mActivity.getClass().getName());
                dismissPopupWindow();
                break;
            case R.id.feed_back_cancel:
                dismissPopupWindow();
                break;
        }
        super.onClick(v);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case GET_LAW_CATEGORY:
                    mPositionType = data.getLongExtra(InformationCategoryChooseActivity.CATEGORY_ID, -1);
                    String name = data.getStringExtra(InformationCategoryChooseActivity.CATEGORY_NAME);
                    tvCategory.setText(name);
                    return;
                case GET_LOCAL_AREA:
                    //所在省份
                    ProvinceBean province = (ProvinceBean) data.getSerializableExtra(ChooseCityActivity.PROVINCE);
                    tvLocalArea.setText(province.getName());
                    whereProvince = province.getId();
                    return;
                case GET_TIME:
                    tvPracticingTime.setText(data.getStringExtra(ChoosePropertyActivity.PROPERTY));
                    return;
                case GET_AREA:
                    //发布区域
                    Agent agent = (Agent) data.getSerializableExtra(ChoosePublishAreaActivity.AGENT);
                    agentId = agent.getId();
                    String area = "";
                    if (agent.getProvinceName() != null) area = agent.getProvinceName();
                    if (agent.getCityName() != null && !area.equals(agent.getCityName()))
                        area += agent.getCityName();
                    if (agent.getAgentType() == 1 && agent.getDistrictName() != null)
                        area += agent.getDistrictName();
                    tvReleaseArea.setText(area);
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
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void dismissPopupWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
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

    private void publish(final InformationFreeStandard informationFreeStandard) {
        showUploadDialog();

        Map<String, Object> map = new HashMap<>();
        map.put("informationType", InformationType.Law.getValue());
        map.put("title", etName.getText().toString().trim());
        map.put("imgs", imageUrls.toString());
        map.put("mobile", etPhone.getText().toString().trim());
        map.put("description", etRequirements.getText().toString().trim());
        map.put("agentId", agentId);
        map.put("type", "1");
        map.put("headImg", avatarUrl);
        map.put("name", etName.getText().toString().trim());
        map.put("whereCompany", etCompany.getText().toString().trim());
        map.put("practiceTime", tvPracticingTime.getText());
        map.put("goodField", etField.getText().toString().trim());
        map.put("whereProvince", whereProvince);
        map.put("whereProvinceName", tvLocalArea.getText());
        map.put("personalProfile", etRequirements.getText().toString().trim());
        map.put("categoryId", mPositionType);
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

        if (llBottomLabel.getVisibility() == View.VISIBLE) {
            map.put("code", etCode.getText().toString().trim());
        }
        VolleyOperater<InformationLawModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CREATE_INFORMATION, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMaterialDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    InformationLawModel model = (InformationLawModel) obj;
                    if (model.getCode() == 0) {
                        ChooseCityModel.getInstance().setHasChanged(true);
                        if (informationFreeStandard == null) {
                            toast("已发布");
                        } else {
                            tvPublish.setEnabled(true);
                            if (BigDecimal.ZERO.compareTo(informationFreeStandard.getPrice()) < 0) {
                                PayActivity.toPay(mActivity, model.getValue().getId(), model.getValue().getInformationOrder().getId(),
                                        InformationType.Law.getValue(), model.getValue().getAgentId());
                            } else {
//                                InformationDetailActivity.toInformationDetail(mActivity, model.getValue().getId(), InformationType.Recruit.getValue());
                                MyPublishInformationActivity.toMyPublishInformationList(mActivity, InformationType.Law.getValue());
                            }
                        }
                        finish();
                    }
                }
            }
        }, InformationLawModel.class);
    }

    private boolean checkCanPublish() {
        if (TextUtils.isEmpty(etName.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写您的姓名", mActivity);
            return false;
        }
        if (!CommonUtils.checkJobname(etName.getText().toString().trim())) {
            ToastUtils.displayMsg("用户名不能有空格和特殊符号", mActivity);
            return false;
        }
        if (etName.getText().toString().trim().length() < 2 || etName.getText().toString().trim().length() > 6) {
            ToastUtils.displayMsg("姓名字数限制为最少2个字，最多6个字", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(etCompany.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写您所在的公司", mActivity);
            return false;
        }
        if (etCompany.getText().toString().trim().length() > 20) {
            ToastUtils.displayMsg("所在的公司字数限制为20个字", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(tvPracticingTime.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择您的执业时间", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(tvCategory.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择您的专业类别", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(etField.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择您擅长的领域", mActivity);
            return false;
        }
        if (etField.getText().toString().trim().length() < 6 || etField.getText().toString().trim().length() > 30) {
            ToastUtils.displayMsg("擅长领域字数最少6个字，最多限制30个字", mActivity);
            return false;
        }

        if (TextUtils.isEmpty(tvLocalArea.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择您所在的区域", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(tvReleaseArea.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择您所发布的区域", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(etRequirements.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写您的亮点、成绩或实践经验", mActivity);
            return false;
        }
        if (etRequirements.getText().toString().length() < 50) {
            ToastUtils.displayMsg("个人简介不得少于50字", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(etPhone.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写您的手机号", mActivity);
            return false;
        }
        if (llBottomLabel.getVisibility() == View.VISIBLE && TextUtils.isEmpty(etCode.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写验证码", mActivity);
            return false;
        }

        if (TextUtils.isEmpty(avatarUrl)) {
            ToastUtils.displayMsg("请选择照片", mActivity);
            return false;
        }

        imageUrls.setLength(0);
        for (int i = 0, size = mSelectFiles.size(); i < size; i++) {
            if (TextUtils.isEmpty(mSelectFiles.get(i).getUrl())) continue;
            if (i == size - 1) {
                imageUrls.append(mSelectFiles.get(i).getUrl());
            } else {
                imageUrls.append(mSelectFiles.get(i).getUrl() + ";");
            }
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

        return true;
    }

    private void showUploadDialog() {
        if (mMaterialDialog == null) {
            mMaterialDialog = new MaterialDialog(mActivity);
            mMaterialDialog.setCanceledOnTouchOutside(true);
        }
        mMaterialDialog.setMessage("正在提交...");
        mMaterialDialog.show();
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
                    getInformationFeeList(null, agentId, InformationType.Law.getValue(), null, OrderType.SendInformation.getValue());
                    mDialog.dismiss();
                }

            }, "去上传", "仍要发布", "", "您选取的照片还有没上传的哦~~");
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.show();
        } else {
            mDialog.show();
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

    private void showPopupWindow() {
        if (mPopupWindow == null) {
            initPopupWindow();
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        } else if (!mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
    }

    /**
     * 获取验证码
     */
    private void getCheckCode() {
        timeTick = true;
        etPhone.setEnabled(false);
        tvGetCode.setEnabled(false);
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", etPhone.getText().toString().trim());
        VolleyOperater<SendSmsModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_NEW_SEND_RELEASE_INFORMATION_SMS, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
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
                    tvPublish.setEnabled(true);
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
    public boolean onTouchEvent(MotionEvent event) {
        CommonUtils.hideKeyBoard(mActivity);
        return super.onTouchEvent(event);
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
}

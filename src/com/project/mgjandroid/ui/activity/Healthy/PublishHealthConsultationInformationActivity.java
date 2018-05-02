package com.project.mgjandroid.ui.activity.healthy;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
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
import com.project.mgjandroid.model.information.InformationHealthModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.CameraActivity;
import com.project.mgjandroid.ui.activity.ImageUploadActivity;
import com.project.mgjandroid.ui.activity.UploadPhotoActivity;
import com.project.mgjandroid.ui.activity.information.ChoosePublishAreaActivity;
import com.project.mgjandroid.ui.activity.information.InformationCategoryChooseActivity;
import com.project.mgjandroid.ui.activity.information.MyPublishInformationActivity;
import com.project.mgjandroid.ui.activity.information.PayActivity;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.materialdialog.MaterialDialog;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.mgjandroid.R.id.tv_publish_info;

/**
 * Created by Administrator on 2016/11/21.
 */

public class PublishHealthConsultationInformationActivity extends ImageUploadActivity {

    private static final int GET_AREA = 101;
    private static final int GET_ADMINISTRATIVE_OFFICE = 102;
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.tv_take_photo)
    private TextView tvTakePhoto;
    @InjectView(R.id.et_name)
    private EditText etName;
    @InjectView(R.id.et_hospital)
    private EditText etHospital;
    @InjectView(R.id.tv_administrative_office)
    private TextView tvAdministrativeOffice;
    @InjectView(R.id.et_job_name)
    private EditText etJobName;
    @InjectView(R.id.et_expertise)
    private EditText etExpertise;
    @InjectView(R.id.tv_release_area)
    private TextView tvReleaseArea;
    @InjectView(R.id.et_personal_profile)
    private EditText etPersonalProfile;
    @InjectView(R.id.tv_health_length)
    private TextView tvHealthLength;
    @InjectView(R.id.et_phone)
    private EditText etPhone;
    @InjectView(R.id.tv_get_code)
    private TextView tvGetCode;
    @InjectView(R.id.et_code)
    private EditText etCode;
    @InjectView(tv_publish_info)
    private TextView tvPublish;
    @InjectView(R.id.tv_change)
    private TextView tvChange;
    @InjectView(R.id.get_code_line)
    private View vBottomLine;
    @InjectView(R.id.code_label)
    private LinearLayout llBottomLabel;

    private long mPositionType = -1;
    private long mCity = 0;
    private long mDistrict = 0;
    private String mCityCode = "";
    private long mProvince = 0;


    private PopupWindow mPopupWindow;
    private CustomDialog mDialog;
    private boolean timeTick = false;
    private ArrayList<UploadPhoto> mSelectFiles = new ArrayList<>();
    private StringBuffer imageUrls = new StringBuffer();
    private long agentId = 0;
    private String mPhone;
    private String mPrePhone;
    private MaterialDialog mMaterialDialog;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_publish_health_consultation_information);
        Injector.get(this).inject();
        initView();
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

    private void initView() {
        tvTitle.setText("发布健康咨询信息");

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
        tvReleaseArea.setOnClickListener(this);
        tvGetCode.setOnClickListener(this);
        tvChange.setOnClickListener(this);
        tvPublish.setOnClickListener(this);
        tvAdministrativeOffice.setOnClickListener(this);
        etPersonalProfile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvHealthLength.setText(s.toString().trim().length() + "/500");
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                onBackPressed();
                break;
            case R.id.tv_administrative_office:
                CommonUtils.hideKeyBoard(mActivity);
                InformationCategoryChooseActivity.toChooseInformationCategory(mActivity, InformationType.Health.getValue(), InformationCategoryChooseActivity.FROM_INFORMATION_PUBLISH_PAGE, GET_ADMINISTRATIVE_OFFICE);
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
            case tv_publish_info:
                CommonUtils.hideKeyBoard(mActivity);
                if (checkCanPublish()) {
//                    tvPublish.setEnabled(false);
                    getInformationFeeList(null, agentId, InformationType.Health.getValue(), mPositionType, OrderType.SendInformation.getValue());
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
                case GET_ADMINISTRATIVE_OFFICE:
                    mPositionType = data.getLongExtra(InformationCategoryChooseActivity.CATEGORY_ID, -1);
                    String name = data.getStringExtra(InformationCategoryChooseActivity.CATEGORY_NAME);
                    tvAdministrativeOffice.setText(name);
                    return;
                case GET_AREA:
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
                        mDistrict = 1;
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

        HashMap<String, Object> map = new HashMap<>();
        map.put("type", "1");
        map.put("informationType", InformationType.Health.getValue());
        map.put("title", etName.getText().toString().trim());
        map.put("headImg", avatarUrl);
        map.put("mobile", etPhone.getText().toString().trim());
        map.put("description", etPersonalProfile.getText().toString().trim());
        map.put("categoryId", mPositionType);

        map.put("name", etName.getText().toString().trim());
        map.put("hospital", etHospital.getText().toString().trim());
        map.put("departments", tvAdministrativeOffice.getText());
        map.put("professionalTitle", etJobName.getText().toString().trim());
        map.put("doctorExpertise", etExpertise.getText().toString().trim());
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
        map.put("agentId", agentId);
        if (llBottomLabel.getVisibility() == View.VISIBLE) {
            map.put("code", etCode.getText().toString().trim());
        }
        VolleyOperater<InformationHealthModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CREATE_INFORMATION, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMaterialDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    InformationHealthModel model = (InformationHealthModel) obj;
                    if (model.getCode() == 0) {
                        ChooseCityModel.getInstance().setHasChanged(true);
                        if (informationFreeStandard == null) {
                            toast("已发布");
                        } else {
                            if (BigDecimal.ZERO.compareTo(informationFreeStandard.getPrice()) < 0) {
                                PayActivity.toPay(mActivity, model.getValue().getId(), model.getValue().getInformationOrder().getId(),
                                        InformationType.Health.getValue(), model.getValue().getAgentId());
                            } else {
//                                InformationDetailActivity.toInformationDetail(mActivity, model.getValue().getId(), InformationType.Recruit.getValue());
                                MyPublishInformationActivity.toMyPublishInformationList(mActivity, InformationType.Health.getValue());
                            }
                        }
                        finish();
                    }
                }
            }
        }, InformationHealthModel.class);
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
        if (TextUtils.isEmpty(etHospital.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写您所在的医院", mActivity);
            return false;
        }
        if (etHospital.getText().toString().trim().length() < 6 || etHospital.getText().toString().trim().length() > 30) {
            ToastUtils.displayMsg("所在的医院字数限制为最少6个字，最多16个字", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(tvAdministrativeOffice.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写您所在的科室", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(etJobName.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写您的职称", mActivity);
            return false;
        }
        if (etJobName.getText().toString().trim().length() < 2 || etJobName.getText().toString().trim().length() > 8) {
            ToastUtils.displayMsg("职称字数最少2个字，最多8个字", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(etExpertise.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写您的专长", mActivity);
            return false;
        }
        if (etExpertise.getText().toString().trim().length() < 6 || etExpertise.getText().toString().trim().length() > 30) {
            ToastUtils.displayMsg("专长字数最少6个字，最多限制30个字", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(tvReleaseArea.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择您所发布的区域", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(etPersonalProfile.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写您的亮点、成绩或实践经验(不少于50字)", mActivity);
            return false;
        }
        if (etPersonalProfile.getText().toString().length() < 50) {
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
                    getInformationFeeList(null, agentId, InformationType.Health.getValue(), null, OrderType.SendInformation.getValue());
                    mDialog.dismiss();
                }

            }, "去上传", "仍要发布", "", "您选取的照片还有没上传的哦~~");
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.show();
        } else {
            mDialog.show();
        }
    }

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

package com.project.mgjandroid.ui.activity.education;

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
import com.project.mgjandroid.model.EducationDetailModel;
import com.project.mgjandroid.model.InformationAreaModel;
import com.project.mgjandroid.model.SendSmsModel;
import com.project.mgjandroid.model.SmsLoginModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.CameraActivity;
import com.project.mgjandroid.ui.activity.ChooseEducationTeacherOrTutorshipStageActivity;
import com.project.mgjandroid.ui.activity.UploadPhotoActivity;
import com.project.mgjandroid.ui.activity.employment.PublishRecruitmentActivity;
import com.project.mgjandroid.ui.activity.information.ChoosePublishAreaActivity;
import com.project.mgjandroid.ui.activity.information.InformationCategoryChooseActivity;
import com.project.mgjandroid.ui.activity.information.MyPublishInformationActivity;
import com.project.mgjandroid.ui.activity.information.PayActivity;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuandi on 2016/7/22.
 */
public class PublishFamilyEducationInfoActivity extends BaseActivity {
    public final static int REQUEST_CHOOSE_EDU_TEACHER = 1001;
    public final static int REQUEST_CHOOSE_EDU_TUTORSHIP = 1002;
    private static final int REQUEST_CHOOSE_TYPE = 1003;
    private static final int REQUEST_CHOOSE_AREA = 1004;
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.edu_img)
    private ImageView ivImage;
    @InjectView(R.id.edu_photo)
    private ImageView ivPhoto;
    @InjectView(R.id.edu_photo_2)
    private ImageView ivPhoto2;
    @InjectView(R.id.tv_take_photo)
    private TextView tvPhoto;
    @InjectView(R.id.edu_title_layout)
    private LinearLayout titleLayout;
    @InjectView(R.id.edu_title)
    private EditText etTitle;
    @InjectView(R.id.edu_name_layout)
    private LinearLayout nameLayout;
    @InjectView(R.id.edu_name)
    private EditText etName;
    @InjectView(R.id.edu_teacher_layout)
    private LinearLayout teacherLayout;
    @InjectView(R.id.edu_teacher_item_layout)
    private RelativeLayout teacherItem;
    @InjectView(R.id.edu_teacher)
    private TextView tvTeacher;
    @InjectView(R.id.edu_teacher_layout_divide)
    private View teacherDivide;
    @InjectView(R.id.edu_class_category_layout)
    private RelativeLayout rlChooseCategory;
    @InjectView(R.id.edu_class_category)
    private TextView tvCategory;
    @InjectView(R.id.edu_period_layout)
    private LinearLayout periodLayout;
    @InjectView(R.id.edu_period_item_layout)
    private RelativeLayout periodItem;
    @InjectView(R.id.edu_period)
    private TextView tvPeriod;
    @InjectView(R.id.edu_address_layout)
    private LinearLayout addressLayout;
    @InjectView(R.id.edu_address)
    private EditText etAddress;
    @InjectView(R.id.edu_choose_area)
    private RelativeLayout rlChooseArea;
    @InjectView(R.id.edu_area)
    private TextView etArea;
    @InjectView(R.id.edu_content)
    private EditText etContent;
    @InjectView(R.id.tv_content_length)
    private TextView tvContentLength;
    @InjectView(R.id.edu_phone)
    private EditText etPhone;
    @InjectView(R.id.edu_get_code)
    private TextView tvGetCode;
    @InjectView(R.id.edu_code)
    private EditText etCode;
    @InjectView(R.id.edu_publish)
    private TextView tvPublish;
    @InjectView(R.id.edu_change)
    private TextView tvChange;
    @InjectView(R.id.edu_get_code_line)
    private View vBottomLine;
    @InjectView(R.id.edu_code_label)
    private LinearLayout llBottomLabel;

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
    private long educationTeacherTypeId;
    private long educationTutorshipStageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_education_info);
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

        ChoosePhotoModel.getInstance().setMaxCount(12);
        ChoosePhotoModel.getInstance().setCurrentActivity(this.getClass());

        ivImage.setImageResource(R.drawable.edu_training_default);

        tvTitle.setText("发布家教信息");
        nameLayout.setVisibility(View.GONE);
        addressLayout.setVisibility(View.GONE);

        ivBack.setOnClickListener(this);
        ivImage.setOnClickListener(this);
        ivPhoto.setOnClickListener(this);
        ivPhoto2.setOnClickListener(this);
        tvPhoto.setOnClickListener(this);
        teacherItem.setOnClickListener(this);
        periodItem.setOnClickListener(this);
        tvPublish.setOnClickListener(this);
        tvChange.setOnClickListener(this);
        tvGetCode.setOnClickListener(this);
        rlChooseCategory.setOnClickListener(this);
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
        super.onClick(v);
        switch (v.getId()) {
            case R.id.edu_img:
                CommonUtils.hideKeyBoard(mActivity);
                break;
            case R.id.edu_photo_2:
                CommonUtils.hideKeyBoard(mActivity);
                Intent dealPhoto = new Intent(mActivity, UploadPhotoActivity.class);
                dealPhoto.putExtra("from", mActivity.getClass().toString());
                startActivity(dealPhoto);
                break;

            case R.id.edu_photo:
            case R.id.tv_take_photo:
                CommonUtils.hideKeyBoard(mActivity);
                showPopupWindow();
                break;

            case R.id.edu_publish:
                CommonUtils.hideKeyBoard(mActivity);
                if (checkCanPublish()) {
//                    tvPublish.setEnabled(false);
                    getInformationFeeList(null, agentId, InformationType.FamilyEducation.getValue(), mPositionCategory, OrderType.SendInformation.getValue());
                }
                break;

            case R.id.edu_change:
                changeGetCodeLabel("");
                break;

            case R.id.edu_get_code:
                mPrePhone = etPhone.getText().toString().trim();
                if (!CheckUtils.isNoEmptyStr(etPhone.getText().toString().trim())) {
                    ToastUtils.displayMsg("请输入手机号", this);
                    return;
                }
                getCheckCode();
                break;

            case R.id.edu_class_category_layout:
                CommonUtils.hideKeyBoard(mActivity);
                InformationCategoryChooseActivity.toChooseInformationCategory(mActivity, InformationType.FamilyEducation.getValue(), InformationCategoryChooseActivity.FROM_INFORMATION_PUBLISH_PAGE, REQUEST_CHOOSE_TYPE);
                break;

            case R.id.edu_choose_area:
                CommonUtils.hideKeyBoard(mActivity);
                ChoosePublishAreaActivity.toChoosePublishArea(mActivity, REQUEST_CHOOSE_AREA);
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
            case R.id.edu_teacher_item_layout:
                Intent teacher = new Intent(mActivity, ChooseEducationTeacherOrTutorshipStageActivity.class);
                teacher.putExtra("type", ChooseEducationTeacherOrTutorshipStageActivity.TYPE_TEACHER_TYPE);
                startActivityForResult(teacher, REQUEST_CHOOSE_EDU_TEACHER);
                break;
            case R.id.edu_period_item_layout:
                Intent tutorShip = new Intent(mActivity, ChooseEducationTeacherOrTutorshipStageActivity.class);
                tutorShip.putExtra("type", ChooseEducationTeacherOrTutorshipStageActivity.TYPE_TUTORSHIP_STAGE);
                startActivityForResult(tutorShip, REQUEST_CHOOSE_EDU_TUTORSHIP);
                break;
        }
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
        if (mSelectFiles.size() > 0) {
            Bitmap bitmap = BitmapUtil.compressBitmap(mSelectFiles.get(0).getPath(), 1280);
            ivImage.setImageBitmap(bitmap);
            ivPhoto.setVisibility(View.GONE);
            ivPhoto2.setVisibility(View.VISIBLE);
            tvPhoto.setVisibility(View.GONE);
        } else {
            if (type == 1) {
                ivImage.setImageResource(R.drawable.edu_training_default);
            } else {
                ivImage.setImageResource(R.drawable.family_edu_default);
            }
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
                case REQUEST_CHOOSE_EDU_TEACHER:
                    educationTeacherTypeId = data.getLongExtra(ChooseEducationTeacherOrTutorshipStageActivity.ID, -1);
                    tvTeacher.setText(data.getStringExtra(ChooseEducationTeacherOrTutorshipStageActivity.NAME));
                    return;
                case REQUEST_CHOOSE_EDU_TUTORSHIP:
                    educationTutorshipStageId = data.getLongExtra(ChooseEducationTeacherOrTutorshipStageActivity.ID, -1);
                    tvPeriod.setText(data.getStringExtra(ChooseEducationTeacherOrTutorshipStageActivity.NAME));
                    return;
                case REQUEST_CHOOSE_TYPE:
                    mPositionCategory = data.getLongExtra(InformationCategoryChooseActivity.CATEGORY_ID, -1);
                    String name = data.getStringExtra(InformationCategoryChooseActivity.CATEGORY_NAME);
                    tvCategory.setText(name);
                    return;
                case REQUEST_CHOOSE_AREA:
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

    private boolean checkCanPublish() {
        if (TextUtils.isEmpty(etTitle.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写标题", mActivity);
            return false;
        }
        if (etTitle.getText().toString().trim().length() < 6) {
            ToastUtils.displayMsg("标题最少6个字", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(tvTeacher.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择教师身份", mActivity);
            return false;
        }
        if (mPositionCategory == -1) {
            ToastUtils.displayMsg("请选择课程类别", mActivity);
            return false;
        }
        if (type == 2 && TextUtils.isEmpty(tvPeriod.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择辅导阶段", mActivity);
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
        imageUrls.setLength(0);
        if (CheckUtils.isEmptyList(mSelectFiles)) {
            ToastUtils.displayMsg("请选择照片", mActivity);
            return false;
        }
        for (int i = 0, size = mSelectFiles.size(); i < size; i++) {
            if (TextUtils.isEmpty(mSelectFiles.get(i).getUrl())) continue;
            imageUrls.append(mSelectFiles.get(i).getUrl() + ";");
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
                    getInformationFeeList(null, agentId, InformationType.FamilyEducation.getValue(), mPositionCategory, OrderType.SendInformation.getValue());
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
        map.put("title", etTitle.getText().toString().trim());
        map.put("informationType", InformationType.FamilyEducation.getValue());
        map.put("categoryId", mPositionCategory);
        map.put("educationTeacherTypeId", educationTeacherTypeId);
        map.put("educationTutorshipStageId", educationTutorshipStageId);
        map.put("educationCategoryId", mPositionCategory);
        map.put("mobile", etPhone.getText().toString().trim());
        map.put("description", etContent.getText().toString().trim());
        map.put("type", 2);
        map.put("imgs", imageUrls.toString());
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
        VolleyOperater<EducationDetailModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CREATE_INFORMATION, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMaterialDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    EducationDetailModel model = (EducationDetailModel) obj;
                    if (model.getCode() == 0) {
                        ChooseCityModel.getInstance().setHasChanged(true);
                        if (informationFreeStandard == null) {
                            toast("已发布");
                        } else {
                            tvPublish.setEnabled(true);
                            if (BigDecimal.ZERO.compareTo(informationFreeStandard.getPrice()) < 0) {
                                PayActivity.toPay(mActivity, model.getValue().getId(), model.getValue().getInformationOrder().getId(),
                                        InformationType.FamilyEducation.getValue(), model.getValue().getAgentId());
                            } else {
//                                InformationDetailActivity.toInformationDetail(mActivity, model.getValue().getId(), InformationType.Recruit.getValue());
                                MyPublishInformationActivity.toMyPublishInformationList(mActivity, InformationType.FamilyEducation.getValue());
                            }
                        }
                        finish();
                    }
                }
            }
        }, EducationDetailModel.class);
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

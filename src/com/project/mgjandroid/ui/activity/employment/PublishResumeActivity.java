package com.project.mgjandroid.ui.activity.employment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
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
import com.project.mgjandroid.model.information.PositionInformationModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.CameraActivity;
import com.project.mgjandroid.ui.activity.ImageUploadActivity;
import com.project.mgjandroid.ui.activity.UploadPhotoActivity;
import com.project.mgjandroid.ui.activity.information.ChooseJobRecruitmentPublishAreaActivity;
import com.project.mgjandroid.ui.activity.information.ChoosePropertyActivity;
import com.project.mgjandroid.ui.activity.information.ChoosePublishAreaActivity;
import com.project.mgjandroid.ui.activity.information.InformationCategoryChooseActivity;
import com.project.mgjandroid.ui.activity.information.MyPublishInformationActivity;
import com.project.mgjandroid.ui.activity.information.PayActivity;
import com.project.mgjandroid.ui.adapter.ImageRecyclerAdapter;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.materialdialog.MaterialDialog;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.DipToPx;
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

/**
 * Created by yuandi on 2016/11/11.
 */

public class PublishResumeActivity extends ImageUploadActivity {

    private static final int GET_BIRTH = 101;
    private static final int GET_EDUCATION = 102;
    private static final int GET_WORKING_EXPIRENCE = 103;
    private static final int GET_SALARY = 104;
    private static final int GET_POSITION_TYPE = 105;
    private static final int GET_AREA = 106;

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.scroll_view)
    private ScrollView scrollView;
    @InjectView(R.id.tv_take_photo)
    private TextView tvTakePhoto;
    @InjectView(R.id.et_name)
    private EditText etName;
    @InjectView(R.id.cb_man)
    private CheckBox cbMale;
    @InjectView(R.id.cb_woman)
    private CheckBox cbFemale;
    @InjectView(R.id.tv_birth)
    private TextView tvBirth;
    @InjectView(R.id.tv_educational_background)
    private TextView tvEduBg;
    @InjectView(R.id.tv_work_experience)
    private TextView tvWorkExp;
    @InjectView(R.id.tv_expected_salary)
    private TextView tvSalary;
    @InjectView(R.id.tv_expected_position)
    private TextView tvPosition;
    @InjectView(R.id.tv_publish_area)
    private TextView tvPublishArea;
    @InjectView(R.id.et_individual_profiles)
    private EditText etIndividualProfiles;
    @InjectView(R.id.tv_content_length)
    private TextView tvContentLength;
    @InjectView(R.id.recycler_view)
    private RecyclerView recyclerView;
    @InjectView(R.id.et_phone)
    private EditText etPhone;
    @InjectView(R.id.tv_get_code)
    private TextView tvGetCode;
    @InjectView(R.id.et_code)
    private EditText etCode;
    @InjectView(R.id.tv_change)
    private TextView tvChange;
    @InjectView(R.id.get_code_line)
    private View vBottomLine;
    @InjectView(R.id.code_label)
    private LinearLayout llBottomLabel;
    @InjectView(R.id.tv_publish_info)
    private TextView tvPublishInfo;

    private long mPositionType = -1;
    private long mProvince = 0;
    private long mCity = 0;
    private long mDistrict = 0;
    private String mCityCode = "";
    private long agentId = 0;

    private String mPhone;
    private String mPrePhone;
    private boolean timeTick = false;
    private MaterialDialog mMaterialDialog;
    private CustomDialog mDialog;
    private PopupWindow mPopupWindow;
    private ArrayList<UploadPhoto> mSelectFiles = new ArrayList<>();
    private StringBuffer imageUrls = new StringBuffer();
    private ImageRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_publish_resume);
        Injector.get(this).inject();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvPublishInfo.setEnabled(true);
        mSelectFiles = ChoosePhotoModel.getInstance().getmUploadPhotoList();
        List<String> list = new ArrayList<>();
        if (CheckUtils.isNoEmptyList(mSelectFiles)) {
            for (int i = 0, size = mSelectFiles.size(); i < size; i++) {
                list.add(mSelectFiles.get(i).getPath());
            }
        }
        if (adapter != null) adapter.setList(list);
    }

    @Override
    protected void onDestroy() {
        ChoosePhotoModel.getInstance().clear();
        super.onDestroy();
    }

    private void initView() {
        tvTitle.setText("发布简历");
        SmsLoginModel.ValueEntity.AppUserEntity userInfo = App.getInstance().getUserInfo();
        if (userInfo != null) {
            mPhone = userInfo.getMobile();
            changeGetCodeLabel(mPhone);
        } else {
            changeGetCodeLabel(null);
        }

        ChoosePhotoModel.getInstance().setMaxCount(12);
        ChoosePhotoModel.getInstance().setCurrentActivity(this.getClass());

        adapter = new ImageRecyclerAdapter(mActivity, true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new ImageRecyclerAdapter.OnClickListener() {
            @Override
            public void onClickImage(int currentItem) {
                Intent dealPhoto = new Intent(mActivity, UploadPhotoActivity.class);
                dealPhoto.putExtra("from", mActivity.getClass().toString());
                startActivity(dealPhoto);
            }

            @Override
            public void onClichAdd() {
                showPopupWindow();
            }
        });

        ivAvatar = (CornerImageView) findViewById(R.id.iv_avatar);
        ivBack.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
        tvTakePhoto.setOnClickListener(this);
        cbMale.setOnClickListener(this);
        cbFemale.setOnClickListener(this);
        tvBirth.setOnClickListener(this);
        tvEduBg.setOnClickListener(this);
        tvWorkExp.setOnClickListener(this);
        tvSalary.setOnClickListener(this);
        tvPosition.setOnClickListener(this);
        tvPublishArea.setOnClickListener(this);
        tvChange.setOnClickListener(this);
        tvGetCode.setOnClickListener(this);
        tvPublishInfo.setOnClickListener(this);
        etIndividualProfiles.addTextChangedListener(new TextWatcher() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR2)
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!CommonUtils.checkViewVisibleInWindow(mActivity, tvContentLength)) {
                    int Y;
                    if (CommonUtils.getCurrentCursorLine(etIndividualProfiles) == 3) {
                        Y = DipToPx.sp2px(mActivity, 17);
                    } else if (CommonUtils.getCurrentCursorLine(etIndividualProfiles) == 2) {
                        Y = DipToPx.sp2px(mActivity, 17 * 2);
                    } else {
                        Y = DipToPx.sp2px(mActivity, 17 * 3);
                    }
                    scrollView.smoothScrollBy(0, Y);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvContentLength.setText(s.toString().trim().length() + "/500");
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
        etIndividualProfiles.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (view.getId() == R.id.et_individual_profiles) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        scrollView.setFocusable(true);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
//                if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                    CommonUtils.hideKeyBoard(mActivity);
//                }
                return false;
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
                tvPublishArea.setText(valueBean.getName());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                onBackPressed();
                return;
            case R.id.cb_man:
                CommonUtils.hideKeyBoard(mActivity);
                if (cbMale.isChecked()) {
                    cbFemale.setChecked(false);
                } else {
                    cbMale.setChecked(true);
                }
                return;
            case R.id.cb_woman:
                CommonUtils.hideKeyBoard(mActivity);
                if (cbFemale.isChecked()) {
                    cbMale.setChecked(false);
                } else {
                    cbFemale.setChecked(true);
                }
                return;
            case R.id.tv_birth:
                CommonUtils.hideKeyBoard(mActivity);
                ChoosePropertyActivity.toChooseProperty(mActivity, "出生年份", 0, 0, GET_BIRTH);
                return;
            case R.id.tv_educational_background:
                CommonUtils.hideKeyBoard(mActivity);
                ChoosePropertyActivity.toChooseProperty(mActivity, "最高学历", InformationType.Position.getValue(), 1, GET_EDUCATION);
                return;
            case R.id.tv_work_experience:
                CommonUtils.hideKeyBoard(mActivity);
                ChoosePropertyActivity.toChooseProperty(mActivity, "工作经验", InformationType.Position.getValue(), 2, GET_WORKING_EXPIRENCE);
                return;
            case R.id.tv_expected_salary:
                CommonUtils.hideKeyBoard(mActivity);
                ChoosePropertyActivity.toChooseProperty(mActivity, "期望薪资", InformationType.Position.getValue(), 3, GET_SALARY);
                return;
            case R.id.tv_expected_position:
                CommonUtils.hideKeyBoard(mActivity);
                InformationCategoryChooseActivity.toChooseInformationCategory(mActivity, InformationType.Position.getValue(), InformationCategoryChooseActivity.FROM_INFORMATION_PUBLISH_PAGE, GET_POSITION_TYPE);
                return;
            case R.id.tv_publish_area:
                CommonUtils.hideKeyBoard(mActivity);
                ChooseJobRecruitmentPublishAreaActivity.toChoosePublishArea(mActivity, GET_AREA);
                return;
            case R.id.tv_get_code:
                mPrePhone = etPhone.getText().toString().trim();
                if (!CheckUtils.isNoEmptyStr(etPhone.getText().toString().trim())) {
                    ToastUtils.displayMsg("请输入手机号", this);
                    return;
                }
                getCheckCode();
                return;
            case R.id.tv_publish_info:
                CommonUtils.hideKeyBoard(mActivity);
                if (checkCanPublish()) {
                    tvPublishInfo.setEnabled(false);
                    getInformationFeeList(null, agentId, InformationType.Position.getValue(), mPositionType, OrderType.SendInformation.getValue());
                }
                return;
            case R.id.tv_change:
                changeGetCodeLabel("");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case GET_BIRTH:
                    tvBirth.setText(data.getStringExtra(ChoosePropertyActivity.PROPERTY));
                    return;
                case GET_EDUCATION:
                    tvEduBg.setText(data.getStringExtra(ChoosePropertyActivity.PROPERTY));
                    return;
                case GET_WORKING_EXPIRENCE:
                    tvWorkExp.setText(data.getStringExtra(ChoosePropertyActivity.PROPERTY));
                    break;
                case GET_SALARY:
                    tvSalary.setText(data.getStringExtra(ChoosePropertyActivity.PROPERTY));
                    return;
                case GET_POSITION_TYPE:
                    mPositionType = data.getLongExtra(InformationCategoryChooseActivity.CATEGORY_ID, -1);
                    String name = data.getStringExtra(InformationCategoryChooseActivity.CATEGORY_NAME);
                    tvPosition.setText(name);
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
                    tvPublishArea.setText(area);
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

    private void publish(final InformationFreeStandard informationFreeStandard) {
        showUploadDialog();

        Map<String, Object> map = new HashMap<>();
        map.put("title", etName.getText().toString().trim());
        map.put("informationType", InformationType.Position.getValue());
        map.put("imgs", imageUrls.toString());
        map.put("mobile", etPhone.getText().toString().trim());
        map.put("description", etIndividualProfiles.getText().toString().trim());
        map.put("sex", cbMale.isChecked() ? 0 : 1);
        map.put("headImg", avatarUrl);
        map.put("birthday", tvBirth.getText());
        map.put("highestEducation", tvEduBg.getText());
        map.put("workExperience", tvWorkExp.getText());
        map.put("expectSalary", tvSalary.getText());
        map.put("expectPosition", tvPosition.getText());
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
        map.put("address", tvPublishArea.getText());
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
        VolleyOperater<PositionInformationModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CREATE_INFORMATION, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMaterialDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    PositionInformationModel model = (PositionInformationModel) obj;
                    if (model.getCode() == 0) {
                        ChooseCityModel.getInstance().setHasChanged(true);
                        if (informationFreeStandard == null) {
                            toast("已提交您发布的信息，请等待管理员审核后上线");
                            MyPublishInformationActivity.toMyPublishInformationList(mActivity, InformationType.Position.getValue());
                        } else {
                            tvPublishInfo.setEnabled(true);
                            if (BigDecimal.ZERO.compareTo(informationFreeStandard.getPrice()) < 0) {
                                PayActivity.toPay(mActivity, model.getValue().getId(), model.getValue().getInformationOrder().getId(),
                                        InformationType.Position.getValue(), model.getValue().getAgentId());
                            } else {
//                                InformationDetailActivity.toInformationDetail(mActivity, model.getValue().getId(), InformationType.Position.getValue());
                                MyPublishInformationActivity.toMyPublishInformationList(mActivity, InformationType.Position.getValue());
                            }
                        }

                    }
                }
            }
        }, PositionInformationModel.class);
    }

    private boolean checkCanPublish() {
        if (TextUtils.isEmpty(etName.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写姓名", mActivity);
            return false;
        }
        if (!CommonUtils.checkJobname(etName.getText().toString().trim())) {
            ToastUtils.displayMsg("用户名不能有空格和特殊符号", mActivity);
            return false;
        }
        if (!cbMale.isChecked() && !cbFemale.isChecked()) {
            ToastUtils.displayMsg("请选择您的性别", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(tvBirth.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择您的出生年份", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(tvEduBg.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择您的最高学历", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(tvWorkExp.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择您的工作经验", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(tvSalary.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择您期望的薪资", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(tvPosition.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择您期望的职位", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(tvPublishArea.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择您工作区域", mActivity);
            return false;
        }
        if (etIndividualProfiles.getText().toString().length() < 20) {
            ToastUtils.displayMsg("个人简介不得少于20字", mActivity);
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
//            ToastUtils.displayMsg("请选择照片",mActivity);
            return true;
        }

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
                    getInformationFeeList(null, agentId, InformationType.Position.getValue(), mPositionType, OrderType.SendInformation.getValue());
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
                    tvPublishInfo.setEnabled(true);
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
        tvPublishInfo.setEnabled(true);
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

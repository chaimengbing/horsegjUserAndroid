package com.project.mgjandroid.ui.activity.employment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import com.project.mgjandroid.model.information.PropertyArrayModel;
import com.project.mgjandroid.model.information.RecruitInformationModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.CameraActivity;
import com.project.mgjandroid.ui.activity.UploadPhotoActivity;
import com.project.mgjandroid.ui.activity.information.ChooseJobRecruitmentPublishAreaActivity;
import com.project.mgjandroid.ui.activity.information.ChoosePropertyActivity;
import com.project.mgjandroid.ui.activity.information.ChoosePublishAreaActivity;
import com.project.mgjandroid.ui.activity.information.InformationCategoryChooseActivity;
import com.project.mgjandroid.ui.activity.information.MyPublishInformationActivity;
import com.project.mgjandroid.ui.activity.information.PayActivity;
import com.project.mgjandroid.ui.view.LoadingDialog;
import com.project.mgjandroid.ui.view.NoScrollGridView;
import com.project.mgjandroid.ui.view.materialdialog.MaterialDialog;
import com.project.mgjandroid.utils.BitmapUtil;
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
import java.util.Map;

/**
 * Created by Administrator on 2016/11/12.
 */

public class PublishRecruitmentActivity extends BaseActivity {

    private static final int GET_NATURE = 101;
    private static final int GET_INDUSTRY = 102;
    private static final int GET_SIZE = 103;
    private static final int GET_POSITION_TYPE = 104;
    private static final int GET_NUMBERS = 105;
    private static final int GET_EDUCATION = 106;
    private static final int GET_AREA = 107;
    private static final int GET_WORKING_SENIORITY = 108;
    private static final int GET_SALARY = 109;

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.scroll_view)
    private ScrollView scrollView;
    @InjectView(R.id.iv_img)
    private ImageView ivImage;
    @InjectView(R.id.iv_photo)
    private ImageView ivPhoto;
    @InjectView(R.id.iv_photo_2)
    private ImageView ivPhoto2;
    @InjectView(R.id.tv_take_photo)
    private TextView tvPhoto;
    @InjectView(R.id.et_name)
    private EditText etName;
    @InjectView(R.id.et_address)
    private EditText etAddress;
    @InjectView(R.id.tv_nature)
    private TextView tvNature;
    @InjectView(R.id.tv_industry)
    private TextView tvIndustry;
    @InjectView(R.id.tv_size)
    private TextView tvSize;
    @InjectView(R.id.et_company_profiles)
    private EditText etCompanyProfiles;
    @InjectView(R.id.tv_profiles_length)
    private TextView tvProfilesLength;
    @InjectView(R.id.et_position)
    private EditText etPosition;
    @InjectView(R.id.tv_position_type)
    private TextView tvPositionType;
    @InjectView(R.id.tv_recruiting_numbers)
    private TextView tvNumbers;
    @InjectView(R.id.tv_education)
    private TextView tvEducation;
    @InjectView(R.id.tv_working_seniority)
    private TextView tvWorkingSeniority;
    @InjectView(R.id.tv_salary)
    private TextView tvSalary;
    @InjectView(R.id.tv_publish_area)
    private TextView tvPublishArea;
    @InjectView(R.id.et_requirements)
    private EditText etRequirements;
    @InjectView(R.id.tv_requirements_length)
    private TextView tvRequirementsLength;
    @InjectView(R.id.grid_view)
    private NoScrollGridView gridView;
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

    private String mPhone;
    private String mPrePhone;
    private boolean timeTick = false;
    private MaterialDialog mMaterialDialog;
    private CustomDialog mDialog;
    private PopupWindow mPopupWindow;
    private ArrayList<UploadPhoto> mSelectFiles = new ArrayList<>();
    private StringBuffer imageUrls = new StringBuffer();
    private StringBuffer welfares = new StringBuffer();
    private WelfareChooseAdapter adapter;
    private long agentId = 0;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_publish_recruitment);
        Injector.get(this).inject();
        initView();
        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvPublish.setEnabled(true);
        mSelectFiles = ChoosePhotoModel.getInstance().getmUploadPhotoList();
        if (mSelectFiles.size() > 0) {
            Bitmap bitmap = BitmapUtil.compressBitmap(mSelectFiles.get(0).getPath(), 1280);
            ivImage.setImageBitmap(bitmap);
            ivPhoto.setVisibility(View.GONE);
            ivPhoto2.setVisibility(View.VISIBLE);
            tvPhoto.setVisibility(View.GONE);
        } else {
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

    private void initView() {
        tvTitle.setText("发布招聘信息");
        SmsLoginModel.ValueEntity.AppUserEntity userInfo = App.getInstance().getUserInfo();
        if (userInfo != null) {
            mPhone = userInfo.getMobile();
            changeGetCodeLabel(mPhone);
        } else {
            changeGetCodeLabel(null);
        }
        String json = PreferenceUtils.getRecruitInformation(mActivity);
        if (!"{}".equals(json)) {
            try {
                JSONObject info = JSONObject.parseObject(json);
                if (info.getString("mobile").equals(etPhone.getText().toString().trim())) {
                    etName.setText(info.getString("companyName"));
                    etAddress.setText(info.getString("companyAddress"));
                    tvNature.setText(info.getString("companyType"));
                    tvIndustry.setText(info.getString("profession"));
                    tvSize.setText(info.getString("companyScale"));
                    etCompanyProfiles.setText(info.getString("description"));
                    tvProfilesLength.setText(etCompanyProfiles.getText().toString().length() + "/500");
                    etCompanyProfiles.requestFocus();
                    etCompanyProfiles.setSelection(etCompanyProfiles.getText().toString().length());
                    etName.requestFocus();
                    etName.setSelection(etCompanyProfiles.getText().toString().length());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ChoosePhotoModel.getInstance().setMaxCount(12);
        ChoosePhotoModel.getInstance().setCurrentActivity(this.getClass());

        adapter = new WelfareChooseAdapter(this);
        gridView.setAdapter(adapter);
        ivBack.setOnClickListener(this);
        ivImage.setOnClickListener(this);
        ivPhoto.setOnClickListener(this);
        ivPhoto2.setOnClickListener(this);
        tvPhoto.setOnClickListener(this);
        tvNature.setOnClickListener(this);
        tvIndustry.setOnClickListener(this);
        tvSize.setOnClickListener(this);
        tvPositionType.setOnClickListener(this);
        tvNumbers.setOnClickListener(this);
        tvEducation.setOnClickListener(this);
        tvWorkingSeniority.setOnClickListener(this);
        tvSalary.setOnClickListener(this);
        tvPublishArea.setOnClickListener(this);
        tvPublish.setOnClickListener(this);
        tvChange.setOnClickListener(this);
        tvGetCode.setOnClickListener(this);
        etCompanyProfiles.addTextChangedListener(new TextWatcher() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR2)
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!CommonUtils.checkViewVisibleInWindow(mActivity, tvProfilesLength)) {
                    int Y;
                    if (CommonUtils.getCurrentCursorLine(etCompanyProfiles) == 3) {
                        Y = DipToPx.sp2px(mActivity, 17);
                    } else if (CommonUtils.getCurrentCursorLine(etCompanyProfiles) == 2) {
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
                tvProfilesLength.setText(s.toString().trim().length() + "/500");
                if (s.toString().trim().length() > 500) {
                    ToastUtils.displayMsg("您输入的字数已超过500字", mActivity);
                }
            }
        });
        etRequirements.addTextChangedListener(new TextWatcher() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR2)
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!CommonUtils.checkViewVisibleInWindow(mActivity, tvRequirementsLength)) {
                    int Y;
                    if (CommonUtils.getCurrentCursorLine(etRequirements) == 3) {
                        Y = DipToPx.sp2px(mActivity, 17);
                    } else if (CommonUtils.getCurrentCursorLine(etRequirements) == 2) {
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
                tvRequirementsLength.setText(s.toString().trim().length() + "/500");
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
        etRequirements.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (view.getId() == R.id.et_requirements) {
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
        etCompanyProfiles.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (view.getId() == R.id.et_company_profiles) {
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
                break;
            case R.id.iv_img:
                CommonUtils.hideKeyBoard(mActivity);
                break;
            case R.id.iv_photo_2:
                CommonUtils.hideKeyBoard(mActivity);
                Intent dealPhoto = new Intent(mActivity, UploadPhotoActivity.class);
                dealPhoto.putExtra("from", mActivity.getClass().toString());
                startActivity(dealPhoto);
                break;
            case R.id.iv_photo:
            case R.id.tv_take_photo:
                CommonUtils.hideKeyBoard(mActivity);
                showPopupWindow();
                break;
            case R.id.tv_nature:
                CommonUtils.hideKeyBoard(mActivity);
                ChoosePropertyActivity.toChooseProperty(mActivity, "公司性质", InformationType.Recruit.getValue(), 1, GET_NATURE);
                break;
            case R.id.tv_industry:
                CommonUtils.hideKeyBoard(mActivity);
                ChoosePropertyActivity.toChooseProperty(mActivity, "从事行业", InformationType.Recruit.getValue(), 2, GET_INDUSTRY);
                break;
            case R.id.tv_size:
                CommonUtils.hideKeyBoard(mActivity);
                ChoosePropertyActivity.toChooseProperty(mActivity, "公司规模", InformationType.Recruit.getValue(), 3, GET_SIZE);
                break;
            case R.id.tv_position_type:
                CommonUtils.hideKeyBoard(mActivity);
                InformationCategoryChooseActivity.toChooseInformationCategory(mActivity, InformationType.Recruit.getValue(), InformationCategoryChooseActivity.FROM_INFORMATION_PUBLISH_PAGE, GET_POSITION_TYPE);
                break;
            case R.id.tv_recruiting_numbers:
                CommonUtils.hideKeyBoard(mActivity);
                ChoosePropertyActivity.toChooseProperty(mActivity, "招聘人数", InformationType.Recruit.getValue(), 4, GET_NUMBERS);
                break;
            case R.id.tv_education:
                CommonUtils.hideKeyBoard(mActivity);
                ChoosePropertyActivity.toChooseProperty(mActivity, "学历要求", InformationType.Recruit.getValue(), 5, GET_EDUCATION);
                break;
            case R.id.tv_publish_area:
                CommonUtils.hideKeyBoard(mActivity);
                ChooseJobRecruitmentPublishAreaActivity.toChoosePublishArea(mActivity, GET_AREA);
                break;
            case R.id.tv_working_seniority:
                CommonUtils.hideKeyBoard(mActivity);
                ChoosePropertyActivity.toChooseProperty(mActivity, "工作年限", InformationType.Recruit.getValue(), 6, GET_WORKING_SENIORITY);
                break;
            case R.id.tv_salary:
                CommonUtils.hideKeyBoard(mActivity);
                ChoosePropertyActivity.toChooseProperty(mActivity, "薪资水平", InformationType.Recruit.getValue(), 7, GET_SALARY);
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
                    tvPublish.setEnabled(false);
                    getInformationFeeList(null, agentId, InformationType.Recruit.getValue(), mPositionType, OrderType.SendInformation.getValue());
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case GET_NATURE:
                    tvNature.setText(data.getStringExtra(ChoosePropertyActivity.PROPERTY));
                    return;
                case GET_INDUSTRY:
                    tvIndustry.setText(data.getStringExtra(ChoosePropertyActivity.PROPERTY));
                    return;
                case GET_SIZE:
                    tvSize.setText(data.getStringExtra(ChoosePropertyActivity.PROPERTY));
                    break;
                case GET_NUMBERS:
                    tvNumbers.setText(data.getStringExtra(ChoosePropertyActivity.PROPERTY));
                    return;
                case GET_EDUCATION:
                    tvEducation.setText(data.getStringExtra(ChoosePropertyActivity.PROPERTY));
                    return;
                case GET_WORKING_SENIORITY:
                    tvWorkingSeniority.setText(data.getStringExtra(ChoosePropertyActivity.PROPERTY));
                    return;
                case GET_SALARY:
                    tvSalary.setText(data.getStringExtra(ChoosePropertyActivity.PROPERTY));
                    return;
                case GET_POSITION_TYPE:
                    mPositionType = data.getLongExtra(InformationCategoryChooseActivity.CATEGORY_ID, -1);
                    String name = data.getStringExtra(InformationCategoryChooseActivity.CATEGORY_NAME);
                    tvPositionType.setText(name);
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
        map.put("title", etPosition.getText().toString().trim());
        map.put("informationType", InformationType.Recruit.getValue());
        map.put("imgs", imageUrls.toString());
        map.put("mobile", etPhone.getText().toString().trim());
        map.put("description", etCompanyProfiles.getText().toString().trim());
        map.put("companyName", etName.getText().toString().trim());
        map.put("companyAddress", etAddress.getText().toString().trim());
        map.put("companyType", tvNature.getText());
        map.put("profession", tvIndustry.getText());
        map.put("companyScale", tvSize.getText());
        map.put("positionName", tvPositionType.getText());

        map.put("recruitNum", tvNumbers.getText());
        map.put("education", tvEducation.getText());
        map.put("workYears", tvWorkingSeniority.getText());
        map.put("claim", etRequirements.getText().toString().trim());
        map.put("salary", tvSalary.getText());
        map.put("welfare", welfares.toString());
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
        VolleyOperater<RecruitInformationModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CREATE_INFORMATION, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMaterialDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    RecruitInformationModel model = (RecruitInformationModel) obj;
                    if (model.getCode() == 0) {
                        ChooseCityModel.getInstance().setHasChanged(true);
                        if (informationFreeStandard == null) {
                            toast("已提交您发布的信息，请等待管理员审核后上线");
                            MyPublishInformationActivity.toMyPublishInformationList(mActivity, InformationType.Recruit.getValue());
                        } else {
                            tvPublish.setEnabled(true);
                            if (BigDecimal.ZERO.compareTo(informationFreeStandard.getPrice()) < 0) {
                                PayActivity.toPay(mActivity, model.getValue().getId(), model.getValue().getInformationOrder().getId(),
                                        InformationType.Recruit.getValue(), model.getValue().getAgentId());
                            } else {
//                                InformationDetailActivity.toInformationDetail(mActivity, model.getValue().getId(), InformationType.Recruit.getValue());
                                MyPublishInformationActivity.toMyPublishInformationList(mActivity, InformationType.Recruit.getValue());
                            }
                        }

                    }
                }
            }
        }, RecruitInformationModel.class);

        JSONObject info = new JSONObject();
        info.put("companyName", etName.getText().toString().trim());
        info.put("companyAddress", etAddress.getText().toString().trim());
        info.put("companyType", tvNature.getText());
        info.put("profession", tvIndustry.getText());
        info.put("companyScale", tvSize.getText());
        info.put("description", etCompanyProfiles.getText().toString().trim());
        info.put("mobile", etPhone.getText().toString().trim());
        PreferenceUtils.saveRecruitInformation(JSONObject.toJSONString(info), mActivity);
    }

    private boolean checkCanPublish() {
        if (TextUtils.isEmpty(etName.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写公司名称", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(etAddress.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写公司地址", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(tvNature.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择公司性质", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(tvSize.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择公司规模", mActivity);
            return false;
        }
        if (etCompanyProfiles.getText().toString().length() < 20) {
            ToastUtils.displayMsg("公司简介不得少于20字", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(etPosition.getText().toString().trim())) {
            ToastUtils.displayMsg("请填写职位名称", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(tvPositionType.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择职位类别", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(tvNumbers.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择招聘人数", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(tvEducation.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择学历要求", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(tvPublishArea.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择工作区域", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(tvWorkingSeniority.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择工作年限", mActivity);
            return false;
        }
        if (etRequirements.getText().toString().length() < 20) {
            ToastUtils.displayMsg("任职要求不得少于20字", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(tvSalary.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择薪资水平", mActivity);
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

        welfares.setLength(0);
        if (adapter != null && CheckUtils.isNoEmptyList(adapter.getList())) {
            for (int i = 0, size = adapter.getList().size(); i < size; i++) {
                if (adapter.getList().get(i).isChecked()) {
                    welfares.append(adapter.getList().get(i).getName() + ",");
                }
            }
        }

        imageUrls.setLength(0);
        if (CheckUtils.isEmptyList(mSelectFiles)) {
//            ToastUtils.displayMsg("请选择照片",mActivity);
            return true;
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
                    getInformationFeeList(null, agentId, InformationType.Recruit.getValue(), mPositionType, OrderType.SendInformation.getValue());
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

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("informationType", InformationType.Recruit.getValue());
        params.put("type", 8);
        VolleyOperater<PropertyArrayModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_IN_BASIC_INFORMATION, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    PropertyArrayModel model = (PropertyArrayModel) obj;
                    if (CheckUtils.isNoEmptyList(model.getValue())) {
                        adapter.setList(model.getValue());
                    }
                }
            }
        }, PropertyArrayModel.class);
    }
}

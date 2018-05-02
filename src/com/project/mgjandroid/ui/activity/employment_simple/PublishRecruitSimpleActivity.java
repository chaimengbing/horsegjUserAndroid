package com.project.mgjandroid.ui.activity.employment_simple;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.Agent;
import com.project.mgjandroid.bean.information.InformationFreeStandard;
import com.project.mgjandroid.bean.information.InformationType;
import com.project.mgjandroid.bean.information.OrderType;
import com.project.mgjandroid.chooseimage.ChoosePhotoModel;
import com.project.mgjandroid.chooseimage.UploadPhoto;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ChooseCityModel;
import com.project.mgjandroid.model.InformationAreaModel;
import com.project.mgjandroid.model.SendSmsModel;
import com.project.mgjandroid.model.SmsLoginModel;
import com.project.mgjandroid.model.information.NewPositionInformationModel;
import com.project.mgjandroid.model.information.NewRecruitInformationModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.ImageUploadActivity;
import com.project.mgjandroid.ui.activity.information.ChoosePublishAreaActivity;
import com.project.mgjandroid.ui.activity.information.InformationCategoryChooseActivity;
import com.project.mgjandroid.ui.activity.information.MyPublishInformationActivity;
import com.project.mgjandroid.ui.activity.information.PayActivity;
import com.project.mgjandroid.ui.adapter.ImageRecyclerAdapter;
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


public class PublishRecruitSimpleActivity extends ImageUploadActivity {

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
    @InjectView(R.id.job_name)
    private EditText etName;
    @InjectView(R.id.job_type)
    private TextView etType;
    @InjectView(R.id.job_area)
    private TextView etArea;
    @InjectView(R.id.job_content)
    private EditText etContent;
    @InjectView(R.id.tv_content_length)
    private TextView tvContentLength;
    @InjectView(R.id.job_phone)
    private EditText etPhone;
    @InjectView(R.id.job_get_code)
    private TextView tvGetCode;
    @InjectView(R.id.job_code)
    private EditText etCode;
    @InjectView(R.id.job_publish)
    private TextView tvPublish;
    @InjectView(R.id.job_change)
    private TextView tvChange;
    @InjectView(R.id.job_get_code_line)
    private View vBottomLine;
    @InjectView(R.id.job_code_label)
    private LinearLayout llBottomLabel;


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
        setContentView(R.layout.activity_recruit_message_simple);
        Injector.get(this).inject();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvPublish.setEnabled(true);
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
        tvTitle.setText("发布招聘信息");
        SmsLoginModel.ValueEntity.AppUserEntity userInfo = App.getInstance().getUserInfo();
        if (userInfo != null) {
            mPhone = userInfo.getMobile();
            changeGetCodeLabel(mPhone);
        } else {
            changeGetCodeLabel(null);
        }

        ChoosePhotoModel.getInstance().setMaxCount(12);
        ChoosePhotoModel.getInstance().setCurrentActivity(this.getClass());

        ivBack.setOnClickListener(this);
        etArea.setOnClickListener(this);
        etType.setOnClickListener(this);
        tvChange.setOnClickListener(this);
        tvGetCode.setOnClickListener(this);
        tvPublish.setOnClickListener(this);
        etContent.addTextChangedListener(new TextWatcher() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR2)
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!CommonUtils.checkViewVisibleInWindow(mActivity, tvContentLength)) {
                    int Y;
                    if (CommonUtils.getCurrentCursorLine(etContent) == 3) {
                        Y = DipToPx.sp2px(mActivity, 17);
                    } else if (CommonUtils.getCurrentCursorLine(etContent) == 2) {
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
        etContent.setOnTouchListener(new View.OnTouchListener() {
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
                etArea.setText(valueBean.getName());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                onBackPressed();
                return;
            case R.id.job_type:
                CommonUtils.hideKeyBoard(mActivity);
                InformationCategoryChooseActivity.toChooseInformationCategory(mActivity, InformationType.NewRecruit.getValue(), InformationCategoryChooseActivity.FROM_INFORMATION_PUBLISH_PAGE, GET_POSITION_TYPE);
                return;
            case R.id.job_area:
                CommonUtils.hideKeyBoard(mActivity);
                ChoosePublishAreaActivity.toChoosePublishArea(mActivity, GET_AREA);
                return;
            case R.id.job_get_code:
                mPrePhone = etPhone.getText().toString().trim();
                if (!CheckUtils.isNoEmptyStr(etPhone.getText().toString().trim())) {
                    ToastUtils.displayMsg("请输入手机号", this);
                    return;
                }
                getCheckCode();
                return;
            case R.id.job_publish:
                CommonUtils.hideKeyBoard(mActivity);
                if (checkCanPublish()) {
                    tvPublish.setEnabled(false);
                    getInformationFeeList(null, agentId, InformationType.NewRecruit.getValue(), mPositionType, OrderType.SendInformation.getValue());
                }
                return;
            case R.id.job_change:
                changeGetCodeLabel("");
                return;
        }
        super.onClick(v);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case GET_POSITION_TYPE:
                    mPositionType = data.getLongExtra(InformationCategoryChooseActivity.CATEGORY_ID, -1);
                    String name = data.getStringExtra(InformationCategoryChooseActivity.CATEGORY_NAME);
                    etType.setText(name);
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
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void publish(final InformationFreeStandard informationFreeStandard) {
        showUploadDialog();

        Map<String, Object> map = new HashMap<>();
        map.put("title", etName.getText().toString().trim());
        map.put("informationType", InformationType.NewRecruit.getValue());
        map.put("mobile", etPhone.getText().toString().trim());
        map.put("description", etContent.getText().toString().trim());
        map.put("positionName", etName.getText().toString().trim());
        map.put("categoryName", etType.getText());
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
        map.put("address", etArea.getText());
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
        VolleyOperater<NewRecruitInformationModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CREATE_INFORMATION, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMaterialDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    NewRecruitInformationModel model = (NewRecruitInformationModel) obj;
                    if (model.getCode() == 0) {
                        ChooseCityModel.getInstance().setHasChanged(true);
                        if (informationFreeStandard == null) {
                            toast("已发布");
                        } else {
                            tvPublish.setEnabled(true);
                            if (BigDecimal.ZERO.compareTo(informationFreeStandard.getPrice()) < 0) {
                                PayActivity.toPay(mActivity, model.getValue().getId(), model.getValue().getInformationOrder().getId(),
                                        InformationType.NewRecruit.getValue(), model.getValue().getAgentId());
                            } else {
//                                InformationDetailActivity.toInformationDetail(mActivity, model.getValue().getId(), InformationType.Position.getValue());
                                MyPublishInformationActivity.toMyPublishInformationList(mActivity, InformationType.NewRecruit.getValue());
                            }
                        }

                        finish();
                    }
                }
            }
        }, NewRecruitInformationModel.class);
    }

    private boolean checkCanPublish() {
        if (TextUtils.isEmpty(etName.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择您期望的职位", mActivity);
            return false;
        }
        if (etName.getText().toString().trim().length() < 2 || etName.getText().toString().trim().length() > 12) {
            ToastUtils.displayMsg("职位不得少于2个字,且不得多于12个字", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(etType.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择职位类别", mActivity);
            return false;
        }
        if (TextUtils.isEmpty(etArea.getText().toString().trim())) {
            ToastUtils.displayMsg("请选择您发布的区域", mActivity);
            return false;
        }
        if (etContent.getText().toString().length() < 20) {
            ToastUtils.displayMsg("职位描述不得少于20字", mActivity);
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

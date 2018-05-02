package com.project.mgjandroid.ui.activity.legwork;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.CustomerAndComplainPhoneDTOModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SunXueLiang on 2018-03-12.
 */

public class LegworkFeedbackActivity extends BaseActivity {

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.tv_submit)
    private TextView tvSubmit;
    @InjectView(R.id.tv_feed_phone)
    private TextView tvPhone;
    @InjectView(R.id.et_company_profiles)
    private EditText etCompanyProfiles;
    @InjectView(R.id.tv_profiles_length)
    private TextView tvProfilesLength;
    @InjectView(R.id.et_phone)
    private EditText etPhone;

    private String regionalHeadPhone;
    private long agentId = 0;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_legwork_feedback);
        Injector.get(this).inject();
        agentId = PreferenceUtils.getLongPreference("issueAgentId", 0, mActivity);
        initView();
        getTelNumXY(agentId, 18);
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        tvTitle.setText("意见反馈");
        etCompanyProfiles.addTextChangedListener(new TextWatcher() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR2)
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() > 5) {
                    tvProfilesLength.setVisibility(View.GONE);
                } else {
                    tvProfilesLength.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void getTelNumXY(long agentId, int type) {
        final Map<String, Object> map = new HashMap<>();
        map.put("agentId", agentId);
        map.put("type", type);
        VolleyOperater<CustomerAndComplainPhoneDTOModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_USER_TELNUM_ID_NEW, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (obj instanceof String) {
                    ToastUtils.displayMsg(obj.toString(), mActivity);
                    return;
                }
                CustomerAndComplainPhoneDTOModel model = (CustomerAndComplainPhoneDTOModel) obj;
                for (int i = 0; i < model.getValue().size(); i++) {
                    if (model.getValue() != null && 1 == model.getValue().get(i).getType()) {
                        regionalHeadPhone = model.getValue().get(i).getPhone();
                        tvPhone.setText(regionalHeadPhone);
                    }
                }
            }
        }, CustomerAndComplainPhoneDTOModel.class);
    }

    private void getFeedBackData() {
        Map<String, Object> map = new HashMap<>();
        map.put("agentId", agentId);
        if (CheckUtils.isEmptyStr(etCompanyProfiles.getText().toString().trim()) || etCompanyProfiles.getText().toString().trim().length() < 5) {
            ToastUtils.displayMsg("请填写您的建议(不少于5个字)", this);
            return;
        } else {
            map.put("content", etCompanyProfiles.getText().toString().trim());
        }
        if (CheckUtils.isNoEmptyStr(etPhone.getText().toString().trim()) && etPhone.getText().toString().length() != 11) {
            ToastUtils.displayMsg("请输入正确的手机号", this);
            return;
        } else {
            map.put("mobile", etPhone.getText().toString().trim());
        }
        VolleyOperater<Object> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CREATE_FEEDBACK, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    toast("提交成功");
                    finish();
                }
            }
        }, Object.class);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.common_back:
                onBackPressed();
                break;
            case R.id.tv_submit:
                getFeedBackData();
                break;
        }
    }
}

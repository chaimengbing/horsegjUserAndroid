package com.project.mgjandroid.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mgjandroid.base.App;
import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.AppLaunchModel;
import com.project.mgjandroid.model.SendSmsModel;
import com.project.mgjandroid.model.SmsLoginModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.HashMap;
import java.util.Map;

public class BindMobileActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.sms_login_mobile)
    private EditText et_mobile;
    @InjectView(R.id.sms_login_code)
    private EditText et_code;
    @InjectView(R.id.sms_login_get_code)
    private TextView tv_getCode;
    @InjectView(R.id.sms_login_in)
    private Button btn_loginIn;
    @InjectView(R.id.login_back)
    private ImageView img_back;

    private Context mContext;
    private MLoadingDialog loadingDialog;
    private Toast mToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_mobile);
        mContext = this;
        Injector.get(this).inject();
        loadingDialog = new MLoadingDialog();
        setListener();
    }

    private void setListener() {
        btn_loginIn.setOnClickListener(this);
        img_back.setOnClickListener(this);
        tv_getCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sms_login_in:
                if (TextUtils.isEmpty(et_mobile.getText().toString().trim()) ||
                        TextUtils.isEmpty(et_code.getText().toString().trim())) {
                    ToastUtils.displayMsg("手机号或验证码为空", this);
                    return;
                }
//                if (!CheckUtils.isMobileNO(et_mobile.getText().toString().trim())) {
//                    ToastUtils.displayMsg("手机号码格式不正确", this);
//                    return;
//                }
                doLogin();
                break;
            case R.id.login_back:
                finish();
                break;
            case R.id.login_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.sms_login_get_code:
                if (TextUtils.isEmpty(et_mobile.getText().toString().trim())) {
                    ToastUtils.displayMsg("手机号码为空", this);
                    return;
                }
//                if (!CheckUtils.isMobileNO(et_mobile.getText().toString().trim())) {
//                    ToastUtils.displayMsg("手机号码格式不正确", this);
//                    return;
//                }
                getSMSCode();
                break;
        }
    }


    /**
     * 请求短信验证码
     */
    private void getSMSCode() {
        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mobile", et_mobile.getText().toString().trim());
        VolleyOperater<SendSmsModel> operater = new VolleyOperater<>(mContext);
        operater.doRequest(Constants.URL_GET_MOBILE_BIND_MSG_CODE, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    SendSmsModel sendSmsModel = (SendSmsModel) obj;
                    if (sendSmsModel.isSuccess()) {
                        ToastUtils.displayMsg("发送成功", mContext);
                        tv_getCode.setEnabled(false);
                        new SmsCountDown(60000, 1000).start();
                    } else {
                        showToast(sendSmsModel.getValue());
                    }
                }
            }
        }, SendSmsModel.class);
    }

    /**
     * 验证码登录操作
     */
    private void doLogin() {
        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", et_mobile.getText().toString().trim());
        map.put("code", et_code.getText().toString().trim());
        VolleyOperater<AppLaunchModel> operater = new VolleyOperater<>(mContext);
        operater.doRequest(Constants.URL_BIND_MOBILE, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        showToast((String) obj);
                    } else {
                        AppLaunchModel appLaunchModel = (AppLaunchModel) obj;
                        SmsLoginModel.ValueEntity.AppUserEntity appUserEntity = new SmsLoginModel.ValueEntity.AppUserEntity();
                        AppLaunchModel.ValueEntity valueEntity = appLaunchModel.getValue();
                        appUserEntity.setId(valueEntity.getId());
                        appUserEntity.setCreateTime(valueEntity.getCreateTime());
                        appUserEntity.setModifyTime(valueEntity.getModifyTime());
                        appUserEntity.setName(valueEntity.getName());
                        appUserEntity.setMobile(valueEntity.getMobile());
                        appUserEntity.setPwd(valueEntity.getPwd());
                        appUserEntity.setHeaderImg(valueEntity.getHeaderImg());
                        appUserEntity.setRegTime(valueEntity.getRegTime());
                        appUserEntity.setLastLoginTime(valueEntity.getLastLoginTime());
                        appUserEntity.setChannel(valueEntity.getChannel());
                        appUserEntity.setToken(valueEntity.getToken());
                        App.setUserInfo(appUserEntity);
                        App.setIsLogin(true);
                        App.setIsUserInfoChange(true);
                        setResult(HomeActivity.LOCATION_RESPOND_CODE, new Intent());
                        finish();
                    }
                }
            }
        }, AppLaunchModel.class);
    }

    private void showToast(String msg) {
        if (mToast == null) {
            mToast = new Toast(mActivity);
            TextView v = new TextView(mActivity);
            v.setPadding(getResources().getDimensionPixelSize(R.dimen.x15), getResources().getDimensionPixelSize(R.dimen.x15),
                    getResources().getDimensionPixelSize(R.dimen.x15), getResources().getDimensionPixelSize(R.dimen.x15));
            v.setGravity(Gravity.CENTER);
            v.setBackgroundResource(R.drawable.my_toast);
            v.setTextColor(Color.WHITE);
            v.setLineSpacing(0, 1.1f);
            v.setTextSize(18);
            mToast.setView(v);
        }
        ((TextView) mToast.getView()).setText(msg);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }


    private class SmsCountDown extends CountDownTimer {

        public SmsCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_getCode.setText("(" + millisUntilFinished / 1000 + ")s");
        }

        @Override
        public void onFinish() {
            tv_getCode.setEnabled(true);
            tv_getCode.setText("获取验证码");
        }
    }
}

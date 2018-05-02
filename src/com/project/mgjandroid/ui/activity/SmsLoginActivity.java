package com.project.mgjandroid.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.h5container.YLBSdkConstants;
import com.project.mgjandroid.model.SendSmsModel;
import com.project.mgjandroid.model.SmsLoginModel;
import com.project.mgjandroid.model.WechatLoginModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.fragment.MineFragment;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

public class SmsLoginActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.sms_login_mobile)
    private EditText et_mobile;
    @InjectView(R.id.iv_phone_del)
    private ImageView ivPhoneDel;
    @InjectView(R.id.iv_code_del)
    private ImageView ivCodeDel;
    @InjectView(R.id.sms_login_code)
    private EditText et_code;
    @InjectView(R.id.sms_login_get_code)
    private TextView tv_getCode;
    @InjectView(R.id.sms_login_in)
    private Button btn_loginIn;
    @InjectView(R.id.login_back)
    private ImageView img_back;
    @InjectView(R.id.login_register)
    private TextView tv_register;
    @InjectView(R.id.login_by_weichat)
    private LinearLayout loginByWeichat;
    @InjectView(R.id.login_by_qq)
    private LinearLayout loginByQQ;

    private Context mContext;

    private IWXAPI api;
    private boolean canToWechat = true;
    private MLoadingDialog loadingDialog;
    private boolean mIsFinished = false;
    private boolean isFromThird;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_login);
        mContext = this;
        Injector.get(this).inject();
        loadingDialog = new MLoadingDialog();
        if (getIntent() != null) {
            isFromThird = getIntent().getBooleanExtra("isFromThird", false);
        }
        setListener();
        instance = SmsLoginActivity.this;
    }

    private void setListener() {
        btn_loginIn.setOnClickListener(this);
        img_back.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_getCode.setOnClickListener(this);
        loginByWeichat.setOnClickListener(this);
        loginByQQ.setOnClickListener(this);
        ivPhoneDel.setOnClickListener(this);
        ivCodeDel.setOnClickListener(this);
        et_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    String str = s.toString().replace(" ", "");
                    if (!s.toString().equals(str)) {
                        et_mobile.setText(str);
                        return;
                    }
                }
                if (s.toString().length() > 0) {
                    ivPhoneDel.setVisibility(View.VISIBLE);
                } else {
                    ivPhoneDel.setVisibility(View.GONE);
                }
            }
        });
        et_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    ivCodeDel.setVisibility(View.VISIBLE);
                } else {
                    ivCodeDel.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sms_login_in:
                if (TextUtils.isEmpty(et_mobile.getText().toString().trim())) {
                    ToastUtils.displayMsg("手机号为空", this);
                    return;
                }
                if (et_mobile.getText().toString().trim().length() != 11) {
                    ToastUtils.displayMsg("请输入正确的手机号", this);
                    return;
                }
                if (TextUtils.isEmpty(et_code.getText().toString().trim())) {
                    ToastUtils.displayMsg("验证码为空", this);
                    return;
                }
                doLogin();
                break;
            case R.id.iv_phone_del:
                et_mobile.setText("");
                break;
            case R.id.iv_code_del:
                et_code.setText("");
                break;
            case R.id.login_back:
                if (isFromThird) {
                    setResult(RESULT_CANCELED);
                }
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
                if (et_mobile.getText().toString().trim().length() != 11) {
                    ToastUtils.displayMsg("请输入正确的手机号", this);
                    return;
                }
                getSMSCode();
                break;
            case R.id.login_by_weichat:
                /*
                此处跳转微信
				 */
                if (api == null) {
                    api = WXAPIFactory.createWXAPI(this, Constants.WE_CHAT_APP_ID, false);
                }

                if (!api.isWXAppInstalled()) {
                    //提醒用户没有按照微信
                    ToastUtils.displayMsg("请先安装微信客户端", SmsLoginActivity.this);
//                    Intent intent = new Intent("android.intent.action.VIEW");
//                    intent.setData(Uri.parse("market://details?id=com.tencent.mm"));
//                    startActivity(intent);
                    return;
                }
                // send oauth request
                if (canToWechat) {
                    canToWechat = false;
                    api.registerApp(Constants.WE_CHAT_APP_ID);
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "login_by_weichat";
                    api.sendReq(req);
                }
                break;
            case R.id.login_by_qq:
                loginByQQ();
                break;
        }
    }

    public void loginByQQ() {
        if (!App.getmTencent().isSessionValid()) {
//            App.getmTencent().login(this, "get_user_info,add_t", loginListener);
            loadingDialog.show(getFragmentManager(), "");
            App.getmTencent().loginServerSide(this, "get_user_info,add_t", loginListener);
        }
    }

    private BaseUiListener loginListener = new BaseUiListener();

    /**
     * 请求短信验证码
     */
    private void getSMSCode() {
        if (!CommonUtils.isNetworkConnected()) {
            ToastUtils.displayMsg("无网络连接，请检查网络", mActivity);
            return;
        }
        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mobile", et_mobile.getText().toString().trim());
        VolleyOperater<SendSmsModel> operater = new VolleyOperater<SendSmsModel>(SmsLoginActivity.this);
        operater.doRequest(Constants.URL_GET_MSG_CODE, map, new VolleyOperater.ResponseListener() {

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
                        ToastUtils.displayMsg(sendSmsModel.getValue(), mContext);
                    }
                }
            }
        }, SendSmsModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        canToWechat = true;
    }

    /**
     * 验证码登录操作
     */
    private void doLogin() {
        //loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", et_mobile.getText().toString().trim());
        map.put("code", et_code.getText().toString().trim());
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        VolleyOperater<SmsLoginModel> operater = new VolleyOperater<SmsLoginModel>(SmsLoginActivity.this);
        operater.doRequest(Constants.URL_SMS_LOGIN, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
//                if (loadingDialog != null){
//                    loadingDialog.dismiss();
//                }
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg((String) obj, mActivity);
                    } else {
                        SmsLoginModel smsLoginModel = (SmsLoginModel) obj;
                        SmsLoginModel.ValueEntity value = smsLoginModel.getValue();
                        App.setUserInfo(value.getAppUser());
                        App.setIsLogin(true);
                        //极光推送TAG绑定
                        if (!TextUtils.isEmpty(value.getAppUser().getAgentId())) {
                            JPushInterface.checkTagBindState(SmsLoginActivity.this, 101, "agent_" + value.getAppUser().getAgentId());
                        }
                        PreferenceUtils.saveStringPreference("token", smsLoginModel.getValue().getAppUser().getToken(), mContext);
                        if (isFromThird) {
                            try {
                                JSONObject object = new JSONObject();
                                object.put("code", 0);
                                object.put("value", smsLoginModel.getValue().getAppUser().getToken());
                                Intent i = new Intent();
                                i.putExtra(YLBSdkConstants.EXTRA_YLBSDK_RESULT, object.toString());
                                setResult(RESULT_OK, i);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                finish();
                            }
                        } else {
                            setResult(MineFragment.LOGIN_IN_SUCCESS, new Intent());
                            finish();
                        }
                    }
                } else {
                    if (obj != null && obj instanceof String) {
                        ToastUtils.displayMsg((String) obj, mActivity);
                    }
                }
            }
        }, SmsLoginModel.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIsFinished = true;
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

    public static SmsLoginActivity instance;

    public void thirdLoginSuccess(boolean isFromQQ) {
        if (isFromQQ) {
            loadingDialog.dismiss();
        }
        setResult(MineFragment.LOGIN_IN_SUCCESS, new Intent());
        finish();
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            MLog.i("onComplete:" + response.toString());
            ToastUtils.displayMsg("授权成功，正在登陆", SmsLoginActivity.this);
            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject response) {
            String openId = response.optString("openid");
            login(openId);
        }

        @Override
        public void onError(UiError e) {
            MLog.i("onError:" + "code:" + e.errorCode + ", msg:"
                    + e.errorMessage + ", detail:" + e.errorDetail);
            ToastUtils.displayMsg("授权失败", SmsLoginActivity.this);
            loadingDialog.dismiss();
        }

        @Override
        public void onCancel() {
            MLog.i("onCancel");
            ToastUtils.displayMsg("授权取消", SmsLoginActivity.this);
            loadingDialog.dismiss();
        }
    }

    private void login(String code) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", 2);//QQ
        map.put("token", code);
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        VolleyOperater<WechatLoginModel> operater = new VolleyOperater<WechatLoginModel>(SmsLoginActivity.this);
        operater.doRequest(Constants.URL_THIRD_PARTY_LOGIN, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed) {
                    WechatLoginModel wechatLoginModel = (WechatLoginModel) obj;
                    SmsLoginModel.ValueEntity.AppUserEntity value = wechatLoginModel.getValue();
                    App.setUserInfo(value);
                    App.setIsLogin(true);
                    //极光推送TAG绑定
                    if (!TextUtils.isEmpty(value.getAgentId())) {
                        JPushInterface.checkTagBindState(SmsLoginActivity.this, 101, "agent_" + value.getAgentId());
                    }
                    PreferenceUtils.saveStringPreference("token", wechatLoginModel.getValue().getToken(), SmsLoginActivity.this);
                    SmsLoginActivity.instance.thirdLoginSuccess(true);
                }
            }
        }, WechatLoginModel.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == com.tencent.connect.common.Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
        }
    }

    @Override
    public void onBackPressed() {
        if (isFromThird) {
            setResult(RESULT_CANCELED);
        }
        super.onBackPressed();
    }
}

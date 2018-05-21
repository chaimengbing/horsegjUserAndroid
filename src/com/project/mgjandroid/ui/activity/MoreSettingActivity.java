package com.project.mgjandroid.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.h5base.view.YLH5Container;
import com.project.mgjandroid.h5container.view.YLBWebViewActivity;
import com.project.mgjandroid.model.LogoutModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.fragment.MineFragment;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;

public class MoreSettingActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.more_setting_back)
    private ImageView settingBack;
    @InjectView(R.id.more_setting_exit)
    private TextView tvExit;
    @InjectView(R.id.setting_clear_cache)
    private RelativeLayout rlClearCache;
    @InjectView(R.id.setting_about)
    private RelativeLayout rlAbout;
    @InjectView(R.id.setting_size)
    private TextView tvCacheSize;
    @InjectView(R.id.setting_non_wifi)
    private RelativeLayout rlNonWifi;
    @InjectView(R.id.setting_push_notice)
    private RelativeLayout rlPushSetting;
    @InjectView(R.id.setting_wifi)
    private TextView imgQuality;

    private Context mContext;
    private Dialog avatarDialog;
    private MLoadingDialog loadingDialog;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_setting);
        Injector.get(this).inject();
        mContext = this;
        initView();
    }

    private void initView() {
        loadingDialog = new MLoadingDialog();
        settingBack.setOnClickListener(this);
        tvExit.setOnClickListener(this);
        rlClearCache.setOnClickListener(this);
        rlAbout.setOnClickListener(this);
        rlNonWifi.setOnClickListener(this);
        rlPushSetting.setOnClickListener(this);
        if (App.isLogin()) {
            tvExit.setVisibility(View.VISIBLE);
            rlPushSetting.setVisibility(View.VISIBLE);
        } else {
            tvExit.setVisibility(View.GONE);
            rlPushSetting.setVisibility(View.GONE);
        }
        String quality = PreferenceUtils.getStringPreference("ImageQuality", "普通", mContext);
        imgQuality.setText(quality);
//        try {
//            String autoCacheSize = FileUtils.getAutoCacheSize();
//            tvCacheSize.setText(autoCacheSize);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_setting_back:
                onBackPressed();
                break;
            case R.id.more_setting_exit:
                exit();
                break;
//            case R.id.setting_clear_cache:
//                FileUtils.deleteAllCacheFile();
//                tvCacheSize.setText("0B");
//                break;
            case R.id.setting_about:
                startActivity(new Intent(mActivity, AboutActivity.class));
                break;
            case R.id.setting_non_wifi:
                showPopupWindow();
                break;
            case R.id.take_photo:
                PreferenceUtils.saveStringPreference("ImageQuality", "普通", mContext);
                imgQuality.setText("普通");
                dismissPopupWindow();
                break;
            case R.id.select_photo:
                PreferenceUtils.saveStringPreference("ImageQuality", "高质量", mContext);
                imgQuality.setText("高质量");
                dismissPopupWindow();
                break;
            case R.id.feed_back_cancel:
                dismissPopupWindow();
                break;
            case R.id.setting_push_notice:
                startActivity(new Intent(mActivity, PushSettingActivity.class));
                break;
        }
    }

    /**
     * 登出
     */
    private void exit() {
        if (!CommonUtils.isNetworkConnected()) {
            ToastUtils.displayMsg("无网络连接，请检查网络", mActivity);
            return;
        }
        loadingDialog.show(getFragmentManager(), "");
        VolleyOperater<LogoutModel> operater = new VolleyOperater<LogoutModel>(this);
        operater.doRequest(Constants.URL_EXIT_OUT, null, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();

                if (isSucceed && obj != null) {
                    LogoutModel logoutModel = (LogoutModel) obj;
                    if (logoutModel.isSuccess()) {
                        App.setIsLogin(false);
                        App.setUserInfo(null);
                        PreferenceUtils.saveStringPreference("token", "", mContext);
                        setResult(MineFragment.EXIT_APP_SUCCESS, new Intent());
                        finish();
                    }
                }

                // 清空缓存
                CookieSyncManager.createInstance(mActivity);  //Create a singleton CookieSyncManager within a context
                CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
                cookieManager.removeAllCookie();// Removes all cookies.
                cookieManager.setAcceptCookie(true);
                CookieSyncManager.getInstance().sync(); // forces sync manager to sync now
                System.gc();
            }
        }, LogoutModel.class);
    }

    private void showPopupWindow() {
        String quality = PreferenceUtils.getStringPreference("ImageQuality", "普通", mContext);
        if (mPopupWindow == null) {
            initPopupWindow(quality);
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        } else if (!mPopupWindow.isShowing()) {
            if (quality.equals("普通")) {
                ((TextView) mPopupWindow.getContentView().findViewById(R.id.take_photo)).setTextColor(getResources().getColor(R.color.pintuan_red));
                ((TextView) mPopupWindow.getContentView().findViewById(R.id.select_photo)).setTextColor(Color.parseColor("#0066ff"));
            } else {
                ((TextView) mPopupWindow.getContentView().findViewById(R.id.take_photo)).setTextColor(Color.parseColor("#0066ff"));
                ((TextView) mPopupWindow.getContentView().findViewById(R.id.select_photo)).setTextColor(getResources().getColor(R.color.pintuan_red));
            }
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
    }

    private void initPopupWindow(String quality) {
        LinearLayout linearLayout = (LinearLayout) mInflater.inflate(R.layout.layout_select_photo, null);
        TextView tvTitle = (TextView) linearLayout.findViewById(R.id.title);
        TextView tvTakePhoto = (TextView) linearLayout.findViewById(R.id.take_photo);
        TextView tvSelectPhoto = (TextView) linearLayout.findViewById(R.id.select_photo);
        TextView tvCancel = (TextView) linearLayout.findViewById(R.id.feed_back_cancel);
        tvTakePhoto.setOnClickListener(this);
        tvSelectPhoto.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        if (quality.equals("普通")) {
            tvTakePhoto.setTextColor(getResources().getColor(R.color.pintuan_red));
        } else {
            tvSelectPhoto.setTextColor(getResources().getColor(R.color.pintuan_red));
        }
        tvTitle.setText("非wifi下图片质量");
        tvTakePhoto.setText("普通  (节省流量)");
        tvSelectPhoto.setText("高清  (最佳效果)");
        mPopupWindow = new PopupWindow(linearLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOutsideTouchable(false);
    }

    private void dismissPopupWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }
}

package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.h5container.H5TestActivity;
import com.project.mgjandroid.manager.LocationManager;
import com.project.mgjandroid.model.FestivalModel;
import com.project.mgjandroid.model.HomeVersionModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * 欢迎页面，显示广告
 */
public class CoverPageActivity extends BaseActivity {

    @InjectView(R.id.iv_gif)
    private GifImageView ivGif;
    @InjectView(R.id.iv_img)
    private ImageView ivImg;
    @InjectView(R.id.img_add_hide)
    private ImageView add;
    @InjectView(R.id.buy_count_hide)
    private TextView tvCount;
    @InjectView(R.id.img_minus_hide)
    private ImageView minus;

    @InjectView(R.id.goods_item_choose_spec1)
    private TextView add1;
    @InjectView(R.id.goods_item_tv_buy_count_spec1)
    private TextView tvCount1;
    @InjectView(R.id.goods_item_img_minus_spec1)
    private ImageView minus1;

    private static long DELAY_LOAD_TIME = 1500;
    private long startTime;
    private String gifUri;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                if (gifUri.endsWith("jpg") || gifUri.endsWith("png") || gifUri.endsWith("JPG") || gifUri.endsWith("PNG")) {
                    Glide.with(CoverPageActivity.this)
                            .load(gifUri)
                            .into(ivImg);
                    ivImg.setVisibility(View.VISIBLE);
                } else if (gifUri.endsWith("gif") || gifUri.endsWith("GIF")) {
                    gifFromUri = new GifDrawable(gifUri);
                    ivGif.setImageDrawable(gifFromUri);
                    ivGif.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                ivGif.setVisibility(View.INVISIBLE);
                ivImg.setVisibility(View.INVISIBLE);
                e.printStackTrace();
            }
        }
    };
    private GifDrawable gifFromUri;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.coverpage_act);
        startTime = System.currentTimeMillis();
        Injector.get(this).inject();
        ivGif.setVisibility(View.INVISIBLE);
        ivImg.setVisibility(View.INVISIBLE);
        if (PreferenceUtils.getVersionCode(CoverPageActivity.this) <= CommonUtils.getCurrentVersionCode()) {
            //获取gif
            String splashGif = PreferenceUtils.getStringPreference("splashGif", "", mActivity);
            if (!TextUtils.isEmpty(splashGif)) {
                gifUri = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + splashGif;
                if (new File(gifUri).exists()) {
                    // 存在
                    DELAY_LOAD_TIME = 3000;
                    handler.sendEmptyMessageDelayed(1, 1000);
                }
            }
        }
        //获取节日效果
        //getFestivalStatus();
        PreferenceUtils.saveBoolPreference("festivalStatus", false, mActivity);

        //获取坐标位置
        LocationManager.getIManager().registeLocation(this, listener);
    }

    //注册一个定位监听
    BDLocationListener listener = new BDLocationListener() {
        @SuppressWarnings("unchecked")
        @Override
        public void onReceiveLocation(BDLocation location) {
            LocationManager.getIManager().stopLocation();
            if (location != null) {
                if ("4.9E-324".equals("" + location.getLatitude()) || "4.9E-324".equals("" + location.getLongitude())) {
                    ToastUtils.displayMsg("定位失败,请检查马管家定位权限是否开启", CoverPageActivity.this);
                }
                PreferenceUtils.saveLocation(location.getLatitude() + "", location.getLongitude() + "", mActivity);
                //保存定位信息,因为上面保存的位置会因为手动选择而改变
                PreferenceUtils.saveFixedLocation(location.getLatitude() + "", location.getLongitude() + "", location.getAddrStr(), mActivity);
                PreferenceUtils.saveAddressName(location.getAddrStr(), mActivity);
                if (CheckUtils.isNoEmptyList(location.getPoiList())) {
                    List<Poi> list = location.getPoiList();
                    PreferenceUtils.saveAddressDes(list.get(0).getName(), mActivity);
                }
                if (location.getAddress() != null && location.getAddress().city != null) {
                    PreferenceUtils.saveAddressCity(location.getAddress().city, mActivity);
                }
                if (location.getAddress() != null && location.getAddress().cityCode != null) {
                    PreferenceUtils.saveAddressCityCode(location.getAddress().cityCode, mActivity);
                }
                getNewHomePage(location.getLatitude() + "", location.getLongitude() + "");
            } else {
                // 位置获取失败 进入首页
                toHomeActivity();
            }
//            Intent it = new Intent(mActivity, H5TestActivity.class);
//            startActivity(it);
//            finish();
        }
    };


    private void toHomeActivity() {
        long delayTime = DELAY_LOAD_TIME - (System.currentTimeMillis() - startTime);
        //Log.e("TAG", "toHomeActivity: 时长--" + DELAY_LOAD_TIME + "--剩余时间--" + delayTime);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                if (PreferenceUtils.getVersionCode(CoverPageActivity.this) < CommonUtils.getCurrentVersionCode()) {
                    intent.setClass(CoverPageActivity.this, GuideActivity.class);
                } else {
                    intent.setClass(CoverPageActivity.this, HomeActivity.class);
                }
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                finish();
            }
        }, delayTime <= 0 ? 0 : delayTime);
    }

    /**
     * 获取新版首页
     */
    private void getNewHomePage(String latitude, String longitude) {
        VolleyOperater<HomeVersionModel> operater = new VolleyOperater<>(mActivity);
        HashMap<String, Object> map = new HashMap<>();
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        operater.doRequest(Constants.URL_FIND_APP_HOME_PAGE_VERSION, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (!(obj instanceof String)) {
                        HomeVersionModel model = (HomeVersionModel) obj;
                        int versionType = model.getValue().getAppHomePageVersion().getVersionType();
                        int agentId = model.getValue().getAppHomePageVersion().getAgentId();
                        String secondMenuTypeName = model.getValue().getAppHomePageVersion().getSecondMenuTypeName();
                        PreferenceUtils.saveIntPreference("versionType", versionType);
                        PreferenceUtils.saveIntPreference("agentId", agentId);
                        PreferenceUtils.saveStringPreference("secondMenuTypeName", secondMenuTypeName, mActivity);
                    }
                }
                //进入首页
                toHomeActivity();
            }
        }, HomeVersionModel.class);
    }

    /**
     * 获取节日状态
     */
    public void getFestivalStatus() {
        VolleyOperater<FestivalModel> operater = new VolleyOperater<>(mActivity);
        HashMap<String, Object> map = new HashMap<>();
        operater.doRequest(Constants.URL_GET_FESTIVAL_STATUS, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                // 节日UI
                if (isSucceed && obj != null) {
                    if (!(obj instanceof String)) {
                        FestivalModel model = (FestivalModel) obj;
                        // 0未生效  1生效
                        int status = model.getValue().getStatus();
                        PreferenceUtils.saveBoolPreference("festivalStatus", status == 1, mActivity);
                    } else {
                        PreferenceUtils.saveBoolPreference("festivalStatus", false, mActivity);
                    }
                } else {
                    PreferenceUtils.saveBoolPreference("festivalStatus", false, mActivity);
                }
            }
        }, FestivalModel.class);
    }

    @Override
    protected void onDestroy() {
        if (Util.isOnMainThread() && !this.isFinishing()) {
            if (gifFromUri != null && gifFromUri.isRunning()) {
                gifFromUri.stop();
            }
            Glide.with(this).pauseRequests();
        }
        super.onDestroy();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int[] addLocation = new int[2];
        int[] minusLocation = new int[2];
        int[] countLocation = new int[2];
        try {
            add.getLocationInWindow(addLocation);
            minus.getLocationInWindow(minusLocation);
            tvCount.getLocationInWindow(countLocation);
            PreferenceUtils.saveFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, minusLocation[0] - addLocation[0], CoverPageActivity.this);
            PreferenceUtils.saveFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, -DipToPx.dip2px(mActivity, 20), CoverPageActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int[] addLocation1 = new int[2];
        int[] minusLocation1 = new int[2];
        int[] countLocation1 = new int[2];
        try {
            add1.getLocationInWindow(addLocation1);
            minus1.getLocationInWindow(minusLocation1);
            tvCount1.getLocationInWindow(countLocation1);
            PreferenceUtils.saveFloatPreference(PreferenceUtils.MY_MINUS, minusLocation1[0] - addLocation1[0] - DipToPx.dip2px(mActivity, 40), CoverPageActivity.this);
            PreferenceUtils.saveFloatPreference(PreferenceUtils.MY_COUNT, -DipToPx.dip2px(mActivity, 60), CoverPageActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

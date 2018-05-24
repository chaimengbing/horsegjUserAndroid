package com.project.mgjandroid.h5container.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.github.mzule.activityrouter.router.RouterCallback;
import com.github.mzule.activityrouter.router.Routers;
import com.google.gson.Gson;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.Broadcast;
import com.project.mgjandroid.bean.H5Banner;
import com.project.mgjandroid.bean.PrimaryCategory;
import com.project.mgjandroid.bean.UserAddress;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.h5base.jsbridge.JsBridgeCallBack;
import com.project.mgjandroid.h5base.jsbridge.JsBridgeHandler;
import com.project.mgjandroid.h5base.jsbridge.WebPageLoadingListener;
import com.project.mgjandroid.h5base.utils.YLLogUtils;
import com.project.mgjandroid.h5base.view.YLH5CBaseActivity;
import com.project.mgjandroid.h5container.YLBSdkConstants;
import com.project.mgjandroid.h5container.models.AlertModel;
import com.project.mgjandroid.h5container.models.CallTelModel;
import com.project.mgjandroid.h5container.models.ConfirmModel;
import com.project.mgjandroid.h5container.models.ErrorModel;
import com.project.mgjandroid.h5container.models.Native2H5Model;
import com.project.mgjandroid.h5container.models.NativeH5Model;
import com.project.mgjandroid.h5container.models.PayModel;
import com.project.mgjandroid.h5container.models.ScanModel;
import com.project.mgjandroid.h5container.models.ShareModel;
import com.project.mgjandroid.h5container.models.ShowRightItemModel;
import com.project.mgjandroid.h5container.models.addressModel;
import com.project.mgjandroid.h5container.utils.ToastHandler;
import com.project.mgjandroid.h5container.utils.YLDialogUtils;
import com.project.mgjandroid.model.FindAgentModel;
import com.project.mgjandroid.model.SmsLoginModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.AddressManageActivity;
import com.project.mgjandroid.ui.activity.BindMobileActivity;
import com.project.mgjandroid.ui.activity.HomeActivity;
import com.project.mgjandroid.ui.activity.OldHomeActivity;
import com.project.mgjandroid.ui.activity.OnlinePayActivity;
import com.project.mgjandroid.ui.activity.PrimaryCategoryActivity;
import com.project.mgjandroid.ui.activity.SmsLoginActivity;
import com.project.mgjandroid.ui.activity.groupbuying.GroupBuyingCategoryActivity;
import com.project.mgjandroid.ui.view.NoticeDialog;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ShareUtil;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.zxing.activity.CaptureActivity;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebBackForwardList;
import com.tencent.smtt.sdk.WebView;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

@Router(value = "html5Show/:extra_h5_url", stringParams = "extra_h5_url")
public class YLBWebViewActivity extends YLH5CBaseActivity implements View.OnClickListener {

    private static final String TAG = "YLBWebViewActivity";
    public static final int IS_LINK = 1;
    public static final int IS_CATEGORY = 2;
    public static final int IS_MERCHANT = 3;
    public static final int IS_GROUPBUY = 4;

    private TextView mTvTitle;
    private TextView mRightItem;
    private FrameLayout mLlWebview;
    private ProgressBar mProgressBar;
    private ProgressDialog mProgressDialog;

    private Map<String, JsBridgeCallBack> callbacks = new HashMap<>();
    private String sURL;
    private String backUrl;

    private ShareUtil shareUtil;
    private NoticeDialog noticeDialog;
    private long agentId = 0L;
    private String tag;//头条进入展示分享按钮
    private ImageView ivShare;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置native 默认消息处理器，在没有匹配到js消息指定处理器的时候 处理本条js消息
        // 可以不设置，webview中已经设置了默认处理器 DefaultHandler，返回对象: { errorCode: 1, errorMessage: '接口不存在' }
//        setDefaultHandler(new JsBridgeHandler() {
//            @Override
//            public void handler(String s, JsBridgeCallBack jsBridgeCallBack) {
//                // 接口不存在时处理
//            }
//        });

        // 修改ua使得web端正确判断
        String ua = h5Container.getSettings().getUserAgentString() + "Mobile/mgj/platform/android ylh5sdkVersion/1.0.0";
        h5Container.getSettings().setUserAgentString(ua);

        // webview 加载H5地址
        loadUrl(sURL);

        // 注册js交互相关
        registerCustomHandlers();

        // 获取AgentId
        getAgentIdByXY();

        // callJs("putData", "{\"msg\": \"我是native传过来的数据\"}");

        // 设置webview页面加载监听
        setWebPageLoadingListener(new WebPageLoadingListener() {
            // 页面开始加载
            @Override
            public void onWebPageStarted(WebView webView, String url, Bitmap bitmap) {
                YLLogUtils.d(TAG, "onWebPageStarted-->" + url);
                mRightItem.setText("");
                mRightItem.setVisibility(View.GONE);
                ivShare.setVisibility(View.GONE);
                // webView.getSettings().setBlockNetworkImage(true); 禁止图片加载
            }

            // 页面开始结束
            @Override
            public void onWebPageFinished(WebView webView, String url) {
                YLLogUtils.d(TAG, "onWebPageFinished-->" + url);

                if (!TextUtils.isEmpty(tag)) {
                    ShowRightIcon("1");
                }
                // webView.getSettings().setBlockNetworkImage(false); 允许图片加载
            }
        });

        // 设置webview下载监听，处理下载事件 （默认操作为调起手机浏览器进行下载操作）
        setDownLoadLister(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition,
                                        String mimetype, long contentLength) {
                YLLogUtils.d(TAG, "DownloadListener-->" + url);
            }
        });

    }

    private void getAgentIdByXY() {
        VolleyOperater<FindAgentModel> operater = new VolleyOperater<>(this);
        Map<String, Object> map = new HashMap<>();
        if (PreferenceUtils.getLocation(this)[0] != null && PreferenceUtils.getLocation(this)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(this)[0]);
            map.put("longitude", PreferenceUtils.getLocation(this)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        operater.doRequest(Constants.URL_FIND_AGENT_BY_USER_XY, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    FindAgentModel agentModel = (FindAgentModel) obj;
                    if (agentModel.getValue().getAgentType() == 1) {
                        agentId = agentModel.getValue().getId();
                    } else {
                        agentId = 0L;
                    }
                } else {
                    agentId = 0L;
                }
            }
        }, FindAgentModel.class);
    }

    public static String getValueByName(String url, String name) {
        String result = "";
        int index = url.indexOf("?");
        String temp = url.substring(index + 1);
        String[] keyValue = temp.split("&");
        for (String str : keyValue) {
            if (str.contains(name)) {
                result = str.replace(name + "=", "");
                break;
            }
        }
        return result;
    }

    /**
     * 设置消息处理器集合, 与js约定调用
     */
    private void registerCustomHandlers() {
        // 注册native 消息处理器， 处理 js传递的消息指定处理器，可多次调用设置多个处理器
        registerHandler("toast", new ToastHandler(this));

        registerHandler("confirm", new JsBridgeHandler() {
            @Override
            public void handler(String data, final JsBridgeCallBack function) {
                Gson gson = new Gson();
                ConfirmModel model = gson.fromJson(data, ConfirmModel.class);
                StringBuffer sb = new StringBuffer("");
                if (TextUtils.isEmpty(model.getTitle())) {
                    sb.append("缺少参数title,");
                }
                if (TextUtils.isEmpty(model.getMessage())) {
                    sb.append("缺少参数message,");
                }
                if (TextUtils.isEmpty(model.getOkButton())) {
                    sb.append("缺少参数okButton");
                }
                if (TextUtils.isEmpty(model.getCancelButton())) {
                    sb.append("缺少参数cancelButton");
                }
                if (sb.toString().length() > 0) {
                    function.onCallBack(gson.toJson(new ErrorModel(500, sb.toString())));
                    return;
                }
                YLDialogUtils.DialogButtonClickListener dialogListener = new YLDialogUtils.DialogButtonClickListener() {
                    @Override
                    public void onButtonClick(String var) {
                        function.onCallBack(var);
                    }
                };
                YLDialogUtils.showDialogWithTwoButton(YLBWebViewActivity.this, model.getTitle(), model.getMessage(), model.getOkButton(), model.getCancelButton(), dialogListener);
            }
        });

        registerHandler("alert", new JsBridgeHandler() {
            @Override
            public void handler(String data, final JsBridgeCallBack function) {
                Gson gson = new Gson();
                AlertModel model = gson.fromJson(data, AlertModel.class);
                StringBuffer sb = new StringBuffer("");
                if (TextUtils.isEmpty(model.getTitle())) {
                    sb.append("缺少参数title,");
                }
                if (TextUtils.isEmpty(model.getMessage())) {
                    sb.append("缺少参数message,");
                }
                if (TextUtils.isEmpty(model.getButton())) {
                    sb.append("缺少参数button");
                }
                if (sb.toString().length() > 0) {
                    function.onCallBack(gson.toJson(new ErrorModel(500, sb.toString())));
                    return;
                }
                YLDialogUtils.DialogButtonClickListener dialogListener = new YLDialogUtils.DialogButtonClickListener() {
                    @Override
                    public void onButtonClick(String var) {
                        function.onCallBack(var);
                    }
                };
                YLDialogUtils.showDialogWithOneButton(YLBWebViewActivity.this, model.getTitle(), model.getMessage(), model.getButton(), dialogListener);
            }
        });

        // 导航栏右侧按钮点击事件
        registerHandler("showRightItem", new JsBridgeHandler() {
            @Override
            public void handler(String data, final JsBridgeCallBack function) {
                Gson gson = new Gson();
                ShowRightItemModel model = gson.fromJson(data, ShowRightItemModel.class);
                // 优先使用图片
                if (!model.isShow()) {
                    mRightItem.setVisibility(View.GONE);
                    ivShare.setVisibility(View.GONE);
                    return;
                }
                if (!TextUtils.isEmpty(model.getIconType())) {
                    ShowRightIcon(model.getIconType());
                    return;
                } else if (!TextUtils.isEmpty(model.getMessage())) {
                    ivShare.setVisibility(View.GONE);
                    mRightItem.setVisibility(View.VISIBLE);
                    mRightItem.setText(model.getMessage());
                    return;
                }
                function.onCallBack(gson.toJson(new ErrorModel(500, "缺少参数")));
                mRightItem.setVisibility(View.GONE);
                ivShare.setVisibility(View.GONE);
            }
        });

        // 跳转登录页面
        registerHandler("login", new JsBridgeHandler() {
            @Override
            public void handler(String data, final JsBridgeCallBack function) {
                callbacks.put("login", function);
                Intent intent = new Intent(YLBWebViewActivity.this, SmsLoginActivity.class);
                intent.putExtra(YLBSdkConstants.EXTRA_YLBSDK_MSG, data);
                intent.putExtra("isFromThird", true);
                startActivityForResult(intent, YLBSdkConstants.YLBSDK_LOGIN_REQUEST_CODE);
            }
        });

        // 点击Banner图操作
        registerHandler("bannerAction", new JsBridgeHandler() {
            @Override
            public void handler(String data, final JsBridgeCallBack function) {
                Gson gson = new Gson();
                H5Banner bannerItem = gson.fromJson(data, H5Banner.class);
                if (bannerItem == null) {
                    function.onCallBack(new Gson().toJson(new ErrorModel(500, "banner不能为空")));
                    return;
                }
                int bannerType = bannerItem.getBannerType();
                switch (bannerType) {
                    case IS_LINK:
                    case 5:
                        if (bannerItem.getUrl().startsWith("maguanjia://")) {
                            if (bannerItem.getUrl().replace("maguanjia://", "").startsWith("http")) {
                                Intent intent = new Intent(YLBWebViewActivity.this, YLBWebViewActivity.class);
                                intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, bannerItem.getUrl().replace("maguanjia://", ""));
                                startActivity(intent);
                            } else {
                                if (bannerItem.getUrl().equals("maguanjia://supermarket")) {
                                    if (HomeActivity.instance != null) {
                                        YLBWebViewActivity.this.finish();
                                        HomeActivity.instance.setToSuperMarket(0, 0);
                                    }
                                    return;
                                }
                                if ("maguanjia://takeaway".equals(bannerItem.getUrl())) {
                                    //外卖 , 跳转老版首页
                                    YLBWebViewActivity.this.startActivity(new Intent(YLBWebViewActivity.this, OldHomeActivity.class));
                                    return;
                                }
                                Routers.open(YLBWebViewActivity.this, ActivitySchemeManager.SCHEME + bannerItem.getUrl().replace("maguanjia://", ""), new RouterCallback() {
                                    @Override
                                    public void notFound(Context context, Uri uri) {
                                        showNoticeDialog();
                                    }

                                    @Override
                                    public void beforeOpen(Context context, Uri uri) {
                                    }

                                    @Override
                                    public void afterOpen(Context context, Uri uri) {
                                    }

                                    @Override
                                    public void error(Context context, Uri uri, Throwable e) {
                                    }
                                });
                            }
                        } else if (bannerItem.getUrl().startsWith("http")) {
                            Intent intent = new Intent(YLBWebViewActivity.this, YLBWebViewActivity.class);
                            intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, bannerItem.getUrl() + "?longitude=" + PreferenceUtils.getLocation(YLBWebViewActivity.this)[1] + "&latitude=" + PreferenceUtils.getLocation(YLBWebViewActivity.this)[0]);
                            startActivity(intent);
                        }
                        break;
                    case IS_CATEGORY:
                        Intent intent1 = new Intent(YLBWebViewActivity.this, PrimaryCategoryActivity.class);
                        intent1.putExtra("tagCategoryId", bannerItem.getFirstCategoryId());
                        intent1.putExtra("tagCategorySecondId", bannerItem.getSecondCategoryId());
                        intent1.putExtra("tagCategoryType", bannerItem.getCategoryType());
                        intent1.putExtra("categoryName", bannerItem.getName());
                        startActivity(intent1);
                        break;
                    case IS_MERCHANT:
                        Routers.open(YLBWebViewActivity.this, ActivitySchemeManager.SCHEME + "merchant/" + bannerItem.getMerchantId());
                        break;
                    case IS_GROUPBUY:
                        Routers.open(YLBWebViewActivity.this, ActivitySchemeManager.SCHEME + "groupPurchaseDetail/" + bannerItem.getGroupBuyId());
                        break;
                    case 6: // 商超
                        if (HomeActivity.instance != null) {
                            YLBWebViewActivity.this.finish();
                            HomeActivity.instance.setToSuperMarket(bannerItem.getFirstTGoodsCategoryId(), bannerItem.getSecondTGoodsCategoryId());
                        }
                        break;
                    case 7: // 团购商家
                        Routers.open(YLBWebViewActivity.this, ActivitySchemeManager.SCHEME + "groupPurchaseMerchant/" + bannerItem.getGroupPurchaseMerchantId());
                        break;
                }
            }
        });

        // 点击广告栏图操作
        registerHandler("broadcastAction", new JsBridgeHandler() {
            @Override
            public void handler(String data, final JsBridgeCallBack function) {
                Gson gson = new Gson();
                H5Banner bannerItem = gson.fromJson(data, H5Banner.class);
                if (bannerItem == null) {
                    function.onCallBack(new Gson().toJson(new ErrorModel(500, "banner不能为空")));
                    return;
                }
                int broadcastType = bannerItem.getBroadcastType();
                switch (broadcastType) {
                    case IS_LINK:
                    case 5:
                        if (bannerItem.getUrl().startsWith("maguanjia://")) {
                            if (bannerItem.getUrl().replace("maguanjia://", "").startsWith("http")) {
                                Intent intent = new Intent(YLBWebViewActivity.this, YLBWebViewActivity.class);
                                intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, bannerItem.getUrl().replace("maguanjia://", ""));
                                startActivity(intent);
                            } else {
                                if (bannerItem.getUrl().equals("maguanjia://supermarket")) {
                                    if (HomeActivity.instance != null) {
                                        YLBWebViewActivity.this.finish();
                                        HomeActivity.instance.setToSuperMarket(0, 0);
                                    }
                                    return;
                                }
                                if ("maguanjia://takeaway".equals(bannerItem.getUrl())) {
                                    //外卖 , 跳转老版首页
                                    YLBWebViewActivity.this.startActivity(new Intent(YLBWebViewActivity.this, OldHomeActivity.class));
                                    return;
                                }
                                Routers.open(YLBWebViewActivity.this, ActivitySchemeManager.SCHEME + bannerItem.getUrl().replace("maguanjia://", ""), new RouterCallback() {
                                    @Override
                                    public void notFound(Context context, Uri uri) {
                                        showNoticeDialog();
                                    }

                                    @Override
                                    public void beforeOpen(Context context, Uri uri) {
                                    }

                                    @Override
                                    public void afterOpen(Context context, Uri uri) {
                                    }

                                    @Override
                                    public void error(Context context, Uri uri, Throwable e) {
                                    }
                                });
                            }
                        } else if (bannerItem.getUrl().startsWith("http")) {
                            Intent intent = new Intent(YLBWebViewActivity.this, YLBWebViewActivity.class);
                            intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, bannerItem.getUrl() + "?longitude=" + PreferenceUtils.getLocation(YLBWebViewActivity.this)[1] + "&latitude=" + PreferenceUtils.getLocation(YLBWebViewActivity.this)[0]);
                            startActivity(intent);
                        }
                        break;
                    case IS_CATEGORY:
                        Intent intent1 = new Intent(YLBWebViewActivity.this, PrimaryCategoryActivity.class);
                        intent1.putExtra("tagCategoryId", bannerItem.getFirstCategoryId());
                        intent1.putExtra("tagCategorySecondId", bannerItem.getSecondCategoryId());
                        intent1.putExtra("tagCategoryType", bannerItem.getCategoryType());
                        intent1.putExtra("categoryName", bannerItem.getName());
                        startActivity(intent1);
                        break;
                    case IS_MERCHANT:
                        Routers.open(YLBWebViewActivity.this, ActivitySchemeManager.SCHEME + "merchant/" + bannerItem.getMerchantId());
                        break;
                    case IS_GROUPBUY:
                        Routers.open(YLBWebViewActivity.this, ActivitySchemeManager.SCHEME + "groupPurchaseDetail/" + bannerItem.getGroupBuyId());
                        break;
                    case 6:
                        if (HomeActivity.instance != null) {
                            YLBWebViewActivity.this.finish();
                            HomeActivity.instance.setToSuperMarket(bannerItem.getFirstTGoodsCategoryId(), bannerItem.getSecondTGoodsCategoryId());
                        }
                        break;
                    case 7: // 团购商家
                        Routers.open(YLBWebViewActivity.this, ActivitySchemeManager.SCHEME + "groupPurchaseMerchant/" + bannerItem.getGroupPurchaseMerchantId());
                        break;
                }
            }
        });

        // 支付逻辑
        registerHandler("pay", new JsBridgeHandler() {
            @Override
            public void handler(String data, final JsBridgeCallBack function) {
                callbacks.put("pay", function);
                Gson gson = new Gson();
                PayModel model = gson.fromJson(data, PayModel.class);
                if (TextUtils.isEmpty(model.getOrderId())) {
                    function.onCallBack(gson.toJson(new ErrorModel(500, "缺少参数orderId")));
                    return;
                }
                Intent intent = new Intent(YLBWebViewActivity.this, OnlinePayActivity.class);
                intent.putExtra(YLBSdkConstants.EXTRA_YLBSDK_MSG, model.getOrderId());
                intent.putExtra("isFromThird", true);
                startActivityForResult(intent, YLBSdkConstants.YLBSDK_PAY_REQUEST_CODE);
            }
        });

        // 关闭页面
        registerHandler("exitApp", new JsBridgeHandler() {
            @Override
            public void handler(String data, final JsBridgeCallBack function) {
                finish();
            }
        });

        // 拨打电话
        registerHandler("callTel", new JsBridgeHandler() {//调起拨打电话
            @Override
            public void handler(String data, final JsBridgeCallBack function) {
                Gson gson = new Gson();
                CallTelModel model = gson.fromJson(data, CallTelModel.class);
                if (TextUtils.isEmpty(model.getPhoneNum())) {
                    function.onCallBack(gson.toJson(new ErrorModel(500, "缺少参数phoneNum")));
                    return;
                }
                YLDialogUtils.DialogButtonClickListener dialogListener = new YLDialogUtils.DialogButtonClickListener() {
                    @Override
                    public void onButtonClick(String var) {
                        function.onCallBack(var);
                    }
                };
                YLDialogUtils.showDialogCallPhone(YLBWebViewActivity.this, "拨打电话", model.getPhoneNum(), "拨打", "取消", dialogListener);
            }
        });

        // 返回Token
        registerHandler("getToken", new JsBridgeHandler() {//获取token
            @Override
            public void handler(String data, JsBridgeCallBack function) {
                JSONObject object = new JSONObject();
                String token;
                SmsLoginModel.ValueEntity.AppUserEntity userInfo = App.getUserInfo();
                if (userInfo != null) {
                    token = userInfo.getToken();
                } else {
                    token = PreferenceUtils.getStringPreference("token", "", App.getInstance());
                }
                try {
                    object.put("value", token);
                    object.put("code", 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                function.onCallBack(object.toString());
            }
        });

        // 返回AgentId
        registerHandler("getAgentId", new JsBridgeHandler() {//获取token
            @Override
            public void handler(String data, JsBridgeCallBack function) {
                JSONObject object = new JSONObject();
                try {
                    object.put("value", agentId);
                    object.put("code", 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                function.onCallBack(object.toString());
            }
        });

        // 调起分享
        registerHandler("share", new JsBridgeHandler() {//分享功能
            @Override
            public void handler(String data, JsBridgeCallBack function) {
                Gson gson = new Gson();
                ShareModel model = gson.fromJson(data, ShareModel.class);
                StringBuffer sb = new StringBuffer("");
                if (TextUtils.isEmpty(model.getTitle())) {
                    sb.append("缺少参数title,");
                }
                if (TextUtils.isEmpty(model.getDes())) {
                    sb.append("缺少参数des,");
                }
                if (TextUtils.isEmpty(model.getSharedUrl())) {
                    sb.append("缺少参数shareUrl");
                }
                if (sb.toString().length() > 0) {
                    function.onCallBack(gson.toJson(new ErrorModel(500, sb.toString())));
                    return;
                }
                MLog.d(model.getTitle() + "," + model.getDes() + "," + model.getImg() + "," + model.getSharedUrl());
                shareUtil = null;
                shareUtil = new ShareUtil(YLBWebViewActivity.this, model.getTitle(), model.getDes(), model.getSharedUrl(), model.getImg(), true);
                shareUtil.showPopupWindow();
                function.onCallBack(gson.toJson(model));
            }
        });

        // 跳转到之前打开的URL
        registerHandler("backUrl", new JsBridgeHandler() {//分享功能
            @Override
            public void handler(String data, JsBridgeCallBack function) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    backUrl = jsonObject.getString("url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (TextUtils.isEmpty(backUrl)) {
                    function.onCallBack(new Gson().toJson(new ErrorModel(500, "缺少参数backUrl")));
                    return;
                }
                function.onCallBack(data);
            }
        });

        //获取收货地址列表
        registerHandler("selectAddress", new JsBridgeHandler() {
            @Override
            public void handler(String data, JsBridgeCallBack function) {
                callbacks.put("selectAddress", function);
                Gson gson = new Gson();
                addressModel model = gson.fromJson(data, addressModel.class);
                Intent intent = new Intent(YLBWebViewActivity.this, AddressManageActivity.class);
                intent.putExtra("group", "group");
                if (!TextUtils.isEmpty(model.getTitle())) {
                    intent.putExtra("title", model.getTitle());
                }
                startActivityForResult(intent, YLBSdkConstants.YLBSDK_SELECT_ADDRESS);
            }
        });

        //获取当前位置信息
        registerHandler("getCurrentLocation", new JsBridgeHandler() {
            @Override
            public void handler(String data, JsBridgeCallBack function) {
                JSONObject object = new JSONObject();
                try {
                    String latitude = PreferenceUtils.getFixedLocation(YLBWebViewActivity.this)[0];
                    String longitude = PreferenceUtils.getFixedLocation(YLBWebViewActivity.this)[1];
                    if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude) || latitude.contains("E") || longitude.contains("E")) {
                        Native2H5Model native2H5Model = new Native2H5Model(0, "");
                        function.onCallBack(new Gson().toJson(native2H5Model));
                    } else {
                        object.put("latitude", latitude);
                        object.put("longitude", longitude);
                        object.put("cityName", PreferenceUtils.getFixedAddressName(YLBWebViewActivity.this));
                        Native2H5Model native2H5Model = new Native2H5Model(0, object.toString());
                        function.onCallBack(new Gson().toJson(native2H5Model));
                    }
                } catch (JSONException e) {
                    //返回空
                    Native2H5Model native2H5Model = new Native2H5Model(0, "");
                    function.onCallBack(new Gson().toJson(native2H5Model));
                    e.printStackTrace();
                }
            }
        });

        //获取当前选择的位置
        registerHandler("getSelectLocation", new JsBridgeHandler() {
            @Override
            public void handler(String data, JsBridgeCallBack function) {
                JSONObject object = new JSONObject();
                try {
                    String latitude = PreferenceUtils.getLocation(YLBWebViewActivity.this)[0];
                    String longitude = PreferenceUtils.getLocation(YLBWebViewActivity.this)[1];
                    if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude) || latitude.contains("E") || longitude.contains("E")) {
                        Native2H5Model native2H5Model = new Native2H5Model(0, "");
                        function.onCallBack(new Gson().toJson(native2H5Model));
                    } else {
                        object.put("latitude", latitude);
                        object.put("longitude", longitude);
                        object.put("cityName", PreferenceUtils.getAddressName(YLBWebViewActivity.this));
                        Native2H5Model native2H5Model = new Native2H5Model(0, object.toString());
                        function.onCallBack(new Gson().toJson(native2H5Model));
                    }
                } catch (JSONException e) {
                    Native2H5Model native2H5Model = new Native2H5Model(0, "");
                    function.onCallBack(new Gson().toJson(native2H5Model));
                    e.printStackTrace();
                }
            }
        });

        // 点击分类的跳转逻辑
        registerHandler("categoryAction", new JsBridgeHandler() {
            @Override
            public void handler(String data, final JsBridgeCallBack function) {
                Gson gson = new Gson();
                PrimaryCategory primaryCategory = gson.fromJson(data, PrimaryCategory.class);
                if (primaryCategory == null) {
                    function.onCallBack(new Gson().toJson(new ErrorModel(500, "数据不能为空")));
                    return;
                }
                if (primaryCategory.getGraySwitch() == 0) {
                    if (primaryCategory.getGotoType() == 2) {
                        Intent intent = new Intent(YLBWebViewActivity.this, PrimaryCategoryActivity.class);
                        intent.putExtra("tagCategoryId", primaryCategory.getTagCategoryId());
                        intent.putExtra("tagCategoryType", primaryCategory.getTagCategoryType());
                        intent.putExtra("categoryName", primaryCategory.getName());
                        startActivity(intent);
                    } else if (primaryCategory.getGotoType() == 1) {
                        if (primaryCategory.getGotoUrl().replace("maguanjia://", "").startsWith("http")) {
                            Intent intent = new Intent(YLBWebViewActivity.this, YLBWebViewActivity.class);
                            intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, primaryCategory.getGotoUrl().replace("maguanjia://", ""));
                            startActivity(intent);
                        } else {
                            if (primaryCategory.getGotoUrl().startsWith("maguanjia://")) {
                                Routers.open(YLBWebViewActivity.this, ActivitySchemeManager.SCHEME + primaryCategory.getGotoUrl().replace("maguanjia://", ""), new RouterCallback() {
                                    @Override
                                    public void notFound(Context context, Uri uri) {
                                        showNoticeDialog();
                                    }

                                    @Override
                                    public void beforeOpen(Context context, Uri uri) {
                                    }

                                    @Override
                                    public void afterOpen(Context context, Uri uri) {
                                    }

                                    @Override
                                    public void error(Context context, Uri uri, Throwable e) {
                                    }
                                });
                            } else if (primaryCategory.getGotoUrl().startsWith("http")) {
                                Intent intent = new Intent(YLBWebViewActivity.this, YLBWebViewActivity.class);
                                intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, primaryCategory.getGotoUrl());
                                startActivity(intent);
                            }
                        }
                    } else if (primaryCategory.getGotoType() == 4) {
                        GroupBuyingCategoryActivity.toGroupBuyingCategoryActivity(YLBWebViewActivity.this, primaryCategory.getCategoryName(), primaryCategory.getGroupPurchaseCategoryId(), primaryCategory.getChildGroupPurchaseCategoryId());
                    }
                } else {
                    ToastUtils.displayMsg("尚未开通，敬请期待", YLBWebViewActivity.this);
                }
            }
        });

        // 点击推广位的跳转逻辑
        registerHandler("headLineAction", new JsBridgeHandler() {
            @Override
            public void handler(String data, final JsBridgeCallBack function) {
                Gson gson = new Gson();
                Broadcast bean = gson.fromJson(data, Broadcast.class);
                if (bean == null) {
                    function.onCallBack(new Gson().toJson(new ErrorModel(500, "数据不能为空")));
                    return;
                }
                int gotoType = bean.getGotoType();
                switch (gotoType) {
                    case 1:
                        if (bean.getLinkUrl().startsWith("maguanjia://")) {
                            if (bean.getLinkUrl().replace("maguanjia://", "").startsWith("http")) {
                                Intent intent = new Intent(YLBWebViewActivity.this, YLBWebViewActivity.class);
                                intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, bean.getLinkUrl().replace("maguanjia://", ""));
                                startActivity(intent);
                            } else {
                                Routers.open(YLBWebViewActivity.this, ActivitySchemeManager.SCHEME + bean.getLinkUrl().replace("maguanjia://", ""), new RouterCallback() {
                                    @Override
                                    public void notFound(Context context, Uri uri) {
                                        showNoticeDialog();
                                    }

                                    @Override
                                    public void beforeOpen(Context context, Uri uri) {
                                    }

                                    @Override
                                    public void afterOpen(Context context, Uri uri) {
                                    }

                                    @Override
                                    public void error(Context context, Uri uri, Throwable e) {
                                    }
                                });
                            }
                        } else {
                            Intent intent = new Intent(YLBWebViewActivity.this, YLBWebViewActivity.class);
                            intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, bean.getLinkUrl() + "?longitude=" + PreferenceUtils.getLocation(YLBWebViewActivity.this)[1] + "&latitude=" + PreferenceUtils.getLocation(YLBWebViewActivity.this)[0]);
                            startActivity(intent);
                        }
                        break;
                    case 2:
                        Routers.open(YLBWebViewActivity.this, ActivitySchemeManager.SCHEME + "merchant/" + bean.getMerchantId());
                        break;
                    case 3:
                        Routers.open(YLBWebViewActivity.this, ActivitySchemeManager.SCHEME + "groupPurchaseDetail/" + bean.getGroupBuyId());
                        break;
                    case 4:
                        Intent intent1 = new Intent(YLBWebViewActivity.this, PrimaryCategoryActivity.class);
                        intent1.putExtra("tagCategoryId", bean.getFirstCategoryId());
                        intent1.putExtra("tagCategorySecondId", bean.getSecondCategoryId());
                        intent1.putExtra("tagCategoryType", bean.getCategoryType());
                        if (bean instanceof Broadcast) {
                            intent1.putExtra("categoryName", (bean).getTitle());
                        }
                        startActivity(intent1);
                        break;
                    case 5:
                        if (HomeActivity.instance != null) {
                            YLBWebViewActivity.this.finish();
                            HomeActivity.instance.setToSuperMarket(bean.getFirstTGoodsCategoryId(), bean.getSecondTGoodsCategoryId());
                        }
                        break;
                    case 6:
                        Routers.open(YLBWebViewActivity.this, ActivitySchemeManager.SCHEME + "groupPurchaseMerchant/" + bean.getGroupPurchaseMerchantId());
                        break;
                    case 7:
                        Routers.open(YLBWebViewActivity.this, ActivitySchemeManager.SCHEME + "groupPurchaseCoupon/" + bean.getGroupPurchaseCouponId());
                        break;
                }
            }
        });

        //检查用户是否绑定手机号 未绑定直接跳转绑定页面
        registerHandler("bindStateDetection", new JsBridgeHandler() {
            @Override
            public void handler(String data, JsBridgeCallBack function) {
                // 判断是否绑定手机卡
                JSONObject object = new JSONObject();
                try {
                    if (TextUtils.isEmpty(App.getUserInfo().getMobile())) {
                        ToastUtils.displayMsg("请先绑定手机号", YLBWebViewActivity.this);
                        Intent intent = new Intent(YLBWebViewActivity.this, BindMobileActivity.class);
                        YLBWebViewActivity.this.startActivity(intent);
                        object.put("isBind", false);
                    } else {
                        object.put("isBind", true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Native2H5Model native2H5Model = new Native2H5Model(0, object.toString());
                function.onCallBack(new Gson().toJson(native2H5Model));
            }
        });

        // 扫码
        registerHandler("scanCode", new JsBridgeHandler() {
            @Override
            public void handler(String data, final JsBridgeCallBack function) {
                callbacks.put("scanCode", function);
                Intent intent = new Intent(YLBWebViewActivity.this, CaptureActivity.class);
                startActivityForResult(intent, YLBSdkConstants.YLBSDK_SCAN_CODE);
            }
        });

        //反馈 feedback
        registerHandler("feedback", new JsBridgeHandler() {
            @Override
            public void handler(String data, final JsBridgeCallBack function) {
                Routers.open(YLBWebViewActivity.this, ActivitySchemeManager.SCHEME + ActivitySchemeManager.URL_FEED_BACK);
            }
        });

        //返回代理商电话
        registerHandler("getAgentTel", new JsBridgeHandler() {
            @Override
            public void handler(String data, final JsBridgeCallBack function) {
                //tel
                String agentPhone = PreferenceUtils.getStringPreference("agentPhone", getString(R.string.sale_phone), YLBWebViewActivity.this);
                if (TextUtils.isEmpty(agentPhone)) {
                    agentPhone = getString(R.string.sale_phone);
                }
                Native2H5Model native2H5Model = new Native2H5Model(0, agentPhone);
                function.onCallBack(new Gson().toJson(native2H5Model));
            }
        });
    }

    /**
     * title 右侧图标显示
     * 1:分享  2:搜索  3:更多
     *
     * @param iconType
     */
    private void ShowRightIcon(String iconType) {
        mRightItem.setVisibility(View.GONE);
        ivShare.setVisibility(View.VISIBLE);
        if ("1".equals(iconType)) {
            ivShare.setImageResource(R.drawable.icon_web_share);
        } else if ("2".equals(iconType)) {
            ivShare.setImageResource(R.drawable.icon_web_search);
        } else if ("3".equals(iconType)) {
            ivShare.setImageResource(R.drawable.icon_web_phone);
        } else {
            ivShare.setImageResource(R.drawable.icon_web_more);
        }
    }

    /**
     * 获取activity contentView 的布局id
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_ylb_webview;
    }


    /**
     * 页面、数据初始化在这个方法里写
     */
    @Override
    protected void initial() {
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        FrameLayout mFlTitle = (FrameLayout) findViewById(R.id.fl_title);
        View viewLine = (View) findViewById(R.id.view_line);

        mRightItem = (TextView) findViewById(R.id.tv_right_text);
        mRightItem.setOnClickListener(this);

        mLlWebview = (FrameLayout) findViewById(R.id.ll_webview);

        mProgressBar = (ProgressBar) findViewById(R.id.pb_process);

        ImageView ivTitleBack = (ImageView) findViewById(R.id.iv_title_back);
        ivTitleBack.setOnClickListener(this);

        ImageView ivTitleClose = (ImageView) findViewById(R.id.iv_title_finish);
        ivTitleClose.setOnClickListener(this);

        ivShare = (ImageView) findViewById(R.id.com_share);
        ivShare.setOnClickListener(this);

        mProgressDialog = new ProgressDialog(this);

        sURL = this.getIntent().getStringExtra(YLBSdkConstants.EXTRA_H5_URL);
        if (!sURL.contains("://")) {
            sURL = "http://" + sURL;
        }
        tag = this.getIntent().getStringExtra(YLBSdkConstants.EXTRA_H5_SHARE);

        boolean isTitleColor = this.getIntent().getBooleanExtra(YLBSdkConstants.EXTRA_H5_TITLE_COLOR, false);
        if (isTitleColor) {
            mFlTitle.setBackgroundColor(getResources().getColor(R.color.web_title));
            mTvTitle.setTextColor(getResources().getColor(R.color.white));
            mRightItem.setTextColor(getResources().getColor(R.color.white));
            ivTitleBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_back_white));
            ivTitleClose.setImageDrawable(getResources().getDrawable(R.drawable.ic_close_white));
            viewLine.setVisibility(View.GONE);
        }
    }

    /**
     * 提供WebView的parent，webview初始化之后会add到parent中。不得返回null
     */
    @Override
    protected ViewGroup getWebViewContainer() {
        return mLlWebview;
    }

    /**
     * 提供显示title的textView，以便webView在onReceivedTitle回调时设置标题，可以返回null
     */
    @Override
    protected TextView getTitleView() {
        return mTvTitle;
    }

    /**
     * 提供进度条，以便webView在onProgressChanged回调时更新进度，可以返回null
     */
    @Override
    protected ProgressBar getProgressBar() {
        return mProgressBar;
//        return null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_title_back) {
            if (h5Container != null && h5Container.canGoBack()) {
                if (!TextUtils.isEmpty(backUrl)) {
                    goBack();
                } else {
                    String url = h5Container.getUrl();
                    if ((sURL.contains("#/") ? sURL : sURL + "#/").equals(url)) {
                        finish();
                    } else if (url.contains("SearchResult")) {
                        // 信息筛选列表页面
                        backUrl = sURL.contains("#/") ? sURL : sURL + "#/";
                        goBack();
                    } else {
                        h5Container.goBack();
                    }
                }
            } else {
                finish();
            }
        } else if (v.getId() == R.id.iv_title_finish) {
            finish();
        } else if (v.getId() == R.id.tv_right_text) {
            if (mRightItem.getText().toString().length() > 0) {
                h5Container.loadUrl("javascript:rightItemClick()");
            }
        } else if (v.getId() == R.id.com_share) {
            if (TextUtils.isEmpty(tag)) {
                h5Container.loadUrl("javascript:rightItemClick()");
            } else {
                String url = h5Container.getUrl();
                String shareUrl = getValueByName(url, "shareUrl");
                ShareUtil shareUtil = new ShareUtil(YLBWebViewActivity.this, "马管家头条", h5Container.getTitle(), h5Container.getUrl(), shareUrl, true);
                shareUtil.showPopupWindow();
            }
        }
    }

    private Integer getBackIndex() {
        WebBackForwardList list = h5Container.copyBackForwardList();
        Integer backi = null;
        for (int i = list.getSize() - 1; i >= 0; --i) {
            String url = list.getItemAtIndex(i).getUrl();
            // MLog.e("URL" + url + "---BackUrl:" + backUrl);
            // select the appropriate index and set to backi
            // 第二个判断是判断返回的backUrl是否和当前打开的最顶层Url相同
            if (url.equals(backUrl) && i != list.getSize() - 1) {
                backi = i;
            }
        }
        if (backi != null) {
            backUrl = null;
        }
        return backi;
    }

    private void goBack() {
        Integer backi = getBackIndex();
        if (backi == null) {
            if (TextUtils.isEmpty(backUrl)) {
                h5Container.goBack();
            } else {
                Intent intent = new Intent(this, YLBWebViewActivity.class);
                intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, backUrl);
                startActivity(intent);
                backUrl = null;
                finish();
            }
            return;
        }
        Assert.assertNotNull(backi);
        WebBackForwardList list = h5Container.copyBackForwardList();
        Log.d("web", "going " + String.valueOf(backi - list.getCurrentIndex()));
        h5Container.goBackOrForward(backi - list.getCurrentIndex());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == YLBSdkConstants.YLBSDK_LOGIN_REQUEST_CODE) {
            if (data != null && data.hasExtra(YLBSdkConstants.EXTRA_YLBSDK_RESULT)) {
                if (callbacks.get("login") != null)
                    callbacks.get("login").onCallBack(data.getStringExtra(YLBSdkConstants.EXTRA_YLBSDK_RESULT));
            } else if (resultCode == RESULT_CANCELED) {
                Native2H5Model native2H5Model = new Native2H5Model(504, "登录取消");
                if (callbacks.get("login") != null)
                    callbacks.get("login").onCallBack(new Gson().toJson(native2H5Model));
            } else {
                Native2H5Model native2H5Model = new Native2H5Model(503, "未知参数");
                if (callbacks.get("login") != null)
                    callbacks.get("login").onCallBack(new Gson().toJson(native2H5Model));
            }
        } else if (requestCode == YLBSdkConstants.YLBSDK_PAY_REQUEST_CODE) {
            if (data != null && data.hasExtra(YLBSdkConstants.EXTRA_YLBSDK_RESULT)) {
                if (callbacks.get("pay") != null) {
                    String stringExtra = data.getStringExtra(YLBSdkConstants.EXTRA_YLBSDK_RESULT);
                    try {
                        JSONObject jsonObject = new JSONObject(stringExtra);
                        org.json.JSONObject object = new org.json.JSONObject();
                        int code = (int) jsonObject.get("code");
                        object.put("code", code);
                        if (code == 0) {
                            object.put("value", "支付成功");
                        } else {
                            object.put("value", jsonObject.get("msg"));
                            object.put("msg", jsonObject.get("value"));
                        }
                        callbacks.get("pay").onCallBack(object.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callbacks.get("pay").onCallBack(stringExtra);
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                Native2H5Model native2H5Model = new Native2H5Model(504, "取消支付");
                if (callbacks.get("pay") != null)
                    callbacks.get("pay").onCallBack(new Gson().toJson(native2H5Model));
            } else {
                Native2H5Model native2H5Model = new Native2H5Model(503, "未知参数");
                if (callbacks.get("pay") != null)
                    callbacks.get("pay").onCallBack(new Gson().toJson(native2H5Model));
            }
        } else if (requestCode == YLBSdkConstants.YLBSDK_SELECT_ADDRESS) {
            if (resultCode == 10002) {
                if (data.getSerializableExtra("address") != null) {
                    if (callbacks.get("selectAddress") != null) {
                        UserAddress userAddress = (UserAddress) data.getSerializableExtra("address");
                        Native2H5Model native2H5Model = new Native2H5Model(0, new Gson().toJson(userAddress));
                        callbacks.get("selectAddress").onCallBack(new Gson().toJson(native2H5Model));
                    } else {
                        Native2H5Model native2H5Model = new Native2H5Model(1, "地址获取失败");
                        callbacks.get("selectAddress").onCallBack(new Gson().toJson(native2H5Model));
                    }
                } else {
                    Native2H5Model native2H5Model = new Native2H5Model(1, "地址获取失败");
                    callbacks.get("selectAddress").onCallBack(new Gson().toJson(native2H5Model));
                }
            }
        } else if (requestCode == YLBSdkConstants.YLBSDK_SCAN_CODE) {
            //二维码扫描结果
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                String scanResult = bundle.getString("result");
                ScanModel scanModel = new ScanModel();
                scanModel.setCodeStr(scanResult);
                NativeH5Model<ScanModel> scanModelNativeH5Model = new NativeH5Model<>(0, scanModel);
                if (callbacks.get("scanCode") != null)
                    callbacks.get("scanCode").onCallBack(new Gson().toJson(scanModelNativeH5Model));
            } else {
                Native2H5Model native2H5Model = new Native2H5Model(1, "扫码失败");
                if (callbacks.get("scanCode") != null)
                    callbacks.get("scanCode").onCallBack(new Gson().toJson(native2H5Model));
            }
        }
        if (shareUtil != null) {
            shareUtil.onActivityResultData(requestCode, resultCode, data);
        }
    }


    @Override
    protected void onResume() {
        // 调用此方法可以向js传递事件，参数为事件名称
        sendEventToJs("resume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        sendEventToJs("pause");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        //如果要清空webview缓存，需在super.onDestroy()之前调用此方法
//        clearWebCache();
//        CookieSyncManager.createInstance(this);  //Create a singleton CookieSyncManager within a context
//        CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
//        cookieManager.removeAllCookie();// Removes all cookies.
//        cookieManager.setAcceptCookie(true);
//        CookieSyncManager.getInstance().sync(); // forces sync manager to sync now

        h5Container.setWebChromeClient(null);
        h5Container.setWebViewClient(null);
        h5Container.getSettings().setJavaScriptEnabled(false);
        h5Container.clearCache(true);
        h5Container.destroy();
        h5Container = null;

        super.onDestroy();
        System.gc();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (h5Container != null && h5Container.canGoBack()) {
            if (!TextUtils.isEmpty(backUrl)) {
                goBack();
            } else {
                String url = h5Container.getUrl();
                if ((sURL.contains("#/") ? sURL : sURL + "#/").equals(url)) {
                    finish();
                } else if (url.contains("SearchResult")) {
                    // 信息筛选列表页面
                    backUrl = sURL.contains("#/") ? sURL : sURL + "#/";
                    goBack();
                } else {
                    h5Container.goBack();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showNoticeDialog() {
        if (noticeDialog == null) {
            noticeDialog = new NoticeDialog(YLBWebViewActivity.this, new NoticeDialog.onBtnClickListener() {
                @Override
                public void onSure() {
                    noticeDialog.dismiss();
                }
            }, "", "您当前版本过低，暂无法使用该功能。请更新到最新版本马管家。");
        }
        noticeDialog.show();
    }

}


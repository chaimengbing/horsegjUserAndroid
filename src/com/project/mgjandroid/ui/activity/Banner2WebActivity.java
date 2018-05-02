package com.project.mgjandroid.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.net.MyWebViewClient;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.ShareUtil;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

/**
 * Created by yuandi on 2016/3/24.
 */
public class Banner2WebActivity extends BaseActivity {
    public final static String URL = "url";
    @InjectView(R.id.web_view_act_back)
    private ImageView ivBack;
    @InjectView(R.id.web_view_act_tv_title)
    private TextView tvTitle;
    @InjectView(R.id.web_view_act_progress)
    private ProgressBar progressBar;
    @InjectView(R.id.web_view_act_web_view)
    private WebView webview;
    @InjectView(R.id.web_view_share)
    private ImageView ivShare;
    @InjectView(R.id.web_view_act_title_bar)
    private RelativeLayout rlTitleBar;

    private String shareUrl;
    private String shareImageUrl;
    private ShareUtil shareUtil;
    private String mainTitle;
    private String url;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_web_view);
        Injector.get(this).inject();
        url = getIntent().getExtras().getString(URL);
        initView();
        webview.loadUrl(url);
    }


    private void initView() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webCanGoBack();
            }
        });
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shareUtil == null) {
                    shareUtil = new ShareUtil(mActivity, getString(R.string.share_horsegj_head_msg_content),
                            CheckUtils.isNoEmptyStr(webview.getTitle()) ? webview.getTitle() : getString(R.string.share_horsegj_head_msg),
                            shareUrl, CheckUtils.isNoEmptyStr(shareImageUrl) ? shareImageUrl : null, true);
                } else {
                    shareUtil.showPopupWindow();
                }
            }
        });
        mainTitle = getIntent().getExtras().getString("name");
        if ("管家头条".equals(mainTitle) || "新闻资讯".equals(mainTitle)) {
            rlTitleBar.setBackgroundColor(getResources().getColor(R.color.color_f5));
            tvTitle.setTextColor(getResources().getColor(R.color.color_3));
            ivBack.setBackgroundResource(R.drawable.icon_back_black);
        } else {
            tvTitle.setText(mainTitle);
        }
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.addJavascriptInterface(new JavaScriptInterface(), "android");

//        webview.getSettings().setDatabaseEnabled(true);
//        String dir = getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
//        webview.getSettings().setGeolocationDatabasePath(dir);
        webview.getSettings().setGeolocationEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        boolean flag = CommonUtils.isNetworkConnected();
        if (!flag) {
            webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        } else {//有网加载网络数据
            webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        }

        webview.setWebViewClient(new MyWebViewClient(progressBar, mActivity) {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.contains("childPage=1")) {
//                    view.loadUrl("javascript:window.android.GetContent('<head>'+"
//                            + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                    ivShare.setVisibility(View.VISIBLE);
                } else {
                    ivShare.setVisibility(View.GONE);
                }
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                ivShare.setVisibility(View.GONE);
//                tvTitle.setText(view.getTitle());
                if (url.contains("childPage=1")) {
                    shareUrl = url;
                    shareImageUrl = Uri.parse(url).getQueryParameter("shareUrl");
                    if ("管家头条".equals(mainTitle)) {
                        tvTitle.setText("详情");
                    } else if ("新闻资讯".equals(mainTitle)) {
                        tvTitle.setText("新闻资讯");
                    }
                } else {
                    shareUrl = "";
                    shareImageUrl = "";
                    if (CheckUtils.isEmptyStr(mainTitle)) {
                        tvTitle.setText(view.getTitle());
                    } else {
                        tvTitle.setText(mainTitle);
                    }
                }
            }
        });

        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (shareUtil != null) {
            shareUtil.onActivityResultData(requestCode, resultCode, data);
        }
    }

    public class JavaScriptInterface {
        @JavascriptInterface
        public void JsCallShare() {//Js端调用的方法 待修改
            ToastUtils.displayMsg("success", mActivity);
        }

        @JavascriptInterface
        public void GetContent(String content) {
            if (CheckUtils.isNoEmptyStr(content)) {
//                MLog.d(content);
            }
        }
    }

    private void webCanGoBack() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            finish();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        if (webview != null) {
            try {
                webview.removeAllViews();
                webview.clearCache(true);
                webview.clearHistory();
                webview.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()){
            if(webview.getUrl().equals(url)){
                super.onBackPressed();
            }else{
                webview.goBack();
            }
        }else{
            super.onBackPressed();
        }
    }
}

package com.project.mgjandroid.h5base.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.mgjandroid.h5base.jsbridge.JsBridgeCallBack;
import com.project.mgjandroid.h5base.jsbridge.JsBridgeHandler;
import com.project.mgjandroid.h5base.jsbridge.WebPageLoadingListener;
import com.tencent.smtt.sdk.DownloadListener;

/**
 * 项目名称：mgjandroid
 * 类描述：
 * 创建人：Mr_Lei
 * 创建时间：2018/4/17 11:58
 */
public abstract class YLH5CBaseActivity extends Activity {
    protected YLH5Container h5Container;

    public YLH5CBaseActivity() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getLayoutId());
        this.initial();
        this.h5Container = new YLH5Container(this, null, true);
        this.h5Container.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.h5Container.getSettings().setUserAgentString("appChannel/androidylh5sdk, appVersion/1.0.0");
        if (this.getWebViewContainer() == null) {
            throw new IllegalArgumentException("function getWebViewContainer() must not return null!");
        } else {
            this.getWebViewContainer().addView(this.h5Container);
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initial();

    protected abstract ViewGroup getWebViewContainer();

    protected abstract TextView getTitleView();

    protected abstract ProgressBar getProgressBar();

    protected void loadUrl(String url) {
        if (this.h5Container != null) {
            this.h5Container.loadUrl(url);
        }

    }

    protected void setDefaultHandler(JsBridgeHandler handler) {
        if (this.h5Container != null) {
            this.h5Container.setDefaultHandler(handler);
        }

    }

    protected void registerHandler(String handlerName, JsBridgeHandler handler) {
        if (this.h5Container != null) {
            this.h5Container.registerNativeHandler(handlerName, handler);
        }

    }

    protected void callJs(String jsHandlerName, String data) {
        if (this.h5Container != null) {
            this.h5Container.sendMessage(jsHandlerName, data);
        }

    }

    protected void callJs(String jsHandlerName, String data, JsBridgeCallBack callBack) {
        if (this.h5Container != null) {
            this.h5Container.sendMessage(jsHandlerName, data, callBack);
        }

    }

    protected void sendEventToJs(String eventName) {
        if (this.h5Container != null) {
            this.h5Container.sendEvent(eventName);
        }

    }

    protected void setDownLoadLister(DownloadListener listener) {
        if (this.h5Container != null) {
            this.h5Container.setDownloadListener(listener);
        }

    }

    protected void setWebPageLoadingListener(WebPageLoadingListener listener) {
        if (this.h5Container != null) {
            this.h5Container.setWebPageLoadingListener(listener);
        }

    }

    protected void clearWebCache() {
        if (this.h5Container != null) {
            this.h5Container.loadUrl("javascript:localStorage.clear()");
        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4 && event.getRepeatCount() == 0) {
            if (this.h5Container != null && this.h5Container.canGoBack()) {
                this.h5Container.goBack();
            } else {
                this.finish();
            }
        }

        return true;
    }

    protected void onDestroy() {
        if (this.h5Container != null) {
            if (this.getWebViewContainer() != null) {
                this.getWebViewContainer().removeView(this.h5Container);
            }

            this.h5Container.removeAllViews();
            this.h5Container.destroy();
        }

        super.onDestroy();
        System.gc();
    }
}

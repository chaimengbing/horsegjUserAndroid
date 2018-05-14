package com.project.mgjandroid.h5base.jsbridge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.project.mgjandroid.h5base.utils.YLLogUtils;
import com.project.mgjandroid.h5base.view.YLH5Container;
import com.tencent.smtt.export.external.interfaces.HttpAuthHandler;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;

/**
 * 项目名称：mgjandroid
 * 类描述：
 * 创建人：Mr_Lei
 * 创建时间：2018/4/17 12:03
 */
public class JsWebViewClient extends WebViewClient {
    private YLH5Container h5Container;

    public JsWebViewClient(YLH5Container h5Container) {
        this.h5Container = h5Container;
    }

    public void onPageStarted(WebView webView, String url, Bitmap favicon) {
        super.onPageStarted(webView, url, favicon);
        this.h5Container.onWebPageStarted(webView, url, favicon);
    }

    public void onPageFinished(WebView webView, String url) {
        super.onPageFinished(webView, url);
        JsBridgeUtils.webViewLoadLocalJs(this.h5Container);
        if (this.h5Container.getInitMessages() != null) {
            Iterator var3 = this.h5Container.getInitMessages().iterator();

            while (var3.hasNext()) {
                JsBridgeMessage m = (JsBridgeMessage) var3.next();
                this.h5Container.dispatchMessage(m);
            }

            this.h5Container.setInitMessages(null);
        }

        this.h5Container.onWebPageFinished(webView, url);
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException var5) {
            var5.printStackTrace();
        }

        if (url.startsWith("ylsdk://return/")) {
            this.h5Container.handlerReturnData(url);
            return true;
        } else if (url.startsWith("ylsdk://")) {
            this.h5Container.flushMessageQueue();
            return true;
        } else if (url.startsWith("file://")) {
            return false;
        } else {
            if (!url.startsWith("http")) {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                    this.h5Container.getContext().startActivity(intent);
                } catch (Exception var4) {
                    var4.printStackTrace();
                }
            } else {
                //view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
            return true;
        }
    }

    public void onReceivedHttpAuthRequest(WebView webview, HttpAuthHandler httpAuthHandlerhost, String host, String realm) {
        boolean flag = httpAuthHandlerhost.useHttpAuthUsernamePassword();
        YLLogUtils.i("X5WebView", "useHttpAuthUsernamePassword is" + flag);
        YLLogUtils.i("X5WebView", "HttpAuth host is" + host);
        YLLogUtils.i("X5WebView", "HttpAuth realm is" + realm);
    }

    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        sslErrorHandler.proceed();
    }

    public void onDetectedBlankScreen(String arg0, int arg1) {
        super.onDetectedBlankScreen(arg0, arg1);
    }

    public WebResourceResponse shouldInterceptRequest(WebView arg0, String arg1) {
        return super.shouldInterceptRequest(arg0, arg1);
    }
}


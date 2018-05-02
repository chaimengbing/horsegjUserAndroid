package com.project.mgjandroid.h5base.jsbridge;

import android.graphics.Bitmap;

import com.tencent.smtt.sdk.WebView;

/**
 * 项目名称：mgjandroid
 * 类描述：
 * 创建人：Mr_Lei
 * 创建时间：2018/4/17 12:00
 */
public interface WebPageLoadingListener {
    void onWebPageStarted(WebView var1, String var2, Bitmap var3);

    void onWebPageFinished(WebView var1, String var2);
}

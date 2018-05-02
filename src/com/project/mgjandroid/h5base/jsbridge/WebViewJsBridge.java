package com.project.mgjandroid.h5base.jsbridge;


/**
 * 项目名称：mgjandroid
 * 类描述：
 * 创建人：Mr_Lei
 * 创建时间：2018/4/17 12:00
 */
public interface WebViewJsBridge {
    void sendMessage(String var1, String var2);

    void sendMessage(String var1, String var2, JsBridgeCallBack var3);

    void sendEvent(String var1);
}

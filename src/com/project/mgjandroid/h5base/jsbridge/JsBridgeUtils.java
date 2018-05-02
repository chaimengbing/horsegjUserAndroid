package com.project.mgjandroid.h5base.jsbridge;

import android.content.Context;

import com.tencent.smtt.sdk.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 项目名称：mgjandroid
 * 类描述：
 * 创建人：Mr_Lei
 * 创建时间：2018/4/17 12:03
 */
public class JsBridgeUtils {
    public static final String YL_OVERRIDE_SCHEMA = "ylsdk://";
    public static final String YL_RETURN_DATA = "ylsdk://return/";
    public static final String UNDERLINE_STR = "_";
    public static final String CALLBACK_ID_FORMAT = "JAVA_CB_%s";
    public static final String JS_FETCH_QUEUE_FROM_JAVA = "javascript:YLJsBridge._fetchQueue();";
    public static final String JS_HANDLE_MESSAGE_FROM_JAVA = "javascript:YLJsBridge._handleMsgFromNative('%s');";
    public static final String JAVA_SEND_EVENT_TO_JS = "javascript:YLJsBridge._sendEventFromNative('%s');";
    private static final String YL_FETCH_QUEUE = "ylsdk://return/_fetchQueue/";
    private static final String YL_JS_STR = "javascript:YLJsBridge.";
    private static final String JS_STR = "javascript:";
    private static final String EMPTY_STR = "";
    private static final String SPLIT_MARK = "/";

    public JsBridgeUtils() {
    }

    public static String getFuncFromUrl(String url) {
        String temp = url.replace("ylsdk://return/", "");
        String[] funcAndData = temp.split("/");
        return funcAndData.length >= 1 ? funcAndData[0] : null;
    }

    public static String getDataFromUrl(String url) {
        if (url.startsWith("ylsdk://return/_fetchQueue/")) {
            return url.replace("ylsdk://return/_fetchQueue/", "");
        } else {
            String temp = url.replace("ylsdk://return/", "");
            String[] funcAndData = temp.split("/");
            if (funcAndData.length < 2) {
                return null;
            } else {
                StringBuilder sb = new StringBuilder();

                for(int i = 1; i < funcAndData.length; ++i) {
                    sb.append(funcAndData[i]);
                }

                return sb.toString();
            }
        }
    }

    public static String parseFuncName(String jsUrl) {
        return jsUrl.replace("javascript:YLJsBridge.", "").replaceAll("\\(.*\\);", "");
    }

    public static void webViewLoadLocalJs(WebView view) {
        view.loadUrl("javascript:(function() {\n    if (window.YLJsBridge) {\n        return;\n    }\n\n    var CUSTOM_PROTOCOL_SCHEME = 'ylsdk://';\n    var QUEUE_HAS_MESSAGE = '__QUEUE_MESSAGE__/';\n    var JS_FETCH_QUEUE = 'return/_fetchQueue/';\n\n    var aIframe;\n    var sendMessageQueue = [];\n    var receiveMessageQueue = [];\n    var jsMessageHandlers = {};\n    var jsCallbacks = {};\n    var uniqueId = 1;\n\n    function setDefaultHandler(defaultHandler) {\n        if (YLJsBridge._defaultHandler) {\n            throw new Error('YLJsBridge.init called twice');\n        }\n        YLJsBridge._defaultHandler = defaultHandler;\n        var receivedMessages = receiveMessageQueue;\n        receiveMessageQueue = null;\n        for (var i = 0; i < receivedMessages.length; i++) {\n            dispatchMsgFromNative(receivedMessages[i]);\n        }\n    }\n\n    function registerJsHandler(handlerName, handler) {\n        jsMessageHandlers[handlerName] = handler;\n    }\n\n    function sendMessage(data, responseCallback) {\n        doSendMessage({\n            data: data\n        }, responseCallback);\n    }\n\n    function callNativeHandler(handlerName, data, callback) {\n        doSendMessage({\n            handlerName: handlerName,\n            data: data\n        }, callback);\n    }\n\n    function doSendMessage(message, callback) {\n        if (callback) {\n            var callbackId = 'JS_CB_' + (uniqueId++) + '_' + new Date().getTime();\n            jsCallbacks[callbackId] = callback;\n            message.callbackId = callbackId;\n        }\n        sendMessageQueue.push(message);\n        aIframe.src = CUSTOM_PROTOCOL_SCHEME + QUEUE_HAS_MESSAGE;\n    }\n\n    function fetchQueue() {\n        var messageQueueString = JSON.stringify(sendMessageQueue);\n        sendMessageQueue = [];\n        aIframe.src = CUSTOM_PROTOCOL_SCHEME + JS_FETCH_QUEUE + encodeURIComponent(messageQueueString);\n    }\n\n    function handleMsgFromNative(messageJSON) {\n        console.log(messageJSON);\n        if (receiveMessageQueue && receiveMessageQueue.length > 0) {\n            receiveMessageQueue.push(messageJSON);\n        } else {\n            dispatchMsgFromNative(messageJSON);\n        }\n    }\n\n    function dispatchMsgFromNative(messageJSON) {\n        setTimeout(function() {\n            var message = JSON.parse(messageJSON);\n            var callback;\n            if (message.responseId) {\n                callback = jsCallbacks[message.responseId];\n                if (!callback) {\n                    return;\n                }\n                var result = message.result;\n                if (typeof result == 'string' && result.indexOf('{') > -1) {\n                    try {\n                        result = JSON.parse(result);\n                    } catch(e) {\n                        console.log(e);\n                    }\n                }\n                callback(result);\n                delete jsCallbacks[message.responseId];\n            } else {\n                if (message.callbackId) {\n                    var callbackResponseId = message.callbackId;\n                    callback = function(result) {\n                        doSendMessage({\n                            responseId: callbackResponseId,\n                            result: result\n                        });\n                    };\n                }\n                var handler = YLJsBridge._defaultHandler;\n                if (message.handlerName && jsMessageHandlers[message.handlerName]) {\n                    handler = jsMessageHandlers[message.handlerName];\n                }\n                try {\n                    var data = message.data;\n                    if (typeof data == 'string' && data.indexOf('{') > -1) {\n                        try {\n                            data = JSON.parse(data);\n                        } catch(e) {\n                            console.log(e);\n                        }\n                    }\n                    handler(data, callback);\n                } catch (exception) {\n                    if (typeof console != 'undefined') {\n                        console.log(\"YLJsBridge: WARNING: javascript handler threw.\", message, exception);\n                    }\n                }\n            }\n        });\n    }\n\n    function sendEventFromNative(eventStr) {\n        var nativeEvent = document.createEvent('Events');\n        nativeEvent.initEvent(eventStr);\n        document.dispatchEvent(nativeEvent);\n    }\n\n    var YLJsBridge = window.YLJsBridge = {\n        init: setDefaultHandler,\n        registerHandler: registerJsHandler,\n        sendMessage: sendMessage,\n        call: callNativeHandler,\n        _fetchQueue: fetchQueue,\n        _handleMsgFromNative: handleMsgFromNative,\n        _sendEventFromNative: sendEventFromNative\n    };\n\n    function createQueueReadyIframe(doc) {\n        aIframe = doc.createElement('iframe');\n        aIframe.style.display = 'none';\n        doc.documentElement.appendChild(aIframe);\n    }\n\n    createQueueReadyIframe(document);\n\n    var readyEvent = document.createEvent('Events');\n    readyEvent.initEvent('YLJsBridgeReady');\n    document.dispatchEvent(readyEvent);\n})();\n");
    }

    public static void webViewLoadLocalJs(WebView view, String path) {
        String jsContent = assetFile2Str(view.getContext(), path);
        view.loadUrl("javascript:" + jsContent);
    }

    private static String assetFile2Str(Context c, String urlStr) {
        InputStream in = null;

        try {
            in = c.getAssets().open(urlStr);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            StringBuilder sb = new StringBuilder();

            do {
                line = bufferedReader.readLine();
                if (line != null && !line.matches("^\\s*\\/\\/.*")) {
                    sb.append(line);
                }
            } while(line != null);

            bufferedReader.close();
            in.close();
            String var6 = sb.toString();
            return var6;
        } catch (Exception var16) {
            var16.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException var15) {
                    var15.printStackTrace();
                }
            }

        }

        return "";
    }
}

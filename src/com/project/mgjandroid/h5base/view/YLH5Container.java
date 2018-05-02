package com.project.mgjandroid.h5base.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.project.mgjandroid.h5base.jsbridge.DefaultHandler;
import com.project.mgjandroid.h5base.jsbridge.JsBridgeCallBack;
import com.project.mgjandroid.h5base.jsbridge.JsBridgeHandler;
import com.project.mgjandroid.h5base.jsbridge.JsBridgeMessage;
import com.project.mgjandroid.h5base.jsbridge.JsBridgeUtils;
import com.project.mgjandroid.h5base.jsbridge.JsWebViewClient;
import com.project.mgjandroid.h5base.jsbridge.WebPageLoadingListener;
import com.project.mgjandroid.h5base.jsbridge.WebViewJsBridge;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：mgjandroid
 * 类描述：
 * 创建人：Mr_Lei
 * 创建时间：2018/4/17 11:57
 */
public class YLH5Container extends WebView implements WebViewJsBridge, WebPageLoadingListener {
    private Context mContext;
    private Map<String, JsBridgeCallBack> callbacks = new HashMap();
    private Map<String, JsBridgeHandler> messageHandlers = new HashMap();
    private JsBridgeHandler defaultHandler = new DefaultHandler();
    private List<JsBridgeMessage> initMessages = new ArrayList();
    private long uniqueId = 0L;
    private WebPageLoadingListener webPageLoadingListener = null;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable = new Runnable() {
        public void run() {
            if (((YLH5CBaseActivity) YLH5Container.this.mContext).getProgressBar() != null) {
                ((YLH5CBaseActivity) YLH5Container.this.mContext).getProgressBar().setVisibility(View.GONE);
            }

        }
    };

    public YLH5Container(Context arg0) {
        super(arg0);
        this.mContext = arg0;
        this.setBackgroundColor(Color.parseColor("#f5f5f9"));
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    public YLH5Container(Context arg0, AttributeSet arg1, boolean useCache) {
        super(arg0, arg1);
        this.mContext = arg0;
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setDefaultTextEncodingName("utf-8");
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(false);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setBlockNetworkImage(false);
        webSetting.setAppCacheEnabled(useCache);
        webSetting.setAppCacheMaxSize(9223372036854775807L);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setPluginState(WebSettings.PluginState.ON);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(2);
        if (Build.VERSION.SDK_INT >= 21) {
            webSetting.setMixedContentMode(0);
        }

        this.getView().setClickable(true);
        this.getView().setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        this.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent("android.intent.action.VIEW", uri);
                YLH5Container.this.getContext().startActivity(intent);
            }
        });
        this.setWebViewClient(new JsWebViewClient(this));
        this.setWebChromeClient(new WebChromeClient() {
            View myVideoView;
            View myNormalView;
            IX5WebChromeClient.CustomViewCallback callback;

            public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {
                FrameLayout normalView = YLH5Container.this;
                ViewGroup viewGroup = (ViewGroup) normalView.getParent();
                viewGroup.removeView(normalView);
                viewGroup.addView(view);
                this.myVideoView = view;
                this.myNormalView = normalView;
                this.callback = customViewCallback;
            }

            public void onHideCustomView() {
                if (this.callback != null) {
                    this.callback.onCustomViewHidden();
                    this.callback = null;
                }

                if (this.myVideoView != null) {
                    ViewGroup viewGroup = (ViewGroup) this.myVideoView.getParent();
                    viewGroup.removeView(this.myVideoView);
                    viewGroup.addView(this.myNormalView);
                }

            }

            public void onProgressChanged(WebView webview, int progress) {
                if (((YLH5CBaseActivity) YLH5Container.this.mContext).getProgressBar() != null) {
                    YLH5Container.this.setProgress(progress);
                } else {
                    super.onProgressChanged(webview, progress);
                }

            }

            public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String captureType) {
            }

            public void onShowCustomView(View arg0, int arg1, IX5WebChromeClient.CustomViewCallback arg2) {
                super.onShowCustomView(arg0, arg1, arg2);
            }

            public boolean onCreateWindow(WebView arg0, boolean arg1, boolean arg2, Message msg) {
                WebViewTransport webViewTransport = (WebViewTransport) msg.obj;
                WebView webView = new WebView(YLH5Container.this.getContext()) {
                    protected void onDraw(Canvas canvas) {
                        super.onDraw(canvas);
                        Paint paint = new Paint();
                        paint.setColor(-16711936);
                        paint.setTextSize(15.0F);
                        canvas.drawText("新建窗口", 10.0F, 10.0F, paint);
                    }
                };
                webView.setWebViewClient(new WebViewClient() {
                    public boolean shouldOverrideUrlLoading(WebView arg0, String arg1) {
                        arg0.loadUrl(arg1);
                        return true;
                    }
                });
                LayoutParams lp = new LayoutParams(-1, -1);
                YLH5Container.this.addView(webView, lp);
                webViewTransport.setWebView(webView);
                msg.sendToTarget();
                return true;
            }

            public boolean onJsAlert(WebView webView, String url, String message, JsResult jsResult) {
                if (message != null) {
                    //捕获网页中弹框信息
                    Toast.makeText(YLH5Container.this.getContext(), message, Toast.LENGTH_SHORT).show();
                }
                jsResult.cancel();   //
                return true;       //表示确认进行捕获
                //return super.onJsAlert(webView, url, message, jsResult);
            }

            public boolean onJsPrompt(WebView webView, String s, String message, String s2, JsPromptResult jsPromptResult) {
                return super.onJsPrompt(webView, s, message, s2, jsPromptResult);
            }

            public void onReceivedTitle(WebView arg0, String title) {
                super.onReceivedTitle(arg0, title);
                if (!TextUtils.isEmpty(title) && ((YLH5CBaseActivity) YLH5Container.this.mContext).getTitleView() != null) {
                    ((YLH5CBaseActivity) YLH5Container.this.mContext).getTitleView().setText(title);
                }

            }
        });
    }

    private void setProgress(int progress) {
        if (((YLH5CBaseActivity) this.mContext).getProgressBar() != null) {
            ((YLH5CBaseActivity) this.mContext).getProgressBar().setProgress(progress);
            if (progress == 100) {
                this.handler.postDelayed(this.runnable, 50L);
            } else if (((YLH5CBaseActivity) this.mContext).getProgressBar().getVisibility() != View.VISIBLE) {
                ((YLH5CBaseActivity) this.mContext).getProgressBar().setVisibility(View.VISIBLE);
            }
        }

    }

    public void setDefaultHandler(JsBridgeHandler handler) {
        this.defaultHandler = handler;
    }

    public void handlerReturnData(String url) {
        String funcName = JsBridgeUtils.getFuncFromUrl(url);
        JsBridgeCallBack func = (JsBridgeCallBack) this.callbacks.get(funcName);
        String data = JsBridgeUtils.getDataFromUrl(url);
        if (func != null) {
            func.onCallBack(data);
            this.callbacks.remove(funcName);
        }

    }

    public void sendMessage(String jsHandlerName, String data) {
        this.sendMessage(jsHandlerName, data, (JsBridgeCallBack) null);
    }

    public void sendMessage(String jsHandlerName, String data, JsBridgeCallBack callBack) {
        this.doSendMessage(jsHandlerName, data, callBack);
    }

    public void sendEvent(String eventName) {
        String javascriptCommand = String.format("javascript:YLJsBridge._sendEventFromNative('%s');", eventName);
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            this.loadUrl(javascriptCommand);
        }

    }

    private void doSendMessage(String handlerName, String data, JsBridgeCallBack callBack) {
        JsBridgeMessage m = new JsBridgeMessage();
        if (!TextUtils.isEmpty(data)) {
            m.setData(data);
        }

        if (callBack != null) {
            String callbackId = String.format("JAVA_CB_%s", ++this.uniqueId + "_" + SystemClock.currentThreadTimeMillis());
            this.callbacks.put(callbackId, callBack);
            m.setCallbackId(callbackId);
        }

        if (!TextUtils.isEmpty(handlerName)) {
            m.setHandlerName(handlerName);
        }

        this.queueMessage(m);
    }

    private void queueMessage(JsBridgeMessage m) {
        if (this.initMessages != null) {
            this.initMessages.add(m);
        } else {
            this.dispatchMessage(m);
        }

    }

    public void dispatchMessage(JsBridgeMessage m) {
        String messageJson = m.toJson();
        messageJson = messageJson.replaceAll("(\\\\)([^utrn])", "\\\\\\\\$1$2");
        messageJson = messageJson.replaceAll("(?<=[^\\\\])(\")", "\\\\\"");
        String javascriptCommand = String.format("javascript:YLJsBridge._handleMsgFromNative('%s');", messageJson);
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            this.loadUrl(javascriptCommand);
        }

    }

    public void flushMessageQueue() {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            this.loadUrl("javascript:YLJsBridge._fetchQueue();", new JsBridgeCallBack() {
                public void onCallBack(String data) {
                    List list = null;

                    try {
                        list = JsBridgeMessage.toArrayList(data);
                    } catch (Exception var9) {
                        var9.printStackTrace();
                        return;
                    }

                    if (list != null && list.size() != 0) {
                        for (int i = 0; i < list.size(); ++i) {
                            JsBridgeMessage m = (JsBridgeMessage) list.get(i);
                            String responseId = m.getResponseId();
                            JsBridgeCallBack callBack;
                            final String callbackId;
                            if (!TextUtils.isEmpty(responseId)) {
                                callBack = (JsBridgeCallBack) YLH5Container.this.callbacks.get(responseId);
                                callbackId = m.getResult();
                                callBack.onCallBack(callbackId);
                                YLH5Container.this.callbacks.remove(responseId);
                            } else {
                                callBack = null;
                                callbackId = m.getCallbackId();
                                if (!TextUtils.isEmpty(callbackId)) {
                                    callBack = new JsBridgeCallBack() {
                                        public void onCallBack(String result) {
                                            JsBridgeMessage responseMsg = new JsBridgeMessage();
                                            responseMsg.setResponseId(callbackId);
                                            responseMsg.setResult(result);
                                            YLH5Container.this.queueMessage(responseMsg);
                                        }
                                    };
                                } else {
                                    callBack = new JsBridgeCallBack() {
                                        public void onCallBack(String result) {
                                        }
                                    };
                                }

                                JsBridgeHandler handler = YLH5Container.this.defaultHandler;
                                if (!TextUtils.isEmpty(m.getHandlerName()) && YLH5Container.this.messageHandlers.containsKey(m.getHandlerName())) {
                                    handler = (JsBridgeHandler) YLH5Container.this.messageHandlers.get(m.getHandlerName());
                                }

                                if (handler != null) {
                                    handler.handler(m.getData(), callBack);
                                }
                            }
                        }

                    }
                }
            });
        }

    }

    public void loadUrl(String jsUrl, JsBridgeCallBack returnCallback) {
        this.loadUrl(jsUrl);
        this.callbacks.put(JsBridgeUtils.parseFuncName(jsUrl), returnCallback);
    }

    public void registerNativeHandler(String handlerName, JsBridgeHandler handler) {
        if (handler != null) {
            this.messageHandlers.put(handlerName, handler);
        }

    }

    public void callJsHandler(String handlerName, String data, JsBridgeCallBack callBack) {
        this.doSendMessage(handlerName, data, callBack);
    }

    public List<JsBridgeMessage> getInitMessages() {
        return this.initMessages;
    }

    public void setInitMessages(List<JsBridgeMessage> initMessages) {
        this.initMessages = initMessages;
    }

    public void setWebPageLoadingListener(WebPageLoadingListener webPageLoadingListener) {
        this.webPageLoadingListener = webPageLoadingListener;
    }

    public void onWebPageStarted(WebView webView, String url, Bitmap favicon) {
        if (this.webPageLoadingListener != null) {
            this.webPageLoadingListener.onWebPageStarted(webView, url, favicon);
        }

    }

    public void onWebPageFinished(WebView webView, String url) {
        if (this.webPageLoadingListener != null) {
            this.webPageLoadingListener.onWebPageFinished(webView, url);
        }

    }
}

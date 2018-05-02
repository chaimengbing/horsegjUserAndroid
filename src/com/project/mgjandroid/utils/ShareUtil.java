package com.project.mgjandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by yuandi on 2017/3/10.
 */

public class ShareUtil {

    private Activity activity;
    private String title;
    private String summary;
    private String tagUrl;
    private String imgUrls;
    private boolean isH5;

    private PopupWindow mPopupWindow;
    private BaseUiListener uiListener;
    private OnClickListener listener;

    public ShareUtil(@NonNull Activity activity, String shareTitle, String shareSummary, String shareTagUrl, String shareImgUrls) {
        this.activity = activity;
        title = shareTitle;
        summary = shareSummary;
        if (!TextUtils.isEmpty(shareTagUrl)) {
            if ("com.horsegj.company".equals(App.getInstance().getPackageName())) {
                tagUrl = shareTagUrl + "&scheme=mgjofficial";
            } else {
                tagUrl = shareTagUrl + "&scheme=mgjqyue";
            }
        }
        imgUrls = shareImgUrls;
        init();
    }

    public ShareUtil(@NonNull Activity activity, String shareTitle, String shareSummary, String shareTagUrl, String shareImgUrls, boolean isH5) {
        this.activity = activity;
        this.isH5 = isH5;
        title = shareTitle;
        summary = shareSummary;
        tagUrl = shareTagUrl + "&scheme=mgjofficial";
        imgUrls = shareImgUrls;
        init();
    }

    private void init() {
        uiListener = new BaseUiListener(activity);

        listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.second_hand_qq:
                        hidePopup();
                        shareToQQ(activity, uiListener, title, summary, tagUrl, getImgUrl(imgUrls));
                        break;
                    case R.id.second_hand_wechat:
                        hidePopup();
                        if (isH5) {
                            shareToWechatInH5(activity, 0, title, summary, tagUrl, imgUrls);
                        } else {
                            shareToWechat(activity, 0, title, summary, tagUrl, getImgUrl(imgUrls));
                        }
                        break;
                    case R.id.second_hand_friend:
                        hidePopup();
                        if (isH5) {
                            shareToWechatInH5(activity, 1, title, summary, tagUrl, imgUrls);
                        } else {
                            shareToWechat(activity, 1, title, summary, tagUrl, getImgUrl(imgUrls));
                        }
                        break;
                    case R.id.outside:
                    case R.id.second_hand_cancel:
                        hidePopup();
                        break;
                }
            }
        };
    }

    public void showPopupWindow() {
        if (mPopupWindow == null) {
            initPopup();
        }
        if (!mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
    }

    public void hidePopup() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    public boolean isShowing() {
        return mPopupWindow != null && mPopupWindow.isShowing();
    }

    private void initPopup() {
        View view = LayoutInflater.from(activity).inflate(R.layout.popup_second_hand_share, null);
        View outside = view.findViewById(R.id.outside);
        LinearLayout llQQ = (LinearLayout) view.findViewById(R.id.second_hand_qq);
        LinearLayout llWechat = (LinearLayout) view.findViewById(R.id.second_hand_wechat);
        LinearLayout llFriend = (LinearLayout) view.findViewById(R.id.second_hand_friend);
        TextView tvCancel = (TextView) view.findViewById(R.id.second_hand_cancel);
        outside.setOnClickListener(listener);
        tvCancel.setOnClickListener(listener);
        llQQ.setOnClickListener(listener);
        llWechat.setOnClickListener(listener);
        llFriend.setOnClickListener(listener);

        mPopupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
    }

    /**
     * param flag 0 好友，1 朋友圈
     */
    public static void shareToWechat(Context context, final int flag, String title, String summary, String tagUrl, String imgUrl) {
        if (!App.getApi().isWXAppInstalled()) {
            ToastUtils.displayMsg("请先安装微信客户端", context);
            return;
        }

        WXWebpageObject webpage = new WXWebpageObject(tagUrl);
        final WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = summary;
        if (CheckUtils.isNoEmptyStr(imgUrl)) {
            Glide.with(context).load(imgUrl + "?imageView2/2/w/150/h/150/interlace/1").asBitmap().toBytes().into(new SimpleTarget<byte[]>(150, 150) {
                @Override
                public void onResourceReady(byte[] data, GlideAnimation anim) {
                    msg.thumbData = data;
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = String.valueOf(System.currentTimeMillis());
                    req.message = msg;
                    req.scene = flag;
                    App.getApi().sendReq(req);
                }
            });
        } else {
            Glide.with(context).load(R.drawable.about_icon).asBitmap().toBytes().into(new SimpleTarget<byte[]>(150, 150) {
                @Override
                public void onResourceReady(byte[] data, GlideAnimation anim) {
                    msg.thumbData = data;
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = String.valueOf(System.currentTimeMillis());
                    req.message = msg;
                    req.scene = flag;
                    App.getApi().sendReq(req);
                }
            });
        }
    }

    /**
     * param flag 0 好友，1 朋友圈
     */
    public static void shareToWechatInH5(final Context context, final int flag, String title, String summary, String tagUrl, String imgUrl) {
        if (!App.getApi().isWXAppInstalled()) {
            ToastUtils.displayMsg("请先安装微信客户端", context);
            return;
        }

        WXWebpageObject webpage = new WXWebpageObject(tagUrl);
        final WXMediaMessage msg = new WXMediaMessage(webpage);

        msg.title = title;
        msg.description = summary;
        if (CheckUtils.isNoEmptyStr(imgUrl)) {
            Glide.with(context).load(imgUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    Bitmap thumb = resource;
                    if (thumb == null) {
                        thumb = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.about_icon);
                    } else if (ImageUtils.getBmpSize(thumb) > 32 * 1024) { //weixin要求图片小于32K
                        thumb = ImageUtils.compressImg(thumb, 32);
                    }
                    msg.setThumbImage(thumb);
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = String.valueOf(System.currentTimeMillis());
                    req.message = msg;
                    req.scene = flag;
                    App.getApi().sendReq(req);
                }
            });
        } else {
            Glide.with(context).load(R.drawable.about_icon).asBitmap().toBytes().into(new SimpleTarget<byte[]>(150, 150) {
                @Override
                public void onResourceReady(byte[] data, GlideAnimation anim) {
                    msg.thumbData = data;
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = String.valueOf(System.currentTimeMillis());
                    req.message = msg;
                    req.scene = flag;
                    App.getApi().sendReq(req);
                }
            });
        }
    }

    public static void shareToQQ(Activity context, BaseUiListener uiListener, String title, String summary, String tagUrl, String imgUrl) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, tagUrl);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, context.getString(R.string.app_name));
        App.getmTencent().shareToQQ(context, params, uiListener);
    }

    public static void shareToQzone(Activity context, BaseUiListener uiListener, String title, String summary, String tagUrl, String imgUrl) {
        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, tagUrl);
        params.putString(QzoneShare.SHARE_TO_QQ_APP_NAME, context.getString(R.string.app_name));
        if (CheckUtils.isNoEmptyStr(imgUrl)) {
            String[] strings = imgUrl.split(";");
            ArrayList<String> imgUrls = new ArrayList<>();
            imgUrls.addAll(Arrays.asList(strings));
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrls);
        }
        App.getmTencent().shareToQzone(context, params, uiListener);
    }

    private String getImgUrl(String urls) {
        String imgUrl = "";
        if (!TextUtils.isEmpty(urls)) {
            imgUrl = urls.split(";")[0];
        }
        return imgUrl;
    }

    /**
     * 如果要监听回调，一定要在onActivityResult方法中调用此方法
     */
    public void onActivityResultData(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_QQ_SHARE || requestCode == Constants.REQUEST_QZONE_SHARE) {
            Tencent.onActivityResultData(requestCode, resultCode, data, uiListener);
        }
    }

    /**
     * QQ分享回调 (别忘了在onActivityResult中调用)
     */
    public static class BaseUiListener implements IUiListener {

        private Context context;

        public BaseUiListener(Context context) {
            this.context = context;
        }

        @Override
        public void onComplete(Object response) {
            MLog.i("onComplete:" + response.toString());
            ToastUtils.displayMsg("分享成功", context);
        }

        @Override
        public void onError(UiError e) {
            MLog.i("onError:" + "code:" + e.errorCode + ", msg:"
                    + e.errorMessage + ", detail:" + e.errorDetail);
            ToastUtils.displayMsg("分享失败", context);
        }

        @Override
        public void onCancel() {
            MLog.i("onCancel");
//            ToastUtils.displayMsg("分享已取消", context);
        }
    }
}

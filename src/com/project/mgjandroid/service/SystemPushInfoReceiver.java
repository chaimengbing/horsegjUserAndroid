package com.project.mgjandroid.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.project.mgjandroid.base.App;
import com.project.mgjandroid.h5container.YLBSdkConstants;
import com.project.mgjandroid.h5container.view.YLBWebViewActivity;
import com.project.mgjandroid.ui.activity.HomeActivity;
import com.project.mgjandroid.ui.activity.OrderRefundInfoActivity;
import com.project.mgjandroid.ui.activity.invitingfriends.InvitingFriendsActivity;
import com.project.mgjandroid.utils.PreferenceUtils;

import org.json.JSONException;

import cn.jpush.android.api.JPushInterface;

public class SystemPushInfoReceiver extends BroadcastReceiver {

    private Intent voiceService;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String extra;
        int notificationId;
        if (bundle == null) {
            return;
        }
        extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {//点击事件
            try {
                org.json.JSONObject object = new org.json.JSONObject(extra);
                String type = object.optString("type");
                if (TextUtils.isEmpty(type)) {
                    Intent i = new Intent(context, HomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                    JPushInterface.clearNotificationById(context, notificationId);
                    return;
                }
                if ("Cashback".equals(type)) {
                    Intent i = new Intent(context, InvitingFriendsActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                } else if("refundsSuccess".equals(type)){
                    //团购退款
                    String orderId = object.optString("orderId");
                    String groupPurchaseOrderCouponCodeId = object.optString("groupPurchaseOrderCouponCodeId");
                    Intent i = new Intent(context, OrderRefundInfoActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("orderId",orderId);
                    i.putExtra("groupPurchaseOrderCouponCodeId",groupPurchaseOrderCouponCodeId);
                    context.startActivity(i);
                }else if ("Hitchhiking".equals(type) || "VisualAgriculture".equals(type)) {
                    // 顺风车/可视农场
                    String url = object.optString("url");
                    if (url.replace("maguanjia://", "").startsWith("http")) {
                        Intent i = new Intent(context, YLBWebViewActivity.class);
                        i.putExtra(YLBSdkConstants.EXTRA_H5_URL, url.replace("maguanjia://", ""));
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        context.startActivity(i);
                    } else {
                        Intent i = new Intent(context, HomeActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(i);
                    }
                } else {
                    Intent i = new Intent(context, HomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                }
                JPushInterface.clearNotificationById(context, notificationId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) { //接受到推送下来的通知
            try {
                org.json.JSONObject jsonObject = new org.json.JSONObject(extra);
                String type = jsonObject.optString("type");
                String userId = jsonObject.optString("userId");
                if (type != null && "Cashback".equals(type)) {
                    //人人分利
                    if (App.isLogin() && PreferenceUtils.getPushSwitch(context) && userId != null && userId.equals(App.getUserInfo().getId() + "")) {
                        //开启声音
                        voiceService = new Intent(context, VoiceService.class);
                        context.startService(voiceService);
                    } else {
                        JPushInterface.clearNotificationById(context, notificationId);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


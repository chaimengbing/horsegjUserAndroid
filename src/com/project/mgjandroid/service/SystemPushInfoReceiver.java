package com.project.mgjandroid.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.project.mgjandroid.base.App;
import com.project.mgjandroid.constants.AgentRequestType;
import com.project.mgjandroid.h5container.YLBSdkConstants;
import com.project.mgjandroid.h5container.view.YLBWebViewActivity;
import com.project.mgjandroid.ui.activity.ConfirmOrderActivity;
import com.project.mgjandroid.ui.activity.HomeActivity;
import com.project.mgjandroid.ui.activity.OrderDetailActivity;
import com.project.mgjandroid.ui.activity.OrderRefundInfoActivity;
import com.project.mgjandroid.ui.activity.carhailing.CarHailingOrderDetailActivity;
import com.project.mgjandroid.ui.activity.groupbuying.GroupBuyingOrderForGoodsDetailsActivity;
import com.project.mgjandroid.ui.activity.invitingfriends.InvitingFriendsActivity;
import com.project.mgjandroid.ui.activity.legwork.LegworkOrderdetailsActivity;
import com.project.mgjandroid.ui.activity.pintuan.MyGroupPurchaseDetailActivity;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
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
                    goHome(context);
                    JPushInterface.clearNotificationById(context, notificationId);
                    return;
                }
                // 顺风车/可视农场
                String url = object.optString("url");
                if ("Cashback".equals(type)) {
                    Intent i = new Intent(context, InvitingFriendsActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                } else if ("refundsSuccess".equals(type)) {
                    //团购退款
                    String orderId = object.optString("orderId");
                    String groupPurchaseOrderCouponCodeId = object.optString("groupPurchaseOrderCouponCodeId");
                    Intent i = new Intent(context, OrderRefundInfoActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("orderId", orderId);
                    i.putExtra("groupPurchaseOrderCouponCodeId", groupPurchaseOrderCouponCodeId);
                    context.startActivity(i);
                } else if (CheckUtils.isNoEmptyStr(url)) {
                    //"Hitchhiking".equals(type) || "VisualAgriculture".equals(type)LegWork(9,"跑腿"),Express(10,"快递"),Laundry(11,"洗衣")
                    if (url.replace("maguanjia://", "").startsWith("http")) {
                        Intent i = new Intent(context, YLBWebViewActivity.class);
                        i.putExtra(YLBSdkConstants.EXTRA_H5_URL, url.replace("maguanjia://", ""));
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        context.startActivity(i);
                    } else {
                        goHome(context);
                    }
                } else if ("CancelTOrder".equals(type) || "OrderDone".equals(type) || "CancelOrder".equals(type)) {
                    String orderId = object.optString("orderId");
                    String orderType = object.optString("orderType");
                    goDetail(context, orderId, orderType);
                } else {
                    goHome(context);
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
                if (CheckUtils.isNoEmptyStr(type)) {
                    if ("Cashback".equals(type)) {
                        //人人分利
                        if (App.isLogin() && PreferenceUtils.getPushSwitch(context) && userId != null && userId.equals(App.getUserInfo().getId() + "")) {
                            //开启声音
                            voiceService = new Intent(context, VoiceService.class);
                            context.startService(voiceService);
                        } else {
                            JPushInterface.clearNotificationById(context, notificationId);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void goDetail(Context context, String orderId, String orderType) {
        //取消订单  订单完成
        if (CheckUtils.isNoEmptyStr(orderType)) {
            Intent i = null;
            int typeOrder = Integer.parseInt(orderType);
            if (typeOrder == AgentRequestType.LegWork.getValue()) {
                i = new Intent(context, LegworkOrderdetailsActivity.class);
            } else if (typeOrder == AgentRequestType.Default.getValue() || typeOrder == AgentRequestType.Takeaway.getValue() || typeOrder == AgentRequestType.Shop.getValue()) {//外卖商超
                i = new Intent(context, OrderDetailActivity.class);
            } else if (typeOrder == AgentRequestType.Groupbuy.getValue()) {//拼团
                i = new Intent(context, MyGroupPurchaseDetailActivity.class);
            } else if (typeOrder == AgentRequestType.GroupPurchase.getValue()) {//团购
                i = new Intent(context, GroupBuyingOrderForGoodsDetailsActivity.class);
            } else if (typeOrder == AgentRequestType.Car.getValue()) {//约车
                i = new Intent(context, CarHailingOrderDetailActivity.class);
            }
            if (i != null) {
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("orderId", orderId);
                context.startActivity(i);
            } else {
                goHome(context);
            }
        } else {
            goHome(context);
        }
    }

    private void goHome(Context context) {
        Intent i = new Intent(context, HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(i);
    }
}


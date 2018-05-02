package com.project.mgjandroid.service;

import android.content.Context;
import android.util.Log;

import java.util.HashSet;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class JPushTagReceiver extends JPushMessageReceiver {
    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {

        if (!jPushMessage.getTagCheckStateResult()) {
            Log.d("TAG", "onCheckTagOperatorResult: 未绑定");
            HashSet<String> strings = new HashSet<>();
            strings.add(jPushMessage.getCheckTag());
            JPushInterface.setTags(context, 201, strings);
        } else {
            Log.d("TAG", "onCheckTagOperatorResult: 已绑定");
        }

        super.onCheckTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        if (jPushMessage.getErrorCode() == 0) {
            for (String tag : jPushMessage.getTags()) {
                Log.d("TAG", "gotResult: 验证登录设置极光标签  " + tag);
            }
        } else {
            Log.d("TAG", "gotResult: 错误码" + jPushMessage.getErrorCode());
        }
        super.onTagOperatorResult(context, jPushMessage);
    }
}

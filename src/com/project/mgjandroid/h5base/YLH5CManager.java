package com.project.mgjandroid.h5base;

import android.content.Context;

import com.project.mgjandroid.h5base.utils.YLLogUtils;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.QbSdk.PreInitCallback;

/**
 * 项目名称：mgjandroid
 * 类描述：
 * 创建人：Mr_Lei
 * 创建时间：2018/4/17 11:54
 */
public class YLH5CManager {
    private static boolean debugMode = false;

    public YLH5CManager() {
    }

    public static void init(Context context) {
        PreInitCallback cb = new PreInitCallback() {
            public void onViewInitFinished(boolean arg0) {
                YLLogUtils.d("QbSdk", " onViewInitFinished is " + arg0);
            }

            public void onCoreInitFinished() {
                YLLogUtils.d("QbSdk", " onCoreInitFinished");
            }
        };
        QbSdk.initX5Environment(context.getApplicationContext(), cb);
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static void setDebugMode(boolean debugMode) {
        debugMode = debugMode;
    }
}

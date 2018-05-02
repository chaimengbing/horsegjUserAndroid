package com.project.mgjandroid.h5base.utils;

import android.util.Log;

import com.project.mgjandroid.h5base.YLH5CManager;


/**
 * 项目名称：mgjandroid
 * 类描述：
 * 创建人：Mr_Lei
 * 创建时间：2018/4/17 11:59
 */
public class YLLogUtils {
    public YLLogUtils() {
    }

    public static void d(String tag, String msg) {
        if (YLH5CManager.isDebugMode()) {
            Log.d(tag, msg);
        }

    }

    public static void i(String tag, String msg) {
        if (YLH5CManager.isDebugMode()) {
            Log.i(tag, msg);
        }

    }

    public static void w(String tag, String msg) {
        if (YLH5CManager.isDebugMode()) {
            Log.w(tag, msg);
        }

    }

    public static void e(String tag, String msg) {
        if (YLH5CManager.isDebugMode()) {
            Log.e(tag, msg);
        }

    }
}

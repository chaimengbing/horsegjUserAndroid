package com.project.mgjandroid.h5base.utils;

import android.content.Context;
import android.content.res.Resources;

import com.project.mgjandroid.R;

/**
 * 项目名称：mgjuser
 * 类描述：
 * 创建人：Mr_Lei
 * 创建时间：2018/5/24 10:29
 */
public class ADFilterTool {

    /**
     * 屏蔽广告的NoAdWebViewClient类
     *
     * @param context
     * @param url
     * @return true 为广告链接，false 为正常连接
     */
    public static boolean hasAd(Context context, String url) {
        Resources res = context.getResources();
        String[] adUrls = res.getStringArray(R.array.adBlockUrl);
        for (String adUrl : adUrls) {
            if (url.contains(adUrl)) {
                return true;
            }
        }
        return false;
    }
}

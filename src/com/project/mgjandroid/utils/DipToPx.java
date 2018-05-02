package com.project.mgjandroid.utils;

import android.content.Context;

public class DipToPx {

    /**
     * dip转成px
     *
     * @param context
     * @param dipValue 要转换成px单位的dp单位数据
     * @return int 返回类型 px值
     * @Title: dip2px
     * @Description dip转成px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param fontScale （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}

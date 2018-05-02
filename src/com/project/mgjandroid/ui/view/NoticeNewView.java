package com.project.mgjandroid.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.project.mgjandroid.R;

import java.util.List;

/**
 * Created by MrLei on 2017/9/7.
 */

public class NoticeNewView extends ViewFlipper {

    private Context mContext;
    private boolean isSetAnimDuration = false;
    private int interval = 3000;
    /**
     * 动画时间
     */
    private int animDuration = 800;


//    android:autoStart：设置自动加载下一个View
//    android:flipInterval：设置View之间切换的时间间隔
//    android:inAnimation：设置切换View的进入动画
//    android:outAnimation：设置切换View的退出动画
//
//    isFlipping： 判断View切换是否正在进行
//    setFilpInterval：设置View之间切换的时间间隔
//    startFlipping：开始View的切换，而且会循环进行
//    stopFlipping：停止View的切换
//    setOutAnimation：设置切换View的退出动画
//    setInAnimation：设置切换View的进入动画
//    showNext： 显示ViewFlipper里的下一个View
//    showPrevious：显示ViewFlipper里的上一个View


    public NoticeNewView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.mContext = context;
        //设置View之间切换的时间间隔
        setFlipInterval(interval);

        //设置切换View的进入动画
        Animation animIn = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_in);
        if (isSetAnimDuration) animIn.setDuration(animDuration);
        setInAnimation(animIn);

        //设置切换View的退出动画
        Animation animOut = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_out);
        if (isSetAnimDuration) animOut.setDuration(animDuration);
        setOutAnimation(animOut);
    }


    /**
     * 设置循环滚动的View数组
     *
     * @param views
     */
    public void setViews(List<View> views) {
        if (views == null || views.size() == 0) return;
        removeAllViews();
        for (int i = 0; i < views.size(); i++) {
            addView(views.get(i));
        }
        startFlipping();
    }

    /**
     * 停止播放
     */
    public void stop() {
        if (isFlipping()) {
            stopFlipping();
        }
    }

}

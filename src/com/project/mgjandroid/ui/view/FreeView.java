package com.project.mgjandroid.ui.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RadioGroup;

/**
 * 项目名称：mgjandroid
 * 类描述：
 * 创建人：Mr_Lei
 * 创建时间：2018/3/15 15:42
 */

public class FreeView extends RadioGroup {
    private MyCountDownTimer countDownTimer;
    /**
     * 倒计时时间
     */
    private long millisInFuture = 3500;
    /**
     * 倒计时过程中
     * 回调{@link CountDownTimer#onTick(long)}的间隔时间
     */
    private long countDownInterval = 500;
    private int viewWidth;
    private int viewHight;

    public FreeView(Context context) {
        this(context, null);
    }

    public FreeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        viewWidth = getRight() - getLeft();
        viewHight = getBottom() - getTop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                if (!isShow) {
                    showView(true);
                    return false;
                }
                break;
            default:
        }
        return super.dispatchTouchEvent(ev);
    }

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            showView(true);
        }
    }

    public boolean isShow = true;

    /**
     * 开启显示倒计时
     */
    public void startTimer() {
        if (countDownTimer == null) {
            countDownTimer = new MyCountDownTimer(millisInFuture, countDownInterval);
        }
        countDownTimer.cancel();
        countDownTimer.start();
    }

    /**
     * 显示/隐藏
     */
    public void showView(boolean isShow) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (this.isShow == isShow) {
            return;
        }
        //保存当前状态
        this.isShow = isShow;
        if (isShow) {
            // 显示
            // setAlpha(1f);
            ObjectAnimator animator = ObjectAnimator.ofFloat(this, "translationX", 0);
            animator.setDuration(500);
            animator.start();
        } else {
            // 隐藏 清除倒计时
            // setAlpha(toAlpha);
            ObjectAnimator animator = ObjectAnimator.ofFloat(this, "translationX", viewWidth / 2);
            animator.setDuration(500);
            animator.start();
        }
    }
}

package com.project.mgjandroid.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;

/**
 * Created by SunXueLiang on 2017-03-10.
 */

public class RefundDialog extends Dialog implements View.OnClickListener {
    private RefundDialog.onBtnClickListener onBtnClickListener;
    private Context context;

    private String appTitle;
    private String tips;
    private String sure;
    private String cancel;
    private String sureColor;
    private String cancelColor;

    private RelativeLayout re_tip_dialog;


    public RefundDialog(Context context, RefundDialog.onBtnClickListener onBtnClickListener, String appTitle, String tips, String sure, String cancel) {
        super(context);
        this.onBtnClickListener = onBtnClickListener;
        this.context = context;
        this.appTitle = appTitle;
        this.tips = tips;
        this.sure = sure;
        this.cancel = cancel;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.refund_dialog_layout);
        TextView tv_dele_sure = (TextView) findViewById(R.id.sure);
        TextView tv_dele_cancle = (TextView) findViewById(R.id.cancel);
        TextView tv_appTitle = (TextView) findViewById(R.id.appTitle);
        TextView tv_tips = (TextView) findViewById(R.id.tips);
        re_tip_dialog = (RelativeLayout) findViewById(R.id.re_tip_dialog);

        tv_dele_cancle.setOnClickListener(this);
        tv_dele_sure.setOnClickListener(this);

        Window window = this.getWindow();
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        // 去除四角黑色背景
        window.setBackgroundDrawable(new BitmapDrawable());
        // 设置周围的暗色系数
        params.dimAmount = 0.5f;
        WindowManager manager = window.getWindowManager();
        Display display = manager.getDefaultDisplay();
        params.width = (int) (display.getWidth() * 0.8);
        window.setAttributes(params);

        // 为各个textview赋值
        if (!TextUtils.isEmpty(appTitle)) {
            tv_appTitle.setVisibility(View.VISIBLE);
            tv_appTitle.setText(appTitle);
        }
        if (!TextUtils.isEmpty(sureColor)) {
            tv_dele_sure.setTextColor(Color.parseColor(sureColor));
        }
        if (!TextUtils.isEmpty(sure)) {
            tv_dele_sure.setText(cancel);
        }
        if (!TextUtils.isEmpty(cancelColor)) {
            tv_dele_cancle.setTextColor(Color.parseColor(cancelColor));
        }
        if (!TextUtils.isEmpty(cancel)) {
            tv_dele_cancle.setText(sure);
        }
        tv_tips.setText(tips);
    }

    public interface onBtnClickListener {
        void onSure();

        void onExit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                onBtnClickListener.onSure();
//                alertDialogExitAnim();
                break;

            case R.id.sure:
                onBtnClickListener.onExit();
//                alertDialogExitAnim();
                break;

            default:
                break;
        }
    }

    private void alertDialogExitAnim() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f,
                1.0f, 0.0f, Animation.ABSOLUTE,
                re_tip_dialog.getWidth() / 2, Animation.ABSOLUTE,
                re_tip_dialog.getHeight() / 2);
        scaleAnimation.setDuration(1000);
        re_tip_dialog.startAnimation(scaleAnimation);

        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                RefundDialog.this.dismiss();
            }
        });
    }
}

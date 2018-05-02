package com.project.mgjandroid.ui.view.homeredbag;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.utils.StringUtils;

import java.math.BigDecimal;

/**
 * Created by yuandi on 2017/2/24.
 */

public class FirstCallCarDialog extends Dialog implements View.OnClickListener {

    private onBtnClickListener onBtnClickListener;

    private RelativeLayout re_tip_dialog;
    private BigDecimal amt = BigDecimal.ZERO;

    public FirstCallCarDialog(Context context, onBtnClickListener onBtnClickListener, BigDecimal amt) {
        super(context);
        this.onBtnClickListener = onBtnClickListener;
        this.amt = amt;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.first_call_car_dialog_layout);
        re_tip_dialog = (RelativeLayout) findViewById(R.id.root_view);
        ImageView sure = (ImageView) findViewById(R.id.sure);
        ImageView cancel = (ImageView) findViewById(R.id.cancel);
        TextView tv_amt = (TextView) findViewById(R.id.amt);
        tv_amt.setText(StringUtils.BigDecimal2Str(amt));

        cancel.setOnClickListener(this);
        sure.setOnClickListener(this);

        Window window = this.getWindow();
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        // 去除四角黑色背景
        window.setBackgroundDrawable(new BitmapDrawable());
        // 设置周围的暗色系数
        params.dimAmount = 0.7f;
//        params.width = DipToPx.dip2px(getContext(), 320);
        window.setAttributes(params);
    }

    public interface onBtnClickListener {
        void onSure();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                alertDialogExitAnim();
                break;

            case R.id.sure:
//                alertDialogExitAnim();
                onBtnClickListener.onSure();
                FirstCallCarDialog.this.dismiss();
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
        scaleAnimation.setDuration(150);
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
                FirstCallCarDialog.this.dismiss();
            }
        });
    }
}

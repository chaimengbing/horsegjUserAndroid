package com.project.mgjandroid.h5container.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;

public class ProgressDialog extends Dialog {

    private ImageView imageView;

    private boolean bCancelable = true;

    public ProgressDialog(Context context) {
        super(context, R.style.CustomProgressDialog);
    }

    public void setCancelable(boolean b) {
        bCancelable = b;
    }

    private onProcessDialogListener oLsner = null;

    public interface onProcessDialogListener {
        void onCancelled();
    }

    public void setMessage(String sMsg) {
        TextView tvMsg = (TextView) findViewById(R.id.id_tv_loadingmsg);

        if (tvMsg != null) {
            if (!TextUtils.isEmpty(sMsg)) {
                tvMsg.setVisibility(View.VISIBLE);
                tvMsg.setText(sMsg);
            } else {
                tvMsg.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        imageView = (ImageView) findViewById(R.id.loadingImageView);
        if (imageView != null) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "rotation", 0, 360);
            animator.setDuration(1200);
            animator.setRepeatMode(ValueAnimator.RESTART);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setInterpolator(new LinearInterpolator());
            animator.start();
        }
    }

    public void showDialog(String sMsg) {
        showDialog(sMsg, null);
    }

    public void showDialog(String sMsg, onProcessDialogListener lsn) {
        try {
            setContentView(R.layout.layout_custom_progress_dialog);

            Window window = this.getWindow();
            WindowManager.LayoutParams params = this.getWindow().getAttributes();
            // 去除四角黑色背景
            window.setBackgroundDrawable(new BitmapDrawable());
            // 设置周围的暗色系数
            params.dimAmount = 0f;
            params.gravity = Gravity.CENTER;
            window.setAttributes(params);

            setCanceledOnTouchOutside(false);
            setMessage(sMsg);
            oLsner = lsn;
            setCancelable(bCancelable);
            setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface arg0) {
                    if (oLsner != null) {
                        oLsner.onCancelled();
                    }
                    if (imageView != null) {
                        imageView.clearAnimation();
                    }
                    dismiss();
                }
            });
            show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.project.mgjandroid.ui.view.homeredbag;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.RedBag;
import com.project.mgjandroid.utils.DipToPx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuandi on 2017/2/22.
 */

public class RedbagDialog extends Dialog implements View.OnClickListener {

    private onBtnClickListener onBtnClickListener;
    private Activity context;
    private List<RedBag> redBags = new ArrayList<>();

    private FrameLayout redbag_dialog;

    public RedbagDialog(Activity context, onBtnClickListener onBtnClickListener, List<RedBag> redBags) {
        super(context);
        this.onBtnClickListener = onBtnClickListener;
        this.context = context;
        this.redBags = redBags;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.red_bag_dialog_layout);
        ImageView ivSure = (ImageView) findViewById(R.id.sure);
        ImageView ivCancel = (ImageView) findViewById(R.id.cancel);
        ListView listView = (ListView) findViewById(R.id.list_view);
        redbag_dialog = (FrameLayout) findViewById(R.id.redbag_dialog);
        ivSure.setOnClickListener(this);
        ivCancel.setOnClickListener(this);

        initData(listView);

        Window window = this.getWindow();
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        // 去除四角黑色背景
        window.setBackgroundDrawable(new BitmapDrawable());
        // 设置周围的暗色系数
        params.dimAmount = 0.7f;
        params.width = DipToPx.dip2px(context, 250);
        window.setAttributes(params);
    }

    private void initData(ListView listView) {
        HomeRedBagAdapter adapter = new HomeRedBagAdapter(R.layout.red_bag_item, context);
        listView.setAdapter(adapter);
        adapter.setData(redBags);
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
                RedbagDialog.this.dismiss();
                break;
            default:
                break;
        }
    }

    private void alertDialogExitAnim() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f,
                1.0f, 0.0f, Animation.ABSOLUTE,
                redbag_dialog.getWidth() / 2, Animation.ABSOLUTE,
                redbag_dialog.getHeight() / 2);
        scaleAnimation.setDuration(150);
        redbag_dialog.startAnimation(scaleAnimation);

        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                RedbagDialog.this.dismiss();
            }
        });
    }
}


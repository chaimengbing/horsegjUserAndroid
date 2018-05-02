package com.project.mgjandroid.ui.activity.carhailing;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.carhailing.CarHailingTripPlace;
import com.project.mgjandroid.utils.DipToPx;

import java.util.List;

/**
 * Created by User_Cjh on 2016/12/16.
 */
public class MorePlaceWindow {
    private static PopupWindow payPopupWindow;
    private static PopupWindow morePopupWindow;

    public static void showWindow(final Activity context, String place, boolean isGetOn) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_car_take_place, null);
        ImageView ivCancel = (ImageView) view.findViewById(R.id.take_place_close);
        TextView tvTitle = (TextView) view.findViewById(R.id.take_car_on_or_off);
        if (isGetOn) {
            tvTitle.setText("全部上车地点");
        } else {
            tvTitle.setText("全部下车地点");
        }
        LinearLayout llShow = (LinearLayout) view.findViewById(R.id.take_car_place_layout);
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payPopupWindow.dismiss();
            }
        });
        List<CarHailingTripPlace> list = JSONArray.parseArray(place, CarHailingTripPlace.class);
        for (CarHailingTripPlace trip : list) {
            TextView tv = new TextView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.topMargin = DipToPx.dip2px(context, 17);
            tv.setText(trip.getAddress());
            tv.setSingleLine();
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setTextColor(context.getResources().getColor(R.color.color_3));
            tv.setTextSize(16);
            llShow.addView(tv, params);
        }
        payPopupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        payPopupWindow.setBackgroundDrawable(cd);
        payPopupWindow.setOutsideTouchable(true);
        payPopupWindow.setFocusable(true);
        payPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = context.getWindow().getAttributes();
                lp.alpha = 1.0f;
                context.getWindow().setAttributes(lp);
            }
        });
        if (!payPopupWindow.isShowing()) {
            WindowManager.LayoutParams lp = context.getWindow().getAttributes();
            lp.alpha = 0.5f;
            context.getWindow().setAttributes(lp);
            payPopupWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        }
    }

    public static void showMoreWindow(final Activity context, View v, View.OnClickListener listener, boolean canCancel) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_car_hailing_more, null);
        TextView tvCancel = (TextView) view.findViewById(R.id.car_hailing_cancel_order);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.more);
        TextView tvService = (TextView) view.findViewById(R.id.car_hailing_call_service);
        if (canCancel) {
            tvCancel.setVisibility(View.VISIBLE);
            layout.setBackgroundResource(R.drawable.more_operate_bg);
        } else {
            tvCancel.setVisibility(View.GONE);
            layout.setBackgroundResource(R.drawable.more_operate_bg_1);
        }
        tvCancel.setOnClickListener(listener);
        tvService.setOnClickListener(listener);
        morePopupWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        morePopupWindow.setBackgroundDrawable(cd);
        morePopupWindow.setOutsideTouchable(true);
        morePopupWindow.setFocusable(true);
        if (!morePopupWindow.isShowing()) {
            morePopupWindow.showAsDropDown(v, 0,
                    -context.getResources().getDimensionPixelSize(R.dimen.x5));
        }
    }

    public static void hideMoreWindow() {
        if (morePopupWindow != null && morePopupWindow.isShowing()) {
            morePopupWindow.dismiss();
        }
    }

    public static void hideWindow() {
        if (payPopupWindow != null && payPopupWindow.isShowing()) {
            payPopupWindow.dismiss();
        }
    }
}

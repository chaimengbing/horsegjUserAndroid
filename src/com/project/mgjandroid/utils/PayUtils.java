package com.project.mgjandroid.utils;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.information.InformationFreeStandard;
import com.project.mgjandroid.ui.adapter.PayListAdapter;

import java.util.List;


/**
 * Created by User_Cjh on 2016/11/14.
 */
public class PayUtils {

    private static PopupWindow payPopupWindow;

    public static void showWindow(final Activity context, List<InformationFreeStandard> list, AdapterView.OnItemClickListener itemListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.popup_pay, null);
        TextView tvCancel = (TextView) view.findViewById(R.id.popup_pay_cancel);
        ListView lvPay = (ListView) view.findViewById(R.id.popup_pay_list);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payPopupWindow.dismiss();
            }
        });
        PayListAdapter adapter = new PayListAdapter(R.layout.popup_item_pay, context);
        lvPay.setAdapter(adapter);
        adapter.setData(list);
        lvPay.setOnItemClickListener(itemListener);

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
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            payPopupWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
    }

    public static void hideWindow() {
        if (payPopupWindow != null && payPopupWindow.isShowing()) {
            payPopupWindow.dismiss();
            payPopupWindow = null;
        }
    }
}

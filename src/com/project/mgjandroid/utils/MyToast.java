package com.project.mgjandroid.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mgjandroid.R;

/**
 * 自定义Toast
 *
 * @author jian
 */
public class MyToast {
    private static Toast mToast;

    // 显示提示消息
    public static void displayMsg(int resId, Context context) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    public static void displayMsg(String msg, Context context) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    public static void showMyToast(Context context, String msg, int imgResourceId) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_custom_toast, null);
        TextView txt = (TextView) view.findViewById(R.id.txt);
        ImageView img = (ImageView) view.findViewById(R.id.img);
        txt.setText(msg);
        img.setImageResource(imgResourceId);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}

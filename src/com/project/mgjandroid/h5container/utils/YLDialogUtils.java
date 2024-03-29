package com.project.mgjandroid.h5container.utils;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.project.mgjandroid.R;

import org.json.JSONException;
import org.json.JSONObject;

public class YLDialogUtils {

    public static void showDialogWithOneButton(Context context, String title, String msg, String button, final DialogButtonClickListener listener) {
        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(msg)) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false);
        final Dialog dialog = builder.create();
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });
        dialog.show();
        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(msg)) return;
        dialog.getWindow().setContentView(R.layout.layout_ylb_alert_dialog_with_one_button);

        TextView tv_msg = (TextView) dialog.getWindow().findViewById(R.id.alert_msg);
        TextView tv_title = (TextView) dialog.getWindow().findViewById(R.id.alert_title);

        if (TextUtils.isEmpty(title)) {
            tv_title.setText(msg);
            tv_msg.setVisibility(View.GONE);
        } else {
            tv_title.setText(title);
            tv_msg.setText(msg);
            tv_msg.setVisibility(View.VISIBLE);
        }

        TextView tv_confirm = (TextView) dialog.getWindow().findViewById(R.id.tv_confirm);
        if (!TextUtils.isEmpty(button)) tv_confirm.setText(button);

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (listener != null) {
                    JSONObject object = new JSONObject();
                    try {
                        object.put("code", 0);
                        object.put("value", "按钮点击");
                        listener.onButtonClick(object.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void showDialogWithTwoButton(Context context, String title, String msg, String confirm, String cancel, final DialogButtonClickListener listener) {
        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(msg)) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false);
        final Dialog dialog = builder.create();
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });
        dialog.show();
        dialog.getWindow().setContentView(R.layout.layout_ylb_alert_dialog_with_two_button);

        TextView tv_msg = (TextView) dialog.getWindow().findViewById(R.id.alert_msg);
        TextView tv_title = (TextView) dialog.getWindow().findViewById(R.id.alert_title);

        if (TextUtils.isEmpty(title)) {
            tv_title.setText(msg);
            tv_msg.setVisibility(View.GONE);
        } else {
            tv_title.setText(title);
            tv_msg.setText(msg);
            tv_msg.setVisibility(View.VISIBLE);
        }

        TextView tv_cancel = (TextView) dialog.getWindow().findViewById(R.id.tv_cancel);
        if (!TextUtils.isEmpty(cancel)) tv_cancel.setText(cancel);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (listener != null) {
                    JSONObject object = new JSONObject();
                    try {
                        object.put("code", 0);
                        object.put("value", "取消操作");
                        listener.onButtonClick(object.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        TextView tv_confirm = (TextView) dialog.getWindow().findViewById(R.id.tv_confirm);
        if (!TextUtils.isEmpty(confirm)) tv_confirm.setText(confirm);

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (listener != null) {
                    JSONObject object = new JSONObject();
                    try {
                        object.put("code", 0);
                        object.put("value", "确认操作");
                        listener.onButtonClick(object.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void showDialogCallPhone(final Context context, String title, final String msg, String confirm, String cancel, final DialogButtonClickListener listener) {
        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(msg)) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false);
        final Dialog dialog = builder.create();
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });
        dialog.show();
        dialog.getWindow().setContentView(R.layout.layout_ylb_alert_dialog_with_two_button);

        TextView tv_msg = (TextView) dialog.getWindow().findViewById(R.id.alert_msg);
        TextView tv_title = (TextView) dialog.getWindow().findViewById(R.id.alert_title);

        if (TextUtils.isEmpty(title)) {
            tv_title.setText(msg);
            tv_msg.setVisibility(View.GONE);
        } else {
            tv_title.setText(title);
            tv_msg.setText(msg);
            tv_msg.setVisibility(View.VISIBLE);
        }

        TextView tv_cancel = (TextView) dialog.getWindow().findViewById(R.id.tv_cancel);
        if (!TextUtils.isEmpty(cancel)) tv_cancel.setText(cancel);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (listener != null) {
                    JSONObject object = new JSONObject();
                    try {
                        object.put("code", 0);
                        object.put("value", "取消拨打");
                        listener.onButtonClick(object.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        TextView tv_confirm = (TextView) dialog.getWindow().findViewById(R.id.tv_confirm);
        if (!TextUtils.isEmpty(confirm)) tv_confirm.setText(confirm);

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + msg));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                if (listener != null) {
                    JSONObject object = new JSONObject();
                    try {
                        object.put("code", 0);
                        object.put("value", "点击拨打");
                        listener.onButtonClick(object.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public interface DialogButtonClickListener {
        void onButtonClick(String var);
    }

}

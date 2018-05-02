package com.project.mgjandroid.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.sql.Timestamp;

/**
 * Created by yuandi on 2016/3/28.
 */
public class GroupTimeTextView extends TextView {

    private long Time;
    private boolean run = true;

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeMessages(0);
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (run) {
                        long mTime = Time;
                        if (mTime > 1) {
                            GroupTimeTextView.this.setText(getTimeString(mTime / 1000));
                            handler.sendEmptyMessageDelayed(0, 1000);
                            Time = Time - 1000;
                        } else {
                            GroupTimeTextView.this.setText("剩余 00:00:00");
                            handler.sendEmptyMessageDelayed(0, 1000);
                            Time = Time - 1000;
                        }
                    }
                    break;
            }
        }
    };

    private String getTimeString(long time) {
        StringBuffer sb = new StringBuffer();
        if (time / (60 * 60 * 24) > 0) {
            sb.append("剩余" + time / (60 * 60 * 24) + "天");
            return sb.toString();
        }
        //时
        if (time / (60 * 60) < 10) {
            sb.append("剩余 0" + time / (60 * 60) + ":");
        } else {
            sb.append("剩余 " + time / (60 * 60) + ":");
        }
        //分
        if ((time / 60) % 60 < 10) {
            sb.append("0" + (time / 60) % 60 + ":");
        } else {
            sb.append((time / 60) % 60 + ":");
        }
        //秒
        if (time % 60 < 10) {
            sb.append("0" + time % 60);
        } else {
            sb.append(time % 60);
        }
        return sb.toString();
    }

    public GroupTimeTextView(Context context) {
        super(context);
    }

    public GroupTimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GroupTimeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @SuppressLint("NewApi")
    public void setTimes(long mT) {
        Time = mT;
        handler.removeMessages(0);
        handler.sendEmptyMessage(0);
    }

    public void stop() {
        run = false;
    }

}
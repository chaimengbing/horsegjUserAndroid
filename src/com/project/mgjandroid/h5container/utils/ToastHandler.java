package com.project.mgjandroid.h5container.utils;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;


import com.google.gson.Gson;
import com.project.mgjandroid.h5base.jsbridge.JsBridgeCallBack;
import com.project.mgjandroid.h5base.jsbridge.JsBridgeHandler;
import com.project.mgjandroid.h5container.models.ErrorModel;
import com.project.mgjandroid.h5container.models.ToastModel;

public class ToastHandler implements JsBridgeHandler {

    private Context mContext;

    public ToastHandler(Context context) {
        mContext = context;
    }

    @Override
    public void handler(String data, final JsBridgeCallBack function) {

        Gson gson = new Gson();
        ToastModel model = gson.fromJson(data, ToastModel.class);

        if (TextUtils.isEmpty(model.getMsg())) {
            function.onCallBack(new Gson().toJson(new ErrorModel(500, "缺少参数msg")));
            return;
        }
        YLToastUtils.getInstance(mContext).showMsg(model.getMsg(), model.getType(), model.getDuration());
        if (function != null) {
            Handler handler = new Handler(Looper.getMainLooper());
            if (model.getDuration() <= 2000) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        function.onCallBack("{\"code\": \"0\"}");
                    }
                }, 2000);
            } else {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        function.onCallBack("{\"code\": \"0\"}");
                    }
                }, 3500);
            }
        }
    }
}

package com.project.mgjandroid.h5base.jsbridge;


import com.project.mgjandroid.h5base.utils.YLLogUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目名称：mgjandroid
 * 类描述：
 * 创建人：Mr_Lei
 * 创建时间：2018/4/17 12:01
 */
public class DefaultHandler implements JsBridgeHandler {
    public DefaultHandler() {
    }

    public void handler(String param, JsBridgeCallBack function) {
        YLLogUtils.d("DefaultHandler", "receive js message ( " + param + " )");
        if (function != null) {
            JSONObject object = new JSONObject();

            try {
                object.put("errorCode", 1);
                object.put("errorMessage", "接口不存在");
                function.onCallBack(object.toString());
            } catch (JSONException var5) {
                var5.printStackTrace();
            }
        }

    }
}

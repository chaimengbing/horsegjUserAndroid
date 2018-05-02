package com.project.mgjandroid.h5base.jsbridge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：mgjandroid
 * 类描述：
 * 创建人：Mr_Lei
 * 创建时间：2018/4/17 12:02
 */
public class JsBridgeMessage {
    private static final String CALLBACK_ID_STR = "callbackId";
    private static final String RESPONSE_ID_STR = "responseId";
    private static final String RESULT_STR = "result";
    private static final String DATA_STR = "data";
    private static final String HANDLER_NAME_STR = "handlerName";
    private String callbackId;
    private String responseId;
    private String result;
    private String data;
    private String handlerName;

    public JsBridgeMessage() {
    }

    public String getCallbackId() {
        return this.callbackId;
    }

    public void setCallbackId(String callbackId) {
        this.callbackId = callbackId;
    }

    public String getResponseId() {
        return this.responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHandlerName() {
        return this.handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public String toJson() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("callbackId", this.getCallbackId());
            jsonObject.put("data", this.getData());
            jsonObject.put("handlerName", this.getHandlerName());
            jsonObject.put("result", this.getResult());
            jsonObject.put("responseId", this.getResponseId());
            return jsonObject.toString();
        } catch (JSONException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static JsBridgeMessage toObject(String jsonStr) {
        JsBridgeMessage m = new JsBridgeMessage();

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            m.setHandlerName(jsonObject.has("handlerName") ? jsonObject.getString("handlerName") : null);
            m.setCallbackId(jsonObject.has("callbackId") ? jsonObject.getString("callbackId") : null);
            m.setResult(jsonObject.has("result") ? jsonObject.getString("result") : null);
            m.setResponseId(jsonObject.has("responseId") ? jsonObject.getString("responseId") : null);
            m.setData(jsonObject.has("data") ? jsonObject.getString("data") : null);
            return m;
        } catch (JSONException var3) {
            var3.printStackTrace();
            return m;
        }
    }

    public static List<JsBridgeMessage> toArrayList(String jsonStr) {
        ArrayList list = new ArrayList();

        try {
            JSONArray jsonArray = new JSONArray(jsonStr);

            for (int i = 0; i < jsonArray.length(); ++i) {
                JsBridgeMessage m = new JsBridgeMessage();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                m.setHandlerName(jsonObject.has("handlerName") ? jsonObject.getString("handlerName") : null);
                m.setCallbackId(jsonObject.has("callbackId") ? jsonObject.getString("callbackId") : null);
                m.setResult(jsonObject.has("result") ? jsonObject.getString("result") : null);
                m.setResponseId(jsonObject.has("responseId") ? jsonObject.getString("responseId") : null);
                m.setData(jsonObject.has("data") ? jsonObject.getString("data") : null);
                list.add(m);
            }
        } catch (JSONException var6) {
            var6.printStackTrace();
        }

        return list;
    }
}
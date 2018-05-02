package com.project.mgjandroid.model;

/**
 * Created by MrLei on 2017/9/7.
 */
public class LotteryStatusModel extends Entity {

    /**
     * code : 0
     * uuid : 867451020506330
     * value : {"switchSign":8,"url":"http://aasdf/as.img"}
     * success : true
     * servertime : 1522657358887
     * msg : 成功
     */

    private int code;
    private String uuid;
    private ValueBean value;
    private boolean success;
    private long servertime;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ValueBean getValue() {
        return value;
    }

    public void setValue(ValueBean value) {
        this.value = value;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getServertime() {
        return servertime;
    }

    public void setServertime(long servertime) {
        this.servertime = servertime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class ValueBean {
        /**
         * switchSign : 8
         * url : http://aasdf/as.img
         * "type": 1,
         * "url": "http://112.74.18.147/lot/build/index.html#/"
         */

        private int switchSign; //8是打开，7是关闭
        private int type;
        private String url;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getSwitchSign() {
            return switchSign;
        }

        public void setSwitchSign(int switchSign) {
            this.switchSign = switchSign;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}

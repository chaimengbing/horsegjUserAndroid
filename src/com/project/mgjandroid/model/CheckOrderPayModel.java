package com.project.mgjandroid.model;

/**
 * Created by User_Cjh on 2016/11/21.
 */
public class CheckOrderPayModel extends Entity {

    private int code;
    private Object uuid;
    private ValueEntity value;
    private boolean success;
    private String servertime;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getUuid() {
        return uuid;
    }

    public void setUuid(Object uuid) {
        this.uuid = uuid;
    }

    public ValueEntity getValue() {
        return value;
    }

    public void setValue(ValueEntity value) {
        this.value = value;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getServertime() {
        return servertime;
    }

    public void setServertime(String servertime) {
        this.servertime = servertime;
    }

    public static class ValueEntity {
        private Integer payState;
        private String orderId;

        public Integer getPayState() {
            return payState;
        }

        public void setPayState(Integer payState) {
            this.payState = payState;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }
}

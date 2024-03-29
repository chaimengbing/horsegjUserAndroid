package com.project.mgjandroid.model;

/**
 * Created by ning on 2016/3/19.
 */
public class DeliveryManModel extends Entity {

    /**
     * code : 0
     * uuid : 865543020573963
     * value : 2016031800001114
     * success : true
     */

    private int code;
    private String uuid;
    private DeliveryManInfo value;
    private boolean success;

    public void setCode(int code) {
        this.code = code;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public String getUuid() {
        return uuid;
    }


    public boolean isSuccess() {
        return success;
    }

    public DeliveryManInfo getValue() {
        return value;
    }

    public void setValue(DeliveryManInfo value) {
        this.value = value;
    }
}

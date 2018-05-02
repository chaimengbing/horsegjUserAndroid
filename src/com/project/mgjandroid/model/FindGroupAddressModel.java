package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.UserAddress;

/**
 * Created by User_Cjh on 2016/8/22.
 */
public class FindGroupAddressModel extends Entity {

    private int code;
    private String uuid;
    private UserAddress value;
    private boolean success;
    private String servertime;

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

    public UserAddress getValue() {
        return value;
    }

    public void setValue(UserAddress value) {
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
}

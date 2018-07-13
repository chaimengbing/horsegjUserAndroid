package com.project.mgjandroid.bean.groupbuying;

import com.project.mgjandroid.model.Entity;

import java.util.List;

public class GroupPurchaseCouponList extends Entity{
    private int code;
    private String uuid;
    private boolean success;
    private String servertime;
    private List<GroupPurchaseCoupon> value;

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

    public List<GroupPurchaseCoupon> getValue() {
        return value;
    }

    public void setValue(List<GroupPurchaseCoupon> value) {
        this.value = value;
    }
}

package com.project.mgjandroid.model.groupbuying;

import com.project.mgjandroid.bean.groupbuying.GroupPurchaseCoupon;
import com.project.mgjandroid.model.Entity;

import java.util.Date;

/**
 * Created by yuandi on 2017/3/14.
 */

public class GroupBuyingCouponModel extends Entity {
    private int code;
    private String uuid;
    private boolean success;
    private Date servertime;

    private GroupPurchaseCoupon value;

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

    public Date getServertime() {
        return servertime;
    }

    public void setServertime(Date servertime) {
        this.servertime = servertime;
    }

    public GroupPurchaseCoupon getValue() {
        return value;
    }

    public void setValue(GroupPurchaseCoupon value) {
        this.value = value;
    }
}

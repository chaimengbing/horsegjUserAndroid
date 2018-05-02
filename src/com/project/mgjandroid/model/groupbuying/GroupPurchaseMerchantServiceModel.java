package com.project.mgjandroid.model.groupbuying;

import com.project.mgjandroid.bean.groupbuying.GroupPurchaseMerchantService;
import com.project.mgjandroid.model.Entity;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yuandi on 2017/3/11.
 */

public class GroupPurchaseMerchantServiceModel extends Entity {
    private int code;
    private String uuid;
    private boolean success;
    private Date servertime;

    private ArrayList<GroupPurchaseMerchantService> value;

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

    public ArrayList<GroupPurchaseMerchantService> getValue() {
        return value;
    }

    public void setValue(ArrayList<GroupPurchaseMerchantService> value) {
        this.value = value;
    }
}

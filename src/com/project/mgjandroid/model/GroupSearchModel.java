package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.groupbuying.GroupPurchaseMerchant;

import java.util.List;

/**
 * Created by ning on 2016/5/6.
 */
public class GroupSearchModel extends Entity {

    private int code;
    private String uuid;
    private boolean success;
    private String servertime;
    private List<GroupPurchaseMerchant> value;

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

    public List<GroupPurchaseMerchant> getValue() {
        return value;
    }

    public void setValue(List<GroupPurchaseMerchant> value) {
        this.value = value;
    }

}

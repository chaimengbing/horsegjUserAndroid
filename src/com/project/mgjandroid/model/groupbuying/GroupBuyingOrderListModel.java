package com.project.mgjandroid.model.groupbuying;

import com.project.mgjandroid.bean.groupbuying.GroupPurchaseOrder;
import com.project.mgjandroid.model.Entity;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yuandi on 2017/3/14.
 */

public class GroupBuyingOrderListModel extends Entity {
    private int code;
    private String uuid;
    private boolean success;
    private String servertime;

    private ArrayList<GroupPurchaseOrder> value;

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

    public ArrayList<GroupPurchaseOrder> getValue() {
        return value;
    }

    public void setValue(ArrayList<GroupPurchaseOrder> value) {
        this.value = value;
    }
}

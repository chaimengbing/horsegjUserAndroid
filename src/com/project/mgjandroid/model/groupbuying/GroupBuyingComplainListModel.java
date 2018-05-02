package com.project.mgjandroid.model.groupbuying;

import com.project.mgjandroid.bean.groupbuying.GroupPurchaseComplain;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseEvaluate;
import com.project.mgjandroid.model.Entity;

import java.util.Date;
import java.util.List;

/**
 * Created by SunXueLiang on 2017/3/14.
 */

public class GroupBuyingComplainListModel extends Entity {
    private int code;
    private String uuid;
    private boolean success;
    private Date servertime;

    private List<GroupPurchaseComplain> value;

    public List<GroupPurchaseComplain> getValue() {
        return value;
    }

    public void setValue(List<GroupPurchaseComplain> value) {
        this.value = value;
    }

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
}

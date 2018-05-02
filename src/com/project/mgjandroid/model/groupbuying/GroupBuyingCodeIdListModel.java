package com.project.mgjandroid.model.groupbuying;

import java.util.Date;
import java.util.List;

/**
 * Created by yuandi on 2017/3/20.
 */

public class GroupBuyingCodeIdListModel {
    private int code;
    private String uuid;
    private boolean success;
    private Date servertime;

    private List<Long> value;

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

    public List<Long> getValue() {
        return value;
    }

    public void setValue(List<Long> value) {
        this.value = value;
    }
}

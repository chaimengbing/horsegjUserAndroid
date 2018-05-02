package com.project.mgjandroid.model.yellowpage;

import com.project.mgjandroid.bean.yellowpage.YellowPage;
import com.project.mgjandroid.model.Entity;

import java.util.ArrayList;
import java.util.Date;

public class YellowPageDetailModel extends Entity {
    private int code;
    private String uuid;
    private boolean success;
    private Date servertime;

    private YellowPage value;

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

    public YellowPage getValue() {
        return value;
    }

    public void setValue(YellowPage value) {
        this.value = value;
    }
}

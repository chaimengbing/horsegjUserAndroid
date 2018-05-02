package com.project.mgjandroid.model.yellowpage;

import com.project.mgjandroid.bean.yellowpage.YellowPageBanner;
import com.project.mgjandroid.bean.yellowpage.YellowPageCategory;
import com.project.mgjandroid.model.Entity;

import java.util.ArrayList;
import java.util.Date;

public class YellowPageCategoryModel extends Entity {
    private int code;
    private String uuid;
    private boolean success;
    private Date servertime;

    private ArrayList<YellowPageCategory> value;

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

    public ArrayList<YellowPageCategory> getValue() {
        return value;
    }

    public void setValue(ArrayList<YellowPageCategory> value) {
        this.value = value;
    }
}

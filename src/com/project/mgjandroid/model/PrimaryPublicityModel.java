package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.PrimaryPublicity;

import java.util.List;

/**
 * Created by yuandi on 2016/9/22.
 */
public class PrimaryPublicityModel extends Entity {

    private int code;
    private String uuid;
    private boolean success;
    private String servertime;
    private List<PrimaryPublicity> value;

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

    public List<PrimaryPublicity> getValue() {
        return value;
    }

    public void setValue(List<PrimaryPublicity> value) {
        this.value = value;
    }
}

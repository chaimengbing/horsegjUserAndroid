package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.BroadcastNew;

/**
 * Created by MrLei on 2017/9/7.
 */
public class BroadcastNewModel extends Entity {

    private int code;
    private String uuid;
    private boolean success;
    private String servertime;
    private BroadcastNew value;

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

    public BroadcastNew getValue() {
        return value;
    }

    public void setValue(BroadcastNew value) {
        this.value = value;
    }
}

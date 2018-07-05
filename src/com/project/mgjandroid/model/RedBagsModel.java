package com.project.mgjandroid.model;

/**
 * Created by yuandi on 2016/5/27.
 */
public class RedBagsModel extends Entity {

    private int code;

    private String uuid;

    private RedBagListModel value;

    private boolean success;

    private String servertime;

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

    public RedBagListModel getValue() {
        return value;
    }

    public void setValue(RedBagListModel value) {
        this.value = value;
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
}

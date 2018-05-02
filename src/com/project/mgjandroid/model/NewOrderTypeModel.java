package com.project.mgjandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by SunXueLiang on 2018-01-05.
 */

public class NewOrderTypeModel extends Entity {

    private int code;
    private String uuid;
    private boolean success;
    private String servertime;
    private List<Map<Integer, String>> value;

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

    public List<Map<Integer, String>> getValue() {
        return value;
    }

    public void setValue(List<Map<Integer, String>> value) {
        this.value = value;
    }

}

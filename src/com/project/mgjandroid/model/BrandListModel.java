package com.project.mgjandroid.model;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SunXueLiang on 2017-05-08.
 */

public class BrandListModel extends Entity {

    private int code;
    private String uuid;
    private boolean success;
    private String servertime;
    private Map<String, List<AutoLogos>> value;

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

    public Map<String, List<AutoLogos>> getValue() {
        return value;
    }

    public void setValue(Map<String, List<AutoLogos>> value) {
        this.value = value;
    }
}

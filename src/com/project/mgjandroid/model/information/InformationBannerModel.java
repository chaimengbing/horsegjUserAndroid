package com.project.mgjandroid.model.information;

import com.project.mgjandroid.bean.information.InformationBanner;
import com.project.mgjandroid.model.Entity;

import java.util.List;

public class InformationBannerModel extends Entity {

    private int code;
    private String uuid;
    private boolean success;
    private String servertime;

    private List<InformationBanner> value;

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

    public List<InformationBanner> getValue() {
        return value;
    }

    public void setValue(List<InformationBanner> value) {
        this.value = value;
    }
}

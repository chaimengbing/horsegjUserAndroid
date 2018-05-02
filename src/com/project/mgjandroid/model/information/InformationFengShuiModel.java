package com.project.mgjandroid.model.information;

import com.project.mgjandroid.bean.information.InformationFengShui;
import com.project.mgjandroid.model.Entity;

/**
 * Created by yuandi on 2016/11/24.
 */

public class InformationFengShuiModel extends Entity {

    private int code;

    private String uuid;

    private InformationFengShui value;

    private boolean success;

    private String servertime;

    public String getServertime() {
        return servertime;
    }

    public void setServertime(String servertime) {
        this.servertime = servertime;
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

    public InformationFengShui getValue() {
        return value;
    }

    public void setValue(InformationFengShui value) {
        this.value = value;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

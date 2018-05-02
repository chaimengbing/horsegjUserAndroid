package com.project.mgjandroid.model.information;

import com.project.mgjandroid.bean.information.InformationHealth;
import com.project.mgjandroid.model.Entity;

/**
 * Created by yuandi on 2016/11/24.
 */

public class InformationHealthModel extends Entity {

    private int code;

    private String uuid;

    private InformationHealth value;

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

    public InformationHealth getValue() {
        return value;
    }

    public void setValue(InformationHealth value) {
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

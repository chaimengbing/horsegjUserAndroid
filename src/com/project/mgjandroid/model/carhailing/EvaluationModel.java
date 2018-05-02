package com.project.mgjandroid.model.carhailing;

import com.project.mgjandroid.bean.carhailing.DriverComment;
import com.project.mgjandroid.model.Entity;

/**
 * Created by yuandi on 2017/1/6.
 */

public class EvaluationModel extends Entity {

    private int code;
    private String uuid;
    private boolean success;
    private String servertime;
    private DriverComment value;

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

    public DriverComment getValue() {
        return value;
    }

    public void setValue(DriverComment value) {
        this.value = value;
    }
}

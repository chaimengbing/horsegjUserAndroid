package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.SecondhandInformation;

import java.util.Date;

/**
 * Created by rjp on 2016/7/14.
 * Email:rjpgoodjob@gmail.com
 */
public class SecondHandDetailModel extends Entity {
    private int code;
    private String uuid;
    private SecondhandInformation value;
    private boolean success;
    private Date servertime;

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

    public SecondhandInformation getValue() {
        return value;
    }

    public void setValue(SecondhandInformation value) {
        this.value = value;
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
}

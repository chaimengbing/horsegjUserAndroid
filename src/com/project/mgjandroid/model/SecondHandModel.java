package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.SecondhandInformation;

import java.util.Date;
import java.util.List;

/**
 * Created by rjp on 2016/7/11.
 * Email:rjpgoodjob@gmail.com
 */
public class SecondHandModel extends Entity {

    private int code;
    private String uuid;
    private boolean success;
    private Date servertime;
    private List<SecondhandInformation> value;

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

    public Date getServertime() {
        return servertime;
    }

    public void setServertime(Date servertime) {
        this.servertime = servertime;
    }

    public List<SecondhandInformation> getValue() {
        return value;
    }

    public void setValue(List<SecondhandInformation> value) {
        this.value = value;
    }
}

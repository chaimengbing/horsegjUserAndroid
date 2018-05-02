package com.project.mgjandroid.model.information;

import com.project.mgjandroid.bean.information.NewPositionInformation;
import com.project.mgjandroid.bean.information.PositionInformation;
import com.project.mgjandroid.model.Entity;

import java.util.Date;

/**
 * Created by yuandi on 2016/11/17.
 */

public class NewPositionInformationModel extends Entity {

    private int code;

    private String uuid;

    private NewPositionInformation value;

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

    public NewPositionInformation getValue() {
        return value;
    }

    public void setValue(NewPositionInformation value) {
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

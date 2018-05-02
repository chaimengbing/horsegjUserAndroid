package com.project.mgjandroid.model.carhailing;

import com.project.mgjandroid.bean.carhailing.CarHailing;
import com.project.mgjandroid.bean.carhailing.CarHailingTrip;
import com.project.mgjandroid.model.Entity;

import java.util.Date;

/**
 * Created by User_Cjh on 2016/12/23.
 */
public class CarHailingTripDetailModel extends Entity {
    private int code;
    private String uuid;
    private boolean success;
    private Date servertime;

    private CarHailingTrip value;

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

    public CarHailingTrip getValue() {
        return value;
    }

    public void setValue(CarHailingTrip value) {
        this.value = value;
    }
}

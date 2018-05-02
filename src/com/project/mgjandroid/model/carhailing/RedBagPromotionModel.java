package com.project.mgjandroid.model.carhailing;

import com.project.mgjandroid.bean.carhailing.CarRedBagPromotion;
import com.project.mgjandroid.model.Entity;

/**
 * Created by yuandi on 2017/1/7.
 */

public class RedBagPromotionModel extends Entity {

    private int code;
    private String uuid;
    private boolean success;
    private String servertime;
    private CarRedBagPromotion value;

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

    public CarRedBagPromotion getValue() {
        return value;
    }

    public void setValue(CarRedBagPromotion value) {
        this.value = value;
    }
}

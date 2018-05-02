package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.Goods;

/**
 * Created by yuandi on 2016/11/30.
 */

public class FindGoodsModel extends Entity {

    private int code;
    private String uuid;
    private Goods value;
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

    public Goods getValue() {
        return value;
    }

    public void setValue(Goods value) {
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

package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.AreaBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by User_Cjh on 2016/9/19.
 */
public class RegionModel extends Entity {
    private int code;
    private String uuid;
    private boolean success;
    private String servertime;
    private List<AreaBean> value;

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

    public List<AreaBean> getValue() {
        return value;
    }

    public void setValue(List<AreaBean> value) {
        this.value = value;
    }
}

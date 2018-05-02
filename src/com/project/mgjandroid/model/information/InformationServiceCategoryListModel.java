package com.project.mgjandroid.model.information;

import com.project.mgjandroid.bean.information.InformationServiceCategory;

import java.util.List;

/**
 * Created by yuandi on 2016/11/19.
 */

public class InformationServiceCategoryListModel {

    private int code;

    private String uuid;

    private boolean success;

    private String servertime;

    private List<InformationServiceCategory> value;

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

    public List<InformationServiceCategory> getValue() {
        return value;
    }

    public void setValue(List<InformationServiceCategory> value) {
        this.value = value;
    }
}

package com.project.mgjandroid.model.information;

import com.project.mgjandroid.bean.information.InformationWasteRecovery;
import com.project.mgjandroid.model.Entity;

import java.util.List;

/**
 * Created by yuandi on 2016/11/24.
 */

public class InformationWasteListModel extends Entity {

    private int code;

    private String uuid;

    private List<InformationWasteRecovery> value;

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

    public List<InformationWasteRecovery> getValue() {
        return value;
    }

    public void setValue(List<InformationWasteRecovery> value) {
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

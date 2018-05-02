package com.project.mgjandroid.model.information;

import com.project.mgjandroid.bean.Agent;
import com.project.mgjandroid.model.Entity;

import java.util.List;

/**
 * Created by yuandi on 2016/11/22.
 */

public class AgentListModel extends Entity {

    private int code;

    private String uuid;

    private List<Agent> value;

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

    public List<Agent> getValue() {
        return value;
    }

    public void setValue(List<Agent> value) {
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

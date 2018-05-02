package com.project.mgjandroid.model.information;

import com.project.mgjandroid.bean.HomemakingInformation;
import com.project.mgjandroid.bean.RepairInformation;
import com.project.mgjandroid.model.Entity;

import java.util.List;

/**
 * Created by User_Cjh on 2016/11/18.
 */
public class InformationRepairListModel extends Entity {

    private int code;
    private String uuid;
    private boolean success;
    private String servertime;
    private List<RepairInformation> value;

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

    public List<RepairInformation> getValue() {
        return value;
    }

    public void setValue(List<RepairInformation> value) {
        this.value = value;
    }
}

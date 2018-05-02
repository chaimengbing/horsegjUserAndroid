package com.project.mgjandroid.model.information;

import com.project.mgjandroid.bean.information.PositionInformation;
import com.project.mgjandroid.bean.information.RecruitInformation;
import com.project.mgjandroid.model.Entity;

import java.util.Date;
import java.util.List;

/**
 * Created by User_Cjh on 2016/11/18.
 */
public class InformationRecruitListModel extends Entity {

    private int code;
    private String uuid;
    private boolean success;
    private Date servertime;
    private List<RecruitInformation> value;

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

    public List<RecruitInformation> getValue() {
        return value;
    }

    public void setValue(List<RecruitInformation> value) {
        this.value = value;
    }
}

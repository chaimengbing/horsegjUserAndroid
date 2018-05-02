package com.project.mgjandroid.model.information;

import com.project.mgjandroid.bean.information.InformationFreeStandard;
import com.project.mgjandroid.model.Entity;

import java.util.ArrayList;

/**
 * Created by yuandi on 2016/11/17.
 */

public class InformationFreeStandardListModel extends Entity {

    private int code;

    private String uuid;

    private ValueEntity value;

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

    public ValueEntity getValue() {
        return value;
    }

    public void setValue(ValueEntity value) {
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

    public class ValueEntity extends Entity {

        private boolean hasPayed;

        private ArrayList<InformationFreeStandard> freeStandardList;

        public boolean isHasPayed() {
            return hasPayed;
        }

        public void setHasPayed(boolean hasPayed) {
            this.hasPayed = hasPayed;
        }

        public ArrayList<InformationFreeStandard> getFreeStandardList() {
            return freeStandardList;
        }

        public void setFreeStandardList(ArrayList<InformationFreeStandard> freeStandardList) {
            this.freeStandardList = freeStandardList;
        }
    }
}

package com.project.mgjandroid.model.carhailing;

import com.project.mgjandroid.bean.carhailing.DriverComment;
import com.project.mgjandroid.model.Entity;

import java.util.List;

/**
 * Created by User_Cjh on 2016/12/19.
 */
public class DriverCommentModel extends Entity {
    private int code;
    private String uuid;

    private ValueBean value;
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

    public ValueBean getValue() {
        return value;
    }

    public void setValue(ValueBean value) {
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

    public static class ValueBean {
        private int total;
        private List<DriverComment> chauffeurOrderCommontsList;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DriverComment> getChauffeurOrderCommontsList() {
            return chauffeurOrderCommontsList;
        }

        public void setChauffeurOrderCommontsList(List<DriverComment> chauffeurOrderCommontsList) {
            this.chauffeurOrderCommontsList = chauffeurOrderCommontsList;
        }
    }
}

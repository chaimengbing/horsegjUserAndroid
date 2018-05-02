package com.project.mgjandroid.model;

import java.util.List;

/**
 * Created by Administrator on 2016/11/2.
 */
public class CustomerAndComplainPhoneDTOModel extends Entity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int code;
    private String uuid;
    private List<Value> value;
    private boolean success;
    private String servertime;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<Value> getValue() {
        return value;
    }

    public void setValue(List<Value> value) {
        this.value = value;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class Value extends Entity {
        /**
         * 电话类型 1:联系客服，2：投诉电话 (区域负责人),3:客服电话
         **/
        private int type;
        private String phone;
        private String title;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}

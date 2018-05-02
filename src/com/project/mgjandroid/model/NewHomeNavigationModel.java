package com.project.mgjandroid.model;

import java.util.List;

/**
 * Created by SunXueLiang on 2017-09-01.
 */

public class NewHomeNavigationModel extends Entity {

    private int code;
    private String uuid;
    private boolean success;
    private String servertime;
    private List<ValueBean> value;

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

    public List<ValueBean> getValue() {
        return value;
    }

    public void setValue(List<ValueBean> value) {
        this.value = value;
    }

    public static class ValueBean extends Entity {

        private int id;
        private String createTime;
        private String modifyTime;
        private int agentId;
        private String name;
        private String picUrl;
        private String grayUrl;
        private int sortNo;
        private int graySwitch;
        private int hasDel;
        private int gotoType;
        private String gotoUrl;
        private Object businessType;
        private int menuType;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getGrayUrl() {
            return grayUrl;
        }

        public void setGrayUrl(String grayUrl) {
            this.grayUrl = grayUrl;
        }

        public int getSortNo() {
            return sortNo;
        }

        public void setSortNo(int sortNo) {
            this.sortNo = sortNo;
        }

        public int getGraySwitch() {
            return graySwitch;
        }

        public void setGraySwitch(int graySwitch) {
            this.graySwitch = graySwitch;
        }

        public int getHasDel() {
            return hasDel;
        }

        public void setHasDel(int hasDel) {
            this.hasDel = hasDel;
        }

        public int getGotoType() {
            return gotoType;
        }

        public void setGotoType(int gotoType) {
            this.gotoType = gotoType;
        }

        public String getGotoUrl() {
            return gotoUrl;
        }

        public void setGotoUrl(String gotoUrl) {
            this.gotoUrl = gotoUrl;
        }

        public Object getBusinessType() {
            return businessType;
        }

        public void setBusinessType(Object businessType) {
            this.businessType = businessType;
        }

        public int getMenuType() {
            return menuType;
        }

        public void setMenuType(int menuType) {
            this.menuType = menuType;
        }
    }
}

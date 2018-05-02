package com.project.mgjandroid.model;

import java.util.Date;

/**
 * Created by User_Cjh on 2017/7/26.
 */

public class PushSetModel extends Entity {

    private int code;
    private String uuid;
    private ValueBean value;
    private boolean success;
    private Date servertime;

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

    public Date getServertime() {
        return servertime;
    }

    public void setServertime(Date servertime) {
        this.servertime = servertime;
    }

    public static class ValueBean {

        private long id;
        private String createTime;
        private String modifyTime;
        private int userId;
        private int pushType;
        private int isAble;

        public long getId() {
            return id;
        }

        public void setId(long id) {
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

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getPushType() {
            return pushType;
        }

        public void setPushType(int pushType) {
            this.pushType = pushType;
        }

        public int getIsAble() {
            return isAble;
        }

        public void setIsAble(int isAble) {
            this.isAble = isAble;
        }
    }
}

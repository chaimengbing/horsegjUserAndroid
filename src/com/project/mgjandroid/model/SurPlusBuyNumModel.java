package com.project.mgjandroid.model;

/**
 * Created by User_Cjh on 2016/8/17.
 */
public class SurPlusBuyNumModel extends Entity {

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

    public static class ValueEntity {
        private Integer goodsSpecId;
        private Integer buyNum;

        public Integer getGoodsSpecId() {
            return goodsSpecId;
        }

        public void setGoodsSpecId(Integer goodsSpecId) {
            this.goodsSpecId = goodsSpecId;
        }

        public Integer getBuyNum() {
            return buyNum;
        }

        public void setBuyNum(Integer buyNum) {
            this.buyNum = buyNum;
        }
    }
}

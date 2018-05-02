package com.project.mgjandroid.model;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/10/20.
 */
public class ShippingFeeModel extends Entity {
    private int code;
    private String uuid;
    private Value value;
    private boolean success;
    private String servertime;

    public static class Value extends Entity {
        public BigDecimal minPrice;
        public BigDecimal shipFee;

        public BigDecimal getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(BigDecimal minPrice) {
            this.minPrice = minPrice;
        }

        public BigDecimal getShipFee() {
            return shipFee;
        }

        public void setShipFee(BigDecimal shipFee) {
            this.shipFee = shipFee;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getServertime() {
        return servertime;
    }

    public void setServertime(String servertime) {
        this.servertime = servertime;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}

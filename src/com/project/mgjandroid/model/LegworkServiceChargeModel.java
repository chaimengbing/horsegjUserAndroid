package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.RedBag;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by SunXueLiang on 2018-03-28.
 */

public class LegworkServiceChargeModel extends Entity{

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

    public static class ValueBean extends Entity{

        private double serviceCharge;
        private double defDistance;
        private double baseCharge;
        private double addCharge;
        /**
         * platformRedBags：使用的红包（集合）
         * platformRedBagCount：可使用红包数
         * redBagsTotalAmt：红包优惠金额
         * totalPrice：最终支付金额
         */

        private int platformRedBagCount;
        private ArrayList<RedBag> platformRedBags;
        private BigDecimal totalPrice;
        private BigDecimal redBagsTotalAmt;


        public ArrayList<RedBag> getPlatformRedBagList() {
            return platformRedBags;
        }

        public void setPlatformRedBagList(ArrayList<RedBag> platformRedBagList) {
            this.platformRedBags = platformRedBagList;
        }

        public int getPlatformRedBagCount() {
            return platformRedBagCount;
        }

        public void setPlatformRedBagCount(int platformRedBagCount) {
            this.platformRedBagCount = platformRedBagCount;
        }


        public BigDecimal getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
        }

        public BigDecimal getRedBagsTotalAmt() {
            return redBagsTotalAmt;
        }

        public void setRedBagsTotalAmt(BigDecimal redBagsTotalAmt) {
            this.redBagsTotalAmt = redBagsTotalAmt;
        }

        public double getServiceCharge() {
            return serviceCharge;
        }

        public void setServiceCharge(double serviceCharge) {
            this.serviceCharge = serviceCharge;
        }

        public double getDefDistance() {
            return defDistance;
        }

        public void setDefDistance(double defDistance) {
            this.defDistance = defDistance;
        }

        public double getBaseCharge() {
            return baseCharge;
        }

        public void setBaseCharge(double baseCharge) {
            this.baseCharge = baseCharge;
        }

        public double getAddCharge() {
            return addCharge;
        }

        public void setAddCharge(double addCharge) {
            this.addCharge = addCharge;
        }
    }
}

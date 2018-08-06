package com.project.mgjandroid.model.groupbuying;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;

public class GroupBuyingPreviewModel extends Entity {

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

        private int merchantId;
        private int userId;
        private long agentId;
        private String userMobile;
        private String loginToken;
        private int groupPurchaseOrderType;
        private int hasDiscount;
        private BigDecimal discountAmt;
        private int discountRatio;
        private Object groupPurchaseCouponId;
        private Object groupPurchaseOrderCouponCodeList;
        private Object groupPurchaseCouponType;
        private String originalPrice;
        private String totalOriginalPrice;
        private String cashDeductionPrice;
        private String price;
        private BigDecimal totalPrice;
        private int quantity;
        private Object paymentType;
        private Object groupPurchaseCouponGoodsList;
        private int redBagDiscountTotalAmt;
        private int platformRedBagCount;
        private int enableGroupPurchaseOrderCouponCodeCount;
        private int originalTotalPrice;
        private Object redBags;
        private Object targetDate;

        public BigDecimal getDiscountAmt() {
            return discountAmt;
        }

        public void setDiscountAmt(BigDecimal discountAmt) {
            this.discountAmt = discountAmt;
        }

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public long getAgentId() {
            return agentId;
        }

        public void setAgentId(long agentId) {
            this.agentId = agentId;
        }

        public String getUserMobile() {
            return userMobile;
        }

        public void setUserMobile(String userMobile) {
            this.userMobile = userMobile;
        }

        public String getLoginToken() {
            return loginToken;
        }

        public void setLoginToken(String loginToken) {
            this.loginToken = loginToken;
        }

        public int getGroupPurchaseOrderType() {
            return groupPurchaseOrderType;
        }

        public void setGroupPurchaseOrderType(int groupPurchaseOrderType) {
            this.groupPurchaseOrderType = groupPurchaseOrderType;
        }

        public int getHasDiscount() {
            return hasDiscount;
        }

        public void setHasDiscount(int hasDiscount) {
            this.hasDiscount = hasDiscount;
        }

        public int getDiscountRatio() {
            return discountRatio;
        }

        public void setDiscountRatio(int discountRatio) {
            this.discountRatio = discountRatio;
        }

        public Object getGroupPurchaseCouponId() {
            return groupPurchaseCouponId;
        }

        public void setGroupPurchaseCouponId(Object groupPurchaseCouponId) {
            this.groupPurchaseCouponId = groupPurchaseCouponId;
        }

        public Object getGroupPurchaseOrderCouponCodeList() {
            return groupPurchaseOrderCouponCodeList;
        }

        public void setGroupPurchaseOrderCouponCodeList(Object groupPurchaseOrderCouponCodeList) {
            this.groupPurchaseOrderCouponCodeList = groupPurchaseOrderCouponCodeList;
        }

        public Object getGroupPurchaseCouponType() {
            return groupPurchaseCouponType;
        }

        public void setGroupPurchaseCouponType(Object groupPurchaseCouponType) {
            this.groupPurchaseCouponType = groupPurchaseCouponType;
        }

        public String getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }

        public String getTotalOriginalPrice() {
            return totalOriginalPrice;
        }

        public void setTotalOriginalPrice(String totalOriginalPrice) {
            this.totalOriginalPrice = totalOriginalPrice;
        }

        public String getCashDeductionPrice() {
            return cashDeductionPrice;
        }

        public void setCashDeductionPrice(String cashDeductionPrice) {
            this.cashDeductionPrice = cashDeductionPrice;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public Object getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(Object paymentType) {
            this.paymentType = paymentType;
        }

        public Object getGroupPurchaseCouponGoodsList() {
            return groupPurchaseCouponGoodsList;
        }

        public void setGroupPurchaseCouponGoodsList(Object groupPurchaseCouponGoodsList) {
            this.groupPurchaseCouponGoodsList = groupPurchaseCouponGoodsList;
        }

        public int getRedBagDiscountTotalAmt() {
            return redBagDiscountTotalAmt;
        }

        public void setRedBagDiscountTotalAmt(int redBagDiscountTotalAmt) {
            this.redBagDiscountTotalAmt = redBagDiscountTotalAmt;
        }

        public int getPlatformRedBagCount() {
            return platformRedBagCount;
        }

        public void setPlatformRedBagCount(int platformRedBagCount) {
            this.platformRedBagCount = platformRedBagCount;
        }

        public int getEnableGroupPurchaseOrderCouponCodeCount() {
            return enableGroupPurchaseOrderCouponCodeCount;
        }

        public void setEnableGroupPurchaseOrderCouponCodeCount(int enableGroupPurchaseOrderCouponCodeCount) {
            this.enableGroupPurchaseOrderCouponCodeCount = enableGroupPurchaseOrderCouponCodeCount;
        }

        public int getOriginalTotalPrice() {
            return originalTotalPrice;
        }

        public void setOriginalTotalPrice(int originalTotalPrice) {
            this.originalTotalPrice = originalTotalPrice;
        }

        public Object getRedBags() {
            return redBags;
        }

        public void setRedBags(Object redBags) {
            this.redBags = redBags;
        }

        public Object getTargetDate() {
            return targetDate;
        }

        public void setTargetDate(Object targetDate) {
            this.targetDate = targetDate;
        }
    }
}

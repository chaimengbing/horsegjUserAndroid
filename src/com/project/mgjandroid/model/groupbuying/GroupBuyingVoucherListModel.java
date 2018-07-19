package com.project.mgjandroid.model.groupbuying;

import com.project.mgjandroid.model.Entity;

import java.util.List;

public class GroupBuyingVoucherListModel extends Entity{

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

    public static class ValueBean extends Entity{

        private int id;
        private String createTime;
        private String modifyTime;
        private int groupPurchaseCouponId;
        private String groupPurchaseOrderId;
        private int agentId;
        private int userId;
        private int merchantId;
        private String userMobile;
        private int groupPurchaseCouponType;
        private String couponCode;
        private Object expireTime;
        private String endTime;
        private Object targetDate;
        private Object isAutomaticallyCancelAfterVerification;
        private Object cancelAfterVerificationTime;
        private Object usingTime;
        private String refundTime;
        private int isExpire;
        private int status;
        private double originPrice;
        private double price;
        private int isCumulate;
        private int isBespeak;
        private String consumeTime;
        private String applyRange;
        private String useRule;
        private String images;
        private int merchantSettleState;
        private int agentSettleState;
        private int settleState;
        private double sysRate;
        private double sysRateAmt;
        private double agentRate;
        private double agentRateAmt;
        private double provinceAgentRate;
        private double provinceAgentRateAmt;
        private double partnerAgentRateAmt;
        private double merchantAmt;
        private String commissionJson;
        private Object groupPurchaseCouponTypeName;
        private Object groupPurchaseOrderCoupon;
        private Object agent;
        private Object groupPurchaseMerchant;
        private Object groupPurchaseName;
        private Object redBagJson;
        private Object redBagDiscountTotalAmt;
        private boolean isChecked;

        public boolean isChecked() {
            return isChecked;
        }

        public void setIsChecked(boolean isChecked) {
            this.isChecked = isChecked;
        }

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

        public int getGroupPurchaseCouponId() {
            return groupPurchaseCouponId;
        }

        public void setGroupPurchaseCouponId(int groupPurchaseCouponId) {
            this.groupPurchaseCouponId = groupPurchaseCouponId;
        }

        public String getGroupPurchaseOrderId() {
            return groupPurchaseOrderId;
        }

        public void setGroupPurchaseOrderId(String groupPurchaseOrderId) {
            this.groupPurchaseOrderId = groupPurchaseOrderId;
        }

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }

        public String getUserMobile() {
            return userMobile;
        }

        public void setUserMobile(String userMobile) {
            this.userMobile = userMobile;
        }

        public int getGroupPurchaseCouponType() {
            return groupPurchaseCouponType;
        }

        public void setGroupPurchaseCouponType(int groupPurchaseCouponType) {
            this.groupPurchaseCouponType = groupPurchaseCouponType;
        }

        public String getCouponCode() {
            return couponCode;
        }

        public void setCouponCode(String couponCode) {
            this.couponCode = couponCode;
        }

        public Object getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(Object expireTime) {
            this.expireTime = expireTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public Object getTargetDate() {
            return targetDate;
        }

        public void setTargetDate(Object targetDate) {
            this.targetDate = targetDate;
        }

        public Object getIsAutomaticallyCancelAfterVerification() {
            return isAutomaticallyCancelAfterVerification;
        }

        public void setIsAutomaticallyCancelAfterVerification(Object isAutomaticallyCancelAfterVerification) {
            this.isAutomaticallyCancelAfterVerification = isAutomaticallyCancelAfterVerification;
        }

        public Object getCancelAfterVerificationTime() {
            return cancelAfterVerificationTime;
        }

        public void setCancelAfterVerificationTime(Object cancelAfterVerificationTime) {
            this.cancelAfterVerificationTime = cancelAfterVerificationTime;
        }

        public Object getUsingTime() {
            return usingTime;
        }

        public void setUsingTime(Object usingTime) {
            this.usingTime = usingTime;
        }

        public String getRefundTime() {
            return refundTime;
        }

        public void setRefundTime(String refundTime) {
            this.refundTime = refundTime;
        }

        public int getIsExpire() {
            return isExpire;
        }

        public void setIsExpire(int isExpire) {
            this.isExpire = isExpire;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public double getOriginPrice() {
            return originPrice;
        }

        public void setOriginPrice(double originPrice) {
            this.originPrice = originPrice;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getIsCumulate() {
            return isCumulate;
        }

        public void setIsCumulate(int isCumulate) {
            this.isCumulate = isCumulate;
        }

        public int getIsBespeak() {
            return isBespeak;
        }

        public void setIsBespeak(int isBespeak) {
            this.isBespeak = isBespeak;
        }

        public String getConsumeTime() {
            return consumeTime;
        }

        public void setConsumeTime(String consumeTime) {
            this.consumeTime = consumeTime;
        }

        public String getApplyRange() {
            return applyRange;
        }

        public void setApplyRange(String applyRange) {
            this.applyRange = applyRange;
        }

        public String getUseRule() {
            return useRule;
        }

        public void setUseRule(String useRule) {
            this.useRule = useRule;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public int getMerchantSettleState() {
            return merchantSettleState;
        }

        public void setMerchantSettleState(int merchantSettleState) {
            this.merchantSettleState = merchantSettleState;
        }

        public int getAgentSettleState() {
            return agentSettleState;
        }

        public void setAgentSettleState(int agentSettleState) {
            this.agentSettleState = agentSettleState;
        }

        public int getSettleState() {
            return settleState;
        }

        public void setSettleState(int settleState) {
            this.settleState = settleState;
        }

        public double getSysRate() {
            return sysRate;
        }

        public void setSysRate(double sysRate) {
            this.sysRate = sysRate;
        }

        public double getSysRateAmt() {
            return sysRateAmt;
        }

        public void setSysRateAmt(double sysRateAmt) {
            this.sysRateAmt = sysRateAmt;
        }

        public double getAgentRate() {
            return agentRate;
        }

        public void setAgentRate(double agentRate) {
            this.agentRate = agentRate;
        }

        public double getAgentRateAmt() {
            return agentRateAmt;
        }

        public void setAgentRateAmt(double agentRateAmt) {
            this.agentRateAmt = agentRateAmt;
        }

        public double getProvinceAgentRate() {
            return provinceAgentRate;
        }

        public void setProvinceAgentRate(double provinceAgentRate) {
            this.provinceAgentRate = provinceAgentRate;
        }

        public double getProvinceAgentRateAmt() {
            return provinceAgentRateAmt;
        }

        public void setProvinceAgentRateAmt(double provinceAgentRateAmt) {
            this.provinceAgentRateAmt = provinceAgentRateAmt;
        }

        public double getPartnerAgentRateAmt() {
            return partnerAgentRateAmt;
        }

        public void setPartnerAgentRateAmt(double partnerAgentRateAmt) {
            this.partnerAgentRateAmt = partnerAgentRateAmt;
        }

        public double getMerchantAmt() {
            return merchantAmt;
        }

        public void setMerchantAmt(double merchantAmt) {
            this.merchantAmt = merchantAmt;
        }

        public String getCommissionJson() {
            return commissionJson;
        }

        public void setCommissionJson(String commissionJson) {
            this.commissionJson = commissionJson;
        }

        public Object getGroupPurchaseCouponTypeName() {
            return groupPurchaseCouponTypeName;
        }

        public void setGroupPurchaseCouponTypeName(Object groupPurchaseCouponTypeName) {
            this.groupPurchaseCouponTypeName = groupPurchaseCouponTypeName;
        }

        public Object getGroupPurchaseOrderCoupon() {
            return groupPurchaseOrderCoupon;
        }

        public void setGroupPurchaseOrderCoupon(Object groupPurchaseOrderCoupon) {
            this.groupPurchaseOrderCoupon = groupPurchaseOrderCoupon;
        }

        public Object getAgent() {
            return agent;
        }

        public void setAgent(Object agent) {
            this.agent = agent;
        }

        public Object getGroupPurchaseMerchant() {
            return groupPurchaseMerchant;
        }

        public void setGroupPurchaseMerchant(Object groupPurchaseMerchant) {
            this.groupPurchaseMerchant = groupPurchaseMerchant;
        }

        public Object getGroupPurchaseName() {
            return groupPurchaseName;
        }

        public void setGroupPurchaseName(Object groupPurchaseName) {
            this.groupPurchaseName = groupPurchaseName;
        }

        public Object getRedBagJson() {
            return redBagJson;
        }

        public void setRedBagJson(Object redBagJson) {
            this.redBagJson = redBagJson;
        }

        public Object getRedBagDiscountTotalAmt() {
            return redBagDiscountTotalAmt;
        }

        public void setRedBagDiscountTotalAmt(Object redBagDiscountTotalAmt) {
            this.redBagDiscountTotalAmt = redBagDiscountTotalAmt;
        }
    }
}

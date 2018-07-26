package com.project.mgjandroid.bean.groupbuying;

import com.project.mgjandroid.bean.BaseBean;

public class GroupPurchaseOrderCoupon extends BaseBean{

    private int groupPurchaseCouponId;
    private String groupPurchaseOrderId;
    private int userId;
    private int agentId;
    private int merchantId;
    private int type;
    private double originPrice;
    private double price;
    private int isCumulate;
    private int isBespeak;
    private Object endTime;
    private String consumeTime;
    private String targetDate;
    private int isAutomaticallyCancelAfterVerification;
    private String cancelAfterVerificationTime;
    private String applyRange;
    private String useRule;
    private Object images;
    private String groupPurchaseName;
    private String targetTime;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public Object getEndTime() {
        return endTime;
    }

    public void setEndTime(Object endTime) {
        this.endTime = endTime;
    }

    public String getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(String consumeTime) {
        this.consumeTime = consumeTime;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public int getIsAutomaticallyCancelAfterVerification() {
        return isAutomaticallyCancelAfterVerification;
    }

    public void setIsAutomaticallyCancelAfterVerification(int isAutomaticallyCancelAfterVerification) {
        this.isAutomaticallyCancelAfterVerification = isAutomaticallyCancelAfterVerification;
    }

    public String getCancelAfterVerificationTime() {
        return cancelAfterVerificationTime;
    }

    public void setCancelAfterVerificationTime(String cancelAfterVerificationTime) {
        this.cancelAfterVerificationTime = cancelAfterVerificationTime;
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

    public Object getImages() {
        return images;
    }

    public void setImages(Object images) {
        this.images = images;
    }

    public String getGroupPurchaseName() {
        return groupPurchaseName;
    }

    public void setGroupPurchaseName(String groupPurchaseName) {
        this.groupPurchaseName = groupPurchaseName;
    }

    public String getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(String targetTime) {
        this.targetTime = targetTime;
    }
}

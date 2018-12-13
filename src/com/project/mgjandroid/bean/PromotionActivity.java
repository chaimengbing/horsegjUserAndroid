package com.project.mgjandroid.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by yuandi on 2016/5/20.
 */
public class PromotionActivity implements Serializable {

    /**
     * "activityId": 14,
     * "promotionId": 3,
     * "agentId": 3,
     * "promoName": "满减优惠",
     * "promoType": 1,
     * "rule": "满60减20",
     * "discountAmt": 20,
     * "promoTip": "",
     * "promoImg": "http://7xu5hi.com1.z0.glb.clouddn.com/1611021611432865318.png",
     * "type": 0,
     * "presentGoodsName": null,
     * "activityType": 2,
     * "merchantBearAmt": 0,
     * "agentBearAmt": 20,
     * "startTime": null,
     * "endTime": null,
     * "promoValue": "-￥20",
     * "ruleDtoList": []
     */

    private Long activityId;

    private Long promotionId;

    private Long agentId;
    private Integer userLimit;

    private String promoName;

    private Integer promoType;
    private Integer activityType;

    private String rule;

    private BigDecimal discountAmt;

    private String promoTip;

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public String getPromoValue() {
        return promoValue;
    }

    public void setPromoValue(String promoValue) {
        this.promoValue = promoValue;
    }

    public BigDecimal getAgentBearAmt() {
        return agentBearAmt;
    }

    public void setAgentBearAmt(BigDecimal agentBearAmt) {
        this.agentBearAmt = agentBearAmt;
    }

    public BigDecimal getMerchantBearAmt() {
        return merchantBearAmt;
    }

    public void setMerchantBearAmt(BigDecimal merchantBearAmt) {
        this.merchantBearAmt = merchantBearAmt;
    }

    private String promoValue;
    private BigDecimal agentBearAmt;
    private BigDecimal merchantBearAmt;

    private String promoImg;
    private String presentGoodsName;
    /**
     * 满减
     */
    private List<FullSub> ruleDtoList;

    /**
     * 活动类型 0：减钱 1：赠送
     */
    private Integer type;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public Integer getPromoType() {
        return promoType;
    }

    public void setPromoType(Integer promoType) {
        this.promoType = promoType;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public BigDecimal getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(BigDecimal discountAmt) {
        this.discountAmt = discountAmt;
    }

    public String getPromoTip() {
        return promoTip;
    }

    public void setPromoTip(String promoTip) {
        this.promoTip = promoTip;
    }

    public String getPromoImg() {
        return promoImg;
    }

    public void setPromoImg(String promoImg) {
        this.promoImg = promoImg;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPresentGoodsName() {
        return presentGoodsName;
    }

    public void setPresentGoodsName(String presentGoodsName) {
        this.presentGoodsName = presentGoodsName;
    }

    public List<FullSub> getRuleDtoList() {
        return ruleDtoList;
    }

    public void setRuleDtoList(List<FullSub> ruleDtoList) {
        this.ruleDtoList = ruleDtoList;
    }

    public Integer getUserLimit() {
        return userLimit;
    }

    public void setUserLimit(Integer userLimit) {
        this.userLimit = userLimit;
    }
}

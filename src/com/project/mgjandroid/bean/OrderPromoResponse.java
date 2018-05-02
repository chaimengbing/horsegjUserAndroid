package com.project.mgjandroid.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yuandi on 2017/2/28.
 */

public class OrderPromoResponse implements Serializable {

    /**
     * 活动id
     **/
    private Long activityId;
    /**
     * 活动类型id
     **/
    private Long promotionId;
    /**
     * 该活动代理商
     **/
    private Long agentId;
    /**
     * 活动名称
     **/
    private String promoName;
    /**
     * 活动分类
     **/
    private Integer promoType;
    /**
     * 活动规则
     **/
    private String rule;
    /**
     * 优惠金额
     **/
    private BigDecimal discountAmt;
    /**
     * 活动提示
     **/
    private String promoTip = "";
    /**
     * 活动图片
     **/
    private String promoImg = "";
    /**
     * 活动类型 0：减钱，1：赠送
     **/
    private int type;
    /**
     * 赠送商品名
     **/
    private String presentGoodsName;
    /**
     * 1:首单，2：满减，3：满赠 ，4：(新)满减分摊
     **/
    private Integer activityType;
    /**
     * 商家承担金额
     **/
    private BigDecimal merchantBearAmt;
    /**
     * 代理商承担金额
     **/
    private BigDecimal agentBearAmt;
    /**
     * 商家优惠活动开始时间
     **/
    private Date startTime;
    /**
     * 商家优惠活动结束时间
     **/
    private Date endTime;
    /**
     * 促销内容
     **/
    private String promoValue;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPresentGoodsName() {
        return presentGoodsName;
    }

    public void setPresentGoodsName(String presentGoodsName) {
        this.presentGoodsName = presentGoodsName;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public BigDecimal getMerchantBearAmt() {
        return merchantBearAmt;
    }

    public void setMerchantBearAmt(BigDecimal merchantBearAmt) {
        this.merchantBearAmt = merchantBearAmt;
    }

    public BigDecimal getAgentBearAmt() {
        return agentBearAmt;
    }

    public void setAgentBearAmt(BigDecimal agentBearAmt) {
        this.agentBearAmt = agentBearAmt;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPromoValue() {
        return promoValue;
    }

    public void setPromoValue(String promoValue) {
        this.promoValue = promoValue;
    }
}

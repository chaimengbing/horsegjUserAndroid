package com.project.mgjandroid.bean.carhailing;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yuandi on 2017/1/5.
 */

public class CarRedBagActivity extends Entity {
    /**
     * 行程编号
     */
    private Long id;
    /**
     * 代理商编号
     **/
    private Long agentId;
    /**
     * 商家id
     **/
    private Long userId;
    /**
     * 司机id
     **/
    private Long chauffeurId;
    /**
     * 促销模板编号
     **/
    private Long promotionId;
    /**
     * 约车促销活动编号
     **/
    private Long carRedBagPromotionId;
    /**
     * 来源订单号
     **/
    private String fromOrderId;
    /**
     * 标题
     **/
    private String title;
    /**
     * 活动概述
     **/
    private String description;
    /**
     * 红包类型
     **/
    private Integer redBagType;
    /**
     * 促销类型
     **/
    private Integer promotionType;
    /**
     * 红包优惠类型
     **/
    private Integer redBagCouponType;
    /**
     * 红包数量
     **/
    private int redBagNum;
    /**
     * 已领取红包数量
     **/
    private int getedRedBagNum;
    /**
     * 折扣
     **/
    private BigDecimal startDiscountRate;

    private BigDecimal endDiscountRate;
    /**
     * 红包金额
     **/
    private BigDecimal redBagAmt;
    /** 领取规则 **/
    // private String obtainRule;
    /** 使用规则 **/
    // private String useRule;
    /**
     * 活动开始时间
     **/
    private Date startTime;
    /**
     * 活动结束时间
     **/
    private Date endTime;
    /**
     * 红包有效期
     **/
    private Integer validityDays;
    /**
     * 是否叠加 0:否,1:是
     **/
    private int isCumulate;
    /**
     * 是否排它0:否;1:排它（不能与其他红包活动一起使用）
     **/
    private int isExclusive;
    /**
     * 活动状态 0:待生效，1：生效中，2：已结束，3：提前结束
     **/
    private Integer status;
    /**
     * 分享地址
     **/
    private String shareUrl;
    /**
     * 创建时间
     **/
    private Date createTime;
    /**
     * 修改时间
     **/
    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChauffeurId() {
        return chauffeurId;
    }

    public void setChauffeurId(Long chauffeurId) {
        this.chauffeurId = chauffeurId;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Long getCarRedBagPromotionId() {
        return carRedBagPromotionId;
    }

    public void setCarRedBagPromotionId(Long carRedBagPromotionId) {
        this.carRedBagPromotionId = carRedBagPromotionId;
    }

    public String getFromOrderId() {
        return fromOrderId;
    }

    public void setFromOrderId(String fromOrderId) {
        this.fromOrderId = fromOrderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRedBagType() {
        return redBagType;
    }

    public void setRedBagType(Integer redBagType) {
        this.redBagType = redBagType;
    }

    public Integer getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(Integer promotionType) {
        this.promotionType = promotionType;
    }

    public Integer getRedBagCouponType() {
        return redBagCouponType;
    }

    public void setRedBagCouponType(Integer redBagCouponType) {
        this.redBagCouponType = redBagCouponType;
    }

    public int getRedBagNum() {
        return redBagNum;
    }

    public void setRedBagNum(int redBagNum) {
        this.redBagNum = redBagNum;
    }

    public int getGetedRedBagNum() {
        return getedRedBagNum;
    }

    public void setGetedRedBagNum(int getedRedBagNum) {
        this.getedRedBagNum = getedRedBagNum;
    }

    public BigDecimal getStartDiscountRate() {
        return startDiscountRate;
    }

    public void setStartDiscountRate(BigDecimal startDiscountRate) {
        this.startDiscountRate = startDiscountRate;
    }

    public BigDecimal getEndDiscountRate() {
        return endDiscountRate;
    }

    public void setEndDiscountRate(BigDecimal endDiscountRate) {
        this.endDiscountRate = endDiscountRate;
    }

    public BigDecimal getRedBagAmt() {
        return redBagAmt;
    }

    public void setRedBagAmt(BigDecimal redBagAmt) {
        this.redBagAmt = redBagAmt;
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

    public Integer getValidityDays() {
        return validityDays;
    }

    public void setValidityDays(Integer validityDays) {
        this.validityDays = validityDays;
    }

    public int getIsCumulate() {
        return isCumulate;
    }

    public void setIsCumulate(int isCumulate) {
        this.isCumulate = isCumulate;
    }

    public int getIsExclusive() {
        return isExclusive;
    }

    public void setIsExclusive(int isExclusive) {
        this.isExclusive = isExclusive;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }
}

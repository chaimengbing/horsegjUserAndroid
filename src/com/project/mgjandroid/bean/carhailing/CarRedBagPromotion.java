package com.project.mgjandroid.bean.carhailing;

import com.project.mgjandroid.bean.Agent;
import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yuandi on 2017/1/7.
 */

public class CarRedBagPromotion extends Entity {
    /**
     * 行程编号
     */
    private Long id;
    /**
     * 代理商id
     **/
    private Long agentId;
    /**
     * 活动类型id
     **/
    private Long promotionId;
    /**
     * 活动名称
     **/
    private String name;
    /**
     * 活动概述
     **/
    private String description;
    /**
     * 优惠折扣
     **/
    private BigDecimal startDiscountRate;
    /**
     * 优惠折扣
     **/
    private BigDecimal endDiscountRate;
    /**
     * 红包个数（单个分享红包总数）
     **/
    private Integer redBagNum;
    /**
     * 单日领取数
     **/
    private Integer daysGetRedBagNum;
    /**
     * 红包有效天数
     **/
    private Integer redBagValidDays;
    /**
     * 活动状态：0：关闭，1：开启，2:删除
     **/
    private Integer status;
    /**
     * 领取个数
     **/
    private int getedRedBagNum;
    /**
     * 使用个数
     **/
    private int usedRedBagNum;
    /**
     * 是否叠加 0:否,1:是
     **/
    private int isCumulate;
    /**
     * 是否排它0:否;1:排它（不能与其他红包活动一起使用）
     **/
    private int isExclusive;

    private Agent agent;
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

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getRedBagNum() {
        return redBagNum;
    }

    public void setRedBagNum(Integer redBagNum) {
        this.redBagNum = redBagNum;
    }

    public Integer getDaysGetRedBagNum() {
        return daysGetRedBagNum;
    }

    public void setDaysGetRedBagNum(Integer daysGetRedBagNum) {
        this.daysGetRedBagNum = daysGetRedBagNum;
    }

    public Integer getRedBagValidDays() {
        return redBagValidDays;
    }

    public void setRedBagValidDays(Integer redBagValidDays) {
        this.redBagValidDays = redBagValidDays;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getGetedRedBagNum() {
        return getedRedBagNum;
    }

    public void setGetedRedBagNum(int getedRedBagNum) {
        this.getedRedBagNum = getedRedBagNum;
    }

    public int getUsedRedBagNum() {
        return usedRedBagNum;
    }

    public void setUsedRedBagNum(int usedRedBagNum) {
        this.usedRedBagNum = usedRedBagNum;
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

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
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
}

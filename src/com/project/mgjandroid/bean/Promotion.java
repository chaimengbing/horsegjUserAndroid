package com.project.mgjandroid.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yuandi on 2017/2/28.
 */

public class Promotion implements Serializable {

    private Long id;

    private Date createTime;

    private Date modifyTime;

    private Long agentId;

    private Long promotionId;

    private String rules;

    private int status;

    private String title;

    private Agent agent;

    private Object promotion;

    private Object ruleList;

    private BigDecimal discountAmount;

    private String presentGoodsName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Object getPromotion() {
        return promotion;
    }

    public void setPromotion(Object promotion) {
        this.promotion = promotion;
    }

    public Object getRuleList() {
        return ruleList;
    }

    public void setRuleList(Object ruleList) {
        this.ruleList = ruleList;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getPresentGoodsName() {
        return presentGoodsName;
    }

    public void setPresentGoodsName(String presentGoodsName) {
        this.presentGoodsName = presentGoodsName;
    }
}

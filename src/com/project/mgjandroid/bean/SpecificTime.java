package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;

public class SpecificTime extends Entity {

    /**
     * id : 1
     * createTime : 2018-08-22 10:39:07
     * modifyTime : 2018-08-22 10:39:10
     * agentId : 3
     * deliveryTimeRange : 15:00-18:00
     * timeAddCharge : 1.0
     * hasDel : 0
     */

    private int id;
    private String createTime;
    private String modifyTime;
    private int agentId;
    private String deliveryTimeRange;
    private BigDecimal timeAddCharge;
    private int hasDel;

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

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getDeliveryTimeRange() {
        return deliveryTimeRange;
    }

    public void setDeliveryTimeRange(String deliveryTimeRange) {
        this.deliveryTimeRange = deliveryTimeRange;
    }

    public BigDecimal getTimeAddCharge() {
        return timeAddCharge;
    }

    public void setTimeAddCharge(BigDecimal timeAddCharge) {
        this.timeAddCharge = timeAddCharge;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }
}

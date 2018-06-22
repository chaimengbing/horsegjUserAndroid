package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;

public class FullSub extends Entity{

    private BigDecimal full;
    private BigDecimal sub;
    private Object present;
    private Object fullRange;
    private Object subRange;
    private int merchantBearAmt;
    private int agentBearAmt;

    public BigDecimal getFull() {
        return full;
    }

    public void setFull(BigDecimal full) {
        this.full = full;
    }

    public BigDecimal getSub() {
        return sub;
    }

    public void setSub(BigDecimal sub) {
        this.sub = sub;
    }

    public Object getPresent() {
        return present;
    }

    public void setPresent(Object present) {
        this.present = present;
    }

    public Object getFullRange() {
        return fullRange;
    }

    public void setFullRange(Object fullRange) {
        this.fullRange = fullRange;
    }

    public Object getSubRange() {
        return subRange;
    }

    public void setSubRange(Object subRange) {
        this.subRange = subRange;
    }

    public int getMerchantBearAmt() {
        return merchantBearAmt;
    }

    public void setMerchantBearAmt(int merchantBearAmt) {
        this.merchantBearAmt = merchantBearAmt;
    }

    public int getAgentBearAmt() {
        return agentBearAmt;
    }

    public void setAgentBearAmt(int agentBearAmt) {
        this.agentBearAmt = agentBearAmt;
    }
}

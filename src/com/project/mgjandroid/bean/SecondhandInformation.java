package com.project.mgjandroid.bean;

import com.project.mgjandroid.bean.information.InformationBaseProperty;
import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yuandi on 2016/7/11.
 */
public class SecondhandInformation extends InformationBaseProperty {
    private static final long serialVersionUID = 1L;
    /**
     * 类型（1：二手信息；2：求购）
     */
    private int type;
    /**
     * 价格 二手信息
     */
    private BigDecimal amt = BigDecimal.ZERO;
    /**
     * 最小价格 求购
     */
    private BigDecimal minAmt = BigDecimal.ZERO;
    /**
     * 最大价格 求购
     */
    private BigDecimal maxAmt = BigDecimal.ZERO;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public BigDecimal getMinAmt() {
        return minAmt;
    }

    public void setMinAmt(BigDecimal minAmt) {
        this.minAmt = minAmt;
    }

    public BigDecimal getMaxAmt() {
        return maxAmt;
    }

    public void setMaxAmt(BigDecimal maxAmt) {
        this.maxAmt = maxAmt;
    }

}


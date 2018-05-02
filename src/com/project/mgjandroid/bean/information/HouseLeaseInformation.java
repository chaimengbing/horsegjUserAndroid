package com.project.mgjandroid.bean.information;

import java.math.BigDecimal;

/**
 * Created by User_Cjh on 2017/1/4.
 */
public class HouseLeaseInformation extends InformationBaseProperty {
    private static final long serialVersionUID = 1L;
    /**
     * 类型（1：租赁；2：求租；）
     */
    private int type;
    /**
     * 户型
     */
    private String houseType;
    /**
     * 地段
     */
    private String sectorArea;
    /**
     * 房屋面积 租赁
     */
    private BigDecimal houseArea;
    /**
     * 价格 租赁
     */
    private BigDecimal amt = BigDecimal.ZERO;
    /**
     * 最小价格 求租
     */
    private BigDecimal minAmt = BigDecimal.ZERO;
    /**
     * 最大价格 求租
     */
    private BigDecimal maxAmt = BigDecimal.ZERO;

    private String leasePrice;

    public String getLeasePrice() {
        return leasePrice;
    }

    public void setLeasePrice(String leasePrice) {
        this.leasePrice = leasePrice;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getSectorArea() {
        return sectorArea;
    }

    public void setSectorArea(String sectorArea) {
        this.sectorArea = sectorArea;
    }

    public BigDecimal getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(BigDecimal houseArea) {
        this.houseArea = houseArea;
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

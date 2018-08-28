package com.project.mgjandroid.h5container.models;


import java.io.Serializable;

public class RedBagModel implements Serializable {

    /**
     * 代理商ID
     */
    private long agentId = 0l;
    /**
     * 支付金额
     */
    private double itemsPrice = 0;
    /**
     * 配送地址ID
     */
    private Long addressId;
    /**
     * 优惠金额(可不传)
     */
    private double discountGoodsDiscountAmt;
    /**
     * 业务类型
     */
    private int businessType = -1;
    /**
     * 优惠规则列表(可不传)
     */
    private String promoInfoJson;

    public long getAgentId() {
        return agentId;
    }

    public void setAgentId(long agentId) {
        this.agentId = agentId;
    }

    public double getItemsPrice() {
        return itemsPrice;
    }

    public void setItemsPrice(double itemsPrice) {
        this.itemsPrice = itemsPrice;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public double getDiscountGoodsDiscountAmt() {
        return discountGoodsDiscountAmt;
    }

    public void setDiscountGoodsDiscountAmt(double discountGoodsDiscountAmt) {
        this.discountGoodsDiscountAmt = discountGoodsDiscountAmt;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public String getPromoInfoJson() {
        return promoInfoJson;
    }

    public void setPromoInfoJson(String promoInfoJson) {
        this.promoInfoJson = promoInfoJson;
    }

    public long getPlatformRedbagId() {
        return platformRedbagId;
    }

    public void setPlatformRedbagId(long platformRedbagId) {
        this.platformRedbagId = platformRedbagId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(long merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * {"agentId":"3","platformRedbagId":16491,"businessType":11,"addressId":1630,"itemsPrice":30}
     * 选择红包ID
     */
    private long platformRedbagId = -1;
    /**
     * 购买数量
     */
    private int quantity = -1;
    /**
     * 商家ID
     */
    private long merchantId = -1;
}

package com.project.mgjandroid.bean.groupbuying;

import com.project.mgjandroid.bean.BaseBean;

import java.math.BigDecimal;

/**
 * Created by yuandi on 2017/3/11.
 */
public class GroupPurchaseCouponGoods extends BaseBean {

    /**
     * 团购券编号
     **/
    private Long groupPurchaseCouponId;
    /**
     * 代理商编号
     **/
    private Long agentId;
    /**
     * 商家编号
     */
    private Long merchantId;
    /** 商品类型名 */
    //private String typeName;
    /**
     * 名称
     */
    private String name;
    /**
     * 数量
     */
    private int quantity;
    /**
     * 原价
     */
    private BigDecimal originPrice = BigDecimal.ZERO;
    /**
     * 商品类型Id
     **/
    private Long groupPurchaseCouponGoodsTypeId;

    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }

    public Long getGroupPurchaseCouponId() {
        return groupPurchaseCouponId;
    }

    public void setGroupPurchaseCouponId(Long groupPurchaseCouponId) {
        this.groupPurchaseCouponId = groupPurchaseCouponId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getGroupPurchaseCouponGoodsTypeId() {
        return groupPurchaseCouponGoodsTypeId;
    }

    public void setGroupPurchaseCouponGoodsTypeId(Long groupPurchaseCouponGoodsTypeId) {
        this.groupPurchaseCouponGoodsTypeId = groupPurchaseCouponGoodsTypeId;
    }
}

package com.project.mgjandroid.bean.groupbuying;

import com.project.mgjandroid.bean.BaseBean;

import java.math.BigDecimal;

/**
 * Created by yuandi on 2017/3/16.
 */

public class GroupPurchaseOrderCouponGoods extends BaseBean {

    private String orderId;
    /**
     * 团购券编号
     **/
    private Long groupPurchaseCouponId;
    /**
     * 代理商编号
     **/
    private Long agentId;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 商家编号
     */
    private Long merchantId;
    /**
     * 商品类型名
     */
    private String typeName;
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
    /**
     * 排序编号，默认为0
     */
    private int sortNo;

    private String icon = "";
    /**
     * 类型描述
     **/
    private String describes;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }

    public Long getGroupPurchaseCouponGoodsTypeId() {
        return groupPurchaseCouponGoodsTypeId;
    }

    public void setGroupPurchaseCouponGoodsTypeId(Long groupPurchaseCouponGoodsTypeId) {
        this.groupPurchaseCouponGoodsTypeId = groupPurchaseCouponGoodsTypeId;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}

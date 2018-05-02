package com.project.mgjandroid.bean.groupbuying;

import com.project.mgjandroid.bean.BaseBean;

import java.util.List;

/**
 * Created by yuandi on 2017/3/16.
 */

public class GroupPurchaseCouponGoodsType extends BaseBean {

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
    /**
     * 商品类型名
     */
    private String typeName;
    /**
     * 排序编号，默认为0
     */
    private int sortNo;

    private String icon = "";
    /**
     * 类型描述
     **/
    private String describes;

    private List<GroupPurchaseCouponGoods> groupPurchaseCouponGoodsList;

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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public List<GroupPurchaseCouponGoods> getGroupPurchaseCouponGoodsList() {
        return groupPurchaseCouponGoodsList;
    }

    public void setGroupPurchaseCouponGoodsList(List<GroupPurchaseCouponGoods> groupPurchaseCouponGoodsList) {
        this.groupPurchaseCouponGoodsList = groupPurchaseCouponGoodsList;
    }
}

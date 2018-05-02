package com.project.mgjandroid.bean.newhomepage;

import com.project.mgjandroid.model.Entity;

/**
 * Created by SunXueLiang on 2017-09-08.
 */

public class GoodsDetail extends Entity {
    private int id;
    private String createTime;
    private String modifyTime;
    private int agentId;
    private int type;
    private Object merchantId;
    private Object groupPurchaseCouponType;
    private String goodsId;
    private int sortNo;
    private int hasDel;
    private int modifiedByAgentId;
    private Object modifiedByAgent;
    private Object agent;
    private Object merchantName;
    private Object goodsName;
    private GroupBuy goodsDetail;
    private Object takeawayGoodsId;
    private Object groupPurchaseGoodsId;
    private Object groupBuyGoodsId;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Object merchantId) {
        this.merchantId = merchantId;
    }

    public Object getGroupPurchaseCouponType() {
        return groupPurchaseCouponType;
    }

    public void setGroupPurchaseCouponType(Object groupPurchaseCouponType) {
        this.groupPurchaseCouponType = groupPurchaseCouponType;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public int getModifiedByAgentId() {
        return modifiedByAgentId;
    }

    public void setModifiedByAgentId(int modifiedByAgentId) {
        this.modifiedByAgentId = modifiedByAgentId;
    }

    public Object getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Object modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Object getAgent() {
        return agent;
    }

    public void setAgent(Object agent) {
        this.agent = agent;
    }

    public Object getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(Object merchantName) {
        this.merchantName = merchantName;
    }

    public Object getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(Object goodsName) {
        this.goodsName = goodsName;
    }

    public GroupBuy getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(GroupBuy goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public Object getTakeawayGoodsId() {
        return takeawayGoodsId;
    }

    public void setTakeawayGoodsId(Object takeawayGoodsId) {
        this.takeawayGoodsId = takeawayGoodsId;
    }

    public Object getGroupPurchaseGoodsId() {
        return groupPurchaseGoodsId;
    }

    public void setGroupPurchaseGoodsId(Object groupPurchaseGoodsId) {
        this.groupPurchaseGoodsId = groupPurchaseGoodsId;
    }

    public Object getGroupBuyGoodsId() {
        return groupBuyGoodsId;
    }

    public void setGroupBuyGoodsId(Object groupBuyGoodsId) {
        this.groupBuyGoodsId = groupBuyGoodsId;
    }

}

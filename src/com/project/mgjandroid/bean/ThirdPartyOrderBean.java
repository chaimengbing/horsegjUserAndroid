package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

/**
 * Created by User_Cjh on 2018/1/4.
 */

public class ThirdPartyOrderBean extends Entity {

    /**
     * "agentRateAmt": 0,
     * "commissionJson": {
     * "agentAccountId": 3,
     * "commissionJson": {
     * "partnerAgentRate": 10,
     * "provinceAgentRate": 10,
     * "sysRate": 10
     * },
     * "commissionRule": ""
     * },
     * "createTime": 1515398015000,
     * "description": "出发时间：2018-01-08 23:52:5",
     * "hasDel": 0,
     * "hasPayed": 0,
     * "id": 451,
     * "journeyId": 227,
     * "modifyTime": 1515398340000,
     * "orderFlowStatusStr": "取消订单",
     * "orderId": "1801080000148471",
     * "orderType": 1,
     * "partnerAgentRateAmt": 0,
     * "paymentState": 0,
     * "paymentType": 1,
     * "provinceAgentRateAmt": 0,
     * "settleStatus": 0,
     * "status": -1,
     * "sysRateAmt": 0,
     * "thirdpartyId": 227,
     * "title": "萝莉控哦 - 图摸摸看",
     * "totalPrice": 0.5,
     * "type": 7,
     * "typeStr": "顺风车",
     * "url": "http://112.74.18.147/sfc/build/index.html#/AnnounceDetail:227",
     * "userId": 903
     */
    private String title;
    private String description;
    private String picUrl;
    private String typeStr;
    private String orderFlowStatusStr;
    private int status;
    private String paymentExpireTime;
    private String url;
    private String type;
    private String serverTime;

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getOrderFlowStatusStr() {
        return orderFlowStatusStr;
    }

    public void setOrderFlowStatusStr(String orderFlowStatusStr) {
        this.orderFlowStatusStr = orderFlowStatusStr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPaymentExpireTime() {
        return paymentExpireTime;
    }

    public void setPaymentExpireTime(String paymentExpireTime) {
        this.paymentExpireTime = paymentExpireTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

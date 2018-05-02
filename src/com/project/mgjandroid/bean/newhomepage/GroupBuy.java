package com.project.mgjandroid.bean.newhomepage;

import com.project.mgjandroid.model.Entity;

import java.util.List;

/**
 * Created by SunXueLiang on 2017-09-08.
 */

public class GroupBuy extends Entity {
    private String createTime;
    private String modifyTime;
    private int userId;
    private int agentId;
    private String goodsName;
    private int minNum;
    private int maxNum;
    private int totalNum;
    private int totalUserNum;
    private double price;
    private double originalPrice;
    private Object totalPrice;
    private int days;
    private int deliveryDays;
    private String service;
    private String description;
    private Object marketingImg;
    private String imgs;
    private double agentRate;
    private double agentRateAmt;
    private double userAmt;
    private Object startTime;
    private Object defaultEndTime;
    private Object endTime;
    private int sortNo;
    private Object memo;
    private int status;
    private int hasDel;
    private int quotaCount;
    private List<CommontsAll> groupBuyUser;
    private String shareUrl;
    private Object groupBuyOrderList;
    private Object agentInfoMap;
    private List<?> otherGroupBuyList;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getMinNum() {
        return minNum;
    }

    public void setMinNum(int minNum) {
        this.minNum = minNum;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getTotalUserNum() {
        return totalUserNum;
    }

    public void setTotalUserNum(int totalUserNum) {
        this.totalUserNum = totalUserNum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Object getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Object totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getDeliveryDays() {
        return deliveryDays;
    }

    public void setDeliveryDays(int deliveryDays) {
        this.deliveryDays = deliveryDays;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getMarketingImg() {
        return marketingImg;
    }

    public void setMarketingImg(Object marketingImg) {
        this.marketingImg = marketingImg;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public double getAgentRate() {
        return agentRate;
    }

    public void setAgentRate(double agentRate) {
        this.agentRate = agentRate;
    }

    public double getAgentRateAmt() {
        return agentRateAmt;
    }

    public void setAgentRateAmt(double agentRateAmt) {
        this.agentRateAmt = agentRateAmt;
    }

    public double getUserAmt() {
        return userAmt;
    }

    public void setUserAmt(double userAmt) {
        this.userAmt = userAmt;
    }

    public Object getStartTime() {
        return startTime;
    }

    public void setStartTime(Object startTime) {
        this.startTime = startTime;
    }

    public Object getDefaultEndTime() {
        return defaultEndTime;
    }

    public void setDefaultEndTime(Object defaultEndTime) {
        this.defaultEndTime = defaultEndTime;
    }

    public Object getEndTime() {
        return endTime;
    }

    public void setEndTime(Object endTime) {
        this.endTime = endTime;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public Object getMemo() {
        return memo;
    }

    public void setMemo(Object memo) {
        this.memo = memo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public int getQuotaCount() {
        return quotaCount;
    }

    public void setQuotaCount(int quotaCount) {
        this.quotaCount = quotaCount;
    }

    public List<CommontsAll> getGroupBuyUser() {
        return groupBuyUser;
    }

    public void setGroupBuyUser(List<CommontsAll> groupBuyUser) {
        this.groupBuyUser = groupBuyUser;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public Object getGroupBuyOrderList() {
        return groupBuyOrderList;
    }

    public void setGroupBuyOrderList(Object groupBuyOrderList) {
        this.groupBuyOrderList = groupBuyOrderList;
    }

    public Object getAgentInfoMap() {
        return agentInfoMap;
    }

    public void setAgentInfoMap(Object agentInfoMap) {
        this.agentInfoMap = agentInfoMap;
    }

    public List<?> getOtherGroupBuyList() {
        return otherGroupBuyList;
    }

    public void setOtherGroupBuyList(List<?> otherGroupBuyList) {
        this.otherGroupBuyList = otherGroupBuyList;
    }
}

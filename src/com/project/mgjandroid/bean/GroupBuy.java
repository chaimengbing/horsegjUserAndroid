package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class GroupBuy extends Entity {
    private static final long serialVersionUID = 1L;
    private String id;
    /**
     * 用户编号
     **/
    private Long userId;
    /**
     * 代理商编号
     **/
    private Long agentId;
    /**
     * 商品名称
     **/
    private String goodsName;
    /**
     * 开团数量
     **/
    private Integer minNum = 0;
    /**
     * 最大数量
     **/
    private Integer maxNum = 0;
    /**
     * 拼团总数量
     **/
    private Integer totalNum = 0;
    /**
     * 拼团总人数
     **/
    private Integer totalUserNum = 0;
    /**
     * 拼团价格
     **/
    private BigDecimal price;
    /**
     * 原价格
     **/
    private BigDecimal originalPrice;
    /**
     * 拼团总价格
     **/
    private BigDecimal totalPrice;
    /**
     * 天数
     **/
    private Integer days = 0;
    /**
     * 发货时间(拼团成功后天数)
     **/
    private Integer deliveryDays = 0;
    /**
     * 服务
     **/
    private String service;
    /**
     * 商品描述
     **/
    private String description;
    /**
     * 营销图片
     **/
    private String marketingImg;
    /**
     * 商品图片
     **/
    private String imgs;
    /**
     * 代理商佣金比率
     */
    private BigDecimal agentRate = BigDecimal.ZERO;
    /**
     * 代理商佣金金额
     */
    private BigDecimal agentRateAmt = BigDecimal.ZERO;
    /**
     * 买手金额
     */
    private BigDecimal userAmt = BigDecimal.ZERO;
    /**
     * 开始时间
     **/
    private String startTime;
    /**
     * 默认结束时间
     **/
    private String defaultEndTime;
    /**
     * 结束时间
     **/
    private String endTime;
    /**
     * 排序编号
     */
    private Integer sortNo;
    private String memo;
    //(-1, "取消订单"),(0, "订单创建"),(1, "等待付款"),(2, "已支付,未成团"),(3, "待发货"),(4, "交易完成"),(5, "未成团,退款成功");
    private int status;
    /**
     * 是否删除
     */
    private int hasDel;
    private String shareUrl;
    private GroupBuyUser groupBuyUser;

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getMinNum() {
        return minNum;
    }

    public void setMinNum(Integer minNum) {
        this.minNum = minNum;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getDeliveryDays() {
        return deliveryDays;
    }

    public void setDeliveryDays(Integer deliveryDays) {
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

    public String getMarketingImg() {
        return marketingImg;
    }

    public void setMarketingImg(String marketingImg) {
        this.marketingImg = marketingImg;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public BigDecimal getAgentRate() {
        return agentRate;
    }

    public void setAgentRate(BigDecimal agentRate) {
        this.agentRate = agentRate;
    }

    public BigDecimal getAgentRateAmt() {
        return agentRateAmt;
    }

    public void setAgentRateAmt(BigDecimal agentRateAmt) {
        this.agentRateAmt = agentRateAmt;
    }

    public BigDecimal getUserAmt() {
        return userAmt;
    }

    public void setUserAmt(BigDecimal userAmt) {
        this.userAmt = userAmt;
    }

    public Integer getTotalUserNum() {
        return totalUserNum;
    }

    public void setTotalUserNum(Integer totalUserNum) {
        this.totalUserNum = totalUserNum;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDefaultEndTime() {
        return defaultEndTime;
    }

    public void setDefaultEndTime(String defaultEndTime) {
        this.defaultEndTime = defaultEndTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
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

    public GroupBuyUser getGroupBuyUser() {
        return groupBuyUser;
    }

    public void setGroupBuyUser(GroupBuyUser groupBuyUser) {
        this.groupBuyUser = groupBuyUser;
    }

}
package com.project.mgjandroid.bean.information;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yuandi on 2016/11/16.
 */

public class InformationOrder extends Entity {

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
     * 信息编号
     **/
    private Long informationId;
    /**
     * 信息类型 {@link InformationType}
     **/
    private Integer informationType;
    /**
     * 服务分类编号
     **/
    private Long serviceCategoryId;
    /**
     * 信息所属分类编号
     **/
    private Long categoryId;
    /**
     * 信息分类
     **/
    private Integer type;
    /**
     * 订单类型 {@link OrderType}
     **/
    private Integer orderType;
    /**
     * 收费标准id
     **/
    private Long freeStandardId;
    /**
     * 交易编号
     */
    private String transactionId;
    /**
     * 信息发布天数
     **/
    private Integer days;
    /**
     * 商品价格
     */
    private BigDecimal price = BigDecimal.ZERO;
    /**
     * 商品原价
     */
    private BigDecimal originalPrice = BigDecimal.ZERO;
    /**
     * 优惠前价格
     */
    private BigDecimal originalTotalPrice = BigDecimal.ZERO;
    /**
     * 最终价格
     */
    private BigDecimal totalPrice = BigDecimal.ZERO;
    /**
     * 支付类型 {PaymentType}
     */
    private Integer paymentType;
    /**
     * 付款状态 0:未完成，1：已完成
     */
    private Integer paymentState = 0;
    /**
     * 支付过期时间
     */
    private Date paymentExpireTime;
    /**
     * 审核过期时间
     */
    private Date checkExpireTime;
    /**
     * 已支付金额
     */
    private BigDecimal hasPayed = BigDecimal.ZERO;
    /**
     * 订单状态 {InformationOrderStatus}
     **/
    private Integer status;
    /**
     * 是否删除
     */
    private int hasDel;
    /**
     * 代理商结算状态 0：未结算，1：已结算
     */
    private int agentSettleState = 0;
    /**
     * 代理商佣金比率
     */
    private BigDecimal agentRate = BigDecimal.ZERO;
    /**
     * 代理商佣金金额
     */
    private BigDecimal agentRateAmt = BigDecimal.ZERO;
    /**
     * 代理商结算时间
     **/
    private Date agentSettleTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 标题
     */
    private String title;
    private String serviceTime;

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

    public Long getInformationId() {
        return informationId;
    }

    public void setInformationId(Long informationId) {
        this.informationId = informationId;
    }

    public Integer getInformationType() {
        return informationType;
    }

    public void setInformationType(Integer informationType) {
        this.informationType = informationType;
    }

    public Long getServiceCategoryId() {
        return serviceCategoryId;
    }

    public void setServiceCategoryId(Long serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Long getFreeStandardId() {
        return freeStandardId;
    }

    public void setFreeStandardId(Long freeStandardId) {
        this.freeStandardId = freeStandardId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
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

    public BigDecimal getOriginalTotalPrice() {
        return originalTotalPrice;
    }

    public void setOriginalTotalPrice(BigDecimal originalTotalPrice) {
        this.originalTotalPrice = originalTotalPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(Integer paymentState) {
        this.paymentState = paymentState;
    }

    public Date getPaymentExpireTime() {
        return paymentExpireTime;
    }

    public void setPaymentExpireTime(Date paymentExpireTime) {
        this.paymentExpireTime = paymentExpireTime;
    }

    public Date getCheckExpireTime() {
        return checkExpireTime;
    }

    public void setCheckExpireTime(Date checkExpireTime) {
        this.checkExpireTime = checkExpireTime;
    }

    public BigDecimal getHasPayed() {
        return hasPayed;
    }

    public void setHasPayed(BigDecimal hasPayed) {
        this.hasPayed = hasPayed;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public int getAgentSettleState() {
        return agentSettleState;
    }

    public void setAgentSettleState(int agentSettleState) {
        this.agentSettleState = agentSettleState;
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

    public Date getAgentSettleTime() {
        return agentSettleTime;
    }

    public void setAgentSettleTime(Date agentSettleTime) {
        this.agentSettleTime = agentSettleTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }
}

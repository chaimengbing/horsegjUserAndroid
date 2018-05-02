package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class GroupBuyOrder extends Entity {
    private static final long serialVersionUID = 1L;
    private String id;
    private String createTime;

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String modifyTime;
    /**
     * 拼团编号
     **/
    private String groupBuyId;
    /**
     * 代理商编号
     **/
    private Long agentId;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 买手结算状态 0：未结算，1：已结算
     */
    private int userSettleState = 0;
    /**
     * 代理商结算状态 0：未结算，1：已结算
     */
    private int agentSettleState = 0;
    /**
     * 默认结算时间
     **/
    private String defSettleTime;
    /**
     * 系统默认评价时间
     **/
    private String defCommontsTime;
    /**
     * 用户配送地址编号
     */
    private Long userAddressId;
    private String userAddress = "";
    /**
     * 门牌楼号
     */
    private String userHouseNumber = "";
    /**
     * 姓名
     */
    private String userName = "";
    /**
     * 性别
     */
    private String userGender = "";
    private String userMobile = "";
    private String userBackupMobile = "";
    /**
     * 经度
     */
    private Double userLongitude;
    /**
     * 维度
     */
    private Double userLatitude;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品备注
     */
    private String caution;
    /**
     * 商品原价
     */
    private BigDecimal originPrice = BigDecimal.ZERO;
    /**
     * 商品原总价格
     */
    private BigDecimal totalOriginPrice = BigDecimal.ZERO;
    /**
     * 商品价格
     */
    private BigDecimal price = BigDecimal.ZERO;
    /**
     * 商品总价格
     */
    private BigDecimal totalPrice = BigDecimal.ZERO;
    /**
     * 商品数量
     */
    private Integer quantity;
    /**
     * 交易编号
     */
    private String transactionId;
    /**
     * 支付类型
     */
    private Integer paymentType;
    /**
     * 付款状态 0:未完成，1：已完成
     */
    private Integer paymentState = 0;
    /**
     * 支付过期时间
     */
    private String paymentExpireTime;
    /**
     * 已支付金额
     */
    private BigDecimal hasPayed = BigDecimal.ZERO;
    /**
     * 订单状态
     */
    private Integer status;
    /**
     * 是否删除
     */
    private int hasDel;
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
    private GroupBuy groupBuy;
    private int hasComments;

    public int getHasComments() {
        return hasComments;
    }

    public void setHasComments(int hasComments) {
        this.hasComments = hasComments;
    }

    public String getGroupBuyId() {
        return groupBuyId;
    }

    public void setGroupBuyId(String groupBuyId) {
        this.groupBuyId = groupBuyId;
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

    public int getUserSettleState() {
        return userSettleState;
    }

    public void setUserSettleState(int userSettleState) {
        this.userSettleState = userSettleState;
    }

    public int getAgentSettleState() {
        return agentSettleState;
    }

    public void setAgentSettleState(int agentSettleState) {
        this.agentSettleState = agentSettleState;
    }

    public Long getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(Long userAddressId) {
        this.userAddressId = userAddressId;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserHouseNumber() {
        return userHouseNumber;
    }

    public void setUserHouseNumber(String userHouseNumber) {
        this.userHouseNumber = userHouseNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserBackupMobile() {
        return userBackupMobile;
    }

    public void setUserBackupMobile(String userBackupMobile) {
        this.userBackupMobile = userBackupMobile;
    }

    public Double getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(Double userLongitude) {
        this.userLongitude = userLongitude;
    }

    public Double getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(Double userLatitude) {
        this.userLatitude = userLatitude;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution;
    }

    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }

    public BigDecimal getTotalOriginPrice() {
        return totalOriginPrice;
    }

    public void setTotalOriginPrice(BigDecimal totalOriginPrice) {
        this.totalOriginPrice = totalOriginPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    public String getPaymentExpireTime() {
        return paymentExpireTime;
    }

    public void setPaymentExpireTime(String paymentExpireTime) {
        this.paymentExpireTime = paymentExpireTime;
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

    public GroupBuy getGroupBuy() {
        return groupBuy;
    }

    public void setGroupBuy(GroupBuy groupBuy) {
        this.groupBuy = groupBuy;
    }

    public String getDefSettleTime() {
        return defSettleTime;
    }

    public void setDefSettleTime(String defSettleTime) {
        this.defSettleTime = defSettleTime;
    }

    public String getDefCommontsTime() {
        return defCommontsTime;
    }

    public void setDefCommontsTime(String defCommontsTime) {
        this.defCommontsTime = defCommontsTime;
    }

}
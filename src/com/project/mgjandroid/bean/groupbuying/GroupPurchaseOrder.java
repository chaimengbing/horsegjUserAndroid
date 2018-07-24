package com.project.mgjandroid.bean.groupbuying;

import com.project.mgjandroid.bean.Agent;
import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by yuandi on 2017/3/11.
 */

public class GroupPurchaseOrder extends Entity {

    private String id;

    private String createTime;

    private String modifyTime;
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
     * 用户手机号
     */
    private String userMobile;
    /**
     * 类型 {GroupPurchaseCouponType}
     */
    private int groupPurchaseCouponType;
    /**
     * 商家结算类型1：在线结算，2：线下结算。{SettleMode}
     */
    private int merchantSettleMode;
    /**
     * 商家结算状态 0：未结算，1：已结算
     */
    private int merchantSettleState = 0;
    /**
     * 代理商结算状态 0：未结算，1：已结算
     */
    private int agentSettleState = 0;
    /**
     * 默认结算时间
     **/
    private Date defSettleTime;
    /**
     * 系统默认评价时间
     **/
    private Date defCommontsTime;
    /**
     * 代金/团购券有效期
     **/
    private String groupPurchaseCouponEndTime;
    /**
     * 图片
     */
    private String groupPurchaseCouponImages;
    /**
     * 原价
     */
    private BigDecimal originalPrice = BigDecimal.ZERO;
    /**
     * 原总价格
     */
    private BigDecimal totalOriginalPrice = BigDecimal.ZERO;
    /**
     * 价格
     */
    private BigDecimal price = BigDecimal.ZERO;
    /**
     * 总价格
     */
    private BigDecimal totalPrice = BigDecimal.ZERO;
    /**
     * 数量
     */
    private int quantity;
    /**
     * 退款数量
     */
    private int refundQuantity;
    /**
     * 可用数量
     */
    private int usableQuantity;
    /**
     * 使用数量
     */
    private int useQuantity;
    /**
     * 交易编号
     */
    private String transactionId;
    /**
     * 支付类型 {PaymentType}
     * 1.在线支付
     * 2.货到付款
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
     * 订单状态 {@link GroupPurchaseOrderStatus}
     */
    private Integer status;
    /**
     * 是否删除
     */
    private int hasDel;
    /**
     * 系统佣金比率
     */
    private BigDecimal sysRate = BigDecimal.ZERO;
    /**
     * 系统佣金金额
     */
    private BigDecimal sysRateAmt = BigDecimal.ZERO;
    /**
     * 代理商佣金比率
     */
    private BigDecimal agentRate = BigDecimal.ZERO;
    /**
     * 代理商佣金金额
     */
    private BigDecimal agentRateAmt = BigDecimal.ZERO;
    /**
     * 省级代理商佣金比率
     */
    private BigDecimal provinceAgentRate = BigDecimal.ZERO;
    /**
     * 省级代理商佣金金额
     */
    private BigDecimal provinceAgentRateAmt = BigDecimal.ZERO;
    /**
     * 商家金额
     */
    private BigDecimal merchantAmt = BigDecimal.ZERO;
    /**
     * 是否有评价
     */
    private Integer hasComments = 0;
    /**
     * 支付完成时间
     **/
    private Date payDoneTime;
    /**
     * 订单完成时间
     **/
    private Date orderDoneTime;
    /**
     * 取消原因
     */
    private String cancelReason;
    /**
     * 取消来源
     */
    private String cancelSource;
    /**
     * 团购名称
     **/
    private String groupPurchaseName;
    /**
     * 优惠买单 3
     **/
    private int orderType;
    private BigDecimal redBagDiscountTotalAmt;
    /** 折扣优惠总金额 **/
    private BigDecimal discountAmt = BigDecimal.ZERO;
    /** 代金券总金额 */
    private BigDecimal cashDeductionPrice = BigDecimal.ZERO;
    private String groupPurchaseMerchantImg;
    private String groupPurchaseMerchantContacts;

    private GroupPurchaseCoupon groupPurchaseCoupon;

    private GroupPurchaseMerchant groupPurchaseMerchant;

    private Agent agent;

    private String groupPurchaseMerchantName;

    private List<GroupPurchaseOrderCouponCode> groupPurchaseOrderCouponCodeList;

    private List<GroupPurchaseOrderCouponGoods> groupPurchaseOrderCouponGoodsList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public int getGroupPurchaseCouponType() {
        return groupPurchaseCouponType;
    }

    public void setGroupPurchaseCouponType(int groupPurchaseCouponType) {
        this.groupPurchaseCouponType = groupPurchaseCouponType;
    }

    public int getMerchantSettleMode() {
        return merchantSettleMode;
    }

    public void setMerchantSettleMode(int merchantSettleMode) {
        this.merchantSettleMode = merchantSettleMode;
    }

    public int getMerchantSettleState() {
        return merchantSettleState;
    }

    public void setMerchantSettleState(int merchantSettleState) {
        this.merchantSettleState = merchantSettleState;
    }

    public int getAgentSettleState() {
        return agentSettleState;
    }

    public void setAgentSettleState(int agentSettleState) {
        this.agentSettleState = agentSettleState;
    }

    public Date getDefSettleTime() {
        return defSettleTime;
    }

    public void setDefSettleTime(Date defSettleTime) {
        this.defSettleTime = defSettleTime;
    }

    public Date getDefCommontsTime() {
        return defCommontsTime;
    }

    public void setDefCommontsTime(Date defCommontsTime) {
        this.defCommontsTime = defCommontsTime;
    }

    public String getGroupPurchaseCouponEndTime() {
        return groupPurchaseCouponEndTime;
    }

    public void setGroupPurchaseCouponEndTime(String groupPurchaseCouponEndTime) {
        this.groupPurchaseCouponEndTime = groupPurchaseCouponEndTime;
    }

    public String getGroupPurchaseCouponImages() {
        return groupPurchaseCouponImages;
    }

    public void setGroupPurchaseCouponImages(String groupPurchaseCouponImages) {
        this.groupPurchaseCouponImages = groupPurchaseCouponImages;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getTotalOriginalPrice() {
        return totalOriginalPrice;
    }

    public void setTotalOriginalPrice(BigDecimal totalOriginalPrice) {
        this.totalOriginalPrice = totalOriginalPrice;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getRefundQuantity() {
        return refundQuantity;
    }

    public void setRefundQuantity(int refundQuantity) {
        this.refundQuantity = refundQuantity;
    }

    public int getUsableQuantity() {
        return usableQuantity;
    }

    public void setUsableQuantity(int usableQuantity) {
        this.usableQuantity = usableQuantity;
    }

    public int getUseQuantity() {
        return useQuantity;
    }

    public void setUseQuantity(int useQuantity) {
        this.useQuantity = useQuantity;
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

    public BigDecimal getSysRate() {
        return sysRate;
    }

    public void setSysRate(BigDecimal sysRate) {
        this.sysRate = sysRate;
    }

    public BigDecimal getSysRateAmt() {
        return sysRateAmt;
    }

    public void setSysRateAmt(BigDecimal sysRateAmt) {
        this.sysRateAmt = sysRateAmt;
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

    public BigDecimal getProvinceAgentRate() {
        return provinceAgentRate;
    }

    public void setProvinceAgentRate(BigDecimal provinceAgentRate) {
        this.provinceAgentRate = provinceAgentRate;
    }

    public BigDecimal getProvinceAgentRateAmt() {
        return provinceAgentRateAmt;
    }

    public void setProvinceAgentRateAmt(BigDecimal provinceAgentRateAmt) {
        this.provinceAgentRateAmt = provinceAgentRateAmt;
    }

    public BigDecimal getMerchantAmt() {
        return merchantAmt;
    }

    public void setMerchantAmt(BigDecimal merchantAmt) {
        this.merchantAmt = merchantAmt;
    }

    public Integer getHasComments() {
        return hasComments;
    }

    public void setHasComments(Integer hasComments) {
        this.hasComments = hasComments;
    }

    public Date getPayDoneTime() {
        return payDoneTime;
    }

    public void setPayDoneTime(Date payDoneTime) {
        this.payDoneTime = payDoneTime;
    }

    public Date getOrderDoneTime() {
        return orderDoneTime;
    }

    public void setOrderDoneTime(Date orderDoneTime) {
        this.orderDoneTime = orderDoneTime;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getCancelSource() {
        return cancelSource;
    }

    public void setCancelSource(String cancelSource) {
        this.cancelSource = cancelSource;
    }

    public String getGroupPurchaseName() {
        return groupPurchaseName;
    }

    public void setGroupPurchaseName(String groupPurchaseName) {
        this.groupPurchaseName = groupPurchaseName;
    }

    public GroupPurchaseCoupon getGroupPurchaseCoupon() {
        return groupPurchaseCoupon;
    }

    public void setGroupPurchaseCoupon(GroupPurchaseCoupon groupPurchaseCoupon) {
        this.groupPurchaseCoupon = groupPurchaseCoupon;
    }

    public GroupPurchaseMerchant getGroupPurchaseMerchant() {
        return groupPurchaseMerchant;
    }

    public void setGroupPurchaseMerchant(GroupPurchaseMerchant groupPurchaseMerchant) {
        this.groupPurchaseMerchant = groupPurchaseMerchant;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public String getGroupPurchaseMerchantName() {
        return groupPurchaseMerchantName;
    }

    public void setGroupPurchaseMerchantName(String groupPurchaseMerchantName) {
        this.groupPurchaseMerchantName = groupPurchaseMerchantName;
    }

    public List<GroupPurchaseOrderCouponCode> getGroupPurchaseOrderCouponCodeList() {
        return groupPurchaseOrderCouponCodeList;
    }

    public void setGroupPurchaseOrderCouponCodeList(List<GroupPurchaseOrderCouponCode> groupPurchaseOrderCouponCodeList) {
        this.groupPurchaseOrderCouponCodeList = groupPurchaseOrderCouponCodeList;
    }

    public List<GroupPurchaseOrderCouponGoods> getGroupPurchaseOrderCouponGoodsList() {
        return groupPurchaseOrderCouponGoodsList;
    }

    public void setGroupPurchaseOrderCouponGoodsList(List<GroupPurchaseOrderCouponGoods> groupPurchaseOrderCouponGoodsList) {
        this.groupPurchaseOrderCouponGoodsList = groupPurchaseOrderCouponGoodsList;
    }

    /**
     * 查询类别
     **/
    private int queryType;
    /**
     * 刷新间隔时间
     **/
    private int refreshTime;

    public int getQueryType() {
        return queryType;
    }

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }

    public int getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(int refreshTime) {
        this.refreshTime = refreshTime;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public BigDecimal getRedBagDiscountTotalAmt() {
        return redBagDiscountTotalAmt;
    }

    public void setRedBagDiscountTotalAmt(BigDecimal redBagDiscountTotalAmt) {
        this.redBagDiscountTotalAmt = redBagDiscountTotalAmt;
    }

    public BigDecimal getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(BigDecimal discountAmt) {
        this.discountAmt = discountAmt;
    }

    public BigDecimal getCashDeductionPrice() {
        return cashDeductionPrice;
    }

    public void setCashDeductionPrice(BigDecimal cashDeductionPrice) {
        this.cashDeductionPrice = cashDeductionPrice;
    }

    public String getGroupPurchaseMerchantImg() {
        return groupPurchaseMerchantImg;
    }

    public void setGroupPurchaseMerchantImg(String groupPurchaseMerchantImg) {
        this.groupPurchaseMerchantImg = groupPurchaseMerchantImg;
    }

    public String getGroupPurchaseMerchantContacts() {
        return groupPurchaseMerchantContacts;
    }

    public void setGroupPurchaseMerchantContacts(String groupPurchaseMerchantContacts) {
        this.groupPurchaseMerchantContacts = groupPurchaseMerchantContacts;
    }
}

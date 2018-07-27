package com.project.mgjandroid.bean.groupbuying;

import com.project.mgjandroid.bean.Agent;
import com.project.mgjandroid.bean.BaseBean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yuandi on 2017/3/14.
 */

public class GroupPurchaseOrderCouponCode extends BaseBean {

    /**
     * 团购券编号
     **/
    private Long groupPurchaseCouponId;
    /**
     * 团购订单编号
     **/
    private String groupPurchaseOrderId;
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
     * 类型
     */
    private int groupPurchaseCouponType;
    /**
     * 券验证码
     */
    private String couponCode;
    /**
     * 过期时间
     */
    private Date expireTime;

    private Date endTime;
    /**
     * 使用时间
     */
    private Date usingTime;
    /**
     * 是否过期 0:未过期,1:已过期
     */
    private int isExpire;
    /**
     * 状态 0:未使用,1:已使用,2:已退款
     */
    private int status;
    /**
     * 抵扣价
     */
    private BigDecimal originPrice = BigDecimal.ZERO;
    /**
     * 售卖价
     */
    private BigDecimal price = BigDecimal.ZERO;
    /**
     * 是否叠加 0:否,1:是
     **/
    private int isCumulate;
    /**
     * 是否预约 0:否,1:是
     **/
    private int isBespeak;
    /**
     * 消费时间
     **/
    private String consumeTime;
    /**
     * 适用范围
     */
    private String applyRange;
    /**
     * 使用规则
     */
    private String useRule;
    /**
     * 图片
     */
    private String images;
    /**
     * 商家结算状态 0：未结算，1：已结算
     */
    private int merchantSettleState = 0;
    /**
     * 代理商结算状态 0：未结算，1：已结算
     */
    private int agentSettleState = 0;
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

    private String groupPurchaseCouponTypeName;

    private GroupPurchaseCoupon groupPurchaseCoupon;

    private Agent agent;

    private GroupPurchaseMerchant groupPurchaseMerchant;

    private GroupPurchaseOrder groupPurchaseOrder;

    private String groupPurchaseName;

    private boolean isSelected;
    /**
     * 预约日期
     */
    private Date targetDate;
    /**
     * 是否核销 1核销 2不核销
     */
    private int isAutomaticallyCancelAfterVerification;
    /**
     * 核销时间
     */
    private String cancelAfterVerificationTime;

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public int getIsAutomaticallyCancelAfterVerification() {
        return isAutomaticallyCancelAfterVerification;
    }

    public void setIsAutomaticallyCancelAfterVerification(int isAutomaticallyCancelAfterVerification) {
        this.isAutomaticallyCancelAfterVerification = isAutomaticallyCancelAfterVerification;
    }

    public String getCancelAfterVerificationTime() {
        return cancelAfterVerificationTime;
    }

    public void setCancelAfterVerificationTime(String cancelAfterVerificationTime) {
        this.cancelAfterVerificationTime = cancelAfterVerificationTime;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Long getGroupPurchaseCouponId() {
        return groupPurchaseCouponId;
    }

    public void setGroupPurchaseCouponId(Long groupPurchaseCouponId) {
        this.groupPurchaseCouponId = groupPurchaseCouponId;
    }

    public String getGroupPurchaseOrderId() {
        return groupPurchaseOrderId;
    }

    public void setGroupPurchaseOrderId(String groupPurchaseOrderId) {
        this.groupPurchaseOrderId = groupPurchaseOrderId;
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

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getUsingTime() {
        return usingTime;
    }

    public void setUsingTime(Date usingTime) {
        this.usingTime = usingTime;
    }

    public int getIsExpire() {
        return isExpire;
    }

    public void setIsExpire(int isExpire) {
        this.isExpire = isExpire;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getIsCumulate() {
        return isCumulate;
    }

    public void setIsCumulate(int isCumulate) {
        this.isCumulate = isCumulate;
    }

    public int getIsBespeak() {
        return isBespeak;
    }

    public void setIsBespeak(int isBespeak) {
        this.isBespeak = isBespeak;
    }

    public String getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(String consumeTime) {
        this.consumeTime = consumeTime;
    }

    public String getApplyRange() {
        return applyRange;
    }

    public void setApplyRange(String applyRange) {
        this.applyRange = applyRange;
    }

    public String getUseRule() {
        return useRule;
    }

    public void setUseRule(String useRule) {
        this.useRule = useRule;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
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

    public String getGroupPurchaseCouponTypeName() {
        return groupPurchaseCouponTypeName;
    }

    public void setGroupPurchaseCouponTypeName(String groupPurchaseCouponTypeName) {
        this.groupPurchaseCouponTypeName = groupPurchaseCouponTypeName;
    }

    public GroupPurchaseCoupon getGroupPurchaseCoupon() {
        return groupPurchaseCoupon;
    }

    public void setGroupPurchaseCoupon(GroupPurchaseCoupon groupPurchaseCoupon) {
        this.groupPurchaseCoupon = groupPurchaseCoupon;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public GroupPurchaseMerchant getGroupPurchaseMerchant() {
        return groupPurchaseMerchant;
    }

    public void setGroupPurchaseMerchant(GroupPurchaseMerchant groupPurchaseMerchant) {
        this.groupPurchaseMerchant = groupPurchaseMerchant;
    }

    public String getGroupPurchaseName() {
        return groupPurchaseName;
    }

    public void setGroupPurchaseName(String groupPurchaseName) {
        this.groupPurchaseName = groupPurchaseName;
    }
}

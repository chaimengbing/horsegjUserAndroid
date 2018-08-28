package com.project.mgjandroid.bean.groupbuying;

import com.project.mgjandroid.bean.BaseBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by yuandi on 2017/3/11.
 */

public class GroupPurchaseCoupon extends BaseBean {

    /**
     * 代理商编号
     **/
    private Long agentId;
    /**
     * 商家编号
     */
    private Long merchantId;
    /**
     * 类型 1, 代金券  2， 团购券
     */
    private int type;
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
     * 有效期
     **/
    private String endTime;
    /**
     * 下线日期
     **/
    private Date offlineTime;
    /**
     * 状态(1:上线;2:下线)
     **/
    private int status;
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
     * 是否删除 0:否,1:是
     **/
    private int hasDel;
    /**
     * 累积售出
     **/
    private int accumulateSoldCount;
    /**
     * 累积使用
     **/
    private int accumulateUseCount;
    /**
     * 累积退款
     **/
    private int accumulateRefundCount;
    /**
     * 创建角色 1:管理员;2:代理商;3:商家
     **/
    private int createRole;
    /**
     * 团购名称
     **/
    private String groupPurchaseName;
    /**
     * 已售
     **/
    private int buyCount;
    /**
     * 可预约天数
     **/
    private int bespeakDayCount;

    private BigDecimal sumGroupPurchaseCouponGoodsOriginPrice;

    private List<GroupPurchaseCouponGoodsType> groupPurchaseCouponGoodsTypeList;

    private GroupPurchaseMerchant groupPurchaseMerchant;

    private String shareUrl;
    /**
     * 1 核销 2 不核销
     */
    private int isAutomaticallyCancelAfterVerification;
    private String cancelAfterVerificationTime;
    /**
     * 预约日期
     */
    private String targetTime;
    /**
     * 新用户专享
     */
    private int isPurchaseRestriction;
    /**
     * 设置售罄日期
     */
    private String sellOutDates;

    public String getSellOutDates() {
        return sellOutDates;
    }

    public void setSellOutDates(String sellOutDates) {
        this.sellOutDates = sellOutDates;
    }

    public int getIsPurchaseRestriction() {
        return isPurchaseRestriction;
    }

    public void setIsPurchaseRestriction(int isPurchaseRestriction) {
        this.isPurchaseRestriction = isPurchaseRestriction;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Date getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(Date offlineTime) {
        this.offlineTime = offlineTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public int getAccumulateSoldCount() {
        return accumulateSoldCount;
    }

    public void setAccumulateSoldCount(int accumulateSoldCount) {
        this.accumulateSoldCount = accumulateSoldCount;
    }

    public int getAccumulateUseCount() {
        return accumulateUseCount;
    }

    public void setAccumulateUseCount(int accumulateUseCount) {
        this.accumulateUseCount = accumulateUseCount;
    }

    public int getAccumulateRefundCount() {
        return accumulateRefundCount;
    }

    public void setAccumulateRefundCount(int accumulateRefundCount) {
        this.accumulateRefundCount = accumulateRefundCount;
    }

    public int getCreateRole() {
        return createRole;
    }

    public void setCreateRole(int createRole) {
        this.createRole = createRole;
    }

    public String getGroupPurchaseName() {
        return groupPurchaseName;
    }

    public void setGroupPurchaseName(String groupPurchaseName) {
        this.groupPurchaseName = groupPurchaseName;
    }

    public BigDecimal getSumGroupPurchaseCouponGoodsOriginPrice() {
        return sumGroupPurchaseCouponGoodsOriginPrice;
    }

    public void setSumGroupPurchaseCouponGoodsOriginPrice(BigDecimal sumGroupPurchaseCouponGoodsOriginPrice) {
        this.sumGroupPurchaseCouponGoodsOriginPrice = sumGroupPurchaseCouponGoodsOriginPrice;
    }

    public List<GroupPurchaseCouponGoodsType> getGroupPurchaseCouponGoodsTypeList() {
        return groupPurchaseCouponGoodsTypeList;
    }

    public void setGroupPurchaseCouponGoodsTypeList(List<GroupPurchaseCouponGoodsType> groupPurchaseCouponGoodsTypeList) {
        this.groupPurchaseCouponGoodsTypeList = groupPurchaseCouponGoodsTypeList;
    }

    public GroupPurchaseMerchant getGroupPurchaseMerchant() {
        return groupPurchaseMerchant;
    }

    public void setGroupPurchaseMerchant(GroupPurchaseMerchant groupPurchaseMerchant) {
        this.groupPurchaseMerchant = groupPurchaseMerchant;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public int getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
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

    public String getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(String targetTime) {
        this.targetTime = targetTime;
    }

    public int getBespeakDayCount() {
        return bespeakDayCount;
    }

    public void setBespeakDayCount(int bespeakDayCount) {
        this.bespeakDayCount = bespeakDayCount;
    }
}

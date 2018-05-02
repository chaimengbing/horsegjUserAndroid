package com.project.mgjandroid.bean.groupbuying;

import com.project.mgjandroid.bean.BaseBean;
import com.project.mgjandroid.model.SmsLoginModel;

import java.math.BigDecimal;

/**
 * Created by yuandi on 2017/3/11.
 */

public class GroupPurchaseEvaluate extends BaseBean {

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
     * 团购订单编号
     **/
    private String groupPurchaseOrderId;
    /**
     * 类型 1, 代金券 2, 团购券
     */
    private int groupPurchaseCouponType;
    /**
     * 口味评分
     **/
    private BigDecimal tasteScore = new BigDecimal(5.0);
    /**
     * 环境评分
     **/
    private BigDecimal environmentScore = new BigDecimal(5.0);
    /**
     * 服务评分
     **/
    private BigDecimal serviceScore = new BigDecimal(5.0);
    /**
     * 总体评分
     **/
    private BigDecimal totalScore = new BigDecimal(5.0);
    /**
     * 评价内容
     **/
    private String content;
    /**
     * 图片
     **/
    private String images;
    /**
     * 人均消费
     **/
    private BigDecimal perCapitaConsumptionAmt;

    private GroupPurchaseMerchant groupPurchaseMerchant;

    private SmsLoginModel.ValueEntity.AppUserEntity appUser;

    private String shareUrl;

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

    public String getGroupPurchaseOrderId() {
        return groupPurchaseOrderId;
    }

    public void setGroupPurchaseOrderId(String groupPurchaseOrderId) {
        this.groupPurchaseOrderId = groupPurchaseOrderId;
    }

    public int getGroupPurchaseCouponType() {
        return groupPurchaseCouponType;
    }

    public void setGroupPurchaseCouponType(int groupPurchaseCouponType) {
        this.groupPurchaseCouponType = groupPurchaseCouponType;
    }

    public BigDecimal getTasteScore() {
        return tasteScore;
    }

    public void setTasteScore(BigDecimal tasteScore) {
        this.tasteScore = tasteScore;
    }

    public BigDecimal getEnvironmentScore() {
        return environmentScore;
    }

    public void setEnvironmentScore(BigDecimal environmentScore) {
        this.environmentScore = environmentScore;
    }

    public BigDecimal getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(BigDecimal serviceScore) {
        this.serviceScore = serviceScore;
    }

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public BigDecimal getPerCapitaConsumptionAmt() {
        return perCapitaConsumptionAmt;
    }

    public void setPerCapitaConsumptionAmt(BigDecimal perCapitaConsumptionAmt) {
        this.perCapitaConsumptionAmt = perCapitaConsumptionAmt;
    }

    public GroupPurchaseMerchant getGroupPurchaseMerchant() {
        return groupPurchaseMerchant;
    }

    public void setGroupPurchaseMerchant(GroupPurchaseMerchant groupPurchaseMerchant) {
        this.groupPurchaseMerchant = groupPurchaseMerchant;
    }

    public SmsLoginModel.ValueEntity.AppUserEntity getAppUser() {
        return appUser;
    }

    public void setAppUser(SmsLoginModel.ValueEntity.AppUserEntity appUser) {
        this.appUser = appUser;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }
}

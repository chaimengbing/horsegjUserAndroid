package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

/**
 * Created by yuandi on 2016/9/22.
 */
public class BaseProperty extends Entity {

    private Long id;
    /**
     * 跳转类型 [ '1', '链接地址' ], [ '2', '商家' ],[ '3', '拼团' ] ,[ '4', '分类' ],[ '5', '商超' ],[ '6', '团购商家' ],[ '7', '团购券' ]
     **/
    private Integer gotoType;
    /**
     * 商家id
     **/
    private Long merchantId;
    /**
     * 拼团id
     **/
    private String groupBuyId;
    /**
     * 团购商家id
     */
    private Long groupPurchaseMerchantId;
    /**
     * 团购商家优惠券id
     */
    private Long groupPurchaseCouponId;
    /**
     * 跳转地址
     **/
    private String linkUrl;
    /**
     * 服务类型 ServiceCategory
     **/
    private Integer categoryType;
    /**
     * 一级分类id{@link PrimaryCategory}
     */
    private Long firstCategoryId;
    /**
     * 二级分类id -1：全部{@link PrimaryCategory}
     */
    private Long secondCategoryId;
    /**
     * 【商超】一级分类id
     */
    private long firstTGoodsCategoryId;
    /**
     * 【商超】二级分类id -2：未选择
     */
    private long secondTGoodsCategoryId;

    private String createTime;

    private String modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getGroupBuyId() {
        return groupBuyId;
    }

    public void setGroupBuyId(String groupBuyId) {
        this.groupBuyId = groupBuyId;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    public Long getFirstCategoryId() {
        return firstCategoryId;
    }

    public void setFirstCategoryId(Long firstCategoryId) {
        this.firstCategoryId = firstCategoryId;
    }

    public Long getSecondCategoryId() {
        return secondCategoryId;
    }

    public void setSecondCategoryId(Long secondCategoryId) {
        this.secondCategoryId = secondCategoryId;
    }

    public Integer getGotoType() {
        return gotoType;
    }

    public void setGotoType(Integer gotoType) {
        this.gotoType = gotoType;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public long getFirstTGoodsCategoryId() {
        return firstTGoodsCategoryId;
    }

    public void setFirstTGoodsCategoryId(long firstTGoodsCategoryId) {
        this.firstTGoodsCategoryId = firstTGoodsCategoryId;
    }

    public long getSecondTGoodsCategoryId() {
        return secondTGoodsCategoryId;
    }

    public void setSecondTGoodsCategoryId(long secondTGoodsCategoryId) {
        this.secondTGoodsCategoryId = secondTGoodsCategoryId;
    }

    public Long getGroupPurchaseMerchantId() {
        return groupPurchaseMerchantId;
    }

    public void setGroupPurchaseMerchantId(Long groupPurchaseMerchantId) {
        this.groupPurchaseMerchantId = groupPurchaseMerchantId;
    }

    public Long getGroupPurchaseCouponId() {
        return groupPurchaseCouponId;
    }

    public void setGroupPurchaseCouponId(Long groupPurchaseCouponId) {
        this.groupPurchaseCouponId = groupPurchaseCouponId;
    }
}

package com.project.mgjandroid.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yuandi on 2016/3/24.
 */
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 图片地址
     */
    private String picUrl = "";
    /**
     * 页面地址
     */
    private String url = "";
    /**
     * 排序编号
     */
    private int sortNo;
    /**
     * 是否删除
     */
    private int hasDel;

    private Date createTime;

    private Date modifyTime;

    private Long agentId;
    /**
     * 修改人
     */
    private String modifiedBy;
    /**
     * 分类类型
     **/
    private Integer categoryType;
    /**
     * 一级分类id
     */
    private Long firstCategoryId;
    /**
     * 二级分类id -1：全部
     */
    private Long secondCategoryId;
    /**
     * 一级分类名称
     */
    private String firstCategoryName;
    /**
     * 二级分类名称
     */
    private String secondCategoryName;
    /**
     * 广告类型1：网址链接/业务模块，2：外卖分类，3：外卖商家,4:拼团,6：商超分类 7：团购商家
     **/
    private int bannerType;
    /**
     * 拼团编号
     **/
    private String groupBuyId;
    /**
     * 商家id（外卖/商超）
     */
    private Long merchantId;
    /**
     * 业务模块标记 0：否，1：是
     **/
    private Integer businessFlag;
    /**
     * 业务模块
     **/
    private Integer businessType;
    /**
     * 【商超】一级分类id{GoodsCategory}
     */
    private Long firstTGoodsCategoryId;
    /**
     * 【商超】二级分类id -2：未选择{GoodsCategory}
     */
    private Long secondTGoodsCategoryId;
    /**
     * 团购商家编号
     **/
    private Long groupPurchaseMerchantId;
    /**
     * 【团购】一级分类
     **/
    private Long groupPurchaseCategoryId;
    /**
     * 【团购】二级分类
     **/
    private Long childGroupPurchaseCategoryId;
    /**
     * 【团购】一级分类名称
     **/
    private String groupPurchaseCategoryName;
    /**
     * 【团购】二级分类名称
     **/
    private String childGroupPurchaseCategoryName;

    private Agent agent;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
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

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
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

    public String getFirstCategoryName() {
        return firstCategoryName;
    }

    public void setFirstCategoryName(String firstCategoryName) {
        this.firstCategoryName = firstCategoryName;
    }

    public String getSecondCategoryName() {
        return secondCategoryName;
    }

    public void setSecondCategoryName(String secondCategoryName) {
        this.secondCategoryName = secondCategoryName;
    }

    public int getBannerType() {
        return bannerType;
    }

    public void setBannerType(int bannerType) {
        this.bannerType = bannerType;
    }

    public String getGroupBuyId() {
        return groupBuyId;
    }

    public void setGroupBuyId(String groupBuyId) {
        this.groupBuyId = groupBuyId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getBusinessFlag() {
        return businessFlag;
    }

    public void setBusinessFlag(Integer businessFlag) {
        this.businessFlag = businessFlag;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Long getFirstTGoodsCategoryId() {
        return firstTGoodsCategoryId;
    }

    public void setFirstTGoodsCategoryId(Long firstTGoodsCategoryId) {
        this.firstTGoodsCategoryId = firstTGoodsCategoryId;
    }

    public Long getSecondTGoodsCategoryId() {
        return secondTGoodsCategoryId;
    }

    public void setSecondTGoodsCategoryId(Long secondTGoodsCategoryId) {
        this.secondTGoodsCategoryId = secondTGoodsCategoryId;
    }

    public Long getGroupPurchaseMerchantId() {
        return groupPurchaseMerchantId;
    }

    public void setGroupPurchaseMerchantId(Long groupPurchaseMerchantId) {
        this.groupPurchaseMerchantId = groupPurchaseMerchantId;
    }

    public Long getGroupPurchaseCategoryId() {
        return groupPurchaseCategoryId;
    }

    public void setGroupPurchaseCategoryId(Long groupPurchaseCategoryId) {
        this.groupPurchaseCategoryId = groupPurchaseCategoryId;
    }

    public Long getChildGroupPurchaseCategoryId() {
        return childGroupPurchaseCategoryId;
    }

    public void setChildGroupPurchaseCategoryId(Long childGroupPurchaseCategoryId) {
        this.childGroupPurchaseCategoryId = childGroupPurchaseCategoryId;
    }

    public String getGroupPurchaseCategoryName() {
        return groupPurchaseCategoryName;
    }

    public void setGroupPurchaseCategoryName(String groupPurchaseCategoryName) {
        this.groupPurchaseCategoryName = groupPurchaseCategoryName;
    }

    public String getChildGroupPurchaseCategoryName() {
        return childGroupPurchaseCategoryName;
    }

    public void setChildGroupPurchaseCategoryName(String childGroupPurchaseCategoryName) {
        this.childGroupPurchaseCategoryName = childGroupPurchaseCategoryName;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}

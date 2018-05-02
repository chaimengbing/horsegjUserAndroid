package com.project.mgjandroid.model;

import java.util.List;

/**
 * Created by SunXueLiang on 2017-09-01.
 */

public class NewHomeBannerModel extends Entity {

    private int code;
    private String uuid;
    private boolean success;
    private String servertime;
    private List<ValueBean> value;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getServertime() {
        return servertime;
    }

    public void setServertime(String servertime) {
        this.servertime = servertime;
    }

    public List<ValueBean> getValue() {
        return value;
    }

    public void setValue(List<ValueBean> value) {
        this.value = value;
    }

    public static class ValueBean extends Entity {

        private int id;
        private String createTime;
        private String modifyTime;
        private String name;
        private int agentId;
        private String picUrl;
        private String url;
        private int sortNo;
        private int hasDel;
        private String modifiedBy;
        private Long categoryType;
        private Long firstCategoryId;
        private Long secondCategoryId;
        private Object firstCategoryName;
        private Object secondCategoryName;
        private int bannerType;
        private String groupBuyId;
        private Object merchantId;
        private int businessFlag;
        private Object businessType;
        private Long firstTGoodsCategoryId;
        private Long secondTGoodsCategoryId;
        private Object groupPurchaseMerchantId;
        private Object groupPurchaseCategoryId;
        private Object childGroupPurchaseCategoryId;
        private Object groupPurchaseCategoryName;
        private Object childGroupPurchaseCategoryName;
        private int creator;
        private Object agent;
        private Object merchant;
        private Object groupBuyMap;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
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

        public String getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public Long getCategoryType() {
            return categoryType;
        }

        public void setCategoryType(Long categoryType) {
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

        public Object getFirstCategoryName() {
            return firstCategoryName;
        }

        public void setFirstCategoryName(Object firstCategoryName) {
            this.firstCategoryName = firstCategoryName;
        }

        public Object getSecondCategoryName() {
            return secondCategoryName;
        }

        public void setSecondCategoryName(Object secondCategoryName) {
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

        public Object getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(Object merchantId) {
            this.merchantId = merchantId;
        }

        public int getBusinessFlag() {
            return businessFlag;
        }

        public void setBusinessFlag(int businessFlag) {
            this.businessFlag = businessFlag;
        }

        public Object getBusinessType() {
            return businessType;
        }

        public void setBusinessType(Object businessType) {
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

        public Object getGroupPurchaseMerchantId() {
            return groupPurchaseMerchantId;
        }

        public void setGroupPurchaseMerchantId(Object groupPurchaseMerchantId) {
            this.groupPurchaseMerchantId = groupPurchaseMerchantId;
        }

        public Object getGroupPurchaseCategoryId() {
            return groupPurchaseCategoryId;
        }

        public void setGroupPurchaseCategoryId(Object groupPurchaseCategoryId) {
            this.groupPurchaseCategoryId = groupPurchaseCategoryId;
        }

        public Object getChildGroupPurchaseCategoryId() {
            return childGroupPurchaseCategoryId;
        }

        public void setChildGroupPurchaseCategoryId(Object childGroupPurchaseCategoryId) {
            this.childGroupPurchaseCategoryId = childGroupPurchaseCategoryId;
        }

        public Object getGroupPurchaseCategoryName() {
            return groupPurchaseCategoryName;
        }

        public void setGroupPurchaseCategoryName(Object groupPurchaseCategoryName) {
            this.groupPurchaseCategoryName = groupPurchaseCategoryName;
        }

        public Object getChildGroupPurchaseCategoryName() {
            return childGroupPurchaseCategoryName;
        }

        public void setChildGroupPurchaseCategoryName(Object childGroupPurchaseCategoryName) {
            this.childGroupPurchaseCategoryName = childGroupPurchaseCategoryName;
        }

        public int getCreator() {
            return creator;
        }

        public void setCreator(int creator) {
            this.creator = creator;
        }

        public Object getAgent() {
            return agent;
        }

        public void setAgent(Object agent) {
            this.agent = agent;
        }

        public Object getMerchant() {
            return merchant;
        }

        public void setMerchant(Object merchant) {
            this.merchant = merchant;
        }

        public Object getGroupBuyMap() {
            return groupBuyMap;
        }

        public void setGroupBuyMap(Object groupBuyMap) {
            this.groupBuyMap = groupBuyMap;
        }
    }
}

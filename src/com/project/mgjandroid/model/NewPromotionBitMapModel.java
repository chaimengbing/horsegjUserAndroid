package com.project.mgjandroid.model;

import java.util.List;

/**
 * Created by SunXueLiang on 2017-09-04.
 */

public class NewPromotionBitMapModel extends Entity {

    private int code;
    private String uuid;
    private ValueBean value;
    private boolean success;
    private String servertime;

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

    public ValueBean getValue() {
        return value;
    }

    public void setValue(ValueBean value) {
        this.value = value;
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

    public static class ValueBean {
        private List<AppPromotionBitListBean> appPromotionBitList;
        private List<StaticHorizontalScreenAdvertisementListBean> staticHorizontalScreenAdvertisementList;

        public List<AppPromotionBitListBean> getAppPromotionBitList() {
            return appPromotionBitList;
        }

        public void setAppPromotionBitList(List<AppPromotionBitListBean> appPromotionBitList) {
            this.appPromotionBitList = appPromotionBitList;
        }

        public List<StaticHorizontalScreenAdvertisementListBean> getStaticHorizontalScreenAdvertisementList() {
            return staticHorizontalScreenAdvertisementList;
        }

        public void setStaticHorizontalScreenAdvertisementList(List<StaticHorizontalScreenAdvertisementListBean> staticHorizontalScreenAdvertisementList) {
            this.staticHorizontalScreenAdvertisementList = staticHorizontalScreenAdvertisementList;
        }

        public static class AppPromotionBitListBean extends Entity {
            private int id;
            private String createTime;
            private String modifyTime;
            private int gotoType;
            private int merchantId;
            private Object groupPurchaseMerchantId;
            private Object groupPurchaseCouponId;
            private String groupBuyId;
            private String linkUrl;
            private Long categoryType;
            private Long firstCategoryId;
            private Long secondCategoryId;
            private Long firstTGoodsCategoryId;
            private Long secondTGoodsCategoryId;
            private int agentId;
            private String name;
            private String title;
            private String img;
            private int status;
            private Object sortNo;
            private int hasDel;
            private int type;
            private int advertisingPosition;
            private Object agent;
            private Object merchant;
            private Object groupBuyMap;
            private Object groupPurchaseMerchant;
            private Object groupPurchaseCoupon;

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

            public int getGotoType() {
                return gotoType;
            }

            public void setGotoType(int gotoType) {
                this.gotoType = gotoType;
            }

            public int getMerchantId() {
                return merchantId;
            }

            public void setMerchantId(int merchantId) {
                this.merchantId = merchantId;
            }

            public Object getGroupPurchaseMerchantId() {
                return groupPurchaseMerchantId;
            }

            public void setGroupPurchaseMerchantId(Object groupPurchaseMerchantId) {
                this.groupPurchaseMerchantId = groupPurchaseMerchantId;
            }

            public Object getGroupPurchaseCouponId() {
                return groupPurchaseCouponId;
            }

            public void setGroupPurchaseCouponId(Object groupPurchaseCouponId) {
                this.groupPurchaseCouponId = groupPurchaseCouponId;
            }

            public String getGroupBuyId() {
                return groupBuyId;
            }

            public void setGroupBuyId(String groupBuyId) {
                this.groupBuyId = groupBuyId;
            }

            public String getLinkUrl() {
                return linkUrl;
            }

            public void setLinkUrl(String linkUrl) {
                this.linkUrl = linkUrl;
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

            public int getAgentId() {
                return agentId;
            }

            public void setAgentId(int agentId) {
                this.agentId = agentId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public Object getSortNo() {
                return sortNo;
            }

            public void setSortNo(Object sortNo) {
                this.sortNo = sortNo;
            }

            public int getHasDel() {
                return hasDel;
            }

            public void setHasDel(int hasDel) {
                this.hasDel = hasDel;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getAdvertisingPosition() {
                return advertisingPosition;
            }

            public void setAdvertisingPosition(int advertisingPosition) {
                this.advertisingPosition = advertisingPosition;
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

            public Object getGroupPurchaseMerchant() {
                return groupPurchaseMerchant;
            }

            public void setGroupPurchaseMerchant(Object groupPurchaseMerchant) {
                this.groupPurchaseMerchant = groupPurchaseMerchant;
            }

            public Object getGroupPurchaseCoupon() {
                return groupPurchaseCoupon;
            }

            public void setGroupPurchaseCoupon(Object groupPurchaseCoupon) {
                this.groupPurchaseCoupon = groupPurchaseCoupon;
            }
        }

        public static class StaticHorizontalScreenAdvertisementListBean extends Entity {
            private int id;
            private String createTime;
            private String modifyTime;
            private int gotoType;
            private int merchantId;
            private Object groupPurchaseMerchantId;
            private Object groupPurchaseCouponId;
            private String groupBuyId;
            private String linkUrl;
            private Long categoryType;
            private Long firstCategoryId;
            private Long secondCategoryId;
            private int firstTGoodsCategoryId;
            private int secondTGoodsCategoryId;
            private int agentId;
            private String name;
            private Object title;
            private String img;
            private int status;
            private Object sortNo;
            private int hasDel;
            private int type;
            private int advertisingPosition;
            private Object agent;
            private Object merchant;
            private Object groupBuyMap;
            private Object groupPurchaseMerchant;
            private Object groupPurchaseCoupon;

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

            public int getGotoType() {
                return gotoType;
            }

            public void setGotoType(int gotoType) {
                this.gotoType = gotoType;
            }

            public int getMerchantId() {
                return merchantId;
            }

            public void setMerchantId(int merchantId) {
                this.merchantId = merchantId;
            }

            public Object getGroupPurchaseMerchantId() {
                return groupPurchaseMerchantId;
            }

            public void setGroupPurchaseMerchantId(Object groupPurchaseMerchantId) {
                this.groupPurchaseMerchantId = groupPurchaseMerchantId;
            }

            public Object getGroupPurchaseCouponId() {
                return groupPurchaseCouponId;
            }

            public void setGroupPurchaseCouponId(Object groupPurchaseCouponId) {
                this.groupPurchaseCouponId = groupPurchaseCouponId;
            }

            public String getGroupBuyId() {
                return groupBuyId;
            }

            public void setGroupBuyId(String groupBuyId) {
                this.groupBuyId = groupBuyId;
            }

            public String getLinkUrl() {
                return linkUrl;
            }

            public void setLinkUrl(String linkUrl) {
                this.linkUrl = linkUrl;
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

            public int getFirstTGoodsCategoryId() {
                return firstTGoodsCategoryId;
            }

            public void setFirstTGoodsCategoryId(int firstTGoodsCategoryId) {
                this.firstTGoodsCategoryId = firstTGoodsCategoryId;
            }

            public int getSecondTGoodsCategoryId() {
                return secondTGoodsCategoryId;
            }

            public void setSecondTGoodsCategoryId(int secondTGoodsCategoryId) {
                this.secondTGoodsCategoryId = secondTGoodsCategoryId;
            }

            public int getAgentId() {
                return agentId;
            }

            public void setAgentId(int agentId) {
                this.agentId = agentId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getTitle() {
                return title;
            }

            public void setTitle(Object title) {
                this.title = title;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public Object getSortNo() {
                return sortNo;
            }

            public void setSortNo(Object sortNo) {
                this.sortNo = sortNo;
            }

            public int getHasDel() {
                return hasDel;
            }

            public void setHasDel(int hasDel) {
                this.hasDel = hasDel;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getAdvertisingPosition() {
                return advertisingPosition;
            }

            public void setAdvertisingPosition(int advertisingPosition) {
                this.advertisingPosition = advertisingPosition;
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

            public Object getGroupPurchaseMerchant() {
                return groupPurchaseMerchant;
            }

            public void setGroupPurchaseMerchant(Object groupPurchaseMerchant) {
                this.groupPurchaseMerchant = groupPurchaseMerchant;
            }

            public Object getGroupPurchaseCoupon() {
                return groupPurchaseCoupon;
            }

            public void setGroupPurchaseCoupon(Object groupPurchaseCoupon) {
                this.groupPurchaseCoupon = groupPurchaseCoupon;
            }
        }
    }
}

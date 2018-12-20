package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.util.List;

public class DiscountedGoods extends Entity{

    /**
     * id : 467
     * createTime : 2018-10-22 11:27:38
     * modifyTime : 2018-10-22 11:27:38
     * merchantId : 550
     * goodsRestrictedPurchaseRuleId : 259
     * goodsCategoryId : 928
     * goodsId : 4480
     * specId : 4876
     * discountProportion : 5
     * discountStock : 10
     * surplusDiscountStock : 10
     * saleStatus : 1
     * hasDel : 0
     * discountPrice : null
     * goods : null
     * goodsCategory : {"id":928,"createTime":"2018-07-17 18:15:26","modifyTime":"2018-09-28 17:31:07","merchantId":550,"name":"阿卡丽","description":"回来了","sortNo":1,"hasDel":0,"icon":null,"parentId":null,"level":1,"type":0,"isMandatory":0,"goodsList":[],"goodsCategoryList":null,"relationCategoryIds":null,"isAssociated":0,"associatedName":null,"relationCategoryId":null}
     * price : null
     * goodsRestrictedPurchaseRule : {"id":259,"createTime":"2018-10-22 11:27:17","modifyTime":"2018-10-22 11:29:01","agentId":3,"title":"折扣","merchantId":550,"everydayBuyCount":3,"everyOrderBuyCount":5,"everyGoodsEveryOrderBuyCount":3,"startTime":"2018-10-22 11:29:00","endTime":"2018-10-26 00:00:00","hasDel":0,"merchantName":null,"discountedGoodsList":null,"discountedGoodsNum":null,"status":1,"totalSales":0,"todaySales":0}
     * totalSales : 0
     * todaySales : 0
     */

    private int id;
    private String createTime;
    private String modifyTime;
    private int merchantId;
    private int goodsRestrictedPurchaseRuleId;
    private int goodsCategoryId;
    private int goodsId;
    private int specId;
    private int discountProportion;
    private int discountStock;
    private int surplusDiscountStock;
    private int saleStatus;
    private int hasDel;
    private Object discountPrice;
    private Object goods;
    private GoodsCategoryBean goodsCategory;
    private Object price;
    private GoodsRestrictedPurchaseRuleBean goodsRestrictedPurchaseRule;
    private int totalSales;
    private int todaySales;
    //每个用户限购数量
    private Integer maxBuyNum;
    //剩余限购数量
    private Integer surplusBuyNum;

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

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public int getGoodsRestrictedPurchaseRuleId() {
        return goodsRestrictedPurchaseRuleId;
    }

    public void setGoodsRestrictedPurchaseRuleId(int goodsRestrictedPurchaseRuleId) {
        this.goodsRestrictedPurchaseRuleId = goodsRestrictedPurchaseRuleId;
    }

    public int getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(int goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getSpecId() {
        return specId;
    }

    public void setSpecId(int specId) {
        this.specId = specId;
    }

    public int getDiscountProportion() {
        return discountProportion;
    }

    public void setDiscountProportion(int discountProportion) {
        this.discountProportion = discountProportion;
    }

    public int getDiscountStock() {
        return discountStock;
    }

    public void setDiscountStock(int discountStock) {
        this.discountStock = discountStock;
    }

    public int getSurplusDiscountStock() {
        return surplusDiscountStock;
    }

    public void setSurplusDiscountStock(int surplusDiscountStock) {
        this.surplusDiscountStock = surplusDiscountStock;
    }

    public int getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(int saleStatus) {
        this.saleStatus = saleStatus;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public Object getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Object discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Object getGoods() {
        return goods;
    }

    public void setGoods(Object goods) {
        this.goods = goods;
    }

    public GoodsCategoryBean getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(GoodsCategoryBean goodsCategory) {
        this.goodsCategory = goodsCategory;
    }

    public Object getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = price;
    }

    public GoodsRestrictedPurchaseRuleBean getGoodsRestrictedPurchaseRule() {
        return goodsRestrictedPurchaseRule;
    }

    public void setGoodsRestrictedPurchaseRule(GoodsRestrictedPurchaseRuleBean goodsRestrictedPurchaseRule) {
        this.goodsRestrictedPurchaseRule = goodsRestrictedPurchaseRule;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public int getTodaySales() {
        return todaySales;
    }

    public void setTodaySales(int todaySales) {
        this.todaySales = todaySales;
    }

    public Integer getMaxBuyNum() {
        return maxBuyNum;
    }

    public void setMaxBuyNum(Integer maxBuyNum) {
        this.maxBuyNum = maxBuyNum;
    }

    public Integer getSurplusBuyNum() {
        return surplusBuyNum;
    }

    public void setSurplusBuyNum(Integer surplusBuyNum) {
        this.surplusBuyNum = surplusBuyNum;
    }

    public static class GoodsCategoryBean extends Entity{
        /**
         * id : 928
         * createTime : 2018-07-17 18:15:26
         * modifyTime : 2018-09-28 17:31:07
         * merchantId : 550
         * name : 阿卡丽
         * description : 回来了
         * sortNo : 1
         * hasDel : 0
         * icon : null
         * parentId : null
         * level : 1
         * type : 0
         * isMandatory : 0
         * goodsList : []
         * goodsCategoryList : null
         * relationCategoryIds : null
         * isAssociated : 0
         * associatedName : null
         * relationCategoryId : null
         */

        private int id;
        private String createTime;
        private String modifyTime;
        private int merchantId;
        private String name;
        private String description;
        private int sortNo;
        private int hasDel;
        private Object icon;
        private Object parentId;
        private int level;
        private int type;
        private int isMandatory;
        private Object goodsCategoryList;
        private Object relationCategoryIds;
        private int isAssociated;
        private Object associatedName;
        private Object relationCategoryId;
        private List<?> goodsList;

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

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public Object getIcon() {
            return icon;
        }

        public void setIcon(Object icon) {
            this.icon = icon;
        }

        public Object getParentId() {
            return parentId;
        }

        public void setParentId(Object parentId) {
            this.parentId = parentId;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getIsMandatory() {
            return isMandatory;
        }

        public void setIsMandatory(int isMandatory) {
            this.isMandatory = isMandatory;
        }

        public Object getGoodsCategoryList() {
            return goodsCategoryList;
        }

        public void setGoodsCategoryList(Object goodsCategoryList) {
            this.goodsCategoryList = goodsCategoryList;
        }

        public Object getRelationCategoryIds() {
            return relationCategoryIds;
        }

        public void setRelationCategoryIds(Object relationCategoryIds) {
            this.relationCategoryIds = relationCategoryIds;
        }

        public int getIsAssociated() {
            return isAssociated;
        }

        public void setIsAssociated(int isAssociated) {
            this.isAssociated = isAssociated;
        }

        public Object getAssociatedName() {
            return associatedName;
        }

        public void setAssociatedName(Object associatedName) {
            this.associatedName = associatedName;
        }

        public Object getRelationCategoryId() {
            return relationCategoryId;
        }

        public void setRelationCategoryId(Object relationCategoryId) {
            this.relationCategoryId = relationCategoryId;
        }

        public List<?> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<?> goodsList) {
            this.goodsList = goodsList;
        }
    }

    public static class GoodsRestrictedPurchaseRuleBean extends Entity{
        /**
         * id : 259
         * createTime : 2018-10-22 11:27:17
         * modifyTime : 2018-10-22 11:29:01
         * agentId : 3
         * title : 折扣
         * merchantId : 550
         * everydayBuyCount : 3
         * everyOrderBuyCount : 5
         * everyGoodsEveryOrderBuyCount : 3
         * startTime : 2018-10-22 11:29:00
         * endTime : 2018-10-26 00:00:00
         * hasDel : 0
         * merchantName : null
         * discountedGoodsList : null
         * discountedGoodsNum : null
         * status : 1
         * totalSales : 0
         * todaySales : 0
         */

        private int id;
        private String createTime;
        private String modifyTime;
        private int agentId;
        private String title;
        private int merchantId;
        private int everydayBuyCount;
        private int everyOrderBuyCount;
        private int everyGoodsEveryOrderBuyCount;
        private String startTime;
        private String endTime;
        private int hasDel;
        private Object merchantName;
        private Object discountedGoodsList;
        private Object discountedGoodsNum;
        private int status;
        private int totalSales;
        private int todaySales;

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

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }

        public int getEverydayBuyCount() {
            return everydayBuyCount;
        }

        public void setEverydayBuyCount(int everydayBuyCount) {
            this.everydayBuyCount = everydayBuyCount;
        }

        public int getEveryOrderBuyCount() {
            return everyOrderBuyCount;
        }

        public void setEveryOrderBuyCount(int everyOrderBuyCount) {
            this.everyOrderBuyCount = everyOrderBuyCount;
        }

        public int getEveryGoodsEveryOrderBuyCount() {
            return everyGoodsEveryOrderBuyCount;
        }

        public void setEveryGoodsEveryOrderBuyCount(int everyGoodsEveryOrderBuyCount) {
            this.everyGoodsEveryOrderBuyCount = everyGoodsEveryOrderBuyCount;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getHasDel() {
            return hasDel;
        }

        public void setHasDel(int hasDel) {
            this.hasDel = hasDel;
        }

        public Object getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(Object merchantName) {
            this.merchantName = merchantName;
        }

        public Object getDiscountedGoodsList() {
            return discountedGoodsList;
        }

        public void setDiscountedGoodsList(Object discountedGoodsList) {
            this.discountedGoodsList = discountedGoodsList;
        }

        public Object getDiscountedGoodsNum() {
            return discountedGoodsNum;
        }

        public void setDiscountedGoodsNum(Object discountedGoodsNum) {
            this.discountedGoodsNum = discountedGoodsNum;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTotalSales() {
            return totalSales;
        }

        public void setTotalSales(int totalSales) {
            this.totalSales = totalSales;
        }

        public int getTodaySales() {
            return todaySales;
        }

        public void setTodaySales(int todaySales) {
            this.todaySales = todaySales;
        }
    }
}

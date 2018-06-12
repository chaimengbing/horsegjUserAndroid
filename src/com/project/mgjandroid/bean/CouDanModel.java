package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;
import java.util.List;

public class CouDanModel extends Entity{

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

    public static class ValueBean extends Entity{

        private int id;
        private String createTime;
        private String modifyTime;
        private int merchantId;
        private int categoryId;
        private int categoryParentId;
        private int type;
        private String name;
        private String description;
        private String goodsUnit;
        private String imgs;
        private String attributes;
        private int sortNo;
        private String month;
        private int monthSaled;
        private int totalSaled;
        private int praiseNum;
        private int commentNum;
        private int goodCommentNum;
        private int mediumCommentNum;
        private int badCommentNum;
        private double commentScore;
        private int status;
        private int hasDel;
        private Object delTime;
        private Object goodsSpecList;
        private Object goodsAttributeList;
        private String barcode;
        private int isStandard;
        private Object brandName;
        private int hasDiscount;
        private int everyGoodsEveryOrderBuyCount;
        private int everyOrderBuyCount;
        private int surplusDiscountStock;
        private Object discountedGoods;
        private TGoodsSpecBean tGoodsSpec;
        private int canReturn;
        private int daysForReturn;
        private int canChange;
        private int daysForChange;
        private Object shareUrl;

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

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public int getCategoryParentId() {
            return categoryParentId;
        }

        public void setCategoryParentId(int categoryParentId) {
            this.categoryParentId = categoryParentId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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

        public String getGoodsUnit() {
            return goodsUnit;
        }

        public void setGoodsUnit(String goodsUnit) {
            this.goodsUnit = goodsUnit;
        }

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public String getAttributes() {
            return attributes;
        }

        public void setAttributes(String attributes) {
            this.attributes = attributes;
        }

        public int getSortNo() {
            return sortNo;
        }

        public void setSortNo(int sortNo) {
            this.sortNo = sortNo;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public int getMonthSaled() {
            return monthSaled;
        }

        public void setMonthSaled(int monthSaled) {
            this.monthSaled = monthSaled;
        }

        public int getTotalSaled() {
            return totalSaled;
        }

        public void setTotalSaled(int totalSaled) {
            this.totalSaled = totalSaled;
        }

        public int getPraiseNum() {
            return praiseNum;
        }

        public void setPraiseNum(int praiseNum) {
            this.praiseNum = praiseNum;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public int getGoodCommentNum() {
            return goodCommentNum;
        }

        public void setGoodCommentNum(int goodCommentNum) {
            this.goodCommentNum = goodCommentNum;
        }

        public int getMediumCommentNum() {
            return mediumCommentNum;
        }

        public void setMediumCommentNum(int mediumCommentNum) {
            this.mediumCommentNum = mediumCommentNum;
        }

        public int getBadCommentNum() {
            return badCommentNum;
        }

        public void setBadCommentNum(int badCommentNum) {
            this.badCommentNum = badCommentNum;
        }

        public double getCommentScore() {
            return commentScore;
        }

        public void setCommentScore(double commentScore) {
            this.commentScore = commentScore;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getHasDel() {
            return hasDel;
        }

        public void setHasDel(int hasDel) {
            this.hasDel = hasDel;
        }

        public Object getDelTime() {
            return delTime;
        }

        public void setDelTime(Object delTime) {
            this.delTime = delTime;
        }

        public Object getGoodsSpecList() {
            return goodsSpecList;
        }

        public void setGoodsSpecList(Object goodsSpecList) {
            this.goodsSpecList = goodsSpecList;
        }

        public Object getGoodsAttributeList() {
            return goodsAttributeList;
        }

        public void setGoodsAttributeList(Object goodsAttributeList) {
            this.goodsAttributeList = goodsAttributeList;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public int getIsStandard() {
            return isStandard;
        }

        public void setIsStandard(int isStandard) {
            this.isStandard = isStandard;
        }

        public Object getBrandName() {
            return brandName;
        }

        public void setBrandName(Object brandName) {
            this.brandName = brandName;
        }

        public int getHasDiscount() {
            return hasDiscount;
        }

        public void setHasDiscount(int hasDiscount) {
            this.hasDiscount = hasDiscount;
        }

        public int getEveryGoodsEveryOrderBuyCount() {
            return everyGoodsEveryOrderBuyCount;
        }

        public void setEveryGoodsEveryOrderBuyCount(int everyGoodsEveryOrderBuyCount) {
            this.everyGoodsEveryOrderBuyCount = everyGoodsEveryOrderBuyCount;
        }

        public int getEveryOrderBuyCount() {
            return everyOrderBuyCount;
        }

        public void setEveryOrderBuyCount(int everyOrderBuyCount) {
            this.everyOrderBuyCount = everyOrderBuyCount;
        }

        public int getSurplusDiscountStock() {
            return surplusDiscountStock;
        }

        public void setSurplusDiscountStock(int surplusDiscountStock) {
            this.surplusDiscountStock = surplusDiscountStock;
        }

        public Object getDiscountedGoods() {
            return discountedGoods;
        }

        public void setDiscountedGoods(Object discountedGoods) {
            this.discountedGoods = discountedGoods;
        }

        public TGoodsSpecBean getTGoodsSpec() {
            return tGoodsSpec;
        }

        public void setTGoodsSpec(TGoodsSpecBean tGoodsSpec) {
            this.tGoodsSpec = tGoodsSpec;
        }

        public int getCanReturn() {
            return canReturn;
        }

        public void setCanReturn(int canReturn) {
            this.canReturn = canReturn;
        }

        public int getDaysForReturn() {
            return daysForReturn;
        }

        public void setDaysForReturn(int daysForReturn) {
            this.daysForReturn = daysForReturn;
        }

        public int getCanChange() {
            return canChange;
        }

        public void setCanChange(int canChange) {
            this.canChange = canChange;
        }

        public int getDaysForChange() {
            return daysForChange;
        }

        public void setDaysForChange(int daysForChange) {
            this.daysForChange = daysForChange;
        }

        public Object getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(Object shareUrl) {
            this.shareUrl = shareUrl;
        }

        public static class TGoodsSpecBean extends Entity{

            private int id;
            private String createTime;
            private String modifyTime;
            private int goodsId;
            private String spec;
            private BigDecimal originalPrice;
            private BigDecimal price;
            private int boxNum;
            private double boxPrice;
            private int minOrderNum;
            private int orderLimit;
            private int stockType;
            private Object stock;
            private int hasDel;
            /**
             * 购买数量
             */
            private int buyCount;

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

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public String getSpec() {
                return spec;
            }

            public void setSpec(String spec) {
                this.spec = spec;
            }

            public BigDecimal getOriginalPrice() {
                return originalPrice;
            }

            public void setOriginalPrice(BigDecimal originalPrice) {
                this.originalPrice = originalPrice;
            }

            public BigDecimal getPrice() {
                return price;
            }

            public void setPrice(BigDecimal price) {
                this.price = price;
            }

            public int getBoxNum() {
                return boxNum;
            }

            public void setBoxNum(int boxNum) {
                this.boxNum = boxNum;
            }

            public double getBoxPrice() {
                return boxPrice;
            }

            public void setBoxPrice(double boxPrice) {
                this.boxPrice = boxPrice;
            }

            public int getMinOrderNum() {
                return minOrderNum;
            }

            public void setMinOrderNum(int minOrderNum) {
                this.minOrderNum = minOrderNum;
            }

            public int getOrderLimit() {
                return orderLimit;
            }

            public void setOrderLimit(int orderLimit) {
                this.orderLimit = orderLimit;
            }

            public int getStockType() {
                return stockType;
            }

            public void setStockType(int stockType) {
                this.stockType = stockType;
            }

            public Object getStock() {
                return stock;
            }

            public void setStock(Object stock) {
                this.stock = stock;
            }

            public int getHasDel() {
                return hasDel;
            }

            public void setHasDel(int hasDel) {
                this.hasDel = hasDel;
            }

            public int getBuyCount() {
                return buyCount;
            }

            public void setBuyCount(int buyCount) {
                this.buyCount = buyCount;
            }
        }
    }
}

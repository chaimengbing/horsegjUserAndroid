package com.project.mgjandroid.bean;

/**
 * 项目名称：mgjandroid
 * 类描述：
 * 创建人：Mr_Lei
 * 创建时间：2018/4/26 15:48
 */
public class GoodsDetailModel {

    /**
     * code : 0
     * uuid : 867451020506330
     * value : {"id":1536,"createTime":"2018-04-09 11:14:32","modifyTime":"2018-04-26 13:57:08","merchantId":544,"categoryId":830,"categoryParentId":null,"type":0,"name":"多规格","description":"成都市将超过sd","goodsUnit":"份","imgs":"http://7xu5hi.com1.z0.glb.clouddn.com/20180409102924933.png","attributes":"","sortNo":1,"month":null,"monthSaled":0,"totalSaled":0,"praiseNum":0,"commentNum":0,"goodCommentNum":0,"mediumCommentNum":0,"badCommentNum":0,"commentScore":5,"status":1,"hasDel":0,"delTime":null,"goodsSpecList":[{"id":1919,"createTime":"2018-04-09 11:14:32","modifyTime":"2018-04-24 18:15:30","goodsId":1536,"spec":"的方法大幅度","originalPrice":200,"price":120,"boxNum":0,"boxPrice":5,"minOrderNum":2,"orderLimit":5,"stockType":1,"stock":8,"hasDel":0},{"id":4719,"createTime":"2018-04-24 16:58:17","modifyTime":"2018-04-24 16:58:17","goodsId":1536,"spec":"同一天也同样","originalPrice":254,"price":230,"boxNum":0,"boxPrice":5,"minOrderNum":0,"orderLimit":0,"stockType":0,"stock":null,"hasDel":0},{"id":4720,"createTime":"2018-04-24 16:58:17","modifyTime":"2018-04-24 16:58:17","goodsId":1536,"spec":"44444","originalPrice":55,"price":555,"boxNum":0,"boxPrice":55,"minOrderNum":1,"orderLimit":1,"stockType":1,"stock":40,"hasDel":0}],"goodsAttributeList":[{"id":281,"createTime":"2018-04-24 16:59:16","modifyTime":"2018-04-24 16:59:16","goodsId":1536,"title":"111","name":"111|*|111|*|111|*|111|*|111|*|rtgrt","hasDel":0},{"id":282,"createTime":"2018-04-24 16:59:16","modifyTime":"2018-04-24 16:59:16","goodsId":1536,"title":"222","name":"111|*|111|*|111|*|111|*|111|*|ere","hasDel":0},{"id":283,"createTime":"2018-04-24 16:59:16","modifyTime":"2018-04-24 16:59:16","goodsId":1536,"title":"333","name":"111|*|111|*|111|*|111|*|111|*|111|*|ere","hasDel":0}],"barcode":"6544613062444","isStandard":0,"brandName":null,"canReturn":0,"daysForReturn":0,"canChange":0,"daysForChange":0,"shareUrl":"http://120.24.16.64/maguanjia/html/merchantMarketShare.html?goodsId=1536"}
     * success : true
     * servertime : 2018-04-26 15:47:28
     */

    private int code;
    private String uuid;
    private Goods value;
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

    public Goods getValue() {
        return value;
    }

    public void setValue(Goods value) {
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

//    public static class ValueBean {
//        /**
//         * id : 1536
//         * createTime : 2018-04-09 11:14:32
//         * modifyTime : 2018-04-26 13:57:08
//         * merchantId : 544
//         * categoryId : 830
//         * categoryParentId : null
//         * type : 0
//         * name : 多规格
//         * description : 成都市将超过sd
//         * goodsUnit : 份
//         * imgs : http://7xu5hi.com1.z0.glb.clouddn.com/20180409102924933.png
//         * attributes :
//         * sortNo : 1
//         * month : null
//         * monthSaled : 0
//         * totalSaled : 0
//         * praiseNum : 0
//         * commentNum : 0
//         * goodCommentNum : 0
//         * mediumCommentNum : 0
//         * badCommentNum : 0
//         * commentScore : 5.0
//         * status : 1
//         * hasDel : 0
//         * delTime : null
//         * goodsSpecList : [{"id":1919,"createTime":"2018-04-09 11:14:32","modifyTime":"2018-04-24 18:15:30","goodsId":1536,"spec":"的方法大幅度","originalPrice":200,"price":120,"boxNum":0,"boxPrice":5,"minOrderNum":2,"orderLimit":5,"stockType":1,"stock":8,"hasDel":0},{"id":4719,"createTime":"2018-04-24 16:58:17","modifyTime":"2018-04-24 16:58:17","goodsId":1536,"spec":"同一天也同样","originalPrice":254,"price":230,"boxNum":0,"boxPrice":5,"minOrderNum":0,"orderLimit":0,"stockType":0,"stock":null,"hasDel":0},{"id":4720,"createTime":"2018-04-24 16:58:17","modifyTime":"2018-04-24 16:58:17","goodsId":1536,"spec":"44444","originalPrice":55,"price":555,"boxNum":0,"boxPrice":55,"minOrderNum":1,"orderLimit":1,"stockType":1,"stock":40,"hasDel":0}]
//         * goodsAttributeList : [{"id":281,"createTime":"2018-04-24 16:59:16","modifyTime":"2018-04-24 16:59:16","goodsId":1536,"title":"111","name":"111|*|111|*|111|*|111|*|111|*|rtgrt","hasDel":0},{"id":282,"createTime":"2018-04-24 16:59:16","modifyTime":"2018-04-24 16:59:16","goodsId":1536,"title":"222","name":"111|*|111|*|111|*|111|*|111|*|ere","hasDel":0},{"id":283,"createTime":"2018-04-24 16:59:16","modifyTime":"2018-04-24 16:59:16","goodsId":1536,"title":"333","name":"111|*|111|*|111|*|111|*|111|*|111|*|ere","hasDel":0}]
//         * barcode : 6544613062444
//         * isStandard : 0
//         * brandName : null
//         * canReturn : 0
//         * daysForReturn : 0
//         * canChange : 0
//         * daysForChange : 0
//         * shareUrl : http://120.24.16.64/maguanjia/html/merchantMarketShare.html?goodsId=1536
//         */
//
//        private int id;
//        private String createTime;
//        private String modifyTime;
//        private int merchantId;
//        private int categoryId;
//        private Object categoryParentId;
//        private int type;
//        private String name;
//        private String description;
//        private String goodsUnit;
//        private String imgs;
//        private String attributes;
//        private int sortNo;
//        private Object month;
//        private int monthSaled;
//        private int totalSaled;
//        private int praiseNum;
//        private int commentNum;
//        private int goodCommentNum;
//        private int mediumCommentNum;
//        private int badCommentNum;
//        private double commentScore;
//        private int status;
//        private int hasDel;
//        private Object delTime;
//        private String barcode;
//        private int isStandard;
//        private Object brandName;
//        private int canReturn;
//        private int daysForReturn;
//        private int canChange;
//        private int daysForChange;
//        private String shareUrl;
//        private List<GoodsSpecListBean> goodsSpecList;
//        private List<GoodsAttributeListBean> goodsAttributeList;
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getCreateTime() {
//            return createTime;
//        }
//
//        public void setCreateTime(String createTime) {
//            this.createTime = createTime;
//        }
//
//        public String getModifyTime() {
//            return modifyTime;
//        }
//
//        public void setModifyTime(String modifyTime) {
//            this.modifyTime = modifyTime;
//        }
//
//        public int getMerchantId() {
//            return merchantId;
//        }
//
//        public void setMerchantId(int merchantId) {
//            this.merchantId = merchantId;
//        }
//
//        public int getCategoryId() {
//            return categoryId;
//        }
//
//        public void setCategoryId(int categoryId) {
//            this.categoryId = categoryId;
//        }
//
//        public Object getCategoryParentId() {
//            return categoryParentId;
//        }
//
//        public void setCategoryParentId(Object categoryParentId) {
//            this.categoryParentId = categoryParentId;
//        }
//
//        public int getType() {
//            return type;
//        }
//
//        public void setType(int type) {
//            this.type = type;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getDescription() {
//            return description;
//        }
//
//        public void setDescription(String description) {
//            this.description = description;
//        }
//
//        public String getGoodsUnit() {
//            return goodsUnit;
//        }
//
//        public void setGoodsUnit(String goodsUnit) {
//            this.goodsUnit = goodsUnit;
//        }
//
//        public String getImgs() {
//            return imgs;
//        }
//
//        public void setImgs(String imgs) {
//            this.imgs = imgs;
//        }
//
//        public String getAttributes() {
//            return attributes;
//        }
//
//        public void setAttributes(String attributes) {
//            this.attributes = attributes;
//        }
//
//        public int getSortNo() {
//            return sortNo;
//        }
//
//        public void setSortNo(int sortNo) {
//            this.sortNo = sortNo;
//        }
//
//        public Object getMonth() {
//            return month;
//        }
//
//        public void setMonth(Object month) {
//            this.month = month;
//        }
//
//        public int getMonthSaled() {
//            return monthSaled;
//        }
//
//        public void setMonthSaled(int monthSaled) {
//            this.monthSaled = monthSaled;
//        }
//
//        public int getTotalSaled() {
//            return totalSaled;
//        }
//
//        public void setTotalSaled(int totalSaled) {
//            this.totalSaled = totalSaled;
//        }
//
//        public int getPraiseNum() {
//            return praiseNum;
//        }
//
//        public void setPraiseNum(int praiseNum) {
//            this.praiseNum = praiseNum;
//        }
//
//        public int getCommentNum() {
//            return commentNum;
//        }
//
//        public void setCommentNum(int commentNum) {
//            this.commentNum = commentNum;
//        }
//
//        public int getGoodCommentNum() {
//            return goodCommentNum;
//        }
//
//        public void setGoodCommentNum(int goodCommentNum) {
//            this.goodCommentNum = goodCommentNum;
//        }
//
//        public int getMediumCommentNum() {
//            return mediumCommentNum;
//        }
//
//        public void setMediumCommentNum(int mediumCommentNum) {
//            this.mediumCommentNum = mediumCommentNum;
//        }
//
//        public int getBadCommentNum() {
//            return badCommentNum;
//        }
//
//        public void setBadCommentNum(int badCommentNum) {
//            this.badCommentNum = badCommentNum;
//        }
//
//        public double getCommentScore() {
//            return commentScore;
//        }
//
//        public void setCommentScore(double commentScore) {
//            this.commentScore = commentScore;
//        }
//
//        public int getStatus() {
//            return status;
//        }
//
//        public void setStatus(int status) {
//            this.status = status;
//        }
//
//        public int getHasDel() {
//            return hasDel;
//        }
//
//        public void setHasDel(int hasDel) {
//            this.hasDel = hasDel;
//        }
//
//        public Object getDelTime() {
//            return delTime;
//        }
//
//        public void setDelTime(Object delTime) {
//            this.delTime = delTime;
//        }
//
//        public String getBarcode() {
//            return barcode;
//        }
//
//        public void setBarcode(String barcode) {
//            this.barcode = barcode;
//        }
//
//        public int getIsStandard() {
//            return isStandard;
//        }
//
//        public void setIsStandard(int isStandard) {
//            this.isStandard = isStandard;
//        }
//
//        public Object getBrandName() {
//            return brandName;
//        }
//
//        public void setBrandName(Object brandName) {
//            this.brandName = brandName;
//        }
//
//        public int getCanReturn() {
//            return canReturn;
//        }
//
//        public void setCanReturn(int canReturn) {
//            this.canReturn = canReturn;
//        }
//
//        public int getDaysForReturn() {
//            return daysForReturn;
//        }
//
//        public void setDaysForReturn(int daysForReturn) {
//            this.daysForReturn = daysForReturn;
//        }
//
//        public int getCanChange() {
//            return canChange;
//        }
//
//        public void setCanChange(int canChange) {
//            this.canChange = canChange;
//        }
//
//        public int getDaysForChange() {
//            return daysForChange;
//        }
//
//        public void setDaysForChange(int daysForChange) {
//            this.daysForChange = daysForChange;
//        }
//
//        public String getShareUrl() {
//            return shareUrl;
//        }
//
//        public void setShareUrl(String shareUrl) {
//            this.shareUrl = shareUrl;
//        }
//
//        public List<GoodsSpecListBean> getGoodsSpecList() {
//            return goodsSpecList;
//        }
//
//        public void setGoodsSpecList(List<GoodsSpecListBean> goodsSpecList) {
//            this.goodsSpecList = goodsSpecList;
//        }
//
//        public List<GoodsAttributeListBean> getGoodsAttributeList() {
//            return goodsAttributeList;
//        }
//
//        public void setGoodsAttributeList(List<GoodsAttributeListBean> goodsAttributeList) {
//            this.goodsAttributeList = goodsAttributeList;
//        }
//
//        public static class GoodsSpecListBean {
//            /**
//             * id : 1919
//             * createTime : 2018-04-09 11:14:32
//             * modifyTime : 2018-04-24 18:15:30
//             * goodsId : 1536
//             * spec : 的方法大幅度
//             * originalPrice : 200.0
//             * price : 120.0
//             * boxNum : 0
//             * boxPrice : 5.0
//             * minOrderNum : 2
//             * orderLimit : 5
//             * stockType : 1
//             * stock : 8
//             * hasDel : 0
//             */
//
//            private int id;
//            private String createTime;
//            private String modifyTime;
//            private int goodsId;
//            private String spec;
//            private double originalPrice;
//            private double price;
//            private int boxNum;
//            private double boxPrice;
//            private int minOrderNum;
//            private int orderLimit;
//            private int stockType;
//            private int stock;
//            private int hasDel;
//
//            public int getId() {
//                return id;
//            }
//
//            public void setId(int id) {
//                this.id = id;
//            }
//
//            public String getCreateTime() {
//                return createTime;
//            }
//
//            public void setCreateTime(String createTime) {
//                this.createTime = createTime;
//            }
//
//            public String getModifyTime() {
//                return modifyTime;
//            }
//
//            public void setModifyTime(String modifyTime) {
//                this.modifyTime = modifyTime;
//            }
//
//            public int getGoodsId() {
//                return goodsId;
//            }
//
//            public void setGoodsId(int goodsId) {
//                this.goodsId = goodsId;
//            }
//
//            public String getSpec() {
//                return spec;
//            }
//
//            public void setSpec(String spec) {
//                this.spec = spec;
//            }
//
//            public double getOriginalPrice() {
//                return originalPrice;
//            }
//
//            public void setOriginalPrice(double originalPrice) {
//                this.originalPrice = originalPrice;
//            }
//
//            public double getPrice() {
//                return price;
//            }
//
//            public void setPrice(double price) {
//                this.price = price;
//            }
//
//            public int getBoxNum() {
//                return boxNum;
//            }
//
//            public void setBoxNum(int boxNum) {
//                this.boxNum = boxNum;
//            }
//
//            public double getBoxPrice() {
//                return boxPrice;
//            }
//
//            public void setBoxPrice(double boxPrice) {
//                this.boxPrice = boxPrice;
//            }
//
//            public int getMinOrderNum() {
//                return minOrderNum;
//            }
//
//            public void setMinOrderNum(int minOrderNum) {
//                this.minOrderNum = minOrderNum;
//            }
//
//            public int getOrderLimit() {
//                return orderLimit;
//            }
//
//            public void setOrderLimit(int orderLimit) {
//                this.orderLimit = orderLimit;
//            }
//
//            public int getStockType() {
//                return stockType;
//            }
//
//            public void setStockType(int stockType) {
//                this.stockType = stockType;
//            }
//
//            public int getStock() {
//                return stock;
//            }
//
//            public void setStock(int stock) {
//                this.stock = stock;
//            }
//
//            public int getHasDel() {
//                return hasDel;
//            }
//
//            public void setHasDel(int hasDel) {
//                this.hasDel = hasDel;
//            }
//        }
//
//        public static class GoodsAttributeListBean {
//            /**
//             * id : 281
//             * createTime : 2018-04-24 16:59:16
//             * modifyTime : 2018-04-24 16:59:16
//             * goodsId : 1536
//             * title : 111
//             * name : 111|*|111|*|111|*|111|*|111|*|rtgrt
//             * hasDel : 0
//             */
//
//            private int id;
//            private String createTime;
//            private String modifyTime;
//            private int goodsId;
//            private String title;
//            private String name;
//            private int hasDel;
//
//            public int getId() {
//                return id;
//            }
//
//            public void setId(int id) {
//                this.id = id;
//            }
//
//            public String getCreateTime() {
//                return createTime;
//            }
//
//            public void setCreateTime(String createTime) {
//                this.createTime = createTime;
//            }
//
//            public String getModifyTime() {
//                return modifyTime;
//            }
//
//            public void setModifyTime(String modifyTime) {
//                this.modifyTime = modifyTime;
//            }
//
//            public int getGoodsId() {
//                return goodsId;
//            }
//
//            public void setGoodsId(int goodsId) {
//                this.goodsId = goodsId;
//            }
//
//            public String getTitle() {
//                return title;
//            }
//
//            public void setTitle(String title) {
//                this.title = title;
//            }
//
//            public String getName() {
//                return name;
//            }
//
//            public void setName(String name) {
//                this.name = name;
//            }
//
//            public int getHasDel() {
//                return hasDel;
//            }
//
//            public void setHasDel(int hasDel) {
//                this.hasDel = hasDel;
//            }
//        }
//    }
}

package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.GoodsSpec;
import com.project.mgjandroid.bean.yellowpage.GoodsAttribute;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by SunXueLiang on 2017-09-08.
 */

public class NewRecommendGoodsModel extends Entity {

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
        private int agentId;
        private int type;
        private int merchantId;
        private Object groupPurchaseCouponType;
        private String goodsId;
        private int sortNo;
        private int hasDel;
        private int modifiedByAgentId;
        private Object modifiedByAgent;
        private Object agent;
        private Object merchantName;
        private Object goodsName;
        private GoodsDetailBean goodsDetail;
        private Object takeawayGoodsId;
        private Object groupPurchaseGoodsId;
        private Object groupBuyGoodsId;

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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }

        public Object getGroupPurchaseCouponType() {
            return groupPurchaseCouponType;
        }

        public void setGroupPurchaseCouponType(Object groupPurchaseCouponType) {
            this.groupPurchaseCouponType = groupPurchaseCouponType;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
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

        public int getModifiedByAgentId() {
            return modifiedByAgentId;
        }

        public void setModifiedByAgentId(int modifiedByAgentId) {
            this.modifiedByAgentId = modifiedByAgentId;
        }

        public Object getModifiedByAgent() {
            return modifiedByAgent;
        }

        public void setModifiedByAgent(Object modifiedByAgent) {
            this.modifiedByAgent = modifiedByAgent;
        }

        public Object getAgent() {
            return agent;
        }

        public void setAgent(Object agent) {
            this.agent = agent;
        }

        public Object getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(Object merchantName) {
            this.merchantName = merchantName;
        }

        public Object getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(Object goodsName) {
            this.goodsName = goodsName;
        }

        public GoodsDetailBean getGoodsDetail() {
            return goodsDetail;
        }

        public void setGoodsDetail(GoodsDetailBean goodsDetail) {
            this.goodsDetail = goodsDetail;
        }

        public Object getTakeawayGoodsId() {
            return takeawayGoodsId;
        }

        public void setTakeawayGoodsId(Object takeawayGoodsId) {
            this.takeawayGoodsId = takeawayGoodsId;
        }

        public Object getGroupPurchaseGoodsId() {
            return groupPurchaseGoodsId;
        }

        public void setGroupPurchaseGoodsId(Object groupPurchaseGoodsId) {
            this.groupPurchaseGoodsId = groupPurchaseGoodsId;
        }

        public Object getGroupBuyGoodsId() {
            return groupBuyGoodsId;
        }

        public void setGroupBuyGoodsId(Object groupBuyGoodsId) {
            this.groupBuyGoodsId = groupBuyGoodsId;
        }

        public static class GoodsDetailBean extends Entity {

            private String createTime;
            private String modifyTime;
            private String id;
            private int userId;
            private int agentId;
            private String goodsName;
            private String name;
            private String groupPurchaseName;
            private int minNum;
            private int maxNum;
            private int totalNum;
            private int totalUserNum;
            private double price;
            private double originalPrice;
            private Object totalPrice;
            private int days;
            private int deliveryDays;
            private String service;
            private String description;
            private Object marketingImg;
            private String imgs;
            private String images;
            private double agentRate;
            private double agentRateAmt;
            private double userAmt;
            private Object startTime;
            private Object defaultEndTime;
            private Object endTime;
            private int sortNo;
            private Object memo;
            private int status;
            private int hasDel;
            private int quotaCount;
            private GroupBuyUserBean groupBuyUser;
            private String shareUrl;
            private Object groupBuyOrderList;
            private Object agentInfoMap;
            private List<GoodsSpec> goodsSpecList;
            private List<GoodsAttribute> goodsAttributeList;
            private List<?> otherGroupBuyList;
            private int commentNum;
            private int goodCommentNum;
            private int mediumCommentNum;
            private int badCommentNum;
            private BigDecimal commentScore;
            private int monthSaled;
            private int totalSaled;
            private Long categoryId;

            public List<GoodsAttribute> getGoodsAttributeList() {
                return goodsAttributeList;
            }

            public void setGoodsAttributeList(List<GoodsAttribute> goodsAttributeList) {
                this.goodsAttributeList = goodsAttributeList;
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

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getAgentId() {
                return agentId;
            }

            public void setAgentId(int agentId) {
                this.agentId = agentId;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getGroupPurchaseName() {
                return groupPurchaseName;
            }

            public void setGroupPurchaseName(String groupPurchaseName) {
                this.groupPurchaseName = groupPurchaseName;
            }

            public int getMinNum() {
                return minNum;
            }

            public void setMinNum(int minNum) {
                this.minNum = minNum;
            }

            public int getMaxNum() {
                return maxNum;
            }

            public void setMaxNum(int maxNum) {
                this.maxNum = maxNum;
            }

            public int getTotalNum() {
                return totalNum;
            }

            public void setTotalNum(int totalNum) {
                this.totalNum = totalNum;
            }

            public int getTotalUserNum() {
                return totalUserNum;
            }

            public void setTotalUserNum(int totalUserNum) {
                this.totalUserNum = totalUserNum;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getOriginalPrice() {
                return originalPrice;
            }

            public void setOriginalPrice(double originalPrice) {
                this.originalPrice = originalPrice;
            }

            public Object getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(Object totalPrice) {
                this.totalPrice = totalPrice;
            }

            public int getDays() {
                return days;
            }

            public void setDays(int days) {
                this.days = days;
            }

            public int getDeliveryDays() {
                return deliveryDays;
            }

            public void setDeliveryDays(int deliveryDays) {
                this.deliveryDays = deliveryDays;
            }

            public String getService() {
                return service;
            }

            public void setService(String service) {
                this.service = service;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Object getMarketingImg() {
                return marketingImg;
            }

            public void setMarketingImg(Object marketingImg) {
                this.marketingImg = marketingImg;
            }

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }

            public double getAgentRate() {
                return agentRate;
            }

            public void setAgentRate(double agentRate) {
                this.agentRate = agentRate;
            }

            public double getAgentRateAmt() {
                return agentRateAmt;
            }

            public void setAgentRateAmt(double agentRateAmt) {
                this.agentRateAmt = agentRateAmt;
            }

            public double getUserAmt() {
                return userAmt;
            }

            public void setUserAmt(double userAmt) {
                this.userAmt = userAmt;
            }

            public Object getStartTime() {
                return startTime;
            }

            public void setStartTime(Object startTime) {
                this.startTime = startTime;
            }

            public Object getDefaultEndTime() {
                return defaultEndTime;
            }

            public void setDefaultEndTime(Object defaultEndTime) {
                this.defaultEndTime = defaultEndTime;
            }

            public Object getEndTime() {
                return endTime;
            }

            public void setEndTime(Object endTime) {
                this.endTime = endTime;
            }

            public int getSortNo() {
                return sortNo;
            }

            public void setSortNo(int sortNo) {
                this.sortNo = sortNo;
            }

            public Object getMemo() {
                return memo;
            }

            public void setMemo(Object memo) {
                this.memo = memo;
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

            public int getQuotaCount() {
                return quotaCount;
            }

            public void setQuotaCount(int quotaCount) {
                this.quotaCount = quotaCount;
            }

            public GroupBuyUserBean getGroupBuyUser() {
                return groupBuyUser;
            }

            public void setGroupBuyUser(GroupBuyUserBean groupBuyUser) {
                this.groupBuyUser = groupBuyUser;
            }

            public String getShareUrl() {
                return shareUrl;
            }

            public void setShareUrl(String shareUrl) {
                this.shareUrl = shareUrl;
            }

            public Object getGroupBuyOrderList() {
                return groupBuyOrderList;
            }

            public void setGroupBuyOrderList(Object groupBuyOrderList) {
                this.groupBuyOrderList = groupBuyOrderList;
            }

            public Object getAgentInfoMap() {
                return agentInfoMap;
            }

            public void setAgentInfoMap(Object agentInfoMap) {
                this.agentInfoMap = agentInfoMap;
            }

            public List<GoodsSpec> getGoodsSpecList() {
                return goodsSpecList;
            }

            public void setGoodsSpecList(List<GoodsSpec> goodsSpecList) {
                this.goodsSpecList = goodsSpecList;
            }

            public List<?> getOtherGroupBuyList() {
                return otherGroupBuyList;
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

            public BigDecimal getCommentScore() {
                return commentScore;
            }

            public void setCommentScore(BigDecimal commentScore) {
                this.commentScore = commentScore;
            }

            public void setOtherGroupBuyList(List<?> otherGroupBuyList) {
                this.otherGroupBuyList = otherGroupBuyList;

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

            public Long getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(Long categoryId) {
                this.categoryId = categoryId;
            }
            //            public static class GoodsSpecList extends Entity{
//
//                private int id;
//                private String createTime;
//                private String modifyTime;
//                private int goodsId;
//                private String spec;
//                private int minOrderNum;
//                private int orderLimit;
//                private int stockType;
//                private int hasDel;
//                private int boxNum;
//                private double originalPrice;
//                private double price;
//                private double boxPrice;
//
//                public int getId() {
//                    return id;
//                }
//
//                public void setId(int id) {
//                    this.id = id;
//                }
//
//                public String getCreateTime() {
//                    return createTime;
//                }
//
//                public void setCreateTime(String createTime) {
//                    this.createTime = createTime;
//                }
//
//                public String getModifyTime() {
//                    return modifyTime;
//                }
//
//                public void setModifyTime(String modifyTime) {
//                    this.modifyTime = modifyTime;
//                }
//
//                public int getGoodsId() {
//                    return goodsId;
//                }
//
//                public void setGoodsId(int goodsId) {
//                    this.goodsId = goodsId;
//                }
//
//                public String getSpec() {
//                    return spec;
//                }
//
//                public void setSpec(String spec) {
//                    this.spec = spec;
//                }
//
//                public int getMinOrderNum() {
//                    return minOrderNum;
//                }
//
//                public void setMinOrderNum(int minOrderNum) {
//                    this.minOrderNum = minOrderNum;
//                }
//
//                public int getOrderLimit() {
//                    return orderLimit;
//                }
//
//                public void setOrderLimit(int orderLimit) {
//                    this.orderLimit = orderLimit;
//                }
//
//                public int getStockType() {
//                    return stockType;
//                }
//
//                public void setStockType(int stockType) {
//                    this.stockType = stockType;
//                }
//
//                public int getHasDel() {
//                    return hasDel;
//                }
//
//                public void setHasDel(int hasDel) {
//                    this.hasDel = hasDel;
//                }
//
//                public int getBoxNum() {
//                    return boxNum;
//                }
//
//                public void setBoxNum(int boxNum) {
//                    this.boxNum = boxNum;
//                }
//
//                public double getOriginalPrice() {
//                    return originalPrice;
//                }
//
//                public void setOriginalPrice(double originalPrice) {
//                    this.originalPrice = originalPrice;
//                }
//
//                public double getPrice() {
//                    return price;
//                }
//
//                public void setPrice(double price) {
//                    this.price = price;
//                }
//
//                public double getBoxPrice() {
//                    return boxPrice;
//                }
//
//                public void setBoxPrice(double boxPrice) {
//                    this.boxPrice = boxPrice;
//                }
//            }

            public static class GroupBuyUserBean extends Entity {

                private int id;
                private String createTime;
                private String modifyTime;
                private String name;
                private String headerImg;
                private String mobile;
                private int sex;
                private String idCardNo;
                private String intro;
                private String address;
                private int benefitUserCount;
                private double compositeAvgScore;
                private double goodsAvgScore;
                private double serviceAvgScore;
                private double totalCommontsCount;
                private List<CommontsAllListBean> commontsAllList;

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

                public String getHeaderImg() {
                    return headerImg;
                }

                public void setHeaderImg(String headerImg) {
                    this.headerImg = headerImg;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public int getSex() {
                    return sex;
                }

                public void setSex(int sex) {
                    this.sex = sex;
                }

                public String getIdCardNo() {
                    return idCardNo;
                }

                public void setIdCardNo(String idCardNo) {
                    this.idCardNo = idCardNo;
                }

                public String getIntro() {
                    return intro;
                }

                public void setIntro(String intro) {
                    this.intro = intro;
                }

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public int getBenefitUserCount() {
                    return benefitUserCount;
                }

                public void setBenefitUserCount(int benefitUserCount) {
                    this.benefitUserCount = benefitUserCount;
                }

                public double getCompositeAvgScore() {
                    return compositeAvgScore;
                }

                public void setCompositeAvgScore(double compositeAvgScore) {
                    this.compositeAvgScore = compositeAvgScore;
                }

                public double getGoodsAvgScore() {
                    return goodsAvgScore;
                }

                public void setGoodsAvgScore(double goodsAvgScore) {
                    this.goodsAvgScore = goodsAvgScore;
                }

                public double getServiceAvgScore() {
                    return serviceAvgScore;
                }

                public void setServiceAvgScore(double serviceAvgScore) {
                    this.serviceAvgScore = serviceAvgScore;
                }

                public double getTotalCommontsCount() {
                    return totalCommontsCount;
                }

                public void setTotalCommontsCount(double totalCommontsCount) {
                    this.totalCommontsCount = totalCommontsCount;
                }

                public List<CommontsAllListBean> getCommontsAllList() {
                    return commontsAllList;
                }

                public void setCommontsAllList(List<CommontsAllListBean> commontsAllList) {
                    this.commontsAllList = commontsAllList;
                }

                public static class CommontsAllListBean extends Entity {

                    private int id;
                    private String createTime;
                    private String modifyTime;
                    private String groupBuyId;
                    private String groupBuyOrderId;
                    private int userId;
                    private double compositeScore;
                    private double goodsScore;
                    private double serviceScore;
                    private String groupBuyScoreComments;
                    private Object imgs;
                    private int hasDel;
                    private UserInfoBean userInfo;

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

                    public String getGroupBuyId() {
                        return groupBuyId;
                    }

                    public void setGroupBuyId(String groupBuyId) {
                        this.groupBuyId = groupBuyId;
                    }

                    public String getGroupBuyOrderId() {
                        return groupBuyOrderId;
                    }

                    public void setGroupBuyOrderId(String groupBuyOrderId) {
                        this.groupBuyOrderId = groupBuyOrderId;
                    }

                    public int getUserId() {
                        return userId;
                    }

                    public void setUserId(int userId) {
                        this.userId = userId;
                    }

                    public double getCompositeScore() {
                        return compositeScore;
                    }

                    public void setCompositeScore(double compositeScore) {
                        this.compositeScore = compositeScore;
                    }

                    public double getGoodsScore() {
                        return goodsScore;
                    }

                    public void setGoodsScore(double goodsScore) {
                        this.goodsScore = goodsScore;
                    }

                    public double getServiceScore() {
                        return serviceScore;
                    }

                    public void setServiceScore(double serviceScore) {
                        this.serviceScore = serviceScore;
                    }

                    public String getGroupBuyScoreComments() {
                        return groupBuyScoreComments;
                    }

                    public void setGroupBuyScoreComments(String groupBuyScoreComments) {
                        this.groupBuyScoreComments = groupBuyScoreComments;
                    }

                    public Object getImgs() {
                        return imgs;
                    }

                    public void setImgs(Object imgs) {
                        this.imgs = imgs;
                    }

                    public int getHasDel() {
                        return hasDel;
                    }

                    public void setHasDel(int hasDel) {
                        this.hasDel = hasDel;
                    }

                    public UserInfoBean getUserInfo() {
                        return userInfo;
                    }

                    public void setUserInfo(UserInfoBean userInfo) {
                        this.userInfo = userInfo;
                    }

                    public static class UserInfoBean extends Entity {

                        private int id;
                        private String createTime;
                        private String modifyTime;
                        private int agentId;
                        private String userAndAgentTime;
                        private String name;
                        private String mobile;
                        private String pwd;
                        private String headerImg;
                        private String regTime;
                        private String lastLoginTime;
                        private String channel;
                        private Object token;
                        private Object city;
                        private Object inviterAppUserId;
                        private Object userToken;
                        private Object userAccount;
                        private Object totalAmt;
                        private Object agent;
                        private Object inviter;

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

                        public String getUserAndAgentTime() {
                            return userAndAgentTime;
                        }

                        public void setUserAndAgentTime(String userAndAgentTime) {
                            this.userAndAgentTime = userAndAgentTime;
                        }

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }

                        public String getMobile() {
                            return mobile;
                        }

                        public void setMobile(String mobile) {
                            this.mobile = mobile;
                        }

                        public String getPwd() {
                            return pwd;
                        }

                        public void setPwd(String pwd) {
                            this.pwd = pwd;
                        }

                        public String getHeaderImg() {
                            return headerImg;
                        }

                        public void setHeaderImg(String headerImg) {
                            this.headerImg = headerImg;
                        }

                        public String getRegTime() {
                            return regTime;
                        }

                        public void setRegTime(String regTime) {
                            this.regTime = regTime;
                        }

                        public String getLastLoginTime() {
                            return lastLoginTime;
                        }

                        public void setLastLoginTime(String lastLoginTime) {
                            this.lastLoginTime = lastLoginTime;
                        }

                        public String getChannel() {
                            return channel;
                        }

                        public void setChannel(String channel) {
                            this.channel = channel;
                        }

                        public Object getToken() {
                            return token;
                        }

                        public void setToken(Object token) {
                            this.token = token;
                        }

                        public Object getCity() {
                            return city;
                        }

                        public void setCity(Object city) {
                            this.city = city;
                        }

                        public Object getInviterAppUserId() {
                            return inviterAppUserId;
                        }

                        public void setInviterAppUserId(Object inviterAppUserId) {
                            this.inviterAppUserId = inviterAppUserId;
                        }

                        public Object getUserToken() {
                            return userToken;
                        }

                        public void setUserToken(Object userToken) {
                            this.userToken = userToken;
                        }

                        public Object getUserAccount() {
                            return userAccount;
                        }

                        public void setUserAccount(Object userAccount) {
                            this.userAccount = userAccount;
                        }

                        public Object getTotalAmt() {
                            return totalAmt;
                        }

                        public void setTotalAmt(Object totalAmt) {
                            this.totalAmt = totalAmt;
                        }

                        public Object getAgent() {
                            return agent;
                        }

                        public void setAgent(Object agent) {
                            this.agent = agent;
                        }

                        public Object getInviter() {
                            return inviter;
                        }

                        public void setInviter(Object inviter) {
                            this.inviter = inviter;
                        }
                    }
                }
            }
        }
    }
}

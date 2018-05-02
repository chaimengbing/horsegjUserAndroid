package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by User_Cjh on 2016/8/22.
 */
public class GroupInfo extends Entity {
    private String createTime;
    private String modifyTime;
    private String id;
    private int userId;
    private int agentId;
    private String goodsName;
    private int minNum;
    private int maxNum;
    private int totalNum;
    private int totalUserNum;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private Object totalPrice;
    private int days;
    private int deliveryDays;
    private String service;
    private String description;
    private String marketingImg;
    private String imgs;
    private BigDecimal agentRate;
    private BigDecimal agentRateAmt;
    private BigDecimal userAmt;
    private String startTime;
    private String defaultEndTime;
    private String endTime;
    private int sortNo;
    private Object memo;
    private int status;//1 "审核中",2 "审核失败",3 "组团中",4 "组团成功",5 "组团失败";

    private int hasDel;
    private GroupBuyUserEntity groupBuyUser;
    private List<OtherGroupBuyListBean> otherGroupBuyList;
    private String shareUrl;
    private boolean isOver = false;

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public List<OtherGroupBuyListBean> getOtherGroupBuyList() {
        return otherGroupBuyList;
    }

    public void setOtherGroupBuyList(List<OtherGroupBuyListBean> otherGroupBuyList) {
        this.otherGroupBuyList = otherGroupBuyList;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
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

    public String getMarketingImg() {
        return marketingImg;
    }

    public void setMarketingImg(String marketingImg) {
        this.marketingImg = marketingImg;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public BigDecimal getAgentRate() {
        return agentRate;
    }

    public void setAgentRate(BigDecimal agentRate) {
        this.agentRate = agentRate;
    }

    public BigDecimal getAgentRateAmt() {
        return agentRateAmt;
    }

    public void setAgentRateAmt(BigDecimal agentRateAmt) {
        this.agentRateAmt = agentRateAmt;
    }

    public BigDecimal getUserAmt() {
        return userAmt;
    }

    public void setUserAmt(BigDecimal userAmt) {
        this.userAmt = userAmt;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDefaultEndTime() {
        return defaultEndTime;
    }

    public void setDefaultEndTime(String defaultEndTime) {
        this.defaultEndTime = defaultEndTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
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

    public GroupBuyUserEntity getGroupBuyUser() {
        return groupBuyUser;
    }

    public void setGroupBuyUser(GroupBuyUserEntity groupBuyUser) {
        this.groupBuyUser = groupBuyUser;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setIsOver(boolean isOver) {
        this.isOver = isOver;
    }

    public static class GroupBuyUserEntity extends Entity {
        private int id;
        private String createTime;
        private String modifyTime;
        private String name;
        private String mobile;
        private int sex;
        private String idCardNo;
        private String intro;
        private String address;
        private int benefitUserCount;
        private BigDecimal compositeAvgScore;
        private BigDecimal goodsAvgScore;
        private BigDecimal serviceAvgScore;
        private BigDecimal totalCommontsCount;
        private List<GroupUserComments> commontsAllList;
        private String headerImg;

        public String getHeaderImg() {
            return headerImg;
        }

        public void setHeaderImg(String headerImg) {
            this.headerImg = headerImg;
        }

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

        public BigDecimal getCompositeAvgScore() {
            return compositeAvgScore;
        }

        public void setCompositeAvgScore(BigDecimal compositeAvgScore) {
            this.compositeAvgScore = compositeAvgScore;
        }

        public BigDecimal getGoodsAvgScore() {
            return goodsAvgScore;
        }

        public void setGoodsAvgScore(BigDecimal goodsAvgScore) {
            this.goodsAvgScore = goodsAvgScore;
        }

        public BigDecimal getServiceAvgScore() {
            return serviceAvgScore;
        }

        public void setServiceAvgScore(BigDecimal serviceAvgScore) {
            this.serviceAvgScore = serviceAvgScore;
        }

        public BigDecimal getTotalCommontsCount() {
            return totalCommontsCount;
        }

        public void setTotalCommontsCount(BigDecimal totalCommontsCount) {
            this.totalCommontsCount = totalCommontsCount;
        }

        public List<GroupUserComments> getCommontsAllList() {
            return commontsAllList;
        }

        public void setCommontsAllList(List<GroupUserComments> commontsAllList) {
            this.commontsAllList = commontsAllList;
        }
    }

    public static class OtherGroupBuyListBean extends Entity {
        private String createTime;
        private String modifyTime;
        private String id;
        private int userId;
        private int agentId;
        private String goodsName;
        private int minNum;
        private int maxNum;
        private int totalNum;
        private int totalUserNum;
        private BigDecimal price;
        private double originalPrice;
        private Object totalPrice;
        private int days;
        private int deliveryDays;
        private String service;
        private String description;
        private String marketingImg;
        private String imgs;
        private double agentRate;
        private double agentRateAmt;
        private double userAmt;
        private String startTime;
        private String defaultEndTime;
        private Object endTime;
        private int sortNo;
        private Object memo;
        private int status;
        private int hasDel;

        private GroupBuyUserBean groupBuyUser;
        private Object otherGroupBuyList;

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

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
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

        public String getMarketingImg() {
            return marketingImg;
        }

        public void setMarketingImg(String marketingImg) {
            this.marketingImg = marketingImg;
        }

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
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

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getDefaultEndTime() {
            return defaultEndTime;
        }

        public void setDefaultEndTime(String defaultEndTime) {
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

        public GroupBuyUserBean getGroupBuyUser() {
            return groupBuyUser;
        }

        public void setGroupBuyUser(GroupBuyUserBean groupBuyUser) {
            this.groupBuyUser = groupBuyUser;
        }

        public Object getOtherGroupBuyList() {
            return otherGroupBuyList;
        }

        public void setOtherGroupBuyList(Object otherGroupBuyList) {
            this.otherGroupBuyList = otherGroupBuyList;
        }

        public static class GroupBuyUserBean extends Entity {
            private int id;
            private String createTime;
            private String modifyTime;
            private String name;
            private Object headerImg;
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
            private Object commontsAllList;

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

            public Object getHeaderImg() {
                return headerImg;
            }

            public void setHeaderImg(Object headerImg) {
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

            public Object getCommontsAllList() {
                return commontsAllList;
            }

            public void setCommontsAllList(Object commontsAllList) {
                this.commontsAllList = commontsAllList;
            }
        }
    }
}

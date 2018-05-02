package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;

/**
 * Created by User_Cjh on 2016/8/26.
 */
public class GroupUserComments extends Entity {
    private int id;
    private String createTime;
    private String modifyTime;
    private String groupBuyId;
    private String groupBuyOrderId;
    private int userId;
    private BigDecimal compositeScore;
    private BigDecimal goodsScore;
    private BigDecimal serviceScore;
    private String groupBuyScoreComments;
    private String imgs;
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

    public BigDecimal getCompositeScore() {
        return compositeScore;
    }

    public void setCompositeScore(BigDecimal compositeScore) {
        this.compositeScore = compositeScore;
    }

    public BigDecimal getGoodsScore() {
        return goodsScore;
    }

    public void setGoodsScore(BigDecimal goodsScore) {
        this.goodsScore = goodsScore;
    }

    public BigDecimal getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(BigDecimal serviceScore) {
        this.serviceScore = serviceScore;
    }

    public String getGroupBuyScoreComments() {
        return groupBuyScoreComments;
    }

    public void setGroupBuyScoreComments(String groupBuyScoreComments) {
        this.groupBuyScoreComments = groupBuyScoreComments;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
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
        private String name;
        private String mobile;
        private String pwd;
        private String headerImg;
        private String regTime;
        private String lastLoginTime;
        private String channel;
        private Object token;
        private Object city;
        private Object userToken;
        private Object userAccount;

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
    }
}

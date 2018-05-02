package com.project.mgjandroid.bean.newhomepage;

import com.project.mgjandroid.model.Entity;

/**
 * Created by SunXueLiang on 2017-09-08.
 */

public class UserInfo extends Entity {
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
    private User userInfo;

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

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }
}

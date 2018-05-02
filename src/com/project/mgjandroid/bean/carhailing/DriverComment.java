package com.project.mgjandroid.bean.carhailing;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;

/**
 * Created by User_Cjh on 2016/12/19.
 */
public class DriverComment extends Entity {
    private long id;
    private String createTime;
    private String modifyTime;
    private long agentId;
    private long chauffeurId;
    private long chauffeurTripId;
    private String chauffeurOrderId;
    private long userId;
    private String userName;
    private String userHeaderImg;
    private int anonymous;
    private BigDecimal compositeScore;
    private double serviceScore;
    private double carConditionScore;
    private double driveScore;
    private String chauffeurOrderScoreComments;
    private String imgs;
    private int hasDel;
    private Object userInfo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getAgentId() {
        return agentId;
    }

    public void setAgentId(long agentId) {
        this.agentId = agentId;
    }

    public long getChauffeurId() {
        return chauffeurId;
    }

    public void setChauffeurId(long chauffeurId) {
        this.chauffeurId = chauffeurId;
    }

    public long getChauffeurTripId() {
        return chauffeurTripId;
    }

    public void setChauffeurTripId(long chauffeurTripId) {
        this.chauffeurTripId = chauffeurTripId;
    }

    public String getChauffeurOrderId() {
        return chauffeurOrderId;
    }

    public void setChauffeurOrderId(String chauffeurOrderId) {
        this.chauffeurOrderId = chauffeurOrderId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHeaderImg() {
        return userHeaderImg;
    }

    public void setUserHeaderImg(String userHeaderImg) {
        this.userHeaderImg = userHeaderImg;
    }

    public int getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(int anonymous) {
        this.anonymous = anonymous;
    }

    public BigDecimal getCompositeScore() {
        return compositeScore;
    }

    public void setCompositeScore(BigDecimal compositeScore) {
        this.compositeScore = compositeScore;
    }

    public double getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(double serviceScore) {
        this.serviceScore = serviceScore;
    }

    public double getCarConditionScore() {
        return carConditionScore;
    }

    public void setCarConditionScore(double carConditionScore) {
        this.carConditionScore = carConditionScore;
    }

    public double getDriveScore() {
        return driveScore;
    }

    public void setDriveScore(double driveScore) {
        this.driveScore = driveScore;
    }

    public String getChauffeurOrderScoreComments() {
        return chauffeurOrderScoreComments;
    }

    public void setChauffeurOrderScoreComments(String chauffeurOrderScoreComments) {
        this.chauffeurOrderScoreComments = chauffeurOrderScoreComments;
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

    public Object getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Object userInfo) {
        this.userInfo = userInfo;
    }
}

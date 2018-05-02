package com.project.mgjandroid.bean.newhomepage;

/**
 * Created by SunXueLiang on 2017-09-08.
 */

public class CommontsAll extends GroupBuy {
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
    private UserInfo commontsAllList;

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

    public UserInfo getCommontsAllList() {
        return commontsAllList;
    }

    public void setCommontsAllList(UserInfo commontsAllList) {
        this.commontsAllList = commontsAllList;
    }
}

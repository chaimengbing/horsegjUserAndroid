package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class GroupBuyUser extends Entity {
    private static final long serialVersionUID = 1L;
    /**
     * 姓名
     **/
    private String name;
    /**
     * 头像
     */
    private String headerImg = "";
    /**
     * 联系方式
     **/
    private String mobile;
    /**
     * 性别0:女;1:男
     **/
    private int sex;
    /**
     * 身份证号码
     **/
    private String idCardNo;
    /**
     * 个人介绍
     **/
    private String intro;
    /**
     * 地址
     **/
    private String address;
    /**
     * 受益人数
     **/
    private int benefitUserCount;
    /**
     * 总体评价
     **/
    private BigDecimal compositeAvgScore = new BigDecimal(5.0);
    /**
     * 商品评价
     **/
    private BigDecimal goodsAvgScore = new BigDecimal(5.0);
    /**
     * 服务评价
     **/
    private BigDecimal serviceAvgScore = new BigDecimal(5.0);
    /**
     * 评价总数
     **/
    private BigDecimal totalCommontsCount = BigDecimal.ZERO;
    /**
     * 该用户发起的所有团购评价信息
     **/
    private List<GroupBuyCommonts> commontsAllList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public List<GroupBuyCommonts> getCommontsAllList() {
        return commontsAllList;
    }

    public void setCommontsAllList(List<GroupBuyCommonts> commontsAllList) {
        this.commontsAllList = commontsAllList;
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

    public BigDecimal getTotalCommontsCount() {
        return totalCommontsCount;
    }

    public void setTotalCommontsCount(BigDecimal totalCommontsCount) {
        this.totalCommontsCount = totalCommontsCount;
    }

}
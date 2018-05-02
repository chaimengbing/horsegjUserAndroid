package com.project.mgjandroid.bean;

import java.io.Serializable;

/**
 * Created by User_Cjh on 2016/8/18.
 */
public class CreateGroup implements Serializable {

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getMinMember() {
        return minMember;
    }

    public void setMinMember(String minMember) {
        this.minMember = minMember;
    }

    public String getMaxMember() {
        return maxMember;
    }

    public void setMaxMember(String maxMember) {
        this.maxMember = maxMember;
    }

    public String getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(String groupPrice) {
        this.groupPrice = groupPrice;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getGoodsIntro() {
        return goodsIntro;
    }

    public void setGoodsIntro(String goodsIntro) {
        this.goodsIntro = goodsIntro;
    }

    public String getGoodsImgs() {
        return goodsImgs;
    }

    public void setGoodsImgs(String goodsImgs) {
        this.goodsImgs = goodsImgs;
    }

    public String getGoodsPath() {
        return goodsPath;
    }

    public void setGoodsPath(String goodsPath) {
        this.goodsPath = goodsPath;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getSendDays() {
        return sendDays;
    }

    public void setSendDays(String sendDays) {
        this.sendDays = sendDays;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFlied() {
        return flied;
    }

    public void setFlied(String flied) {
        this.flied = flied;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getOneselfIntro() {
        return oneselfIntro;
    }

    public void setOneselfIntro(String oneselfIntro) {
        this.oneselfIntro = oneselfIntro;
    }

    private int userId;
    private String goodsName = "";
    private String minMember = "";
    private String maxMember = "";
    private String groupPrice = "";
    private String marketPrice = "";
    private String goodsIntro = "";
    private String goodsImgs = "";
    private String goodsPath = "";
    private String days = "";
    private String sendDays = "";
    private String services;
    private String name = "";
    private String sex = "1";
    private String phone = "";
    private String address = "";
    private String flied;
    private String idCard = "";
    private String oneselfIntro = "";
}

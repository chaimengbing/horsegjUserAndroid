package com.project.mgjandroid.bean.information;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/12/12.
 */

public class InformationWasteRecovery extends InformationBaseProperty {


    private static final long serialVersionUID = 1L;
    /**
     * 身份类别（1：个人；2：商家）
     */
    private int type;
    /**
     * 头像
     **/
    private String headImg;
    /**
     * 服务区域
     **/
    private String serviceArea;
    /**
     * 擅长领域
     */
    private String goodField;
    /**
     * 评分
     */
    private BigDecimal score = new BigDecimal(0.0);

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getGoodField() {
        return goodField;
    }

    public void setGoodField(String goodField) {
        this.goodField = goodField;
    }

}

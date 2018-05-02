package com.project.mgjandroid.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class GroupBuyCommonts implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 拼团信息id
     **/
    private String groupBuyId;
    /**
     * 团购订单编号
     **/
    private String groupBuyOrderId;
    /**
     * 用户编号
     **/
    private Long userId;
    /**
     * 总体评价
     **/
    private BigDecimal compositeScore = new BigDecimal(5.0);
    /**
     * 商品评价
     **/
    private BigDecimal goodsScore = new BigDecimal(5.0);
    /**
     * 服务评价
     **/
    private BigDecimal serviceScore = new BigDecimal(5.0);
    /**
     * 评价内容
     **/
    private String groupBuyScoreComments;
    /**
     * 图片
     **/
    private String imgs;
    /**
     * 是否删除 0：未删除，1：已删除
     **/
    private int hasDel;

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

}
package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

/**
 * Created by yuandi on 2016/9/22.
 */
public class RecommendCategoryGoods extends Entity {

    private Long id;
    /**
     * 代理商编号
     **/
    private Long agentId;
    /**
     * 商家编号
     **/
    private Long merchantId;
    /**
     * 推荐分类编号
     **/
    private Long recommendCategoryId;
    private int sortNo;
    /**
     * 商品编号
     **/
    private Long goodsId;
    /**
     * 商品类型1:外卖;2:商超
     **/
    private int goodsType;

    private int hasDel;

    private Goods goods;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getRecommendCategoryId() {
        return recommendCategoryId;
    }

    public void setRecommendCategoryId(Long recommendCategoryId) {
        this.recommendCategoryId = recommendCategoryId;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public int getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(int goodsType) {
        this.goodsType = goodsType;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

}

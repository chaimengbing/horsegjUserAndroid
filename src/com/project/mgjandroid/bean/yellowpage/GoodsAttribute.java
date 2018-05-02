package com.project.mgjandroid.bean.yellowpage;

import java.io.Serializable;

/**
 * Created by SunXueLiang on 2017-12-04.
 */

public class GoodsAttribute implements Serializable {

    private Long id;
    private Long goodsId;
    private String title;
    private String name;
    private int hasDel;
    /**
     * 购买数量
     */
    private int buyCount;

    public int getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }
}

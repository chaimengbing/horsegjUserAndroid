package com.project.mgjandroid.bean;

import java.util.List;

/**
 * Created by yuandi on 2016/9/22.
 */
public class RecommendCategory extends BaseProperty {

    /**
     * 代理商编号
     **/
    private Long agentId;
    /**
     * 推荐分类名称
     **/
    private String name;
    /**
     * 推荐分类名称
     **/
    private int sortNo;
    /**
     * 推荐分类商品展示类型 1:一行(每行展示8个) 2:两行(每行展示2个) 3:两行(每行展示3个)
     **/
    private int showType;

    private int hasDel;

    private List<RecommendCategoryGoods> recommendCategoryGoodsList;

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public List<RecommendCategoryGoods> getRecommendCategoryGoodsList() {
        return recommendCategoryGoodsList;
    }

    public void setRecommendCategoryGoodsList(List<RecommendCategoryGoods> recommendCategoryGoodsList) {
        this.recommendCategoryGoodsList = recommendCategoryGoodsList;
    }

}

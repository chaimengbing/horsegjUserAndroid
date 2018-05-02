package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yuandi on 2016/3/9.
 */
public class Menu extends Entity {

    private Long id;
    /**
     * 商家编号
     */
    private Long merchantId;
    /**
     * 名称
     */
    private String name;
    /**
     * 排序号
     */
    private int sortNo;
    /**
     * 描述
     */
    private String description;
    /**
     * 是否删除
     */
    private int hasDel;
    /**
     * 图标
     */
    private String icon;

    private List<Goods> goodsList;

    private String createTime;

    private String modifyTime;

    private Long parentId;

    private int level;

    private int type;

    private List<Menu> goodsCategoryList;

    private boolean isSelected;

    private Long isMandatory;

    public Long getIsMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(Long isMandatory) {
        this.isMandatory = isMandatory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Menu> getGoodsCategoryList() {
        return goodsCategoryList;
    }

    public void setGoodsCategoryList(List<Menu> goodsCategoryList) {
        this.goodsCategoryList = goodsCategoryList;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
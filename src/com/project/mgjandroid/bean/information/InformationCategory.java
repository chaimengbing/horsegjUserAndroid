package com.project.mgjandroid.bean.information;

import com.project.mgjandroid.model.Entity;

import java.util.Date;

/**
 * Created by yuandi on 2016/11/19.
 */

public class InformationCategory extends Entity {

    private Long id;

    private Date createTime;

    private Date modifyTime;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 排序编号，默认为0
     */
    private int sortNo;

    private int hasDel;
    /**
     * 是否热门0:否；1：是
     */
    private int isHot;
    /**
     * 设置热门时间
     */
    private Date isHotTime;

    private Long informationServiceCategoryId;

    private String informationServiceCategoryName;

    private boolean check;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
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

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    public Date getIsHotTime() {
        return isHotTime;
    }

    public void setIsHotTime(Date isHotTime) {
        this.isHotTime = isHotTime;
    }

    public Long getInformationServiceCategoryId() {
        return informationServiceCategoryId;
    }

    public void setInformationServiceCategoryId(Long informationServiceCategoryId) {
        this.informationServiceCategoryId = informationServiceCategoryId;
    }

    public String getInformationServiceCategoryName() {
        return informationServiceCategoryName;
    }

    public void setInformationServiceCategoryName(String informationServiceCategoryName) {
        this.informationServiceCategoryName = informationServiceCategoryName;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}

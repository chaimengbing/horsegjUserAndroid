package com.project.mgjandroid.bean.information;

import com.project.mgjandroid.model.Entity;

import java.util.Date;
import java.util.List;

/**
 * Created by yuandi on 2016/11/19.
 */

public class InformationServiceCategory extends Entity {

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
     * 职位类别关联ID
     */
    private int informationTypeId;

    private List<InformationCategory> informationCategoryList;

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

    public int getInformationTypeId() {
        return informationTypeId;
    }

    public void setInformationTypeId(int informationTypeId) {
        this.informationTypeId = informationTypeId;
    }

    public List<InformationCategory> getInformationCategoryList() {
        return informationCategoryList;
    }

    public void setInformationCategoryList(List<InformationCategory> informationCategoryList) {
        this.informationCategoryList = informationCategoryList;
    }
}

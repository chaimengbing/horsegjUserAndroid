package com.project.mgjandroid.bean.groupbuying;

import com.project.mgjandroid.bean.BaseBean;

/**
 * Created by yuandi on 2017/3/14.
 */

public class GroupPurchasePrimaryCategory extends BaseBean {

    /**
     * 名称
     */
    private String name;
    /**
     * 代理商编号
     */
    private Long agentId;
    /**
     * 图片地址
     */
    private String picUrl = "";
    /**
     * 灰色图片地址
     */
    private String grayUrl = "";
    /**
     * 排序编号
     */
    private int sortNo;
    /**
     * 是否可用 0可用1不可用
     */
    private int graySwitch;
    /**
     * 是否删除
     */
    private int hasDel;
    /**
     * 跳转类型1：网址链接，2:团购分类
     **/
    private int gotoType;
    /**
     * 跳转地址
     */
    private String gotoUrl = "";
    /**
     * 【团购】一级分类
     **/
    private Long groupPurchaseCategoryId;
    /**
     * 【团购】二级分类
     **/
    private Long childGroupPurchaseCategoryId;
    /**
     * 【团购】一级分类名称
     **/
    private String groupPurchaseCategoryName;
    /**
     * 【团购】二级分类名称
     **/
    private String childGroupPurchaseCategoryName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getGrayUrl() {
        return grayUrl;
    }

    public void setGrayUrl(String grayUrl) {
        this.grayUrl = grayUrl;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public int getGraySwitch() {
        return graySwitch;
    }

    public void setGraySwitch(int graySwitch) {
        this.graySwitch = graySwitch;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public int getGotoType() {
        return gotoType;
    }

    public void setGotoType(int gotoType) {
        this.gotoType = gotoType;
    }

    public String getGotoUrl() {
        return gotoUrl;
    }

    public void setGotoUrl(String gotoUrl) {
        this.gotoUrl = gotoUrl;
    }

    public Long getGroupPurchaseCategoryId() {
        return groupPurchaseCategoryId;
    }

    public void setGroupPurchaseCategoryId(Long groupPurchaseCategoryId) {
        this.groupPurchaseCategoryId = groupPurchaseCategoryId;
    }

    public Long getChildGroupPurchaseCategoryId() {
        return childGroupPurchaseCategoryId;
    }

    public void setChildGroupPurchaseCategoryId(Long childGroupPurchaseCategoryId) {
        this.childGroupPurchaseCategoryId = childGroupPurchaseCategoryId;
    }

    public String getGroupPurchaseCategoryName() {
        return groupPurchaseCategoryName;
    }

    public void setGroupPurchaseCategoryName(String groupPurchaseCategoryName) {
        this.groupPurchaseCategoryName = groupPurchaseCategoryName;
    }

    public String getChildGroupPurchaseCategoryName() {
        return childGroupPurchaseCategoryName;
    }

    public void setChildGroupPurchaseCategoryName(String childGroupPurchaseCategoryName) {
        this.childGroupPurchaseCategoryName = childGroupPurchaseCategoryName;
    }
}

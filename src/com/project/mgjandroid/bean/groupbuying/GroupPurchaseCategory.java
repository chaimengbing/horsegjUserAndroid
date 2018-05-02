package com.project.mgjandroid.bean.groupbuying;

import com.project.mgjandroid.bean.BaseBean;

import java.util.List;

/**
 * Created by yuandi on 2017/3/11.
 */

public class GroupPurchaseCategory extends BaseBean {

    /**
     * 代理商编号
     */
    private long agentId;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 上级分类名称。最高级分类的parentId为0
     */
    private long parentId;

    private String icon = "";
    /**
     * 1：一级，2：二级
     */
    private int level;
    /**
     * 排序编号，默认为0
     */
    private int sortNo;
    /**
     * 0正常;1删除
     **/
    private int hasDel;

    private List<GroupPurchaseCategory> childGroupPurchaseCategoryList;

    private boolean selected;

    public long getAgentId() {
        return agentId;
    }

    public void setAgentId(long agentId) {
        this.agentId = agentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public List<GroupPurchaseCategory> getChildGroupPurchaseCategoryList() {
        return childGroupPurchaseCategoryList;
    }

    public void setChildGroupPurchaseCategoryList(List<GroupPurchaseCategory> childGroupPurchaseCategoryList) {
        this.childGroupPurchaseCategoryList = childGroupPurchaseCategoryList;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

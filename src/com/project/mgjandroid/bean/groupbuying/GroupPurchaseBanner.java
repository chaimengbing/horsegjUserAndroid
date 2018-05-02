package com.project.mgjandroid.bean.groupbuying;

import com.project.mgjandroid.bean.Agent;
import com.project.mgjandroid.bean.BaseBean;

/**
 * Created by yuandi on 2017/3/14.
 */

public class GroupPurchaseBanner extends BaseBean {

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
     * 页面地址
     */
    private String url = "";
    /**
     * 排序编号
     */
    private int sortNo;
    /**
     * 是否删除
     */
    private int hasDel;
    /**
     * 修改人
     */
    private String modifiedBy;
    /**
     * 广告类型1：链接地址，2：团购优惠券，3：团购商家, 4：团购分类
     **/
    private int bannerType;
    /**
     * 团购商家id
     */
    private Long groupPurchaseMerchantId;
    /**
     * 团购优惠券编号
     **/
    private Long groupPurchaseCouponId;
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

    private Agent agent;

    private GroupPurchaseMerchant groupPurchaseMerchant;

    private GroupPurchaseCoupon groupPurchaseCoupon;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public int getBannerType() {
        return bannerType;
    }

    public void setBannerType(int bannerType) {
        this.bannerType = bannerType;
    }

    public Long getGroupPurchaseMerchantId() {
        return groupPurchaseMerchantId;
    }

    public void setGroupPurchaseMerchantId(Long groupPurchaseMerchantId) {
        this.groupPurchaseMerchantId = groupPurchaseMerchantId;
    }

    public Long getGroupPurchaseCouponId() {
        return groupPurchaseCouponId;
    }

    public void setGroupPurchaseCouponId(Long groupPurchaseCouponId) {
        this.groupPurchaseCouponId = groupPurchaseCouponId;
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

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public GroupPurchaseMerchant getGroupPurchaseMerchant() {
        return groupPurchaseMerchant;
    }

    public void setGroupPurchaseMerchant(GroupPurchaseMerchant groupPurchaseMerchant) {
        this.groupPurchaseMerchant = groupPurchaseMerchant;
    }

    public GroupPurchaseCoupon getGroupPurchaseCoupon() {
        return groupPurchaseCoupon;
    }

    public void setGroupPurchaseCoupon(GroupPurchaseCoupon groupPurchaseCoupon) {
        this.groupPurchaseCoupon = groupPurchaseCoupon;
    }
}

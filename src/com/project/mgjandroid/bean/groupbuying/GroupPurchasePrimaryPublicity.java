package com.project.mgjandroid.bean.groupbuying;

import com.project.mgjandroid.bean.Agent;
import com.project.mgjandroid.bean.BaseBean;

/**
 * Created by yuandi on 2017/3/14.
 */

public class GroupPurchasePrimaryPublicity extends BaseBean {

    /**
     * 代理商编号
     **/
    private Long agentId;
    /**
     * 推广位置名
     **/
    private String name;
    /**
     * 标题
     **/
    private String title;
    /**
     * 图片地址
     **/
    private String img;
    /**
     * 状态0：未启动，1：已启动
     **/
    private int status;
    /**
     * 排序序号
     **/
    private Integer sortNo;
    /**
     * 删除状态0：未删除，1：已删除
     **/
    private int hasDel;
    /**
     * 推广类型 1, "静态横屏广告" 2, "推广位置"
     **/
    private Integer publicityType;
    /**
     * 跳转类型 1：链接地址，2：优惠券，3：团购商家
     **/
    private Integer gotoType;
    /**
     * 链接地址
     **/
    private String linkUrl;
    /**
     * 团购商家id
     */
    private Long groupPurchaseMerchantId;
    /**
     * 团购优惠券编号
     **/
    private Long groupPurchaseCouponId;

    private Agent agent;

    private GroupPurchaseMerchant groupPurchaseMerchant;

    private GroupPurchaseCoupon groupPurchaseCoupon;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public Integer getPublicityType() {
        return publicityType;
    }

    public void setPublicityType(Integer publicityType) {
        this.publicityType = publicityType;
    }

    public Integer getGotoType() {
        return gotoType;
    }

    public void setGotoType(Integer gotoType) {
        this.gotoType = gotoType;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
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

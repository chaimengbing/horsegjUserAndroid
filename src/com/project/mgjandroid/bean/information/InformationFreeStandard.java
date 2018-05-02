package com.project.mgjandroid.bean.information;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yuandi on 2016/11/17.
 */

public class InformationFreeStandard extends Entity {

    private Long id;
    /**
     * 代理商编号
     **/
    private Long agentId;
    /**
     * 标题
     **/
    private String title;
    /**
     * 信息类型 {@link InformationType}
     **/
    private Integer informationType;
    /**
     * 规则类型 1,发布信息；2,置顶信息；3,查看信息；4,刷新信息
     **/
    private Integer type;
    /**
     * 信息分类id
     **/
    private Long categoryId;
    /**
     * 价格
     **/
    private BigDecimal price = BigDecimal.ZERO;
    /**
     * 原价
     **/
    private BigDecimal originPrice = BigDecimal.ZERO;
    /**
     * 天数
     **/
    private Integer days;
    /**
     * 是否删除 0：未删除;1：已删除
     **/
    private Integer hasDel;

    private Date createTime;

    private Date modifyTime;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getInformationType() {
        return informationType;
    }

    public void setInformationType(Integer informationType) {
        this.informationType = informationType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getHasDel() {
        return hasDel;
    }

    public void setHasDel(Integer hasDel) {
        this.hasDel = hasDel;
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
}

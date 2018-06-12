package com.project.mgjandroid.bean;

import com.project.mgjandroid.bean.yellowpage.GoodsAttribute;
import com.project.mgjandroid.model.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by yuandi on 2016/3/9.
 */
public class Goods extends Entity {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 商家编号
     */
    private Long merchantId;
    /**
     * 分类编号
     */
    private Long categoryId;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 商品单位
     */
    private String goodsUnit;

    private String imgs;
    /**
     * 商品属性
     */
    private String attributes;
    /**
     * 排序号
     */
    private Integer sortNo;
    private String month;
    private int monthSaled;
    /**
     * 总售
     */
    private int totalSaled;
    /**
     * 赞数量
     */
    private int praiseNum;
    /**
     * 点评数
     */
    private int commentNum;
    /**
     * 好评数
     **/
    private int goodCommentNum;
    /**
     * 中评数
     **/
    private int mediumCommentNum;
    /**
     * 差评数
     **/
    private int badCommentNum;
    /**
     * 点评分
     */
    private BigDecimal commentScore;
    /**
     * 售卖状态 (0, "售罄"), (1, "在售")
     */
    private int status;
    /**
     * 是否删除
     */
    private int hasDel;
    /**
     * 删除时间
     */
    private Date delTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 搜索返回的关键字
     */
    private String highLights;
    /**
     * 商家类型 0：外卖 1：商超
     */
    private int type;
    private List<String> specName;

    private String shareUrl;

    private List<GoodsSpec> goodsSpecList;
    private List<GoodsAttribute> goodsAttributeList;

    private boolean isEditing;
    private boolean canEdit;
    private boolean isChecked;
    private boolean saleStatus;
    private int count;
    private int hasDiscount;
    private int everyGoodsEveryOrderBuyCount;
    private int surplusDiscountStock;
    private boolean isFirst = true;

    public List<String> getSpecName() {
        return specName;
    }

    public void setSpecName(List<String> specName) {
        this.specName = specName;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public int getTotalSaled() {
        return totalSaled;
    }

    public void setTotalSaled(int totalSaled) {
        this.totalSaled = totalSaled;
    }

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getGoodCommentNum() {
        return goodCommentNum;
    }

    public void setGoodCommentNum(int goodCommentNum) {
        this.goodCommentNum = goodCommentNum;
    }

    public int getMediumCommentNum() {
        return mediumCommentNum;
    }

    public void setMediumCommentNum(int mediumCommentNum) {
        this.mediumCommentNum = mediumCommentNum;
    }

    public int getBadCommentNum() {
        return badCommentNum;
    }

    public void setBadCommentNum(int badCommentNum) {
        this.badCommentNum = badCommentNum;
    }

    public BigDecimal getCommentScore() {
        return commentScore;
    }

    public void setCommentScore(BigDecimal commentScore) {
        this.commentScore = commentScore;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
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

    public Date getDelTime() {
        return delTime;
    }

    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }

    public List<GoodsSpec> getGoodsSpecList() {
        return goodsSpecList;
    }

    public void setGoodsSpecList(List<GoodsSpec> goodsSpecList) {
        this.goodsSpecList = goodsSpecList;
    }

    public List<GoodsAttribute> getGoodsAttributeList() {
        return goodsAttributeList;
    }

    public void setGoodsAttributeList(List<GoodsAttribute> goodsAttributeList) {
        this.goodsAttributeList = goodsAttributeList;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Goods) {
            Goods p = (Goods) o;
            if (p.getId() == getId()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * 月售
     */
    public int getMonthSaled() {
        return monthSaled;
    }

    public void setMonthSaled(int monthSaled) {
        this.monthSaled = monthSaled;
    }

    public boolean isEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getHasDiscount() {
        return hasDiscount;
    }

    public void setHasDiscount(int hasDiscount) {
        this.hasDiscount = hasDiscount;
    }

    public int getEveryGoodsEveryOrderBuyCount() {
        return everyGoodsEveryOrderBuyCount;
    }

    public void setEveryGoodsEveryOrderBuyCount(int everyGoodsEveryOrderBuyCount) {
        this.everyGoodsEveryOrderBuyCount = everyGoodsEveryOrderBuyCount;
    }

    public int getSurplusDiscountStock() {
        return surplusDiscountStock;
    }

    public void setSurplusDiscountStock(int surplusDiscountStock) {
        this.surplusDiscountStock = surplusDiscountStock;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public String getHighLights() {
        return highLights;
    }

    public void setHighLights(String highLights) {
        this.highLights = highLights;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(boolean saleStatus) {
        this.saleStatus = saleStatus;
    }
}


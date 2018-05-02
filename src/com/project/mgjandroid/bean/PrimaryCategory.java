package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yuandi on 2016/6/22.
 */
public class PrimaryCategory extends Entity {

    private Long id;
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
     * 标签[商家]分类ID
     */
    private Long tagCategoryId;
    /**
     * 跳转分类所属类型
     */
    private Integer tagCategoryType;
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

    private TagCategory tagCategory;

    private String tagCategoryName;

    private String categoryName;

    private String tagCategoryTypeName;
    /**
     * 跳转类型1：网址链接/业务模块，2：外卖分类 ,4:团购分类
     **/
    private int gotoType;
    /**
     * 跳转地址
     */
    private String gotoUrl = "";
    /**
     * 业务模块
     **/
    private Integer businessType;
    /**
     * 商超商家
     **/
    private Long merchantId;
    /**
     * 【商超】一级分类id{GoodsCategory}
     */
    private Long firstTGoodsCategoryId;
    /**
     * 【商超】二级分类id -2：未选择{GoodsCategory}
     */
    private Long secondTGoodsCategoryId;
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
    /**
     * 服务分类编号
     */
    private Long serviceCategoryId;

    private String serviceCategoryName;

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

    public TagCategory getTagCategory() {
        return tagCategory;
    }

    public void setTagCategory(TagCategory tagCategory) {
        this.tagCategory = tagCategory;
    }

    public String getTagCategoryName() {
        return tagCategoryName;
    }

    public void setTagCategoryName(String tagCategoryName) {
        this.tagCategoryName = tagCategoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTagCategoryTypeName() {
        return tagCategoryTypeName;
    }

    public void setTagCategoryTypeName(String tagCategoryTypeName) {
        this.tagCategoryTypeName = tagCategoryTypeName;
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

    public Long getServiceCategoryId() {
        return serviceCategoryId;
    }

    public void setServiceCategoryId(Long serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }

    public String getServiceCategoryName() {
        return serviceCategoryName;
    }

    public void setServiceCategoryName(String serviceCategoryName) {
        this.serviceCategoryName = serviceCategoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getTagCategoryId() {
        return tagCategoryId;
    }

    public void setTagCategoryId(Long tagCategoryId) {
        this.tagCategoryId = tagCategoryId;
    }

    public Integer getTagCategoryType() {
        return tagCategoryType;
    }

    public void setTagCategoryType(Integer tagCategoryType) {
        this.tagCategoryType = tagCategoryType;
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

    public class TagCategory implements Serializable {

        private Long id;
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

        private int hasDel;
        /**
         * 代理商编号
         */
        private long agentId;
        /**
         * 分类所属类型 TagCategoryType
         */
        private Integer tagCategoryType;

        List<TagCategory> childTagCategoryList;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

        public long getAgentId() {
            return agentId;
        }

        public void setAgentId(long agentId) {
            this.agentId = agentId;
        }

        public Integer getTagCategoryType() {
            return tagCategoryType;
        }

        public void setTagCategoryType(Integer tagCategoryType) {
            this.tagCategoryType = tagCategoryType;
        }

        public List<TagCategory> getChildTagCategoryList() {
            return childTagCategoryList;
        }

        public void setChildTagCategoryList(List<TagCategory> childTagCategoryList) {
            this.childTagCategoryList = childTagCategoryList;
        }
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getFirstTGoodsCategoryId() {
        return firstTGoodsCategoryId;
    }

    public void setFirstTGoodsCategoryId(Long firstTGoodsCategoryId) {
        this.firstTGoodsCategoryId = firstTGoodsCategoryId;
    }

    public Long getSecondTGoodsCategoryId() {
        return secondTGoodsCategoryId;
    }

    public void setSecondTGoodsCategoryId(Long secondTGoodsCategoryId) {
        this.secondTGoodsCategoryId = secondTGoodsCategoryId;
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

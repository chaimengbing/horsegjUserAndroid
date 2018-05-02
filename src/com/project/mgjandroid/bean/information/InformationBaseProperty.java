package com.project.mgjandroid.bean.information;

import com.project.mgjandroid.model.Entity;

import java.util.Date;

/**
 * Created by yuandi on 2016/11/16.
 */

public class InformationBaseProperty extends Entity {

    private Long id;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 代理商编号
     */
    private Long agentId;
    /**
     * 省
     */
    private Long province;
    /**
     * 市
     */
    private Long city;
    /**
     * 区
     */
    private Long district;
    /**
     * 标题
     **/
    private String title;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 状态 0未审核;1已审核 ;2审核失败
     */
    private int status;
    /**
     * 是否过期0：未过期，1：已过期
     **/
    private int isExpire;
    /**
     * 刷新日期
     */
    private String refreshDate;
    /**
     * 当日刷新次数
     */
    private int refreshCount;
    /**
     * 0正常;1删除
     */
    private int hasDel;
    /**
     * 0正常;1置顶
     */
    private int isTop;
    /**
     * 发布角色 0：用户;1：代理商
     **/
    private int roleType;

    private int type;

    private String baiduCityCode = "";
    /**
     * 详细描述
     */
    private String description;
    /**
     * 图片
     **/
    private String imgs;
    /**
     * 服务分类编号
     */
    private Long serviceCategoryId;
    /**
     * 信息所属分类编号
     */
    private Long categoryId;
    /**
     * 置顶过期时间
     **/
    private Date topExpireTime;
    /**
     * 信息过期时间
     **/
    private Date expireTime;
    /**
     * 审核过期时间
     **/
    private Date examineExpireTime;
    /**
     * 举报次数
     **/
    private int reportCount;
    /**
     * 订单号
     **/
    private String informationOrderId;
    /**
     * 分享地址
     **/
    private String shareUrl;
    /**
     * 代理商名称
     **/
    private String agentName;

    private String categoryName;

    private InformationOrder informationOrder;
    /**
     * 创建时间
     **/
    private Date createTime;
    /**
     * 修改时间
     **/
    private Date modifyTime;
    /**
     * 是否已重新发布 0：未重新发布，1：已重新发布
     **/
    private int hasAgainSend = 0;
    /**
     * 系统时间
     **/
    private Date serviceTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Long getProvince() {
        return province;
    }

    public void setProvince(Long province) {
        this.province = province;
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    public Long getDistrict() {
        return district;
    }

    public void setDistrict(Long district) {
        this.district = district;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsExpire() {
        return isExpire;
    }

    public void setIsExpire(int isExpire) {
        this.isExpire = isExpire;
    }

    public String getRefreshDate() {
        return refreshDate;
    }

    public void setRefreshDate(String refreshDate) {
        this.refreshDate = refreshDate;
    }

    public int getRefreshCount() {
        return refreshCount;
    }

    public void setRefreshCount(int refreshCount) {
        this.refreshCount = refreshCount;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBaiduCityCode() {
        return baiduCityCode;
    }

    public void setBaiduCityCode(String baiduCityCode) {
        this.baiduCityCode = baiduCityCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public Long getServiceCategoryId() {
        return serviceCategoryId;
    }

    public void setServiceCategoryId(Long serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Date getTopExpireTime() {
        return topExpireTime;
    }

    public void setTopExpireTime(Date topExpireTime) {
        this.topExpireTime = topExpireTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public InformationOrder getInformationOrder() {
        return informationOrder;
    }

    public void setInformationOrder(InformationOrder informationOrder) {
        this.informationOrder = informationOrder;
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

    public Date getExamineExpireTime() {
        return examineExpireTime;
    }

    public void setExamineExpireTime(Date examineExpireTime) {
        this.examineExpireTime = examineExpireTime;
    }

    public int getReportCount() {
        return reportCount;
    }

    public void setReportCount(int reportCount) {
        this.reportCount = reportCount;
    }

    public String getInformationOrderId() {
        return informationOrderId;
    }

    public void setInformationOrderId(String informationOrderId) {
        this.informationOrderId = informationOrderId;
    }

    public int getHasAgainSend() {
        return hasAgainSend;
    }

    public void setHasAgainSend(int hasAgainSend) {
        this.hasAgainSend = hasAgainSend;
    }

    public Date getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Date serviceTime) {
        this.serviceTime = serviceTime;
    }
}

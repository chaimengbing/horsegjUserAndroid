package com.project.mgjandroid.bean.groupbuying;

import com.project.mgjandroid.bean.Agent;
import com.project.mgjandroid.bean.BaseBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by yuandi on 2017/3/10.
 */

public class GroupPurchaseMerchant extends BaseBean {

    /**
     * 代理商Id
     */
    private Long agentId;
    /**
     * 商家名称
     */
    private String name;
    /**
     * 商家信息
     */
    private String description;
    /**
     * 团购分类编号
     **/
    private Long groupPurchaseCategoryId;
    /**
     * 团购二级分类编号
     **/
    private Long childGroupPurchaseCategoryId;
    /**
     * 商家联系方式
     */
    private String contacts;
    /**
     * 商家状态[ '0', '等待审核' ], [ '1', '审核通过' ]
     */
    private int merchantStatus;
    /**
     * 支持的付款方式
     */
    private String payments;
    /**
     * 结算类型。1：在线结算，2：线下结算
     */
    private int settleMode = 1;
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
     * 商家地址
     */
    private String address;
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 维度
     */
    private Double latitude;
    /**
     * GeoHash
     */
    private String geohash;
    /**
     * LOGO
     */
    private String logo;
    /**
     * 商家图片
     */
    private String imgs;
    /**
     * 营业时间
     */
    private String workingTime;
    /**
     * 公告信息
     */
    private String broadcast;
    /**
     * 平均分
     */
    private BigDecimal averageScore = new BigDecimal(5.0);
    /**
     * 商家评论数量
     */
    private Integer merchantCommentNum = 0;
    /**
     * 商家得分
     */
    private BigDecimal merchantScore = new BigDecimal(5.0);
    /**
     * 配送评价数量
     */
    private Integer shipCommentNum = 0;
    /**
     * 配送服务分
     */
    private BigDecimal shipScore = new BigDecimal(5.0);
    /**
     * 月售的月份
     */
    private String month;
    /**
     * 月售
     */
    private Integer monthSaled = 0;
    /**
     * 总售
     */
    private Integer totalSaled = 0;
    /**
     * 商家状态
     */
    private int status = 0;
    /**
     * 代理商佣金比率
     */
    private BigDecimal agentRate = BigDecimal.ZERO;
    /**
     * 人均消费
     **/
    private BigDecimal avgPersonPrice;
    /**
     * 商家标签
     **/
    private String merchantTag;
    /**
     * 商家推荐
     **/
    private String merchantRecommend;
    /**
     * 填写人均消费的评价数量
     **/
    private int evaluateCount = 0;
    /**
     * 评价人均消费平均值
     **/
    private BigDecimal evaluateAvgPersonPrice;
    /**
     * 无线网络 0：无，1：有
     **/
    private int hasWifi = 0;
    /**
     * 刷卡支付 0：无，1：有
     **/
    private int hasPOSPayment = 0;
    /**
     * 优雅包厢 0：无，1：有
     **/
    private int hasRooms = 0;
    /**
     * 停车场 0：无，1：有
     **/
    private int hasDepot = 0;
    /**
     * 景观位 0：无，1：有
     **/
    private int hasScenerySeat = 0;
    /**
     * 露天位 0：无，1：有
     **/
    private int hasAlfrescoSeat = 0;
    /**
     * 无烟区 0：无，1：有
     **/
    private int hasNoSmokIngArea = 0;
    /**
     * 商家服务
     **/
    private String merchantServices;

    private Double distance;

    private Agent agent;
    /**
     * 团购优惠券数量
     **/
    private int couponCount = 0;

    private Integer hasTakeaway = 0;
    /**
     * 用户收藏 0，未收藏  1，已收藏
     **/
    private int isUserFavorites;

    private String shareUrl;
    /**
     * 营业执照
     **/
    private String businessLicenseImg;
    /**
     * 卫生许可证
     **/
    private String hygieneLicenseImg;
    /**
     * 是否共享 1共享2不共享
     */
    private int isSharingRelationship;
    /**
     * 折扣
     */
    private String discountRatio;

    private String highLightName;

    private String highLightMerchantTag;

    private List<GroupPurchaseCoupon> groupPurchaseCouponList;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public int getMerchantStatus() {
        return merchantStatus;
    }

    public void setMerchantStatus(int merchantStatus) {
        this.merchantStatus = merchantStatus;
    }

    public String getPayments() {
        return payments;
    }

    public void setPayments(String payments) {
        this.payments = payments;
    }

    public int getSettleMode() {
        return settleMode;
    }

    public void setSettleMode(int settleMode) {
        this.settleMode = settleMode;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getGeohash() {
        return geohash;
    }

    public void setGeohash(String geohash) {
        this.geohash = geohash;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(String workingTime) {
        this.workingTime = workingTime;
    }

    public String getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(String broadcast) {
        this.broadcast = broadcast;
    }

    public BigDecimal getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(BigDecimal averageScore) {
        this.averageScore = averageScore;
    }

    public Integer getMerchantCommentNum() {
        return merchantCommentNum;
    }

    public void setMerchantCommentNum(Integer merchantCommentNum) {
        this.merchantCommentNum = merchantCommentNum;
    }

    public BigDecimal getMerchantScore() {
        return merchantScore;
    }

    public void setMerchantScore(BigDecimal merchantScore) {
        this.merchantScore = merchantScore;
    }

    public Integer getShipCommentNum() {
        return shipCommentNum;
    }

    public void setShipCommentNum(Integer shipCommentNum) {
        this.shipCommentNum = shipCommentNum;
    }

    public BigDecimal getShipScore() {
        return shipScore;
    }

    public void setShipScore(BigDecimal shipScore) {
        this.shipScore = shipScore;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getMonthSaled() {
        return monthSaled;
    }

    public void setMonthSaled(Integer monthSaled) {
        this.monthSaled = monthSaled;
    }

    public Integer getTotalSaled() {
        return totalSaled;
    }

    public void setTotalSaled(Integer totalSaled) {
        this.totalSaled = totalSaled;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BigDecimal getAgentRate() {
        return agentRate;
    }

    public void setAgentRate(BigDecimal agentRate) {
        this.agentRate = agentRate;
    }

    public BigDecimal getAvgPersonPrice() {
        return avgPersonPrice;
    }

    public void setAvgPersonPrice(BigDecimal avgPersonPrice) {
        this.avgPersonPrice = avgPersonPrice;
    }

    public String getMerchantTag() {
        return merchantTag;
    }

    public void setMerchantTag(String merchantTag) {
        this.merchantTag = merchantTag;
    }

    public String getMerchantRecommend() {
        return merchantRecommend;
    }

    public void setMerchantRecommend(String merchantRecommend) {
        this.merchantRecommend = merchantRecommend;
    }

    public int getEvaluateCount() {
        return evaluateCount;
    }

    public void setEvaluateCount(int evaluateCount) {
        this.evaluateCount = evaluateCount;
    }

    public BigDecimal getEvaluateAvgPersonPrice() {
        return evaluateAvgPersonPrice;
    }

    public void setEvaluateAvgPersonPrice(BigDecimal evaluateAvgPersonPrice) {
        this.evaluateAvgPersonPrice = evaluateAvgPersonPrice;
    }

    public Integer getHasTakeaway() {
        return hasTakeaway;
    }

    public void setHasTakeaway(Integer hasTakeaway) {
        this.hasTakeaway = hasTakeaway;
    }

    public int getHasWifi() {
        return hasWifi;
    }

    public void setHasWifi(int hasWifi) {
        this.hasWifi = hasWifi;
    }

    public int getHasPOSPayment() {
        return hasPOSPayment;
    }

    public void setHasPOSPayment(int hasPOSPayment) {
        this.hasPOSPayment = hasPOSPayment;
    }

    public int getHasRooms() {
        return hasRooms;
    }

    public void setHasRooms(int hasRooms) {
        this.hasRooms = hasRooms;
    }

    public int getHasDepot() {
        return hasDepot;
    }

    public void setHasDepot(int hasDepot) {
        this.hasDepot = hasDepot;
    }

    public int getHasScenerySeat() {
        return hasScenerySeat;
    }

    public void setHasScenerySeat(int hasScenerySeat) {
        this.hasScenerySeat = hasScenerySeat;
    }

    public int getHasAlfrescoSeat() {
        return hasAlfrescoSeat;
    }

    public void setHasAlfrescoSeat(int hasAlfrescoSeat) {
        this.hasAlfrescoSeat = hasAlfrescoSeat;
    }

    public int getHasNoSmokIngArea() {
        return hasNoSmokIngArea;
    }

    public void setHasNoSmokIngArea(int hasNoSmokIngArea) {
        this.hasNoSmokIngArea = hasNoSmokIngArea;
    }

    public String getMerchantServices() {
        return merchantServices;
    }

    public void setMerchantServices(String merchantServices) {
        this.merchantServices = merchantServices;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public int getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(int couponCount) {
        this.couponCount = couponCount;
    }

    public List<GroupPurchaseCoupon> getGroupPurchaseCouponList() {
        return groupPurchaseCouponList;
    }

    public void setGroupPurchaseCouponList(List<GroupPurchaseCoupon> groupPurchaseCouponList) {
        this.groupPurchaseCouponList = groupPurchaseCouponList;
    }

    public int getIsUserFavorites() {
        return isUserFavorites;
    }

    public void setIsUserFavorites(int isUserFavorites) {
        this.isUserFavorites = isUserFavorites;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getBusinessLicenseImg() {
        return businessLicenseImg;
    }

    public void setBusinessLicenseImg(String businessLicenseImg) {
        this.businessLicenseImg = businessLicenseImg;
    }

    public String getHygieneLicenseImg() {
        return hygieneLicenseImg;
    }

    public void setHygieneLicenseImg(String hygieneLicenseImg) {
        this.hygieneLicenseImg = hygieneLicenseImg;
    }

    public int getIsSharingRelationship() {
        return isSharingRelationship;
    }

    public void setIsSharingRelationship(int isSharingRelationship) {
        this.isSharingRelationship = isSharingRelationship;
    }

    public String getDiscountRatio() {
        return discountRatio;
    }

    public void setDiscountRatio(String discountRatio) {
        this.discountRatio = discountRatio;
    }

    public String getHighLightMerchantTag() {
        return highLightMerchantTag;
    }

    public void setHighLightMerchantTag(String highLightMerchantTag) {
        this.highLightMerchantTag = highLightMerchantTag;
    }

    public String getHighLightName() {
        return highLightName;
    }

    public void setHighLightName(String highLightName) {
        this.highLightName = highLightName;
    }
}

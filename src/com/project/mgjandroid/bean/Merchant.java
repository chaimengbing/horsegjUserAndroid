package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yuandi on 2016/3/9.
 */
public class Merchant extends Entity {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 代理商Id
     */
    private Long agentId;
    /**
     * 商家登录名
     */
    private String loginName;
    /**
     * 商家登录密码
     */
    private String loginPwd;
    /**
     * 商家名称
     */
    private String name;
    /**
     * 商家信息
     */
    private String description;
    /**
     * 商家联系方式
     */
    private String contacts;
    /**
     * 商家联系方式
     */
    private String contactsArray;
    /**
     * 开户行
     */
    private String bankName;
    /**
     * 银行卡号
     */
    private String bankCard;
    /**
     * 开户人
     */
    private String bankPerson;
    /**
     * 商家状态
     */
    private int merchantStatus;
    /**
     * 支持的付款方式
     */
    private String payments;
    private int shipmentMode;
    /**
     * 最远配送距离 单位m
     */
    private Integer limitDistance;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String district;
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
     * 起送价
     */
    private BigDecimal minPrice = BigDecimal.ZERO;
    /**
     * 配送费
     */
    private BigDecimal shipFee = BigDecimal.ZERO;
    /** 配送费优惠金额 */
    private BigDecimal shippingPreferentialFee = BigDecimal.ZERO;

    /**商家承担配送费金额*/
    private BigDecimal merchantAssumeAmt = BigDecimal.ZERO;

    /**
     * 新配送费
     */
    private BigDecimal practicalShipFee = BigDecimal.ZERO;
    /**
     * 平均配送时长
     */
    private Integer avgDeliveryTime;
    /**
     * 支持开票
     */
    private Integer invoiceSupport;
    /**
     * 最小开票金额
     */
    private BigDecimal minInvoicePrice;
    /**
     * 显示好评榜单
     */
    private int showPraise = 1;
    /**
     * 显示热卖榜单
     */
    private int showHotsale = 1;
    /**
     * 平均分
     */
    private BigDecimal averageScore;
    /** 商家包装分数 */
    private BigDecimal packagingScore = new BigDecimal(5.0);
    /** 口味分数 */
    private BigDecimal tasteScore = new BigDecimal(5.0);

    private int merchantCommentNum;
    private BigDecimal merchantScore;
    private int shipCommentNum;
    private BigDecimal shipScore;
    private String month;
    private int monthSaled;
    private int totalSaled;
    /**
     * 商家状态
     */
    private Integer status = 0;
    /**
     * 是否品牌商家0：不是，1是
     */
    private Integer isBrandMerchant = 0;
    /**
     * 商家类型 0：外卖 1：商超
     */
    private int type;

    private Double distance;
    /**
     * 已选商品数量
     */
    private int pickGoodsCount = 0;
    /**
     * 商家是否有终端设备
     */
    private Integer hasTerminal = 0;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 商家活动
     */
    private List<PromotionActivity> promotionActivityList;
    /**
     * 满减活动id
     **/
    private Long fullSubPromotion;
    /**
     * 首单立减活动id
     **/
    private Long firstOrderSubPromotion;
    /**
     * 商家红包
     **/
    private List<MerchantRedBag> merchantRedBagList;
    /**
     * 可视餐厅列表数据
     */
    private List<VisibleLive> visualRestaurantList;
    /**
     * 活动共享关系
     **/
    private List<SharingRelationship> activitySharedRelationList;
    private List<Goods> goodsList;
    private String highLights;
    private boolean isShowGoods;
    private boolean isShoppingTime = true;
    /**
     * 营业执照
     **/
    private String businessLicenseImg;
    /**
     * 卫生许可证
     **/
    private String hygieneLicenseImg;
    /**
     * 是否可视餐厅 1 是  0 否
     */
    private int hasVisualRestaurant;

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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
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

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankPerson() {
        return bankPerson;
    }

    public void setBankPerson(String bankPerson) {
        this.bankPerson = bankPerson;
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

    public Integer getLimitDistance() {
        return limitDistance;
    }

    public void setLimitDistance(Integer limitDistance) {
        this.limitDistance = limitDistance;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
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

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getShipFee() {
//        if (practicalShipFee != null) {
//            return practicalShipFee;
//        }
        return shipFee;
    }

    public void setShipFee(BigDecimal shipFee) {
        this.shipFee = shipFee;
    }

    public Integer getInvoiceSupport() {
        return invoiceSupport;
    }

    public void setInvoiceSupport(Integer invoiceSupport) {
        this.invoiceSupport = invoiceSupport;
    }

    public BigDecimal getMinInvoicePrice() {
        return minInvoicePrice;
    }

    public void setMinInvoicePrice(BigDecimal minInvoicePrice) {
        this.minInvoicePrice = minInvoicePrice;
    }

    public Integer getAvgDeliveryTime() {
        return avgDeliveryTime;
    }

    public void setAvgDeliveryTime(Integer avgDeliveryTime) {
        this.avgDeliveryTime = avgDeliveryTime;
    }

    public int getPickGoodsCount() {
        return pickGoodsCount;
    }

    public void setPickGoodsCount(int pickGoodsCount) {
        this.pickGoodsCount = pickGoodsCount;
    }

    public int getShowPraise() {
        return showPraise;
    }

    public void setShowPraise(int showPraise) {
        this.showPraise = showPraise;
    }

    public int getShowHotsale() {
        return showHotsale;
    }

    public void setShowHotsale(int showHotsale) {
        this.showHotsale = showHotsale;
    }

    public BigDecimal getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(BigDecimal averageScore) {
        this.averageScore = averageScore;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getHasTerminal() {
        return hasTerminal;
    }

    public void setHasTerminal(Integer hasTerminal) {
        this.hasTerminal = hasTerminal;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public List<PromotionActivity> getPromotionActivityList() {
        return promotionActivityList;
    }

    public void setPromotionActivityList(List<PromotionActivity> promotionActivityList) {
        this.promotionActivityList = promotionActivityList;
    }

    /**
     * 支持的配送方式
     */
    public int getShipmentMode() {
        return shipmentMode;
    }

    public void setShipmentMode(int shipmentMode) {
        this.shipmentMode = shipmentMode;
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

    public int getMonthSaled() {
        return monthSaled;
    }

    public void setMonthSaled(int monthSaled) {
        this.monthSaled = monthSaled;
    }

    public int getTotalSaled() {
        return totalSaled;
    }

    public void setTotalSaled(int totalSaled) {
        this.totalSaled = totalSaled;
    }

    public BigDecimal getMerchantScore() {
        return merchantScore;
    }

    public void setMerchantScore(BigDecimal merchantScore) {
        this.merchantScore = merchantScore;
    }

    public int getMerchantCommentNum() {
        return merchantCommentNum;
    }

    public void setMerchantCommentNum(int merchantCommentNum) {
        this.merchantCommentNum = merchantCommentNum;
    }

    public int getShipCommentNum() {
        return shipCommentNum;
    }

    public void setShipCommentNum(int shipCommentNum) {
        this.shipCommentNum = shipCommentNum;
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

    public Long getFullSubPromotion() {
        return fullSubPromotion;
    }

    public void setFullSubPromotion(Long fullSubPromotion) {
        this.fullSubPromotion = fullSubPromotion;
    }

    public Long getFirstOrderSubPromotion() {
        return firstOrderSubPromotion;
    }

    public void setFirstOrderSubPromotion(Long firstOrderSubPromotion) {
        this.firstOrderSubPromotion = firstOrderSubPromotion;
    }

    public List<MerchantRedBag> getMerchantRedBagList() {
        return merchantRedBagList;
    }

    public void setMerchantRedBagList(List<MerchantRedBag> merchantRedBagList) {
        this.merchantRedBagList = merchantRedBagList;
    }

    public List<SharingRelationship> getActivitySharedRelationList() {
        return activitySharedRelationList;
    }

    public void setActivitySharedRelationList(List<SharingRelationship> activitySharedRelationList) {
        this.activitySharedRelationList = activitySharedRelationList;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    public String getHighLights() {
        return highLights;
    }

    public void setHighLights(String highLights) {
        this.highLights = highLights;
    }

    public boolean isShowGoods() {
        return isShowGoods;
    }

    public void setIsShowGoods(boolean isShowGoods) {
        this.isShowGoods = isShowGoods;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isShoppingTime() {
        return isShoppingTime;
    }

    public void setShoppingTime(boolean shoppingTime) {
        isShoppingTime = shoppingTime;
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

    public String getContactsArray() {
        return contactsArray;
    }

    public void setContactsArray(String contactsArray) {
        this.contactsArray = contactsArray;
    }

    public Integer getIsBrandMerchant() {
        return isBrandMerchant;
    }

    public void setIsBrandMerchant(Integer isBrandMerchant) {
        this.isBrandMerchant = isBrandMerchant;
    }


    public int getHasVisualRestaurant() {
        return hasVisualRestaurant;
    }

    public void setHasVisualRestaurant(int hasVisualRestaurant) {
        this.hasVisualRestaurant = hasVisualRestaurant;
    }

    public List<VisibleLive> getVisualRestaurantList() {
        return visualRestaurantList;
    }

    public void setVisualRestaurantList(List<VisibleLive> visualRestaurantList) {
        this.visualRestaurantList = visualRestaurantList;
    }

    public BigDecimal getPackagingScore() {
        return packagingScore;
    }

    public void setPackagingScore(BigDecimal packagingScore) {
        this.packagingScore = packagingScore;
    }

    public BigDecimal getTasteScore() {
        return tasteScore;
    }

    public void setTasteScore(BigDecimal tasteScore) {
        this.tasteScore = tasteScore;
    }

    public BigDecimal getShippingPreferentialFee() {
        return shippingPreferentialFee;
    }

    public void setShippingPreferentialFee(BigDecimal shippingPreferentialFee) {
        this.shippingPreferentialFee = shippingPreferentialFee;
    }

    public BigDecimal getMerchantAssumeAmt() {
        return merchantAssumeAmt;
    }

    public void setMerchantAssumeAmt(BigDecimal merchantAssumeAmt) {
        this.merchantAssumeAmt = merchantAssumeAmt;
    }
}

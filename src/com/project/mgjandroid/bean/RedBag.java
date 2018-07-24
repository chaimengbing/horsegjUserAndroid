package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yuandi on 2016/5/24.
 */
public class RedBag extends Entity {

    /**
     * * "id": 14101,
     * "createTime": "2018-07-02 11:41:22",
     * "modifyTime": "2018-07-02 11:41:22",
     * "userId": 73,
     * "promotionId": null,
     * "platformRedBagRulesId": null,
     * "activityId": 385,
     * "rulesSource": null,
     * "fromOrderId": "1807020000212737",
     * "useOrderId": null,
     * "name": "下单返",
     * "amt": 15.0,
     * "discountRate": null,
     * "restrictAmt": 48.0,
     * "promotionType": 2,
     * "redeemCode": null,
     * "type": 2,
     * "couponType": 2,
     * "businessType": null,
     * "merchantId": 78,
     * "agentId": null,
     * "expirationTime": 1533094881527,
     * "isRestrictTime": 0,
     * "restrictTime": null,
     * "usingTime": null,
     * "sendBackTime": null,
     * "isCumulate": 0,
     * "mobile": null,
     * "status": 0,
     * "merchantName": "兰州拉面 1",
     * "merchantLogo": "http://7xu5hi.com1.z0.glb.clouddn.com/201610211712494115412.jpg",
     * "isDisable": 1,
     * "businessTypeName": null,
     * "disableList": []
     *
     *
     * {
     "code": 0,
     "uuid": "352203064445533",
     "value": {
     "platformRedBagCount": 7,
     "vouchersList": [{
     "id": 13885,
     "createTime": "2018-06-19 15:55:20",
     "modifyTime": "2018-06-19 15:55:20",
     "userId": 1382,
     "promotionId": null,
     "platformRedBagRulesId": null,
     "activityId": 384,
     "rulesSource": null,
     "fromOrderId": null,
     "useOrderId": null,
     "name": "进店领",
     "amt": 10.0,
     "discountRate": null,
     "restrictAmt": 48.0,
     "promotionType": 2,
     "redeemCode": null,
     "type": 2,
     "couponType": 2,
     "businessType": null,
     "merchantId": 221,
     "agentId": null,
     "expirationTime": 1531986919952,
     "isRestrictTime": 0,
     "restrictTime": null,
     "usingTime": null,
     "sendBackTime": null,
     "isCumulate": 0,
     "mobile": null,
     "status": 0,
     "merchantName": "德玛西亚",
     "merchantLogo": "http://7xu5hi.com1.z0.glb.clouddn.com/201805301027148994984.JPG",
     "isDisable": 1,
     "businessTypeName": null,
     "disableList": []
     }],
     "platformRedBagList": [{
     "id": 14127,
     "createTime": "2018-07-05 16:15:55",
     "modifyTime": "2018-07-05 16:15:55",
     "userId": 1382,
     "promotionId": null,
     "platformRedBagRulesId": 6,
     "activityId": null,
     "rulesSource": null,
     "fromOrderId": null,
     "useOrderId": null,


     "discountRate": 0.0,

     "promotionType": 3,
     "redeemCode": null,
     "type": 4,
     "couponType": 1,
     "businessType": "2,6",
     "merchantId": null,
     "agentId": 3,
     "expirationTime": 1534234555332,
     "isRestrictTime": 1,
     "name": "dfg ",
     "restrictTime": "01:00-02:00",
     "businessTypeName": "拼团 团购",
     "mobile": "15312345678",
     "amt": 3.7,
     "restrictAmt": 40.0,  满减
     "isDisable": 1,
     "usingTime": null,
     "sendBackTime": null,
     "isCumulate": 0,

     "status": 0,
     "merchantName": null,
     "merchantLogo": null,


     "disableList": []
     },],
     "vouchersCount": 1
     },
     "success": true,
     "servertime": "2018-07-05 16:58:35"
     }
     *
     *
     */

    private Integer isDisable = 1;
    /**
     * 支持业务名称
     */
    private String businessTypeName;
    /**
     * 不可用原因
     */
    private List<String> disableList = new ArrayList<>();

    private Long id;
    /**
     * 用户编号
     **/
    private Long userId;
    /**
     * 活动类型id
     **/
    private Long promotionId;
    /**
     * 活动编号
     **/
    private Long activityId;
    /**
     * 来源订单编号
     **/
    private String fromOrderId;
    /**
     * 使用订单编号
     **/
    private String useOrderId;
    /**
     * 名称
     **/
    private String name;
    /**
     * 红包金额
     **/
    private BigDecimal amt;
    /**
     * 满减限制金额
     **/
    private BigDecimal restrictAmt;
    /**
     * 优惠折扣
     **/
    private BigDecimal discountRate;
    /**
     * 促销类型 {PromotionType}
     **/
    private int promotionType;
    /**
     * 兑换码
     **/
    private String redeemCode;
    /**
     * 类型 {RedBagType}
     **/
    private int type;
    /**
     * 优惠类型 {RedBagCouponType}
     **/
    private int couponType;
    /**
     * 商家编号
     **/
    private Long merchantId;
    /**
     * 代理商编号
     **/
    private Long agentId;
    /**
     * 过期时间
     **/
    private String expirationTime;
    /**
     * 是否限制使用时间0:否,1:是
     */
    private int isRestrictTime;
    /**
     * 限制使用时间
     */
    private String restrictTime;
    /**
     * 使用时间
     */
    private Date usingTime;
    /**
     * 退还时间
     */
    private Date sendBackTime;
    /**
     * 是否叠加 0:否,1:是
     **/
    private int isCumulate;
    /**
     * 是否排它0:否;1:排它（不能与其他活动一起使用）
     **/
    private int isExclusive;
    /**
     * 手机号
     **/
    private String mobile;
    /**
     * 状态 0:未使用,1:已使用
     **/
    private int status;

    /**
     * 是否展开不可用原因
     */
    private boolean isExpand = false;

    private String redBagImg;

    private String title;

    private String merchantName;

    private String merchantLogo;

    private Date createTime;

    private Date modifyTime;

    private boolean selected;

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

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getFromOrderId() {
        return fromOrderId;
    }

    public void setFromOrderId(String fromOrderId) {
        this.fromOrderId = fromOrderId;
    }

    public String getUseOrderId() {
        return useOrderId;
    }

    public void setUseOrderId(String useOrderId) {
        this.useOrderId = useOrderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public BigDecimal getRestrictAmt() {
        return restrictAmt;
    }

    public void setRestrictAmt(BigDecimal restrictAmt) {
        this.restrictAmt = restrictAmt;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public int getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(int promotionType) {
        this.promotionType = promotionType;
    }

    public String getRedeemCode() {
        return redeemCode;
    }

    public void setRedeemCode(String redeemCode) {
        this.redeemCode = redeemCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public int getIsRestrictTime() {
        return isRestrictTime;
    }

    public void setIsRestrictTime(int isRestrictTime) {
        this.isRestrictTime = isRestrictTime;
    }

    public String getRestrictTime() {
        return restrictTime;
    }

    public void setRestrictTime(String restrictTime) {
        this.restrictTime = restrictTime;
    }

    public Date getUsingTime() {
        return usingTime;
    }

    public void setUsingTime(Date usingTime) {
        this.usingTime = usingTime;
    }

    public Date getSendBackTime() {
        return sendBackTime;
    }

    public void setSendBackTime(Date sendBackTime) {
        this.sendBackTime = sendBackTime;
    }

    public int getIsCumulate() {
        return isCumulate;
    }

    public void setIsCumulate(int isCumulate) {
        this.isCumulate = isCumulate;
    }

    public int getIsExclusive() {
        return isExclusive;
    }

    public void setIsExclusive(int isExclusive) {
        this.isExclusive = isExclusive;
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

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantLogo() {
        return merchantLogo;
    }

    public void setMerchantLogo(String merchantLogo) {
        this.merchantLogo = merchantLogo;
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

    public String getRedBagImg() {
        return redBagImg;
    }

    public void setRedBagImg(String redBagImg) {
        this.redBagImg = redBagImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Integer getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(Integer isDisable) {
        this.isDisable = isDisable;
    }

    public String getBusinessTypeName() {
        return businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
    }

    public List<String> getDisableList() {
        return disableList;
    }

    public void setDisableList(List<String> disableList) {
        this.disableList = disableList;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }
}

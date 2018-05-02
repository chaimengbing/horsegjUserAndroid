package com.project.mgjandroid.model;

/**
 * Created by SunXueLiang on 2018-03-23.
 */

public class LegworkModel extends Entity {

    private String id;
    private String createTime;
    private String modifyTime;
    private int type;
    private int childType;
    private int settleState;
    private int agentId;
    private Object deliverymanId;
    private int userId;
    private int userAddressId;
    private String userAddress;
    private String userHouseNumber;
    private String userName;
    private String userGender;
    private String userMobile;
    private double userLongitude;
    private double userLatitude;
    private int shipperType;
    private int shipperAddressId;
    private String shipperAddress;
    private String shipperHouseNumber;
    private String shipperName;
    private String shipperGender;
    private String shipperMobile;
    private double shipperLongitude;
    private double shipperLatitude;
    private Object transactionId;
    private int paymentType;
    private int paymentState;
    private String description;
    private String remark;
    private String goodsEstimatePrice;
    private double servePrice;
    private double hasPayed;
    private double totalPrice;
    private int status;
    private int hasDel;
    private Object deliveryTaskId;
    private String imgUrls;
    private int hasComments;
    private Object defCommentsTime;
    private String commissionJson;
    private double sysRateAmt;
    private double provinceAgentRateAmt;
    private double partnerAgentRateAmt;
    private double agentRateAmt;
    private Object cancelSource;
    private Object cancelReason;
    private String userImei;
    private String expectArrivalTime;
    private String paymentExpireTime;
    private Object paymentFinishTime;
    private Object acceptedFinishTime;
    private Object hasTakeFinishTime;
    private Object orderCancelTime;
    private Object orderDoneTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getChildType() {
        return childType;
    }

    public void setChildType(int childType) {
        this.childType = childType;
    }

    public int getSettleState() {
        return settleState;
    }

    public void setSettleState(int settleState) {
        this.settleState = settleState;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public Object getDeliverymanId() {
        return deliverymanId;
    }

    public void setDeliverymanId(Object deliverymanId) {
        this.deliverymanId = deliverymanId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(int userAddressId) {
        this.userAddressId = userAddressId;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserHouseNumber() {
        return userHouseNumber;
    }

    public void setUserHouseNumber(String userHouseNumber) {
        this.userHouseNumber = userHouseNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public double getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(double userLongitude) {
        this.userLongitude = userLongitude;
    }

    public double getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(double userLatitude) {
        this.userLatitude = userLatitude;
    }

    public int getShipperType() {
        return shipperType;
    }

    public void setShipperType(int shipperType) {
        this.shipperType = shipperType;
    }

    public int getShipperAddressId() {
        return shipperAddressId;
    }

    public void setShipperAddressId(int shipperAddressId) {
        this.shipperAddressId = shipperAddressId;
    }

    public String getShipperAddress() {
        return shipperAddress;
    }

    public void setShipperAddress(String shipperAddress) {
        this.shipperAddress = shipperAddress;
    }

    public String getShipperHouseNumber() {
        return shipperHouseNumber;
    }

    public void setShipperHouseNumber(String shipperHouseNumber) {
        this.shipperHouseNumber = shipperHouseNumber;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getShipperGender() {
        return shipperGender;
    }

    public void setShipperGender(String shipperGender) {
        this.shipperGender = shipperGender;
    }

    public String getShipperMobile() {
        return shipperMobile;
    }

    public void setShipperMobile(String shipperMobile) {
        this.shipperMobile = shipperMobile;
    }

    public double getShipperLongitude() {
        return shipperLongitude;
    }

    public void setShipperLongitude(double shipperLongitude) {
        this.shipperLongitude = shipperLongitude;
    }

    public double getShipperLatitude() {
        return shipperLatitude;
    }

    public void setShipperLatitude(double shipperLatitude) {
        this.shipperLatitude = shipperLatitude;
    }

    public Object getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Object transactionId) {
        this.transactionId = transactionId;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public int getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(int paymentState) {
        this.paymentState = paymentState;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGoodsEstimatePrice() {
        return goodsEstimatePrice;
    }

    public void setGoodsEstimatePrice(String goodsEstimatePrice) {
        this.goodsEstimatePrice = goodsEstimatePrice;
    }

    public double getServePrice() {
        return servePrice;
    }

    public void setServePrice(double servePrice) {
        this.servePrice = servePrice;
    }

    public double getHasPayed() {
        return hasPayed;
    }

    public void setHasPayed(double hasPayed) {
        this.hasPayed = hasPayed;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
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

    public Object getDeliveryTaskId() {
        return deliveryTaskId;
    }

    public void setDeliveryTaskId(Object deliveryTaskId) {
        this.deliveryTaskId = deliveryTaskId;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public int getHasComments() {
        return hasComments;
    }

    public void setHasComments(int hasComments) {
        this.hasComments = hasComments;
    }

    public Object getDefCommentsTime() {
        return defCommentsTime;
    }

    public void setDefCommentsTime(Object defCommentsTime) {
        this.defCommentsTime = defCommentsTime;
    }

    public String getCommissionJson() {
        return commissionJson;
    }

    public void setCommissionJson(String commissionJson) {
        this.commissionJson = commissionJson;
    }

    public double getSysRateAmt() {
        return sysRateAmt;
    }

    public void setSysRateAmt(double sysRateAmt) {
        this.sysRateAmt = sysRateAmt;
    }

    public double getProvinceAgentRateAmt() {
        return provinceAgentRateAmt;
    }

    public void setProvinceAgentRateAmt(double provinceAgentRateAmt) {
        this.provinceAgentRateAmt = provinceAgentRateAmt;
    }

    public double getPartnerAgentRateAmt() {
        return partnerAgentRateAmt;
    }

    public void setPartnerAgentRateAmt(double partnerAgentRateAmt) {
        this.partnerAgentRateAmt = partnerAgentRateAmt;
    }

    public double getAgentRateAmt() {
        return agentRateAmt;
    }

    public void setAgentRateAmt(double agentRateAmt) {
        this.agentRateAmt = agentRateAmt;
    }

    public Object getCancelSource() {
        return cancelSource;
    }

    public void setCancelSource(Object cancelSource) {
        this.cancelSource = cancelSource;
    }

    public Object getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(Object cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getUserImei() {
        return userImei;
    }

    public void setUserImei(String userImei) {
        this.userImei = userImei;
    }

    public String getExpectArrivalTime() {
        return expectArrivalTime;
    }

    public void setExpectArrivalTime(String expectArrivalTime) {
        this.expectArrivalTime = expectArrivalTime;
    }

    public String getPaymentExpireTime() {
        return paymentExpireTime;
    }

    public void setPaymentExpireTime(String paymentExpireTime) {
        this.paymentExpireTime = paymentExpireTime;
    }

    public Object getPaymentFinishTime() {
        return paymentFinishTime;
    }

    public void setPaymentFinishTime(Object paymentFinishTime) {
        this.paymentFinishTime = paymentFinishTime;
    }

    public Object getAcceptedFinishTime() {
        return acceptedFinishTime;
    }

    public void setAcceptedFinishTime(Object acceptedFinishTime) {
        this.acceptedFinishTime = acceptedFinishTime;
    }

    public Object getHasTakeFinishTime() {
        return hasTakeFinishTime;
    }

    public void setHasTakeFinishTime(Object hasTakeFinishTime) {
        this.hasTakeFinishTime = hasTakeFinishTime;
    }

    public Object getOrderCancelTime() {
        return orderCancelTime;
    }

    public void setOrderCancelTime(Object orderCancelTime) {
        this.orderCancelTime = orderCancelTime;
    }

    public Object getOrderDoneTime() {
        return orderDoneTime;
    }

    public void setOrderDoneTime(Object orderDoneTime) {
        this.orderDoneTime = orderDoneTime;
    }
}

package com.project.mgjandroid.model;

import java.util.List;

/**
 * Created by ning on 2016/3/28.
 */
public class BalancePayModel extends Entity {

    private int code;
    private String uuid;
    private ValueEntity value;
    private boolean success;
    private String servertime;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ValueEntity getValue() {
        return value;
    }

    public void setValue(ValueEntity value) {
        this.value = value;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getServertime() {
        return servertime;
    }

    public void setServertime(String servertime) {
        this.servertime = servertime;
    }

    public static class ValueEntity {
        private String createTime;
        private String modifyTime;
        private String id;
        private int type;
        private int merchantId;
        private Object merchant;
        private int merchantSettleMode;
        private int merchantSettleState;
        private int agentSettleState;
        private int agentId;
        private int userId;
        private Object deliverymanId;
        private int userAddressId;
        private String userAddress;
        private String userHouseNumber;
        private String userName;
        private String userGender;
        private String userMobile;
        private Object userBackupMobile;
        private double userLongitude;
        private double userLatitude;
        private String promoInfoJson;
        private String redBagJson;
        private double redBagTotalAmt;
        private int shipmentType;
        private Object caution;
        private double itemsPrice;
        private double boxPrice;
        private double shippingFee;
        private double originalTotalPrice;
        private double totalPrice;
        private String expectArrivalTime;
        private Object transactionId;
        private int paymentType;
        private int paymentState;
        private String paymentExpireTime;
        private String acceptedExpireTime;
        private double hasPayed;
        private int orderFlowStatus;
        private int hasDel;
        private int deliveryTaskId;
        private Object deliveryTask;
        private int hasComments;
        private double sysRate;
        private double sysRateAmt;
        private double agentRate;
        private double agentRateAmt;
        private double currentAgentRate;
        private double currentAgentRateAmt;
        private double provinceAgentRate;
        private double provinceAgentRateAmt;
        private int merchantAmt;
        private int agentAmt;
        private Object cancelSource;
        private Object cancelReason;
        private int isShow;
        private Object agent;
        private Object promoList;
        private Object redBagList;
        private Object agentPhone;
        private Object expectArrivalTimeTemp;
        private Object groupbuyOrder;
        private List<?> orderItems;

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }

        public Object getMerchant() {
            return merchant;
        }

        public void setMerchant(Object merchant) {
            this.merchant = merchant;
        }

        public int getMerchantSettleMode() {
            return merchantSettleMode;
        }

        public void setMerchantSettleMode(int merchantSettleMode) {
            this.merchantSettleMode = merchantSettleMode;
        }

        public int getMerchantSettleState() {
            return merchantSettleState;
        }

        public void setMerchantSettleState(int merchantSettleState) {
            this.merchantSettleState = merchantSettleState;
        }

        public int getAgentSettleState() {
            return agentSettleState;
        }

        public void setAgentSettleState(int agentSettleState) {
            this.agentSettleState = agentSettleState;
        }

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public Object getDeliverymanId() {
            return deliverymanId;
        }

        public void setDeliverymanId(Object deliverymanId) {
            this.deliverymanId = deliverymanId;
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

        public Object getUserBackupMobile() {
            return userBackupMobile;
        }

        public void setUserBackupMobile(Object userBackupMobile) {
            this.userBackupMobile = userBackupMobile;
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

        public String getPromoInfoJson() {
            return promoInfoJson;
        }

        public void setPromoInfoJson(String promoInfoJson) {
            this.promoInfoJson = promoInfoJson;
        }

        public String getRedBagJson() {
            return redBagJson;
        }

        public void setRedBagJson(String redBagJson) {
            this.redBagJson = redBagJson;
        }

        public double getRedBagTotalAmt() {
            return redBagTotalAmt;
        }

        public void setRedBagTotalAmt(double redBagTotalAmt) {
            this.redBagTotalAmt = redBagTotalAmt;
        }

        public int getShipmentType() {
            return shipmentType;
        }

        public void setShipmentType(int shipmentType) {
            this.shipmentType = shipmentType;
        }

        public Object getCaution() {
            return caution;
        }

        public void setCaution(Object caution) {
            this.caution = caution;
        }

        public double getItemsPrice() {
            return itemsPrice;
        }

        public void setItemsPrice(double itemsPrice) {
            this.itemsPrice = itemsPrice;
        }

        public double getBoxPrice() {
            return boxPrice;
        }

        public void setBoxPrice(double boxPrice) {
            this.boxPrice = boxPrice;
        }

        public double getShippingFee() {
            return shippingFee;
        }

        public void setShippingFee(double shippingFee) {
            this.shippingFee = shippingFee;
        }

        public double getOriginalTotalPrice() {
            return originalTotalPrice;
        }

        public void setOriginalTotalPrice(double originalTotalPrice) {
            this.originalTotalPrice = originalTotalPrice;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getExpectArrivalTime() {
            return expectArrivalTime;
        }

        public void setExpectArrivalTime(String expectArrivalTime) {
            this.expectArrivalTime = expectArrivalTime;
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

        public String getPaymentExpireTime() {
            return paymentExpireTime;
        }

        public void setPaymentExpireTime(String paymentExpireTime) {
            this.paymentExpireTime = paymentExpireTime;
        }

        public String getAcceptedExpireTime() {
            return acceptedExpireTime;
        }

        public void setAcceptedExpireTime(String acceptedExpireTime) {
            this.acceptedExpireTime = acceptedExpireTime;
        }

        public double getHasPayed() {
            return hasPayed;
        }

        public void setHasPayed(double hasPayed) {
            this.hasPayed = hasPayed;
        }

        public int getOrderFlowStatus() {
            return orderFlowStatus;
        }

        public void setOrderFlowStatus(int orderFlowStatus) {
            this.orderFlowStatus = orderFlowStatus;
        }

        public int getHasDel() {
            return hasDel;
        }

        public void setHasDel(int hasDel) {
            this.hasDel = hasDel;
        }

        public int getDeliveryTaskId() {
            return deliveryTaskId;
        }

        public void setDeliveryTaskId(int deliveryTaskId) {
            this.deliveryTaskId = deliveryTaskId;
        }

        public Object getDeliveryTask() {
            return deliveryTask;
        }

        public void setDeliveryTask(Object deliveryTask) {
            this.deliveryTask = deliveryTask;
        }

        public int getHasComments() {
            return hasComments;
        }

        public void setHasComments(int hasComments) {
            this.hasComments = hasComments;
        }

        public double getSysRate() {
            return sysRate;
        }

        public void setSysRate(double sysRate) {
            this.sysRate = sysRate;
        }

        public double getSysRateAmt() {
            return sysRateAmt;
        }

        public void setSysRateAmt(double sysRateAmt) {
            this.sysRateAmt = sysRateAmt;
        }

        public double getAgentRate() {
            return agentRate;
        }

        public void setAgentRate(double agentRate) {
            this.agentRate = agentRate;
        }

        public double getAgentRateAmt() {
            return agentRateAmt;
        }

        public void setAgentRateAmt(double agentRateAmt) {
            this.agentRateAmt = agentRateAmt;
        }

        public double getCurrentAgentRate() {
            return currentAgentRate;
        }

        public void setCurrentAgentRate(double currentAgentRate) {
            this.currentAgentRate = currentAgentRate;
        }

        public double getCurrentAgentRateAmt() {
            return currentAgentRateAmt;
        }

        public void setCurrentAgentRateAmt(double currentAgentRateAmt) {
            this.currentAgentRateAmt = currentAgentRateAmt;
        }

        public double getProvinceAgentRate() {
            return provinceAgentRate;
        }

        public void setProvinceAgentRate(double provinceAgentRate) {
            this.provinceAgentRate = provinceAgentRate;
        }

        public double getProvinceAgentRateAmt() {
            return provinceAgentRateAmt;
        }

        public void setProvinceAgentRateAmt(double provinceAgentRateAmt) {
            this.provinceAgentRateAmt = provinceAgentRateAmt;
        }

        public int getMerchantAmt() {
            return merchantAmt;
        }

        public void setMerchantAmt(int merchantAmt) {
            this.merchantAmt = merchantAmt;
        }

        public int getAgentAmt() {
            return agentAmt;
        }

        public void setAgentAmt(int agentAmt) {
            this.agentAmt = agentAmt;
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

        public int getIsShow() {
            return isShow;
        }

        public void setIsShow(int isShow) {
            this.isShow = isShow;
        }

        public Object getAgent() {
            return agent;
        }

        public void setAgent(Object agent) {
            this.agent = agent;
        }

        public Object getPromoList() {
            return promoList;
        }

        public void setPromoList(Object promoList) {
            this.promoList = promoList;
        }

        public Object getRedBagList() {
            return redBagList;
        }

        public void setRedBagList(Object redBagList) {
            this.redBagList = redBagList;
        }

        public Object getAgentPhone() {
            return agentPhone;
        }

        public void setAgentPhone(Object agentPhone) {
            this.agentPhone = agentPhone;
        }

        public Object getExpectArrivalTimeTemp() {
            return expectArrivalTimeTemp;
        }

        public void setExpectArrivalTimeTemp(Object expectArrivalTimeTemp) {
            this.expectArrivalTimeTemp = expectArrivalTimeTemp;
        }

        public Object getGroupbuyOrder() {
            return groupbuyOrder;
        }

        public void setGroupbuyOrder(Object groupbuyOrder) {
            this.groupbuyOrder = groupbuyOrder;
        }

        public List<?> getOrderItems() {
            return orderItems;
        }

        public void setOrderItems(List<?> orderItems) {
            this.orderItems = orderItems;
        }
    }
}

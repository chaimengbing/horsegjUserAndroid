package com.project.mgjandroid.model;

import java.util.List;

/**
 * Created by User_Cjh on 2016/8/23.
 */
public class JoinGroupModel extends Entity {
    private int code;
    private String uuid;
    private ValueBean value;
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

    public ValueBean getValue() {
        return value;
    }

    public void setValue(ValueBean value) {
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

    public static class ValueBean extends Entity {
        private String createTime;
        private String modifyTime;
        private String id;
        private int type;
        private Object merchantId;
        private Object merchant;
        private int merchantSettleMode;
        private int merchantSettleState;
        private int agentSettleState;
        private int agentId;
        private int userId;
        private Object deliverymanId;
        private Object userAddressId;
        private String userAddress;
        private String userHouseNumber;
        private String userName;
        private String userGender;
        private String userMobile;
        private String userBackupMobile;
        private Object userLongitude;
        private Object userLatitude;
        private Object promoInfoJson;
        private Object redBagJson;
        private Object redBagTotalAmt;
        private Object shipmentType;
        private Object caution;
        private int itemsPrice;
        private int boxPrice;
        private int shippingFee;
        private int originalTotalPrice;
        private double totalPrice;
        private Object expectArrivalTime;
        private Object transactionId;
        private Object paymentType;
        private int paymentState;
        private Object paymentExpireTime;
        private Object acceptedExpireTime;
        private int hasPayed;
        private Object orderFlowStatus;
        private int hasDel;
        private Object deliveryTaskId;
        private Object deliveryTask;
        private int hasComments;
        private int sysRate;
        private int sysRateAmt;
        private int agentRate;
        private int agentRateAmt;
        private int currentAgentRate;
        private int currentAgentRateAmt;
        private int provinceAgentRate;
        private int provinceAgentRateAmt;
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
        /**
         * id : 1608230000052405
         * createTime : 2016-08-24 01:14:02
         * modifyTime : 2016-08-24 01:14:02
         * groupBuyId : PT160819000000000302
         * agentId : 3
         * userId : 12
         * userSettleState : 0
         * agentSettleState : 0
         * userAddressId : 50
         * userAddress : 华杰大厦C
         * userHouseNumber : 北京市海淀区四道口北街
         * userName : 陈
         * userGender : 先生
         * userMobile : 18810718147
         * userBackupMobile :
         * userLongitude : 116.34729895397606
         * userLatitude : 39.97030613193949
         * goodsName : null
         * caution :
         * originPrice : 50.0
         * totalOriginPrice : 50.0
         * price : 26.88
         * totalPrice : 26.88
         * quantity : 1
         * transactionId : null
         * paymentType : 1
         * paymentState : 0
         * paymentExpireTime : 2016-08-24 01:29:02
         * hasPayed : 0
         * status : null
         * hasDel : 0
         * agentRate : 0.0
         * agentRateAmt : 0.0
         * userAmt : 26.88
         * groupBuy : null
         */

        private GroupbuyOrderBean groupbuyOrder;
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

        public Object getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(Object merchantId) {
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

        public Object getUserAddressId() {
            return userAddressId;
        }

        public void setUserAddressId(Object userAddressId) {
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

        public String getUserBackupMobile() {
            return userBackupMobile;
        }

        public void setUserBackupMobile(String userBackupMobile) {
            this.userBackupMobile = userBackupMobile;
        }

        public Object getUserLongitude() {
            return userLongitude;
        }

        public void setUserLongitude(Object userLongitude) {
            this.userLongitude = userLongitude;
        }

        public Object getUserLatitude() {
            return userLatitude;
        }

        public void setUserLatitude(Object userLatitude) {
            this.userLatitude = userLatitude;
        }

        public Object getPromoInfoJson() {
            return promoInfoJson;
        }

        public void setPromoInfoJson(Object promoInfoJson) {
            this.promoInfoJson = promoInfoJson;
        }

        public Object getRedBagJson() {
            return redBagJson;
        }

        public void setRedBagJson(Object redBagJson) {
            this.redBagJson = redBagJson;
        }

        public Object getRedBagTotalAmt() {
            return redBagTotalAmt;
        }

        public void setRedBagTotalAmt(Object redBagTotalAmt) {
            this.redBagTotalAmt = redBagTotalAmt;
        }

        public Object getShipmentType() {
            return shipmentType;
        }

        public void setShipmentType(Object shipmentType) {
            this.shipmentType = shipmentType;
        }

        public Object getCaution() {
            return caution;
        }

        public void setCaution(Object caution) {
            this.caution = caution;
        }

        public int getItemsPrice() {
            return itemsPrice;
        }

        public void setItemsPrice(int itemsPrice) {
            this.itemsPrice = itemsPrice;
        }

        public int getBoxPrice() {
            return boxPrice;
        }

        public void setBoxPrice(int boxPrice) {
            this.boxPrice = boxPrice;
        }

        public int getShippingFee() {
            return shippingFee;
        }

        public void setShippingFee(int shippingFee) {
            this.shippingFee = shippingFee;
        }

        public int getOriginalTotalPrice() {
            return originalTotalPrice;
        }

        public void setOriginalTotalPrice(int originalTotalPrice) {
            this.originalTotalPrice = originalTotalPrice;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public Object getExpectArrivalTime() {
            return expectArrivalTime;
        }

        public void setExpectArrivalTime(Object expectArrivalTime) {
            this.expectArrivalTime = expectArrivalTime;
        }

        public Object getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(Object transactionId) {
            this.transactionId = transactionId;
        }

        public Object getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(Object paymentType) {
            this.paymentType = paymentType;
        }

        public int getPaymentState() {
            return paymentState;
        }

        public void setPaymentState(int paymentState) {
            this.paymentState = paymentState;
        }

        public Object getPaymentExpireTime() {
            return paymentExpireTime;
        }

        public void setPaymentExpireTime(Object paymentExpireTime) {
            this.paymentExpireTime = paymentExpireTime;
        }

        public Object getAcceptedExpireTime() {
            return acceptedExpireTime;
        }

        public void setAcceptedExpireTime(Object acceptedExpireTime) {
            this.acceptedExpireTime = acceptedExpireTime;
        }

        public int getHasPayed() {
            return hasPayed;
        }

        public void setHasPayed(int hasPayed) {
            this.hasPayed = hasPayed;
        }

        public Object getOrderFlowStatus() {
            return orderFlowStatus;
        }

        public void setOrderFlowStatus(Object orderFlowStatus) {
            this.orderFlowStatus = orderFlowStatus;
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

        public int getSysRate() {
            return sysRate;
        }

        public void setSysRate(int sysRate) {
            this.sysRate = sysRate;
        }

        public int getSysRateAmt() {
            return sysRateAmt;
        }

        public void setSysRateAmt(int sysRateAmt) {
            this.sysRateAmt = sysRateAmt;
        }

        public int getAgentRate() {
            return agentRate;
        }

        public void setAgentRate(int agentRate) {
            this.agentRate = agentRate;
        }

        public int getAgentRateAmt() {
            return agentRateAmt;
        }

        public void setAgentRateAmt(int agentRateAmt) {
            this.agentRateAmt = agentRateAmt;
        }

        public int getCurrentAgentRate() {
            return currentAgentRate;
        }

        public void setCurrentAgentRate(int currentAgentRate) {
            this.currentAgentRate = currentAgentRate;
        }

        public int getCurrentAgentRateAmt() {
            return currentAgentRateAmt;
        }

        public void setCurrentAgentRateAmt(int currentAgentRateAmt) {
            this.currentAgentRateAmt = currentAgentRateAmt;
        }

        public int getProvinceAgentRate() {
            return provinceAgentRate;
        }

        public void setProvinceAgentRate(int provinceAgentRate) {
            this.provinceAgentRate = provinceAgentRate;
        }

        public int getProvinceAgentRateAmt() {
            return provinceAgentRateAmt;
        }

        public void setProvinceAgentRateAmt(int provinceAgentRateAmt) {
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

        public GroupbuyOrderBean getGroupbuyOrder() {
            return groupbuyOrder;
        }

        public void setGroupbuyOrder(GroupbuyOrderBean groupbuyOrder) {
            this.groupbuyOrder = groupbuyOrder;
        }

        public List<?> getOrderItems() {
            return orderItems;
        }

        public void setOrderItems(List<?> orderItems) {
            this.orderItems = orderItems;
        }

        public static class GroupbuyOrderBean {
            private String id;
            private String createTime;
            private String modifyTime;
            private String groupBuyId;
            private int agentId;
            private int userId;
            private int userSettleState;
            private int agentSettleState;
            private int userAddressId;
            private String userAddress;
            private String userHouseNumber;
            private String userName;
            private String userGender;
            private String userMobile;
            private String userBackupMobile;
            private double userLongitude;
            private double userLatitude;
            private Object goodsName;
            private String caution;
            private double originPrice;
            private double totalOriginPrice;
            private double price;
            private double totalPrice;
            private int quantity;
            private Object transactionId;
            private int paymentType;
            private int paymentState;
            private String paymentExpireTime;
            private int hasPayed;
            private Object status;
            private int hasDel;
            private double agentRate;
            private double agentRateAmt;
            private double userAmt;
            private Object groupBuy;

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

            public String getGroupBuyId() {
                return groupBuyId;
            }

            public void setGroupBuyId(String groupBuyId) {
                this.groupBuyId = groupBuyId;
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

            public int getUserSettleState() {
                return userSettleState;
            }

            public void setUserSettleState(int userSettleState) {
                this.userSettleState = userSettleState;
            }

            public int getAgentSettleState() {
                return agentSettleState;
            }

            public void setAgentSettleState(int agentSettleState) {
                this.agentSettleState = agentSettleState;
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

            public String getUserBackupMobile() {
                return userBackupMobile;
            }

            public void setUserBackupMobile(String userBackupMobile) {
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

            public Object getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(Object goodsName) {
                this.goodsName = goodsName;
            }

            public String getCaution() {
                return caution;
            }

            public void setCaution(String caution) {
                this.caution = caution;
            }

            public double getOriginPrice() {
                return originPrice;
            }

            public void setOriginPrice(double originPrice) {
                this.originPrice = originPrice;
            }

            public double getTotalOriginPrice() {
                return totalOriginPrice;
            }

            public void setTotalOriginPrice(double totalOriginPrice) {
                this.totalOriginPrice = totalOriginPrice;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(double totalPrice) {
                this.totalPrice = totalPrice;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
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

            public int getHasPayed() {
                return hasPayed;
            }

            public void setHasPayed(int hasPayed) {
                this.hasPayed = hasPayed;
            }

            public Object getStatus() {
                return status;
            }

            public void setStatus(Object status) {
                this.status = status;
            }

            public int getHasDel() {
                return hasDel;
            }

            public void setHasDel(int hasDel) {
                this.hasDel = hasDel;
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

            public double getUserAmt() {
                return userAmt;
            }

            public void setUserAmt(double userAmt) {
                this.userAmt = userAmt;
            }

            public Object getGroupBuy() {
                return groupBuy;
            }

            public void setGroupBuy(Object groupBuy) {
                this.groupBuy = groupBuy;
            }
        }
    }
}

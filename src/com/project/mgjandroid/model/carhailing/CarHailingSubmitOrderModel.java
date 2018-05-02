package com.project.mgjandroid.model.carhailing;

import com.project.mgjandroid.model.Entity;

import java.util.List;

/**
 * Created by User_Cjh on 2016/12/13.
 */
public class CarHailingSubmitOrderModel extends Entity {

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

    public static class ValueBean {
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
        private Object defCommentsTime;
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
        private Object groupbuyOrder;
        private ChauffeurOrderBean chauffeurOrder;
        private boolean canReturnOrChange;
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

        public Object getDefCommentsTime() {
            return defCommentsTime;
        }

        public void setDefCommentsTime(Object defCommentsTime) {
            this.defCommentsTime = defCommentsTime;
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

        public Object getGroupbuyOrder() {
            return groupbuyOrder;
        }

        public void setGroupbuyOrder(Object groupbuyOrder) {
            this.groupbuyOrder = groupbuyOrder;
        }

        public ChauffeurOrderBean getChauffeurOrder() {
            return chauffeurOrder;
        }

        public void setChauffeurOrder(ChauffeurOrderBean chauffeurOrder) {
            this.chauffeurOrder = chauffeurOrder;
        }

        public boolean isCanReturnOrChange() {
            return canReturnOrChange;
        }

        public void setCanReturnOrChange(boolean canReturnOrChange) {
            this.canReturnOrChange = canReturnOrChange;
        }

        public List<?> getOrderItems() {
            return orderItems;
        }

        public void setOrderItems(List<?> orderItems) {
            this.orderItems = orderItems;
        }

        public static class ChauffeurOrderBean extends Entity {
            private String id;
            private String createTime;
            private String modifyTime;
            private int chauffeurTripId;
            private int chauffeurTripDetailId;
            private int agentId;
            private int chauffeurId;
            private int userId;
            private String userName;
            private String userMobile;
            private String userHeaderImg;
            private int chauffeurSettleState;
            private int agentSettleState;
            private Object transactionId;
            private int paymentType;
            private int paymentState;
            private String paymentExpireTime;
            private int hasPayed;
            private int status;
            private double price;
            private int peopleNum;
            private double totalPrice;
            private int chauffeurTripType;
            private Object caution;
            private int hasComments;
            private Object orderDoneTime;
            private Object defDoneTime;
            private Object defCommontsTime;
            private int hasDel;
            private int chauffeurHasDel;
            private double sysRate;
            private double sysRateAmt;
            private double agentRate;
            private double agentRateAmt;
            private int currentAgentRate;
            private double currentAgentRateAmt;
            private double provinceAgentRate;
            private double provinceAgentRateAmt;
            private double rateTotalAmt;
            private double chauffeurAmt;
            private String leaveTime;

            private ChauffeurOrderTripBean chauffeurOrderTrip;
            private Object chauffeurTrip;
            private Object chauffeurOrderCommonts;
            private Object chauffeur;
            private Object agentJson;

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

            public int getChauffeurTripId() {
                return chauffeurTripId;
            }

            public void setChauffeurTripId(int chauffeurTripId) {
                this.chauffeurTripId = chauffeurTripId;
            }

            public int getChauffeurTripDetailId() {
                return chauffeurTripDetailId;
            }

            public void setChauffeurTripDetailId(int chauffeurTripDetailId) {
                this.chauffeurTripDetailId = chauffeurTripDetailId;
            }

            public int getAgentId() {
                return agentId;
            }

            public void setAgentId(int agentId) {
                this.agentId = agentId;
            }

            public int getChauffeurId() {
                return chauffeurId;
            }

            public void setChauffeurId(int chauffeurId) {
                this.chauffeurId = chauffeurId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserMobile() {
                return userMobile;
            }

            public void setUserMobile(String userMobile) {
                this.userMobile = userMobile;
            }

            public String getUserHeaderImg() {
                return userHeaderImg;
            }

            public void setUserHeaderImg(String userHeaderImg) {
                this.userHeaderImg = userHeaderImg;
            }

            public int getChauffeurSettleState() {
                return chauffeurSettleState;
            }

            public void setChauffeurSettleState(int chauffeurSettleState) {
                this.chauffeurSettleState = chauffeurSettleState;
            }

            public int getAgentSettleState() {
                return agentSettleState;
            }

            public void setAgentSettleState(int agentSettleState) {
                this.agentSettleState = agentSettleState;
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getPeopleNum() {
                return peopleNum;
            }

            public void setPeopleNum(int peopleNum) {
                this.peopleNum = peopleNum;
            }

            public double getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(double totalPrice) {
                this.totalPrice = totalPrice;
            }

            public int getChauffeurTripType() {
                return chauffeurTripType;
            }

            public void setChauffeurTripType(int chauffeurTripType) {
                this.chauffeurTripType = chauffeurTripType;
            }

            public Object getCaution() {
                return caution;
            }

            public void setCaution(Object caution) {
                this.caution = caution;
            }

            public int getHasComments() {
                return hasComments;
            }

            public void setHasComments(int hasComments) {
                this.hasComments = hasComments;
            }

            public Object getOrderDoneTime() {
                return orderDoneTime;
            }

            public void setOrderDoneTime(Object orderDoneTime) {
                this.orderDoneTime = orderDoneTime;
            }

            public Object getDefDoneTime() {
                return defDoneTime;
            }

            public void setDefDoneTime(Object defDoneTime) {
                this.defDoneTime = defDoneTime;
            }

            public Object getDefCommontsTime() {
                return defCommontsTime;
            }

            public void setDefCommontsTime(Object defCommontsTime) {
                this.defCommontsTime = defCommontsTime;
            }

            public int getHasDel() {
                return hasDel;
            }

            public void setHasDel(int hasDel) {
                this.hasDel = hasDel;
            }

            public int getChauffeurHasDel() {
                return chauffeurHasDel;
            }

            public void setChauffeurHasDel(int chauffeurHasDel) {
                this.chauffeurHasDel = chauffeurHasDel;
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

            public int getCurrentAgentRate() {
                return currentAgentRate;
            }

            public void setCurrentAgentRate(int currentAgentRate) {
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

            public double getRateTotalAmt() {
                return rateTotalAmt;
            }

            public void setRateTotalAmt(double rateTotalAmt) {
                this.rateTotalAmt = rateTotalAmt;
            }

            public double getChauffeurAmt() {
                return chauffeurAmt;
            }

            public void setChauffeurAmt(double chauffeurAmt) {
                this.chauffeurAmt = chauffeurAmt;
            }

            public String getLeaveTime() {
                return leaveTime;
            }

            public void setLeaveTime(String leaveTime) {
                this.leaveTime = leaveTime;
            }

            public ChauffeurOrderTripBean getChauffeurOrderTrip() {
                return chauffeurOrderTrip;
            }

            public void setChauffeurOrderTrip(ChauffeurOrderTripBean chauffeurOrderTrip) {
                this.chauffeurOrderTrip = chauffeurOrderTrip;
            }

            public Object getChauffeurTrip() {
                return chauffeurTrip;
            }

            public void setChauffeurTrip(Object chauffeurTrip) {
                this.chauffeurTrip = chauffeurTrip;
            }

            public Object getChauffeurOrderCommonts() {
                return chauffeurOrderCommonts;
            }

            public void setChauffeurOrderCommonts(Object chauffeurOrderCommonts) {
                this.chauffeurOrderCommonts = chauffeurOrderCommonts;
            }

            public Object getChauffeur() {
                return chauffeur;
            }

            public void setChauffeur(Object chauffeur) {
                this.chauffeur = chauffeur;
            }

            public Object getAgentJson() {
                return agentJson;
            }

            public void setAgentJson(Object agentJson) {
                this.agentJson = agentJson;
            }

            public static class ChauffeurOrderTripBean extends Entity {
                private int id;
                private String createTime;
                private String modifyTime;
                private String chauffeurOrderId;
                private int chauffeurId;
                private int chauffeurTripId;
                private int chauffeurTripDetailId;
                private int peopleNum;
                private int chauffeurTripType;
                private String leaveTime;
                private String service;
                private String chauffeurTripStartAddress;
                private String chauffeurTripEndAddress;
                private String startAddress;
                private String endAddress;
                private String startProvinceName;
                private String startCityName;
                private String startDistrictName;
                private String endProvinceName;
                private String endCityName;
                private String endDistrictName;
                private double price;
                private double totalPrice;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
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

                public String getChauffeurOrderId() {
                    return chauffeurOrderId;
                }

                public void setChauffeurOrderId(String chauffeurOrderId) {
                    this.chauffeurOrderId = chauffeurOrderId;
                }

                public int getChauffeurId() {
                    return chauffeurId;
                }

                public void setChauffeurId(int chauffeurId) {
                    this.chauffeurId = chauffeurId;
                }

                public int getChauffeurTripId() {
                    return chauffeurTripId;
                }

                public void setChauffeurTripId(int chauffeurTripId) {
                    this.chauffeurTripId = chauffeurTripId;
                }

                public int getChauffeurTripDetailId() {
                    return chauffeurTripDetailId;
                }

                public void setChauffeurTripDetailId(int chauffeurTripDetailId) {
                    this.chauffeurTripDetailId = chauffeurTripDetailId;
                }

                public int getPeopleNum() {
                    return peopleNum;
                }

                public void setPeopleNum(int peopleNum) {
                    this.peopleNum = peopleNum;
                }

                public int getChauffeurTripType() {
                    return chauffeurTripType;
                }

                public void setChauffeurTripType(int chauffeurTripType) {
                    this.chauffeurTripType = chauffeurTripType;
                }

                public String getLeaveTime() {
                    return leaveTime;
                }

                public void setLeaveTime(String leaveTime) {
                    this.leaveTime = leaveTime;
                }

                public String getService() {
                    return service;
                }

                public void setService(String service) {
                    this.service = service;
                }

                public String getChauffeurTripStartAddress() {
                    return chauffeurTripStartAddress;
                }

                public void setChauffeurTripStartAddress(String chauffeurTripStartAddress) {
                    this.chauffeurTripStartAddress = chauffeurTripStartAddress;
                }

                public String getChauffeurTripEndAddress() {
                    return chauffeurTripEndAddress;
                }

                public void setChauffeurTripEndAddress(String chauffeurTripEndAddress) {
                    this.chauffeurTripEndAddress = chauffeurTripEndAddress;
                }

                public String getStartAddress() {
                    return startAddress;
                }

                public void setStartAddress(String startAddress) {
                    this.startAddress = startAddress;
                }

                public String getEndAddress() {
                    return endAddress;
                }

                public void setEndAddress(String endAddress) {
                    this.endAddress = endAddress;
                }

                public String getStartProvinceName() {
                    return startProvinceName;
                }

                public void setStartProvinceName(String startProvinceName) {
                    this.startProvinceName = startProvinceName;
                }

                public String getStartCityName() {
                    return startCityName;
                }

                public void setStartCityName(String startCityName) {
                    this.startCityName = startCityName;
                }

                public String getStartDistrictName() {
                    return startDistrictName;
                }

                public void setStartDistrictName(String startDistrictName) {
                    this.startDistrictName = startDistrictName;
                }

                public String getEndProvinceName() {
                    return endProvinceName;
                }

                public void setEndProvinceName(String endProvinceName) {
                    this.endProvinceName = endProvinceName;
                }

                public String getEndCityName() {
                    return endCityName;
                }

                public void setEndCityName(String endCityName) {
                    this.endCityName = endCityName;
                }

                public String getEndDistrictName() {
                    return endDistrictName;
                }

                public void setEndDistrictName(String endDistrictName) {
                    this.endDistrictName = endDistrictName;
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
            }
        }
    }
}

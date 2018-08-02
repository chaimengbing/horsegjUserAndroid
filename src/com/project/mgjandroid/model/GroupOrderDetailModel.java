package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.Agent;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by User_Cjh on 2016/8/24.
 */
public class GroupOrderDetailModel extends Entity {


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

    public static class ValueEntity extends Entity {
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
        private String redBagJson;
        private Object redBagTotalAmt;
        private Object shipmentType;
        private Object caution;
        private double itemsPrice;
        private double boxPrice;
        private double shippingFee;
        private double originalTotalPrice;
        private double totalPrice;
        private Object expectArrivalTime;
        private Object transactionId;
        private Object paymentType;
        private int paymentState;
        private Object paymentExpireTime;
        private Object acceptedExpireTime;
        private double hasPayed;
        private Object orderFlowStatus;
        private int hasDel;
        private Object deliveryTaskId;
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
        private GroupbuyOrderBean groupbuyOrder;
        private List<?> orderItems;
        private ShareRedBagInfo shareRedBagInfo;

        public ShareRedBagInfo getShareRedBagInfo() {
            return shareRedBagInfo;
        }

        public void setShareRedBagInfo(ShareRedBagInfo shareRedBagInfo) {
            this.shareRedBagInfo = shareRedBagInfo;
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

        public String getRedBagJson() {
            return redBagJson;
        }

        public void setRedBagJson(String redBagJson) {
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

        public double getHasPayed() {
            return hasPayed;
        }

        public void setHasPayed(double hasPayed) {
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

        public static class ShareRedBagInfo extends Entity{

            /**
             * title : 马管家拼手气红包
             * memo : 一大波红包等你来撩，手快有，手慢无~
             * img : http://7xu5hi.com1.z0.glb.clouddn.com/201807301450489509848.png
             * url : http://120.24.16.64//horsegj/dist/html/shareredbag/redbag.html?shareRedBagRulesId=181
             */

            private String title;
            private String memo;
            private String img;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getMemo() {
                return memo;
            }

            public void setMemo(String memo) {
                this.memo = memo;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class GroupbuyOrderBean extends Entity {
            private String id;
            private String createTime;
            private String modifyTime;
            private String groupBuyId;
            private int agentId;
            private int userId;
            private int userSettleState;
            private int agentSettleState;
            private Object defSettleTime;
            private Object defCommontsTime;
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
            private BigDecimal price;
            private BigDecimal totalPrice;
            private int quantity;
            private Object transactionId;
            private int paymentType;
            private int paymentState;
            private String paymentExpireTime;

            public String getOrderDoneTime() {
                return orderDoneTime;
            }

            public void setOrderDoneTime(String orderDoneTime) {
                this.orderDoneTime = orderDoneTime;
            }

            private String orderDoneTime;
            private double hasPayed;
            private int status;
            private int hasDel;
            private double agentRate;
            private double agentRateAmt;
            private double userAmt;
            private int hasComments;

            private GroupBuyBean groupBuy;
            private Agent agentInfoMap;

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

            public Object getDefSettleTime() {
                return defSettleTime;
            }

            public void setDefSettleTime(Object defSettleTime) {
                this.defSettleTime = defSettleTime;
            }

            public Object getDefCommontsTime() {
                return defCommontsTime;
            }

            public void setDefCommontsTime(Object defCommontsTime) {
                this.defCommontsTime = defCommontsTime;
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

            public BigDecimal getPrice() {
                return price;
            }

            public void setPrice(BigDecimal price) {
                this.price = price;
            }

            public BigDecimal getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(BigDecimal totalPrice) {
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

            public double getHasPayed() {
                return hasPayed;
            }

            public void setHasPayed(double hasPayed) {
                this.hasPayed = hasPayed;
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

            public GroupBuyBean getGroupBuy() {
                return groupBuy;
            }

            public void setGroupBuy(GroupBuyBean groupBuy) {
                this.groupBuy = groupBuy;
            }

            public Agent getAgentInfoMap() {
                return agentInfoMap;
            }

            public void setAgentInfoMap(Agent agentInfoMap) {
                this.agentInfoMap = agentInfoMap;
            }

            public int getHasComments() {
                return hasComments;
            }

            public void setHasComments(int hasComments) {
                this.hasComments = hasComments;
            }

            public static class GroupBuyBean extends Entity {
                private String createTime;
                private String modifyTime;
                private String id;
                private int userId;
                private int agentId;
                private String goodsName;
                private int minNum;
                private int maxNum;
                private int totalNum;
                private int totalUserNum;
                private double price;
                private double originalPrice;
                private Object totalPrice;
                private int days;
                private int deliveryDays;
                private Object service;
                private String description;
                private Object marketingImg;
                private String imgs;
                private double agentRate;
                private double agentRateAmt;
                private double userAmt;
                private String startTime;
                private String defaultEndTime;
                private String endTime;
                private int sortNo;
                private Object memo;
                private int status;
                private int hasDel;
                private String shareUrl;

                public String getShareUrl() {
                    return shareUrl;
                }

                public void setShareUrl(String shareUrl) {
                    this.shareUrl = shareUrl;
                }

                private GroupBuyUserBean groupBuyUser;
                private List<OtherGroupBuyListBean> otherGroupBuyList;

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

                public int getUserId() {
                    return userId;
                }

                public void setUserId(int userId) {
                    this.userId = userId;
                }

                public int getAgentId() {
                    return agentId;
                }

                public void setAgentId(int agentId) {
                    this.agentId = agentId;
                }

                public String getGoodsName() {
                    return goodsName;
                }

                public void setGoodsName(String goodsName) {
                    this.goodsName = goodsName;
                }

                public int getMinNum() {
                    return minNum;
                }

                public void setMinNum(int minNum) {
                    this.minNum = minNum;
                }

                public int getMaxNum() {
                    return maxNum;
                }

                public void setMaxNum(int maxNum) {
                    this.maxNum = maxNum;
                }

                public int getTotalNum() {
                    return totalNum;
                }

                public void setTotalNum(int totalNum) {
                    this.totalNum = totalNum;
                }

                public int getTotalUserNum() {
                    return totalUserNum;
                }

                public void setTotalUserNum(int totalUserNum) {
                    this.totalUserNum = totalUserNum;
                }

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public double getOriginalPrice() {
                    return originalPrice;
                }

                public void setOriginalPrice(double originalPrice) {
                    this.originalPrice = originalPrice;
                }

                public Object getTotalPrice() {
                    return totalPrice;
                }

                public void setTotalPrice(Object totalPrice) {
                    this.totalPrice = totalPrice;
                }

                public int getDays() {
                    return days;
                }

                public void setDays(int days) {
                    this.days = days;
                }

                public int getDeliveryDays() {
                    return deliveryDays;
                }

                public void setDeliveryDays(int deliveryDays) {
                    this.deliveryDays = deliveryDays;
                }

                public Object getService() {
                    return service;
                }

                public void setService(Object service) {
                    this.service = service;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public Object getMarketingImg() {
                    return marketingImg;
                }

                public void setMarketingImg(Object marketingImg) {
                    this.marketingImg = marketingImg;
                }

                public String getImgs() {
                    return imgs;
                }

                public void setImgs(String imgs) {
                    this.imgs = imgs;
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

                public String getStartTime() {
                    return startTime;
                }

                public void setStartTime(String startTime) {
                    this.startTime = startTime;
                }

                public String getDefaultEndTime() {
                    return defaultEndTime;
                }

                public void setDefaultEndTime(String defaultEndTime) {
                    this.defaultEndTime = defaultEndTime;
                }

                public String getEndTime() {
                    return endTime;
                }

                public void setEndTime(String endTime) {
                    this.endTime = endTime;
                }

                public int getSortNo() {
                    return sortNo;
                }

                public void setSortNo(int sortNo) {
                    this.sortNo = sortNo;
                }

                public Object getMemo() {
                    return memo;
                }

                public void setMemo(Object memo) {
                    this.memo = memo;
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

                public GroupBuyUserBean getGroupBuyUser() {
                    return groupBuyUser;
                }

                public void setGroupBuyUser(GroupBuyUserBean groupBuyUser) {
                    this.groupBuyUser = groupBuyUser;
                }

                public List<OtherGroupBuyListBean> getOtherGroupBuyList() {
                    return otherGroupBuyList;
                }

                public void setOtherGroupBuyList(List<OtherGroupBuyListBean> otherGroupBuyList) {
                    this.otherGroupBuyList = otherGroupBuyList;
                }

                public static class GroupBuyUserBean extends Entity {
                    private int id;
                    private String createTime;
                    private String modifyTime;
                    private String name;
                    private String headerImg;
                    private String mobile;
                    private int sex;
                    private String idCardNo;
                    private String intro;
                    private String address;
                    private int benefitUserCount;
                    private double compositeAvgScore;
                    private double goodsAvgScore;
                    private double serviceAvgScore;
                    private double totalCommontsCount;
                    private List<?> commontsAllList;

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

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getHeaderImg() {
                        return headerImg;
                    }

                    public void setHeaderImg(String headerImg) {
                        this.headerImg = headerImg;
                    }

                    public String getMobile() {
                        return mobile;
                    }

                    public void setMobile(String mobile) {
                        this.mobile = mobile;
                    }

                    public int getSex() {
                        return sex;
                    }

                    public void setSex(int sex) {
                        this.sex = sex;
                    }

                    public String getIdCardNo() {
                        return idCardNo;
                    }

                    public void setIdCardNo(String idCardNo) {
                        this.idCardNo = idCardNo;
                    }

                    public String getIntro() {
                        return intro;
                    }

                    public void setIntro(String intro) {
                        this.intro = intro;
                    }

                    public String getAddress() {
                        return address;
                    }

                    public void setAddress(String address) {
                        this.address = address;
                    }

                    public int getBenefitUserCount() {
                        return benefitUserCount;
                    }

                    public void setBenefitUserCount(int benefitUserCount) {
                        this.benefitUserCount = benefitUserCount;
                    }

                    public double getCompositeAvgScore() {
                        return compositeAvgScore;
                    }

                    public void setCompositeAvgScore(double compositeAvgScore) {
                        this.compositeAvgScore = compositeAvgScore;
                    }

                    public double getGoodsAvgScore() {
                        return goodsAvgScore;
                    }

                    public void setGoodsAvgScore(double goodsAvgScore) {
                        this.goodsAvgScore = goodsAvgScore;
                    }

                    public double getServiceAvgScore() {
                        return serviceAvgScore;
                    }

                    public void setServiceAvgScore(double serviceAvgScore) {
                        this.serviceAvgScore = serviceAvgScore;
                    }

                    public double getTotalCommontsCount() {
                        return totalCommontsCount;
                    }

                    public void setTotalCommontsCount(double totalCommontsCount) {
                        this.totalCommontsCount = totalCommontsCount;
                    }

                    public List<?> getCommontsAllList() {
                        return commontsAllList;
                    }

                    public void setCommontsAllList(List<?> commontsAllList) {
                        this.commontsAllList = commontsAllList;
                    }
                }

                public static class OtherGroupBuyListBean extends Entity {
                    private String createTime;
                    private String modifyTime;
                    private String id;
                    private int userId;
                    private int agentId;
                    private String goodsName;
                    private int minNum;
                    private int maxNum;
                    private int totalNum;
                    private int totalUserNum;
                    private BigDecimal price;
                    private double originalPrice;
                    private Object totalPrice;
                    private int days;
                    private int deliveryDays;
                    private String service;
                    private String description;
                    private String marketingImg;
                    private String imgs;
                    private double agentRate;
                    private double agentRateAmt;
                    private double userAmt;
                    private String startTime;
                    private String defaultEndTime;
                    private Object endTime;
                    private int sortNo;
                    private Object memo;
                    private int status;
                    private int hasDel;

                    private GroupBuyUserBean groupBuyUser;
                    private Object otherGroupBuyList;

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

                    public int getUserId() {
                        return userId;
                    }

                    public void setUserId(int userId) {
                        this.userId = userId;
                    }

                    public int getAgentId() {
                        return agentId;
                    }

                    public void setAgentId(int agentId) {
                        this.agentId = agentId;
                    }

                    public String getGoodsName() {
                        return goodsName;
                    }

                    public void setGoodsName(String goodsName) {
                        this.goodsName = goodsName;
                    }

                    public int getMinNum() {
                        return minNum;
                    }

                    public void setMinNum(int minNum) {
                        this.minNum = minNum;
                    }

                    public int getMaxNum() {
                        return maxNum;
                    }

                    public void setMaxNum(int maxNum) {
                        this.maxNum = maxNum;
                    }

                    public int getTotalNum() {
                        return totalNum;
                    }

                    public void setTotalNum(int totalNum) {
                        this.totalNum = totalNum;
                    }

                    public int getTotalUserNum() {
                        return totalUserNum;
                    }

                    public void setTotalUserNum(int totalUserNum) {
                        this.totalUserNum = totalUserNum;
                    }

                    public BigDecimal getPrice() {
                        return price;
                    }

                    public void setPrice(BigDecimal price) {
                        this.price = price;
                    }

                    public double getOriginalPrice() {
                        return originalPrice;
                    }

                    public void setOriginalPrice(double originalPrice) {
                        this.originalPrice = originalPrice;
                    }

                    public Object getTotalPrice() {
                        return totalPrice;
                    }

                    public void setTotalPrice(Object totalPrice) {
                        this.totalPrice = totalPrice;
                    }

                    public int getDays() {
                        return days;
                    }

                    public void setDays(int days) {
                        this.days = days;
                    }

                    public int getDeliveryDays() {
                        return deliveryDays;
                    }

                    public void setDeliveryDays(int deliveryDays) {
                        this.deliveryDays = deliveryDays;
                    }

                    public String getService() {
                        return service;
                    }

                    public void setService(String service) {
                        this.service = service;
                    }

                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }

                    public String getMarketingImg() {
                        return marketingImg;
                    }

                    public void setMarketingImg(String marketingImg) {
                        this.marketingImg = marketingImg;
                    }

                    public String getImgs() {
                        return imgs;
                    }

                    public void setImgs(String imgs) {
                        this.imgs = imgs;
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

                    public String getStartTime() {
                        return startTime;
                    }

                    public void setStartTime(String startTime) {
                        this.startTime = startTime;
                    }

                    public String getDefaultEndTime() {
                        return defaultEndTime;
                    }

                    public void setDefaultEndTime(String defaultEndTime) {
                        this.defaultEndTime = defaultEndTime;
                    }

                    public Object getEndTime() {
                        return endTime;
                    }

                    public void setEndTime(Object endTime) {
                        this.endTime = endTime;
                    }

                    public int getSortNo() {
                        return sortNo;
                    }

                    public void setSortNo(int sortNo) {
                        this.sortNo = sortNo;
                    }

                    public Object getMemo() {
                        return memo;
                    }

                    public void setMemo(Object memo) {
                        this.memo = memo;
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

                    public GroupBuyUserBean getGroupBuyUser() {
                        return groupBuyUser;
                    }

                    public void setGroupBuyUser(GroupBuyUserBean groupBuyUser) {
                        this.groupBuyUser = groupBuyUser;
                    }

                    public Object getOtherGroupBuyList() {
                        return otherGroupBuyList;
                    }

                    public void setOtherGroupBuyList(Object otherGroupBuyList) {
                        this.otherGroupBuyList = otherGroupBuyList;
                    }

                    public static class GroupBuyUserBean extends Entity {
                        private int id;
                        private String createTime;
                        private String modifyTime;
                        private String name;
                        private Object headerImg;
                        private String mobile;
                        private int sex;
                        private String idCardNo;
                        private String intro;
                        private String address;
                        private int benefitUserCount;
                        private double compositeAvgScore;
                        private double goodsAvgScore;
                        private double serviceAvgScore;
                        private double totalCommontsCount;
                        private Object commontsAllList;

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

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }

                        public Object getHeaderImg() {
                            return headerImg;
                        }

                        public void setHeaderImg(Object headerImg) {
                            this.headerImg = headerImg;
                        }

                        public String getMobile() {
                            return mobile;
                        }

                        public void setMobile(String mobile) {
                            this.mobile = mobile;
                        }

                        public int getSex() {
                            return sex;
                        }

                        public void setSex(int sex) {
                            this.sex = sex;
                        }

                        public String getIdCardNo() {
                            return idCardNo;
                        }

                        public void setIdCardNo(String idCardNo) {
                            this.idCardNo = idCardNo;
                        }

                        public String getIntro() {
                            return intro;
                        }

                        public void setIntro(String intro) {
                            this.intro = intro;
                        }

                        public String getAddress() {
                            return address;
                        }

                        public void setAddress(String address) {
                            this.address = address;
                        }

                        public int getBenefitUserCount() {
                            return benefitUserCount;
                        }

                        public void setBenefitUserCount(int benefitUserCount) {
                            this.benefitUserCount = benefitUserCount;
                        }

                        public double getCompositeAvgScore() {
                            return compositeAvgScore;
                        }

                        public void setCompositeAvgScore(double compositeAvgScore) {
                            this.compositeAvgScore = compositeAvgScore;
                        }

                        public double getGoodsAvgScore() {
                            return goodsAvgScore;
                        }

                        public void setGoodsAvgScore(double goodsAvgScore) {
                            this.goodsAvgScore = goodsAvgScore;
                        }

                        public double getServiceAvgScore() {
                            return serviceAvgScore;
                        }

                        public void setServiceAvgScore(double serviceAvgScore) {
                            this.serviceAvgScore = serviceAvgScore;
                        }

                        public double getTotalCommontsCount() {
                            return totalCommontsCount;
                        }

                        public void setTotalCommontsCount(double totalCommontsCount) {
                            this.totalCommontsCount = totalCommontsCount;
                        }

                        public Object getCommontsAllList() {
                            return commontsAllList;
                        }

                        public void setCommontsAllList(Object commontsAllList) {
                            this.commontsAllList = commontsAllList;
                        }
                    }
                }
            }
        }
    }
}

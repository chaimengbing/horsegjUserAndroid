package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.GroupBuyOrder;
import com.project.mgjandroid.bean.GroupInfo;
import com.project.mgjandroid.bean.Merchant;
import com.project.mgjandroid.bean.ThirdPartyOrderBean;
import com.project.mgjandroid.bean.carhailing.CarHailingOrder;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseOrder;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by User_Cjh on 2016/8/23.
 */
public class NewOrderFragmentModel extends Entity {
    private int code;
    private String uuid;
    private boolean success;
    private String servertime;
    private List<ValueEntity> value;

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

    public List<ValueEntity> getValue() {
        return value;
    }

    public void setValue(List<ValueEntity> value) {
        this.value = value;
    }

    public static class ValueEntity extends Entity {
        private String createTime;
        private String modifyTime;
        private String id;
        private int type;
        private int merchantId;
        private Merchant merchant;
        private int merchantSettleMode;
        private int merchantSettleState;
        private int agentSettleState;
        private int agentId;
        private int userId;
        private int deliverymanId;
        private int userAddressId;
        private String userAddress;
        private String userHouseNumber;
        private String userName;
        private String userGender;
        private String userMobile;
        private String userBackupMobile;
        private double userLongitude;
        private double userLatitude;
        private String promoInfoJson;
        private String redBagJson;
        private BigDecimal redBagTotalAmt;
        private int shipmentType;
        private String caution;
        private BigDecimal itemsPrice;
        private BigDecimal boxPrice;
        private BigDecimal shippingFee;
        private BigDecimal originalTotalPrice;
        private BigDecimal totalPrice;
        private String expectArrivalTime;
        private String transactionId;
        private int paymentType;
        private int paymentState;
        private String paymentExpireTime;
        private String acceptedExpireTime;
        private BigDecimal hasPayed;
        private int orderFlowStatus;
        private int hasDel;
        private int deliveryTaskId;
        private Object deliveryTask;
        private int hasComments;

        public String getServerTime() {
            return serverTime;
        }

        public void setServerTime(String serverTime) {
            this.serverTime = serverTime;
        }

        private String serverTime;
        private BigDecimal sysRate;
        private BigDecimal sysRateAmt;
        private BigDecimal agentRate;
        private BigDecimal agentRateAmt;
        private BigDecimal currentAgentRate;
        private BigDecimal currentAgentRateAmt;
        private BigDecimal provinceAgentRate;
        private BigDecimal provinceAgentRateAmt;
        private int merchantAmt;
        private int agentAmt;
        private String cancelSource;
        private String cancelReason;
        private int isShow;
        private Object agent;
        private Object promoList;
        private Object redBagList;
        private Object agentPhone;
        private Object expectArrivalTimeTemp;
        private GroupBuyOrder groupbuyOrder;
        private List<OrderItemsEntity> orderItems;
        private CarHailingOrder chauffeurOrder;
        private GroupPurchaseOrder groupPurchaseOrder;
        private ThirdPartyOrderBean thirdpartyOrder;
        private LegworkModel legWorkOrder;

        public LegworkModel getLegWorkOrder() {
            return legWorkOrder;
        }

        public void setLegWorkOrder(LegworkModel legWorkOrder) {
            this.legWorkOrder = legWorkOrder;
        }

        public GroupPurchaseOrder getGroupPurchaseOrder() {
            return groupPurchaseOrder;
        }

        public void setGroupPurchaseOrder(GroupPurchaseOrder groupPurchaseOrder) {
            this.groupPurchaseOrder = groupPurchaseOrder;
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

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }

        public Merchant getMerchant() {
            return merchant;
        }

        public void setMerchant(Merchant merchant) {
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

        public int getDeliverymanId() {
            return deliverymanId;
        }

        public void setDeliverymanId(int deliverymanId) {
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

        public BigDecimal getRedBagTotalAmt() {
            return redBagTotalAmt;
        }

        public void setRedBagTotalAmt(BigDecimal redBagTotalAmt) {
            this.redBagTotalAmt = redBagTotalAmt;
        }

        public int getShipmentType() {
            return shipmentType;
        }

        public void setShipmentType(int shipmentType) {
            this.shipmentType = shipmentType;
        }

        public String getCaution() {
            return caution;
        }

        public void setCaution(String caution) {
            this.caution = caution;
        }

        public BigDecimal getItemsPrice() {
            return itemsPrice;
        }

        public void setItemsPrice(BigDecimal itemsPrice) {
            this.itemsPrice = itemsPrice;
        }

        public BigDecimal getBoxPrice() {
            return boxPrice;
        }

        public void setBoxPrice(BigDecimal boxPrice) {
            this.boxPrice = boxPrice;
        }

        public BigDecimal getShippingFee() {
            return shippingFee;
        }

        public void setShippingFee(BigDecimal shippingFee) {
            this.shippingFee = shippingFee;
        }

        public BigDecimal getOriginalTotalPrice() {
            return originalTotalPrice;
        }

        public void setOriginalTotalPrice(BigDecimal originalTotalPrice) {
            this.originalTotalPrice = originalTotalPrice;
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getExpectArrivalTime() {
            return expectArrivalTime;
        }

        public void setExpectArrivalTime(String expectArrivalTime) {
            this.expectArrivalTime = expectArrivalTime;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
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

        public BigDecimal getHasPayed() {
            return hasPayed;
        }

        public void setHasPayed(BigDecimal hasPayed) {
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

        public BigDecimal getSysRate() {
            return sysRate;
        }

        public void setSysRate(BigDecimal sysRate) {
            this.sysRate = sysRate;
        }

        public BigDecimal getSysRateAmt() {
            return sysRateAmt;
        }

        public void setSysRateAmt(BigDecimal sysRateAmt) {
            this.sysRateAmt = sysRateAmt;
        }

        public BigDecimal getAgentRate() {
            return agentRate;
        }

        public void setAgentRate(BigDecimal agentRate) {
            this.agentRate = agentRate;
        }

        public BigDecimal getAgentRateAmt() {
            return agentRateAmt;
        }

        public void setAgentRateAmt(BigDecimal agentRateAmt) {
            this.agentRateAmt = agentRateAmt;
        }

        public BigDecimal getCurrentAgentRate() {
            return currentAgentRate;
        }

        public void setCurrentAgentRate(BigDecimal currentAgentRate) {
            this.currentAgentRate = currentAgentRate;
        }

        public BigDecimal getCurrentAgentRateAmt() {
            return currentAgentRateAmt;
        }

        public void setCurrentAgentRateAmt(BigDecimal currentAgentRateAmt) {
            this.currentAgentRateAmt = currentAgentRateAmt;
        }

        public BigDecimal getProvinceAgentRate() {
            return provinceAgentRate;
        }

        public void setProvinceAgentRate(BigDecimal provinceAgentRate) {
            this.provinceAgentRate = provinceAgentRate;
        }

        public BigDecimal getProvinceAgentRateAmt() {
            return provinceAgentRateAmt;
        }

        public void setProvinceAgentRateAmt(BigDecimal provinceAgentRateAmt) {
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

        public String getCancelSource() {
            return cancelSource;
        }

        public void setCancelSource(String cancelSource) {
            this.cancelSource = cancelSource;
        }

        public String getCancelReason() {
            return cancelReason;
        }

        public void setCancelReason(String cancelReason) {
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

        public GroupBuyOrder getGroupbuyOrder() {
            return groupbuyOrder;
        }

        public void setGroupbuyOrder(GroupBuyOrder groupbuyOrder) {
            this.groupbuyOrder = groupbuyOrder;
        }

        public List<OrderItemsEntity> getOrderItems() {
            return orderItems;
        }

        public void setOrderItems(List<OrderItemsEntity> orderItems) {
            this.orderItems = orderItems;
        }

        public CarHailingOrder getChauffeurOrder() {
            return chauffeurOrder;
        }

        public void setChauffeurOrder(CarHailingOrder chauffeurOrder) {
            this.chauffeurOrder = chauffeurOrder;
        }

        public ThirdPartyOrderBean getThirdpartyOrder() {
            return thirdpartyOrder;
        }

        public void setThirdpartyOrder(ThirdPartyOrderBean thirdpartyOrder) {
            this.thirdpartyOrder = thirdpartyOrder;
        }

        public static class OrderItemsEntity extends Entity {
            private int id;
            private String createTime;
            private String modifyTime;
            private String orderId;
            private int goodsId;
            private int goodsSpecId;
            private String name;
            private String spec;
            private String unit;
            private int quantity;
            private BigDecimal originPrice;
            private BigDecimal price;
            private BigDecimal totalPrice;
            private BigDecimal boxPrice;
            private BigDecimal totalBoxPrice;
            private int isPromo;
            private String tip;
            private int rating;
            private boolean isShow;
            private String content;

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

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public int getGoodsSpecId() {
                return goodsSpecId;
            }

            public void setGoodsSpecId(int goodsSpecId) {
                this.goodsSpecId = goodsSpecId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSpec() {
                return spec;
            }

            public void setSpec(String spec) {
                this.spec = spec;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public BigDecimal getOriginPrice() {
                return originPrice;
            }

            public void setOriginPrice(BigDecimal originPrice) {
                this.originPrice = originPrice;
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

            public BigDecimal getBoxPrice() {
                return boxPrice;
            }

            public void setBoxPrice(BigDecimal boxPrice) {
                this.boxPrice = boxPrice;
            }

            public BigDecimal getTotalBoxPrice() {
                return totalBoxPrice;
            }

            public void setTotalBoxPrice(BigDecimal totalBoxPrice) {
                this.totalBoxPrice = totalBoxPrice;
            }

            public int getIsPromo() {
                return isPromo;
            }

            public void setIsPromo(int isPromo) {
                this.isPromo = isPromo;
            }

            public String getTip() {
                return tip;
            }

            public void setTip(String tip) {
                this.tip = tip;
            }

            public int getRating() {
                return rating;
            }

            public void setRating(int rating) {
                this.rating = rating;
            }

            public boolean isShow() {
                return isShow;
            }

            public void setIsShow(boolean isShow) {
                this.isShow = isShow;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }

    }
}

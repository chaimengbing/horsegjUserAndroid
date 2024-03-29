package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.Merchant;
import com.project.mgjandroid.bean.PromotionActivity;
import com.project.mgjandroid.bean.carhailing.CarHailingOrder;
import com.project.mgjandroid.bean.carhailing.CarRedBagActivity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by ning on 2016/3/14.
 */
public class SubmitOrderModel extends Entity {

    private int code;
    private String uuid;
    private Date servertime;
    /**
     * createTime : 2016-03-31 17:50:02
     * modifyTime : 2016-03-31 18:00:45
     * id : 2016033100004704
     * merchantId : 1
     * merchant : {"id":1,"createTime":null,"modifyTime":"2016-03-31 17:00:09","agentId":1,"name":"肯德基(中鼎餐厅)","description":"","contacts":"400558888","merchantStatus":1,"payments":"1,2","shipments":"2","limitDistance":3000,"province":"1","city":"2","district":"5","address":"北三环西路23号大钟寺中坤广场1楼(近地铁13号线大钟寺站) ","longitude":116.348849,"latitude":39.971683,"geohash":"tm3d0ju7ycmf","logo":"http://7xpvkm.com1.z0.glb.clouddn.com/201603081920420037128","imgs":null,"workingTime":"9:00-22:00","broadcast":"","minPrice":0,"shipFee":0,"invoiceSupport":1,"minInvoicePrice":50,"reserveSupport":1,"reserveDays":1,"prepareTime":30,"avgDeliveryTime":40,"hasTerminal":0,"showPraise":1,"showHotsale":1,"commentNum":6,"averageScore":4.167,"status":1,"distance":null,"promotions":null,"merchantUser":null}
     * agentId : 1
     * userId : 12
     * userAddressId : 50
     * userAddress : 华杰大厦C
     * userHouseNumber : 北京市海淀区四道口北街
     * userName : 陈
     * userGender : 先生
     * userMobile : 18810718147
     * userBackupMobile : null
     * userLongitude : 116.34729895397606
     * userLatitude : 39.97030613193949
     * promoInfoJson : []
     * shipmentType : 2
     * caution : null
     * itemsPrice : 33.5
     * boxPrice : 0.0
     * shippingFee : 7.0
     * originalTotalPrice : 33.5
     * totalPrice : 33.5
     * expectArrivalTime : 1
     * transactionId : null
     * paymentType : 2
     * paymentState : 0
     * paymentExpireTime : null
     * hasPayed : 0.0
     * orderFlowStatus : 7
     * hasDel : 0
     * orderItems : [{"id":460,"createTime":"2016-03-31 17:50:02","modifyTime":"2016-03-31 17:50:02","orderId":"2016033100004704","goodsId":4,"goodsSpecId":4,"name":"香辣鸡腿堡薯条套餐","spec":"","unit":"份","quantity":1,"originPrice":33.5,"price":33.5,"totalPrice":33.5,"boxPrice":0,"totalBoxPrice":0,"isPromo":0,"tip":""}]
     * deliveryTaskId : 98
     * deliveryTask : {"id":98,"createTime":"2016-03-31 17:50:02","modifyTime":"2016-03-31 18:00:45","agentId":1,"orderId":"2016033100004704","tOrder":null,"wakeTime":null,"deliverymanId":1,"deliveryman":{"id":1,"createTime":null,"modifyTime":null,"agentId":1,"token":null,"mobile":"18612141989","name":"wuyuandi","pwd":null,"ip":null,"client":null,"clientId":null,"apnsProduction":null,"app":null,"imei":null,"clientVersion":null,"hasDel":0,"longitude":116.347233,"latitude":39.970758,"geohash":null,"isOnline":1,"waitAcceptNum":0,"hasAcceptedNum":0,"arrivalMerchantNum":0,"deliveringNum":0,"doneNum":0,"cancelNum":0},"dispatchTime":"2016-03-31 17:53:05","processBy":"test,1","status":7,"deliveryDone":0}
     * hasComments : 0
     */

    private ValueEntity value;
    private boolean success;

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

    public Date getServertime() {
        return servertime;
    }

    public void setServertime(Date servertime) {
        this.servertime = servertime;
    }

    public static class ValueEntity extends Entity {
        private String createTime;
        private String modifyTime;
        private String id;
        private int type;
        private int merchantId;
        /**
         * id : 1
         * createTime : null
         * modifyTime : 2016-03-31 17:00:09
         * agentId : 1
         * name : 肯德基(中鼎餐厅)
         * description :
         * contacts : 400558888
         * merchantStatus : 1
         * payments : 1,2
         * shipments : 2
         * limitDistance : 3000
         * province : 1
         * city : 2
         * district : 5
         * address : 北三环西路23号大钟寺中坤广场1楼(近地铁13号线大钟寺站)
         * longitude : 116.348849
         * latitude : 39.971683
         * geohash : tm3d0ju7ycmf
         * logo : http://7xpvkm.com1.z0.glb.clouddn.com/201603081920420037128
         * imgs : null
         * workingTime : 9:00-22:00
         * broadcast :
         * minPrice : 0.0
         * shipFee : 0.0
         * invoiceSupport : 1
         * minInvoicePrice : 50.0
         * reserveSupport : 1
         * reserveDays : 1
         * prepareTime : 30
         * avgDeliveryTime : 40
         * hasTerminal : 0
         * showPraise : 1
         * showHotsale : 1
         * commentNum : 6
         * averageScore : 4.167
         * status : 1
         * distance : null
         * promotions : null
         * merchantUser : null
         */

        private Merchant merchant;
        private int agentId;
        private int userId;
        private int userAddressId;
        private String userAddress;
        private String userHouseNumber;
        private String userName;
        private String userGender;
        private String userMobile;
        private String userBackupMobile;
        private BigDecimal userLongitude;
        private BigDecimal userLatitude;
        private String promoInfoJson;
        private int shipmentType;
        private String caution;
        private BigDecimal itemsPrice;
        private BigDecimal boxPrice;
        private BigDecimal shippingFee;
        private BigDecimal originalTotalPrice;
        private BigDecimal totalPrice;
        private String redBagJson;
        private BigDecimal redBagTotalAmt;
        private String expectArrivalTime;
        private Object transactionId;
        private int paymentType;
        private int paymentState;
        private String paymentExpireTime;
        private String paymentFinishTime;
        private BigDecimal hasPayed;
        private int orderFlowStatus;
        private int hasDel;
        private int deliveryTaskId;
        /**
         * id : 98
         * createTime : 2016-03-31 17:50:02
         * modifyTime : 2016-03-31 18:00:45
         * agentId : 1
         * orderId : 2016033100004704
         * tOrder : null
         * wakeTime : null
         * deliverymanId : 1
         * deliveryman : {"id":1,"createTime":null,"modifyTime":null,"agentId":1,"token":null,"mobile":"18612141989","name":"wuyuandi","pwd":null,"ip":null,"client":null,"clientId":null,"apnsProduction":null,"app":null,"imei":null,"clientVersion":null,"hasDel":0,"longitude":116.347233,"latitude":39.970758,"geohash":null,"isOnline":1,"waitAcceptNum":0,"hasAcceptedNum":0,"arrivalMerchantNum":0,"deliveringNum":0,"doneNum":0,"cancelNum":0}
         * dispatchTime : 2016-03-31 17:53:05
         * processBy : test,1
         * status : 7
         * deliveryDone : 0
         */

        private DeliveryTaskEntity deliveryTask;
        private int hasComments;
        private String agentPhone;
        private boolean canReturnOrChange;
        private CarHailingOrder chauffeurOrder;
        private CarRedBagActivity carRedBagActivity;
        private String prepareOrderReceivingTime;
        private String orderDoneTime;
        private ShareRedBagInfo shareRedBagInfo;

        public String getOrderDoneTime() {
            return orderDoneTime;
        }

        public void setOrderDoneTime(String orderDoneTime) {
            this.orderDoneTime = orderDoneTime;
        }

        public String getPrepareOrderReceivingTime() {
            return prepareOrderReceivingTime;
        }

        public void setPrepareOrderReceivingTime(String prepareOrderReceivingTime) {
            this.prepareOrderReceivingTime = prepareOrderReceivingTime;
        }

        public String getPaymentFinishTime() {
            return paymentFinishTime;
        }

        public void setPaymentFinishTime(String paymentFinishTime) {
            this.paymentFinishTime = paymentFinishTime;
        }

        /**
         * id : 460
         * createTime : 2016-03-31 17:50:02
         * modifyTime : 2016-03-31 17:50:02
         * orderId : 2016033100004704
         * goodsId : 4
         * goodsSpecId : 4
         * name : 香辣鸡腿堡薯条套餐
         * spec :
         * unit : 份
         * quantity : 1
         * originPrice : 33.5
         * price : 33.5
         * totalPrice : 33.5
         * boxPrice : 0.0
         * totalBoxPrice : 0.0
         * isPromo : 0
         * tip :
         */

        /** 配送费优惠金额 */
        private BigDecimal shippingPreferentialFee = BigDecimal.ZERO;



        private List<OrderItemsEntity> orderItems;

        private List<PromotionActivity> promoList;

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

        public BigDecimal getUserLongitude() {
            return userLongitude;
        }

        public void setUserLongitude(BigDecimal userLongitude) {
            this.userLongitude = userLongitude;
        }

        public BigDecimal getUserLatitude() {
            return userLatitude;
        }

        public void setUserLatitude(BigDecimal userLatitude) {
            this.userLatitude = userLatitude;
        }

        public String getPromoInfoJson() {
            return promoInfoJson;
        }

        public void setPromoInfoJson(String promoInfoJson) {
            this.promoInfoJson = promoInfoJson;
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

        public DeliveryTaskEntity getDeliveryTask() {
            return deliveryTask;
        }

        public void setDeliveryTask(DeliveryTaskEntity deliveryTask) {
            this.deliveryTask = deliveryTask;
        }

        public ShareRedBagInfo getShareRedBagInfo() {
            return shareRedBagInfo;
        }

        public void setShareRedBagInfo(ShareRedBagInfo shareRedBagInfo) {
            this.shareRedBagInfo = shareRedBagInfo;
        }

        public int getHasComments() {
            return hasComments;
        }

        public void setHasComments(int hasComments) {
            this.hasComments = hasComments;
        }

        public List<OrderItemsEntity> getOrderItems() {
            return orderItems;
        }

        public void setOrderItems(List<OrderItemsEntity> orderItems) {
            this.orderItems = orderItems;
        }

        public List<PromotionActivity> getPromoList() {
            return promoList;
        }

        public void setPromoList(List<PromotionActivity> promoList) {
            this.promoList = promoList;
        }

        public String getAgentPhone() {
            return agentPhone;
        }

        public void setAgentPhone(String agentPhone) {
            this.agentPhone = agentPhone;
        }

        public boolean isCanReturnOrChange() {
            return canReturnOrChange;
        }

        public void setCanReturnOrChange(boolean canReturnOrChange) {
            this.canReturnOrChange = canReturnOrChange;
        }

        public CarHailingOrder getChauffeurOrder() {
            return chauffeurOrder;
        }

        public void setChauffeurOrder(CarHailingOrder chauffeurOrder) {
            this.chauffeurOrder = chauffeurOrder;
        }

        public CarRedBagActivity getCarRedBagActivity() {
            return carRedBagActivity;
        }

        public void setCarRedBagActivity(CarRedBagActivity carRedBagActivity) {
            this.carRedBagActivity = carRedBagActivity;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public BigDecimal getShippingPreferentialFee() {
            return shippingPreferentialFee;
        }

        public void setShippingPreferentialFee(BigDecimal shippingPreferentialFee) {
            this.shippingPreferentialFee = shippingPreferentialFee;
        }

        public static class DeliveryTaskEntity extends Entity {
            private int id;
            private String createTime;
            private String modifyTime;
            private int agentId;
            private String orderId;
            private Object tOrder;
            private Object wakeTime;
            private int deliverymanId;
            /**
             * id : 1
             * createTime : null
             * modifyTime : null
             * agentId : 1
             * token : null
             * mobile : 18612141989
             * name : wuyuandi
             * pwd : null
             * ip : null
             * client : null
             * clientId : null
             * apnsProduction : null
             * app : null
             * imei : null
             * clientVersion : null
             * hasDel : 0
             * longitude : 116.347233
             * latitude : 39.970758
             * geohash : null
             * isOnline : 1
             * waitAcceptNum : 0
             * hasAcceptedNum : 0
             * arrivalMerchantNum : 0
             * deliveringNum : 0
             * doneNum : 0
             * cancelNum : 0
             */

            private DeliverymanEntity deliveryman;
            private String dispatchTime;
            private String processBy;
            private String orderConfirmTime;
            private String acceptTime;
            private String arrivalMerchantTime;
            private int status;
            private int deliveryDone;

            public String getOrderConfirmTime() {
                return orderConfirmTime;
            }

            public void setOrderConfirmTime(String orderConfirmTime) {
                this.orderConfirmTime = orderConfirmTime;
            }

            public String getAcceptTime() {
                return acceptTime;
            }

            public void setAcceptTime(String acceptTime) {
                this.acceptTime = acceptTime;
            }

            public String getArrivalMerchantTime() {
                return arrivalMerchantTime;
            }

            public void setArrivalMerchantTime(String arrivalMerchantTime) {
                this.arrivalMerchantTime = arrivalMerchantTime;
            }

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

            public int getAgentId() {
                return agentId;
            }

            public void setAgentId(int agentId) {
                this.agentId = agentId;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public Object getTOrder() {
                return tOrder;
            }

            public void setTOrder(Object tOrder) {
                this.tOrder = tOrder;
            }

            public Object getWakeTime() {
                return wakeTime;
            }

            public void setWakeTime(Object wakeTime) {
                this.wakeTime = wakeTime;
            }

            public int getDeliverymanId() {
                return deliverymanId;
            }

            public void setDeliverymanId(int deliverymanId) {
                this.deliverymanId = deliverymanId;
            }

            public DeliverymanEntity getDeliveryman() {
                return deliveryman;
            }

            public void setDeliveryman(DeliverymanEntity deliveryman) {
                this.deliveryman = deliveryman;
            }

            public String getDispatchTime() {
                return dispatchTime;
            }

            public void setDispatchTime(String dispatchTime) {
                this.dispatchTime = dispatchTime;
            }

            public String getProcessBy() {
                return processBy;
            }

            public void setProcessBy(String processBy) {
                this.processBy = processBy;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getDeliveryDone() {
                return deliveryDone;
            }

            public void setDeliveryDone(int deliveryDone) {
                this.deliveryDone = deliveryDone;
            }

            public static class DeliverymanEntity extends Entity {
                private int id;
                private Object createTime;
                private Object modifyTime;
                private int agentId;
                private Object token;
                private String mobile;
                private String name;
                private String headerImg;
                private Object pwd;
                private Object ip;
                private Object client;
                private Object clientId;
                private Object apnsProduction;
                private Object app;
                private Object imei;
                private Object clientVersion;
                private int hasDel;
                private BigDecimal longitude;
                private BigDecimal latitude;
                private Object geohash;
                private int isOnline;
                private int waitAcceptNum;
                private int hasAcceptedNum;
                private int arrivalMerchantNum;
                private int deliveringNum;
                private int doneNum;
                private int cancelNum;

                public String getHeaderImg() {
                    return headerImg;
                }

                public void setHeaderImg(String headerImg) {
                    this.headerImg = headerImg;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public Object getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(Object createTime) {
                    this.createTime = createTime;
                }

                public Object getModifyTime() {
                    return modifyTime;
                }

                public void setModifyTime(Object modifyTime) {
                    this.modifyTime = modifyTime;
                }

                public int getAgentId() {
                    return agentId;
                }

                public void setAgentId(int agentId) {
                    this.agentId = agentId;
                }

                public Object getToken() {
                    return token;
                }

                public void setToken(Object token) {
                    this.token = token;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Object getPwd() {
                    return pwd;
                }

                public void setPwd(Object pwd) {
                    this.pwd = pwd;
                }

                public Object getIp() {
                    return ip;
                }

                public void setIp(Object ip) {
                    this.ip = ip;
                }

                public Object getClient() {
                    return client;
                }

                public void setClient(Object client) {
                    this.client = client;
                }

                public Object getClientId() {
                    return clientId;
                }

                public void setClientId(Object clientId) {
                    this.clientId = clientId;
                }

                public Object getApnsProduction() {
                    return apnsProduction;
                }

                public void setApnsProduction(Object apnsProduction) {
                    this.apnsProduction = apnsProduction;
                }

                public Object getApp() {
                    return app;
                }

                public void setApp(Object app) {
                    this.app = app;
                }

                public Object getImei() {
                    return imei;
                }

                public void setImei(Object imei) {
                    this.imei = imei;
                }

                public Object getClientVersion() {
                    return clientVersion;
                }

                public void setClientVersion(Object clientVersion) {
                    this.clientVersion = clientVersion;
                }

                public int getHasDel() {
                    return hasDel;
                }

                public void setHasDel(int hasDel) {
                    this.hasDel = hasDel;
                }

                public BigDecimal getLongitude() {
                    return longitude;
                }

                public void setLongitude(BigDecimal longitude) {
                    this.longitude = longitude;
                }

                public BigDecimal getLatitude() {
                    return latitude;
                }

                public void setLatitude(BigDecimal latitude) {
                    this.latitude = latitude;
                }

                public Object getGeohash() {
                    return geohash;
                }

                public void setGeohash(Object geohash) {
                    this.geohash = geohash;
                }

                public int getIsOnline() {
                    return isOnline;
                }

                public void setIsOnline(int isOnline) {
                    this.isOnline = isOnline;
                }

                public int getWaitAcceptNum() {
                    return waitAcceptNum;
                }

                public void setWaitAcceptNum(int waitAcceptNum) {
                    this.waitAcceptNum = waitAcceptNum;
                }

                public int getHasAcceptedNum() {
                    return hasAcceptedNum;
                }

                public void setHasAcceptedNum(int hasAcceptedNum) {
                    this.hasAcceptedNum = hasAcceptedNum;
                }

                public int getArrivalMerchantNum() {
                    return arrivalMerchantNum;
                }

                public void setArrivalMerchantNum(int arrivalMerchantNum) {
                    this.arrivalMerchantNum = arrivalMerchantNum;
                }

                public int getDeliveringNum() {
                    return deliveringNum;
                }

                public void setDeliveringNum(int deliveringNum) {
                    this.deliveringNum = deliveringNum;
                }

                public int getDoneNum() {
                    return doneNum;
                }

                public void setDoneNum(int doneNum) {
                    this.doneNum = doneNum;
                }

                public int getCancelNum() {
                    return cancelNum;
                }

                public void setCancelNum(int cancelNum) {
                    this.cancelNum = cancelNum;
                }
            }
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
            private String attributes;

            public String getAttributes() {
                return attributes;
            }

            public void setAttributes(String attributes) {
                this.attributes = attributes;
            }

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

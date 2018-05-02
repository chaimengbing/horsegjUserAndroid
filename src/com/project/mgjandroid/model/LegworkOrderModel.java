package com.project.mgjandroid.model;



public class LegworkOrderModel extends Entity{


    /**
     * code : 0
     * uuid : 867451020506330
     * value : {"settleState":0,"defDistance":1,"imgUrls":"","type":9,"shipperMobile":"","agentRateAmt":0,"shipperType":2,"setoutTime":null,"modifyTime":"2018-03-29 11:23:17","hasTakeFinishTime":null,"userAddressId":234,"hasDel":0,"userMobile":"13552741085","orderDoneTime":null,"id":"1803290000169925","paymentState":0,"childType":0,"servePrice":22,"baseCharge":10,"shipperName":"","expectArrivalTime":"1","defCommentsTime":null,"userImei":"2574562353254532","shipperGender":"","shipperAddress":"北京市海淀区知春路甲48-1号盈都大厦","transactionId":null,"userAddress":"北京市朝阳区","userLatitude":39.91443444995251,"orderCancelTime":null,"shipperHouseNumber":"","addCharge":1,"acceptedFinishTime":null,"status":1,"paymentExpireTime":"2018-03-29 11:38:17","agentId":3,"hasComments":0,"hasPayed":0,"totalPrice":22,"goodsEstimatePrice":11,"description":"早餐","shipperLatitude":39.98077004133814,"remark":"","deliverymanId":null,"paymentType":1,"commissionJson":"{\"commissionJson\":\"\",\"agentAccountId\":3,\"commissionRule\":\"\"}","cancelReason":null,"userHouseNumber":"国贸-地铁站","shipperAddressId":0,"userLongitude":116.46751918037641,"partnerAgentRateAmt":0,"userName":"果果","userId":92,"sysRateAmt":0,"paymentFinishTime":null,"shipperLongitude":116.3453855632398,"createTime":"2018-03-29 11:23:17","cancelSource":null,"userGender":"女士","deliveryTaskId":null,"provinceAgentRateAmt":0}
     * success : true
     * servertime : 2018-03-29 11:23:17
     */

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
        /**
         * settleState : 0
         * defDistance : 1.0
         * imgUrls :
         * type : 9
         * shipperMobile :
         * agentRateAmt : 0
         * shipperType : 2
         * setoutTime : null
         * modifyTime : 2018-03-29 11:23:17
         * hasTakeFinishTime : null
         * userAddressId : 234
         * hasDel : 0
         * userMobile : 13552741085
         * orderDoneTime : null
         * id : 1803290000169925
         * paymentState : 0
         * childType : 0
         * servePrice : 22
         * baseCharge : 10.0
         * shipperName :
         * expectArrivalTime : 1
         * defCommentsTime : null
         * userImei : 2574562353254532
         * shipperGender :
         * shipperAddress : 北京市海淀区知春路甲48-1号盈都大厦
         * transactionId : null
         * userAddress : 北京市朝阳区
         * userLatitude : 39.91443444995251
         * orderCancelTime : null
         * shipperHouseNumber :
         * addCharge : 1.0
         * acceptedFinishTime : null
         * status : 1
         * paymentExpireTime : 2018-03-29 11:38:17
         * agentId : 3
         * hasComments : 0
         * hasPayed : 0
         * totalPrice : 22
         * goodsEstimatePrice : 11
         * description : 早餐
         * shipperLatitude : 39.98077004133814
         * remark :
         * deliverymanId : null
         * paymentType : 1
         * commissionJson : {"commissionJson":"","agentAccountId":3,"commissionRule":""}
         * cancelReason : null
         * userHouseNumber : 国贸-地铁站
         * shipperAddressId : 0
         * userLongitude : 116.46751918037641
         * partnerAgentRateAmt : 0
         * userName : 果果
         * userId : 92
         * sysRateAmt : 0
         * paymentFinishTime : null
         * shipperLongitude : 116.3453855632398
         * createTime : 2018-03-29 11:23:17
         * cancelSource : null
         * userGender : 女士
         * deliveryTaskId : null
         * provinceAgentRateAmt : 0
         */

        private int settleState;
        private double defDistance;
        private String imgUrls;
        private int type;
        private String shipperMobile;
        private int agentRateAmt;
        private int shipperType;
        private Object setoutTime;
        private String modifyTime;
        private Object hasTakeFinishTime;
        private int userAddressId;
        private int hasDel;
        private String userMobile;
        private Object orderDoneTime;
        private String id;
        private int paymentState;
        private int childType;
        private int servePrice;
        private double baseCharge;
        private String shipperName;
        private String expectArrivalTime;
        private Object defCommentsTime;
        private String userImei;
        private String shipperGender;
        private String shipperAddress;
        private Object transactionId;
        private String userAddress;
        private double userLatitude;
        private Object orderCancelTime;
        private String shipperHouseNumber;
        private double addCharge;
        private Object acceptedFinishTime;
        private int status;
        private String paymentExpireTime;
        private int agentId;
        private int hasComments;
        private int hasPayed;
        private int totalPrice;
        private int goodsEstimatePrice;
        private String description;
        private double shipperLatitude;
        private String remark;
        private Object deliverymanId;
        private int paymentType;
        private String commissionJson;
        private Object cancelReason;
        private String userHouseNumber;
        private int shipperAddressId;
        private double userLongitude;
        private int partnerAgentRateAmt;
        private String userName;
        private int userId;
        private int sysRateAmt;
        private Object paymentFinishTime;
        private double shipperLongitude;
        private String createTime;
        private Object cancelSource;
        private String userGender;
        private Object deliveryTaskId;
        private int provinceAgentRateAmt;

        public int getSettleState() {
            return settleState;
        }

        public void setSettleState(int settleState) {
            this.settleState = settleState;
        }

        public double getDefDistance() {
            return defDistance;
        }

        public void setDefDistance(double defDistance) {
            this.defDistance = defDistance;
        }

        public String getImgUrls() {
            return imgUrls;
        }

        public void setImgUrls(String imgUrls) {
            this.imgUrls = imgUrls;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getShipperMobile() {
            return shipperMobile;
        }

        public void setShipperMobile(String shipperMobile) {
            this.shipperMobile = shipperMobile;
        }

        public int getAgentRateAmt() {
            return agentRateAmt;
        }

        public void setAgentRateAmt(int agentRateAmt) {
            this.agentRateAmt = agentRateAmt;
        }

        public int getShipperType() {
            return shipperType;
        }

        public void setShipperType(int shipperType) {
            this.shipperType = shipperType;
        }

        public Object getSetoutTime() {
            return setoutTime;
        }

        public void setSetoutTime(Object setoutTime) {
            this.setoutTime = setoutTime;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public Object getHasTakeFinishTime() {
            return hasTakeFinishTime;
        }

        public void setHasTakeFinishTime(Object hasTakeFinishTime) {
            this.hasTakeFinishTime = hasTakeFinishTime;
        }

        public int getUserAddressId() {
            return userAddressId;
        }

        public void setUserAddressId(int userAddressId) {
            this.userAddressId = userAddressId;
        }

        public int getHasDel() {
            return hasDel;
        }

        public void setHasDel(int hasDel) {
            this.hasDel = hasDel;
        }

        public String getUserMobile() {
            return userMobile;
        }

        public void setUserMobile(String userMobile) {
            this.userMobile = userMobile;
        }

        public Object getOrderDoneTime() {
            return orderDoneTime;
        }

        public void setOrderDoneTime(Object orderDoneTime) {
            this.orderDoneTime = orderDoneTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getPaymentState() {
            return paymentState;
        }

        public void setPaymentState(int paymentState) {
            this.paymentState = paymentState;
        }

        public int getChildType() {
            return childType;
        }

        public void setChildType(int childType) {
            this.childType = childType;
        }

        public int getServePrice() {
            return servePrice;
        }

        public void setServePrice(int servePrice) {
            this.servePrice = servePrice;
        }

        public double getBaseCharge() {
            return baseCharge;
        }

        public void setBaseCharge(double baseCharge) {
            this.baseCharge = baseCharge;
        }

        public String getShipperName() {
            return shipperName;
        }

        public void setShipperName(String shipperName) {
            this.shipperName = shipperName;
        }

        public String getExpectArrivalTime() {
            return expectArrivalTime;
        }

        public void setExpectArrivalTime(String expectArrivalTime) {
            this.expectArrivalTime = expectArrivalTime;
        }

        public Object getDefCommentsTime() {
            return defCommentsTime;
        }

        public void setDefCommentsTime(Object defCommentsTime) {
            this.defCommentsTime = defCommentsTime;
        }

        public String getUserImei() {
            return userImei;
        }

        public void setUserImei(String userImei) {
            this.userImei = userImei;
        }

        public String getShipperGender() {
            return shipperGender;
        }

        public void setShipperGender(String shipperGender) {
            this.shipperGender = shipperGender;
        }

        public String getShipperAddress() {
            return shipperAddress;
        }

        public void setShipperAddress(String shipperAddress) {
            this.shipperAddress = shipperAddress;
        }

        public Object getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(Object transactionId) {
            this.transactionId = transactionId;
        }

        public String getUserAddress() {
            return userAddress;
        }

        public void setUserAddress(String userAddress) {
            this.userAddress = userAddress;
        }

        public double getUserLatitude() {
            return userLatitude;
        }

        public void setUserLatitude(double userLatitude) {
            this.userLatitude = userLatitude;
        }

        public Object getOrderCancelTime() {
            return orderCancelTime;
        }

        public void setOrderCancelTime(Object orderCancelTime) {
            this.orderCancelTime = orderCancelTime;
        }

        public String getShipperHouseNumber() {
            return shipperHouseNumber;
        }

        public void setShipperHouseNumber(String shipperHouseNumber) {
            this.shipperHouseNumber = shipperHouseNumber;
        }

        public double getAddCharge() {
            return addCharge;
        }

        public void setAddCharge(double addCharge) {
            this.addCharge = addCharge;
        }

        public Object getAcceptedFinishTime() {
            return acceptedFinishTime;
        }

        public void setAcceptedFinishTime(Object acceptedFinishTime) {
            this.acceptedFinishTime = acceptedFinishTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPaymentExpireTime() {
            return paymentExpireTime;
        }

        public void setPaymentExpireTime(String paymentExpireTime) {
            this.paymentExpireTime = paymentExpireTime;
        }

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }

        public int getHasComments() {
            return hasComments;
        }

        public void setHasComments(int hasComments) {
            this.hasComments = hasComments;
        }

        public int getHasPayed() {
            return hasPayed;
        }

        public void setHasPayed(int hasPayed) {
            this.hasPayed = hasPayed;
        }

        public int getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
        }

        public int getGoodsEstimatePrice() {
            return goodsEstimatePrice;
        }

        public void setGoodsEstimatePrice(int goodsEstimatePrice) {
            this.goodsEstimatePrice = goodsEstimatePrice;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getShipperLatitude() {
            return shipperLatitude;
        }

        public void setShipperLatitude(double shipperLatitude) {
            this.shipperLatitude = shipperLatitude;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Object getDeliverymanId() {
            return deliverymanId;
        }

        public void setDeliverymanId(Object deliverymanId) {
            this.deliverymanId = deliverymanId;
        }

        public int getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(int paymentType) {
            this.paymentType = paymentType;
        }

        public String getCommissionJson() {
            return commissionJson;
        }

        public void setCommissionJson(String commissionJson) {
            this.commissionJson = commissionJson;
        }

        public Object getCancelReason() {
            return cancelReason;
        }

        public void setCancelReason(Object cancelReason) {
            this.cancelReason = cancelReason;
        }

        public String getUserHouseNumber() {
            return userHouseNumber;
        }

        public void setUserHouseNumber(String userHouseNumber) {
            this.userHouseNumber = userHouseNumber;
        }

        public int getShipperAddressId() {
            return shipperAddressId;
        }

        public void setShipperAddressId(int shipperAddressId) {
            this.shipperAddressId = shipperAddressId;
        }

        public double getUserLongitude() {
            return userLongitude;
        }

        public void setUserLongitude(double userLongitude) {
            this.userLongitude = userLongitude;
        }

        public int getPartnerAgentRateAmt() {
            return partnerAgentRateAmt;
        }

        public void setPartnerAgentRateAmt(int partnerAgentRateAmt) {
            this.partnerAgentRateAmt = partnerAgentRateAmt;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getSysRateAmt() {
            return sysRateAmt;
        }

        public void setSysRateAmt(int sysRateAmt) {
            this.sysRateAmt = sysRateAmt;
        }

        public Object getPaymentFinishTime() {
            return paymentFinishTime;
        }

        public void setPaymentFinishTime(Object paymentFinishTime) {
            this.paymentFinishTime = paymentFinishTime;
        }

        public double getShipperLongitude() {
            return shipperLongitude;
        }

        public void setShipperLongitude(double shipperLongitude) {
            this.shipperLongitude = shipperLongitude;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getCancelSource() {
            return cancelSource;
        }

        public void setCancelSource(Object cancelSource) {
            this.cancelSource = cancelSource;
        }

        public String getUserGender() {
            return userGender;
        }

        public void setUserGender(String userGender) {
            this.userGender = userGender;
        }

        public Object getDeliveryTaskId() {
            return deliveryTaskId;
        }

        public void setDeliveryTaskId(Object deliveryTaskId) {
            this.deliveryTaskId = deliveryTaskId;
        }

        public int getProvinceAgentRateAmt() {
            return provinceAgentRateAmt;
        }

        public void setProvinceAgentRateAmt(int provinceAgentRateAmt) {
            this.provinceAgentRateAmt = provinceAgentRateAmt;
        }
    }
}

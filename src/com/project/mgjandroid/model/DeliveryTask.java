package com.project.mgjandroid.model;

public class DeliveryTask extends Entity{

    private int id;
    private String createTime;
    private String modifyTime;
    private int agentId;
    private String orderId;
    private Object tOrder;
    private Object wakeTime;
    private int isSleep;
    private int deliverymanId;
    private DeliverymanBean deliveryman;
    private String sendOrderTime;
    private String orderConfirmTime;
    private String dispatchTime;
    private String acceptTime;
    private String arrivalMerchantTime;
    private String deliveryDoneTime;
    private int deliverySpentTime;
    private Object sysAutomaticDoneTime;
    private String processBy;
    private int status;
    private int deliveryDone;
    private Object imgs;
    private double merchantAmt;
    private double realMerchantAmt;
    private double userAmt;
    private double realUserAmt;
    private double turninAmt;
    private double realTurninAmt;
    private int settleStatus;
    private int hasReceiptImg;
    private Object receiptImg;
    private int merchantOrderCounter;
    private int isReturnOrChange;
    private int returnOrChangeType;
    private int type;
    private int legWorkType;
    private Object legWorkOrder;
    private int returnOrChangeCount;
    private int orderType;
    private Object hasTransform;
    private Object expectArrivalTime;
    private Object deliverymanTransform;

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

    public int getIsSleep() {
        return isSleep;
    }

    public void setIsSleep(int isSleep) {
        this.isSleep = isSleep;
    }

    public int getDeliverymanId() {
        return deliverymanId;
    }

    public void setDeliverymanId(int deliverymanId) {
        this.deliverymanId = deliverymanId;
    }

    public DeliverymanBean getDeliveryman() {
        return deliveryman;
    }

    public void setDeliveryman(DeliverymanBean deliveryman) {
        this.deliveryman = deliveryman;
    }

    public String getSendOrderTime() {
        return sendOrderTime;
    }

    public void setSendOrderTime(String sendOrderTime) {
        this.sendOrderTime = sendOrderTime;
    }

    public String getOrderConfirmTime() {
        return orderConfirmTime;
    }

    public void setOrderConfirmTime(String orderConfirmTime) {
        this.orderConfirmTime = orderConfirmTime;
    }

    public String getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(String dispatchTime) {
        this.dispatchTime = dispatchTime;
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

    public String getDeliveryDoneTime() {
        return deliveryDoneTime;
    }

    public void setDeliveryDoneTime(String deliveryDoneTime) {
        this.deliveryDoneTime = deliveryDoneTime;
    }

    public int getDeliverySpentTime() {
        return deliverySpentTime;
    }

    public void setDeliverySpentTime(int deliverySpentTime) {
        this.deliverySpentTime = deliverySpentTime;
    }

    public Object getSysAutomaticDoneTime() {
        return sysAutomaticDoneTime;
    }

    public void setSysAutomaticDoneTime(Object sysAutomaticDoneTime) {
        this.sysAutomaticDoneTime = sysAutomaticDoneTime;
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

    public Object getImgs() {
        return imgs;
    }

    public void setImgs(Object imgs) {
        this.imgs = imgs;
    }

    public double getMerchantAmt() {
        return merchantAmt;
    }

    public void setMerchantAmt(double merchantAmt) {
        this.merchantAmt = merchantAmt;
    }

    public double getRealMerchantAmt() {
        return realMerchantAmt;
    }

    public void setRealMerchantAmt(double realMerchantAmt) {
        this.realMerchantAmt = realMerchantAmt;
    }

    public double getUserAmt() {
        return userAmt;
    }

    public void setUserAmt(double userAmt) {
        this.userAmt = userAmt;
    }

    public double getRealUserAmt() {
        return realUserAmt;
    }

    public void setRealUserAmt(double realUserAmt) {
        this.realUserAmt = realUserAmt;
    }

    public double getTurninAmt() {
        return turninAmt;
    }

    public void setTurninAmt(double turninAmt) {
        this.turninAmt = turninAmt;
    }

    public double getRealTurninAmt() {
        return realTurninAmt;
    }

    public void setRealTurninAmt(double realTurninAmt) {
        this.realTurninAmt = realTurninAmt;
    }

    public int getSettleStatus() {
        return settleStatus;
    }

    public void setSettleStatus(int settleStatus) {
        this.settleStatus = settleStatus;
    }

    public int getHasReceiptImg() {
        return hasReceiptImg;
    }

    public void setHasReceiptImg(int hasReceiptImg) {
        this.hasReceiptImg = hasReceiptImg;
    }

    public Object getReceiptImg() {
        return receiptImg;
    }

    public void setReceiptImg(Object receiptImg) {
        this.receiptImg = receiptImg;
    }

    public int getMerchantOrderCounter() {
        return merchantOrderCounter;
    }

    public void setMerchantOrderCounter(int merchantOrderCounter) {
        this.merchantOrderCounter = merchantOrderCounter;
    }

    public int getIsReturnOrChange() {
        return isReturnOrChange;
    }

    public void setIsReturnOrChange(int isReturnOrChange) {
        this.isReturnOrChange = isReturnOrChange;
    }

    public int getReturnOrChangeType() {
        return returnOrChangeType;
    }

    public void setReturnOrChangeType(int returnOrChangeType) {
        this.returnOrChangeType = returnOrChangeType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLegWorkType() {
        return legWorkType;
    }

    public void setLegWorkType(int legWorkType) {
        this.legWorkType = legWorkType;
    }

    public Object getLegWorkOrder() {
        return legWorkOrder;
    }

    public void setLegWorkOrder(Object legWorkOrder) {
        this.legWorkOrder = legWorkOrder;
    }

    public int getReturnOrChangeCount() {
        return returnOrChangeCount;
    }

    public void setReturnOrChangeCount(int returnOrChangeCount) {
        this.returnOrChangeCount = returnOrChangeCount;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public Object getHasTransform() {
        return hasTransform;
    }

    public void setHasTransform(Object hasTransform) {
        this.hasTransform = hasTransform;
    }

    public Object getExpectArrivalTime() {
        return expectArrivalTime;
    }

    public void setExpectArrivalTime(Object expectArrivalTime) {
        this.expectArrivalTime = expectArrivalTime;
    }

    public Object getDeliverymanTransform() {
        return deliverymanTransform;
    }

    public void setDeliverymanTransform(Object deliverymanTransform) {
        this.deliverymanTransform = deliverymanTransform;
    }

    public static class DeliverymanBean extends Entity{
        /**
         * id : 73
         * createTime : null
         * modifyTime : 2018-11-07 14:13:25
         * agentId : 3
         * token : null
         * mobile : 15955218936
         * name : 唐玉
         * pwd :
         * mac : null
         * brand : null
         * model : null
         * imei : null
         * ip : null
         * client : null
         * clientId : null
         * apnsProduction : null
         * app : null
         * clientVersion : null
         * hasDel : 0
         * longitude : 116.346242
         * latitude : 39.99707
         * geohash : null
         * isOnline : 1
         * legWorkIsOnline : null
         * waitAcceptNum : 3
         * hasAcceptedNum : -1
         * arrivalMerchantNum : 0
         * deliveringNum : 0
         * doneNum : 63
         * cancelNum : 4
         * legWorkWaitAcceptNum : 0
         * legWorkHasAcceptedNum : 0
         * legWorkArrivalMerchantNum : 0
         * legWorkDeliveringNum : 0
         * legWorkDoneNum : 18
         * legWorkCancelNum : 5
         * headerImg : http://7xu5hi.com1.z0.glb.clouddn.com/201611091634369459534.png
         * isTakeawayDeliveryman : 1
         * isPaoTuiDeliveryman : 1
         * type : 1
         * sendOrderCountMax : null
         * rejectTimes : null
         * lockStatus : 0
         * deliveryTaskList : null
         * takeFoodDistance : 0.0
         * orderStatus : null
         * deliverymanScore : 3.0
         * deliverymanGoodScore : 0.0
         * deliverymanPunctualityScore : 100.0
         * deliverymanCommentNum : 1
         * deliverymanCommentGoodNum : 0
         * deliverymanAvgTime : 34.46
         * deliverymanTimeoutNum : 0
         * deliverymanImpress : {"1":0,"2":1,"3":0,"4":0,"5":0,"6":0,"7":0,"8":0,"9":0,"10":0,"11":0,"12":0}
         */

        private int id;
        private Object createTime;
        private String modifyTime;
        private int agentId;
        private Object token;
        private String mobile;
        private String name;
        private String pwd;
        private Object mac;
        private Object brand;
        private Object model;
        private Object imei;
        private Object ip;
        private Object client;
        private Object clientId;
        private Object apnsProduction;
        private Object app;
        private Object clientVersion;
        private int hasDel;
        private double longitude;
        private double latitude;
        private Object geohash;
        private int isOnline;
        private Object legWorkIsOnline;
        private int waitAcceptNum;
        private int hasAcceptedNum;
        private int arrivalMerchantNum;
        private int deliveringNum;
        private int doneNum;
        private int cancelNum;
        private int legWorkWaitAcceptNum;
        private int legWorkHasAcceptedNum;
        private int legWorkArrivalMerchantNum;
        private int legWorkDeliveringNum;
        private int legWorkDoneNum;
        private int legWorkCancelNum;
        private String headerImg;
        private int isTakeawayDeliveryman;
        private int isPaoTuiDeliveryman;
        private int type;
        private Object sendOrderCountMax;
        private Object rejectTimes;
        private int lockStatus;
        private Object deliveryTaskList;
        private double takeFoodDistance;
        private Object orderStatus;
        private double deliverymanScore;
        private double deliverymanGoodScore;
        private double deliverymanPunctualityScore;
        private int deliverymanCommentNum;
        private int deliverymanCommentGoodNum;
        private double deliverymanAvgTime;
        private int deliverymanTimeoutNum;
        private String deliverymanImpress;

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

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public Object getMac() {
            return mac;
        }

        public void setMac(Object mac) {
            this.mac = mac;
        }

        public Object getBrand() {
            return brand;
        }

        public void setBrand(Object brand) {
            this.brand = brand;
        }

        public Object getModel() {
            return model;
        }

        public void setModel(Object model) {
            this.model = model;
        }

        public Object getImei() {
            return imei;
        }

        public void setImei(Object imei) {
            this.imei = imei;
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

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
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

        public Object getLegWorkIsOnline() {
            return legWorkIsOnline;
        }

        public void setLegWorkIsOnline(Object legWorkIsOnline) {
            this.legWorkIsOnline = legWorkIsOnline;
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

        public int getLegWorkWaitAcceptNum() {
            return legWorkWaitAcceptNum;
        }

        public void setLegWorkWaitAcceptNum(int legWorkWaitAcceptNum) {
            this.legWorkWaitAcceptNum = legWorkWaitAcceptNum;
        }

        public int getLegWorkHasAcceptedNum() {
            return legWorkHasAcceptedNum;
        }

        public void setLegWorkHasAcceptedNum(int legWorkHasAcceptedNum) {
            this.legWorkHasAcceptedNum = legWorkHasAcceptedNum;
        }

        public int getLegWorkArrivalMerchantNum() {
            return legWorkArrivalMerchantNum;
        }

        public void setLegWorkArrivalMerchantNum(int legWorkArrivalMerchantNum) {
            this.legWorkArrivalMerchantNum = legWorkArrivalMerchantNum;
        }

        public int getLegWorkDeliveringNum() {
            return legWorkDeliveringNum;
        }

        public void setLegWorkDeliveringNum(int legWorkDeliveringNum) {
            this.legWorkDeliveringNum = legWorkDeliveringNum;
        }

        public int getLegWorkDoneNum() {
            return legWorkDoneNum;
        }

        public void setLegWorkDoneNum(int legWorkDoneNum) {
            this.legWorkDoneNum = legWorkDoneNum;
        }

        public int getLegWorkCancelNum() {
            return legWorkCancelNum;
        }

        public void setLegWorkCancelNum(int legWorkCancelNum) {
            this.legWorkCancelNum = legWorkCancelNum;
        }

        public String getHeaderImg() {
            return headerImg;
        }

        public void setHeaderImg(String headerImg) {
            this.headerImg = headerImg;
        }

        public int getIsTakeawayDeliveryman() {
            return isTakeawayDeliveryman;
        }

        public void setIsTakeawayDeliveryman(int isTakeawayDeliveryman) {
            this.isTakeawayDeliveryman = isTakeawayDeliveryman;
        }

        public int getIsPaoTuiDeliveryman() {
            return isPaoTuiDeliveryman;
        }

        public void setIsPaoTuiDeliveryman(int isPaoTuiDeliveryman) {
            this.isPaoTuiDeliveryman = isPaoTuiDeliveryman;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Object getSendOrderCountMax() {
            return sendOrderCountMax;
        }

        public void setSendOrderCountMax(Object sendOrderCountMax) {
            this.sendOrderCountMax = sendOrderCountMax;
        }

        public Object getRejectTimes() {
            return rejectTimes;
        }

        public void setRejectTimes(Object rejectTimes) {
            this.rejectTimes = rejectTimes;
        }

        public int getLockStatus() {
            return lockStatus;
        }

        public void setLockStatus(int lockStatus) {
            this.lockStatus = lockStatus;
        }

        public Object getDeliveryTaskList() {
            return deliveryTaskList;
        }

        public void setDeliveryTaskList(Object deliveryTaskList) {
            this.deliveryTaskList = deliveryTaskList;
        }

        public double getTakeFoodDistance() {
            return takeFoodDistance;
        }

        public void setTakeFoodDistance(double takeFoodDistance) {
            this.takeFoodDistance = takeFoodDistance;
        }

        public Object getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(Object orderStatus) {
            this.orderStatus = orderStatus;
        }

        public double getDeliverymanScore() {
            return deliverymanScore;
        }

        public void setDeliverymanScore(double deliverymanScore) {
            this.deliverymanScore = deliverymanScore;
        }

        public double getDeliverymanGoodScore() {
            return deliverymanGoodScore;
        }

        public void setDeliverymanGoodScore(double deliverymanGoodScore) {
            this.deliverymanGoodScore = deliverymanGoodScore;
        }

        public double getDeliverymanPunctualityScore() {
            return deliverymanPunctualityScore;
        }

        public void setDeliverymanPunctualityScore(double deliverymanPunctualityScore) {
            this.deliverymanPunctualityScore = deliverymanPunctualityScore;
        }

        public int getDeliverymanCommentNum() {
            return deliverymanCommentNum;
        }

        public void setDeliverymanCommentNum(int deliverymanCommentNum) {
            this.deliverymanCommentNum = deliverymanCommentNum;
        }

        public int getDeliverymanCommentGoodNum() {
            return deliverymanCommentGoodNum;
        }

        public void setDeliverymanCommentGoodNum(int deliverymanCommentGoodNum) {
            this.deliverymanCommentGoodNum = deliverymanCommentGoodNum;
        }

        public double getDeliverymanAvgTime() {
            return deliverymanAvgTime;
        }

        public void setDeliverymanAvgTime(double deliverymanAvgTime) {
            this.deliverymanAvgTime = deliverymanAvgTime;
        }

        public int getDeliverymanTimeoutNum() {
            return deliverymanTimeoutNum;
        }

        public void setDeliverymanTimeoutNum(int deliverymanTimeoutNum) {
            this.deliverymanTimeoutNum = deliverymanTimeoutNum;
        }

        public String getDeliverymanImpress() {
            return deliverymanImpress;
        }

        public void setDeliverymanImpress(String deliverymanImpress) {
            this.deliverymanImpress = deliverymanImpress;
        }
    }
}

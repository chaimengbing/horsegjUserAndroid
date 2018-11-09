package com.project.mgjandroid.model;

import java.math.BigDecimal;
import java.util.List;

public class RiderInformation extends Entity{
    /**
     * code : 0
     * uuid : 865586022125258
     * value : {"id":8,"createTime":"2016-04-09 17:51:52","modifyTime":"2018-11-07 16:23:08","agentId":3,"token":"77bbe0457c2a46c785bb97875a9383dd","mobile":"15201117804","name":"2","pwd":"8cfb19894a74dabd4fb930bafe7a0dab0f29a3413f416d56e335579e15426936e9080342e12f8787","mac":"b4:0b:44:31:99:a1","brand":"SMARTISAN","model":"YQ603","imei":"867840020764404","ip":"43.227.254.30","client":"android","clientId":"13065ffa4e0be32ed86","apnsProduction":null,"app":"deliveryman","clientVersion":"1.3.1","hasDel":0,"longitude":116.346185,"latitude":39.997103,"geohash":"wx4ex928n87k","isOnline":1,"legWorkIsOnline":1,"waitAcceptNum":0,"hasAcceptedNum":0,"arrivalMerchantNum":0,"deliveringNum":0,"doneNum":464,"cancelNum":29,"legWorkWaitAcceptNum":0,"legWorkHasAcceptedNum":0,"legWorkArrivalMerchantNum":0,"legWorkDeliveringNum":0,"legWorkDoneNum":3,"legWorkCancelNum":0,"headerImg":"http://7xu5hi.com1.z0.glb.clouddn.com/201811061106014266165.jpg","isTakeawayDeliveryman":1,"isPaoTuiDeliveryman":1,"type":1,"sendOrderCountMax":7,"rejectTimes":0,"lockStatus":0,"deliveryTaskList":null,"takeFoodDistance":0,"orderStatus":null,"deliverymanScore":5,"deliverymanGoodScore":100,"deliverymanPunctualityScore":100,"deliverymanCommentNum":0,"deliverymanCommentGoodNum":0,"deliverymanAvgTime":35,"deliverymanTimeoutNum":0,"deliverymanImpress":null}
     * success : true
     * servertime : 2018-11-07 16:51:31
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

    public static class ValueBean extends Entity{
        /** 代理商编号 */
        private Long agentId;
        /** 做为 用户注册或登录返回uuid做为登录标识。 */
        private String token;
        /** 手机号 */
        private String mobile;
        /** 姓名 */
        private String name;
        /** 密码 */
        private String pwd = "";
        /** 设备mac地址 */
        private String mac;
        /** 设备厂商 .ios的是apple。android的是厂商标识 */
        private String brand;
        /** 设备型号 */
        private String model;
        /** 设备IMEI或apple设备的uuid */
        private String imei;
        /** 登录时IP地址 */
        private String ip;
        /** 客户端名称apple或android */
        private String client;
        /** 推送编号 */
        private String clientId;
        /** apple设备。正式环境true,开发环境false */
        private Boolean apnsProduction;
        /** 项目名称，也可称为包名，推送时有可能要区分不同的包进行推送 */
        private String app;

        private String clientVersion;
        /** 是否已删除 */
        private int hasDel;
        /** 经度 */
        private Double longitude;
        /** 维度 */
        private Double latitude;
        /** GeoHash */
        private String geohash;
        /** 是否在线 */
        private int isOnline;
        /** 跑腿是否在线 */
        private Integer legWorkIsOnline;
        /** 待确认订单个数 */
        private int waitAcceptNum;
        /** 已确认订单个数 */
        private int hasAcceptedNum;
        /** 待取餐订单个数 */
        private int arrivalMerchantNum;
        /** 配送中订单个数 */
        private int deliveringNum;
        /** 已配送订单个数 */
        private int doneNum;
        /** 取消订单个数 */
        private int cancelNum;
        /** 跑腿待确认订单个数 */
        private int legWorkWaitAcceptNum;
        /** 跑腿已确认订单个数 */
        private int legWorkHasAcceptedNum;
        /** 跑腿待取餐订单个数 */
        private int legWorkArrivalMerchantNum;
        /** 跑腿配送中订单个数 */
        private int legWorkDeliveringNum;
        /** 跑腿已配送订单个数 */
        private int legWorkDoneNum;
        /** 跑腿取消订单个数 */
        private int legWorkCancelNum;

        /** 头像 */
        private String headerImg = "";
        /** 是否为外卖骑手 0:否;1:是;*/
        private Integer isTakeawayDeliveryman;
        /** 是否为跑腿骑手 0:否;1:是;*/
        private Integer isPaoTuiDeliveryman;
        /** 骑手类型 1:全职;2:兼职;*/
        private Integer type;
        /**骑手派送订单上限数*/
        private Integer sendOrderCountMax;
        /**骑手当天拒单次数*/
        private Integer rejectTimes;
        /** 骑手派送锁定状态 0:正常 1:被锁定*/
        private Integer lockStatus=0;
        private List<DeliveryTask> deliveryTaskList;
        /**用于自动派单取餐距离做比较*/
        private double takeFoodDistance;
        /**订单状态*/
        private Integer orderStatus;
        /******************骑手评价start********************/

        /** 骑手总体评分 **/
        private BigDecimal deliverymanScore = new BigDecimal(5.0);
        /** 骑手好评率 **/
        private BigDecimal deliverymanGoodScore  = new BigDecimal("100");
        /** 骑手准时率 **/
        private BigDecimal deliverymanPunctualityScore  = new BigDecimal("100");
        /** 骑手评论数量 */
        private Integer deliverymanCommentNum = 0;
        /** 骑手获得好评次数 */
        private Integer deliverymanCommentGoodNum = 0;
        /** 骑手平均配送时长 */
        private BigDecimal deliverymanAvgTime = new BigDecimal("35");
        /** 骑手超时订单数 */
        private Integer deliverymanTimeoutNum = 0;
        /** 骑手印象，json串
         * 1：提前点送达
         * 2：服务态度差
         * 3：餐品翻撒
         * 4：送餐慢
         * 5：着装脏乱
         * 6：沟通困难
         * 7：穿戴工装
         * 8：风雨无阻
         * 9：快速准时
         * 10：仪容整洁
         * 11：货品完好
         * 12：礼貌热情
         * */
        private String deliverymanImpress ;

        public Long getAgentId() {
            return agentId;
        }

        public void setAgentId(Long agentId) {
            this.agentId = agentId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
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

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public Boolean getApnsProduction() {
            return apnsProduction;
        }

        public void setApnsProduction(Boolean apnsProduction) {
            this.apnsProduction = apnsProduction;
        }

        public String getApp() {
            return app;
        }

        public void setApp(String app) {
            this.app = app;
        }

        public String getClientVersion() {
            return clientVersion;
        }

        public void setClientVersion(String clientVersion) {
            this.clientVersion = clientVersion;
        }

        public int getHasDel() {
            return hasDel;
        }

        public void setHasDel(int hasDel) {
            this.hasDel = hasDel;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public String getGeohash() {
            return geohash;
        }

        public void setGeohash(String geohash) {
            this.geohash = geohash;
        }

        public int getIsOnline() {
            return isOnline;
        }

        public void setIsOnline(int isOnline) {
            this.isOnline = isOnline;
        }

        public Integer getLegWorkIsOnline() {
            return legWorkIsOnline;
        }

        public void setLegWorkIsOnline(Integer legWorkIsOnline) {
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

        public Integer getIsTakeawayDeliveryman() {
            return isTakeawayDeliveryman;
        }

        public void setIsTakeawayDeliveryman(Integer isTakeawayDeliveryman) {
            this.isTakeawayDeliveryman = isTakeawayDeliveryman;
        }

        public Integer getIsPaoTuiDeliveryman() {
            return isPaoTuiDeliveryman;
        }

        public void setIsPaoTuiDeliveryman(Integer isPaoTuiDeliveryman) {
            this.isPaoTuiDeliveryman = isPaoTuiDeliveryman;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getSendOrderCountMax() {
            return sendOrderCountMax;
        }

        public void setSendOrderCountMax(Integer sendOrderCountMax) {
            this.sendOrderCountMax = sendOrderCountMax;
        }

        public Integer getRejectTimes() {
            return rejectTimes;
        }

        public void setRejectTimes(Integer rejectTimes) {
            this.rejectTimes = rejectTimes;
        }

        public Integer getLockStatus() {
            return lockStatus;
        }

        public void setLockStatus(Integer lockStatus) {
            this.lockStatus = lockStatus;
        }

        public List<DeliveryTask> getDeliveryTaskList() {
            return deliveryTaskList;
        }

        public void setDeliveryTaskList(List<DeliveryTask> deliveryTaskList) {
            this.deliveryTaskList = deliveryTaskList;
        }

        public double getTakeFoodDistance() {
            return takeFoodDistance;
        }

        public void setTakeFoodDistance(double takeFoodDistance) {
            this.takeFoodDistance = takeFoodDistance;
        }

        public Integer getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(Integer orderStatus) {
            this.orderStatus = orderStatus;
        }

        public BigDecimal getDeliverymanScore() {
            return deliverymanScore;
        }

        public void setDeliverymanScore(BigDecimal deliverymanScore) {
            this.deliverymanScore = deliverymanScore;
        }

        public BigDecimal getDeliverymanGoodScore() {
            return deliverymanGoodScore;
        }

        public void setDeliverymanGoodScore(BigDecimal deliverymanGoodScore) {
            this.deliverymanGoodScore = deliverymanGoodScore;
        }

        public BigDecimal getDeliverymanPunctualityScore() {
            return deliverymanPunctualityScore;
        }

        public void setDeliverymanPunctualityScore(BigDecimal deliverymanPunctualityScore) {
            this.deliverymanPunctualityScore = deliverymanPunctualityScore;
        }

        public Integer getDeliverymanCommentNum() {
            return deliverymanCommentNum;
        }

        public void setDeliverymanCommentNum(Integer deliverymanCommentNum) {
            this.deliverymanCommentNum = deliverymanCommentNum;
        }

        public Integer getDeliverymanCommentGoodNum() {
            return deliverymanCommentGoodNum;
        }

        public void setDeliverymanCommentGoodNum(Integer deliverymanCommentGoodNum) {
            this.deliverymanCommentGoodNum = deliverymanCommentGoodNum;
        }

        public BigDecimal getDeliverymanAvgTime() {
            return deliverymanAvgTime;
        }

        public void setDeliverymanAvgTime(BigDecimal deliverymanAvgTime) {
            this.deliverymanAvgTime = deliverymanAvgTime;
        }

        public Integer getDeliverymanTimeoutNum() {
            return deliverymanTimeoutNum;
        }

        public void setDeliverymanTimeoutNum(Integer deliverymanTimeoutNum) {
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

package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.Distance;
import com.project.mgjandroid.bean.SpecificTime;

import java.util.ArrayList;

/**
 * Created by SunXueLiang on 2018-03-26.
 */

public class LegworkOrderDetailsModel extends Entity {

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

        private String id;
        private String createTime;
        private String modifyTime;
        private int type;
        private int childType;
        private int settleState;
        private int agentId;
        private int deliverymanId;
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
        private double baseCharge;
        private double addCharge;
        private double defDistance;
        private String servePrice;
        private double hasPayed;
        private double totalPrice;
        private int status;
        private int hasDel;
        private int deliveryTaskId;
        private String imgUrls;
        private int hasComments;
        private String defCommentsTime;
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
        private String paymentFinishTime;
        private String acceptedFinishTime;
        private String hasTakeFinishTime;
        private String orderCancelTime;
        private String orderDoneTime;
        private String redBagDiscountTotalAmt;
        private String redBagJson;
        private int setoutTime;
        private AgentBean agent;
        private DeliveryTaskBean deliveryTask;
        private ShareRedBagInfo shareRedBagInfo;
        private ArrayList<Distance> stairServiceChargeRuleList;
        private ArrayList<SpecificTime> timeFrameServiceChargeList;

        public ArrayList<Distance> getStairServiceChargeRuleList() {
            return stairServiceChargeRuleList;
        }

        public void setStairServiceChargeRuleList(ArrayList<Distance> stairServiceChargeRuleList) {
            this.stairServiceChargeRuleList = stairServiceChargeRuleList;
        }

        public ArrayList<SpecificTime> getTimeFrameServiceChargeList() {
            return timeFrameServiceChargeList;
        }

        public void setTimeFrameServiceChargeList(ArrayList<SpecificTime> timeFrameServiceChargeList) {
            this.timeFrameServiceChargeList = timeFrameServiceChargeList;
        }

        public ShareRedBagInfo getShareRedBagInfo() {
            return shareRedBagInfo;
        }

        public void setShareRedBagInfo(ShareRedBagInfo shareRedBagInfo) {
            this.shareRedBagInfo = shareRedBagInfo;
        }

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

        public int getDeliverymanId() {
            return deliverymanId;
        }

        public void setDeliverymanId(int deliverymanId) {
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

        public String getServePrice() {
            return servePrice;
        }

        public void setServePrice(String servePrice) {
            this.servePrice = servePrice;
        }

        public double getBaseCharge() {
            return baseCharge;
        }

        public void setBaseCharge(double baseCharge) {
            this.baseCharge = baseCharge;
        }

        public double getAddCharge() {
            return addCharge;
        }

        public void setAddCharge(double addCharge) {
            this.addCharge = addCharge;
        }

        public double getDefDistance() {
            return defDistance;
        }

        public void setDefDistance(double defDistance) {
            this.defDistance = defDistance;
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

        public int getDeliveryTaskId() {
            return deliveryTaskId;
        }

        public void setDeliveryTaskId(int deliveryTaskId) {
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

        public String getDefCommentsTime() {
            return defCommentsTime;
        }

        public void setDefCommentsTime(String defCommentsTime) {
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

        public String getPaymentFinishTime() {
            return paymentFinishTime;
        }

        public void setPaymentFinishTime(String paymentFinishTime) {
            this.paymentFinishTime = paymentFinishTime;
        }

        public String getAcceptedFinishTime() {
            return acceptedFinishTime;
        }

        public void setAcceptedFinishTime(String acceptedFinishTime) {
            this.acceptedFinishTime = acceptedFinishTime;
        }

        public String getHasTakeFinishTime() {
            return hasTakeFinishTime;
        }

        public void setHasTakeFinishTime(String hasTakeFinishTime) {
            this.hasTakeFinishTime = hasTakeFinishTime;
        }

        public String getOrderCancelTime() {
            return orderCancelTime;
        }

        public void setOrderCancelTime(String orderCancelTime) {
            this.orderCancelTime = orderCancelTime;
        }

        public String getOrderDoneTime() {
            return orderDoneTime;
        }

        public void setOrderDoneTime(String orderDoneTime) {
            this.orderDoneTime = orderDoneTime;
        }

        public int getSetoutTime() {
            return setoutTime;
        }

        public void setSetoutTime(int setoutTime) {
            this.setoutTime = setoutTime;
        }

        public AgentBean getAgent() {
            return agent;
        }

        public void setAgent(AgentBean agent) {
            this.agent = agent;
        }

        public DeliveryTaskBean getDeliveryTask() {
            return deliveryTask;
        }

        public void setDeliveryTask(DeliveryTaskBean deliveryTask) {
            this.deliveryTask = deliveryTask;
        }

        public String getRedBagDiscountTotalAmt() {
            return redBagDiscountTotalAmt;
        }

        public void setRedBagDiscountTotalAmt(String redBagDiscountTotalAmt) {
            this.redBagDiscountTotalAmt = redBagDiscountTotalAmt;
        }

        public String getRedBagJson() {
            return redBagJson;
        }

        public void setRedBagJson(String redBagJson) {
            this.redBagJson = redBagJson;
        }

        public static class AgentBean {
            /**
             * id : 3
             * createTime : 2016-03-21 15:43:33
             * modifyTime : 2018-03-23 12:38:48
             * managementUserId : 3
             * createdById : null
             * modifiedById : 1
             * modifiedByName : null
             * name : test
             * loginName : test
             * mobile : 18701501121
             * phone : 1111111111
             * pwd : 0f1d1fe88e739b3476a26905aa1628c2b5fbf66a2229b6f9069c632f5a1dc2bac6a0d6b79150b7f0
             * parentId : 1
             * level : 1
             * agentType : 1
             * hasDel : 0
             * isAdmin : 0
             * province : 1
             * city : 2
             * district : 5
             * accountType : 0
             * bankName : 中国农业银行
             * bankCard : 6164844540400056401121
             * bankPerson : 何谐
             * sysRate : 10.0
             * currentAgentRate : 0.0
             * provinceAgentRate : 10.0
             * provinceAgentId : 37
             * partnerAgentId : null
             * partnerAgentName : null
             * marginAmt : 0.0
             * merchantAgent : null
             * agentAccount : null
             * discountAmt : null
             * address : null
             * provinceName : null
             * cityName : null
             * districtName : null
             * canDrawCashAmt : null
             * isDrawCash : null
             * balance : null
             * agentId : null
             * hasChild : null
             */

            private int id;
            private String createTime;
            private String modifyTime;
            private int managementUserId;
            private Object createdById;
            private int modifiedById;
            private Object modifiedByName;
            private String name;
            private String loginName;
            private String mobile;
            private String phone;
            private String pwd;
            private int parentId;
            private int level;
            private int agentType;
            private int hasDel;
            private int isAdmin;
            private int province;
            private int city;
            private int district;
            private int accountType;
            private String bankName;
            private String bankCard;
            private String bankPerson;
            private double sysRate;
            private double currentAgentRate;
            private double provinceAgentRate;
            private int provinceAgentId;
            private Object partnerAgentId;
            private Object partnerAgentName;
            private double marginAmt;
            private Object merchantAgent;
            private Object agentAccount;
            private Object discountAmt;
            private Object address;
            private Object provinceName;
            private Object cityName;
            private Object districtName;
            private Object canDrawCashAmt;
            private Object isDrawCash;
            private Object balance;
            private Object agentId;
            private Object hasChild;

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

            public int getManagementUserId() {
                return managementUserId;
            }

            public void setManagementUserId(int managementUserId) {
                this.managementUserId = managementUserId;
            }

            public Object getCreatedById() {
                return createdById;
            }

            public void setCreatedById(Object createdById) {
                this.createdById = createdById;
            }

            public int getModifiedById() {
                return modifiedById;
            }

            public void setModifiedById(int modifiedById) {
                this.modifiedById = modifiedById;
            }

            public Object getModifiedByName() {
                return modifiedByName;
            }

            public void setModifiedByName(Object modifiedByName) {
                this.modifiedByName = modifiedByName;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLoginName() {
                return loginName;
            }

            public void setLoginName(String loginName) {
                this.loginName = loginName;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPwd() {
                return pwd;
            }

            public void setPwd(String pwd) {
                this.pwd = pwd;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getAgentType() {
                return agentType;
            }

            public void setAgentType(int agentType) {
                this.agentType = agentType;
            }

            public int getHasDel() {
                return hasDel;
            }

            public void setHasDel(int hasDel) {
                this.hasDel = hasDel;
            }

            public int getIsAdmin() {
                return isAdmin;
            }

            public void setIsAdmin(int isAdmin) {
                this.isAdmin = isAdmin;
            }

            public int getProvince() {
                return province;
            }

            public void setProvince(int province) {
                this.province = province;
            }

            public int getCity() {
                return city;
            }

            public void setCity(int city) {
                this.city = city;
            }

            public int getDistrict() {
                return district;
            }

            public void setDistrict(int district) {
                this.district = district;
            }

            public int getAccountType() {
                return accountType;
            }

            public void setAccountType(int accountType) {
                this.accountType = accountType;
            }

            public String getBankName() {
                return bankName;
            }

            public void setBankName(String bankName) {
                this.bankName = bankName;
            }

            public String getBankCard() {
                return bankCard;
            }

            public void setBankCard(String bankCard) {
                this.bankCard = bankCard;
            }

            public String getBankPerson() {
                return bankPerson;
            }

            public void setBankPerson(String bankPerson) {
                this.bankPerson = bankPerson;
            }

            public double getSysRate() {
                return sysRate;
            }

            public void setSysRate(double sysRate) {
                this.sysRate = sysRate;
            }

            public double getCurrentAgentRate() {
                return currentAgentRate;
            }

            public void setCurrentAgentRate(double currentAgentRate) {
                this.currentAgentRate = currentAgentRate;
            }

            public double getProvinceAgentRate() {
                return provinceAgentRate;
            }

            public void setProvinceAgentRate(double provinceAgentRate) {
                this.provinceAgentRate = provinceAgentRate;
            }

            public int getProvinceAgentId() {
                return provinceAgentId;
            }

            public void setProvinceAgentId(int provinceAgentId) {
                this.provinceAgentId = provinceAgentId;
            }

            public Object getPartnerAgentId() {
                return partnerAgentId;
            }

            public void setPartnerAgentId(Object partnerAgentId) {
                this.partnerAgentId = partnerAgentId;
            }

            public Object getPartnerAgentName() {
                return partnerAgentName;
            }

            public void setPartnerAgentName(Object partnerAgentName) {
                this.partnerAgentName = partnerAgentName;
            }

            public double getMarginAmt() {
                return marginAmt;
            }

            public void setMarginAmt(double marginAmt) {
                this.marginAmt = marginAmt;
            }

            public Object getMerchantAgent() {
                return merchantAgent;
            }

            public void setMerchantAgent(Object merchantAgent) {
                this.merchantAgent = merchantAgent;
            }

            public Object getAgentAccount() {
                return agentAccount;
            }

            public void setAgentAccount(Object agentAccount) {
                this.agentAccount = agentAccount;
            }

            public Object getDiscountAmt() {
                return discountAmt;
            }

            public void setDiscountAmt(Object discountAmt) {
                this.discountAmt = discountAmt;
            }

            public Object getAddress() {
                return address;
            }

            public void setAddress(Object address) {
                this.address = address;
            }

            public Object getProvinceName() {
                return provinceName;
            }

            public void setProvinceName(Object provinceName) {
                this.provinceName = provinceName;
            }

            public Object getCityName() {
                return cityName;
            }

            public void setCityName(Object cityName) {
                this.cityName = cityName;
            }

            public Object getDistrictName() {
                return districtName;
            }

            public void setDistrictName(Object districtName) {
                this.districtName = districtName;
            }

            public Object getCanDrawCashAmt() {
                return canDrawCashAmt;
            }

            public void setCanDrawCashAmt(Object canDrawCashAmt) {
                this.canDrawCashAmt = canDrawCashAmt;
            }

            public Object getIsDrawCash() {
                return isDrawCash;
            }

            public void setIsDrawCash(Object isDrawCash) {
                this.isDrawCash = isDrawCash;
            }

            public Object getBalance() {
                return balance;
            }

            public void setBalance(Object balance) {
                this.balance = balance;
            }

            public Object getAgentId() {
                return agentId;
            }

            public void setAgentId(Object agentId) {
                this.agentId = agentId;
            }

            public Object getHasChild() {
                return hasChild;
            }

            public void setHasChild(Object hasChild) {
                this.hasChild = hasChild;
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
        public static class DeliveryTaskBean extends Entity{

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
            private Object sendOrderTime;
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
            private Object merchantOrderCounter;
            private int isReturnOrChange;
            private int returnOrChangeType;
            private int type;
            private int legWorkType;
            private Object legWorkOrder;
            private int returnOrChangeCount;
            private int orderType;
            private Object hasTransform;
            private Object expectArrivalTime;

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

            public Object getSendOrderTime() {
                return sendOrderTime;
            }

            public void setSendOrderTime(Object sendOrderTime) {
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

            public Object getMerchantOrderCounter() {
                return merchantOrderCounter;
            }

            public void setMerchantOrderCounter(Object merchantOrderCounter) {
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

            public static class DeliverymanBean {
                /**
                 * id : 55
                 * createTime : null
                 * modifyTime : 2018-04-18 11:07:04
                 * agentId : 3
                 * token : null
                 * mobile : 18701501125
                 * name : 小治
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
                 * longitude : 116.34606031502922
                 * latitude : 39.97988033305856
                 * geohash : null
                 * isOnline : 1
                 * legWorkIsOnline : null
                 * waitAcceptNum : 0
                 * hasAcceptedNum : 1
                 * arrivalMerchantNum : 0
                 * deliveringNum : 0
                 * doneNum : 189
                 * cancelNum : 3
                 * legWorkWaitAcceptNum : 5
                 * legWorkHasAcceptedNum : 2
                 * legWorkArrivalMerchantNum : 0
                 * legWorkDeliveringNum : 8
                 * legWorkDoneNum : 37
                 * legWorkCancelNum : 1
                 * headerImg :
                 * isTakeawayDeliveryman : 1
                 * isPaoTuiDeliveryman : 1
                 * type : 1
                 * deliveryTaskList : null
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
                private Object deliveryTaskList;

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

                public Object getDeliveryTaskList() {
                    return deliveryTaskList;
                }

                public void setDeliveryTaskList(Object deliveryTaskList) {
                    this.deliveryTaskList = deliveryTaskList;
                }
            }
        }
    }
}

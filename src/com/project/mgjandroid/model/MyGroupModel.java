package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.GroupInfo;

import java.util.List;

/**
 * Created by User_Cjh on 2016/8/25.
 */
public class MyGroupModel extends Entity {

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
        private double price;
        private double totalPrice;
        private int quantity;
        private Object transactionId;
        private int paymentType;
        private int paymentState;
        private String paymentExpireTime;
        private double hasPayed;
        private int status;
        private int hasDel;
        private double agentRate;
        private double agentRateAmt;
        private double userAmt;
        private int hasComments;
        private Object orderDoneTime;
        private GroupInfo groupBuy;
        private Object agentInfoMap;
        private Object shareUrl;

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

        public GroupInfo getGroupBuy() {
            return groupBuy;
        }

        public void setGroupBuy(GroupInfo groupBuy) {
            this.groupBuy = groupBuy;
        }

        public Object getAgentInfoMap() {
            return agentInfoMap;
        }

        public void setAgentInfoMap(Object agentInfoMap) {
            this.agentInfoMap = agentInfoMap;
        }

        public Object getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(Object shareUrl) {
            this.shareUrl = shareUrl;
        }
    }
}

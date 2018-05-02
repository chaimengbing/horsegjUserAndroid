package com.project.mgjandroid.bean.carhailing;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by User_Cjh on 2016/12/13.
 */
public class CarHailingOrder extends Entity {

    private String id;
    private String createTime;
    private String modifyTime;
    private long chauffeurTripId;
    private long chauffeurTripDetailId;
    private long agentId;
    private long chauffeurId;
    private long userId;
    private String userName;
    private String userMobile;
    private String userHeaderImg;
    private int chauffeurSettleState;
    private int agentSettleState;
    private Object transactionId;
    private int paymentType;
    private int paymentState;
    private String paymentExpireTime;
    private double hasPayed;
    private int status;
    private BigDecimal price;
    private int peopleNum;
    private BigDecimal originalTotalPrice;
    private BigDecimal totalPrice;
    private BigDecimal redBagTotalAmt;
    private BigDecimal promoTotalAmt;
    private int chauffeurTripType;
    private String caution;
    private int hasComments;
    private String orderDoneTime;
    private String defDoneTime;
    private String defCommontsTime;
    private int hasDel;
    private int chauffeurHasDel;
    private double sysRate;
    private double sysRateAmt;
    private double agentRate;
    private double agentRateAmt;
    private double currentAgentRate;
    private BigDecimal currentAgentRateAmt;
    private double provinceAgentRate;
    private double provinceAgentRateAmt;
    private double rateTotalAmt;
    private double chauffeurAmt;
    private String leaveTime;
    private String reason;
    private ChauffeurOrderTripBean chauffeurOrderTrip;
    private Object chauffeurTrip;
    private DriverComment chauffeurOrderCommonts;
    private CarHailingDriver chauffeur;
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

    public long getChauffeurTripId() {
        return chauffeurTripId;
    }

    public void setChauffeurTripId(long chauffeurTripId) {
        this.chauffeurTripId = chauffeurTripId;
    }

    public long getChauffeurTripDetailId() {
        return chauffeurTripDetailId;
    }

    public void setChauffeurTripDetailId(long chauffeurTripDetailId) {
        this.chauffeurTripDetailId = chauffeurTripDetailId;
    }

    public long getAgentId() {
        return agentId;
    }

    public void setAgentId(long agentId) {
        this.agentId = agentId;
    }

    public long getChauffeurId() {
        return chauffeurId;
    }

    public void setChauffeurId(long chauffeurId) {
        this.chauffeurId = chauffeurId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(int peopleNum) {
        this.peopleNum = peopleNum;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getChauffeurTripType() {
        return chauffeurTripType;
    }

    public void setChauffeurTripType(int chauffeurTripType) {
        this.chauffeurTripType = chauffeurTripType;
    }

    public BigDecimal getPromoTotalAmt() {
        return promoTotalAmt;
    }

    public void setPromoTotalAmt(BigDecimal promoTotalAmt) {
        this.promoTotalAmt = promoTotalAmt;
    }

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution;
    }

    public int getHasComments() {
        return hasComments;
    }

    public void setHasComments(int hasComments) {
        this.hasComments = hasComments;
    }

    public String getOrderDoneTime() {
        return orderDoneTime;
    }

    public void setOrderDoneTime(String orderDoneTime) {
        this.orderDoneTime = orderDoneTime;
    }

    public String getDefDoneTime() {
        return defDoneTime;
    }

    public void setDefDoneTime(String defDoneTime) {
        this.defDoneTime = defDoneTime;
    }

    public String getDefCommontsTime() {
        return defCommontsTime;
    }

    public void setDefCommontsTime(String defCommontsTime) {
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

    public double getCurrentAgentRate() {
        return currentAgentRate;
    }

    public void setCurrentAgentRate(double currentAgentRate) {
        this.currentAgentRate = currentAgentRate;
    }

    public BigDecimal getCurrentAgentRateAmt() {
        return currentAgentRateAmt;
    }

    public void setCurrentAgentRateAmt(BigDecimal currentAgentRateAmt) {
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

    public DriverComment getChauffeurOrderCommonts() {
        return chauffeurOrderCommonts;
    }

    public void setChauffeurOrderCommonts(DriverComment chauffeurOrderCommonts) {
        this.chauffeurOrderCommonts = chauffeurOrderCommonts;
    }

    public BigDecimal getOriginalTotalPrice() {
        return originalTotalPrice;
    }

    public void setOriginalTotalPrice(BigDecimal originalTotalPrice) {
        this.originalTotalPrice = originalTotalPrice;
    }

    public BigDecimal getRedBagTotalAmt() {
        return redBagTotalAmt;
    }

    public void setRedBagTotalAmt(BigDecimal redBagTotalAmt) {
        this.redBagTotalAmt = redBagTotalAmt;
    }

    public CarHailingDriver getChauffeur() {
        return chauffeur;
    }

    public void setChauffeur(CarHailingDriver chauffeur) {
        this.chauffeur = chauffeur;
    }

    public Object getAgentJson() {
        return agentJson;
    }

    public void setAgentJson(Object agentJson) {
        this.agentJson = agentJson;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
        private Date leaveTime;
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

        public Date getLeaveTime() {
            return leaveTime;
        }

        public void setLeaveTime(Date leaveTime) {
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

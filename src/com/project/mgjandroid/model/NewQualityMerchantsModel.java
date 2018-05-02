package com.project.mgjandroid.model;

import java.util.List;

/**
 * Created by SunXueLiang on 2017-09-05.
 */

public class NewQualityMerchantsModel extends Entity {

    private int code;
    private String uuid;
    private boolean success;
    private String servertime;
    private List<ValueBean> value;

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

    public List<ValueBean> getValue() {
        return value;
    }

    public void setValue(List<ValueBean> value) {
        this.value = value;
    }

    public static class ValueBean extends Entity {
        private int id;
        private String createTime;
        private String modifyTime;
        private int agentId;
        private int merchantType;
        private int merchantId;
        private String img;
        private int sortNo;
        private int hasDel;
        private AgentBean agent;
        private String merchantName;
        private MerchantDetailBean merchantDetail;

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

        public int getMerchantType() {
            return merchantType;
        }

        public void setMerchantType(int merchantType) {
            this.merchantType = merchantType;
        }

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getSortNo() {
            return sortNo;
        }

        public void setSortNo(int sortNo) {
            this.sortNo = sortNo;
        }

        public int getHasDel() {
            return hasDel;
        }

        public void setHasDel(int hasDel) {
            this.hasDel = hasDel;
        }

        public AgentBean getAgent() {
            return agent;
        }

        public void setAgent(AgentBean agent) {
            this.agent = agent;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public MerchantDetailBean getMerchantDetail() {
            return merchantDetail;
        }

        public void setMerchantDetail(MerchantDetailBean merchantDetail) {
            this.merchantDetail = merchantDetail;
        }

        public static class AgentBean extends Entity {

            private int id;
            private String createTime;
            private String modifyTime;
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
            private String bankName;
            private String bankCard;
            private String bankPerson;
            private double sysRate;
            private double currentAgentRate;
            private double provinceAgentRate;
            private int provinceAgentId;
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

        public static class MerchantDetailBean extends Entity {

            private int id;
            private long createTime;
            private long modifyTime;
            private int agentId;
            private String name;
            private String description;
            private int type;
            private String contacts;
            private int merchantStatus;
            private String payments;
            private int settleMode;
            private int shipmentMode;
            private int province;
            private int city;
            private int district;
            private String address;
            private double longitude;
            private double latitude;
            private String geohash;
            private String workingTime;
            private double minPrice;
            private double shipFee;
            private double addShipFee;
            private double defDistance;
            private int invoiceSupport;
            private String deliveryDates;
            private int prepareTime;
            private int avgDeliveryTime;
            private int hasTerminal;
            private int showPraise;
            private int showHotsale;
            private double averageScore;
            private int merchantCommentNum;
            private double merchantScore;
            private int shipCommentNum;
            private double shipScore;
            private int monthSaled;
            private int totalSaled;
            private int status;
            private int businessStatus;
            private double agentRate;
            private String invitationCode;
            private String invitationCodeImg;
            private double weight;
            private double manualWeight;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public long getModifyTime() {
                return modifyTime;
            }

            public void setModifyTime(long modifyTime) {
                this.modifyTime = modifyTime;
            }

            public int getAgentId() {
                return agentId;
            }

            public void setAgentId(int agentId) {
                this.agentId = agentId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getContacts() {
                return contacts;
            }

            public void setContacts(String contacts) {
                this.contacts = contacts;
            }

            public int getMerchantStatus() {
                return merchantStatus;
            }

            public void setMerchantStatus(int merchantStatus) {
                this.merchantStatus = merchantStatus;
            }

            public String getPayments() {
                return payments;
            }

            public void setPayments(String payments) {
                this.payments = payments;
            }

            public int getSettleMode() {
                return settleMode;
            }

            public void setSettleMode(int settleMode) {
                this.settleMode = settleMode;
            }

            public int getShipmentMode() {
                return shipmentMode;
            }

            public void setShipmentMode(int shipmentMode) {
                this.shipmentMode = shipmentMode;
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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
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

            public String getGeohash() {
                return geohash;
            }

            public void setGeohash(String geohash) {
                this.geohash = geohash;
            }

            public String getWorkingTime() {
                return workingTime;
            }

            public void setWorkingTime(String workingTime) {
                this.workingTime = workingTime;
            }

            public double getMinPrice() {
                return minPrice;
            }

            public void setMinPrice(double minPrice) {
                this.minPrice = minPrice;
            }

            public double getShipFee() {
                return shipFee;
            }

            public void setShipFee(double shipFee) {
                this.shipFee = shipFee;
            }

            public double getAddShipFee() {
                return addShipFee;
            }

            public void setAddShipFee(double addShipFee) {
                this.addShipFee = addShipFee;
            }

            public double getDefDistance() {
                return defDistance;
            }

            public void setDefDistance(double defDistance) {
                this.defDistance = defDistance;
            }

            public int getInvoiceSupport() {
                return invoiceSupport;
            }

            public void setInvoiceSupport(int invoiceSupport) {
                this.invoiceSupport = invoiceSupport;
            }

            public String getDeliveryDates() {
                return deliveryDates;
            }

            public void setDeliveryDates(String deliveryDates) {
                this.deliveryDates = deliveryDates;
            }

            public int getPrepareTime() {
                return prepareTime;
            }

            public void setPrepareTime(int prepareTime) {
                this.prepareTime = prepareTime;
            }

            public int getAvgDeliveryTime() {
                return avgDeliveryTime;
            }

            public void setAvgDeliveryTime(int avgDeliveryTime) {
                this.avgDeliveryTime = avgDeliveryTime;
            }

            public int getHasTerminal() {
                return hasTerminal;
            }

            public void setHasTerminal(int hasTerminal) {
                this.hasTerminal = hasTerminal;
            }

            public int getShowPraise() {
                return showPraise;
            }

            public void setShowPraise(int showPraise) {
                this.showPraise = showPraise;
            }

            public int getShowHotsale() {
                return showHotsale;
            }

            public void setShowHotsale(int showHotsale) {
                this.showHotsale = showHotsale;
            }

            public double getAverageScore() {
                return averageScore;
            }

            public void setAverageScore(double averageScore) {
                this.averageScore = averageScore;
            }

            public int getMerchantCommentNum() {
                return merchantCommentNum;
            }

            public void setMerchantCommentNum(int merchantCommentNum) {
                this.merchantCommentNum = merchantCommentNum;
            }

            public double getMerchantScore() {
                return merchantScore;
            }

            public void setMerchantScore(double merchantScore) {
                this.merchantScore = merchantScore;
            }

            public int getShipCommentNum() {
                return shipCommentNum;
            }

            public void setShipCommentNum(int shipCommentNum) {
                this.shipCommentNum = shipCommentNum;
            }

            public double getShipScore() {
                return shipScore;
            }

            public void setShipScore(double shipScore) {
                this.shipScore = shipScore;
            }

            public int getMonthSaled() {
                return monthSaled;
            }

            public void setMonthSaled(int monthSaled) {
                this.monthSaled = monthSaled;
            }

            public int getTotalSaled() {
                return totalSaled;
            }

            public void setTotalSaled(int totalSaled) {
                this.totalSaled = totalSaled;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getBusinessStatus() {
                return businessStatus;
            }

            public void setBusinessStatus(int businessStatus) {
                this.businessStatus = businessStatus;
            }

            public double getAgentRate() {
                return agentRate;
            }

            public void setAgentRate(double agentRate) {
                this.agentRate = agentRate;
            }

            public String getInvitationCode() {
                return invitationCode;
            }

            public void setInvitationCode(String invitationCode) {
                this.invitationCode = invitationCode;
            }

            public String getInvitationCodeImg() {
                return invitationCodeImg;
            }

            public void setInvitationCodeImg(String invitationCodeImg) {
                this.invitationCodeImg = invitationCodeImg;
            }

            public double getWeight() {
                return weight;
            }

            public void setWeight(double weight) {
                this.weight = weight;
            }

            public double getManualWeight() {
                return manualWeight;
            }

            public void setManualWeight(double manualWeight) {
                this.manualWeight = manualWeight;
            }
        }
    }
}

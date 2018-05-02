package com.project.mgjandroid.model;

import java.util.List;

/**
 * Created by User_Cjh on 2016/8/19.
 */
public class GroupCheckModel extends Entity {

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
        private GroupBuyListEntity groupBuyList;
        private AgentEntity agent;

        public GroupBuyListEntity getGroupBuyList() {
            return groupBuyList;
        }

        public void setGroupBuyList(GroupBuyListEntity groupBuyList) {
            this.groupBuyList = groupBuyList;
        }

        public AgentEntity getAgent() {
            return agent;
        }

        public void setAgent(AgentEntity agent) {
            this.agent = agent;
        }

        public static class GroupBuyListEntity extends Entity {
            private int code;
            private boolean success;
            private List<ValueBean> value;

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public boolean isSuccess() {
                return success;
            }

            public void setSuccess(boolean success) {
                this.success = success;
            }

            public List<ValueBean> getValue() {
                return value;
            }

            public void setValue(List<ValueBean> value) {
                this.value = value;
            }

            public static class ValueBean extends Entity {
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
                private String service;
                private String description;
                private Object marketingImg;
                private String imgs;
                private double agentRate;
                private double agentRateAmt;
                private double userAmt;
                private Object startTime;
                private Object defaultEndTime;
                private Object endTime;
                private Object sortNo;
                private Object memo;
                private int status;
                private int hasDel;
                private Object groupBuyUser;

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

                public Object getStartTime() {
                    return startTime;
                }

                public void setStartTime(Object startTime) {
                    this.startTime = startTime;
                }

                public Object getDefaultEndTime() {
                    return defaultEndTime;
                }

                public void setDefaultEndTime(Object defaultEndTime) {
                    this.defaultEndTime = defaultEndTime;
                }

                public Object getEndTime() {
                    return endTime;
                }

                public void setEndTime(Object endTime) {
                    this.endTime = endTime;
                }

                public Object getSortNo() {
                    return sortNo;
                }

                public void setSortNo(Object sortNo) {
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

                public Object getGroupBuyUser() {
                    return groupBuyUser;
                }

                public void setGroupBuyUser(Object groupBuyUser) {
                    this.groupBuyUser = groupBuyUser;
                }
            }
        }

        public static class AgentEntity {
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
            private String provinceName;
            private String cityName;
            private String districtName;

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

            public String getProvinceName() {
                return provinceName;
            }

            public void setProvinceName(String provinceName) {
                this.provinceName = provinceName;
            }

            public String getCityName() {
                return cityName;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName;
            }

            public String getDistrictName() {
                return districtName;
            }

            public void setDistrictName(String districtName) {
                this.districtName = districtName;
            }
        }
    }
}

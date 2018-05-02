package com.project.mgjandroid.model;

/**
 * Created by SunXueLiang on 2017-08-29.
 */

public class HomeVersionModel extends Entity {

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

    public static class ValueBean extends Entity {

        private AppHomePageVersionBean appHomePageVersion;
        private AgentBean agent;

        public AppHomePageVersionBean getAppHomePageVersion() {
            return appHomePageVersion;
        }

        public void setAppHomePageVersion(AppHomePageVersionBean appHomePageVersion) {
            this.appHomePageVersion = appHomePageVersion;
        }

        public AgentBean getAgent() {
            return agent;
        }

        public void setAgent(AgentBean agent) {
            this.agent = agent;
        }

        public static class AppHomePageVersionBean extends Entity {

            private int id;
            private String createTime;
            private String modifyTime;
            private int agentId;
            private int hasModify;
            private int versionType;
            private int hasDel;
            private int appMenuCount;
            private String secondMenuTypeName;
            private Object agent;

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

            public int getHasModify() {
                return hasModify;
            }

            public void setHasModify(int hasModify) {
                this.hasModify = hasModify;
            }

            public int getVersionType() {
                return versionType;
            }

            public void setVersionType(int versionType) {
                this.versionType = versionType;
            }

            public int getHasDel() {
                return hasDel;
            }

            public void setHasDel(int hasDel) {
                this.hasDel = hasDel;
            }

            public int getAppMenuCount() {
                return appMenuCount;
            }

            public void setAppMenuCount(int appMenuCount) {
                this.appMenuCount = appMenuCount;
            }

            public Object getAgent() {
                return agent;
            }

            public void setAgent(Object agent) {
                this.agent = agent;
            }

            public String getSecondMenuTypeName() {
                return secondMenuTypeName;
            }

            public void setSecondMenuTypeName(String secondMenuTypeName) {
                this.secondMenuTypeName = secondMenuTypeName;
            }
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
    }
}

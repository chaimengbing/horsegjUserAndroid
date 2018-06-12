package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

public class SharingRelationship extends Entity{

    /**
     * id : 277
     * createTime : 2018-03-04 14:47:20
     * modifyTime : 2018-05-10 18:23:28
     * agentId : 167
     * promotionId : 1
     * relationPromotionId : 2
     * status : 0
     * promotionName : 首单立减
     * relationPromotionName : 满赠优惠
     * agent : {"id":167,"createTime":"2017-12-19 17:03:56","modifyTime":"2018-06-05 18:12:32","managementUserId":131,"createdById":1,"modifiedById":1,"name":"新合伙人","loginName":"新合伙人","mobile":"13552741085","phone":"0105214555","pwd":"0ea1785dd1d45a443fdb167418b1182219e66e0324a18989dc1950469d3a4a7eabfad002b314fec0","parentId":0,"level":1,"agentType":1,"hasDel":0,"isAdmin":0,"province":3238,"city":3239,"district":3240,"bankName":"招商","bankCard":"666666666666","bankPerson":"发嫂","sysRate":1,"currentAgentRate":1,"provinceAgentRate":1,"provinceAgentId":null,"partnerAgentId":null,"marginAmt":0}
     */

    private int id;
    private String createTime;
    private String modifyTime;
    private int agentId;
    private int promotionId;
    private int relationPromotionId;
    private int status;
    private String promotionName;
    private String relationPromotionName;
    private AgentBean agent;

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

    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }

    public int getRelationPromotionId() {
        return relationPromotionId;
    }

    public void setRelationPromotionId(int relationPromotionId) {
        this.relationPromotionId = relationPromotionId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getRelationPromotionName() {
        return relationPromotionName;
    }

    public void setRelationPromotionName(String relationPromotionName) {
        this.relationPromotionName = relationPromotionName;
    }

    public AgentBean getAgent() {
        return agent;
    }

    public void setAgent(AgentBean agent) {
        this.agent = agent;
    }

    public static class AgentBean extends Entity{
        /**
         * id : 167
         * createTime : 2017-12-19 17:03:56
         * modifyTime : 2018-06-05 18:12:32
         * managementUserId : 131
         * createdById : 1
         * modifiedById : 1
         * name : 新合伙人
         * loginName : 新合伙人
         * mobile : 13552741085
         * phone : 0105214555
         * pwd : 0ea1785dd1d45a443fdb167418b1182219e66e0324a18989dc1950469d3a4a7eabfad002b314fec0
         * parentId : 0
         * level : 1
         * agentType : 1
         * hasDel : 0
         * isAdmin : 0
         * province : 3238
         * city : 3239
         * district : 3240
         * bankName : 招商
         * bankCard : 666666666666
         * bankPerson : 发嫂
         * sysRate : 1.0
         * currentAgentRate : 1.0
         * provinceAgentRate : 1.0
         * provinceAgentId : null
         * partnerAgentId : null
         * marginAmt : 0.0
         */

        private int id;
        private String createTime;
        private String modifyTime;
        private int managementUserId;
        private int createdById;
        private int modifiedById;
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
        private Object provinceAgentId;
        private Object partnerAgentId;
        private double marginAmt;

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

        public int getCreatedById() {
            return createdById;
        }

        public void setCreatedById(int createdById) {
            this.createdById = createdById;
        }

        public int getModifiedById() {
            return modifiedById;
        }

        public void setModifiedById(int modifiedById) {
            this.modifiedById = modifiedById;
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

        public Object getProvinceAgentId() {
            return provinceAgentId;
        }

        public void setProvinceAgentId(Object provinceAgentId) {
            this.provinceAgentId = provinceAgentId;
        }

        public Object getPartnerAgentId() {
            return partnerAgentId;
        }

        public void setPartnerAgentId(Object partnerAgentId) {
            this.partnerAgentId = partnerAgentId;
        }

        public double getMarginAmt() {
            return marginAmt;
        }

        public void setMarginAmt(double marginAmt) {
            this.marginAmt = marginAmt;
        }
    }
}

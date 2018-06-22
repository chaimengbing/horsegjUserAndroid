package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.util.List;

public class SharingRelationship extends Entity{

        private int id;
        private String createTime;
        private String modifyTime;
        private int agentId;
        private int promotionId;
        private int relationPromotionId;
        private int status;
        private String promotionName;
        private String relationPromotionName;
        private int promotionActivityType;
        private PromotionBean promotion;
        private int relationPromotionActivityType;
        private RelationPromotionBean relationPromotion;
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

        public int getPromotionActivityType() {
            return promotionActivityType;
        }

        public void setPromotionActivityType(int promotionActivityType) {
            this.promotionActivityType = promotionActivityType;
        }

        public PromotionBean getPromotion() {
            return promotion;
        }

        public void setPromotion(PromotionBean promotion) {
            this.promotion = promotion;
        }

        public int getRelationPromotionActivityType() {
            return relationPromotionActivityType;
        }

        public void setRelationPromotionActivityType(int relationPromotionActivityType) {
            this.relationPromotionActivityType = relationPromotionActivityType;
        }

        public RelationPromotionBean getRelationPromotion() {
            return relationPromotion;
        }

        public void setRelationPromotion(RelationPromotionBean relationPromotion) {
            this.relationPromotion = relationPromotion;
        }

        public AgentBean getAgent() {
            return agent;
        }

        public void setAgent(AgentBean agent) {
            this.agent = agent;
        }

        public static class PromotionBean extends Entity{
            /**
             * id : 1
             * createTime : 2018-01-23 11:37:57
             * modifyTime : 2018-03-04 15:58:56
             * name : 首单立减
             * icon : http://7xu5hi.com1.z0.glb.clouddn.com/1611021611514026752.png
             * type : 1
             * priority : 1
             * promotionGenre : 1
             * promotionActivityType : 1
             * sortNo : 1
             * hasDel : 0
             */

            private int id;
            private String createTime;
            private String modifyTime;
            private String name;
            private String icon;
            private int type;
            private int priority;
            private int promotionGenre;
            private int promotionActivityType;
            private int sortNo;
            private int hasDel;

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

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getPriority() {
                return priority;
            }

            public void setPriority(int priority) {
                this.priority = priority;
            }

            public int getPromotionGenre() {
                return promotionGenre;
            }

            public void setPromotionGenre(int promotionGenre) {
                this.promotionGenre = promotionGenre;
            }

            public int getPromotionActivityType() {
                return promotionActivityType;
            }

            public void setPromotionActivityType(int promotionActivityType) {
                this.promotionActivityType = promotionActivityType;
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
        }

        public static class RelationPromotionBean extends Entity{
            /**
             * id : 2
             * createTime : 2018-01-23 11:38:25
             * modifyTime : 2018-02-25 16:51:26
             * name : 满赠优惠
             * icon : http://7xu5hi.com1.z0.glb.clouddn.com/1611011628430224319.png
             * type : 1
             * priority : 4
             * promotionGenre : 1
             * promotionActivityType : 3
             * sortNo : 2
             * hasDel : 0
             */

            private int id;
            private String createTime;
            private String modifyTime;
            private String name;
            private String icon;
            private int type;
            private int priority;
            private int promotionGenre;
            private int promotionActivityType;
            private int sortNo;
            private int hasDel;

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

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getPriority() {
                return priority;
            }

            public void setPriority(int priority) {
                this.priority = priority;
            }

            public int getPromotionGenre() {
                return promotionGenre;
            }

            public void setPromotionGenre(int promotionGenre) {
                this.promotionGenre = promotionGenre;
            }

            public int getPromotionActivityType() {
                return promotionActivityType;
            }

            public void setPromotionActivityType(int promotionActivityType) {
                this.promotionActivityType = promotionActivityType;
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
        }

        public static class AgentBean extends Entity{
            /**
             * id : 3
             * createTime : 2016-03-21 15:43:33
             * modifyTime : 2018-05-19 09:52:02
             * managementUserId : 3
             * createdById : null
             * modifiedById : 1
             * name : test
             * loginName : test
             * mobile : 18701501121
             * phone : 1111111114
             * pwd : 0f1d1fe88e739b3476a26905aa1628c2b5fbf66a2229b6f9069c632f5a1dc2bac6a0d6b79150b7f0
             * parentId : 1
             * level : 1
             * agentType : 1
             * hasDel : 0
             * isAdmin : 0
             * province : 1
             * city : 2
             * district : 5
             * bankName : 中国邮政储蓄银行
             * bankCard : 61648445404000564
             * bankPerson : 何谐
             * sysRate : 10.0
             * currentAgentRate : 0.0
             * provinceAgentRate : 10.0
             * provinceAgentId : 37
             * partnerAgentId : null
             * marginAmt : 0.0
             */

            private int id;
            private String createTime;
            private String modifyTime;
            private int managementUserId;
            private Object createdById;
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
            private int provinceAgentId;
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

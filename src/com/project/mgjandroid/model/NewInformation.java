package com.project.mgjandroid.model;

import java.util.List;

/**
 * Created by SunXueLiang on 2017-09-06.
 */

public class NewInformation extends Entity {

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
        private List<NewsInformationDataBean> newsInformationData;
        private List<PositionDataBean> positionData;
        private List<RecruitDataBean> recruitData;
        private List<LeaseDataBean> leaseData;

        public List<NewsInformationDataBean> getNewsInformationData() {
            return newsInformationData;
        }

        public void setNewsInformationData(List<NewsInformationDataBean> newsInformationData) {
            this.newsInformationData = newsInformationData;
        }

        public List<PositionDataBean> getPositionData() {
            return positionData;
        }

        public void setPositionData(List<PositionDataBean> positionData) {
            this.positionData = positionData;
        }

        public List<RecruitDataBean> getRecruitData() {
            return recruitData;
        }

        public void setRecruitData(List<RecruitDataBean> recruitData) {
            this.recruitData = recruitData;
        }

        public List<LeaseDataBean> getLeaseData() {
            return leaseData;
        }

        public void setLeaseData(List<LeaseDataBean> leaseData) {
            this.leaseData = leaseData;
        }

        public static class NewsInformationDataBean extends Entity {

            private int id;
            private String createTime;
            private String modifyTime;
            private int agentId;
            private String title;
            private int position;
            private String address;
            private String newsSource;
            private String releaseDate;
            private String imgs;
            private int sortNo;
            private int hasDel;
            private int modifiedByAgentId;
            private Object modifiedByAgent;
            private Object img1;
            private Object img2;
            private Object img3;
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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getPosition() {
                return position;
            }

            public void setPosition(int position) {
                this.position = position;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getNewsSource() {
                return newsSource;
            }

            public void setNewsSource(String newsSource) {
                this.newsSource = newsSource;
            }

            public String getReleaseDate() {
                return releaseDate;
            }

            public void setReleaseDate(String releaseDate) {
                this.releaseDate = releaseDate;
            }

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
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

            public int getModifiedByAgentId() {
                return modifiedByAgentId;
            }

            public void setModifiedByAgentId(int modifiedByAgentId) {
                this.modifiedByAgentId = modifiedByAgentId;
            }

            public Object getModifiedByAgent() {
                return modifiedByAgent;
            }

            public void setModifiedByAgent(Object modifiedByAgent) {
                this.modifiedByAgent = modifiedByAgent;
            }

            public Object getImg1() {
                return img1;
            }

            public void setImg1(Object img1) {
                this.img1 = img1;
            }

            public Object getImg2() {
                return img2;
            }

            public void setImg2(Object img2) {
                this.img2 = img2;
            }

            public Object getImg3() {
                return img3;
            }

            public void setImg3(Object img3) {
                this.img3 = img3;
            }

            public Object getAgent() {
                return agent;
            }

            public void setAgent(Object agent) {
                this.agent = agent;
            }
        }

        public static class PositionDataBean extends Entity {

            private int id;
            private String createTime;
            private String modifyTime;
            private int agentId;
            private int informationId;
            private int informationType;
            private int sortNo;
            private int hasDel;
            private int modifiedByAgentId;
            private Object modifiedByAgent;
            private Object agent;
            private InformationDetailBean informationDetail;

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

            public int getInformationId() {
                return informationId;
            }

            public void setInformationId(int informationId) {
                this.informationId = informationId;
            }

            public int getInformationType() {
                return informationType;
            }

            public void setInformationType(int informationType) {
                this.informationType = informationType;
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

            public int getModifiedByAgentId() {
                return modifiedByAgentId;
            }

            public void setModifiedByAgentId(int modifiedByAgentId) {
                this.modifiedByAgentId = modifiedByAgentId;
            }

            public Object getModifiedByAgent() {
                return modifiedByAgent;
            }

            public void setModifiedByAgent(Object modifiedByAgent) {
                this.modifiedByAgent = modifiedByAgent;
            }

            public Object getAgent() {
                return agent;
            }

            public void setAgent(Object agent) {
                this.agent = agent;
            }

            public InformationDetailBean getInformationDetail() {
                return informationDetail;
            }

            public void setInformationDetail(InformationDetailBean informationDetail) {
                this.informationDetail = informationDetail;
            }

            public static class InformationDetailBean extends Entity {

                private int id;
                private String createTime;
                private String modifyTime;
                private int userId;
                private int agentId;
                private int province;
                private int city;
                private int district;
                private String title;
                private String mobile;
                private int status;
                private int isExpire;
                private Object refreshDate;
                private int refreshCount;
                private String refreshTime;
                private int hasDel;
                private int isTop;
                private int roleType;
                private String baiduCityCode;
                private String description;
                private Object imgs;
                private int serviceCategoryId;
                private int categoryId;
                private Object topExpireTime;
                private String examineExpireTime;
                private String expireTime;
                private int reportCount;
                private String informationOrderId;
                private int hasAgainSend;
                private Object fromInformationId;
                private String shareUrl;
                private Object agentName;
                private String categoryName;
                private Object informationOrder;
                private int type;
                private int sex;
                private String headImg;
                private String birthday;
                private String address;
                private String highestEducation;
                private String workExperience;
                private String expectSalary;
                private String expectPosition;
                private int age;

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

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getIsExpire() {
                    return isExpire;
                }

                public void setIsExpire(int isExpire) {
                    this.isExpire = isExpire;
                }

                public Object getRefreshDate() {
                    return refreshDate;
                }

                public void setRefreshDate(Object refreshDate) {
                    this.refreshDate = refreshDate;
                }

                public int getRefreshCount() {
                    return refreshCount;
                }

                public void setRefreshCount(int refreshCount) {
                    this.refreshCount = refreshCount;
                }

                public String getRefreshTime() {
                    return refreshTime;
                }

                public void setRefreshTime(String refreshTime) {
                    this.refreshTime = refreshTime;
                }

                public int getHasDel() {
                    return hasDel;
                }

                public void setHasDel(int hasDel) {
                    this.hasDel = hasDel;
                }

                public int getIsTop() {
                    return isTop;
                }

                public void setIsTop(int isTop) {
                    this.isTop = isTop;
                }

                public int getRoleType() {
                    return roleType;
                }

                public void setRoleType(int roleType) {
                    this.roleType = roleType;
                }

                public String getBaiduCityCode() {
                    return baiduCityCode;
                }

                public void setBaiduCityCode(String baiduCityCode) {
                    this.baiduCityCode = baiduCityCode;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public Object getImgs() {
                    return imgs;
                }

                public void setImgs(Object imgs) {
                    this.imgs = imgs;
                }

                public int getServiceCategoryId() {
                    return serviceCategoryId;
                }

                public void setServiceCategoryId(int serviceCategoryId) {
                    this.serviceCategoryId = serviceCategoryId;
                }

                public int getCategoryId() {
                    return categoryId;
                }

                public void setCategoryId(int categoryId) {
                    this.categoryId = categoryId;
                }

                public Object getTopExpireTime() {
                    return topExpireTime;
                }

                public void setTopExpireTime(Object topExpireTime) {
                    this.topExpireTime = topExpireTime;
                }

                public String getExamineExpireTime() {
                    return examineExpireTime;
                }

                public void setExamineExpireTime(String examineExpireTime) {
                    this.examineExpireTime = examineExpireTime;
                }

                public String getExpireTime() {
                    return expireTime;
                }

                public void setExpireTime(String expireTime) {
                    this.expireTime = expireTime;
                }

                public int getReportCount() {
                    return reportCount;
                }

                public void setReportCount(int reportCount) {
                    this.reportCount = reportCount;
                }

                public String getInformationOrderId() {
                    return informationOrderId;
                }

                public void setInformationOrderId(String informationOrderId) {
                    this.informationOrderId = informationOrderId;
                }

                public int getHasAgainSend() {
                    return hasAgainSend;
                }

                public void setHasAgainSend(int hasAgainSend) {
                    this.hasAgainSend = hasAgainSend;
                }

                public Object getFromInformationId() {
                    return fromInformationId;
                }

                public void setFromInformationId(Object fromInformationId) {
                    this.fromInformationId = fromInformationId;
                }

                public String getShareUrl() {
                    return shareUrl;
                }

                public void setShareUrl(String shareUrl) {
                    this.shareUrl = shareUrl;
                }

                public Object getAgentName() {
                    return agentName;
                }

                public void setAgentName(Object agentName) {
                    this.agentName = agentName;
                }

                public String getCategoryName() {
                    return categoryName;
                }

                public void setCategoryName(String categoryName) {
                    this.categoryName = categoryName;
                }

                public Object getInformationOrder() {
                    return informationOrder;
                }

                public void setInformationOrder(Object informationOrder) {
                    this.informationOrder = informationOrder;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public int getSex() {
                    return sex;
                }

                public void setSex(int sex) {
                    this.sex = sex;
                }

                public String getHeadImg() {
                    return headImg;
                }

                public void setHeadImg(String headImg) {
                    this.headImg = headImg;
                }

                public String getBirthday() {
                    return birthday;
                }

                public void setBirthday(String birthday) {
                    this.birthday = birthday;
                }

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getHighestEducation() {
                    return highestEducation;
                }

                public void setHighestEducation(String highestEducation) {
                    this.highestEducation = highestEducation;
                }

                public String getWorkExperience() {
                    return workExperience;
                }

                public void setWorkExperience(String workExperience) {
                    this.workExperience = workExperience;
                }

                public String getExpectSalary() {
                    return expectSalary;
                }

                public void setExpectSalary(String expectSalary) {
                    this.expectSalary = expectSalary;
                }

                public String getExpectPosition() {
                    return expectPosition;
                }

                public void setExpectPosition(String expectPosition) {
                    this.expectPosition = expectPosition;
                }

                public int getAge() {
                    return age;
                }

                public void setAge(int age) {
                    this.age = age;
                }
            }
        }

        public static class RecruitDataBean extends Entity {

            private int id;
            private String createTime;
            private String modifyTime;
            private int agentId;
            private int informationId;
            private int informationType;
            private int sortNo;
            private int hasDel;
            private int modifiedByAgentId;
            private Object modifiedByAgent;
            private Object agent;
            private InformationDetailBeanX informationDetail;

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

            public int getInformationId() {
                return informationId;
            }

            public void setInformationId(int informationId) {
                this.informationId = informationId;
            }

            public int getInformationType() {
                return informationType;
            }

            public void setInformationType(int informationType) {
                this.informationType = informationType;
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

            public int getModifiedByAgentId() {
                return modifiedByAgentId;
            }

            public void setModifiedByAgentId(int modifiedByAgentId) {
                this.modifiedByAgentId = modifiedByAgentId;
            }

            public Object getModifiedByAgent() {
                return modifiedByAgent;
            }

            public void setModifiedByAgent(Object modifiedByAgent) {
                this.modifiedByAgent = modifiedByAgent;
            }

            public Object getAgent() {
                return agent;
            }

            public void setAgent(Object agent) {
                this.agent = agent;
            }

            public InformationDetailBeanX getInformationDetail() {
                return informationDetail;
            }

            public void setInformationDetail(InformationDetailBeanX informationDetail) {
                this.informationDetail = informationDetail;
            }

            public static class InformationDetailBeanX extends Entity {

                private int id;
                private String createTime;
                private String modifyTime;
                private int userId;
                private int agentId;
                private int province;
                private int city;
                private int district;
                private String title;
                private String mobile;
                private int status;
                private int isExpire;
                private Object refreshDate;
                private int refreshCount;
                private String refreshTime;
                private int hasDel;
                private int isTop;
                private int roleType;
                private String baiduCityCode;
                private String description;
                private String imgs;
                private int serviceCategoryId;
                private int categoryId;
                private Object topExpireTime;
                private String examineExpireTime;
                private String expireTime;
                private int reportCount;
                private String informationOrderId;
                private int hasAgainSend;
                private Object fromInformationId;
                private String shareUrl;
                private Object agentName;
                private String categoryName;
                private Object informationOrder;
                private int type;
                private String companyName;
                private String companyAddress;
                private String companyType;
                private String profession;
                private String companyScale;
                private String positionName;
                private String recruitNum;
                private String education;
                private String workYears;
                private String claim;
                private String salary;
                private String welfare;

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

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getIsExpire() {
                    return isExpire;
                }

                public void setIsExpire(int isExpire) {
                    this.isExpire = isExpire;
                }

                public Object getRefreshDate() {
                    return refreshDate;
                }

                public void setRefreshDate(Object refreshDate) {
                    this.refreshDate = refreshDate;
                }

                public int getRefreshCount() {
                    return refreshCount;
                }

                public void setRefreshCount(int refreshCount) {
                    this.refreshCount = refreshCount;
                }

                public String getRefreshTime() {
                    return refreshTime;
                }

                public void setRefreshTime(String refreshTime) {
                    this.refreshTime = refreshTime;
                }

                public int getHasDel() {
                    return hasDel;
                }

                public void setHasDel(int hasDel) {
                    this.hasDel = hasDel;
                }

                public int getIsTop() {
                    return isTop;
                }

                public void setIsTop(int isTop) {
                    this.isTop = isTop;
                }

                public int getRoleType() {
                    return roleType;
                }

                public void setRoleType(int roleType) {
                    this.roleType = roleType;
                }

                public String getBaiduCityCode() {
                    return baiduCityCode;
                }

                public void setBaiduCityCode(String baiduCityCode) {
                    this.baiduCityCode = baiduCityCode;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getImgs() {
                    return imgs;
                }

                public void setImgs(String imgs) {
                    this.imgs = imgs;
                }

                public int getServiceCategoryId() {
                    return serviceCategoryId;
                }

                public void setServiceCategoryId(int serviceCategoryId) {
                    this.serviceCategoryId = serviceCategoryId;
                }

                public int getCategoryId() {
                    return categoryId;
                }

                public void setCategoryId(int categoryId) {
                    this.categoryId = categoryId;
                }

                public Object getTopExpireTime() {
                    return topExpireTime;
                }

                public void setTopExpireTime(Object topExpireTime) {
                    this.topExpireTime = topExpireTime;
                }

                public String getExamineExpireTime() {
                    return examineExpireTime;
                }

                public void setExamineExpireTime(String examineExpireTime) {
                    this.examineExpireTime = examineExpireTime;
                }

                public String getExpireTime() {
                    return expireTime;
                }

                public void setExpireTime(String expireTime) {
                    this.expireTime = expireTime;
                }

                public int getReportCount() {
                    return reportCount;
                }

                public void setReportCount(int reportCount) {
                    this.reportCount = reportCount;
                }

                public String getInformationOrderId() {
                    return informationOrderId;
                }

                public void setInformationOrderId(String informationOrderId) {
                    this.informationOrderId = informationOrderId;
                }

                public int getHasAgainSend() {
                    return hasAgainSend;
                }

                public void setHasAgainSend(int hasAgainSend) {
                    this.hasAgainSend = hasAgainSend;
                }

                public Object getFromInformationId() {
                    return fromInformationId;
                }

                public void setFromInformationId(Object fromInformationId) {
                    this.fromInformationId = fromInformationId;
                }

                public String getShareUrl() {
                    return shareUrl;
                }

                public void setShareUrl(String shareUrl) {
                    this.shareUrl = shareUrl;
                }

                public Object getAgentName() {
                    return agentName;
                }

                public void setAgentName(Object agentName) {
                    this.agentName = agentName;
                }

                public String getCategoryName() {
                    return categoryName;
                }

                public void setCategoryName(String categoryName) {
                    this.categoryName = categoryName;
                }

                public Object getInformationOrder() {
                    return informationOrder;
                }

                public void setInformationOrder(Object informationOrder) {
                    this.informationOrder = informationOrder;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getCompanyName() {
                    return companyName;
                }

                public void setCompanyName(String companyName) {
                    this.companyName = companyName;
                }

                public String getCompanyAddress() {
                    return companyAddress;
                }

                public void setCompanyAddress(String companyAddress) {
                    this.companyAddress = companyAddress;
                }

                public String getCompanyType() {
                    return companyType;
                }

                public void setCompanyType(String companyType) {
                    this.companyType = companyType;
                }

                public String getProfession() {
                    return profession;
                }

                public void setProfession(String profession) {
                    this.profession = profession;
                }

                public String getCompanyScale() {
                    return companyScale;
                }

                public void setCompanyScale(String companyScale) {
                    this.companyScale = companyScale;
                }

                public String getPositionName() {
                    return positionName;
                }

                public void setPositionName(String positionName) {
                    this.positionName = positionName;
                }

                public String getRecruitNum() {
                    return recruitNum;
                }

                public void setRecruitNum(String recruitNum) {
                    this.recruitNum = recruitNum;
                }

                public String getEducation() {
                    return education;
                }

                public void setEducation(String education) {
                    this.education = education;
                }

                public String getWorkYears() {
                    return workYears;
                }

                public void setWorkYears(String workYears) {
                    this.workYears = workYears;
                }

                public String getClaim() {
                    return claim;
                }

                public void setClaim(String claim) {
                    this.claim = claim;
                }

                public String getSalary() {
                    return salary;
                }

                public void setSalary(String salary) {
                    this.salary = salary;
                }

                public String getWelfare() {
                    return welfare;
                }

                public void setWelfare(String welfare) {
                    this.welfare = welfare;
                }
            }
        }

        public static class LeaseDataBean extends Entity {

            private int id;
            private String createTime;
            private String modifyTime;
            private int agentId;
            private int informationId;
            private int informationType;
            private int sortNo;
            private int hasDel;
            private int modifiedByAgentId;
            private Object modifiedByAgent;
            private Object agent;
            private InformationDetailBeanXX informationDetail;

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

            public int getInformationId() {
                return informationId;
            }

            public void setInformationId(int informationId) {
                this.informationId = informationId;
            }

            public int getInformationType() {
                return informationType;
            }

            public void setInformationType(int informationType) {
                this.informationType = informationType;
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

            public int getModifiedByAgentId() {
                return modifiedByAgentId;
            }

            public void setModifiedByAgentId(int modifiedByAgentId) {
                this.modifiedByAgentId = modifiedByAgentId;
            }

            public Object getModifiedByAgent() {
                return modifiedByAgent;
            }

            public void setModifiedByAgent(Object modifiedByAgent) {
                this.modifiedByAgent = modifiedByAgent;
            }

            public Object getAgent() {
                return agent;
            }

            public void setAgent(Object agent) {
                this.agent = agent;
            }

            public InformationDetailBeanXX getInformationDetail() {
                return informationDetail;
            }

            public void setInformationDetail(InformationDetailBeanXX informationDetail) {
                this.informationDetail = informationDetail;
            }

            public static class InformationDetailBeanXX extends Entity {

                private int id;
                private String createTime;
                private String modifyTime;
                private int userId;
                private int agentId;
                private int province;
                private int city;
                private int district;
                private String title;
                private String mobile;
                private int status;
                private int isExpire;
                private Object refreshDate;
                private int refreshCount;
                private String refreshTime;
                private int hasDel;
                private int isTop;
                private int roleType;
                private String baiduCityCode;
                private String description;
                private String imgs;
                private int serviceCategoryId;
                private int categoryId;
                private String topExpireTime;
                private String examineExpireTime;
                private String expireTime;
                private int reportCount;
                private String informationOrderId;
                private int hasAgainSend;
                private Object fromInformationId;
                private String shareUrl;
                private Object agentName;
                private String categoryName;
                private Object informationOrder;
                private int type;
                private String houseType;
                private String sectorArea;
                private double houseArea;
                private double amt;
                private Object minAmt;
                private Object maxAmt;

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

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getIsExpire() {
                    return isExpire;
                }

                public void setIsExpire(int isExpire) {
                    this.isExpire = isExpire;
                }

                public Object getRefreshDate() {
                    return refreshDate;
                }

                public void setRefreshDate(Object refreshDate) {
                    this.refreshDate = refreshDate;
                }

                public int getRefreshCount() {
                    return refreshCount;
                }

                public void setRefreshCount(int refreshCount) {
                    this.refreshCount = refreshCount;
                }

                public String getRefreshTime() {
                    return refreshTime;
                }

                public void setRefreshTime(String refreshTime) {
                    this.refreshTime = refreshTime;
                }

                public int getHasDel() {
                    return hasDel;
                }

                public void setHasDel(int hasDel) {
                    this.hasDel = hasDel;
                }

                public int getIsTop() {
                    return isTop;
                }

                public void setIsTop(int isTop) {
                    this.isTop = isTop;
                }

                public int getRoleType() {
                    return roleType;
                }

                public void setRoleType(int roleType) {
                    this.roleType = roleType;
                }

                public String getBaiduCityCode() {
                    return baiduCityCode;
                }

                public void setBaiduCityCode(String baiduCityCode) {
                    this.baiduCityCode = baiduCityCode;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getImgs() {
                    return imgs;
                }

                public void setImgs(String imgs) {
                    this.imgs = imgs;
                }

                public int getServiceCategoryId() {
                    return serviceCategoryId;
                }

                public void setServiceCategoryId(int serviceCategoryId) {
                    this.serviceCategoryId = serviceCategoryId;
                }

                public int getCategoryId() {
                    return categoryId;
                }

                public void setCategoryId(int categoryId) {
                    this.categoryId = categoryId;
                }

                public String getTopExpireTime() {
                    return topExpireTime;
                }

                public void setTopExpireTime(String topExpireTime) {
                    this.topExpireTime = topExpireTime;
                }

                public String getExamineExpireTime() {
                    return examineExpireTime;
                }

                public void setExamineExpireTime(String examineExpireTime) {
                    this.examineExpireTime = examineExpireTime;
                }

                public String getExpireTime() {
                    return expireTime;
                }

                public void setExpireTime(String expireTime) {
                    this.expireTime = expireTime;
                }

                public int getReportCount() {
                    return reportCount;
                }

                public void setReportCount(int reportCount) {
                    this.reportCount = reportCount;
                }

                public String getInformationOrderId() {
                    return informationOrderId;
                }

                public void setInformationOrderId(String informationOrderId) {
                    this.informationOrderId = informationOrderId;
                }

                public int getHasAgainSend() {
                    return hasAgainSend;
                }

                public void setHasAgainSend(int hasAgainSend) {
                    this.hasAgainSend = hasAgainSend;
                }

                public Object getFromInformationId() {
                    return fromInformationId;
                }

                public void setFromInformationId(Object fromInformationId) {
                    this.fromInformationId = fromInformationId;
                }

                public String getShareUrl() {
                    return shareUrl;
                }

                public void setShareUrl(String shareUrl) {
                    this.shareUrl = shareUrl;
                }

                public Object getAgentName() {
                    return agentName;
                }

                public void setAgentName(Object agentName) {
                    this.agentName = agentName;
                }

                public String getCategoryName() {
                    return categoryName;
                }

                public void setCategoryName(String categoryName) {
                    this.categoryName = categoryName;
                }

                public Object getInformationOrder() {
                    return informationOrder;
                }

                public void setInformationOrder(Object informationOrder) {
                    this.informationOrder = informationOrder;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getHouseType() {
                    return houseType;
                }

                public void setHouseType(String houseType) {
                    this.houseType = houseType;
                }

                public String getSectorArea() {
                    return sectorArea;
                }

                public void setSectorArea(String sectorArea) {
                    this.sectorArea = sectorArea;
                }

                public double getHouseArea() {
                    return houseArea;
                }

                public void setHouseArea(double houseArea) {
                    this.houseArea = houseArea;
                }

                public double getAmt() {
                    return amt;
                }

                public void setAmt(double amt) {
                    this.amt = amt;
                }

                public Object getMinAmt() {
                    return minAmt;
                }

                public void setMinAmt(Object minAmt) {
                    this.minAmt = minAmt;
                }

                public Object getMaxAmt() {
                    return maxAmt;
                }

                public void setMaxAmt(Object maxAmt) {
                    this.maxAmt = maxAmt;
                }
            }
        }
    }
}

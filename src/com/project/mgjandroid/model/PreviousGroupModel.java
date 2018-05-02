package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.GroupInfo;

import java.util.List;

/**
 * Created by User_Cjh on 2016/8/24.
 */
public class PreviousGroupModel extends Entity {

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
        private int usergroupBuyCount;
        private GroupBuyUserEntity groupBuyUser;
        private List<GroupInfo> userGroupBuyList;

        public int getUsergroupBuyCount() {
            return usergroupBuyCount;
        }

        public void setUsergroupBuyCount(int usergroupBuyCount) {
            this.usergroupBuyCount = usergroupBuyCount;
        }

        public GroupBuyUserEntity getGroupBuyUser() {
            return groupBuyUser;
        }

        public void setGroupBuyUser(GroupBuyUserEntity groupBuyUser) {
            this.groupBuyUser = groupBuyUser;
        }

        public List<GroupInfo> getUserGroupBuyList() {
            return userGroupBuyList;
        }

        public void setUserGroupBuyList(List<GroupInfo> userGroupBuyList) {
            this.userGroupBuyList = userGroupBuyList;
        }

        public static class GroupBuyUserEntity {
            private int id;
            private String createTime;
            private String modifyTime;
            private String name;
            private String headerImg;
            private String mobile;
            private int sex;
            private String idCardNo;
            private String intro;
            private String address;
            private int benefitUserCount;
            private double compositeAvgScore;
            private double goodsAvgScore;
            private double serviceAvgScore;
            private double totalCommontsCount;
            private Object commontsAllList;

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

            public String getHeaderImg() {
                return headerImg;
            }

            public void setHeaderImg(String headerImg) {
                this.headerImg = headerImg;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getIdCardNo() {
                return idCardNo;
            }

            public void setIdCardNo(String idCardNo) {
                this.idCardNo = idCardNo;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getBenefitUserCount() {
                return benefitUserCount;
            }

            public void setBenefitUserCount(int benefitUserCount) {
                this.benefitUserCount = benefitUserCount;
            }

            public double getCompositeAvgScore() {
                return compositeAvgScore;
            }

            public void setCompositeAvgScore(double compositeAvgScore) {
                this.compositeAvgScore = compositeAvgScore;
            }

            public double getGoodsAvgScore() {
                return goodsAvgScore;
            }

            public void setGoodsAvgScore(double goodsAvgScore) {
                this.goodsAvgScore = goodsAvgScore;
            }

            public double getServiceAvgScore() {
                return serviceAvgScore;
            }

            public void setServiceAvgScore(double serviceAvgScore) {
                this.serviceAvgScore = serviceAvgScore;
            }

            public double getTotalCommontsCount() {
                return totalCommontsCount;
            }

            public void setTotalCommontsCount(double totalCommontsCount) {
                this.totalCommontsCount = totalCommontsCount;
            }

            public Object getCommontsAllList() {
                return commontsAllList;
            }

            public void setCommontsAllList(Object commontsAllList) {
                this.commontsAllList = commontsAllList;
            }
        }
    }
}

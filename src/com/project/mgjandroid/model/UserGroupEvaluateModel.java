package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.GroupUserComments;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by User_Cjh on 2016/8/26.
 */
public class UserGroupEvaluateModel extends Entity {

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
        private BigDecimal compositeAvgScore;
        private BigDecimal goodsAvgScore;
        private BigDecimal serviceAvgScore;
        private BigDecimal totalCommontsCount;
        private List<GroupUserComments> commontsAllList;

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

        public BigDecimal getCompositeAvgScore() {
            return compositeAvgScore;
        }

        public void setCompositeAvgScore(BigDecimal compositeAvgScore) {
            this.compositeAvgScore = compositeAvgScore;
        }

        public BigDecimal getGoodsAvgScore() {
            return goodsAvgScore;
        }

        public void setGoodsAvgScore(BigDecimal goodsAvgScore) {
            this.goodsAvgScore = goodsAvgScore;
        }

        public BigDecimal getServiceAvgScore() {
            return serviceAvgScore;
        }

        public void setServiceAvgScore(BigDecimal serviceAvgScore) {
            this.serviceAvgScore = serviceAvgScore;
        }

        public BigDecimal getTotalCommontsCount() {
            return totalCommontsCount;
        }

        public void setTotalCommontsCount(BigDecimal totalCommontsCount) {
            this.totalCommontsCount = totalCommontsCount;
        }

        public List<GroupUserComments> getCommontsAllList() {
            return commontsAllList;
        }

        public void setCommontsAllList(List<GroupUserComments> commontsAllList) {
            this.commontsAllList = commontsAllList;
        }
    }
}

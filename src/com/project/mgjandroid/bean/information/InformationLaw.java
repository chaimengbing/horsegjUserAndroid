package com.project.mgjandroid.bean.information;

/**
 * Created by pb on 2016-12-16.
 */

public class InformationLaw extends InformationBaseProperty {
    /**
     * 类型1:法律咨询
     */
    private int type = 1;
    /**
     * 头像
     **/
    private String headImg;
    /**
     * 执业多长时间(执业几年)
     */
    private Integer practiceTimeNum;
    /**
     * 姓名
     */
    private String name;
    /**
     * 所在公司
     */
    private String whereCompany;
    /**
     * 执业证号
     */
    private String certificateOfPractice;
    /**
     * 执业时间
     */
    private String practiceTime;
    /**
     * 擅长领域
     */
    private String goodField;
    /** 咨询时间 */
    //private Date consultationTime;
    /**
     * 所在省份
     **/
    private Long whereProvince;
    /**
     * 所在省份名称
     **/
    private String whereProvinceName;
    /**
     * 个人简介
     **/
    private String personalProfile;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWhereCompany() {
        return whereCompany;
    }

    public void setWhereCompany(String whereCompany) {
        this.whereCompany = whereCompany;
    }

    public String getCertificateOfPractice() {
        return certificateOfPractice;
    }

    public void setCertificateOfPractice(String certificateOfPractice) {
        this.certificateOfPractice = certificateOfPractice;
    }

    public String getPracticeTime() {
        return practiceTime;
    }

    public void setPracticeTime(String practiceTime) {
        this.practiceTime = practiceTime;
    }

    /*	public Date getConsultationTime() {
            return consultationTime;
        }
        public void setConsultationTime(Date consultationTime) {
            this.consultationTime = consultationTime;
        }*/
    public String getPersonalProfile() {
        return personalProfile;
    }

    public void setPersonalProfile(String personalProfile) {
        this.personalProfile = personalProfile;
    }

    public String getGoodField() {
        return goodField;
    }

    public void setGoodField(String goodField) {
        this.goodField = goodField;
    }

    public Long getWhereProvince() {
        return whereProvince;
    }

    public void setWhereProvince(Long whereProvince) {
        this.whereProvince = whereProvince;
    }

    public String getWhereProvinceName() {
        return whereProvinceName;
    }

    public void setWhereProvinceName(String whereProvinceName) {
        this.whereProvinceName = whereProvinceName;
    }

    public Integer getPracticeTimeNum() {
        return practiceTimeNum;
    }

    public void setPracticeTimeNum(Integer practiceTimeNum) {
        this.practiceTimeNum = practiceTimeNum;
    }
}

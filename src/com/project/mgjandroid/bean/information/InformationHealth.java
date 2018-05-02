package com.project.mgjandroid.bean.information;

/**
 * Created by yuandi on 2016/11/24.
 */

public class InformationHealth extends InformationBaseProperty {

    /**
     * 类型1:健康咨询
     */
    private int type = 1;
    /**
     * 头像
     **/
    private String headImg;
    /**
     * 姓名
     */
    private String name;
    /**
     * 所在医院
     **/
    private String hospital;
    /**
     * 所在科室
     **/
    private String departments;
    /**
     * 职称
     **/
    private String professionalTitle;
    /**
     * 医生专长
     **/
    private String doctorExpertise;

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

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDepartments() {
        return departments;
    }

    public void setDepartments(String departments) {
        this.departments = departments;
    }

    public String getProfessionalTitle() {
        return professionalTitle;
    }

    public void setProfessionalTitle(String professionalTitle) {
        this.professionalTitle = professionalTitle;
    }

    public String getDoctorExpertise() {
        return doctorExpertise;
    }

    public void setDoctorExpertise(String doctorExpertise) {
        this.doctorExpertise = doctorExpertise;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
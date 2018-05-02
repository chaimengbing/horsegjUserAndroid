package com.project.mgjandroid.bean;

import com.project.mgjandroid.bean.information.InformationBaseProperty;
import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yuandi on 2016/7/22.
 */
public class EducationInformation extends InformationBaseProperty {


    private static final long serialVersionUID = 1L;
    /**
     * 类型 1:教育培训;2:家教
     */
    private int type;
    /**
     * 教师身份  家教
     */
    private Long educationTeacherTypeId;
    /**
     * 辅导阶段  家教
     */
    private Long educationTutorshipStageId;
    /**
     * 机构名称 教育培训
     */
    private String organizationName;
    /**
     * 地址 教育培训
     */
    private String address;
    /**
     * 评分
     */
    private BigDecimal score = new BigDecimal(0.0);
    private String educationTeacherTypeName;
    private String educationTutorshipStageName;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getEducationTeacherTypeId() {
        return educationTeacherTypeId;
    }

    public void setEducationTeacherTypeId(Long educationTeacherTypeId) {
        this.educationTeacherTypeId = educationTeacherTypeId;
    }

    public Long getEducationTutorshipStageId() {
        return educationTutorshipStageId;
    }

    public void setEducationTutorshipStageId(Long educationTutorshipStageId) {
        this.educationTutorshipStageId = educationTutorshipStageId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getEducationTeacherTypeName() {
        return educationTeacherTypeName;
    }

    public void setEducationTeacherTypeName(String educationTeacherTypeName) {
        this.educationTeacherTypeName = educationTeacherTypeName;
    }

    public String getEducationTutorshipStageName() {
        return educationTutorshipStageName;
    }

    public void setEducationTutorshipStageName(String educationTutorshipStageName) {
        this.educationTutorshipStageName = educationTutorshipStageName;
    }

}

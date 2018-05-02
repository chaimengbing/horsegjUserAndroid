package com.project.mgjandroid.bean.information;

/**
 * Created by yuandi on 2016/11/16.
 */

public class PositionInformation extends InformationBaseProperty {

    /**
     * 性别：0：男， 1：女
     **/
    private int sex;
    /**
     * 头像
     **/
    private String headImg;
    /**
     * 地址
     **/
    private String address;
    /**
     * 出生日期
     **/
    private String birthday;
    /**
     * 最高学历
     **/
    private String highestEducation;
    /**
     * 工作经验
     **/
    private String workExperience;
    /**
     * 期望薪资
     **/
    private String expectSalary;
    /**
     * 期望职位
     **/
    private String expectPosition;
    /**
     * 年龄
     **/
    private int age;
    /**
     * 浏览量
     **/
    private String pageViewCount;

    public String getPageViewCount() {
        return pageViewCount;
    }

    public void setPageViewCount(String pageViewCount) {
        this.pageViewCount = pageViewCount;
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

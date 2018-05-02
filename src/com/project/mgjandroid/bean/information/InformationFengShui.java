package com.project.mgjandroid.bean.information;

/**
 * Created by yuandi on 2016/11/24.
 */

public class InformationFengShui extends InformationBaseProperty {

    /**
     * 类型1:风水咨询
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
     * 年龄
     **/
    private String birthday;
    /**
     * 擅长领域
     **/
    private String goodField;
    /**
     * 所在省份
     **/
    private Long whereProvince;
    private String whereProvinceName;
    /**
     * 个人简介
     **/
    private String personalProfile;
    /**
     * 年龄(出生几年,通过出生年份计算)
     */
    private Integer age;

    public String getWhereProvinceName() {
        return whereProvinceName;
    }

    public void setWhereProvinceName(String whereProvinceName) {
        this.whereProvinceName = whereProvinceName;
    }

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public String getPersonalProfile() {
        return personalProfile;
    }

    public void setPersonalProfile(String personalProfile) {
        this.personalProfile = personalProfile;
    }
}
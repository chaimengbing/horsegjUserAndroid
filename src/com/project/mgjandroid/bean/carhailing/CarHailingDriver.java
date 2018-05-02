package com.project.mgjandroid.bean.carhailing;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;

/**
 * Created by User_Cjh on 2016/12/12.
 */
public class CarHailingDriver extends Entity {
    private static final long serialVersionUID = 1L;
    /**
     * 司机编号
     */
    private Long id;
    /**
     * 代理商编号
     */
    private Long agentId;
    /**
     * 做为 用户注册或登录返回uuid做为登录标识。
     */
    private String token;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 姓名
     */
    private String name;
    /**
     * 头像
     */
    private String headerImg = "";
    /**
     * 密码
     */
    private String pwd = "";
    /**
     * 姓名0:女;1:男;2:保密
     */
    private int sex = 2;
    /**
     * 身份证号
     */
    private String certId;
    /**
     * 车牌号
     */
    private String carNumber;
    /**
     * 品牌
     */
    private String carBrands;
    /**
     * 车标
     */
    private String carImgUrl;
    /**
     * 车系
     */
    private String carSeries;
    /**
     * 车型
     */
    private String carType;
    /**
     * 车颜色
     */
    private String carColor;
    /**
     * 人数
     */
    private int carPeopleNum;
    /**
     * 出行次数
     */
    private int tripCount = 0;


    /**
     * 车主认证0:未认证;1:已认证
     */
    private int hasCarOwnerAuthenticate;
    /**
     * 实名认证0:未认证;1:已认证
     */
    private int hasCarRealnameAuthenticate;
    /**
     * 设备mac地址
     */
    private String mac;
    /**
     * 设备厂商 .ios的是apple。android的是厂商标识
     */
    private String brand;
    /**
     * 设备型号
     */
    private String model;
    /**
     * 设备IMEI或apple设备的uuid
     */
    private String imei;
    /**
     * 登录时IP地址
     */
    private String ip;
    /**
     * 客户端名称apple或android
     */
    private String client;
    /**
     * 推送编号
     */
    private String clientId;
    /**
     * apple设备。正式环境true,开发环境false
     */
    private Boolean apnsProduction;
    /**
     * 项目名称，也可称为包名，推送时有可能要区分不同的包进行推送
     */
    private String app;
    private String clientVersion;
    /**
     * 是否已删除
     */
    private int hasDel;
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 维度
     */
    private Double latitude;
    /**
     * GeoHash
     */
    private String geohash;

    /**
     * 开户行
     */
    private String bankName = "";
    /**
     * 银行卡号
     */
    private String bankCard = "";
    /**
     * 开户人
     */
    private String bankPerson = "";
    /**
     * 代理商佣金比率
     */
    private BigDecimal agentRate = BigDecimal.ZERO;
    /**
     * 平均分
     */
    private BigDecimal averageScore = new BigDecimal(0.0);
    /**
     * 服务人数
     */
    private Integer servicePeopleNumber;

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarBrands() {
        return carBrands;
    }

    public void setCarBrands(String carBrands) {
        this.carBrands = carBrands;
    }

    public String getCarSeries() {
        return carSeries;
    }

    public void setCarSeries(String carSeries) {
        this.carSeries = carSeries;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public int getCarPeopleNum() {
        return carPeopleNum;
    }

    public void setCarPeopleNum(int carPeopleNum) {
        this.carPeopleNum = carPeopleNum;
    }

    public int getHasCarOwnerAuthenticate() {
        return hasCarOwnerAuthenticate;
    }

    public void setHasCarOwnerAuthenticate(int hasCarOwnerAuthenticate) {
        this.hasCarOwnerAuthenticate = hasCarOwnerAuthenticate;
    }

    public int getHasCarRealnameAuthenticate() {
        return hasCarRealnameAuthenticate;
    }

    public void setHasCarRealnameAuthenticate(int hasCarRealnameAuthenticate) {
        this.hasCarRealnameAuthenticate = hasCarRealnameAuthenticate;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Boolean getApnsProduction() {
        return apnsProduction;
    }

    public void setApnsProduction(Boolean apnsProduction) {
        this.apnsProduction = apnsProduction;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getGeohash() {
        return geohash;
    }

    public void setGeohash(String geohash) {
        this.geohash = geohash;
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

    public BigDecimal getAgentRate() {
        return agentRate;
    }

    public void setAgentRate(BigDecimal agentRate) {
        this.agentRate = agentRate;
    }

    public BigDecimal getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(BigDecimal averageScore) {
        this.averageScore = averageScore;
    }

    public Integer getServicePeopleNumber() {
        return servicePeopleNumber;
    }

    public void setServicePeopleNumber(Integer servicePeopleNumber) {
        this.servicePeopleNumber = servicePeopleNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarImgUrl() {
        return carImgUrl;
    }

    public void setCarImgUrl(String carImgUrl) {
        this.carImgUrl = carImgUrl;
    }

    public int getTripCount() {
        return tripCount;
    }

    public void setTripCount(int tripCount) {
        this.tripCount = tripCount;
    }
}

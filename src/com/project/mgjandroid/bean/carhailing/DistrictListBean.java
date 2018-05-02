package com.project.mgjandroid.bean.carhailing;

import com.project.mgjandroid.model.Entity;

public class DistrictListBean extends Entity {
    private String createTime;
    private Object startcarTripId;
    private String cityName;
    private int agentId;
    private String provinceName;
    private int type;
    private Object carpoolPrice;
    private long city;
    private long id;
    private String districtName;
    private long province;
    private Object charteredlPrice;
    private long district;
    private String modifyTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Object getStartcarTripId() {
        return startcarTripId;
    }

    public void setStartcarTripId(Object startcarTripId) {
        this.startcarTripId = startcarTripId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getCarpoolPrice() {
        return carpoolPrice;
    }

    public void setCarpoolPrice(Object carpoolPrice) {
        this.carpoolPrice = carpoolPrice;
    }

    public long getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public long getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public Object getCharteredlPrice() {
        return charteredlPrice;
    }

    public void setCharteredlPrice(Object charteredlPrice) {
        this.charteredlPrice = charteredlPrice;
    }

    public long getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }
}
package com.project.mgjandroid.bean.carhailing;

import com.project.mgjandroid.model.Entity;

import java.util.List;

public class CityListBean extends Entity {
    private int type;
    private String cityName;
    private int city;

    private List<DistrictListBean> districtList;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public List<DistrictListBean> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<DistrictListBean> districtList) {
        this.districtList = districtList;
    }
}
package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by rjp on 2016/6/23.
 * Email:rjpgoodjob@gmail.com
 */
public class CityBean extends Entity {

    private String baiduCityCode;
    private int id;
    private int parentId;
    private String name;
    private int level;
    private int hasMerchant;
    private BigDecimal longitude;
    private BigDecimal latitude;

    private List<AreaBean> childCityList;

    public String getBaiduCityCode() {
        return baiduCityCode;
    }

    public void setBaiduCityCode(String baiduCityCode) {
        this.baiduCityCode = baiduCityCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AreaBean> getChildCityList() {
        return childCityList;
    }

    public void setChildCityList(List<AreaBean> childCityList) {
        this.childCityList = childCityList;
    }

    public int getHasMerchant() {
        return hasMerchant;
    }

    public void setHasMerchant(int hasMerchant) {
        this.hasMerchant = hasMerchant;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
}

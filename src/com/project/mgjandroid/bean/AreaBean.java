package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;

/**
 * Created by rjp on 2016/6/23.
 * Email:rjpgoodjob@gmail.com
 */
public class AreaBean extends Entity {
    private String baiduCityCode;
    private int id;
    private int parentId;
    private String name;
    private int level;
    private int hasMerchant;
    private BigDecimal longitude;
    private BigDecimal latitude;

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

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
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
}

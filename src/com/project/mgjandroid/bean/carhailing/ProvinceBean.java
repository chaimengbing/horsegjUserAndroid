package com.project.mgjandroid.bean.carhailing;

import com.project.mgjandroid.model.Entity;

import java.util.List;

/**
 * Created by User_Cjh on 2016/12/15.
 */
public class ProvinceBean extends Entity {
    private int province;
    private String provinceName;
    private int type;

    private List<CityListBean> cityList;

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
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

    public List<CityListBean> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityListBean> cityList) {
        this.cityList = cityList;
    }
}

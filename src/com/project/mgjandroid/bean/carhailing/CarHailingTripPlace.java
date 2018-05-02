package com.project.mgjandroid.bean.carhailing;

import com.project.mgjandroid.model.Entity;

/**
 * Created by User_Cjh on 2016/12/13.
 */
public class CarHailingTripPlace extends Entity {
    private long carTripDetailId;
    private String address;

    public long getCarTripDetailId() {
        return carTripDetailId;
    }

    public void setCarTripDetailId(long carTripDetailId) {
        this.carTripDetailId = carTripDetailId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

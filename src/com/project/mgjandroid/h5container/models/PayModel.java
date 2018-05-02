package com.project.mgjandroid.h5container.models;

import java.io.Serializable;

/**
 * Created by User_Cjh on 2017/12/27.
 */

public class PayModel implements Serializable {
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}

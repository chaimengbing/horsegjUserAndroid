package com.project.mgjandroid.bean.information;

/**
 * Created by yuandi on 2016/11/16.
 */

public enum OrderStatus {

    Cancel(-1, "取消订单"),

    WaitPay(0, "待支付"),

    WaitCheck(1, "等待审核"),

    CheckSuccess(2, "审核通过"),

    CheckFail(3, "审核失败");

    private int value;

    private String name;

    OrderStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static OrderStatus getOrderStatusByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (OrderStatus type : OrderStatus.values()) {
            if (type.getValue() == value.intValue()) {
                return type;
            }
        }
        return null;
    }

}

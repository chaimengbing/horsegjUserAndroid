package com.project.mgjandroid.bean.information;

/**
 * Created by yuandi on 2016/11/16.
 */

public enum OrderType {

    SendInformation(1, "发布支付"),

    TopInformation(2, "置顶支付"),

    LookInformation(3, "查看支付"),

    RefreshInformation(4, "刷新支付");

    private int value;

    private String name;

    OrderType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static OrderType getOrderTypeByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (OrderType type : OrderType.values()) {
            if (type.getValue() == value.intValue()) {
                return type;
            }
        }
        return null;
    }

}

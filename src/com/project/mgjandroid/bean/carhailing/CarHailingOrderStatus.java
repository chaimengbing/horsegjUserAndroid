package com.project.mgjandroid.bean.carhailing;

/**
 * Created by User_Cjh on 2016/12/12.
 */
public enum CarHailingOrderStatus {
    Cancel(-1, "已取消"),

    Init(0, "订单创建"),

    WaitPay(1, "等待付款"),

    WaitDepart(2, "等待发车"),

    WaitAboard(3, "等待乘客上车"),

    Aboard(4, "等待送达"),

    Arrive(5, "等待乘客确认"),

    Done(6, "已完成");

    private int value;

    private String name;

    CarHailingOrderStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static CarHailingOrderStatus getOrderTypeByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (CarHailingOrderStatus type : CarHailingOrderStatus.values()) {
            if (type.getValue() == value.intValue()) {
                return type;
            }
        }
        return null;
    }
}

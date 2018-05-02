package com.project.mgjandroid.bean;

/**
 * Created by User_Cjh on 2016/12/12.
 */
public enum UserOrderType {
    Takeaway(1, "外卖"),

    Groupbuy(2, "拼团"),

    Shop(3, "商超"),

    Car(4, "约车"),

    GroupPurchase(6, "团购");

    private int value;

    private String name;

    UserOrderType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static UserOrderType getOrderTypeByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (UserOrderType type : UserOrderType.values()) {
            if (type.getValue() == value.intValue()) {
                return type;
            }
        }
        return null;
    }
}

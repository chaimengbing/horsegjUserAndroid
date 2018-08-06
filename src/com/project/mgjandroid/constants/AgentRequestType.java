package com.project.mgjandroid.constants;

/**
 * Created by User_Cjh on 2017/11/15.
 */

public enum AgentRequestType {


    Default(0, "默认"),

    Takeaway(1, "外卖"),

    Groupbuy(2, "拼团"),

    Shop(3, "商超"),

    Car(4, "约车"),

    Information(5, "信息发布"),

    GroupPurchase(6, "团购"),

    Hitchhiking(7,"顺风车"),

    VisualAgriculture(8,"可视认养"),

    LegWork(9,"跑腿"),

    Express(10,"快递"),

    Laundry(11,"洗衣");

    private int value;

    private String name;

    AgentRequestType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static AgentRequestType getOrderTypeByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (AgentRequestType type : AgentRequestType.values()) {
            if (type.getValue() == value.intValue()) {
                return type;
            }
        }
        return null;
    }
}

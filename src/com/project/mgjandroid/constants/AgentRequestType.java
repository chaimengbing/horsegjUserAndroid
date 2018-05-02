package com.project.mgjandroid.constants;

/**
 * Created by User_Cjh on 2017/11/15.
 */

public enum AgentRequestType {
    Takeaway(1, "外卖"),

    Groupbuy(2, "拼团"),

    Shop(3, "商超"),

    Car(4, "约车拼车"),

    PositionRecruit(5, "求职招聘"),

    GroupPurchase(6, "团购"),

    Secondhand(7, "二手信息"),

    Lease(8, "房屋租售"),

    Education(9, "教育培训"),

    Homemaking(10, "家政服务"),

    Repair(11, "维修服务"),

    WasteRecovery(12, "废品回收"),

    Law(13, "法律咨询"),

    Health(14, "健康咨询"),

    Divination(15, "风水咨询");

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

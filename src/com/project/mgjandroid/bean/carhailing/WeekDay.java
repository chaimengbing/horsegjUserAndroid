package com.project.mgjandroid.bean.carhailing;

/**
 * Created by User_Cjh on 2016/12/12.
 */
public enum WeekDay {
    Monday(2, "周一"),

    Tuesday(3, "周二"),

    Wednesday(4, "周三"),

    Thursday(5, "周四"),

    Friday(6, "周五"),

    Saturday(7, "周六"),

    Sunday(1, "周日");

    private int value;

    private String name;

    WeekDay(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static WeekDay getOrderTypeByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (WeekDay type : WeekDay.values()) {
            if (type.getValue() == value.intValue()) {
                return type;
            }
        }
        return null;
    }
}

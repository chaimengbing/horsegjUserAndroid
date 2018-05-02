package com.project.mgjandroid.bean.information;

/**
 * Created by pb on 2016-12-16.
 */

public enum InformationOrderStatus {
    Cancel(-1, "已取消"),

    Init(0, "订单创建"),

    WaitPay(1, "未支付"),

    WaitCheck(2, "已支付"),

    Done(3, "已支付"),

    Refund(4, "已退款");

    private int value;

    private String memo;

    InformationOrderStatus(int value, String memo) {
        this.value = value;
        this.memo = memo;
    }

    public int getValue() {
        return value;
    }

    public String getMemo() {
        return memo;
    }

    public static InformationOrderStatus getInformationOrderStatusByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (InformationOrderStatus type : InformationOrderStatus.values()) {
            if (type.getValue() == value.intValue()) {
                return type;
            }
        }
        return null;
    }
}

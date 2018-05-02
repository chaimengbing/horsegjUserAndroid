package com.project.mgjandroid.bean.groupbuying;

/**
 * Created by yuandi on 2017/3/15.
 */

public enum GroupPurchaseOrderStatus {
    Cancel(-1, "取消订单"),

    Init(0, "订单创建"),

    WaitPay(1, "等待付款"),

    Done(2, "支付完成"),

    Refund(3, "已退款");

    private int value;
    private String memo;

    GroupPurchaseOrderStatus(int value, String memo) {
        this.value = value;
        this.memo = memo;
    }

    public int getValue() {
        return value;
    }

    public String getMemo() {
        return memo;
    }

    public static GroupPurchaseOrderStatus getGroupPurchaseCouponTypeByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (GroupPurchaseOrderStatus type : GroupPurchaseOrderStatus.values()) {
            if (type.getValue() == value.intValue()) {
                return type;
            }
        }
        return null;
    }

}

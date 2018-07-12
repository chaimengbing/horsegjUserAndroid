package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.RedBag;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by yuandi on 2016/5/27.
 */
public class ConfirmGroupOrModel extends Entity {

    /**
     * platformRedBags：使用的红包（集合）
     * platformRedBagCount：可使用红包数
     * redBagsTotalAmt：红包优惠金额
     * totalPrice：最终支付金额
     */

    private int platformRedBagCount;
    private ArrayList<RedBag> platformRedBags;
    private BigDecimal totalPrice;
    private BigDecimal redBagsTotalAmt;


    public ArrayList<RedBag> getPlatformRedBagList() {
        return platformRedBags;
    }

    public void setPlatformRedBagList(ArrayList<RedBag> platformRedBagList) {
        this.platformRedBags = platformRedBagList;
    }

    public int getPlatformRedBagCount() {
        return platformRedBagCount;
    }

    public void setPlatformRedBagCount(int platformRedBagCount) {
        this.platformRedBagCount = platformRedBagCount;
    }


    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getRedBagsTotalAmt() {
        return redBagsTotalAmt;
    }

    public void setRedBagsTotalAmt(BigDecimal redBagsTotalAmt) {
        this.redBagsTotalAmt = redBagsTotalAmt;
    }
}

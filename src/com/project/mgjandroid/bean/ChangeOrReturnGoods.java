package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

/**
 * Created by User_Cjh on 2016/9/23.
 */
public class ChangeOrReturnGoods extends Entity {

    /*orderId 订单编号
        orderItemId 商品编号
        type 0 为退货  1 为换货
        amt 退换数量
        reason 退换理由
        imgs   图片地址串，分号隔开*/
    private String orderId;
    private String reason;
    private String imgs;
    private String paths;
    private long orderItemId;
    private int type;
    private int amt;
    private int position;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAmt() {
        return amt;
    }

    public void setAmt(int amt) {
        this.amt = amt;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getPaths() {
        return paths;
    }

    public void setPaths(String paths) {
        this.paths = paths;
    }
}

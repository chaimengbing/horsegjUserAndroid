package com.project.mgjandroid.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by yuandi on 2016/3/9.
 */
public class MerchantTakeAwayMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Menu> menu;
    private List<Order> orderList;
    private Date servertime;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public Date getServertime() {
        return servertime;
    }

    public void setServertime(Date servertime) {
        this.servertime = servertime;
    }
}

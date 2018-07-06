package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.RedBag;

import java.util.ArrayList;

/**
 * Created by yuandi on 2016/5/27.
 */
public class RedBagListModel extends Entity {

    /**
     * "platformRedBagCount": 0,
     * "vouchersList": [{
     * }, ],
     * "platformRedBagList": [],
     * "vouchersCount": 10
     */

    private int platformRedBagCount;
    private int vouchersCount;

    //代金券
    private ArrayList<RedBag> vouchersList;
    //平台红包
    private ArrayList<RedBag> platformRedBagList;


    public ArrayList<RedBag> getVouchersList() {
        return vouchersList;
    }

    public void setVouchersList(ArrayList<RedBag> vouchersList) {
        this.vouchersList = vouchersList;
    }

    public ArrayList<RedBag> getPlatformRedBagList() {
        return platformRedBagList;
    }

    public void setPlatformRedBagList(ArrayList<RedBag> platformRedBagList) {
        this.platformRedBagList = platformRedBagList;
    }

    public int getPlatformRedBagCount() {
        return platformRedBagCount;
    }

    public void setPlatformRedBagCount(int platformRedBagCount) {
        this.platformRedBagCount = platformRedBagCount;
    }

    public int getVouchersCount() {
        return vouchersCount;
    }

    public void setVouchersCount(int vouchersCount) {
        this.vouchersCount = vouchersCount;
    }

    /**
     * "redBagList": null,
     "type": 1
     */
    //平台红包
    private ArrayList<RedBag> redBagList;
    private int type = 0;

    public ArrayList<RedBag> getRedBagList() {
        return redBagList;
    }

    public void setRedBagList(ArrayList<RedBag> redBagList) {
        this.redBagList = redBagList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

package com.project.mgjandroid.model.carhailing;

import com.project.mgjandroid.bean.Promotion;
import com.project.mgjandroid.bean.RedBag;
import com.project.mgjandroid.model.Entity;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yuandi on 2017/2/27.
 */

public class CarFirstOrderActivityModel extends Entity {
    private int code;
    private String uuid;
    private boolean success;
    private Date servertime;
    private ValueEntity value;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Date getServertime() {
        return servertime;
    }

    public void setServertime(Date servertime) {
        this.servertime = servertime;
    }

    public ValueEntity getValue() {
        return value;
    }

    public void setValue(ValueEntity value) {
        this.value = value;
    }

    public static class ValueEntity extends Entity {

        private ArrayList<RedBag> redBagList;

        private Promotion carFirstOrder;

        public ArrayList<RedBag> getRedBagList() {
            return redBagList;
        }

        public void setRedBagList(ArrayList<RedBag> redBagList) {
            this.redBagList = redBagList;
        }

        public Promotion getCarFirstOrder() {
            return carFirstOrder;
        }

        public void setCarFirstOrder(Promotion carFirstOrder) {
            this.carFirstOrder = carFirstOrder;
        }
    }
}

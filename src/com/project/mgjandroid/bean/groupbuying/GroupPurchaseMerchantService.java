package com.project.mgjandroid.bean.groupbuying;

import com.project.mgjandroid.model.Entity;

/**
 * Created by yuandi on 2017/3/11.
 */

public class GroupPurchaseMerchantService extends Entity {

    private String name;

    private int value;

    private boolean selected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

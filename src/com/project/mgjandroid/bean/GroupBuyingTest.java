package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

/**
 * Created by pb on 2017-03-13.
 */

public class GroupBuyingTest extends Entity {
    private String str;
    private String count;
    private boolean Selected;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }
}

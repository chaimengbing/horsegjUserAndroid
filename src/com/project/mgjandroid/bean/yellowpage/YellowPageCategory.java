package com.project.mgjandroid.bean.yellowpage;

import com.project.mgjandroid.model.Entity;

/**
 * Created by User_Cjh on 2017/6/13.
 */

public class YellowPageCategory extends Entity {
    private String picUrl;
    private String name;
    private int value;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

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
}

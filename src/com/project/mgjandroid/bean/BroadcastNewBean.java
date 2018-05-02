package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

/**
 * Created by yuandi on 2016/9/22.
 */
public class BroadcastNewBean extends Entity {


    /**
     * titleType : 1
     * hotTitle : 热门1
     * concernTitle : 关注1
     */

    private int titleType;
    private String hotTitle;
    private String concernTitle;

    public int getTitleType() {
        return titleType;
    }

    public void setTitleType(int titleType) {
        this.titleType = titleType;
    }

    public String getHotTitle() {
        return hotTitle;
    }

    public void setHotTitle(String hotTitle) {
        this.hotTitle = hotTitle;
    }

    public String getConcernTitle() {
        return concernTitle;
    }

    public void setConcernTitle(String concernTitle) {
        this.concernTitle = concernTitle;
    }
}

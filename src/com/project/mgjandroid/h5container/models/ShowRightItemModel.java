package com.project.mgjandroid.h5container.models;

import java.io.Serializable;

/**
 * Created by User_Cjh on 2018/1/4.
 */

public class ShowRightItemModel implements Serializable {
    private boolean isShow;//0 不显示 1 显示
    private String iconType;//图片类型 1:分享  2:搜索  3:更多
    private String message;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

}

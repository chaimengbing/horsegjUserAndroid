package com.project.mgjandroid.h5container.utils;

import java.io.Serializable;

/**
 * Created by User_Cjh on 2017/11/25.
 */

public class H5ResultModel implements Serializable {

    private int code;

    private Object value;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

package com.project.mgjandroid.h5container.models;

import java.io.Serializable;

/**
 * Created by User_Cjh on 2017/11/25.
 */

public class ErrorModel implements Serializable {

    public ErrorModel() {
    }

    public ErrorModel(int code, String value) {
        this.code = code;
        this.value = value;
    }

    private int code;

    private String value;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

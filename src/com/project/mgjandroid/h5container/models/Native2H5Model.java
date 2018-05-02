package com.project.mgjandroid.h5container.models;


import java.io.Serializable;

public class Native2H5Model implements Serializable {

    private int code; // 返回状态  00：未处理， 01：成功， 02：失败， 03：处理中， 04：取消。

    private String value; // app执行后的返回信息，传给H5app

    public Native2H5Model(int code, String value) {
        this.code = code;
        this.value = value;
    }

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

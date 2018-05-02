package com.project.mgjandroid.h5container.models;


import java.io.Serializable;

public class NativeH5Model<T> implements Serializable {

    private int code; // 返回状态  00：未处理， 01：成功， 02：失败， 03：处理中， 04：取消。

    private T value; // app执行后的返回信息，传给H5app

    public NativeH5Model(int code, T value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}

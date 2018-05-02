package com.project.mgjandroid.bean.newhomepage;

import com.project.mgjandroid.bean.RecommendCategory;
import com.project.mgjandroid.model.Entity;

import java.util.List;

/**
 * Created by SunXueLiang on 2017-09-08.
 */

public class NewRecommendCategory extends Entity {
    private int code;
    private String uuid;
    private boolean success;
    private String servertime;
    private List<GoodsDetail> value;

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

    public String getServertime() {
        return servertime;
    }

    public void setServertime(String servertime) {
        this.servertime = servertime;
    }

    public List<GoodsDetail> getValue() {
        return value;
    }

    public void setValue(List<GoodsDetail> value) {
        this.value = value;
    }
}

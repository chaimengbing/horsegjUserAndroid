package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.util.Date;

/**
 * Created by yuandi on 2017/3/10.
 */

public class BaseBean extends Entity {

    private Long id;

    private Date createTime;

    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}

package com.project.mgjandroid.bean.information;

import com.project.mgjandroid.model.Entity;

/**
 * Created by rjp on 2016/7/12.
 * Email:rjpgoodjob@gmail.com
 */
public class InformationBanner extends Entity {

    private int id;
    private String createTime;
    private String modifyTime;
    private String name;
    private int agentId;
    private String picUrl;
    private String url;
    private int sortNo;
    private int hasDel;
    private int informationType;
    private Object agent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public int getInformationType() {
        return informationType;
    }

    public void setInformationType(int informationType) {
        this.informationType = informationType;
    }

    public Object getAgent() {
        return agent;
    }

    public void setAgent(Object agent) {
        this.agent = agent;
    }
}

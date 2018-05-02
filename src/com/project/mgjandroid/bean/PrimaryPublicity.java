package com.project.mgjandroid.bean;

/**
 * Created by yuandi on 2016/9/22.
 */
public class PrimaryPublicity extends BaseProperty {

    /**
     * 代理商编号
     **/
    private Long agentId;
    /**
     * 推广位置名
     **/
    private String name;
    /**
     * 推荐头部展示标题
     **/
    private String title;
    /**
     * 图片地址
     **/
    private String img;
    /**
     * 状态0：未启动，1：已启动
     **/
    private int status;
    /**
     * 排序序号
     **/
    private Integer sortNo;
    /**
     * 删除状态0：未删除，1：已删除
     **/
    private int hasDel;
    /**
     * 推广类型 1 静态横屏广告, 2 推广位置
     **/
    private Integer publicityType;

    private Agent agent;

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Integer getPublicityType() {
        return publicityType;
    }

    public void setPublicityType(Integer publicityType) {
        this.publicityType = publicityType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

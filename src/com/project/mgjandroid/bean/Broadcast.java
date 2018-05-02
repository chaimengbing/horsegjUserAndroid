package com.project.mgjandroid.bean;

/**
 * Created by yuandi on 2016/9/22.
 */
public class Broadcast extends BaseProperty {

    /**
     * 代理商id
     **/
    private Long agentId;
    /**
     * 标题
     **/
    private String title;
    /**
     * 是否删除0：未删除，1：已删除
     **/
    private int hasDel;
    /**
     * 排序序号
     **/
    private Integer sortNo;

    private Agent agent;

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

}

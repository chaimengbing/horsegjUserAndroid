package com.project.mgjandroid.bean.newhomepage;

import com.project.mgjandroid.model.Entity;

/**
 * Created by SunXueLiang on 2017-09-08.
 */

public class User extends Entity {
    private int id;
    private String createTime;
    private String modifyTime;
    private int agentId;
    private String userAndAgentTime;
    private String name;
    private String mobile;
    private String pwd;
    private String headerImg;
    private String regTime;
    private String lastLoginTime;
    private String channel;
    private Object token;
    private Object city;
    private Object inviterAppUserId;
    private Object userToken;
    private Object userAccount;
    private Object totalAmt;
    private Object agent;
    private Object inviter;

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

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getUserAndAgentTime() {
        return userAndAgentTime;
    }

    public void setUserAndAgentTime(String userAndAgentTime) {
        this.userAndAgentTime = userAndAgentTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getInviterAppUserId() {
        return inviterAppUserId;
    }

    public void setInviterAppUserId(Object inviterAppUserId) {
        this.inviterAppUserId = inviterAppUserId;
    }

    public Object getUserToken() {
        return userToken;
    }

    public void setUserToken(Object userToken) {
        this.userToken = userToken;
    }

    public Object getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(Object userAccount) {
        this.userAccount = userAccount;
    }

    public Object getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(Object totalAmt) {
        this.totalAmt = totalAmt;
    }

    public Object getAgent() {
        return agent;
    }

    public void setAgent(Object agent) {
        this.agent = agent;
    }

    public Object getInviter() {
        return inviter;
    }

    public void setInviter(Object inviter) {
        this.inviter = inviter;
    }
}

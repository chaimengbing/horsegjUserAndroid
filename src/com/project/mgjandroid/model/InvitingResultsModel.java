package com.project.mgjandroid.model;

import java.util.Date;
import java.util.List;

/**
 * Created by SunXueLiang on 2017-07-26.
 */

public class InvitingResultsModel extends Entity {

    private int code;
    private String uuid;
    private ValueBean value;
    private boolean success;
    private String servertime;

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

    public ValueBean getValue() {
        return value;
    }

    public void setValue(ValueBean value) {
        this.value = value;
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

    public static class ValueBean extends Entity {


        private double cashbackAmtSum;
        private List<UserListBean> userList;

        public double getCashbackAmtSum() {
            return cashbackAmtSum;
        }

        public void setCashbackAmtSum(double cashbackAmtSum) {
            this.cashbackAmtSum = cashbackAmtSum;
        }

        public List<UserListBean> getUserList() {
            return userList;
        }

        public void setUserList(List<UserListBean> userList) {
            this.userList = userList;
        }

        public static class UserListBean extends Entity {

            private int id;
            private Date createTime;
            private Date modifyTime;
            private Object agentId;
            private Object userAndAgentTime;
            private String name;
            private String mobile;
            private String pwd;
            private String headerImg;
            private String regTime;
            private String lastLoginTime;
            private String channel;
            private Object token;
            private Object city;
            private int inviterAppUserId;
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

            public Object getAgentId() {
                return agentId;
            }

            public void setAgentId(Object agentId) {
                this.agentId = agentId;
            }

            public Object getUserAndAgentTime() {
                return userAndAgentTime;
            }

            public void setUserAndAgentTime(Object userAndAgentTime) {
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

            public int getInviterAppUserId() {
                return inviterAppUserId;
            }

            public void setInviterAppUserId(int inviterAppUserId) {
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
    }
}

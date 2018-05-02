package com.project.mgjandroid.model;

import java.util.List;

/**
 * Created by SunXueLiang on 2017-07-26.
 */

public class CashBackListModel extends Entity {

    private int code;
    private String uuid;
    private boolean success;
    private String servertime;
    private List<ValueBean> value;

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

    public List<ValueBean> getValue() {
        return value;
    }

    public void setValue(List<ValueBean> value) {
        this.value = value;
    }

    public static class ValueBean extends Entity {

        private int id;
        private String createTime;
        private String modifyTime;
        private int agentId;
        private String orderId;
        private int orderType;
        private int inviterId;
        private int inviteeId;
        private String inviterMobile;
        private String inviteeMobile;
        private double cashbackRate;
        private double cashbackAmt;
        private int inviteCashbackActivityId;
        private Object agent;
        private InviteeAppUserBean inviteeAppUser;

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

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public int getInviterId() {
            return inviterId;
        }

        public void setInviterId(int inviterId) {
            this.inviterId = inviterId;
        }

        public int getInviteeId() {
            return inviteeId;
        }

        public void setInviteeId(int inviteeId) {
            this.inviteeId = inviteeId;
        }

        public String getInviterMobile() {
            return inviterMobile;
        }

        public void setInviterMobile(String inviterMobile) {
            this.inviterMobile = inviterMobile;
        }

        public String getInviteeMobile() {
            return inviteeMobile;
        }

        public void setInviteeMobile(String inviteeMobile) {
            this.inviteeMobile = inviteeMobile;
        }

        public double getCashbackRate() {
            return cashbackRate;
        }

        public void setCashbackRate(double cashbackRate) {
            this.cashbackRate = cashbackRate;
        }

        public double getCashbackAmt() {
            return cashbackAmt;
        }

        public void setCashbackAmt(double cashbackAmt) {
            this.cashbackAmt = cashbackAmt;
        }

        public int getInviteCashbackActivityId() {
            return inviteCashbackActivityId;
        }

        public void setInviteCashbackActivityId(int inviteCashbackActivityId) {
            this.inviteCashbackActivityId = inviteCashbackActivityId;
        }

        public Object getAgent() {
            return agent;
        }

        public void setAgent(Object agent) {
            this.agent = agent;
        }

        public InviteeAppUserBean getInviteeAppUser() {
            return inviteeAppUser;
        }

        public void setInviteeAppUser(InviteeAppUserBean inviteeAppUser) {
            this.inviteeAppUser = inviteeAppUser;
        }

        public static class InviteeAppUserBean extends Entity {

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

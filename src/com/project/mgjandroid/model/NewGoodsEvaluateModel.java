package com.project.mgjandroid.model;

import java.math.BigDecimal;
import java.util.List;

public class NewGoodsEvaluateModel extends Entity{

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

    public static class ValueBean extends Entity{

        private int poorCount;
        private int goodCount;
        private int allCount;
        private int imgCount;
        private List<ListBean> list;

        public int getPoorCount() {
            return poorCount;
        }

        public void setPoorCount(int poorCount) {
            this.poorCount = poorCount;
        }

        public int getGoodCount() {
            return goodCount;
        }

        public void setGoodCount(int goodCount) {
            this.goodCount = goodCount;
        }

        public int getAllCount() {
            return allCount;
        }

        public void setAllCount(int allCount) {
            this.allCount = allCount;
        }

        public int getImgCount() {
            return imgCount;
        }

        public void setImgCount(int imgCount) {
            this.imgCount = imgCount;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean extends Entity{
            /**
             * id : 6290
             * createTime : 2018-11-07 16:58:56
             * modifyTime : 2018-11-07 16:58:56
             * orderId : 1811070000255308
             * goodsId : 296
             * userId : 941
             * orderCommentsId : 5517
             * goodsScore : 1.0
             * goodsScoreComments : 反反复复
             * imgUrl : http://7xu5hi.com1.z0.glb.clouddn.com/20181107045833218.jpg
             * isImg : 1
             * isAnonymous : 1
             * appUser : {"id":941,"createTime":null,"modifyTime":null,"agentId":null,"userAndAgentTime":null,"name":"D2FqV2Ns9wbs","mobile":"19999999999","pwd":"","headerImg":"","regTime":null,"lastLoginTime":null,"channel":null,"token":null,"city":null,"inviterAppUserId":null,"userToken":null,"userAccount":null,"totalAmt":null,"agent":null,"inviter":null,"cashbackCount":null,"redBagCount":null,"couponsCount":null,"balance":null}
             * replyContent : null
             * goodsName : null
             */

            private int id;
            private String createTime;
            private String modifyTime;
            private String orderId;
            private int goodsId;
            private int userId;
            private int orderCommentsId;
            private BigDecimal goodsScore;
            private String goodsScoreComments;
            private String imgUrl;
            private int isImg;
            private int isAnonymous;
            private AppUserBean appUser;
            private String replyContent;
            private Object goodsName;

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

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getOrderCommentsId() {
                return orderCommentsId;
            }

            public void setOrderCommentsId(int orderCommentsId) {
                this.orderCommentsId = orderCommentsId;
            }

            public BigDecimal getGoodsScore() {
                return goodsScore;
            }

            public void setGoodsScore(BigDecimal goodsScore) {
                this.goodsScore = goodsScore;
            }

            public String getGoodsScoreComments() {
                return goodsScoreComments;
            }

            public void setGoodsScoreComments(String goodsScoreComments) {
                this.goodsScoreComments = goodsScoreComments;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public int getIsImg() {
                return isImg;
            }

            public void setIsImg(int isImg) {
                this.isImg = isImg;
            }

            public int getIsAnonymous() {
                return isAnonymous;
            }

            public void setIsAnonymous(int isAnonymous) {
                this.isAnonymous = isAnonymous;
            }

            public AppUserBean getAppUser() {
                return appUser;
            }

            public void setAppUser(AppUserBean appUser) {
                this.appUser = appUser;
            }

            public String getReplyContent() {
                return replyContent;
            }

            public void setReplyContent(String replyContent) {
                this.replyContent = replyContent;
            }

            public Object getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(Object goodsName) {
                this.goodsName = goodsName;
            }

            public static class AppUserBean extends Entity{
                /**
                 * id : 941
                 * createTime : null
                 * modifyTime : null
                 * agentId : null
                 * userAndAgentTime : null
                 * name : D2FqV2Ns9wbs
                 * mobile : 19999999999
                 * pwd :
                 * headerImg :
                 * regTime : null
                 * lastLoginTime : null
                 * channel : null
                 * token : null
                 * city : null
                 * inviterAppUserId : null
                 * userToken : null
                 * userAccount : null
                 * totalAmt : null
                 * agent : null
                 * inviter : null
                 * cashbackCount : null
                 * redBagCount : null
                 * couponsCount : null
                 * balance : null
                 */

                private int id;
                private Object createTime;
                private Object modifyTime;
                private Object agentId;
                private Object userAndAgentTime;
                private String name;
                private String mobile;
                private String pwd;
                private String headerImg;
                private Object regTime;
                private Object lastLoginTime;
                private Object channel;
                private Object token;
                private Object city;
                private Object inviterAppUserId;
                private Object userToken;
                private Object userAccount;
                private Object totalAmt;
                private Object agent;
                private Object inviter;
                private Object cashbackCount;
                private Object redBagCount;
                private Object couponsCount;
                private Object balance;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public Object getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(Object createTime) {
                    this.createTime = createTime;
                }

                public Object getModifyTime() {
                    return modifyTime;
                }

                public void setModifyTime(Object modifyTime) {
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

                public Object getRegTime() {
                    return regTime;
                }

                public void setRegTime(Object regTime) {
                    this.regTime = regTime;
                }

                public Object getLastLoginTime() {
                    return lastLoginTime;
                }

                public void setLastLoginTime(Object lastLoginTime) {
                    this.lastLoginTime = lastLoginTime;
                }

                public Object getChannel() {
                    return channel;
                }

                public void setChannel(Object channel) {
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

                public Object getCashbackCount() {
                    return cashbackCount;
                }

                public void setCashbackCount(Object cashbackCount) {
                    this.cashbackCount = cashbackCount;
                }

                public Object getRedBagCount() {
                    return redBagCount;
                }

                public void setRedBagCount(Object redBagCount) {
                    this.redBagCount = redBagCount;
                }

                public Object getCouponsCount() {
                    return couponsCount;
                }

                public void setCouponsCount(Object couponsCount) {
                    this.couponsCount = couponsCount;
                }

                public Object getBalance() {
                    return balance;
                }

                public void setBalance(Object balance) {
                    this.balance = balance;
                }
            }
        }
    }
}

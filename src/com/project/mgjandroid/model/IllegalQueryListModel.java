package com.project.mgjandroid.model;

import java.util.List;

/**
 * Created by SunXueLiang on 2017-05-10.
 */

public class IllegalQueryListModel extends Entity {

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
        private int userId;
        private int agentId;
        private String lsprefix;
        private String lsnum;
        private String engineno;
        private String frameno;
        private String carBrand;
        private Object fontLetter;
        private String imgUrl;
        private int hasDel;
        private IllegalQueryResultBean illegalQueryResult;

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

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }

        public String getLsprefix() {
            return lsprefix;
        }

        public void setLsprefix(String lsprefix) {
            this.lsprefix = lsprefix;
        }

        public String getLsnum() {
            return lsnum;
        }

        public void setLsnum(String lsnum) {
            this.lsnum = lsnum;
        }

        public String getEngineno() {
            return engineno;
        }

        public void setEngineno(String engineno) {
            this.engineno = engineno;
        }

        public String getFrameno() {
            return frameno;
        }

        public void setFrameno(String frameno) {
            this.frameno = frameno;
        }

        public String getCarBrand() {
            return carBrand;
        }

        public void setCarBrand(String carBrand) {
            this.carBrand = carBrand;
        }

        public Object getFontLetter() {
            return fontLetter;
        }

        public void setFontLetter(Object fontLetter) {
            this.fontLetter = fontLetter;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getHasDel() {
            return hasDel;
        }

        public void setHasDel(int hasDel) {
            this.hasDel = hasDel;
        }

        public IllegalQueryResultBean getIllegalQueryResult() {
            return illegalQueryResult;
        }

        public void setIllegalQueryResult(IllegalQueryResultBean illegalQueryResult) {
            this.illegalQueryResult = illegalQueryResult;
        }

        public static class IllegalQueryResultBean {

            private int id;
            private String createTime;
            private String modifyTime;
            private int userId;
            private int agentId;
            private int illegalQueryCarId;
            private int count;
            private double totalprice;
            private int totalscore;
            private String usercarid;

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

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getAgentId() {
                return agentId;
            }

            public void setAgentId(int agentId) {
                this.agentId = agentId;
            }

            public int getIllegalQueryCarId() {
                return illegalQueryCarId;
            }

            public void setIllegalQueryCarId(int illegalQueryCarId) {
                this.illegalQueryCarId = illegalQueryCarId;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public double getTotalprice() {
                return totalprice;
            }

            public void setTotalprice(double totalprice) {
                this.totalprice = totalprice;
            }

            public int getTotalscore() {
                return totalscore;
            }

            public void setTotalscore(int totalscore) {
                this.totalscore = totalscore;
            }

            public String getUsercarid() {
                return usercarid;
            }

            public void setUsercarid(String usercarid) {
                this.usercarid = usercarid;
            }
        }
    }
}

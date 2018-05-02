package com.project.mgjandroid.model;

import java.util.List;

/**
 * Created by SunXueLiang on 2017-05-12.
 */

public class IllegalDetailsModel extends Entity {

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

        private String status;
        private String msg;
        private ResultBean result;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public static class ResultBean extends Entity {

            private String lsprefix;
            private String lsnum;
            private String carorg;
            private String usercarid;
            private String count;
            private String totalprice;
            private String totalscore;
            private List<ListBean> list;

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

            public String getCarorg() {
                return carorg;
            }

            public void setCarorg(String carorg) {
                this.carorg = carorg;
            }

            public String getUsercarid() {
                return usercarid;
            }

            public void setUsercarid(String usercarid) {
                this.usercarid = usercarid;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public String getTotalprice() {
                return totalprice;
            }

            public void setTotalprice(String totalprice) {
                this.totalprice = totalprice;
            }

            public String getTotalscore() {
                return totalscore;
            }

            public void setTotalscore(String totalscore) {
                this.totalscore = totalscore;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean extends Entity {

                private String time;
                private String address;
                private String content;
                private String legalnum;
                private String price;
                private String score;
                private String agency;
                private String illegalid;
                private String province;
                private String city;
                private String town;
                private String lat;
                private String lng;

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getLegalnum() {
                    return legalnum;
                }

                public void setLegalnum(String legalnum) {
                    this.legalnum = legalnum;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getScore() {
                    return score;
                }

                public void setScore(String score) {
                    this.score = score;
                }

                public String getAgency() {
                    return agency;
                }

                public void setAgency(String agency) {
                    this.agency = agency;
                }

                public String getIllegalid() {
                    return illegalid;
                }

                public void setIllegalid(String illegalid) {
                    this.illegalid = illegalid;
                }

                public String getProvince() {
                    return province;
                }

                public void setProvince(String province) {
                    this.province = province;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getTown() {
                    return town;
                }

                public void setTown(String town) {
                    this.town = town;
                }

                public String getLat() {
                    return lat;
                }

                public void setLat(String lat) {
                    this.lat = lat;
                }

                public String getLng() {
                    return lng;
                }

                public void setLng(String lng) {
                    this.lng = lng;
                }
            }
        }
    }
}

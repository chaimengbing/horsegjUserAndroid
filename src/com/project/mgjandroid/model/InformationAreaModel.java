package com.project.mgjandroid.model;

/**
 * Created by yuandi on 2016/10/25.
 */

public class InformationAreaModel extends Entity {

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

        private String name;

        private long province;

        private long city;

        private long district;

        private long baiduCityCode;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getProvince() {
            return province;
        }

        public void setProvince(long province) {
            this.province = province;
        }

        public long getCity() {
            return city;
        }

        public void setCity(long city) {
            this.city = city;
        }

        public long getDistrict() {
            return district;
        }

        public void setDistrict(long district) {
            this.district = district;
        }

        public long getBaiduCityCode() {
            return baiduCityCode;
        }

        public void setBaiduCityCode(long baiduCityCode) {
            this.baiduCityCode = baiduCityCode;
        }
    }
}

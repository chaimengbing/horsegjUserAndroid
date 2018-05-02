package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.Menu;

import java.util.ArrayList;

/**
 * Created by yuandi on 2016/10/12.
 */

public class SuperMarketGoodsCategoryModel extends Entity {

    private int code;

    private String uuid;

    private ValueEntity value;

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

    public ValueEntity getValue() {
        return value;
    }

    public void setValue(ValueEntity value) {
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

    public static class ValueEntity extends Entity {

        private ArrayList<Menu> merchantShopGoodsCategoryList;

        private Long merchantId;

        private int showHotsale;

        public ArrayList<Menu> getMerchantShopGoodsCategoryList() {
            return merchantShopGoodsCategoryList;
        }

        public void setMerchantShopGoodsCategoryList(ArrayList<Menu> merchantShopGoodsCategoryList) {
            this.merchantShopGoodsCategoryList = merchantShopGoodsCategoryList;
        }

        public Long getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(Long merchantId) {
            this.merchantId = merchantId;
        }

        public int getShowHotsale() {
            return showHotsale;
        }

        public void setShowHotsale(int showHotsale) {
            this.showHotsale = showHotsale;
        }
    }
}

package com.project.mgjandroid.bean;

import com.project.mgjandroid.bean.groupbuying.GroupPurchaseMerchant;

import java.io.Serializable;

/**
 * Created by yuandi on 2016/4/27.
 */
public class UserFavorites implements Serializable {

    private Long id;

    private Long userId;

    private Long merchantId;

    private Merchant merchant;
    /**
     * 商家类型(0:外卖 1:商超 2:团购)
     **/
    private int merchantType;

    private GroupPurchaseMerchant groupPurchaseMerchant;

    private int hasDel;

    private boolean isSelected;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public int getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(int merchantType) {
        this.merchantType = merchantType;
    }

    public GroupPurchaseMerchant getGroupPurchaseMerchant() {
        return groupPurchaseMerchant;
    }

    public void setGroupPurchaseMerchant(GroupPurchaseMerchant groupPurchaseMerchant) {
        this.groupPurchaseMerchant = groupPurchaseMerchant;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

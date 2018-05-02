package com.project.mgjandroid.bean.groupbuying;

import com.project.mgjandroid.bean.BaseBean;

/**
 * Created by SunXueLiang on 2017-03-14.
 */

public class GroupPurchaseComplain extends BaseBean {
    /**
     * 代理商编号
     **/
    private Long agentId;
    /**
     * 商家编号
     */
    private Long merchantId;
    /**
     * 类型 1, 代金券 2, 团购券
     */
    private int groupPurchaseCouponType;
    /**
     * 内容
     **/
    private String content;

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public int getGroupPurchaseCouponType() {
        return groupPurchaseCouponType;
    }

    public void setGroupPurchaseCouponType(int groupPurchaseCouponType) {
        this.groupPurchaseCouponType = groupPurchaseCouponType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

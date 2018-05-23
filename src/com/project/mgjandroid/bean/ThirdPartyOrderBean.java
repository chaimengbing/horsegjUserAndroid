package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User_Cjh on 2018/1/4.
 */

public class ThirdPartyOrderBean extends Entity {

    /**
     * "agentRateAmt": 0,
     * "commissionJson": {
     * "agentAccountId": 3,
     * "commissionJson": {
     * "partnerAgentRate": 10,
     * "provinceAgentRate": 10,
     * "sysRate": 10
     * },
     * "commissionRule": ""
     * },
     * "createTime": 1515398015000,
     * "description": "出发时间：2018-01-08 23:52:5",
     * "hasDel": 0,
     * "hasPayed": 0,
     * "id": 451,
     * "journeyId": 227,
     * "modifyTime": 1515398340000,
     * "orderFlowStatusStr": "取消订单",
     * "orderId": "1801080000148471",
     * "orderType": 1,
     * "partnerAgentRateAmt": 0,
     * "paymentState": 0,
     * "paymentType": 1,
     * "provinceAgentRateAmt": 0,
     * "settleStatus": 0,
     * "status": -1,
     * "sysRateAmt": 0,
     * "thirdpartyId": 227,
     * "title": "萝莉控哦 - 图摸摸看",
     * "totalPrice": 0.5,
     * "type": 7,
     * "typeStr": "顺风车",
     * "url": "http://112.74.18.147/sfc/build/index.html#/AnnounceDetail:227",
     * "userId": 903
     */
    private String title;
    private String description;
    private String picUrl;
    private String typeStr;
    private String orderFlowStatusStr;
    private int status;
    private int paymentState;
    private String paymentExpireTime;
    private String url;
    private String type;
    private String serverTime;
    private String startAddress;
    private String endAddress;
    private BigDecimal totalPrice;
    private ArrayList<OrderModer> orderList;

    public ArrayList<OrderModer> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<OrderModer> orderList) {
        this.orderList = orderList;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(int paymentState) {
        this.paymentState = paymentState;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getOrderFlowStatusStr() {
        return orderFlowStatusStr;
    }

    public void setOrderFlowStatusStr(String orderFlowStatusStr) {
        this.orderFlowStatusStr = orderFlowStatusStr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPaymentExpireTime() {
        return paymentExpireTime;
    }

    public void setPaymentExpireTime(String paymentExpireTime) {
        this.paymentExpireTime = paymentExpireTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class OrderModer extends Entity {

        /**
         * hasPayed : 88.00
         * orderFlowStatusStr : 已取消
         * totalPrice : 88.00
         * orderId : 2018041716144321466
         * agentId : 202
         * merchantId : 535
         * userId : 903
         * status : -1
         * settleStatus : 0
         * type : 8
         * commissionJson : {"partnerAgentId":202,"commissionRule":"","agentAccountId":202,"commissionJson":""}
         * expressFee : 0
         * orderItems : [{"goodsName":"金","totalQuantity":"1","totalPrice":88,"price":"88.00"}]
         * agentRate : 0
         */

        private String hasPayed;
        private String orderFlowStatusStr;
        private String totalPrice;
        private String orderId;
        private String agentId;
        private String merchantId;
        private String userId;
        private String status;
        private String settleStatus;
        private String type;
        private CommissionJsonBean commissionJson;
        private String expressFee;
        private String agentRate;
        private List<OrderItemsBean> orderItems;

        public String getHasPayed() {
            return hasPayed;
        }

        public void setHasPayed(String hasPayed) {
            this.hasPayed = hasPayed;
        }

        public String getOrderFlowStatusStr() {
            return orderFlowStatusStr;
        }

        public void setOrderFlowStatusStr(String orderFlowStatusStr) {
            this.orderFlowStatusStr = orderFlowStatusStr;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getAgentId() {
            return agentId;
        }

        public void setAgentId(String agentId) {
            this.agentId = agentId;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSettleStatus() {
            return settleStatus;
        }

        public void setSettleStatus(String settleStatus) {
            this.settleStatus = settleStatus;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public CommissionJsonBean getCommissionJson() {
            return commissionJson;
        }

        public void setCommissionJson(CommissionJsonBean commissionJson) {
            this.commissionJson = commissionJson;
        }

        public String getExpressFee() {
            return expressFee;
        }

        public void setExpressFee(String expressFee) {
            this.expressFee = expressFee;
        }

        public String getAgentRate() {
            return agentRate;
        }

        public void setAgentRate(String agentRate) {
            this.agentRate = agentRate;
        }

        public List<OrderItemsBean> getOrderItems() {
            return orderItems;
        }

        public void setOrderItems(List<OrderItemsBean> orderItems) {
            this.orderItems = orderItems;
        }

        public static class CommissionJsonBean extends Entity {
            /**
             * partnerAgentId : 202
             * commissionRule :
             * agentAccountId : 202
             * commissionJson :
             */

            private int partnerAgentId;
            private String commissionRule;
            private int agentAccountId;
            private String commissionJson;

            public int getPartnerAgentId() {
                return partnerAgentId;
            }

            public void setPartnerAgentId(int partnerAgentId) {
                this.partnerAgentId = partnerAgentId;
            }

            public String getCommissionRule() {
                return commissionRule;
            }

            public void setCommissionRule(String commissionRule) {
                this.commissionRule = commissionRule;
            }

            public int getAgentAccountId() {
                return agentAccountId;
            }

            public void setAgentAccountId(int agentAccountId) {
                this.agentAccountId = agentAccountId;
            }

            public String getCommissionJson() {
                return commissionJson;
            }

            public void setCommissionJson(String commissionJson) {
                this.commissionJson = commissionJson;
            }
        }

        public static class OrderItemsBean extends Entity {
            /**
             * goodsName : 金
             * totalQuantity : 1
             * totalPrice : 88
             * price : 88.00
             */

            private String goodsName;
            private int totalQuantity;
            private int totalPrice;
            private String price;

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public int getTotalQuantity() {
                return totalQuantity;
            }

            public void setTotalQuantity(int totalQuantity) {
                this.totalQuantity = totalQuantity;
            }

            public int getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(int totalPrice) {
                this.totalPrice = totalPrice;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}

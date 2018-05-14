package com.project.mgjandroid.model;

/**
 * 项目名称：mgjuser
 * 类描述：
 * 创建人：Mr_Lei
 * 创建时间：2018/5/11 12:28
 */
public class OrderRefundInfoModel extends Entity {

    /**
     * code : 0
     * uuid : 867451020506330
     * value : {"refundSuccessTime":"2018-05-11 11:38:28","applySuccessTime":"2018-05-11 11:38:27","balanceCost":0,"amt":100,"state":2,"processingTime":"2018-05-11 11:38:27","refundTotalMoney":100,"transactionNo":"1805110000142109","chargeType":"wx","orderId":"1805110000189919"}
     * success : true
     * servertime : 2018-05-11 12:27:56
     */

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

    public static class ValueBean {
        /**
         * refundSuccessTime : 2018-05-11 11:38:28  //成功时间
         * applySuccessTime : 2018-05-11 11:38:27   //申请成功时间
         * balanceCost : 0.0  //余额付款金额
         * amt : 100.0 //第三方付款金额
         * state : 2  //退款状态   2:退款成功  4:处理中  3:退款失败
         * processingTime : 2018-05-11 11:38:27  // 处理中时间
         * refundTotalMoney : 100.0 //付款总金额
         * transactionNo : 1805110000142109  第三方交易标号
         * chargeType : wx   支付类型  "alipay", "支付宝手机支付"  "wx_lite", "微信小程序支付" "wx", "微信支付"  balance："余额"
         * orderId : 1805110000189919  订单号
         */

        private String refundSuccessTime;
        private String applySuccessTime;
        private String balanceCost;
        private String amt;
        private int state;
        private String processingTime;
        private String refundTotalMoney;
        private String transactionNo;
        private String chargeType;
        private String orderId;

        public String getRefundSuccessTime() {
            return refundSuccessTime;
        }

        public void setRefundSuccessTime(String refundSuccessTime) {
            this.refundSuccessTime = refundSuccessTime;
        }

        public String getApplySuccessTime() {
            return applySuccessTime;
        }

        public void setApplySuccessTime(String applySuccessTime) {
            this.applySuccessTime = applySuccessTime;
        }

        public String getBalanceCost() {
            return balanceCost;
        }

        public void setBalanceCost(String balanceCost) {
            this.balanceCost = balanceCost;
        }

        public String getAmt() {
            return amt;
        }

        public void setAmt(String amt) {
            this.amt = amt;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getProcessingTime() {
            return processingTime;
        }

        public void setProcessingTime(String processingTime) {
            this.processingTime = processingTime;
        }

        public String getRefundTotalMoney() {
            return refundTotalMoney;
        }

        public void setRefundTotalMoney(String refundTotalMoney) {
            this.refundTotalMoney = refundTotalMoney;
        }

        public String getTransactionNo() {
            return transactionNo;
        }

        public void setTransactionNo(String transactionNo) {
            this.transactionNo = transactionNo;
        }

        public String getChargeType() {
            return chargeType;
        }

        public void setChargeType(String chargeType) {
            this.chargeType = chargeType;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }
}

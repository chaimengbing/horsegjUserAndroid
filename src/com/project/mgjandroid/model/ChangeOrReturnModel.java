package com.project.mgjandroid.model;

import java.util.List;

/**
 * Created by User_Cjh on 2016/9/23.
 */
public class ChangeOrReturnModel extends Entity {

    private int code;
    private String uuid;
    private boolean success;
    private String servertime;
    /**
     * orderId : 1609230000066602
     * canReturn : false
     * canChange : false
     * orderItem : {"id":6439,"createTime":"2016-09-23 12:26:42","modifyTime":"2016-09-23 12:26:42","orderId":"1609230000066602","goodsId":296,"goodsSpecId":356,"name":"但没面","spec":"大","unit":"份","quantity":1,"originPrice":100,"price":100,"totalPrice":100,"boxPrice":5,"totalBoxPrice":5,"isPromo":0,"tip":"","canReturn":0,"daysForReturn":1,"canChange":0,"daysForChange":7}
     * applyStatus : null
     */

    private List<ValueEntity> value;

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

    public List<ValueEntity> getValue() {
        return value;
    }

    public void setValue(List<ValueEntity> value) {
        this.value = value;
    }

    public static class ValueEntity extends Entity {
        private int position;
        private String orderId;
        private boolean canReturn;
        private boolean canChange;
        /**
         * id : 6439
         * createTime : 2016-09-23 12:26:42
         * modifyTime : 2016-09-23 12:26:42
         * orderId : 1609230000066602
         * goodsId : 296
         * goodsSpecId : 356
         * name : 但没面
         * spec : 大
         * unit : 份
         * quantity : 1
         * originPrice : 100.0
         * price : 100.0
         * totalPrice : 100.0
         * boxPrice : 5.0
         * totalBoxPrice : 5.0
         * isPromo : 0
         * tip :
         * canReturn : 0
         * daysForReturn : 1
         * canChange : 0
         * daysForChange : 7
         */

        private OrderItemEntity orderItem;
        private Integer applyStatus;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public boolean isCanReturn() {
            return canReturn;
        }

        public void setCanReturn(boolean canReturn) {
            this.canReturn = canReturn;
        }

        public boolean isCanChange() {
            return canChange;
        }

        public void setCanChange(boolean canChange) {
            this.canChange = canChange;
        }

        public OrderItemEntity getOrderItem() {
            return orderItem;
        }

        public void setOrderItem(OrderItemEntity orderItem) {
            this.orderItem = orderItem;
        }

        public Integer getApplyStatus() {
            return applyStatus;
        }

        public void setApplyStatus(Integer applyStatus) {
            this.applyStatus = applyStatus;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public static class OrderItemEntity extends Entity {
            private long id;
            private String createTime;
            private String modifyTime;
            private String orderId;
            private int goodsId;
            private int goodsSpecId;
            private String name;
            private String spec;
            private String unit;
            private int quantity;
            private double originPrice;
            private double price;
            private double totalPrice;
            private double boxPrice;
            private double totalBoxPrice;
            private int isPromo;
            private String tip;
            private int canReturn;
            private int daysForReturn;
            private int canChange;
            private int daysForChange;

            public long getId() {
                return id;
            }

            public void setId(long id) {
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

            public int getGoodsSpecId() {
                return goodsSpecId;
            }

            public void setGoodsSpecId(int goodsSpecId) {
                this.goodsSpecId = goodsSpecId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSpec() {
                return spec;
            }

            public void setSpec(String spec) {
                this.spec = spec;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public double getOriginPrice() {
                return originPrice;
            }

            public void setOriginPrice(double originPrice) {
                this.originPrice = originPrice;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(double totalPrice) {
                this.totalPrice = totalPrice;
            }

            public double getBoxPrice() {
                return boxPrice;
            }

            public void setBoxPrice(double boxPrice) {
                this.boxPrice = boxPrice;
            }

            public double getTotalBoxPrice() {
                return totalBoxPrice;
            }

            public void setTotalBoxPrice(double totalBoxPrice) {
                this.totalBoxPrice = totalBoxPrice;
            }

            public int getIsPromo() {
                return isPromo;
            }

            public void setIsPromo(int isPromo) {
                this.isPromo = isPromo;
            }

            public String getTip() {
                return tip;
            }

            public void setTip(String tip) {
                this.tip = tip;
            }

            public int getCanReturn() {
                return canReturn;
            }

            public void setCanReturn(int canReturn) {
                this.canReturn = canReturn;
            }

            public int getDaysForReturn() {
                return daysForReturn;
            }

            public void setDaysForReturn(int daysForReturn) {
                this.daysForReturn = daysForReturn;
            }

            public int getCanChange() {
                return canChange;
            }

            public void setCanChange(int canChange) {
                this.canChange = canChange;
            }

            public int getDaysForChange() {
                return daysForChange;
            }

            public void setDaysForChange(int daysForChange) {
                this.daysForChange = daysForChange;
            }
        }
    }
}

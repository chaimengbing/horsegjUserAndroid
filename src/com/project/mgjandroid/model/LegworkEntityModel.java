package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.UserAddress;

import java.util.List;
import java.util.Map;

/**
 * Created by SunXueLiang on 2018-03-23.
 */

public class LegworkEntityModel extends Entity {

    /**
     * code : 0
     * uuid : 867451020506330
     * value : {"legWorkGoodsCategoryList":[{"id":1,"name":"食物","parentId":0,"icon":"","level":1,"sortNo":1,"hasDel":0,"agentId":3,"childLegWorkGoodsCategoryList":null},{"id":3,"name":"水果","parentId":0,"icon":"","level":1,"sortNo":1,"hasDel":0,"agentId":3,"childLegWorkGoodsCategoryList":null},{"id":4,"name":"肉类","parentId":0,"icon":"","level":1,"sortNo":3,"hasDel":0,"agentId":3,"childLegWorkGoodsCategoryList":null},{"id":6,"name":"小吃","parentId":0,"icon":"","level":1,"sortNo":5,"hasDel":0,"agentId":3,"childLegWorkGoodsCategoryList":null},{"id":7,"name":"咖啡","parentId":0,"icon":"","level":1,"sortNo":5,"hasDel":0,"agentId":3,"childLegWorkGoodsCategoryList":null}],"deliveryTimesResponseDTOList":null,"serviceIntroduceUrl":"https://www.baidu.com","protocolUrl":null,"business":true}
     * success : true
     * servertime : 2018-03-27 16:40:57
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
         * legWorkGoodsCategoryList : [{"id":1,"name":"食物","parentId":0,"icon":"","level":1,"sortNo":1,"hasDel":0,"agentId":3,"childLegWorkGoodsCategoryList":null},{"id":3,"name":"水果","parentId":0,"icon":"","level":1,"sortNo":1,"hasDel":0,"agentId":3,"childLegWorkGoodsCategoryList":null},{"id":4,"name":"肉类","parentId":0,"icon":"","level":1,"sortNo":3,"hasDel":0,"agentId":3,"childLegWorkGoodsCategoryList":null},{"id":6,"name":"小吃","parentId":0,"icon":"","level":1,"sortNo":5,"hasDel":0,"agentId":3,"childLegWorkGoodsCategoryList":null},{"id":7,"name":"咖啡","parentId":0,"icon":"","level":1,"sortNo":5,"hasDel":0,"agentId":3,"childLegWorkGoodsCategoryList":null}]
         * deliveryTimesResponseDTOList : null
         * serviceIntroduceUrl : https://www.baidu.com
         * protocolUrl : null
         * business : true
         */

        private List<DeliveryTimesResponseDTOListBean> deliveryTimesResponseDTOList;
        private String agencyPurchasingServiceProtocolUrl;
        private String takeDeliveryServiceProtocolUrl;
        private String serviceIntroduceUrl;
        private UserAddress userAddress;
        private boolean business;
        private List<LegWorkGoodsCategoryListBean> legWorkGoodsCategoryList;
        private List<LegWorkBannersBean> legWorkBanners;
        private List<LegWorkBroadcastsBean> legWorkBroadcasts;

        public UserAddress getUserAddress() {
            return userAddress;
        }

        public void setUserAddress(UserAddress userAddress) {
            this.userAddress = userAddress;
        }

        public List<DeliveryTimesResponseDTOListBean> getDeliveryTimesResponseDTOList() {
            return deliveryTimesResponseDTOList;
        }

        public void setDeliveryTimesResponseDTOList(List<DeliveryTimesResponseDTOListBean> deliveryTimesResponseDTOList) {
            this.deliveryTimesResponseDTOList = deliveryTimesResponseDTOList;
        }

        public String getAgencyPurchasingServiceProtocolUrl() {
            return agencyPurchasingServiceProtocolUrl;
        }

        public void setAgencyPurchasingServiceProtocolUrl(String agencyPurchasingServiceProtocolUrl) {
            this.agencyPurchasingServiceProtocolUrl = agencyPurchasingServiceProtocolUrl;
        }

        public String getTakeDeliveryServiceProtocolUrl() {
            return takeDeliveryServiceProtocolUrl;
        }

        public void setTakeDeliveryServiceProtocolUrl(String takeDeliveryServiceProtocolUrl) {
            this.takeDeliveryServiceProtocolUrl = takeDeliveryServiceProtocolUrl;
        }

        public String getServiceIntroduceUrl() {
            return serviceIntroduceUrl;
        }

        public void setServiceIntroduceUrl(String serviceIntroduceUrl) {
            this.serviceIntroduceUrl = serviceIntroduceUrl;
        }

        public boolean isBusiness() {
            return business;
        }

        public void setBusiness(boolean business) {
            this.business = business;
        }

        public List<LegWorkGoodsCategoryListBean> getLegWorkGoodsCategoryList() {
            return legWorkGoodsCategoryList;
        }

        public void setLegWorkGoodsCategoryList(List<LegWorkGoodsCategoryListBean> legWorkGoodsCategoryList) {
            this.legWorkGoodsCategoryList = legWorkGoodsCategoryList;
        }

        public List<LegWorkBannersBean> getLegWorkBanners() {
            return legWorkBanners;
        }

        public void setLegWorkBanners(List<LegWorkBannersBean> legWorkBanners) {
            this.legWorkBanners = legWorkBanners;
        }

        public List<LegWorkBroadcastsBean> getLegWorkBroadcasts() {
            return legWorkBroadcasts;
        }

        public void setLegWorkBroadcasts(List<LegWorkBroadcastsBean> legWorkBroadcasts) {
            this.legWorkBroadcasts = legWorkBroadcasts;
        }

        public static class LegWorkBroadcastsBean extends Entity {
            private String content;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }

        public static class LegWorkBannersBean extends Entity {
            private String name;
            private String picUrl;
            private String url;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class DeliveryTimesResponseDTOListBean extends Entity {

            private String day;
            private List<Map<String, String>> times;

            public void setDay(String day) {
                this.day = day;
            }

            public String getDay() {
                return day;
            }

            public List<Map<String, String>> getTimes() {
                return times;
            }

            public void setTimes(List<Map<String, String>> times) {
                this.times = times;
            }
        }

        public static class LegWorkGoodsCategoryListBean {
            /**
             * id : 1
             * name : 食物
             * parentId : 0
             * icon :
             * level : 1
             * sortNo : 1
             * hasDel : 0
             * agentId : 3
             * childLegWorkGoodsCategoryList : null
             */

            private int id;
            private String name;
            private int parentId;
            private String icon;
            private int level;
            private int sortNo;
            private int hasDel;
            private int agentId;
            private Object childLegWorkGoodsCategoryList;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getSortNo() {
                return sortNo;
            }

            public void setSortNo(int sortNo) {
                this.sortNo = sortNo;
            }

            public int getHasDel() {
                return hasDel;
            }

            public void setHasDel(int hasDel) {
                this.hasDel = hasDel;
            }

            public int getAgentId() {
                return agentId;
            }

            public void setAgentId(int agentId) {
                this.agentId = agentId;
            }

            public Object getChildLegWorkGoodsCategoryList() {
                return childLegWorkGoodsCategoryList;
            }

            public void setChildLegWorkGoodsCategoryList(Object childLegWorkGoodsCategoryList) {
                this.childLegWorkGoodsCategoryList = childLegWorkGoodsCategoryList;
            }
        }
    }
}

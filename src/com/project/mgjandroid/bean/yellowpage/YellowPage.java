package com.project.mgjandroid.bean.yellowpage;

import com.project.mgjandroid.model.Entity;

public class YellowPage extends Entity {

    private static final long serialVersionUID = 1L;
    private long id;
    /**
     * 代理商编号
     */
    private Long agentId;
    /**
     * 商家名称
     */
    private String merchantName;
    /**
     * 分类(1：银行;2:品牌售后;3:党政机关;4:美食外卖;5:保险证券;6:快递物流;7:汽车公司;8:酒店预订;)
     **/
    private Integer type;
    /**
     * 电话(多个电话用;号隔开)
     **/
    private String mobiles;
    /**
     * 地址
     **/
    private String address;
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 维度
     */
    private Double latitude;
    /**
     * logo
     **/
    private String logoPath;
    /**
     * 是否删除 0:正常;1:删除
     */
    private int hasDel;
    private Double distance;

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

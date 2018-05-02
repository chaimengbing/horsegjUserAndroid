package com.project.mgjandroid.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yuandi on 2016/7/27.
 */
public class Agent implements Serializable {

    private Long id;
    /**
     * 名称
     */
    private String name;

    private String loginName = "";
    /**
     * 手机号
     */
    private String mobile = "";
    /**
     * 联系（客服）电话（拒单时所需代理商联系电话）
     **/
    private String phone = "";
    /**
     * 密码
     */
    private String pwd = "";
    /**
     * 上级代理。最高级代理的parentId为0
     */
    private long parentId;
    /**
     * 1：一级，2：二级
     */
    private int level;
    /**
     * 代理商类型
     */
    private int agentType;
    /**
     * 是否已删除
     */
    private int hasDel;
    /**
     * 是否是管理员
     */
    private int isAdmin;
    /**
     * 省
     */
    private Long province;
    /**
     * 市
     */
    private Long city;
    /**
     * 区
     */
    private Long district;
    /**
     * 开户行
     */
    private String bankName = "";
    /**
     * 银行卡号
     */
    private String bankCard = "";
    /**
     * 开户人
     */
    private String bankPerson = "";
    /**
     * 系统佣金比率
     */
    private BigDecimal sysRate = new BigDecimal(1);
    /**
     * 当前代理商佣金比率
     */
    private BigDecimal currentAgentRate = new BigDecimal(1);
    /**
     * 省级代理商佣金比率
     */
    private BigDecimal provinceAgentRate = new BigDecimal(1);
    /**
     * 所属省级代理商编号
     */
    private Long provinceAgentId;
    /**
     * 保证金限额
     */
    private BigDecimal marginAmt = BigDecimal.ZERO;
    /**
     * 代理商优惠金额
     **/
    private BigDecimal discountAmt;
    /**
     * 地图管理使用
     **/
    private String address;

    private String provinceName;

    private String cityName;

    private String districtName;

    private Date createTime;

    private Date modifyTime;

    private String sortArea;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAgentType() {
        return agentType;
    }

    public void setAgentType(int agentType) {
        this.agentType = agentType;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Long getProvince() {
        return province;
    }

    public void setProvince(Long province) {
        this.province = province;
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    public Long getDistrict() {
        return district;
    }

    public void setDistrict(Long district) {
        this.district = district;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankPerson() {
        return bankPerson;
    }

    public void setBankPerson(String bankPerson) {
        this.bankPerson = bankPerson;
    }

    public BigDecimal getSysRate() {
        return sysRate;
    }

    public void setSysRate(BigDecimal sysRate) {
        this.sysRate = sysRate;
    }

    public BigDecimal getCurrentAgentRate() {
        return currentAgentRate;
    }

    public void setCurrentAgentRate(BigDecimal currentAgentRate) {
        this.currentAgentRate = currentAgentRate;
    }

    public BigDecimal getProvinceAgentRate() {
        return provinceAgentRate;
    }

    public void setProvinceAgentRate(BigDecimal provinceAgentRate) {
        this.provinceAgentRate = provinceAgentRate;
    }

    public Long getProvinceAgentId() {
        return provinceAgentId;
    }

    public void setProvinceAgentId(Long provinceAgentId) {
        this.provinceAgentId = provinceAgentId;
    }

    public BigDecimal getMarginAmt() {
        return marginAmt;
    }

    public void setMarginAmt(BigDecimal marginAmt) {
        this.marginAmt = marginAmt;
    }

    public BigDecimal getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(BigDecimal discountAmt) {
        this.discountAmt = discountAmt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getSortArea() {
        return sortArea;
    }

    public void setSortArea(String sortArea) {
        this.sortArea = sortArea;
    }
}


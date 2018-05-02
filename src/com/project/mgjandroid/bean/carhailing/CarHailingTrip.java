package com.project.mgjandroid.bean.carhailing;

import com.project.mgjandroid.bean.Promotion;
import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by User_Cjh on 2016/12/12.
 */
public class CarHailingTrip extends Entity {
    private static final long serialVersionUID = 1L;
    /**
     * 行程编号
     */
    private Long id;
    /**
     * 代理商编号
     */
    private Long agentId;
    /**
     * 司机编号
     */
    private Long chauffeurId;
    /**
     * 行程编号(关联终点行程Id)
     */
    private Long carTripId;

    /**
     * 1:拼车;2:包车
     */
    private int type;
    /**
     * 人数
     */
    private int peopleNum;
    /**
     * 当前人数
     */
    private int currentPeopleNum;
    /**
     * 发车时间
     */
    private Date leaveTime;
    /**
     * 服务
     */
    private String service;

    /**
     * 省
     */
    private Long startProvince;
    /**
     * 市
     */
    private Long startCity;
    /**
     * 区
     */
    private Long startDistrict;
    /**
     * 省
     */
    private Long endProvince;
    /**
     * 市
     */
    private Long endCity;
    /**
     * 区
     */
    private Long endDistrict;

    /**
     * 省
     */
    private String startProvinceName;
    /**
     * 市
     */
    private String startCityName;
    /**
     * 区
     */
    private String startDistrictName = "市区";
    /**
     * 省
     */
    private String endProvinceName;
    /**
     * 市
     */
    private String endCityName;
    /**
     * 区
     */
    private String endDistrictName = "市区";

    private String startAddress;
    private String endAddress;
    /**
     * 状态
     */
    private int status;
    /**
     * 拼车默认价格
     */
    private BigDecimal carpoolDefaultPrice;
    /**
     * 包车默认价格
     */
    private BigDecimal charteredlDefaultPrice;
    /**
     * 是否已删除
     */
    private int hasDel;
    /**
     * 取消原因
     **/
    private String reason;
    private CarHailingDriver chauffeur;
    private List<CarHailing> chauffeurTripDetailList;
    private List<CarHailingOrder> chauffeurOrderList;
    private Date serviceTime;

    private Promotion carFirstOrder;

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Long getChauffeurId() {
        return chauffeurId;
    }

    public void setChauffeurId(Long chauffeurId) {
        this.chauffeurId = chauffeurId;
    }

    public Long getCarTripId() {
        return carTripId;
    }

    public void setCarTripId(Long carTripId) {
        this.carTripId = carTripId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(int peopleNum) {
        this.peopleNum = peopleNum;
    }

    public int getCurrentPeopleNum() {
        return currentPeopleNum;
    }

    public void setCurrentPeopleNum(int currentPeopleNum) {
        this.currentPeopleNum = currentPeopleNum;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Long getStartProvince() {
        return startProvince;
    }

    public void setStartProvince(Long startProvince) {
        this.startProvince = startProvince;
    }

    public Long getStartCity() {
        return startCity;
    }

    public void setStartCity(Long startCity) {
        this.startCity = startCity;
    }

    public Long getStartDistrict() {
        return startDistrict;
    }

    public void setStartDistrict(Long startDistrict) {
        this.startDistrict = startDistrict;
    }

    public Long getEndProvince() {
        return endProvince;
    }

    public void setEndProvince(Long endProvince) {
        this.endProvince = endProvince;
    }

    public Long getEndCity() {
        return endCity;
    }

    public void setEndCity(Long endCity) {
        this.endCity = endCity;
    }

    public Long getEndDistrict() {
        return endDistrict;
    }

    public void setEndDistrict(Long endDistrict) {
        this.endDistrict = endDistrict;
    }

    public String getStartProvinceName() {
        return startProvinceName;
    }

    public void setStartProvinceName(String startProvinceName) {
        this.startProvinceName = startProvinceName;
    }

    public String getStartCityName() {
        return startCityName;
    }

    public void setStartCityName(String startCityName) {
        this.startCityName = startCityName;
    }

    public String getStartDistrictName() {
        return startDistrictName;
    }

    public void setStartDistrictName(String startDistrictName) {
        this.startDistrictName = startDistrictName;
    }

    public String getEndProvinceName() {
        return endProvinceName;
    }

    public void setEndProvinceName(String endProvinceName) {
        this.endProvinceName = endProvinceName;
    }

    public String getEndCityName() {
        return endCityName;
    }

    public void setEndCityName(String endCityName) {
        this.endCityName = endCityName;
    }

    public String getEndDistrictName() {
        return endDistrictName;
    }

    public void setEndDistrictName(String endDistrictName) {
        this.endDistrictName = endDistrictName;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BigDecimal getCarpoolDefaultPrice() {
        return carpoolDefaultPrice;
    }

    public void setCarpoolDefaultPrice(BigDecimal carpoolDefaultPrice) {
        this.carpoolDefaultPrice = carpoolDefaultPrice;
    }

    public BigDecimal getCharteredlDefaultPrice() {
        return charteredlDefaultPrice;
    }

    public void setCharteredlDefaultPrice(BigDecimal charteredlDefaultPrice) {
        this.charteredlDefaultPrice = charteredlDefaultPrice;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public CarHailingDriver getChauffeur() {
        return chauffeur;
    }

    public void setChauffeur(CarHailingDriver chauffeur) {
        this.chauffeur = chauffeur;
    }

    public List<CarHailing> getChauffeurTripDetailList() {
        return chauffeurTripDetailList;
    }

    public void setChauffeurTripDetailList(List<CarHailing> chauffeurTripDetailList) {
        this.chauffeurTripDetailList = chauffeurTripDetailList;
    }

    public Date getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Date serviceTime) {
        this.serviceTime = serviceTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CarHailingOrder> getChauffeurOrderList() {
        return chauffeurOrderList;
    }

    public void setChauffeurOrderList(List<CarHailingOrder> chauffeurOrderList) {
        this.chauffeurOrderList = chauffeurOrderList;
    }

    public Promotion getCarFirstOrder() {
        return carFirstOrder;
    }

    public void setCarFirstOrder(Promotion carFirstOrder) {
        this.carFirstOrder = carFirstOrder;
    }
}

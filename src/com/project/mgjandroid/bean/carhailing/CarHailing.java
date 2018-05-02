package com.project.mgjandroid.bean.carhailing;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by User_Cjh on 2016/12/12.
 */
public class CarHailing extends Entity {
    private static final long serialVersionUID = 1L;
    private long id;
    /**
     * 代理商编号
     */
    private Long agentId;
    /**
     * 司机编号
     */
    private Long chauffeurId;
    /**
     * 行程编号
     */
    private Long chauffeurTripId;

    /**
     * 开始位置Id
     */
    private Long startCarTripDetailId;
    /**
     * 结束位置Id
     */
    private Long endCarTripDetailId;

    /**
     * 开始位置地址
     */
    private String startAddress;
    /**
     * 结束位置地址
     */
    private String endAddress;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 当前人数
     */
    private int currentPeopleNum;
    /**
     * 状态
     */
    private int status;
    /**
     * 是否已删除
     */
    private int hasDel;
    private CarHailingTrip chauffeurTrip;
    private CarHailingDriver chauffeur;

    private Date serviceTime;

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

    public Long getChauffeurTripId() {
        return chauffeurTripId;
    }

    public void setChauffeurTripId(Long chauffeurTripId) {
        this.chauffeurTripId = chauffeurTripId;
    }

    public Long getStartCarTripDetailId() {
        return startCarTripDetailId;
    }

    public void setStartCarTripDetailId(Long startCarTripDetailId) {
        this.startCarTripDetailId = startCarTripDetailId;
    }

    public Long getEndCarTripDetailId() {
        return endCarTripDetailId;
    }

    public void setEndCarTripDetailId(Long endCarTripDetailId) {
        this.endCarTripDetailId = endCarTripDetailId;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getCurrentPeopleNum() {
        return currentPeopleNum;
    }

    public void setCurrentPeopleNum(int currentPeopleNum) {
        this.currentPeopleNum = currentPeopleNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public CarHailingTrip getChauffeurTrip() {
        return chauffeurTrip;
    }

    public void setChauffeurTrip(CarHailingTrip chauffeurTrip) {
        this.chauffeurTrip = chauffeurTrip;
    }

    public CarHailingDriver getChauffeur() {
        return chauffeur;
    }

    public void setChauffeur(CarHailingDriver chauffeur) {
        this.chauffeur = chauffeur;
    }

    public Date getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Date serviceTime) {
        this.serviceTime = serviceTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

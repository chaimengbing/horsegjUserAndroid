package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;

public class Distance extends Entity{

    /**
     * minDistance : 0
     * maxDistance : 10
     * charge : 5
     */

    private int minDistance;
    private int maxDistance;
    private BigDecimal charge;

    public int getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(int minDistance) {
        this.minDistance = minDistance;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }
}

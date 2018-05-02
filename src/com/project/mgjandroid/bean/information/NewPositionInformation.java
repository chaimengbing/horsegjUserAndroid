package com.project.mgjandroid.bean.information;

/**
 * Created by pb on 2017-02-10.
 */

public class NewPositionInformation extends InformationBaseProperty {

    /**
     * 类型1:求职
     */
    private int type = 1;
    /**
     * 职位名称
     **/
    private String positionName;

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}

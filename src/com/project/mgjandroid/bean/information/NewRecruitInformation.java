package com.project.mgjandroid.bean.information;

/**
 * Created by pb on 2017-02-10.
 */

public class NewRecruitInformation extends InformationBaseProperty {

    /**
     * 类型2:招聘
     */
    private int type = 2;
    /**
     * 职位名称
     **/
    private String positionName;

    private String categoryName;

    private InformationOrder informationOrder;

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
package com.project.mgjandroid.bean.information;

/**
 * Created by yuandi on 2016/11/16.
 */

public enum InformationType {

    WasteRecovery(11, "废品回收", "http://7xu5hi.com1.z0.glb.clouddn.com/1607141136300779669.png"),

    Position(10, "个人求职", "http://7xu5hi.com1.z0.glb.clouddn.com/1607141136300779669.png"),

    Recruit(1, "招聘信息", "http://7xu5hi.com1.z0.glb.clouddn.com/1607141136300779669.png"),

    Lease(2, "房屋租赁", "http://7xu5hi.com1.z0.glb.clouddn.com/1607141136299341686.png"),

    Rent(14, "个人求租", "http://7xu5hi.com1.z0.glb.clouddn.com/1607141136299341686.png"),

    Repair(3, "维修服务", "http://7xu5hi.com1.z0.glb.clouddn.com/1607141136300087449.png"),

    Education(4, "教育培训", "http://7xu5hi.com1.z0.glb.clouddn.com/1607141142421704963.png"),

    FamilyEducation(13, "家教信息", "http://7xu5hi.com1.z0.glb.clouddn.com/1607141142421704963.png"),

    Homemaking(5, "家政服务", "http://7xu5hi.com1.z0.glb.clouddn.com/1607141142420386759.png"),

    Secondhand(6, "二手交易", "http://7xu5hi.com1.z0.glb.clouddn.com/1607141136297502608.png"),

    buy(12, "二手求购", "http://7xu5hi.com1.z0.glb.clouddn.com/1607141136297502608.png"),

    Divination(7, "风水咨询", "http://7xu5hi.com1.z0.glb.clouddn.com/1607141142419748227.png"),

    Law(8, "法律咨询", "http://7xu5hi.com1.z0.glb.clouddn.com/1607141142417176015.png"),

    Health(9, "健康咨询", "http://7xu5hi.com1.z0.glb.clouddn.com/1607141142421064806.png"),

    NewPosition(16, "*求职*", "http://7xu5hi.com1.z0.glb.clouddn.com/1607141136300779669.png"),

    NewRecruit(15, "*招聘*", "http://7xu5hi.com1.z0.glb.clouddn.com/1607141136300779669.png");

    private int value;

    private String name;

    private String picUrl;

    InformationType(int value, String name, String picUrl) {
        this.value = value;
        this.name = name;
        this.picUrl = picUrl;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public static InformationType getInformationTypeByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (InformationType type : InformationType.values()) {
            if (type.getValue() == value.intValue()) {
                return type;
            }
        }
        return null;
    }

}
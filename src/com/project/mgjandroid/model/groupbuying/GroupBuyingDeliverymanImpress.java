package com.project.mgjandroid.model.groupbuying;

import com.project.mgjandroid.model.Entity;

public class GroupBuyingDeliverymanImpress extends Entity{

    private String impress;
    private boolean checked;

    public String getImpress() {
        return impress;
    }

    public void setImpress(String impress) {
        this.impress = impress;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

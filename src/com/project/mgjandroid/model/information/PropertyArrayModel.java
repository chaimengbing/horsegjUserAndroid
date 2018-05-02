package com.project.mgjandroid.model.information;

import com.project.mgjandroid.model.Entity;

import java.util.ArrayList;

/**
 * Created by yuandi on 2016/11/16.
 */

public class PropertyArrayModel extends Entity {

    private int code;

    private String uuid;

    private ArrayList<Property> value;

    private boolean success;

    private String servertime;

    public String getServertime() {
        return servertime;
    }

    public void setServertime(String servertime) {
        this.servertime = servertime;
    }

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

    public ArrayList<Property> getValue() {
        return value;
    }

    public void setValue(ArrayList<Property> value) {
        this.value = value;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class Property extends Entity {

        private String id;

        private String name;

        private boolean checked;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }
}

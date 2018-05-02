package com.project.mgjandroid.h5container.models;

import java.io.Serializable;

public class AlertModel implements Serializable {

    private String title;

    private String message;

    private String button;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }
}

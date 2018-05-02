package com.project.mgjandroid.h5container.models;


import java.io.Serializable;

public class ConfirmModel implements Serializable {

    private String title;

    private String message;

    private String okButton;

    private String cancelButton;

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

    public String getOkButton() {
        return okButton;
    }

    public void setOkButton(String okButton) {
        this.okButton = okButton;
    }

    public String getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(String cancelButton) {
        this.cancelButton = cancelButton;
    }
}

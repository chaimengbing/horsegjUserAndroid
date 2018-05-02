package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

public class AddImage extends Entity {
    private boolean hasImage;
    private String imageUrl;

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
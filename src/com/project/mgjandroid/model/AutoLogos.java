package com.project.mgjandroid.model;

/**
 * Created by SunXueLiang on 2017-05-09.
 */

public class AutoLogos extends Entity {
    private String imgUrl;
    private String fontLetter;
    private String brand;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getFontLetter() {
        return fontLetter;
    }

    public void setFontLetter(String fontLetter) {
        this.fontLetter = fontLetter;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}

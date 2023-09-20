package com.savita.nasa_app.models;

public class MainItem {
    private String title;
    private int imageResourceId;
    private Class linkTo;

    public MainItem(String title, int imageResourceId, Class linkTo) {
        this.title = title;
        this.imageResourceId = imageResourceId;
        this.linkTo = linkTo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public Class getLinkTo() {
        return linkTo;
    }

    public void setLinkTo(Class linkTo) {
        this.linkTo = linkTo;
    }
}

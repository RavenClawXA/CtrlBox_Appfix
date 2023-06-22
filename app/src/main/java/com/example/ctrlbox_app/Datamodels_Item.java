package com.example.ctrlbox_app;

import java.util.Base64;

public class Datamodels_Item {
    private String item;
    private String picture;

    public Datamodels_Item(String item , String picture){
        this.item = item;
        this.picture = picture;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}

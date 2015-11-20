package com.tars.synthesis.bean;

import java.io.Serializable;

/**
 * Created by kyly on 2015/11/18.
 */
public class Category implements Serializable {
    private  int id;
    private int imageId;
    private String name;

    public Category(int imageresId,String name){
        this.imageId = imageresId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}

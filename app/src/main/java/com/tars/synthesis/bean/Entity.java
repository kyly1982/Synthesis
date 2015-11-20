package com.tars.synthesis.bean;

import java.io.Serializable;

/**
 * Created by kyly on 2015/11/18.
 */
public class Entity implements Serializable{
    private int id;
    private int expreNum;
    private String imgPath;
    private String viewName;
    private String shortDesc;
    private String insertTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExpreNum() {
        return expreNum;
    }

    public void setExpreNum(int expreNum) {
        this.expreNum = expreNum;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }


    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }
}

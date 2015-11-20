package com.tars.synthesis.bean;

import java.io.Serializable;

/**
 * Created by kyly on 2015/11/18.
 */
public class Entity implements Serializable{
    private int id;
    private int expreNum;
    private String resId;
    private String bigKinds;
    private String smallKinds;
    private String imgPath;
    private String viewName;
    private String detailDesc;
    private String shortDesc;
    private String expression;
    private String includeData;
    private String insertTime;



    public Entity(String name,String desc){
        this.viewName = name;
        this.detailDesc = desc;
    }

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

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getBigKinds() {
        return bigKinds;
    }

    public void setBigKinds(String bigKinds) {
        this.bigKinds = bigKinds;
    }

    public String getSmallKinds() {
        return smallKinds;
    }

    public void setSmallKinds(String smallKinds) {
        this.smallKinds = smallKinds;
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

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getIncludeData() {
        return includeData;
    }

    public void setIncludeData(String includeData) {
        this.includeData = includeData;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }
}

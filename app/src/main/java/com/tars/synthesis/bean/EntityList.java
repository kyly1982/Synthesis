package com.tars.synthesis.bean;

import java.util.ArrayList;

/**
 * Created by kyly on 2015/11/19.
 */
public class EntityList {
    private int allCount;
    private int page;
    private int pageCount;
    private int pageSize;
    private ArrayList<Entity> data;

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public ArrayList<Entity> getData() {
        return data;
    }

    public void setData(ArrayList<Entity> data) {
        this.data = data;
    }
}

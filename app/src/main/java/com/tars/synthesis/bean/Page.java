package com.tars.synthesis.bean;

/**
 * Created by kyly on 2015/11/19.
 */
public class Page {
    private int allCount;
    private int page;
    private int pageCount;
    private int pageSize;

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
        if (0 < pageSize){
            pageCount = allCount / pageSize;
            if (0 < allCount % pageSize){
                pageCount++;
            }
            return pageCount;
        } else {
            pageCount = 1;
            return pageCount;
        }
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

    public int getNextPage(){
        if (getPageCount() == page){
            return page;
        } else {
            return page+1;
        }
    }

    public boolean EOP(){
        return  page ==  getNextPage();
    }
}

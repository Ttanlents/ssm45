package com.yjf.entity;

/**
 * @author 余俊锋
 * @date 2020/9/16 18:30
 */
public class Page<T> {
    private T data;
    //当前页
    private int pageCurrent;

    //每页数
    private int pageSize;

    //共记录条数
    private int count;

    //共页数
    private int pageTotal;

    public Page() {
    }

    public Page(int pageCurrent, int pageSize, int count) {
        this.count = count;
        this.pageSize = pageSize;
        this.pageTotal=this.count%this.pageSize==0?count/this.pageSize:count/this.pageSize+1;
        if (pageCurrent<=0){
            this.pageCurrent=1;
        }else if (pageCurrent>getPageTotal()&&getPageTotal()>0){
            this.pageCurrent=getPageTotal();
        }else {
            this.pageCurrent=pageCurrent;
        }

    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public int getPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(int pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        this.pageTotal=this.count%this.pageSize==0?count/this.pageSize:count/this.pageSize+1;
        if (pageCurrent<=0){
            this.pageCurrent=1;
        }else if (pageCurrent>getPageTotal()&&getPageTotal()>0){
            this.pageCurrent=getPageTotal();
        }
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }


}

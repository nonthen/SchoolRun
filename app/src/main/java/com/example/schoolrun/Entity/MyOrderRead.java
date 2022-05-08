package com.example.schoolrun.Entity;

import cn.bmob.v3.BmobObject;

//接单者是否已读消息实体类
public class MyOrderRead extends BmobObject {
    private int tid;//任务的id
    private int id;//接单人编号
    private String tname;//任务标题
    private int torderread;//接单者是否已读消息（0表示无异常或已读，1表示未读）
    private String torderreaddetails;//异常订单消息详情

    public int getTid() {
        return tid;
    }

    public MyOrderRead setTid(int tid) {
        this.tid = tid;
        return this;
    }

    public int getId() {
        return id;
    }

    public MyOrderRead setId(int id) {
        this.id = id;
        return this;
    }

    public String getTname() {
        return tname;
    }

    public MyOrderRead setTname(String tname) {
        this.tname = tname;
        return this;
    }

    public int getTorderread() {
        return torderread;
    }

    public MyOrderRead setTorderread(int torderread) {
        this.torderread = torderread;
        return this;
    }

    public String getTorderreaddetails() {
        return torderreaddetails;
    }

    public MyOrderRead setTorderreaddetails(String torderreaddetails) {
        this.torderreaddetails = torderreaddetails;
        return this;
    }
}

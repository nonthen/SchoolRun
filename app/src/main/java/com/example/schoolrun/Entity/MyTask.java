package com.example.schoolrun.Entity;

import cn.bmob.v3.BmobObject;

//任务实体类
public class MyTask extends BmobObject {

    private int tid;//任务编号
    private int uid;//发单人编号
    private int id;//接单人编号
    private int tkind;//任务分类
    private String tname;//任务标题
    private String tdetail;//任务详情
    private String targetaddress;//目标地址
    private String myaddress;//本人地址
    private int tphone;//发单人联系电话
    private Number tprice;//任务价格
    private int tcheck;//判断是否审核成功
    private int torder;//任务是否被接单
    private int tfinish;//任务是否已经完成
    private int tappraise;//评分

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTkind() {
        return tkind;
    }

    public void setTkind(int tkind) {
        this.tkind = tkind;
    }

    public String getTdetail() {
        return tdetail;
    }

    public void setTdetail(String tdetail) {
        this.tdetail = tdetail;
    }

    public String getMyaddress() {
        return myaddress;
    }

    public void setMyaddress(String myaddress) {
        this.myaddress = myaddress;
    }

    public int getTphone() {
        return tphone;
    }

    public void setTphone(int tphone) {
        this.tphone = tphone;
    }

    public int getTcheck() {
        return tcheck;
    }

    public void setTcheck(int tcheck) {
        this.tcheck = tcheck;
    }

    public int getTorder() {
        return torder;
    }

    public void setTorder(int torder) {
        this.torder = torder;
    }

    public int getTfinish() {
        return tfinish;
    }

    public void setTfinish(int tfinish) {
        this.tfinish = tfinish;
    }

    public int getTappraise() {
        return tappraise;
    }

    public void setTappraise(int tappraise) {
        this.tappraise = tappraise;
    }


    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTargetaddress() {
        return targetaddress;
    }

    public void setTargetaddress(String targetaddress) {
        this.targetaddress = targetaddress;
    }

    public Number getTprice() {
        return tprice;
    }

    public void setTprice(Number tprice) {
        this.tprice =  tprice;
    }


}

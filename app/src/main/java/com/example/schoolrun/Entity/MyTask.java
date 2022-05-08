package com.example.schoolrun.Entity;

import cn.bmob.v3.BmobObject;

//任务实体类
public class MyTask extends BmobObject {

    private int tid;//任务编号
    private int uid;//发单人编号
    private int id;//接单人编号
    private String tkind;//任务分类
    private String tname;//任务标题
    private String tdetail;//任务详情
    private String targetaddress;//目标地址
    private String myaddress;//本人地址
    private int tphone;//发单人联系电话
    private Number tprice;//任务价格
    private int tcheck;//判断是否审核成功
    private int torder;//任务是否被接单
    private String tordercanceldetails;//任务被取消的详细信息
    private int tordercheck;//判断异常订单是同意取消还是拒绝取消
    private int tfinish;//任务是否已经完成
    private float tappraise;//评分
    private String tappraisetext;//文字评论
    private int tappfinsh;//任务是否已经评论
    private int tasknotify;//是否删除任务通知
    private String tcheckerrordetails;//审核任务失败原因

    public int getTid() {
        return tid;
    }

    public MyTask setTid(int tid) {
        this.tid = tid;
        return this;
    }

    public int getUid() {
        return uid;
    }

    public MyTask setUid(int uid) {
        this.uid = uid;
        return this;
    }

    public int getId() {
        return id;
    }

    public MyTask setId(int id) {
        this.id = id;
        return this;
    }

    public String getTkind() {
        return tkind;
    }

    public MyTask setTkind(String tkind) {
        this.tkind = tkind;
        return this;
    }

    public String getTdetail() {
        return tdetail;
    }

    public MyTask setTdetail(String tdetail) {
        this.tdetail = tdetail;
        return this;
    }

    public String getMyaddress() {
        return myaddress;
    }

    public MyTask setMyaddress(String myaddress) {
        this.myaddress = myaddress;
        return this;
    }

    public int getTphone() {
        return tphone;
    }

    public MyTask setTphone(int tphone) {
        this.tphone = tphone;
        return this;
    }

    public int getTcheck() {
        return tcheck;
    }

    public MyTask setTcheck(int tcheck) {
        this.tcheck = tcheck;
        return this;
    }

    public int getTorder() {
        return torder;
    }

    public MyTask setTorder(int torder) {
        this.torder = torder;
        return this;
    }

    public int getTfinish() {
        return tfinish;
    }

    public MyTask setTfinish(int tfinish) {
        this.tfinish = tfinish;
        return this;
    }

    public float getTappraise() {
        return tappraise;
    }

    public MyTask setTappraise(float tappraise) {
        this.tappraise = tappraise;
        return this;
    }


    public String getTname() {
        return tname;
    }

    public MyTask setTname(String tname) {
        this.tname = tname;
        return this;
    }

    public String getTargetaddress() {
        return targetaddress;
    }

    public MyTask setTargetaddress(String targetaddress) {
        this.targetaddress = targetaddress;
        return this;
    }

    public Number getTprice() {
        return tprice;
    }

    public MyTask setTprice(Number tprice) {
        this.tprice =  tprice;
        return this;
    }

    public String getTappraisetext() {
        return tappraisetext;
    }

    public MyTask setTappraisetext(String tappraisetext) {
        this.tappraisetext = tappraisetext;
        return this;
    }

    public int getTappfinsh() {
        return tappfinsh;
    }

    public MyTask setTappfinsh(int tappfinsh) {
        this.tappfinsh = tappfinsh;
        return this;
    }

    public String getTordercanceldetails() {
        return tordercanceldetails;
    }

    public MyTask setTordercanceldetails(String tordercanceldetails) {
        this.tordercanceldetails = tordercanceldetails;
        return this;
    }

    public int getTordercheck() {
        return tordercheck;
    }

    public MyTask setTordercheck(int tordercheck) {
        this.tordercheck = tordercheck;
        return this;
    }

    public int getTasknotify() {
        return tasknotify;
    }

    public MyTask setTasknotify(int tasknotify) {
        this.tasknotify = tasknotify;
        return this;
    }

    public String getTcheckerrordetails() {
        return tcheckerrordetails;
    }

    public MyTask setTcheckerrordetails(String tcheckerrordetails) {
        this.tcheckerrordetails = tcheckerrordetails;
        return this;
    }
}

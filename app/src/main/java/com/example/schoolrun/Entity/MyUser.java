package com.example.schoolrun.Entity;


import android.widget.ImageView;

import cn.bmob.v3.BmobObject;

//用户实体类，对应bmob数据库的MyUser表
public class MyUser extends BmobObject {

    private int uid;//用户编号
    private String account;//对应user表中account，用户账号
    private String upassword;//用户密码
    private String uname;//用户名称
    private int ucheck;//判断当前用户是否具有接单员资格
//    private ImageView image;//用户头像
    private float ureputation;//用户信誉值
    private int goodappraisecount;//好评数
    private int badappraisecount;//差评数


    public MyUser() {
    }


    public String getAccount() {
        return account;
    }

    public MyUser setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getUpassword() {
        return upassword;
    }

    public MyUser setUpassword(String upassword) {
        this.upassword = upassword;
        return this;
    }

    public int getUid() {
        return uid;
    }

    public MyUser setUid(int uid) {
        this.uid = uid;
        return this;
    }

    public int getUcheck() {
        return ucheck;
    }

    public MyUser setUcheck(int ucheck) {
        this.ucheck = ucheck;
        return this;
    }

    public String getUname() {
        return uname;
    }

    public MyUser setUname(String uname) {
        this.uname = uname;
        return this;
    }

    public float getUreputation() {
        return ureputation;
    }

    public MyUser setUreputation(float ureputation) {
        this.ureputation = ureputation;
        return this;
    }

    public int getGoodappraisecount() {
        return goodappraisecount;
    }

    public MyUser setGoodappraisecount(int goodappraisecount) {
        this.goodappraisecount = goodappraisecount;
        return this;
    }

    public int getBadappraisecount() {
        return badappraisecount;
    }

    public MyUser setBadappraisecount(int badappraisecount) {
        this.badappraisecount = badappraisecount;
        return this;
    }
}


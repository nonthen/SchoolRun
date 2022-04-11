package com.example.schoolrun.Entity;


import android.widget.ImageView;

import cn.bmob.v3.BmobObject;

//用户实体类，对应bmob数据库的MyUser表
public class MyUser extends BmobObject {

    private int uid;//用户编号
    private String account;//对应user表中account，用户账号
    private String upassword;//用户密码
//    private ImageView image;//用户头像

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

}

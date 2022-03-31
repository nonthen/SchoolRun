package com.example.schoolrun.Entity;


import cn.bmob.v3.BmobObject;

//用户实体类，对应bmob数据库的MyUser表
public class MyUser extends BmobObject {

    private String account;//对应user表中account，用户账号
    private String upassword;//用户密码

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



}

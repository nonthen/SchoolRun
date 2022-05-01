package com.example.schoolrun.Entity;


import cn.bmob.v3.BmobObject;

//用户实体类，对应bmob数据库的MyUser表
public class MyUser extends BmobObject {

    private int uid;//用户编号
    private String account;//对应user表中account，用户账号
    private String upassword;//用户密码
    private String uname;//用户名称
    private int ucheck;//判断当前用户是否具有接单员资格
    private String sex;//用户性别
    private String income;//收益
    private String phone;//用户电话
    private String qq;//qq号码
//    private ImageView image;//用户头像
    private float ureputation;//用户信誉值
    private int goodappraisecount;//好评数
    private int badappraisecount;//差评数
    private int usubapplicate;//表示用户是否提交申请
    private String urealname;//用户真实姓名
    private String uidentitycard;//用户身份证号码


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

    public int getUsubapplicate() {
        return usubapplicate;
    }

    public MyUser setUsubapplicate(int usubapplicate) {
        this.usubapplicate = usubapplicate;
        return this;
    }

    public String getUrealname() {
        return urealname;
    }

    public MyUser setUrealname(String urealname) {
        this.urealname = urealname;
        return this;
    }

    public String getUidentitycard() {
        return uidentitycard;
    }

    public MyUser setUidentitycard(String uidentitycard) {
        this.uidentitycard = uidentitycard;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public MyUser setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getIncome() {
        return income;
    }

    public MyUser setIncome(String income) {
        this.income = income;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public MyUser setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getQq() {
        return qq;
    }

    public MyUser setQq(String qq) {
        this.qq = qq;
        return this;
    }
}


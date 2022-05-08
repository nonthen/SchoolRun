package com.example.schoolrun;

import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Activity.MainActivity;
import com.example.schoolrun.Activity.Register;
import com.example.schoolrun.Activity.RootMainActivity;
import com.example.schoolrun.Entity.MyUser;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
//登录界面
public class LoginActivity extends AppCompatActivity  {

    private EditText etAccount;//输入用户的账户名
    private EditText etPwd;//输入密码
    private Button btLogin;//登录按钮
    private Button register;//注册按钮
    public static String uaccount;//当前用户账号
    public static int uid;//当前用户id
    private ImageButton imageButton;
    private boolean isHideFirst = true;// 输入框密码是否是隐藏的，默认为true
    public static float ureputation;//当前用户信誉值
    public static int ucheck;//当前用户是否具有接单资格

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能

        etAccount = findViewById(R.id.et_account);//用户的账户名
        etPwd = findViewById(R.id.et_pwd);//密码的形式
        btLogin = findViewById(R.id.bt_login);//登陆按钮的形式
        register = findViewById(R.id.bt_register);//登陆按钮的形式
        imageButton = findViewById(R.id.iv_pwd_switch);

        //当前登陆活动设置一个监听事件
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                String userAccount = etAccount.getText().toString().trim();//获取文本框的数据
                String passWord = etPwd.getText().toString().trim();
                System.out.println(userAccount + passWord);
                Intent intent = new Intent();
                BmobQuery<MyUser> bmobQuery = new BmobQuery<>();
                String bql = "select * from MyUser where account = ? and upassword = ?";
                bmobQuery.setSQL(bql);
                bmobQuery.setPreparedParams(new Object[]{userAccount, passWord});

                bmobQuery.doSQLQuery(new SQLQueryListener<MyUser>() {
                    @Override
                    public void done(BmobQueryResult<MyUser> bmobQueryResult, BmobException e) {
                        List<MyUser> list = (List<MyUser>) bmobQueryResult.getResults();
                        if (list != null && list.size() > 0) {//存在一个匹配的用户
                            Snackbar.make(btLogin, "登录成功：" + userAccount, Snackbar.LENGTH_LONG).show();
                            uaccount = userAccount;//将当前用户的账号赋值给uaccount，uaccount作为整个项目的变量
                            uid = list.get(0).getUid();//将当前的用户id赋值给uid，uid作为整个项目的变量
                            ucheck=list.get(0).getUcheck();
                            ureputation=list.get(0).getUreputation();
                            if (userAccount.equals("root")&&passWord.equals("root")){
                                Intent intent=new Intent(LoginActivity.this, RootMainActivity.class);
                                startActivity(intent);
                                finish();//释放资源
                            }else {
                                Map<String, String> mHashMap;
                                List<Map<String, String>> mapList = new ArrayList<>();
                                for (MyUser myuser : list) {
                                    String objectId, uid, userAccount, passWord, uname, qq, sex, phone;
                                    objectId = String.valueOf(myuser.getObjectId());
                                    uid = String.valueOf(myuser.getUid());
                                    System.out.println("objectid:" + myuser.getObjectId() + "uid：" + myuser.getUid() + "昵称：" + myuser.getUname());
                                    intent.setClass(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("uid", uid);
                                    intent.putExtra("objectId", objectId);
                                    startActivity(intent);
                                    finish();//释放资源
                                }
                            }
                        } else {
                            Snackbar.make(btLogin, "登录失败", Snackbar.LENGTH_LONG).show();
                        }


                    }

                });

            }

        });


        imageButton.setOnClickListener(new View.OnClickListener() {//密码隐藏与显现
            @Override
            public void onClick(View v) {
                if (isHideFirst) {
                    //设置EditText文本为可见的
                    imageButton.setImageResource(R.drawable.ic_eye_open);
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    imageButton.setImageResource(R.drawable.ic_eye_close);
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHideFirst = !isHideFirst;
                etPwd.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = etPwd.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }

            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, Register.class);
                startActivity(intent);
            }

        });


    }


}





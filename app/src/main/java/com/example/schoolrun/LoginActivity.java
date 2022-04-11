package com.example.schoolrun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.schoolrun.Activity.MainActivity;
import com.example.schoolrun.Activity.ReleaseTask;
import com.example.schoolrun.Entity.MyUser;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
//登录界面
public class LoginActivity extends AppCompatActivity {

    private EditText etAccount;//输入用户的账户名
    private EditText etPwd;//输入密码
    private Button btLogin;//登录按钮
    public static String uaccount;//当前用户账号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能

        etAccount = findViewById(R.id.et_account);//用户的账户名
        etPwd = findViewById(R.id.et_pwd);//密码的形式
        btLogin = findViewById(R.id.bt_login);//登陆按钮的形式

        //当前登陆活动设置一个监听事件
        btLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                login();

            }

        });

    }

    public void login() {//验证此账号密码是否正确

        String userAccount = etAccount.getText().toString().trim();//获取文本框的数据
        String passWord = etPwd.getText().toString().trim();
        System.out.println(userAccount+passWord);

        BmobQuery<MyUser> bmobQuery = new BmobQuery<>();
        String bql = "select * from MyUser where account = ? and upassword = ?";
        bmobQuery.setSQL(bql);
        bmobQuery.setPreparedParams(new Object[]{userAccount,passWord});

        bmobQuery.doSQLQuery(new SQLQueryListener<MyUser>() {
            @Override
            public void done(BmobQueryResult<MyUser> bmobQueryResult, BmobException e) {
                List<MyUser> list = (List<MyUser>) bmobQueryResult.getResults();
                if (list!=null && list.size()>0){//存在一个匹配的用户
                    Snackbar.make(btLogin, "登录成功：" + userAccount, Snackbar.LENGTH_LONG).show();
                    uaccount=userAccount;//将当前用户的账号赋值给uaccount，uaccount作为整个项目的变量
                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();//释放资源
                }
                else{
                    Snackbar.make(btLogin, "登录失败", Snackbar.LENGTH_LONG).show();
                }


//                if (e == null) {
//                    List<MyUser> list = (List<MyUser>) bmobQueryResult.getResults();
//                    if (list!=null && list.size()>0) {
//                        //成功
//                        Snackbar.make(btLogin, "登录成功：" + userAccount, Snackbar.LENGTH_LONG).show();
//                    }
//
//                }else {
//                    //查询失败
//                    Log.e("BMOB", e.toString());
//                    Snackbar.make(btLogin, "登录失败", Snackbar.LENGTH_LONG).show();
//
//                }
            }

        });

//        bmobQuery.getObject("k4b8DDDI", new QueryListener<MyUser>() {
//            @Override
//            public void done(MyUser myUser, BmobException e) {
//                if (e == null) {
//                    Snackbar.make(btLogin, "查询成功：" + myUser.getAccount(), Snackbar.LENGTH_LONG).show();
//                } else {
//                    Log.e("BMOB", e.toString());
//                    Snackbar.make(btLogin, e.getMessage(), Snackbar.LENGTH_LONG).show();
//                }
//            }
//        });


    }


}
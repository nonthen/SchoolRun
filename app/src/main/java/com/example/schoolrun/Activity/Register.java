package com.example.schoolrun.Activity;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Entity.MyUser;
import com.example.schoolrun.LoginActivity;
import com.example.schoolrun.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class Register extends AppCompatActivity{

    private Button button;
    private ImageButton imageButton;
    private boolean isHideFirst = true;// 输入框密码是否是隐藏的，默认为true
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能
        EditText textView1 = findViewById(R.id.account);
        EditText textView2 = findViewById(R.id.password);
        button = findViewById(R.id.bt_register);
        Intent intent = getIntent();
        imageButton = findViewById(R.id.iv_pwd_switch);

        //这里获取了任务主页
        BmobQuery<MyUser> bmobQuery=new BmobQuery<MyUser>();
        bmobQuery.findObjects(new FindListener<MyUser>() {

            @Override
            public void done(List<MyUser> list, BmobException e) {
                if (e == null) {

                    Toast.makeText(Register.this,"成功，共"+list.size()+"条数据",Toast.LENGTH_SHORT).show();
                    int size=list.size();
                    //当前登陆活动设置一个监听事件
                    button.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            String account = textView1.getText().toString().trim();//获取文本框的数据
                            String password = textView2.getText().toString().trim();
                            //Intent intent = getIntent();
                            //BmobQuery<MyUser> bmobQuery = new BmobQuery<>();

                            MyUser p1 = new MyUser();
                            p1.setAccount(account);
                            p1.setUpassword(password);
                            p1.setUname(null);
                            p1.setSex(null);
                            p1.setQq(null);
                            p1.setIncome(null);
                            p1.setUcheck(0);
                            p1.setUid(size+1);
                            p1.setUreputation(6);
                            System.out.println("账号：" + account + "密码：" + password);

                            p1.save(new SaveListener<String>() {
                                @Override
                                public void done(String objectId, BmobException e) {
                                    if (e == null) {
                                        Snackbar.make(button, "注册成功", Snackbar.LENGTH_LONG).show();
                                        intent.setClass(Register.this, LoginActivity.class);

                                    } else {
                                        Snackbar.make(button, "注册失败", Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });


        imageButton.setOnClickListener(new View.OnClickListener() {//密码隐藏与显现
            @Override
            public void onClick(View v) {
                if (isHideFirst) {
                    //设置EditText文本为可见的
                    imageButton.setImageResource(R.drawable.ic_eye_open);
                    textView2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    imageButton.setImageResource(R.drawable.ic_eye_close);
                    textView2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHideFirst = !isHideFirst;
                textView2.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = textView2.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }

            }
        });
    }
}

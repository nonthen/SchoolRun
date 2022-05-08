package com.example.schoolrun.userbackground;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Entity.MyUser;
import com.example.schoolrun.R;
import com.google.android.material.snackbar.Snackbar;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

//后台修改密码
public class Changepasswordbackground extends AppCompatActivity {
        private Button button;
        private RadioGroup mRadioGroup;
        private RadioButton tab1,tab2,tab3;  //3个单选按钮
        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.change_password_background);
            Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能
            EditText textView1 = findViewById(R.id.shownewpassword);
            button = findViewById(R.id.change_passwordbutton);
            Intent intent = getIntent();
            String useruid = intent.getStringExtra("uid");
            String objectId = intent.getStringExtra("objectId");
            String uname = intent.getStringExtra("uname");
            TextView textView2 = (TextView) findViewById(R.id.showuid);
            TextView textView3 = findViewById(R.id.showuname);
            textView2.setText(useruid);
            textView3.setText(uname);
            //当前登陆活动设置一个监听事件
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    String newpassword = textView1.getText().toString().trim();//获取文本框的数据
                    System.out.println(objectId + useruid + newpassword);
                    int id = Integer.parseInt(useruid);
                    MyUser p3 = new MyUser();
                    p3.setUpassword(newpassword);
                    p3.setUid(id);
                    p3.update(objectId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Snackbar.make(button, "修改成功", Snackbar.LENGTH_LONG).show();
                                finish();
                            } else {
                                Snackbar.make(button, "修改失败", Snackbar.LENGTH_LONG).show();
                            }
                        }

                    });


                }
            });
        }
    }



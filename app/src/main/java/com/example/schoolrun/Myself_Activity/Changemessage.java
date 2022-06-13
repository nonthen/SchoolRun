package com.example.schoolrun.Myself_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Activity.TestMeAc;
import com.example.schoolrun.Entity.MyUser;
import com.example.schoolrun.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

//修改个人信息
public class Changemessage extends AppCompatActivity {
    private Button button,back;
    private RadioGroup mRadioGroup;
    private RadioButton tab1,tab2,tab3;  //3个单选按钮
    String uid,uname,sex,qq,phone,Income;
    String newuid,newuname,newsex,newqq,newphone;
    int check,Goodappraisecount,Badappraisecount;
    float Ureputation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_message);
        //初始化控件
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能
        EditText textView1 = findViewById(R.id.changeuname);
        EditText textView2 = findViewById(R.id.changesex);
        EditText textView3 = findViewById(R.id.changeqq);
        EditText textView4 = findViewById(R.id.changephone);
        button = findViewById(R.id.change_messagebutton);
        back = findViewById(R.id.fanhui);
        Intent intent = getIntent();
        String useruid = intent.getStringExtra("uid");
        String objectId = intent.getStringExtra("objectId");

        BmobQuery<MyUser> bmobQuery = new BmobQuery<MyUser>();
        bmobQuery.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {

                if (e == null) {
                    SimpleAdapter simpleAdapter;
                    String id = null;
                    for (MyUser myuser : list) {
                        uid = String.valueOf(myuser.getUid());
                        if (uid.equals(useruid)) {
                            uname = String.valueOf(myuser.getUname());
                            qq = String.valueOf(myuser.getQq());
                            sex = String.valueOf(myuser.getSex());
                            phone = String.valueOf(myuser.getPhone());
                            check = myuser.getUcheck();
                            Ureputation = myuser.getUreputation();
                            Goodappraisecount = myuser.getGoodappraisecount();
                            Badappraisecount = myuser.getBadappraisecount();
                            Income = myuser.getIncome();
                            if (uname.equals("")) {
                                uname = null;
                            } else if (qq.equals("")) {
                                qq = null;
                            } else if (sex.equals("")) {
                                sex = null;
                            } else if (phone.equals("")) {
                                phone = null;
                            }
                        }
                    }

                    //当前登陆活动设置一个监听事件
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            newuname = textView1.getText().toString().trim();//获取文本框的数据
                            newsex = textView2.getText().toString().trim();
                            newqq = textView3.getText().toString().trim();//获取文本框的数据
                            newphone = textView4.getText().toString().trim();
                            if (newuname.equals("")) {
                                newuname = uname;
                            } else if (newsex.equals("")) {
                                newsex = sex;
                            } else if (newqq.equals("")) {
                                newqq = qq;
                            } else if (newphone.equals("")) {
                                newphone = phone;
                            }

                            BmobQuery<MyUser> bmobQuery = new BmobQuery<>();
                            System.out.println(objectId + useruid + uname + sex + qq + phone);
                            int id = Integer.parseInt(useruid);
                            MyUser p2 = new MyUser();
                            p2.setUid(id);
                            System.out.println("id:"+id);
                            p2.setUname(newuname);
                            p2.setSex(newsex);
                            p2.setQq(newqq);
                            p2.setPhone(newphone);
                            p2.setIncome(Income);
                            p2.setUcheck(check);
                            p2.setGoodappraisecount(Goodappraisecount);
                            p2.setBadappraisecount(Badappraisecount);
                            p2.setUreputation(Ureputation);
                            p2.update(objectId, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        Snackbar.make(button, "修改成功", Snackbar.LENGTH_LONG).show();
                                    } else {
                                        Snackbar.make(button, "修改失败", Snackbar.LENGTH_LONG).show();
                                    }
                                }

                            });
                        }
                    });
                }
            }
        });


        back.setOnClickListener(new View.OnClickListener() {//返回
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Changemessage.this, TestMeAc.class);
                String uid=intent.getStringExtra("uid");
                String objectId=intent.getStringExtra("objectId");
                a.putExtra("uid", uid);
                a.putExtra("objectId", objectId);
                startActivity(a);
                finish();
            }
        });



    }
}
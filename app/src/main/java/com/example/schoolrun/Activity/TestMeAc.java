package com.example.schoolrun.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Entity.MyUser;
import com.example.schoolrun.Myself_Activity.Apply;
import com.example.schoolrun.Myself_Activity.AppraiseActivity;
import com.example.schoolrun.Myself_Activity.Changepassword;
import com.example.schoolrun.Myself_Activity.Ideafeedback;
import com.example.schoolrun.Myself_Activity.Lookincome;
import com.example.schoolrun.Myself_Activity.Looktask;
import com.example.schoolrun.Myself_Activity.Mymessage;
import com.example.schoolrun.Myself_Activity.NotifyActivity;
import com.example.schoolrun.Myself_Activity.Softwaresetting;
import com.example.schoolrun.Myself_Activity.ViewOrderlistActivity;
import com.example.schoolrun.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

//个人信息界面
public class TestMeAc extends AppCompatActivity implements View.OnClickListener {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private ImageButton button5;
    private Button button6;
    private Button button7;
    private RadioGroup mRadioGroup;
    private RadioButton tab1, tab2, tab3;  //3个单选按钮
    public static String check;

    private Button backProcess;

    private Button appraisebutton;//评价服务
    private Button view_order;//查看订单
    private ImageButton xiaoxi;//查看系统通知

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity);
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能

        //初始化控件
        mRadioGroup = findViewById(R.id.rg_tab);
        tab1 = findViewById(R.id.rb_home);
        tab2 = findViewById(R.id.rb_task);//发布任务
        tab3 = findViewById(R.id.rb_me);
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        button1 = findViewById(R.id.my_message);//跳转到查看个人信息界面
        button2 = findViewById(R.id.change_password);//跳转到修改密码界面
        button3 = findViewById(R.id.look_income);//跳转到查看收益界面
        button4 = findViewById(R.id.look_task);//跳转到查看任务界面
        button5 = findViewById(R.id.software_setting);//跳转到软件设置界面
        button6 = findViewById(R.id.idea_feedback);//跳转到软件设置界面
        TextView checkView = (TextView) findViewById(R.id.check);//判断是否是普通用户
        TextView uidview = (TextView) findViewById(R.id.uid);//判断是否是普通用户
        button7 = findViewById(R.id.apply);

        appraisebutton=findViewById(R.id.appraisebutton);
        view_order=findViewById(R.id.look_order);

        backProcess=findViewById(R.id.back_process);
        xiaoxi=findViewById(R.id.xiaoxi);

        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");
        String objectId = intent.getStringExtra("objectId");

        //这里获取了用户主页
        BmobQuery<MyUser> bmobQuery = new BmobQuery<MyUser>();
        bmobQuery.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                if (e == null) {
                    SimpleAdapter simpleAdapter;
                    Log.d("path", "查询成功");
                    Map<String, String> mHashMap;
                    String tempTprice, tempTid, tempTphone, tempTkind;
                    Toast.makeText(TestMeAc.this, "成功，共" + list.size() + "条数据", Toast.LENGTH_SHORT).show();
                    List<Map<String, String>> mapList = new ArrayList<>();
                    for (MyUser myuser : list) {
                        String Uid = String.valueOf(myuser.getUid());
                        if (Uid.equals(uid)) {
                            check = String.valueOf(myuser.getUcheck());
                            System.out.println("check:"+ check);
                            uidview.setText("uid:"+uid);
                            String i = "1.0";
                            if (check.equals(i)) {
                                checkView.setText("接单用户");
                            }
                            else{
                                checkView.setText("普通用户");
                            }
                        }

                        button1.setOnClickListener(new View.OnClickListener() {//个人信息
                            @Override
                            public void onClick(View view) {
                                Intent a = new Intent();
                                a.setClass(TestMeAc.this, Mymessage.class);
                                String uid = intent.getStringExtra("uid");
                                String objectId = intent.getStringExtra("objectId");
                                System.out.println("id是：" + uid + "objectId是：" + objectId);
                                a.putExtra("uid", uid);
                                a.putExtra("objectId", objectId);
                                startActivity(a);
                            }
                        });
                        button2.setOnClickListener(new View.OnClickListener() {//修改密码
                            @Override
                            public void onClick(View view) {
                                Intent b = new Intent();
                                b.setClass(TestMeAc.this, Changepassword.class);
                                String uid = intent.getStringExtra("uid");
                                String objectId = intent.getStringExtra("objectId");
                                System.out.println("id是：" + uid + "objectId是：" + objectId);
                                b.putExtra("uid", uid);
                                b.putExtra("objectId", objectId);
                                TestMeAc.this.startActivity(b);
                            }
                        });
                        button3.setOnClickListener(new View.OnClickListener() {//查看收益
                            @Override
                            public void onClick(View view) {
                                Intent c = new Intent();
                                c.setClass(TestMeAc.this, Lookincome.class);
                                String uid = intent.getStringExtra("uid");
                                String objectId = intent.getStringExtra("objectId");
                                System.out.println("id是：" + uid + "objectId是：" + objectId);
                                c.putExtra("uid", uid);
                                c.putExtra("objectId", objectId);
                                TestMeAc.this.startActivity(c);
                            }
                        });
                        button4.setOnClickListener(new View.OnClickListener() {//查看任务
                            @Override
                            public void onClick(View view) {
                                Intent d = new Intent();
                                d.setClass(TestMeAc.this, Looktask.class);
                                System.out.println("id是：" + uid + "objectId是：" + objectId);
                                d.putExtra("uid", uid);
                                d.putExtra("objectId", objectId);
                                System.out.println("+++++++++uid:"+uid);
                                TestMeAc.this.startActivity(d);
                            }
                        });
                        button5.setOnClickListener(new View.OnClickListener() {//软件设置
                            @Override
                            public void onClick(View view) {
                                Intent e = new Intent();
                                e.setClass(TestMeAc.this, Softwaresetting.class);
                                System.out.println("id是：" + uid + "objectId是：" + objectId);
                                e.putExtra("uid", uid);
                                e.putExtra("objectId", objectId);
                                System.out.println("+++++++++uid:"+uid);
                                TestMeAc.this.startActivity(e);
                            }
                        });
                        button6.setOnClickListener(new View.OnClickListener() {//意见反馈
                            @Override
                            public void onClick(View view) {
                                Intent f = new Intent();
                                f.setClass(TestMeAc.this, Ideafeedback.class);
                                TestMeAc.this.startActivity(f);
                            }
                        });
                        button7.setOnClickListener(new View.OnClickListener() {//申请申请接单者
                            @Override
                            public void onClick(View view) {
                                if (check.equals("1.0")) {
                                    Snackbar.make(button7, "您已经是接单者了，无需重复申请", Snackbar.LENGTH_LONG).show();
                                }
                                else{
                                    Intent g = new Intent();
                                    g.setClass(TestMeAc.this, Apply.class);
                                    g.putExtra("uid", uid);
                                    g.putExtra("objectId", objectId);
                                    TestMeAc.this.startActivity(g);
                                }
                            }
                        });

                        //查看任务进度
                        backProcess.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(TestMeAc.this,TaskProcess.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        //查看系统消息
                        xiaoxi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(TestMeAc.this, NotifyActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });


                        //点击评价服务
                        appraisebutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent button = new Intent(TestMeAc.this, AppraiseActivity.class);
                                startActivity(button);
                                finish();//释放资源
                            }
                        });

                        //点击查看订单
                        view_order.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent button = new Intent(TestMeAc.this, ViewOrderlistActivity.class);
                                startActivity(button);
                                finish();//释放资源
                            }
                        });


                    }


                }
            }
        });



    }
    @Override
    public void onClick (View view){
        Intent intent = getIntent();
        switch (view.getId()) {
            case R.id.rb_home:
                Intent button1 = new Intent(this, MainActivity.class);
                startActivity(button1);
                break;
            case R.id.rb_task:
                Intent button2 = new Intent(this, ReleaseTask.class);
                startActivity(button2);
                break;
            case R.id.rb_me:
                Intent button3 = new Intent(this, TestMeAc.class);
                String uid = intent.getStringExtra("uid");
                intent.putExtra("uid", uid);
                System.out.println("uid:" + uid);
                startActivity(button3);
                break;
        }
    }
}

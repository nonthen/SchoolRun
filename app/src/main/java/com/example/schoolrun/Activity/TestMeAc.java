package com.example.schoolrun.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Myself_Activity.AppraiseActivity;
import com.example.schoolrun.Myself_Activity.ViewOrderlistActivity;
import com.example.schoolrun.R;

import cn.bmob.v3.Bmob;

public class TestMeAc extends AppCompatActivity {

    private Button appraisebutton;//评价服务
    private Button view_order;//查看订单
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private RadioButton tab1,tab2,tab3;//底部导航栏

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymessage);//绑定发布任务布局
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能

        appraisebutton=findViewById(R.id.appraisebutton);
        view_order=findViewById(R.id.look_order);
        button1=findViewById(R.id.change_message);//跳转到查看个人信息界面
        button2=findViewById(R.id.change_password);//跳转到修改密码界面
        button3=findViewById(R.id.look_income);//跳转到查看收益界面
        button4=findViewById(R.id.look_task);//跳转到查看任务界面
        button5=findViewById(R.id.software_setting);//跳转到软件设置界面

        tab1=findViewById(R.id.rb_home);
        tab2=findViewById(R.id.rb_task);
        tab3=findViewById(R.id.rb_me);

        button1.setOnClickListener(new View.OnClickListener() {//个人信息
            @Override
            public void onClick(View view) {
//                Intent a = new Intent();
//                a.setClass(TestMeAc.this, Mymessage.class);
//                TestMeAc.this.startActivity(a);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {//修改密码
            @Override
            public void onClick(View view) {
//                Intent b = new Intent();
//                b.setClass(TestMeAc.this, Changemessage.class);
//                TestMeAc.this.startActivity(b);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {//查看收益
            @Override
            public void onClick(View view) {
//                Intent c = new Intent();
//                c.setClass(TestMeAc.this, Lookincome.class);
//                TestMeAc.this.startActivity(c);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {//查看任务
            @Override
            public void onClick(View view) {
//                Intent d = new Intent();
//                d.setClass(TestMeAc.this, Looktask.class);
//                TestMeAc.this.startActivity(d);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {//软件设置
            @Override
            public void onClick(View view) {
//                Intent e = new Intent();
//                e.setClass(TestMeAc.this, Softwaresetting.class);
//                TestMeAc.this.startActivity(e);
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

        //点击第一个按钮
        tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent button1 = new Intent(TestMeAc.this, MainActivity.class);
                startActivity(button1);
                finish();//释放资源
            }
        });

        //点击第二个按钮
        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent button2 = new Intent(TestMeAc.this, ReleaseTask.class);
                startActivity(button2);
                finish();//释放资源
            }
        });

        //点击第三个按钮
        tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent button3 = new Intent(TestMeAc.this, TestMeAc.class);
                startActivity(button3);
                finish();//释放资源
            }
        });


    }
}

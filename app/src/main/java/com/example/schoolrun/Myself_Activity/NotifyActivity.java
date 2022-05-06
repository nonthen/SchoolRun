package com.example.schoolrun.Myself_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Activity.TestMeAc;
import com.example.schoolrun.R;

import cn.bmob.v3.Bmob;

//系统通知
public class NotifyActivity extends AppCompatActivity {

    private ImageButton returnMe;
    private RadioButton ordernotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_notify);//绑定系统通知布局
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能

        returnMe=findViewById(R.id.returnMe);
        ordernotify=findViewById(R.id.ordernotify);

        //返回用户界面
        returnMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent button = new Intent(NotifyActivity.this, TestMeAc.class);
                startActivity(button);
                finish();//释放资源
            }
        });

        //查看订单消息通知
        ordernotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent button = new Intent(NotifyActivity.this, MyOrderNotificitionActivity.class);
                startActivity(button);
                finish();//释放资源
            }
        });

    }

}

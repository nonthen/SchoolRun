package com.example.schoolrun.Myself_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Activity.TestMeAc;
import com.example.schoolrun.LoginActivity;
import com.example.schoolrun.R;

//软件设置
public class Softwaresetting extends AppCompatActivity {
    private RadioGroup mRadioGroup;
    private RadioButton tab1,tab2,tab3;  //3个单选按钮
    private Button back,exit;
    String useruid;
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.software_setting);
        //初始化控件
        back = findViewById(R.id.fanhui);
        exit = findViewById(R.id.exit);
        Intent intent = getIntent();
        useruid = intent.getStringExtra("uid");
        String objectId = intent.getStringExtra("objectId");


        back.setOnClickListener(new View.OnClickListener() {//返回
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Softwaresetting.this, TestMeAc.class);
                String uid=intent.getStringExtra("uid");
                String objectId=intent.getStringExtra("objectId");
                a.putExtra("uid", uid);
                a.putExtra("objectId", objectId);
                startActivity(a);
                finish();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {//返回
            @Override
            public void onClick(View view) {
                Intent b = new Intent(Softwaresetting.this, LoginActivity.class);
                b.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                b.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(b);
            }
        });

    }
}

package com.example.schoolrun.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.R;

//管理员身份，进来的界面
public class RootMainActivity extends AppCompatActivity implements View.OnClickListener{
    private RadioButton bt1,bt2,bt3;  //3个单选按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_root);

        bt1=findViewById(R.id.bt_task);
        bt2=findViewById(R.id.bt_order);
        bt3=findViewById(R.id.bt_user);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_task:
                Intent button1 = new Intent(this,RootMainActivity.class);//任务
                startActivity(button1);
                break;
            case R.id.bt_order:
//                Intent button2 = new Intent(this,订单.class);
//                startActivity(button2);
                break;
            case R.id.bt_user:
//                Intent button3 = new Intent(this, 用户.class);
//                startActivity(button3);
                break;

        }
    }
}

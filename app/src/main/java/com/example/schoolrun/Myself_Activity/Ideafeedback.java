package com.example.schoolrun.Myself_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Activity.MainActivity;
import com.example.schoolrun.Activity.ReleaseTask;
import com.example.schoolrun.Activity.TestMeAc;
import com.example.schoolrun.R;
import com.google.android.material.snackbar.Snackbar;

//意见反馈
public class Ideafeedback extends AppCompatActivity implements View.OnClickListener{
    private RadioGroup mRadioGroup;
    private RadioButton tab1,tab2,tab3;  //3个单选按钮
    private Button button,back;
    String useruid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.idea_feedback);
        //初始化控件
        mRadioGroup=findViewById(R.id.rg_tab);
        tab1=findViewById(R.id.rb_home);
        tab2=findViewById(R.id.rb_task);//发布任务
        tab3=findViewById(R.id.rb_me);
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        button = findViewById(R.id.messagebutton);
        back = findViewById(R.id.fanhui);

        Intent intent = getIntent();
        useruid = intent.getStringExtra("uid");
        String objectId = intent.getStringExtra("objectId");
        //当前登陆活动设置一个监听事件
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Snackbar.make(button, "反馈成功", Snackbar.LENGTH_LONG).show();
                Intent c = new Intent();
                c.setClass(Ideafeedback.this, TestMeAc.class);
                Ideafeedback.this.startActivity(c);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {//返回
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Ideafeedback.this,TestMeAc.class);
                String uid=intent.getStringExtra("uid");
                String objectId=intent.getStringExtra("objectId");
                System.out.println("id是："+uid+"objectId是："+objectId);
                a.putExtra("uid", uid);
                a.putExtra("objectId", objectId);
                startActivity(a);
                finish();
            }
        });

    }
    @Override
    public void onClick(View view) {
        Intent intent = getIntent();
        switch (view.getId()){
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
                String uid=intent.getStringExtra("uid");
                String objectId=intent.getStringExtra("objectId");
                button3.putExtra("uid", uid);
                button3.putExtra("objectId", objectId);
                System.out.println("uid:"+uid+"objectId:"+objectId);
                startActivity(button3);
                break;
        }
    }
}

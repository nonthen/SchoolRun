package com.example.schoolrun.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private RadioGroup mRadioGroup;
    private RadioButton tab1,tab2,tab3;  //3个单选按钮



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能

        //初始化控件
        mRadioGroup=findViewById(R.id.rg_tab);
        tab1=findViewById(R.id.rb_home);
        tab2=findViewById(R.id.rb_task);//发布任务
        tab3=findViewById(R.id.rb_me);
        tab1.setOnClickListener(this);
        tab1.setOnClickListener(this);
        tab1.setOnClickListener(this);

        //这里获取了任务主页
        BmobQuery<MyTask> bmobQuery=new BmobQuery<MyTask>();
        bmobQuery.findObjects(new FindListener<MyTask>() {
            @Override
            public void done(List<MyTask> list, BmobException e) {
                if(e==null){
                    Log.d("path","查询成功");
                    ViewTaskAdapte viewTaskAdapte=new ViewTaskAdapte(MainActivity.this,R.layout.view_task_item_info,list);
                    ListView listView=findViewById(R.id.listView);
                    listView.setAdapter(viewTaskAdapte);
                }
                else {
                    Log.d("path","查询不成功");
                }
            }
        });


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rb_home:
                Intent button1 = new Intent(this,MainActivity.class);
                startActivity(button1);
                break;
            case R.id.rb_task:
                Intent button2 = new Intent(this,ReleaseTask.class);
                startActivity(button2);
                break;
            case R.id.rb_me:
                Intent button3 = new Intent(this,TestMeAc.class);
                startActivity(button3);
                break;

        }
    }
}

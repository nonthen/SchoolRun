package com.example.schoolrun.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
//任务进度
public class TaskProcess extends AppCompatActivity {
    private int tcheck;
    private String process;
    private int id;//接单人id
    int i=0;
    private ImageButton returnMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_process);
        returnMe=findViewById(R.id.returnMe);
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能

        BmobQuery<MyTask> bmobQuery = new BmobQuery<MyTask>();
        bmobQuery.findObjects(new FindListener<MyTask>() {
            @Override
            public void done(List<MyTask> list, BmobException e) {
                if (e == null) {
                    SimpleAdapter simpleAdapter;
                    Log.d("path", "查询成功");
                    Map<String, String> mHashMap;
                    String  tempTid, tempid;
                    Toast.makeText(TaskProcess.this, "成功，共" + list.size() + "条数据", Toast.LENGTH_SHORT).show();
                    List<Map<String, String>> mapList = new ArrayList<>();
                    for (MyTask myTask : list) {
                        id=myTask.getId();
                        tempTid = String.valueOf(myTask.getTid());
                        tempid=String.valueOf(id);
                        if(myTask.getTorder()==0){
                            process ="任务待接单中~";
                        }else if (myTask.getTorder()==1&&myTask.getTfinish()==0){
                            process ="配送员正在快马加鞭配送中~";
                        }else if (myTask.getTfinish()==1&&myTask.getTappfinsh()==0){
                            process ="您已成功收货~订单未评价，快去评价吧~";
                        }else if (myTask.getTappfinsh()==1){
                            process ="您已成功收货~完成评价~";
                        }else {
                            System.out.println("判断任务进度失败~");
                        }

                        tcheck=myTask.getTcheck();
                        if (tcheck==1) {
                            mHashMap = new HashMap<>();
                            mHashMap.put("tname", myTask.getTname());
                            mHashMap.put("tid", tempTid);
                            mHashMap.put("process", process);
                            mHashMap.put("id",tempid);
                            mapList.add(mHashMap);
                            System.out.println("标题id：" + myTask.getTid() + "接单状态：" + myTask.getTorder() + "收货：" + myTask.getTfinish()+"评价状态："+myTask.getTappfinsh());
                        } else {
                            System.out.println("获取任务进度失败~");
                        }
                    }
                    ListView listView = findViewById(R.id.task_listView);

                    listView.addHeaderView(new View(TaskProcess.this));
                    listView.addFooterView(new View(TaskProcess.this));

                    simpleAdapter = new SimpleAdapter(TaskProcess.this, mapList, R.layout.task_process_item_info, new String[]{"tname","process"}, new int[]{R.id.item_tname,R.id.item_torder});
                    listView.setAdapter(simpleAdapter);
                    simpleAdapter.notifyDataSetChanged();

                } else {
                    Log.d("path", "查询不成功");
                }
            }
        });

        //返回个人信息界面
        returnMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskProcess.this,TestMeAc.class);
                startActivity(intent);
                finish();
            }
        });
    }

}

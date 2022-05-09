package com.example.schoolrun.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

//普通用户任务列表主页
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private RadioGroup mRadioGroup;
    private RadioButton tab1,tab2,tab3;  //3个单选按钮
    private int torder,tcheck;
    public static Number price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能
        //初始化控件
        mRadioGroup = findViewById(R.id.rg_tab);
        tab1 = findViewById(R.id.rb_home);
        tab2 = findViewById(R.id.rb_task);//发布任务
        tab3 = findViewById(R.id.rb_me);
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);

        //这里获取了任务主页
        BmobQuery<MyTask> bmobQuery = new BmobQuery<MyTask>();
        bmobQuery.findObjects(new FindListener<MyTask>() {
            @Override
            public void done(List<MyTask> list, BmobException e) {

                if (e == null) {
                    SimpleAdapter simpleAdapter;
                    Log.d("path", "查询成功");
                    Map<String, String> mHashMap;
                    String tempTprice, tempTid, tempTphone, tempTkind;
                    List<Map<String, String>> mapList = new ArrayList<>();
                    for (MyTask myTask : list) {
                        price=0.9*myTask.getTprice().floatValue();
                        tempTprice = String.valueOf(price);
                        tempTid = String.valueOf(myTask.getTid());
                        tempTphone = String.valueOf(myTask.getTphone());
                        tempTkind = String.valueOf(myTask.getTkind());
                        torder = myTask.getTorder();
                        tcheck=myTask.getTcheck();
                        if (torder == 0&&tcheck==1) {//审核通过且未被接单任务
                            mHashMap = new HashMap<>();
                            mHashMap.put("tname", myTask.getTname());
                            mHashMap.put("targetaddress", myTask.getTargetaddress());
                            mHashMap.put("tprice", tempTprice);//价格还是原价!!!!!!!
                            mHashMap.put("tid", tempTid);
                            mHashMap.put("tdetail", myTask.getTdetail());
                            mHashMap.put("myaddress", myTask.getMyaddress());
                            mHashMap.put("tphone", tempTphone);
                            mHashMap.put("tkind", tempTkind);
                            mapList.add(mHashMap);
                            System.out.println("标题：" + myTask.getTname() + "目标地址：" + myTask.getTargetaddress() + "价格：" + myTask.getTprice());
                        } else {
                            System.out.println("清单已被其他同学接走~");
                        }
                    }
                    Toast.makeText(MainActivity.this, "成功，共" + mapList.size() + "条数据", Toast.LENGTH_SHORT).show();
                    ListView listView = findViewById(R.id.listView);

                    listView.addHeaderView(new View(MainActivity.this));//实现卡片listview
                    listView.addFooterView(new View(MainActivity.this));

                    simpleAdapter = new SimpleAdapter(MainActivity.this, mapList, R.layout.view_task_item_info, new String[]{"tname", "targetaddress", "tprice"}, new int[]{R.id.item_tname, R.id.item_targetaddress, R.id.item_tprice});
                    listView.setAdapter(simpleAdapter);
                    simpleAdapter.notifyDataSetChanged();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            //Bmob获取listview中某一行数据
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, DetailedInfoActivity.class);//点击每一项任务
                            intent.putExtra("tid", mapList.get(position-1).get("tid").toString());
                            System.out.println(position);
                            startActivity(intent);
                        }
                    });
                } else {
                    Log.d("path", "查询不成功");
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        Intent intent = getIntent();
        switch (view.getId()){
            case R.id.rb_home:
                Intent button1 = new Intent(this,MainActivity.class);
                String uid = intent.getStringExtra("uid");
                button1.putExtra("uid", uid);
                System.out.println("uid:" + uid);
                startActivity(button1);
                break;
            case R.id.rb_task:
                Intent button2 = new Intent(this,ReleaseTask.class);
                uid = intent.getStringExtra("uid");
                button2.putExtra("uid", uid);
                System.out.println("uid:" + uid);
                startActivity(button2);
                break;
            case R.id.rb_me:
                Intent button3 = new Intent(this, TestMeAc.class);
                uid=intent.getStringExtra("uid");
                String objectId=intent.getStringExtra("objectId");
                button3.putExtra("uid", uid);
                button3.putExtra("objectId", objectId);
                System.out.println("uid:"+uid+"objectId:"+objectId);
                startActivity(button3);
                break;
        }
    }
}


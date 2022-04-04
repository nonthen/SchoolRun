package com.example.schoolrun.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

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
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);

        //这里获取了任务主页
        BmobQuery<MyTask> bmobQuery=new BmobQuery<MyTask>();
        bmobQuery.findObjects(new FindListener<MyTask>() {
            @Override
            public void done(List<MyTask> list, BmobException e) {

                if(e==null){
                    SimpleAdapter simpleAdapter;
                    Log.d("path","查询成功");
                    Map<String, String> mHashMap;
                    String tempTprice,tempTid,tempTphone,tempTkind;
                    Toast.makeText(MainActivity.this,"成功，共"+list.size()+"条数据",Toast.LENGTH_SHORT).show();
                    List<Map<String,String>> mapList=new ArrayList<>();
                    for (MyTask myTask:list){
                        tempTprice=String.valueOf(myTask.getTprice());
                        tempTid=String.valueOf(myTask.getTid());
                        tempTphone=String.valueOf(myTask.getTphone());
                        tempTkind=String.valueOf(myTask.getTkind());
                        mHashMap=new HashMap<>();
                        mHashMap.put("tname",myTask.getTname());
                        mHashMap.put("targetaddress",myTask.getTargetaddress());
                        mHashMap.put("tprice",tempTprice);
                        mHashMap.put("tid",tempTid);
                        mHashMap.put("tdetail",myTask.getTdetail());
                        mHashMap.put("myaddress",myTask.getMyaddress());
                        mHashMap.put("tphone",tempTphone);
                        mHashMap.put("tkind",tempTkind);
                        mapList.add(mHashMap);
                        System.out.println("标题："+myTask.getTname()+"目标地址："+myTask.getTargetaddress()+"价格："+myTask.getTprice());
                    }
                    ListView listView=findViewById(R.id.listView);
                    simpleAdapter=new SimpleAdapter(MainActivity.this,mapList,R.layout.view_task_item_info,new String[]{"tname","targetaddress","tprice"},new int[]{R.id.item_tname,R.id.item_targetaddress,R.id.item_tprice});
                    listView.setAdapter(simpleAdapter);
                    simpleAdapter.notifyDataSetChanged();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            //Bmob获取listview中某一行数据
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, DetailedInfoActivity.class);
                            intent.putExtra("tid", mapList.get(position).get("tid").toString()); // 获取该列表项的key为id的键值，即商品的id，将其储存在Bundle传递给打开的页面
                            System.out.println(position);
                            startActivity(intent);

                        }
                    });
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

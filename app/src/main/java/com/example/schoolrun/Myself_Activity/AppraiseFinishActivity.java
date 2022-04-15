package com.example.schoolrun.Myself_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Activity.DetailedInfoActivity;
import com.example.schoolrun.Activity.MainActivity;
import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.LoginActivity;
import com.example.schoolrun.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

//查看已经评价的任务列表
public class AppraiseFinishActivity extends AppCompatActivity {

    private ImageButton fanhuialllistbutton;//返回未评价任务列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.appraisefinishlist);//绑定已经评价任务列表布局
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能

        fanhuialllistbutton=findViewById(R.id.fanhuialllistbutton);

        //这里获取已完成评价的任务列表
        BmobQuery<MyTask> bmobQuery=new BmobQuery<MyTask>();
        String bql = "select * from MyTask where tappfinsh=1 and  uid = ?";
        bmobQuery.setSQL(bql);//必须先获取uid，由uaccount获取uid
        bmobQuery.setPreparedParams(new Object[]{LoginActivity.uid});
//        System.out.println("评价的uid是"+LoginActivity.uid);
        bmobQuery.doSQLQuery(new SQLQueryListener<MyTask>() {
            @Override
            public void done(BmobQueryResult<MyTask> bmobQueryResult, BmobException e) {
                if(e==null){
                    //获取数据，存储到Map中
                    SimpleAdapter simpleAdapter;
                    Map<String, String> mHashMap;
                    String tempTprice,tempTid,tempTphone,tempTkind;
                    Toast.makeText(AppraiseFinishActivity.this,"评价成功的任务查询成功",Toast.LENGTH_SHORT).show();
                    List<Map<String,String>> mapList=new ArrayList<>();

                    List<MyTask> list = (List<MyTask>) bmobQueryResult.getResults();//查询结果
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
//                        System.out.println("标题："+myTask.getTname()+"目标地址："+myTask.getTargetaddress()+"价格："+myTask.getTprice());
                    }

                    //获取数据显示在列表中
                    ListView listView=findViewById(R.id.listView);
                    simpleAdapter=new SimpleAdapter(AppraiseFinishActivity.this,mapList,R.layout.view_task_item_info,
                            new String[]{"tname","targetaddress","tprice"},
                            new int[]{R.id.item_tname,R.id.item_targetaddress,R.id.item_tprice});
                    listView.setAdapter(simpleAdapter);
                    simpleAdapter.notifyDataSetChanged();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            //Bmob获取listview中某一行数据
                            Intent intent = new Intent();
                            //跳转到已完成评价的任务详细信息
                            intent.setClass(AppraiseFinishActivity.this, AppraiseFinshDetailsActivity.class);
                            intent.putExtra("tid", mapList.get(position).get("tid")); // 获取该列表项的key为id的键值，即商品的id，将其储存在Bundle传递给打开的页面
                            System.out.println(position);
                            startActivity(intent);

                        }
                    });
                }
                else {
                    Log.d("path","已完成评价任务查询不成功");
                }
            }

        });

        //返回未完成评价任务列表
        fanhuialllistbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AppraiseFinishActivity.this,AppraiseActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }
}

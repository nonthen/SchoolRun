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
import com.example.schoolrun.Activity.PayTypesDialog;
import com.example.schoolrun.Activity.ReleaseTask;
import com.example.schoolrun.Activity.TestMeAc;
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

//评价任务功能
public class AppraiseActivity extends AppCompatActivity {

    private  ImageButton fanhuiButton;//返回上一层按钮
    private StarScoreDialog starScoreDialog;//评分弹窗

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.appraise_task);//绑定评价服务布局
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能

        fanhuiButton=findViewById(R.id.fanhuibutton);//返回按钮
        starScoreDialog=new StarScoreDialog(AppraiseActivity.this, R.style.pay_type_dialog);

        //这里获取已完成任务的列表
        BmobQuery<MyTask> bmobQuery=new BmobQuery<MyTask>();
        bmobQuery.findObjects(new FindListener<MyTask>() {
            @Override
            public void done(List<MyTask> list, BmobException e) {

                if(e==null){
                    //获取数据，存储到Map中
                    SimpleAdapter simpleAdapter;
                    Log.d("path","已经完成的任务查询成功");
                    Map<String, String> mHashMap;
                    String tempTprice,tempTid,tempTphone,tempTkind;
                    Toast.makeText(AppraiseActivity.this,"成功，共"+list.size()+"条数据",Toast.LENGTH_SHORT).show();
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

                    //获取数据显示在列表中
                    ListView listView=findViewById(R.id.listView);
                    simpleAdapter=new SimpleAdapter(AppraiseActivity.this,mapList,R.layout.view_task_item_info,
                            new String[]{"tname","targetaddress","tprice"},
                            new int[]{R.id.item_tname,R.id.item_targetaddress,R.id.item_tprice});
                    listView.setAdapter(simpleAdapter);
                    simpleAdapter.notifyDataSetChanged();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            //Bmob获取listview中某一行数据
                            Intent intent = new Intent();
                            // 获取该列表项的key为id的键值，即商品的id
                            intent.putExtra("tid", mapList.get(position).get("tid").toString());

                            System.out.println("弹出任务评分");
                            starScoreDialog.show();

                        }
                    });
                }
                else {
                    Log.d("path","已完成任务查询不成功");
                }
            }
        });








        //返回到个人信息界面
        fanhuiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fanhui = new Intent(AppraiseActivity.this, TestMeAc.class);
                startActivity(fanhui);
                finish();//释放资源
            }
        });

    }


}

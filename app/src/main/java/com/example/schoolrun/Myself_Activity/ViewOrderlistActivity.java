package com.example.schoolrun.Myself_Activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Activity.DetailedInfoActivity;
import com.example.schoolrun.Activity.MainActivity;
import com.example.schoolrun.Activity.ReleaseTask;
import com.example.schoolrun.Activity.TestMeAc;
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

//查看订单
public class ViewOrderlistActivity extends AppCompatActivity implements View.OnClickListener{

    private RadioGroup mRadioGroup;
    private RadioButton tab1,tab2,tab3;  //3个单选按钮
    private ImageButton returnmebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_orderlist);//绑定订单布局
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能

        //初始化控件
        returnmebutton=findViewById(R.id.returnmebutton);
        mRadioGroup=findViewById(R.id.rg_tab);
        tab1=findViewById(R.id.yijiedanbutton);//已接单按钮
        tab2=findViewById(R.id.runnigbutton);//进行中按钮
        tab3=findViewById(R.id.jiefinishbutton);//已完成按钮
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        //点击当前界面，按钮的背景变色
        tab1.setBackgroundColor(Color.parseColor("#00abf4"));

        //这里获取已接单的任务列表，此时是发单员和接单员共有视角
        BmobQuery<MyTask> bmobQuery=new BmobQuery<MyTask>();
        String bql = "select * from MyTask where torder=1 and tfinish =0 and  uid = ?";
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
                    Toast.makeText(ViewOrderlistActivity.this,"已接单查询成功",Toast.LENGTH_SHORT).show();
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
                    simpleAdapter=new SimpleAdapter(ViewOrderlistActivity.this,mapList,R.layout.view_order_item_info,
                            new String[]{"tname","myadress","targetaddress","tphone"},
                            new int[]{R.id.item_tname,R.id.item_myaddress,R.id.item_targetaddress,R.id.item_tphone});
                    listView.setAdapter(simpleAdapter);
                    simpleAdapter.notifyDataSetChanged();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            //Bmob获取listview中某一行数据
                            System.out.println("跳转到订单详情");
                            Intent intent = new Intent();
                            intent.setClass(ViewOrderlistActivity.this, ViewOrderDetailsActivity.class);
                            // 获取该列表项的key为id的键值，即商品的id，将其储存在Bundle传递给打开的页面
                            intent.putExtra("tid", mapList.get(position).get("tid"));
                            startActivity(intent);
                            finish();
                        }
                    });
                }
                else {
                    Log.d("path","已接单的任务，查询不成功");
                }
            }

        });


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.yijiedanbutton:
                tab1.setBackgroundColor(Color.parseColor("#00abf4"));
                tab2.setBackgroundColor(Color.parseColor("#ffffff"));
                tab3.setBackgroundColor(Color.parseColor("#ffffff"));
                Intent button1 = new Intent(this, ViewOrderlistActivity.class);
                startActivity(button1);
                break;
            case R.id.runnigbutton:
                tab1.setBackgroundColor(Color.parseColor("#ffffff"));
                tab2.setBackgroundColor(Color.parseColor("#00abf4"));
                tab3.setBackgroundColor(Color.parseColor("#ffffff"));
                Intent button2 = new Intent(this, ViewOrderingActivity.class);
                startActivity(button2);
                break;
            case R.id.jiefinishbutton:
                tab1.setBackgroundColor(Color.parseColor("#ffffff"));
                tab2.setBackgroundColor(Color.parseColor("#ffffff"));
                tab3.setBackgroundColor(Color.parseColor("#ffffff"));
                Intent button3 = new Intent(this, ViewOrderFinishActivity.class);
                startActivity(button3);
                break;

        }

        //返回我的界面
        returnmebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ViewOrderlistActivity.this, TestMeAc.class);
                finish();
            }
        });

    }
}

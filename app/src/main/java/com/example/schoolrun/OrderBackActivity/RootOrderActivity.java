package com.example.schoolrun.OrderBackActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Activity.RootMainActivity;
import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.R;
import com.example.schoolrun.userbackground.user_activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

//管理员异常订单管理，对订单进行审核，管理员审核当前订单是否能够取消
public class RootOrderActivity extends AppCompatActivity implements View.OnClickListener{

    private RadioButton bt1,bt2,bt3;  //3个单选按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_order);
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能

        bt1 = findViewById(R.id.bt_task);
        bt2 = findViewById(R.id.bt_order);
        bt3 = findViewById(R.id.bt_user);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);

        BmobQuery<MyTask> bmobQuery = new BmobQuery<MyTask>();
        String bql = "select * from MyTask where torder=2";//异常订单
        bmobQuery.setSQL(bql);//必须先获取uid，由uaccount获取uid
        bmobQuery.doSQLQuery(new SQLQueryListener<MyTask>() {
            @Override
            public void done(BmobQueryResult<MyTask> bmobQueryResult, BmobException e) {
                if (e==null){
                    //获取数据，存储到Map中
                    SimpleAdapter simpleAdapter;
                    Map<String, String> mHashMap;
                    String tempTid,tempId;
                    Toast.makeText(RootOrderActivity.this,"异常订单，查询成功",Toast.LENGTH_SHORT).show();
                    List<Map<String,String>> mapList=new ArrayList<>();

                    List<MyTask> list = (List<MyTask>) bmobQueryResult.getResults();//查询结果
                    for (MyTask myTask:list){
                        tempId=String.valueOf(myTask.getTid());
                        tempTid=String.valueOf(myTask.getTid());
                        mHashMap=new HashMap<>();
                        mHashMap.put("tname",myTask.getTname());
                        mHashMap.put("tid",tempTid);//任务id
                        mHashMap.put("id",tempId);//接单人编号
                        mHashMap.put("tdetail",myTask.getTdetail());
                        mHashMap.put("tordercanceldetails",myTask.getTordercanceldetails());
                        mapList.add(mHashMap);

                    }

                    //获取数据显示在列表中
                    ListView listView=findViewById(R.id.listView);
                    simpleAdapter=new SimpleAdapter(RootOrderActivity.this,mapList,R.layout.root_order_item_info,
                            new String[]{"tname","id","tordercanceldetails"},
                            new int[]{R.id.jiefinish_tname,R.id.item_id,R.id.item_canceldetails});
                    listView.setAdapter(simpleAdapter);
                    simpleAdapter.notifyDataSetChanged();

                    //短按某个订单，就会跳转到订单详细界面
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            //Bmob获取listview中某一行数据
                            System.out.println("跳转到订单详情");
                            Intent intent = new Intent();
                            intent.setClass(RootOrderActivity.this, RootOrderDetailsActivity.class);
                            // 获取该列表项的key为id的键值，即商品的id，将其储存在Bundle传递给打开的页面
                            intent.putExtra("tid", mapList.get(position).get("tid"));
                            startActivity(intent);
                            finish();
                        }
                    });


                }
                else {
                    Toast.makeText(RootOrderActivity.this,"订单异常，读取失败",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_task:
                Intent button1 = new Intent(this, RootMainActivity.class);//任务
                startActivity(button1);
                break;
            case R.id.bt_order:
                Intent button2 = new Intent(this, RootOrderActivity.class);//订单
                startActivity(button2);
                break;
            case R.id.bt_user:
                Intent button3 = new Intent(this, user_activity.class);//用户
                startActivity(button3);
                break;

        }
    }
}

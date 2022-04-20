package com.example.schoolrun.Myself_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.LoginActivity;
import com.example.schoolrun.OrderBackActivity.RootOrderDetailsActivity;
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
import cn.bmob.v3.listener.UpdateListener;

//异常订单后续通知
public class ViewOrderAbnormalActivity extends AppCompatActivity {

    private ImageButton relistbutton;
    private Button readbutton;
    private List<MyTask> readList;
    private String Objectid;//bmob中默认的id值

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_order_abnormal);//绑定订单通知布局
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能

        relistbutton=findViewById(R.id.relistbutton);
        readbutton=findViewById(R.id.readbutton);//标为已读

        readList=new ArrayList<>();

        //筛选异常订单通过审核后的订单
        BmobQuery<MyTask> bmobQuery=new BmobQuery<MyTask>();
        String bql = "select * from MyTask where tfinish=0 and tcheckorder!=0";
        bmobQuery.setSQL(bql);//必须先获取uid，由uaccount获取uid
        bmobQuery.doSQLQuery(new SQLQueryListener<MyTask>() {
            @Override
            public void done(BmobQueryResult<MyTask> bmobQueryResult, BmobException e) {
                List<MyTask> list = (List<MyTask>) bmobQueryResult.getResults();//查询结果
                if (e==null){

                    int i=0;
                    SimpleAdapter simpleAdapter;
                    Map<String, String> mHashMap;
                    List<Map<String,String>> mapList=new ArrayList<>();
                    for (MyTask myTask:list){
                        mHashMap=new HashMap<>();

                        if (list.get(i).getId()==LoginActivity.uid&&list.get(i).getTorder()==1&&list.get(i).getTordercheck()==1){
                            mHashMap.put("tid",String.valueOf(myTask.getTid()));
                            mHashMap.put("tname",myTask.getTname());
                            mHashMap.put("torderabnormaledetails","拒绝取消订单，请完成当前订单");
                            mapList.add(mHashMap);
                            readList.add(myTask);
                        }
                        else if (list.get(i).getId()==LoginActivity.uid&&list.get(i).getTorder()==0&&list.get(i).getTordercheck()==2){
                            mHashMap.put("tid",String.valueOf(myTask.getTid()));
                            mHashMap.put("tname",myTask.getTname());
                            mHashMap.put("torderabnormaledetails","申请取消订单成功");
                            System.out.println("list.get(i).getId()="+list.get(i).getId()+myTask.getTname());
                            mapList.add(mHashMap);
                            readList.add(myTask);
                        }
                        else if (list.get(i).getUid()==LoginActivity.uid&&list.get(i).getTorder()==0&&list.get(i).getTordercheck()==2){
                            mHashMap.put("tid",String.valueOf(myTask.getTid()));
                            mHashMap.put("tname",myTask.getTname());
                            mHashMap.put("torderabnormaledetails","订单已被接单员取消，已重新安排");
                            System.out.println("list.get(i).getUid()="+list.get(i).getUid()+myTask.getTname());
                            mapList.add(mHashMap);
                            readList.add(myTask);
                        }
                        i++;
                    }


                    //获取数据显示在列表中
                    ListView listView=findViewById(R.id.listView);
                    simpleAdapter=new SimpleAdapter(ViewOrderAbnormalActivity.this,mapList,R.layout.view_order_abnormalitem_info,
                            new String[]{"tname","torderabnormaledetails"},
                            new int[]{R.id.item_tname,R.id.item_abnormaltext});
                    listView.setAdapter(simpleAdapter);
                    simpleAdapter.notifyDataSetChanged();

                    //短按某个订单，就会跳转到订单详细界面
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            //Bmob获取listview中某一行数据
                            System.out.println("跳转到异常订单详情");
                            Intent intent = new Intent();
                            intent.setClass(ViewOrderAbnormalActivity.this, ViewOrderAbnormalDetails.class);
                            // 获取该列表项的key为id的键值，即商品的id，将其储存在Bundle传递给打开的页面
                            intent.putExtra("tid", mapList.get(position).get("tid"));
                            startActivity(intent);
                            finish();
                        }
                    });


                }
            }
        });

        //返回订单列表
        relistbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ViewOrderAbnormalActivity.this,ViewOrderlistActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //一键已读
        readbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bmobQuery.doSQLQuery(new SQLQueryListener<MyTask>() {
                    @Override
                    public void done(BmobQueryResult<MyTask> bmobQueryResult, BmobException e) {
                        int temp=0;
                        if (e==null){
                            for (MyTask myTask:readList){

                                Objectid=readList.get(temp).getObjectId();//获取bmob中默认的ObjectId值
                                myTask.setUid(readList.get(temp).getUid());
                                myTask.setTid(readList.get(temp).getTid());
                                myTask.setId(readList.get(temp).getId());
                                myTask.setTname(readList.get(temp).getTname());
                                myTask.setTkind(readList.get(temp).getTkind());
                                myTask.setTphone(readList.get(temp).getTphone());
                                myTask.setTprice(readList.get(temp).getTprice());
                                myTask.setTdetail(readList.get(temp).getTdetail());
                                myTask.setMyaddress(readList.get(temp).getMyaddress());
                                myTask.setTargetaddress(readList.get(temp).getTargetaddress());
                                myTask.setTcheck(1);//任务审核
                                myTask.setTorder(readList.get(temp).getTorder());
                                myTask.setTordercheck(0);//置0

                                temp++;

                                myTask.update(Objectid,new UpdateListener(){
                                    @Override
                                    public void done(BmobException e) {

                                        if (e == null) {
                                            System.out.println("已读一条信息");
                                        }

                                    }
                                });

                                //刷新界面
                                Intent intent = new Intent(ViewOrderAbnormalActivity.this,ViewOrderAbnormalActivity.class);
                                startActivity(intent);
                                finish();

                            }
                            System.out.println("最后已读消息temp="+temp);

                        }

                    }
                });

            }
        });


    }

}

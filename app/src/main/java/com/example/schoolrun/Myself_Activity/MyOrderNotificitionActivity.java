package com.example.schoolrun.Myself_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Entity.MyOrderRead;
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
import cn.bmob.v3.listener.UpdateListener;

//这是从个人界面的通知跳转的异常订单消息通知
public class MyOrderNotificitionActivity extends AppCompatActivity {

    private ImageButton relistbutton;
    private ImageButton readbutton;
//    private List<MyTask> readList;
    private String Objectid;//bmob中默认的id值
    private MyTask myTask;
    private MyOrderRead myOrderRead;
    private Map<String, String> mHashMap;
    private List<Map<String,String>> mapList;
    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_order_abnormal);//绑定订单通知布局
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能

        relistbutton=findViewById(R.id.relistbutton);
        readbutton=findViewById(R.id.readbutton);//标为已读

//        readList=new ArrayList<>();

        myTask=new MyTask();
        myOrderRead=new MyOrderRead();
        mapList=new ArrayList<>();

        //筛选异常订单通过审核后的订单
        BmobQuery<MyTask> bmobQuery=new BmobQuery<MyTask>();
        String bql = "select * from MyTask where tfinish=0 and tordercheck!=0";//这里进行了修改
        bmobQuery.setSQL(bql);//必须先获取uid，由uaccount获取uid
        bmobQuery.doSQLQuery(new SQLQueryListener<MyTask>() {
            @Override
            public void done(BmobQueryResult<MyTask> bmobQueryResult, BmobException e) {
                List<MyTask> list =  bmobQueryResult.getResults();//查询结果
                if (e==null){

                    int i=0;//循环变量

                    for (MyTask myTask:list){
                        mHashMap=new HashMap<>();

                        //通知发单者
                        if (list.get(i).getUid()== LoginActivity.uid&&list.get(i).getTorder()==0&&list.get(i).getTordercheck()==2){
                            mHashMap.put("tid",String.valueOf(myTask.getTid()));
                            mHashMap.put("id",String.valueOf(myTask.getId()));//接单者编号
                            mHashMap.put("tname",myTask.getTname());
                            mHashMap.put("torderabnormaledetails","订单已被接单员取消，已重新安排");
                            mapList.add(mHashMap);
//                            readList.add(myTask);

                        }
                        i++;
                    }

                    //当前用户是接单者
                    BmobQuery<MyOrderRead> bmobQuery1=new BmobQuery<MyOrderRead>();
                    String bql1 = "select * from MyOrderRead where id = ?";
                    bmobQuery1.setSQL(bql1);
                    bmobQuery1.setPreparedParams(new Object[]{LoginActivity.uid});//接单者
                    bmobQuery1.doSQLQuery(new SQLQueryListener<MyOrderRead>(){

                        @Override
                        public void done(BmobQueryResult<MyOrderRead> bmobQueryResult, BmobException e) {
                            List<MyOrderRead> myorderreadlist =bmobQueryResult.getResults();
                            if (e==null){
                                int i=0;
                                for (MyOrderRead myOrderRead:myorderreadlist){
                                    mHashMap=new HashMap<>();
                                    if (myorderreadlist.get(i).getTorderread()==1){//获取接单者未读的消息
                                        System.out.println("接单者是否运行");
                                        mHashMap.put("tid",String.valueOf(myOrderRead.getTid()));
                                        mHashMap.put("id",String.valueOf(myOrderRead.getId()));//接单者编号
                                        mHashMap.put("tname",myOrderRead.getTname());
                                        mHashMap.put("torderabnormaledetails",myOrderRead.getTorderreaddetails());
                                        mapList.add(mHashMap);
//                                        jiereadList.add(myOrderRead);
                                    }
                                    i++;
                                }


                                //获取数据显示在列表中
                                ListView listView=findViewById(R.id.listView);
                                simpleAdapter=new SimpleAdapter(MyOrderNotificitionActivity.this,mapList,R.layout.view_from_myorder_abnormalitem_info,
                                        new String[]{"tname","torderabnormaledetails"},
                                        new int[]{R.id.jiefinish_tname,R.id.item_abnormaltext});
                                listView.setAdapter(simpleAdapter);
                                simpleAdapter.notifyDataSetChanged();

                                //短按某个订单，就会跳转到订单详细界面
//                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                    @Override
//                                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                                        //Bmob获取listview中某一行数据
//                                        System.out.println("跳转到异常订单详情");
//                                        Intent intent = new Intent();
//                                        intent.setClass(MyOrderNotificitionActivity.this, ViewOrderAbnormalDetails.class);
//                                        // 获取该列表项的key为id的键值，即商品的id，将其储存在Bundle传递给打开的页面
//                                        intent.putExtra("tid", mapList.get(position).get("tid"));
//                                        intent.putExtra("tdingdanabnormal",mapList.get(position).get("torderabnormaledetails"));
//                                        startActivity(intent);
//                                        finish();
//                                    }
//                                });


                            }
                        }
                    });

                    //获取数据显示在列表中,这个界面是长按删除
                    ListView listView=findViewById(R.id.listView);
                    simpleAdapter=new SimpleAdapter(MyOrderNotificitionActivity.this,mapList,R.layout.view_from_myorder_abnormalitem_info,
                            new String[]{"tname","torderabnormaledetails"},
                            new int[]{R.id.jiefinish_tname,R.id.item_abnormaltext});
                    listView.setAdapter(simpleAdapter);
                    simpleAdapter.notifyDataSetChanged();


//                    //短按某个订单，就会跳转到订单详细界面
//                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                            //Bmob获取listview中某一行数据
//                            System.out.println("跳转到异常订单详情");
//                            Intent intent = new Intent();
//                            intent.setClass(MyOrderNotificitionActivity.this, ViewOrderAbnormalDetails.class);
//                            // 获取该列表项的key为id的键值，即商品的id，将其储存在Bundle传递给打开的页面
//                            intent.putExtra("tid", mapList.get(position).get("tid"));
//                            intent.putExtra("tdingdanabnormal",mapList.get(position).get("torderabnormaledetails"));
//                            startActivity(intent);
//                            finish();
//                        }
//                    });

                    //长按某个订单，表示消息已读(即删除通知)
                    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                            //当前用户是当前订单的接单者，则修改MyOrderRead表
                            if (LoginActivity.uid==Integer.parseInt(mapList.get(i).get("id"))){
                                BmobQuery<MyOrderRead> bmobQuery=new BmobQuery<>();
                                String bql="select * from MyOrderRead where tid = ?";
                                bmobQuery.setSQL(bql);
                                bmobQuery.setPreparedParams(new Object[]{Integer.parseInt(mapList.get(i).get("tid"))});
                                bmobQuery.doSQLQuery(new SQLQueryListener<MyOrderRead>(){

                                    @Override
                                    public void done(BmobQueryResult<MyOrderRead> bmobQueryResult, BmobException e) {
                                        List<MyOrderRead> list = (List<MyOrderRead>) bmobQueryResult.getResults();//查询结果
                                        if (e==null){
                                            Objectid=list.get(0).getObjectId();//获取bmob中默认的ObjectId值
                                            myOrderRead.setTid(list.get(0).getTid());
                                            myOrderRead.setId(list.get(0).getId());
                                            myOrderRead.setTname(list.get(0).getTname());
                                            myOrderRead.setTorderreaddetails(list.get(0).getTorderreaddetails());
                                            myOrderRead.setTorderread(0);//表示消息已读
                                            //更新MyOrderRead表
                                            myOrderRead.update(Objectid, new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    if (e==null){
                                                        Toast.makeText(MyOrderNotificitionActivity.this,"已删除一条消息",Toast.LENGTH_SHORT).show();
                                                        System.out.println("已删除一条消息");
                                                        //刷新界面
                                                        Intent intent = new Intent(MyOrderNotificitionActivity.this,MyOrderNotificitionActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }
                                            });

                                        }

                                    }
                                });

                            }
                            else {//那么此时用户是该订单的发单者，修改MyTask表
                                BmobQuery<MyTask> bmobQuery=new BmobQuery<MyTask>();
                                String bql = "select * from MyTask where tid = ?";
                                bmobQuery.setSQL(bql);
                                bmobQuery.setPreparedParams(new Object[]{Integer.parseInt(mapList.get(i).get("tid"))});
                                bmobQuery.doSQLQuery(new SQLQueryListener<MyTask>(){

                                    @Override
                                    public void done(BmobQueryResult<MyTask> bmobQueryResult, BmobException e) {
                                        List<MyTask> list = (List<MyTask>) bmobQueryResult.getResults();//查询结果
                                        if (e==null){
                                            Objectid=list.get(0).getObjectId();//获取bmob中默认的ObjectId值
                                            myTask.setUid(list.get(0).getUid());
                                            myTask.setTid(list.get(0).getTid());
                                            myTask.setId(list.get(0).getId());
                                            myTask.setTname(list.get(0).getTname());
                                            myTask.setTkind(list.get(0).getTkind());
                                            myTask.setTphone(list.get(0).getTphone());
                                            myTask.setTprice(list.get(0).getTprice());
                                            myTask.setTdetail(list.get(0).getTdetail());
                                            myTask.setMyaddress(list.get(0).getMyaddress());
                                            myTask.setTargetaddress(list.get(0).getTargetaddress());
                                            myTask.setTcheck(1);//任务审核
                                            myTask.setTorder(list.get(0).getTorder());
                                            myTask.setTordercheck(0);//置0


                                            myTask.update(Objectid,new UpdateListener(){
                                                @Override
                                                public void done(BmobException e) {

                                                    if (e == null) {
                                                        Toast.makeText(MyOrderNotificitionActivity.this,"已删除一条消息",Toast.LENGTH_SHORT).show();
                                                        System.out.println("已删除一条消息");
                                                        //刷新界面
                                                        Intent intent = new Intent(MyOrderNotificitionActivity.this,MyOrderNotificitionActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }

                                                }
                                            });
                                        }
                                    }
                                });

                            }

                            return true;
                        }
                    });

                }
            }
        });

        //返回通知消息列表
        relistbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyOrderNotificitionActivity.this,NotifyActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}

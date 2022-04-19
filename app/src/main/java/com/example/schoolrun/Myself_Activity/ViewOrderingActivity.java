package com.example.schoolrun.Myself_Activity;

import android.content.DialogInterface;
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

import com.example.schoolrun.Activity.TestMeAc;
import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.Entity.MyUser;
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

//任务进行中
public class ViewOrderingActivity extends AppCompatActivity implements View.OnClickListener{

    private RadioGroup mRadioGroup;
    private RadioButton tab1,tab2;  //2个单选按钮
    private ImageButton returnmebutton2;
    private OrderDialog orderDialog;//订单弹窗
    private ImageButton notificationbutton;//通知

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_orderlist);//绑定订单布局
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能

        //初始化控件
        orderDialog=new OrderDialog(ViewOrderingActivity.this, R.style.pay_type_dialog);
        mRadioGroup=findViewById(R.id.rg_tab);
        returnmebutton2=findViewById(R.id.returnmebutton);
        notificationbutton=findViewById(R.id.notificationbutton);
        tab1=findViewById(R.id.yijiedanbutton);//已接单按钮
        tab2=findViewById(R.id.runnigbutton);//进行中按钮
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        //点击当前界面，按钮的背景变色
        tab2.setBackgroundColor(Color.parseColor("#00abf4"));

        //这里判断当前用户是否具有接单员权限
        BmobQuery<MyUser> bQmyuser=new BmobQuery<MyUser>();
        String bqluser = "select * from MyUser where uid = ?";
        bQmyuser.setSQL(bqluser);//必须先获取uid，由uaccount获取uid
        bQmyuser.setPreparedParams(new Object[]{LoginActivity.uid});
        bQmyuser.doSQLQuery(new SQLQueryListener<MyUser>() {//获取当前用户的所有信息
            @Override
            public void done(BmobQueryResult<MyUser> bmobQueryResult, BmobException e) {

                if (e==null){
                    List<MyUser> list = (List<MyUser>) bmobQueryResult.getResults();//查询结果
                    if (list.get(0).getUcheck()==1){//具有接单员资格，可以查看自己接的订单，并进行派送
                        System.out.println("具有接单员资格，可以查看正在进行的订单，并查看订单");
                        Havezige();
                    }
                    else {
                        Toast.makeText(ViewOrderingActivity.this,"您不是接单员，请申请",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Log.d("path","用户信息获取失败！");
                }

            }
        });

        //返回我的界面
        returnmebutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewOrderingActivity.this, TestMeAc.class);
                startActivity(intent);
                finish();
            }
        });

        //查看异常订单后续
        notificationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BmobQuery<MyTask> bmobQuery=new BmobQuery<MyTask>();
                //接单员提交取消订单申请后，管理员拒绝当前取消申请，通知接单者继续完成该订单
                String bql = "select * from MyTask where torder=1 and tcheckorder =1  and  id = ?";
                bmobQuery.setSQL(bql);//必须先获取uid，由uaccount获取uid
                bmobQuery.setPreparedParams(new Object[]{LoginActivity.uid});
                bmobQuery.doSQLQuery(new SQLQueryListener<MyTask>() {
                    @Override
                    public void done(BmobQueryResult<MyTask> bmobQueryResult, BmobException e) {
                        List<MyTask> list = (List<MyTask>) bmobQueryResult.getResults();//查询结果
                        if (e==null){
                            if (list.size()>0){
                                System.out.println("拒绝申请，请继续完成，订单的数量"+list.size());
                                notificationbutton.setImageResource(R.drawable.ic_baseline_notifications_active_24);
                            }
                        }
                    }
                });


                BmobQuery<MyTask> bmobQuery1=new BmobQuery<MyTask>();
                //接单员提交取消订单申请后，管理员同意取消订单申请，通知接单者成功取消订单
                String bql1 = "select * from MyTask where torder=0 and tcheckorder =2  and  id = ?";
                bmobQuery1.setSQL(bql1);//必须先获取uid，由uaccount获取uid
                bmobQuery1.setPreparedParams(new Object[]{LoginActivity.uid});
                bmobQuery1.doSQLQuery(new SQLQueryListener<MyTask>() {
                    @Override
                    public void done(BmobQueryResult<MyTask> bmobQueryResult, BmobException e) {
                        List<MyTask> list = (List<MyTask>) bmobQueryResult.getResults();//查询结果
                        if (e==null){
                            if (list.size()>0){
                                System.out.println("接单者：同意申请，订单的数量"+list.size());
                                notificationbutton.setImageResource(R.drawable.ic_baseline_notifications_active_24);
                            }
                        }
                    }
                });

                BmobQuery<MyTask> bmobQuery2=new BmobQuery<MyTask>();
                //接单员提交取消订单申请后，管理员同意取消订单申请，通知发单者当前订单被接单者取消
                String bql2 = "select * from MyTask where torder=0 and tcheckorder =2  and  uid = ?";
                bmobQuery2.setSQL(bql2);//必须先获取uid，由uaccount获取uid
                bmobQuery2.setPreparedParams(new Object[]{LoginActivity.uid});
                bmobQuery2.doSQLQuery(new SQLQueryListener<MyTask>() {
                    @Override
                    public void done(BmobQueryResult<MyTask> bmobQueryResult, BmobException e) {
                        List<MyTask> list = (List<MyTask>) bmobQueryResult.getResults();//查询结果
                        if (e==null){
                            if (list.size()>0){
                                System.out.println("发单者：当前订单已被接单者取消，订单的数量"+list.size());
                                notificationbutton.setImageResource(R.drawable.ic_baseline_notifications_active_24);
                            }
                        }
                    }
                });

                //跳转到异常订单列表
                Intent intent = new Intent(ViewOrderingActivity.this,ViewOrderAbnormalActivity.class);
                startActivity(intent);
                finish();

            }
        });



    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.yijiedanbutton:
                tab1.setBackgroundColor(Color.parseColor("#00abf4"));
                tab2.setBackgroundColor(Color.parseColor("#ffffff"));
                Intent button1 = new Intent(this, ViewOrderlistActivity.class);
                startActivity(button1);
                break;
            case R.id.runnigbutton:
                tab1.setBackgroundColor(Color.parseColor("#ffffff"));
                tab2.setBackgroundColor(Color.parseColor("#00abf4"));
                Intent button2 = new Intent(this, ViewOrderingActivity.class);
                startActivity(button2);
                break;

        }

    }

    //具有接单员资格后做的操作
    void Havezige(){
        //这里获取正在进行的任务列表，此时是接单员才有的视角
        BmobQuery<MyTask> bmobQuery=new BmobQuery<MyTask>();
        String bql = "select * from MyTask where torder=1 and tfinish =0  and  id = ?";
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
                    Toast.makeText(ViewOrderingActivity.this,"进行中订单，查询成功",Toast.LENGTH_SHORT).show();
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
                    simpleAdapter=new SimpleAdapter(ViewOrderingActivity.this,mapList,R.layout.view_order_item_info,
                            new String[]{"tname","myadress","targetaddress","tphone"},
                            new int[]{R.id.item_tname,R.id.item_myaddress,R.id.item_targetaddress,R.id.item_tphone});
                    listView.setAdapter(simpleAdapter);
                    simpleAdapter.notifyDataSetChanged();

                    //短按某个订单，就会跳转到订单详细界面
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            //Bmob获取listview中某一行数据
                            System.out.println("跳转到订单详情");
                            Intent intent = new Intent();
                            intent.setClass(ViewOrderingActivity.this, ViewOrderDetailsActivity.class);
                            // 获取该列表项的key为id的键值，即商品的id，将其储存在Bundle传递给打开的页面
                            intent.putExtra("tid", mapList.get(position).get("tid"));
                            startActivity(intent);
                            finish();
                        }
                    });

                    //长按订单列表，显示弹窗，选择是否已经完成当前订单，还是有特殊原因拒绝完成当前进行中的订单
                    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                            //Bmob获取listview中某一行数据
                            System.out.println("弹窗显示订单选择详情");
                            //传入当前准备评分的任务的tid
                            orderDialog.SetTaskid(mapList.get(i).get("tid"));
                            orderDialog.show();
                            return true;
                        }
                    });

                    //在取消dialog弹窗时添加一个事件监听
                    orderDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        //刷新当前界面
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            Intent intent = new Intent(ViewOrderingActivity.this,ViewOrderingActivity.class);
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

}

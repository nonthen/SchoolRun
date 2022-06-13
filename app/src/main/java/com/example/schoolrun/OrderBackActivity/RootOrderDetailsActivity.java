package com.example.schoolrun.OrderBackActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Entity.MyOrderRead;
import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.R;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;

//管理员详细订单信息

public class RootOrderDetailsActivity extends AppCompatActivity{

    private ImageButton orderlist;//返回管理员订单列表按钮
    private TextView ordertitle;
    private TextView renwudetails;
    private TextView ordercanceldetails;
    private Button Refusebutton;
    private Button Allowbutton;

    private Intent intent;
    private String stid;
    private String Objectid;//bmob中默认的id值
    private MyTask myTask;
    private MyOrderRead myOrderRead;
    private String Objectreadid;

    private String stitle;
    private String sdetails;
    private String sordercaceldetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_order_details);
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能

        intent=getIntent();//获取上一个界面的intent
        stid=intent.getStringExtra("tid");//获取上个界面传过来的任务tid
        myTask=new MyTask();
        myOrderRead=new MyOrderRead();

        orderlist=findViewById(R.id.orderlist);
        ordertitle=findViewById(R.id.ordertitle);
        renwudetails=findViewById(R.id.renwudetails);
        ordercanceldetails=findViewById(R.id.ordercanceldetails);
        Refusebutton=findViewById(R.id.Refusebutton);
        Allowbutton=findViewById(R.id.Allowbutton);

        //获取当前任务的所有信息
        BmobQuery<MyTask> bmobQuery=new BmobQuery<MyTask>();
        String bql = "select * from MyTask where tid = ?";
        bmobQuery.setSQL(bql);
        bmobQuery.setPreparedParams(new Object[]{Integer.parseInt(stid)});
        bmobQuery.doSQLQuery(new SQLQueryListener<MyTask>(){
            @Override
            public void done(BmobQueryResult<MyTask> bmobQueryResult, BmobException e) {

                if(e==null){
                    List<MyTask> list = (List<MyTask>) bmobQueryResult.getResults();//查询结果
                    stitle=list.get(0).getTname();
                    sdetails=list.get(0).getTdetail();
                    sordercaceldetails=list.get(0).getTordercanceldetails();

                    //显示在界面上
                    ordertitle.setText(stitle);
                    renwudetails.setText(sdetails);
                    ordercanceldetails.setText(sordercaceldetails);

                    //一些共有属性的设置
                    Objectid=list.get(0).getObjectId();//获取bmob中默认的ObjectId值
//                    System.out.println("获取当前任务的Objectid="+Objectid);

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
                    myTask.setTorder(2);//先维持异常订单状态

                    //从任务id找到接单者消息表的id
                    BmobQuery<MyOrderRead> bmobQuery1=new BmobQuery<MyOrderRead>();
                    String bql1 = "select * from MyOrderRead where tid = ?";
                    bmobQuery1.setSQL(bql1);
                    bmobQuery1.setPreparedParams(new Object[]{Integer.parseInt(stid)});
                    bmobQuery1.doSQLQuery(new SQLQueryListener<MyOrderRead>(){

                        @Override
                        public void done(BmobQueryResult<MyOrderRead> bmobQueryResult, BmobException e) {
                            List<MyOrderRead> listread= bmobQueryResult.getResults();
                            Objectreadid=listread.get(0).getObjectId();
                            myOrderRead.setTid(listread.get(0).getTid());
                            myOrderRead.setId(myTask.getId());//接单者编号
                            myOrderRead.setTname(listread.get(0).getTname());
                            myOrderRead.setTorderread(1);//通知接单者
                        }
                    });

                }

            }
        });


        //拒绝取消订单
        Refusebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bmobQuery.doSQLQuery(new SQLQueryListener<MyTask>(){

                    @Override
                    public void done(BmobQueryResult<MyTask> bmobQueryResult, BmobException e) {
                        myTask.setTorder(1);//将当前订单恢复成进行中订单
                        myTask.setTordercheck(1);//拒绝取消订单

                        myTask.update(Objectid,new UpdateListener(){
                            @Override
                            public void done(BmobException e) {

                                if (e == null) {

                                    System.out.println("Objectreadid="+Objectreadid);
                                    //更新接单者消息表
                                    myOrderRead.setTorderreaddetails("已拒绝取消订单,请尽快完成");
                                    myOrderRead.update(Objectreadid, new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e==null){
                                                System.out.println("已拒绝取消订单="+Objectreadid);
                                                Toast.makeText(RootOrderDetailsActivity.this,"已拒绝取消订单",Toast.LENGTH_SHORT).show();
                                                returnlist();
                                            }
                                        }
                                    });

//                                    Toast.makeText(RootOrderDetailsActivity.this,"已拒绝取消订单",Toast.LENGTH_SHORT).show();
//                                    returnlist();
                                }

                            }
                        });


                    }
                });

            }
        });


        //同意取消订单
        Allowbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bmobQuery.doSQLQuery(new SQLQueryListener<MyTask>(){

                    @Override
                    public void done(BmobQueryResult<MyTask> bmobQueryResult, BmobException e) {
                        myTask.setTorder(0);//将当前订单改成未接单状态
                        myTask.setTordercheck(2);//同意取消订单

                        myTask.update(Objectid,new UpdateListener(){
                            @Override
                            public void done(BmobException e) {

                                if (e == null) {
                                    System.out.println("Objectreadid="+Objectreadid);
                                    //更新接单者消息表
                                    myOrderRead.setTorderreaddetails("已同意取消订单");
                                    myOrderRead.update(Objectreadid, new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e==null){
//                                                System.out.println("已同意取消订单="+Objectreadid);
                                                Toast.makeText(RootOrderDetailsActivity.this,"已同意取消订单",Toast.LENGTH_SHORT).show();
                                                returnlist();
                                            }
                                        }
                                    });

//                                    Toast.makeText(RootOrderDetailsActivity.this,"已同意取消订单",Toast.LENGTH_SHORT).show();
//                                    returnlist();
                                }

                            }
                        });


                    }
                });


            }
        });


        //返回异常订单列表
        orderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent button = new Intent(RootOrderDetailsActivity.this, RootOrderActivity.class);//异常订单列表
                startActivity(button);
                finish();
            }
        });


    }

    //由当前界面跳转到异常订单列表
    void returnlist(){
        Intent button = new Intent(RootOrderDetailsActivity.this, RootOrderActivity.class);//异常订单列表
        startActivity(button);
        finish();
    }


}

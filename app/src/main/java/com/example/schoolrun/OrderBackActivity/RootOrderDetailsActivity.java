package com.example.schoolrun.OrderBackActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

//管理员详细订单信息
import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Activity.RootMainActivity;
import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.R;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

public class RootOrderDetailsActivity extends AppCompatActivity{

    private ImageButton orderlist;//返回管理员订单列表按钮
    private TextView ordertitle;
    private TextView renwudetails;
    private TextView ordercanceldetails;
    private Button Refusebutton;
    private Button Allowbutton;

    private Intent intent;
    private String stid;

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

                }

            }
        });

        //返回异常订单列表
        orderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent button = new Intent(RootOrderDetailsActivity.this, RootOrderActivity.class);//任务
                startActivity(button);
                finish();
            }
        });


    }

}

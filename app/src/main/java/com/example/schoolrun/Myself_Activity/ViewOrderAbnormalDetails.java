package com.example.schoolrun.Myself_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.R;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

//异常订单信息详情
public class ViewOrderAbnormalDetails extends AppCompatActivity {

    private TextView item_tname;
    private TextView item_tkind;
    private TextView item_tdetail;
    private TextView dingdanabnormal;
    private ImageButton returnfirstbutton;

    private Intent intent;
    private String stid;

    private String stitle;
    private String skind;
    private String sdetails;
    private String sdingdanabnormal;

    private MyTask myTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_order_abnormal_details);//绑定异常订单详细信息布局
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能

        item_tname=findViewById(R.id.jiefinish_tname);
        item_tkind=findViewById(R.id.item_tkind);
        item_tdetail=findViewById(R.id.item_tdetail);
        dingdanabnormal=findViewById(R.id.dingdanabnormal);
        returnfirstbutton=findViewById(R.id.returnfirstbutton);

        intent=getIntent();//获取上一个界面的intent
        stid=intent.getStringExtra("tid");//获取上个界面传过来的任务tid
        sdingdanabnormal=intent.getStringExtra("tdingdanabnormal");//获取上个界面传过来的异常订单状态
        myTask=new MyTask();

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
                    skind=list.get(0).getTkind();
                    sdetails=list.get(0).getTdetail();

                    //显示在界面上
                    item_tname.setText(stitle);
                    item_tkind.setText(skind);
                    item_tdetail.setText(sdetails);
                    dingdanabnormal.setText(sdingdanabnormal);

                }

            }
        });

        //返回订单任务列表
        returnfirstbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent button1 = new Intent(ViewOrderAbnormalDetails.this, ViewOrderAbnormalActivity.class);
                startActivity(button1);
                finish();
            }
        });


    }


}

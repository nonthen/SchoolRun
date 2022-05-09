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

//订单详细信息
public class ViewOrderDetailsActivity extends AppCompatActivity {

    private TextView tvtitle;
    private TextView tvkind;
    private TextView tvdetail;
    private TextView tvtargetaddress;
    private TextView tvmyaddress;
    private TextView tvphone;
    private TextView tvprice;
    private TextView tvbegaintime;//任务发布时间
    private TextView tvjiedantime;//任务被接单时间
    private ImageButton fanhuilistbutton;

    private Intent intent;
    private String stid;

    private String stitle;
    private String skind;
    private String sdetails;
    private String stargetaddres;
    private String smyaddress;
    private String sphone;
    private String sprice;
    private String sbegaintime;
    private String sjiedantime;

    private long phone;
    private Number price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_order_details);//绑定订单布局
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能

        //绑定布局
        tvtitle=findViewById(R.id.jiefinish_tname);
        tvkind=findViewById(R.id.item_tkind);
        tvdetail=findViewById(R.id.item_tdetail);
        tvtargetaddress=findViewById(R.id.item_targetaddress);
        tvmyaddress=findViewById(R.id.item_myaddress);
        tvphone=findViewById(R.id.item_tphone);
        tvprice=findViewById(R.id.jiefinish_tprice);
        tvbegaintime=findViewById(R.id.begain_time);
        tvjiedantime=findViewById(R.id.jiedan_time);
        fanhuilistbutton=findViewById(R.id.returnlistbutton);

        intent=getIntent();//获取上一个界面的intent
        stid=intent.getStringExtra("tid");//获取上个界面传过来的任务tid


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
                    stargetaddres=list.get(0).getTargetaddress();
                    smyaddress=list.get(0).getMyaddress();
                    phone=list.get(0).getTphone();
                    price=list.get(0).getTprice();
                    sbegaintime=list.get(0).getCreatedAt();
                    sjiedantime=list.get(0).getUpdatedAt();


                    //显示在界面上
                    tvtitle.setText(stitle);
                    tvkind.setText(skind);
                    tvdetail.setText(sdetails);
                    tvtargetaddress.setText(stargetaddres);
                    tvmyaddress.setText(smyaddress);
                    tvphone.setText(String.valueOf(phone));
                    tvprice.setText(String.valueOf(price));
                    tvbegaintime.setText(sbegaintime);
                    tvjiedantime.setText(sjiedantime);

                }

            }
        });

        //返回已接单的任务列表
        fanhuilistbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent button1 = new Intent(ViewOrderDetailsActivity.this, ViewOrderlistActivity.class);
                startActivity(button1);
                finish();

            }
        });


    }
}

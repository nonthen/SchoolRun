package com.example.schoolrun.Myself_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.LoginActivity;
import com.example.schoolrun.R;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

//已完成评价任务的详细信息
public class AppraiseFinshDetailsActivity extends AppCompatActivity {

    private TextView tvtitle;
    private TextView tvkind;
    private TextView tvdetail;
    private TextView tvtargetaddress;
    private TextView tvmyaddress;
    private TextView tvphone;
    private TextView tvprice;
    private TextView tvscore;
    private TextView tvappfinishdetails;
    private Button fanhuifinishlist;

    private Intent intent;
    private String stid;

    private String stitle;
    private String skind;
    private String sdetails;
    private String stargetaddres;
    private String smyaddress;
    private String sphone;
    private String sprice;
    private String sscore;
    private String sappfinishdetails;

    private int phone;
    private Number price;
    private float score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.appraisefiniah_details);//绑定已经评价任务详细信息布局

        //绑定布局
        tvtitle=findViewById(R.id.item_tname);
        tvkind=findViewById(R.id.item_tkind);
        tvdetail=findViewById(R.id.item_tdetail);
        tvtargetaddress=findViewById(R.id.item_targetaddress);
        tvmyaddress=findViewById(R.id.item_myaddress);
        tvphone=findViewById(R.id.item_tphone);
        tvprice=findViewById(R.id.item_tprice);
        tvscore=findViewById(R.id.appfinishscore);
        tvappfinishdetails=findViewById(R.id.appfinishdetails);
        fanhuifinishlist=findViewById(R.id.button_fanhui);

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
                    sappfinishdetails=list.get(0).getTappraisetext();
                    phone=list.get(0).getTphone();
                    price=list.get(0).getTprice();
                    score=list.get(0).getTappraise();

                    //显示在界面上
                    tvtitle.setText(stitle);
                    tvkind.setText(skind);
                    tvdetail.setText(sdetails);
                    tvtargetaddress.setText(stargetaddres);
                    tvmyaddress.setText(smyaddress);
                    tvappfinishdetails.setText(sappfinishdetails);
                    tvphone.setText(String.valueOf(phone));
                    tvprice.setText(String.valueOf(price));
                    tvscore.setText(String.valueOf(score));

                }

            }
        });

        //返回已完成评价任务列表界面
        fanhuifinishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AppraiseFinshDetailsActivity.this,AppraiseFinishActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}

package com.example.schoolrun.DataBackActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.Entity.MyUser;
import com.example.schoolrun.OrderBackActivity.RootUserActivity;
import com.example.schoolrun.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.SQLQueryListener;

//后台大致数据
public class RootDataActivity extends AppCompatActivity {

    private ImageButton returnuserbutton;
    private TextView personcount;
    private TextView fadancount;
    private TextView jiedancount;
    private TextView rootusercount;
    private TextView taskcount;
    private TextView totalmoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_data_statistics);

        returnuserbutton=findViewById(R.id.returnuserbutton);
        personcount=findViewById(R.id.personcount);
        fadancount=findViewById(R.id.fadancount);
        jiedancount=findViewById(R.id.jiedancount);
        rootusercount=findViewById(R.id.rootusercount);
        taskcount=findViewById(R.id.taskcount);
        totalmoney=findViewById(R.id.totalmoney);

        UserCount();
        FadanCount();
        JiedanCount();
        TaskCount();
        MoneySum();

        //跳转到管理用户界面
        returnuserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RootDataActivity.this, RootUserActivity.class);
                startActivity(intent);
                finish();//释放资源
            }
        });

    }

    //统计使用当前系统的总人数
    void UserCount(){
        BmobQuery<MyUser> bmobQuery=new BmobQuery<MyUser>();
        bmobQuery.count(MyUser.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e==null){
                    personcount.setText(String.valueOf(integer));
                    rootusercount.setText(String.valueOf(1));
                }
            }
        });

    }

    //统计发单者人数
    void FadanCount(){
        BmobQuery<MyUser> bmobQuery=new BmobQuery<MyUser>();
        bmobQuery.addWhereEqualTo("ucheck",0);
        bmobQuery.count(MyUser.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e==null){
                    fadancount.setText(String.valueOf(integer));
                }
            }
        });
    }

    //统计接单者人数
    void JiedanCount(){
        BmobQuery<MyUser> bmobQuery=new BmobQuery<MyUser>();
        bmobQuery.addWhereEqualTo("ucheck",1);
        bmobQuery.count(MyUser.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e==null){
                    jiedancount.setText(String.valueOf(integer-1));
                }
            }
        });
    }

    //统计任务数量
    void TaskCount(){
        BmobQuery<MyTask> bmobQuery=new BmobQuery<MyTask>();
        bmobQuery.addWhereEqualTo("tcheck",1);
        bmobQuery.count(MyTask.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e==null){
                    taskcount.setText(String.valueOf(integer));
                }
            }
        });

    }

    //统计当前软件的总收益
    void MoneySum(){
        BmobQuery<MyUser> bmobQuery=new BmobQuery<MyUser>();
        String bql = "select * from MyUser";
        bmobQuery.setSQL(bql);
        bmobQuery.doSQLQuery(new SQLQueryListener<MyUser>(){
            @Override
            public void done(BmobQueryResult<MyUser> bmobQueryResult, BmobException e) {
                if (e==null){
                    float money=0;
                    List<MyUser> list = (List<MyUser>) bmobQueryResult.getResults();//查询结果
                    for (int i=0;i<list.size();i++){
                        float moneytemp=Float.parseFloat(list.get(i).getIncome());
                        money=moneytemp+money;
                    }
                    money= (float) (money*0.2);
                    totalmoney.setText(String.valueOf(money));
                }
            }
        });

    }

}

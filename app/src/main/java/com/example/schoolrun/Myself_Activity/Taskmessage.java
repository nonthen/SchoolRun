package com.example.schoolrun.Myself_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

//审核成功之后的任务详细信息
public class Taskmessage extends AppCompatActivity {
    private String sprice, sphone, storder, uaccount;
    private String tasktid,time;
    private String sname, skind, sdetail, sargetaddress, smyaddress, phone, uid;
    private Number iphone, iprice, torder, id;
    private String data;
    private Button back;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_yestask);
        final Intent intent = getIntent();
        tasktid = intent.getStringExtra("tid");
        Integer temptid;
        temptid = Double.valueOf(tasktid).intValue();//带小数点的字符串是不能直接转成整数的，应该先将它转成double类型再转int整数。
        System.out.println("tasktid是：" + tasktid + "temptid是" + temptid);
        TextView textView1 = (TextView) findViewById(R.id.item_tname);
        TextView textView2 = findViewById(R.id.item_tkind);
        TextView textView3 = (TextView) findViewById(R.id.item_tdetail);
        TextView textView4 = (TextView) findViewById(R.id.item_targetaddress);
        TextView textView5 = (TextView) findViewById(R.id.item_myaddress);
        TextView textView6 = (TextView) findViewById(R.id.item_tphone);
        TextView textView7 = (TextView) findViewById(R.id.item_tprice);
        TextView textView8 = (TextView) findViewById(R.id.item_time);
        back = findViewById(R.id.fanhui);
        BmobQuery<MyTask> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("tid", temptid);
        bmobQuery.findObjects(new FindListener<MyTask>() {
            //            String sname,skind,sdetail,sargetaddress,smyaddress;
//            Number iphone,iprice,iph;
            @Override
            public void done(List<MyTask> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        sname = list.get(i).getTname();
                        skind = list.get(i).getTkind();
                        sdetail = list.get(i).getTdetail();
                        sargetaddress = list.get(i).getTargetaddress();
                        smyaddress = list.get(i).getMyaddress();
                        iphone = list.get(i).getTphone();
                        iprice = list.get(i).getTprice();
                        data = list.get(i).getCreatedAt();
                        torder = list.get(i).getTorder();
                        id = list.get(i).getId();
                        time = list.get(i).getCreatedAt();
                        System.out.println("id:" + id);
                    }
                } else {
                    System.out.println("查询失败");
                }
                sphone = String.valueOf(iphone);
                sprice = String.valueOf(iprice);
                storder = String.valueOf(torder);
                textView1.setText(sname);
                textView2.setText(skind);
                textView3.setText(sdetail);
                textView4.setText(sargetaddress);
                textView5.setText(smyaddress);
                textView6.setText(sphone);
                textView7.setText(sprice);
                textView8.setText(time);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {//返回
            @Override
            public void onClick(View view) {
                Intent a = new Intent();
                String uid=intent.getStringExtra("uid");
                String objectId=intent.getStringExtra("objectId");
                System.out.println("id是："+uid+"objectId是："+objectId);
                a.putExtra("uid", uid);
                a.putExtra("objectId", objectId);
                startActivity(a);
                finish();
            }
        });
    }
}


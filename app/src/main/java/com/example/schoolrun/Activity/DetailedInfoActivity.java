package com.example.schoolrun.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class DetailedInfoActivity extends AppCompatActivity {
    private String sprice, sphone, storder,uaccount;
    private String tasktid;
    private Button btmain,btorder;
    private String sname,skind,sdetail,sargetaddress,smyaddress;
    private Number iphone,iprice,torder;
    private String data;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_task_detailed_info);
        final Intent intent = getIntent();
        tasktid=intent.getStringExtra("tid");
        Integer temptid;
        temptid=Double.valueOf(tasktid).intValue();//带小数点的字符串是不能直接转成整数的，应该先将它转成double类型再转int整数。
        System.out.println("tasktid是："+tasktid+"temptid是"+temptid);
        TextView textView1 =(TextView)findViewById(R.id.item_tname);
        TextView textView2 =findViewById(R.id.item_tkind);
        TextView textView3 =(TextView)findViewById(R.id.item_tdetail);
        TextView textView4=(TextView)findViewById(R.id.item_targetaddress);
        TextView textView5=(TextView)findViewById(R.id.item_myaddress);
        TextView textView6=(TextView)findViewById(R.id.item_tphone);
        TextView textView7=(TextView)findViewById(R.id.item_tprice);
        btorder=findViewById(R.id.button_receive_order);
        btmain=findViewById(R.id.button_main);
        BmobQuery<MyTask> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("tid",temptid);
        bmobQuery.findObjects(new FindListener<MyTask>() {
            //            String sname,skind,sdetail,sargetaddress,smyaddress;
//            Number iphone,iprice,iph;
            @Override
            public void done(List<MyTask> list, BmobException e) {
                if (e==null){
                    for (int i=0;i<list.size();i++){
                        sname =list.get(i).getTname();
                        skind=list.get(i).getTkind();
                        sdetail=list.get(i).getTdetail();
                        sargetaddress=list.get(i).getTargetaddress();
                        smyaddress=list.get(i).getMyaddress();
                        iphone=list.get(i).getTphone();
                        iprice=list.get(i).getTprice();
                        data=list.get(i).getCreatedAt();
                        torder=list.get(i).getTorder();
                        System.out.println("sname为"+sname+"iphone为"+iphone+"data为"+data);
                    }
                }
                else {
                    System.out.println("查询失败");
                }
                sphone =String.valueOf(iphone);
                sprice =String.valueOf(iprice);
                storder =String.valueOf(torder);
                textView1.setText(sname);
                textView2.setText(skind);
                textView3.setText(sdetail);
                textView4.setText(sargetaddress);
                textView5.setText(smyaddress);
                textView6.setText(sphone);
                textView7.setText(sprice);
            }
        });

        btorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer ord=torder.intValue();
                System.out.println("ord为"+ord);
                if (ord==0){
                    Intent intent = new Intent(DetailedInfoActivity.this, ReceiveOrderActivity.class);
//                intent.putExtra("tid","tname",tasktid,sname);
                    Bundle bundle=new Bundle();
                    bundle.putString("bundle_tid",tasktid);
                    bundle.putString("bundle_tname",sname);
                    bundle.putString("bundle_tkind",skind);
                    bundle.putString("bundle_tdetail",sdetail);
                    bundle.putString("bundle_targetaddress",sargetaddress);
                    bundle.putString("bundle_tmyaddress",smyaddress);
                    bundle.putString("bundle_phone", sphone);
                    bundle.putString("bundle_price", sprice);
                    bundle.putString("bundle_data",data);
                    bundle.putString("bundle_torder", storder);
//                    bundle.putString("UACCOUNT",uaccount);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else {
                    Snackbar.make(btorder,"任务已被其他同学接单~请浏览首页选择新的任务吧~",Snackbar.LENGTH_LONG).show();
                }

            }
        });
        //返回登录
        btmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedInfoActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }


}

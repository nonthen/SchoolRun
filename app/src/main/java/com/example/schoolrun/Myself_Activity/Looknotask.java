package com.example.schoolrun.Myself_Activity;

import android.content.Intent;
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
import com.example.schoolrun.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

//查看未审核的任务
public class Looknotask extends AppCompatActivity implements View.OnClickListener{
    private RadioGroup mRadioGroup;
    private RadioButton tab1,tab2,tab3;  //3个单选按钮
    private ImageButton button1;
    private String temp0,temp1,temp2,temp3;
    String objecttid;
    private String errorTcheck;
    public static String result,tempcheckdetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.look_task);
        //初始化控件
        mRadioGroup=findViewById(R.id.rg_tab);
        tab1=findViewById(R.id.rb_home);
        tab2=findViewById(R.id.rb_task);//发布任务
        tab3=findViewById(R.id.rb_nocheck);//发布任务
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        button1=findViewById(R.id.button3);//跳转到查看个人信息界面
        Intent intent = getIntent();
        String uid=intent.getStringExtra("uid");
        String objectId=intent.getStringExtra("objectId");
        //这里获取了任务主页
        BmobQuery<MyTask> bmobQuery = new BmobQuery<MyTask>();
        bmobQuery.findObjects(new FindListener<MyTask>() {
            @Override
            public void done(List<MyTask> list, BmobException e) {

                if (e == null) {
                    SimpleAdapter simpleAdapter = null;
                    Log.d("path", "查询成功");
                    Map<String, String> mHashMap;
                    String tempTprice, tempTid, tempTphone, tempTkind,tempUid;
                    Toast.makeText(Looknotask.this, "成功，共" + list.size() + "条数据", Toast.LENGTH_SHORT).show();
                    List<Map<String, String>> mapList = new ArrayList<>();
                    List<Map<String, String>> mapList1 = new ArrayList<>();

                    String id = null;
                    System.out.println("uid:"+uid);

                    for (MyTask myTask : list) {
                        tempUid = String.valueOf(myTask.getUid());
                        result =" ";
                        String check = String.valueOf(myTask.getTcheck());
                        System.out.println("tempUid:"+tempUid);
                        if (tempUid.equals(uid)) {
                            id =tempUid;
                        }
                        if(tempUid.equals(id)&&check.equals("2")) {
                            tempUid = String.valueOf(myTask.getTid());
                            System.out.println("tempUid:" + tempUid);
                            tempTprice = String.valueOf(myTask.getTprice());
                            tempTid = String.valueOf(myTask.getTid());
                            tempTphone = String.valueOf(myTask.getTphone());
                            tempTkind = String.valueOf(myTask.getTkind());
                            System.out.println("tempTid="+tempTid+"   tempTkind="+tempTkind);
                            String[] items = {"任务标题违规", "任务详情违规", "任务目标地址不合理", "任务本人地址不合理"};
                            errorTcheck =myTask.getTcheckerrordetails();
                            System.out.println("错误消息的值为errorTcheck="+errorTcheck);
                            String[] split = errorTcheck.split(" ");
                            temp0 = temp1 = temp2 = temp3 = " ";
                            if (!split[0].equals(items[0])) {
                                temp0 =items[0];
                            }
                            if (!split[1].equals(items[1])) {
                                temp1 = items[1];
                            }
                            if (!split[2].equals(items[2])) {
                                temp2 =items[2];
                            }
                            if (!split[3].equals(items[3])) {
                                temp3 = items[3];
                            }
                            result =temp0 + temp1 + temp2 + temp3;
                            System.out.println("错误消息的值为result="+result);
                            mHashMap = new HashMap<>();
                            mHashMap.put("tname", myTask.getTname());
                            mHashMap.put("terrordetail", result);
                            mHashMap.put("targetaddress", myTask.getTargetaddress());
                            mHashMap.put("tprice", tempTprice);
                            mHashMap.put("tid", tempTid);
                            mHashMap.put("tdetail", myTask.getTdetail());
                            mHashMap.put("myaddress", myTask.getMyaddress());
                            mHashMap.put("tphone", tempTphone);
                            mHashMap.put("tkind", tempTkind);
                            mapList1.add(mHashMap);
                            System.out.println("标题：" + myTask.getTname() + "目标地址：" + myTask.getTargetaddress() + "价格：" + myTask.getTprice());

                        }
                    }
                    ListView listView = findViewById(R.id.listView);
                    SimpleAdapter simpleAdapter1 = new SimpleAdapter(Looknotask.this, mapList1, R.layout.view_task_checkno, new String[]{"tname", "targetaddress", "tprice","terrordetail"}, new int[]{R.id.item_tname, R.id.item_targetaddress, R.id.item_tprice,R.id.item_tcheckdetail});
                    listView.setAdapter(simpleAdapter1);
                    simpleAdapter1.notifyDataSetChanged();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            //Bmob获取listview中某一行数据
                            intent.setClass(Looknotask.this, Judgetask.class);
                            intent.putExtra("tid", mapList1.get(position).get("tid").toString()); // 获取该列表项的key为id的键值，即商品的id，将其储存在Bundle传递给打开的页面
                            System.out.println(position);
                            System.out.println(position);
                            intent.putExtra("uid", uid);
                            intent.putExtra("objectId", objectId);
                            System.out.println("uid:"+uid+"objectId:"+objectId);
                            startActivity(intent);
                        }
                    });

                }
                else {
                    Log.d("path","查询不成功");
                }
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {//返回
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Looknotask.this, TestMeAc.class);
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

    @Override
    public void onClick(View view) {
        Intent intent = getIntent();
        switch (view.getId()) {
            case R.id.rb_home:
                Intent button1 = new Intent(this,Looktask.class);
                String uid=intent.getStringExtra("uid");
                button1.putExtra("uid", uid);
                System.out.println("uid:"+uid);
                startActivity(button1);
                break;
            case R.id.rb_task:
                Intent button2 = new Intent(this, Looknotask.class);
                String uid1=intent.getStringExtra("uid");
                button2.putExtra("uid", uid1);
                System.out.println("uid:"+uid1);
                startActivity(button2);
                break;
            case R.id.rb_nocheck:
                Intent button3 = new Intent(this, Lookcheckingtask.class);
                String uid2=intent.getStringExtra("uid");
                button3.putExtra("uid", uid2);
                System.out.println("uid:"+uid2);
                startActivity(button3);
                break;
        }
    }
}
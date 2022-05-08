package com.example.schoolrun.Myself_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Activity.DetailedInfoActivity;
import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

//用户查看收益
public class Lookincome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.look_income);
        TextView textView1 = (TextView) findViewById(R.id.showmoney);
        Intent intent = getIntent();
        String uid=intent.getStringExtra("uid");
        String objectId=intent.getStringExtra("objectId");
        //这里获取了任务主页
        BmobQuery<MyTask> bmobQuery = new BmobQuery<MyTask>();
        bmobQuery.findObjects(new FindListener<MyTask>() {
            @Override
            public void done(List<MyTask> list, BmobException e) {

                if (e == null) {
                    SimpleAdapter simpleAdapter;
                    Log.d("path", "查询成功");
                    Map<String, String> mHashMap;
                    String tempTprice, tempTid, tempTphone, tempTkind,tempUid;
                    Toast.makeText(Lookincome.this, "成功，共" + list.size() + "条数据", Toast.LENGTH_SHORT).show();
                    List<Map<String, String>> mapList = new ArrayList<>();

                    System.out.println("uid:"+uid);
                    double sum = 0;
                    String sum1,id=null;

                    for (MyTask myTask : list) {
                        tempUid = String.valueOf(myTask.getId());
                        System.out.println("tempUid:"+tempUid);
                        if (tempUid.equals(uid)) {
                            id =tempUid;
                        }
                        if(tempUid.equals(id)) {
                            String finish = String.valueOf(myTask.getTfinish());
                            if (finish.equals("1")) {
                                tempTprice = String.valueOf(myTask.getTprice());
                                double price = Double.parseDouble(tempTprice);
                                tempTid = String.valueOf(myTask.getTid());
                                tempTphone = String.valueOf(myTask.getTphone());
                                tempTkind = String.valueOf(myTask.getTkind());
                                mHashMap = new HashMap<>();
                                mHashMap.put("tname", myTask.getTname());
                                mHashMap.put("targetaddress", myTask.getTargetaddress());
                                mHashMap.put("tprice", tempTprice);
                                mHashMap.put("tid", tempTid);
                                mHashMap.put("tdetail", myTask.getTdetail());
                                mHashMap.put("myaddress", myTask.getMyaddress());
                                mHashMap.put("tphone", tempTphone);
                                mHashMap.put("tkind", tempTkind);
                                mapList.add(mHashMap);
                                sum = sum + (price * 0.97);
                                System.out.println("标题：" + myTask.getTname() + "目标地址：" + myTask.getTargetaddress() + "价格：" + myTask.getTprice());
                            }
                        }
                    }
                    ListView listView = findViewById(R.id.listView);
                    simpleAdapter = new SimpleAdapter(Lookincome.this, mapList, R.layout.view_task_item_info, new String[]{"tname", "targetaddress", "tprice"}, new int[]{R.id.item_tname, R.id.item_targetaddress, R.id.item_tprice});
                    listView.setAdapter(simpleAdapter);
                    sum1=String.valueOf(sum);
                    textView1.setText(sum1);
                    simpleAdapter.notifyDataSetChanged();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            //Bmob获取listview中某一行数据
                            intent.setClass(Lookincome.this, DetailedInfoActivity.class);
                            intent.putExtra("tid", mapList.get(position).get("tid").toString()); // 获取该列表项的key为id的键值，即商品的id，将其储存在Bundle传递给打开的页面
                            System.out.println(position);
                            startActivity(intent);
                        }
                    });

                }
                else {
                    Log.d("path","查询不成功");
                }
            }
        });
    }

    }

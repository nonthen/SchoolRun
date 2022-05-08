package com.example.schoolrun.OrderBackActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Entity.MyUser;
import com.example.schoolrun.R;
import com.example.schoolrun.userbackground.user_activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

//管理员审核用户
public class RootUserCheckActivity extends AppCompatActivity {

    private ImageButton returnrootmu;//返回用户管理界面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_user_check);
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能

        returnrootmu=findViewById(R.id.returnrootmu);

        BmobQuery<MyUser> bmobQuery = new BmobQuery<MyUser>();
        String bql = "select * from MyUser where usubapplicate=1";//提交申请的用户
        bmobQuery.setSQL(bql);//必须先获取uid，由uaccount获取uid
        bmobQuery.doSQLQuery(new SQLQueryListener<MyUser>() {

            @Override
            public void done(BmobQueryResult<MyUser> bmobQueryResult, BmobException e) {

                if (e==null){
                    //获取数据，存储到Map中
                    SimpleAdapter simpleAdapter;
                    Map<String, String> mHashMap;
                    List<Map<String,String>> mapList=new ArrayList<>();
                    List<MyUser> list = (List<MyUser>) bmobQueryResult.getResults();//查询结果
                    for (MyUser myUser:list){
                        mHashMap=new HashMap<>();
                        mHashMap.put("uid",String.valueOf(myUser.getUid()));
                        mHashMap.put("uaccount",myUser.getAccount());
                        mHashMap.put("urealname",myUser.getUrealname());
                        mapList.add(mHashMap);
                    }

                    //获取数据显示在列表中
                    ListView listView=findViewById(R.id.listView);
                    simpleAdapter=new SimpleAdapter(RootUserCheckActivity.this,mapList,R.layout.root_user_checkuser_info,
                            new String[]{"uaccount","urealname"},
                            new int[]{R.id.item_uaccount,R.id.item_urealname});
                    listView.setAdapter(simpleAdapter);
                    simpleAdapter.notifyDataSetChanged();

                    //跳转到用户详细信息界面
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            //Bmob获取listview中某一行数据
                            System.out.println("跳转到审核用户信息详情");
                            Intent intent = new Intent();
                            intent.setClass(RootUserCheckActivity.this, RootUserCheckDetailsActivity.class);
                            // 获取该列表项的key为id的键值，即商品的id，将其储存在Bundle传递给打开的页面
                            intent.putExtra("uid", mapList.get(position).get("uid"));
                            startActivity(intent);
                            finish();
                        }
                    });


                }
                else {
                    Toast.makeText(RootUserCheckActivity.this,"用户审核列表，读取失败",Toast.LENGTH_SHORT).show();
                }

            }
        });

        //返回用户管理列表
        returnrootmu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RootUserCheckActivity.this, user_activity.class);
                startActivity(intent);
                finish();
            }
        });

    }


}

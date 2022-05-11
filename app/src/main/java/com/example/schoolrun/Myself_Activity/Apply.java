package com.example.schoolrun.Myself_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Activity.TestMeAc;
import com.example.schoolrun.Entity.MyUser;
import com.example.schoolrun.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

//用户申请成为接单者功能
public class Apply extends AppCompatActivity {
    private Button button, back;
    private RadioGroup mRadioGroup;
    private RadioButton tab1, tab2, tab3;  //3个单选按钮
    String useruid;
    int check, subapplicate, Goodappraisecount, Badappraisecount;
    Float reputation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply);
        //初始化控件

        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能
        EditText textView1 = findViewById(R.id.taskname);
        EditText textView2 = findViewById(R.id.taskphone);
        button = findViewById(R.id.applybutton);
        back = findViewById(R.id.fanhui);


        Intent intent = getIntent();
        useruid = intent.getStringExtra("uid");
        String objectId = intent.getStringExtra("objectId");

        BmobQuery<MyUser> bmobQuery = new BmobQuery<MyUser>();
        bmobQuery.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                if (e == null) {
                    SimpleAdapter simpleAdapter;
                    Log.d("path", "查询成功");
                    Map<String, String> mHashMap;
                    String tempTprice, tempTid, tempTphone, tempTkind;
                    Toast.makeText(Apply.this, "成功，共" + list.size() + "条数据", Toast.LENGTH_SHORT).show();
                    List<Map<String, String>> mapList = new ArrayList<>();
                    for (MyUser myuser : list) {
                        String Uid = String.valueOf(myuser.getUid());
                        if (Uid.equals(useruid)) {
                            check = myuser.getUcheck();
                            subapplicate = myuser.getUsubapplicate();
                            Badappraisecount = myuser.getBadappraisecount();
                            Goodappraisecount = myuser.getGoodappraisecount();
                            reputation = myuser.getUreputation();
                            System.out.println("=========Goodappraisecount:" + Goodappraisecount);
                        }


                    }
                }
                //当前登陆活动设置一个监听事件
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String name = textView1.getText().toString().trim();//获取文本框的数据
                        String shenfenzheng = textView2.getText().toString().trim();
                        Intent intent = getIntent();
                        BmobQuery<MyUser> bmobQuery = new BmobQuery<>();
                        String useruid = intent.getStringExtra("uid");
                        String objectId = intent.getStringExtra("objectId");
                        System.out.println("============:" + objectId + useruid);

                        MyUser p2 = new MyUser();
                        int id = Integer.parseInt(useruid);
                        p2.setUid(id);
                        p2.setUrealname(name);
                        p2.setUidentitycard(shenfenzheng);
                        p2.setUsubapplicate(1);
                        p2.setUcheck(check);
                        p2.setGoodappraisecount(Goodappraisecount);
                        p2.setBadappraisecount(Badappraisecount);
                        p2.setUreputation(reputation);
                        p2.update(objectId, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Snackbar.make(button, "上传成功", Snackbar.LENGTH_LONG).show();
                                } else {
                                    Snackbar.make(button, "上传失败", Snackbar.LENGTH_LONG).show();
                                }
                            }

                        });
                    }
                });


                back.setOnClickListener(new View.OnClickListener() {//返回
                    @Override
                    public void onClick(View view) {
                        Intent a = new Intent(Apply.this, TestMeAc.class);
                        String uid = intent.getStringExtra("uid");
                        String objectId = intent.getStringExtra("objectId");
                        System.out.println("id是：" + uid + "objectId是：" + objectId);
                        a.putExtra("uid", uid);
                        a.putExtra("objectId", objectId);
                        startActivity(a);
                        finish();
                    }
                });
            }


            });
        }
}
package com.example.schoolrun.Myself_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Activity.MainActivity;
import com.example.schoolrun.Activity.ReleaseTask;
import com.example.schoolrun.Activity.TestMeAc;
import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

//任务审核失败之后，选择继续发布还是取消发布
public class Judgetask extends AppCompatActivity implements View.OnClickListener {
    private Button button1, button2,back;
    private RadioGroup mRadioGroup;
    private RadioButton tab1, tab2, tab3;  //3个单选按钮
    String objecttidd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.notaskpanduan);
        //初始化控件
        mRadioGroup = findViewById(R.id.rg_tab);
        tab1 = findViewById(R.id.rb_home);
        tab2 = findViewById(R.id.rb_task);//发布任务
        tab3 = findViewById(R.id.rb_me);
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能
        button1 = findViewById(R.id.yes);
        button2 = findViewById(R.id.no);
        Intent intent = getIntent();
        String useruid = intent.getStringExtra("uid");
        String position = intent.getStringExtra("tid");
        Integer temptid;
        temptid=Double.valueOf(position).intValue();//带小数点的字符串是不能直接转成整数的，应该先将它转成double类型再转int整数。
        System.out.println("tasktid是："+position+"temptid是"+temptid);


        BmobQuery<MyTask> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("tid",temptid);
        bmobQuery.findObjects(new FindListener<MyTask>() {
            @Override
            public void done(List<MyTask> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        objecttidd = list.get(i).getObjectId();
                        System.out.println("objecttidd:"+objecttidd);
                    }
                } else {
                    System.out.println("查询失败");
                }
            }
        });


                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MyTask p2 = new MyTask();
                        p2.setObjectId(objecttidd);
                        p2.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Snackbar.make(button1, "删除成功", Snackbar.LENGTH_LONG).show();
                                } else {
                                    Snackbar.make(button1, "删除失败", Snackbar.LENGTH_LONG).show();
                                }
                            }

                        });
                    }
                });
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent.setClass(Judgetask.this, Tasknomessage.class);
                        intent.putExtra("tid", position); // 获取该列表项的key为id的键值，即商品的id，将其储存在Bundle传递给打开的页面
                        System.out.println(position);
                        intent.putExtra("uid", useruid);
                        System.out.println("uid:" + useruid);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                switch (view.getId()) {
                    case R.id.rb_home:
                        Intent button1 = new Intent(this, MainActivity.class);
                        startActivity(button1);
                        break;
                    case R.id.rb_task:
                        Intent button2 = new Intent(this, ReleaseTask.class);
                        startActivity(button2);
                        break;
                    case R.id.rb_me:
                        Intent button3 = new Intent(this, TestMeAc.class);
                        String uid = intent.getStringExtra("uid");
                        String objectId = intent.getStringExtra("objectId");
                        button3.putExtra("uid", uid);
                        button3.putExtra("objectId", objectId);
                        System.out.println("uid:" + uid + "objectId:" + objectId);
                        startActivity(button3);
                        break;
                }

            }
        }



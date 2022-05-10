package com.example.schoolrun.Myself_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Activity.MainActivity;
import com.example.schoolrun.Activity.ReleaseTask;
import com.example.schoolrun.Activity.TestMeAc;
import com.example.schoolrun.Entity.MyUser;
import com.example.schoolrun.R;
import com.google.android.material.snackbar.Snackbar;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

//修改密码
public class Changepassword extends AppCompatActivity implements View.OnClickListener {
    private Button button,back;
    private RadioGroup mRadioGroup;
    private RadioButton tab1,tab2,tab3;  //3个单选按钮
    String useruid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        //初始化控件
        mRadioGroup=findViewById(R.id.rg_tab);
        tab1=findViewById(R.id.rb_home);
        tab2=findViewById(R.id.rb_task);//发布任务
        tab3=findViewById(R.id.rb_me);
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能
        EditText textView1 = findViewById(R.id.showoldpassword);
        EditText textView2 = findViewById(R.id.shownewpassword);
        button = findViewById(R.id.change_passwordbutton);
        back = findViewById(R.id.fanhui);

        Intent intent = getIntent();
        useruid = intent.getStringExtra("uid");
        String objectId = intent.getStringExtra("objectId");
        //当前登陆活动设置一个监听事件
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String oldpassword = textView1.getText().toString().trim();//获取文本框的数据
                String newpassword = textView2.getText().toString().trim();
                Intent intent = getIntent();
                BmobQuery<MyUser> bmobQuery = new BmobQuery<>();
                String useruid = intent.getStringExtra("uid");
                String objectId = intent.getStringExtra("objectId");
                System.out.println(objectId + useruid + oldpassword + newpassword);

                String bql = "select * from MyUser where uid = ? and upassword = ?";
                bmobQuery.setSQL(bql);
                bmobQuery.setPreparedParams(new Object[]{useruid, oldpassword});

                bmobQuery.getObject(objectId, new QueryListener<MyUser>() {
                    public void done(MyUser myuser, BmobException e) {
                        //结果回调
                        if(e==null) {
                            System.out.println(myuser.getUpassword());
                            if(myuser.getUpassword().equals(oldpassword)) {
                                MyUser p3 = new MyUser();
                                int id = Integer.parseInt(useruid);
                                p3.setUid(id);
                                p3.setUpassword(newpassword);
                                p3.update(objectId, new UpdateListener() {

                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            Snackbar.make(button, "修改成功", Snackbar.LENGTH_LONG).show();
                                        } else {
                                            Snackbar.make(button, "修改失败", Snackbar.LENGTH_LONG).show();
                                        }
                                    }

                                });
                            }
                            else{
                                Snackbar.make(button, "原密码不对", Snackbar.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Snackbar.make(button, "查询失败", Snackbar.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });
        back.setOnClickListener(new View.OnClickListener() {//返回
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Changepassword.this,TestMeAc.class);
                String uid=intent.getStringExtra("uid");
                String objectId=intent.getStringExtra("objectId");
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
        switch (view.getId()){
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
                String uid=intent.getStringExtra("uid");
                String objectId=intent.getStringExtra("objectId");
                button3.putExtra("uid", uid);
                button3.putExtra("objectId", objectId);
                System.out.println("uid:"+uid+"objectId:"+objectId);
                startActivity(button3);
                break;
        }
    }
}

package com.example.schoolrun.OrderBackActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Entity.MyUser;
import com.example.schoolrun.R;
import com.example.schoolrun.Utils.IDCardValidate;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;

//待审核的用户详细信息
public class RootUserCheckDetailsActivity extends AppCompatActivity {

    private ImageButton returnucheklistbutton;
    private TextView tvrealname;
    private TextView tvuidentitycard;
    private Button ucheckbutton;

    private Intent intent;
    private String suid;
    private String Objectid;//bmob中默认的id值
    private MyUser myUser;

    private String uidcard;//身份证号码

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_user_checkuser_details);
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能

        returnucheklistbutton=findViewById(R.id.returnucheklistbutton);
        tvrealname=findViewById(R.id.tvrealname);
        tvuidentitycard=findViewById(R.id.tvuidentitycard);
        ucheckbutton=findViewById(R.id.ucheckbutton);

        intent=getIntent();//获取上一个界面的intent
        suid=intent.getStringExtra("uid");//获取上个界面传过来的任务tid
        myUser=new MyUser();

        BmobQuery<MyUser> bmobQuery=new BmobQuery<MyUser>();
        String bql = "select * from MyUser where uid = ?";
        bmobQuery.setSQL(bql);
        bmobQuery.setPreparedParams(new Object[]{Integer.parseInt(suid)});
        bmobQuery.doSQLQuery(new SQLQueryListener<MyUser>(){
            @Override
            public void done(BmobQueryResult<MyUser> bmobQueryResult, BmobException e) {
                if(e==null){
                    List<MyUser> list = (List<MyUser>) bmobQueryResult.getResults();//查询结果
                    Objectid=list.get(0).getObjectId();
                    myUser.setUid(list.get(0).getUid());
                    myUser.setAccount(list.get(0).getAccount());
                    myUser.setUpassword(list.get(0).getUpassword());
                    myUser.setUname(list.get(0).getUname());
                    myUser.setSex(list.get(0).getSex());
                    myUser.setQq(list.get(0).getQq());
                    myUser.setPhone(list.get(0).getPhone());
                    myUser.setIncome(list.get(0).getIncome());
                    myUser.setUrealname(list.get(0).getUrealname());
                    myUser.setUidentitycard(list.get(0).getUidentitycard());
                    myUser.setGoodappraisecount(list.get(0).getGoodappraisecount());
                    myUser.setBadappraisecount(list.get(0).getBadappraisecount());

                    uidcard=list.get(0).getUidentitycard();

                    tvrealname.setText(list.get(0).getUrealname());
                    tvuidentitycard.setText(list.get(0).getUidentitycard());

                }
            }
        });

        //验证当前的身份证是否有效
        ucheckbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //身份证有效，那么给予该用户接单者权限
                if (IDCardValidate.validate_effective(uidcard)==true){

                    bmobQuery.doSQLQuery(new SQLQueryListener<MyUser>(){
                        @Override
                        public void done(BmobQueryResult<MyUser> bmobQueryResult, BmobException e) {

                            myUser.setUreputation(0);//置0，表示已经具有接单者权限且已经经过申请
                            myUser.setUcheck(1);//具有接单者权限
                            myUser.setUreputation(6);

                            myUser.update(Objectid,new UpdateListener(){

                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(RootUserCheckDetailsActivity.this,
                                                "身份证信息有效，授予接单者权限",Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(RootUserCheckDetailsActivity.this,RootUserCheckActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                }
                            });

                        }
                    });

                }
                else {//身份证验证失败

                    bmobQuery.doSQLQuery(new SQLQueryListener<MyUser>(){
                        @Override
                        public void done(BmobQueryResult<MyUser> bmobQueryResult, BmobException e) {

                            myUser.setUreputation(0);//置0，表示用户能够重新进行申请
                            myUser.setUcheck(0);//没有接单者权限
                            myUser.setUreputation(6);

                            myUser.update(Objectid,new UpdateListener(){

                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(RootUserCheckDetailsActivity.this,
                                                "身份证信息错误！无法获得接单者权限，已通知用户",Toast.LENGTH_SHORT).show();
                                      Intent intent=new Intent(RootUserCheckDetailsActivity.this,RootUserCheckActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                }
                            });

                        }
                    });

                }

            }
        });

        //返回待审核用户列表
        returnucheklistbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RootUserCheckDetailsActivity.this,RootUserCheckActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

}

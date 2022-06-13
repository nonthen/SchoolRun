package com.example.schoolrun.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Entity.MyOrderRead;
import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.Entity.MyUser;
import com.example.schoolrun.LoginActivity;
import com.example.schoolrun.R;
import com.example.schoolrun.Utils.PayTypesDialog;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

//发布任务
public class ReleaseTask extends AppCompatActivity {

    private EditText etname;//任务标题
    private EditText etprice;//任务价格
    private EditText etphone;//联系人电话号码
    private EditText etdetails;//任务详情
    private EditText etmyadress;//任务起始地址
    private EditText ettargetadress;//任务目标地址
    private Button fabubutton;//发布按钮
    private Spinner kindsp;//任务类型下拉框
    private String kind;//类型名称
    private MyTask myTask;
    private MyOrderRead myOrderRead;

    private RadioButton tab1,tab2,tab3;//底部导航栏
    private PayTypesDialog payTypesDialog;//支付弹窗
    private int  sum;//任务总个数
    protected int uid;//当前发布人的uid

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.relese_task);//绑定发布任务布局
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能

        etname=findViewById(R.id.taskname);
        etprice=findViewById(R.id.taskprice);
        etphone=findViewById(R.id.taskphone);
        etdetails=findViewById(R.id.taskdetails);
        etmyadress=findViewById(R.id.taskmyadress);
        ettargetadress=findViewById(R.id.tasktargetaddress);
        tab1=findViewById(R.id.rb_home);
        tab2=findViewById(R.id.rb_task);
        tab3=findViewById(R.id.rb_me);
        payTypesDialog = new PayTypesDialog(ReleaseTask.this, R.style.pay_type_dialog);
        myTask=new MyTask();
        myOrderRead=new MyOrderRead();

        //下拉框功能实现
        String[] ctype = new String[]{"代取快递或外卖", "代排队", "代购", "代送","其他"};//下拉框数据
        kindsp =  findViewById(R.id.kindspinner);//下拉框样式
        //创建一个数组适配器,用来存放数据
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ctype);
        kindsp.setAdapter(adapter);//将adapter加入到下拉框中，完成下拉框绑定数据
        kindsp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kind=ctype[i];//kind表示当前选项的内容
                adapterView.setVisibility(View.VISIBLE);//显示当前选项
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //点击第一个按钮
        tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Intent button1 = new Intent(ReleaseTask.this,MainActivity.class);
                String uid = intent.getStringExtra("uid");
                button1.putExtra("uid", uid);
                startActivity(button1);
                finish();//释放资源
            }
        });

        //点击第二个按钮
        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Intent button2 = new Intent(ReleaseTask.this,ReleaseTask.class);
                String uid = intent.getStringExtra("uid");
                button2.putExtra("uid", uid);
                System.out.println("uid:" + uid);
                startActivity(button2);
                finish();//释放资源
            }
        });

        //点击第三个按钮
        tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Intent button3 = new Intent(ReleaseTask.this, TestMeAc.class);
                String uid = intent.getStringExtra("uid");
                button3.putExtra("uid", uid);
                System.out.println("uid:" + uid);
                startActivity(button3);
                finish();//释放资源
            }
        });


        //发布功能实现
        fabubutton=findViewById(R.id.fabubutton);
        fabubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etname.getText().toString().equals("")){
//                    System.out.println("名字："+etname.getText().toString());
                    Toast.makeText(getApplication(), "请填写任务标题" ,
                            Toast.LENGTH_SHORT).show();
                }
                else if(etprice.getText().toString().equals("")){
                    Toast.makeText(getApplication(), "请输入任务价格" ,
                            Toast.LENGTH_SHORT).show();
                }
                else if (etphone.getText().toString().equals("")){
                    Toast.makeText(getApplication(), "请输入联系方式" ,
                            Toast.LENGTH_SHORT).show();
                }
                else if(etdetails.getText().toString().equals("")){
                    Toast.makeText(getApplication(), "请填写任务详细信息" ,
                            Toast.LENGTH_SHORT).show();
                }
                else if (etmyadress.getText().toString().equals("")){
                    Toast.makeText(getApplication(), "请输入任务起始地址" ,
                            Toast.LENGTH_SHORT).show();
                }
                else if(ettargetadress.getText().toString().equals("")){
                    Toast.makeText(getApplication(), "请输入任务目标地址" ,
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    BmobQuery<MyTask> bmobQuery = new BmobQuery<>();
                    bmobQuery.findObjects(new FindListener<MyTask>(){
                        @Override
                        public void done(List<MyTask> list, BmobException e) {//统计任务总数
                            if (e == null){
                                int tidend=0;
//                                System.out.println("任务总数："+sum);

                                //插入一条任务数据,如果将插入数据放到外面，就会先执行插入数据，不会先查询，那么tid就永远为0
                                //获得最后一条数据的id值
                                for (int i=0;i<list.size();i++){
                                    tidend=list.get(i).getTid();
                                }
                                int sum=tidend+1;
//                                System.out.println("tidend="+tidend+1);

                                myTask.setTid(sum);
                                myOrderRead.setTid(sum);

                                //通过用户的账号查找到用户的uid
                                BmobQuery<MyUser> bmobQuery = new BmobQuery<>();
                                String bql = "select uid from MyUser where account = ? ";
                                bmobQuery.setSQL(bql);
                                bmobQuery.setPreparedParams(new Object[]{LoginActivity.uaccount});
                                bmobQuery.doSQLQuery(new SQLQueryListener<MyUser>() {
                                    @Override
                                    public void done(BmobQueryResult<MyUser> bmobQueryResult, BmobException e) {
                                        List<MyUser> list = (List<MyUser>) bmobQueryResult.getResults();
                                        if (list != null && list.size() > 0) {//存在一个匹配的用户
                                            uid = list.get(0).getUid();//获得当前用户的uid
                                            myTask.setUid(uid);
//                                            System.out.println("当前用户的uid:" + uid);
                                        } else {
//                                            System.out.println("不存在当前用户的uid");
                                        }

                                    }

                                });


                                myTask.setTname(etname.getText().toString());
                                myTask.setTkind(kind);
                                myTask.setTprice(Double.valueOf(etprice.getText().toString()));
                                myTask.setTphone(Long.valueOf(etphone.getText().toString()));
//                                System.out.println("Long.valueOf(etphone.getText().toString())="+Long.valueOf(etphone.getText().toString()));
                                myTask.setTdetail(etdetails.getText().toString());
                                myTask.setMyaddress(etmyadress.getText().toString());
                                myTask.setTargetaddress(ettargetadress.getText().toString());
                                myTask.setTcheck(0);//任务还没有被管理员审核
                                //此时数据还没有存入任务表

                                myOrderRead.setTname(etname.getText().toString());
                                myOrderRead.setTorderread(0);
                                //此时数据还没有存入接单者消息表

//                                System.out.println("弹出支付方式");
                                payTypesDialog.show();

                            }
                        }
                    });

                }

            }
        });

        //在取消dialog弹窗时添加一个事件监听
        payTypesDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                if (payTypesDialog.getPanduan()==1){//支付成功，刷新界面

                    Toast.makeText(ReleaseTask.this,"支付成功",Toast. LENGTH_LONG).show();
                    myTask.save(new SaveListener<String>() {//支付成功后才会将数据存入任务表
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (e == null) {
                                System.out.println("发布任务成功");
                            } else {
                                Log.e("BMOB", e.toString());
                                System.out.println("发布任务失败");
                            }
                        }
                    });

                    myOrderRead.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                System.out.println("MyOrederRead数据保存成功");
                            } else {
                                Log.e("BMOB", e.toString());
                                System.out.println("保存失败失败");
                            }
                        }
                    });

                    Intent intent=new Intent(ReleaseTask.this, ReleaseTask.class);
                    startActivity(intent);
                    finish();
                }
                else {//支付取消，维持当前状态
                    Toast.makeText(ReleaseTask.this,"支付取消",Toast. LENGTH_LONG).show();
                    payTypesDialog.cancel();
                }

            }
        });


    }




}
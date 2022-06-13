package com.example.schoolrun.Myself_Activity;

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

import com.example.schoolrun.Activity.MainActivity;
import com.example.schoolrun.Activity.ReleaseTask;
import com.example.schoolrun.Activity.TestMeAc;
import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.R;
import com.example.schoolrun.Utils.PayTypesDialog;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

//发布任务
public class Tasknomessage extends AppCompatActivity {

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

    private RadioButton tab1,tab2,tab3;//底部导航栏
    private PayTypesDialog payTypesDialog;//支付弹窗
    private int  sum;//任务总个数
    protected int uid;//当前发布人的uid
    private String sname,skind,sdetail,sargetaddress,smyaddress,objectid;
    private Number iphone,iprice,torder;

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
        payTypesDialog = new PayTypesDialog(Tasknomessage.this, R.style.pay_type_dialog);
        myTask=new MyTask();

        Intent intent = getIntent();
        String uid=intent.getStringExtra("uid");
        //String objectId=intent.getStringExtra("objectId");
        //System.out.println("uid:"+uid+"objectid:"+objectId);
        String tasktid=intent.getStringExtra("tid");
        Integer temptid;
        temptid=Double.valueOf(tasktid).intValue();//带小数点的字符串是不能直接转成整数的，应该先将它转成double类型再转int整数。

        //下拉框功能实现
        String[] ctype = new String[]{"代取快递或外卖", "代排队", "代购", "代送"};//下拉框数据
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
                Intent button1 = new Intent(Tasknomessage.this, MainActivity.class);
                startActivity(button1);
                finish();//释放资源
            }
        });

        //点击第二个按钮
        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent button2 = new Intent(Tasknomessage.this, ReleaseTask.class);
                startActivity(button2);
                finish();//释放资源
            }
        });

        //点击第三个按钮
        tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent button3 = new Intent(Tasknomessage.this, TestMeAc.class);
                startActivity(button3);
                finish();//释放资源
            }
        });



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
                        torder=list.get(i).getTorder();
                        objectid=list.get(i).getObjectId();
                        System.out.println("objectid:"+objectid);
                    }
                }
                else {
                    System.out.println("查询失败");
                }
                String phone =String.valueOf(iphone);
                String price =String.valueOf(iprice);
                etname.setText(sname);
                etdetails.setText(sdetail);
                ettargetadress.setText(sargetaddress);
                etmyadress.setText(smyaddress);
                etphone.setText(phone);
                etprice.setText(price);


        //发布功能实现
        fabubutton=findViewById(R.id.fabubutton);
        fabubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (etname.getText().toString().equals("")) {
//                    System.out.println("名字："+etname.getText().toString());
                        Toast.makeText(getApplication(), "请填写任务标题",
                                Toast.LENGTH_SHORT).show();
                    } else if (etprice.getText().toString().equals("")) {
                        Toast.makeText(getApplication(), "请输入任务价格",
                                Toast.LENGTH_SHORT).show();
                    } else if (etphone.getText().toString().equals("")) {
                        Toast.makeText(getApplication(), "请输入联系方式",
                                Toast.LENGTH_SHORT).show();
                    } else if (etdetails.getText().toString().equals("")) {
                        Toast.makeText(getApplication(), "请填写任务详细信息",
                                Toast.LENGTH_SHORT).show();
                    } else if (etmyadress.getText().toString().equals("")) {
                        Toast.makeText(getApplication(), "请输入任务起始地址",
                                Toast.LENGTH_SHORT).show();
                    } else if (ettargetadress.getText().toString().equals("")) {
                        Toast.makeText(getApplication(), "请输入任务目标地址",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        BmobQuery<MyTask> bmobQuery = new BmobQuery<>();
                        bmobQuery.findObjects(new FindListener<MyTask>() {
                            @Override
                            public void done(List<MyTask> list, BmobException e) {//统计任务总数
                                if (e == null) {
                                    sum = list.size();
//                                System.out.println("任务总数："+sum);

                                    //插入一条任务数据,如果将插入数据放到外面，就会先执行插入数据，不会先查询，那么tid就永远为0
                                    myTask.setTid(Integer.parseInt(tasktid));
                                    myTask.setUid(Integer.parseInt(uid));
                                    myTask.setTname(etname.getText().toString());
                                    myTask.setTkind(kind);
                                    myTask.setTprice(Double.valueOf(etprice.getText().toString()));
                                    myTask.setTphone(Integer.valueOf(etphone.getText().toString()));
                                    myTask.setTdetail(etdetails.getText().toString());
                                    myTask.setMyaddress(etmyadress.getText().toString());
                                    myTask.setTargetaddress(ettargetadress.getText().toString());
                                    myTask.setTcheck(0);//任务还没有被管理员审核
                                    myTask.update(objectid, new UpdateListener() {

                                        @Override
                                        public void done(BmobException e) {
                                            if(e==null){
                                                System.out.println("发布任务成功");
                                            }else{
                                                Log.e("BMOB", e.toString());
                                                System.out.println("发布任务失败");
                                            }
                                        }

                                    });
                                    Intent intent=new Intent(Tasknomessage.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            }
                        });

                    }
                }

            });
            }
        });


    }




}
package com.example.schoolrun.Activity;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.R;
import com.google.android.material.snackbar.Snackbar;

import cn.bmob.v3.Bmob;

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

    private RadioButton tab1,tab2,tab3;//底部导航栏
    private PayTypesDialog payTypesDialog;//支付弹窗

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
                Intent button1 = new Intent(ReleaseTask.this,MainActivity.class);
                startActivity(button1);
                finish();//释放资源
            }
        });

        //点击第二个按钮
        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent button2 = new Intent(ReleaseTask.this,ReleaseTask.class);
                startActivity(button2);
                finish();//释放资源
            }
        });

        //点击第三个按钮
        tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent button3 = new Intent(ReleaseTask.this,TestMeAc.class);
                startActivity(button3);
                finish();//释放资源
            }
        });


        //发布功能实现
        fabubutton=findViewById(R.id.fabubutton);
        fabubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击弹出支付方式
                System.out.println("弹出支付方式");
                payTypesDialog.show();

            }
        });

        //在取消dialog弹窗时添加一个事件监听
        payTypesDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                if (payTypesDialog.getPanduan()==1){//支付成功，刷新界面
                    Toast.makeText(ReleaseTask.this,"支付成功",Toast. LENGTH_LONG).show();
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
package com.example.schoolrun.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;


public class DetailedInfoActivity extends AppCompatActivity {
    private String temp7,temp6,temp2;
    private Spinner  sp;
    private String kind;
//    private Intent intent;
    private String tasktid;
    private Button button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_task_detailed_info);
        final Intent intent = getIntent();
        tasktid=intent.getStringExtra("tid");
        System.out.println("tasktid是："+tasktid);
        TextView textView1 =(TextView)findViewById(R.id.item_tname);
//        Spinner spinner =(Spinner) findViewById(R.id.item_tkind);
        TextView textView3 =(TextView)findViewById(R.id.item_tdetail);
        TextView textView4=(TextView)findViewById(R.id.item_targetaddress);
        TextView textView5=(TextView)findViewById(R.id.item_myaddress);
        TextView textView6=(TextView)findViewById(R.id.item_tphone);
        TextView textView7=(TextView)findViewById(R.id.item_tprice);
//        query.equalTo(列名, 值);		//等于
        BmobQuery<MyTask> bmobQuery = new BmobQuery<MyTask>();
        Bmob bmob=new Bmob();
//        bmob.equals("tid",tasktid);
//        var myTask= Bmob.Object.extend("myTask");
//        var query = new Bmob.Query(myTask);
//        bmobQuery.equals("tid",tasktid);
//        bmobQuery.findObjects(this, new FindListener<MyTask>() {
//            @Override
//            public void done(List<MyTask> list, BmobException e) {
//
//            }
//        })
//        bmobQuery.getObject(, new QueryListener<MyTask>(){
//            @Override
//            public void done(MyTask myTask, BmobException e) {
//                        temp7=String.valueOf(myTask.getTprice());
//        temp2=String.valueOf(myTask.getTkind());
//        temp6=String.valueOf(myTask.getTphone());
//        textView1.setText(myTask.getTname());
//        spinner.set
//        textView3.setText(myTask.getTdetail());
//        textView4.setText(myTask.getTargetaddress());
//        textView5.setText(myTask.getMyaddress());
//        textView6.setText(temp6);
//        textView7.setText(temp7);
//            }
//        });
//        String[] type = new String[]{"代购", "代排队", "代", "代"};
//        sp =  findViewById(R.id.item_tkind);//下拉框样式
//        //创建一个数组适配器,用来存放数据
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, type);
//        sp.setAdapter(adapter);//将adapter加入到下拉框中，完成下拉框绑定数据
//        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                kind=type[i];//kind表示当前选项的内容
//                adapterView.setVisibility(View.VISIBLE);//显示当前选项
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

//        TextView textView1 =(TextView)findViewById(R.id.item_tname);
//        Spinner spinner =(Spinner) findViewById(R.id.item_tkind);
//        TextView textView3 =(TextView)findViewById(R.id.item_tdetail);
//        TextView textView4=(TextView)findViewById(R.id.item_targetaddress);
//        TextView textView5=(TextView)findViewById(R.id.item_myaddress);
//        TextView textView6=(TextView)findViewById(R.id.item_tphone);
//        TextView textView7=(TextView)findViewById(R.id.item_tprice);
//        temp=(myTask.getTprice()).toString();失败
//        temp7=String.valueOf(myTask.getTprice());
//        temp2=String.valueOf(myTask.getTkind());
//        temp6=String.valueOf(myTask.getTphone());
//        textView1.setText(myTask.getTname());
//        spinner.set
//        textView3.setText(myTask.getTdetail());
//        textView4.setText(myTask.getTargetaddress());
//        textView5.setText(myTask.getMyaddress());
//        textView6.setText(temp6);
//        textView7.setText(temp7);
//        return view;
    }


}

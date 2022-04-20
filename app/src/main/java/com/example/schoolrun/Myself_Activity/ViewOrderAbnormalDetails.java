package com.example.schoolrun.Myself_Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.R;

import cn.bmob.v3.Bmob;

//异常订单信息详情
public class ViewOrderAbnormalDetails extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_order_abnormal_details);//绑定异常订单详细信息布局
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能




    }

}

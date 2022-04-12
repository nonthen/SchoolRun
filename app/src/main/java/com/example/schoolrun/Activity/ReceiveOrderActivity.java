package com.example.schoolrun.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.Entity.MyUser;
import com.example.schoolrun.LoginActivity;
import com.example.schoolrun.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

public class ReceiveOrderActivity extends AppCompatActivity {
    private String ordertid,ordertname,orderkind,orderdetail,orsargetaddress,ormyaddress,orphone,orprice,data,ortorder;
    public String Uaccount;
    private Button button;
    private int uid;
    private MyTask myTask;
    private MyUser myUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive_order);
        Intent intent = getIntent();
        Bundle bundle=intent.getExtras();
        ordertid=bundle.getString("bundle_tid");
        ordertname=bundle.getString("bundle_tname");
        orderkind=bundle.getString("bundle_tkind");
        orderdetail=bundle.getString("bundle_tdetail");
        orsargetaddress=bundle.getString("bundle_targetaddress");
        ormyaddress=bundle.getString("bundle_tmyaddress");
        orphone=bundle.getString("bundle_phone");
        orprice=bundle.getString("bundle_price");
        ortorder=bundle.getString("bundle_torder");
        data=bundle.getString("bundle_data");
        System.out.println("tasktid是："+ordertid+" ordertname是："+ordertname+"orderkind:"+orderkind+"orderdetail:"+orderdetail+"orsargetaddress:"+orsargetaddress+"ormyaddress:"+ormyaddress+"data:"+data);
        //通过用户的账号查找到用户的uid
        BmobQuery<MyUser> bmobQuery = new BmobQuery<>();
        myTask=new MyTask();
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
                    Uaccount=LoginActivity.uaccount;
                    System.out.println("当前用户的uid:" + uid+"Uaccount为"+Uaccount);
                } else {
                    System.out.println("不存在当前用户的uid");
                }

            }
        });
//通过tid找到uid

        TextView textView4=(TextView)findViewById(R.id.item_targetaddress);
//        TextView textView7=(TextView)findViewById(R.id.item_tprice);
        TextView textView6=(TextView)findViewById(R.id.item_tphone);
        TextView tViewUaccount=(TextView)findViewById(R.id.item_uaccount);
        tViewUaccount.setText(Uaccount);
        textView6.setText(orphone);
        textView4.setText(orsargetaddress);

//        button=findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(button,"这是测试",Snackbar.LENGTH_LONG).show();
//            }
//        });
    }
}

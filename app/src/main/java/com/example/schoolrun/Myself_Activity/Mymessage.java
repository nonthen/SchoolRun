package com.example.schoolrun.Myself_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Entity.MyUser;
import com.example.schoolrun.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

//前台查看个人信息功能实现
public class Mymessage extends AppCompatActivity{
    private String useruid, temptid;
    private Button button,back;
    private String uname, sex, qq, phone;
    private RadioGroup mRadioGroup;
    private RadioButton tab1,tab2,tab3;  //3个单选按钮
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_message);
        Intent intent = getIntent();
        useruid = intent.getStringExtra("uid");
        String objectId = intent.getStringExtra("objectId");
        Integer temptid;
        temptid = Double.valueOf(useruid).intValue();//带小数点的字符串是不能直接转成整数的，应该先将它转成double类型再转int整数。
        System.out.println("useruid是：" + useruid + "temptid是" + temptid+"objectId是" + objectId);
        TextView textView1 = (TextView) findViewById(R.id.showuname);
        TextView textView2 = (TextView) findViewById(R.id.showsex);
        TextView textView3 = (TextView) findViewById(R.id.showqq);
        TextView textView4 = (TextView) findViewById(R.id.showphone);
        button = findViewById(R.id.messagebutton);
        back = findViewById(R.id.fanhui);
        BmobQuery<MyUser> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("uid", temptid);
        bmobQuery.findObjects(new FindListener<MyUser>() {
            //            String sname,skind,sdetail,sargetaddress,smyaddress;
//            Number iphone,iprice,iph;
            @Override
            public void done(List<MyUser> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        uname = list.get(i).getUname();
                        sex = list.get(i).getSex();
                        qq = list.get(i).getQq();
                        phone = list.get(i).getPhone();
                        System.out.println("uname为" + uname + "phone为" + phone + "qq为" + qq);
                    }
                } else {
                    System.out.println("查询失败");
                }
                //sphone =String.valueOf(iphone);
                //sprice =String.valueOf(iprice);
                //storder =String.valueOf(torder);
                textView1.setText(uname);
                textView2.setText(sex);
                textView3.setText(qq);
                textView4.setText(phone);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {//跳转界面
            @Override
            public void onClick(View view) {
                Intent b = new Intent();
                b.setClass(Mymessage.this, Changemessage.class);
                b.putExtra("uid", useruid);
                b.putExtra("objectId", objectId);
                Mymessage.this.startActivity(b);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {//返回
            @Override
            public void onClick(View view) {
                Intent a = new Intent();
                String uid=intent.getStringExtra("uid");
                String objectId=intent.getStringExtra("objectId");
                System.out.println("id是："+uid+"objectId是："+objectId);
                a.putExtra("uid", uid);
                a.putExtra("objectId", objectId);
                startActivity(a);
                finish();
            }
        });
}

}


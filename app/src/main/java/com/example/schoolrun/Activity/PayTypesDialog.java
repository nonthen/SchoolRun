package com.example.schoolrun.Activity;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.schoolrun.LoginActivity;
import com.example.schoolrun.R;
import com.google.android.material.snackbar.Snackbar;

//底部弹出支付方式
public class PayTypesDialog extends Dialog {

    private ImageView mIvWeiChatPay;
    private ImageView mIvZhiFuBaoPay;
    private Button zhifubutton;
    private Button quxiaobutton;

    private boolean bwechat=true;//默认支付方式为微信
    private boolean bzhifubao=false;

    private int panduan;//判断当前是支付成功还是支付取消

    //dialog构造方法 实现时需要传上下文和一个dialog主题
    public PayTypesDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pay);

        //设置基本属性
        Window dialogWindow = getWindow();
        //设置在底部显示
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //设置宽度和手机持平
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        lp.y = 0;//设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);



        mIvWeiChatPay=findViewById(R.id.wechat);
        mIvZhiFuBaoPay=findViewById(R.id.zhifubao);

        mIvWeiChatPay.setOnClickListener(new View.OnClickListener(){//微信支付

            @Override
            public void onClick(View view) {

                if(bzhifubao==true){//此时选了支付宝，但切换到微信付款
                    bzhifubao=!bzhifubao;
                    bwechat=true;
                    mIvZhiFuBaoPay.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24);
                    mIvWeiChatPay.setImageResource(R.drawable.ic_baseline_radio_button_checked_24);
                }
            }
        });

        mIvZhiFuBaoPay.setOnClickListener(new View.OnClickListener() {//支付宝支付
            @Override
            public void onClick(View view) {

                if (bwechat==true){
                    bwechat=!bwechat;
                    bzhifubao=true;
                    mIvWeiChatPay.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24);
                    mIvZhiFuBaoPay.setImageResource(R.drawable.ic_baseline_radio_button_checked_24);
                }

            }
        });

        zhifubutton=findViewById(R.id.button);//模拟支付
        quxiaobutton=findViewById(R.id.button2);//取消支付

        zhifubutton.setOnClickListener(new View.OnClickListener() {//模拟支付
            @Override
            public void onClick(View view) {
                panduan=1;

                Snackbar.make(zhifubutton, "模拟支付成功", Snackbar.LENGTH_LONG).show();
                cancel();
            }
        });

        quxiaobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panduan=0;
                Snackbar.make(quxiaobutton, "取消支付成功", Snackbar.LENGTH_LONG).show();
                cancel();
            }
        });


    }

    public int getPanduan() {
        return panduan;
    }

    public void setPanduan(int panduan) {
        this.panduan = panduan;
    }



}

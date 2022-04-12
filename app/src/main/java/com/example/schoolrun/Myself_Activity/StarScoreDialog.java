package com.example.schoolrun.Myself_Activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;

import com.example.schoolrun.R;

//实现星星评分弹窗
public class StarScoreDialog extends Dialog {

    private RatingBar ratingBar;
    private Button submitBtn;
    private EditText appraisedetails;



    //dialog构造方法 实现时需要传上下文和一个dialog主题
    public StarScoreDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.star_appraise);

        ratingBar=findViewById(R.id.ratingBar);//星星评分
        appraisedetails=findViewById(R.id.detailappraise);//评分详情
        submitBtn=findViewById(R.id.appraiseendbutton);//评价完成按钮



    }


}

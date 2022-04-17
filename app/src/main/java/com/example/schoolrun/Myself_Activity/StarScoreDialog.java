package com.example.schoolrun.Myself_Activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;

import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;

//实现星星评分弹窗
public class StarScoreDialog extends Dialog {

    private RatingBar ratingBar;
    private Button submitBtn;
    private EditText appraisedetails;
    private String tasktid;//从任务完成列表传进来任务id
    private float ratingscore;//星星的分数
    private MyTask myTask;
    private String Objectid;//bmob中默认的id值

    //dialog构造方法 实现时需要传上下文和一个dialog主题
    public StarScoreDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_starappraise);
        Bmob.initialize(getContext(), "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能

        ratingBar=findViewById(R.id.ratingBar);//星星评分
        appraisedetails=findViewById(R.id.detailappraise);//评分详情
        submitBtn=findViewById(R.id.appraiseendbutton);//评价完成按钮
        myTask=new MyTask();


        //设置基本属性
        Window dialogWindow = getWindow();
        //设置在中间显示
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //设置宽度和手机持平
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
//        lp.y = 0;//设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);


        //星星评分添加监听
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Snackbar.make(ratingBar, "分数是"+rating+"星", Snackbar.LENGTH_LONG).show();
                ratingscore=rating;
            }
        });

        //完成评价
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobQuery<MyTask> bmobQuery = new BmobQuery<>();
                String bql="select * from MyTask where tid=?";
                bmobQuery.setSQL(bql);
                bmobQuery.setPreparedParams(new Object[]{Integer.valueOf(tasktid)});
//                System.out.println("完成评价按钮的tasktid:"+tasktid);
//                System.out.println("完成评价的分数ratingscore:"+ratingscore);
                bmobQuery.doSQLQuery(new SQLQueryListener<MyTask>() {
                    @Override
                    public void done(BmobQueryResult<MyTask> bmobQueryResult, BmobException e) {

                        if (e==null){
                            List<MyTask> list = (List<MyTask>) bmobQueryResult.getResults();
                            Objectid=list.get(0).getObjectId();//获取bmob中默认的ObjectId值
                            System.out.println("获取当前要评价的任务Objectid"+Objectid);
                            System.out.println("评价完成,分数是"+ratingscore+"任务id"+tasktid);

                            myTask.setUid(list.get(0).getUid());
                            myTask.setTid(list.get(0).getTid());
                            myTask.setId(list.get(0).getId());
                            myTask.setTname(list.get(0).getTname());
                            myTask.setTkind(list.get(0).getTkind());
                            myTask.setTphone(list.get(0).getTphone());
                            myTask.setTprice(list.get(0).getTprice());
                            myTask.setTdetail(list.get(0).getTdetail());
                            myTask.setMyaddress(list.get(0).getMyaddress());
                            myTask.setTargetaddress(list.get(0).getTargetaddress());
                            myTask.setTorder(1);
                            myTask.setTcheck(1);
                            myTask.setTfinish(1);

                            myTask.setTappraise(ratingscore);
                            myTask.setTappraisetext(appraisedetails.getText().toString());
                            myTask.setTappfinsh(1);
                            myTask.update(Objectid,new UpdateListener(){
                                @Override
                                public void done(BmobException e) {

                                    if (e == null) {
                                        Snackbar.make(submitBtn, "评价成功", Snackbar.LENGTH_LONG).show();
                                    }
                                    else {

                                        Snackbar.make(submitBtn, "评价失败", Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }

                    }

                });

                cancel();
            }
        });


    }

    public void SetTaskid(String taskid){
        System.out.println("评分弹窗对应的任务的taskid="+taskid);
        this.tasktid=taskid;
    }


}

package com.example.schoolrun.Myself_Activity;

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
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;

import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.Entity.MyUser;
import com.example.schoolrun.LoginActivity;
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
    private MyUser myUser;
    private String Objectid;//bmob中MyTask表中默认的id值
    private String UObjectid;//bmob中MyUser表中默认的id值

    private int id;//当前订单的接单者编号

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
        myUser=new MyUser();

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
                            id=list.get(0).getId();

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
                                        UpdateMyUser();
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


    //接单者信誉值，好评数/差评数的更新
    void UpdateMyUser(){

        //信誉值增减实现，初始6分，2分以下减0.1，4分以上加0.1，上限10分
        BmobQuery<MyUser> bmobQuery=new BmobQuery<MyUser>();
        String bql = "select * from MyUser where uid = ?";//当前接单者个人信息
        bmobQuery.setSQL(bql);
        System.out.println("接单者id="+id);
        bmobQuery.setPreparedParams(new Object[]{id});
        bmobQuery.doSQLQuery(new SQLQueryListener<MyUser>() {

            @Override
            public void done(BmobQueryResult<MyUser> bmobQueryResult, BmobException e) {

                List<MyUser> list = (List<MyUser>) bmobQueryResult.getResults();//查询结果

                UObjectid=list.get(0).getObjectId();
                myUser.setUid(list.get(0).getUid());
                myUser.setAccount(list.get(0).getAccount());
                myUser.setUpassword(list.get(0).getUpassword());
                myUser.setUname(list.get(0).getUname());
                myUser.setUcheck(list.get(0).getUcheck());

                if (ratingscore>=4){//评分4分以上为好评

                    myUser.setGoodappraisecount(list.get(0).getGoodappraisecount()+1);

                    if (list.get(0).getUreputation()<10){
                        float temp= (float) (list.get(0).getUreputation()+0.1);
                        myUser.setUreputation(temp);

                    }
                    else {
                        myUser.setUreputation(10);
                    }

                }
                else if (ratingscore<=2){//评分2分以下为差评
                    myUser.setBadappraisecount(list.get(0).getBadappraisecount()+1);

                    if (list.get(0).getUreputation()>0&&list.get(0).getUreputation()<=10){
                        float temp= (float) (list.get(0).getUreputation()-0.1);
                        myUser.setUreputation(temp);
                    }
                    else {
                        myUser.setUreputation(0);
                    }

                }
                else{
                    myUser.setGoodappraisecount(list.get(0).getGoodappraisecount());
                    myUser.setBadappraisecount(list.get(0).getBadappraisecount());
                }

                myUser.update(UObjectid,new UpdateListener() {
                    @Override
                    public void done(BmobException e) {

                        if (e==null){

                            System.out.println("该订单的接单者信誉值，好评数和差评数已经更改！");

                        }
                        else {

                            System.out.println("该订单的接单者信誉值，好评数和差评数更改失败！");
                        }

                    }
                });

            }


        });

    }

}

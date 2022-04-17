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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;

//订单完成or取消订单
public class OrderDialog extends Dialog {

    private ImageView dingdanfinsh;
    private ImageView dingdancancel;
    private EditText dingdancanceldetail;
    private Button cancelbutton;
    private Button finishbutton;
    private MyTask myTask;
    private String tasktid;//从订单进行中的列表传进来任务id
    private String Objectid;//bmob中默认的id值

    private boolean orderfinish=true;//默认任务成功完成
    private boolean orderfailure=false;


    public OrderDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_finishorder);

        dingdanfinsh=findViewById(R.id.dingdanfinish);
        dingdancancel=findViewById(R.id.dingdancancel);
        dingdancanceldetail=findViewById(R.id.dingdancanceldetail);
        cancelbutton=findViewById(R.id.cancelbutton);
        finishbutton=findViewById(R.id.finishbutton);
        myTask=new MyTask();

        //设置基本属性
        Window dialogWindow = getWindow();
        //设置在中间显示
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //设置宽度和手机持平
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);

        BmobQuery<MyTask> bmobQuery = new BmobQuery<>();
        String bql="select * from MyTask where tid=?";
        bmobQuery.setSQL(bql);
        bmobQuery.setPreparedParams(new Object[]{Integer.valueOf(tasktid)});
        bmobQuery.doSQLQuery(new SQLQueryListener<MyTask>() {

            @Override
            public void done(BmobQueryResult<MyTask> bmobQueryResult, BmobException e) {

                if (e==null){
                    List<MyTask> list = (List<MyTask>) bmobQueryResult.getResults();
                    Objectid=list.get(0).getObjectId();//获取bmob中默认的ObjectId值
                    System.out.println("获取当前要评价的任务Objectid"+Objectid);

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
                    myTask.setTcheck(1);
                    myTask.setTappfinsh(0);

                }

            }
        });

        //订单完成
        dingdanfinsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (orderfailure==true){
                    orderfailure=!orderfailure;
                    orderfinish=true;
                    dingdanfinsh.setImageResource(R.drawable.ic_baseline_radio_button_checked_24);
                    dingdancancel.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24);
                }

            }
        });

        //取消订单
        dingdancancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orderfinish==true){
                    orderfinish=!orderfinish;
                    orderfailure=true;
                    dingdanfinsh.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24);
                    dingdancancel.setImageResource(R.drawable.ic_baseline_radio_button_checked_24);
                }
            }
        });

        //点击确认按钮，这里分成两种情况：一种成功完成该进行中的订单，另一种取消当前订单
        finishbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orderfinish==true){//完成当前进行中订单
                    myTask.setTorder(1);
                    myTask.setTfinish(1);

                    myTask.update(Objectid,new UpdateListener(){
                        @Override
                        public void done(BmobException e) {

                            if (e == null) {
                                cancel();
                            }
                            else {

                            }
                        }
                    });

                }
                else {
                    System.out.println("orderfailure:"+orderfailure);
                    myTask.setTorder(2);//异常
                    myTask.setTfinish(0);
                    if (dingdancanceldetail.getText().toString().equals("")){//当前取消订单的原因
                        Snackbar.make(finishbutton, "请填写取消订单的原因", Snackbar.LENGTH_LONG).show();
                    }
                    else {
                        myTask.setTordercanceldetails(dingdancanceldetail.getText().toString());

                        myTask.update(Objectid,new UpdateListener(){
                            @Override
                            public void done(BmobException e) {

                                if (e == null) {
                                    cancel();
                                }
                                else {

                                }
                            }
                        });
                    }
                }

            }

        });


        //点击取消按钮，表示什么也没做
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

    }

    public void SetTaskid(String taskid){
//        System.out.println("订单是否完成的弹窗对应的任务的taskid="+taskid);
        this.tasktid=taskid;
    }

}

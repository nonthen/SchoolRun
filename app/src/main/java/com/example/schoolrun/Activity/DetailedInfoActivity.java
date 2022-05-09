package com.example.schoolrun.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.Entity.MyUser;
import com.example.schoolrun.LoginActivity;
import com.example.schoolrun.R;
import com.google.android.material.snackbar.Snackbar;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;

//任务详细
public class DetailedInfoActivity extends AppCompatActivity {
    private String sprice, sphone;
    private String tasktid;
    private Button btmain,btorder;
    private String sname,skind,sdetail,sargetaddress,smyaddress, UAccount;
    private Number iprice;
    private long iphone;
    private int  uid,ucheck,torder;
    private float ureputation;
    public static int tid;
    public static String ObjectId;
    private String data;
    public static Integer temptid;
    private MyTask myTask;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_task_detailed_info);
        final Intent intent = getIntent();
        tasktid=intent.getStringExtra("tid");
        temptid=Double.valueOf(tasktid).intValue();//带小数点的字符串是不能直接转成整数的，应该先将它转成double类型再转int整数。
        System.out.println("tasktid是："+tasktid+"temptid是"+temptid);
        TextView textView1 =(TextView)findViewById(R.id.item_tname);
        TextView textView2 =findViewById(R.id.item_tkind);
        TextView textView3 =(TextView)findViewById(R.id.item_tdetail);
        TextView textView4=(TextView)findViewById(R.id.item_targetaddress);
        TextView textView5=(TextView)findViewById(R.id.item_myaddress);
        TextView textView8=findViewById(R.id.item_time);
        TextView textView9=findViewById(R.id.item_uname);
        TextView textView6=(TextView)findViewById(R.id.item_tphone);
        TextView textView7=(TextView)findViewById(R.id.item_tprice);
        myTask=new MyTask();
        btorder=findViewById(R.id.button_receive_order);
        btmain=findViewById(R.id.button_main);
        BmobQuery<MyTask> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("tid",temptid);
        bmobQuery.findObjects(new FindListener<MyTask>() {
            @Override
            public void done(List<MyTask> list, BmobException e) {
                if (e==null){
                    for (int i=0;i<list.size();i++){
                        uid =list.get(i).getUid();
                        BmobQuery<MyUser> bmobQuery = new BmobQuery<>();
                        String bql1 = "select account from MyUser where uid = ? ";
                        bmobQuery.setSQL(bql1);
                        bmobQuery.setPreparedParams(new Object[]{uid});
                        bmobQuery.doSQLQuery(new SQLQueryListener<MyUser>() {
                            @Override
                            public void done(BmobQueryResult<MyUser> bmobQueryResult, BmobException e) {
                                List<MyUser> list = (List<MyUser>) bmobQueryResult.getResults();
                                if (list != null && list.size() > 0) {//存在一个匹配的用户
                                    UAccount = list.get(0).getAccount();//获得当前用户的uaccount
                                    textView9.setText(UAccount);
                                    System.out.println("此时当前用户UAccount为"+ UAccount);
                                } else {
                                    System.out.println("不存在当前用户的UAccount");
                                }

                            }
                        });
                        tid=list.get(i).getTid();
                        sname =list.get(i).getTname();
                        skind=list.get(i).getTkind();
                        sdetail=list.get(i).getTdetail();
                        sargetaddress=list.get(i).getTargetaddress();
                        smyaddress=list.get(i).getMyaddress();
                        iphone=list.get(i).getTphone();
                        iprice=0.9*list.get(i).getTprice().doubleValue();
                        data=list.get(i).getCreatedAt();
                        torder =list.get(i).getTorder();
                        System.out.println("sname为"+sname+"data为"+data+"data为"+data+"torder"+torder);
                    }
                }
                else {
                    System.out.println("查询失败");
                }
                sphone =String.valueOf(iphone);
                sprice =String.valueOf(iprice);
                textView1.setText(sname);
                textView2.setText(skind);
                textView3.setText(sdetail);
                textView4.setText(sargetaddress);
                textView5.setText(smyaddress);
                textView6.setText(sphone);
                textView7.setText(sprice);
                textView8.setText(data);
            }
        });

        //接单
        btorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ucheck= LoginActivity.ucheck;
                ureputation=LoginActivity.ureputation;
                System.out.println("ucheck为"+ ucheck);
                if (uid!= LoginActivity.uid) {
                    if (ucheck == 1) {//ucheck审核成配送员
                        if (ureputation >= 2) {//信誉值超过2才允许接单
                            AlertDialog alertDialog2 = new AlertDialog.Builder(DetailedInfoActivity.this)
                                    .setTitle("确认接单")
                                    .setMessage("您确定接此单吗？")
                                    .setIcon(R.mipmap.ic_launcher)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            System.out.println("测试接单修改torder时temptid为" + temptid);
                                            BmobQuery<MyTask> Query = new BmobQuery<>();
                                            String bql2 = "select * from MyTask where tid = ? ";
                                            Query.setSQL(bql2);
                                            Query.setPreparedParams(new Object[]{temptid});
                                            Query.doSQLQuery(new SQLQueryListener<MyTask>() {
                                                @Override
                                                public void done(BmobQueryResult<MyTask> bmobQueryResult, BmobException e) {
                                                    List<MyTask> list = (List<MyTask>) bmobQueryResult.getResults();
                                                    if (list != null && list.size() > 0) {//存在一个匹配的用户
                                                        ObjectId = list.get(0).getObjectId();
                                                        System.out.println("测试接单修改torder时ObjectId为" + ObjectId);
                                                        myTask.setTid(list.get(0).getTid());//先获取tid才能知道要改的是谁的torder
                                                        myTask.setUid(list.get(0).getUid());
                                                        System.out.println("LoginActivity.uid=" + LoginActivity.uid);
                                                        myTask.setId(LoginActivity.uid);//接单人变成当前登录用户
                                                        System.out.println("myTask.getId()=" + myTask.getId());
                                                        myTask.setTcheck(list.get(0).getTcheck());
                                                        myTask.setTorder(1);//接单成功torder=1
                                                        myTask.setTordercheck(list.get(0).getTordercheck());
                                                        myTask.setTfinish(list.get(0).getTfinish());
                                                        myTask.setTappfinsh(list.get(0).getTappfinsh());
                                                        myTask.setTasknotify(list.get(0).getTasknotify());

                                                        System.out.println("myTask.setTorder(1)为" + myTask.getTorder());
                                                        myTask.update(ObjectId, new UpdateListener() {
                                                            @Override
                                                            public void done(BmobException e) {
                                                                if (e == null) {
                                                                    Log.i("bmob", "更新成功");
                                                                } else {
                                                                    Log.i("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                                                                }
                                                            }
                                                        });
                                                    } else {
                                                        System.out.println("修改用户的torder失败");
                                                    }
                                                }
                                            });
                                            Toast.makeText(DetailedInfoActivity.this, "接单成功~快去配送吧~", Toast.LENGTH_SHORT).show();
                                        }
                                    })

                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    })
                                    .create();
                            alertDialog2.show();
                        } else {
                            Snackbar.make(btorder, "您的信誉值过低，已被禁止接单", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(btorder, "你还未申请成为接单员~请先去申请再来接单吧~", Snackbar.LENGTH_LONG).show();
                    }
                }else {
                    Snackbar.make(btorder, "不可以接自己的单哦~请重新选择", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        //返回主界面
        btmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedInfoActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}

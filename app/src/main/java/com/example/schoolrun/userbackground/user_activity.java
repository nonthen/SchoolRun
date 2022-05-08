package com.example.schoolrun.userbackground;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Activity.RootMainActivity;
import com.example.schoolrun.DataBackActivity.RootDataActivity;
import com.example.schoolrun.Entity.MyUser;
import com.example.schoolrun.OrderBackActivity.RootOrderActivity;
import com.example.schoolrun.OrderBackActivity.RootUserCheckActivity;
import com.example.schoolrun.R;
import com.example.schoolrun.Activity.NoscrollListView;
import com.example.schoolrun.Activity.SyncHorizontalScrollView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class user_activity extends AppCompatActivity implements View.OnClickListener {

    private Button UserCheckButton;//审核用户
    private Button databutton;//数据统计
    private RadioButton bt1, bt2, bt3;  //3个单选按钮

    private NoscrollListView mLeft;
    private LeftAdapter mLeftAdapter;

    private AlertDialog alertDialog3; //多选框
    private NotificationManager manager;
    /**
     * 记录选中item的下标
     */
    private List<Integer> checkedIndexList;
    /**
     * 保存每个item中的checkbox
     */
    private List<CheckBox> checkBoxList;

    private NoscrollListView mData;
    private DataAdapter mDataAdapter;

    private SyncHorizontalScrollView mHeaderHorizontal;
    private SyncHorizontalScrollView mDataHorizontal;

    private List<MyUser> myuserlist;
    List<MyUser> list1;
    List<MyUser> list2;
    public static String ObjectId, time;
    public static int RootUid;
    public static Integer temptid, temptid1;
    private MyUser myuser;
    private Button button;
    public static List<String> mapList;
    public static String temp;
    Button delete,changepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_background);
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能

        UserCheckButton=findViewById(R.id.UserCheckButton);
        databutton=findViewById(R.id.databutton);

        bt1 = findViewById(R.id.bt_task);
        bt2 = findViewById(R.id.bt_order);
        bt3 = findViewById(R.id.bt_user);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        mLeft = (NoscrollListView) findViewById(R.id.lv_left);//左侧列表
        mData = (NoscrollListView) findViewById(R.id.lv_data);//列表
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mDataHorizontal = findViewById(R.id.data_horizontal);//可滑动的表单
        mHeaderHorizontal = findViewById(R.id.header_horizontal);//整块顶部能滑动的

        mDataHorizontal.setScrollView(mHeaderHorizontal);
        mHeaderHorizontal.setScrollView(mDataHorizontal);

        myuserlist = new ArrayList<>();
        getMlist();
        delete = findViewById(R.id.delete);
        changepassword = findViewById(R.id.change_password);


    }


    void getMlist() {
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        BmobQuery<MyUser> bmobQuery = new BmobQuery<MyUser>();
        bmobQuery.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                if (e == null) {
                    int sum = 0, j = 0, sum1 = 0;
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println("list.get(i).getTcheck()为：" + list.get(i).getUid());
                        list1 = list.subList(i, i + 1);
                        list2.addAll(list1);
                        sum++;

                    }
                    myuserlist = list2;//获取未审核的任务

                    mLeftAdapter = new LeftAdapter();
                    mLeft.setAdapter(mLeftAdapter);

                    checkedIndexList = new ArrayList<Integer>();
                    checkBoxList = new ArrayList<CheckBox>();
                    mDataAdapter = new DataAdapter();
                    mData.setAdapter(mDataAdapter);
                }

            }
        });
    }

    class LeftAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return myuserlist.size();
        }

        @Override
        public Object getItem(int position) {
            return myuserlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(user_activity.this).inflate(R.layout.item_left, null);//item_left是tv_left的布局名称
                holder.tvLeft = (TextView) convertView.findViewById(R.id.tv_left);//tv_left是最左不能滑动的标签
                holder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
                checkBoxList.add(holder.checkbox);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvLeft.setText(String.valueOf(myuserlist.get(position).getUid()));
            holder.checkbox.setOnCheckedChangeListener(new CheckBoxListener(position));
            return convertView;
        }

        class ViewHolder {
            TextView tvLeft;
            CheckBox checkbox;
        }
    }

    class CheckBoxListener implements CompoundButton.OnCheckedChangeListener {

        int position;

        public CheckBoxListener(int position) {
            this.position = position;
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                checkedIndexList.add(position);
            } else {
                checkedIndexList.remove((Integer) position);
            }
        }
    }

    /**
     * 编辑按钮的点击事件
     */
    public void click_editButton(View v) {
        //将checkbox设置为可见
        for (int i = 0; i < checkBoxList.size(); i++) {
            checkBoxList.get(i).setVisibility(View.VISIBLE);
        }
    }

    /**
     * 修改用户密码的点击事件
     */
    public void click_succeedButton(View v) {
        String object = null,uname = null;
        final Intent intent = getIntent();
        intent.setClass(user_activity.this, Changepasswordbackground.class);
        for (int w = 0; w < 1; w++) {
            int z = (int) checkedIndexList.get(w);
            //需要强转为int,才会删除对应下标的数据,否则默认删除与括号中对象相同的数据
            System.out.println("System.out.println((int)checkedIndexList.get(i));" + (int) checkedIndexList.get(w));
            RootUid = myuserlist.get(z).getUid();
            object = myuserlist.get(z).getObjectId();
            uname = myuserlist.get(z).getUname();
            System.out.println("在RootMain中RootUid为;" + RootUid );
        }
        //更新数据源
        mLeftAdapter.notifyDataSetChanged();
        mDataAdapter.notifyDataSetChanged();
        //清空checkedIndexList,避免影响下一次删除
        checkedIndexList.clear();
        intent.putExtra("objectId", object);
        String id = Integer.toString(RootUid);
        intent.putExtra("uid", id);
        intent.putExtra("uname",uname);
        System.out.println("uid:"+RootUid+"objectId:"+object);
        startActivity(intent);

    }







    /**
     * 删除用户
     */
    public void click_deleteButton(View v) {
        MyUser p2 = new MyUser();

        for (int w = 0; w < checkedIndexList.size(); w++) {
            int z = (int) checkedIndexList.get(w);
            //需要强转为int,才会删除对应下标的数据,否则默认删除与括号中对象相同的数据
            System.out.println("System.out.println((int)checkedIndexList.get(i));" + (int) checkedIndexList.get(w));
            RootUid = myuserlist.get(z).getUid();
            time = myuserlist.get(z).getCreatedAt();
            System.out.println("在RootMain中RootUid为;" + RootUid );
            checkBoxList.remove(checkedIndexList.get(w));
            BmobQuery<MyUser> Query = new BmobQuery<>();
            String bql2 = "select * from MyUser where uid = ? ";
            Query.setSQL(bql2);
            Query.setPreparedParams(new Object[]{RootUid});
            Query.doSQLQuery(new SQLQueryListener<MyUser>() {
                @Override
                public void done(BmobQueryResult<MyUser> bmobQueryResult, BmobException e) {
                    List<MyUser> list = bmobQueryResult.getResults();
                    if (list != null && list.size() > 0) {//存在一个匹配的用户
                        String Objectd = list.get(0).getObjectId();
                        System.out.println("测试接单修改torder时ObjectId为" + Objectd);
                        p2.setObjectId(Objectd);
                        System.out.println("在RootMain中Uid为;" + p2.getUid());
                        p2.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Log.i("bmob", "删除成功");
                                    Snackbar.make( delete,"删除成功", Snackbar.LENGTH_LONG).show();
                                } else {
                                    Log.i("bmob", "删除失败");
                                }
                            }

                        });
                    } else {
                        System.out.println("--删除失败--");
                    }

                }
            });


        }
        //更新数据源
        mLeftAdapter.notifyDataSetChanged();
        mDataAdapter.notifyDataSetChanged();
        //清空checkedIndexList,避免影响下一次删除
        checkedIndexList.clear();
        finish();


    }





    class DataAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return myuserlist.size();
        }

        @Override
        public Object getItem(int position) {
            return myuserlist.get(position);}

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(user_activity.this).inflate(R.layout.user_data, null);//表格内容
                holder.uname = (TextView) convertView.findViewById(R.id.uname);
                holder.sex=(TextView) convertView.findViewById(R.id.sex);
                holder.qq=(TextView) convertView.findViewById(R.id.qq);
                holder.phone=(TextView) convertView.findViewById(R.id.phone);
                holder.income=(TextView) convertView.findViewById(R.id.income);
                holder.urealname=(TextView) convertView.findViewById(R.id.urealname);
                holder.ureputation=(TextView) convertView.findViewById(R.id.ureputation);
                holder.linContent = (LinearLayout) convertView.findViewById(R.id.lin_content);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            holder.uname.setText(myuserlist.get(position).getUname());
            holder.sex.setText(myuserlist.get(position).getSex());
            holder.qq.setText(myuserlist.get(position).getQq());
            holder.phone.setText(myuserlist.get(position).getPhone());
            holder.income.setText(myuserlist.get(position).getIncome());
            holder.ureputation.setText(String.valueOf(myuserlist.get(position).getUreputation()));
            holder.urealname.setText(String.valueOf(myuserlist.get(position).getUrealname()));
            return convertView;
        }

        class ViewHolder {
            TextView uname,sex,qq,phone,income,urealname,ureputation;
            LinearLayout linContent;
        }
    }


    /**取消按钮的点击事件*/
    public void click_cancelButton(View v){
        for(int i=0;i<checkBoxList.size();i++){
            //将已选的设置成未选状态
            checkBoxList.get(i).setChecked(false);
            //将checkbox设置为不可见
            checkBoxList.get(i).setVisibility(View.INVISIBLE);
        }
    }

    //跳转到审核用户
    public void click_checkUserButton(View v){
        UserCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent button = new Intent(user_activity.this, RootUserCheckActivity.class);
                startActivity(button);
                finish();

            }
        });
    }

    //跳转到数据统计界面
    public void click_datastaticButton(View v){

        databutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent button = new Intent(user_activity.this, RootDataActivity.class);
                startActivity(button);
                finish();
            }
        });
    }


   @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_task:
                Intent button1 = new Intent(this, RootMainActivity.class);//任务
                startActivity(button1);
                break;
            case R.id.bt_order:
                Intent button2 = new Intent(this, RootOrderActivity.class);
                startActivity(button2);
                break;
            case R.id.bt_user:
                Intent button3 = new Intent(this, user_activity.class);
                startActivity(button3);
                break;

        }
    }
}

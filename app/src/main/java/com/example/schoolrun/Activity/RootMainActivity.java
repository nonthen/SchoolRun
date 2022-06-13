package com.example.schoolrun.Activity;

import android.content.DialogInterface;
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

import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.OrderBackActivity.RootOrderActivity;
import com.example.schoolrun.R;
import com.example.schoolrun.userbackground.user_activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;
//审核任务
public class RootMainActivity extends AppCompatActivity implements View.OnClickListener{
    private RadioButton bt1,bt2,bt3;  //3个单选按钮

    private NoscrollListView mLeft;
    private LeftAdapter mLeftAdapter;

    private AlertDialog alertDialog3; //多选框
    /**记录选中item的下标*/
    private List<Integer> checkedIndexList;
    /**保存每个item中的checkbox*/
    private List<CheckBox> checkBoxList;

    private NoscrollListView mData;
    private DataAdapter mDataAdapter;

    private SyncHorizontalScrollView mHeaderHorizontal;
    private SyncHorizontalScrollView mDataHorizontal;

    private List<MyTask> mytasklist;
    List<MyTask> list1,list2,list3,list4;
    public static String ObjectId,time;
    public static int RootUid;
    public static  Integer temptid,temptid1;
    private MyTask myTask;
    private Button button;
    public static List<String> mapList;
    public static String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_root);
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能
        bt1=findViewById(R.id.bt_task);
        bt2=findViewById(R.id.bt_order);
        bt3=findViewById(R.id.bt_user);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        mLeft = (NoscrollListView) findViewById(R.id.lv_left);//左侧列表
        mData = (NoscrollListView) findViewById(R.id.lv_data);//列表
        button=findViewById(R.id.suceesufulbutton);

        mDataHorizontal = (SyncHorizontalScrollView) findViewById(R.id.data_horizontal);//可滑动的表单
        mHeaderHorizontal = (SyncHorizontalScrollView) findViewById(R.id.header_horizontal);//整块顶部能滑动的

        mDataHorizontal.setScrollView(mHeaderHorizontal);
        mHeaderHorizontal.setScrollView(mDataHorizontal);
        myTask=new MyTask();

        mytasklist=new ArrayList<>();
        getMlist();
        //获取任务审核成功的信息
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RootMainActivity.this, SuceesufulTask.class);
                startActivity(intent);
                finish();//释放资源
            }
        });
    }

    void getMlist(){
        list1=new ArrayList<>();
        list2=new ArrayList<>();
        BmobQuery<MyTask> bmobQuery=new BmobQuery<MyTask>();
        bmobQuery.findObjects(new FindListener<MyTask>(){
            @Override
            public void done(List<MyTask> list, BmobException e){
                if(e==null){
                    int sum=0,j=0,sum1=0;
                    for (int i=0;i<list.size();i++){
                        if (list.get(i).getTcheck()==0){//获取未审核任务
                            System.out.println("list.get(i).getTcheck()为："+list.get(i).getTcheck());
                            list1=list.subList(i,i+1);
                            list2.addAll(list1);
                            sum++;
                        }
                    }
                    System.out.println("list2.size()："+list2.size()+"sum为"+sum);
                    mytasklist=list2;//获取未审核的任务

                    mLeftAdapter= new LeftAdapter();
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
            return mytasklist.size();
        }

        @Override
        public Object getItem(int position) {
            return mytasklist.get(position);
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
                convertView = LayoutInflater.from(RootMainActivity.this).inflate(R.layout.item_left, null);//item_left是tv_left的布局名称
                holder.tvLeft = (TextView) convertView.findViewById(R.id.tv_left);//tv_left是最左不能滑动的标签
                holder.checkbox=(CheckBox) convertView.findViewById(R.id.checkbox);
                checkBoxList.add(holder.checkbox);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvLeft.setText(String.valueOf(mytasklist.get(position).getTid()));
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
        public CheckBoxListener(int position){
            this.position=position;
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b){
                checkedIndexList.add(position);
            }else {
                checkedIndexList.remove((Integer) position);
            }
        }
    }

    /**编辑按钮的点击事件*/
    public void click_editButton(View v){
        //将checkbox设置为可见
        for(int i=0;i<checkBoxList.size();i++){
            checkBoxList.get(i).setVisibility(View.VISIBLE);
        }
    }

    /**编辑审核成功的点击事件，即审核任务成功*/
    public void click_succeedButton(View v){
        checkedIndexList = sortCheckedIndexList(checkedIndexList);
        for(int i=0;i<checkedIndexList.size();i++){
            int z=(int)checkedIndexList.get(i);
            temptid1=mytasklist.get(z).getTid();
            mytasklist.remove((int)checkedIndexList.get(i));
            checkBoxList.remove(checkedIndexList.get(i));
            BmobQuery<MyTask> Query = new BmobQuery<>();
            String bql2 = "select * from MyTask where tid = ? ";
            Query.setSQL(bql2);
            Query.setPreparedParams(new Object[]{temptid1});
            Query.doSQLQuery(new SQLQueryListener<MyTask>() {
                @Override
                public void done(BmobQueryResult<MyTask> bmobQueryResult, BmobException e) {
                    List<MyTask> list = (List<MyTask>) bmobQueryResult.getResults();
                    if (list != null && list.size() > 0) {//存在一个匹配的用户
                        ObjectId= list.get(0).getObjectId();
                        System.out.println("测试接单修改tcheck时ObjectId为"+ObjectId);
                        myTask.setTid(list.get(0).getTid());//先获取tid才能知道要改的是谁的torder
                        myTask.setObjectId(ObjectId);
                        myTask.setUid(list.get(0).getUid());
                        myTask.setId(list.get(0).getId());
                        myTask.setTordercheck(list.get(0).getTordercheck());
                        myTask.setTfinish(list.get(0).getTfinish());
                        myTask.setTappfinsh(list.get(0).getTappfinsh());
                        myTask.setTasknotify(list.get(0).getTasknotify());
                        myTask.setTphone(list.get(0).getTphone());
                        myTask.setTcheck(1);//审核合格将tcheck从0改为1
                        System.out.println("ObjectId为"+ ObjectId);
                        myTask.update(ObjectId, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Log.i("bmob","修改任务的tcheck为1成功");
                                }else{
                                    Log.i("bmob","修改任务的tcheck为1失败："+e.getMessage()+","+e.getErrorCode());
                                }
                            }
                        });

                    } else {
                        System.out.println("--修改任务的tcheck为1失败--");
                    }

                }
            });
        }
        for(int i=0;i<checkBoxList.size();i++){
            //将已选的设置成未选状态
            checkBoxList.get(i).setChecked(false);
            //将checkbox设置为不可见
            checkBoxList.get(i).setVisibility(View.INVISIBLE);
        }
        //更新数据源
        mLeftAdapter.notifyDataSetChanged();
        mDataAdapter.notifyDataSetChanged();
        //清空checkedIndexList,避免影响下一次删除
        checkedIndexList.clear();

    }

    /**失败按钮的点击事件，即审核任务不成功*/
    public void click_deleteButton(View v){
        //先将checkedIndexList中的元素从大到小排列,否则可能会出现错位删除或下标溢出的错误
        checkedIndexList = sortCheckedIndexList(checkedIndexList);
        final String[] items = {"任务标题违规", "任务详情违规", "任务目标地址不合理", "任务本人地址不合理"};
        String[] items2 = {"任务标题违规", "任务详情违规", "任务目标地址不合理", "任务本人地址不合理"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("选择审核失败原因");
        /**
         *第一个参数:弹出框的消息集合，一般为字符串集合
         * 第二个参数：默认被选中的，布尔类数组
         * 第三个参数：勾选事件监听
         */
        alertBuilder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                if (isChecked){//通知发布任务用户,选择审核失败原因
//                    Toast.makeText(RootMainActivity.this, "选择" + items[i], Toast.LENGTH_SHORT).show();
                    items[i]="已选择";
                    System.out.println("items[i]的值为"+items);
//                    Toast.makeText(RootMainActivity.this, "选择变成" + items[i], Toast.LENGTH_SHORT).show();
                }else {
//                    Toast.makeText(RootMainActivity.this, "取消选择" + items[i], Toast.LENGTH_SHORT).show();
                    items[i]=items2[i];
//                    Toast.makeText(RootMainActivity.this, "取消选择变成" + items[i], Toast.LENGTH_SHORT).show();
                    System.out.println("取消选择后items[i]的值为"+items);
                }

            }
        });

        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mapList=new ArrayList<>();
                mapList.add(items[0]);
                mapList.add(items[1]);
                mapList.add(items[2]);
                mapList.add(items[3]);
                alertDialog3.dismiss();
                result =mapList.get(0)+" "+mapList.get(1)+" "+mapList.get(2)+" "+mapList.get(3);
                System.out.println("在RootMain中temp的值是"+ result);
                for(int w=0;w<checkedIndexList.size();w++){
                    int z=(int)checkedIndexList.get(w);
                    //需要强转为int,才会删除对应下标的数据,否则默认删除与括号中对象相同的数据
                    System.out.println("System.out.println((int)checkedIndexList.get(i));"+(int)checkedIndexList.get(w));
                    temptid=mytasklist.get(z).getTid();
                    RootUid=mytasklist.get(z).getUid();
                    time=mytasklist.get(z).getCreatedAt();
                    System.out.println("在RootMain中RootUid为;"+RootUid+"而temptid为"+temptid);
                    mytasklist.remove((int)checkedIndexList.get(w));
                    checkBoxList.remove(checkedIndexList.get(w));

                    BmobQuery<MyTask> Query = new BmobQuery<>();
                    String bql2 = "select * from MyTask where tid = ? ";
                    Query.setSQL(bql2);
                    Query.setPreparedParams(new Object[]{temptid});
                    Query.doSQLQuery(new SQLQueryListener<MyTask>() {
                        @Override
                        public void done(BmobQueryResult<MyTask> bmobQueryResult, BmobException e) {
                            List<MyTask> list = (List<MyTask>) bmobQueryResult.getResults();
                            if (list != null && list.size() > 0) {//存在一个匹配的用户
                                ObjectId= list.get(0).getObjectId();
                                System.out.println("测试接单修改torder时ObjectId为"+ObjectId);
                                myTask.setObjectId(ObjectId);
                                myTask.setTid(temptid);
                                myTask.setUid(RootUid);
                                System.out.println("在RootMain中RootUid为;"+myTask.getUid());
                                myTask.setId(list.get(0).getId());
                                myTask.setTcheck(2);//审核不合格将tcheck从0改为2
                                myTask.setTorder(list.get(0).getTorder());
                                myTask.setTphone(list.get(0).getTphone());
                                myTask.setTordercheck(list.get(0).getTordercheck());
                                myTask.setTfinish(list.get(0).getTfinish());
                                myTask.setTappfinsh(list.get(0).getTappfinsh());
                                myTask.setTasknotify(list.get(0).getTasknotify());
                                myTask.setTcheckerrordetails(result);//获取审核任务失败原因
                                System.out.println("在RootMain界面中myTask.getTcheckerrordetails()为"+ myTask.getTcheckerrordetails());
                                myTask.update(ObjectId, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            Log.i("bmob","修改任务的tcheck为2成功");
                                        }else{
                                            Log.i("bmob","修改任务的tcheck为2失败："+e.getMessage()+","+e.getErrorCode());
                                        }
                                    }
                                });
                            } else {
                                System.out.println("--修改任务的tcheck为2失败--");
                            }
                        }
                    });
                }
                for(int j=0;j<checkBoxList.size();j++){
                    //将已选的设置成未选状态
                    checkBoxList.get(j).setChecked(false);
                    //将checkbox设置为不可见
                    checkBoxList.get(j).setVisibility(View.INVISIBLE);
                }
                //更新数据源
                mLeftAdapter.notifyDataSetChanged();
                mDataAdapter.notifyDataSetChanged();
                //清空checkedIndexList,避免影响下一次删除
                checkedIndexList.clear();
            }
        });

        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog3.dismiss();
            }
        });
        alertDialog3 = alertBuilder.create();
        alertDialog3.show();
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

    /**对checkedIndexList中的数据进行从大到小排序*/
    public List<Integer> sortCheckedIndexList(List<Integer> list){
        int[] ass = new int[list.size()];//辅助数组
        for(int i=0;i<list.size();i++){
            ass[i] = list.get(i);
        }
        Arrays.sort(ass);
        list.clear();
        for(int i=ass.length-1;i>=0;i--){
            list.add(ass[i]);
        }
        return list;
    }

    class DataAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mytasklist.size();
        }

        @Override
        public Object getItem(int position) {
            return mytasklist.get(position);}

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(RootMainActivity.this).inflate(R.layout.item_data, null);//表格内容
                holder.tvData = (TextView) convertView.findViewById(R.id.tname);
                holder.tkind=(TextView) convertView.findViewById(R.id.tkind);
                holder.tdetail=(TextView) convertView.findViewById(R.id.tdetail);
                holder.targetaddress=(TextView) convertView.findViewById(R.id.targetaddress);
                holder.myaddress=(TextView) convertView.findViewById(R.id.myaddress);
                holder.uid=(TextView) convertView.findViewById(R.id.uid);
                holder.tprice=(TextView) convertView.findViewById(R.id.tprice);
                holder.tphone=(TextView) convertView.findViewById(R.id.tphone);
                holder.linContent = (LinearLayout) convertView.findViewById(R.id.lin_content);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvData.setText(mytasklist.get(position).getTname());
            holder.tkind.setText(mytasklist.get(position).getTkind());
            holder.tdetail.setText(mytasklist.get(position).getTdetail());
            holder.targetaddress.setText(mytasklist.get(position).getTargetaddress());
            holder.myaddress.setText(mytasklist.get(position).getMyaddress());
            holder.uid.setText(String.valueOf(mytasklist.get(position).getUid()));
            holder.tprice.setText(String.valueOf(mytasklist.get(position).getTprice()));
            holder.tphone.setText(String.valueOf(mytasklist.get(position).getTphone()));
            return convertView;
        }

        class ViewHolder {
            TextView tvData,uid,tprice,tphone;
            TextView tkind;
            TextView myaddress;
            TextView tdetail;
            TextView targetaddress;
            LinearLayout linContent;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_task:
                Intent button1 = new Intent(this,RootMainActivity.class);//任务
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

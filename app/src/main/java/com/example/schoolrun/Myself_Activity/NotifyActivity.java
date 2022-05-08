package com.example.schoolrun.Myself_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Activity.TestMeAc;
import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.LoginActivity;
import com.example.schoolrun.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;
//获取系统通知消息
public class NotifyActivity  extends AppCompatActivity {


    private RadioButton ordernotify;
    private MyTask myTask;
    public static String ObjectId;
    private String temp0,temp1,temp2,temp3;
    public static String result;
    private String errorTcheck,stemptid;
    private int temptid;
    private ImageButton returnMe;
    List<Map<String,String>> mapList;
    /**记录选中item的下标*/
    private List<Integer> checkedIndexList;
    /**保存每个item中的checkbox*/
    private List<CheckBox> checkBoxList;

    private MyNotifyAdapter myNotifyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_notify);
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能
        returnMe=findViewById(R.id.returnMe);
        ordernotify=findViewById(R.id.ordernotify);
        myTask=new MyTask();
        BmobQuery<MyTask> Query = new BmobQuery<>();
        String bql = "select * from MyTask where tcheck=2 and tasknotify=0 ";
        Query.setSQL(bql);
        Query.doSQLQuery(new SQLQueryListener<MyTask>() {
            @Override
            public void done(BmobQueryResult<MyTask> bmobQueryResult, BmobException e) {
                List<MyTask> list = (List<MyTask>) bmobQueryResult.getResults();
                Map<String, String> mHashMap;
                mapList=new ArrayList<>();
                int i=0;
                int j=0;
                for (MyTask myTask:list){
                    result =" ";
                    mHashMap=new HashMap<>();
                    if (list.get(j).getUid()== LoginActivity.uid&&list.get(j).getTasknotify()==0){
                        String[] items = {"任务标题违规", "任务详情违规", "任务目标地址不合理", "任务本人地址不合理"};
                        System.out.println("此时的tid为为="+myTask.getTid()+"，uid="+myTask.getUid());
                        errorTcheck =list.get(j).getTcheckerrordetails();
                        System.out.println("错误消息的值为errorTcheck="+errorTcheck);
                        String[] split = errorTcheck.split(" ");
                        temp0 = temp1 = temp2 = temp3 = " ";
                        if (!split[0].equals(items[0])) {
                            temp0 =items[0];
                        }
                        if (!split[1].equals(items[1])) {

                            temp1 = items[1];
                        }
                        if (!split[2].equals(items[2])) {
                            temp2 =items[2];
                        }
                        if (!split[3].equals(items[3])) {
                            temp3 = items[3];
                        }
                        result = "原因：" + temp0 + temp1 + temp2 + temp3;
                        mHashMap.put("tid",String.valueOf(myTask.getTid()));
                        mHashMap.put("tname",myTask.getTname());
                        mHashMap.put("tcheckerrordetails", result);
                        mapList.add(mHashMap);
                    }else {
                        System.out.println("不是审核任务为2的用户");
                    }
                    j++;
                }
                ListView listView = findViewById(R.id.task_notify_listView);
                myNotifyAdapter=new MyNotifyAdapter();
                listView.setAdapter(myNotifyAdapter);
                checkedIndexList = new ArrayList<Integer>();
                checkBoxList = new ArrayList<CheckBox>();
            }
        });

        //返回个人信息界面
        returnMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotifyActivity.this, TestMeAc.class);
                startActivity(intent);
                finish();
            }
        });

        //查看订单消息通知
        ordernotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent button = new Intent(NotifyActivity.this, MyOrderNotificitionActivity.class);
                startActivity(button);
                finish();//释放资源
            }
        });

    }

    class MyNotifyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mapList.size();
        }

        @Override
        public Object getItem(int position) {
            return mapList.get(position);
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
                convertView = LayoutInflater.from(NotifyActivity.this).inflate(R.layout.my_notify_item_info, null);
                holder.notifyTname = (TextView) convertView.findViewById(R.id.item_noyify_tname);
                holder.item_noyify_tchek=(TextView) convertView.findViewById(R.id.item_noyify_tchek);
                holder.checkbox1=(CheckBox) convertView.findViewById(R.id.checkbox1);
                checkBoxList.add(holder.checkbox1);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.notifyTname.setText(String.valueOf(mapList.get(position).get("tname")));
            holder.item_noyify_tchek.setText(String.valueOf(mapList.get(position).get("tcheckerrordetails")));
            holder.checkbox1.setOnCheckedChangeListener(new CheckBox1Listener(position));
            return convertView;
        }

        class ViewHolder {
            TextView notifyTname,item_noyify_tchek;
            CheckBox checkbox1;
        }
    }

    class CheckBox1Listener implements CompoundButton.OnCheckedChangeListener {
        int position;
        public CheckBox1Listener(int position){
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
    public void click_editButton1(View v){
        //将checkbox设置为可见
        for(int i=0;i<checkBoxList.size();i++){
            checkBoxList.get(i).setVisibility(View.VISIBLE);
        }
    }

    /**删除按钮的点击事件，即删除任务通知消息*/
    public void click_deleteButton1(View v){
        //先将checkedIndexList中的元素从大到小排列,否则可能会出现错位删除或下标溢出的错误
        checkedIndexList = sortCheckedIndexList(checkedIndexList);
        for(int i=0;i<checkedIndexList.size();i++) {
            int j = (int) checkedIndexList.get(i);
            stemptid=mapList.get(j).get("tid");
            temptid=Integer.parseInt(stemptid);
            mapList.remove((int) checkedIndexList.get(i));
            checkBoxList.remove(checkedIndexList.get(i));
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
                        System.out.println("测试接单修改tasknotify时ObjectId为"+ObjectId);
                        myTask.setTid(list.get(0).getTid());//先获取tid才能知道要改的是谁的torder
                        myTask.setUid(list.get(0).getUid());
                        myTask.setId(list.get(0).getId());
                        myTask.setTordercheck(list.get(0).getTordercheck());
                        myTask.setTorder(list.get(0).getTorder());
                        myTask.setTfinish(list.get(0).getTfinish());
                        myTask.setTappfinsh(list.get(0).getTappfinsh());
                        myTask.setObjectId(ObjectId);
                        myTask.setTasknotify(1);//将tasknotify从0改为1
                        System.out.println("ObjectId为"+ ObjectId);
                        myTask.update(ObjectId, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Log.i("bmob","修改任务的tasknotify为1成功");
                                }else{
                                    Log.i("bmob","修改任务的tasknotify为1失败："+e.getMessage()+","+e.getErrorCode());
                                }
                            }
                        });

                    } else {
                        System.out.println("--修改任务的tasknotify为1失败--");
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
        myNotifyAdapter.notifyDataSetChanged();
        //清空checkedIndexList,避免影响下一次删除
        checkedIndexList.clear();
    }

    /**取消按钮的点击事件*/
    public void click_cancelButton1(View v){
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
}



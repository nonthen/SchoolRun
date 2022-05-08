package com.example.schoolrun.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
//获取任务审核发布成功信息
public class SuceesufulTask extends AppCompatActivity {
    private NoscrollListView mLeft;
    private LeftAdapter mLeftAdapter;

    private NoscrollListView mData;
    private DataAdapter mDataAdapter;

    private SyncHorizontalScrollView mHeaderHorizontal;
    private SyncHorizontalScrollView mDataHorizontal;

    private ImageButton returnRoot;
    private List<MyTask> mytasklist;
    List<MyTask> list1,list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_table);
        returnRoot=(ImageButton) findViewById(R.id.returnRootBt);
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能
        mLeft = (NoscrollListView) findViewById(R.id.lv_left);//左侧列表
        mData = (NoscrollListView) findViewById(R.id.lv_data);//列表

        mDataHorizontal = (SyncHorizontalScrollView) findViewById(R.id.data_horizontal);//可滑动的表单
        mHeaderHorizontal = (SyncHorizontalScrollView) findViewById(R.id.header_horizontal);//整块顶部能滑动的
        mDataHorizontal.setScrollView(mHeaderHorizontal);
        mHeaderHorizontal.setScrollView(mDataHorizontal);

        mytasklist=new ArrayList<>();
        getMlist();
        returnRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuceesufulTask.this,RootMainActivity.class);
                startActivity(intent);
                finish();
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
                        if (list.get(i).getTcheck()==1){//获取审核成功任务
                            System.out.println("list.get(i).getTcheck()为："+list.get(i).getTcheck());
                            list1=list.subList(i,i+1);
                            list2.addAll(list1);
                            sum++;
                        }
                        else{
                            System.out.println("审核成功列表异常~");
                        }
                    }
                    System.out.println("list2.size()："+list2.size()+"sum为"+sum);
                    mytasklist=list2;//获取审核成功的任务
                    mLeftAdapter= new LeftAdapter();
                    mLeft.setAdapter(mLeftAdapter);

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
                convertView = LayoutInflater.from(SuceesufulTask.this).inflate(R.layout.item_left, null);//item_left是tv_left的布局名称
                holder.tvLeft = (TextView) convertView.findViewById(R.id.tv_left);//tv_left是最左不能滑动的标签
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvLeft.setText(String.valueOf(mytasklist.get(position).getTid()));

            return convertView;
        }

        class ViewHolder {
            TextView tvLeft;
        }
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
                convertView = LayoutInflater.from(SuceesufulTask.this).inflate(R.layout.item_data, null);//表格内容
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
            TextView tvData,uid,id,tprice,tphone;
            TextView tkind;
            TextView myaddress;
            TextView tdetail;
            TextView targetaddress;
            LinearLayout linContent;
        }
    }


}

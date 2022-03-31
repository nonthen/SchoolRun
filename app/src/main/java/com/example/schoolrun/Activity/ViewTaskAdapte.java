package com.example.schoolrun.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.R;

import java.util.List;
//任务主页配适器
public class ViewTaskAdapte extends ArrayAdapter<MyTask> {

    private int resourceId;
    private String temp;
    public ViewTaskAdapte(Context context, int resource, List<MyTask> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        MyTask myTask=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView textView1 =view.findViewById(R.id.item_tname);
        TextView textView2 =view.findViewById(R.id.item_targetaddress);
        TextView textView3 =view.findViewById(R.id.item_tprice);
//        temp=(myTask.getTprice()).toString();失败
        temp=String.valueOf(myTask.getTprice());
        textView1.setText(myTask.getTname());
        textView2.setText(myTask.getTargetaddress());
        textView3.setText(temp);
        return view;

    }
}

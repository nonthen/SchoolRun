package com.example.schoolrun.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.schoolrun.Entity.MyTask;
import com.example.schoolrun.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private RadioButton tab1,tab2,tab3;  //3个单选按钮
    private List<View> mViews;   //存放视图

    private void initView() {
        //初始化控件
        mViewPager=findViewById(R.id.viewpager);
        mRadioGroup=findViewById(R.id.rg_tab);
        tab1=findViewById(R.id.rb_task);
        tab2=findViewById(R.id.rb_home);
        tab3=findViewById(R.id.rb_me);

        mViews=new ArrayList<View>();//加载，添加视图view_task
        mViews.add(LayoutInflater.from(this).inflate(R.layout.view_task,null));
        mViews.add(LayoutInflater.from(this).inflate(R.layout.relese_task,null));
//        mViews.add(LayoutInflater.from(this).inflate(R.layout.find,null));
        mViews.add(LayoutInflater.from(this).inflate(R.layout.me,null));

        mViewPager.setAdapter(new MyViewPagerAdapter());//设置一个适配器
        //对viewpager监听，让分页和底部图标保持一起滑动
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override   //让viewpager滑动的时候，下面的图标跟着变动
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tab1.setChecked(false);
                        tab2.setChecked(true);
                        tab3.setChecked(false);

                        break;
                    case 1:
                        tab1.setChecked(true);
                        tab2.setChecked(false);
                        tab3.setChecked(false);

                        break;

                    case 2:
                        tab1.setChecked(false);
                        tab2.setChecked(false);
                        tab3.setChecked(true);

                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //ViewPager适配器
    private class MyViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mViews.size();
        }


        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(mViews.get(position));
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(mViews.get(position));
            return mViews.get(position);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Bmob.initialize(this, "ceb483ffe9b2098bc90776ca5d0415b4");//初始化BmobSDk功能
//这里获取了任务主页
        BmobQuery<MyTask> bmobQuery=new BmobQuery<MyTask>();
        bmobQuery.findObjects(new FindListener<MyTask>() {
            @Override
            public void done(List<MyTask> list, BmobException e) {
                if(e==null){
                    Log.d("path","查询成功");
                    ViewTaskAdapte viewTaskAdapte=new ViewTaskAdapte(MainActivity.this,R.layout.view_task_item_info,list);
                    ListView listView=findViewById(R.id.listView);
                    listView.setAdapter(viewTaskAdapte);
                }
                else {
                    Log.d("path","查询不成功");
                }
            }
        });

        initView();//初始化数据
        //对单选按钮进行监听，选中、未选中
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_task://发布任务
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_home://首页
                        mViewPager.setCurrentItem(0);
                        break;


                    case R.id.rb_me://个人信息
                        mViewPager.setCurrentItem(2);
                        break;
                }
            }
        });

    }



}

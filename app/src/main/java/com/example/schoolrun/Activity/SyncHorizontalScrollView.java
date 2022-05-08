package com.example.schoolrun.Activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
//自定义实现一个水平滚动控件HorizontalScrollView
public class SyncHorizontalScrollView extends HorizontalScrollView {
    private View mView;

    public SyncHorizontalScrollView(Context context) {
        super(context);
    }

    public SyncHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SyncHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mView != null) {
            mView.scrollTo(l, t);
        }
    }

    public void setScrollView(View view) {
        mView = view;
    }
}

package com.example.schoolrun.userbackground;

import android.content.ClipData;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

//自定义实现ListView让其能跟着ScrollView滚动
public class NoscrollListView extends ListView {

    private ClipData.Item[] elementData;
    public NoscrollListView(Context context) {
        super(context);
    }

    public NoscrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoscrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {// MeasureSpec.AT_MOST
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}

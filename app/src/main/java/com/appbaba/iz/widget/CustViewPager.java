package com.appbaba.iz.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ruby on 2016/5/26.
 */
public class CustViewPager extends ViewPager {
    public CustViewPager(Context context) {
        super(context);
    }

    public CustViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    int maxHeight = 0;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        for(int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if(h>maxHeight)
            {
                maxHeight = h;
                break;
            }
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.EXACTLY);


        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}

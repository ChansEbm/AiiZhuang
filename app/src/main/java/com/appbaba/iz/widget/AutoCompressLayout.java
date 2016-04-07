package com.appbaba.iz.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by ChanZeeBm on 2016/4/5.
 */
public class AutoCompressLayout extends ViewGroup {

    public AutoCompressLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        MarginLayoutParams marginLayoutParams = new MarginLayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        setLayoutParams(marginLayoutParams);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}

package com.appbaba.iz.widget.ScrollView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by ruby on 2016/5/17.
 */
public class SlowScrollView extends ScrollView {
    public SlowScrollView(Context context) {
        super(context);
    }

    public SlowScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlowScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
    }
}

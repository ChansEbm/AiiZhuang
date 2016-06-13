package com.appbaba.platform.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.appbaba.platform.R;
import com.appbaba.platform.method.MethodConfig;

/**
 * Created by ruby on 2016/6/12.
 */
public class IconImageView extends ImageView {
    int widthPx = 0;
    public IconImageView(Context context) {
        super(context);
        this.setScaleType(ScaleType.FIT_XY);
    }

    public IconImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IconImageView);
        widthPx = a.getInteger(R.styleable.IconImageView_widthPx,0);
        this.setScaleType(ScaleType.FIT_XY);
    }

    public IconImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IconImageView);
        widthPx = a.getInteger(R.styleable.IconImageView_widthPx,0);
        this.setScaleType(ScaleType.FIT_XY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(MethodConfig.metrics==null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
        else
        {
            if(widthPx==0)
            {
                widthPx = 66;
            }
            int value = MethodConfig.metrics.widthPixels*widthPx/750;
            Log.e("sdf","================================="+value);
            setMeasuredDimension(value,value);
        }
    }
}

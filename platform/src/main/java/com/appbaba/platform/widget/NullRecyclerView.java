package com.appbaba.platform.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.appbaba.platform.R;

/**
 * Created by ruby on 2016/6/6.
 */
public class NullRecyclerView extends RecyclerView {
    public NullRecyclerView(Context context) {
        super(context);
    }

    public NullRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NullRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        if(getAdapter().getItemCount()==0) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_error);
            c.drawBitmap(bitmap, getWidth() / 2 - bitmap.getWidth() / 2, 100, new Paint());
            bitmap.recycle();
        }
    }
}

package com.appbaba.platform.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.appbaba.platform.R;


/**
 * Created by Administrator on 2016/4/12.
 * LoadingView
 */
public class LoadingView extends View implements Runnable {

    private Bitmap bmFill;
    private Bitmap bmEmpty;
    private int eachInch;
    private int inch;
    private int bw;
    private int bh;
    private Handler handler = new Handler();
    private Paint paint;

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bmFill = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_loading);
        bmEmpty = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_loading_nor);
        bw = bmFill.getWidth();
        bh = bmFill.getHeight();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        eachInch = bh / 30;//分成30份
        inch = eachInch;
        setMeasuredDimension(bw, bh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect eDst = new Rect(0, 0, bw, bh);
        canvas.drawBitmap(bmEmpty, null, eDst, paint);
        Rect src = new Rect(0, bh - inch, bw, bh);
        Rect dst = new Rect(src);
        canvas.drawBitmap(bmFill, src, dst, paint);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        handler.post(this);
    }

    @Override
    public void run() {
        inch += eachInch;
        if (inch >= bh) {
            inch = bh;
        }
        postInvalidate();
        if (inch >= bh)
            inch = eachInch;
        handler.postDelayed(this, 60);//每隔60ms画多1/30
    }

    public void cancel() {
        handler.removeCallbacks(this);
    }

    public void start() {
        handler.post(this);
    }
}

package com.appbaba.platform.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.appbaba.platform.R;

/**
 * Created by ruby on 2016/5/13.
 */
public class MyTextView extends TextView {
    private  int mm = 0;
    private String text="";

    public MyTextView(Context context) {
        super(context);
        xformode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        init(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        xformode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GradientTextView);
        text = a.getString(R.styleable.GradientTextView_text);
        init(context);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GradientTextView);
        text = a.getString(R.styleable.GradientTextView_text);
        xformode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        init(context);
    }

    float scale=0;
    public void init(Context context)
    {
        Resources resources = context.getResources();
         scale = resources.getDisplayMetrics().density;
    }


    public void setMM(int x) {
        this.mm = x;
    }

    Paint mPaint;
    private float mTextHeight;
    private float mTextWidth;

    private PorterDuffXfermode xformode;
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        Bitmap bitmap = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvasTmp = new Canvas(bitmap);
        canvasTmp.drawColor(Color.TRANSPARENT);
        // new antialised Paint
        Paint paint = new Paint();
        // text color - #3D3D3D
        paint.setColor(Color.rgb(61, 61, 61));
        // text size in pixels
        paint.setTextSize(getTextSize());
        // text shadow
        mPaint = paint;
        mPaint.setColor(Color.BLACK);
        // draw text to the Canvas center
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        float x = (bitmap.getWidth() - bounds.width())/2;
        float y = (bitmap.getHeight() + bounds.height())/2;

        canvasTmp.drawText(text, x, y, paint);
        canvas.drawText(text,x,y,mPaint);
        paint.setXfermode(xformode);
        paint.setColor(Color.RED);
        Rect rect;
        if(mm>0)
         rect = new Rect(0,0,mm,getHeight());
        else
        rect = new Rect(getWidth()+mm,0,getWidth(),getHeight());
        canvasTmp.drawRect(rect,paint);
        canvas.drawBitmap(bitmap,0,0,getPaint());
        postInvalidate();
    }
}

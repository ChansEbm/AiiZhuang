package com.appbaba.platform.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import com.appbaba.platform.R;

/**
 * Created by ruby on 2016/5/13.
 */
public class MyTextView extends TextView {
    private  int mm = 0; //渲染的距离  <0:从x=0开始  >0:从width开始
    private String text="";
    private Paint mPaint;
    private PorterDuffXfermode fermode;
    private int normalColor,pressColor;

    public MyTextView(Context context) {
        super(context);
        fermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        fermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GradientTextView);
        text = a.getString(R.styleable.GradientTextView_text);
         normalColor = a.getColor(R.styleable.GradientTextView_normal_color,-1);
        pressColor = a.getColor(R.styleable.GradientTextView_press_color,-1);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GradientTextView);
        text = a.getString(R.styleable.GradientTextView_text);
        fermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }



    public void setMM(int x) {
        this.mm = x;
    }



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
        paint.setAntiAlias(true);
        // text size in pixels
        paint.setTextSize(getTextSize());
        // text shadow
        mPaint = paint;
        if(normalColor==-1) {
            mPaint.setColor(Color.RED);
        }
        else
        {
            mPaint.setColor(normalColor);
        }
        // draw text to the Canvas center
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        float x = (bitmap.getWidth() - bounds.width())/2;
        float y = (bitmap.getHeight() + bounds.height())/2;

        canvasTmp.drawText(text, x, y, paint);
        canvas.drawText(text,x,y,mPaint);
        paint.setXfermode(fermode);
        if(pressColor==-1) {
            paint.setColor(Color.BLACK);
        }
        else
        {
            paint.setColor(pressColor);
        }
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

package com.appbaba.platform.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContentResolverCompat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.appbaba.platform.R;

/**
 * Created by ruby on 2016/6/24.
 */
public class VoiceImageView extends View {
    private Context context;
    private Bitmap bitmap;
    private int height = 0;
    private double max = 160;

    public VoiceImageView(Context context) {
        super(context);
        this.context = context;
        Init();
    }

    public VoiceImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        Init();
    }

    public VoiceImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        Init();
    }

    public void Init()
    {
        bitmap = ((BitmapDrawable) ContextCompat.getDrawable(context, R.mipmap.icon_voice_normal)).getBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap1 = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
        Canvas srcCanvas = new Canvas(bitmap1);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int nWidth = getWidth()/5*4;
        int nHeight = nWidth * height / width;
        Rect rect = new Rect((getWidth()-nWidth)/2,0,nWidth,nHeight);
        srcCanvas.drawBitmap(bitmap,new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()),rect,null);
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(context,R.color.base_color_tv_bg));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        srcCanvas.drawRect(0,getHeight()-this.height,getWidth(),getHeight(),paint);
        canvas.drawBitmap(bitmap1,0,0,null);
        postInvalidate();
    }


    public void setHeight(double height) {
        if(height>max)
        {
            max = height;
        }
            double x = getHeight()  * (height * 1.0f/ max);
            this.height = (int) x;
        Log.e("sdf","height:"+this.height+" max:"+max);
    }
}

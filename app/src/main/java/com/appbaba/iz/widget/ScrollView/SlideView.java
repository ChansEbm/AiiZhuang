package com.appbaba.iz.widget.ScrollView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.tools.LogTools;


/**
 * Created by ruby on 2016/3/15.
 */
public class SlideView extends LinearLayout {
    private Scroller scroller;
    int width = 0;
    public boolean showing = false;//判断是否已经在横向显示
    public  boolean moving=false; //判断是否手指在移动
    public boolean canrun = true; //判断是否可以横向拖动
    public   boolean isShow = false; //判断是否已经显示
    public SlideView(Context context) {
        super(context);
        InitView();
    }

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitView();
    }

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitView();
    }

    public void  InitView()
    {
          scroller = new Scroller(getContext());
          width = MethodConfig.dip2px(getContext(), 160);
    }

    public void  back()
    {
        showing = false;
        smoothScrollTo(0, 0);
    }


    //调用此方法滚动到目标位置
    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - scroller.getFinalX();
        if(!isShow) {
            if (dx >= 0 && canrun) {
                if ((scroller.getFinalX()+dx) < width) {
                    smoothScrollBy(dx, 0);
                } else {
                    dx = width - scroller.getFinalX();
                    canrun = false;
                    isShow = true;
                    smoothScrollBy(dx, 0);
                }
            } else if (fx == 0) {
                canrun = true;
                isShow = false;
                showing = false;
                smoothScrollBy(dx, 0);
            }

        }
        else
        {
            isShow = false;
            if(fx==0) {
                canrun = true;
                smoothScrollBy(dx, 0, 1000);
            }
        }

    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy) {

        //设置mScroller的滚动偏移量
        scroller.startScroll(scroller.getFinalX(), scroller.getFinalY(), dx, dy);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy,int duration) {

        //设置mScroller的滚动偏移量
        scroller.startScroll(scroller.getFinalX(), scroller.getFinalY(), dx, dy,duration);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }
    int newX = 0;
    int newY =0;



    public  boolean  onRequireTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                moving = false;
                if(isShow)
                {
                    newX = 0;
                    newY = 0;
                    back();
                }
                newX =(int) event.getX();
                newY = (int)event.getY();
                Log.e("mm", "onRequireTouchEvent: "+newX );
                break;
            case  MotionEvent.ACTION_MOVE:
            {
                moving = true;
                int x1 = newX-(int)event.getX();
                int y1 = newY -(int)event.getY();

                if(Math.abs(x1)>80 && Math.abs(y1)<80) {
                    Log.e("mm", "onRequireTouchEvent:x1 "+y1 );
                    smoothScrollTo(x1, 0);
                    showing = true;
                    return true;
                }
                else if(x1==0 && y1==0)
                {
                    moving = false;
                }
            }
            break;
            case MotionEvent.ACTION_UP:
                if(!isShow) {
                    int fx = scroller.getFinalX();

                    if (width * 0.2 > fx) {
                        smoothScrollBy(-fx, 0);
                        isShow = false;
                        canrun = true;
                        showing = false;
                    } else {
                        int dx = width - scroller.getFinalX();
                        canrun = false;
                        isShow = true;

                        smoothScrollBy(dx, 0);
                    }
                    Log.e("mm", "onRequireTouchEvent:f1 "+fx );
                }
                break;
        }
        return  false;
    }



    @Override
    public void computeScroll() {
        if(scroller.computeScrollOffset())
        {
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }
}

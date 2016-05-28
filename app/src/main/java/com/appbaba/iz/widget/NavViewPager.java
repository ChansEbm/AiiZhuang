package com.appbaba.iz.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/5/14.
 */
public class NavViewPager extends RelativeLayout implements ViewPager.OnPageChangeListener{
    private Context context;
    private ViewPager viewPager;
    private PointsView pointsView;
    private CallAction callAction;

    public NavViewPager(Context context) {
        super(context);
        this.context = context;
        InitView();
    }

    public NavViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        InitView();
    }

    public NavViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        InitView();
    }


    public void InitView()
    {
        viewPager = new ViewPager(context);
        pointsView = new PointsView(context);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewPager.setLayoutParams(params);
        LayoutParams params1 =new LayoutParams(200, 40);
        params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
        params1.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
        params1.bottomMargin=80;
        pointsView.setLayoutParams(params1);
        this.addView(viewPager);
        this.addView(pointsView);
        viewPager.addOnPageChangeListener(this);
    }

    public void SetPointViewStyle(int pointStyle, @ColorInt int bgColor,@ColorInt int baseColor,@ColorInt int selectColor, int radius)
    {
        if(pointStyle== PointsView.POINT_STYLE_LINE || pointStyle== PointsView.POINT_STYLE_CIRCLE) {
            pointsView.setPointStyle(pointStyle);
        }
        if(bgColor!=0) {
            pointsView.setBgColor(bgColor);
        }
        if(baseColor!=0)
        {
          pointsView.setBaseColor(baseColor);
        }
        if(selectColor!=0)
        {
            pointsView.setSelectColor(selectColor);
        }
        if(radius>0)
        {
            pointsView.setRadius(radius);
        }
        postInvalidate();
    }

    public void  SetAdapter(PagerAdapter adapter)
    {
        viewPager.setAdapter(adapter);
        List<String> list = new ArrayList<>();
        for(int i=0;i<adapter.getCount();i++)
        {
            list.add(""+i);
        }
        pointsView.setList(list);
    }

    public int GetVPCurrentIndex()
    {
        return viewPager.getCurrentItem();
    }

    public int GetVPCount()
    {
        int count = viewPager.getAdapter().getCount();
        return count;
    }

    public void Next()
    {
        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
    }

    public void ScrollEnd()
    {
        if(callAction!=null)
        {
            callAction.OnEnd();
        }
    }

    public void setCallAction(CallAction callAction)
    {
        this.callAction = callAction;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        pointsView.setSelectIndex(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class PointsView extends View {
        public final static int POINT_STYLE_CIRCLE = 1;
        public final static int POINT_STYLE_LINE = 2;
        private List<String> list;
        private int selectIndex=0;
        private int radius=10;
        private int pointStyle=1;
        private int selectColor = Color.argb(128,255,255,255),baseColor=Color.argb(255,255,255,255),bgColor = Color.TRANSPARENT;


        public PointsView(Context context) {
            super(context);
        }

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }

        public int getSelectIndex() {
            return selectIndex;
        }

        public void setSelectIndex(int selectIndex) {
            this.selectIndex = selectIndex;
        }

        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }

        public int getPointStyle() {
            return pointStyle;
        }

        public void setPointStyle(int pointStyle) {
            this.pointStyle = pointStyle;
        }

        public int getSelectColor() {
            return selectColor;
        }

        public void setSelectColor(int selectColor) {
            this.selectColor = selectColor;
        }

        public int getBaseColor() {
            return baseColor;
        }

        public void setBaseColor(int baseColor) {
            this.baseColor = baseColor;
        }

        public int getBgColor() {
            return bgColor;
        }

        public void setBgColor(int bgColor) {
            this.bgColor = bgColor;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if(list!=null)
            {
                Paint paint = new Paint();
                paint.setStyle(Paint.Style.FILL);
                canvas.drawColor(bgColor);
                for(int i=0;i<list.size();i++)
                {
                    if(selectIndex==i)
                    {
                        paint.setColor(selectColor);
                    }
                    else
                    {
                        paint.setColor(baseColor);
                    }
                    if(pointStyle == POINT_STYLE_CIRCLE) {
                        canvas.drawCircle(radius + radius*2*i+10*(i+1), radius+10, radius, paint);
                    }
                    else
                    {
                        canvas.drawRect(i*(radius+10)+10,15,radius+i*(radius+10)+10,25,paint);
                    }
                }
            }
            postInvalidate();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            if(list!=null)
            {
                int width = pointStyle==POINT_STYLE_CIRCLE ? list.size()*(radius*2+10)+10 : list.size()*(radius+10)+10;
                int height = pointStyle==POINT_STYLE_CIRCLE ? radius*2+20 : 40;
                setMeasuredDimension(width, height);
            }
        }
    }

    public interface CallAction{
        void OnEnd();
    }
}

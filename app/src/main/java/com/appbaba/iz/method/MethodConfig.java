package com.appbaba.iz.method;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.appbaba.iz.entity.Friends.FriendsClientBean;
import com.appbaba.iz.entity.Login.AuthBean;

/**
 * Created by ruby on 2016/4/1.
 */
public class MethodConfig {
    public  static  DisplayMetrics metrics;
    public  static AuthBean localUser;
    public  static FriendsClientBean.ListEntity chooseClient;
    public  static  String jpush_id;
    private static long ticks= 0;

    public  static void  SetDispaly(Context context)
    {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        MethodConfig.metrics = metrics;
    }

    public  static  long GetTicks()
    {
         long nowTicks = System.currentTimeMillis();
         long result = nowTicks-ticks;
         ticks = nowTicks;
         return  result;
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 16 比 9转换
     * @param width
     * @return
     */
    public static int GetHeightFor16v9(int width)
    {
        return width*8/15;
    }

    public static int GetHeightFor4v3(int width)
    {
        return width*3/4;
    }
    public static int GetWidthFor16v9(int height)
    {
        return height*16/9;
    }
}

package com.appbaba.platform.method;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appbaba.platform.entity.User.UserInfo;
import com.appbaba.platform.entity.comm.LocationBean;

/**
 * Created by ruby on 2016/5/4.
 */
public class MethodConfig {
    private static long ticks= 0;
    private static Handler handler = new Handler();

    public static Context context;
    public static DisplayMetrics metrics;
    public static UserInfo userInfo;
    public static LocationBean locationBean;

    public static boolean IsLogin()
    {
        if(userInfo==null || TextUtils.isEmpty(userInfo.getToken()))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public static void SetDispaly(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();

        display.getMetrics(metrics);
        if(metrics.widthPixels>metrics.heightPixels)
        {
            int x = metrics.widthPixels;
            metrics.widthPixels = metrics.heightPixels;
            metrics.heightPixels =x;
        }
        MethodConfig.metrics = metrics;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static  int GetHeight(int width,int w,int h)
    {
        return width*h/w;
    }

    public static long GetTicks() {
        long nowTicks = System.currentTimeMillis();
        long result = nowTicks - ticks;
        ticks = nowTicks;
        return result;
    }

    public static void  ShowToast(final String msg)
    {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
            }
        });

    }

    public static boolean EmptyEditText(EditText... editTexts)
    {
        for(int i=0;i<editTexts.length;i++)
        {
            if(TextUtils.isEmpty(editTexts[i].getText()))
            {
                return true;
            }
        }
        return false;
    }
}

package com.appbaba.platform.method;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.appbaba.platform.entity.User.UserBean;
import com.appbaba.platform.entity.comm.BaseHotWordsBean;
import com.appbaba.platform.entity.User.UserInfo;
import com.appbaba.platform.entity.comm.LocationBean;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.HashMap;
import java.util.Hashtable;

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
    public static BaseHotWordsBean hotWordsBean;
    public static UserBean userBean;
    public static HashMap<String,String> userImage = new HashMap<>();

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
    private static  int QR_WIDTH = 500, QR_HEIGHT = 500;
    // 生成QR图
    public static Bitmap createImage(String text) {
        try {
            if (TextUtils.isEmpty(text)) {
                return null;
            }
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix martix = writer.encode(text, BarcodeFormat.QR_CODE,
                    QR_WIDTH, QR_HEIGHT);

            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(text,
                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
                    Bitmap.Config.ARGB_8888);

            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }

    }
}

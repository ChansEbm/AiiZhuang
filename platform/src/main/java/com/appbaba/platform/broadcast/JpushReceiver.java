package com.appbaba.platform.broadcast;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;

import com.appbaba.platform.R;
import com.appbaba.platform.tools.AppTools;
import com.appbaba.platform.ui.activity.IndexActivity;
import com.github.pwittchen.prefser.library.Prefser;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ChanZeeBm on 3/2/2016.
 */
public class JpushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (intent.getAction().equals("cn.jpush.android.intent.REGISTRATION")) {
            Prefser prefser = new Prefser(AppTools.getSharePreferences());
            final String extraRegistrationId = bundle.getString(JPushInterface
                    .EXTRA_REGISTRATION_ID, "");
//            LogTools.i("receiver:" + extraRegistrationId);
            if (!TextUtils.isEmpty(extraRegistrationId)) {
                prefser.put("registrationId", extraRegistrationId);
            }
        } else if (intent.getAction().equals("cn.jpush.android.intent.MESSAGE_RECEIVED")) {
            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setAutoCancel(true);
            builder.setSmallIcon(R.mipmap.icon_bubble);
            builder.setColor(context.getResources().getColor(R.color.bg_color)).setWhen
                    (System.currentTimeMillis()).setDefaults(Notification.DEFAULT_ALL);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(IndexActivity.class);
            stackBuilder.addNextIntent(intent);
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent
                    .FLAG_UPDATE_CURRENT);
            Bundle bundle1 = intent.getExtras();
            String title = bundle1.getString(JPushInterface.EXTRA_TITLE, context.getResources()
                    .getString(R.string.app_name));
            title = TextUtils.isEmpty(title) ? context.getResources()
                    .getString(R.string.app_name) : title;
            String msg = bundle1.getString(JPushInterface.EXTRA_MESSAGE, "");
            builder.setTicker(title).setContentTitle(title).setContentText(msg);
            managerCompat.notify(1, builder.build());
        }
        else if(JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction()))
        {
            //点击打开通知
            Bundle bundle1 = intent.getExtras();
//            LogTools.e(JPushInterface.EXTRA_EXTRA);
//           LogTools.e(JPushInterface.EXTRA_MESSAGE+"  "+bundle1.getString(JPushInterface.EXTRA_MESSAGE));
//            LogTools.e(JPushInterface.EXTRA_TITLE+"  "+bundle1.getString(JPushInterface.EXTRA_TITLE));
//            LogTools.e(JPushInterface.EXTRA_ALERT+"  "+bundle1.getString(JPushInterface.EXTRA_ALERT));
//            LogTools.e(JPushInterface.EXTRA_NOTIFICATION_TITLE+"  "+bundle1.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
//            LogTools.e(JPushInterface.EXTRA_STATUS+"  "+bundle1.getString(JPushInterface.EXTRA_STATUS));
//            LogTools.e(JPushInterface.EXTRA_APP_KEY+"  "+bundle1.getString(JPushInterface.EXTRA_APP_KEY));

            String msg = bundle1.getString(JPushInterface.EXTRA_ALERT);
            int index = msg.indexOf("链接：");
            if(index>0)
            {
                String urlString = msg.substring(index+"链接：".length());
                if(!TextUtils.isEmpty(urlString)) {
                    String productID = Uri.parse(urlString).getQueryParameter("product_id");
                    String cases_id = Uri.parse(urlString).getQueryParameter("cases_id");

                }

            }
        }
    }

}

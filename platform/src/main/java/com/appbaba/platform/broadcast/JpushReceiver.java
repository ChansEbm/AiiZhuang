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
import android.util.Log;
import android.widget.Toast;

import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.R;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.tools.AppTools;
import com.appbaba.platform.tools.LogTools;
import com.appbaba.platform.ui.activity.IndexActivity;
import com.appbaba.platform.ui.activity.comm.CommWebActivity;
import com.appbaba.platform.ui.activity.inspiration.InspirationDetailActivity;
import com.appbaba.platform.ui.activity.products.ProductWebDetailActivity;
import com.appbaba.platform.ui.activity.user.MessageDetailActivity;
import com.github.pwittchen.prefser.library.JsonConverter;
import com.github.pwittchen.prefser.library.Prefser;
import com.google.gson.Gson;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ChanZeeBm on 3/2/2016.
 */
public class JpushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (intent.getAction().equals(JPushInterface.ACTION_REGISTRATION_ID)) {
            Prefser prefser = new Prefser(AppTools.getSharePreferences());
            final String extraRegistrationId = bundle.getString(JPushInterface
                    .EXTRA_REGISTRATION_ID, "");
            LogTools.i("receiver:" + extraRegistrationId);
            if (!TextUtils.isEmpty(extraRegistrationId)) {
                prefser.put("registrationId", extraRegistrationId);
            }
        } else if (intent.getAction().equals(JPushInterface.ACTION_MESSAGE_RECEIVED)) {
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
        else if(JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction()))
        {
            AppTools.sendBroadcast(null, AppKeyMap.MESSAGE_UN_READ);
            Bundle bundle1 = intent.getExtras();
            String msg = bundle1.getString(JPushInterface.EXTRA_ALERT);
            String action = bundle1.getString(JPushInterface.EXTRA_EXTRA);
            Gson gson = new Gson();
            BaseBean baseBean = gson.fromJson(action, BaseBean.class);
            String turn_to = baseBean.getTurn_to();
            if(!TextUtils.isEmpty(turn_to) && turn_to.equals("force_logout"))
            {
                Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
                MethodConfig.LogOut();
            }
        }
        else if(JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction()))
        {
            //点击打开通知
              Bundle bundle1 = intent.getExtras();
//            LogTools.e(JPushInterface.EXTRA_EXTRA);
//            LogTools.e(JPushInterface.EXTRA_MESSAGE+"  "+bundle1.getString(JPushInterface.EXTRA_MESSAGE));
//            LogTools.e(JPushInterface.EXTRA_TITLE+"  "+bundle1.getString(JPushInterface.EXTRA_TITLE));
//            LogTools.e(JPushInterface.EXTRA_ALERT+"  "+bundle1.getString(JPushInterface.EXTRA_ALERT));
//            LogTools.e(JPushInterface.EXTRA_NOTIFICATION_TITLE+"  "+bundle1.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
//            LogTools.e(JPushInterface.EXTRA_STATUS+"  "+bundle1.getString(JPushInterface.EXTRA_STATUS));
//            LogTools.e(JPushInterface.EXTRA_APP_KEY+"  "+bundle1.getString(JPushInterface.EXTRA_APP_KEY));

            String msg = bundle1.getString(JPushInterface.EXTRA_ALERT);

            String action = bundle1.getString(JPushInterface.EXTRA_EXTRA);
            Gson gson = new Gson();
            BaseBean baseBean = gson.fromJson(action, BaseBean.class);
            String jumpTo = baseBean.getJump_to();
            if(jumpTo!=null && jumpTo.equals("app"))
            {
                String which = baseBean.getTurn_to();
                String id = baseBean.getId();
                if(which.equals("inspiration"))
                {
                    Intent intent1 = new Intent(context,InspirationDetailActivity.class);
                    intent1.putExtra("id",id);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);
                }
                else if(which.equals("notification"))
                {
                    Intent intent1 = new Intent(context,MessageDetailActivity.class);
                    intent1.putExtra("id",id);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);
                }
                else if(which.equals("commodity"))
                {
                    Intent intent1 = new Intent(context,ProductWebDetailActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent1.putExtra("id",id);
                    context.startActivity(intent1);
                }
            }
            else
            {
                Intent intent1 = new Intent(context,CommWebActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("url",baseBean.getTurn_to());
                context.startActivity(intent1);
            }

//            else if()

        }
    }

}

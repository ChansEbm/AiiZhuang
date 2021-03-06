package com.appbaba.platform.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.util.Log;

import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.R;
import com.appbaba.platform.entity.comm.HYMessageBean;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.tools.AppTools;
import com.appbaba.platform.tools.FrescoTools;
import com.appbaba.platform.ui.activity.IndexActivity;
import com.appbaba.platform.ui.activity.user.FriendsActivity;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;


import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.finalteam.galleryfinal.BuildConfig;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.widget.GFImageView;
import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by ruby on 2016/5/4.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MethodConfig.context = this;
        MethodConfig.SetDispaly(this);
        AppTools.init(this);
        ShareSDK.initSDK(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        MethodConfig.jpush_id = JPushInterface.getRegistrationID(getApplicationContext());
        initGalleryFinal();
         StartHY();
        InitMessage();
    }

    public void  StartHY()
    {
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
// 如果APP启用了远程的service，此application:onCreate会被调用2次
// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null ||!processAppName.equalsIgnoreCase(this.getPackageName())) {
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        EMOptions emOptions = new EMOptions();
        emOptions.setAutoLogin(false);
        emOptions.setAcceptInvitationAlways(false);
        EMClient.getInstance().init(this,emOptions);
    }

    EMMessageListener messageListener;
    public void InitMessage()
    {
         messageListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> list) {

                for(int i = 0;i<list.size();i++)
                {
                    Log.e("messageReceived","收到最新消息："+list.get(i).getFrom() +"  id为："+list.get(i).getMsgId());
                    if(TextUtils.isEmpty(MethodConfig.nowMsgUsername) || !list.get(i).getFrom().equals(MethodConfig.nowMsgUsername))
                    {
                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MethodConfig.context);
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(MethodConfig.context);
                        builder.setAutoCancel(true);
                        builder.setSmallIcon(R.mipmap.icon_bubble);
                        builder.setColor(MethodConfig.context.getResources().getColor(R.color.bg_color_dark)).setWhen
                                (System.currentTimeMillis()).setDefaults(Notification.DEFAULT_ALL);
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(MethodConfig.context);
                        stackBuilder.addParentStack(IndexActivity.class);
                        Intent intent = new Intent(MethodConfig.context, FriendsActivity.class);
                        PendingIntent contentIntent = PendingIntent.getActivity(MethodConfig.context, 1,
                                intent, 0);
                        builder.setContentIntent(contentIntent);
                        builder.setTicker(ParseMsg(list.get(i))).setContentTitle(getResources().getString(R.string.app_name)).setContentText("你有一个消息");
                        managerCompat.notify(0, builder.build());
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("message",new ArrayList<>(list));

                AppTools.sendBroadcast(bundle,AppKeyMap.MESSAGE_ACTION);
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageReadAckReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageDeliveryAckReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage emMessage, Object o) {

            }
        };
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    public void RemoveMessage()
    {
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
    }

    public String ParseMsg(EMMessage eMsg)
    {

        switch (eMsg.getType()) {
            case TXT:
            {
                EMTextMessageBody msg = (EMTextMessageBody) eMsg.getBody();
               return msg.getMessage();
            }
            case IMAGE:
            {
               return "[图片]";
            }
            case VOICE: {
                return "[语音]";
            }
        }
        return "";
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    private void initGalleryFinal() {
        ImageLoader imageloader = new FrescoImageLoader(this);
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnablePreview(true).setMutiSelectMaxSize(8).build();
        CoreConfig coreConfig = new CoreConfig.Builder(this, imageloader, ThemeConfig.CYAN)
                .setDebug(BuildConfig.DEBUG).setTakePhotoFolder(new File(AppTools
                        .getPictureCacheDir()))
                .setFunctionConfig(functionConfig).build();
        GalleryFinal.init(coreConfig);
    }

    public class FrescoImageLoader implements cn.finalteam.galleryfinal.ImageLoader {

        private Context context;

        public FrescoImageLoader(Context context) {
            this(context, Bitmap.Config.RGB_565);
        }

        public FrescoImageLoader(Context context, Bitmap.Config config) {
            this.context = context;
            ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(context)
                    .setBitmapsConfig(config)
                    .build();
            Fresco.initialize(context, imagePipelineConfig);
        }

        @Override
        public void displayImage(Activity activity, String path, final GFImageView imageView,
                                 final Drawable defaultDrawable, int width, int height) {
            Resources resources = context.getResources();
            GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(resources)
                    .setFadeDuration(300)
                    .setPlaceholderImage(defaultDrawable)
                    .setFailureImage(defaultDrawable)
                    .setProgressBarImage(new ProgressBarDrawable())
                    .build();

            final DraweeHolder<GenericDraweeHierarchy> draweeHolder = DraweeHolder.create
                    (hierarchy, context);
            imageView.setOnImageViewListener(new GFImageView.OnImageViewListener() {
                @Override
                public void onDetach() {
                    draweeHolder.onDetach();
                }

                @Override
                public void onAttach() {
                    draweeHolder.onAttach();
                }

                @Override
                public boolean verifyDrawable(Drawable dr) {
                    if (dr == draweeHolder.getHierarchy().getTopLevelDrawable()) {
                        return true;
                    }
                    return false;
                }

                @Override
                public void onDraw(Canvas canvas) {
                    Drawable drawable = draweeHolder.getHierarchy().getTopLevelDrawable();
                    if (drawable == null) {
                        imageView.setImageDrawable(defaultDrawable);
                    } else {
                        imageView.setImageDrawable(drawable);
                    }
                }
            });
            Uri uri = new Uri.Builder()
                    .scheme(UriUtil.LOCAL_FILE_SCHEME)
                    .path(path)
                    .build();
            ImageRequest imageRequest = ImageRequestBuilder
                    .newBuilderWithSource(uri)
                    .setResizeOptions(new ResizeOptions(width, height))//图片目标大小
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(draweeHolder.getController())
                    .setImageRequest(imageRequest)
                    .build();
            draweeHolder.setController(controller);
        }

        @Override
        public void clearMemoryCache() {

        }
    }
}

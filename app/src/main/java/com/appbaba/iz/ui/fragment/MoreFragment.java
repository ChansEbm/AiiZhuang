package com.appbaba.iz.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.appbaba.iz.AppKeyMap;
import com.appbaba.iz.FragmentMoreBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.dialog.LoadingDialog;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.tools.LogTools;
import com.appbaba.iz.tools.OkHttpBuilder;
import com.appbaba.iz.tools.SDCardTools;
import com.appbaba.iz.tools.StringFormatTools;
import com.appbaba.iz.ui.activity.LoginActivity;
import com.appbaba.iz.ui.activity.TransferActivity;
import com.appbaba.iz.widget.DialogView.MyDialogView;
import com.appbaba.iz.widget.DialogView.ShareDialogView;
import com.squareup.picasso.Cache;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.SimpleFormatter;


/**
 * Created by ruby on 2016/4/1.
 */
public class MoreFragment extends BaseFgm{
    private FragmentMoreBinding moreBinding;

    private Handler handler;
    private File cacheFile;
    private LoadingDialog dialog;
    @Override
    protected void initViews() {
       moreBinding = (FragmentMoreBinding)viewDataBinding;
        moreBinding.includeTopTitle.title.setText(R.string.main_activity_bottom_more);
        moreBinding.includeTopTitle.title.setTextColor(Color.BLACK);
        moreBinding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);

        handler = new Handler();
        dialog = new LoadingDialog(getContext());
         if(MethodConfig.localUser==null)
         {
             moreBinding.btnLogin.setText("登录");
         }

        cacheFile = new File(getContext().getApplicationContext().getCacheDir().getPath()+File.separator+"picasso-cache");
        StartGet();
    }

    public  float  GetSize()
    {
        if(cacheFile!=null) {
            File[] files = cacheFile.listFiles();
            if(files!=null) {
                float ll = 0;
                for (int i = 0; i < files.length; i++) {
                    float x = files[i].length();
                    ll += x;
                }
                return ll / 1024 / 1024 / 8;
            }
            else
            {
                return 0;
            }
        }
        else
        {
            return 0;
        }
    }

    public void StartGet()
    {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                String message = String.format(Locale.CHINA, "%.1fMB", GetSize());
                HandleSetTextForSize(message);
            }
        };
        timer.schedule(timerTask,0);
    }
    public  void StartDel()
    {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(cacheFile!=null) {
                    File[] files = cacheFile.listFiles();
                    if (files != null) {
                        for(int i=0;i<files.length;i++)
                           files[i].delete();
                    }
                }
                StartGet();
            }
        };
        timer.schedule(timerTask,0);
    }

    public void  HandleSetTextForSize(final  String message)
    {
        if(handler!=null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (moreBinding.tvCacheSize != null)
                        moreBinding.tvCacheSize.setText(message);
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            });
        }
    }

    @Override
    protected void initEvents() {

        moreBinding.linearPerson.setOnClickListener(this);
        moreBinding.linearChangePwd.setOnClickListener(this);
        moreBinding.linearFeedback.setOnClickListener(this);
        moreBinding.btnLogin.setOnClickListener(this);
        moreBinding.linearPrivate.setOnClickListener(this);
        moreBinding.linearService.setOnClickListener(this);
        moreBinding.linearAboutUs.setOnClickListener(this);
        moreBinding.linearGuide.setOnClickListener(this);
        moreBinding.linearCache.setOnClickListener(this);
        moreBinding.linearShare.setOnClickListener(this);
    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {

        switch (id)
        {
            case R.id.linear_person: {
                if(MethodConfig.localUser==null)
                {
                    start(LoginActivity.class);
                }
                else {
                    Intent intent = new Intent(getContext(), TransferActivity.class);
                    intent.putExtra("fragment", 5);
                    startActivity(intent);
                }
            }
                break;
            case R.id.linear_change_pwd: {
                if(MethodConfig.localUser==null)
                {
                    start(LoginActivity.class);
                }
                else {
                    Intent intent = new Intent(getContext(), TransferActivity.class);
                    intent.putExtra("fragment", 6);
                    startActivity(intent);
                }
            }
                break;
            case R.id.linear_feedback:
            {
                if(MethodConfig.localUser==null)
                {
                    start(LoginActivity.class);
                }
                else {
                    Intent intent = new Intent(getContext(), TransferActivity.class);
                    intent.putExtra("fragment", 10);
                    startActivity(intent);
                }
            }
            break;
            case R.id.btn_login:
            {
                if(MethodConfig.localUser!=null)
                {
                    networkModel.HomeMoreLogout(MethodConfig.localUser.getAuth(),MethodConfig.jpush_id, NetworkParams.LOGOUT);
                }
                startActivity(new Intent(getContext(), LoginActivity.class));
                ((Activity)getContext()).finish();
            }
                break;
            case R.id.linear_about_us: {
                Intent intent = new Intent(getContext(), TransferActivity.class);
                intent.putExtra("fragment", 14);
                intent.putExtra("title", getString(R.string.more_fragment_about_us));
                intent.putExtra("which", 8);
                intent.putExtra("value", "");
                startActivity(intent);
            }
                break;
            case R.id.linear_service: {
                Intent intent = new Intent(getContext(), TransferActivity.class);
                intent.putExtra("fragment", 14);
                intent.putExtra("title", getString(R.string.more_fragment_service));
                intent.putExtra("which", 9);
                intent.putExtra("value", "");
                startActivity(intent);
            }
                break;
            case R.id.linear_private:
            {
                Intent intent = new Intent(getContext(), TransferActivity.class);
                intent.putExtra("fragment", 14);
                intent.putExtra("title",getString(R.string.more_fragment_private));
                intent.putExtra("which",10);
                intent.putExtra("value","");
                startActivity(intent);
            }
                break;
            case R.id.linear_guide:
            {
                Intent intent = new Intent(getContext(), TransferActivity.class);
                intent.putExtra("fragment", 14);
                intent.putExtra("title",getString(R.string.more_fragment_use_guide));
                intent.putExtra("which",7);
                intent.putExtra("value","");
                startActivity(intent);
            }
                break;
            case R.id.linear_cache: {
                final MyDialogView myDialogView = new MyDialogView(getContext(),"提示","是否现在清理缓存？");
                myDialogView.setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialogView.dismiss();
                    }
                });
                myDialogView.setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialogView.dismiss();
                        dialog.show();
                        StartDel();
                    }
                });
                myDialogView.show();

            }
                break;
            case R.id.linear_share:
            {
                File file = new File(SDCardTools.getSDCardPosition()+"app_icon.png");
                if(!file.exists())
                {
                    try {
                        InputStream inputStream = getContext().getResources().getAssets().open("app_icon.png");
                        FileOutputStream outputStream = new FileOutputStream(file);
                        byte[] buffer = new byte[2048];
                        int count = 0;
                        while ( (count = inputStream.read(buffer))>0)
                        {
                            outputStream.write(buffer,0,count);
                        }
                        outputStream.close();
                        inputStream.close();
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
                ShareDialogView shareDialogView = new ShareDialogView("装色","装色在手，建材营销无忧",file.getAbsolutePath(),"www.izhuangse.com",getContext());
                shareDialogView.show();
            }
                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_more;
    }
}

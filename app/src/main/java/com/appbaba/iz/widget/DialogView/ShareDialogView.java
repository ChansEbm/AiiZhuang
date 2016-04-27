package com.appbaba.iz.widget.DialogView;

import android.app.AlertDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextClock;
import android.widget.Toast;

import com.appbaba.iz.R;
import com.appbaba.iz.ShareDialogBinding;
import com.appbaba.iz.method.MethodConfig;
import com.google.repacked.antlr.v4.runtime.misc.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.system.email.Email;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by ruby on 2016/4/26.
 */
public class ShareDialogView implements PlatformActionListener,View.OnClickListener {

    private AlertDialog dialog;
    private View view;
    private ShareDialogBinding binding;

    private Platform.ShareParams shareParams;
    private Platform platform;
    private  Context context;

    private  String title,message,url,imageUrl,imagePath;

    public ShareDialogView(Context context, String title, @NotNull String imageUrl, String message, String url)
    {
        this.context = context;
        this.title = title;

        this.message = message;
        this.url = url;
        this.imageUrl = imageUrl;
        Init();

    }

    public  ShareDialogView(String title,String message,@NotNull String imagePath,String url,Context context)
    {
        this.context = context;
        this.title = title;

        this.message = message;
        this.url = url;
        this.imagePath = imagePath;
        Init();
    }


    public  void  Init()
    {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.popup_share_view,null,false);
        view = binding.getRoot();
        binding.linearEmail.setOnClickListener(this);
        binding.linearMessage.setOnClickListener(this);
        binding.linearQq.setOnClickListener(this);
        binding.linearQqZone.setOnClickListener(this);
        binding.linearQrCode.setOnClickListener(this);
        binding.linearWechat.setOnClickListener(this);
        binding.linearWechatCollection.setOnClickListener(this);
        binding.linearWechatMoment.setOnClickListener(this);
        binding.linearWeibo.setOnClickListener(this);
        dialog = new AlertDialog.Builder(context,R.style.dialog).create();
    }
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.linear_wechat:
                shareParams = new Wechat.ShareParams();
                shareParams.setTitle(title);
                shareParams.setText(message);
                if(TextUtils.isEmpty(imagePath)) {
                    shareParams.setImageUrl(imageUrl);
                }
                else
                {
                    shareParams.setImagePath(imagePath);
                }
                shareParams.setUrl(url);
                shareParams.setShareType(Platform.SHARE_WEBPAGE);
                platform = ShareSDK.getPlatform(Wechat.NAME);
                platform.setPlatformActionListener(this);
                platform.share(shareParams);
                dialog.dismiss();
                break;
            case R.id.linear_wechat_moment:
                shareParams = new WechatMoments.ShareParams();
                shareParams.setTitle(title);
                shareParams.setText(message);
                if(TextUtils.isEmpty(imagePath)) {
                    shareParams.setImageUrl(imageUrl);
                }
                else
                {
                    shareParams.setImagePath(imagePath);
                }
                shareParams.setUrl(url);
                shareParams.setShareType(Platform.SHARE_WEBPAGE);
                platform = ShareSDK.getPlatform(WechatMoments.NAME);
                platform.setPlatformActionListener(this);
                platform.share(shareParams);
                dialog.dismiss();
                break;
            case R.id.linear_wechat_collection:
                shareParams = new WechatFavorite.ShareParams();
                shareParams.setTitle(title);
                shareParams.setText(message);
                if(TextUtils.isEmpty(imagePath)) {
                    shareParams.setImageUrl(imageUrl);
                }
                else
                {
                    shareParams.setImagePath(imagePath);
                }
                shareParams.setUrl(url);
                shareParams.setShareType(Platform.SHARE_WEBPAGE);
                platform = ShareSDK.getPlatform(WechatFavorite.NAME);
                platform.setPlatformActionListener(this);
                platform.share(shareParams);
                dialog.dismiss();
                break;
            case R.id.linear_qq:
                shareParams = new QQ.ShareParams();
                shareParams.setTitle(title);
                shareParams.setText(message+"\r\n"+url);
                if(TextUtils.isEmpty(imagePath)) {
                    shareParams.setImageUrl(imageUrl);
                }
                else
                {
                    shareParams.setImagePath(imagePath);
                }
                shareParams.setTitleUrl(url);
                shareParams.setShareType(Platform.SHARE_WEBPAGE);
                platform = ShareSDK.getPlatform(QQ.NAME);
                platform.setPlatformActionListener(this);
                platform.share(shareParams);
                dialog.dismiss();
                break;
            case R.id.linear_qq_zone:
                shareParams = new QZone.ShareParams();
                shareParams.setTitle(title);
                shareParams.setTitleUrl(url);
                if(TextUtils.isEmpty(imagePath)) {
                    shareParams.setImageUrl(imageUrl);
                }
                else
                {
                    shareParams.setImagePath(imagePath);
                }
                shareParams.setText(message);
                shareParams.setSite(title);
                shareParams.setSiteUrl(url);
                shareParams.setShareType(Platform.SHARE_WEBPAGE);
                platform = ShareSDK.getPlatform(QZone.NAME);
                platform.setPlatformActionListener(this);
                platform.share(shareParams);
                dialog.dismiss();
                break;
            case R.id.linear_weibo:
                shareParams = new SinaWeibo.ShareParams();
                shareParams.setTitle(title);
                shareParams.setText(message);
                if(TextUtils.isEmpty(imagePath)) {
                    shareParams.setImageUrl(imageUrl);
                }
                else
                {
                    shareParams.setImagePath(imagePath);
                }
                shareParams.setUrl(url);
                shareParams.setShareType(Platform.SHARE_WEBPAGE);
                platform = ShareSDK.getPlatform(SinaWeibo.NAME);
                platform.setPlatformActionListener(this);
                platform.share(shareParams);
                dialog.dismiss();
                break;
            case R.id.linear_message:
                shareParams = new ShortMessage.ShareParams();
                shareParams.setAddress("");
                if(TextUtils.isEmpty(imagePath)) {
                    shareParams.setImageUrl(imageUrl);
                }
                else
                {
                    shareParams.setImagePath(imagePath);
                }
                shareParams.setText(title+" "+url);
                platform = ShareSDK.getPlatform(ShortMessage.NAME);
                platform.setPlatformActionListener(this);
                platform.share(shareParams);
                dialog.dismiss();
                break;
            case R.id.linear_email:
                shareParams = new Email.ShareParams();
                shareParams.setTitle(title);
                shareParams.setText(message+"\r\n"+url);
                if(TextUtils.isEmpty(imagePath)) {
                    shareParams.setImageUrl(imageUrl);
                }
                else
                {
                    shareParams.setImagePath(imagePath);
                }
                shareParams.setShareType(Platform.SHARE_WEBPAGE);
                platform = ShareSDK.getPlatform(Email.NAME);
                platform.setPlatformActionListener(this);
                platform.share(shareParams);
                dialog.dismiss();
                break;
            case R.id.linear_qr_code:
                binding.linearBg.setVisibility(View.GONE);
                binding.ivQrCode.setVisibility(View.VISIBLE);
                binding.ivQrCode.setImageBitmap(MethodConfig.createImage(url));
               break;

        }

    }

    public void  StartShare()
    {


    }
    public  void  show()
    {
        if(!dialog.isShowing())
        {
            dialog.show();
            Window window = dialog.getWindow();
            window.setContentView(view);
        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Toast.makeText(context,"分享成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Toast.makeText(context,"分享失败！"+throwable.getMessage(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Toast.makeText(context,"取消分享",Toast.LENGTH_LONG).show();
    }
}

package com.appbaba.platform.method;

import android.os.Handler;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ruby on 2016/6/17.
 */
public class HYMethod {

    private Handler handler;

    public HYMethod()
    {
        handler = new Handler();
    }

    public void Login(String username, String password, EMCallBack callBack)
    {
        if(callBack==null)
        {
            callBack = new EMCallBack() {
                @Override
                public void onSuccess() {
                    ShowToast("登录环信成功");
                    //Toast.makeText(MethodConfig.context,,Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(int i, String s) {

                }

                @Override
                public void onProgress(int i, String s) {

                }
            };
        }
        EMClient.getInstance().login(username,password,callBack);
    }

    public void Register()
    {}


    public void AddFriend()
    {}

    public void SendText(String content,String toChatUsername)
    {
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
////如果是群聊，设置chattype，默认是单聊
//        if (chatType == CHATTYPE_GROUP)
//            message.setChatType(EMMessage.ChatType.GroupChat);
//发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    public void SendPicture(final String imagePath, final String toChatUsername)
    {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //imagePath为图片本地路径，false为不发送原图（默认超过100k的图片会压缩后发给对方），需要发送原图传true
                EMMessage message = EMMessage.createImageSendMessage(imagePath, false, toChatUsername);
                EMClient.getInstance().chatManager().sendMessage(message);
            }
        };
                timer.schedule(timerTask,0);



    }

    public void SendVoice()
    {}


    public void ShowToast(final String text)
    {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MethodConfig.context,text,Toast.LENGTH_LONG).show();
            }
        });
    }

}

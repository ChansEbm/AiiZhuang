package com.appbaba.platform.method;

import android.os.Handler;
import android.util.Log;
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
//                    ShowToast("登录环信成功");
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();
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
        MethodConfig.msgMap.put(toChatUsername,message.getMsgId() +"   "+toChatUsername);
        Log.e("text id","id:"+message.getMsgId()+"  发送人："+message.getFrom()+"   接收人："+toChatUsername);
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
                MethodConfig.msgMap.put(toChatUsername,message.getMsgId());
                Log.e("picture id","id:"+message.getMsgId() +"   "+toChatUsername);
            }
        };
                timer.schedule(timerTask,0);



    }

    public void SendVoice(String filePath,int length,String toChatUsername)
    {
        //filePath为语音文件路径，length为录音时间(秒)
        EMMessage message = EMMessage.createVoiceSendMessage(filePath, length, toChatUsername);
        Log.e("voice id","id:"+message.getMsgId() +"   "+toChatUsername);
        MethodConfig.msgMap.put(toChatUsername,message.getMsgId());
        EMClient.getInstance().chatManager().sendMessage(message);
    }


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

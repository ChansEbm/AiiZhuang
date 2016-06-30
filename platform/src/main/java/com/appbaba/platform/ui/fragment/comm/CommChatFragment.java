package com.appbaba.platform.ui.fragment.comm;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.FragmentChatBinding;
import com.appbaba.platform.ItemChatFriendViewBinding;
import com.appbaba.platform.ItemChatMeViewBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.adapters.MultiAdapter;
import com.appbaba.platform.base.BaseFragment;
import com.appbaba.platform.broadcast.UpdateUIBroadcast;
import com.appbaba.platform.entity.comm.HYMessageBean;
import com.appbaba.platform.impl.UpdateUIListener;
import com.appbaba.platform.method.HYMethod;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.method.SpaceItemDecoration;
import com.appbaba.platform.tools.AppTools;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by ruby on 2016/6/17.
 */
public class CommChatFragment extends BaseFragment implements UpdateUIListener{
    private FragmentChatBinding binding;
    private RecyclerView recyclerView;
    private List<HYMessageBean> list;
    private MultiAdapter<HYMessageBean> adapter;
    private HYMethod hyMethod = new HYMethod();
    private UpdateUIBroadcast receiver;

    public String toChatUsername="",toChatUserImage="",toChatNickName="";
    private boolean isVoice = false,isback = false;
    private Handler handler = new Handler();
    private EMConversation conversation;
    private Rect outRect = new Rect();
    private String nowFilepath;
    private long currentTime = 0;
    private boolean isInSendArea = true,isRecord= false;

    /**
     * 录音
     */
    private MediaRecorder recorder;
    private AudioManager audioManager;
    private MediaPlayer mMediaPlayer = new MediaPlayer();

    @Override
    protected void InitView() {
        binding = (FragmentChatBinding)viewDataBinding;
        recyclerView = binding.recycle;
        recyclerView.setLayoutManager(new org.solovyev.android.views.llm.LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));

    }

    @Override
    protected void InitData() {
        recorder = new MediaRecorder();
        audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_NORMAL);// 把模式调成听筒放音模式
        MethodConfig.nowMsgUsername = toChatUsername;
        conversation= EMClient.getInstance().chatManager().getConversation(toChatUsername);
        list = new ArrayList<>();
        adapter = new MultiAdapter<HYMessageBean>(getContext(),list,R.layout.item_chat_friend_view,R.layout.item_chat_me_view) {
            @Override
            public int getItemViewType(int position) {
                switch (list.get(position).getType())
                {
                    case 0:
                        return FIRST_LAYOUT;
                    case 1:
                        return SECOND_LAYOUT;
                    default:
                        return 0;
                }
            }

            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, HYMessageBean hyMessageBean) {
                    int type = hyMessageBean.getType();

                    if(type==0)
                    {
                        ItemChatFriendViewBinding itemChatFriendViewBinding = (ItemChatFriendViewBinding)viewDataBinding;
                        itemChatFriendViewBinding.dvItem.setImageURI(Uri.parse(toChatUserImage));
                        itemChatFriendViewBinding.tvTimeCount.setText("");
                        if(hyMessageBean.getWhich()==1) {
                            itemChatFriendViewBinding.tvItem.setText(hyMessageBean.getMsg());
                            itemChatFriendViewBinding.tvItem.setVisibility(View.VISIBLE);
                            itemChatFriendViewBinding.ivItem.setVisibility(View.GONE);
                            itemChatFriendViewBinding.ivVoiceItem.setVisibility(View.GONE);
                        }
                        else if(hyMessageBean.getWhich()==2)
                        {
                            itemChatFriendViewBinding.tvItem.setVisibility(View.GONE);
                            itemChatFriendViewBinding.ivItem.setVisibility(View.VISIBLE);
                            itemChatFriendViewBinding.ivVoiceItem.setVisibility(View.GONE);
                            int width = MethodConfig.metrics.widthPixels/4;
                            int height = hyMessageBean.getImageHeight();
                            if(height>0)
                            {
                                height = width*height/hyMessageBean.getImageWidth();
                            }
                            else
                            {
                                height = width;
                            }
                            Picasso.with(getContext()).load(hyMessageBean.getImage()).resize(width,height).into(itemChatFriendViewBinding.ivItem);
                        }
                        else
                        {
                            itemChatFriendViewBinding.tvItem.setVisibility(View.GONE);
                            itemChatFriendViewBinding.ivItem.setVisibility(View.GONE);
                            itemChatFriendViewBinding.ivVoiceItem.setVisibility(View.VISIBLE);
                            itemChatFriendViewBinding.tvTimeCount.setText(" "+hyMessageBean.getVoiceLength()+"``");
                            final String filePath = hyMessageBean.getVoice();
                            itemChatFriendViewBinding.ivVoiceItem.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    isPlay = true;
                                    playMusic(filePath,(ImageView)v,0);
                                }
                            });
                        }
                    }
                else
                    {
                        ItemChatMeViewBinding itemChatMeViewBinding = (ItemChatMeViewBinding)viewDataBinding;
                        itemChatMeViewBinding.dvItem.setImageURI(Uri.parse(MethodConfig.userInfo.getImgUrl()));
                        itemChatMeViewBinding.tvTimeCount.setText("");
                        if(hyMessageBean.getWhich()==1) {
                            itemChatMeViewBinding.tvItem.setText(hyMessageBean.getMsg());
                            itemChatMeViewBinding.tvItem.setVisibility(View.VISIBLE);
                            itemChatMeViewBinding.ivItem.setVisibility(View.GONE);
                            itemChatMeViewBinding.ivVoiceItem.setVisibility(View.GONE);
                        }
                        else if(hyMessageBean.getWhich()==2)
                        {
                            itemChatMeViewBinding.tvItem.setVisibility(View.GONE);
                            itemChatMeViewBinding.ivItem.setVisibility(View.VISIBLE);
                            itemChatMeViewBinding.ivVoiceItem.setVisibility(View.GONE);
                            int width = MethodConfig.metrics.widthPixels/4;
                            int height = hyMessageBean.getImageHeight();
                            if(height>0)
                            {
                                height = width*height/hyMessageBean.getImageWidth();
                            }
                            else
                            {
                                height = width;
                            }
                            Picasso.with(getContext()).load(hyMessageBean.getImage()).resize(width,height).into(itemChatMeViewBinding.ivItem);
                        }
                        else
                        {
                            itemChatMeViewBinding.tvItem.setVisibility(View.GONE);
                            itemChatMeViewBinding.ivItem.setVisibility(View.GONE);
                            itemChatMeViewBinding.ivVoiceItem.setVisibility(View.VISIBLE);
                            itemChatMeViewBinding.tvTimeCount.setText(" "+hyMessageBean.getVoiceLength()+"``");
                            final String filePath = hyMessageBean.getVoice();
                            itemChatMeViewBinding.ivVoiceItem.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    isPlay = true;
                                     playMusic(filePath,(ImageView)v,1);
                                }
                            });
                        }
                    }
            }
        };

        recyclerView.setAdapter(adapter);
        if(conversation!=null) {

              List<EMMessage>  msgs = conversation.loadMoreMsgFromDB("",100);
                for (int i = 0; i < msgs.size(); i++) {
                    ParseMsg(msgs.get(i));
                }
        }
        receiver = new UpdateUIBroadcast();
        receiver.setListener(this);
        AppTools.registerBroadcast(receiver,AppKeyMap.MESSAGE_ACTION);
    }

    @Override
    protected void InitEvent() {
        binding.edtMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideMethod();
            }
        });
        binding.edtMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShowKey = true;
            }
        });

        binding.edtMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ScrollToEnd();
                if(binding.linearMethod.getVisibility()==View.VISIBLE)
                {
                    binding.linearMethod.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(MethodConfig.EmptyEditText(binding.edtMsg))
                {
                    binding.btnSend.setVisibility(View.INVISIBLE);
                    binding.ivAdd.setVisibility(View.VISIBLE);
                }
                else
                {
                    binding.btnSend.setVisibility(View.VISIBLE);
                    binding.ivAdd.setVisibility(View.INVISIBLE);
                }
            }
        });
        binding.recycle.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {


                    Log.e("hide","变了……"+"   "+(bottom - oldBottom) +"  "+MethodConfig.metrics.heightPixels/3);
                    ScrollToEnd();
                if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom <= 0)) {
                    isShowKey = true;
                }
                else {
                    isShowKey = false;
                }
            }
        });

        binding.recycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState==RecyclerView.SCROLL_STATE_DRAGGING)
                {
                    Log.e("hide","我碰了"+newState);
                    HideSoftKeyBoard();
                    HideMethod();
                }
            }
        });

        binding.tvItemDetail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        binding.tvItemDetail.getDrawingRect(outRect);
                        binding.tvItemDetail.setText("松开 结束");
                        binding.tvItemDetail.setBackgroundResource(R.drawable.chat_tv_voice_press);
                        binding.linearVoiceBg.setVisibility(View.VISIBLE);
                        isInSendArea = true;
                        StartRecord();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(outRect.contains((int)event.getX(),(int)event.getY()))
                        {
                            isInSendArea = true;
                        }
                        else
                        {
                            if(Math.abs((int) (event.getY()-outRect.centerY()))>200)
                            {
                                isInSendArea = false;
                                binding.tvItemMsg.setText("松开手指取消发送");
                            }
                            else
                            {
                                isInSendArea = true;
                               binding.tvItemMsg.setText("手指上移取消发送");
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    {
                        binding.tvItemDetail.setText("按住 说话");
                        binding.tvItemDetail.setBackgroundResource(R.drawable.chat_tv_voice_normal);
                        binding.linearVoiceBg.setVisibility(View.INVISIBLE);
                        EndRecord();
                        if(isInSendArea) {
                            isInSendArea = false;
                            int time = (int) (System.currentTimeMillis() - currentTime) / 1000;
                            if (time >= 1) {
                                hyMethod.SendVoice(nowFilepath, time, toChatUsername);
                                HYMessageBean messageBean = new HYMessageBean();
                                messageBean.setVoice(nowFilepath);
                                messageBean.setVoiceLenght(time);
                                messageBean.setType(1);
                                list.add(messageBean);
                                adapter.notifyItemChanged(list.size());
                                ScrollToEnd();
                            }
                            else
                            {
                                Toast.makeText(getContext(),"时间太短啦",Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void InitListening() {
        binding.ivAdd.setOnClickListener(this);
        binding.btnSend.setOnClickListener(this);
        binding.ivVoice.setOnClickListener(this);
        binding.linearTakePhoto.setOnClickListener(this);
        binding.linearTakeCamera.setOnClickListener(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    @Override
    protected void OnClick(int id, View view) {
           switch (id)
           {
               case R.id.btn_send: {
                   hyMethod.SendText(binding.edtMsg.getText().toString().trim(), toChatUsername);
                   HYMessageBean bean = new HYMessageBean();
                   bean.setMsg(binding.edtMsg.getText().toString().trim());
                   bean.setType(1);
                   bean.setWhich(1);
                   list.add(bean);
                   adapter.notifyItemChanged(list.size());
                   binding.edtMsg.getText().clear();
                   ScrollToEnd();
               }
                   break;
               case R.id.iv_add:
                   HideSoftKeyBoard();
                   binding.linearMethod.setVisibility(binding.linearMethod.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                   break;
               case R.id.iv_voice:
                   if(isVoice)
                   {
                       binding.edtMsg.setVisibility(View.VISIBLE);
                       binding.tvItemDetail.setVisibility(View.GONE);
                       binding.ivVoice.setImageResource(R.mipmap.icon_voice);
                   }
                   else
                   {
                       binding.edtMsg.setVisibility(View.GONE);
                       binding.tvItemDetail.setVisibility(View.VISIBLE);
                       binding.ivVoice.setImageResource(R.mipmap.icon_keyboard);
                   }
                   HideMethod();
                   isVoice = !isVoice;
                   break;
               case R.id.linear_take_photo:
               {

                   HideMethod();
                   GalleryFinal.openCamera(101, new GalleryFinal.OnHanlderResultCallback() {
                       @Override
                       public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                           if(reqeustCode==101 && resultList.size()>0)
                           {
                               String path = resultList.get(0).getPhotoPath();
                               HYMessageBean bean = new HYMessageBean();
                               bean.setType(1);
                               bean.setImageWidth(resultList.get(0).getWidth());
                               bean.setImageHeight(resultList.get(0).getHeight());
                               bean.setImage("file://"+path);
                               bean.setWhich(2);
                               list.add(bean);

                               hyMethod.SendPicture(path,toChatUsername);
                               adapter.notifyItemChanged(list.size());
                               ScrollToEnd();
                           }
                       }

                       @Override
                       public void onHanlderFailure(int requestCode, String errorMsg) {

                       }
                   });
               }
                   break;
               case R.id.linear_take_camera:
               {
                   HideMethod();
                   GalleryFinal.openGallerySingle(100, new GalleryFinal.OnHanlderResultCallback() {
                       @Override
                       public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                           if(reqeustCode==100 && resultList.size()>0)
                           {
                               String path = resultList.get(0).getPhotoPath();

                               HYMessageBean bean = new HYMessageBean();
                               bean.setImage("file://"+path);
                               bean.setImageWidth(resultList.get(0).getWidth());
                               bean.setImageHeight(resultList.get(0).getHeight());
                               bean.setType(1);
                               bean.setWhich(2);
                               list.add(bean);
                               hyMethod.SendPicture(path,toChatUsername);
                               adapter.notifyItemChanged(list.size());
                               ScrollToEnd();
                           }
                       }

                       @Override
                       public void onHanlderFailure(int requestCode, String errorMsg) {

                       }
                   });
               }
                   break;
           }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_chat;
    }


    public void ParseMsg(EMMessage eMsg)
    {
        //把一条消息置为已读
        Log.e("bu","获取到的信息ID："+eMsg.getMsgId() +"  发送人："+eMsg.getFrom() +"  现在聊天的人："+toChatUsername);
        if(conversation!=null) {
            conversation.markMessageAsRead(eMsg.getMsgId());
        }
        else
        {
            conversation = EMClient.getInstance().chatManager().getConversation(toChatUsername);
        }
        switch (eMsg.getType()) {
            case TXT:
            {
                EMTextMessageBody msg = (EMTextMessageBody) eMsg.getBody();
                HYMessageBean bean = new HYMessageBean();
                bean.setWhich(1);
                if (eMsg.getFrom().equals(toChatUsername)) {
                    bean.setType(0);
                    bean.setMsg(msg.getMessage());
                    list.add(bean);
                    adapter.notifyItemChanged(list.size());
                    ScrollToEnd();
                }
                else if(eMsg.getFrom().equals(MethodConfig.userInfo.getEusername()))
                {
                    bean.setType(1);
                    bean.setMsg(msg.getMessage());
                    list.add(bean);
                    adapter.notifyItemChanged(list.size());
                    ScrollToEnd();
                }
                else {
                    //Toast.makeText(getContext(), eMsg.getFrom() + "给你发了：" + msg.getMessage(), Toast.LENGTH_LONG).show();
                }
        }
                break;
            case IMAGE:
            {
                EMImageMessageBody msg = (EMImageMessageBody)eMsg.getBody();
                HYMessageBean bean = new HYMessageBean();
                bean.setWhich(2);
                if (eMsg.getFrom().equals(toChatUsername)) {

                    bean.setType(0);
                    bean.setImageWidth(msg.getWidth());
                    bean.setImageHeight(msg.getHeight());
                    bean.setImage(msg.getRemoteUrl());
                    list.add(bean);
                    adapter.notifyItemChanged(list.size());
                    ScrollToEnd();
                }
                else if(eMsg.getFrom().equals(MethodConfig.userInfo.getEusername()))
                {
                    bean.setType(1);
                    bean.setImageWidth(msg.getWidth());
                    bean.setImageHeight(msg.getHeight());
                    bean.setImage(msg.getRemoteUrl());
                    list.add(bean);
                    adapter.notifyItemChanged(list.size());
                    ScrollToEnd();
                }else {
                    //Toast.makeText(getContext(), eMsg.getUserName() + "给你发了一张图片", Toast.LENGTH_LONG).show();
                }
            }
                break;
            case VOICE: {
                EMVoiceMessageBody msg = (EMVoiceMessageBody) eMsg.getBody();
                if (eMsg.getFrom().equals(toChatUsername)) {
                    HYMessageBean bean = new HYMessageBean();
                    bean.setType(0);
                    bean.setWhich(3);
                   // bean.setVoice(eMsg.getBody().);
                    bean.setVoiceLenght(msg.getLength());
                    bean.setVoice(msg.getRemoteUrl());
                    list.add(bean);
                    adapter.notifyItemChanged(list.size());
                    ScrollToEnd();
                } else if (eMsg.getFrom().equals(MethodConfig.userInfo.getEusername())) {
                    HYMessageBean bean = new HYMessageBean();
                    bean.setType(1);
                    bean.setWhich(3);
                    bean.setVoiceLenght(msg.getLength());
                    bean.setVoice(msg.getRemoteUrl());
                    list.add(bean);
                    adapter.notifyItemChanged(list.size());
                    ScrollToEnd();
                } else {
                    //Toast.makeText(getContext(), eMsg.getUserName() + "给你发了一张图片", Toast.LENGTH_LONG).show();
                }
            }
                break;
        }
    }

    public boolean Back()
    {
        if(MethodConfig.GetTicks()>1500)
        {
            isback = false;
        }
        if(isback) {
            HideSoftKeyBoard();
            return true;
        }
        else
        {
            HideSoftKeyBoard();
            HideMethod();
            isback = true;
        }
        return  false;
    }

    boolean isShowKey = false;
    public void  HideSoftKeyBoard()
    {

        if(isShowKey) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            Log.e("hide","show,执行隐藏");
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
//        if(imm.isActive()) {
//
//           // imm.hideSoftInputFromInputMethod(binding.edtMsg.getWindowToken(),0);
//
//            //binding.edtMsg.clearFocus();
//        }
        Log.e("hide","隐藏输入法");
    }

    public void ScrollToEnd()
    {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.e("hide","滚动到底部");
                binding.recycle.smoothScrollToPosition(list.size());
//                binding.scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    /**
     * 隐藏功能界面
     */
    public void HideMethod()
    {
        Log.e("hide","关闭当前功能框");
        binding.linearMethod.setVisibility(View.GONE );
    }

    @Override
    public void uiUpData(Intent intent) {
        if(intent.getAction().equals(AppKeyMap.MESSAGE_ACTION))
        {
            List<EMMessage> msgs = intent.getExtras().getParcelableArrayList("message");
            for(int i=0;i<msgs.size();i++)
            {
                ParseMsg(msgs.get(i));
            }
        }
    }

    public void DelFragment()
    {
        Log.e("chatfragment","注销监听");
        MethodConfig.nowMsgUsername = "";
        AppTools.unregisterBroadcast(receiver);
    }

    /**
     * 录音
     */
    public void StartRecord()
    {
        try {
            currentTime = System.currentTimeMillis();
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            nowFilepath = getContext().getCacheDir().getAbsolutePath() + File.separator + System.currentTimeMillis() + ".amr";
            recorder.setOutputFile(nowFilepath);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.prepare();
            recorder.start();
            isRecord= true;
            updateMicStatus();
        }
        catch (Exception ex)
        {
            Toast.makeText(getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void EndRecord()
    {
        try {
            recorder.stop();
            recorder.release();
            isRecord = false;
        }catch (Exception ex)
        {}

    }

    /**
     * 播放语音
     * @Description
     * @param name
     */
    private void playMusic(String name,ImageView view,int type) {
        try {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
                isPlay = false;
                imageIndex = 0;
            }
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(name);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            StartVoiceAnim(view,type);

            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    isPlay = false;
                    imageIndex = 0;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean isPlay = false; //是否正在播放录音
    private int imageIndex = 0;     //当前显示录音动画的图片index

    /**
     * 播放语音动画
     * @param imageView
     * @param type
     */
    public void StartVoiceAnim(final ImageView imageView, final int type)
    {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                int resID = 0;
                switch (type)
                {
                    case 0:
                    {
                        switch (imageIndex) {
                            case 0:
                                resID = R.mipmap.icon_left_voice_1;
                                break;
                            case 1:
                                resID = R.mipmap.icon_left_voice_2;
                                break;
                            case 2:
                                resID = R.mipmap.icon_left_voice_3;
                                break;

                        }
                    }
                        break;
                    case 1:
                    {
                        switch (imageIndex) {
                            case 0:
                                resID = R.mipmap.icon_right_voice_1;
                                break;
                            case 1:
                                resID = R.mipmap.icon_right_voice_2;
                                break;
                            case 2:
                                resID = R.mipmap.icon_right_voice_3;
                                break;

                        }
                    }
                        break;
                }
                SetImage(imageView, resID);
                imageIndex = (imageIndex+1)%3;
                if(isPlay) {
                   StartVoiceAnim(imageView,type);
                }

            }
        };
        timer.schedule(timerTask,300);
    }

    public void SetImage(final ImageView imageView, final int resID)
    {
        handler.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageResource(resID);
            }
        });
    }

    private int BASE = 1;
    public void updateMicStatus()
    {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                while (isRecord) {
                    if(isInSendArea) {
                        double ratio = (double) recorder.getMaxAmplitude() / BASE;
                        double db = 0;// 分贝
                        if (ratio > 1)
                            db = 20 * Math.log10(ratio);

                        binding.ivVoiceBg.setHeight(db);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Log.e("sdf","run");
            }
        };
        timer.schedule(timerTask,0);

    }


}

package com.appbaba.platform.ui.fragment.comm;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.appbaba.platform.FragmentChatBinding;
import com.appbaba.platform.ItemChatFriendViewBinding;
import com.appbaba.platform.ItemChatMeViewBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.adapters.MultiAdapter;
import com.appbaba.platform.base.BaseFragment;
import com.appbaba.platform.entity.comm.HYMessageBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.BinderOnItemClickListener;
import com.appbaba.platform.method.HYMethod;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.method.SpaceItemDecoration;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by ruby on 2016/6/17.
 */
public class CommChatFragment extends BaseFragment{
    private FragmentChatBinding binding;
    private RecyclerView recyclerView;
    private List<HYMessageBean> list;
    private MultiAdapter<HYMessageBean> adapter;
    private HYMethod hyMethod = new HYMethod();

    public String toChatUsername="",toChatUserImage="",toChatNickName="";
    private boolean isVoice = false;
    private Handler handler = new Handler();
    private EMMessageListener messageListener;


    @Override
    protected void InitView() {
        binding = (FragmentChatBinding)viewDataBinding;
        recyclerView = binding.recycle;
        recyclerView.setLayoutManager(new org.solovyev.android.views.llm.LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));

    }

    @Override
    protected void InitData() {
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
                Log.e("ew2r","||||||||||||      "+position+"     "+hyMessageBean.getMsg() + "     ==========               "+hyMessageBean.getImage());
                    if(type==0)
                    {
                        ItemChatFriendViewBinding itemChatFriendViewBinding = (ItemChatFriendViewBinding)viewDataBinding;
                        itemChatFriendViewBinding.dvItem.setImageURI(Uri.parse(toChatUserImage));
                        itemChatFriendViewBinding.tvItem.setText(hyMessageBean.getMsg());
                    }
                else
                    {
                        ItemChatMeViewBinding itemChatMeViewBinding = (ItemChatMeViewBinding)viewDataBinding;
                        itemChatMeViewBinding.dvItem.setImageURI(Uri.parse(MethodConfig.userInfo.getImgUrl()));
                        if(TextUtils.isEmpty(hyMessageBean.getImage())) {
                            itemChatMeViewBinding.tvItem.setText(hyMessageBean.getMsg());
                            itemChatMeViewBinding.ivItem.setVisibility(View.GONE);
                        }
                        else
                        {
                            itemChatMeViewBinding.tvItem.setVisibility(View.GONE);
                            itemChatMeViewBinding.ivItem.setVisibility(View.VISIBLE);
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
//                            itemChatMeViewBinding.ivItem.setImageURI(Uri.parse());
                        }
                    }
            }
        };

        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void InitEvent() {
        binding.edtMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                     recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView,new RecyclerView.State(),list.size());
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
        binding.scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    ScrollToEnd();
                    HideSoftKeyBoard();
                    if(list.size()==0 || (list.size()>0 && TextUtils.isEmpty(list.get(list.size()-1).getMsg()))) {
                        HideMethod();
                    }
                }
                return false;
            }
        });

        messageListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                for(int i=0;i<list.size();i++)
                {
                    ParseMsg(list.get(i));
                }
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
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
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
                   list.add(bean);
                   adapter.notifyItemChanged(list.size());
                   binding.edtMsg.getText().clear();
                   ScrollToEnd();
               }
                   break;
               case R.id.iv_add:
                   HideSoftKeyBoard();
                   binding.linearMethod.setVisibility(View.VISIBLE);
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
        switch (eMsg.getType()) {
            case TXT:
            {
                EMTextMessageBody msg = (EMTextMessageBody) eMsg.getBody();
                if (eMsg.getFrom().equals(toChatUsername)) {
                    HYMessageBean bean = new HYMessageBean();
                    bean.setType(0);
                    bean.setMsg(msg.getMessage());
                    list.add(bean);
                    adapter.notifyItemChanged(list.size());
                    ScrollToEnd();
                } else {
                    Toast.makeText(getContext(), eMsg.getUserName() + "给你发了：" + msg.getMessage(), Toast.LENGTH_LONG).show();
                }
        }
                break;
            case IMAGE:
            {
                EMImageMessageBody msg = (EMImageMessageBody)eMsg.getBody();
                if (eMsg.getFrom().equals(toChatUsername)) {
                    HYMessageBean bean = new HYMessageBean();
                    bean.setType(0);
                    bean.setImageWidth(msg.getWidth());
                    bean.setImageHeight(msg.getHeight());
                    bean.setImage(msg.getRemoteUrl());
                    list.add(bean);
                    adapter.notifyItemChanged(list.size());
                    ScrollToEnd();
                } else {
                    Toast.makeText(getContext(), eMsg.getUserName() + "给你发了一张图片", Toast.LENGTH_LONG).show();
                }
            }
                break;
            case VOICE:
            {}
                break;
        }
    }

    public void  HideSoftKeyBoard()
    {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()) {
            Log.e("sdf","234");
            imm.hideSoftInputFromInputMethod(binding.edtMsg.getWindowToken(),0);
            binding.edtMsg.clearFocus();
        }
        else
        {
            Log.e("sdf","23423444sdfssfdd23");
        }
    }

    public void ScrollToEnd()
    {
        handler.post(new Runnable() {
            @Override
            public void run() {
                binding.scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    public void HideMethod()
    {
        binding.linearMethod.setVisibility(View.GONE );
    }
}

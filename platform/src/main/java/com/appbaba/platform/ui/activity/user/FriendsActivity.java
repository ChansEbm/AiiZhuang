package com.appbaba.platform.ui.activity.user;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Telephony;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.appbaba.platform.ActivityFriendsBinding;
import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.ItemFriendViewBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.base.BaseActivity;
import com.appbaba.platform.broadcast.UpdateUIBroadcast;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.User.FriendsBean;
import com.appbaba.platform.entity.comm.HYMessageBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.BinderOnItemClickListener;
import com.appbaba.platform.impl.UpdateUIListener;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.method.SpaceItemDecoration;
import com.appbaba.platform.tools.AppTools;
import com.appbaba.platform.ui.activity.comm.CommChatActivity;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by ruby on 2016/6/17.
 */
public class FriendsActivity extends BaseActivity implements BinderOnItemClickListener,UpdateUIListener{

    private ActivityFriendsBinding binding;
    private RecyclerView recyclerView;

    private List<FriendsBean.FriendsListEntity> list;
    private CommonBinderAdapter<FriendsBean.FriendsListEntity> adapter;
    private List<EMMessage> msgs;

    private int currentPage=1,num=12;
    private UpdateUIBroadcast receiver;

    @Override
    protected void InitView() {
        binding = (ActivityFriendsBinding)viewDataBinding;
        binding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        binding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.icon_back);
        binding.includeTopTitle.title.setText("聊天列表");
        binding.includeTopTitle.title.setTextColor(Color.BLACK);

        recyclerView = binding.recycle;

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpaceItemDecoration(2));
    }

    @Override
    protected void InitData() {

       list = new ArrayList<>();
        adapter = new CommonBinderAdapter<FriendsBean.FriendsListEntity>(this,R.layout.item_friend_view,list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, FriendsBean.FriendsListEntity friendsListEntity) {
                ItemFriendViewBinding itemFriendViewBinding = (ItemFriendViewBinding)viewDataBinding;
                itemFriendViewBinding.setItem(friendsListEntity);
                itemFriendViewBinding.dvItem.setImageURI(Uri.parse(friendsListEntity.getPicture()));
                try {
                    if(!TextUtils.isEmpty(friendsListEntity.getEasemob_username())) {
                        EMConversation emConversation = EMClient.getInstance().chatManager().getConversation(friendsListEntity.getEasemob_username());
                        List<EMMessage> emMessages = emConversation.getAllMessages();
                        if(emMessages.size()==0)
                        {
                            itemFriendViewBinding.tvMsg.setText("");
                        }
                        else
                        {
                            String text = ParseMsg(emMessages.get(emMessages.size()-1), friendsListEntity.getEasemob_username());
                            Log.e("hide", "哈哈哈" + position + " friend :" + friendsListEntity.getEasemob_username() + "  说了：" + text);
                            itemFriendViewBinding.tvMsg.setText(text);
                        }

                        int count = emConversation.getUnreadMsgCount();
                        Log.e("hide","获取未读消息："+count);
                        if (count == 0) {
                            itemFriendViewBinding.tvItemCount.setVisibility(View.GONE);
                        } else {
                            itemFriendViewBinding.tvItemCount.setVisibility(View.VISIBLE);
                            itemFriendViewBinding.tvItemCount.setText("" + count);
                        }
                    }
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }

            }
        };
        recyclerView.setAdapter(adapter);
        if(MethodConfig.IsLogin()) {
            networkModel.GetFriendList(MethodConfig.userInfo.getToken(),currentPage,num, NetworkParams.CUPCAKE);
        }

        receiver = new UpdateUIBroadcast();
        receiver.setListener(this);
        AppTools.registerBroadcast(receiver,AppKeyMap.MESSAGE_ACTION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter!=null)
        {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppTools.unregisterBroadcast(receiver);
    }

    @Override
    protected void InitEvent() {
        binding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void InitListening() {

        adapter.setBinderOnItemClickListener(this);
    }

    @Override
    protected void OnClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_friends;
    }

    @Override
    public void onJsonObjectSuccess(BaseBean baseBean, NetworkParams paramsCode) {
       if(paramsCode==NetworkParams.CUPCAKE)
       {
           if(baseBean.getErrorcode()==0)
           {
               FriendsBean friendsBean = (FriendsBean)baseBean;
               list.addAll(friendsBean.getFriends_list());
               adapter.notifyDataSetChanged();
           }
       }
    }


    public String ParseMsg(EMMessage eMsg,String toUsername)
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
            case VOICE:
            {
                    return "[语音]";
            }
        }
        return "";
    }

    @Override
    public void onBinderItemClick(View clickItem, int parentId, int pos) {
        String id =  list.get(pos).getEasemob_username();
        String name = list.get(pos).getName();
        String image = list.get(pos).getPicture();
        Intent intent = new Intent(this, CommChatActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("image",image);
        intent.putExtra("id",id);
        if(EMClient.getInstance().chatManager().getConversation(id)!=null)
        EMClient.getInstance().chatManager().getConversation(id).markAllMessagesAsRead();
        startActivity(intent);
    }

    @Override
    public void onBinderItemLongClick(View clickItem, int parentId, int pos) {

    }

    @Override
    public void uiUpData(Intent intent) {
        if(intent.getAction().equals(AppKeyMap.MESSAGE_ACTION))
        {
            adapter.notifyDataSetChanged();
        }
    }


}

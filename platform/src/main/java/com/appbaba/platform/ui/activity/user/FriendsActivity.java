package com.appbaba.platform.ui.activity.user;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appbaba.platform.ActivityFriendsBinding;
import com.appbaba.platform.ItemFriendViewBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.base.BaseActivity;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.User.FriendsBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.BinderOnItemClickListener;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.method.SpaceItemDecoration;
import com.appbaba.platform.ui.activity.comm.CommChatActivity;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/6/17.
 */
public class FriendsActivity extends BaseActivity implements EMMessageListener,BinderOnItemClickListener{

    private ActivityFriendsBinding binding;
    private RecyclerView recyclerView;

    private List<FriendsBean.FriendsListEntity> list;
    private CommonBinderAdapter<FriendsBean.FriendsListEntity> adapter;

    private int currentPage=1,num=12;

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
            }
        };
        recyclerView.setAdapter(adapter);
        if(MethodConfig.IsLogin()) {
            networkModel.GetFriendList(MethodConfig.userInfo.getToken(),currentPage,num, NetworkParams.CUPCAKE);
        }
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

    @Override
    public void onBinderItemClick(View clickItem, int parentId, int pos) {
        String id =  list.get(pos).getEasemob_username();
        String name = list.get(pos).getName();
        String image = list.get(pos).getPicture();
        Intent intent = new Intent(this, CommChatActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("image",image);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    @Override
    public void onBinderItemLongClick(View clickItem, int parentId, int pos) {

    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {

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
}

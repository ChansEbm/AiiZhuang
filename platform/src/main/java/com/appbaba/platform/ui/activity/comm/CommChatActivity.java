package com.appbaba.platform.ui.activity.comm;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.R;
import com.appbaba.platform.base.BaseActivity;
import com.appbaba.platform.broadcast.UpdateUIBroadcast;
import com.appbaba.platform.databinding.ActivityChatBinding;
import com.appbaba.platform.impl.UpdateUIListener;
import com.appbaba.platform.ui.fragment.comm.CommChatFragment;
import com.appbaba.platform.widget.ChangeValueDialog;

/**
 * Created by ruby on 2016/6/20.
 */
public class CommChatActivity extends BaseActivity{

    private ActivityChatBinding binding;
    private CommChatFragment chatFragment;

    @Override
    protected void InitView() {
        binding = (ActivityChatBinding)viewDataBinding;
        binding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        binding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.icon_back);
        binding.includeTopTitle.title.setTextColor(Color.BLACK);
    }

    @Override
    protected void InitData() {
        String name = getIntent().getStringExtra("name");
        String id = getIntent().getStringExtra("id");
        String image = getIntent().getStringExtra("image");
        binding.includeTopTitle.title.setText(name);


        chatFragment = new CommChatFragment();
        chatFragment.toChatNickName = name;
        chatFragment.toChatUsername = id;
        chatFragment.toChatUserImage = image;

        getSupportFragmentManager().beginTransaction().add(R.id.frame_content,chatFragment).commit();
    }

    @Override
    protected void InitEvent() {
        binding.includeTopTitle.toolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatFragment.DelFragment();
                getSupportFragmentManager().beginTransaction().remove(chatFragment);
                chatFragment = null;
                Log.e("fragment","del fragment");
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        boolean isBack = chatFragment.Back();
        if(isBack) {
            chatFragment.DelFragment();
            getSupportFragmentManager().beginTransaction().remove(chatFragment);
            chatFragment = null;
            Log.e("fragment","del fragment");
            finish();
        }
    }

    @Override
    protected void InitListening() {

    }

    @Override
    protected void OnClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_comm_chat;
    }

}

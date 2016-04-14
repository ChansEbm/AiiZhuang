package com.appbaba.iz.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.appbaba.iz.FragmentFriendAddClientBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.impl.UpdateUIListener;
import com.appbaba.iz.ui.activity.TransferActivity;

/**
 * Created by ruby on 2016/4/12.
 */
public class FriendsItemAddClientFragment extends BaseFgm {
    FragmentFriendAddClientBinding addClientBinding;

    @Override
    protected void initViews() {

        addClientBinding = (FragmentFriendAddClientBinding)viewDataBinding;
        addClientBinding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.more_arrow_dark_left);
        addClientBinding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        addClientBinding.includeTopTitle.title.setText(R.string.friends_fragment_client_add_title);
        addClientBinding.includeTopTitle.title.setTextColor(Color.BLACK);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_friend_item_add_client;
    }


}

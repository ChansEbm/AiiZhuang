package com.appbaba.iz.ui.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.appbaba.iz.FragmentMoreChangeBinding;
import com.appbaba.iz.FragmentMorePersonBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseFgm;

/**
 * Created by ruby on 2016/4/8.
 */
public class MoreItemChangePWDFragment extends BaseFgm {
    FragmentMoreChangeBinding changeBinding;
    @Override
    protected void initViews() {
        changeBinding = (FragmentMoreChangeBinding)viewDataBinding;
        changeBinding.includeTopTitle.title.setText(R.string.more_fragment_change_pwd);
        changeBinding.includeTopTitle.title.setTextColor(Color.BLACK);
        changeBinding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        changeBinding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.more_arrow_dark_left);

    }

    @Override
    protected void initEvents() {
         changeBinding.includeTopTitle.toolBar.setNavigationOnClickListener(this);
    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {
        switch (id)
        {
            case android.R.id.home:
                ((Activity)getContext()).finish();
                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_more_item_change_pwd;
    }
}

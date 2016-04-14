package com.appbaba.iz.ui.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.appbaba.iz.FragmentMorePersonBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseFgm;

/**
 * Created by ruby on 2016/4/8.
 */
public class MoreItemPersonFragment extends BaseFgm {
    FragmentMorePersonBinding personBinding;
    @Override
    protected void initViews() {
        personBinding = (FragmentMorePersonBinding)viewDataBinding;
        personBinding.includeTopTitle.title.setText(R.string.more_fragment_person);
        personBinding.includeTopTitle.title.setTextColor(Color.BLACK);
        personBinding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        personBinding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.more_arrow_dark_left);
    }

    @Override
    protected void initEvents() {
         personBinding.includeTopTitle.toolBar.setNavigationOnClickListener(this);
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
        return R.layout.fragment_more_item_person;
    }
}

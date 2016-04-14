package com.appbaba.iz.ui.fragment;

import android.graphics.Color;
import android.view.View;

import com.appbaba.iz.FragmentMoreBackBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseFgm;

/**
 * Created by ruby on 2016/4/13.
 */
public class MoreItemBackFragment extends BaseFgm {
    FragmentMoreBackBinding backBinding;
    @Override
    protected void initViews() {
        backBinding = (FragmentMoreBackBinding)viewDataBinding;
        backBinding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        backBinding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.more_arrow_dark_left);
        backBinding.includeTopTitle.title.setText(R.string.more_fragment_feedback);
        backBinding.includeTopTitle.title.setTextColor(Color.BLACK);
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
        return R.layout.fragment_more_item_back;
    }
}

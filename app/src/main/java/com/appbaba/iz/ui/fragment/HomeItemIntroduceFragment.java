package com.appbaba.iz.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.appbaba.iz.FragmentHomeTopIntroduceBinding;
import com.appbaba.iz.FragmentHomeTopScanBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseFgm;

/**
 * Created by ruby on 2016/4/5.
 */
public class HomeItemIntroduceFragment extends BaseFgm {
    private FragmentHomeTopIntroduceBinding introduceBinding;
    private TextView tv_top_simple_title,iv_back;
    @Override
    protected void initViews() {
        introduceBinding = (FragmentHomeTopIntroduceBinding)viewDataBinding;
        tv_top_simple_title = introduceBinding.includeTopSimpleTitle.tvTopSimpleTitle;
        iv_back = introduceBinding.includeTopSimpleTitle.Back;
        tv_top_simple_title.setText(R.string.popup_pinpaijieshao);
    }

    @Override
    protected void initEvents() {
        iv_back.setOnClickListener(this);
    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {
        switch (id)
        {
            case R.id.iv_back:

                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home_item_introduce;
    }
}

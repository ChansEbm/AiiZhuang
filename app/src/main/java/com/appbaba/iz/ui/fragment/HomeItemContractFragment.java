package com.appbaba.iz.ui.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.appbaba.iz.FragmentHomeTopContractBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseFgm;

/**
 * Created by ruby on 2016/4/5.
 */
public class HomeItemContractFragment extends BaseFgm {
    private FragmentHomeTopContractBinding contractBinding;
    TextView tv_top_simple_title,iv_back;

    @Override
    protected void initViews() {
         contractBinding = (FragmentHomeTopContractBinding)viewDataBinding;
        tv_top_simple_title = contractBinding.includeTopSimpleTitle.tvTopSimpleTitle;
        iv_back = contractBinding.includeTopSimpleTitle.Back;
        tv_top_simple_title.setText(R.string.popup_bodadianhau);
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
        return R.layout.fragment_home_item_contract;
    }
}

package com.appbaba.iz.ui.fragment;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.TextView;

import com.appbaba.iz.FragmentHomeBinding;
import com.appbaba.iz.FragmentHomeTopScanBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.TopTitleBinding;
import com.appbaba.iz.base.BaseFgm;

/**
 * Created by ruby on 2016/4/5.
 */
public class HomeItemScanFragment extends BaseFgm {

    FragmentHomeTopScanBinding topScanBinding;
    TextView tv_top_simple_title,iv_back;
    @Override
    protected void initViews() {
         tv_top_simple_title = topScanBinding.includeTopSimpleTitle.tvTopSimpleTitle;
         iv_back = topScanBinding.includeTopSimpleTitle.Back;
         tv_top_simple_title.setText("扫一扫");
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
        return R.layout.fragment_home_item_scan;
    }
}

package com.appbaba.platform.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appbaba.platform.ActivityDesignWorkDetailBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.base.BaseActivity;

import java.util.List;

/**
 * Created by ruby on 2016/5/13.
 */
public class DesignWorkDetailActivity extends BaseActivity{
    private ActivityDesignWorkDetailBinding binding;
    private RecyclerView recyclerView;

    private CommonBinderAdapter<Object> adapter;
    private List<Object> list;

    @Override
    protected void InitView() {
        binding = (ActivityDesignWorkDetailBinding)viewDataBinding;
        recyclerView = binding.recycle;
    }

    @Override
    protected void InitData() {

    }

    @Override
    protected void InitEvent() {

    }

    @Override
    protected void InitListening() {

    }

    @Override
    protected void OnClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_design_work_detail;
    }
}

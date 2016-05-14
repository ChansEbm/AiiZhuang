package com.appbaba.platform.ui.activity;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appbaba.platform.ActivityDesignWorksBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.base.BaseActivity;
import com.appbaba.platform.impl.BinderOnItemClickListener;
import com.appbaba.platform.method.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/5/12.
 */
public class DesignWorksActivity extends BaseActivity implements BinderOnItemClickListener{

    private ActivityDesignWorksBinding binding;
    private RecyclerView recyclerView;

    private CommonBinderAdapter<Object> adapter;
    private List<Object> list;

    @Override
    protected void InitView() {
        binding = (ActivityDesignWorksBinding)viewDataBinding;
        binding.toolBar.setNavigationIcon(R.mipmap.icon_back);
        setSupportActionBar( binding.toolBar);
        recyclerView = binding.recycle;

    }

    @Override
    protected void InitData() {
        list = new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            list.add(new Object());
        }
        adapter = new CommonBinderAdapter<Object>(this,R.layout.item_inspiration_view,list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, Object o) {

            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void InitEvent() {

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
        return R.layout.activity_design_works;
    }

    @Override
    public void onBinderItemClick(View clickItem, int parentId, int pos) {
        StartActivity(DesignWorkDetailActivity.class);
    }

    @Override
    public void onBinderItemLongClick(View clickItem, int parentId, int pos) {

    }
}

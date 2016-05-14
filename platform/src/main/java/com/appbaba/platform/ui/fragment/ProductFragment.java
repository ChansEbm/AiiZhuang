package com.appbaba.platform.ui.fragment;


import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.appbaba.platform.FragmentProductBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.base.BaseFragment;
import com.appbaba.platform.impl.BinderOnItemClickListener;
import com.appbaba.platform.method.GridSpacingItemDecoration;
import com.appbaba.platform.method.SpaceItemDecoration;
import com.appbaba.platform.ui.activity.ProductDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/5/4.
 */
public class ProductFragment extends BaseFragment implements BinderOnItemClickListener{
    private FragmentProductBinding binding;
    private RecyclerView recyclerView;

    private CommonBinderAdapter<Object> adapter;
    private List<Object> list;

    @Override
    protected void InitView() {
        binding = (FragmentProductBinding)viewDataBinding;
        binding.includeTopTitle.title.setText("商品");
        binding.includeTopTitle.title.setTextColor(Color.BLACK);
        binding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        binding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.icon_grid);
        binding.includeTopTitle.toolBar.getMenu().add(0,R.id.action_search,0,"").setIcon(R.mipmap.icon_search_black_bg).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        recyclerView = binding.recycle;
    }

    @Override
    protected void InitData() {
        list = new ArrayList<>();
        for (int i=0;i<60;i++)
        {
            list.add(new Object());
        }
adapter = new CommonBinderAdapter<Object>(getContext(),R.layout.item_product_view,list) {
    @Override
    public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, Object o) {

    }
};
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,5,true));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void InitEvent() {

    }

    @Override
    protected void InitListening() {
        binding.includeTopTitle.toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        binding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        adapter.setBinderOnItemClickListener(this);
    }

    @Override
    protected void OnClick(int id, View view) {

    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_product;
    }

    @Override
    public void onBinderItemClick(View clickItem, int parentId, int pos) {
        StartActivity(ProductDetailActivity.class);
    }

    @Override
    public void onBinderItemLongClick(View clickItem, int parentId, int pos) {

    }
}

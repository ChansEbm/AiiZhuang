package com.appbaba.platform.ui.fragment;


import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.appbaba.platform.FragmentProductBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.base.BaseFragment;
import com.appbaba.platform.impl.AnimationCallBack;
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
    private int gridMode = 0;
    private GridSpacingItemDecoration gridSpacingItemDecoration;
    private SpaceItemDecoration spaceItemDecoration;
    private AnimationCallBack callBack;

    @Override
    protected void InitView() {
        binding = (FragmentProductBinding)viewDataBinding;
//        binding.includeTopTitle.title.setText("商品");
//        binding.includeTopTitle.title.setTextColor(Color.BLACK);
//        binding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
//        binding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.icon_grid);
//        binding.includeTopTitle.toolBar.getMenu().add(0,R.id.action_search,0,"").setIcon(R.mipmap.icon_search_black_bg).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        recyclerView = binding.recycle;
    }

    @Override
    protected void InitData() {
        list = new ArrayList<>();
        for (int i=0;i<60;i++)
        {
            list.add(new Object());
        }
      spaceItemDecoration =  new SpaceItemDecoration(2);
      gridSpacingItemDecoration =  new GridSpacingItemDecoration(2,5,true);
        SetRecyclerViewData();
    }

    @Override
    protected void InitEvent() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState==1)
                {
                    callBack.StartAnimation();
                }
                else if(newState==0)
                {
                    callBack.EndAnimation();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

    @Override
    protected void InitListening() {

        adapter.setBinderOnItemClickListener(this);
        binding.ivTopLeft.setOnClickListener(this);
        binding.ivTopRight.setOnClickListener(this);
    }

    @Override
    protected void OnClick(int id, View view) {
         switch (id)
         {
             case R.id.iv_top_left:
                 if(gridMode==0)
                 {
                     gridMode = 1;
                     ((ImageView)view).setImageResource(R.mipmap.icon_list);
                 }
                 else
                 {
                     gridMode = 0;
                     ((ImageView)view).setImageResource(R.mipmap.icon_grid);
                 }
                 SetRecyclerViewData();
                 break;
         }
    }

    public void SetRecyclerViewData()
    {
        if (gridMode==0)
        {
            adapter = new CommonBinderAdapter<Object>(getContext(),R.layout.item_product_view,list) {
                @Override
                public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, Object o) {

                }
            };
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
           recyclerView.removeItemDecoration(spaceItemDecoration);
            recyclerView.addItemDecoration(gridSpacingItemDecoration);
            recyclerView.setAdapter(adapter);
        }
        else
        {
            adapter = new CommonBinderAdapter<Object>(getContext(),R.layout.item_collection_view,list) {
                @Override
                public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, Object o) {

                }
            };
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.removeItemDecoration(gridSpacingItemDecoration);
            recyclerView.addItemDecoration(spaceItemDecoration);
            recyclerView.setAdapter(adapter);
        }
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

    public void  setCallBack(AnimationCallBack callBack)
    {
        this.callBack = callBack;
    }
}

package com.appbaba.iz.ui.fragment;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.appbaba.iz.AppKeyMap;
import com.appbaba.iz.FragmentFavouriteBinding;
import com.appbaba.iz.ItemFavouriteBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.adapters.CommonBinderAdapter;
import com.appbaba.iz.adapters.CommonBinderHolder;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.entity.Favourite.FavouriteBean;
import com.appbaba.iz.entity.Friends.FriendsClientBean;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.impl.BinderOnItemClickListener;
import com.appbaba.iz.impl.UpdateUIListener;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.method.SpaceItemDecoration;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.tools.LogTools;
import com.appbaba.iz.ui.activity.TransferActivity;
import com.squareup.picasso.Picasso;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.widget.HorizontalListView;

/**
 * Created by ruby on 2016/4/1.
 */
public class FavouriteFragment extends BaseFgm implements BinderOnItemClickListener {
    private FragmentFavouriteBinding favouriteBinding;
    private RecyclerView recyclerView;
    private List<FavouriteBean.ListEntity> list;
    private  CommonBinderAdapter<FavouriteBean.ListEntity> adapter;
    private  int height,page=1,pagesize=10;
    private  boolean canPageUper = true;

    @Override
    protected void initViews() {
        favouriteBinding = (FragmentFavouriteBinding) viewDataBinding;
        favouriteBinding.includeTopTitle.toolBar.setBackgroundResource(R.color.white);
        favouriteBinding.includeTopTitle.title.setText(R.string.main_activity_bottom_favourite);
        favouriteBinding.includeTopTitle.title.setTextColor(Color.BLACK);
        height = MethodConfig.GetHeightFor4v3(MethodConfig.metrics.widthPixels);
        recyclerView = favouriteBinding.rcyFavourite;
        favouriteBinding.swRefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);

        list = new ArrayList<>();

        adapter = new CommonBinderAdapter<FavouriteBean.ListEntity>(getContext(),R.layout.item_favourite_view,list){

            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, FavouriteBean.ListEntity entity) {
                ItemFavouriteBinding itemFavouriteBinding = (ItemFavouriteBinding)viewDataBinding;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height);
                Picasso.with(getContext()).load(entity.getThumb()).into(itemFavouriteBinding.ivItemFavourite);
                itemFavouriteBinding.ivItemFavourite.setLayoutParams(params);
                itemFavouriteBinding.setItem(entity);
            }
        };

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       // recyclerView.setLayoutParams(new LinearLayout.LayoutParams(MethodConfig.metrics.widthPixels,MethodConfig.metrics.heightPixels-100));
        recyclerView.addItemDecoration(new SpaceItemDecoration(MethodConfig.dip2px(getContext(),15)));


        networkModel.HomeSubject(AppTools.getStringSharedPreferences(AppKeyMap.AUTH,""),page,pagesize, NetworkParams.SUBJECT);
    }

    @Override
    public void onBinderItemClick(View view,int parentId ,int pos) {

        Intent intent = new Intent(getContext(),TransferActivity.class);
        intent.putExtra("fragment",4);
        intent.putExtra("title",list.get(pos).getTitle());
        intent.putExtra("id", list.get(pos).getSubject_id());
        startActivity(intent);
    }

    @Override
    protected void initEvents() {
        adapter.setBinderOnItemClickListener(this);
         favouriteBinding.scrollView.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 if(event.getAction()==MotionEvent.ACTION_UP && canPageUper) {
                     View childView = favouriteBinding.scrollView.getChildAt(0);
                     if (childView!=null && childView.getHeight()==favouriteBinding.scrollView.getScrollY()+favouriteBinding.scrollView.getHeight()) {
                         page++;
                         networkModel.HomeSubject(AppTools.getStringSharedPreferences(AppKeyMap.AUTH,""),page,pagesize, NetworkParams.SUBJECT);
                     }
                 }
                 return false;
             }
         });

        favouriteBinding.swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                page=0;
                canPageUper = true;
                networkModel.HomeSubject(AppTools.getStringSharedPreferences(AppKeyMap.AUTH,""),page,pagesize, NetworkParams.SUBJECT);
            }
        });
    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_favourite;
    }

    @Override
    public void onJsonObjectSuccess(Object t, NetworkParams paramsCode) {
        if(paramsCode==NetworkParams.SUBJECT)
        {
            FavouriteBean bean = (FavouriteBean)t;
            if(bean.getErrorcode()==0)
            {
                if(bean.getList().size()<pagesize)
                {
                    canPageUper = false;//不需要再添加数据
                }
                list.addAll(bean.getList());
                adapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        }
        if(favouriteBinding.swRefresh.isRefreshing())
        {
            favouriteBinding.swRefresh.setRefreshing(false);
        }
    }
}

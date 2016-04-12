package com.appbaba.iz.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appbaba.iz.FragmentFavouriteBinding;
import com.appbaba.iz.ItemFavouriteBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.adapters.CommonBinderAdapter;
import com.appbaba.iz.adapters.CommonBinderHolder;
import com.appbaba.iz.adapters.CommonRecyclerAdapter;
import com.appbaba.iz.adapters.RecyclerViewHolder;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.entity.Favourite.FavouriteBean;
import com.appbaba.iz.impl.BinderOnItemClickListener;
import com.appbaba.iz.impl.UpdateUIListener;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.method.SpaceItemDecoration;
import com.appbaba.iz.ui.activity.TransferActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/4/1.
 */
public class FavouriteFragment extends BaseFgm implements BinderOnItemClickListener{
    private FragmentFavouriteBinding favouriteBinding;
    private RecyclerView recyclerView;
    private List<FavouriteBean> list;
    private  CommonBinderAdapter<FavouriteBean> adapter;
    private  int height;

    @Override
    protected void initViews() {
        favouriteBinding = (FragmentFavouriteBinding)viewDataBinding;
        favouriteBinding.includeTopTitle.toolBar.setBackgroundResource(R.color.white);
        favouriteBinding.includeTopTitle.title.setText(R.string.main_activity_bottom_favourite);
        favouriteBinding.includeTopTitle.title.setTextColor(Color.BLACK);
         height = MethodConfig.GetHeightFor16v9(MethodConfig.metrics.widthPixels);
        recyclerView = favouriteBinding.rcyFavourite;
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,MethodConfig.metrics.heightPixels);
//        recyclerView.setLayoutParams(params);
        list = new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            FavouriteBean bean = new FavouriteBean();
            bean.setDetail("qingxin tuoshu  hah ");
            bean.setLike(100+i);
            bean.setSeen(200+i);
            bean.setUrl("www.baidu.com");
            bean.setTitle("这是一个测试" + i);
            list.add(bean);
        }

        adapter = new CommonBinderAdapter<FavouriteBean>(getContext(),R.layout.item_favourite_view,list){

            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, FavouriteBean favouriteBean) {

                      ItemFavouriteBinding itemFavouriteBinding = (ItemFavouriteBinding)viewDataBinding;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height);

                itemFavouriteBinding.ivItemFavourite.setLayoutParams(params);
                       itemFavouriteBinding.setItem(favouriteBean);
            }
        };

        recyclerView.setLayoutManager(new org.solovyev.android.views.llm.LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(30));
        recyclerView.setAdapter(adapter);
        adapter.setBinderOnItemClickListener(this);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBinderItemClick(View view, int pos) {

        Intent intent = new Intent(getContext(),TransferActivity.class);
        intent.putExtra("fragment",4);
        startActivity(intent);
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
        return R.layout.fragment_favourite;
    }
}

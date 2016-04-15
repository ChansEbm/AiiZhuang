package com.appbaba.iz.ui.fragment;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
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
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.impl.BinderOnItemClickListener;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.method.SpaceItemDecoration;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.ui.activity.TransferActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/4/1.
 */
public class FavouriteFragment extends BaseFgm implements BinderOnItemClickListener {

    @Override
    protected void initViews() {

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
        return 0;
    }
}
//    private FragmentFavouriteBinding favouriteBinding;
//    private RecyclerView recyclerView;
//    private List<FavouriteBean> list;
//    private CommonBinderAdapter<FavouriteBean> adapter;
//    private List<FavouriteBean.ListEntity> list;
//    private CommonBinderAdapter<FavouriteBean.ListEntity> adapter;
//    private int height;
//
//    @Override
//    protected void initViews() {
//        favouriteBinding = (FragmentFavouriteBinding) viewDataBinding;
//        favouriteBinding.includeTopTitle.toolBar.setBackgroundResource(R.color.white);
//        favouriteBinding.includeTopTitle.title.setText(R.string.main_activity_bottom_favourite);
//        favouriteBinding.includeTopTitle.title.setTextColor(Color.BLACK);
//        height = MethodConfig.GetHeightFor16v9(MethodConfig.metrics.widthPixels);
//        recyclerView = favouriteBinding.rcyFavourite;
////        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams
//// .MATCH_PARENT,MethodConfig.metrics.heightPixels);
////        recyclerView.setLayoutParams(params);
//        list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            FavouriteBean bean = new FavouriteBean();
//            bean.setDetail("qingxin tuoshu  hah ");
//            bean.setLike(100 + i);
//            bean.setSeen(200 + i);
//            bean.setUrl("www.baidu.com");
//            bean.setTitle("这是一个测试" + i);
//            list.add(bean);
//        }
//
//        adapter = new CommonBinderAdapter<FavouriteBean>(getContext(), R.layout
//                .item_favourite_view, list) {
//
//            @Override
//            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int
//                    position, FavouriteBean favouriteBean) {
//
//                ItemFavouriteBinding itemFavouriteBinding = (ItemFavouriteBinding) viewDataBinding;
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup
//                        .LayoutParams.MATCH_PARENT, height);
//
//                itemFavouriteBinding.ivItemFavourite.setLayoutParams(params);
//                itemFavouriteBinding.setItem(favouriteBean);
//                list = new ArrayList<>();
//
//                adapter = new CommonBinderAdapter<FavouriteBean.ListEntity>(getContext(), R
//                        .layout.item_favourite_view, list) {
//
//                    @Override
//                    public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder
//                            holder, int position, FavouriteBean.ListEntity entity) {
//                        ItemFavouriteBinding itemFavouriteBinding = (ItemFavouriteBinding)
//                                viewDataBinding;
//                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
//                                (ViewGroup.LayoutParams.MATCH_PARENT, height);
//                        Picasso.with(getContext()).load(entity.getThumb()).into
//                                (itemFavouriteBinding.ivItemFavourite);
//                        itemFavouriteBinding.ivItemFavourite.setLayoutParams(params);
//                        itemFavouriteBinding.setItem(entity);
//                    }
//                };
//
//                recyclerView.setLayoutManager(new org.solovyev.android.views.llm.LinearLayoutManager
//                        (getContext()));
//                recyclerView.addItemDecoration(new SpaceItemDecoration(30));
//                recyclerView.setAdapter(adapter);
//
//                adapter.notifyDataSetChanged();
//
//                networkModel.HomeSubject(AppTools.getStringSharedPreferences(AppKeyMap.AUTH, ""),
//                        0, 10, NetworkParams.SUBJECT);
//            }
//
//            @Override
//            public void onBinderItemClick(View view, int parentId, int pos) {
//
//                Intent intent = new Intent(getContext(), TransferActivity.class);
//                intent.putExtra("fragment", 4);
//                public void onBinderItemClick (View view,int parentId, int pos){
//
//                    Intent intent = new Intent(getContext(), TransferActivity.class);
//                    intent.putExtra("fragment", 4);
//                    intent.putExtra("title", list.get(pos).getTitle());
//                    intent.putExtra("id", list.get(pos).getSubject_id());
//                startActivity(intent);
//                }
//
//                @Override
//                protected void initEvents () {
//                    adapter.setBinderOnItemClickListener(this);
//                }
//
//                @Override
//                protected void noNetworkStatus () {
//
//                }
//
//                @Override
//                protected void onClick ( int id, View view){
//
//                }
//
//                @Override
//                protected int getContentView () {
//                    return R.layout.fragment_favourite;
//                }
//
//                @Override
//                public void onJsonObjectSuccess (Object t, NetworkParams paramsCode){
//                    if (paramsCode == NetworkParams.SUBJECT) {
//                        FavouriteBean bean = (FavouriteBean) t;
//                        if (bean.getErrorcode() == 0) {
//                            list.addAll(bean.getList());
//                            adapter.notifyDataSetChanged();
//                        }
//                    }
//                }
//            }

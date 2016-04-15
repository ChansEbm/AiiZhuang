package com.appbaba.iz.ui.fragment;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ViewSwitcher;

import com.appbaba.iz.AppKeyMap;
import com.appbaba.iz.FragmentFriendBinding;
import com.appbaba.iz.ItemFriendBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.adapters.CommonBinderAdapter;
import com.appbaba.iz.adapters.CommonBinderHolder;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.entity.Friends.FriendsBean;
import com.appbaba.iz.entity.Friends.FriendsClientBean;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.impl.BinderOnItemClickListener;
import com.appbaba.iz.impl.UpdateUIListener;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.method.SpaceItemDecoration;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.ui.activity.LoginActivity;
import com.appbaba.iz.ui.activity.TransferActivity;
import com.squareup.picasso.Picasso;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/4/1.
 */
public class FriendsFragment extends BaseFgm implements BinderOnItemClickListener, Toolbar
        .OnMenuItemClickListener {

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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
//    private FragmentFriendBinding friendBinding;
//    private RecyclerView recyclerView;
//    private ViewSwitcher vs;
//    private List<FriendsBean.ListEntity> list;
//
//    CommonBinderAdapter adapter;
//    FriendsBean.ListEntity entity = new FriendsBean.ListEntity();
//
//    @Override
//    protected void initViews() {
//
//        friendBinding = (FragmentFriendBinding) viewDataBinding;
//        vs = friendBinding.vs;
//
//        friendBinding.includeTopTitle.title.setText(R.string.main_activity_bottom_friends);
//        friendBinding.includeTopTitle.title.setTextColor(Color.BLACK);
//        friendBinding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
//        friendBinding.includeTopTitle.toolBar.getMenu().add(0, R.id.menu_add, 0, "").setIcon(R
//                .mipmap.icon_friend_add).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//
//        friendBinding.swRefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
//        recyclerView = friendBinding.recycler;
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.addItemDecoration(new SpaceItemDecoration(2));
//
//        list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            FriendsBean bean = new FriendsBean();
//            bean.setTitle("sfd" + i);
//            bean.setDetail("detail" + i);
//            bean.setDate(i % 2 == 0 ? "昨天" : "2016-12-" + i);
//            list.add(bean);
//        }
//        adapter = new CommonBinderAdapter<FriendsBean>(getContext(), R.layout.item_friend_view,
//                list) {
//            @Override
//            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int
//                    position, FriendsBean o) {
//                ItemFriendBinding binding = (ItemFriendBinding) viewDataBinding;
//=======
//
//        entity.setTitle("我的客户");
//        entity.setDesc("记录客户通讯录和客户的收藏");
//        entity.setUpdatetime("");
//        list.add(entity);
////        for(int i=0;i<10;i++)
////        {
////            FriendsBean bean = new FriendsBean();
////            bean.setTitle("sfd"+i);
////            bean.setDetail("detail" + i);
////            bean.setDate(i % 2 == 0 ? "昨天" : "2016-12-" + i);
////            list.add(bean);
////        }
//        adapter = new CommonBinderAdapter<FriendsBean.ListEntity>(getContext(),R.layout.item_friend_view,list) {
//            @Override
//            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, FriendsBean.ListEntity o) {
//                ItemFriendBinding binding = (ItemFriendBinding)viewDataBinding;
//>>>>>>> dd250d0dd59c296c8e07bff323797ff0ef2a899c
//                binding.setItem(o);
//                if(position==0)
//                {
//                    binding.dvHead.setVisibility(View.GONE);
//                    binding.ivHead.setVisibility(View.VISIBLE);
//                }
//                else
//                {
//                    binding.dvHead.setVisibility(View.VISIBLE);
//                    binding.ivHead.setVisibility(View.GONE);
//                    Picasso.with(getContext()).load(o.getThumb()).into(binding.dvHead);
//                }
//            }
//        };
//
//        recyclerView.setAdapter(adapter);
//        networkModel.HomeMarketingArticleCate(AppTools.getStringSharedPreferences(AppKeyMap.AUTH,""),NetworkParams.ARTICLECATE,true);
//    }
//
//    @Override
//    protected void initEvents() {
//        adapter.setBinderOnItemClickListener(this);
//        friendBinding.includeTopTitle.toolBar.setOnMenuItemClickListener(this);
//        friendBinding.swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                list.clear();
//                list.add(entity);
//                networkModel.HomeMarketingArticleCate(AppTools.getStringSharedPreferences(AppKeyMap.AUTH,""),NetworkParams.ARTICLECATE,false);
//            }
//        });
//    }
//
//    @Override
//    protected void noNetworkStatus() {
//
//    }
//
//    @Override
//    protected void onClick(int id, View view) {
//
//    }
//
//    @Override
//<<<<<<< HEAD
//    public void onBinderItemClick(View view, int parentId, int pos) {
//        Intent intent = new Intent(getContext(), TransferActivity.class);
//        intent.putExtra("fragment", 8);
//        startActivity(intent);
//=======
//    public void onBinderItemClick(View view,int parentId ,int pos) {
//        if(pos==0) {
//            if(MethodConfig.localUser!=null) {
//                Intent intent = new Intent(getContext(), TransferActivity.class);
//                intent.putExtra("fragment", 8);
//                startActivity(intent);
//            }
//            else
//            {
//                Intent intent = new Intent(getContext(), LoginActivity.class);
//                startActivity(intent);
//            }
//        }
//        else
//        {
//            Intent intent = new Intent(getContext(), TransferActivity.class);
//
//            intent.putExtra("title", list.get(pos).getTitle());
//            intent.putExtra("id",list.get(pos).getArticle_cate_id());
//            intent.putExtra("fragment", 12);
//            startActivity(intent);
//        }
//>>>>>>> dd250d0dd59c296c8e07bff323797ff0ef2a899c
//    }
//
//    @Override
//    protected int getContentView() {
//        return R.layout.fragment_friend;
//    }
//
//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_add:
//                final FriendsItemCRMFragment itemCRMFragment = new FriendsItemCRMFragment();
//                itemCRMFragment.uiListener = new UpdateUIListener() {
//                    @Override
//                    public void uiUpData(Intent intent) {
//                        vs.setDisplayedChild(0);
//                        getFragmentManager().beginTransaction().remove(itemCRMFragment);
//                    }
//                };
//                getFragmentManager().beginTransaction().add(R.id.layout_contain_1,
//                        itemCRMFragment).commit();
//                vs.setDisplayedChild(1);
//                break;
//        }
//        return false;
//    }
//
//    @Override
//    public void onJsonObjectSuccess(Object t, NetworkParams paramsCode) {
//        if(paramsCode==NetworkParams.ARTICLECATE)
//        {
//            FriendsBean bean = (FriendsBean)t;
//            if(bean.getErrorcode()==0)
//            {
//                list.addAll(bean.getList());
//                adapter.notifyDataSetChanged();
//
//            }
//        }
//
//        if(friendBinding.swRefresh.isRefreshing())
//        {
//            friendBinding.swRefresh.setRefreshing(false);
//        }
//    }
//}

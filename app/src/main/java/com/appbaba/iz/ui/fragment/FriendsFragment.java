package com.appbaba.iz.ui.fragment;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ViewSwitcher;

import com.appbaba.iz.FragmentFriendBinding;
import com.appbaba.iz.FragmentHomeTopNearbyBinding;
import com.appbaba.iz.ItemFriendBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.adapters.CommonBinderAdapter;
import com.appbaba.iz.adapters.CommonBinderHolder;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.entity.Friends.FriendsBean;
import com.appbaba.iz.impl.BinderOnItemClickListener;
import com.appbaba.iz.impl.UpdateUIListener;
import com.appbaba.iz.method.SpaceItemDecoration;
import com.appbaba.iz.ui.activity.TransferActivity;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/4/1.
 */
public class FriendsFragment extends BaseFgm implements BinderOnItemClickListener,Toolbar.OnMenuItemClickListener{
    private FragmentFriendBinding friendBinding;
    private RecyclerView recyclerView;
    private ViewSwitcher vs;
    private List<FriendsBean> list;

    CommonBinderAdapter adapter;

    @Override
    protected void initViews() {

        friendBinding = (FragmentFriendBinding)viewDataBinding;
        vs = friendBinding.vs;

        friendBinding.includeTopTitle.title.setText(R.string.main_activity_bottom_friends);
        friendBinding.includeTopTitle.title.setTextColor(Color.BLACK);
        friendBinding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        friendBinding.includeTopTitle.toolBar.getMenu().add(0,R.id.menu_add,0,"").setIcon(R.mipmap.icon_friend_add).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        recyclerView = friendBinding.recycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(2));

        list = new ArrayList<>();
        for(int i=0;i<10;i++)
        {
          FriendsBean bean = new FriendsBean();
            bean.setTitle("sfd"+i);
            bean.setDetail("detail" + i);
            bean.setDate(i % 2 == 0 ? "昨天" : "2016-12-" + i);
            list.add(bean);
        }
        adapter = new CommonBinderAdapter<FriendsBean>(getContext(),R.layout.item_friend_view,list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, FriendsBean o) {
                ItemFriendBinding binding = (ItemFriendBinding)viewDataBinding;
                binding.setItem(o);
            }
        };


        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initEvents() {
        adapter.setBinderOnItemClickListener(this);
        friendBinding.includeTopTitle.toolBar.setOnMenuItemClickListener(this);
    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {

    }

    @Override
    public void onBinderItemClick(View view, int pos) {
         Intent intent = new Intent(getContext(),TransferActivity.class);
        intent.putExtra("fragment",8);
        startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_friend;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_add:
                final FriendsItemCRMFragment itemCRMFragment = new FriendsItemCRMFragment();
                itemCRMFragment.uiListener = new UpdateUIListener() {
                    @Override
                    public void uiUpData(Intent intent) {
                       vs.setDisplayedChild(0);
                        getFragmentManager().beginTransaction().remove(itemCRMFragment);
                    }
                };
                getFragmentManager().beginTransaction().add(R.id.layout_contain_1,itemCRMFragment).commit();
                vs.setDisplayedChild(1);
//                Intent intent = new Intent(getContext(), TransferActivity.class);
//                intent.putExtra("fragment",7);
//                startActivity(intent);
                break;
        }
        return false;
    }
}

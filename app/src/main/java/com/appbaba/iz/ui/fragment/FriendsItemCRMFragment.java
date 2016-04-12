package com.appbaba.iz.ui.fragment;

import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appbaba.iz.FragmentFriendCRMBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.adapters.CommonBinderAdapter;
import com.appbaba.iz.adapters.CommonBinderHolder;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.impl.UpdateUIListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/4/8.
 */
public class FriendsItemCRMFragment extends BaseFgm {

    private FragmentFriendCRMBinding crmBinding;
    private RecyclerView recyclerView;
    private CommonBinderAdapter adapter;
    private List<Object> list;

    public UpdateUIListener uiListener;

    @Override
    protected void initViews() {

        crmBinding = (FragmentFriendCRMBinding)viewDataBinding;
        crmBinding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.more_arrow_dark_left);
        crmBinding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        crmBinding.includeTopTitle.title.setText(R.string.friends_fragment_crm);
        crmBinding.includeTopTitle.title.setTextColor(Color.BLACK);

        recyclerView = crmBinding.recycler;

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        list = new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            list.add(new Object());
        }
        adapter = new CommonBinderAdapter(getContext(),R.layout.item_friend_crm_view,list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, Object o) {

            }

        };
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void initEvents() {

        crmBinding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uiListener!=null)
                {
                    uiListener.uiUpData(null);
                }
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
        return R.layout.fragment_friend_item_crm;
    }
}

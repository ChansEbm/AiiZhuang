package com.appbaba.platform.ui.fragment;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appbaba.platform.FragmentMeItemBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.base.BaseFragment;
import com.appbaba.platform.method.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/5/10.
 */
public class MeItemFragment extends BaseFragment {
    private FragmentMeItemBinding binding;
    private RecyclerView recyclerView;

    private CommonBinderAdapter<Object> adapter;
    private List<Object> list;

    @Override
    protected void InitView() {
       binding = (FragmentMeItemBinding)viewDataBinding;
        recyclerView = binding.recycle;
    }

    @Override
    protected void InitData() {
        list = new ArrayList<>();
        for(int i=0;i<20;i++)
        {
            list.add(new Object());
        }
        adapter = new CommonBinderAdapter<Object>(getContext(),R.layout.item_collection_view,list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, Object o) {

            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(2));
        recyclerView.setAdapter(adapter);
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
        return R.layout.fragment_me_item_view;
    }
}

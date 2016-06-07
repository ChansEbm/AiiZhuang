package com.appbaba.platform.ui.fragment;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appbaba.platform.FragmentMeItemBinding;
import com.appbaba.platform.ItemCollectionBinding;
import com.appbaba.platform.ItemUserCollectionBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.base.BaseFragment;
import com.appbaba.platform.entity.User.BaseItemBean;
import com.appbaba.platform.method.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/5/10.
 */
public class MeItemFragment extends BaseFragment {
    private FragmentMeItemBinding binding;
    private RecyclerView recyclerView;

    private CommonBinderAdapter<BaseItemBean> adapter;
    private List<BaseItemBean> list;
    private int index= 0 ;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    protected void InitView() {
       binding = (FragmentMeItemBinding)viewDataBinding;
        recyclerView = binding.recycle;
    }

    public void  AddListData(List<BaseItemBean> dataList)
    {
         list.addAll(dataList);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void InitData() {
        list = new ArrayList<>();

        adapter = new CommonBinderAdapter<BaseItemBean>(getContext(),R.layout.item_user_bottom_collection_view,list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, BaseItemBean o) {
                ItemUserCollectionBinding collectionBinding = (ItemUserCollectionBinding)viewDataBinding;
                collectionBinding.setItem(o);
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

package com.appbaba.platform.ui.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.view.menu.MenuWrapperFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.appbaba.platform.FragmentInspirationBinding;
import com.appbaba.platform.PopupRippleBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.base.BaseFragment;
import com.appbaba.platform.databinding.ItemRippleBinding;
import com.appbaba.platform.impl.AnimationCallBack;
import com.appbaba.platform.impl.BinderOnItemClickListener;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.method.SpaceItemDecoration;
import com.appbaba.platform.tools.LogTools;
import com.appbaba.platform.ui.activity.InspirationDetailActivity;
import com.appbaba.platform.ui.activity.InspirationSearchActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/5/4.
 */
public class InspirationFragment extends BaseFragment implements BinderOnItemClickListener{
    private FragmentInspirationBinding binding;
    private RecyclerView recyclerView;

    private CommonBinderAdapter<Object> commonBinderAdapter;
    private List<Object> list;
    private AnimationCallBack callBack;

    @Override
    protected void InitView() {
        binding = (FragmentInspirationBinding)viewDataBinding;
        recyclerView = binding.recycle;

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(20));
    }

    @Override
    protected void InitData() {
        list =new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            list.add(new Object());
        }
        commonBinderAdapter = new CommonBinderAdapter<Object>(getContext(),R.layout.item_inspiration_view,list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, Object o) {

            }
        };
        recyclerView.setAdapter(commonBinderAdapter);

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
        binding.ivSearch.setOnClickListener(this);
        commonBinderAdapter.setBinderOnItemClickListener(this);
    }

    @Override
    protected void OnClick(int id, View view) {
        switch (id)
        {
            case R.id.iv_search:
                StartActivity(InspirationSearchActivity.class);
                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_inspiration;
    }

    @Override
    public void onBinderItemClick(View clickItem, int parentId, int pos) {
          StartActivity(InspirationDetailActivity.class);
    }

    @Override
    public void onBinderItemLongClick(View clickItem, int parentId, int pos) {

    }

    public void  setCallBack(AnimationCallBack callBack)
    {
        this.callBack = callBack;
    }
}

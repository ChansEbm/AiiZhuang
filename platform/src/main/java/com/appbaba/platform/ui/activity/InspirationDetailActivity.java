package com.appbaba.platform.ui.activity;

import android.content.res.TypedArray;
import android.databinding.ViewDataBinding;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AndroidException;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appbaba.platform.ActivityInspirationDetailBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.base.BaseActivity;
import com.appbaba.platform.method.SpaceItemDecoration;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class InspirationDetailActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener{


    private ActivityInspirationDetailBinding binding;

    private AppBarLayout mAppBarLayout;
    private SimpleDraweeView dv_head;
    private RecyclerView recyclerView;

    private CommonBinderAdapter<Object> adapter;
    private List<Object> list;

    @Override
    protected void InitView() {
        binding = (ActivityInspirationDetailBinding)viewDataBinding;

        mAppBarLayout   = binding.appbar;
        dv_head = binding.dvHead;
        recyclerView = binding.recycle;
    }

    @Override
    protected void InitData() {
        list = new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            list.add(new Object());
        }
          adapter = new CommonBinderAdapter<Object>(this,R.layout.item_inspiration_detail_view,list) {
              @Override
              public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, Object o) {

              }
          };

        recyclerView.setLayoutManager(new org.solovyev.android.views.llm.LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpaceItemDecoration(1));
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void InitEvent() {

    }

    @Override
    protected void InitListening() {
        mAppBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    protected void OnClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_inspiration_detail;
    }

    private  float x =0,y=0,height=0,height1=0;
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
        if(x==0)
        {
            x = dv_head.getX();
            y=dv_head.getY();
            height = dv_head.getHeight();
            height1 = binding.tvTopTitle.getHeight();
        }
            dv_head.setScaleX(1 - 0.5f*percentage);
            dv_head.setScaleY(1 - 0.5f*percentage);
        if(percentage==0)
        {
            binding.tvName.setAlpha(1);
        }
        else
        {
            binding.tvName.setAlpha(0);
        }
        binding.tvTopTitle.setAlpha(percentage);
        dv_head.setX(x- x*percentage);
        float k = y+height*percentage*0.5f*0.5f-(binding.toolbar.getHeight()-height/2)/2*percentage;
        float k1 = k+(height-height1)/2;
        dv_head.setY(k);
        binding.tvTopTitle.setY(k1);
    }
}

package com.appbaba.platform.ui.activity.inspiration;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.appbaba.platform.ActivityDesignWorksBinding;
import com.appbaba.platform.ItemInspirationBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.base.BaseActivity;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.inspiration.AllInspirationBean;
import com.appbaba.platform.entity.inspiration.InspirationEntity;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.BinderOnItemClickListener;
import com.appbaba.platform.method.SpaceItemDecoration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/5/12.
 */
public class DesignWorksActivity extends BaseActivity implements BinderOnItemClickListener{

    private ActivityDesignWorksBinding binding;
    private RecyclerView recyclerView;

    private CommonBinderAdapter<InspirationEntity> adapter;
    private List<InspirationEntity> list;
    private String designerID = "";

    @Override
    protected void InitView() {
        binding = (ActivityDesignWorksBinding)viewDataBinding;
        binding.toolBar.setNavigationIcon(R.mipmap.icon_back);
        binding.toolBar.getMenu().add(0,R.id.action_share,0,"").setIcon(R.mipmap.icon_share).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        setSupportActionBar( binding.toolBar);
        recyclerView = binding.recycle;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
    }

    @Override
    protected void InitData() {
        designerID = getIntent().getStringExtra("designerID");
        binding.tvTitle.setText("全部作品");
        list = new ArrayList<>();
        adapter = new CommonBinderAdapter<InspirationEntity>(this,R.layout.item_inspiration_view,list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, InspirationEntity o) {
                ItemInspirationBinding itemInspirationBinding = (ItemInspirationBinding)viewDataBinding;
                itemInspirationBinding.setItem(o);
                Picasso.with(DesignWorksActivity.this).load(o.getThumb()).into(itemInspirationBinding.ivItem);
            }
        };

        recyclerView.setAdapter(adapter);

        networkModel.Collections(designerID, NetworkParams.CUPCAKE);
    }

    @Override
    protected void InitEvent() {

    }

    @Override
    protected void InitListening() {
        adapter.setBinderOnItemClickListener(this);
    }

    @Override
    protected void OnClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_design_works;
    }

    @Override
    public void onBinderItemClick(View clickItem, int parentId, int pos) {
       // StartActivity(DesignWorkDetailActivity.class);
    }

    @Override
    public void onBinderItemLongClick(View clickItem, int parentId, int pos) {

    }

    @Override
    public void onJsonObjectSuccess(BaseBean baseBean, NetworkParams paramsCode) {
       if(paramsCode==NetworkParams.CUPCAKE)
       {
           if(baseBean.getErrorcode()==0)
           {
               AllInspirationBean bean = (AllInspirationBean)baseBean;
               list.addAll(bean.getMy_inspiration());
               adapter.notifyDataSetChanged();
           }
       }
    }
}

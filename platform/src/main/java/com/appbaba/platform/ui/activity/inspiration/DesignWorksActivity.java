package com.appbaba.platform.ui.activity.inspiration;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.appbaba.platform.ActivityDesignWorksBinding;
import com.appbaba.platform.AppKeyMap;
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
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.method.SpaceItemDecoration;
import com.appbaba.platform.widget.DialogView.ShareDialogView;
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
    private int height = 0;
    private String designerID = "",name="",desc="",image="";

    @Override
    protected void InitView() {
        binding = (ActivityDesignWorksBinding)viewDataBinding;
        binding.toolBar.setNavigationIcon(R.mipmap.icon_back);
        binding.toolBar.getMenu().add(0,R.id.action_share,0,"").setIcon(R.mipmap.icon_share).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        //setSupportActionBar( binding.toolBar);
        recyclerView = binding.recycle;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
    }

    @Override
    protected void InitData() {
        height = MethodConfig.GetHeight(MethodConfig.metrics.widthPixels,5,3);

        designerID = getIntent().getStringExtra("designerID");
        name = getIntent().getStringExtra("name");
        desc = getIntent().getStringExtra("desc");
        image = getIntent().getStringExtra("image");
        binding.tvTitle.setText("全部作品");
        list = new ArrayList<>();
        adapter = new CommonBinderAdapter<InspirationEntity>(this,R.layout.item_inspiration_view,list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, InspirationEntity o) {
                ItemInspirationBinding itemInspirationBinding = (ItemInspirationBinding)viewDataBinding;
                itemInspirationBinding.setItem(o);
                itemInspirationBinding.ivItem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height));
                if(TextUtils.isEmpty(o.getImage()))
                {
                    Picasso.with(DesignWorksActivity.this).load(o.getImage()).into(itemInspirationBinding.ivItem);
                }
            }
        };

        recyclerView.setAdapter(adapter);

        networkModel.Collections(designerID, NetworkParams.CUPCAKE);
    }

    @Override
    protected void InitEvent() {
         binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 onBackPressed();
             }
         });
        binding.toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.action_share:
                        String url = AppKeyMap.HEAD_API_PAGE_ALLDESIGN+designerID;
                        ShareDialogView shareDialogView = new ShareDialogView(DesignWorksActivity.this,name,image,desc,url);
                        shareDialogView.show();
                        break;
                }
                return false;
            }
        });
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

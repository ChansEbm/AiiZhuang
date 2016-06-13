package com.appbaba.platform.ui.activity.inspiration;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import com.appbaba.platform.ActivityInspirationDetailBinding;
import com.appbaba.platform.ItemInspirationDetailBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.base.BaseActivity;
import com.appbaba.platform.databinding.ItemRippleBinding;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.inspiration.InspirationDetailBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.BinderOnItemClickListener;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.method.SpaceItemDecoration;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class InspirationDetailActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener,BinderOnItemClickListener{


    private ActivityInspirationDetailBinding binding;

    private AppBarLayout mAppBarLayout;
    private SimpleDraweeView dv_head;
    private RecyclerView recyclerView;

    private CommonBinderAdapter<InspirationDetailBean.InspirationEntity.InspirationBottomEntity> adapter;
    private List<InspirationDetailBean.InspirationEntity.InspirationBottomEntity> list;
    private HashMap<String,ItemRippleBinding> tempBindingMap = new HashMap<>();

    private String id;
    private boolean isFirst= true;

    @Override
    protected void InitView() {
        binding = (ActivityInspirationDetailBinding)viewDataBinding;
        binding.toolbar.setNavigationIcon(R.mipmap.icon_back);
        binding.toolbar.getMenu().add(0,R.id.action_collection,0,"").setIcon(R.mipmap.icon_heart_y_n).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        binding.toolbar.getMenu().add(0,R.id.action_share,1,"").setIcon(R.mipmap.icon_share).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        mAppBarLayout   = binding.appbar;
        dv_head = binding.dvHead;
        recyclerView = binding.recycle;
    }

    @Override
    protected void InitData() {
        id = getIntent().getStringExtra("id");
        list = new ArrayList<>();

          adapter = new CommonBinderAdapter<InspirationDetailBean.InspirationEntity.InspirationBottomEntity>(this,R.layout.item_inspiration_detail_view,list) {
              @Override
              public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position,final InspirationDetailBean.InspirationEntity.InspirationBottomEntity o) {
                final  ItemInspirationDetailBinding itemInspirationDetailBinding = (ItemInspirationDetailBinding)viewDataBinding;
                  itemInspirationDetailBinding.setItem(o);
                  if(!TextUtils.isEmpty(o.getThumb()))
                  Picasso.with(InspirationDetailActivity.this).load(o.getThumb()).into(itemInspirationDetailBinding.ivItem, new Callback() {
                      @Override
                      public void onSuccess() {
                          Timer timer = new Timer();
                          TimerTask timerTask = new TimerTask() {
                              @Override
                              public void run() {
                                  Refresh(itemInspirationDetailBinding.getRoot(),o);
                              }
                          };
                          timer.schedule(timerTask,2000);
                      }

                      @Override
                      public void onError() {

                      }
                  });

              }
          };

        recyclerView.setLayoutManager(new org.solovyev.android.views.llm.LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpaceItemDecoration(1));
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        if(!TextUtils.isEmpty(id)) {
            networkModel.InspirationDetail(id, NetworkParams.CUPCAKE);
        }
    }

    @Override
    protected void InitEvent() {
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_share:
                        Toast.makeText(InspirationDetailActivity.this,"hehe",Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        });
    }


    @Override
    protected void InitListening() {
        binding.dvHead.setOnClickListener(this);
        mAppBarLayout.addOnOffsetChangedListener(this);
        adapter.setBinderOnItemClickListener(this);
    }

    @Override
    protected void OnClick(int id, View view) {
        switch (id)
        {
            case R.id.dv_head:
                String designerID = (String) view.getTag(R.string.tag_value);
                if(TextUtils.isEmpty(designerID))
                {
                    Toast.makeText(this,"设计师获取失败",Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(this,DesignWorkDetailActivity.class);
                intent.putExtra("designerID",designerID);
                startActivity(intent);
                break;
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_inspiration_detail;
    }

    private  float x =0,y=0,height=0,height1=0;
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if(isFirst)
        {
            return;
        }
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
            if(binding.tvSayHello.getVisibility()!=View.VISIBLE)
            ShowOrHide(false);
        }
        else
        {
            binding.tvName.setAlpha(0);
            if(percentage==1 && binding.tvSayHello.getVisibility()!=View.INVISIBLE)
            {
                ShowOrHide(true);
            }
        }
        binding.tvTopTitle.setAlpha(percentage);
        dv_head.setX(x- x*percentage);
        float k = y+height*percentage*0.5f*0.5f-(binding.toolbar.getHeight()-height/2)/2*percentage;
        float k1 = k+(height-height1)/2;
        dv_head.setY(k);
        binding.tvTopTitle.setY(k1);
    }

    @Override
    public void onBinderItemClick(View clickItem, int parentId, int pos) {
        Intent intent = new Intent(this,InspirationWebDetailActivity.class);
        intent.putExtra("id",list.get(pos).getId());
        startActivity(intent);
    }

    @Override
    public void onBinderItemLongClick(View clickItem, int parentId, int pos) {

    }

    @Override
    public void onJsonObjectSuccess(BaseBean baseBean, NetworkParams paramsCode) {
        if(baseBean.getErrorcode()==0)
        {
            InspirationDetailBean bean = (InspirationDetailBean)baseBean;
            list.addAll(bean.getInspiration().getInspiration_bottom());
            adapter.notifyDataSetChanged();
            binding.setItem(bean.getInspiration().getInspiration_top());
            if(TextUtils.isEmpty(bean.getInspiration().getInspiration_top().getUser_thumb()))
            {
                binding.dvHead.setImageURI(Uri.parse(""));
            }
            else
            {
                binding.dvHead.setImageURI(Uri.parse(bean.getInspiration().getInspiration_top().getUser_thumb()));
            }
            binding.dvHead.setTag(R.string.tag_value,bean.getInspiration().getInspiration_top().getStylist_id());
        }
        isFirst = false;
    }

    Handler handler = new Handler();
    public void  Refresh(final View view,final InspirationDetailBean.InspirationEntity.InspirationBottomEntity entity)
    {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(view !=null && view.getTag(R.string.tag_value)==null)
                {
                    ItemInspirationDetailBinding itemInspirationDetailBinding = DataBindingUtil.bind(view);
                    if(itemInspirationDetailBinding.ivItem.getHeight()>0)
                    {
                        for(int i=0;i<entity.getGoods().size();i++) {
                            int x = Integer.parseInt(entity.getGoods().get(i).getLocation_x());
                            int y = Integer.parseInt(entity.getGoods().get(i).getLocation_y());
                            if (x > 0 && y > 0) {
                                final String productID = entity.getGoods().get(i).getGoods_id();
                                final ItemRippleBinding rippleBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_rip_view, null, false);
                                rippleBinding.getRoot().setX(MethodConfig.metrics.widthPixels * x / 100 - MethodConfig.dip2px(InspirationDetailActivity.this, 30) / 2);
                                rippleBinding.getRoot().setY(itemInspirationDetailBinding.ivItem.getHeight() * y / 100 - MethodConfig.dip2px(InspirationDetailActivity.this, 30) / 2);
                                rippleBinding.tvItemView.setText(entity.getGoods().get(i).getModel());
                                itemInspirationDetailBinding.itemContain.addView(rippleBinding.getRoot());
                                rippleBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (tempBindingMap.containsKey(productID)) {
                                            if (tempBindingMap.get(productID) != null){
                                                ItemRippleBinding tempBinding = tempBindingMap.get(productID);
                                                tempBinding.content.startRippleAnimation();
                                                tempBinding.content.setVisibility(View.VISIBLE);
                                                tempBinding.itemRipBg.setVisibility(View.GONE);

                                                tempBindingMap.put(productID, null);
                                                if (tempBinding == rippleBinding) {
                                                    return;
                                                }
                                            }
                                        }

                                        rippleBinding.itemRipBg.setVisibility(View.VISIBLE);
                                        rippleBinding.content.setVisibility(View.GONE);
                                        rippleBinding.content.stopRippleAnimation();
                                        tempBindingMap.put(productID, rippleBinding);
                                    }
                                });
                                rippleBinding.content.startRippleAnimation();
                            }
                            view.setTag(R.string.tag_value,"true");
                        }
                    }
                }
            }
        });

    }

    public void ShowOrHide(boolean isShow)
    {
        if(isShow)
        {
            TranslateAnimation animation = new TranslateAnimation(0,0,0,binding.tvSayHello.getHeight());
            animation.setDuration(200);
            animation.setInterpolator(new AccelerateInterpolator());
            binding.tvSayHello.startAnimation(animation);
            binding.tvSayHello.setVisibility(View.INVISIBLE);
        }
        else
        {
            TranslateAnimation animation = new TranslateAnimation(0,0,binding.tvSayHello.getHeight(),0);
            animation.setDuration(200);
            animation.setInterpolator(new AccelerateInterpolator());
            binding.tvSayHello.startAnimation(animation);
            binding.tvSayHello.setVisibility(View.VISIBLE);
        }
    }
}

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.appbaba.platform.ActivityInspirationDetailBinding;
import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.ItemInspirationDetailBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.base.BaseActivity;
import com.appbaba.platform.databinding.ItemRippleBinding;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.User.DesignerEMBean;
import com.appbaba.platform.entity.inspiration.InspirationDetailBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.BinderOnItemClickListener;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.method.SpaceItemDecoration;
import com.appbaba.platform.ui.activity.comm.CommChatActivity;
import com.appbaba.platform.ui.activity.comm.CommWebActivity;
import com.appbaba.platform.ui.activity.comm.CommZoomActivity;
import com.appbaba.platform.ui.activity.products.ProductWebDetailActivity;
import com.appbaba.platform.widget.DialogView.ShareDialogView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hyphenate.chat.EMClient;
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
    private boolean isFirst= true,isFirstIn = true;
    private InspirationDetailBean detailBean;
    private  DesignerEMBean emBean;


    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

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
    protected void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(id))
        {
            String tid = getIntent().getStringExtra("id");
            if(!TextUtils.isEmpty(tid) && !tid.equals(id))
            {
                InitData();
            }
        }
        else
        {
            InitData();
        }
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
                  itemInspirationDetailBinding.ivItem.setLayoutParams(new RelativeLayout.LayoutParams(MethodConfig.metrics.widthPixels,(int)(MethodConfig.metrics.widthPixels/o.getProportion())));
                  if(!TextUtils.isEmpty(o.getThumb()))
                  Picasso.with(InspirationDetailActivity.this).load(o.getThumb()).into(itemInspirationDetailBinding.ivItem);
                  Refresh(itemInspirationDetailBinding.getRoot(),o);
              }
          };

        recyclerView.setLayoutManager(new org.solovyev.android.views.llm.LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpaceItemDecoration(MethodConfig.dip2px(this,10)));
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        if(!TextUtils.isEmpty(id)) {
            networkModel.InspirationDetail(id, NetworkParams.CUPCAKE);
            if(MethodConfig.IsLogin())
            {
                networkModel.GetCheckLove(MethodConfig.userInfo.getToken(), id, NetworkParams.FROYO);
            }
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
                        if(detailBean!=null) {
                            String url = AppKeyMap.HEAD_API_PAGE_INSPIRATION + id;
                            ShareDialogView dialogView = new ShareDialogView(InspirationDetailActivity.this, detailBean.getInspiration().getInspiration_top().getTitle(), detailBean.getInspiration().getInspiration_top().getInspiration_thumb(), detailBean.getInspiration().getInspiration_top().getDesc(), url);
                            dialogView.show();
                        }
                        break;
                    case R.id.action_collection:
                        if(MethodConfig.IsLogin())
                        {
                            networkModel.ClickLove(MethodConfig.userInfo.getToken(),id,NetworkParams.DONUT);
                        }
                        else
                        {
                            Toast.makeText(InspirationDetailActivity.this,"请先登录",Toast.LENGTH_LONG).show();
                        }
                        break;
                }
                return false;
            }
        });
    }


    @Override
    protected void InitListening() {
        binding.dvHead.setOnClickListener(this);
        binding.linearSayHello.setOnClickListener(this);
        binding.ivTopRight.setOnClickListener(this);
        mAppBarLayout.addOnOffsetChangedListener(this);
        adapter.setBinderOnItemClickListener(this);
    }

    @Override
    protected void OnClick(int id, View view) {
        switch (id)
        {
            case R.id.dv_head: {
                String designerID = (String) view.getTag(R.string.tag_value);
                if (TextUtils.isEmpty(designerID)) {
                    Toast.makeText(this, "设计师获取失败", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(this, DesignWorkDetailActivity.class);
                intent.putExtra("designerID", designerID);
                startActivity(intent);
            }
                break;
            case R.id.linear_say_hello:
            case R.id.iv_top_right:
            {
                if(MethodConfig.IsLogin() && emBean!=null) {
                    Intent intent = new Intent(this, CommChatActivity.class);
                    intent.putExtra("id", emBean.getDesign_infor().getEasemob_username());
                    intent.putExtra("name", detailBean.getInspiration().getInspiration_top().getName());
                    intent.putExtra("image", detailBean.getInspiration().getInspiration_top().getUser_thumb());
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(this,"请先登录",Toast.LENGTH_LONG).show();
                }
            }
                break;
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_inspiration_detail;
    }

    private  float x =0,y=0,height=0,height1=0,height2=0;
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
            height2 = binding.ivTopRight.getHeight();
        }
            dv_head.setScaleX(1 - 0.5f*percentage);
            dv_head.setScaleY(1 - 0.5f*percentage);
        if(percentage==0)
        {
            binding.tvName.setAlpha(1);
            if(binding.linearSayHello.getVisibility()!=View.VISIBLE)
            ShowOrHide(false);
        }
        else
        {
            binding.tvName.setAlpha(0);
            if(percentage==1 && binding.linearSayHello.getVisibility()!=View.INVISIBLE)
            {
                ShowOrHide(true);
            }
        }
        binding.ivTopRight.setAlpha(percentage);
        binding.tvTopTitle.setAlpha(percentage);
        dv_head.setX(x- x*percentage);
        float k = y+height*percentage*0.5f*0.5f-(binding.toolbar.getHeight()-height/2)/2*percentage;
        float k1 = k+(height-height1)/2;
        float k2 = k+(height-height2)/2;
        dv_head.setY(k);
        binding.tvTopTitle.setY(k1);
        binding.ivTopRight.setY(k2);
    }

    @Override
    public void onBinderItemClick(View clickItem, int parentId, int pos) {
        Intent intent = new Intent(this,CommZoomActivity.class);
        intent.putExtra("name",list.get(pos).getTitle());
        intent.putExtra("url",list.get(pos).getThumb());
        startActivity(intent);
    }

    @Override
    public void onBinderItemLongClick(View clickItem, int parentId, int pos) {

    }

    @Override
    public void onJsonObjectSuccess(BaseBean baseBean, NetworkParams paramsCode) {
        if(baseBean.getErrorcode()==0) {
            if (paramsCode == NetworkParams.CUPCAKE) {
                InspirationDetailBean bean = (InspirationDetailBean) baseBean;
                detailBean = bean;
                list.addAll(bean.getInspiration().getInspiration_bottom());
                adapter.notifyDataSetChanged();
                binding.setItem(bean.getInspiration().getInspiration_top());
                if (TextUtils.isEmpty(bean.getInspiration().getInspiration_top().getUser_thumb())) {
                    binding.dvHead.setImageURI(Uri.parse(""));
                } else {
                    MethodConfig.userImage.put(detailBean.getInspiration().getInspiration_top().getStylist_id(),bean.getInspiration().getInspiration_top().getUser_thumb());
                    binding.dvHead.setImageURI(Uri.parse(bean.getInspiration().getInspiration_top().getUser_thumb()));
                }
                binding.dvHead.setTag(R.string.tag_value, bean.getInspiration().getInspiration_top().getStylist_id());
                isFirst = false;
               if(MethodConfig.IsLogin()) {
                   networkModel.GetUserEMID(MethodConfig.userInfo.getToken(), detailBean.getInspiration().getInspiration_top().getStylist_id(), NetworkParams.LOLLIPOP);
               }
            } else if (paramsCode == NetworkParams.DONUT) {
                networkModel.GetCheckLove(MethodConfig.userInfo.getToken(), id, NetworkParams.FROYO);
            } else if (paramsCode == NetworkParams.FROYO)
            {
                if(baseBean.getStatus()==1)
                {
                    if(!isFirstIn)
                     Toast.makeText(this,"收藏成功",Toast.LENGTH_LONG).show();
                      binding.toolbar.getMenu().getItem(0).setIcon(R.mipmap.icon_heart_y_r);
                }
                else
                {
                    if(!isFirstIn)
                    Toast.makeText(this,"已取消",Toast.LENGTH_LONG).show();
                    binding.toolbar.getMenu().getItem(0).setIcon(R.mipmap.icon_heart_y_n);
                }
                isFirstIn = false;
            }
            else if(paramsCode==NetworkParams.LOLLIPOP)
            {
                emBean = (DesignerEMBean)baseBean;
            }
        }
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
                            final InspirationDetailBean.InspirationEntity.InspirationBottomEntity.GoodsEntity goodsEntity = entity.getGoods().get(i);
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
                                        rippleBinding.itemRipBg.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                               String url =  goodsEntity.getBuy_link();
                                                String id = goodsEntity.getGoods_id();
                                                if(TextUtils.isEmpty(id))
                                                {
                                                    Toast.makeText(InspirationDetailActivity.this,"商品ID不存在",Toast.LENGTH_LONG).show();
                                                }
                                                else
                                                {
                                                    Intent intent = new Intent(InspirationDetailActivity.this,ProductWebDetailActivity.class);
                                                    intent.putExtra("id",id);
                                                    intent.putExtra("url",url);
                                                    startActivity(intent);

                                                }
                                            }
                                        });
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
            TranslateAnimation animation = new TranslateAnimation(0,0,0,binding.linearSayHello.getHeight());
            animation.setDuration(200);
            animation.setInterpolator(new AccelerateInterpolator());
            binding.linearSayHello.startAnimation(animation);
            binding.linearSayHello.setVisibility(View.INVISIBLE);
        }
        else
        {
            TranslateAnimation animation = new TranslateAnimation(0,0,binding.linearSayHello.getHeight(),0);
            animation.setDuration(200);
            animation.setInterpolator(new AccelerateInterpolator());
            binding.linearSayHello.startAnimation(animation);
            binding.linearSayHello.setVisibility(View.VISIBLE);
        }
    }
}

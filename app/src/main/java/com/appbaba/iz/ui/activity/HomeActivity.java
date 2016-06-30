package com.appbaba.iz.ui.activity;

import android.content.ClipData;
import android.content.Intent;
import android.databinding.Bindable;
import android.databinding.ViewDataBinding;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appbaba.iz.ActivityHomeBinding;
import com.appbaba.iz.AppKeyMap;
import com.appbaba.iz.ItemGuideListBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.adapters.CommonBinderAdapter;
import com.appbaba.iz.adapters.CommonBinderHolder;
import com.appbaba.iz.base.BaseAty;
import com.appbaba.iz.entity.Index.HomeGuideBean;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.impl.BinderOnItemClickListener;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.widget.NavViewPager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/6/28.
 */
public class HomeActivity extends BaseAty{
    private ActivityHomeBinding binding;

    private NavViewPager navViewPager;
    private RecyclerView recyclerView;

    private List<HomeGuideBean.ListEntity> list;
    private CommonBinderAdapter<HomeGuideBean.ListEntity> adapter;
    private List<ImageView> imageList;
    private List<HomeGuideBean.PictureEntity> picList;
    private ImageAdapter imageAdapter;

    @Override
    protected void initViews() {
        binding = (ActivityHomeBinding)viewDataBinding;
        navViewPager = binding.navViewPager;
        recyclerView = binding.recycler;

        String auth = AppTools.getStringSharedPreferences(AppKeyMap.AUTH,"");
         if(TextUtils.isEmpty(auth))
         {
             networkModel.GuidePage(NetworkParams.CUPCAKE);
         }
        else
         {
             start(LoginActivity.class);
             finish();
         }

    }

    @Override
    protected void initEvents() {
        imageList = new ArrayList<>();
        picList = new ArrayList<>();

        list = new ArrayList<>();
        adapter = new CommonBinderAdapter<HomeGuideBean.ListEntity>(this,R.layout.item_guide_list_view,list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, HomeGuideBean.ListEntity listEntity) {
                ItemGuideListBinding listBinding = (ItemGuideListBinding)viewDataBinding;
                listBinding.setItem(listEntity);
                if(!TextUtils.isEmpty(listEntity.getLogo()))
                Picasso.with(HomeActivity.this).load(listEntity.getLogo()).resize(500,500).into(listBinding.ivItem);
            }
        };
        adapter.setBinderOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        binding.tvLogin.setOnClickListener(this);
    }

    public void InitNav()
    {
        navViewPager.InitView();
        navViewPager.SetPointViewStyle(NavViewPager.PointsView.POINT_STYLE_CIRCLE, ContextCompat.getColor(this,R.color.half_white),
                ContextCompat.getColor(this,R.color.application_base_color),ContextCompat.getColor(this,R.color.white),20);
        navViewPager.SetDistance(20);
        navViewPager.IsCircleBound(20);
        imageAdapter = new ImageAdapter();
        navViewPager.SetAdapter(imageAdapter);
        //navViewPager.notifyDataChange();
        navViewPager.AutoScrollNoCancel(2000);
    }

    @Override
    public void onBinderItemClick(View clickItem, int parentId, int pos) {
             MethodConfig.banner_id = ""+list.get(pos).getId();
             start(MainActivity.class);
             finish();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected void onClick(int id, View view) {
          switch (id)
          {
              case R.id.tv_login:
              {
                  start(LoginActivity.class);
                  finish();
              }
                  break;
          }
    }

    @Override
    public void onJsonObjectResponse(Object o, NetworkParams paramsCode) {
       if(paramsCode==NetworkParams.CUPCAKE)
       {
           HomeGuideBean guideBean = (HomeGuideBean)o;
           if(guideBean.getErrorcode()==0)
           {
               list.addAll(guideBean.getList());
               picList.addAll(guideBean.getPicture());
               for(int i=0;i<picList.size();i++)
               {
                   final String url = picList.get(i).getUrl();
                   ImageView imageView = new ImageView(this);
                   imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                   imageView.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Intent intent = new Intent(HomeActivity.this, TransferActivity.class);
                           intent.putExtra("fragment", 14);
                           intent.putExtra("title","");
                           intent.putExtra("which",-1);
                           intent.putExtra("value",url);

                           startActivity(intent);
                       }
                   });
                   imageList.add(imageView);
                   Picasso.with(HomeActivity.this).load(picList.get(i).getImg()).into(imageView);
               }
              InitNav();

               adapter.notifyDataSetChanged();
           }
       }
    }

    public class ImageAdapter extends PagerAdapter
    {
        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageList.get(position));
            return imageList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageList.get(position));
        }
    }
}

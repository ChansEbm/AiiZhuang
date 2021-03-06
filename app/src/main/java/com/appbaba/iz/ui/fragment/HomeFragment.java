package com.appbaba.iz.ui.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.appbaba.iz.AppKeyMap;
import com.appbaba.iz.FragmentHomeBinding;
import com.appbaba.iz.ItemBottomListBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.TopMenuBinding;
import com.appbaba.iz.adapters.CommonBinderAdapter;
import com.appbaba.iz.adapters.CommonBinderHolder;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.entity.Index.HomeBean;
import com.appbaba.iz.entity.main.album.CasesAttrSelection;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.impl.BinderOnItemClickListener;
import com.appbaba.iz.impl.UpdateClickCallback;
import com.appbaba.iz.impl.UpdateUIListener;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.method.SpaceItemDecoration;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.tools.LogTools;
import com.appbaba.iz.ui.activity.LoginActivity;
import com.appbaba.iz.ui.activity.TransferActivity;
import com.appbaba.iz.ui.activity.album.ProductActivity;
import com.appbaba.iz.widget.NavViewPager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/4/1.
 */
public class HomeFragment extends BaseFgm implements BinderOnItemClickListener{
    private FragmentHomeBinding homeBinding;
    private  RelativeLayout relate_1,relate_2,relate_3,relate_4,relate_5;
    private  ImageView iv_menu;
//    iv_banner
    private NavViewPager navViewPager;
    private RecyclerView recyclerView;
    private  PopupWindow window;
    private List<ImageView> imageList = new ArrayList<>();
    private ScrollView scrollview;
    private ImageAdapter imageAdapter;



    private CommonBinderAdapter<HomeBean.SubjectListEntity> adapter;
    private  List<HomeBean.SubjectListEntity> list;
    private  int height ;// 15 : 8 高度
    public UpdateClickCallback callback;
    public UpdateUIListener uiListener;

    @Override
    protected void initViews() {
       homeBinding = (FragmentHomeBinding)viewDataBinding;
//        iv_banner = homeBinding.ivBanner;
        iv_menu = homeBinding.includeTopTitle.ivMenu;
        recyclerView = homeBinding.recycler;
        scrollview = homeBinding.scrollView;
        navViewPager = homeBinding.navViewPager;

        relate_1 = homeBinding.relate1;
        relate_2 = homeBinding.relate2;
        relate_3 = homeBinding.relate3;
        relate_4 = homeBinding.relate4;
        relate_5 = homeBinding.relate5;

        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams)navViewPager.getLayoutParams();
        params1.width = MethodConfig.metrics.widthPixels;
        params1.height = MethodConfig.GetHeightFor16v9(params1.width);
        height = MethodConfig.GetHeightFor4v3(params1.width);
        navViewPager.setLayoutParams(params1);

        int m = MethodConfig.dip2px(getContext(),8);
        LinearLayout.LayoutParams p2 = ( LinearLayout.LayoutParams)relate_2.getLayoutParams();
        p2.width = (MethodConfig.metrics.widthPixels-m*3)/2;
        LinearLayout.LayoutParams p1 = ( LinearLayout.LayoutParams)relate_1.getLayoutParams();
        LinearLayout.LayoutParams p3 = ( LinearLayout.LayoutParams)relate_3.getLayoutParams();
        LinearLayout.LayoutParams p4 = ( LinearLayout.LayoutParams)relate_4.getLayoutParams();
        LinearLayout.LayoutParams p5 = ( LinearLayout.LayoutParams)relate_5.getLayoutParams();
        p2.height = MethodConfig.GetHeightFor16v9(p2.width);

        p5.height= p4.height = p3.height = p2.height;
        p5.width = p4.width = p3.width = p2.width;

        relate_2.setLayoutParams(p2);
        relate_3.setLayoutParams(p3);
        relate_4.setLayoutParams(p4);
        relate_5.setLayoutParams(p5);

//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)iv_item_test.getLayoutParams();
//        params.width = MethodConfig.metrics.widthPixels;
//        params.height = MethodConfig.metrics.widthPixels/2;
//        iv_item_test.setLayoutParams(params);


        list = new ArrayList<>();
        adapter = new CommonBinderAdapter<HomeBean.SubjectListEntity>(getContext(),R.layout.item_index_bottom_view,list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, HomeBean.SubjectListEntity subjectListEntity) {
                ItemBottomListBinding bottomListBinding = (ItemBottomListBinding)viewDataBinding;
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height);
                bottomListBinding.ivItemView.setLayoutParams(params);
                Picasso.with(getContext()).load(subjectListEntity.getThumb()).into(bottomListBinding.ivItemView);
                bottomListBinding.setItem(subjectListEntity);
            }
        };
        recyclerView.setLayoutManager(new org.solovyev.android.views.llm.LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(MethodConfig.dip2px(getContext(),4)));
        recyclerView.setAdapter(adapter);
//        homeBinding.scrollView.setscrol;
        String auth="";
        if(MethodConfig.localUser!=null)
        {
            auth = MethodConfig.localUser.getAuth();
        }
        networkModel.HomeIndex(auth, NetworkParams.INDEX);
    }


    public  void  ScrollToTop()
    {
        if(recyclerView!=null)
        recyclerView.clearFocus();
    }




    @Override
    protected void initEvents() {
        iv_menu.setOnClickListener(this);
        homeBinding.includeTopTitle.tvChooseClient.setOnClickListener(this);
        adapter.setBinderOnItemClickListener(this);
        relate_1.setOnClickListener(this);
        relate_2.setOnClickListener(this);
        relate_3.setOnClickListener(this);
        relate_4.setOnClickListener(this);
        relate_5.setOnClickListener(this);
//        iv_banner.setOnClickListener(this);
    }

    @Override
    protected void noNetworkStatus() {
//        Picasso.with(getContext()).load()
    }

    @Override
    protected void onClick(int id, View view) {
        switch (id)
        {
            case R.id.iv_menu: {
                try {

                    ViewDataBinding viewDataBinding=DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.popup_top_menu, null, false);
                    TopMenuBinding menuBinding = (TopMenuBinding)viewDataBinding;

                    menuBinding.tvIntroduce.setOnClickListener(this);
                    menuBinding.tvContract.setOnClickListener(this);
                    menuBinding.tvNearby.setOnClickListener(this);
                    menuBinding.tvScan.setOnClickListener(this);
                    window = new PopupWindow(viewDataBinding.getRoot(), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    window.setBackgroundDrawable(new BitmapDrawable());
                    window.showAsDropDown(iv_menu);
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
                break;
            case R.id.tv_scan: {
                Intent intent = new Intent(getContext(), TransferActivity.class);
                intent.putExtra("fragment", 0);
                startActivity(intent);
                HideInfoWindow();
            }
                break;
            case R.id.tv_introduce: {
                Intent intent = new Intent(getContext(), TransferActivity.class);
                intent.putExtra("fragment", 14);
                intent.putExtra("title",getString(R.string.popup_pinpaijieshao));
                intent.putExtra("which",1);
                if(MethodConfig.localUser!=null)
                {
                    intent.putExtra("value",MethodConfig.localUser.getInfo().getSeller_id());
                }
                startActivity(intent);
                HideInfoWindow();
            }
                break;
            case R.id.tv_nearby: {
                Intent intent = new Intent(getContext(), TransferActivity.class);
                intent.putExtra("fragment", 14);
                intent.putExtra("title",getString(R.string.popup_mendian));
                intent.putExtra("which",2);
                if(MethodConfig.localUser!=null)
                {
                    intent.putExtra("value",MethodConfig.localUser.getInfo().getSeller_id());
                }
                startActivity(intent);
                HideInfoWindow();
            }
                break;
            case R.id.tv_contract: {
                Intent intent = new Intent(getContext(), TransferActivity.class);
                intent.putExtra("fragment", 14);
                intent.putExtra("title",getString(R.string.popup_bodadianhau));
                intent.putExtra("which",3);
                if(MethodConfig.localUser!=null)
                {
                    intent.putExtra("value",MethodConfig.localUser.getInfo().getSeller_id());
                }
                startActivity(intent);
                HideInfoWindow();
            }
                break;
            case R.id.tv_choose_client:
            {
                Intent intent;
                if(MethodConfig.localUser==null)
                {
                     intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    intent = new Intent(getContext(), TransferActivity.class);
                    intent.putExtra("fragment", 13);
                    intent.putExtra("which",1);
                    intent.putExtra("title","选择客户");
                    startActivityForResult(intent,100);
                }
            }
                break;
//            case R.id.iv_banner: {
//                Intent intent = new Intent(getContext(), TransferActivity.class);
//                intent.putExtra("fragment", 14);
//                intent.putExtra("title", getString(R.string.popup_pinpaijieshao));
//                intent.putExtra("which", 1);
//                if (MethodConfig.localUser != null) {
//                    intent.putExtra("value", MethodConfig.localUser.getInfo().getSeller_id());
//                }
//                startActivity(intent);
//            }
//                break;
            case R.id.relate_1:

            case R.id.relate_2:

            case R.id.relate_3:

            case R.id.relate_4:

            case R.id.relate_5: {
                HandleClickAction((HomeBean.CateListEntity)view.getTag());
            }
                break;

        }
    }


    @Override
    public void onBinderItemClick(View clickItem, int parentId, int pos) {
        Intent intent = new Intent(getContext(),TransferActivity.class);
        intent.putExtra("fragment",4);
        intent.putExtra("title",list.get(pos).getTitle());
        intent.putExtra("id", list.get(pos).getSubject_id());
        startActivity(intent);
    }

    public  void  HideInfoWindow()
    {
        if(window!=null && window.isShowing())
        {
            window.dismiss();
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode)
        {
            case 100:
                if(MethodConfig.chooseClient!=null)
                {
//                    homeBinding.includeTopTitle.tvChooseClient.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    homeBinding.includeTopTitle.tvChooseClient.setText(MethodConfig.chooseClient.getName());
                }
                break;
        }
    }
    public void InitNav()
    {
        navViewPager.InitView();
        navViewPager.SetPointViewStyle(NavViewPager.PointsView.POINT_STYLE_CIRCLE, ContextCompat.getColor(getContext(),R.color.half_white),
                ContextCompat.getColor(getContext(),R.color.application_base_color),ContextCompat.getColor(getContext(),R.color.white),10);
        navViewPager.SetDistance(10);
        navViewPager.IsCircleBound(10);
        imageAdapter = new ImageAdapter();
        navViewPager.SetAdapter(imageAdapter);
        //navViewPager.notifyDataChange();
        if(imageList.size()>1)
        navViewPager.AutoScrollNoCancel(2000);
    }

    @Override
    public void onJsonObjectSuccess(Object t, NetworkParams paramsCode) {
        if(paramsCode==NetworkParams.INDEX)
        {
            HomeBean bean = (HomeBean)t;
            if(bean.getErrorcode()==0)
            {
                MethodConfig.sellerInfoEntity= bean.getSeller_info();
                for(int i=0;i< bean.getSeller_info().getBanner().size();i++) {
                    final String url = bean.getSeller_info().getBanner().get(i).getUrl();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), TransferActivity.class);
                            intent.putExtra("fragment", 14);
                            intent.putExtra("title","");
                            intent.putExtra("which",-1);
                            intent.putExtra("value",url);

                            startActivity(intent);
                        }
                    });
                    imageList.add(imageView);
                    Picasso.with(getContext()).load(bean.getSeller_info().getBanner().get(i).getImg()).into(imageView);
                }
                InitNav();
                Picasso.with(getContext()).load(bean.getSeller_info().getLogo()).into(homeBinding.includeTopTitle.ivLogo);

                list.clear();
                list.addAll(bean.getSubject_list());

                List<HomeBean.CateListEntity> list = bean.getCate_list();
                for(int i=0;i<list.size();i++)
                {
                    HomeBean.CateListEntity entity = list.get(i);
                   switch (entity.getIndex_id())
                   {
                       case "1":
                           homeBinding.tvTitle1Cn.setText(entity.getTitle());
                           homeBinding.tvTitle1En.setText(entity.getEn_title());
                           homeBinding.tvDetail1.setText(entity.getDesc());
                           homeBinding.relate1.setTag(entity);
                           Picasso.with(getContext()).load(entity.getIndex_thumb()).into(homeBinding.ivItem1);
                           break;
                       case "2":
                           homeBinding.tvTitle2Cn.setText(entity.getTitle());
                           homeBinding.tvTitle2En.setText(entity.getEn_title());
                           homeBinding.tvDetail2.setText(entity.getDesc());
                           homeBinding.relate2.setTag(entity);
                           Picasso.with(getContext()).load(entity.getIndex_thumb()).into(homeBinding.ivItem2);
                           break;
                       case "3":
                           homeBinding.tvTitle3Cn.setText(entity.getTitle());
                           homeBinding.tvTitle3En.setText(entity.getEn_title());
                           homeBinding.tvDetail3.setText(entity.getDesc());
                           homeBinding.relate3.setTag(entity);
                           Picasso.with(getContext()).load(entity.getIndex_thumb()).into(homeBinding.ivItem3);
                           break;
                       case "4":
                           homeBinding.tvTitle4Cn.setText(entity.getTitle());
                           homeBinding.tvTitle4En.setText(entity.getEn_title());
                           homeBinding.tvDetail4.setText(entity.getDesc());
                           homeBinding.relate4.setTag(entity);
                           Picasso.with(getContext()).load(entity.getIndex_thumb()).into(homeBinding.ivItem4);
                           break;
                       case "5":
                           homeBinding.tvTitle5Cn.setText(entity.getTitle());
                           homeBinding.tvTitle5En.setText(entity.getEn_title());
                           homeBinding.tvDetail5.setText(entity.getDesc());
                           homeBinding.relate5.setTag(entity);
                           Picasso.with(getContext()).load(entity.getIndex_thumb()).into(homeBinding.ivItem5);
                           break;
                   }
                }
                adapter.notifyDataSetChanged();
            }
            else
            {
                AppTools.showNormalSnackBar(getView(),bean.getMsg());
            }
        }
    }

    public void HandleClickAction(HomeBean.CateListEntity entity)
    {
        if(TextUtils.isEmpty(entity.getReturnX()))
        {
            return;
        }
        switch (entity.getReturnX())
        {
            case "product":
                if(callback!=null)
                {
                    callback.Update(entity.getCate_id(),"m",entity.getSize_id(),entity.getStyle_id());
                }
                break;
            case "subject":
            {
                if(uiListener!=null)
                {
                    uiListener.uiUpData(null);
                }
//                Intent intent = new Intent(getContext(),TransferActivity.class);
//                intent.putExtra("fragment",4);
//                intent.putExtra("title",entity.getTitle());
//                intent.putExtra("id",  entity.getSubject_id());
//                startActivity(intent);
            }
                break;
            case "cases": {
                if(callback!=null) {
                    callback.Update(entity.getCases_id(), entity.getSpace_id(), "m", entity.getStyle_id());
                }
            }
                break;
            case "article": {
                Intent intent = new Intent(getContext(), TransferActivity.class);
                intent.putExtra("title", entity.getTitle());
                intent.putExtra("id", entity.getSeller_article_cate());
                intent.putExtra("fragment", 12);
                startActivity(intent);
            }
                break;

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

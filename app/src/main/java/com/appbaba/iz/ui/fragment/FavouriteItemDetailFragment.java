package com.appbaba.iz.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appbaba.iz.AppKeyMap;
import com.appbaba.iz.FragmentFavouriteItemDetailBinding;
import com.appbaba.iz.FragmentHomeTopNearbyBinding;
import com.appbaba.iz.ItemFavouriteBinding;
import com.appbaba.iz.ItemFavouriteDetailBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.adapters.CommonBinderAdapter;
import com.appbaba.iz.adapters.CommonBinderHolder;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.entity.Favourite.FavouriteDetailBean;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.impl.BinderOnItemClickListener;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.tools.LogTools;
import com.appbaba.iz.ui.activity.TransferActivity;
import com.appbaba.iz.widget.DialogView.ShareDialogView;
import com.google.zxing.qrcode.encoder.QRCode;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by ruby on 2016/4/7.
 */
public class FavouriteItemDetailFragment extends BaseFgm implements Toolbar.OnMenuItemClickListener,BinderOnItemClickListener{

    private FragmentFavouriteItemDetailBinding detailBinding;
//    private RecyclerView recyclerView;

    private  String title,id;
    private  int height;
//    private CommonBinderAdapter<FavouriteDetailBean.InfoEntity.DetailListEntity> adapter;
    private List<FavouriteDetailBean.InfoEntity.DetailListEntity> list;
    private FavouriteDetailBean bean;

    @Override
    protected void initViews() {

        title = getArguments().getString("title");
        id = getArguments().getString("id","");
        height = MethodConfig.GetHeightFor16v9(MethodConfig.metrics.widthPixels);
        detailBinding = (FragmentFavouriteItemDetailBinding)viewDataBinding;

        detailBinding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.icon_top_back);
        detailBinding.includeTopTitle.toolBar.setBackgroundResource(R.color.application_base_color);
        detailBinding.includeTopTitle.toolBar.getMenu().add(Menu.NONE,R.id.menu_unlike,0,"").setIcon(R.mipmap.icon_unlike).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        detailBinding.includeTopTitle.toolBar.getMenu().add(Menu.NONE, R.id.menu_share, 1, "").setIcon(R.mipmap.icon_share).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        detailBinding.includeTopTitle.toolBar.setOnMenuItemClickListener(this);
        //detailBinding.includeTopTitle.title.setText(title);

        String auth ="";
        if(MethodConfig.localUser!=null)
        {
            auth = MethodConfig.localUser.getAuth();
        }

        list = new ArrayList<>();

//        adapter = new CommonBinderAdapter<FavouriteDetailBean.InfoEntity.DetailListEntity>(getContext(),R.layout.item_favourite_detail_view,list) {
//            @Override
//            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, FavouriteDetailBean.InfoEntity.DetailListEntity detailListEntity) {
//                ItemFavouriteDetailBinding itemFavouriteDetailBinding = (ItemFavouriteDetailBinding)viewDataBinding;
//                float scale = 0;
//                try
//                {
//                    scale = Float.parseFloat(detailListEntity.getScale());
//                }
//                catch (Exception ex)
//                {}
//
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int)( MethodConfig.metrics.widthPixels/scale));
//                Picasso.with(getContext()).load(detailListEntity.getUrl()).into(itemFavouriteDetailBinding.ivItemView);
//                itemFavouriteDetailBinding.ivItemView.setLayoutParams(params);
//                itemFavouriteDetailBinding.setItem(detailListEntity);
//            }
//        };
//
//        recyclerView = detailBinding.recycler;
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(adapter);


        networkModel.HomeSubjectDetail(auth,id, NetworkParams.SUBJECTDETAIL);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_favourite_detail, menu);
    }

    @Override
    protected void initEvents() {
        detailBinding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
       // adapter.setBinderOnItemClickListener(this);
    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {

    }

    @Override
    public void onBinderItemClick(View clickItem, int parentId, int pos) {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_favourite_item_detail;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case  R.id.menu_unlike:
                if(MethodConfig.localUser!=null)
                {

                    String customer_id = AppTools.getStringSharedPreferences(AppKeyMap.CUSTOMERID,"");
                    if(bean!=null && bean.getInfo()!=null)
                    networkModel.HomeSubjectCollectSubject(MethodConfig.localUser.getAuth(),customer_id,bean.getInfo().getSubject_id(),NetworkParams.COLLECTION);
                }
                else
                {
                    AppTools.showNormalSnackBar(getView(),"请先登录");
                }

                break;
            case R.id.menu_share:
            {
                String url = AppKeyMap.HEAD+"Page/subject_detail?subject_id="+id;
                ShareDialogView dialogView = new ShareDialogView(getContext(),bean.getInfo().getTitle(),bean.getInfo().getThumb(),bean.getInfo().getDesc(),url);
                dialogView.show();
            }
                break;
        }
        return true;
    }

    @Override
    public void onJsonObjectSuccess(Object t, NetworkParams paramsCode) {
        if(paramsCode==NetworkParams.SUBJECTDETAIL)
        {
            bean = (FavouriteDetailBean)t;
            detailBinding.setItem(bean.getInfo());
            list.addAll(bean.getInfo().getDetail_list());
            if(bean.getInfo().is_collect()==1)
            {
                detailBinding.includeTopTitle.toolBar.getMenu().getItem(0).setIcon(R.mipmap.icon_like);
            }
            else
            {
                detailBinding.includeTopTitle.toolBar.getMenu().getItem(0).setIcon(R.mipmap.icon_unlike);
            }
            AddItem(0);
        }
        if(paramsCode==NetworkParams.COLLECTION)
        {
            if(bean.getErrorcode()==0) {
                bean.getInfo().setIs_collect(bean.getInfo().is_collect()==1? 0 : 1);
                if (bean.getInfo().is_collect()==1) {
                    detailBinding.includeTopTitle.toolBar.getMenu().getItem(0).setIcon(R.mipmap.icon_like);
                } else {
                    detailBinding.includeTopTitle.toolBar.getMenu().getItem(0).setIcon(R.mipmap.icon_unlike);
                }
            }
            else
            {
                AppTools.showNormalSnackBar(getView(),bean.getMsg());
            }
        }
    }

    public  void  AddItem(int pos)
    {
        for(int i=pos;i<list.size();i++)
        {
            FavouriteDetailBean.InfoEntity.DetailListEntity  detailListEntity = list.get(i);
            ItemFavouriteDetailBinding itemFavouriteDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.item_favourite_detail_view,null,false);
                float scale = 0;
                try
                {
                    scale = Float.parseFloat(detailListEntity.getScale());
                }
                catch (Exception ex)
                {}

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int)( MethodConfig.metrics.widthPixels/scale));
                Picasso.with(getContext()).load(detailListEntity.getUrl()).into(itemFavouriteDetailBinding.ivItemView);
                itemFavouriteDetailBinding.ivItemView.setLayoutParams(params);
                itemFavouriteDetailBinding.setItem(detailListEntity);
            detailBinding.linearContent.addView(itemFavouriteDetailBinding.getRoot());
        }
    }
}

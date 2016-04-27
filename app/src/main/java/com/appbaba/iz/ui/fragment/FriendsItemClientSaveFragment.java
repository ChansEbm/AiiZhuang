package com.appbaba.iz.ui.fragment;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.appbaba.iz.AppKeyMap;
import com.appbaba.iz.FragmentFriendClientSaveBinding;
import com.appbaba.iz.ItemFriendClientCollectionBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.adapters.CommonBinderAdapter;
import com.appbaba.iz.adapters.CommonBinderHolder;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.entity.Friends.FriendsClientCollectionBean;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.method.FullyGridLayoutManager;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.tools.SDCardTools;
import com.appbaba.iz.widget.DialogView.ShareDialogView;
import com.appbaba.iz.widget.GridSpacingItemDecoration;
import com.squareup.picasso.Picasso;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/4/19.
 */
public class FriendsItemClientSaveFragment extends BaseFgm implements Toolbar.OnMenuItemClickListener{
    private FragmentFriendClientSaveBinding saveBinding;
    private RecyclerView recyclerView1,recyclerView2;
    private  CommonBinderAdapter<FriendsClientCollectionBean.CasesListEntity> adapter1;
    private  CommonBinderAdapter<FriendsClientCollectionBean.ProductListEntity> adapter2;
    private List<FriendsClientCollectionBean.CasesListEntity> list1;
    private List<FriendsClientCollectionBean.ProductListEntity> list2;

    private  String id,name;
    private  int height1,height2;
    private  FriendsClientCollectionBean bean;

    @Override
    protected void initViews() {
        id = getArguments().getString("id","");
        name = getArguments().getString("title","");
        saveBinding = (FragmentFriendClientSaveBinding)viewDataBinding;
        saveBinding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.more_arrow_dark_left);
        saveBinding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        saveBinding.includeTopTitle.toolBar.getMenu().add(0,R.id.menu_share,0,"").setIcon(R.mipmap.icon_share_dark).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        saveBinding.includeTopTitle.title.setText(name+"的收藏夹");
        saveBinding.includeTopTitle.title.setTextColor(Color.BLACK);

        recyclerView1 = saveBinding.recycler1;
        recyclerView2 = saveBinding.recycler2;

        recyclerView1.addItemDecoration(new GridSpacingItemDecoration(2, 10, true));
        recyclerView1.setLayoutManager(new FullyGridLayoutManager(getContext(),2,10));

        recyclerView2.addItemDecoration(new GridSpacingItemDecoration(3, 30, true));
        FullyGridLayoutManager manager = new FullyGridLayoutManager(getContext(), 3,30);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        manager.setSmoothScrollbarEnabled(true);
        recyclerView2.setLayoutManager(manager);
// new LinearLayoutManager()
        recyclerView1.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);

        height1 = MethodConfig.GetHeightFor4v3((MethodConfig.metrics.widthPixels-10)/2);
        height2 = (MethodConfig.metrics.widthPixels-4*30)/3;

        list1 = new ArrayList<>();
        list2 = new ArrayList<>();

        adapter1 = new CommonBinderAdapter<FriendsClientCollectionBean.CasesListEntity>(getContext(),R.layout.item_friend_client_collection,list1) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, FriendsClientCollectionBean.CasesListEntity casesListEntity) {
                ItemFriendClientCollectionBinding binding = (ItemFriendClientCollectionBinding)viewDataBinding;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height1);
                binding.ivItemView.setLayoutParams(params);
                Picasso.with(getContext()).load( casesListEntity.getThumb()).into(binding.ivItemView);
                binding.tvItemView.setText(casesListEntity.getTitle());
//                casesListEntity.getTitle();
//                casesListEntity.getThumb();
            }
        };
        adapter2 = new CommonBinderAdapter<FriendsClientCollectionBean.ProductListEntity>(getContext(),R.layout.item_friend_client_collection,list2) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, FriendsClientCollectionBean.ProductListEntity productListEntity) {
                ItemFriendClientCollectionBinding binding = (ItemFriendClientCollectionBinding)viewDataBinding;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(height2,height2);
                binding.ivItemView.setLayoutParams(params);
                Picasso.with(getContext()).load( productListEntity.getThumb()).into(binding.ivItemView);
                binding.tvItemView.setText(productListEntity.getModel());
            }
        };
        recyclerView1.setAdapter(adapter1);
        recyclerView2.setAdapter(adapter2);

        networkModel.HomeMarketingCustomerCollect(MethodConfig.localUser.getAuth(),id, NetworkParams.CUSTOMERCOLLECTION);
    }

    @Override
    protected void initEvents() {
        saveBinding.includeTopTitle.toolBar.setOnMenuItemClickListener(this);
        saveBinding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_friend_item_client_save;
    }

    @Override
    public void onJsonObjectSuccess(Object t, NetworkParams paramsCode) {
        if(paramsCode==NetworkParams.CUSTOMERCOLLECTION)
        {
            bean = (FriendsClientCollectionBean)t;
            list1.addAll(bean.getCases_list());
            list2.addAll(bean.getProduct_list());
            adapter1.notifyDataSetChanged();
            adapter2.notifyDataSetChanged();

            if(list1.size()>0)
            {
                saveBinding.linearCase.setVisibility(View.VISIBLE);
            }
            if(list2.size()>0)
            {
                saveBinding.linearProduct.setVisibility(View.VISIBLE);
            }
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,bean.getCases_list().size()/2*(height1+20));
//            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,bean.getCases_list().size()/3*(height2+20));
//            recyclerView1.setLayoutParams(params);
//            recyclerView2.setLayoutParams(params2);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_share:
                if(bean!=null) {
                    String url = AppKeyMap.HEAD + "Page/customer_collect?customer_id=" + id;
                    File file = new File(SDCardTools.getSDCardPosition()+"collection.png");
                    if(!file.exists())
                    {
                        try {
                            InputStream inputStream = getContext().getResources().getAssets().open("collection.png");
                            FileOutputStream outputStream = new FileOutputStream(file);
                            byte[] buffer = new byte[2048];
                            int count = 0;
                            while ( (count = inputStream.read(buffer))>0)
                            {
                                outputStream.write(buffer,0,count);
                            }
                            outputStream.close();
                            inputStream.close();
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }

                    ShareDialogView dialogView = new ShareDialogView( name+"的收藏夹", name, file.getAbsolutePath(), url,getContext());
                    dialogView.show();
                }
                break;
        }
        return false;
    }
}

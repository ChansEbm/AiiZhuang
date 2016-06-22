package com.appbaba.platform.ui.fragment;


import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appbaba.platform.FragmentProductBinding;
import com.appbaba.platform.ItemCollectionBinding;
import com.appbaba.platform.ItemProductBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.base.BaseFragment;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.product.ProductListBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.AnimationCallBack;
import com.appbaba.platform.impl.BinderOnItemClickListener;
import com.appbaba.platform.impl.SearchCallBack;
import com.appbaba.platform.method.GridSpacingItemDecoration;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.method.SpaceItemDecoration;
import com.appbaba.platform.ui.activity.products.ProductDetailActivity;
import com.appbaba.platform.ui.activity.products.ProductWebDetailActivity;
import com.appbaba.platform.ui.fragment.comm.CommSearchFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/5/4.
 */
public class ProductFragment extends BaseFragment implements BinderOnItemClickListener{
    private FragmentProductBinding binding;
    private RecyclerView recyclerView;

    private CommonBinderAdapter<ProductListBean.ProductsEntity> adapter;
    private List<ProductListBean.ProductsEntity> list;
    private int gridMode = 0;
    private GridSpacingItemDecoration gridSpacingItemDecoration;
    private SpaceItemDecoration spaceItemDecoration;
    private AnimationCallBack callBack;
    private int height = 0,currentPage=1,pageTemp = 1,num =12,scrollEnd=0;
    private boolean isSearch = false;
    private String word,  sort;
    private List<String> styleList, spaceList;

    private CommSearchFragment commSearchFragment;

    private SearchCallBack searchCallBack;

    @Override
    protected void InitView() {
        binding = (FragmentProductBinding)viewDataBinding;
//        binding.includeTopTitle.title.setText("商品");
//        binding.includeTopTitle.title.setTextColor(Color.BLACK);
//        binding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
//        binding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.icon_grid);
//        binding.includeTopTitle.toolBar.getMenu().add(0,R.id.action_search,0,"").setIcon(R.mipmap.icon_search_black_bg).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        recyclerView = binding.recycle;
        swipeRefreshLayout = binding.swRefresh;
        binding.frameSearchContent.getLayoutParams().width = MethodConfig.metrics.widthPixels/5*4;
        commSearchFragment = new CommSearchFragment();
        getChildFragmentManager().beginTransaction().add(R.id.frame_search_content,commSearchFragment).commit();
    }

    @Override
    protected void InitData() {
        list = new ArrayList<>();
        height = (MethodConfig.metrics.widthPixels-MethodConfig.dip2px(getContext(),41))/2;

      spaceItemDecoration =  new SpaceItemDecoration(2);
      gridSpacingItemDecoration =  new GridSpacingItemDecoration(2,5,true);
      networkModel.ProductList(currentPage,num, NetworkParams.CUPCAKE);
      SetRecyclerViewData();
        searchCallBack = new SearchCallBack() {
            @Override
            public void Update(String word, String sort, List<String> styleList, List<String> spaceList) {
                if(binding.drawerLayout.isDrawerOpen(Gravity.RIGHT))
                {
                    binding.drawerLayout.closeDrawers();
                }
                currentPage=pageTemp = 1;
                ProductFragment.this.word = word;
                ProductFragment.this.sort = sort;
                ProductFragment.this.styleList = styleList;
                ProductFragment.this.spaceList = spaceList;
                isSearch = true;
                networkModel.SearchProduct( word,  sort,  styleList, spaceList,currentPage,num,NetworkParams.DONUT);
            }
        };
        commSearchFragment.callBack = searchCallBack;
    }

    @Override
    protected void InitEvent() {
        binding.swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage=1;
                pageTemp = 1;
                word = "";
                sort = "";
                styleList = new ArrayList<>();
                spaceList = new ArrayList<>();
                isSearch = false;
                networkModel.ProductList(currentPage,num, NetworkParams.CUPCAKE);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(swipeRefreshLayout.isRefreshing())
                {
                    return;
                }
                if(newState==1)
                {
                    callBack.StartAnimation();
                    scrollEnd=recyclerView.computeVerticalScrollOffset();
                }
                else if(newState==0)
                {
                    callBack.EndAnimation();
                    if( scrollEnd == recyclerView.computeVerticalScrollOffset())
                    {
                        if(list.size()%num ==0) {
                            pageTemp = currentPage + 1;
                            networkModel.ProductList(pageTemp,num, NetworkParams.CUPCAKE);
                        }
                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
        binding.drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(callBack!=null)
                    callBack.StartAnimation();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if(callBack!=null)
                    callBack.EndAnimation();
            }
        });
    }

    @Override
    protected void InitListening() {

        adapter.setBinderOnItemClickListener(this);
        binding.ivTopLeft.setOnClickListener(this);
        binding.ivTopRight.setOnClickListener(this);
    }

    @Override
    protected void OnClick(int id, View view) {
         switch (id)
         {
             case R.id.iv_top_left:
                 if(gridMode==0)
                 {
                     gridMode = 1;
                     ((ImageView)view).setImageResource(R.mipmap.icon_grid);
                 }
                 else
                 {
                     gridMode = 0;
                     ((ImageView)view).setImageResource(R.mipmap.icon_list);
                 }
                 SetRecyclerViewData();
                 break;
             case R.id.iv_top_right:
                 binding.drawerLayout.openDrawer(Gravity.RIGHT);
                 break;
         }
    }

    public void SetRecyclerViewData()
    {
        if (gridMode==0)
        {
            adapter = new CommonBinderAdapter<ProductListBean.ProductsEntity>(getContext(),R.layout.item_product_view,list) {
                @Override
                public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, ProductListBean.ProductsEntity o) {
                    ItemProductBinding itemProductBinding = (ItemProductBinding)viewDataBinding;
                    itemProductBinding.setItem(o);
                    itemProductBinding.ivItem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height));
                    if(!TextUtils.isEmpty(o.getThumb())) {
                        Picasso.with(getContext()).load(o.getThumb()).into(itemProductBinding.ivItem);
                    }
                }
            };
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
            recyclerView.removeItemDecoration(spaceItemDecoration);
            recyclerView.addItemDecoration(gridSpacingItemDecoration);
            recyclerView.setAdapter(adapter);
            adapter.setBinderOnItemClickListener(this);
        }
        else
        {
            adapter = new CommonBinderAdapter<ProductListBean.ProductsEntity>(getContext(),R.layout.item_collection_view,list) {
                @Override
                public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, ProductListBean.ProductsEntity o) {
                    ItemCollectionBinding itemCollectionBinding = (ItemCollectionBinding)viewDataBinding;
                    itemCollectionBinding.setItem(o);
                    if(!TextUtils.isEmpty(o.getThumb())) {
                        Picasso.with(getContext()).load(o.getThumb()).into(itemCollectionBinding.ivItem);
                    }
                }
            };
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.removeItemDecoration(gridSpacingItemDecoration);
            recyclerView.setAdapter(adapter);
            adapter.setBinderOnItemClickListener(this);
        }
    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_product;
    }

    @Override
    public void onBinderItemClick(View clickItem, int parentId, int pos) {
        String id =  list.get(pos).getId();
        Intent intent = new Intent(getContext(),ProductWebDetailActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("url",list.get(pos).getBuy_link());
        intent.putExtra("title",list.get(pos).getTitle());
        intent.putExtra("desc","");
        intent.putExtra("image",list.get(pos).getThumb());
        startActivity(intent);
    }

    @Override
    public void onBinderItemLongClick(View clickItem, int parentId, int pos) {

    }

    public void  setCallBack(AnimationCallBack callBack)
    {
        this.callBack = callBack;
    }

    @Override
    public void onJsonObjectSuccess(BaseBean baseBean, NetworkParams paramsCode) {

        if(pageTemp==1)
        {
            list.clear();
        }
            if (baseBean.getErrorcode() == 0) {
                ProductListBean bean = (ProductListBean) baseBean;
                    currentPage = pageTemp;
                    if(paramsCode==NetworkParams.CUPCAKE) {
                        if (bean.getProducts().size() == 0) {
                            Toast.makeText(getContext(), "已经最后一页了", Toast.LENGTH_LONG).show();
                        } else {
                            list.addAll(bean.getProducts());
                        }
                    }
                    else if(paramsCode==NetworkParams.DONUT)
                    {
                        if(bean.getResult().size()==0)
                        {
                            Toast.makeText(getContext(), "没有更多搜索结果了", Toast.LENGTH_LONG).show();
                        }
                        else {
                            list.addAll(bean.getResult());
                        }
                    }
            }

        adapter.notifyDataSetChanged();
    }
}

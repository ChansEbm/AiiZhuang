package com.appbaba.platform.ui.fragment;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.FragmentInspirationBinding;
import com.appbaba.platform.ItemInspirationBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.base.BaseFragment;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.inspiration.InspirationEntity;
import com.appbaba.platform.entity.inspiration.InspirationListBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.AnimationCallBack;
import com.appbaba.platform.impl.BinderOnItemClickListener;
import com.appbaba.platform.impl.SearchCallBack;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.method.SpaceItemDecoration;
import com.appbaba.platform.ui.activity.inspiration.InspirationDetailActivity;
import com.appbaba.platform.ui.activity.inspiration.InspirationSearchActivity;
import com.appbaba.platform.ui.fragment.comm.CommSearchFragment;
import com.google.repacked.antlr.v4.runtime.misc.Nullable;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/5/4.
 */
public class InspirationFragment extends BaseFragment implements BinderOnItemClickListener{
    private FragmentInspirationBinding binding;
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    private CommonBinderAdapter<InspirationEntity> commonBinderAdapter;
    private List<InspirationEntity> list;
    private AnimationCallBack callBack;
    private int height = 0; // 4:3 比例的高度
    private int currentPage = 1,pageTemp=1,num=5,scrollEnd = 0;
    private String word,sort;
    private List<String> styleList, spaceList;
    private boolean isSearch = false;
    private SearchCallBack searchCallBack;

    CommSearchFragment commSearchFragment;

    @Override
    protected void InitView() {
        binding = (FragmentInspirationBinding)viewDataBinding;

        recyclerView = binding.recycle;
        swipeRefreshLayout = binding.swRefresh;
        drawerLayout = binding.drawerLayout;
//        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);

        binding.frameSearchContent.getLayoutParams().width = MethodConfig.metrics.widthPixels/5*4;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(MethodConfig.dip2px(getContext(),10)));

        commSearchFragment = new CommSearchFragment();

        getChildFragmentManager().beginTransaction().add(R.id.frame_search_content,commSearchFragment).commit();

    }

    @Override
    protected void InitData() {



        height = MethodConfig.GetHeight(MethodConfig.metrics.widthPixels,5,3);
        list =new ArrayList<>();

        commonBinderAdapter = new CommonBinderAdapter<InspirationEntity>(getContext(),R.layout.item_inspiration_view,list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, InspirationEntity o) {
                ItemInspirationBinding itemInspirationBinding = (ItemInspirationBinding)viewDataBinding;
                itemInspirationBinding.setItem(o);
                itemInspirationBinding.ivItem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height));
                if(!TextUtils.isEmpty(o.getImage()))
                Picasso.with(getContext()).load(o.getImage()).resize(MethodConfig.metrics.widthPixels,height).into(itemInspirationBinding.ivItem);
            }
        };
        recyclerView.setAdapter(commonBinderAdapter);

        networkModel.InspirationList(currentPage, num,NetworkParams.CUPCAKE);

        searchCallBack = new SearchCallBack() {
            @Override
            public void Update(String word, String sort, List<String> styleList, List<String> spaceList) {
                if(binding.drawerLayout.isDrawerOpen(Gravity.RIGHT))
                {
                    binding.drawerLayout.closeDrawers();
                }
                InspirationFragment.this.word = word;
                InspirationFragment.this.sort = sort;
                InspirationFragment.this.styleList = styleList;
                InspirationFragment.this.spaceList = spaceList;

                 currentPage=pageTemp = 1;
                 isSearch = true;
                 networkModel.SearchInspiration( word, sort, styleList, spaceList,pageTemp,num,NetworkParams.DONUT);
            }
        };
        commSearchFragment.callBack = searchCallBack;
    }

    @Override
    protected void InitEvent() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(swipeRefreshLayout.isRefreshing())
                {
                    return;
                }
                if(newState==1)
                {
                    if(callBack!=null)
                    callBack.StartAnimation();
                    scrollEnd=recyclerView.computeVerticalScrollOffset();

                    if(scrollEnd==0)
                    {
                        swipeRefreshLayout.setEnabled(true);
                    }
                    else
                    {
                        swipeRefreshLayout.setEnabled(false);
                    }
                }
                else if(newState==0)
                {
                    callBack.EndAnimation();
                    Log.e("rrr",""+(recyclerView.computeVerticalScrollOffset()/currentPage)+ "     "+recyclerView.getLayoutManager().getHeight());
                    if(scrollEnd > 0 && scrollEnd == recyclerView.computeVerticalScrollOffset()) {
                        if (list.size() % num == 0) {
                            pageTemp = currentPage + 1;
                            if (!isSearch) {
                                networkModel.InspirationList(pageTemp, num, NetworkParams.CUPCAKE);
                            } else
                            {
                                networkModel.SearchInspiration(word, sort, styleList, spaceList, pageTemp, num, NetworkParams.DONUT);
                            }
                        }
                        else
                        {
                            Toast.makeText(getContext(),"滚动至底部，请往回拉哟",Toast.LENGTH_LONG).show();
                        }
                    }
                    else if(recyclerView.computeVerticalScrollOffset()/currentPage > recyclerView.getLayoutManager().getHeight()/2)
                    {
                        if (list.size() % num == 0) {
                            pageTemp = currentPage + 1;
                            if (!isSearch) {
                                networkModel.InspirationList(pageTemp, num, NetworkParams.CUPCAKE);
                            } else
                            {
                                networkModel.SearchInspiration(word, sort, styleList, spaceList, pageTemp, num, NetworkParams.DONUT);
                            }
                        }
                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                Log.e("sdf",""+recyclerView.computeVerticalScrollOffset());
                swipeRefreshLayout.setEnabled(recyclerView.computeVerticalScrollOffset()==0? true : false);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                pageTemp = 1;
                word = "";
                sort = "";
                spaceList = new ArrayList<>();
                styleList = new ArrayList<>();
                isSearch = false;
                networkModel.InspirationList(currentPage,num,NetworkParams.CUPCAKE);
            }

        });

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
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
        binding.ivSearch.setOnClickListener(this);
        commonBinderAdapter.setBinderOnItemClickListener(this);
    }

    @Override
    protected void OnClick(int id, View view) {
        switch (id)
        {
            case R.id.iv_search:
                drawerLayout.openDrawer(Gravity.RIGHT);
                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_inspiration;
    }

    @Override
    public void onBinderItemClick(View clickItem, int parentId, int pos) {
        Intent intent = new Intent(getContext(),InspirationDetailActivity.class);
        intent.putExtra("id",list.get(pos).getId());
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

         if(baseBean.getErrorcode()==0)
         {
              InspirationListBean bean =  (InspirationListBean)baseBean;
                 if(paramsCode==NetworkParams.CUPCAKE) {
                     if(bean.getInspiration().size()==0)
                     {
                         Toast.makeText(getContext(),"滚动至底部，请往回拉哟",Toast.LENGTH_LONG).show();
                     }
                     else {
                         if(pageTemp==1)
                         {
                             list.clear();
                         }
                         list.addAll(bean.getInspiration());
                     }
                 }
                 else if(paramsCode==NetworkParams.DONUT)
                 {
                     if(bean.getResult().size()==0)
                     {
                         Toast.makeText(getContext(),"非常抱歉，您要找的灵感不存在喔~",Toast.LENGTH_LONG).show();
                     }
                     else {
                         if(pageTemp==1)
                         {
                             list.clear();
                         }
                         list.addAll(bean.getResult());
                     }
                 }
                 currentPage = pageTemp;
         }
        commonBinderAdapter.notifyDataSetChanged();
    }
}

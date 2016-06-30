package com.appbaba.platform.ui.fragment;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.appbaba.platform.FragmentUserItemBinding;
import com.appbaba.platform.ItemCollectionBinding;
import com.appbaba.platform.ItemUserCollectionBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.base.BaseFragment;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.User.BaseItemBean;
import com.appbaba.platform.entity.User.UserCollectionListBean;
import com.appbaba.platform.entity.User.UserConcernListBean;
import com.appbaba.platform.entity.User.UserInspirationListBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.AnimationCallBack;
import com.appbaba.platform.impl.BinderOnItemClickListener;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.method.SpaceItemDecoration;
import com.appbaba.platform.ui.activity.inspiration.DesignWorkDetailActivity;
import com.appbaba.platform.ui.activity.inspiration.InspirationDetailActivity;
import com.appbaba.platform.ui.activity.products.ProductWebDetailActivity;
import com.baidu.mapapi.map.Text;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/5/10.
 */
public class UserItemFragment extends BaseFragment implements BinderOnItemClickListener{
    private FragmentUserItemBinding binding;
    private AnimationCallBack callBack;
    private RecyclerView recyclerView;

    private CommonBinderAdapter<BaseItemBean> adapter;
    private List<BaseItemBean> list;
    private int index= 0 ;
    private  int currentPage = 1,pageTemp=1,num = 12,scrollEnd=0;
    private boolean isCreate = false;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    protected void InitView() {
       binding = (FragmentUserItemBinding)viewDataBinding;
        recyclerView = binding.recycle;
        swipeRefreshLayout = binding.swRefresh;
        if(MethodConfig.IsLogin())
        {
            swipeRefreshLayout.setEnabled(true);
        }
        else
        {
            swipeRefreshLayout.setEnabled(false);
        }
        isCreate  =true;
    }

    public void  AddListData(List<BaseItemBean> dataList)
    {
        if(list==null)
            return;
        list.clear();
        list.addAll(dataList);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.e("usrevisible","isVisibleToUser"+isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
        if(isCreate)
        {
           if(isVisibleToUser && MethodConfig.IsLogin() && list.size()==0)
           {
               GetData();
           }
        }
    }

    public void  setCallBack(AnimationCallBack callBack)
    {
        this.callBack = callBack;
    }

    @Override
    protected void InitData() {
        list = new ArrayList<>();

        adapter = new CommonBinderAdapter<BaseItemBean>(getContext(),R.layout.item_user_bottom_collection_view,list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, BaseItemBean o) {
                ItemUserCollectionBinding collectionBinding = (ItemUserCollectionBinding)viewDataBinding;
                collectionBinding.setItem(o);
                collectionBinding.tvItemStatus.setVisibility(View.GONE);
                if(index==2)
                {
                    collectionBinding.tvItemTitle.setText(o.getName());
                    String image = TextUtils.isEmpty(o.getPicture()) ? o.getPicture_thumb() :o.getPicture();
                    Picasso.with(getContext()).load(image).resize(500,500).into(collectionBinding.ivItem);
                }
                else {
                    collectionBinding.tvItemTitle.setText(o.getTitle());
                    if(!TextUtils.isEmpty(o.getImage()))
                    Picasso.with(getContext()).load(o.getImage()).resize(500,500).into(collectionBinding.ivItem);
                }

            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        if(MethodConfig.IsLogin())
        {
            GetData();
        }
    }

    @Override
    protected void InitEvent() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!MethodConfig.IsLogin())
                {
                    swipeRefreshLayout.setRefreshing(false);
                    return;
                }
                list.clear();
                   GetData();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState==1)
                {
                    if(callBack!=null)
                    callBack.StartAnimation();
                    scrollEnd=recyclerView.computeVerticalScrollOffset();
                }
                else if(newState==0)
                {
                    if(callBack!=null)
                    callBack.EndAnimation();
                    if( scrollEnd == recyclerView.computeVerticalScrollOffset() && MethodConfig.IsLogin())
                    {
                        if(list.size()%num ==0) {
                            pageTemp = currentPage + 1;
                            GetData();
                        }
                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                swipeRefreshLayout.setEnabled(recyclerView.computeVerticalScrollOffset() == 0);
            }
        });

    }

    public void GetData()
    {
        switch (index)
        {
            case 0:
                networkModel.UserInspiration(MethodConfig.userInfo.getToken(),currentPage,num, NetworkParams.CUPCAKE);
                break;
            case 1:
                networkModel.UserKeep(MethodConfig.userInfo.getToken(),currentPage,num, NetworkParams.CUPCAKE);
                break;
            case 2:
                networkModel.UserConcern(MethodConfig.userInfo.getToken(),currentPage,num, NetworkParams.CUPCAKE);
                break;
        }
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
        return R.layout.fragment_me_item_view;
    }

    @Override
    public void onJsonObjectSuccess(BaseBean baseBean, NetworkParams paramsCode) {
        if(baseBean.getErrorcode()==0)
        {
            if(paramsCode==NetworkParams.CUPCAKE)
            {
                swipeRefreshLayout.setEnabled(true);
                switch (index)
                {
                    case 0: {
                        UserInspirationListBean bean = (UserInspirationListBean) baseBean;
                        list.addAll(bean.getInspiration_list());
                    }
                        break;
                    case 1: {
                        UserCollectionListBean bean = (UserCollectionListBean) baseBean;
                        list.addAll(bean.getFavorite_list());
                    }
                        break;
                    case 2: {
                        UserConcernListBean bean = (UserConcernListBean) baseBean;
                        list.addAll(bean.getConcern_list());
                    }
                        break;
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onBinderItemClick(View clickItem, int parentId, int pos) {
        if(index==0)
        {
            Intent intent = new Intent(getContext(),InspirationDetailActivity.class);
            intent.putExtra("id",list.get(pos).getId());
            startActivity(intent);
        }
        else if(index==1)
        {
            Intent intent = new Intent(getContext(),ProductWebDetailActivity.class);
            intent.putExtra("id",list.get(pos).getId());
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(getContext(),DesignWorkDetailActivity.class);
            String id = TextUtils.isEmpty(list.get(pos).getSubscribe_id()) ? list.get(pos).getId() : list.get(pos).getSubscribe_id();
            if(TextUtils.isEmpty(id))
            {
                Toast.makeText(getContext(),"ID 异常",Toast.LENGTH_LONG).show();
            }
            else {
                intent.putExtra("designerID", id);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onBinderItemLongClick(View clickItem, int parentId, int pos) {

    }
}

package com.appbaba.iz.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.appbaba.iz.FragmentFriendArticleBinding;
import com.appbaba.iz.ItemFriendArticleBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.adapters.CommonBinderAdapter;
import com.appbaba.iz.adapters.CommonBinderHolder;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.entity.Friends.FriendsArticleBean;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.impl.BinderOnItemClickListener;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.method.SpaceItemDecoration;
import com.appbaba.iz.model.NetworkModel;
import com.appbaba.iz.ui.activity.TransferActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/4/15.
 */
public class FriendsItemArticleFragment extends BaseFgm implements  BinderOnItemClickListener{

    private FragmentFriendArticleBinding articleBinding;
    private RecyclerView recyclerView;

    private  String title,id;
    private  int height=0;
    private CommonBinderAdapter<FriendsArticleBean.ListEntity> adapter;
    private List<FriendsArticleBean.ListEntity> list;

    @Override
    protected void initViews() {
         title = getArguments().getString("title");
         id = getArguments().getString("id");
        height = MethodConfig.GetHeightFor16v9(MethodConfig.metrics.widthPixels);

        articleBinding = (FragmentFriendArticleBinding)viewDataBinding;
        articleBinding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.more_arrow_dark_left);
        articleBinding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        articleBinding.includeTopTitle.title.setText(title.trim());
        articleBinding.includeTopTitle.title.setTextColor(Color.BLACK);

        recyclerView = articleBinding.recycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(MethodConfig.dip2px(getContext(),10)));

        list = new ArrayList<>();
        adapter = new CommonBinderAdapter<FriendsArticleBean.ListEntity>(getContext(),R.layout.item_friend_article_view,list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, FriendsArticleBean.ListEntity listEntity) {
                ItemFriendArticleBinding itemFriendArticleBinding = (ItemFriendArticleBinding)viewDataBinding;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height);
                if(!TextUtils.isEmpty(listEntity.getThumb())) {
                    Picasso.with(getContext()).load(listEntity.getThumb()).into(itemFriendArticleBinding.ivItemView);
                }
                else
                {
                    itemFriendArticleBinding.ivItemView.setVisibility(View.GONE);
                }
                itemFriendArticleBinding.ivItemView.setLayoutParams(params);
                itemFriendArticleBinding.setItem(listEntity);
            }
        };
        recyclerView.setAdapter(adapter);

        String auth = "";
        if(MethodConfig.localUser!=null)
        {
            auth = MethodConfig.localUser.getAuth();
        }

        networkModel.HomeMarketingArticle(auth,id,1,10, NetworkParams.ARTICLE);
    }

    @Override
    protected void initEvents() {

        articleBinding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
        adapter.setBinderOnItemClickListener(this);
    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {

    }

    @Override
    public void onBinderItemClick(View clickItem, int parentId, int pos) {
        Intent intent = new Intent(getContext(), TransferActivity.class);
        intent.putExtra("fragment", 14);
//        intent.putExtra("title",list.get(pos).getTitle());
        intent.putExtra("which",6);
        intent.putExtra("value",list.get(pos).getArticle_id());
        startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_friend_item_article;
    }

    @Override
    public void onJsonObjectSuccess(Object t, NetworkParams paramsCode) {
        if(paramsCode==NetworkParams.ARTICLE)
        {
            FriendsArticleBean bean = (FriendsArticleBean)t;
            list.addAll(bean.getList());
            adapter.notifyDataSetChanged();
        }

    }
}

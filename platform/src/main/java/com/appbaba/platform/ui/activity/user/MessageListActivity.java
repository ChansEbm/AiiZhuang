package com.appbaba.platform.ui.activity.user;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.appbaba.platform.ActivityMessageListBinding;
import com.appbaba.platform.ItemMessageListBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.base.BaseActivity;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.User.MessageDetailBean;
import com.appbaba.platform.entity.User.MessageListBean;
import com.appbaba.platform.entity.product.ProductListBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.BinderOnItemClickListener;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.method.SpaceItemDecoration;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/6/24.
 */
public class MessageListActivity extends BaseActivity implements BinderOnItemClickListener{
    private ActivityMessageListBinding binding;
    private RecyclerView recyclerView;
    private CommonBinderAdapter<MessageListBean.PushInformationEntity> adapter;
    private List<MessageListBean.PushInformationEntity> list;

    private  int currentPage = 1,pageTemp=1,num=10,endScroll = 0;

    @Override
    protected void InitView() {
        binding = (ActivityMessageListBinding)viewDataBinding;
        binding.includeTopTitle.title.setText("我的消息");
        binding.includeTopTitle.title.setTextColor(Color.BLACK);
        binding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        binding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.ic_back_dark);
        recyclerView = binding.recycle;
        swipeRefreshLayout = binding.swRefresh;
    }

    @Override
    protected void InitData() {
        list = new ArrayList<>();
         adapter = new CommonBinderAdapter<MessageListBean.PushInformationEntity>(this,R.layout.item_message_list_view,list) {
             @Override
             public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, MessageListBean.PushInformationEntity pushInformationEntity) {
                 ItemMessageListBinding listBinding = (ItemMessageListBinding)viewDataBinding;
                 listBinding.setItem(pushInformationEntity);
                 int isRead = pushInformationEntity.getIs_read();
                 if(isRead==0)
                 {
                     listBinding.iv1.setVisibility(View.INVISIBLE);
                     listBinding.tvCircle.setVisibility(View.VISIBLE);
                 }
                 else
                 {
                     listBinding.iv1.setVisibility(View.VISIBLE);
                     listBinding.tvCircle.setVisibility(View.INVISIBLE);
                 }

             }
         };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpaceItemDecoration(2));
        recyclerView.setAdapter(adapter);

        networkModel.UserMessageList(MethodConfig.userInfo.getToken(),currentPage,num, NetworkParams.CUPCAKE);
    }

    @Override
    protected void InitEvent() {
        binding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageTemp = currentPage=1;
                endScroll = 0;
                networkModel.UserMessageList(MethodConfig.userInfo.getToken(),pageTemp,num, NetworkParams.CUPCAKE);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.e("hide","滚动多少："+endScroll +"         哈哈:"+recyclerView.computeVerticalScrollOffset());
                if(endScroll<recyclerView.computeVerticalScrollOffset())
                {
                    endScroll = recyclerView.computeVerticalScrollOffset();
                }
                else if(endScroll==recyclerView.computeVerticalScrollOffset())
                {
                    if(list.size()%num==0) {
                        pageTemp = currentPage + 1;
                        networkModel.UserMessageList(MethodConfig.userInfo.getToken(), pageTemp, num, NetworkParams.CUPCAKE);
                    }
                }
            }
        });
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
        return R.layout.activity_message_list;
    }

    @Override
    public void onJsonObjectSuccess(BaseBean baseBean, NetworkParams paramsCode) {
        if(paramsCode==NetworkParams.CUPCAKE)
        {
            if(baseBean.getErrorcode()==0)
            {
                MessageListBean listBean = (MessageListBean)baseBean;
                if(listBean.getPush_information().size()>0)
                {
                    currentPage = pageTemp;
                    if(currentPage==1)
                    {
                        list.clear();
                    }
                    list.addAll(listBean.getPush_information());
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    pageTemp = currentPage;
                }

            }
        }
    }

    @Override
    public void onBinderItemClick(View clickItem, int parentId, int pos) {
        String id = ""+list.get(pos).getUser_push_id();
        list.get(pos).setIs_read(1);
        Intent intent = new Intent(this, MessageDetailActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBinderItemLongClick(View clickItem, int parentId, int pos) {

    }
}

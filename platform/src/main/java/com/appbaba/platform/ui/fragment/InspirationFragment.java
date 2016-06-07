package com.appbaba.platform.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.view.menu.MenuWrapperFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.FragmentInspirationBinding;
import com.appbaba.platform.ItemInspirationBinding;
import com.appbaba.platform.PopupRippleBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.base.BaseFragment;
import com.appbaba.platform.databinding.ItemRippleBinding;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.inspiration.InspirationListBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.AnimationCallBack;
import com.appbaba.platform.impl.BinderOnItemClickListener;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.method.SpaceItemDecoration;
import com.appbaba.platform.tools.AppTools;
import com.appbaba.platform.tools.LogTools;
import com.appbaba.platform.ui.activity.InspirationDetailActivity;
import com.appbaba.platform.ui.activity.InspirationSearchActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/5/4.
 */
public class InspirationFragment extends BaseFragment implements BinderOnItemClickListener{
    private FragmentInspirationBinding binding;
    private RecyclerView recyclerView;

    private CommonBinderAdapter<InspirationListBean.InspirationEntity> commonBinderAdapter;
    private List<InspirationListBean.InspirationEntity> list;
    private AnimationCallBack callBack;
    private int height = 0; // 4:3 比例的高度

    @Override
    protected void InitView() {
        binding = (FragmentInspirationBinding)viewDataBinding;
        recyclerView = binding.recycle;

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(MethodConfig.dip2px(getContext(),10)));
    }

    @Override
    protected void InitData() {

        height = MethodConfig.GetHeight(MethodConfig.metrics.widthPixels,4,3);
        list =new ArrayList<>();

        commonBinderAdapter = new CommonBinderAdapter<InspirationListBean.InspirationEntity>(getContext(),R.layout.item_inspiration_view,list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, InspirationListBean.InspirationEntity o) {
                ItemInspirationBinding itemInspirationBinding = (ItemInspirationBinding)viewDataBinding;
                itemInspirationBinding.setItem(o);
                itemInspirationBinding.ivItem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height));
                itemInspirationBinding.ivItem.setBackgroundColor(Color.DKGRAY);
                if(!TextUtils.isEmpty(o.getThumb()))
                Picasso.with(getContext()).load(AppKeyMap.HEAD+o.getThumb()).into(itemInspirationBinding.ivItem);
            }
        };
        recyclerView.setAdapter(commonBinderAdapter);

        networkModel.InspirationList(1,NetworkParams.CUPCAKE);

    }

    @Override
    protected void InitEvent() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState==1)
                {
                    callBack.StartAnimation();
                }
                else if(newState==0)
                {
                    callBack.EndAnimation();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

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
                StartActivity(InspirationSearchActivity.class);
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
              list.addAll(bean.getInspiration());
              commonBinderAdapter.notifyDataSetChanged();
         }
    }
}

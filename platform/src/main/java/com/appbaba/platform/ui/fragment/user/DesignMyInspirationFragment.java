package com.appbaba.platform.ui.fragment.user;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.appbaba.platform.FragmentMyInspirationBinding;
import com.appbaba.platform.ItemMyInspirationBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.base.BaseFragment;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.User.MyInspirationBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.BinderOnItemClickListener;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.method.SpaceItemDecoration;
import com.appbaba.platform.ui.activity.inspiration.InspirationDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/6/8.
 */
public class DesignMyInspirationFragment extends BaseFragment implements BinderOnItemClickListener{

    private FragmentMyInspirationBinding binding;
    private RecyclerView recyclerView;

    private List<MyInspirationBean.MyInspirationEntity> list;
    private CommonBinderAdapter<MyInspirationBean.MyInspirationEntity> adapter;

    private int page=1,num=12;


    @Override
    protected void InitView() {
        binding = (FragmentMyInspirationBinding)viewDataBinding;
        recyclerView = binding.recycle;

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void InitData() {
        list = new ArrayList<>();
        adapter = new CommonBinderAdapter<MyInspirationBean.MyInspirationEntity>(getContext(),R.layout.item_my_inspiration_view,list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, MyInspirationBean.MyInspirationEntity myInspirationEntity) {
                ItemMyInspirationBinding itemMyInspirationBinding = (ItemMyInspirationBinding)viewDataBinding;
                itemMyInspirationBinding.setItem(myInspirationEntity);
                itemMyInspirationBinding.tvStatus.setText(myInspirationEntity.getStatus());
                if(myInspirationEntity.getStatus_color().equals("1"))
                {

                    itemMyInspirationBinding.tvStatus.setBackgroundResource(R.color.base_color_tv_bg);
                }
                else
                {
                    itemMyInspirationBinding.tvStatus.setBackgroundResource(R.color.color_text_gravy);
                }
                if(!TextUtils.isEmpty(myInspirationEntity.getThumb()))
                Picasso.with(getContext()).load(myInspirationEntity.getThumb()).resize(500,500).into(itemMyInspirationBinding.ivItem);

            }
        };

        recyclerView.setAdapter(adapter);
        networkModel.MyInspiration(MethodConfig.userInfo.getToken(),page,num, NetworkParams.CUPCAKE);
    }

    public void Refresh()
    {
        list.clear();
        networkModel.MyInspiration(MethodConfig.userInfo.getToken(),page,num, NetworkParams.CUPCAKE);
    }

    @Override
    protected void InitEvent() {

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
        return R.layout.fragment_my_inspiration;
    }

    @Override
    public void onJsonObjectSuccess(BaseBean baseBean, NetworkParams paramsCode) {
        if(paramsCode==NetworkParams.CUPCAKE)
        {
            MyInspirationBean bean = (MyInspirationBean)baseBean;

            list.addAll(bean.getMy_inspiration());
            adapter.notifyDataSetChanged();
            recyclerView.clearFocus();
        }
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
}

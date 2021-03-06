package com.appbaba.platform.ui.fragment.user;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appbaba.platform.FragmentMyProductBinding;
import com.appbaba.platform.ItemMyProductBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.base.BaseFragment;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.User.MyProductBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.BinderOnItemClickListener;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.method.SpaceItemDecoration;
import com.appbaba.platform.ui.activity.products.ProductWebDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/6/8.
 */
public class DesignMyProductFragment extends BaseFragment implements BinderOnItemClickListener{

    private FragmentMyProductBinding binding;

    private RecyclerView recyclerView;

    private List<MyProductBean.MyGoodsEntity> list;
    private CommonBinderAdapter<MyProductBean.MyGoodsEntity> adapter;

    private int page=1,num=12;
    @Override
    protected void InitView() {
        binding = (FragmentMyProductBinding)viewDataBinding;
        recyclerView = binding.recycle;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void InitData() {
         list = new ArrayList<>();
        adapter = new CommonBinderAdapter<MyProductBean.MyGoodsEntity>(getContext(),R.layout.item_my_product_view,list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, MyProductBean.MyGoodsEntity myGoodsEntity) {
                ItemMyProductBinding itemMyProductBinding = (ItemMyProductBinding)viewDataBinding;
                itemMyProductBinding.setItem(myGoodsEntity);
                itemMyProductBinding.tvItemStatus.setVisibility(View.GONE);
                Picasso.with(getContext()).load(myGoodsEntity.getThumb()).into(itemMyProductBinding.ivItem);
            }
        };
        recyclerView.setAdapter(adapter);

        networkModel.MyProduct(MethodConfig.userInfo.getToken(),page,num, NetworkParams.CUPCAKE);
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
        return R.layout.fragment_my_product;
    }

    @Override
    public void onJsonObjectSuccess(BaseBean baseBean, NetworkParams paramsCode) {
        if(paramsCode==NetworkParams.CUPCAKE)
        {
            if(baseBean.getErrorcode()==0)
            {
                MyProductBean bean = (MyProductBean) baseBean;
                list.addAll(bean.getMy_goods());
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onBinderItemClick(View clickItem, int parentId, int pos) {
        Intent intent = new Intent(getContext(), ProductWebDetailActivity.class);
        intent.putExtra("id",list.get(pos).getId());
        startActivity(intent);
    }

    @Override
    public void onBinderItemLongClick(View clickItem, int parentId, int pos) {

    }
}

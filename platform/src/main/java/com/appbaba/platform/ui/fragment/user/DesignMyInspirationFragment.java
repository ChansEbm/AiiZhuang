package com.appbaba.platform.ui.fragment.user;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
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
import com.appbaba.platform.method.MethodConfig;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/6/8.
 */
public class DesignMyInspirationFragment extends BaseFragment {

    private FragmentMyInspirationBinding binding;
    private RecyclerView recyclerView;

    private List<MyInspirationBean.MyInspirationEntity> list;
    private CommonBinderAdapter<MyInspirationBean.MyInspirationEntity> adapter;

    private int page=0,num=12;


    @Override
    protected void InitView() {
        binding = (FragmentMyInspirationBinding)viewDataBinding;
        recyclerView = binding.recycle;
    }

    @Override
    protected void InitData() {
        list = new ArrayList<>();
        adapter = new CommonBinderAdapter<MyInspirationBean.MyInspirationEntity>(getContext(),R.layout.item_my_inspiration_view,list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, MyInspirationBean.MyInspirationEntity myInspirationEntity) {
                ItemMyInspirationBinding itemMyInspirationBinding = (ItemMyInspirationBinding)viewDataBinding;
                itemMyInspirationBinding.setItem(myInspirationEntity);
                if(myInspirationEntity.getStatus().equals("1"))
                {
                    itemMyInspirationBinding.tvStatus.setText("审核通过");
                    itemMyInspirationBinding.tvStatus.setBackgroundResource(R.color.base_color_tv_bg);
                }
                else
                {
                    itemMyInspirationBinding.tvStatus.setText("审核通过");
                    itemMyInspirationBinding.tvStatus.setBackgroundResource(R.color.color_text_gravy);
                }
                Picasso.with(getContext()).load(myInspirationEntity.getThumb()).into(itemMyInspirationBinding.ivItem);

            }
        };
        recyclerView.setAdapter(adapter);

        networkModel.MyInspiration(MethodConfig.userInfo.getToken(),page,num, NetworkParams.CUPCAKE);
    }

    @Override
    protected void InitEvent() {

    }

    @Override
    protected void InitListening() {

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
        }
    }
}

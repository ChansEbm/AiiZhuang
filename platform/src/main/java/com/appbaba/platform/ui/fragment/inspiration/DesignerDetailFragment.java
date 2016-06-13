package com.appbaba.platform.ui.fragment.inspiration;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.appbaba.platform.FragmentDetailBinding;
import com.appbaba.platform.ItemInspirationBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.base.BaseFragment;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.User.DesignerDetailBean;
import com.appbaba.platform.entity.inspiration.InspirationDetailBean;
import com.appbaba.platform.entity.inspiration.InspirationEntity;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.BinderOnItemClickListener;
import com.appbaba.platform.impl.UpdateClickCallback;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.method.SpaceItemDecoration;
import com.appbaba.platform.ui.activity.inspiration.DesignWorksActivity;
import com.squareup.picasso.Picasso;

import org.apache.http.impl.client.EntityEnclosingRequestWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/6/8.
 */
public class DesignerDetailFragment extends BaseFragment {
    private FragmentDetailBinding binding;
    private RecyclerView recyclerView;

    private List<InspirationEntity> list;
    private CommonBinderAdapter<InspirationEntity> adapter;
    private String designerID = "";
    private int height = 0; // 4:3 比例的高度

    public UpdateClickCallback callback;

    @Override
    protected void InitView() {
        binding = (FragmentDetailBinding) viewDataBinding;
        recyclerView = binding.recycle;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(MethodConfig.dip2px(getContext(),10)));
    }

    @Override
    protected void InitData() {
        height = MethodConfig.GetHeight(MethodConfig.metrics.widthPixels,4,3);

        list = new ArrayList<>();
        adapter = new CommonBinderAdapter<InspirationEntity>(getContext(),R.layout.item_inspiration_view,list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, InspirationEntity inspirationEntity) {
                ItemInspirationBinding itemInspirationBinding = (ItemInspirationBinding)viewDataBinding;
                itemInspirationBinding.setItem(inspirationEntity);
                itemInspirationBinding.ivItem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height));
                if(!TextUtils.isEmpty(inspirationEntity.getThumb()))
                    Picasso.with(getContext()).load(inspirationEntity.getThumb()).into(itemInspirationBinding.ivItem);
            }
        };
        recyclerView.setAdapter(adapter);
        String token = "";
        if(MethodConfig.IsLogin())
        {
            token = MethodConfig.userInfo.getToken();
        }
        networkModel.DesignerProfile(token,designerID, NetworkParams.CUPCAKE);
    }

    @Override
    protected void InitEvent() {

    }

    @Override
    protected void InitListening() {
        binding.tvAllDesign.setOnClickListener(this);
    }

    @Override
    protected void OnClick(int id, View view) {
        switch (id)
        {
            case R.id.tv_all_design:
                Intent intent = new Intent(getContext(), DesignWorksActivity.class);
                intent.putExtra("designerID",designerID);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_designer_data;
    }

    @Override
    public void onJsonObjectSuccess(BaseBean baseBean, NetworkParams paramsCode) {
        if(paramsCode==NetworkParams.CUPCAKE)
        {
           if(baseBean.getErrorcode()==0)
           {
               DesignerDetailBean detailBean = (DesignerDetailBean) baseBean;
               binding.setItem(detailBean.getInfor());
               if(callback!=null)
               {
                   callback.Update(""+detailBean.getInfor().getSubscribe());
               }
               binding.ivTopView.getLayoutParams().height = MethodConfig.metrics.heightPixels/2;
               Picasso.with(getContext()).load(detailBean.getInfor().getBackground()).into(binding.ivTopView);
               list.addAll(detailBean.getInspiration_list());
               adapter.notifyDataSetChanged();
           }
        }
    }

    public String getDesignerID() {
        return designerID;
    }

    public void setDesignerID(String designerID) {
        this.designerID = designerID;
    }

}

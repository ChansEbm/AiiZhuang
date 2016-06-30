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
import com.appbaba.platform.ui.activity.inspiration.InspirationDetailActivity;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/6/8.
 */
public class DesignerDetailFragment extends BaseFragment implements BinderOnItemClickListener{
    private FragmentDetailBinding binding;
    private RecyclerView recyclerView;

    private List<InspirationEntity> list;
    private CommonBinderAdapter<InspirationEntity> adapter;
    private String designerID = "";
    private int height = 0; // 4:3 比例的高度
    private DesignerDetailBean detailBean;

    @Override
    protected void InitView() {
        binding = (FragmentDetailBinding) viewDataBinding;
        recyclerView = binding.recycle;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(MethodConfig.dip2px(getContext(),10)));
        recyclerView.clearFocus();
    }

    @Override
    protected void InitData() {
        height = MethodConfig.GetHeight(MethodConfig.metrics.widthPixels,5,3);

        list = new ArrayList<>();
        adapter = new CommonBinderAdapter<InspirationEntity>(getContext(),R.layout.item_inspiration_view,list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, InspirationEntity inspirationEntity) {
                ItemInspirationBinding itemInspirationBinding = (ItemInspirationBinding)viewDataBinding;
                itemInspirationBinding.setItem(inspirationEntity);
                itemInspirationBinding.ivItem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height));
                if(!TextUtils.isEmpty(inspirationEntity.getImage()))
                    Picasso.with(getContext()).load(inspirationEntity.getImage()).resize(MethodConfig.metrics.widthPixels,height).into(itemInspirationBinding.ivItem);
            }
        };
        recyclerView.setAdapter(adapter);
        if(detailBean!=null)
        {
            binding.setItem(detailBean.getInfor());
            binding.ivTopView.getLayoutParams().height = MethodConfig.metrics.heightPixels/2;
            Picasso.with(getContext()).load(detailBean.getInfor().getBackground()).into(binding.ivTopView);
            list.addAll(detailBean.getInspiration_list());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void InitEvent() {

    }

    @Override
    protected void InitListening() {
        binding.tvAllDesign.setOnClickListener(this);
        adapter.setBinderOnItemClickListener(this);
    }

    @Override
    protected void OnClick(int id, View view) {
        switch (id)
        {
            case R.id.tv_all_design:
                Intent intent = new Intent(getContext(), DesignWorksActivity.class);
                intent.putExtra("designerID",designerID);
                intent.putExtra("name",binding.tvTitle.getText().toString().trim());
                intent.putExtra("desc",binding.tvDetail.getText().toString().trim());
                if(detailBean==null)
                {
                    intent.putExtra("image","");
                }
                else {
                    if(MethodConfig.userImage.containsKey(designerID))
                    {
                        intent.putExtra("image",MethodConfig.userImage.get(designerID));
                    }
                    else {
                        intent.putExtra("image",detailBean.getInfor().getBackground());
                    }
                }
                startActivity(intent);
                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_designer_data;
    }

    public String getDesignerID() {
        return designerID;
    }

    public void setDesignerID(String designerID) {
        this.designerID = designerID;
    }

   public void AddBeanData(DesignerDetailBean detailBean)
   {
       this.detailBean = detailBean;
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

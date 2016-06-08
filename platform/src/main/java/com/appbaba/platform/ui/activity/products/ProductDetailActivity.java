package com.appbaba.platform.ui.activity.products;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.appbaba.platform.ActivityProductDetailBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.base.BaseActivity;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.product.ProductDetailBean;
import com.appbaba.platform.eum.NetworkParams;
import com.squareup.picasso.Picasso;

/**
 * Created by ruby on 2016/5/12.
 */
public class ProductDetailActivity extends BaseActivity {
    private ActivityProductDetailBinding binding;
    String id = "";

    @Override
    protected void InitView() {
        binding = (ActivityProductDetailBinding)viewDataBinding;
        binding.includeTopTitle.title.setTextColor(Color.BLACK);
    }

    @Override
    protected void InitData() {

        id = getIntent().getStringExtra("id");
        if(!TextUtils.isEmpty(id))
        {
        networkModel.ProductDetail(id, NetworkParams.CUPCAKE);
        }

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
        return R.layout.activity_product_detail;
    }

    @Override
    public void onJsonObjectSuccess(BaseBean baseBean, NetworkParams paramsCode) {
        if(baseBean.getErrorcode()==0)
        {
            ProductDetailBean detailBean = (ProductDetailBean) baseBean;
            binding.setItem(detailBean.getGoods());
            Picasso.with(this).load(detailBean.getGoods().getThumb()).into(binding.ivDetail);
            binding.includeTopTitle.title.setText(detailBean.getGoods().getTitle());
        }
    }
}

package com.appbaba.platform.ui.activity;

import android.view.View;

import com.appbaba.platform.ActivityProductDetailBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.base.BaseActivity;

/**
 * Created by ruby on 2016/5/12.
 */
public class ProductDetailActivity extends BaseActivity {
    private ActivityProductDetailBinding binding;

    @Override
    protected void InitView() {
        binding = (ActivityProductDetailBinding)viewDataBinding;
    }

    @Override
    protected void InitData() {

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
}

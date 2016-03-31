package com.appbaba.iz.ui.activity;

import android.view.View;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.appbaba.iz.ActivityRegisterBrandsBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseAty;
import com.appbaba.iz.eum.NetworkParams;

/**
 * Created by ruby on 2016/3/31.
 */
public class RegisterBrandActivity extends BaseAty {
    ActivityRegisterBrandsBinding binding;
    private TextView tv_top_bar_register,tv_top_bar_brand;
    private ViewSwitcher viewSwitcher;
    @Override
    protected void initViews() {
        binding = (ActivityRegisterBrandsBinding)viewDataBinding;
        tv_top_bar_brand = binding.tvBrandIn;
        tv_top_bar_register = binding.tvRegister;
        viewSwitcher = binding.swBg;

    }

    @Override
    protected void initEvents() {
       tv_top_bar_register.setOnClickListener(this);
        tv_top_bar_brand.setOnClickListener(this);
        tv_top_bar_register.performClick();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login_register_brands;
    }

    @Override
    protected void onClick(int id, View view) {

        switch (id)
        {
            case R.id.tv_register:
                viewSwitcher.setDisplayedChild(0);
                tv_top_bar_brand.setSelected(false);
                tv_top_bar_register.setSelected(true);
                break;
            case R.id.tv_brand_in:
                viewSwitcher.setDisplayedChild(1);
                tv_top_bar_brand.setSelected(true);
                tv_top_bar_register.setSelected(false);
                break;
        }
    }

    @Override
    public void onJsonObjectResponse(Object o, NetworkParams paramsCode) {

    }
}

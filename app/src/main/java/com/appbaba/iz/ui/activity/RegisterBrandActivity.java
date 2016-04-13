package com.appbaba.iz.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.appbaba.iz.ActivityRegisterBrandsBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseAty;
import com.appbaba.iz.entity.SellerListBean;
import com.appbaba.iz.eum.NetworkParams;

/**
 * Created by ruby on 2016/3/31.
 */
public class RegisterBrandActivity extends BaseAty {
    ActivityRegisterBrandsBinding binding;
    private TextView tv_top_bar_register,tv_top_bar_brand;
    private ViewSwitcher viewSwitcher;


    private  SellerListBean sellerListBean;

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

        binding.tvRegisterBrands.setOnClickListener(this);

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
            case R.id.tv_register_brands:
                Intent intent = new Intent(this,TransferActivity.class);
                intent.putExtra("fragment",11);
                startActivityForResult(intent,100);
                break;
        }
    }

    @Override
    public void onJsonObjectResponse(Object o, NetworkParams paramsCode) {

        if(paramsCode==NetworkParams.SELLERLIST) {
            SellerListBean bean = (SellerListBean) o;
            sellerListBean = bean;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode)
        {
            case 100:
                binding.tvRegisterBrands.setText(data.getStringExtra("name"));
                binding.tvRegisterBrands.setTag(data.getStringExtra("id"));
                break;
        }
        Toast.makeText(this,""+requestCode+"  "+resultCode,Toast.LENGTH_LONG).show();
    }
}

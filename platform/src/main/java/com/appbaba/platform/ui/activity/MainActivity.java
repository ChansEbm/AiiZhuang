package com.appbaba.platform.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.appbaba.platform.R;
import com.appbaba.platform.base.BaseActivity;
import com.appbaba.platform.databinding.ActivityMainBinding;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.ui.fragment.InspirationFragment;
import com.appbaba.platform.ui.fragment.MeFragment;
import com.appbaba.platform.ui.fragment.ProductFragment;


public class MainActivity extends BaseActivity{

    private ActivityMainBinding binding;

    private FragmentManager manager;
    private Fragment fragment_temp;
    private InspirationFragment inspirationFragment;
    private ProductFragment productFragment;
    private MeFragment meFragment;
    private ImageView iv_temp;

    @Override
    protected void InitView() {
         binding = (ActivityMainBinding)viewDataBinding;
    }

    @Override
    protected void InitData() {
        manager = getSupportFragmentManager();
    }

    @Override
    protected void InitEvent() {

    }

    @Override
    protected void InitListening() {
        binding.ivInspiration.setOnClickListener(this);
        binding.ivProduct.setOnClickListener(this);
        binding.ivMe.setOnClickListener(this);
        binding.ivInspiration.performClick();
    }

    @Override
    protected void OnClick(int id, View view) {
        if(iv_temp!=null)
        {
            iv_temp.setSelected(false);
        }
        switch (id)
        {
            case R.id.iv_inspiration: {
                iv_temp = binding.ivInspiration;
                InspirationFragmentSelector();
            }
                break;
            case R.id.iv_product: {
                iv_temp = binding.ivProduct;
                ProductFragmentSelector();
            }
                break;
            case R.id.iv_me: {
                iv_temp = binding.ivMe;
                MeFragmentSelector();
            }
                break;
        }
        iv_temp.setSelected(true);
    }

    @Override
    public void onBackPressed() {
        if(MethodConfig.GetTicks()>2*1000) {
            MethodConfig.ShowToast("再按一次退出软件");
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    public void InspirationFragmentSelector()
    {
        FragmentTransaction transaction = manager.beginTransaction();
        if(inspirationFragment==null)
        {
            inspirationFragment = new InspirationFragment();
            transaction.add(R.id.frame_contain,inspirationFragment);
        }
        if(fragment_temp!=null)
        {
            transaction.hide(fragment_temp);
        }
        transaction.show(inspirationFragment).commit();
        fragment_temp = inspirationFragment;
    }

    public void ProductFragmentSelector()
    {
        FragmentTransaction transaction = manager.beginTransaction();
        if(productFragment==null)
        {
            productFragment = new ProductFragment();
            transaction.add(R.id.frame_contain,productFragment);
        }
        if(fragment_temp!=null)
        {
            transaction.hide(fragment_temp);
        }
        transaction.show(productFragment).commit();
        fragment_temp = productFragment;
    }

    public void MeFragmentSelector()
    {
        FragmentTransaction transaction = manager.beginTransaction();
        if(meFragment==null)
        {
            meFragment = new MeFragment();
            transaction.add(R.id.frame_contain,meFragment);
        }
        if(fragment_temp!=null)
        {
            transaction.hide(fragment_temp);
        }
        transaction.show(meFragment).commit();
        fragment_temp = meFragment;
    }
}

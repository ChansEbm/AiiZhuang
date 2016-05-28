package com.appbaba.platform.ui.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.appbaba.platform.R;
import com.appbaba.platform.base.BaseActivity;
import com.appbaba.platform.databinding.ActivityMainBinding;
import com.appbaba.platform.impl.AnimationCallBack;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.ui.fragment.InspirationFragment;
import com.appbaba.platform.ui.fragment.MeFragment;
import com.appbaba.platform.ui.fragment.ProductFragment;


public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    private ActivityMainBinding binding;

    private FragmentManager manager;
    private Fragment fragment_temp;
    private InspirationFragment inspirationFragment;
    private ProductFragment productFragment;
    private MeFragment meFragment;
    private ImageView iv_temp;
    private ViewPager viewPager;

    private FragmentAdapter adapter;
    private AnimationCallBack callBack;

    @Override
    protected void InitView() {
         binding = (ActivityMainBinding)viewDataBinding;
        viewPager = binding.viewpager;
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    protected void InitData() {
        manager = getSupportFragmentManager();
        adapter = new FragmentAdapter(manager);
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void InitEvent() {
       callBack = new AnimationCallBack() {
           @Override
           public void StartAnimation() {
               MainActivity.this.StartAnimation();
           }

           @Override
           public void EndAnimation() {
               MainActivity.this.EndAnimation();
           }
       };
    }

    @Override
    protected void InitListening() {
        binding.ivInspiration.setOnClickListener(this);
        binding.ivProduct.setOnClickListener(this);
        binding.ivMe.setOnClickListener(this);
        binding.ivInspiration.performClick();
        viewPager.addOnPageChangeListener(this);
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
                viewPager.setCurrentItem(0,false);
                iv_temp = binding.ivInspiration;
            }
                break;
            case R.id.iv_product: {
                viewPager.setCurrentItem(1,false);
                iv_temp = binding.ivProduct;
            }
                break;
            case R.id.iv_me: {
                viewPager.setCurrentItem(2,false);
                iv_temp = binding.ivMe;
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
                iv_temp.setSelected(false);
        switch (position)
        {
            case 0:
                binding.ivInspiration.setSelected(true);
                iv_temp = binding.ivInspiration;
                break;
            case 1:
                binding.ivProduct.setSelected(true);
                iv_temp = binding.ivProduct;
                break;
            case 2:
                binding.ivMe.setSelected(true);
                iv_temp = binding.ivMe;
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    int which = 0;
    public void StartAnimation() {

        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, 500);
        animation.setDuration(200);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(which==0)
                {
                    binding.ivInspiration.clearAnimation();
                    binding.ivInspiration.setVisibility(View.INVISIBLE);
                    binding.ivProduct.startAnimation(animation);
                    which++;
                }
                else if(which==1) {
                    binding.ivProduct.clearAnimation();
                    binding.ivProduct.setVisibility(View.INVISIBLE);
                    binding.ivMe.startAnimation(animation);
                    which++;
                }
                else if(which==2){
                    binding.ivMe.clearAnimation();
                    binding.ivMe.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        binding.ivInspiration.startAnimation(animation);
    }

    public void EndAnimation() {
        TranslateAnimation animation = new TranslateAnimation(0, 0, 1000, 0);
        animation.setDuration(1000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                which = 0;
                binding.ivInspiration.setVisibility(View.VISIBLE);
                binding.ivProduct.setVisibility(View.VISIBLE);
                binding.ivMe.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        binding.ivInspiration.startAnimation(animation);
        binding.ivProduct.startAnimation(animation);
        binding.ivMe.startAnimation(animation);
    }

    public class FragmentAdapter extends FragmentPagerAdapter
    {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0: {
                    if (inspirationFragment == null) {
                        inspirationFragment = new InspirationFragment();
                        inspirationFragment.setCallBack(callBack);
                    }
                    return inspirationFragment;
                }
                case 1:{
                    if (productFragment == null) {
                        productFragment = new ProductFragment();
                        productFragment.setCallBack(callBack);
                    }
                    return productFragment;
                }
                case 2:{
                    if (meFragment == null) {
                        meFragment = new MeFragment();
                    }
                    return meFragment;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }
}

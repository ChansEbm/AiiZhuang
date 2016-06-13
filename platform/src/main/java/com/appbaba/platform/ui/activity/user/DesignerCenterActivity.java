package com.appbaba.platform.ui.activity.user;

import android.print.PrintJobId;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.appbaba.platform.ActivityDesignerCenterBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.base.BaseActivity;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.ui.fragment.user.DesignMyInspirationFragment;
import com.appbaba.platform.ui.fragment.user.DesignMyProductFragment;
import com.appbaba.platform.widget.MyTextView;
import com.appbaba.platform.widget.SlowViewPager;

import org.apache.http.impl.client.EntityEnclosingRequestWrapper;

/**
 * Created by ruby on 2016/6/8.
 */
public class DesignerCenterActivity extends BaseActivity implements SlowViewPager.OnPageChangeListener{
    private ActivityDesignerCenterBinding binding;
    private MyTextView tv_my_inspiration,tv_my_product;
    private TextView tv_push_inspiration;
    private SlowViewPager viewPager;

    private DesignMyInspirationFragment inspirationFragment;
    private DesignMyProductFragment productFragment;

    private int moveWidth,isFirst=0;

    @Override
    protected void InitView() {
        binding = (ActivityDesignerCenterBinding)viewDataBinding;
        tv_my_inspiration = binding.tvMyInspiration;
        tv_my_product = binding.tvMyProduct;
        tv_push_inspiration = binding.tvPushInspiration;
        viewPager = binding.viewpager;

    }

    @Override
    protected void InitData() {
         moveWidth = MethodConfig.dip2px(this,100);
        inspirationFragment = new DesignMyInspirationFragment();
        productFragment = new DesignMyProductFragment();

        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));

    }

    @Override
    protected void InitEvent() {

    }

    @Override
    protected void InitListening() {
        viewPager.addOnPageChangeListener(this);
        binding.tvPushInspiration.setOnClickListener(this);
    }

    @Override
    protected void OnClick(int id, View view) {
         switch (id)
         {
             case R.id.tv_push_inspiration:
             {
                  StartActivity(InspirationUploadActivity.class);
             }
                 break;
         }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_designer_center;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(isFirst == 0)
        {
            tv_my_inspiration.setMM(moveWidth);
            isFirst = 1;
        }
        if(positionOffsetPixels>0)
        {
            tv_my_inspiration.setMM(-1*(int)((1-positionOffset)*moveWidth));
            tv_my_product.setMM((int)(positionOffset*moveWidth));
        }
        else if(positionOffsetPixels<0){
            tv_my_inspiration.setMM((int)((1-positionOffset)*moveWidth));
            tv_my_product.setMM(-1*(int)(positionOffset*moveWidth));
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class MyFragmentAdapter extends FragmentPagerAdapter
    {
        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    return inspirationFragment;
                case 1:
                    return productFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}

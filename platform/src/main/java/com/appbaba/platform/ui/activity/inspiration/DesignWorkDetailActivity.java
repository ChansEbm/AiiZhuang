package com.appbaba.platform.ui.activity.inspiration;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appbaba.platform.ActivityDesignWorkDetailBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.base.BaseActivity;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.ui.fragment.inspiration.DesignerDetailFragment;
import com.appbaba.platform.ui.fragment.inspiration.DesignerIMFragment;
import com.appbaba.platform.widget.SlowViewPager;

import java.util.List;

/**
 * Created by ruby on 2016/5/13.
 */
public class DesignWorkDetailActivity extends BaseActivity implements SlowViewPager.OnPageChangeListener{
    private ActivityDesignWorkDetailBinding binding;
    private SlowViewPager viewPager;
    private DesignerDetailFragment detailFragment;
    private DesignerIMFragment imFragment;


    private int moveWidth,isFirst = 0;

    private String designerID = "";

    @Override
    protected void InitView() {
        binding = (ActivityDesignWorkDetailBinding)viewDataBinding;
        viewPager = binding.viewpager;
    }

    @Override
    protected void InitData() {
        detailFragment = new DesignerDetailFragment();
        imFragment = new DesignerIMFragment();
        moveWidth = MethodConfig.dip2px(this,50);
        viewPager.setAdapter(new DesignerFragmentAdapter(getSupportFragmentManager()));
        designerID = getIntent().getStringExtra("designerID");
        detailFragment.setDesignerID(designerID);
        imFragment.setDesignerID(designerID);

    }

    @Override
    protected void InitEvent() {

    }

    @Override
    protected void InitListening() {
        viewPager.addOnPageChangeListener(this);

    }

    @Override
    protected void OnClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_design_work_detail;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
          if(isFirst == 0)
          {
              binding.tvData.setMM(moveWidth);
              isFirst = 1;
          }
        if(positionOffsetPixels>0)
        {
            binding.tvData.setMM(-1*(int)((1-positionOffset)*moveWidth));
            binding.tvIm.setMM((int)(positionOffset*moveWidth));
        }
        else if(positionOffsetPixels<0){
            binding.tvData.setMM((int)((1-positionOffset)*moveWidth));
            binding.tvIm.setMM(-1*(int)(positionOffset*moveWidth));
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class DesignerFragmentAdapter extends FragmentPagerAdapter
    {
        public DesignerFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return detailFragment;
                case 1:
                    return imFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}

package com.appbaba.platform.ui.fragment;

import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appbaba.platform.FragmentMeBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.base.BaseFragment;
import com.appbaba.platform.impl.BinderOnItemClickListener;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.method.SpaceItemDecoration;
import com.appbaba.platform.ui.activity.MeSettingActivity;
import com.appbaba.platform.widget.MyTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/5/4.
 */
public class MeFragment extends BaseFragment implements ViewPager.OnPageChangeListener{
    private FragmentMeBinding binding;
    private ViewPager viewPager;
    private LinearLayout linear_move;

    private List<Fragment> fragments;
    private int moveWidth = 0;

    @Override
    protected void InitView() {
        binding = (FragmentMeBinding)viewDataBinding;
        viewPager = binding.viewpager;
        linear_move = binding.linearMove;
    }

    @Override
    protected void InitData() {
        fragments = new ArrayList<>();
        for(int i=0;i<3;i++)
        {
            fragments.add(new MeItemFragment());
        }
        moveWidth = linear_move.getLayoutParams().width = MethodConfig.metrics.widthPixels/3;
        viewPager.setAdapter(new MyPageAdapter(getChildFragmentManager()));
    }

    @Override
    protected void InitEvent() {
//        Shader shader = new LinearGradient(0,0,0,30, Color.RED,Color.BLACK, Shader.TileMode.CLAMP);
//        binding.tvMyCollection.getPaint().setShader(shader);
    }

    @Override
    protected void InitListening() {

          viewPager.addOnPageChangeListener(this);
          binding.ivSetting.setOnClickListener(this);
    }

    @Override
    protected void OnClick(int id, View view) {
        switch (id)
        {
            case R.id.iv_setting:
                StartActivity(MeSettingActivity.class);
                break;
        }
    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_me;
    }

    int first=0;
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(first==0)
        {
            binding.tvMyCollection.setMM(moveWidth);
            first = 1;
        }
        if(positionOffsetPixels>0)
        {
            MyTextView textView1 = (MyTextView)binding.linearTitleParent.getChildAt(position);
            MyTextView textView2 = (MyTextView)binding.linearTitleParent.getChildAt(position+1);
            textView1.setMM(-1*(int)((1-positionOffset)*moveWidth));
            textView2.setMM((int)(positionOffset*moveWidth));
        }
        else if(positionOffsetPixels<0)
        {
            MyTextView textView1 = (MyTextView)binding.linearTitleParent.getChildAt(position);
            MyTextView textView2 = (MyTextView)binding.linearTitleParent.getChildAt(position-1);
            textView1.setMM((int)((1-positionOffset)*moveWidth));
            textView2.setMM(-1*(int)(positionOffset*moveWidth));
        }
        linear_move.setX(moveWidth*position+moveWidth*positionOffset);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class MyPageAdapter extends FragmentPagerAdapter
    {

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}

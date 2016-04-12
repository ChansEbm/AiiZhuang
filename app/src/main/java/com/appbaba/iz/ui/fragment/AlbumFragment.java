package com.appbaba.iz.ui.fragment;

import android.support.annotation.Size;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.RadioGroup;

import com.appbaba.iz.AlbumLayout;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.entity.Base.BaseBean;
import com.appbaba.iz.ui.fragment.album.EffectFragment;
import com.appbaba.iz.ui.fragment.album.ProductFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/4/1.
 */
public class AlbumFragment extends BaseFgm implements ViewPager.OnPageChangeListener, RadioGroup
        .OnCheckedChangeListener {
    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private AppCompatRadioButton rbEffect;
    private AppCompatRadioButton rbProduct;

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void initViews() {
        AlbumLayout albumLayout = (AlbumLayout) viewDataBinding;
        viewPager = albumLayout.viewPager;
        radioGroup = albumLayout.radioGroup;
        rbEffect = albumLayout.rbEffect;
        rbProduct = albumLayout.rbProduct;
    }

    @Override
    protected void initEvents() {
        fragments.add(new EffectFragment());
        fragments.add(new ProductFragment());

        viewPager.setAdapter(new Adapter(getChildFragmentManager()));
        viewPager.addOnPageChangeListener(this);
        rbEffect.setChecked(true);

        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_album;
    }

    public void ss(@Size(max = 5) List<List<? extends BaseBean>> s) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset != 0.0f) {
            final float cAlpha = 0.5f;
            boolean pCheck = rbEffect.isChecked() ? rbEffect.isChecked() : rbProduct.isChecked();
            final float offSet = positionOffset * cAlpha;
            final float alpha = cAlpha + offSet;
            if (pCheck) {
                rbEffect.setAlpha(1 - offSet);
                rbProduct.setAlpha(alpha);
            } else {
                rbProduct.setAlpha(1 - offSet);
                rbEffect.setAlpha(alpha);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            rbEffect.setChecked(true);
            rbProduct.setChecked(false);
        } else if (position == 1) {
            rbEffect.setChecked(false);
            rbProduct.setChecked(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == rbEffect.getId()) {
            viewPager.setCurrentItem(0, true);
        } else if (checkedId == rbProduct.getId()) {
            viewPager.setCurrentItem(1, true);
        }
    }


    private class Adapter extends FragmentPagerAdapter {

        public Adapter(FragmentManager fm) {
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

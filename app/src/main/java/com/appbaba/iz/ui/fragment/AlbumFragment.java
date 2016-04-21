package com.appbaba.iz.ui.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import com.appbaba.iz.AlbumLayout;
import com.appbaba.iz.AppKeyMap;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.broadcast.UpdateUIBroadcast;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.ui.fragment.album.EffectFragment;
import com.appbaba.iz.ui.fragment.album.ProductFragment;
import com.github.pwittchen.prefser.library.Prefser;

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

    private final static int EFFECT = AppKeyMap.FROYO;
    private final static int PRODUCT = AppKeyMap.DONUT;

    private UpdateUIBroadcast updateUIBroadcast;

    @Override
    protected void initViews() {
        AlbumLayout albumLayout = (AlbumLayout) viewDataBinding;
        viewPager = albumLayout.viewPager;
        radioGroup = albumLayout.radioGroup;
        rbEffect = albumLayout.rbEffect;
        rbProduct = albumLayout.rbProduct;

        updateUIBroadcast = new UpdateUIBroadcast();
        updateUIBroadcast.setListener(this);
        AppTools.registerBroadcast(updateUIBroadcast, AppKeyMap.CASE_ACTION);
    }

    @Override
    protected void initEvents() {
        fragments.add(new EffectFragment());
        fragments.add(new ProductFragment());

        viewPager.setAdapter(new Adapter(getChildFragmentManager()));
        viewPager.addOnPageChangeListener(this);
        rbEffect.setChecked(true);

        radioGroup.setOnCheckedChangeListener(this);

        String caseId = new Prefser(AppTools.getSharePreferences()).get(AppKeyMap.CATE_ID, String
                .class, "");
        if (!TextUtils.isEmpty(caseId)) {
            toggleChecked(PRODUCT);
        }
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
            toggleChecked(EFFECT);
        } else if (position == 1) {
            toggleChecked(PRODUCT);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void uiUpData(Intent intent) {
        String action = intent.getAction();
        if (TextUtils.equals(action, AppKeyMap.CASE_ACTION))
            toggleChecked(PRODUCT);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppTools.unregisterBroadcast(updateUIBroadcast);
    }

    private void toggleChecked(int which) {
        switch (which) {
            case EFFECT:
                rbEffect.setChecked(true);
                rbProduct.setChecked(false);
                break;
            case PRODUCT:
                rbEffect.setChecked(false);
                rbProduct.setChecked(true);
                break;
        }
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

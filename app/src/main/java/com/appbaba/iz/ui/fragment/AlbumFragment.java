package com.appbaba.iz.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import com.appbaba.iz.AlbumLayout;
import com.appbaba.iz.AppKeyMap;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.broadcast.UpdateUIBroadcast;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.ui.activity.album.SearchActivity;
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
    private ImageButton ibtnSearch,ibtnList;

    private List<Fragment> fragments = new ArrayList<>();

    private final static int EFFECT = AppKeyMap.FROYO;
    private final static int PRODUCT = AppKeyMap.DONUT;

    private UpdateUIBroadcast updateUIBroadcast;

    private int listStyle = 0;
    private int moveWidth = 0;//搜索按钮移动距离
    private Rect startLayout;

    @Override
    protected void initViews() {
        AlbumLayout albumLayout = (AlbumLayout) viewDataBinding;
        viewPager = albumLayout.viewPager;
        radioGroup = albumLayout.radioGroup;
        rbEffect = albumLayout.rbEffect;
        rbProduct = albumLayout.rbProduct;
        ibtnList = albumLayout.ibtnList;
        ibtnSearch = albumLayout.ibtnSearch;

        updateUIBroadcast = new UpdateUIBroadcast();
        updateUIBroadcast.setListener(this);
        albumLayout.ibtnSearch.setOnClickListener(this);
        albumLayout.ibtnList.setOnClickListener(this);
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


        String spaceId = new Prefser(AppTools.getSharePreferences()).get(AppKeyMap.SPACE_ID, String
                .class, "");
        if(spaceId.equals("m")){
            toggleChecked(PRODUCT);
            rbEffect.setAlpha(0.5f);
            rbProduct.setAlpha(1.0f);
        }
        else
        {
            toggleChecked(EFFECT);
            rbEffect.setAlpha(1.0f);
            rbProduct.setAlpha(0.5f);
        }
    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {
         switch (id)
         {
             case R.id.ibtn_search:
             {
                 Intent intent = new Intent(getContext(),SearchActivity.class);
                 intent.putExtra("isEffect",rbEffect.isChecked());
                 startActivity(intent);
             }
                 break;
             case R.id.ibtn_list:
             {
                 if(listStyle==0)
                 {
                     listStyle = 1;
                     ibtnList.setImageResource(R.mipmap.icon_grid);
                 }
                 else
                 {
                     listStyle = 0;
                     ibtnList.setImageResource(R.mipmap.icon_list);
                 }
                  ProductFragment productFragment = (ProductFragment) fragments.get(1);
                  productFragment.ChangeListStyle(listStyle);
             }
                 break;
         }
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
        if (TextUtils.equals(action, AppKeyMap.CASE_ACTION)) {
            if(intent.getExtras().containsKey("isEffect"))
            {
                if(intent.getExtras().getBoolean("isEffect",false))
                {
                    toggleChecked(EFFECT);
                }
                else
                {
                    toggleChecked(PRODUCT);
                }
            }
            else {
                String spaceId = intent.getExtras().getString(AppKeyMap.SPACE_ID, "");
                if (spaceId.equals("m")) {
                    toggleChecked(PRODUCT);
                } else {
                    toggleChecked(EFFECT);
                }
            }
        }
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
                if(startLayout==null)
                {
                    ibtnSearch.setVisibility(View.VISIBLE);
                    ibtnList.setVisibility(View.GONE);
                    if(ibtnSearch.getWidth()>0)
                        startLayout = new Rect(ibtnSearch.getLeft(), ibtnSearch.getTop(), ibtnSearch.getRight(), ibtnSearch.getBottom());
                }
                else {
                    TopRightHideAnimation();
                }
                break;
            case PRODUCT:
                rbEffect.setChecked(false);
                rbProduct.setChecked(true);
                if(startLayout==null)
                {
                    ibtnSearch.setVisibility(View.VISIBLE);
                    ibtnList.setVisibility(View.VISIBLE);

                }
                else {
                    TopRightShowAnimation();
                }
                break;
        }
    }

    //新增顶部按钮动画效果
    public void TopRightShowAnimation()
    {
        moveWidth = ibtnSearch.getWidth();
        TranslateAnimation animation = new TranslateAnimation(0,-moveWidth,0,0);
        animation.setInterpolator(new OvershootInterpolator());
        animation.setDuration(500);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ibtnSearch.clearAnimation();

                ibtnList.setVisibility(View.VISIBLE);
                Log.e("sdf",""+startLayout.left +"   "+startLayout.right+"  "+startLayout.bottom+"   "+startLayout.top);
                ibtnSearch.layout(startLayout.left-ibtnSearch.getWidth(),startLayout.top,startLayout.right-ibtnSearch.getWidth(),startLayout.bottom);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ibtnSearch.clearAnimation();
        ibtnSearch.startAnimation(animation);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(500);
        ibtnList.startAnimation(alphaAnimation);

    }
    public void TopRightHideAnimation()
    {
        moveWidth = ibtnSearch.getWidth()+MethodConfig.dip2px(getContext(),10);
        TranslateAnimation animation = new TranslateAnimation(0,moveWidth,0,0);
        animation.setInterpolator(new OvershootInterpolator());
        animation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ibtnSearch.clearAnimation();
                ibtnList.clearAnimation();
                ibtnList.setVisibility(View.GONE);
                ibtnSearch.layout(startLayout.left,ibtnSearch.getTop(),startLayout.right,ibtnSearch.getBottom());
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ibtnSearch.clearAnimation();
        ibtnSearch.startAnimation(animation);

        AlphaAnimation alphaAnimation = new AlphaAnimation(1,0);
        alphaAnimation.setDuration(500);
        ibtnList.startAnimation(alphaAnimation);
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

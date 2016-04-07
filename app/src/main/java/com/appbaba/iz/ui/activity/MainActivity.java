package com.appbaba.iz.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

import com.appbaba.iz.ActivityMainBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseAty;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.ui.fragment.AlbumFragment;
import com.appbaba.iz.ui.fragment.FavouriteFragment;
import com.appbaba.iz.ui.fragment.FriendsFragment;
import com.appbaba.iz.ui.fragment.HomeFragment;
import com.appbaba.iz.ui.fragment.MoreFragment;

public class MainActivity extends BaseAty {

    HomeFragment homeFragment;
    AlbumFragment ablumnFragment;
    FavouriteFragment favouriteFragment;
    FriendsFragment friendsFragment;
    MoreFragment moreFragment;
    BaseFgm baseFgm;
    private ActivityMainBinding mainBinding;
    private LinearLayout linear_home, linear_ablum, linear_favourite, linear_friends,
            linear_more, linear_temp;
    private FragmentManager fragmentManager;

    @Override
    protected void initViews() {
        mainBinding = (ActivityMainBinding) viewDataBinding;
        linear_home = mainBinding.linearHome;
        linear_ablum = mainBinding.linearAblum;

        fragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void initEvents() {
        linear_home.setOnClickListener(this);
        linear_ablum.setOnClickListener(this);

        linear_home.performClick();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onClick(int id, View view) {
        switch (id) {
            case R.id.linear_home: {
                ShowOrHide(linear_home);
                ChooseHomeFragment();
                linear_temp = linear_home;
            }
            break;
            case R.id.linear_ablum:
                ShowOrHide(linear_ablum);
                ChooseAblumFragment();
                linear_temp = linear_ablum;
                break;

        }
    }

    private void ShowOrHide(LinearLayout linearLayout) {
        int count = linearLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            linearLayout.getChildAt(i).setSelected(true);
        }
        if (linear_temp != null && linear_temp != linearLayout) {
            count = linear_temp.getChildCount();
            for (int i = 0; i < count; i++) {
                linear_temp.getChildAt(i).setSelected(false);
            }
        }
    }


    /**
     * 底部选项
     */
    private void ChooseHomeFragment() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            ft.add(R.id.layout_contain, homeFragment);
        }
        if (baseFgm != null) {
            ft.hide(baseFgm);
        }
        baseFgm = homeFragment;
        ft.show(homeFragment).commit();
    }

    private void ChooseAblumFragment() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (ablumnFragment == null) {
            ablumnFragment = new AlbumFragment();
            ft.add(R.id.layout_contain, ablumnFragment);
        }
        if (baseFgm != null) {
            ft.hide(baseFgm);
        }
        baseFgm = ablumnFragment;
        ft.show(ablumnFragment).commit();
    }
}

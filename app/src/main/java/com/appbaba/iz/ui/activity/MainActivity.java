package com.appbaba.iz.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appbaba.iz.ActivityMainBinding;
import com.appbaba.iz.ActivityRegisterBrandsBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseAty;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.ui.fragment.AblumFragment;
import com.appbaba.iz.ui.fragment.AlbumFragment;
import com.appbaba.iz.ui.fragment.FavouriteFragment;
import com.appbaba.iz.ui.fragment.FriendsFragment;
import com.appbaba.iz.ui.fragment.HomeFragment;
import com.appbaba.iz.ui.fragment.MoreFragment;

public class MainActivity extends BaseAty {

    private ActivityMainBinding mainBinding;
    private LinearLayout linear_home,linear_ablum,linear_favourite,linear_friends,linear_more,linear_temp;


    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private AlbumFragment ablumFragment;
    private FavouriteFragment favouriteFragment;
    private FriendsFragment friendsFragment;
    private MoreFragment moreFragment;
    private BaseFgm baseFgm;

    @Override
    protected void initViews() {
        mainBinding = (ActivityMainBinding)viewDataBinding;
        linear_home = mainBinding.linearHome;
        linear_ablum = mainBinding.linearAblum;
        linear_favourite = mainBinding.linearFavourite;
        linear_more = mainBinding.linearMore;
        linear_friends = mainBinding.linearFriends;

        fragmentManager = getSupportFragmentManager();

//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)frameLayout.getLayoutParams();
//        params.width = MethodConfig.metrics.widthPixels;
//        params.height = MethodConfig.metrics.heightPixels + MethodConfig.dip2px(this,50);
//        frameLayout.setLayoutParams(params);
    }

    @Override
    protected void initEvents() {
        linear_home.setOnClickListener(this);

        linear_home.performClick();

        linear_ablum.setOnClickListener(this);
        linear_favourite.setOnClickListener(this);
        linear_more.setOnClickListener(this);
        linear_friends.setOnClickListener(this);
//        linear_friends.setOnClickListener(this);
//        linear_more.setOnClickListener(this);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onClick(int id, View view) {
        switch (id)
        {
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
            case R.id.linear_favourite:
                ShowOrHide(linear_favourite);
                ChooseFavouriteFragment();
                linear_temp = linear_favourite;
                break;
            case R.id.linear_more:
                ShowOrHide(linear_more);
                ChooseMoreFragment();
                linear_temp = linear_more;
                break;
            case R.id.linear_friends:
                ShowOrHide(linear_friends);
                ChooseFriendsFragment();
                linear_temp = linear_friends;
                break;

        }
    }

    private void ShowOrHide(LinearLayout linearLayout)
    {
        int count = linearLayout.getChildCount();
        for(int i=0;i<count;i++)
        {
            linearLayout.getChildAt(i).setSelected(true);
        }
        if(linear_temp!=null && linear_temp!=linearLayout)
        {
            count = linear_temp.getChildCount();
            for(int i=0;i<count;i++)
            {
                linear_temp.getChildAt(i).setSelected(false);
            }
        }
    }



    /**
     * 底部选项
     */
    private  void  ChooseHomeFragment()
    {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if(homeFragment==null)
        {
            homeFragment = new HomeFragment();
            ft.add(R.id.layout_contain, homeFragment);
        }
        if(baseFgm!=null)
        {
            ft.hide(baseFgm);
        }
        baseFgm = homeFragment;
        ft.show(homeFragment).commit();
    }

    private  void  ChooseAblumFragment()
    {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if(ablumFragment==null)
        {
            ablumFragment = new AlbumFragment();
            ft.add(R.id.layout_contain, ablumFragment);
        }
        if(baseFgm!=null)
        {
            ft.hide(baseFgm);
        }
        baseFgm = ablumFragment;
        ft.show(ablumFragment).commit();
    }

    private  void  ChooseFavouriteFragment()
    {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if(favouriteFragment==null)
        {
            favouriteFragment = new FavouriteFragment();
            ft.add(R.id.layout_contain,favouriteFragment);
        }
        if(baseFgm!=null)
        {
            ft.hide(baseFgm);
        }
        baseFgm = favouriteFragment;
        ft.show(favouriteFragment).commit();
    }

    private  void  ChooseFriendsFragment()
    {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if(friendsFragment==null)
        {
            friendsFragment = new FriendsFragment();
            ft.add(R.id.layout_contain,friendsFragment);
        }
        if(baseFgm!=null)
        {
            ft.hide(baseFgm);
        }
        baseFgm = friendsFragment;
        ft.show(friendsFragment).commit();
    }

    private  void  ChooseMoreFragment()
    {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if(moreFragment==null)
        {
            moreFragment = new MoreFragment();
            ft.add(R.id.layout_contain,moreFragment);
        }
        if(baseFgm!=null)
        {
            ft.hide(baseFgm);
        }
        baseFgm = moreFragment;
        ft.show(moreFragment).commit();
    }
}

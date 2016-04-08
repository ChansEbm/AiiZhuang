package com.appbaba.iz.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseAty;
import com.appbaba.iz.databinding.ActivityTransferBinding;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.ui.fragment.FavouriteItemDetailFragment;
import com.appbaba.iz.ui.fragment.HomeItemContractFragment;
import com.appbaba.iz.ui.fragment.HomeItemIntroduceFragment;
import com.appbaba.iz.ui.fragment.HomeItemNearbyFragment;
import com.appbaba.iz.ui.fragment.HomeItemScanFragment;
import com.appbaba.iz.ui.fragment.MoreItemChangePWDFragment;
import com.appbaba.iz.ui.fragment.MoreItemPersonFragment;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;

/**
 * Created by ruby on 2016/4/5.
 */
public class TransferActivity extends BaseAty {

    ActivityTransferBinding transferBinding;

    FragmentManager fragmentManager;

    private final  int   TOP_SCAN_FRAGMENT = 0;//扫一扫
    private final  int   TOP_INTRODUCE_FRAGMENT = 1;//品牌介绍
    private final  int   TOP_NEARBY_FRAGMENT = 2;//就近门店
    private final  int   TOP_CONTRACT_FRAGMENT = 3;//联系方式
    private final  int TOP_DETAIL_FRAGMENT=4;  //专题详情

    private  final  int MORE_ITEM_PERSON=5;//个人资料
    private  final  int MORE_ITEM_CHANGE_PWD=6;//修改密码

    @Override
    protected void initViews() {
        int fragment_index = getIntent().getIntExtra("fragment",-1);
        transferBinding = (ActivityTransferBinding)viewDataBinding;
        fragmentManager = getSupportFragmentManager();
       switch (fragment_index)
       {
           case TOP_SCAN_FRAGMENT:
               HomeItemScanFragment homeItemScanFragment = new HomeItemScanFragment();
               fragmentManager.beginTransaction().add(R.id.layout_contain,homeItemScanFragment).commit();
               break;
           case TOP_INTRODUCE_FRAGMENT:
               HomeItemIntroduceFragment homeItemIntroduceFragment = new HomeItemIntroduceFragment();
               fragmentManager.beginTransaction().add(R.id.layout_contain,homeItemIntroduceFragment).commit();
               break;
           case TOP_NEARBY_FRAGMENT:
               HomeItemNearbyFragment homeItemNearbyFragment = new HomeItemNearbyFragment();
               fragmentManager.beginTransaction().add(R.id.layout_contain,homeItemNearbyFragment).commit();
               break;
           case TOP_CONTRACT_FRAGMENT:
               HomeItemContractFragment homeItemContractFragment = new HomeItemContractFragment();
               fragmentManager.beginTransaction().add(R.id.layout_contain,homeItemContractFragment).commit();
               break;
           case TOP_DETAIL_FRAGMENT:
               FavouriteItemDetailFragment favouriteItemDetailFragment = new FavouriteItemDetailFragment();
               fragmentManager.beginTransaction().add(R.id.layout_contain,favouriteItemDetailFragment).commit();
               break;
           case MORE_ITEM_PERSON:
               MoreItemPersonFragment moreItemPersonFragment = new MoreItemPersonFragment();
               fragmentManager.beginTransaction().add(R.id.layout_contain,moreItemPersonFragment).commit();
               break;
           case MORE_ITEM_CHANGE_PWD:
               MoreItemChangePWDFragment moreItemChangePWDFragment = new MoreItemChangePWDFragment();
               fragmentManager.beginTransaction().add(R.id.layout_contain,moreItemChangePWDFragment).commit();
               break;

        }
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_transfer;
    }

    @Override
    protected void onClick(int id, View view) {

    }

    @Override
    public void onJsonObjectResponse(Object o, NetworkParams paramsCode) {

    }
}

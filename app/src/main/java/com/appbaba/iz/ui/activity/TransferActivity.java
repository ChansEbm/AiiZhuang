package com.appbaba.iz.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseAty;
import com.appbaba.iz.databinding.ActivityTransferBinding;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.ui.fragment.CommSellerListFragment;
import com.appbaba.iz.ui.fragment.FavouriteItemDetailFragment;
import com.appbaba.iz.ui.fragment.FriendsItemAddClientFragment;
import com.appbaba.iz.ui.fragment.FriendsItemArticleFragment;
import com.appbaba.iz.ui.fragment.FriendsItemCRMFragment;
import com.appbaba.iz.ui.fragment.FriendsItemClientFragment;
import com.appbaba.iz.ui.fragment.HomeItemContractFragment;
import com.appbaba.iz.ui.fragment.HomeItemIntroduceFragment;
import com.appbaba.iz.ui.fragment.HomeItemNearbyFragment;
import com.appbaba.iz.ui.fragment.HomeItemScanFragment;
import com.appbaba.iz.ui.fragment.MoreItemBackFragment;
import com.appbaba.iz.ui.fragment.MoreItemChangePWDFragment;
import com.appbaba.iz.ui.fragment.MoreItemPersonFragment;

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


    private  final  int FRIEND_CRM = 7; //CRM
    private  final  int FRIEND_CLIENT_LIST = 8; //我的客户
    private  final  int FRIEND_CLIENT_ADD = 9; //添加我的客户

    private  final  int MORE_ITEM_FEEDBACK = 10; //意见反馈

    private  final int COMM_SELLER_LIST = 11; //选择品牌

    private  final int FRIEND_ARTICLE = 12; //文章内容



    @Override
    protected void initViews() {
        int fragment_index = getIntent().getIntExtra("fragment",-1);


        transferBinding = (ActivityTransferBinding)viewDataBinding;
        fragmentManager = getSupportFragmentManager();
       switch (fragment_index) {
           case TOP_SCAN_FRAGMENT:
               HomeItemScanFragment homeItemScanFragment = new HomeItemScanFragment();
               fragmentManager.beginTransaction().add(R.id.layout_contain, homeItemScanFragment).commit();
               break;
           case TOP_INTRODUCE_FRAGMENT:
               HomeItemIntroduceFragment homeItemIntroduceFragment = new HomeItemIntroduceFragment();
               fragmentManager.beginTransaction().add(R.id.layout_contain, homeItemIntroduceFragment).commit();
               break;
           case TOP_NEARBY_FRAGMENT:
               HomeItemNearbyFragment homeItemNearbyFragment = new HomeItemNearbyFragment();
               fragmentManager.beginTransaction().add(R.id.layout_contain, homeItemNearbyFragment).commit();
               break;
           case TOP_CONTRACT_FRAGMENT:
               HomeItemContractFragment homeItemContractFragment = new HomeItemContractFragment();
               fragmentManager.beginTransaction().add(R.id.layout_contain, homeItemContractFragment).commit();
               break;
           case TOP_DETAIL_FRAGMENT: {
               FavouriteItemDetailFragment favouriteItemDetailFragment = new FavouriteItemDetailFragment();
               Bundle bundle = new Bundle();
               bundle.putString("id", getIntent().getStringExtra("id"));
               bundle.putString("title", getIntent().getStringExtra("title"));
               favouriteItemDetailFragment.setArguments(bundle);
               fragmentManager.beginTransaction().add(R.id.layout_contain, favouriteItemDetailFragment).commit();
           }
               break;
           case MORE_ITEM_PERSON:
               MoreItemPersonFragment moreItemPersonFragment = new MoreItemPersonFragment();
               fragmentManager.beginTransaction().add(R.id.layout_contain, moreItemPersonFragment).commit();
               break;
           case MORE_ITEM_CHANGE_PWD:
               MoreItemChangePWDFragment moreItemChangePWDFragment = new MoreItemChangePWDFragment();
               fragmentManager.beginTransaction().add(R.id.layout_contain, moreItemChangePWDFragment).commit();
               break;
           case MORE_ITEM_FEEDBACK:
               MoreItemBackFragment moreItemBackFragment = new MoreItemBackFragment();
               fragmentManager.beginTransaction().add(R.id.layout_contain, moreItemBackFragment).commit();
               break;
           case FRIEND_CRM:
               FriendsItemCRMFragment friendsItemCRMFragment = new FriendsItemCRMFragment();
               fragmentManager.beginTransaction().add(R.id.layout_contain, friendsItemCRMFragment).commit();
               break;
           case FRIEND_CLIENT_LIST:
               FriendsItemClientFragment clientFragment = new FriendsItemClientFragment();
               fragmentManager.beginTransaction().add(R.id.layout_contain, clientFragment).commit();
               break;
           case FRIEND_CLIENT_ADD:
               FriendsItemAddClientFragment addFragment = new FriendsItemAddClientFragment();
               addFragment.entity = getIntent().getParcelableExtra("data");
               fragmentManager.beginTransaction().add(R.id.layout_contain, addFragment).commit();
               break;
           case COMM_SELLER_LIST:
               CommSellerListFragment commSellerListFragment = new CommSellerListFragment();
               fragmentManager.beginTransaction().add(R.id.layout_contain, commSellerListFragment).commit();
               break;
           case FRIEND_ARTICLE: {
               FriendsItemArticleFragment friendsItemArticleFragment = new FriendsItemArticleFragment();
               Bundle bundle = new Bundle();
               bundle.putString("id", getIntent().getStringExtra("id"));
               bundle.putString("title", getIntent().getStringExtra("title"));
               friendsItemArticleFragment.setArguments(bundle);
               fragmentManager.beginTransaction().add(R.id.layout_contain, friendsItemArticleFragment).commit();
           }
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

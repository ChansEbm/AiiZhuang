package com.appbaba.iz.ui.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.appbaba.iz.FragmentHomeBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.TopMenuBinding;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.ui.activity.TransferActivity;

/**
 * Created by ruby on 2016/4/1.
 */
public class HomeFragment extends BaseFgm {
    FragmentHomeBinding homeBinding;
    ImageView iv_1,iv_2,iv_3,iv_4,iv_5,iv_6,iv_item_test,iv_menu;
    PopupWindow window;

    @Override
    protected void initViews() {
       homeBinding = (FragmentHomeBinding)viewDataBinding;
        iv_1 = homeBinding.iv1;
        iv_2 = homeBinding.iv2;
        iv_3 = homeBinding.iv3;
        iv_4 = homeBinding.iv4;
        iv_5 = homeBinding.iv5;
        iv_6 = homeBinding.iv6;
        iv_item_test = homeBinding.ivItemTest;
        iv_menu = homeBinding.includeTopTitle.ivMenu;

        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams)iv_1.getLayoutParams();
        params1.width = MethodConfig.metrics.widthPixels;
        params1.height = MethodConfig.GetHeightFor16v9(params1.width);
        iv_1.setLayoutParams(params1);

        int m = MethodConfig.dip2px(getContext(),10);
        LinearLayout.LayoutParams params2 = ( LinearLayout.LayoutParams)iv_3.getLayoutParams();
        params2.width = (MethodConfig.metrics.widthPixels-m*3)/2;
        LinearLayout.LayoutParams params4 = ( LinearLayout.LayoutParams)iv_4.getLayoutParams();
        LinearLayout.LayoutParams params5 = ( LinearLayout.LayoutParams)iv_5.getLayoutParams();
        LinearLayout.LayoutParams params6 = ( LinearLayout.LayoutParams)iv_6.getLayoutParams();
        params2.height = MethodConfig.GetHeightFor16v9(params2.width);
       params6.height= params5.height = params4.height = params2.height;
        params6.width = params5.width = params4.width = params2.width;

        System.out.print("" + params5.height);
        iv_3.setLayoutParams(params2);
        iv_4.setLayoutParams(params4);
        iv_5.setLayoutParams(params5);
        iv_6.setLayoutParams(params5);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)iv_item_test.getLayoutParams();
        params.width = MethodConfig.metrics.widthPixels;
        params.height = MethodConfig.metrics.widthPixels/2;
        iv_item_test.setLayoutParams(params);



    }





    @Override
    protected void initEvents() {
        iv_menu.setOnClickListener(this);
    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {

        switch (id)
        {
            case R.id.iv_menu: {
                try {

                    ViewDataBinding viewDataBinding=DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.popup_top_menu, null, false);
                    TopMenuBinding menuBinding = (TopMenuBinding)viewDataBinding;

                    menuBinding.tvIntroduce.setOnClickListener(this);
                    menuBinding.tvContract.setOnClickListener(this);
                    menuBinding.tvNearby.setOnClickListener(this);
                    menuBinding.tvScan.setOnClickListener(this);
                    window = new PopupWindow(viewDataBinding.getRoot(), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    window.setBackgroundDrawable(new BitmapDrawable());
                    window.showAsDropDown(iv_menu);
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
                break;
            case R.id.tv_scan: {
                Intent intent = new Intent(getContext(), TransferActivity.class);
                intent.putExtra("fragment", 0);
                startActivity(intent);
            }
                break;
            case R.id.tv_introduce: {
                Intent intent = new Intent(getContext(), TransferActivity.class);
                intent.putExtra("fragment", 1);
                startActivity(intent);
            }
                break;
            case R.id.tv_nearby: {
                Intent intent = new Intent(getContext(), TransferActivity.class);
                intent.putExtra("fragment", 2);
                startActivity(intent);
            }
                break;
            case R.id.tv_contract: {
                Intent intent = new Intent(getContext(), TransferActivity.class);
                intent.putExtra("fragment", 3);
                startActivity(intent);
            }
                break;

        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }
}

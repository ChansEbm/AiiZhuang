package com.appbaba.platform.ui.activity;

import android.graphics.Color;
import android.view.View;

import com.appbaba.platform.ActivityMeSettingBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.base.BaseActivity;

/**
 * Created by ruby on 2016/5/10.
 */
public class MeSettingActivity extends BaseActivity {
    private ActivityMeSettingBinding binding;
    @Override
    protected void InitView() {
        binding = (ActivityMeSettingBinding)viewDataBinding;
        binding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.icon_back);
        binding.includeTopTitle.title.setText("设置");
        binding.includeTopTitle.title.setTextColor(Color.BLACK);
    }

    @Override
    protected void InitData() {

    }

    @Override
    protected void InitEvent() {

    }

    @Override
    protected void InitListening() {

    }

    @Override
    protected void OnClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_me_setting;
    }
}

package com.appbaba.iz.ui.activity.album;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.appbaba.iz.ActivitySearchBinding;
import com.appbaba.iz.AppKeyMap;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseAty;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.widget.TagGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/5/3.
 */
public class SearchActivity extends BaseAty implements Toolbar.OnMenuItemClickListener {
    private ActivitySearchBinding binding;

    @Override
    protected void initViews() {
        binding = (ActivitySearchBinding) viewDataBinding;

        binding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.icon_top_back);
        binding.includeTopTitle.toolBar.getMenu().add(0, R.id.menu_search, 0, "").setIcon(R.mipmap.icon_search).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        binding.includeTopTitle.edtSearch.setHint("请输入型号或名称");
    }

    @Override
    protected void initEvents() {
        List<String> tags = new ArrayList<>();
        if (MethodConfig.sellerInfoEntity != null) {
            tags = MethodConfig.sellerInfoEntity.getHot_label();
        }

        binding.tagGroup.setTags(tags);
        binding.tagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                Bundle bundle = new Bundle();
                bundle.putString(AppKeyMap.PRO_KEYWORD, tag);
                AppTools.sendBroadcast(bundle, AppKeyMap.CASE_ACTION);
                finish();
            }
        });
        binding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.includeTopTitle.toolBar.setOnMenuItemClickListener(this);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_search;
    }

    @Override
    protected void onClick(int id, View view) {

    }

    @Override
    public void onJsonObjectResponse(Object o, NetworkParams paramsCode) {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search: {
                if (TextUtils.isEmpty(binding.includeTopTitle.edtSearch.getText())) {
                    Toast.makeText(SearchActivity.this, "请输入要搜索内容", Toast.LENGTH_LONG).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(AppKeyMap.PRO_KEYWORD, binding.includeTopTitle.edtSearch.getText().toString().trim());
                    AppTools.sendBroadcast(bundle, AppKeyMap.CASE_ACTION);
                    finish();
                }
            }
            break;
        }
        return false;
    }

}

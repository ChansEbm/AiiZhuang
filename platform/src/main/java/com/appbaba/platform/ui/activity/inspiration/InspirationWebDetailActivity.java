package com.appbaba.platform.ui.activity.inspiration;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.appbaba.platform.ActivityInspirationWebDetailBinding;
import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.R;
import com.appbaba.platform.base.BaseActivity;

/**
 * Created by ruby on 2016/6/12.
 */
public class InspirationWebDetailActivity extends BaseActivity {
    private ActivityInspirationWebDetailBinding binding;
    private WebView webView;

    private String inspirationId;

    @Override
    protected void InitView() {
         binding = (ActivityInspirationWebDetailBinding)viewDataBinding;
         webView = binding.webView;
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void InitData() {
        inspirationId = getIntent().getStringExtra("id");
        webView.loadUrl(AppKeyMap.HEAD_API_PAGE_INSPIRATION+inspirationId);

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
        return R.layout.activity_inspiration_web_detail;
    }
}

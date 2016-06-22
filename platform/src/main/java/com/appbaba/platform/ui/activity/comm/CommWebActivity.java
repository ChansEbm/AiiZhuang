package com.appbaba.platform.ui.activity.comm;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.appbaba.platform.ActivityCommWebViewBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.base.BaseActivity;

/**
 * Created by ruby on 2016/6/14.
 */
public class CommWebActivity extends BaseActivity {

    private ActivityCommWebViewBinding binding;
    private WebView webView;
    private  String url;

    @Override
    protected void InitView() {
         binding = (ActivityCommWebViewBinding)viewDataBinding;
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

    public void Back(View view)
    {
        onBackPressed();
    }

    @Override
    protected void InitData() {
        url = getIntent().getStringExtra("url");

        webView.loadUrl(url);
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
        return R.layout.activity_comm_web;
    }
}

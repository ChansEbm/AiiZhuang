package com.appbaba.platform.ui.activity.products;

import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.appbaba.platform.ActivityProductWebDetailBinding;
import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.R;
import com.appbaba.platform.base.BaseActivity;

/**
 * Created by ruby on 2016/6/12.
 */
public class ProductWebDetailActivity extends BaseActivity {

    private ActivityProductWebDetailBinding binding;
    private WebView webView;

    private String productID;

    @Override
    protected void InitView() {
        binding = (ActivityProductWebDetailBinding)viewDataBinding;
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
        productID = getIntent().getStringExtra("id");
        webView.loadUrl(AppKeyMap.HEAD_API_PAGE_PRODUCT+productID);
    }

    @Override
    protected void InitEvent() {
       webView.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               return false;
           }
       });

    }

    @Override
    protected void InitListening() {

    }

    @Override
    protected void OnClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_product_web_detail;
    }

    public void Back(View view)
    {
         onBackPressed();
    }
}

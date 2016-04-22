package com.appbaba.iz.ui.fragment.Comm;

import android.app.Activity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.appbaba.iz.AppKeyMap;
import com.appbaba.iz.FragmentCommWebviewBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseFgm;

/**
 * Created by ruby on 2016/4/16.
 */
public class CommWebviewFragment extends BaseFgm {

    private   FragmentCommWebviewBinding webviewBinding;
    private String title,value;
    private  int which;
    @Override
    protected void initViews() {
        title = getArguments().getString("title","");
        which = getArguments().getInt("which",-1);
        value = getArguments().getString("value","");
        webviewBinding = (FragmentCommWebviewBinding)viewDataBinding;

        webviewBinding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.icon_top_back);
        webviewBinding.includeTopTitle.title.setText(title.trim());
        webviewBinding.webView.getSettings().setDomStorageEnabled(true);
        webviewBinding.webView.getSettings().setJavaScriptEnabled(true);
        webviewBinding.webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void initEvents() {

        webviewBinding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });

        String url = AppKeyMap.HEAD+"Page/";
        switch (which)
        {
            case 1:
                url +=("brand_introduction?seller_id="+value);
                break;
            case 2:
                url +=("nearby_shop?seller_id="+value);
                break;
            case 3:
                url +=("contact?seller_id="+value);
                break;
            case 4:
                url +=("cases_detail?cases_id="+value);
                break;
            case 5:
                url +=("product_detail?product_id="+value);
                break;
            case 6:
                url +=("article_detail?article_id="+value);
                break;
            case 7:
                url +="operating_guide";
                break;
            case 8:
                url +="about_us";
                break;
            case 9:
                url +="service_term";
                break;
            case 10:
                url +="privacy_policy";
                break;
            case 11:
                url += "subject_detail?subject_id="+value;
                break;
            case 12:
                url+="customer_collect?customer_id="+value;
                break;
            case -1:
                url = value;
                break;
        }
        webviewBinding.webView.loadUrl(url);

    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_comm_webview;
    }
}

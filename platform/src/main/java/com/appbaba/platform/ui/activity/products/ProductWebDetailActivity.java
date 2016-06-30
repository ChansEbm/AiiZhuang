package com.appbaba.platform.ui.activity.products;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.appbaba.platform.ActivityProductWebDetailBinding;
import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.R;
import com.appbaba.platform.base.BaseActivity;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.product.ProductDetailBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.ui.activity.comm.CommWebActivity;
import com.appbaba.platform.widget.DialogView.ShareDialogView;
import com.squareup.picasso.Picasso;

/**
 * Created by ruby on 2016/6/12.
 */
public class ProductWebDetailActivity extends BaseActivity {

    private ActivityProductWebDetailBinding binding;
    private WebView webView;
    private ProductDetailBean detailBean;

    private String productID,buyUrl,title,desc,image,url;

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
        url = AppKeyMap.HEAD_API_PAGE_PRODUCT+productID;
        webView.loadUrl(url);
        if(MethodConfig.IsLogin())
        {
            networkModel.GetCheckCollect(MethodConfig.userInfo.getToken(),productID,NetworkParams.DONUT);
        }
        networkModel.ProductDetail(productID,NetworkParams.FROYO);
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
        binding.tvBuy.setOnClickListener(this);
        binding.ivHeart.setOnClickListener(this);
        binding.ivShare.setOnClickListener(this);
    }

    @Override
    protected void OnClick(int id, View view) {
       switch (id)
       {
           case R.id.iv_heart:
               if(MethodConfig.IsLogin())
               {
                   networkModel.Collect(MethodConfig.userInfo.getToken(),productID, NetworkParams.CUPCAKE);
               }
               else
               {
                   Toast.makeText(this,"请先登录",Toast.LENGTH_LONG).show();
               }
               break;
           case R.id.tv_buy:
               if(TextUtils.isEmpty(buyUrl))
               {
                   Toast.makeText(this,"购买链接不存在",Toast.LENGTH_LONG).show();
               }
               else {
                   Intent intent = new Intent(this, CommWebActivity.class);
                   Log.e("hide","哈哈，购买链接！！！"+buyUrl);
                   intent.putExtra("url", buyUrl);
                   startActivity(intent);
               }
               break;
           case R.id.iv_share:
               ShareDialogView dialogView = new ShareDialogView(ProductWebDetailActivity.this, title,image,desc, url);
               dialogView.show();
               break;
       }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_product_web_detail;
    }

    public void Back(View view)
    {
         onBackPressed();
    }

    @Override
    public void onJsonObjectSuccess(BaseBean baseBean, NetworkParams paramsCode) {
        if(baseBean.getErrorcode()==0) {
            if (paramsCode == NetworkParams.CUPCAKE) {
                Toast.makeText(this,"操作成功",Toast.LENGTH_LONG).show();
                networkModel.GetCheckCollect(MethodConfig.userInfo.getToken(), productID, NetworkParams.DONUT);
            } else if (paramsCode == NetworkParams.DONUT) {
                int status = baseBean.getStatus();
                if (status == 1) {
                    binding.ivHeart.setImageResource(R.mipmap.icon_collection_y_r);
                } else {
                    binding.ivHeart.setImageResource(R.mipmap.icon_collection_y_n);
                }
            } else if (paramsCode == NetworkParams.FROYO)
            {
                detailBean = (ProductDetailBean)baseBean;
                buyUrl = detailBean.getGoods().getBuy_link();
                title = detailBean.getGoods().getTitle();
                desc = detailBean.getGoods().getDesc();
                image = detailBean.getGoods().getThumb();
            }
        }
    }



}

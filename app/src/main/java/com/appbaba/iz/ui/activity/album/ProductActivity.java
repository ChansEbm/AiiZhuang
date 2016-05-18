package com.appbaba.iz.ui.activity.album;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appbaba.iz.AppKeyMap;
import com.appbaba.iz.ItemProductBinding;
import com.appbaba.iz.ItemProductChildLayout;
import com.appbaba.iz.ItemProductLayout;
import com.appbaba.iz.ProductLayout;
import com.appbaba.iz.R;
import com.appbaba.iz.adapters.CommonBinderAdapter;
import com.appbaba.iz.adapters.CommonBinderHolder;
import com.appbaba.iz.base.BaseAty;
import com.appbaba.iz.entity.Base.BaseBean;
import com.appbaba.iz.entity.main.album.CaseEntity;
import com.appbaba.iz.entity.main.album.CasesAttrSelection;
import com.appbaba.iz.entity.main.album.ProductEntity;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.tools.LogTools;
import com.appbaba.iz.ui.activity.TransferActivity;
import com.appbaba.iz.widget.DialogView.ShareDialogView;
import com.appbaba.iz.widget.ScrollView.DragLayout;
import com.github.pwittchen.prefser.library.Prefser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends BaseAty<BaseBean, BaseBean> implements ViewPager
        .OnPageChangeListener {
    private ViewPager viewPager;
    private RecyclerView recyclerView;
    private TextView tv_detail;

    private List<ProductEntity.ListBean> productList = new ArrayList<>();
    private List<ProductEntity.ListBean.CasesListBean> recyclerList = new ArrayList<>();
    private CommonBinderAdapter<ProductEntity.ListBean.CasesListBean> commonBinderAdapter;
    private Adapter adapter = new Adapter();

    private CasesAttrSelection selection = new CasesAttrSelection();

    private int index = -1;
    private String pageSize = "10";
    private String productId = "";

    private boolean isCollect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getIntent().getExtras().getInt("index", -1);
        selection = getIntent().getExtras().getParcelable("selection");
        if (selection == null)
            selection = new CasesAttrSelection();
        productId = getIntent().getExtras().getString("productId", "");
        pageSize = getIntent().getExtras().getString("pageSize", "10");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fav_share, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isCollect) {
            menu.findItem(R.id.menu_fav).setIcon(R.mipmap.ic_fav_press);
        } else {
            menu.findItem(R.id.menu_fav).setIcon(R.mipmap.ic_fav_nor);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menu_fav:
                if (collectProduct()) return false;
                break;
            case R.id.menu_share:
                if(productList!=null && productList.size()>0) {
                    String url = AppKeyMap.HEAD + "Page/product_detail?product_id=" + productList.get(viewPager.getCurrentItem()).getProduct_id();
                    ShareDialogView dialogView = new ShareDialogView(this, productList.get(viewPager.getCurrentItem()).getTitle(), productList.get(viewPager.getCurrentItem()).getThumb(), productList.get(viewPager.getCurrentItem()).getThumb(), url);
                    dialogView.show();
                }
                break;
        }

        return true;
    }

    private boolean collectProduct() {
        if (MethodConfig.localUser == null) {
            AppTools.showNormalSnackBar(parentView, "请先登录!");
            return true;
        }
        Prefser prefser = new Prefser(AppTools.getSharePreferences());
        String customerId = prefser.get(AppKeyMap.CUSTOMERID, String.class, "");
        if (TextUtils.isEmpty(customerId)) {
            AppTools.showNormalSnackBar(parentView, "请先选择客户后再收藏");
            return true;
        }
        int currentItem = viewPager.getCurrentItem();
        ProductEntity.ListBean listBean = productList.get(currentItem);
        String productId = listBean.getProduct_id();
        String isCollect = listBean.getIs_collect();

        listBean.setIs_collect(TextUtils.equals(isCollect, "0") ? "1" : "0");
        productList.set(currentItem, listBean);
        onPageSelected(currentItem);

        networkModel.collectProduct(productId, NetworkParams.DONUT);
        return false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.productId = intent.getExtras().getString("productId", "");
        this.pageSize = intent.getExtras().getString("pageSize", "10");
        LogTools.i(productId);
        networkModel.product(productId, "", "1", pageSize, new CasesAttrSelection(), NetworkParams
                .CUPCAKE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        index = -1;
        productId = "";

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void initViews() {
        defaultTitleBar(this).setTitleBackgroundColor(R.color.black);
        ProductLayout productLayout = (ProductLayout) viewDataBinding;
        viewPager = productLayout.viewPager;
        recyclerView = productLayout.recyclerView;
        tv_detail = productLayout.tvDetail;

        commonBinderAdapter = new CommonBinderAdapter<ProductEntity.ListBean.CasesListBean>(this, R
                .layout.item_product, recyclerList) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int
                    position, ProductEntity.ListBean.CasesListBean casesListBean) {
                ((ItemProductLayout) viewDataBinding).setProduct(casesListBean);
            }
        };

        //productLayout.linearBg.getLayoutParams().height = MethodConfig.metrics.heightPixels- MethodConfig.dip2px(this,75);
        productLayout.webView.getSettings().setDomStorageEnabled(true);
        productLayout.webView.getSettings().setJavaScriptEnabled(true);
        productLayout.webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }

    @Override
    protected void initEvents() {
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        recyclerView.setAdapter(commonBinderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager
                .HORIZONTAL, false));
        commonBinderAdapter.setBinderOnItemClickListener(this);
        tv_detail.setOnClickListener(this);
        ((ProductLayout)viewDataBinding).tvDetailBack.setOnClickListener(this);

        networkModel.product(productId, "", "1", pageSize, selection, NetworkParams.CUPCAKE);
        networkModel.addProductVisit(productId,NetworkParams.DONUT);

        DragLayout.ShowNextPageNotifier notifier = new DragLayout.ShowNextPageNotifier() {
            @Override
            public void onDragNext() {

                ((ProductLayout)viewDataBinding).tvDetailBack.setVisibility(View.VISIBLE);
                ((ProductLayout)viewDataBinding).tvDetail.setVisibility(View.GONE);
            }

            @Override
            public void onDragPre() {
                ((ProductLayout)viewDataBinding).tvDetailBack.setVisibility(View.GONE);
                ((ProductLayout)viewDataBinding).tvDetail.setVisibility(View.VISIBLE);
            }
        };

        ((ProductLayout)viewDataBinding).dragLayout.setNextPageListener(notifier);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_product;
    }

    @Override
    protected void onClick(int id, View view) {
        switch (id)
        {
            case R.id.imageView: {
                int currentItem = viewPager.getCurrentItem();
                ArrayList<String> photoPaths = new ArrayList<>();
                for (ProductEntity.ListBean listBean : productList) {
                    photoPaths.add(listBean.getImage());
                }
                Intent intent = new Intent(ProductActivity.this, ZoomEffectActivity.class).putExtra("index",
                        currentItem).putStringArrayListExtra("photoPaths", photoPaths);
                startActivity(intent);
            }
                break;
            case R.id.tv_detail:
            {
                if(productList!=null && productList.size()>0) {
                    ProductLayout d = (ProductLayout)viewDataBinding;
                    d.dragLayout.Back(1);
                }
            }
            break;
            case R.id.tv_detail_back:
            {
                ProductLayout d = (ProductLayout)viewDataBinding;
                d.dragLayout.Back(2);
            }
            break;
            case R.id.tv_pre: {
                int index = viewPager.getCurrentItem();
                if (index == 0) {
                    Toast.makeText(this, "已经是第一个了", Toast.LENGTH_LONG).show();
                } else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                }
            }
                break;
            case R.id.tv_next: {
                int index = viewPager.getCurrentItem();
                if (index>=(adapter.getCount()-1)) {
                    Toast.makeText(this, "已经是最后一个了", Toast.LENGTH_LONG).show();
                } else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() +1);
                }
            }
                break;
        }
    }

    @Override
    public void onJsonObjectSuccess(BaseBean baseBean, NetworkParams paramsCode) {
        if (paramsCode == NetworkParams.CUPCAKE) {
            placeResult((ProductEntity) baseBean);
        }
    }

    private void placeResult(ProductEntity baseBean) {
        this.productList.clear();
        this.productList.addAll(baseBean.getList());

        adapter = new Adapter();
        viewPager.setAdapter(adapter);
        if (index == 0) {
            onPageSelected(0);
            return;
        }
        if (index != -1) {
            viewPager.setCurrentItem(index, false);
        } else {
            if (!TextUtils.isEmpty(productId)) {
                findSelectedPosition();
            }
        }
    }

    private void findSelectedPosition() {
        for (int i = 0; i < productList.size(); i++) {
            ProductEntity.ListBean listBean = productList.get(i);
            if (TextUtils.equals(listBean.getProduct_id(), productId)) {
                if (i == 0) {
                    viewPager.setCurrentItem(0, false);
                    onPageSelected(0);
                    break;
                } else {
                    viewPager.setCurrentItem(i, false);
                    break;
                }
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.recyclerList.clear();
        this.recyclerList.addAll(productList.get(position).getCases_list());
        commonBinderAdapter.notifyDataSetChanged();

        this.isCollect = productList.get(position).getIs_collect().equals("1");
        titleBarTools.setTitle(productList.get(position).getTitle());
        invalidateOptionsMenu();
        String url = AppKeyMap.HEAD+"Page/";
        url+="product_content?product_id="+productList.get(viewPager.getCurrentItem()).getProduct_id();
        ((ProductLayout)viewDataBinding).webView.loadUrl(url);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBinderItemClick(View clickItem, int parentId, int pos) {
        String casesId = recyclerList.get(pos).getCases_id();
        Bundle bundle = new Bundle();
        bundle.putString("casesId", casesId);
        bundle.putString("pageSize", pageSize);
        start(bundle, EffectActivity.class);
    }


    class Adapter extends PagerAdapter implements View.OnClickListener {

        @Override
        public int getCount() {
            return productList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ItemProductChildLayout childLayout = DataBindingUtil.inflate(getLayoutInflater(), R
                    .layout.item_product_child, null, false);
            ViewPager viewPager = childLayout.viewPager;
            viewPager.setAdapter(new ChildAdapter(productList.get(position).getImage_list()));
            childLayout.indicator.setWithViewPager(viewPager);
            childLayout.setProduct(productList.get(position));
            childLayout.tvPre.setOnClickListener(ProductActivity.this);
            childLayout.tvNext.setOnClickListener(ProductActivity.this);
            container.addView(childLayout.getRoot());
            return childLayout.getRoot();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.viewPager:
                    break;
            }
        }

        class ChildAdapter extends PagerAdapter {
            List<String> imageList = new ArrayList<>();

            public ChildAdapter(List<String> imageList) {
                this.imageList = imageList;
            }

            @Override
            public int getCount() {
                return imageList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ItemProductBinding binding = DataBindingUtil.inflate(LayoutInflater.from(ProductActivity.this),R.layout.item_product_view,null,false);

                ImageView imageView = binding.imageView;

                imageView.setOnClickListener(ProductActivity.this);
                Picasso.with(ProductActivity.this).load(imageList.get(position)).into(imageView);
                container.addView(binding.getRoot());
                return binding.getRoot();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        }
    }
}

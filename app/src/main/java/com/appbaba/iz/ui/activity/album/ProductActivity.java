package com.appbaba.iz.ui.activity.album;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appbaba.iz.ItemProductChildLayout;
import com.appbaba.iz.ItemProductLayout;
import com.appbaba.iz.ProductLayout;
import com.appbaba.iz.R;
import com.appbaba.iz.adapters.CommonBinderAdapter;
import com.appbaba.iz.adapters.CommonBinderHolder;
import com.appbaba.iz.base.BaseAty;
import com.appbaba.iz.entity.Base.BaseBean;
import com.appbaba.iz.entity.main.album.CasesAttrSelection;
import com.appbaba.iz.entity.main.album.ProductEntity;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.tools.LogTools;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends BaseAty<BaseBean, BaseBean> implements ViewPager
        .OnPageChangeListener {
    private ViewPager viewPager;
    private RecyclerView recyclerView;

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
        LogTools.w(isCollect);
        if (isCollect) {
            menu.findItem(R.id.menu_fav).setIcon(R.mipmap.ic_fav_press);
        } else {
            menu.findItem(R.id.menu_fav).setIcon(R.mipmap.ic_fav_nor);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.productId = getIntent().getExtras().getString("productId", productId);
        this.pageSize = getIntent().getExtras().getString("pageSize", "10");
        networkModel.product(productId, "", "1", pageSize, new CasesAttrSelection(), NetworkParams
                .CUPCAKE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        index = -1;
    }

    @Override
    protected void initViews() {
        defaultTitleBar(this).setTitleBackgroundColor(R.color.black);
        ProductLayout productLayout = (ProductLayout) viewDataBinding;
        viewPager = productLayout.viewPager;
        recyclerView = productLayout.recyclerView;

        commonBinderAdapter = new CommonBinderAdapter<ProductEntity.ListBean.CasesListBean>(this, R
                .layout.item_product, recyclerList) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int
                    position, ProductEntity.ListBean.CasesListBean casesListBean) {
                ((ItemProductLayout) viewDataBinding).setProduct(casesListBean);
            }
        };
    }

    @Override
    protected void initEvents() {
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        recyclerView.setAdapter(commonBinderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager
                .HORIZONTAL, false));
        commonBinderAdapter.setBinderOnItemClickListener(this);

        networkModel.product(productId, "", "1", pageSize, selection, NetworkParams.CUPCAKE);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_product;
    }

    @Override
    protected void onClick(int id, View view) {

    }

    @Override
    public void onJsonObjectSuccess(BaseBean baseBean, NetworkParams paramsCode) {
        if (paramsCode == NetworkParams.CUPCAKE) {
            ProductEntity entity = (ProductEntity) baseBean;
            this.productList.clear();
            this.productList.addAll(entity.getList());
            adapter.notifyDataSetChanged();
            if (index != -1) {
                viewPager.setCurrentItem(index, false);
            } else {
                if (!TextUtils.isEmpty(productId)) {
                    findSelectedPosition();
                }
            }
        }
    }

    private void findSelectedPosition() {
        for (int i = 0; i < productList.size(); i++) {
            ProductEntity.ListBean listBean = productList.get(i);
            if (TextUtils.equals(listBean.getProduct_id(), productId)) {
                if (i == 0) {
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

    class Adapter extends PagerAdapter {

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
            container.addView(childLayout.getRoot());
            return childLayout.getRoot();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
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
                ImageView imageView = new ImageView(ProductActivity.this);
                ViewPager.LayoutParams params = new ViewPager.LayoutParams();
                params.height = ViewPager.LayoutParams.MATCH_PARENT;
                params.width = ViewPager.LayoutParams.MATCH_PARENT;
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(params);
                Picasso.with(ProductActivity.this).load(imageList.get(position)).resize(
                        500, 500).into(imageView);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        }
    }
}

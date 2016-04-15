package com.appbaba.iz.ui.activity.album;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appbaba.iz.EffectLayout;
import com.appbaba.iz.ItemEffectLayout;
import com.appbaba.iz.R;
import com.appbaba.iz.adapters.CommonBinderAdapter;
import com.appbaba.iz.adapters.CommonBinderHolder;
import com.appbaba.iz.base.BaseAty;
import com.appbaba.iz.entity.Base.BaseBean;
import com.appbaba.iz.entity.main.album.CaseEntity;
import com.appbaba.iz.entity.main.album.CasesAttrSelection;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.tools.LogTools;
import com.appbaba.iz.widget.GridSpacingItemDecoration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 效果图详情页
 */
public class EffectActivity extends BaseAty<BaseBean, BaseBean> implements ViewPager
        .OnPageChangeListener {
    private ViewPager viewPager;
    private RecyclerView recyclerView;
    private List<CaseEntity.ListBean> caseList = new ArrayList<>();//ViewPager list
    private List<CaseEntity.ListBean.ProductListBean> productList = new ArrayList<>();//Product list
    private CommonBinderAdapter<CaseEntity.ListBean.ProductListBean> commonBinderAdapter;

    private CasesAttrSelection selection = new CasesAttrSelection();
    private Adapter adapter = new Adapter();

    private int index = -1;
    private String productId = "";
    private String pageSize = "10";

    private boolean isCollect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getIntent().getExtras().getInt("index", -1);
        selection = getIntent().getExtras().getParcelable("selection");
        productId = getIntent().getExtras().getString("productId", productId);
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
    protected void initViews() {
        defaultTitleBar(this).setTitleBackgroundColor(R.color.black);
        EffectLayout effectLayout = (EffectLayout) viewDataBinding;
        viewPager = effectLayout.viewPager;
        recyclerView = effectLayout.recyclerView;

        commonBinderAdapter = new CommonBinderAdapter<CaseEntity.ListBean.ProductListBean>(this, R
                .layout.item_effect, productList) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int
                    position, CaseEntity.ListBean.ProductListBean productListBean) {
                ((ItemEffectLayout) viewDataBinding).setEffect(productListBean);
            }
        };
    }

    @Override
    protected void initEvents() {
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        recyclerView.setAdapter(commonBinderAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 30, true));
        networkModel.cases(productId, "", "1", pageSize, selection, NetworkParams.CUPCAKE);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_effect;
    }

    @Override
    protected void onClick(int id, View view) {

    }

    @Override
    public void onJsonObjectResponse(BaseBean baseBean, NetworkParams paramsCode) {
        if (paramsCode == NetworkParams.CUPCAKE) {
            placeResultInFirstTime((CaseEntity) baseBean);
        }
    }

    private void placeResultInFirstTime(CaseEntity baseBean) {
        this.caseList.clear();//清空效果数据源
        this.caseList.addAll(baseBean.getList());//填充效果数据源
        this.productList.clear();//删除效果对应的产品数据源
        this.productList.addAll(caseList.get(0).getProduct_list());//填充效果对应的产品数据源
        adapter.notifyDataSetChanged();//刷新适配器
        commonBinderAdapter.notifyDataSetChanged();//刷新适配器

        if (index != -1) {
            titleBarTools.setTitle(caseList.get(0).getTitle());
            viewPager.setCurrentItem(index, false);//滚动到对应位置
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.productList.clear();
        this.productList.addAll(caseList.get(position).getProduct_list());
        commonBinderAdapter.notifyDataSetChanged();

        this.isCollect = caseList.get(position).getIs_collect().equals("1");
        titleBarTools.setTitle(caseList.get(position).getTitle());
        invalidateOptionsMenu();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class Adapter extends PagerAdapter {

        @Override
        public int getCount() {
            return caseList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(EffectActivity.this);
            ViewPager.LayoutParams params = new ViewPager.LayoutParams();
            params.height = ViewPager.LayoutParams.MATCH_PARENT;
            params.width = ViewPager.LayoutParams.MATCH_PARENT;
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(params);
            Picasso.with(EffectActivity.this).load(caseList.get(position).getThumb()).resize(
                    500, 500).into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView imageView = (ImageView) object;
            container.removeView(imageView);
        }
    }


}

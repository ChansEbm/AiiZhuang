package com.appbaba.iz.ui.activity.album;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appbaba.iz.AppKeyMap;
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
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.ui.activity.TransferActivity;
import com.appbaba.iz.widget.DialogView.ShareDialogView;
import com.appbaba.iz.widget.GridSpacingItemDecoration;
import com.github.pwittchen.prefser.library.Prefser;
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
    private String casesId = "";
    private String pageSize = "10";

    private boolean isCollect = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getIntent().getExtras().getInt("index", -1);//如果是从列表进来,则该index不为-1 否则肯定是-1
        selection = getIntent().getExtras().getParcelable("selection");
        if (selection == null)
            selection = new CasesAttrSelection();
        casesId = getIntent().getExtras().getString("casesId", casesId);//如果是从列别进来,则该值肯定肯定肯定是空字符
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
                if (collectEffect()) return false;
                break;
            case R.id.menu_share:
            {
                if(caseList!=null && caseList.size()>0) {
                    String url = AppKeyMap.HEAD + "Page/cases_detail?cases_id=" + caseList.get(viewPager.getCurrentItem()).getCases_id();
                    ShareDialogView dialogView = new ShareDialogView(this, caseList.get(viewPager.getCurrentItem()).getTitle(), caseList.get(viewPager.getCurrentItem()).getThumb(), caseList.get(viewPager.getCurrentItem()).getThumb(), url);
                    dialogView.show();
                }
            }
                break;
        }

        return true;
    }

    private boolean collectEffect() {
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
        CaseEntity.ListBean listBean = caseList.get(currentItem);
        String casesId = listBean.getCases_id();
        String isCollect = listBean.getIs_collect();

        listBean.setIs_collect(TextUtils.equals(isCollect, "0") ? "1" : "0");
        caseList.set(currentItem, listBean);
        onPageSelected(currentItem);

        networkModel.collectCases(casesId, NetworkParams.DONUT);
        return false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.casesId = intent.getExtras().getString("casesId", "");
        this.pageSize = getIntent().getExtras().getString("pageSize", "10");
        networkModel.cases(casesId, "", "1", pageSize, new CasesAttrSelection(), NetworkParams
                .CUPCAKE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        index = -1;
        casesId = "";
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
        viewPager.setOnClickListener(this);
        viewPager.addOnPageChangeListener(this);
        recyclerView.setAdapter(commonBinderAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 30, true));
        commonBinderAdapter.setBinderOnItemClickListener(this);

        networkModel.cases(casesId, "", "1", pageSize, selection, NetworkParams.CUPCAKE);
        networkModel.addCasesVisit(casesId,NetworkParams.DONUT);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_effect;
    }

    @Override
    protected void onClick(int id, View view) {
        switch (id) {
            case R.id.imageView: {
                int currentItem = viewPager.getCurrentItem();
                ArrayList<String> photoPaths = new ArrayList<>();
                for (CaseEntity.ListBean listBean : caseList) {
                    photoPaths.add(listBean.getImage());
                }
                Intent intent = new Intent(this, ZoomEffectActivity.class).putExtra("index",
                        currentItem).putStringArrayListExtra("photoPaths", photoPaths);
                startActivity(intent);
            }
                break;

        }
    }

    @Override
    public void onJsonObjectResponse(BaseBean baseBean, NetworkParams paramsCode) {
        if (paramsCode == NetworkParams.CUPCAKE) {//获取全局数据
            placeResult((CaseEntity) baseBean);
        }
    }

    private void placeResult(CaseEntity baseBean) {
        this.caseList.clear();//清空效果数据源
        this.caseList.addAll(baseBean.getList());//填充效果数据源
        adapter = new Adapter();
        viewPager.setAdapter(adapter);
        if (index == 0) {
            onPageSelected(0);
            return;
        }
        if (index != -1) {
            viewPager.setCurrentItem(index, false);//滚动到对应位置
        } else {
            if (!TextUtils.isEmpty(casesId)) {
                findSelectedPosition();
            }
        }
    }

    private void findSelectedPosition() {
        for (int i = 0; i < this.caseList.size(); i++) {
            CaseEntity.ListBean listBean = caseList.get(i);
            if (TextUtils.equals(listBean.getCases_id(), casesId)) {
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

    @Override
    public void onBinderItemClick(View clickItem, int parentId, int pos) {
        String productId = productList.get(pos).getProduct_id();
        Bundle bundle = new Bundle();
        bundle.putString("productId", productId);
        bundle.putString("pageSize", pageSize);
        start(bundle, ProductActivity.class);
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
            imageView.setId(R.id.imageView);
            imageView.setOnClickListener(EffectActivity.this);
            Picasso.with(EffectActivity.this).load(caseList.get(position).getImage()).resize(
                    600, 600).into(imageView);
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

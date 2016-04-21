package com.appbaba.iz.ui.fragment.album;


import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.appbaba.iz.AlbumChildLayout;
import com.appbaba.iz.AppKeyMap;
import com.appbaba.iz.ItemAlbumProductLayout;
import com.appbaba.iz.ItemAlbumSelectionLayout;
import com.appbaba.iz.R;
import com.appbaba.iz.adapters.CommonBinderAdapter;
import com.appbaba.iz.adapters.CommonBinderHolder;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.broadcast.UpdateUIBroadcast;
import com.appbaba.iz.entity.Base.BaseBean;
import com.appbaba.iz.entity.Base.Events;
import com.appbaba.iz.entity.main.CasesAttrEntity;
import com.appbaba.iz.entity.main.album.CasesAttrSelection;
import com.appbaba.iz.entity.main.album.ProductEntity;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.ui.activity.album.ProductActivity;
import com.appbaba.iz.widget.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends BaseFgm<BaseBean, BaseBean> implements RadioGroup
        .OnCheckedChangeListener {
    private RecyclerView recyclerView;
    private RecyclerView rvSelection;
    private LinearLayout layoutSelection;
    private RadioGroup radioGroup;
    private DrawerLayout drawerLayout;
    private RadioButton rbStyle;
    private RadioButton rbSize;
    private RadioButton rbCate;
    private TextView tvSelectionTitle;

    private List<CasesAttrEntity.AttrParent> selectionList = new ArrayList<>();
    private List<ProductEntity.ListBean> bodyList = new ArrayList<>();
    private CommonBinderAdapter<CasesAttrEntity.AttrParent> selectionAdapter;
    private CommonBinderAdapter<ProductEntity.ListBean> bodyAdapter;

    private CasesAttrEntity casesAttrEntity;
    private ProductEntity productEntity = new ProductEntity();

    private CasesAttrSelection selection = new CasesAttrSelection();//保存选择后的ids

    private String cateId = "";//保存从主页点击过来的id
    private UpdateUIBroadcast updateUIBroadcast;

    @Override
    protected void initViews() {
        AlbumChildLayout effectLayout = (AlbumChildLayout) viewDataBinding;
        Events events = new Events();
        events.setOnClickListener(this);
        effectLayout.setEvents(events);
        recyclerView = effectLayout.recyclerView;
        rvSelection = effectLayout.rvSelection;
        layoutSelection = effectLayout.llytSelection;
        radioGroup = effectLayout.radioGroup;
        drawerLayout = effectLayout.drawerLayout;
        tvSelectionTitle = effectLayout.tvSelectionTitle;
        rbStyle = effectLayout.rbStyle;
        rbSize = effectLayout.rbSpace;
        rbCate = effectLayout.rbCate;
        initAdapters();

        updateUIBroadcast = new UpdateUIBroadcast();
        updateUIBroadcast.setListener(this);
        AppTools.registerBroadcast(updateUIBroadcast, AppKeyMap.CASE_ACTION);
    }

    @Override
    protected void initEvents() {
        rvSelection.setAdapter(selectionAdapter);
        rvSelection.addItemDecoration(AppTools.defaultHorizontalDecoration());
        rvSelection.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(bodyAdapter);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 10, true));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        recyclerView.setHasFixedSize(true);
        rvSelection.setHasFixedSize(true);
        radioGroup.setOnCheckedChangeListener(this);
        rbSize.setText(R.string.fragment_album_size);
        networkModel.casesAttrs(NetworkParams.CUPCAKE);//获取风格、空间、分类
        if (!TextUtils.isEmpty(cateId))
            selection.setCateId(cateId);
        networkModel.product("", "", "1", "10", selection, NetworkParams.DONUT);//主体内容
    }

    /**
     * 初始化Adapter
     */
    private void initAdapters() {
        selectionAdapter = new CommonBinderAdapter<CasesAttrEntity.AttrParent>(getActivity(), R
                .layout.item_album_selection, selectionList) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int
                    position, CasesAttrEntity.AttrParent attrParent) {
                ((ItemAlbumSelectionLayout) viewDataBinding).setAttr(attrParent);
            }
        };
        bodyAdapter = new CommonBinderAdapter<ProductEntity.ListBean>(getContext(), R.layout
                .item_album_product, bodyList) {

            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int
                    position, ProductEntity.ListBean listBean) {
                ((ItemAlbumProductLayout) viewDataBinding).setProduct(listBean);
            }
        };
        selectionAdapter.setBinderOnItemClickListener(this);
        bodyAdapter.setBinderOnItemClickListener(this);
    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {
        switch (id) {
            case R.id.rb_style:
                rbStyle.setChecked(true);
                break;
            case R.id.rb_space:
                rbSize.setChecked(true);
                break;
            case R.id.rb_cate:
                rbCate.setChecked(true);
                break;
        }
        if (id == R.id.rb_space || id == R.id.rb_style || id == R.id.rb_cate) {
            if (!drawerLayout.isDrawerOpen(layoutSelection)) {
                drawerLayout.openDrawer(layoutSelection);
            }
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_album_child;
    }

    @Override
    public void onJsonObjectSuccess(BaseBean t, NetworkParams paramsCode) {
        if (paramsCode == NetworkParams.CUPCAKE) {//means selection data return
            this.casesAttrEntity = (CasesAttrEntity) t;
            notifyCateSelection((CasesAttrEntity) t);
        } else if (paramsCode == NetworkParams.DONUT) {
            productEntity = (ProductEntity) t;
            this.bodyList.clear();
            this.bodyList.addAll(productEntity.getList());
            this.bodyAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void uiUpData(Intent intent) {
        String action = intent.getAction();
        if (TextUtils.equals(action, AppKeyMap.CASE_ACTION)) {
            this.cateId = intent.getStringExtra(AppKeyMap.CATE_ID);
            rbSize.setText(R.string.fragment_album_size);
            rbStyle.setText(R.string.fragment_album_style);
            rbSize.setChecked(false);
            rbStyle.setChecked(false);
            rbCate.setChecked(true);
            selection.setSizeId("0");
            selection.setStyleId("0");
            selection.setSpaceId("0");
            selection.setCateId(cateId);
            networkModel.casesAttrs(NetworkParams.CUPCAKE);//获取风格、空间、分类
            networkModel.product("", "", "1", "10", selection, NetworkParams.DONUT);
        }
    }

    private void notifyCateSelection(CasesAttrEntity t) {
        if (TextUtils.isEmpty(cateId))
            return;
        resetAllSelection();
        List<CasesAttrEntity.CateListBean> caseList = t.getCate_list();
        for (int i = 0; i < caseList.size(); i++) {
            CasesAttrEntity.CateListBean cateListBean = caseList.get(i);
            if (TextUtils.equals(cateListBean.getCate_id(), cateId)) {
                cateListBean.setCheck(true);
                rbCate.setChecked(true);
                rbCate.setText(cateListBean.getTitle());
                break;
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (casesAttrEntity == null) {
            AppTools.showNormalSnackBar(parentView, "获取种类数据失败");
            return;
        }
        this.selectionList.clear();
        switch (checkedId) {
            case R.id.rb_style://风格
                tvSelectionTitle.setText(R.string.fragment_album_style);
                this.selectionList.addAll(casesAttrEntity.getStyle_list());
                break;
            case R.id.rb_space://尺寸
                tvSelectionTitle.setText(R.string.fragment_album_space);
                this.selectionList.addAll(casesAttrEntity.getSize_list());
                break;
            case R.id.rb_cate://分类
                tvSelectionTitle.setText(R.string.fragment_album_cate);
                this.selectionList.addAll(casesAttrEntity.getCate_list());
                break;
        }
        selectionAdapter.notifyDataSetChanged();
        drawerLayout.openDrawer(layoutSelection);
    }

    @Override
    public void onBinderItemClick(View clickItem, int parentId, int pos) {
        switch (parentId) {
            case R.id.rv_selection:
                modifierSelections(pos);//改变条件栏的效果样式并且保存好各种id
                networkModel.product("", "", "1", "20", selection, NetworkParams.DONUT);//然后获取数据
                break;
            case R.id.recyclerView:
                Bundle bundle = new Bundle();
                int page = productEntity.getCond().getPage();
                String pageSize = String.valueOf((page * 10));
                bundle.putInt("index", pos);
                bundle.putParcelable("selection", selection);
                bundle.putString("pageSize", pageSize);
                start(bundle, ProductActivity.class);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppTools.unregisterBroadcast(updateUIBroadcast);
    }

    private void modifierSelections(int pos) {
        CasesAttrEntity.AttrParent attrParent = selectionList.get(pos);
        resetAllSelection();
        attrParent.setCheck(true);
        if (attrParent instanceof CasesAttrEntity.StyleListBean) {
            if (pos == 0) {
                rbStyle.setText(R.string.fragment_album_style);
            } else {
                rbStyle.setText(attrParent.getTitle());
            }
            selection.setStyleId(attrParent.getStyle_id());
        } else if (attrParent instanceof CasesAttrEntity.SizeListBean) {
            if (pos == 0) {
                rbSize.setText(R.string.fragment_album_size);
            } else {
                rbSize.setText(attrParent.getTitle());
            }
            selection.setSizeId(attrParent.getSize_id());
        } else if (attrParent instanceof CasesAttrEntity.CateListBean) {
            if (pos == 0) {
                rbCate.setText(R.string.fragment_album_cate);
            } else {
                rbCate.setText(attrParent.getTitle());
            }
            selection.setCateId(attrParent.getCate_id());
        }
        drawerLayout.closeDrawer(layoutSelection);
        selectionAdapter.notifyDataSetChanged();
    }

    private void resetAllSelection() {
        for (CasesAttrEntity.AttrParent parent : selectionList) {
            parent.setCheck(false);
        }
    }
}

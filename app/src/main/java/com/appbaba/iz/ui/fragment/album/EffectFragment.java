package com.appbaba.iz.ui.fragment.album;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.appbaba.iz.AlbumChildLayout;
import com.appbaba.iz.ItemAlbumLayout;
import com.appbaba.iz.ItemAlbumSelectionLayout;
import com.appbaba.iz.R;
import com.appbaba.iz.adapters.CommonBinderAdapter;
import com.appbaba.iz.adapters.CommonBinderHolder;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.entity.Base.BaseBean;
import com.appbaba.iz.entity.Base.Events;
import com.appbaba.iz.entity.main.CasesAttrEntity;
import com.appbaba.iz.entity.main.album.CaseEntity;
import com.appbaba.iz.entity.main.album.CasesAttrSelection;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.ui.activity.album.EffectActivity;
import com.appbaba.iz.widget.GridSpacingItemDecoration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChanZeeBm on 2016/4/8.
 * 效果图 页面
 */
public class EffectFragment extends BaseFgm<BaseBean, BaseBean> implements RadioGroup
        .OnCheckedChangeListener {
    private RecyclerView recyclerView;
    private RecyclerView rvSelection;
    private LinearLayout layoutSelection;
    private RadioGroup radioGroup;
    private DrawerLayout drawerLayout;
    private RadioButton rbStyle;
    private RadioButton rbSpace;
    private RadioButton rbCate;
    private TextView tvSelectionTitle;

    private List<CasesAttrEntity.AttrParent> selectionList = new ArrayList<>();
    private List<CaseEntity.ListBean> bodyList = new ArrayList<>();
    private CommonBinderAdapter<CasesAttrEntity.AttrParent> selectionAdapter;
    private CommonBinderAdapter<CaseEntity.ListBean> bodyAdapter;

    private CasesAttrEntity casesAttrEntity;

    private CasesAttrSelection selection = new CasesAttrSelection();//保存选择后的ids
    private CaseEntity caseEntity = new CaseEntity();

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
        rbSpace = effectLayout.rbSpace;
        rbCate = effectLayout.rbCate;
        initAdapters();
    }

    @Override
    protected void initEvents() {
        rvSelection.setAdapter(selectionAdapter);
        rvSelection.addItemDecoration(AppTools.defaultHorizontalDecoration());
        rvSelection.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(bodyAdapter);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 10, true));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        recyclerView.setHasFixedSize(true);
        rvSelection.setHasFixedSize(true);
        radioGroup.setOnCheckedChangeListener(this);
        networkModel.casesAttrs(NetworkParams.CUPCAKE);//获取风格、空间、分类
        networkModel.cases("", "", "1", "20", selection, NetworkParams.DONUT);//主体内容
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
        bodyAdapter = new CommonBinderAdapter<CaseEntity.ListBean>(getContext(), R.layout
                .item_album_effect, bodyList) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int
                    position, CaseEntity.ListBean listBean) {
                ItemAlbumLayout albumLayout = (ItemAlbumLayout) viewDataBinding;
                Picasso.with(getContext()).load(listBean.getThumb()).resize(300, 300).into
                        (albumLayout.imageView);
                ((ItemAlbumLayout) viewDataBinding).setAlbum(listBean);
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
                rbSpace.setChecked(true);
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
        } else if (paramsCode == NetworkParams.DONUT) {
            caseEntity = (CaseEntity) t;
            this.bodyList.clear();
            this.bodyList.addAll(caseEntity.getList());
            this.bodyAdapter.notifyDataSetChanged();
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
            case R.id.rb_space://空间
                tvSelectionTitle.setText(R.string.fragment_album_space);
                this.selectionList.addAll(casesAttrEntity.getSpace_list());
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
                networkModel.cases("", "", "1", "20", selection, NetworkParams.DONUT);//然后获取数据
                break;
            case R.id.recyclerView:
                Bundle bundle = new Bundle();
                int page = caseEntity.getCond().getPage();
                String pageSize = String.valueOf((page * 10));
                bundle.putInt("index", pos);
                bundle.putParcelable("selection", selection);
                bundle.putString("pageSize", pageSize);
                start(bundle, EffectActivity.class);
                break;
        }
    }

    private void modifierSelections(int pos) {
        CasesAttrEntity.AttrParent attrParent = selectionList.get(pos);
        for (CasesAttrEntity.AttrParent parent : selectionList) {
            parent.setCheck(false);
        }
        attrParent.setCheck(true);
        if (attrParent instanceof CasesAttrEntity.StyleListBean) {
            if (pos == 0) {
                rbStyle.setText(R.string.fragment_album_style);
            } else {
                rbStyle.setText(attrParent.getTitle());
            }
            selection.setStyleId(attrParent.getStyle_id());
        } else if (attrParent instanceof CasesAttrEntity.SpaceListBean) {
            if (pos == 0) {
                rbSpace.setText(R.string.fragment_album_space);
            } else {
                rbSpace.setText(attrParent.getTitle());
            }
            selection.setSpaceId(attrParent.getSpace_id());
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

}

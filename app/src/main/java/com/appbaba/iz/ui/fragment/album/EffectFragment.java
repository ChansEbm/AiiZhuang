package com.appbaba.iz.ui.fragment.album;

import android.databinding.ViewDataBinding;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.appbaba.iz.AlbumEffectLayout;
import com.appbaba.iz.ItemAlbumSelectionLayout;
import com.appbaba.iz.R;
import com.appbaba.iz.adapters.CommonBinderAdapter;
import com.appbaba.iz.adapters.CommonBinderHolder;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.entity.Base.BaseBean;
import com.appbaba.iz.entity.main.CasesAttrEntity;
import com.appbaba.iz.entity.main.album.CasesAttrSelection;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.tools.AppTools;

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
    private RadioGroup radioGroup;
    private DrawerLayout drawerLayout;
    private RadioButton rbStyle;
    private RadioButton rbSpace;
    private RadioButton rbCate;

    private List<CasesAttrEntity.AttrParent> selectionList = new ArrayList<>();
    private CommonBinderAdapter<CasesAttrEntity.AttrParent> selectionAdapter;

    private CasesAttrEntity casesAttrEntity;

    private CasesAttrSelection selection = new CasesAttrSelection();//保存选择后的ids

    @Override
    protected void initViews() {
        AlbumEffectLayout effectLayout = (AlbumEffectLayout) viewDataBinding;
        recyclerView = effectLayout.recyclerView;
        rvSelection = effectLayout.rvSelection;
        radioGroup = effectLayout.radioGroup;
        drawerLayout = effectLayout.drawerLayout;
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

        radioGroup.setOnCheckedChangeListener(this);
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
        selectionAdapter.setBinderOnItemClickListener(this);
    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_effect;
    }

    @Override
    public void onJsonObjectSuccess(BaseBean t, NetworkParams paramsCode) {
        if (paramsCode == NetworkParams.CUPCAKE) {//means selection data return
            this.casesAttrEntity = (CasesAttrEntity) t;
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
                this.selectionList.addAll(casesAttrEntity.getStyle_list());
                break;
            case R.id.rb_space://空间
                this.selectionList.addAll(casesAttrEntity.getSpace_list());
                break;
            case R.id.rb_cate://分类
                this.selectionList.addAll(casesAttrEntity.getCate_list());
                break;
        }
        selectionAdapter.notifyDataSetChanged();
        drawerLayout.openDrawer(rvSelection);
    }

    @Override
    public void onBinderItemClick(View clickItem, int parentId, int pos) {
        switch (parentId) {
            case R.id.rv_selection:
                modifierSelections(pos);//改变条件栏的效果样式并且保存好各种id
                break;
            case R.id.recyclerView:
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
            selection.setStyleId(attrParent.getStyle_id());
            rbStyle.setText(attrParent.getTitle());
        } else if (attrParent instanceof CasesAttrEntity.SpaceListBean) {
            selection.setSpaceId(attrParent.getSpace_id());
            rbSpace.setText(attrParent.getTitle());
        } else if (attrParent instanceof CasesAttrEntity.CateListBean) {
            selection.setCateId(attrParent.getCate_id());
            rbCate.setText(attrParent.getTitle());
        }
        drawerLayout.closeDrawer(rvSelection);
    }
}

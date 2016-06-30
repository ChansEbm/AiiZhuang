package com.appbaba.iz.ui.fragment.album;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.appbaba.iz.AlbumChildLayout;
import com.appbaba.iz.AppKeyMap;
import com.appbaba.iz.ItemAlbumLayout;
import com.appbaba.iz.ItemAlbumSelectionLayout;
import com.appbaba.iz.R;
import com.appbaba.iz.adapters.CommonBinderAdapter;
import com.appbaba.iz.adapters.CommonBinderHolder;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.broadcast.UpdateUIBroadcast;
import com.appbaba.iz.entity.Base.BaseBean;
import com.appbaba.iz.entity.Base.Events;
import com.appbaba.iz.entity.main.CasesAttrEntity;
import com.appbaba.iz.entity.main.album.CaseEntity;
import com.appbaba.iz.entity.main.album.CasesAttrSelection;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.impl.UpdateUIListener;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.ui.activity.album.EffectActivity;
import com.appbaba.iz.widget.GridSpacingItemDecoration;
import com.github.pwittchen.prefser.library.Prefser;
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
    private SwipeRefreshLayout swRefresh;

    private List<CasesAttrEntity.AttrParent> selectionList = new ArrayList<>();
    private List<CaseEntity.ListBean> bodyList = new ArrayList<>();
    private CommonBinderAdapter<CasesAttrEntity.AttrParent> selectionAdapter;
    private CommonBinderAdapter<CaseEntity.ListBean> bodyAdapter;

    private CasesAttrEntity casesAttrEntity;
    private UpdateUIBroadcast updateUIBroadcast;
    private CasesAttrSelection selection = new CasesAttrSelection();//保存选择后的ids
    private CaseEntity caseEntity = new CaseEntity();
    private int height = 0;
    private int currentPage = 1,pageNum = 12;
    private int state = 0,offset = 0;
    private String keyword= "";
    private String caseId,spaceId,styleId;
    private  boolean isShow = true; //（新增）判断当前是否需要弹出选择列表



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

        swRefresh = effectLayout.swRefresh;
        swRefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        height = MethodConfig.GetHeightFor4v3((MethodConfig.metrics.widthPixels - 3 * MethodConfig.dip2px(getContext(),10))/2);

        initAdapters();

        caseId = new Prefser(AppTools.getSharePreferences()).get(AppKeyMap.E_CATE_ID, String
                .class, "");
        styleId = new Prefser(AppTools.getSharePreferences()).get(AppKeyMap.E_STYLE_ID, String
                .class, "");
        spaceId = new Prefser(AppTools.getSharePreferences()).get(AppKeyMap.SPACE_ID, String
                .class, "");

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
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 10, true));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        recyclerView.setHasFixedSize(true);
        rvSelection.setHasFixedSize(true);
        radioGroup.setOnCheckedChangeListener(this);
        networkModel.casesAttrs(NetworkParams.CUPCAKE);//获取风格、空间、分类


        if (!TextUtils.isEmpty(caseId) || !TextUtils.isEmpty(styleId) || !TextUtils.isEmpty(spaceId)) {
            isShow = false;
            selection.setCateId(caseId);
            selection.setSpaceId(spaceId);
            selection.setStyleId(styleId);
        }
        else
        {
            isShow = true;
        }


        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bodyList.clear();
                currentPage=1;
                keyword = "";
                spaceId ="";
                caseId="";
                styleId = "";
                MethodConfig.ClearEffectSelection();
                selection = new CasesAttrSelection();
                ClearSelction(casesAttrEntity);
                GetData();
            }
        });
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction())
                {
                    case MotionEvent.ACTION_MOVE:
                        if(bodyList.size()%pageNum==0) {
                            if (state == 0) {
                                state = (int) event.getY();
                            }
                            int k = (int) event.getY();
                            if(recyclerView.computeVerticalScrollOffset()>offset)
                            {
                                offset = recyclerView.computeVerticalScrollOffset();
                            }
                            if ((k - state) < (-1) * 50 && offset== recyclerView.computeVerticalScrollOffset()) {
                                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                                int index = lm.findLastVisibleItemPosition();
                                if (index == (bodyList.size() - 1)) {
                                    currentPage++;
                                    GetData();
                                }
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        state = 0;
                        break;
                }
                return false;
            }
        });
        GetData();

    }

    private void ClearSelction(CasesAttrEntity t)
    {
        List<CasesAttrEntity.CateListBean> caseList = t.getCate_list();
        List<CasesAttrEntity.SizeListBean> sizeList = t.getSize_list();
        List<CasesAttrEntity.StyleListBean> styleList = t.getStyle_list();
        for (int i = 0; i < caseList.size(); i++) {
            CasesAttrEntity.CateListBean cateListBean = caseList.get(i);
            cateListBean.setCheck(false);
        }
        for (int i = 0; i < sizeList.size(); i++) {
            CasesAttrEntity.SizeListBean sizeListBean = sizeList.get(i);
            sizeListBean.setCheck(false);
        }
        for (int i = 0; i < styleList.size(); i++) {
            CasesAttrEntity.StyleListBean styleListBean = styleList.get(i);
            styleListBean.setCheck(false);
        }
        rbStyle.setText(R.string.fragment_album_style);
        rbSpace.setText(R.string.fragment_album_space);
        rbCate.setText(R.string.fragment_album_cate);
    }

    public void GetData()
    {
        state = 0;
        offset = 0;
        networkModel.cases("", keyword, ""+currentPage, ""+pageNum, selection, NetworkParams.DONUT);//主体内容
    }

    private void resetAllSelection() {
        for (CasesAttrEntity.AttrParent parent : selectionList) {
            parent.setCheck(false);
        }
    }
    private void notifyCateSelection(CasesAttrEntity t) {
        if (TextUtils.isEmpty(caseId) && TextUtils.isEmpty(spaceId) && TextUtils.isEmpty(styleId))
            return;
        resetAllSelection();
        List<CasesAttrEntity.CateListBean> caseList = t.getCate_list();
        List<CasesAttrEntity.SpaceListBean> spaceList = t.getSpace_list();
        List<CasesAttrEntity.StyleListBean> styleList = t.getStyle_list();

        for (int i = 0; i < caseList.size(); i++) {
            CasesAttrEntity.CateListBean cateListBean = caseList.get(i);
            if (TextUtils.equals(cateListBean.getCate_id(), caseId)) {
                cateListBean.setCheck(true);
                rbCate.setChecked(true);
                rbCate.setText(cateListBean.getTitle());
                break;
            }
        }
        for (int i = 0; i < spaceList.size(); i++) {
            CasesAttrEntity.SpaceListBean spaceListBean = spaceList.get(i);
            if(TextUtils.equals(spaceListBean.getSize_id(), spaceId))
            {
                spaceListBean.setCheck(true);
                rbSpace.setChecked(true);
                rbSpace.setText(spaceListBean.getTitle());
                break;
            }
        }
        for (int i = 0; i < styleList.size(); i++) {
            CasesAttrEntity.StyleListBean styleListBean = styleList.get(i);
            if(TextUtils.equals(styleListBean.getStyle_id(), styleId))
            {
                styleListBean.setCheck(true);
                rbStyle.setChecked(true);
                rbStyle.setText(styleListBean.getTitle());
                break;
            }
        }
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
                albumLayout.imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height));
                Picasso.with(getContext()).load(listBean.getThumb()).into
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
    public void uiUpData(Intent intent) {
        String action = intent.getAction();
        if (TextUtils.equals(action, AppKeyMap.CASE_ACTION)) {
            if(intent.getExtras().getBoolean("isEffect",false))
            {
                keyword = intent.getExtras().getString(AppKeyMap.PRO_KEYWORD,"");
                currentPage = 1;
                GetData();
            }
            else if(!intent.getExtras().containsKey("isEffect"))
            {
                this.caseId = intent.getExtras().getString(AppKeyMap.E_CATE_ID,"");
                this.styleId = intent.getExtras().getString(AppKeyMap.E_STYLE_ID,"");
                this.spaceId = intent.getExtras().getString(AppKeyMap.SPACE_ID,"");

                if (TextUtils.isEmpty(caseId)) {
                    rbCate.setChecked(false);
                } else {
                    rbCate.setChecked(true);
                    isShow = false;
                }
                if (TextUtils.isEmpty(spaceId)) {
                    rbSpace.setChecked(false);
                } else {
                    rbSpace.setChecked(true);
                    isShow = false;
                }
                if (TextUtils.isEmpty(styleId)) {
                    rbStyle.setChecked(false);
                } else {
                    rbStyle.setChecked(true);
                    isShow = false;
                }
                selection.setSizeId("0");
                selection.setStyleId(styleId);
                selection.setSpaceId(spaceId);
                selection.setCateId(caseId);
                notifyCateSelection(casesAttrEntity);
                selectionAdapter.notifyDataSetChanged();

                currentPage = 1;
                GetData();
            }

        }
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
            notifyCateSelection(casesAttrEntity);
        } else if (paramsCode == NetworkParams.DONUT) {
            caseEntity = (CaseEntity) t;
            if(caseEntity.getList()==null || caseEntity.getList().size()==0)
            {
                if(currentPage==1) {
                    AppTools.showNormalSnackBar(getView(), "Sorry,没有找到你要的效果图");
                }
                else
                {
                    currentPage--;
                    AppTools.showNormalSnackBar(getView(), "Sorry,已经最后一页了");
                    return;
                }
            }
            if(currentPage==1) {
                this.bodyList.clear();
            }
            this.bodyList.addAll(caseEntity.getList());
            this.bodyAdapter.notifyDataSetChanged();
        }
        if(swRefresh.isRefreshing())
        {
            swRefresh.setRefreshing(false);
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
        if(isShow) {
            drawerLayout.openDrawer(layoutSelection);
        }
        else
        {
            isShow = false;
        }
//        drawerLayout.openDrawer(layoutSelection);
    }

    @Override
    public void onBinderItemClick(View clickItem, int parentId, int pos) {
        switch (parentId) {
            case R.id.rv_selection:
                modifierSelections(pos);//改变条件栏的效果样式并且保存好各种id
                currentPage = 1;
                keyword="";
                GetData();
                break;
            case R.id.recyclerView:
                Bundle bundle = new Bundle();
                int page = caseEntity.getCond().getPage();
                String pageSize = String.valueOf((page * pageNum));
                bundle.putInt("index", pos);
                bundle.putParcelable("selection", selection);
                bundle.putString("pageSize", pageSize);
                start(bundle, EffectActivity.class);
                break;
        }
    }

    private void modifierSelections(int pos) {
        if(selectionList.size()==0)
            return;
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

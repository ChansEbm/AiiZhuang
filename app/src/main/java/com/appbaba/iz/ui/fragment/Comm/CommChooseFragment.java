package com.appbaba.iz.ui.fragment.Comm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;

import com.appbaba.iz.AppKeyMap;
import com.appbaba.iz.FragmentCommChooseBinding;
import com.appbaba.iz.FragmentCommSellerBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.adapters.CommonAdapter;
import com.appbaba.iz.adapters.CommonBinderAdapter;
import com.appbaba.iz.adapters.ViewHolder;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.entity.Base.BaseBean;
import com.appbaba.iz.entity.Friends.FriendsClientBean;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.tools.AppTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/4/16.
 */
public class CommChooseFragment extends BaseFgm {
    private FragmentCommChooseBinding chooseBinding;

    private CommonAdapter<Object> adapter;
    private List<Object> list;

    private  String title;
    private  int which;

    @Override
    protected void initViews() {

        title = getArguments().getString("title","");
        which = getArguments().getInt("which",-1);

        chooseBinding = (FragmentCommChooseBinding)viewDataBinding;

        chooseBinding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.more_arrow_dark_left);
        chooseBinding.includeTopTitle.title.setText(title);
        chooseBinding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        chooseBinding.includeTopTitle.title.setTextColor(Color.BLACK);

        if(which==-1)
        {
            ((Activity)getContext()).finish();
        }

        list = new ArrayList<>();
        adapter = new CommonAdapter<Object>(list,getContext(),R.layout.item_comm_ltv_view) {
            @Override
            public void convert(int position, ViewHolder holder, Object o) {
                if(which==1)
                {
                    FriendsClientBean.ListEntity entity = (FriendsClientBean.ListEntity)o;
                    holder.setText(R.id.tv_item_view,entity.getName());
                    holder.getConvertView().setTag(entity);
                }

            }
        };
        chooseBinding.ltvData.setAdapter(adapter);

    }

    @Override
    protected void initEvents() {

        chooseBinding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((Activity)getContext()).finish();
            }
        });

        chooseBinding.ltvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(which==1) {
                    MethodConfig.chooseClient = (FriendsClientBean.ListEntity)view.getTag();
                    AppTools.putStringSharedPreferences(AppKeyMap.CUSTOMERID,MethodConfig.chooseClient.getCustomer_id());
                }
                Intent intent = new Intent();
                ((Activity)getContext()).setResult(100,intent);
                ((Activity)getContext()).finish();
            }
        });
        switch (which)
        {
            case 1:
                networkModel.HomeMarketingCustomerList(MethodConfig.localUser.getAuth(), NetworkParams.CUSTOMERLIST);
                break;
        }
    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_comm_choose;
    }

    @Override
    public void onJsonObjectSuccess(Object t, NetworkParams paramsCode) {
        if(paramsCode==NetworkParams.CUSTOMERLIST)
        {
            FriendsClientBean bean = (FriendsClientBean)t;
            list.addAll(bean.getList());
            adapter.notifyDataSetChanged();
        }
    }
}

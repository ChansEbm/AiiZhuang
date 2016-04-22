package com.appbaba.iz.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.appbaba.iz.AppKeyMap;
import com.appbaba.iz.FragmentFriendAddClientBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.entity.Base.BaseBean;
import com.appbaba.iz.entity.Friends.FriendsClientBean;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.impl.UpdateUIListener;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.model.AddClientModel;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.ui.activity.TransferActivity;

/**
 * Created by ruby on 2016/4/12.
 */
public class FriendsItemAddClientFragment extends BaseFgm {
   private   FragmentFriendAddClientBinding addClientBinding;

    private AddClientModel model = new AddClientModel();

    public AddClientModel entity;

    @Override
    protected void initViews() {

        addClientBinding = (FragmentFriendAddClientBinding)viewDataBinding;
        addClientBinding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.more_arrow_dark_left);
        addClientBinding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        if(entity==null) {
            addClientBinding.includeTopTitle.title.setText(R.string.friends_fragment_client_add_title);
        }
        else
        {
            addClientBinding.includeTopTitle.title.setText("编辑客户资料");
            addClientBinding.setItem(entity);
        }
        addClientBinding.includeTopTitle.title.setTextColor(Color.BLACK);

    }

    @Override
    protected void initEvents() {

        addClientBinding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
        addClientBinding.btnUpload.setOnClickListener(this);

    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {

        switch (id)
        {
            case R.id.btn_upload:
            {
                 if(CheckInput())
                 {
                     if(entity==null) {
                         model.setNetworkParams(NetworkParams.ADDCUSTOMER);
                         networkModel.HomeMarketingAddCustomer(MethodConfig.localUser.getAuth(), model);
                     }
                     else
                     {
                          model.setId(entity.getId());
                          networkModel.HomeMarketingSaveCustomer(MethodConfig.localUser.getAuth(),model);
                     }
                 }
            }
                break;
        }
    }

    public  boolean  CheckInput()
    {
        if(TextUtils.isEmpty(addClientBinding.edtClientName.getText().toString()))
        {
            return  false;
        }
        model.setName(addClientBinding.edtClientName.getText().toString());
        if(TextUtils.isEmpty(addClientBinding.edtClientPhone.getText().toString()))
        {
            return  false;
        }
        model.setPhone(addClientBinding.edtClientPhone.getText().toString());

        model.setArea_ids(addClientBinding.edtClientCity.getText().toString());

        model.setAddress(addClientBinding.edtClientAddress.getText().toString());
        return  true;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_friend_item_add_client;
    }

    @Override
    public void onJsonObjectSuccess(Object t, NetworkParams paramsCode) {

            BaseBean bean = (BaseBean)t;
            AppTools.showNormalSnackBar(getView(),bean.getMsg());
            ((Activity)getContext()).finish();

    }
}

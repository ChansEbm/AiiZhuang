package com.appbaba.iz.ui.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.appbaba.iz.FragmentMoreChangeBinding;
import com.appbaba.iz.FragmentMorePersonBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.entity.Base.BaseBean;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.model.PasswordModel;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.ui.activity.LoginActivity;

/**
 * Created by ruby on 2016/4/8.
 */
public class MoreItemChangePWDFragment extends BaseFgm {
    private  FragmentMoreChangeBinding changeBinding;
    private PasswordModel model = new PasswordModel();
    @Override
    protected void initViews() {
        changeBinding = (FragmentMoreChangeBinding)viewDataBinding;
        changeBinding.includeTopTitle.title.setText(R.string.more_fragment_change_pwd);
        changeBinding.includeTopTitle.title.setTextColor(Color.BLACK);
        changeBinding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        changeBinding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.more_arrow_dark_left);

    }

    @Override
    protected void initEvents() {
         changeBinding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ((Activity)getContext()).finish();
             }
         });
        changeBinding.btnUpload.setOnClickListener(this);
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
                    model.setNetworkParams(NetworkParams.CHANGEPWD);
                    networkModel.HomeMoreChangePwd(MethodConfig.localUser.getAuth(),model);
                }
            }
                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_more_item_change_pwd;
    }

    public boolean CheckInput()
    {
        if(TextUtils.isEmpty(changeBinding.edtOldPwd.getText().toString()))
        {
            return false;
        }
        model.setPwd(changeBinding.edtOldPwd.getText().toString().trim());
        if(TextUtils.isEmpty(changeBinding.edtNewPwd.getText().toString()))
        {
            return false;
        }
        model.setnPwd(changeBinding.edtNewPwd.getText().toString().trim());
        if(TextUtils.isEmpty(changeBinding.edtReNewPwd.getText().toString()))
        {
            return false;
        }
        model.setRnPwd(changeBinding.edtReNewPwd.getText().toString());
        return true;
    }

    @Override
    public void onJsonObjectSuccess(Object t, NetworkParams paramsCode) {
        if(paramsCode==NetworkParams.CHANGEPWD)
        {
            BaseBean bean = (BaseBean)t;
            AppTools.showNormalSnackBar(getView(),bean.getMsg());
            AppTools.putStringSharedPreferences("password","");
            start(LoginActivity.class);
            ((Activity)getContext()).finish();
        }
    }
}

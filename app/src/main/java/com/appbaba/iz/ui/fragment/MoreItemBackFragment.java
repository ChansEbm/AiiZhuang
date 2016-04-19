package com.appbaba.iz.ui.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.appbaba.iz.FragmentMoreBackBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.entity.Base.BaseBean;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.tools.AppTools;

/**
 * Created by ruby on 2016/4/13.
 */
public class MoreItemBackFragment extends BaseFgm {
    FragmentMoreBackBinding backBinding;
    @Override
    protected void initViews() {
        backBinding = (FragmentMoreBackBinding)viewDataBinding;
        backBinding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        backBinding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.more_arrow_dark_left);
        backBinding.includeTopTitle.title.setText(R.string.more_fragment_feedback);
        backBinding.includeTopTitle.title.setTextColor(Color.BLACK);
    }

    @Override
    protected void initEvents() {
             backBinding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     ((Activity)getContext()).finish();
                 }
             });
        backBinding.btnUpload.setOnClickListener(this);
    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {

        switch (id)
        {
            case R.id.btn_upload:
                if(backBinding.edtFeedback.getText().toString().trim().length()<6)
                {
                    AppTools.showNormalSnackBar(getView(),"输入内容不能少于6字");
                }
                else
                {
                    networkModel.HomeMoreFeedBack(MethodConfig.localUser.getAuth(),backBinding.edtFeedback.getText().toString(),NetworkParams.FEEDBACK);
                }
                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_more_item_back;
    }

    @Override
    public void onJsonObjectSuccess(Object t, NetworkParams paramsCode) {
        if(paramsCode==NetworkParams.FEEDBACK)
        {
            BaseBean bean = (BaseBean)t;
            AppTools.showNormalSnackBar(getView(),bean.getMsg());
            backBinding.edtFeedback.getText().clear();
        }
    }
}

package com.appbaba.iz.ui.activity;

import android.text.TextUtils;
import android.view.View;

import com.appbaba.iz.ActivityFoundPwdBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseAty;
import com.appbaba.iz.entity.Base.BaseBean;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.model.FoundPwdModel;
import com.appbaba.iz.tools.AppTools;

/**
 * Created by ruby on 2016/4/1.
 */
public class FoundPasswordActivity extends BaseAty {
    private ActivityFoundPwdBinding foundPwdBinding;

    private FoundPwdModel model = new FoundPwdModel();

    @Override
    protected void initViews() {

        foundPwdBinding = (ActivityFoundPwdBinding)viewDataBinding;
        foundPwdBinding.includeTopTitle.title.setText(R.string.found_activity_tv_top_title);
        foundPwdBinding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.icon_top_back);



    }

    @Override
    protected void initEvents() {
          foundPwdBinding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  onBackPressed();
              }
          });
        foundPwdBinding.tvMsg.setOnClickListener(this);
        foundPwdBinding.btnUpload.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_found_password;
    }

    @Override
    protected void onClick(int id, View view) {
          switch (id)
          {
              case R.id.btn_upload:
                  if(CheckInput())
                  {
                      model.setNetworkParams(NetworkParams.FOUNDPASSWORD);
                      networkModel.ResetPassword(model);
                  }
                  break;
              case  R.id.tv_msg:
              {
                  if(!TextUtils.isEmpty(foundPwdBinding.edtFoundPhone.getText()))
                  {
                      networkModel.isPhoneReg(foundPwdBinding.edtFoundPhone.getText().toString().trim(),NetworkParams.CHECKPHONEINUSE);
                  }
                  else
                  {
                      AppTools.showNormalSnackBar(getWindow().getDecorView(),"请先输入手机号码");
                  }
              }
                  break;
          }
    }

    public  boolean CheckInput()
    {
        if(TextUtils.isEmpty(foundPwdBinding.edtFoundPhone.getText()))
        {
            return  false;
        }
        model.setPhone(foundPwdBinding.edtFoundPhone.getText().toString().trim());
        if(TextUtils.isEmpty(foundPwdBinding.edtFoundCode.getText()))
        {
            return  false;
        }
        model.setCode(foundPwdBinding.edtFoundCode.getText().toString().trim());
        if(TextUtils.isEmpty(foundPwdBinding.edtFoundNewPassword.getText()))
        {
            return false;
        }
        model.setPassword(foundPwdBinding.edtFoundNewPassword.getText().toString().trim());
        if(TextUtils.isEmpty(foundPwdBinding.edtFoundRePassword.getText()))
        {
            return  false;
        }
        model.setRe_password(foundPwdBinding.edtFoundRePassword.getText().toString().trim());
          return  true;
    }

    @Override
    public void onJsonObjectResponse(Object o, NetworkParams paramsCode) {
        BaseBean bean = (BaseBean)o;
          if(paramsCode==NetworkParams.CHECKPHONEINUSE)
          {
              if(bean.getErrorcode()==0) {
                  networkModel.sendSmsCode(foundPwdBinding.edtFoundPhone.getText().toString().trim(), NetworkParams.SENDMSG);
              }
              else
              {
                  AppTools.showNormalSnackBar(getWindow().getDecorView(),bean.getMsg());
              }
          }
          if(paramsCode==NetworkParams.SENDMSG)
          {
              AppTools.showNormalSnackBar(getWindow().getDecorView(),bean.getMsg());
          }
        if(paramsCode==NetworkParams.FOUNDPASSWORD)
        {
            AppTools.showNormalSnackBar(getWindow().getDecorView(),bean.getMsg());
            if(bean.getErrorcode()==0)
            {
                foundPwdBinding.edtFoundNewPassword.getText().clear();
                foundPwdBinding.edtFoundRePassword.getText().clear();
                foundPwdBinding.edtFoundCode.getText().clear();
                foundPwdBinding.edtFoundPhone.getText().clear();
//                onBackPressed();
            }

        }
    }
}

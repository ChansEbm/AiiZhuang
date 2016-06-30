package com.appbaba.platform.widget;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ContentFrameLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.appbaba.platform.DialogLoginViewBinding;
import com.appbaba.platform.DialogMobileBinding;
import com.appbaba.platform.DialogPasswordBinding;
import com.appbaba.platform.DialogRegisterBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.User.UserInfo;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.LoginCallBack;
import com.appbaba.platform.impl.OkHttpResponseListener;
import com.appbaba.platform.impl.UpdateUIListener;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.model.NetworkModel;
import com.appbaba.platform.tools.AppTools;

import java.util.List;

/**
 * Created by ruby on 2016/6/2.
 */
public class LoginDialog implements View.OnClickListener,OkHttpResponseListener{
    private Dialog dialog;

    private NetworkModel networkModel;
    public DialogMobileBinding mobileBinding;
    public DialogPasswordBinding passwordBinding;
    public DialogRegisterBinding registerBinding;

    private Context context;

    private DialogLoginViewBinding loginBinding;
    public LoginCallBack callBack;

    public LoginDialog(Context context)
    {
        this.context = context;
        networkModel = new NetworkModel(context);
        networkModel.setResultCallBack(this);

        loginBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_login_view,null,false);
        dialog = new Dialog(context,R.style.dialog);
        Init();
    }

    /**
     *
     * @param
     */
    public void  Init()
    {
        mobileBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.dialog_mobile_view,null,false);
        passwordBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.dialog_password_input_view,null,false);
        registerBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.dialog_register_view,null,false);

        loginBinding.viewSwitcher.addView(mobileBinding.getRoot());
        mobileBinding.edtMobile.setText(AppTools.getSharePreferences().getString("username",""));
        InitEvent();
    }

    public void InitEvent()
    {
        mobileBinding.tvNext.setOnClickListener(this);
        mobileBinding.tvCancel.setOnClickListener(this);

        passwordBinding.tvBack.setOnClickListener(this);
        registerBinding.tvTicks.setOnClickListener(this);
        registerBinding.tvBack.setOnClickListener(this);
        registerBinding.tvRegister.setOnClickListener(this);
        passwordBinding.tvLogin.setOnClickListener(this);
    }

    public void  Show()
    {
        if(!dialog.isShowing())
        {
            dialog.show();
            Window window = dialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = MethodConfig.metrics.widthPixels/4*3; //设置宽度
            window.setAttributes(lp);
            window.setContentView(loginBinding.getRoot());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_cancel:
                dialog.dismiss();
                break;
            case R.id.tv_next:
                if(!TextUtils.isEmpty(mobileBinding.edtMobile.getText().toString())) {
                    CheckMobile();
                }
                else {
                    Toast.makeText(context,"手机号码不能为空",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.tv_back:
            {
                loginBinding.viewSwitcher.showPrevious();
                loginBinding.viewSwitcher.removeViewAt(1);
            }
            break;
            case R.id.tv_login:
            {
                  if(!TextUtils.isEmpty(passwordBinding.edtMsgCode.getText()))
                  {
                      Login(mobileBinding.edtMobile.getText().toString().trim(),passwordBinding.edtMsgCode.getText().toString().trim(),"");
                  }
            }
            break;
            case R.id.tv_register:
            {
                if(!TextUtils.isEmpty(registerBinding.edtMsgCode.getText()) && !TextUtils.isEmpty(registerBinding.edtPwd.getText())) {
                    Register();
                }
                else {

                }
            }
                break;
            case R.id.tv_ticks:
            {
                if(registerBinding.tvTicks.getText().toString().trim().equals("获取")) {
                    GetCode();
                }
            }
                break;
        }
    }

    public void CheckMobile()
    {
        networkModel.CheckReg(mobileBinding.edtMobile.getText().toString().trim(),NetworkParams.CUPCAKE);
    }

    public void Login(String username,String password,String token)
    {
        if(callBack!=null)
        {
            MethodConfig.userInfo = new UserInfo();
            MethodConfig.userInfo.setUsername(mobileBinding.edtMobile.getText().toString().trim());
            MethodConfig.userInfo.setPassword(passwordBinding.edtMsgCode.getText().toString().trim());
            MethodConfig.userInfo.setToken("");
            callBack.Login(username,password,token);
        }
        dialog.dismiss();
    }

    public void GetCode()
    {
        networkModel.SendSmsCode(mobileBinding.edtMobile.getText().toString().trim(),NetworkParams.DONUT);
        CountDownTimer countDownTimer  = new CountDownTimer(60*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                registerBinding.tvTicks.setText(millisUntilFinished/1000+"s");
                if(loginBinding.viewSwitcher.getChildCount()==1) {
                    this.onFinish();
                }
            }

            @Override
            public void onFinish() {
                registerBinding.tvTicks.setText("获取");
            }
        };
        countDownTimer.start();
    }

    public void Register()
    {
         networkModel.Register(mobileBinding.edtMobile.getText().toString().trim(),registerBinding.edtPwd.getText().toString().trim(),registerBinding.edtMsgCode.getText().toString().trim(),NetworkParams.FROYO);
    }

    @Override
    public void onJsonObjectResponse(Object o, NetworkParams paramsCode) {
        BaseBean baseBean = (BaseBean)o;
        if(baseBean.getErrorcode()==0)
        {
            if(paramsCode==NetworkParams.CUPCAKE) {
                loginBinding.viewSwitcher.addView(registerBinding.getRoot());
                registerBinding.tvTitle.setText(mobileBinding.edtMobile.getText().toString().trim());
                loginBinding.viewSwitcher.showNext();
                GetCode();
            }
            else if(paramsCode==NetworkParams.DONUT)
            {
                Toast.makeText(context,baseBean.getMsg(),Toast.LENGTH_LONG).show();
            }
            else if(paramsCode==NetworkParams.FROYO)
            {
                MethodConfig.userInfo = new UserInfo();
                MethodConfig.userInfo.setUsername(mobileBinding.edtMobile.getText().toString().trim());
                MethodConfig.userInfo.setPassword(registerBinding.edtPwd.getText().toString().trim());
                MethodConfig.userInfo.setToken(baseBean.getToken());
                Login(MethodConfig.userInfo.getUsername(),"",MethodConfig.userInfo.getToken());
                dialog.dismiss();
            }
        }
        else {
            if(paramsCode==NetworkParams.CUPCAKE) {
                loginBinding.viewSwitcher.addView(passwordBinding.getRoot());
                passwordBinding.tvTitle.setText(mobileBinding.edtMobile.getText().toString().trim());
                loginBinding.viewSwitcher.showNext();
            }
           // Toast.makeText(context,baseBean.getMsg(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onJsonArrayResponse(List t, NetworkParams paramsCode) {

    }

    @Override
    public void onError(String error, NetworkParams paramsCode) {

    }
}

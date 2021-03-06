package com.appbaba.iz.ui.activity;

import android.content.Intent;
import android.hardware.usb.UsbRequest;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.appbaba.iz.ActivityLoginBinding;
import com.appbaba.iz.AppKeyMap;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseAty;
import com.appbaba.iz.entity.LocationBean;
import com.appbaba.iz.entity.Login.AuthBean;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.tools.LogTools;
import com.github.pwittchen.prefser.library.JsonConverter;
import com.github.pwittchen.prefser.library.Prefser;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ruby on 2016/3/31.
 */
public class LoginActivity extends BaseAty {
    ActivityLoginBinding loginBinding;
    private EditText edt_mobile,edt_password;
    private Button btn_login,btn_visitor;
    private TextView tv_forget,tv_brand_in;
    @Override
    protected void initViews() {
        loginBinding = (ActivityLoginBinding)viewDataBinding;
        edt_mobile = loginBinding.edtMobile;
        edt_password = loginBinding.edtPwd;
        btn_login= loginBinding.btnLogin;
        btn_visitor = loginBinding.btnVisitor;
        tv_forget = loginBinding.tvForget;
        tv_brand_in = loginBinding.tvBrandIn;

        MethodConfig.localUser = null;

        String username = AppTools.getStringSharedPreferences("username","");
        edt_mobile.setText(username);
        String password = AppTools.getStringSharedPreferences("password","");
        edt_password.setText(password);
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password))
        {
            networkModel.Login(username, password, MethodConfig.jpush_id, NetworkParams.LOGIN);
        }
    }


    @Override
    protected void initEvents() {
        btn_visitor.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_brand_in.setOnClickListener(this);
        tv_forget.setOnClickListener(this);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void onClick(int id, View view) {
         switch (id)
         {
             case  R.id.btn_login:
             {
                 if(edt_mobile.getText().toString().trim().length()>0 && edt_password.getText().toString().trim().length()>0) {
                     networkModel.Login(edt_mobile.getText().toString(), edt_password.getText().toString(), MethodConfig.jpush_id, NetworkParams.LOGIN);
                 }
             }
                 break;
             case R.id.btn_visitor:
                 AppTools.putStringSharedPreferences(AppKeyMap.AUTH,"");
                 MethodConfig.localUser = null;
                 new Prefser(AppTools.getSharePreferences()).put(AppKeyMap.CUSTOMERID, "");
                 MethodConfig.ClearAllSelection();

                 Intent intent =new Intent(getApplicationContext(),HomeActivity.class);

                 startActivity(intent);
                 if(MethodConfig.mainActivity!=null)
                 {
                     MethodConfig.mainActivity.finish();
                 }
                 finish();
                 break;
             case R.id.tv_forget:
                 startActivity(new Intent(getApplicationContext(),FoundPasswordActivity.class));
                 break;
             case R.id.tv_brand_in:
                 startActivity(new Intent(getApplicationContext(),RegisterBrandActivity.class));
                 break;
         }
    }

    @Override
    public void onJsonObjectResponse(Object o, NetworkParams paramsCode) {

        if(paramsCode==NetworkParams.LOGIN)
        {
            AuthBean bean = (AuthBean)o;
            if(bean.getErrorcode()==0)
            {
                AppTools.putStringSharedPreferences(AppKeyMap.AUTH,bean.getAuth());
                MethodConfig.localUser = bean;
                new Prefser(AppTools.getSharePreferences()).put(AppKeyMap.CUSTOMERID, "");
                MethodConfig.ClearAllSelection();
                AppTools.putStringSharedPreferences("username",edt_mobile.getText().toString());
                AppTools.putStringSharedPreferences("password",edt_password.getText().toString());
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
            else
            {
                AppTools.showNormalSnackBar(this.getWindow().getDecorView(),bean.getMsg());
            }
        }
    }
}

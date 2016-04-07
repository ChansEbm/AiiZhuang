package com.appbaba.iz.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.appbaba.iz.ActivityLoginBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseAty;
import com.appbaba.iz.eum.NetworkParams;

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
                 startActivity(new Intent(getApplicationContext(),MainActivity.class));
                 break;
             case R.id.btn_visitor:
                 startActivity(new Intent(getApplicationContext(),MainActivity.class));
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

    }
}

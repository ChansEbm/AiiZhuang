package com.appbaba.iz.ui.test;

import android.os.Bundle;
import android.view.View;

import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseAty;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.tools.AppTools;

public class TestAty extends BaseAty {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void initEvents() {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        AppTools.showLoadingDialog(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_test_aty;
    }

    @Override
    protected void onClick(int id, View view) {

    }

    @Override
    public void onJsonObjectResponse(Object o, NetworkParams paramsCode) {

    }
}

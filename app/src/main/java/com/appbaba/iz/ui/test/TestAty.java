package com.appbaba.iz.ui.test;

import android.os.Bundle;
import android.view.View;

import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseAty;
import com.appbaba.iz.entity.main.album.CasesAttrSelection;
import com.appbaba.iz.eum.NetworkParams;

public class TestAty extends BaseAty {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initViews() {
        defaultTitleBar(this).setTitle("12345678");
    }

    @Override
    protected void initEvents() {
        networkModel.product("", "", "", "", new CasesAttrSelection(), NetworkParams.CUPCAKE);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
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

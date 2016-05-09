package com.appbaba.platform.dialog;

import android.content.Context;
import android.view.View;

import com.appbaba.platform.LoadingLayout;
import com.appbaba.platform.R;

/**
 * Created by ChanZeeBm on 2015/10/16.
 */
public class LoadingDialog extends BaseDialog {
    LoadingLayout loadingLayout;

    public LoadingDialog(Context context) {
        super(context);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
        loadingLayout = (LoadingLayout) viewDataBinding;
        setContentView(parentView);
    }

    @Override
    public int getContentView() {
        return R.layout.dig_loading;
    }

    @Override
    public void onClick(int id, View v) {

    }

    @Override
    public void show() {
        loadingLayout.loadingView.start();
        super.show();
    }

    @Override
    public void dismiss() {
        loadingLayout.loadingView.cancel();
        super.dismiss();
    }
}

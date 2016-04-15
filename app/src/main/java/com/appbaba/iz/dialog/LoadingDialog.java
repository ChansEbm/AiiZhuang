package com.appbaba.iz.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;

import com.appbaba.iz.LoadingLayout;
import com.appbaba.iz.R;

/**
 * Created by ChanZeeBm on 2015/10/16.
 */
public class LoadingDialog extends Dialog {
    LoadingLayout loadingLayout;

    public LoadingDialog(Context context) {
        super(context, R.style.dialogDefaultStyle);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
        loadingLayout = DataBindingUtil.inflate(getLayoutInflater(), R.layout
                .dig_loading, null, false);
        setContentView(loadingLayout.getRoot());
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

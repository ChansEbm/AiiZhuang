package com.appbaba.iz.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.appbaba.iz.R;

/**
 * Created by Administrator on 2016/4/21.
 */
public abstract class BaseDialog extends Dialog implements View.OnClickListener {
    protected View parentView;
    protected ViewDataBinding viewDataBinding;

    public BaseDialog(Context context) {
        super(context, R.style.dialogDefaultStyle);
        viewDataBinding = DataBindingUtil.inflate(getLayoutInflater(), getContentView(), null,
                false);
        parentView = viewDataBinding.getRoot();
    }

    public abstract int getContentView();

    @Override
    public void onClick(View v) {
        onClick(v.getId(), v);
    }

    public abstract void onClick(int id, View v);
}

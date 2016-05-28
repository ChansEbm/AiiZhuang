package com.appbaba.platform.base;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.appbaba.platform.R;
import com.appbaba.platform.impl.OkHttpResponseListener;

/**
 * Created by ruby on 2016/5/4.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    protected ViewDataBinding viewDataBinding;
    protected View parentView;
    private boolean isFirstRunnable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getContentView()!=0)
        {
            viewDataBinding = DataBindingUtil.setContentView(this,getContentView());
            parentView = viewDataBinding.getRoot();
        }
        else
        {
            throw new IllegalStateException("not invoke setContentView");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            parentView.setFitsSystemWindows(true);
        }
        InitView();
        InitData();
        InitEvent();
        InitListening();
    }

    @Override
    public void onClick(View v) {
        OnClick(v.getId(),v);
    }

    protected   void  StartActivity(Class cls)
    {
        startActivity(new Intent(this,cls));
    }

    protected abstract void InitView(); //view 初始
    protected abstract void InitData(); //加载数据
    protected abstract void InitEvent(); //加载方法
    protected abstract void InitListening(); //加载监听
    protected abstract void OnClick(int id,View view);

    protected abstract int getContentView();

}

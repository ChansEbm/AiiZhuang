package com.appbaba.platform.base;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.appbaba.platform.R;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.OkHttpResponseListener;
import com.appbaba.platform.model.NetworkModel;

import java.util.List;

/**
 * Created by ruby on 2016/5/4.
 */
public abstract class BaseActivity<E extends BaseBean> extends AppCompatActivity implements View.OnClickListener,OkHttpResponseListener{
    protected ViewDataBinding viewDataBinding;
    protected View parentView;
    protected NetworkModel networkModel;
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
            //parentView.setFitsSystemWindows(true);
        }
        networkModel = new NetworkModel(this);
        networkModel.setResultCallBack(this);
        InitView();
        InitData();
        InitEvent();
        InitListening();
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        OnClick(v.getId(),v);
    }

    protected   void  StartActivity(Class cls)
    {
        startActivity(new Intent(this,cls));
    }

    @Override
    public void onJsonObjectResponse(Object o, NetworkParams paramsCode) {
          try
          {
              E e = (E)o;
              if( e.getErrorcode()!=0)
              {
                  Toast.makeText(this,e.getMsg(),Toast.LENGTH_LONG).show();
              }
              onJsonObjectSuccess((E)o,paramsCode);
          }
          catch (Exception ex)
          {
              Toast.makeText(this,"操作失败",Toast.LENGTH_LONG).show();
              ex.printStackTrace();
          }
    }

    @Override
    public void onJsonArrayResponse(List t, NetworkParams paramsCode) {

    }

    @Override
    public void onError(String error, NetworkParams paramsCode) {
        Toast.makeText(this,"操作失败",Toast.LENGTH_LONG).show();
        Log.e("onError",error);
    }

    public void onJsonObjectSuccess(E e, NetworkParams paramsCode) {

    }

    protected abstract void InitView(); //view 初始
    protected abstract void InitData(); //加载数据
    protected abstract void InitEvent(); //加载方法
    protected abstract void InitListening(); //加载监听
    protected abstract void OnClick(int id,View view);

    protected abstract int getContentView();

}

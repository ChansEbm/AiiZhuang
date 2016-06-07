package com.appbaba.platform.base;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.OkHttpResponseListener;
import com.appbaba.platform.model.NetworkModel;
import com.appbaba.platform.tools.LogTools;

import java.util.List;

/**
 * Created by ruby on 2016/5/4.
 */
public abstract class BaseFragment<E extends BaseBean> extends Fragment implements View.OnClickListener,OkHttpResponseListener{
    protected ViewDataBinding viewDataBinding;
    protected View parentView;
    protected NetworkModel networkModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getContentView()!=0)
        {
            viewDataBinding = DataBindingUtil.inflate(inflater,getContentView(),container,false);
            parentView = viewDataBinding.getRoot();
        }
        else
        {
            throw new IllegalStateException("not invoke setContentView");
        }
        networkModel = new NetworkModel(getContext());
        networkModel.setResultCallBack(this);
        InitView();
        InitData();
        InitEvent();
        InitListening();
        return parentView;
    }

    @Override
    public void onClick(View v) {
        OnClick(v.getId(),v);
    }

    protected   void  StartActivity(Class cls)
    {
        startActivity(new Intent(getContext(),cls));
    }

    @Override
    public void onJsonObjectResponse(Object o, NetworkParams paramsCode) {
        try
        {
            onJsonObjectSuccess((E)o,paramsCode);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    @Override
    public void onJsonArrayResponse(List t, NetworkParams paramsCode) {

    }

    @Override
    public void onError(String error, NetworkParams paramsCode) {

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

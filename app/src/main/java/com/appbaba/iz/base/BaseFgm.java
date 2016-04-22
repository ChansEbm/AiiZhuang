package com.appbaba.iz.base;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appbaba.iz.AppKeyMap;
import com.appbaba.iz.R;
import com.appbaba.iz.adapters.CommonBinderAdapter;
import com.appbaba.iz.adapters.MultiAdapter;
import com.appbaba.iz.broadcast.UpdateUIBroadcast;
import com.appbaba.iz.entity.Base.BaseBean;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.impl.BinderOnItemClickListener;
import com.appbaba.iz.impl.OkHttpResponseListener;
import com.appbaba.iz.impl.UpdateUIListener;
import com.appbaba.iz.model.NetworkModel;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.tools.LogTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChanZeeBm on 2015/9/7.
 */
public abstract class BaseFgm<E, T> extends Fragment implements View.OnClickListener,
        BinderOnItemClickListener, OkHttpResponseListener<E>, UpdateUIListener {
    protected CommonBinderAdapter<T> commonBinderAdapter;
    protected MultiAdapter<T> multiAdapter;
    protected List<T> list = new ArrayList<>();
    protected ViewDataBinding viewDataBinding;
    protected NetworkModel networkModel;
    protected UpdateUIBroadcast uiBroadcast;
    protected View parentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiBroadcast = new UpdateUIBroadcast();
        uiBroadcast.setListener(this);
        AppTools.registerBroadcast(uiBroadcast, AppKeyMap.NO_NETWORK_ACTION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater, getContentView(), null, false);
        networkModel = new NetworkModel((AppCompatActivity) getActivity());
        networkModel.setResultCallBack(this);

        parentView = viewDataBinding.getRoot();

        initViews();
        initEvents();
        return viewDataBinding.getRoot();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    protected abstract void initViews();

    protected abstract void initEvents();

    protected abstract void noNetworkStatus();

    protected abstract void onClick(int id, View view);

    protected abstract int getContentView();


    @Override
    public void onClick(View v) {
        int id = v.getId();
        onClick(id, v);
    }

    protected void start(Bundle bundle, Class<?> targetClz) {
        start(new Intent().setClass(getActivity(), targetClz).putExtras(bundle));
    }

    protected void start(Class<?> targetClz, Integer... flags) {
        Intent intent = new Intent().setClass(getActivity(), targetClz);
        for (Integer flag : flags) {
            intent.addFlags(flag);
        }
        start(intent);
    }

    protected void start(Class<?> cls) {
        start(new Intent().setClass(getActivity(), cls));
    }

    private void start(Intent intent) {
        startActivity(intent);
    }

    protected View getViewById(int id) {
        return viewDataBinding.getRoot().findViewById(id);
    }

    @Override
    public void onBinderItemClick(View clickItem, int parentId, int pos) {

    }

    @Override
    public void onBinderItemLongClick(View clickItem, int parentId, int pos) {

    }

    @Override
    public void onError(String error, NetworkParams paramsCode) {
        LogTools.e("error response here");
        AppTools.showActionSnackBar(viewDataBinding.getRoot(), getString(R.string
                        .connect_server_error)
                , null, null);
    }

    @Override
    public void onJsonArrayResponse(List<E> t, NetworkParams paramsCode) {
        LogTools.w("jsonArray response here");
    }

    @Override
    public void onJsonObjectResponse(E t, NetworkParams paramsCode) {
        if (t instanceof BaseBean) {
            BaseBean baseBean = (BaseBean) t;
            final int errorCode = baseBean.getErrorcode();
            if (errorCode == 1) {
                LogTools.e("参数错误");
                showMsgSnackBar(baseBean.getMsg());
            } else {
                //showMsgSnackBar(baseBean.getMsg());
                if (errorCode == 0) {
                    onJsonObjectSuccess(t, paramsCode);
                }
                else
                {
                    showMsgSnackBar(baseBean.getMsg());
                }
            }
        }
    }

    public void onJsonObjectSuccess(E t, NetworkParams paramsCode) {

    }

    private void showMsgSnackBar(String msg) {
        AppTools.showNormalSnackBar(viewDataBinding.getRoot(), msg);
    }

    @Override
    public void uiUpData(Intent intent) {
        if (intent.getAction().equals(AppKeyMap.NO_NETWORK_ACTION)) {
            LogTools.w("no net work");
            noNetworkStatus();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

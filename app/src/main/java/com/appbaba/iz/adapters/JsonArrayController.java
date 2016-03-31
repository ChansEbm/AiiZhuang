package com.appbaba.iz.adapters;


import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.impl.OkHttpResponseListener;

/**
 * Created by ChanZeeBm on 2015/12/4.
 */
public abstract class JsonArrayController<T> implements OkHttpResponseListener<T> {

    @Override
    public void onJsonObjectResponse(T t, NetworkParams paramsCode) {

    }
}

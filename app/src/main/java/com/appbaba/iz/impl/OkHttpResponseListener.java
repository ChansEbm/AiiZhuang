package com.appbaba.iz.impl;

import com.appbaba.iz.eum.NetworkParams;

import java.util.List;

/**
 * Created by Administrator on 12/3/2015.
 */
public interface OkHttpResponseListener<T> {
    void onJsonObjectResponse(T t, NetworkParams paramsCode);

    void onJsonArrayResponse(List<T> t, NetworkParams paramsCode);

    void onError(String error, NetworkParams paramsCode);
}

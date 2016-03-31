package com.appbaba.iz.adapters;


import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.impl.OkHttpResponseListener;

import java.util.List;

/**
 * Created by ChanZeeBm on 2015/12/4.
 */
public abstract class JsonObjectController<T> implements OkHttpResponseListener<T> {

    @Override
    public void onJsonArrayResponse(List<T> t, NetworkParams paramsCode) {

    }

}

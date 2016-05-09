package com.appbaba.platform.base;

import android.app.Application;

import com.appbaba.platform.method.MethodConfig;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by ruby on 2016/5/4.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MethodConfig.context = this;
        Fresco.initialize(this);
    }
}

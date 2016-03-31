package com.appbaba.iz.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.appbaba.iz.impl.UpdateUIListener;

/**
 * Created by ChanZeeBm on 2015/10/17.
 */
public class UpdateUIBroadcast extends BroadcastReceiver {
    private UpdateUIListener listener;

    public void setListener(UpdateUIListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (listener != null) {
            listener.uiUpData(intent);
        }
    }
}

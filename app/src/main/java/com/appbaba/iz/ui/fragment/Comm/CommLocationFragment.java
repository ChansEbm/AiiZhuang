package com.appbaba.iz.ui.fragment.Comm;

import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;

import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.entity.LocationBean;
import com.appbaba.iz.eum.JsonType;
import com.appbaba.iz.tools.LogTools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonToken;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Created by ruby on 2016/4/18.
 */
public class CommLocationFragment extends BaseFgm {
    @Override
    protected void initViews() {

    }

    public  void  ReadFile()
    {
        try{
           InputStream inputStream = getResources().getAssets().open("location.txt");
            Gson gson = new Gson();
            int count = 0;
            byte[] buffer = new byte[1024];
            String data = "";
            while ((count = inputStream.read(buffer,0,1024))>0)
            {
                data +=(new String(buffer,0,count));
            }
            List<LocationBean> list = gson.fromJson(data, new TypeToken<List<LocationBean>>(){}.getType());
            LogTools.e(list.get(0));

        }catch (Exception ex)
        {

        }
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return 0;
    }
}

package com.appbaba.iz.ui.fragment.Comm;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.appbaba.iz.FragmentCommLocationBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.adapters.CommonAdapter;
import com.appbaba.iz.adapters.CommonBinderAdapter;
import com.appbaba.iz.adapters.CommonBinderHolder;
import com.appbaba.iz.adapters.ViewHolder;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.entity.LocationBean;
import com.appbaba.iz.entity.Login.UpdatePersonBean;
import com.appbaba.iz.eum.JsonType;
import com.appbaba.iz.tools.LogTools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonToken;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ruby on 2016/4/18.
 */
public class CommLocationFragment extends BaseFgm {

    private FragmentCommLocationBinding commLocationBinding;
    private CommonAdapter<Object> adapter;
    private List<Object> list;

    private  int which=0,province=-1,city=-1,area=-1;
    private LocationBean locationBean;


    @Override
    protected void initViews() {

        commLocationBinding = (FragmentCommLocationBinding)viewDataBinding;
        commLocationBinding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        commLocationBinding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.more_arrow_dark_left);
        commLocationBinding.includeTopTitle.title.setTextColor(Color.BLACK);

        list = new ArrayList<>();
        adapter = new CommonAdapter<Object>(list,getContext(),R.layout.item_comm_ltv_view){
            @Override
            public void convert(int position, ViewHolder holder, Object o) {

                holder.setTextColor(R.id.tv_item_view, android.R.color.black);
                switch (which)
                {
                    case 0: {
                        LocationBean.DataEntity entity = (LocationBean.DataEntity) o;
                        holder.setText(R.id.tv_item_view,entity.getName());
                        if(province>=0 && entity.getId()==locationBean.getData().get(province).getId())
                        {
                            holder.setTextColor(R.id.tv_item_view, android.R.color.holo_red_light);
                        }
                    }
                        break;
                    case 1: {
                        LocationBean.DataEntity.CityEntity entity = (LocationBean.DataEntity.CityEntity) o;
                        holder.setText(R.id.tv_item_view,entity.getName());
                        if(city>=0 && entity.getId()==locationBean.getData().get(province).getCity().get(city).getId())
                        {
                            holder.setTextColor(R.id.tv_item_view, android.R.color.holo_red_light);
                        }
                    }
                        break;
                    case 2: {
                        LocationBean.DataEntity.CityEntity.ZoneEntity entity = (LocationBean.DataEntity.CityEntity.ZoneEntity) o;
                        holder.setText(R.id.tv_item_view,entity.getName());
                    }
                        break;
                }

            }
        };

        commLocationBinding.ltvData.setAdapter(adapter);

//        Timer timer = new Timer();
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
                ReadFile();
//            }
//        };
//        timer.schedule(timerTask,0);

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
           locationBean = gson.fromJson(data, new TypeToken<LocationBean>(){}.getType());
            UpdateListData();
        }catch (Exception ex)
        {

        }
    }

    public void UpdateListData()
    {
        list.clear();
        if(which==0)
        {
            list.addAll(locationBean.getData());
        }
        if(which==1)
        {
            commLocationBinding.includeTopTitle.title.setText(locationBean.getData().get(province).getName());
            list.addAll(locationBean.getData().get(province).getCity());
        }
        if(which==2)
        {
            commLocationBinding.includeTopTitle.title.setText(locationBean.getData().get(province).getCity().get(city).getName());
            list.addAll(locationBean.getData().get(province).getCity().get(city).getZone());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void initEvents() {
        commLocationBinding.ltvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (which)
                {
                    case 0:
                        which=1;
                        province = position;
                        city = -1;
                        commLocationBinding.includeTopTitle.title.setText(locationBean.getData().get(position).getName());
                        UpdateListData();
                        break;
                    case 1:
                        which=2;
                        city = position;
                        area = -1;
                        if(locationBean.getData().get(province).getCity().get(city).getZone()!=null && locationBean.getData().get(province).getCity().get(city).getZone().size()>0)
                        {
                            commLocationBinding.includeTopTitle.title.setText(locationBean.getData().get(province).getCity().get(position).getName());
                            UpdateListData();
                        }
                        else
                        {
                            Intent intent = new Intent();
                            intent.putExtra("area_id",locationBean.getData().get(province).getCity().get(city).getId());
                            intent.putExtra("area_name",locationBean.getData().get(province).getCity().get(city).getName());
                            ((Activity)getContext()).setResult(100,intent);
                            ((Activity)getContext()).finish();
                        }
                        break;
                    case 2:
                        area = position;
                        Intent intent = new Intent();
                        intent.putExtra("area_id",locationBean.getData().get(province).getCity().get(city).getZone().get(area).getId());
                        intent.putExtra("area_name",locationBean.getData().get(province).getCity().get(city).getZone().get(area).getName());
                        ((Activity)getContext()).setResult(100,intent);
                        ((Activity)getContext()).finish();
                        break;
                }

            }
        });
        commLocationBinding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(which==0)
                {
                    ((Activity)getContext()).finish();
                }
                else
                {
                    which--;
                    UpdateListData();
                }
            }
        });

    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_comm_location;
    }
}

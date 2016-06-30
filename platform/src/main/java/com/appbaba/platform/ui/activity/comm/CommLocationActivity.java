package com.appbaba.platform.ui.activity.comm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.appbaba.platform.ActivityCommLocationBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonAdapter;
import com.appbaba.platform.adapters.ViewHolder;
import com.appbaba.platform.base.BaseActivity;
import com.appbaba.platform.entity.comm.LocationBean;
import com.appbaba.platform.method.MethodConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/6/7.
 */
public class CommLocationActivity extends BaseActivity {

    private ActivityCommLocationBinding binding;
    private CommonAdapter<Object> adapter;
    private LocationBean locationBean;
    private List<Object> list;
    private int which = 0;
    private int province = -1;

    @Override
    protected void InitView() {
        binding = (ActivityCommLocationBinding)viewDataBinding;
        binding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        binding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.icon_back);
        binding.includeTopTitle.title.setTextColor(Color.BLACK);
    }

    @Override
    protected void InitData() {
        list = new ArrayList<>();
        adapter = new CommonAdapter<Object>(list,this,R.layout.item_comm_ltv_view) {
            @Override
            public void convert(int position, ViewHolder holder, Object s) {
                switch (which)
                {
                    case 0: {
                        LocationBean.DataEntity dataEntity = (LocationBean.DataEntity)s;
                        holder.setText(R.id.tv_item_view, dataEntity.getName());
                        break;
                    }
                    case 1:
                        LocationBean.DataEntity.CityEntity cityEntity = (LocationBean.DataEntity.CityEntity)s;
                        holder.setText(R.id.tv_item_view,cityEntity.getName());
                        break;
                }
            }
        };
        binding.ltvData.setAdapter(adapter);
        if(MethodConfig.locationBean==null)
        {
            ReadFile();
        }
        else
        {
            locationBean = MethodConfig.locationBean;
            UpdateListData();
        }
    }

    public  void  ReadFile()
    {
        try{
            InputStream inputStream = getResources().getAssets().open("area.json");
            Gson gson = new Gson();
            int count = 0;
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream buff = new ByteArrayOutputStream();
            while ((count = inputStream.read(buffer,0,1024))>0)
            {
                buff.write(buffer,0,count);
            }
            String data  =  new String(buff.toByteArray(),0,buff.size(),"utf-8");
            MethodConfig.locationBean = locationBean = gson.fromJson(data, new TypeToken<LocationBean>(){}.getType());
            UpdateListData();
        }catch (Exception ex)
        {
          ex.printStackTrace();
        }
    }

    public void UpdateListData()
    {
        list.clear();
        if(which==0)
        {
            binding.includeTopTitle.title.setText("选择地区");
            list.addAll(locationBean.getData());
        }
        if(which==1)
        {
            binding.includeTopTitle.title.setText(locationBean.getData().get(province).getName());
            list.addAll(locationBean.getData().get(province).getCity());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void InitEvent() {

        binding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(which==0)
                {
                    finish();
                }
                else
                {
                    which--;
                    UpdateListData();
                }
            }
        });
        binding.ltvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (which)
                {
                    case 0:
                        which++;
                        province = position;
                        UpdateListData();
                        break;
                    case 1:
                        Intent intent = new Intent();
                        intent.putExtra("city",locationBean.getData().get(province).getCity().get(position).getName());
                        setResult(101,intent);
                        finish();
                        break;
                }
            }
        });
    }

    @Override
    protected void InitListening() {

    }

    @Override
    protected void OnClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_comm_location;
    }
}

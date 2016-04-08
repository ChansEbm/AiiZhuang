package com.appbaba.iz.ui.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.appbaba.iz.FragmentHomeTopNearbyBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseFgm;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

/**
 * Created by ruby on 2016/4/5.
 */
public class HomeItemNearbyFragment extends BaseFgm implements BaiduMap.OnMarkerClickListener{
    private FragmentHomeTopNearbyBinding nearbyBinding;
    private MapView mapView;
    private BaiduMap mBaiduMap;
    private  InfoWindow infoWindow;

    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();


    boolean isFirstLoc = true; // 是否首次定位

    @Override
    protected void initViews() {
        nearbyBinding = (FragmentHomeTopNearbyBinding)viewDataBinding;
        nearbyBinding.includeTopSimpleTitle.tvTopSimpleTitle.setText(R.string.popup_mendian);
        mapView = nearbyBinding.mapView;
        mBaiduMap = mapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setOnMarkerClickListener(this);

        // 定位初始化
        mLocClient = new LocationClient(getContext());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();


        AddIcon(39.963175,116.400244);
    }

    public void  AddIcon(double lat,double lng)
    {
        //定义Maker坐标点
        LatLng point = new LatLng(lat,lng );
//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_map_mendian);
//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap).title("这是一个测试");
        //在地图上添加Marker，并显示

        mBaiduMap.addOverlay(option);
        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(point);
        mBaiduMap.animateMapStatus(update);
    }


    @Override
    public void onPause() {
        super.onPause();
        if(mBaiduMap!=null) {
            // 当不需要定位图层时关闭定位图层
            mBaiduMap.setMyLocationEnabled(false);
            mapView.onPause();

        }
    }

    @Override
    public void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;
        super.onDestroy();
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
        return R.layout.fragment_home_item_nearby;
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        Button button = new Button(getContext());
        button.setBackgroundResource(R.drawable.icon_pop_bg);
        button.setText(marker.getTitle());
        InfoWindow.OnInfoWindowClickListener listener = new InfoWindow.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick() {
                Toast.makeText(getContext(),"你点击了"+marker.getTitle(),Toast.LENGTH_LONG).show();
                mBaiduMap.hideInfoWindow();
            }
        };
        infoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button),marker.getPosition(),-80,listener);
        mBaiduMap.showInfoWindow(infoWindow);
        return true;
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                AddIcon(location.getLatitude(), location.getLongitude());
            }

        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

}

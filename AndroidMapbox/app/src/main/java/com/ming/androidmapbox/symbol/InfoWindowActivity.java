package com.ming.androidmapbox.symbol;

import android.graphics.PointF;

import androidx.annotation.NonNull;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.ming.androidmapbox.MapActivity;
import com.ming.androidmapbox.symbol.bean.InfoBean;
import com.ming.androidmapbox.symbol.manager.InfoWindowManager;

import java.util.ArrayList;
import java.util.List;

public class InfoWindowActivity extends MapActivity implements MapboxMap.OnMapClickListener {
    private InfoWindowManager infoWindowManager;

    @Override
    public void onMapLoaded() {
        super.onMapLoaded();
        //添加点击地图监听
        mapboxMap.addOnMapClickListener(InfoWindowActivity.this);
        //初始化infoWindowManager
        infoWindowManager = new InfoWindowManager(mapboxMap, style);
        //添加测试数据
        List<InfoBean> infoBeanList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            InfoBean infoBean = new InfoBean();
            infoBean.setLongitude(-122.90480 + 0.001 * i);
            infoBean.setLatitude(47.03676 + 0.001 * i);
            infoBean.setName("testName" + i);
            infoBean.setCapital("testCapital" + i);
            infoBeanList.add(infoBean);
        }
        //添加显示到地图上
        infoWindowManager.addMarkersToMap(infoBeanList);
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        //处理点击地图事件
        if (infoWindowManager != null) {
            //返回对应于地理坐标（LatLng）的屏幕位置。屏幕位置是相对于左上角的屏幕像素（非显示像素）地图的（不是整个屏幕)
            PointF pointF = mapboxMap.getProjection().toScreenLocation(point);
            return infoWindowManager.handleClickIcon(pointF);
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        //移除点击地图监听
        if (mapboxMap != null) {
            mapboxMap.removeOnMapClickListener(this);
        }
        super.onDestroy();
    }
}

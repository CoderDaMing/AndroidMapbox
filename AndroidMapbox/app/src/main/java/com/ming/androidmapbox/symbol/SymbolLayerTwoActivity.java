package com.ming.androidmapbox.symbol;

import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.ming.androidmapbox.MapActivity;
import com.ming.androidmapbox.R;
import com.ming.androidmapbox.symbol.bean.CustomBean;
import com.ming.androidmapbox.symbol.manager.MoreSymbolLayer;

import java.util.ArrayList;
import java.util.List;

public class SymbolLayerTwoActivity extends MapActivity implements View.OnClickListener {
    private Button btn_add;
    private Button btn_change;
    private Button btn_delete;

    private double latitude = 39.9;
    private double longitude = 119.6;
    private MoreSymbolLayer moreSymbolLayer;

    @Override
    public int getLayoutId() {
        return R.layout.activity_symbollayer_two;
    }

    @Override
    public void onMapLoaded() {
        super.onMapLoaded();
        btn_add = findViewById(R.id.btn_add);
        btn_change = findViewById(R.id.btn_change);
        btn_delete = findViewById(R.id.btn_delete);
        Toast.makeText(this, "地图初始化完毕", Toast.LENGTH_SHORT).show();
        moreSymbolLayer = new MoreSymbolLayer(this, mapboxMap, style);
        //添加地图点击监听
        mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
            @Override
            public boolean onMapClick(@NonNull LatLng point) {
                if (moreSymbolLayer != null) {
                    return moreSymbolLayer.handleClickLayer(point);
                }
                return false;
            }
        });
        //添加按钮监听
        btn_add.setOnClickListener(this);
        btn_change.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                if (moreSymbolLayer != null) {
                    List<CustomBean> customBeans = new ArrayList<>();
                    customBeans.add(new CustomBean(1, latitude, longitude));
                    customBeans.add(new CustomBean(2, latitude, longitude + 0.01));
                    customBeans.add(new CustomBean(3, latitude, longitude - 0.01));
                    moreSymbolLayer.addMoreToMap(customBeans);
                }
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(latitude, longitude))
                        .zoom(12)
                        .tilt(0)
                        .bearing(0)
                        .build();
                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                mapboxMap.animateCamera(cameraUpdate);
                break;

            case R.id.btn_change:
                if (moreSymbolLayer != null) {
                    moreSymbolLayer.changeMapBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.mapbox_logo_icon));
                }
                break;

            case R.id.btn_delete:
                if (moreSymbolLayer != null) {
                    moreSymbolLayer.removeAllLayers();
                }
                break;

            default:
                break;
        }
    }
}

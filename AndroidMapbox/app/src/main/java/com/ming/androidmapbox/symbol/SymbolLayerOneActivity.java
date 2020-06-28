package com.ming.androidmapbox.symbol;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.ming.androidmapbox.MapActivity;
import com.ming.androidmapbox.R;
import com.ming.androidmapbox.symbol.manager.SingleSymbolLayer;

import java.util.Random;

public class SymbolLayerOneActivity extends MapActivity  implements View.OnClickListener {
    private Button btn_add;
    private Button btn_change;
    private Button btn_delete;

    private double latitude = 39.9;
    private double longitude = 119.6;
    private SingleSymbolLayer singleSymbolLayer;

    @Override
    public int getLayoutId() {
        return R.layout.activity_symbollayer_one;
    }

    @Override
    public void onMapLoaded() {
        super.onMapLoaded();
        btn_add = findViewById(R.id.btn_add);
        btn_change = findViewById(R.id.btn_change);
        btn_delete = findViewById(R.id.btn_delete);

        Toast.makeText(this, "地图初始化完毕", Toast.LENGTH_SHORT).show();
        //添加监听
        btn_add.setOnClickListener(this);
        btn_change.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                if (singleSymbolLayer == null) {
                    //添加一个锁图片到地图
                    singleSymbolLayer = new SingleSymbolLayer.Builder().setStyle(style)
                            .setImage(getResources().getDrawable(R.drawable.mapbox_marker_icon_default))
                            .setLat(latitude)
                            .setLng(longitude)
                            .build();
                    //移动到位置
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(latitude, longitude))
//                            .zoom(12)
                            .tilt(0)
                            .bearing(0)
                            .build();
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                    mapboxMap.animateCamera(cameraUpdate);
                }
                break;

            case R.id.btn_change:
                if (singleSymbolLayer != null) {
                    double randomValue = new Random().nextDouble();

                    double changeLongitude = latitude + randomValue;

                    singleSymbolLayer.updatePosition(latitude, changeLongitude);
                }
                break;

            case R.id.btn_delete:
                if (singleSymbolLayer != null) {
                    singleSymbolLayer.deleteSelf();
                    singleSymbolLayer = null;
                }
                break;

            default:
                break;
        }
    }
}

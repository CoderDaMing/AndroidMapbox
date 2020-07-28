package com.ming.androidmapbox.symbol;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.content.ContextCompat;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.ming.androidmapbox.MapActivity;
import com.ming.androidmapbox.R;
import com.ming.androidmapbox.util.ToastUtil;

public class LocationComponentActivity extends MapActivity {
    @Override
    public void onMapLoaded() {
        super.onMapLoaded();
        initLocationComponent(mapboxMap, style);
    }

    /**
     * Initialize the Maps SDK's LocationComponent
     */
    private void initLocationComponent(MapboxMap mapboxMap, Style loadedMapStyle) {
        // Get an instance of the component
        LocationComponent locationComponent = mapboxMap.getLocationComponent();
        // Check if permissions are enabled and if not request
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            /*定制在LocationComponent允许一些自定义。您可以设置drawable，不透明度，颜色等。*/
            LocationComponentOptions customLocationComponentOptions = LocationComponentOptions.builder(this)
                    .accuracyColor(Color.TRANSPARENT)
                    .foregroundDrawable(R.drawable.mapbox_user_icon)
                    .foregroundDrawableStale(R.drawable.mapbox_user_icon_stale)
                    .bearingDrawable(R.drawable.mapbox_user_bearing_icon)
                    .build();
            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(this, loadedMapStyle)
                            .locationComponentOptions(customLocationComponentOptions)
                            .build();
            locationComponent.activateLocationComponent(locationComponentActivationOptions);
            locationComponent.setLocationComponentEnabled(true);
            /* CameraMode	描述
             * NONE	没有摄像头跟踪。
             * NONE_COMPASS	相机不跟踪位置，但会跟踪罗盘方位。
             * NONE_GPS	相机不会跟踪位置，但会跟踪GPS Location方位。
             * TRACKING	摄像机跟踪设备位置，不考虑轴承。
             * TRACKING_COMPASS	摄像机跟踪设备位置，跟踪设备指南针提供的轴承。
             * TRACKING_GPS	摄像机跟踪设备位置，轴承由标准化提供Location#getBearing()。
             * TRACKING_GPS_NORTH	摄像机跟踪设备位置，轴承始终设置为北（0）。
             */
            locationComponent.setCameraMode(CameraMode.TRACKING);

            /*
             * RenderMode类包含所述装置位置图像预设选项
             * NORMAL	此模式显示设备位置，忽略指南针和GPS方位（无箭头渲染）
             * COMPASS	此模式显示设备位置，以及考虑设备指南针的箭头。
             * GPS	此模式显示设备位置，图标轴承从Location提供的更新更LocationComponent。
             *
             */
            locationComponent.setRenderMode(RenderMode.COMPASS);
            //  强制设定位置
            LatLng target = mapboxMap.getCameraPosition().target;
            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setLatitude(target.getLatitude());
            location.setLongitude(target.getLongitude());
            locationComponent.forceLocationUpdate(location);
        }else {
            ToastUtil.show("没有位置权限");
        }
    }
}

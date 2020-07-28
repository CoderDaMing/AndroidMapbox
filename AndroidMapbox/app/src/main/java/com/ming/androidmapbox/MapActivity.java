package com.ming.androidmapbox;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

/**
 * Mapbox样式的根级别属性指定地图的图层，图块源和其他资源，以及在其他地方未指定时初始摄像机位置的默认值。
 * <p>
 * {
 * "version": 8,
 * "name": "Mapbox Streets",
 * "sprite": "mapbox://sprites/mapbox/streets-v8",
 * "glyphs": "mapbox://fonts/mapbox/{fontstack}/{range}.pbf",
 * "sources": {...},
 * "layers": [...]
 * }
 */
public abstract class MapActivity extends AppCompatActivity {
    public MapView mapView;
    public MapboxMap mapboxMap;
    public Style style;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(getLayoutId());

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.addOnDidFailLoadingMapListener(new MapView.OnDidFailLoadingMapListener() {
            @Override
            public void onDidFailLoadingMap(String errorMessage) {
                Toast.makeText(MapActivity.this, "无法加载样式或设置了无效的样式URL，则地图视图将变为空白。", Toast.LENGTH_SHORT).show();
            }
        });
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                //是否显示地图调试信息
                mapboxMap.setDebugActive(false);
                MapActivity.this.mapboxMap = mapboxMap;
                //加载新的地图样式
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments.
                        MapActivity.this.style = style;
                        onMapLoaded();
                    }
                });
            }
        });
    }

    public int getLayoutId() {
        return R.layout.activity_map;
    }

    public void onMapLoaded() {

    }

    public boolean isMapEnable() {
        return mapboxMap != null && style != null && style.isFullyLoaded();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
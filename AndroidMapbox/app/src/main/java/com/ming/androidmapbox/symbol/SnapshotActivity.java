package com.ming.androidmapbox.symbol;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.snapshotter.MapSnapshot;
import com.mapbox.mapboxsdk.snapshotter.MapSnapshotter;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.ming.androidmapbox.MapActivity;
import com.ming.androidmapbox.R;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAnchor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

public class SnapshotActivity extends MapActivity {
    private final static String GEO_SOURCE_ID = "geo_source_id";
    private final static String LAYER_ID = "layer_id";
    private final static String SINGLE_IMAGE_ID = "single_image_id";

    private ImageView iv_snapshot;
    private Button btn_clear;
    private Button buttonOld;
    private Button buttonNew;

    private MapSnapshotter mapSnapshotter;
    private boolean hasStartedSnapshotGeneration;

    @Override
    public int getLayoutId() {
        return R.layout.activity_snapshot;
    }

    @Override
    public void onMapLoaded() {
        super.onMapLoaded();
        hasStartedSnapshotGeneration = false;

        iv_snapshot = findViewById(R.id.iv_snapshot);
        btn_clear = findViewById(R.id.btn_clear);
        buttonOld = findViewById(R.id.btn_old);
        buttonNew = findViewById(R.id.btn_new);

        //添加一个layer
        addSingleLayer(style, mapboxMap);

        //当用户点击按钮时,清除截图
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasStartedSnapshotGeneration) {
                    if (iv_snapshot != null) {
                        iv_snapshot.setImageDrawable(new ColorDrawable(Color.TRANSPARENT));
                    }
                }
            }
        });
        //当用户点击按钮时,拍摄地图快照
        buttonOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasStartedSnapshotGeneration) {
                    hasStartedSnapshotGeneration = true;
                    Toast.makeText(SnapshotActivity.this, "loading old api...", Toast.LENGTH_LONG).show();
                    startOldSnapShot();
                }
            }
        });
        //当用户单击按钮时，使用给定的参数开始快照过程
        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasStartedSnapshotGeneration) {
                    hasStartedSnapshotGeneration = true;
                    Toast.makeText(SnapshotActivity.this, "loading new api...", Toast.LENGTH_LONG).show();
                    startNewSnapShot(
                            mapboxMap.getProjection().getVisibleRegion().latLngBounds,
                            mapView.getMeasuredHeight(),
                            mapView.getMeasuredWidth());
                }
            }
        });
    }

    private void addSingleLayer(@NonNull Style style, @NonNull MapboxMap mapboxMap) {
        GeoJsonSource geoJsonSource = new GeoJsonSource(GEO_SOURCE_ID);
        LatLng target = mapboxMap.getCameraPosition().target;
        geoJsonSource.setGeoJson(Point.fromLngLat(target.getLongitude(), target.getLatitude()));
        style.addSource(geoJsonSource);
        style.addImage(SINGLE_IMAGE_ID, getResources().getDrawable(R.drawable.mapbox_marker_icon_default));
        style.addLayer(new SymbolLayer(LAYER_ID, GEO_SOURCE_ID)
                .withProperties(
                        iconImage(SINGLE_IMAGE_ID),
                        iconAnchor(Property.ICON_ANCHOR_BOTTOM),
                        iconIgnorePlacement(true),
                        iconAllowOverlap(true)
                ));
    }

    /**
     * 拍摄地图快照
     */
    private void startOldSnapShot() {
        mapboxMap.snapshot(new MapboxMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(@NonNull Bitmap snapshot) {
                if (iv_snapshot != null) {
                    iv_snapshot.setImageBitmap(snapshot);
                }
                hasStartedSnapshotGeneration = false;
            }
        });
    }

    /**
     * 根据给定的参数创建位图，并使用该位图创建通知
     *
     * @param latLngBounds of map
     * @param height       of map
     * @param width        of map
     */
    private void startNewSnapShot(LatLngBounds latLngBounds, int height, int width) {
        mapboxMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                if (mapSnapshotter == null) {
                    //使用地图尺寸和给定范围初始化快照程序
                    MapSnapshotter.Options options =
                            new MapSnapshotter.Options(width, height)
                                    .withRegion(latLngBounds)
                                    .withCameraPosition(mapboxMap.getCameraPosition())
                                    .withStyle(style.getUri());

                    mapSnapshotter = new MapSnapshotter(SnapshotActivity.this, options);
                } else {
                    //重复使用现有的MapSnapshotter实例
                    mapSnapshotter.setSize(width, height);
                    mapSnapshotter.setRegion(latLngBounds);
                    mapSnapshotter.setCameraPosition(mapboxMap.getCameraPosition());
                }

                mapSnapshotter.start(new MapSnapshotter.SnapshotReadyCallback() {
                    @Override
                    public void onSnapshotReady(MapSnapshot snapshot) {
                        Bitmap bitmapOfMapSnapshotImage = snapshot.getBitmap();
                        if (iv_snapshot != null) {
                            iv_snapshot.setImageBitmap(bitmapOfMapSnapshotImage);
                        }
                        hasStartedSnapshotGeneration = false;
                    }
                });
            }
        });
    }
}

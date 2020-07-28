package com.ming.androidmapbox.layers;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.CircleLayer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.layers.TransitionOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.ming.androidmapbox.R;

import java.net.URI;
import java.net.URISyntaxException;

import static com.mapbox.mapboxsdk.style.expressions.Expression.all;
import static com.mapbox.mapboxsdk.style.expressions.Expression.division;
import static com.mapbox.mapboxsdk.style.expressions.Expression.exponential;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.gte;
import static com.mapbox.mapboxsdk.style.expressions.Expression.has;
import static com.mapbox.mapboxsdk.style.expressions.Expression.interpolate;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.lt;
import static com.mapbox.mapboxsdk.style.expressions.Expression.rgb;
import static com.mapbox.mapboxsdk.style.expressions.Expression.stop;
import static com.mapbox.mapboxsdk.style.expressions.Expression.toNumber;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleRadius;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textField;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textSize;

/**
 * Use GeoJSON and circle layers to visualize point data as circle clusters.
 */
public class CircleLayerClusteringActivity extends AppCompatActivity {
    private static final String URL = "https://www.mapbox.com/mapbox-gl-js/assets/earthquakes.geojson";
    private static final String SOURCE_ID = "earthquakes";
    private static final String UN_CLUSTERED_LAYER_ID = "unclustered-points";
    private static final String CROSS_ICON_ID = "cross-icon-id";
    private static final String POINT_COUNT = "point_count";
    private static final String COUNT = "count";
    private static final String MAG = "mag";
    private static final String CLUSTER_LAYER_ID_PRE = "cluster-";

    private static final int CLUSTER_MAX_ZOOM = 14;
    private static final int CLUSTER_RADIUS = 50;

    private MapView mapView;
    private MapboxMap mapboxMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        setContentView(R.layout.activity_circle_layer_clustering);

        mapView = findViewById(R.id.mapView);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap map) {

                mapboxMap = map;

                map.setStyle(Style.LIGHT, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        // 当图标在地图上碰撞时，禁用任何类型的衰落过渡。这增强了聚集在一起并分离的数据的视觉效果。
                        style.setTransition(new TransitionOptions(0, 0, false));

                        mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                                12.099, -79.045), 3));

                        addClusteredGeoJsonSource(style);

                        style.addImage(CROSS_ICON_ID, getResources().getDrawable(R.drawable.mapbox_marker_icon_default));

                        Toast.makeText(CircleLayerClusteringActivity.this, "缩放地图查看聚合效果",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void addClusteredGeoJsonSource(@NonNull Style loadedMapStyle) {

        //从GeoJSON数据中添加新来源，并将群集选项设置为true。
        try {
            loadedMapStyle.addSource(
                    // 指向GeoJSON数据。此示例显示了USGS地震灾害计划记录的从12/22/15到1/21/16的所有M1.0 +地震。
                    new GeoJsonSource(SOURCE_ID,
                            new URI(URL),
                            new GeoJsonOptions()
                                    .withCluster(true)
                                    .withClusterMaxZoom(CLUSTER_MAX_ZOOM)
                                    .withClusterRadius(CLUSTER_RADIUS)
                    )
            );
        } catch (URISyntaxException uriSyntaxException) {
//            Timber.e("Check the URL %s", uriSyntaxException.getMessage());
        }

        //为单个数据点创建标记层
        SymbolLayer unclustered = new SymbolLayer(UN_CLUSTERED_LAYER_ID, SOURCE_ID);

        unclustered.setProperties(
                iconImage(CROSS_ICON_ID),
                iconSize(
                        division(
                                get(MAG), literal(4.0f)
                        )
                ),
                iconColor(
                        interpolate(exponential(1), get(MAG),
                                stop(2.0, rgb(0, 255, 0)),
                                stop(4.5, rgb(0, 0, 255)),
                                stop(7.0, rgb(255, 0, 0))
                        )
                )
        );
        unclustered.setFilter(has(MAG));
        loadedMapStyle.addLayer(unclustered);

        // 使用地震GeoJSON源创建三个图层：每个群集类别一个图层。
        // 每个点范围将获得不同的填充颜色。
        int[][] layers = new int[][]{
                new int[]{150, ContextCompat.getColor(this, R.color.mapboxRed)},
                new int[]{20, ContextCompat.getColor(this, R.color.mapboxGreen)},
                new int[]{0, ContextCompat.getColor(this, R.color.mapbox_blue)}
        };

        for (int i = 0; i < layers.length; i++) {
            //添加集群的圈子
            CircleLayer circles = new CircleLayer(CLUSTER_LAYER_ID_PRE + i, SOURCE_ID);
            circles.setProperties(
                    circleColor(layers[i][1]),
                    circleRadius(18f)
            );

            Expression pointCount = toNumber(get(POINT_COUNT));

            // 将过滤器添加到群集层，以基于POINT_COUNT隐藏圆
            circles.setFilter(
                    i == 0
                            ? all(has(POINT_COUNT),
                            gte(pointCount, literal(layers[i][0]))
                    ) : all(has(POINT_COUNT),
                            gte(pointCount, literal(layers[i][0])),
                            lt(pointCount, literal(layers[i - 1][0]))
                    )
            );
            loadedMapStyle.addLayer(circles);
        }

        //添加计数标签
        SymbolLayer count = new SymbolLayer(COUNT, SOURCE_ID);
        count.setProperties(
                textField(Expression.toString(get(POINT_COUNT))),
                textSize(12f),
                textColor(Color.WHITE),
                textIgnorePlacement(true),
                textAllowOverlap(true)
        );
        loadedMapStyle.addLayer(count);
    }

    //region 生命周期
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
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
    //endregion
}

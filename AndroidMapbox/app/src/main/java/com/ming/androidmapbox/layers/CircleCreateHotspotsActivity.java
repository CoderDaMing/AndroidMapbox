package com.ming.androidmapbox.layers;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.CircleLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.ming.androidmapbox.R;

import java.net.URI;
import java.net.URISyntaxException;

import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.toNumber;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleBlur;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleRadius;

/**
 * 使用Mapbox GL群集将点数据可视化为热点。
 */
public class CircleCreateHotspotsActivity extends AppCompatActivity {
    private static final String URL = "https://www.mapbox.com/mapbox-gl-js/assets/earthquakes.geojson";
    private static final String SOURCE_ID = "earthquakes";
    private static final String UN_CLUSTERED_LAYER_ID = "unclustered-points";

    private static final String POINT_COUNT = "point_count";

    private static final String CLUSTER_LAYER_ID_PRE = "cluster-";

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        setContentView(R.layout.activity_style_create_hotspots_points);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.DARK, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        addClusteredGeoJsonSource(style);
                    }
                });
            }
        });
    }

    private void addClusteredGeoJsonSource(@NonNull Style loadedMapStyle) {

        //从我们的GeoJSON数据中添加一个新来源，并将'cluster'选项设置为true。
        try {
            loadedMapStyle.addSource(
                    // 指向GeoJSON数据。此示例显示了USGS地震灾害计划记录的从12/22/15到1/21/16的所有M1.0 +地震。
                    new GeoJsonSource(SOURCE_ID,
                            new URI(URL),
                            new GeoJsonOptions()
                                    .withCluster(true)
                                    .withClusterMaxZoom(15) // 最大缩放到聚类点
                                    .withClusterRadius(20) // 为热点外观使用较小的群集半径
                    )
            );
        } catch (URISyntaxException uriSyntaxException) {
//            Timber.e("Check the URL %s", uriSyntaxException.getMessage());
        }

        //使用地震源创建四层：每个聚类类别三层，未聚类点一层

        // 每个点范围将获得不同的填充颜色。
        final int[][] layers = new int[][]{
                new int[]{150, Color.parseColor("#E55E5E")},
                new int[]{20, Color.parseColor("#F9886C")},
                new int[]{0, Color.parseColor("#FBB03B")}
        };

        CircleLayer unclustered = new CircleLayer(UN_CLUSTERED_LAYER_ID, SOURCE_ID);
        unclustered.setProperties(
                circleColor(Color.parseColor("#FBB03B")),
                circleRadius(20f),
                circleBlur(1f));
        unclustered.setFilter(Expression.neq(get("cluster"), literal(true)));
        loadedMapStyle.addLayerBelow(unclustered, "building");

        for (int i = 0; i < layers.length; i++) {
            CircleLayer circles = new CircleLayer(CLUSTER_LAYER_ID_PRE + i, SOURCE_ID);
            circles.setProperties(
                    circleColor(layers[i][1]),
                    circleRadius(70f),
                    circleBlur(1f)
            );
            Expression pointCount = toNumber(get(POINT_COUNT));
            circles.setFilter(
                    i == 0
                            ? Expression.gte(pointCount, literal(layers[i][0])) :
                            Expression.all(
                                    Expression.gte(pointCount, literal(layers[i][0])),
                                    Expression.lt(pointCount, literal(layers[i - 1][0]))
                            )
            );
            loadedMapStyle.addLayerBelow(circles, "building");
        }
    }

    //region 生命周期
    @Override
    protected void onStart() {
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
    protected void onStop() {
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

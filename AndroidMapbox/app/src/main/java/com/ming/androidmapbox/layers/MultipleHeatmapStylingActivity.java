package com.ming.androidmapbox.layers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ming.androidmapbox.R;

import android.view.View;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.HeatmapLayer;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.net.URI;
import java.net.URISyntaxException;

import androidx.annotation.NonNull;

import static com.mapbox.mapboxsdk.style.expressions.Expression.heatmapDensity;
import static com.mapbox.mapboxsdk.style.expressions.Expression.interpolate;
import static com.mapbox.mapboxsdk.style.expressions.Expression.linear;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.rgb;
import static com.mapbox.mapboxsdk.style.expressions.Expression.rgba;
import static com.mapbox.mapboxsdk.style.expressions.Expression.zoom;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.heatmapColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.heatmapIntensity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.heatmapOpacity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.heatmapRadius;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.heatmapWeight;

public class MultipleHeatmapStylingActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private static final String HEATMAP_SOURCE_ID = "HEATMAP_SOURCE_ID";
    private static final String HEATMAP_LAYER_ID = "HEATMAP_LAYER_ID";
    private MapView mapView;
    private MapboxMap mapboxMap;
    private Expression[] listOfHeatmapColors;
    private Expression[] listOfHeatmapRadiusStops;
    private Float[] listOfHeatmapIntensityStops;
    private int index;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        index = 0;

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        setContentView(R.layout.activity_multiple_heatmap_styling);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        MultipleHeatmapStylingActivity.this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.LIGHT, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull final Style style) {
                CameraPosition cameraPositionForFragmentMap = new CameraPosition.Builder()
                        .target(new LatLng(34.056684, -118.254002))
                        .zoom(11.047)
                        .build();
                mapboxMap.animateCamera(
                        CameraUpdateFactory.newCameraPosition(cameraPositionForFragmentMap), 2600);
                try {
                    style.addSource(new GeoJsonSource(HEATMAP_SOURCE_ID, new URI("asset://la_heatmap_styling_points.geojson")));
                } catch (URISyntaxException exception) {
//                    Timber.d(exception);
                }
                initHeatmapColors();
                initHeatmapRadiusStops();
                initHeatmapIntensityStops();
                addHeatmapLayer(style);
                findViewById(R.id.btn_change_heatmap_style).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        index++;
                        if (index == listOfHeatmapColors.length - 1) {
                            index = 0;
                        }
                        Layer heatmapLayer = style.getLayer(HEATMAP_LAYER_ID);
                        if (heatmapLayer != null) {
                            heatmapLayer.setProperties(
                                    heatmapColor(listOfHeatmapColors[index]),
                                    heatmapRadius(listOfHeatmapRadiusStops[index]),
                                    heatmapIntensity(listOfHeatmapIntensityStops[index])
                            );
                        }
                    }
                });
            }
        });
    }

    private void addHeatmapLayer(@NonNull Style loadedMapStyle) {
        // 创建热图图层
        HeatmapLayer layer = new HeatmapLayer(HEATMAP_LAYER_ID, HEATMAP_SOURCE_ID);

        //热图图层在设置为最大缩放级别时消失
        layer.setMaxZoom(18);

        layer.setProperties(
                // 热图的色带。域是0（低）到1（高）,从0停止色开始以0透明色开始颜色渐变，以创建类似模糊的效果。
                /**
                 * 热图颜色.
                 *
                 * 默认为 ["interpolate",["linear"],["heatmap-density"],0,"rgba(0, 0, 255, 0)",0.1,"royalblue",0.3,"cyan",0.5,"lime",0.7,"yellow",1,"red"].
                 *
                 * 支持 interpolate expressions.
                 *
                 * 根据像素在热图中的密度值定义颜色。应该是使用["heatmap-density"]作为输入的表达式。
                 */
                heatmapColor(listOfHeatmapColors[index]),

                // 按缩放级别增加热图颜色权重权重,热图强度是热图权重的乘数
                /**
                 * 可选数字，大于或等于0。
                 * 默认为1。
                 * 支持 interpolate expressions .Transitionable.
                 * 与热图权重相似，但可全局控制热图的强度。主要用于根据缩放级别调整热图。
                 */
                heatmapIntensity(listOfHeatmapIntensityStops[index]),

                /**
                 * 热图不透明度
                 * 可选数字，大于或等于0。
                 * 默认为1。
                 * 支持 interpolate expressions. Transitionable.
                 * 将绘制热图图层的全局不透明度。
                 */
                heatmapOpacity(1f),

                /**
                 * 热图半径
                 * 可选数字，大于或等于1。
                 * 以像素为单位。默认为30。
                 * 支持 feature-state and interpolate expressions. Transitionable.
                 * 一个热图点的影响半径（以像素为单位）。值增加会使热图更平滑，但细节不那么丰富。
                 */
                heatmapRadius(listOfHeatmapRadiusStops[index]),
                /**
                 * 热图权重
                 * 可选数字，大于或等于0。
                 * 默认为1。
                 * 支持 feature-state 和 interpolate expressions.
                 * 衡量单个点对热图贡献多少的度量。值10等于在同一位置具有10个权重点1。与聚类结合使用时特别有用。
                 */
                heatmapWeight(1f)

        );

        // 将热图图层添加到地图上的“水标签”图层上方
        loadedMapStyle.addLayerAbove(layer, "waterway-label");
    }

    //region 生命周期
    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    //endregion

    private void initHeatmapColors() {
        listOfHeatmapColors = new Expression[]{
// 0
                interpolate(
                        linear(), heatmapDensity(),
                        literal(0.01), rgba(0, 0, 0, 0.01),
                        literal(0.25), rgba(224, 176, 63, 0.5),
                        literal(0.5), rgb(247, 252, 84),
                        literal(0.75), rgb(186, 59, 30),
                        literal(0.9), rgb(255, 0, 0)
                ),
// 1
                interpolate(
                        linear(), heatmapDensity(),
                        literal(0.01), rgba(255, 255, 255, 0.4),
                        literal(0.25), rgba(4, 179, 183, 1.0),
                        literal(0.5), rgba(204, 211, 61, 1.0),
                        literal(0.75), rgba(252, 167, 55, 1.0),
                        literal(1), rgba(255, 78, 70, 1.0)
                ),
// 2
                interpolate(
                        linear(), heatmapDensity(),
                        literal(0.01), rgba(12, 182, 253, 0.0),
                        literal(0.25), rgba(87, 17, 229, 0.5),
                        literal(0.5), rgba(255, 0, 0, 1.0),
                        literal(0.75), rgba(229, 134, 15, 0.5),
                        literal(1), rgba(230, 255, 55, 0.6)
                ),
// 3
                interpolate(
                        linear(), heatmapDensity(),
                        literal(0.01), rgba(135, 255, 135, 0.2),
                        literal(0.5), rgba(255, 99, 0, 0.5),
                        literal(1), rgba(47, 21, 197, 0.2)
                ),
// 4
                interpolate(
                        linear(), heatmapDensity(),
                        literal(0.01), rgba(4, 0, 0, 0.2),
                        literal(0.25), rgba(229, 12, 1, 1.0),
                        literal(0.30), rgba(244, 114, 1, 1.0),
                        literal(0.40), rgba(255, 205, 12, 1.0),
                        literal(0.50), rgba(255, 229, 121, 1.0),
                        literal(1), rgba(255, 253, 244, 1.0)
                ),
// 5
                interpolate(
                        linear(), heatmapDensity(),
                        literal(0.01), rgba(0, 0, 0, 0.01),
                        literal(0.05), rgba(0, 0, 0, 0.05),
                        literal(0.4), rgba(254, 142, 2, 0.7),
                        literal(0.5), rgba(255, 165, 5, 0.8),
                        literal(0.8), rgba(255, 187, 4, 0.9),
                        literal(0.95), rgba(255, 228, 173, 0.8),
                        literal(1), rgba(255, 253, 244, .8)
                ),
//6
                interpolate(
                        linear(), heatmapDensity(),
                        literal(0.01), rgba(0, 0, 0, 0.01),
                        literal(0.3), rgba(82, 72, 151, 0.4),
                        literal(0.4), rgba(138, 202, 160, 1.0),
                        literal(0.5), rgba(246, 139, 76, 0.9),
                        literal(0.9), rgba(252, 246, 182, 0.8),
                        literal(1), rgba(255, 255, 255, 0.8)
                ),

//7
                interpolate(
                        linear(), heatmapDensity(),
                        literal(0.01), rgba(0, 0, 0, 0.01),
                        literal(0.1), rgba(0, 2, 114, .1),
                        literal(0.2), rgba(0, 6, 219, .15),
                        literal(0.3), rgba(0, 74, 255, .2),
                        literal(0.4), rgba(0, 202, 255, .25),
                        literal(0.5), rgba(73, 255, 154, .3),
                        literal(0.6), rgba(171, 255, 59, .35),
                        literal(0.7), rgba(255, 197, 3, .4),
                        literal(0.8), rgba(255, 82, 1, 0.7),
                        literal(0.9), rgba(196, 0, 1, 0.8),
                        literal(0.95), rgba(121, 0, 0, 0.8)
                ),
// 8
                interpolate(
                        linear(), heatmapDensity(),
                        literal(0.01), rgba(0, 0, 0, 0.01),
                        literal(0.1), rgba(0, 2, 114, .1),
                        literal(0.2), rgba(0, 6, 219, .15),
                        literal(0.3), rgba(0, 74, 255, .2),
                        literal(0.4), rgba(0, 202, 255, .25),
                        literal(0.5), rgba(73, 255, 154, .3),
                        literal(0.6), rgba(171, 255, 59, .35),
                        literal(0.7), rgba(255, 197, 3, .4),
                        literal(0.8), rgba(255, 82, 1, 0.7),
                        literal(0.9), rgba(196, 0, 1, 0.8),
                        literal(0.95), rgba(121, 0, 0, 0.8)
                ),
// 9
                interpolate(
                        linear(), heatmapDensity(),
                        literal(0.01), rgba(0, 0, 0, 0.01),
                        literal(0.1), rgba(0, 2, 114, .1),
                        literal(0.2), rgba(0, 6, 219, .15),
                        literal(0.3), rgba(0, 74, 255, .2),
                        literal(0.4), rgba(0, 202, 255, .25),
                        literal(0.5), rgba(73, 255, 154, .3),
                        literal(0.6), rgba(171, 255, 59, .35),
                        literal(0.7), rgba(255, 197, 3, .4),
                        literal(0.8), rgba(255, 82, 1, 0.7),
                        literal(0.9), rgba(196, 0, 1, 0.8),
                        literal(0.95), rgba(121, 0, 0, 0.8)
                ),
// 10
                interpolate(
                        linear(), heatmapDensity(),
                        literal(0.01), rgba(0, 0, 0, 0.01),
                        literal(0.1), rgba(0, 2, 114, .1),
                        literal(0.2), rgba(0, 6, 219, .15),
                        literal(0.3), rgba(0, 74, 255, .2),
                        literal(0.4), rgba(0, 202, 255, .25),
                        literal(0.5), rgba(73, 255, 154, .3),
                        literal(0.6), rgba(171, 255, 59, .35),
                        literal(0.7), rgba(255, 197, 3, .4),
                        literal(0.8), rgba(255, 82, 1, 0.7),
                        literal(0.9), rgba(196, 0, 1, 0.8),
                        literal(0.95), rgba(121, 0, 0, 0.8)
                ),
// 11
                interpolate(
                        linear(), heatmapDensity(),
                        literal(0.01), rgba(0, 0, 0, 0.25),
                        literal(0.25), rgba(229, 12, 1, .7),
                        literal(0.30), rgba(244, 114, 1, .7),
                        literal(0.40), rgba(255, 205, 12, .7),
                        literal(0.50), rgba(255, 229, 121, .8),
                        literal(1), rgba(255, 253, 244, .8)
                )
        };
    }

    private void initHeatmapRadiusStops() {
        //通过缩放级别调整热图半径
        listOfHeatmapRadiusStops = new Expression[]{
// 0
                interpolate(
                        linear(), zoom(),
                        literal(6), literal(50),
                        literal(20), literal(100)
                ),
// 1
                interpolate(
                        linear(), zoom(),
                        literal(12), literal(70),
                        literal(20), literal(100)
                ),
// 2
                interpolate(
                        linear(), zoom(),
                        literal(1), literal(7),
                        literal(5), literal(50)
                ),
// 3
                interpolate(
                        linear(), zoom(),
                        literal(1), literal(7),
                        literal(5), literal(50)
                ),
// 4
                interpolate(
                        linear(), zoom(),
                        literal(1), literal(7),
                        literal(5), literal(50)
                ),
// 5
                interpolate(
                        linear(), zoom(),
                        literal(1), literal(7),
                        literal(15), literal(200)
                ),
// 6
                interpolate(
                        linear(), zoom(),
                        literal(1), literal(10),
                        literal(8), literal(70)
                ),
// 7
                interpolate(
                        linear(), zoom(),
                        literal(1), literal(10),
                        literal(8), literal(200)
                ),
// 8
                interpolate(
                        linear(), zoom(),
                        literal(1), literal(10),
                        literal(8), literal(200)
                ),
// 9
                interpolate(
                        linear(), zoom(),
                        literal(1), literal(10),
                        literal(8), literal(200)
                ),
// 10
                interpolate(
                        linear(), zoom(),
                        literal(1), literal(10),
                        literal(8), literal(200)
                ),
// 11
                interpolate(
                        linear(), zoom(),
                        literal(1), literal(10),
                        literal(8), literal(200)
                ),
        };
    }

    private void initHeatmapIntensityStops() {
        listOfHeatmapIntensityStops = new Float[]{
// 0
                0.6f,
// 1
                0.3f,
// 2
                1f,
// 3
                1f,
// 4
                1f,
// 5
                1f,
// 6
                1.5f,
// 7
                0.8f,
// 8
                0.25f,
// 9
                0.8f,
// 10
                0.25f,
// 11
                0.5f
        };
    }
}
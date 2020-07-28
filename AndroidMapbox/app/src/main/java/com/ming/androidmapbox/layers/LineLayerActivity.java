package com.ming.androidmapbox.layers;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.ming.androidmapbox.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mapbox.mapboxsdk.style.expressions.Expression.interpolate;
import static com.mapbox.mapboxsdk.style.expressions.Expression.lineProgress;
import static com.mapbox.mapboxsdk.style.expressions.Expression.linear;
import static com.mapbox.mapboxsdk.style.expressions.Expression.rgb;
import static com.mapbox.mapboxsdk.style.expressions.Expression.stop;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineGradient;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;

public class LineLayerActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private static final String LINE_SOURCE_ID = "line-source-id";
    private static final String LINE_LAYER_ID = "line-layer-id";
    private static final String LINE_IMAGE_ID = "line-image-id";

    private List<Point> routeCoordinates;
    private MapView mapView;
    private Style mapStyle;
    private GeoJsonSource geoJsonSource;
    private FeatureCollection featureCollection;
    private LineLayer lineLayer;

    private RadioGroup radioGroup;
    /**
     * 模糊
     */
    private Button mBtnLineBlur;
    /**
     * 线帽
     */
    private Button mBtnLineCap;
    /**
     * 线条的绘制颜色
     */
    private Button mBtnLineColor;
    /**
     * 行破折号
     */
    private Button mBtnLineDasharray;
    /**
     * 行宽
     */
    private Button mBtnLineWidth;
    /**
     * 在线的实际路径之外绘制线框
     */
    private Button mBtnLineGapWidth;
    /**
     * 线连接
     */
    private Button mBtnLineJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        // This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_line_layer);

        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        radioGroup = findViewById(R.id.rg_line);
        radioGroup.setOnCheckedChangeListener(this);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        initMap();

        mBtnLineBlur = findViewById(R.id.btn_lineBlur);
        mBtnLineBlur.setOnClickListener(this);
        mBtnLineCap = findViewById(R.id.btn_lineCap);
        mBtnLineCap.setOnClickListener(this);
        mBtnLineColor = findViewById(R.id.btn_lineColor);
        mBtnLineColor.setOnClickListener(this);
        mBtnLineDasharray = findViewById(R.id.btn_lineDasharray);
        mBtnLineDasharray.setOnClickListener(this);
        mBtnLineWidth = findViewById(R.id.btn_lineWidth);
        mBtnLineWidth.setOnClickListener(this);
        mBtnLineGapWidth = findViewById(R.id.btn_lineGapWidth);
        mBtnLineGapWidth.setOnClickListener(this);
        mBtnLineJoin = findViewById(R.id.btn_lineJoin);
        mBtnLineJoin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_lineBlur:
                Float blurValue = lineLayer.getLineBlur().getValue();
                if (blurValue != null && blurValue == 5f) {
                    lineLayer.setProperties(PropertyFactory.lineBlur(0f));
                } else {
                    lineLayer.setProperties(PropertyFactory.lineBlur(5f));
                }
                break;
            case R.id.btn_lineCap:
                String capValue = lineLayer.getLineCap().getValue();
                if (Property.LINE_CAP_BUTT.equals(capValue)) {
                    lineLayer.setProperties(PropertyFactory.lineCap(Property.LINE_CAP_ROUND));
                } else {
                    lineLayer.setProperties(PropertyFactory.lineCap(Property.LINE_CAP_BUTT));
                }
                break;
            case R.id.btn_lineColor:
                int lineColorAsInt = lineLayer.getLineColorAsInt();
                if (lineColorAsInt == Color.RED) {
                    lineLayer.setProperties(PropertyFactory.lineColor(Color.GREEN));
                } else {
                    lineLayer.setProperties(PropertyFactory.lineColor(Color.RED));
                }
                break;
            case R.id.btn_lineDasharray:
                Float[] lineDasharrayValue = lineLayer.getLineDasharray().getValue();
                Float[] zeroFloats = {14f, 0f};
                Float[] floats = {5f, 5f};
                if (Arrays.equals(floats, lineDasharrayValue)) {
                    lineLayer.setProperties(PropertyFactory.lineDasharray(zeroFloats));
                } else {
                    lineLayer.setProperties(PropertyFactory.lineDasharray(floats));
                }
                break;
            case R.id.btn_lineWidth:
                Float lineWidthValue = lineLayer.getLineWidth().getValue();
                if (lineWidthValue != null && lineWidthValue == 14f) {
                    lineLayer.setProperties(PropertyFactory.lineWidth(8f));
                } else {
                    lineLayer.setProperties(PropertyFactory.lineWidth(14f));
                }
                break;
            case R.id.btn_lineGapWidth:
                Float lineGapWidthValue = lineLayer.getLineGapWidth().getValue();
                if (lineGapWidthValue != null && lineGapWidthValue == 1f) {
                    lineLayer.setProperties(PropertyFactory.lineGapWidth(0f));
                } else {
                    lineLayer.setProperties(PropertyFactory.lineGapWidth(1f));
                }
                break;
            case R.id.btn_lineJoin:
                String lineJoinValue = lineLayer.getLineJoin().getValue();
                if (Property.LINE_JOIN_MITER.equals(lineJoinValue)) {
                    lineLayer.setProperties(PropertyFactory.lineJoin(Property.LINE_JOIN_BEVEL));
                } else {
                    lineLayer.setProperties(PropertyFactory.lineJoin(Property.LINE_JOIN_MITER));
                }
                break;
            default:
                break;
        }
    }

    private void initMap() {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.LIGHT, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        mapStyle = style;
                        initCoordinates();
                        initLineLayer(style);

                        switch (radioGroup.getCheckedRadioButtonId()) {
                            case R.id.rb_line_normal:
                                drawNormalLine(mapStyle);
                                mBtnLineColor.setVisibility(View.VISIBLE);
                                break;

                            case R.id.rb_line_gradient:
                                drawLineGradient(mapStyle);
                                mBtnLineColor.setVisibility(View.GONE);
                                break;

                            case R.id.rb_line_image:
                                drawImageLine(mapStyle);
                                mBtnLineColor.setVisibility(View.GONE);
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        initMap();
    }

    private void initLineLayer(Style style) {
        if (style == null) return;
        //source
        geoJsonSource = new GeoJsonSource(LINE_SOURCE_ID, new GeoJsonOptions().withLineMetrics(true));
        style.addSource(geoJsonSource);
        //image
        Drawable drawable = getResources().getDrawable(R.drawable.ic_track_road);
        style.addImage(LINE_IMAGE_ID, drawable);
        //Layer
        lineLayer = new LineLayer(LINE_LAYER_ID, LINE_SOURCE_ID);
        style.addLayer(lineLayer);
    }

    private void drawNormalLine(Style style) {
        if (style == null) return;

        //Source
        featureCollection = FeatureCollection.fromFeatures(
                new Feature[]{Feature.fromGeometry(LineString.fromLngLats(routeCoordinates))});
        geoJsonSource.setGeoJson(featureCollection);
        //Layer
        lineLayer.setProperties(
                //应用于线条的模糊，以密度无关的像素为单位
//                PropertyFactory.lineBlur(0f),
                /*
                 * 线帽
                 * 布局 属性。 可选的枚举。一，，。默认为"butt"。 "butt""round""square"
                 * 行尾的显示。
                 *
                 * "butt"：
                 * 端部带有正方形的笔帽，绘制到直线的确切端点。
                 *
                 * "round"：
                 * 具有圆形末端的盖，该圆形末端以线宽的一半的半径超出线的端点绘制，并以线的端点为中心。
                 *
                 * "square"：
                 * 端部带有正方形的笔帽，该笔帽以超出线宽一半的距离画出线的端点。
                 */
                lineCap(Property.LINE_CAP_BUTT),
                //线条的绘制颜色
                PropertyFactory.lineColor(Color.RED),
                /*
                 * 行破折号
                 * 油漆 属性。大于或等于的数字s的 可选数组。线宽单位。由line-pattern 禁用。可转换的。
                 * 指定形成虚线图案的交替虚线和间隙的长度。长度随后通过线宽缩放。要将破折号长度转换为像素，请将该长度乘以当前线宽。
                 * 请注意，lineMetrics: true指定了GeoJSON的源不会将虚线渲染到预期的比例。
                 * 另请注意，仅在整数缩放级别下才会评估与缩放相关的表达式。
                 */
//                PropertyFactory.lineDasharray(new Float[]{0.01f, 2f}),
                //行宽 以像素为单位。
                lineWidth(14f),
                //在线的实际路径之外绘制线框。值表示内部间隙的宽度。
//                PropertyFactory.lineGapWidth(2f),
                //线梯度 定义用于为线要素着色的渐变。只能与指定的GeoJSON源一起使用"lineMetrics": true。
//                PropertyFactory.lineGradient(interpolate(
//                        linear(), lineProgress(),
//                        stop(0f, rgb(6, 1, 255)), // blue
//                        stop(0.1f, rgb(59, 118, 227)), // royal blue
//                        stop(0.3f, rgb(7, 238, 251)), // cyan
//                        stop(0.5f, rgb(0, 255, 42)), // lime
//                        stop(0.7f, rgb(255, 252, 0)), // yellow
//                        stop(1f, rgb(255, 30, 0)) // red
//                )),
                /*
                 * 线连接
                 * 布局 属性。 可选的枚举。一，，。默认为"miter"。 "bevel" "round" "miter"
                 * 连接时显示线。
                 *
                 * "bevel"：
                 * 具有平方根末端的连接，该末端在线宽的一半处超出线的端点。
                 *
                 * "round"：
                 * 具有圆形末端的连接，该末端以线宽度的一半的半径超出线的端点绘制，并以线的端点为中心。
                 *
                 * "miter"：
                 * 具有尖角的角的连接，其外侧超出路径的端点，直到它们汇合为止。
                 */
                lineJoin(Property.LINE_JOIN_MITER),
                //线斜接极限 用于自动将斜接连接转换为斜角连接以形成锐角。
//                PropertyFactory.lineMiterLimit(0f),
                //行数限制 用于自动将圆角连接转换为斜角较小的斜角连接。
//                PropertyFactory.lineRoundLimit(0f),
                /*
                 * 线偏移
                 * 油漆 属性。 可选号码。以像素为单位。默认为。支持和表达。可转换的。
                 * 线的偏移量。对于线性特征，正值使线相对于线的方向向右偏移，负值向左偏移。
                 */
//                PropertyFactory.lineOffset(0f),
                //填充不透明度
//                PropertyFactory.lineOpacity(0.7f),
                //填充模式  可选的resolveImage。 Sprite中用于绘制图像填充的图像名称。对于无缝图案，图像的宽度和高度必须是两倍（2、4、8，...，512）。请注意，仅在整数缩放级别会评估与缩放相关的表达式。
//                PropertyFactory.linePattern(LINE_IMAGE_ID),
                //填充排序键 基于此值按升序对要素进行排序。具有较高排序键的功能将显示在具有较低排序键的功能上方。
//                PropertyFactory.lineSortKey(),
                //填充翻译 几何的偏移量。值为[x，y]，其中负数分别表示左和上。 以像素为单位
//                PropertyFactory.lineTranslate(new Float[]{0f, 0f}),
                /*
                 * 填充翻译锚 可选的枚举 "map" "viewport" 之一。默认为"map"。需要设置line-translate。
                 * "map"：填充相对于地图进行转换。
                 * "viewport"：填充相对于视口平移。
                 */
                PropertyFactory.lineTranslateAnchor(Property.LINE_TRANSLATE_ANCHOR_MAP),
                //可见性
                PropertyFactory.visibility(Property.VISIBLE)
        );
    }

    private void drawLineGradient(Style style) {
        if (style == null) return;
        //Source
        featureCollection = FeatureCollection.fromFeature(Feature.fromGeometry(LineString.fromLngLats(routeCoordinates)));
        geoJsonSource.setGeoJson(featureCollection);
        //Layer
        lineLayer.setProperties(
                lineCap(Property.LINE_CAP_ROUND),
                lineJoin(Property.LINE_JOIN_ROUND),
                lineWidth(14f),
                lineGradient(interpolate(
                        linear(), lineProgress(),
                        stop(0f, rgb(6, 1, 255)), // blue
                        stop(0.1f, rgb(59, 118, 227)), // royal blue
                        stop(0.3f, rgb(7, 238, 251)), // cyan
                        stop(0.5f, rgb(0, 255, 42)), // lime
                        stop(0.7f, rgb(255, 252, 0)), // yellow
                        stop(1f, rgb(255, 30, 0)) // red
                )));
    }

    private void drawImageLine(Style style) {
        if (style == null) return;

        //Source
        featureCollection = FeatureCollection.fromFeatures(
                new Feature[]{Feature.fromGeometry(LineString.fromLngLats(routeCoordinates))});
        geoJsonSource.setGeoJson(featureCollection);
        //Layer
        lineLayer.setProperties(
                lineCap(Property.LINE_CAP_ROUND),
                lineJoin(Property.LINE_JOIN_ROUND),
                lineWidth(14f),
                PropertyFactory.linePattern(LINE_IMAGE_ID)
        );
    }

    private void initCoordinates() {
        routeCoordinates = new ArrayList<>();
        routeCoordinates.add(Point.fromLngLat(-77.044211, 38.852924));
        routeCoordinates.add(Point.fromLngLat(-77.045659, 38.860158));
        routeCoordinates.add(Point.fromLngLat(-77.044232, 38.862326));
        routeCoordinates.add(Point.fromLngLat(-77.040879, 38.865454));
        routeCoordinates.add(Point.fromLngLat(-77.039936, 38.867698));
        routeCoordinates.add(Point.fromLngLat(-77.040338, 38.86943));
        routeCoordinates.add(Point.fromLngLat(-77.04264, 38.872528));
        routeCoordinates.add(Point.fromLngLat(-77.03696, 38.878424));
        routeCoordinates.add(Point.fromLngLat(-77.032309, 38.87937));
        routeCoordinates.add(Point.fromLngLat(-77.030056, 38.880945));
        routeCoordinates.add(Point.fromLngLat(-77.027645, 38.881779));
        routeCoordinates.add(Point.fromLngLat(-77.026946, 38.882645));
        routeCoordinates.add(Point.fromLngLat(-77.026942, 38.885502));
        routeCoordinates.add(Point.fromLngLat(-77.028054, 38.887449));
        routeCoordinates.add(Point.fromLngLat(-77.02806, 38.892088));
        routeCoordinates.add(Point.fromLngLat(-77.03364, 38.892108));
        routeCoordinates.add(Point.fromLngLat(-77.033643, 38.899926));
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
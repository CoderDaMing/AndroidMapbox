package com.ming.androidmapbox.layers;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.ming.androidmapbox.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Polygon
 */
public class FillLayerActivity extends AppCompatActivity {
    private static final String FILL_SOURCE_ID = "fill-source-id";
    private static final String FILL_LAYER_ID = "fill-layer-id";
    private static final String FILL_IMAGE_ID = "fill-image-id";

    private MapView mapView;
    private GeoJsonSource geoJsonSource;
    private FillLayer fillLayer;

    private SwitchCompat switchUseImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_layer);

        switchUseImage = findViewById(R.id.switch_use_image);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        initMap();
        switchUseImage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                initMap();
            }
        });
    }

    private void initMap() {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull final Style style) {
                        initFillLayer(style, switchUseImage.isChecked());
                        //默认画不带洞的
                        addFillLayerOnMap();

                        findViewById(R.id.btn_have_hole).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addFillLayerOnMapWithHole();
                            }
                        });
                        findViewById(R.id.btn_not_have_hole).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addFillLayerOnMap();
                            }
                        });

                        findViewById(R.id.btn_visibility).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (Property.VISIBLE.equals(fillLayer.getVisibility().getValue())) {
                                    fillLayer.setProperties(PropertyFactory.visibility(Property.NONE));
                                } else {
                                    fillLayer.setProperties(PropertyFactory.visibility(Property.VISIBLE));
                                }
                            }
                        });

                        findViewById(R.id.btn_color).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int fillColorAsInt = fillLayer.getFillColorAsInt();
                                if (Config.BLUE_COLOR == fillColorAsInt) {
                                    fillLayer.setProperties(PropertyFactory.fillColor(Config.RED_COLOR)
                                            , PropertyFactory.fillOutlineColor(Config.BLUE_COLOR)
                                            , PropertyFactory.fillOpacity(0.8f));
                                } else {
                                    fillLayer.setProperties(PropertyFactory.fillColor(Config.BLUE_COLOR)
                                            , PropertyFactory.fillOutlineColor(Config.RED_COLOR)
                                            , PropertyFactory.fillOpacity(0.5f));
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    private void initFillLayer(Style style, boolean isUseImage) {
        //Source
        geoJsonSource = new GeoJsonSource(FILL_SOURCE_ID);
        style.addSource(geoJsonSource);
        //Layer
        fillLayer = new FillLayer(FILL_LAYER_ID, FILL_SOURCE_ID).withProperties(
                //填充抗锯齿 默认为true
                PropertyFactory.fillAntialias(true),
                //填充内部填充色
                PropertyFactory.fillColor(Config.BLUE_COLOR),
                //填充不透明度
                PropertyFactory.fillOpacity(0.5f),
                //填充轮廓线颜色
                PropertyFactory.fillOutlineColor(Config.RED_COLOR),
                //填充模式  可选的resolveImage。 Sprite中用于绘制图像填充的图像名称。对于无缝图案，图像的宽度和高度必须是两倍（2、4、8，...，512）。请注意，仅在整数缩放级别会评估与缩放相关的表达式。
//                PropertyFactory.fillPattern(FILL_IMAGE_ID),
                //填充排序键 基于此值按升序对要素进行排序。具有较高排序键的功能将显示在具有较低排序键的功能上方。
//                PropertyFactory.fillSortKey(),
                //填充翻译 几何的偏移量, 默认为0,0。值为[x，y]，其中负数分别表示左和上。 以像素为单位
                PropertyFactory.fillTranslate(new Float[]{0f, 0f}),
                /*
                 * 填充翻译锚 可选的枚举 "map" "viewport" 之一。默认为"map"。需要设置fill-translate。
                 * "map"：填充相对于地图进行转换。
                 * "viewport"：填充相对于视口平移。
                 */
                PropertyFactory.fillTranslateAnchor(Property.FILL_TRANSLATE_ANCHOR_MAP),
                //可见性 默认为可见
                PropertyFactory.visibility(Property.VISIBLE)
        );
        if (isUseImage){
            //image
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher_round);
            style.addImage(FILL_IMAGE_ID, drawable);
            fillLayer.setProperties(PropertyFactory.fillPattern(FILL_IMAGE_ID));
            Toast.makeText(this, "等待图源加载完成...", Toast.LENGTH_SHORT).show();
        }
        style.addLayer(fillLayer);
    }

    private void addFillLayerOnMap() {
        List<List<Point>> points = new ArrayList<>();
        points.add(Config.POLYGON_COORDINATES);
        geoJsonSource.setGeoJson(Polygon.fromLngLats(points));
    }

    private void addFillLayerOnMapWithHole() {
        LineString outerLineString = LineString.fromLngLats(Config.POLYGON_COORDINATES);
        List<LineString> innerList = new ArrayList<>();
        for (int i = 0; i < Config.HOLE_COORDINATES.size(); i++) {
            LineString innerLineString = LineString.fromLngLats(Config.HOLE_COORDINATES.get(i));
            innerList.add(innerLineString);
        }
        geoJsonSource.setGeoJson(Polygon.fromOuterInner(outerLineString, innerList));
    }

    static final class Config {
        static final int BLUE_COLOR = Color.parseColor("#3bb2d0");
        static final int RED_COLOR = Color.parseColor("#AF0000");

        static final List<Point> POLYGON_COORDINATES = new ArrayList<Point>() {
            {
                add(Point.fromLngLat(55.30122473231012, 25.26476622289597));
                add(Point.fromLngLat(55.29743486255916, 25.25827212207261));
                add(Point.fromLngLat(55.28978863411328, 25.251356725509737));
                add(Point.fromLngLat(55.300027931336984, 25.246425506635504));
                add(Point.fromLngLat(55.307474692951274, 25.244200378933655));
                add(Point.fromLngLat(55.31212891895635, 25.256408010450187));
                add(Point.fromLngLat(55.30774064871093, 25.26266169122738));
                add(Point.fromLngLat(55.301357710197806, 25.264946609615492));
                add(Point.fromLngLat(55.30122473231012, 25.26476622289597));
            }
        };

        static final List<List<Point>> HOLE_COORDINATES = new ArrayList<List<Point>>() {
            {
                add(new ArrayList<>(new ArrayList<Point>() {
                    {
                        add(Point.fromLngLat(55.30084858315658, 25.256531695820797));
                        add(Point.fromLngLat(55.298280197635705, 25.252243254705405));
                        add(Point.fromLngLat(55.30163885563897, 25.250501032248863));
                        add(Point.fromLngLat(55.304059065092645, 25.254700192612702));
                        add(Point.fromLngLat(55.30084858315658, 25.256531695820797));
                    }
                }));
                add(new ArrayList<>(new ArrayList<Point>() {
                    {
                        add(Point.fromLngLat(55.30173763969924, 25.262517391695198));
                        add(Point.fromLngLat(55.301095543307355, 25.26122200491396));
                        add(Point.fromLngLat(55.30396028103232, 25.259479911263526));
                        add(Point.fromLngLat(55.30489872958182, 25.261132667394975));
                        add(Point.fromLngLat(55.30173763969924, 25.262517391695198));
                    }
                }));
            }
        };
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
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    //endregion
}

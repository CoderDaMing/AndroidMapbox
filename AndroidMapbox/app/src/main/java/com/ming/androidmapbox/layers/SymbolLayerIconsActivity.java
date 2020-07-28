package com.ming.androidmapbox.layers;

import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.ming.androidmapbox.R;

import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.match;
import static com.mapbox.mapboxsdk.style.expressions.Expression.stop;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.symbolSortKey;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.symbolZOrder;

/**
 * Display {@link SymbolLayer} icons on the map.
 */
public class SymbolLayerIconsActivity extends AppCompatActivity implements
        OnMapReadyCallback, MapboxMap.OnMapClickListener {
    private static final String TAG = "SymbolLayerIcons:";

    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";
    private static final String PROPERTY_NUMBER = "number";
    private static final String PROPERTY_SELECTED = "selected";

    private MapView mapView;
    private MapboxMap mapboxMap;
    private GeoJsonSource geoJsonSource;
    private List<Feature> symbolLayerIconFeatureList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        // This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_symbol_layer_icons);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        SymbolLayerIconsActivity.this.mapboxMap = mapboxMap;

        symbolLayerIconFeatureList = new ArrayList<>();

        Feature feature1 = Feature.fromGeometry(Point.fromLngLat(-57.225365, -33.213144));
        feature1.addNumberProperty(PROPERTY_NUMBER, 1);
        feature1.addBooleanProperty(PROPERTY_SELECTED, false);
        symbolLayerIconFeatureList.add(feature1);
        Feature feature2 = Feature.fromGeometry(Point.fromLngLat(-54.14164, -33.981818));
        feature2.addNumberProperty(PROPERTY_NUMBER, 2);
        feature2.addBooleanProperty(PROPERTY_SELECTED, false);
        symbolLayerIconFeatureList.add(feature2);
        Feature feature3 = Feature.fromGeometry(Point.fromLngLat(-56.990533, -30.583266));
        feature3.addNumberProperty(PROPERTY_NUMBER, 3);
        feature3.addBooleanProperty(PROPERTY_SELECTED, false);
        symbolLayerIconFeatureList.add(feature3);
        //注意这个用的Style.Builder可以解决Style加载完成前添加大量数据的需求
        geoJsonSource = new GeoJsonSource(SOURCE_ID, FeatureCollection.fromFeatures(symbolLayerIconFeatureList));
        mapboxMap.setStyle(new Style.Builder().fromUri(Style.MAPBOX_STREETS)

                // Add the SymbolLayer icon image to the map style
                .withImage(ICON_ID, BitmapFactory.decodeResource(
                        SymbolLayerIconsActivity.this.getResources(), R.drawable.mapbox_marker_icon_default))

                // Adding a GeoJson source for the SymbolLayer icons.
                .withSource(geoJsonSource)

                // Adding the actual SymbolLayer to the map style. An offset is added that the bottom of the red
                // marker icon gets fixed to the coordinate, rather than the middle of the icon being fixed to
                // the coordinate point. This is offset is not always needed and is dependent on the image
                // that you use for the SymbolLayer icon.
                .withLayer(new SymbolLayer(LAYER_ID, SOURCE_ID)
                        .withProperties(
                                iconImage(ICON_ID),
                                iconAllowOverlap(true),
                                iconIgnorePlacement(true),
                                iconSize(match(Expression.toString(get(PROPERTY_SELECTED)), literal(1.0f),
                                        stop("true", 2.0f))),
                                //将图标排序,被选中的放在上面,数字越大越往上
                                symbolSortKey(match(Expression.toString(get(PROPERTY_SELECTED)), literal(0),
                                        stop("true", 10))),
                                //按symbolSortKey设置的排序
                                symbolZOrder(Property.SYMBOL_Z_ORDER_AUTO)

                        )
                ), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                // Map is set up and the style has loaded. Now you can add additional data or make other map adjustments.
                //地图点击监听
                mapboxMap.addOnMapClickListener(SymbolLayerIconsActivity.this);
            }
        });
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        Log.d(TAG, "onMapClick() called with: point = [" + point + "]");
        PointF pointF = mapboxMap.getProjection().toScreenLocation(point);
        List<Feature> features = mapboxMap.queryRenderedFeatures(pointF, LAYER_ID);
        if (features.isEmpty()) {
            Log.d(TAG, "onMapClick() deselectAllFeature");
            deselectAllFeature();
            return false;
        }
        Log.d(TAG, "onMapClick() selectFeature");
        selectFeature(features.get(0).getNumberProperty(PROPERTY_NUMBER).intValue());
        return true;
    }

    private void deselectAllFeature() {
        for (Feature feature : symbolLayerIconFeatureList) {
            feature.addBooleanProperty(PROPERTY_SELECTED, false);
        }
        geoJsonSource.setGeoJson(FeatureCollection.fromFeatures(symbolLayerIconFeatureList));
        Toast.makeText(this, "点击地图取消选中", Toast.LENGTH_SHORT).show();
    }

    private void selectFeature(int number) {
        for (Feature feature : symbolLayerIconFeatureList) {
            int value = feature.getNumberProperty(PROPERTY_NUMBER).intValue();
            if (number == value) {
                feature.addBooleanProperty(PROPERTY_SELECTED, true);
            } else {
                feature.addBooleanProperty(PROPERTY_SELECTED, false);
            }
        }
        geoJsonSource.setGeoJson(FeatureCollection.fromFeatures(symbolLayerIconFeatureList));
        Toast.makeText(this, "点击选中number" + number, Toast.LENGTH_SHORT).show();
    }

    //region 生命周期
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapboxMap != null) {
            mapboxMap.removeOnMapClickListener(this);
        }
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    //endregion
}

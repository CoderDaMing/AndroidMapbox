package com.ming.androidmapbox.symbol.manager;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.ming.androidmapbox.MyApplication;
import com.ming.androidmapbox.R;
import com.ming.androidmapbox.symbol.bean.MapGeoJson;

import java.util.HashMap;
import java.util.List;

import static com.mapbox.mapboxsdk.style.expressions.Expression.all;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.gt;
import static com.mapbox.mapboxsdk.style.expressions.Expression.gte;
import static com.mapbox.mapboxsdk.style.expressions.Expression.has;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.lt;
import static com.mapbox.mapboxsdk.style.expressions.Expression.toNumber;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textField;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textSize;

/**
 * 类描述:聚合数据管理类
 */
public class ClusteringLayerManager {
    private static final String MAP_SOURCE_ID = "map-source-id";
    private static final String CLUSTER_CIRCLE_ICON_ID = "clustered-circle-icon-id";
    private static final String UN_CLUSTERED_POINTS = "un-clustered-points";
    private static final String CLUSTER_ = "cluster-";
    private static final String COUNT = "count";
    private static final String POINT_COUNT = "point_count";
    private static final String PROPERTY_ID = "id";

    private Style loadedMapStyle;
    private GeoJsonSource geoJsonSource = null;
    private final HashMap<String, Bitmap> imageMap = new HashMap<>();


    public ClusteringLayerManager(Style mapStyle) {
        this.loadedMapStyle = mapStyle;
        initLayerIcons();
        initSource();
    }

    private void initLayerIcons() {
        //添加图片
        loadedMapStyle.addImage(CLUSTER_CIRCLE_ICON_ID, BitmapUtils.getBitmapFromDrawable(MyApplication.getInstance().getResources().getDrawable(android.R.drawable.ic_menu_more)));
        loadedMapStyle.addImages(imageMap);
    }

    private void initSource() {
        //添加源
        geoJsonSource = new GeoJsonSource(MAP_SOURCE_ID, FeatureCollection.fromFeatures(new Feature[]{}),
                new GeoJsonOptions()
                        .withCluster(true)
                        .withClusterRadius(30)
        );
        loadedMapStyle.addSource(geoJsonSource);
        //Creating a SymbolLayer icon layer for single data/icon points
        loadedMapStyle.addLayer(new SymbolLayer(UN_CLUSTERED_POINTS, MAP_SOURCE_ID).withProperties(
                iconImage("{" + PROPERTY_ID + "}")
//                ,iconSize(
//                        division(
//                                get("mag"), literal(4.0f)
//                        )
//                )
        ));

        // Use the earthquakes GeoJSON source to create three point ranges.
        int[] layers = new int[]{150, 20, 0};

        for (int i = 0; i < layers.length; i++) {
            //Add clusters' SymbolLayers images
            SymbolLayer symbolLayer = new SymbolLayer(CLUSTER_ + i, MAP_SOURCE_ID);

            symbolLayer.setProperties(
                    iconImage(CLUSTER_CIRCLE_ICON_ID)
            );
            Expression pointCount = toNumber(get(POINT_COUNT));

            // Add a filter to the cluster layer that hides the icons based on "point_count"
            symbolLayer.setFilter(
                    i == 0
                            ? all(has(POINT_COUNT),
                            gte(pointCount, literal(layers[i]))
                    ) : all(has(POINT_COUNT),
                            gt(pointCount, literal(layers[i])),
                            lt(pointCount, literal(layers[i - 1]))
                    )
            );
            loadedMapStyle.addLayer(symbolLayer);
        }

        //Add a SymbolLayer for the cluster data number point count
        loadedMapStyle.addLayer(new SymbolLayer(COUNT, MAP_SOURCE_ID).withProperties(
                textField(Expression.toString(get(POINT_COUNT))),
                textSize(15f),
                textColor(Color.WHITE),
                textIgnorePlacement(true),
                // The .5f offset moves the data numbers down a little bit so that they're
                // in the middle of the triangle cluster image
//                textOffset(new Float[] {0f, .5f}),
                textAllowOverlap(true)
        ));
    }

    //region 添加
    public void addClusteredGeoJsonSource(MapGeoJson mapGeoJson) {
        //先清除
        removeAllLayers();

        List<MapGeoJson.FeaturesBean> features = mapGeoJson.getFeatures();
        if (features == null || features.size() <= 0) {
            return;
        }
        //添加单个头像
        for (MapGeoJson.FeaturesBean featuresBean : features) {
            MapGeoJson.FeaturesBean.PropertiesBeanX properties = featuresBean.getProperties();
            imageMap.put(properties.getId(), BitmapUtils.getBitmapFromDrawable(MyApplication.getInstance().getResources().getDrawable(R.drawable.mapbox_marker_icon_default)));
        }
        loadedMapStyle.addImages(imageMap);
        geoJsonSource.setGeoJson(FeatureCollection.fromJson(mapGeoJson.crateJson()));
    }
    //endregion

    //region 删除

    /**
     * 删除源
     */
    public void removeAllLayers() {
        imageMap.clear();
        if (geoJsonSource != null) {
            geoJsonSource.setGeoJson(FeatureCollection.fromFeatures(new Feature[]{}));
        }
    }
    //endregion
}



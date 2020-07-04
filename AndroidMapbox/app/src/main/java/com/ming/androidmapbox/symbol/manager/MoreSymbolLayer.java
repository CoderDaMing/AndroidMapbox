package com.ming.androidmapbox.symbol.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;

import com.google.gson.JsonElement;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.ming.androidmapbox.R;
import com.ming.androidmapbox.ToastUtil;
import com.ming.androidmapbox.symbol.bean.CustomBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.mapbox.mapboxsdk.style.layers.Property.ICON_ANCHOR_BOTTOM;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAnchor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

/**
 * 类描述:一次性添加多个标记
 */
public class MoreSymbolLayer {
    private static final String TAG = "MoreSymbolLayer";

    private static final String MORE_SOURCE_ID = "more_source_id";
    private static final String MORE_LAYER_ID = "more_layer_id";
    private static final String MORE_PROPERTY_ID = "more_property-id";

    private GeoJsonSource geoJsonSource = null;
    private List<Feature> featureList = new ArrayList<>();
    private HashMap<String, Bitmap> imageMap = new HashMap<>();

    private Context mContext;
    private MapboxMap mMapBoxMap;
    private Style mStyle;

    public MoreSymbolLayer(Context context, MapboxMap mapBoxMap, Style style) {
        this.mContext = context.getApplicationContext();
        this.mMapBoxMap = mapBoxMap;
        this.mStyle = style;
        initSource();
    }

    private void initSource() {
        //1.添加源
        FeatureCollection deviceFeatureCollection = FeatureCollection.fromFeatures(featureList);
        geoJsonSource = new GeoJsonSource(MORE_SOURCE_ID, deviceFeatureCollection);
        mStyle.addSource(geoJsonSource);
        //2.添加图片资源
        mStyle.addImages(imageMap);
        //3.添加Layer
        mStyle.addLayer(new SymbolLayer(MORE_LAYER_ID, MORE_SOURCE_ID)
                .withProperties(
                        /* show image with id title based on the value of the name feature property */
                        iconImage("{" + MORE_PROPERTY_ID + "}"),

                        /* set anchor of icon to bottom-left */
                        iconAnchor(ICON_ANCHOR_BOTTOM),

                        /* all info window and marker image to appear at the same time*/
                        iconAllowOverlap(true)
                ));
    }

    /**
     * 批量添加标记
     *
     * @param customBeanList
     */
    public void addMoreToMap(List<CustomBean> customBeanList) {
        if (customBeanList != null && customBeanList.size() > 0) {
            removeAllLayers();

            for (CustomBean cb : customBeanList) {
                double cbId = cb.getId();
                double cbLat = cb.getLatitude();
                double cbLng = cb.getLongitude();
                //feature
                Feature feature = Feature.fromGeometry(Point.fromLngLat(cbLng, cbLat));
                feature.addStringProperty(MORE_PROPERTY_ID, String.valueOf(cbId));
                featureList.add(feature);
                //ImageMap
                imageMap.put(String.valueOf(cbId), BitmapFactory.decodeResource(mContext.getResources(), R.drawable.mapbox_marker_icon_default));
            }
            //刷新数据源
            mStyle.addImages(imageMap);
            geoJsonSource.setGeoJson(FeatureCollection.fromFeatures(featureList));
        }
    }

    /**
     * 修改图片
     */
    public void changeMapBitmap(Bitmap bitmap) {
        if (featureList != null && featureList.size() > 0) {
            for (Feature feature : featureList) {
                JsonElement featureProperty = feature.getProperty(MORE_PROPERTY_ID);
                String customId = featureProperty.getAsString();
                imageMap.put(customId, bitmap);
            }
            mStyle.addImages(imageMap);
        }
    }

    /**
     * 清除所有标记
     */
    public void removeAllLayers() {
        featureList.clear();
        imageMap.clear();
        if (geoJsonSource != null) {
            geoJsonSource.setGeoJson(FeatureCollection.fromFeatures(new ArrayList<>()));
        }
    }

    public boolean handleClickLayer(LatLng point) {
        try {
            PointF pointF = mMapBoxMap.getProjection().toScreenLocation(point);
            List<Feature> featureList = mMapBoxMap.queryRenderedFeatures(pointF, MORE_LAYER_ID);
            if (featureList != null && featureList.size() > 0) {
                Feature feature = featureList.get(0);
                JsonElement featureProperty = feature.getProperty(MORE_PROPERTY_ID);
                String customId = featureProperty.getAsString();
                ToastUtil.show("点到了SymbolLayer Id:" + customId);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}



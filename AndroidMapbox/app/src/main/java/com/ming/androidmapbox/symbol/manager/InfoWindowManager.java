package com.ming.androidmapbox.symbol.manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.annotations.BubbleLayout;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.ming.androidmapbox.MyApplication;
import com.ming.androidmapbox.R;
import com.ming.androidmapbox.symbol.bean.InfoBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.mapbox.mapboxsdk.style.expressions.Expression.eq;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.layers.Property.ICON_ANCHOR_BOTTOM;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAnchor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

/**
 * 类描述:InfoWindow管理类
 */
public class InfoWindowManager {
    private static final String GEOJSON_SOURCE_ID = "GEOJSON_SOURCE_ID";
    private static final String MARKER_IMAGE_ID = "MARKER_IMAGE_ID";
    private static final String MARKER_LAYER_ID = "MARKER_LAYER_ID";
    private static final String CALLOUT_LAYER_ID = "CALLOUT_LAYER_ID";
    private static final String PROPERTY_SELECTED = "selected";
    private static final String PROPERTY_NAME = "name";
    private static final String PROPERTY_CAPITAL = "capital";

    private Style loadedStyle;
    private MapboxMap mapboxMap;

    private GeoJsonSource source;
    private FeatureCollection featureCollection;
    private final List<Feature> featureList = new ArrayList<>();
    private final HashMap<String, Bitmap> imageMap = new HashMap<>();

    public InfoWindowManager(MapboxMap mapboxMap, Style style) {
        this.mapboxMap = mapboxMap;
        this.loadedStyle = style;
        setupSource(style);
        setUpImage(style);
        setUpMarkerLayer(style);
        setUpInfoWindowLayer(style);
    }

    /**
     * 将GeoJSON源添加到地图
     */
    private void setupSource(@NonNull Style loadedStyle) {
        featureCollection = FeatureCollection.fromFeatures(featureList);
        source = new GeoJsonSource(GEOJSON_SOURCE_ID, featureCollection);
        loadedStyle.addSource(source);
    }

    /**
     * 将标记图像添加到地图中，以用作SymbolLayer图标
     */
    private void setUpImage(@NonNull Style loadedStyle) {
        loadedStyle.addImage(MARKER_IMAGE_ID, BitmapFactory.decodeResource(
                MyApplication.getInstance().getResources(), R.drawable.mapbox_marker_icon_default));
        loadedStyle.addImages(imageMap);
    }

    /**
     * 设置带有mark图标的图层
     */
    private void setUpMarkerLayer(@NonNull Style loadedStyle) {
        loadedStyle.addLayer(new SymbolLayer(MARKER_LAYER_ID, GEOJSON_SOURCE_ID)
                .withProperties(
                        iconImage(MARKER_IMAGE_ID),
                        iconAllowOverlap(true),
                        iconOffset(new Float[]{0f, -10f})
                ));
    }

    /**
     * 使用Android SDK标注设置图层
     * <p>
     * Feature name用作iconImage的键
     * </ p>
     */
    private void setUpInfoWindowLayer(@NonNull Style loadedStyle) {
        loadedStyle.addLayer(new SymbolLayer(CALLOUT_LAYER_ID, GEOJSON_SOURCE_ID)
                .withProperties(
                        /* 根据名称要素属性的值显示具有ID标题的图像 */
                        iconImage("{" + PROPERTY_NAME + "}"),

                        /* 将图标的锚点设置为左下 */
                        iconAnchor(ICON_ANCHOR_BOTTOM),

                        /* 所有信息窗口和标记图像同时显示*/
                        iconAllowOverlap(true),

                        /* 将信息窗口偏移到标记上方*/
                        iconOffset(new Float[]{-2f, -38f})
                )
                /* 添加过滤器以仅在选定要素属性为true时显示 */
                .withFilter(eq((get(PROPERTY_SELECTED)), literal(true))));
    }

    public void addMarkersToMap(List<InfoBean> infoBeanList) {
        featureList.clear();
        for (InfoBean infoBean : infoBeanList) {
            Feature singleFeature = Feature.fromGeometry(Point.fromLngLat(infoBean.getLongitude(), infoBean.getLatitude()));
            singleFeature.addBooleanProperty(PROPERTY_SELECTED, true);
            singleFeature.addStringProperty(PROPERTY_NAME, infoBean.getName());
            singleFeature.addStringProperty(PROPERTY_CAPITAL, infoBean.getCapital());
            //添加进featureList
            featureList.add(singleFeature);
            //添加进imageMap
            imageMap.put(infoBean.getName(), generateBitmap(singleFeature));
        }
        //用addImages更快，因为每个位图分别调用addImage。
        loadedStyle.addImages(imageMap);
        //重新赋值featureCollection
        featureCollection = FeatureCollection.fromFeatures(featureList);
        //更新source
        source.setGeoJson(featureCollection);
    }

    /**
     * 自定义View转Bitmap
     *
     * @param singleFeature Feature
     * @return Bitmap
     */
    private Bitmap generateBitmap(Feature singleFeature) {
        BubbleLayout bubbleLayout = (BubbleLayout)
                LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.symbol_layer_info_window_layout_callout, null);

        TextView titleTextView = bubbleLayout.findViewById(R.id.info_window_title);
        titleTextView.setText(singleFeature.getStringProperty(PROPERTY_NAME));

        TextView descriptionTextView = bubbleLayout.findViewById(R.id.info_window_description);
        descriptionTextView.setText(String.format("Capital: %1$s", singleFeature.getStringProperty(PROPERTY_CAPITAL)));

        int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        bubbleLayout.measure(measureSpec, measureSpec);

        float measuredWidth = bubbleLayout.getMeasuredWidth();

        bubbleLayout.setArrowPosition(measuredWidth / 2 - 5);

        return SymbolGenerator.generate(bubbleLayout);
    }

    /**
     * 此方法处理SymbolLayer符号的click事件。
     * <p>
     * 单击SymbolLayer图标时，我们将该功能移至选定状态。
     * </ p>
     *
     * @param screenPoint 点击屏幕上的点
     *                         
     */
    public boolean handleClickIcon(PointF screenPoint) {
        //是否点击到了Marker
        List<Feature> features = mapboxMap.queryRenderedFeatures(screenPoint, MARKER_LAYER_ID);
        if (!features.isEmpty()) {
            //针对marker点击infoWindow改变状态
            String name = features.get(0).getStringProperty(PROPERTY_NAME);
            List<Feature> featureList = featureCollection.features();
            if (featureList != null) {
                for (int i = 0; i < featureList.size(); i++) {
                    Feature feature = featureList.get(i);
                    if (feature.getStringProperty(PROPERTY_NAME).equals(name)) {
                        if (featureSelectStatus(i)) {
                            setFeatureSelectState(feature, false);
                        } else {
                            setFeatureSelectState(feature, true);
                        }
                    }
                }
            }
            return true;
        } else {
            //点击其他区域infoWindow消失
            List<Feature> featureList = featureCollection.features();
            if (featureList != null) {
                for (int i = 0; i < featureList.size(); i++) {
                    Feature feature = featureList.get(i);
                    if (featureSelectStatus(i)) {
                        setFeatureSelectState(feature, false);
                    }
                }
            }
            return false;
        }
    }


    /**
     * 选择feature的状态
     *
     * @param feature       要选择的功能
     * @param selectedState 选择状态
     *                           
     */
    private void setFeatureSelectState(Feature feature, boolean selectedState) {
        if (feature.properties() != null) {
            feature.properties().addProperty(PROPERTY_SELECTED, selectedState);
            source.setGeoJson(featureCollection);
        }
    }

    /**
     * 检查feature的“selected”属性是true还是false
     *
     * @param index 在FeatureCollection的功能列表中特定功能的索引位置。
     * @return true 如果“selected”为true。 如果boolean属性为false，则为False。
     *      
     */
    private boolean featureSelectStatus(int index) {
        if (featureCollection == null) {
            return false;
        }
        return featureCollection.features().get(index).getBooleanProperty(PROPERTY_SELECTED);
    }
}



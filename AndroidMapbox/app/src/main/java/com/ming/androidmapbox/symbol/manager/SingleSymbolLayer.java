package com.ming.androidmapbox.symbol.manager;

import android.graphics.drawable.Drawable;

import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAnchor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

/**
 * 类描述:一个标记
 * <p>
 * 可自行扩展
 */
public class SingleSymbolLayer {
    //Source的id 不可重复
    private static final String SINGLE_SOURCE_ID = "single_source_id";
    //SymbolLayer的id 不可重复
    private static final String SINGLE_LAYER_ID = "single_layer_id";
    //图片资源的id
    private static final String SINGLE_IMAGE_ID = "single_image_id";

    private Style singleStyle;
    private GeoJsonSource singleSource;

    private SingleSymbolLayer() {

    }

    private SingleSymbolLayer(Style style, Drawable drawable, double lat, double lng) {
        singleStyle = style;
        singleSource = initSingleSource(style, lat, lng);
        initSingleLayer(style, drawable);
    }

    /**
     * 初始化添加源
     *
     * @param style
     * @param lat
     * @param lng
     * @return
     */
    private GeoJsonSource initSingleSource(Style style, double lat, double lng) {
        GeoJsonSource source = new GeoJsonSource(SINGLE_SOURCE_ID);
        source.setGeoJson(Point.fromLngLat(lng, lat));
        style.addSource(source);
        return source;
    }

    /**
     * 初始化添加SymbolLayer
     */
    private void initSingleLayer(Style style, Drawable drawable) {
        style.addImage(SINGLE_IMAGE_ID, drawable);
        SymbolLayer symbolLayer = new SymbolLayer(SINGLE_LAYER_ID, SINGLE_SOURCE_ID)
                .withProperties(
                        /* show image with id title based on the value of the name feature property */
                        iconImage(SINGLE_IMAGE_ID),
                        /* set anchor of icon to bottom */
                        iconAnchor(Property.ICON_ANCHOR_BOTTOM),
                        /* all info window and marker image to appear at the same time*/
                        iconAllowOverlap(true)
                );
        style.addLayer(symbolLayer);
    }

    /**
     * 更新位置
     *
     * @param lat
     * @param lng
     */
    public void updatePosition(double lat, double lng) {
        if (singleSource != null) {
            singleSource.setGeoJson(Point.fromLngLat(lng, lat));
        }
    }

    /**
     * 删除自己
     */
    public void deleteSelf() {
        singleStyle.removeImage(SINGLE_IMAGE_ID);
        singleStyle.removeLayer(SINGLE_LAYER_ID);
        singleStyle.removeSource(SINGLE_SOURCE_ID);
        singleStyle = null;
        singleSource = null;
    }

    /**
     * 可自行扩展
     */
    public static class Builder {
        private Style style;
        private Drawable image;
        private double lat;
        private double lng;

        public SingleSymbolLayer.Builder setStyle(Style style) {
            this.style = style;
            return this;
        }

        public SingleSymbolLayer.Builder setImage(Drawable image) {
            this.image = image;
            return this;
        }

        public SingleSymbolLayer.Builder setLat(double lat) {
            this.lat = lat;
            return this;
        }

        public SingleSymbolLayer.Builder setLng(double lng) {
            this.lng = lng;
            return this;
        }

        public SingleSymbolLayer build() {
            return new SingleSymbolLayer(style, image, lat, lng);
        }
    }
}


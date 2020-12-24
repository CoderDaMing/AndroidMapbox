package com.ming.androidmapbox.titlesource;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.RasterLayer;
import com.mapbox.mapboxsdk.style.sources.RasterSource;
import com.mapbox.mapboxsdk.style.sources.TileSet;
import com.ming.androidmapbox.R;

/**
 * Adding an external Web Map Service layer to the map.
 */
public class RasterTileSourceActivity extends AppCompatActivity {

    private MapView mapView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        // This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_stamen_tiles);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {

                mapboxMap.setStyle(Style.LIGHT, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        // Add the web map source to the map
                        style.addSource(new RasterSource(
                                "web-map-source",
                                new TileSet("tileset", getAmapUrl()), 256));

                        // Create a RasterLayer with the source created above and then add the layer to the map
                        if (style.getLayer("tunnel-street-minor-low") != null) {
                            style.addLayerBelow(
                                    new RasterLayer("web-map-layer", "web-map-source"), "tunnel-street-minor-low");
                        } else {
                            style.addLayer(new RasterLayer("web-map-layer", "web-map-source"));
                        }
                    }
                });
            }

            /**
             *   // mt(0—3) Google地图使用了四个服务地址
             *         // lyrs=
             *         // m：路线图
             *         // t：地形图
             *         // p：带标签的地形图
             *         // s：卫星图
             *         // y：带标签的卫星图
             *         // h：标签层（路名、地名等）
             */
            private String getGoogleUrl() {
                return "https://mt1.google.cn/maps/vt?lyrs=s%40721&hl=zh-CN&gl=CN&x={x}&y={y}&z={z}";
            }

            private String getStamenTileUrl() {
                return "https://stamen-tiles.a.ssl.fastly.net/watercolor/{z}/{x}/{y}.jpg";
            }

            /**
             *  // wprd0{1-4} Amap地图使用了四个服务地址
             *         // scl=1&style=7 为矢量图（含路网和注记）
             *         // scl=2&style=7 为矢量图（含路网但不含注记）
             *         // scl=1&style=6 为影像底图（不含路网，不含注记）
             *         // scl=2&style=6 为影像底图（不含路网、不含注记）
             *         // scl=1&style=8 为影像路图（含路网，含注记）
             *         // scl=2&style=8 为影像路网（含路网，不含注记）
             */
            private String getAmapUrl() {
                return "http://wprd01.is.autonavi.com/appmaptile?lang=zh_cn&size=1&scl=1&style=6&x={x}&y={y}&z={z}";
            }
        });
    }

    @Override
    protected void onResume() {
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
    protected void onPause() {
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
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}

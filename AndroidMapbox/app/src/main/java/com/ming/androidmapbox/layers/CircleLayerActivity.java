package com.ming.androidmapbox.layers;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.mapbox.mapboxsdk.style.layers.CircleLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.ming.androidmapbox.R;

import java.util.ArrayList;
import java.util.List;

public class CircleLayerActivity extends AppCompatActivity {
    private static final String CIRCLE_SOURCE_ID = "circle-source-id";
    private static final String CIRCLE_LAYER_ID = "circle-layer-id";

    private MapView mapView;
    private MapboxMap mapboxMap;
    private List<Point> routeCoordinates;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        setContentView(R.layout.activity_circle_layer);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                CircleLayerActivity.this.mapboxMap = mapboxMap;
                mapboxMap.setStyle(Style.LIGHT, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        initCoordinates();
                        initCircleSource(style);
                        initLayer(style);
                    }
                });
            }
        });
    }

    private void initCoordinates() {
        routeCoordinates = new ArrayList<>();
        routeCoordinates.add(Point.fromLngLat(-77.044211, 38.852924));

        routeCoordinates.add(Point.fromLngLat(-77.044232, 38.862326));

        routeCoordinates.add(Point.fromLngLat(-77.039936, 38.867698));

        routeCoordinates.add(Point.fromLngLat(-77.04264, 38.872528));

        routeCoordinates.add(Point.fromLngLat(-77.032309, 38.87937));

        routeCoordinates.add(Point.fromLngLat(-77.027645, 38.881779));

        routeCoordinates.add(Point.fromLngLat(-77.028054, 38.887449));

        routeCoordinates.add(Point.fromLngLat(-77.03364, 38.892108));
        ;
    }

    private void initCircleSource(@NonNull Style style) {
        FeatureCollection featureCollection = FeatureCollection.fromFeatures(
                new Feature[]{Feature.fromGeometry(LineString.fromLngLats(routeCoordinates))});

        GeoJsonSource circleGeoJsonSource = new GeoJsonSource(CIRCLE_SOURCE_ID, featureCollection);
        style.addSource(circleGeoJsonSource);
    }

    private void initLayer(Style style) {
        CircleLayer circleLayer = new CircleLayer(CIRCLE_LAYER_ID, CIRCLE_SOURCE_ID).withProperties(
                /*
                 *circle-blur
                 * Paint property. Optional number. Defaults to 0. Supports feature-state and interpolateexpressions. Transitionable.
                 * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
                 */
                PropertyFactory.circleBlur(0f),
                /*
                 *circle-color
                 * Paint property. Optional color. Defaults to "#000000". Supports feature-state and interpolateexpressions. Transitionable.
                 * The fill color of the circle.
                 */
                PropertyFactory.circleColor(Color.GREEN),
                /*
                 *circle-opacity
                 * Paint property. Optional number between 0 and 1 inclusive. Defaults to 1. Supports feature-state and interpolateexpressions. Transitionable.
                 * The opacity at which the circle will be drawn.
                 */
                PropertyFactory.circleOpacity(1f),
                /*
                 *circle-pitch-alignment
                 * Paint property. Optional enum. One of "map", "viewport". Defaults to "viewport".
                 * Orientation of circle when map is pitched.
                 *
                 * "map":
                 * The circle is aligned to the plane of the map.
                 *
                 * "viewport":
                 * The circle is aligned to the plane of the viewport.
                 */
                PropertyFactory.circlePitchAlignment(Property.CIRCLE_PITCH_ALIGNMENT_VIEWPORT),
                /*
                 *circle-pitch-scale
                 * Paint property. Optional enum. One of "map", "viewport". Defaults to "map".
                 * Controls the scaling behavior of the circle when the map is pitched.
                 *
                 * "map":
                 * Circles are scaled according to their apparent distance to the camera.
                 *
                 * "viewport":
                 * Circles are not scaled.
                 */
                PropertyFactory.circlePitchScale(Property.CIRCLE_PITCH_SCALE_MAP),
                /*
                 *circle-radius
                 * Paint property. Optional number greater than or equal to 0. Units in pixels. Defaults to 5. Supports feature-state and interpolateexpressions. Transitionable.
                 * Circle radius.
                 */
                PropertyFactory.circleRadius(8f),
                /*
                 *circle-sort-key
                 * Layout property. Optional number.
                 * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
                 */
//                PropertyFactory.circleSortKey(),
                /*
                 *circle-stroke-color
                 * Paint property. Optional color. Defaults to "#000000". Supports feature-state and interpolateexpressions. Transitionable.
                 * The stroke color of the circle.
                 */
                PropertyFactory.circleStrokeColor(Color.parseColor("#000000")),
                /*
                 * circle-stroke-opacity
                 * Paint property. Optional number between 0 and 1 inclusive. Defaults to 1. Supports feature-state and interpolateexpressions. Transitionable.
                 * The opacity of the circle's stroke.
                 */
                PropertyFactory.circleStrokeOpacity(1f),
                /*
                 *circle-stroke-width
                 * Paint property. Optional number greater than or equal to 0. Units in pixels. Defaults to 0. Supports feature-state and interpolateexpressions. Transitionable.
                 * The width of the circle's stroke. Strokes are placed outside of the circle-radius.
                 */
                PropertyFactory.circleStrokeWidth(0f),
                /*
                 * circle-translate
                 * Paint property. Optional array of numbers. Units in pixels. Defaults to [0,0]. Supports interpolateexpressions. Transitionable.
                 * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
                 */
                PropertyFactory.circleTranslate(new Float[]{0f, 0f}),
                /*
                 *circle-translate-anchor
                 * Paint property. Optional enum. One of "map", "viewport". Defaults to "map". Requires circle-translate.
                 * Controls the frame of reference for circle-translate.
                 *
                 * "map":
                 * The circle is translated relative to the map.
                 *
                 * "viewport":
                 * The circle is translated relative to the viewport.
                 */
                PropertyFactory.circleTranslateAnchor(Property.CIRCLE_TRANSLATE_ANCHOR_MAP),
                /*
                 * visibility
                 * Layout property. Optional enum. One of "visible", "none". Defaults to "visible".
                 * Whether this layer is displayed.
                 *
                 * "visible":
                 * The layer is shown.
                 *
                 * "none":
                 * The layer is not shown.
                 */
                PropertyFactory.visibility(Property.VISIBLE));
        style.addLayer(circleLayer);
    }
}

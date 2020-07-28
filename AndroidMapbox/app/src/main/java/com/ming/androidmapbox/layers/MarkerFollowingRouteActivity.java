package com.ming.androidmapbox.layers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.ming.androidmapbox.R;

import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;

/**
 * 标记沿GeoJSON路线行驶
 */
public class MarkerFollowingRouteActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MarkerFollowingRoute";
    private static final String DOT_SOURCE_ID = "dot-source-id";
    private static final String DOT_LAYER_ID = "dot-layer-id";
    private static final String DOT_IMAGE_ID = "dot-image-id";
    private static final String LINE_SOURCE_ID = "line-source-id";
    private static final String LINE_LAYER_ID = "line-layer-id";
    private static final String LINE_BG_SOURCE_ID = "line-bg-source-id";
    private static final String LINE_BG_LAYER_ID = "line-bg-layer-id";

    private MapView mapView;

    private int count = 0;
    private Handler handler;
    private Runnable runnable;
    private GeoJsonSource dotGeoJsonSource;
    private ValueAnimator markerIconAnimator;
    private LatLng markerIconCurrentLocation;
    private List<Point> routeCoordinates;

    private boolean isFollowMode;
    private GeoJsonSource lineGeoJsonSource;
    private List<Point> followRouteCoordinates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        setContentView(R.layout.activity_lab_marker_following_route);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.LIGHT, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        initCoordinates();
                        initSources(style);
                        initBgDotLinePath(style);
                        initDotLinePath(style);
                        initDotLayer(style);

                        findViewById(R.id.btn_route).setOnClickListener(MarkerFollowingRouteActivity.this);
                        findViewById(R.id.btn_route_follow).setOnClickListener(MarkerFollowingRouteActivity.this);
                    }
                });
            }
        });
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

    private void initSources(@NonNull Style loadedMapStyle) {
        FeatureCollection featureCollection = FeatureCollection.fromFeatures(
                new Feature[]{Feature.fromGeometry(LineString.fromLngLats(routeCoordinates))});
        dotGeoJsonSource = new GeoJsonSource(DOT_SOURCE_ID, featureCollection);
        loadedMapStyle.addSource(dotGeoJsonSource);
        lineGeoJsonSource = new GeoJsonSource(LINE_SOURCE_ID, featureCollection);
        loadedMapStyle.addSource(lineGeoJsonSource);
        GeoJsonSource lineBgGeoJsonSource = new GeoJsonSource(LINE_BG_SOURCE_ID, featureCollection);
        loadedMapStyle.addSource(lineBgGeoJsonSource);
    }

    private void initBgDotLinePath(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addLayer(new LineLayer(LINE_BG_LAYER_ID, LINE_BG_SOURCE_ID).withProperties(
                lineColor(Color.GRAY),
                lineWidth(10f)
        ));
    }

    private void initDotLinePath(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addLayer(new LineLayer(LINE_LAYER_ID, LINE_SOURCE_ID).withProperties(
                lineColor(Color.parseColor("#F13C6E")),
                lineWidth(4f)
        ));
    }

    private void initDotLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage(DOT_IMAGE_ID, getResources().getDrawable(R.drawable.pink_dot));

        loadedMapStyle.addLayer(new SymbolLayer(DOT_LAYER_ID, DOT_SOURCE_ID).withProperties(
                iconImage(DOT_IMAGE_ID),
                iconSize(1f),
                iconIgnorePlacement(true),
                iconAllowOverlap(true)
        ));
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        // When the activity is resumed we restart the marker animating.
        if (handler != null && runnable != null) {
            handler.post(runnable);
        }
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
        // Check if the marker is currently animating and if so, we pause the animation so we aren't
        // using resources when the activities not in view.
        if (handler != null && runnable != null) {
            handler.removeCallbacksAndMessages(null);
        }
        if (markerIconAnimator != null) {
            markerIconAnimator.cancel();
        }
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

    @Override
    public void onClick(View v) {
        if (markerIconAnimator != null && markerIconAnimator.isRunning()) {
            return;
        }
        resetParams();
        switch (v.getId()) {
            case R.id.btn_route:
                initRunnable(false);
                break;

            case R.id.btn_route_follow:
                initRunnable(true);
                break;

            default:
                break;
        }
    }

    /**
     * Set up the repeat logic for moving the icon along the route.
     */
    private void initRunnable(final boolean isFollow) {
        isFollowMode = isFollow;
        // Animating the marker requires the use of both the ValueAnimator and a handler.
        // The ValueAnimator is used to move the marker between the GeoJSON points, this is
        // done linearly. The handler is used to move the marker along the GeoJSON points.
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // Check if we are at the end of the points list, if so we want to stop using
                // the handler.
                if ((routeCoordinates.size() - 1 > count)) {

                    Point nextLocation = routeCoordinates.get(count + 1);

                    if (markerIconAnimator != null && markerIconAnimator.isStarted()) {
                        markerIconCurrentLocation = (LatLng) markerIconAnimator.getAnimatedValue();
                        markerIconAnimator.cancel();
                    }
                    LatLng firstLocation = new LatLng(routeCoordinates.get(0).latitude(), routeCoordinates.get(0).longitude());
                    markerIconAnimator = ObjectAnimator
                            .ofObject(AnimalCons.LATLNG_EVALUATOR, count == 0 || markerIconCurrentLocation == null
                                            ? firstLocation
                                            : markerIconCurrentLocation,
                                    new LatLng(nextLocation.latitude(), nextLocation.longitude()))
                            .setDuration(500);
                    markerIconAnimator.setInterpolator(new LinearInterpolator());

                    markerIconAnimator.addUpdateListener(animatorUpdateListener);
                    markerIconAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            //复位
                            resetParams();
                        }
                    });
                    markerIconAnimator.start();

                    // Keeping the current point count we are on.
                    count++;

                    // Once we finish we need to repeat the entire process by executing the
                    // handler again once the ValueAnimator is finished.
                    handler.postDelayed(this, 500);
                } else {
                    //复位
                    resetParams();
                }
            }
        };
        handler.post(runnable);
    }

    private void resetParams() {
        count = 0;
        markerIconCurrentLocation = null;
        isFollowMode = false;
        followRouteCoordinates.clear();
    }

    /**
     * Listener interface for when the ValueAnimator provides an updated value
     */
    private final ValueAnimator.AnimatorUpdateListener animatorUpdateListener =
            new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    LatLng animatedPosition = (LatLng) valueAnimator.getAnimatedValue();
                    Point point = Point.fromLngLat(
                            animatedPosition.getLongitude(), animatedPosition.getLatitude());
                    if (dotGeoJsonSource != null) {
                        dotGeoJsonSource.setGeoJson(point);
                    }
                    if (isFollowMode) {
                        if (lineGeoJsonSource != null) {
                            followRouteCoordinates.add(point);
                            FeatureCollection featureCollection = FeatureCollection.fromFeatures(
                                    new Feature[]{Feature.fromGeometry(LineString.fromLngLats(followRouteCoordinates))});
                            lineGeoJsonSource.setGeoJson(featureCollection);
                        }
                    }
                }
            };
}
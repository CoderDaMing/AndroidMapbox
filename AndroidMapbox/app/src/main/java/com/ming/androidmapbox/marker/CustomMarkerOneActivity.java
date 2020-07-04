package com.ming.androidmapbox.marker;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;

import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.ming.androidmapbox.MapActivity;

public class CustomMarkerOneActivity extends MapActivity {

    private Marker testMarker;

    @Override
    public void onMapLoaded() {
        super.onMapLoaded();

        testMarker = mapboxMap.addMarker(new MarkerOptions()
                .position(mapboxMap.getCameraPosition().target)
                .title("title")
                .snippet("snippet"));
        moveMarker(testMarker, new LatLng(0, 0));
    }

    private void moveMarker(Marker testMarker, LatLng latLng) {
        if (testMarker != null) {
            //1.api实现
            CameraPosition position = new CameraPosition.Builder()
                    .target(latLng) // Sets the new camera position
                    .build(); // Creates a CameraPosition from the builder

            mapboxMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(position), 5000);
            //2.参考官方的动画移动marker实现
            animalMarker(testMarker, latLng);
        }
    }

    /**
     * 动画移动marker
     *
     * @param marker
     * @param point
     */
    public void animalMarker(Marker marker, LatLng point) {
        if (marker != null) {
            ValueAnimator markerAnimator = ObjectAnimator.ofObject(marker, "position",
                    new LatLngEvaluator(), marker.getPosition(), point);
            markerAnimator.setDuration(5000);
            markerAnimator.start();
        }
    }

    /**
     * 移动marker的position动画
     */
    private static class LatLngEvaluator implements TypeEvaluator<LatLng> {
        // Method is used to interpolate the marker animation.

        private LatLng latLng = new LatLng();

        @Override
        public LatLng evaluate(float fraction, LatLng startValue, LatLng endValue) {
            latLng.setLatitude(startValue.getLatitude()
                    + ((endValue.getLatitude() - startValue.getLatitude()) * fraction));
            latLng.setLongitude(startValue.getLongitude()
                    + ((endValue.getLongitude() - startValue.getLongitude()) * fraction));
            return latLng;
        }
    }
}

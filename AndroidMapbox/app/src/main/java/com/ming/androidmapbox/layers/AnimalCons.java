package com.ming.androidmapbox.layers;

import android.animation.TypeEvaluator;

import com.mapbox.mapboxsdk.geometry.LatLng;

public class AnimalCons {
    // Class is used to interpolate the marker animation.
    public static final TypeEvaluator<LatLng> LATLNG_EVALUATOR = new TypeEvaluator<LatLng>() {

        private final LatLng latLng = new LatLng();

        @Override
        public LatLng evaluate(float fraction, LatLng startValue, LatLng endValue) {
            latLng.setLatitude(startValue.getLatitude()
                    + ((endValue.getLatitude() - startValue.getLatitude()) * fraction));
            latLng.setLongitude(startValue.getLongitude()
                    + ((endValue.getLongitude() - startValue.getLongitude()) * fraction));
            return latLng;
        }
    };
}

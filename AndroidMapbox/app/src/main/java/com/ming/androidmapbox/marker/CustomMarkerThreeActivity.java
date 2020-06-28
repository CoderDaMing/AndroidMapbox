package com.ming.androidmapbox.marker;

import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.ming.androidmapbox.MapActivity;

public class CustomMarkerThreeActivity extends MapActivity {
    @Override
    public void onMapLoaded() {
        super.onMapLoaded();
        Icon icon = IconFactory.getInstance(this).defaultMarker();
        double  latitude = mapboxMap.getCameraPosition().target.getLatitude();
        double longitude = mapboxMap.getCameraPosition().target.getLongitude();

        LatLng latlng = new LatLng(latitude, longitude);
        CustomMarkerOptions markerOptions = new CustomMarkerOptions();
        markerOptions
                .icon(icon)
                .position(latlng);
        CustomMarker customMarker = (CustomMarker) mapboxMap.addMarker(markerOptions);
    }
}

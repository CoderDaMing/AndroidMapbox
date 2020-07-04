package com.ming.androidmapbox.marker;

import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.ming.androidmapbox.MapActivity;

public class CustomMarkerThreeActivity extends MapActivity {
    @Override
    public void onMapLoaded() {
        super.onMapLoaded();
        CustomMarkerOptions markerOptions = new CustomMarkerOptions();
        markerOptions
                .title("CustomMarker")
                .snippet("with object tag")
                .icon(IconFactory.getInstance(this).defaultMarker())
                .position(mapboxMap.getCameraPosition().target);
        CustomMarker customMarker = (CustomMarker) mapboxMap.addMarker(markerOptions);
        customMarker.setTag("object tag");
        customMarker.showInfoWindow(mapboxMap, mapView);
    }
}

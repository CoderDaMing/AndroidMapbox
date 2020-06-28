package com.ming.androidmapbox.marker;

import com.mapbox.mapboxsdk.annotations.BaseMarkerOptions;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.Marker;

public class CustomMarker extends Marker {
    Icon iconBig;

    /**
     * Creates a instance of {@link Marker} using the builder of Marker.
     *
     * @param baseMarkerOptions The builder used to construct the Marker.
     */
    public CustomMarker(BaseMarkerOptions baseMarkerOptions) {
        super(baseMarkerOptions);
    }

    public Icon getIconBig() {
        return iconBig;
    }

    public void setIconBig(Icon iconBig) {
        this.iconBig = iconBig;
    }
}
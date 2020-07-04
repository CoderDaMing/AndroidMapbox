package com.ming.androidmapbox.marker;

import com.mapbox.mapboxsdk.annotations.BaseMarkerOptions;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.Marker;

public class CustomMarker extends Marker {
    private Icon iconBig;
    private Object tag;

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

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
}
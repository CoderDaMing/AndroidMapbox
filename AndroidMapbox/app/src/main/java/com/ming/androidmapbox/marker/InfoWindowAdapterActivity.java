package com.ming.androidmapbox.marker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.ming.androidmapbox.MapActivity;
import com.ming.androidmapbox.MyApplication;
import com.ming.androidmapbox.R;

public class InfoWindowAdapterActivity extends MapActivity {
    @Override
    public void onMapLoaded() {
        super.onMapLoaded();
        mapboxMap.addMarker(new MarkerOptions()
                .title("title")
                .snippet("snippet")
                .icon(IconFactory.getInstance(MyApplication.getInstance()).fromResource(R.drawable.mapbox_info_icon_default))
//                .anchor(0.5f, 0.5f)
                .position(new LatLng(39, 119)));
        mapboxMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(this));
    }

    public class CustomInfoWindowAdapter implements MapboxMap.InfoWindowAdapter {
        private Context mContext;

        public CustomInfoWindowAdapter(Context context) {
            this.mContext = context;
        }

        @Nullable
        @Override
        public View getInfoWindow(@NonNull Marker marker) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.custom_info_contents, null);
            ImageView custom_marker_title = (ImageView) view.findViewById(R.id.custom_marker_title);
            TextView custom_marker_snippet = (TextView) view.findViewById(R.id.custom_marker_snippet);
            custom_marker_title.setImageResource(R.drawable.mapbox_logo_icon);
            custom_marker_snippet.setText("我上面有个图片");
            return view;
        }
    }
}

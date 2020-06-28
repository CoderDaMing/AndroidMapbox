package com.ming.androidmapbox.symbol;

import android.graphics.Color;
import android.util.Log;

import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.ming.androidmapbox.MapActivity;

import java.util.List;

public class HideMapNameActivity extends MapActivity {
    @Override
    public void onMapLoaded() {
        super.onMapLoaded();
        //这里就是我们需要的style
        List<Layer> layers = style.getLayers();
        for (Layer lay:layers) {
            //设置Layer文字颜色透明
            lay.setProperties(
                    PropertyFactory.textColor(Color.TRANSPARENT)
            );
            lay.setProperties(
                    PropertyFactory.textHaloColor(Color.TRANSPARENT)
            );
            //打印所有LayerId
            Log.d("Style==>" , "LayerId: " + lay.getId());
        }
    }
}

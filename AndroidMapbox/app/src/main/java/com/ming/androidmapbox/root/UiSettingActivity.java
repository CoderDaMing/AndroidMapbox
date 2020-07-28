package com.ming.androidmapbox.root;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import com.mapbox.mapboxsdk.maps.UiSettings;
import com.ming.androidmapbox.MapActivity;
import com.ming.androidmapbox.R;

import java.util.Random;

public class UiSettingActivity extends MapActivity implements View.OnClickListener {

    private int[] attributionColors;
    private Button btn_AllGesturesEnabled, btn_RotateGesturesEnabled, btn_ScrollGesturesEnabled, btn_TiltGesturesEnabled, btn_ZoomGesturesEnabled, btn_DoubleTapGesturesEnabled, btn_HorizontalScrollGesturesEnabled, btn_QuickZoomGesturesEnabled, btn_enable_attribution, btn_random_change_attribution, btn_enable_compass, btn_random_change_compass;

    @Override
    public void onMapLoaded() {
        //手势
        btn_AllGesturesEnabled = findViewById(R.id.btn_AllGesturesEnabled);
        btn_AllGesturesEnabled.setOnClickListener(this);

        btn_RotateGesturesEnabled = findViewById(R.id.btn_RotateGesturesEnabled);
        btn_RotateGesturesEnabled.setOnClickListener(this);

        btn_ScrollGesturesEnabled = findViewById(R.id.btn_ScrollGesturesEnabled);
        btn_ScrollGesturesEnabled.setOnClickListener(this);

        btn_TiltGesturesEnabled = findViewById(R.id.btn_TiltGesturesEnabled);
        btn_TiltGesturesEnabled.setOnClickListener(this);

        btn_ZoomGesturesEnabled = findViewById(R.id.btn_ZoomGesturesEnabled);
        btn_ZoomGesturesEnabled.setOnClickListener(this);

        btn_DoubleTapGesturesEnabled = findViewById(R.id.btn_DoubleTapGesturesEnabled);
        btn_DoubleTapGesturesEnabled.setOnClickListener(this);

        btn_HorizontalScrollGesturesEnabled = findViewById(R.id.btn_HorizontalScrollGesturesEnabled);
        btn_HorizontalScrollGesturesEnabled.setOnClickListener(this);

        btn_QuickZoomGesturesEnabled = findViewById(R.id.btn_QuickZoomGesturesEnabled);
        btn_QuickZoomGesturesEnabled.setOnClickListener(this);


        //归因（带圈i图标）Attribution
        attributionColors = new int[]{
                Color.RED,
                Color.BLUE,
                Color.YELLOW,
                ContextCompat.getColor(this, R.color.colorAccent), // a runtime app UI color
                ContextCompat.getColor(this, R.color.colorPrimaryDark), // a runtime app UI color
                Color.parseColor("#1e7019"), // dark green
        };
        btn_enable_attribution = findViewById(R.id.btn_enable_attribution);
        btn_enable_attribution.setOnClickListener(this);

        btn_random_change_attribution = findViewById(R.id.btn_random_change_attribution);
        btn_random_change_attribution.setOnClickListener(this);

        //指南针
        btn_enable_compass = findViewById(R.id.btn_enable_compass);
        btn_enable_compass.setOnClickListener(this);

        btn_random_change_compass = findViewById(R.id.btn_random_change_compass);
        btn_random_change_compass.setOnClickListener(this);

        //将地图视图重置为朝北
        findViewById(R.id.btn_resetNorth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapboxMap.resetNorth();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ui_setting;
    }

    @Override
    public void onClick(View v) {
        UiSettings uiSettings = mapboxMap.getUiSettings();
        switch (v.getId()) {
            case R.id.btn_AllGesturesEnabled:
                boolean allGesturesEnabled = !uiSettings.areAllGesturesEnabled();
                uiSettings.setAllGesturesEnabled(allGesturesEnabled);
                btn_AllGesturesEnabled.setText("启用所有手势" + allGesturesEnabled);
                break;
            case R.id.btn_RotateGesturesEnabled:
                boolean rotateGesturesEnabled = !uiSettings.isRotateGesturesEnabled();
                uiSettings.setRotateGesturesEnabled(rotateGesturesEnabled);
                btn_RotateGesturesEnabled.setText("启用旋转手势" + rotateGesturesEnabled);
                break;
            case R.id.btn_ScrollGesturesEnabled:
                boolean scrollGesturesEnabled = !uiSettings.isScrollGesturesEnabled();
                uiSettings.setScrollGesturesEnabled(scrollGesturesEnabled);
                btn_ScrollGesturesEnabled.setText("启用滚动手势" + scrollGesturesEnabled);
                break;
            case R.id.btn_TiltGesturesEnabled:
                boolean tiltGesturesEnabled = !uiSettings.isTiltGesturesEnabled();
                uiSettings.setTiltGesturesEnabled(tiltGesturesEnabled);
                btn_TiltGesturesEnabled.setText("启用倾斜手势" + tiltGesturesEnabled);
                break;
            case R.id.btn_ZoomGesturesEnabled:
                boolean zoomGesturesEnabled = !uiSettings.isZoomGesturesEnabled();
                uiSettings.setZoomGesturesEnabled(zoomGesturesEnabled);
                btn_ZoomGesturesEnabled.setText("启用缩放手势" + zoomGesturesEnabled);
                break;
            case R.id.btn_DoubleTapGesturesEnabled:
                boolean doubleTapGesturesEnabled = !uiSettings.isDoubleTapGesturesEnabled();
                uiSettings.setDoubleTapGesturesEnabled(doubleTapGesturesEnabled);
                btn_DoubleTapGesturesEnabled.setText("启用双击手势" + doubleTapGesturesEnabled);
                break;
            case R.id.btn_HorizontalScrollGesturesEnabled:
                boolean horizontalScrollGesturesEnabled = !uiSettings.isHorizontalScrollGesturesEnabled();
                uiSettings.setHorizontalScrollGesturesEnabled(horizontalScrollGesturesEnabled);
                btn_HorizontalScrollGesturesEnabled.setText("启用水平滚动手势" + horizontalScrollGesturesEnabled);
                break;
            case R.id.btn_QuickZoomGesturesEnabled:
                boolean quickZoomGesturesEnabled = !uiSettings.isQuickZoomGesturesEnabled();
                uiSettings.setQuickZoomGesturesEnabled(quickZoomGesturesEnabled);
                btn_QuickZoomGesturesEnabled.setText("启用快速缩放手势" + quickZoomGesturesEnabled);
                break;
            default:
                break;
            case R.id.btn_enable_attribution:
                boolean attributionEnabled = !uiSettings.isAttributionEnabled();
                uiSettings.setAttributionEnabled(attributionEnabled);
                btn_enable_attribution.setText("启用归因" + attributionEnabled);
                break;
            case R.id.btn_random_change_attribution:
                int intColorAttribution = new Random().nextInt(attributionColors.length);
                uiSettings.setAttributionTintColor(attributionColors[intColorAttribution]);
                uiSettings.setAttributionMargins(uiSettings.getAttributionMarginLeft() + intColorAttribution, uiSettings.getAttributionMarginTop(), uiSettings.getAttributionMarginRight(), uiSettings.getAttributionMarginBottom());
                uiSettings.setAttributionGravity(Gravity.BOTTOM);
                break;
            case R.id.btn_enable_compass:
                boolean isCompassEnabled = !uiSettings.isCompassEnabled();
                uiSettings.setCompassEnabled(isCompassEnabled);
                btn_enable_compass.setText("启用指南针" + isCompassEnabled);
                break;
            case R.id.btn_random_change_compass:
                int intColorCompass = new Random().nextInt(attributionColors.length);
                //true 指向北时不消失
                uiSettings.setCompassFadeFacingNorth(!uiSettings.isCompassFadeWhenFacingNorth());
                uiSettings.setCompassGravity(Gravity.TOP | Gravity.END);
                if (uiSettings.isCompassFadeWhenFacingNorth()) {
                    uiSettings.setCompassImage(getResources().getDrawable(R.drawable.atm_symbol_icon));
                } else {
                    uiSettings.setCompassImage(getResources().getDrawable(R.drawable.mapbox_compass_icon));
                }
                uiSettings.setCompassMargins(uiSettings.getCompassMarginLeft(), uiSettings.getCompassMarginTop() + intColorCompass, uiSettings.getCompassMarginRight(), uiSettings.getCompassMarginBottom());
                break;
        }
    }
}

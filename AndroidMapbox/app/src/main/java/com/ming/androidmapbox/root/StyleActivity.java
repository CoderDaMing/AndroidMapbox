package com.ming.androidmapbox.root;

import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.mapbox.mapboxsdk.maps.Style;
import com.ming.androidmapbox.MapActivity;
import com.ming.androidmapbox.R;

public class StyleActivity extends MapActivity {

    private static final String[] STYLE_ARRAY_KEY = {"Style.MAPBOX_STREETS", "Style.DARK", "Style.LIGHT", "Style.OUTDOORS", "Style.SATELLITE", "Style.SATELLITE_STREETS", "Style.TRAFFIC_DAY", "Style.TRAFFIC_NIGHT"};
    private static final String[] STYLE_ARRAY_VALUE = {Style.MAPBOX_STREETS, Style.DARK, Style.LIGHT, Style.OUTDOORS, Style.SATELLITE, Style.SATELLITE_STREETS, Style.TRAFFIC_DAY, Style.TRAFFIC_NIGHT};
    private AlertDialog alertStyleDialog;
    private int lastChoice;

    @Override
    public void onMapLoaded() {

        findViewById(R.id.btn_change_style).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertStyleDialog != null && alertStyleDialog.isShowing()) {
                    alertStyleDialog.dismiss();
                    alertStyleDialog = null;
                }
                alertStyleDialog = new AlertDialog.Builder(StyleActivity.this)
                        .setTitle("切换Style")
                        .setSingleChoiceItems(STYLE_ARRAY_KEY, lastChoice, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertStyleDialog.dismiss();
                                lastChoice = which;
                                setStyle(STYLE_ARRAY_VALUE[which], new Style.OnStyleLoaded() {
                                    @Override
                                    public void onStyleLoaded(@NonNull Style style) {
                                        Toast.makeText(StyleActivity.this, "Style已切换", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .create();
                alertStyleDialog.show();
            }
        });
        //从指定的构建器加载新的地图样式
        findViewById(R.id.btn_style_builder_local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 引用资产文件夹中的本地JSON样式文件，并将其作为字符串参数传递
                setStyle(new Style.Builder().fromUri("asset://local_style_file.json"), new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        Toast.makeText(StyleActivity.this, "已切换为本地JSON样式文件", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findViewById(R.id.btn_style_builder_mapbox_studio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //使用自定义的Mapbox托管样式。
                setStyle(new Style.Builder().fromUri("mapbox://styles/mapbox/cj3kbeqzo00022smj7akz3o1e"), new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        Toast.makeText(StyleActivity.this, "已切换为自定义的Mapbox托管样式", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_style;
    }

    /**
     * 使用内置Style
     *
     * @param style         Style
     * @param onStyleLoaded Style.OnStyleLoaded
     */
    public void setStyle(@Style.StyleUrl String style, final Style.OnStyleLoaded onStyleLoaded) {
        mapboxMap.setStyle(style, onStyleLoaded);
    }

    /**
     * 从指定的构建器加载新的地图样式
     *
     * @param builder       Style.Builder
     * @param onStyleLoaded Style.OnStyleLoaded
     */
    public void setStyle(Style.Builder builder, Style.OnStyleLoaded onStyleLoaded) {
        mapboxMap.setStyle(builder, onStyleLoaded);
    }

    /**
     * 获取Style
     */
    @Nullable
    public Style getStyle() {
        return mapboxMap.getStyle();
    }

    /**
     * 异步获取地图的Style
     */
    public void getStyle(@NonNull Style.OnStyleLoaded onStyleLoaded) {
        mapboxMap.getStyle(onStyleLoaded);
    }
}

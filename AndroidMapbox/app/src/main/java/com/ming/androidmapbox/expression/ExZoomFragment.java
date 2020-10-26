package com.ming.androidmapbox.expression;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.ming.androidmapbox.R;

import static com.mapbox.mapboxsdk.style.expressions.Expression.color;
import static com.mapbox.mapboxsdk.style.expressions.Expression.exponential;
import static com.mapbox.mapboxsdk.style.expressions.Expression.interpolate;
import static com.mapbox.mapboxsdk.style.expressions.Expression.stop;
import static com.mapbox.mapboxsdk.style.expressions.Expression.zoom;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;

public class ExZoomFragment extends Fragment {


    public ExZoomFragment() {
        // Required empty public constructor
    }

    public static ExZoomFragment newInstance() {
        ExZoomFragment fragment = new ExZoomFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ex_zoom, container, false);
        showZoom(view);
        return view;
    }

    /**
     * 获取当前的缩放级别。请注意，在样式布局和绘画属性中，[“ zoom”]只能作为顶级“ step”或“ interpolate”表达式的输入出现。
     * <p>
     * ["zoom"]: number
     */
    private void showZoom(View view) {
//            Expression.zoom();
        FillLayer fillLayer = new FillLayer("layer-id", "source-id");
        fillLayer.setProperties(
                fillColor(
                        interpolate(
                                exponential(0.5f), zoom(),
                                stop(1.0f, color(Color.RED)),
                                stop(5.0f, color(Color.BLUE)),
                                stop(10.0f, color(Color.GREEN))
                        )
                )
        );

        //设置文字---------------------------------------------------------------------------------------------------------------------------------
        TextView tv_ex_title = view.findViewById(R.id.tv_ex_title);
        TextView tv_ex_msg = view.findViewById(R.id.tv_ex_msg);
        tv_ex_title.setText("获取当前的缩放级别。请注意，在样式布局和绘画属性中，[“ zoom”]只能作为顶级“ step”或“ interpolate”表达式的输入出现。");
        tv_ex_msg.setText("  FillLayer fillLayer = new FillLayer(\"layer-id\", \"source-id\");\n" +
                "        fillLayer.setProperties(\n" +
                "                fillColor(\n" +
                "                        interpolate(\n" +
                "                                exponential(0.5f), zoom(),\n" +
                "                                stop(1.0f, color(Color.RED)),\n" +
                "                                stop(5.0f, color(Color.BLUE)),\n" +
                "                                stop(10.0f, color(Color.GREEN))\n" +
                "                        )\n" +
                "                )\n" +
                "        );");
    }
}

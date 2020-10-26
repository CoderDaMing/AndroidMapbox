package com.ming.androidmapbox.expression;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mapbox.mapboxsdk.style.layers.HeatmapLayer;
import com.ming.androidmapbox.R;

import static com.mapbox.mapboxsdk.style.expressions.Expression.heatmapDensity;
import static com.mapbox.mapboxsdk.style.expressions.Expression.interpolate;
import static com.mapbox.mapboxsdk.style.expressions.Expression.linear;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.rgb;
import static com.mapbox.mapboxsdk.style.expressions.Expression.rgba;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.heatmapColor;

public class ExHeatmapFragment extends Fragment {


    public ExHeatmapFragment() {
        // Required empty public constructor
    }

    public static ExHeatmapFragment newInstance() {
        ExHeatmapFragment fragment = new ExHeatmapFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ex_heatmap, container, false);
        showHeatmap(view);
        return view;
    }

    /**
     * heatmap-density
     * 获取热图图层中像素的内核密度估计，这是在特定像素周围拥挤多少数据点的相对度量。只能在heatmap-color属性中使用。
     * <p>
     * ["heatmap-density"]: number
     */
    private void showHeatmap(View view) {
        //            Expression.heatmapDensity();
        HeatmapLayer layer = new HeatmapLayer("layer-id", "source-id");
        layer.setProperties(
                heatmapColor(interpolate(linear(), heatmapDensity(),
                        literal(0), rgba(33, 102, 172, 0),
                        literal(0.2), rgb(103, 169, 207),
                        literal(0.4), rgb(209, 229, 240),
                        literal(0.6), rgb(253, 219, 199),
                        literal(0.8), rgb(239, 138, 98),
                        literal(1), rgb(178, 24, 43))
                )
        );

        //设置文字---------------------------------------------------------------------------------------------------------------------------------
        TextView tv_ex_title = view.findViewById(R.id.tv_ex_title);
        TextView tv_ex_msg = view.findViewById(R.id.tv_ex_msg);
        tv_ex_title.setText("heatmap-density 获取热图图层中像素的内核密度估计，这是在特定像素周围拥挤多少数据点的相对度量。只能在heatmap-color属性中使用。");
        tv_ex_msg.setText(" HeatmapLayer layer = new HeatmapLayer(\"layer-id\", \"source-id\");\n" +
                "        layer.setProperties(\n" +
                "                heatmapColor(interpolate(linear(), heatmapDensity(),\n" +
                "                        literal(0), rgba(33, 102, 172, 0),\n" +
                "                        literal(0.2), rgb(103, 169, 207),\n" +
                "                        literal(0.4), rgb(209, 229, 240),\n" +
                "                        literal(0.6), rgb(253, 219, 199),\n" +
                "                        literal(0.8), rgb(239, 138, 98),\n" +
                "                        literal(1), rgb(178, 24, 43))\n" +
                "                )\n" +
                "        );");
    }
}

package com.ming.androidmapbox.expression;

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

import static com.mapbox.mapboxsdk.style.expressions.Expression.rgb;
import static com.mapbox.mapboxsdk.style.expressions.Expression.rgba;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;

public class ExColorFragment extends Fragment implements View.OnClickListener {
    private TextView tv_ex_title;
    private TextView tv_ex_msg;

    public ExColorFragment() {
        // Required empty public constructor
    }

    public static ExColorFragment newInstance() {
        ExColorFragment fragment = new ExColorFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ex_color, container, false);
        tv_ex_title = view.findViewById(R.id.tv_ex_title);
        tv_ex_msg = view.findViewById(R.id.tv_ex_msg);

        View viewColorRgb = view.findViewById(R.id.color_rgb);
        viewColorRgb.setOnClickListener(this);
        onClick(viewColorRgb);

        view.findViewById(R.id.color_rgba).setOnClickListener(this);
        view.findViewById(R.id.color_to_rgba).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.color_rgb) {
            /**
             * rgb
             * 从红色，绿色和蓝色分量创建颜色值，该值必须在0到255之间，并且alpha分量为1。如果任何分量超出范围，则表达式为错误。
             */
//          Expression.rgb();
            FillLayer fillLayer = new FillLayer("layer-id", "source-id");
            fillLayer.setProperties(
                    fillColor(
                            rgb(255.0f, 255.0f, 255.0f)
                    )
            );
            setText("rgb从红色，绿色和蓝色分量创建颜色值，该值必须在0到255之间，并且alpha分量为1。如果任何分量超出范围，则表达式为错误。",
                    " FillLayer fillLayer = new FillLayer(\"layer-id\", \"source-id\");\n" +
                    "            fillLayer.setProperties(\n" +
                    "                    fillColor(\n" +
                    "                            rgb(255.0f, 255.0f, 255.0f)\n" +
                    "                    )\n" +
                    "            );");
        } else if (viewId == R.id.color_rgba) {
            /**
             * rgba
             * 从必须在0到255之间的红色，绿色，蓝色分量和必须在0到1之间的alpha分量创建颜色值。如果任何分量超出范围，则表达式为错误。
             *
             * ["rgba", number, number, number, number]: color
             */
//          Expression.rgba();
            FillLayer fillLayer2 = new FillLayer("layer-id", "source-id");
            fillLayer2.setProperties(
                    fillColor(
                            rgba(255.0f, 255.0f, 255.0f, 1.0f)
                    )
            );
            setText("rgba从必须在0到255之间的红色，绿色，蓝色分量和必须在0到1之间的alpha分量创建颜色值。如果任何分量超出范围，则表达式为错误。",
                    "FillLayer fillLayer2 = new FillLayer(\"layer-id\", \"source-id\");\n" +
                    "            fillLayer2.setProperties(\n" +
                    "                    fillColor(\n" +
                    "                            rgba(255.0f, 255.0f, 255.0f, 1.0f)\n" +
                    "                    )\n" +
                    "            );");
        } else if (viewId == R.id.color_to_rgba) {
            /**
             * to-rgba
             * 返回一个四元素的数组，该数组包含输入颜色的红色，绿色，蓝色和Alpha分量（按此顺序）。
             *
             * ["to-rgba", color]: array<number, 4>
             */
//            Expression.toRgba();
            setText("to-rgba 返回一个四元素的数组，该数组包含输入颜色的红色，绿色，蓝色和Alpha分量（按此顺序）。","Expression.toRgba();");
        }
    }

    private void setText(String title, String msg){
        //设置文字---------------------------------------------------------------------------------------------------------------------------------
       tv_ex_title.setText(title);
       tv_ex_msg.setText(msg);
    }
}

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

import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.CircleLayer;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.ming.androidmapbox.R;

import static com.mapbox.mapboxsdk.style.expressions.Expression.color;
import static com.mapbox.mapboxsdk.style.expressions.Expression.exponential;
import static com.mapbox.mapboxsdk.style.expressions.Expression.interpolate;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.step;
import static com.mapbox.mapboxsdk.style.expressions.Expression.stop;
import static com.mapbox.mapboxsdk.style.expressions.Expression.zoom;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleRadius;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;

public class ExRampsScalesCurvesFragment extends Fragment implements View.OnClickListener {
    private TextView tv_ex_title;
    private TextView tv_ex_msg;

    public ExRampsScalesCurvesFragment() {
        // Required empty public constructor
    }

    public static ExRampsScalesCurvesFragment newInstance() {
        ExRampsScalesCurvesFragment fragment = new ExRampsScalesCurvesFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ex_ramps_scales_curves, container, false);

        tv_ex_title = view.findViewById(R.id.tv_ex_title);
        tv_ex_msg = view.findViewById(R.id.tv_ex_msg);

        View viewInterpolate = view.findViewById(R.id.interpolate);
        viewInterpolate.setOnClickListener(this);
        viewInterpolate.callOnClick();

        view.findViewById(R.id.step).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.interpolate) {
            /**
             * interpolate
             * 通过在成对的输入和输出值（“停止”）之间进行插值来产生连续，平滑的结果。
             * 输入可以是任何数字表达式（例如[“ get”，“ population”]）。
             * 停止输入必须是严格按升序排列的数字文字。
             * 输出类型必须为数字，数组<数字>或颜色。
             *
             *插补类型:
             *
             * ["linear"]: 在这两个停止点之间线性地插值，该插值刚好小于输入，而刚大于输入。
             * ["exponential", base]: 在停靠点之间以指数方式插值，该插值刚好小于输入并大于输入。
             * base控制输出增加的速率：值越高，输出越接近范围的高端。值接近1时，输出线性增加。
             * ["cubic-bezier", x1, y1, x2, y2]: 使用给定控制点定义的三次贝塞尔曲线进行插值。
             *
             * ["interpolate",
             *     interpolation: ["linear"] | ["exponential", base] | ["cubic-bezier", x1, y1, x2, y2],
             *     input: number,
             *     stop_input_1: number, stop_output_1: OutputType,
             *     stop_input_n: number, stop_output_n: OutputType, ...
             * ]: OutputType (number, array<number>, or Color)
             */
//            Expression.interpolate();
            FillLayer fillLayer = new FillLayer("layer-id", "source-id");
            fillLayer.setProperties(
                    fillColor(
                            interpolate(exponential(0.5f), zoom(),
                                    stop(1.0f, color(Color.RED)),
                                    stop(5.0f, color(Color.BLUE)),
                                    stop(10.0f, color(Color.GREEN)
                                    )
                            )
                    )
            );
            setText("   interpolate\n" +
                            "通过在成对的输入和输出值（“停止”）之间进行插值来产生连续，平滑的结果。\n" +
                            "输入可以是任何数字表达式（例如[“ get”，“ population”]）。\n" +
                            "停止输入必须是严格按升序排列的数字文字。\n" +
                            "输出类型必须为数字，数组<数字>或颜色。\n" +
                            "\n" +
                            "插补类型:\n" +
                            "\n" +
                            "[\"linear\"]: 在这两个停止点之间线性地插值，该插值刚好小于输入，而刚大于输入。\n" +
                            "[\"exponential\", base]: 在停靠点之间以指数方式插值，该插值刚好小于输入并大于输入。\n" +
                            "base控制输出增加的速率：值越高，输出越接近范围的高端。值接近1时，输出线性增加。\n" +
                            " [\"cubic-bezier\", x1, y1, x2, y2]: 使用给定控制点定义的三次贝塞尔曲线进行插值。",
                    "FillLayer fillLayer = new FillLayer(\"layer-id\", \"source-id\");\n" +
                            "            fillLayer.setProperties(\n" +
                            "                    fillColor(\n" +
                            "                            interpolate(exponential(0.5f), zoom(),\n" +
                            "                                    stop(1.0f, color(Color.RED)),\n" +
                            "                                    stop(5.0f, color(Color.BLUE)),\n" +
                            "                                    stop(10.0f, color(Color.GREEN)\n" +
                            "                                    )\n" +
                            "                            )\n" +
                            "                    )\n" +
                            "            );");
        } else if (viewId == R.id.step) {
            /**
             * step
             * 通过评估由成对的输入和输出值（“停止”）定义的分段常数函数，可以产生离散的步进结果。
             * 输入可以是任何数字表达式（例如[“ get”，“ population”]）。
             * 停止输入必须是严格按升序排列的数字文字。
             * 返回止损的输出值刚好小于输入值；如果输入小于第一个止损，则返回第一个输出。
             *
             * ["step",
             *     input: number,
             *     stop_output_0: OutputType,
             *     stop_input_1: number, stop_output_1: OutputType,
             *     stop_input_n: number, stop_output_n: OutputType, ...
             * ]: OutputType
             */
//            Expression.step();
            CircleLayer circleLayer = new CircleLayer("layer-id", "source-id");
            circleLayer.setProperties(
                    circleRadius(
                            step(zoom(), literal(0.0f),
                                    literal(1.0f), literal(2.5f),
                                    literal(10.0f), literal(5.0f))
                    )
            );
            setText(" step 通过评估由成对的输入和输出值（“停止”）定义的分段常数函数，可以产生离散的步进结果。\n" +
                            "输入可以是任何数字表达式（例如[“ get”，“ population”]）。\n" +
                            "停止输入必须是严格按升序排列的数字文字。\n" +
                            "返回止损的输出值刚好小于输入值；如果输入小于第一个止损，则返回第一个输出。",
                    "  CircleLayer circleLayer = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                            "            circleLayer.setProperties(\n" +
                            "                    circleRadius(\n" +
                            "                            step(zoom(), literal(0.0f),\n" +
                            "                                    literal(1.0f), literal(2.5f),\n" +
                            "                                    literal(10.0f), literal(5.0f))\n" +
                            "                    )\n" +
                            "            );");
        }
    }


    private void setText(String title, String msg){
        //设置文字---------------------------------------------------------------------------------------------------------------------------------
        tv_ex_title.setText(title);
        tv_ex_msg.setText(msg);
    }
}

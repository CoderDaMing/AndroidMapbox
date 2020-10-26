package com.ming.androidmapbox.expression;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.CircleLayer;
import com.ming.androidmapbox.R;
import com.ming.androidmapbox.util.DialogUtil;

import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.style.expressions.Expression.abs;
import static com.mapbox.mapboxsdk.style.expressions.Expression.acos;
import static com.mapbox.mapboxsdk.style.expressions.Expression.asin;
import static com.mapbox.mapboxsdk.style.expressions.Expression.atan;
import static com.mapbox.mapboxsdk.style.expressions.Expression.ceil;
import static com.mapbox.mapboxsdk.style.expressions.Expression.cos;
import static com.mapbox.mapboxsdk.style.expressions.Expression.division;
import static com.mapbox.mapboxsdk.style.expressions.Expression.e;
import static com.mapbox.mapboxsdk.style.expressions.Expression.floor;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.ln;
import static com.mapbox.mapboxsdk.style.expressions.Expression.ln2;
import static com.mapbox.mapboxsdk.style.expressions.Expression.log10;
import static com.mapbox.mapboxsdk.style.expressions.Expression.log2;
import static com.mapbox.mapboxsdk.style.expressions.Expression.max;
import static com.mapbox.mapboxsdk.style.expressions.Expression.min;
import static com.mapbox.mapboxsdk.style.expressions.Expression.mod;
import static com.mapbox.mapboxsdk.style.expressions.Expression.pi;
import static com.mapbox.mapboxsdk.style.expressions.Expression.pow;
import static com.mapbox.mapboxsdk.style.expressions.Expression.product;
import static com.mapbox.mapboxsdk.style.expressions.Expression.round;
import static com.mapbox.mapboxsdk.style.expressions.Expression.sin;
import static com.mapbox.mapboxsdk.style.expressions.Expression.sqrt;
import static com.mapbox.mapboxsdk.style.expressions.Expression.subtract;
import static com.mapbox.mapboxsdk.style.expressions.Expression.sum;
import static com.mapbox.mapboxsdk.style.expressions.Expression.tan;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleRadius;

public class ExMathFragment extends Fragment implements AdapterView.OnItemClickListener {
    private List<String> mathList = new ArrayList<>();

    public ExMathFragment() {
        // Required empty public constructor
    }

    public static ExMathFragment newInstance() {
        ExMathFragment fragment = new ExMathFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ex_math, container, false);
        RecyclerView rv_math_list = view.findViewById(R.id.rv_math_list);
        rv_math_list.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayout.VERTICAL));

        mathList.add("+");
        mathList.add("-");
        mathList.add("*");
        mathList.add("/");

        mathList.add("%");
        mathList.add("^");
        mathList.add("abs");

        mathList.add("sin");
        mathList.add("cos");
        mathList.add("tan");
        mathList.add("asin");
        mathList.add("acos");
        mathList.add("atan");

        mathList.add("ceil");
        mathList.add("floor");
        mathList.add("sqrt");
        mathList.add("round");
        mathList.add("min");
        mathList.add("max");

        mathList.add("e");
        mathList.add("pi");

        mathList.add("ln");
        mathList.add("ln2");
        mathList.add("log2");
        mathList.add("log10");

        mathList.add("distance");
        ExStringAdapter exStringAdapter = new ExStringAdapter(requireContext(), mathList);
        rv_math_list.setAdapter(exStringAdapter);
        exStringAdapter.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String type = mathList.get(position);
        switch (type) {
            case "+":
                /**
                 * 返回输入的总和。
                 *
                 * ["+", number, number, ...]: number
                 */
//                Expression sum(@Size(min = 2) Expression... numbers);
                CircleLayer circleLayer1 = new CircleLayer("layer-id", "source-id");
                circleLayer1.setProperties(
                        circleRadius(sum(literal(10.0f), ln2(), pi()))
                );
                DialogUtil.show(getActivity(),"+返回输入的总和。"+
                        "\n\r"
                        +" CircleLayer circleLayer1 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer1.setProperties(\n" +
                        "                        circleRadius(sum(literal(10.0f), ln2(), pi()))\n" +
                        "                );");
                break;
            case "-":
                /**
                 * 对于两个输入，返回从第一个输入减去第二个输入的结果。对于单个输入，返回从0减去它的结果。
                 *
                 * ["-", number, number]: number
                 * ["-", number]: number
                 */
//                Expression subtract(@NonNull Expression number);
                CircleLayer circleLayer2 = new CircleLayer("layer-id", "source-id");
                circleLayer2.setProperties(
                        circleRadius(subtract(literal(10.0f), pi()))
                );
                DialogUtil.show(getActivity(),"-对于两个输入，返回从第一个输入减去第二个输入的结果。对于单个输入，返回从0减去它的结果。"+
                        "\n\r"
                        +"CircleLayer circleLayer2 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer2.setProperties(\n" +
                        "                        circleRadius(subtract(literal(10.0f), pi()))\n" +
                        "                );");
                break;
            case "*":
                /**
                 * 返回输入的乘积。
                 *
                 * ["*", number, number, ...]: number
                 */
//                Expression product(@Size(min = 2) Expression... numbers);
                CircleLayer circleLayer3 = new CircleLayer("layer-id", "source-id");
                circleLayer3.setProperties(
                        circleRadius(product(literal(10.0f), ln2()))
                );
                DialogUtil.show(getActivity(),"*返回输入的乘积。"+
                        "\n\r"
                        +" CircleLayer circleLayer3 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer3.setProperties(\n" +
                        "                        circleRadius(product(literal(10.0f), ln2()))\n" +
                        "                );");
                break;
            case "/":
                /**
                 *返回第一个输入除以第二个输入的浮点数的结果。
                 *
                 * ["/", number, number]: number
                 */
//                Expression division(@NonNull Expression first, @NonNull Expression second);
                CircleLayer circleLayer4 = new CircleLayer("layer-id", "source-id");
                circleLayer4.setProperties(
                        circleRadius(division(literal(10.0f), pi()))
                );
                DialogUtil.show(getActivity(),"/返回第一个输入除以第二个输入的浮点数的结果。"+
                        "\n\r"
                        +"  CircleLayer circleLayer4 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer4.setProperties(\n" +
                        "                        circleRadius(division(literal(10.0f), pi()))\n" +
                        "                );");
                break;
//---------------------------------------------------------------------------------------------------
            case "%":
                /**
                 * 返回第一个输入除以第二个输入的整数后的余数。
                 *
                 * ["%", number, number]: number
                 */
//                Expression mod(@NonNull Expression first, @NonNull Expression second);
                CircleLayer circleLayer5 = new CircleLayer("layer-id", "source-id");
                circleLayer5.setProperties(
                        circleRadius(mod(literal(10.0f), pi()))
                );
                DialogUtil.show(getActivity(),"%返回第一个输入除以第二个输入的整数后的余数。"+
                        "\n\r"
                        +"  CircleLayer circleLayer5 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer5.setProperties(\n" +
                        "                        circleRadius(mod(literal(10.0f), pi()))\n" +
                        "                );");
                break;
            case "^":
                /**
                 * 返回将第一个输入提高到第二个指定的幂的结果。
                 *
                 * ["^", number, number]: number
                 */
//                Expression pow(@NonNull Expression first, @NonNull Expression second) ;
                CircleLayer circleLayer6 = new CircleLayer("layer-id", "source-id");
                circleLayer6.setProperties(
                        circleRadius(pow(pi(), literal(2.0f)))
                );
                DialogUtil.show(getActivity(),"^返回将第一个输入提高到第二个指定的幂的结果。"+
                        "\n\r"
                        +"   CircleLayer circleLayer6 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer6.setProperties(\n" +
                        "                        circleRadius(pow(pi(), literal(2.0f)))\n" +
                        "                );");
                break;
            case "abs":
                /**
                 *返回输入的绝对值。
                 *
                 * ["abs", number]: number
                 */
//                Expression abs(@NonNull Number number)''
                CircleLayer circleLayer7 = new CircleLayer("layer-id", "source-id");
                circleLayer7.setProperties(
                        circleRadius(abs(subtract(pi())))
                );
                DialogUtil.show(getActivity(),"abs返回输入的绝对值。"+
                        "\n\r"
                        +"  CircleLayer circleLayer7 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer7.setProperties(\n" +
                        "                        circleRadius(abs(subtract(pi())))\n" +
                        "                );");
                break;
//---------------------------------------------------------------------------------------------------
            case "sin":
                /**
                 * 返回输入的正弦值。
                 *
                 * ["sin", number]: number
                 */
//                Expression sin(@NonNull Expression number);
                CircleLayer circleLayer8 = new CircleLayer("layer-id", "source-id");
                circleLayer8.setProperties(
                        circleRadius(sin(pi()))
                );
                DialogUtil.show(getActivity(),"sin 返回输入的正弦值。"+
                        "\n\r"
                        +" CircleLayer circleLayer8 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer8.setProperties(\n" +
                        "                        circleRadius(sin(pi()))\n" +
                        "                );");
                break;
            case "cos":
                /**
                 * 返回输入的余弦值。
                 *
                 * ["cos", number]: number
                 */
//              Expression cos(@NonNull Expression number);
                CircleLayer circleLayer9 = new CircleLayer("layer-id", "source-id");
                circleLayer9.setProperties(
                        circleRadius(cos(pi()))
                );
                DialogUtil.show(getActivity(),"cos返回输入的余弦值。"+
                        "\n\r"
                        +"  CircleLayer circleLayer9 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer9.setProperties(\n" +
                        "                        circleRadius(cos(pi()))\n" +
                        "                );");
                break;
            case "tan":
                /**
                 *返回输入的正切值。
                 *
                 * ["tan", number]: number
                 */
//              Expression tan(@NonNull Expression number);
                CircleLayer circleLayer10 = new CircleLayer("layer-id", "source-id");
                circleLayer10.setProperties(
                        circleRadius(tan(pi()))
                );
                DialogUtil.show(getActivity(),"tan返回输入的正切值"+
                        "\n\r"
                        +" CircleLayer circleLayer10 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer10.setProperties(\n" +
                        "                        circleRadius(tan(pi()))\n" +
                        "                );");
                break;
            case "asin":
                /**
                 * 返回输入的反正弦值。
                 *
                 * ["asin", number]: number
                 */
//              Expression asin(@NonNull Expression number);
                CircleLayer circleLayer11 = new CircleLayer("layer-id", "source-id");
                circleLayer11.setProperties(
                        circleRadius(asin(pi()))
                );
                DialogUtil.show(getActivity(),"asin返回输入的反正弦值。"+
                        "\n\r"
                        +"CircleLayer circleLayer11 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer11.setProperties(\n" +
                        "                        circleRadius(asin(pi()))\n" +
                        "                );");
                break;
            case "acos":
                /**
                 * 返回输入的反余弦值。
                 *
                 * ["acos", number]: number
                 */
//              Expression acos(@NonNull Expression number);
                CircleLayer circleLayer12 = new CircleLayer("layer-id", "source-id");
                circleLayer12.setProperties(
                        circleRadius(acos(pi()))
                );
                DialogUtil.show(getActivity(),"acos返回输入的反余弦值。"+
                        "\n\r"
                        +"CircleLayer circleLayer12 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer12.setProperties(\n" +
                        "                        circleRadius(acos(pi()))\n" +
                        "                );");
                break;
            case "atan":
                /**
                 *返回输入的反正切值。
                 *
                 * ["atan", number]: number
                 */
//              Expression atan(@NonNull Expression number);
                CircleLayer circleLayer13 = new CircleLayer("layer-id", "source-id");
                circleLayer13.setProperties(
                        circleRadius(atan(pi()))
                );
                DialogUtil.show(getActivity(),"atan返回输入的反正切值。"+
                        "\n\r"
                        +" CircleLayer circleLayer13 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer13.setProperties(\n" +
                        "                        circleRadius(atan(pi()))\n" +
                        "                );");
                break;
//---------------------------------------------------------------------------------------------------
            case "ceil":
                /**
                 *返回大于或等于输入的最小整数。
                 *
                 * ["ceil", number]: number
                 */
//                Expression ceil(@NonNull Number number);
                CircleLayer circleLayer14 = new CircleLayer("layer-id", "source-id");
                circleLayer14.setProperties(
                        circleRadius(ceil(3.14159265359))
                );
                DialogUtil.show(getActivity(),"ceil返回大于或等于输入的最小整数"+
                        "\n\r"
                        +" CircleLayer circleLayer14 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer14.setProperties(\n" +
                        "                        circleRadius(ceil(3.14159265359))\n" +
                        "                );");
                break;
            case "floor":
                /**
                 *返回小于或等于输入的最大整数。
                 *
                 * ["floor", number]: number
                 */
//                Expression floor(@NonNull Number number);
                CircleLayer circleLayer15 = new CircleLayer("layer-id", "source-id");
                circleLayer15.setProperties(
                        circleRadius(floor(pi()))
                );
                DialogUtil.show(getActivity(),"floor返回小于或等于输入的最大整数。"+
                        "\n\r"
                        +"CircleLayer circleLayer15 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer15.setProperties(\n" +
                        "                        circleRadius(floor(pi()))\n" +
                        "                );");
                break;
            case "sqrt":
                /**
                 *返回输入的平方根。
                 *
                 * ["sqrt", number]: number
                 */
//                Expression.sqrt(@NonNull Number number);
                CircleLayer circleLayer16 = new CircleLayer("layer-id", "source-id");
                circleLayer16.setProperties(
                        circleRadius(sqrt(25.0f))
                );
                DialogUtil.show(getActivity(),"sqrt"+
                        "\n\r"
                        +"CircleLayer circleLayer16 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer16.setProperties(\n" +
                        "                        circleRadius(sqrt(25.0f))\n" +
                        "                );");
                break;
            case "round":
                /**
                 *将输入舍入到最接近的整数。中途值从零舍入。例如，[“ round”，-1.5]计算为-2。
                 *
                 * ["round", number]: number
                 */
//                Expression round(@NonNull Number number);
                CircleLayer circleLayer17 = new CircleLayer("layer-id", "source-id");
                circleLayer17.setProperties(
                        circleRadius(round(3.14159265359f))
                );
                DialogUtil.show(getActivity(),"round将输入舍入到最接近的整数。中途值从零舍入。例如，[“ round”，-1.5]计算为-2。"+
                        "\n\r"
                        +" CircleLayer circleLayer17 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer17.setProperties(\n" +
                        "                        circleRadius(round(3.14159265359f))\n" +
                        "                );");
                break;
            case "min":
                /**
                 *返回输入的最小值。
                 *
                 * ["min", number, number, ...]: number
                 */
//                Expression min (@Size(min = 1) Number...numbers);
                CircleLayer circleLayer18 = new CircleLayer("layer-id", "source-id");
                circleLayer18.setProperties(
                        circleRadius(min(3.141, 3.14f, 3.15f))
                );
                DialogUtil.show(getActivity(),"min返回输入的最小值"+
                        "\n\r"
                        +" CircleLayer circleLayer18 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer18.setProperties(\n" +
                        "                        circleRadius(min(3.141, 3.14f, 3.15f))\n" +
                        "                );");
                break;
            case "max":
                /**
                 *返回输入的最大值。
                 *
                 * ["max", number, number, ...]: number
                 */
//                Expression max (@Size(min = 1) Number...numbers)
                CircleLayer circleLayer19 = new CircleLayer("layer-id", "source-id");
                circleLayer19.setProperties(
                        circleRadius(max(pi(), product(pi(), pi())))
                );
                DialogUtil.show(getActivity(),"max返回输入的最大值"+
                        "\n\r"
                        +" CircleLayer circleLayer19 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer19.setProperties(\n" +
                        "                        circleRadius(max(pi(), product(pi(), pi())))\n" +
                        "                );");
                break;
//---------------------------------------------------------------------------------------------------
            case "e":
                /**
                 *返回数学常数e。
                 *
                 * ["e"]: number
                 */
//                Expression.e();
                CircleLayer circleLayer20 = new CircleLayer("layer-id", "source-id");
                circleLayer20.setProperties(
                        circleRadius(product(literal(10.0f), e()))
                );
                DialogUtil.show(getActivity(),"e返回数学常数e"+
                        "\n\r"
                        +" CircleLayer circleLayer20 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer20.setProperties(\n" +
                        "                        circleRadius(product(literal(10.0f), e()))\n" +
                        "                );");
                break;
            case "pi":
                /**
                 * 返回数学常数pi。
                 *
                 * ["pi"]:number
                 */
//                Expression.pi();
                CircleLayer circleLayer21 = new CircleLayer("layer-id", "source-id");
                circleLayer21.setProperties(
                        circleRadius(product(literal(10.0f), pi()))
                );
                DialogUtil.show(getActivity(),"pi返回数学常数pi"+
                        "\n\r"
                        +"  CircleLayer circleLayer21 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer21.setProperties(\n" +
                        "                        circleRadius(product(literal(10.0f), pi()))\n" +
                        "                );");
                break;
//---------------------------------------------------------------------------------------------------
            case "ln":
                /**
                 *返回输入的自然对数。
                 *
                 * ["ln", number]: number
                 */
//                Expression ln(@NonNull Number number);
                CircleLayer circleLayer22 = new CircleLayer("layer-id", "source-id");
                circleLayer22.setProperties(
                        circleRadius(ln(10))
                );
                DialogUtil.show(getActivity(),"ln返回输入的自然对数。"+
                        "\n\r"
                        +" CircleLayer circleLayer22 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer22.setProperties(\n" +
                        "                        circleRadius(ln(10))\n" +
                        "                );");
                break;
            case "ln2":
                /**
                 * 返回数学常数ln（2）。
                 *
                 * ["ln2"]: number
                 */
//                Expression ln2();
                CircleLayer circleLayer23 = new CircleLayer("layer-id", "source-id");
                circleLayer23.setProperties(
                        circleRadius(product(literal(10.0f), ln2()))
                );
                DialogUtil.show(getActivity(),"ln2返回数学常数ln（2）"+
                        "\n\r"
                        +" CircleLayer circleLayer23 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer23.setProperties(\n" +
                        "                        circleRadius(product(literal(10.0f), ln2()))\n" +
                        "                );");
                break;
            case "log2":
                /**
                 * 返回输入的以2为底的对数。
                 *
                 * ["log2", number]: number
                 */
//                Expression log2(@NonNull Number number);
                CircleLayer circleLayer24 = new CircleLayer("layer-id", "source-id");
                circleLayer24.setProperties(
                        circleRadius(log2(2))
                );
                DialogUtil.show(getActivity(),"log2返回输入的以2为底的对数。"+
                        "\n\r"
                        +"   CircleLayer circleLayer24 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer24.setProperties(\n" +
                        "                        circleRadius(log2(2))\n" +
                        "                );");
                break;
            case "log10":
                /**
                 *返回输入的以10为底的对数。
                 *
                 * ["log10", number]: number
                 */
//                Expression log10(@NonNull Number number);
                CircleLayer circleLayer25 = new CircleLayer("layer-id", "source-id");
                circleLayer25.setProperties(
                        circleRadius(log10(10))
                );
                DialogUtil.show(getActivity(),"log10返回输入的以10为底的对数。"+
                        "\n\r"
                        +"   CircleLayer circleLayer25 = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                        "                circleLayer25.setProperties(\n" +
                        "                        circleRadius(log10(10))\n" +
                        "                );");
                break;
//---------------------------------------------------------------------------------------------------
            case "distance":
                /**
                 * 返回评估的要素和输入几何之间的最短距离（以米为单位）。
                 * 输入值可以是Point，MultiPoint，LineString，MultiLineString，Polygon，MultiPolygon，Feature
                 * 或FeatureCollection类型的有效GeoJSON。
                 * 由于编码几何的精度损失，返回的距离值的精度可能会有所不同，尤其是在缩放级别13以下。
                 *
                 * ["distance", object]: number
                 */
//                Expression distance (@NonNull GeoJson geoJson);
                DialogUtil.show(getActivity(),"distance返回评估的要素和输入几何之间的最短距离（以米为单位）。\n" +
                        "输入值可以是Point，MultiPoint，LineString，MultiLineString，Polygon，MultiPolygon，Feature\n" +
                        "或FeatureCollection类型的有效GeoJSON。\n" +
                        " 由于编码几何的精度损失，返回的距离值的精度可能会有所不同，尤其是在缩放级别13以下。"+
                        "\n\r"
                        +"  Expression distance (@NonNull GeoJson geoJson);");
                break;
            default:
                break;
        }
    }
}
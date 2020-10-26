package com.ming.androidmapbox.expression;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.ming.androidmapbox.R;
import com.ming.androidmapbox.util.DialogUtil;

import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.style.expressions.Expression.all;
import static com.mapbox.mapboxsdk.style.expressions.Expression.any;
import static com.mapbox.mapboxsdk.style.expressions.Expression.coalesce;
import static com.mapbox.mapboxsdk.style.expressions.Expression.eq;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.gt;
import static com.mapbox.mapboxsdk.style.expressions.Expression.gte;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.lt;
import static com.mapbox.mapboxsdk.style.expressions.Expression.lte;
import static com.mapbox.mapboxsdk.style.expressions.Expression.match;
import static com.mapbox.mapboxsdk.style.expressions.Expression.neq;
import static com.mapbox.mapboxsdk.style.expressions.Expression.not;
import static com.mapbox.mapboxsdk.style.expressions.Expression.rgba;
import static com.mapbox.mapboxsdk.style.expressions.Expression.switchCase;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textColor;

public class ExDecisionFragment extends Fragment implements AdapterView.OnItemClickListener {

    private List<String> decisionList = new ArrayList<>();

    public ExDecisionFragment() {
        // Required empty public constructor
    }

    public static ExDecisionFragment newInstance() {
        ExDecisionFragment fragment = new ExDecisionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ex_decision, container, false);

        RecyclerView rv_decision_list = view.findViewById(R.id.rv_decision_list);
        rv_decision_list.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayout.VERTICAL));

        decisionList.add("!");
        decisionList.add("!=");
        decisionList.add("<");
        decisionList.add("<=");
        decisionList.add("==");
        decisionList.add(">");
        decisionList.add(">=");
        decisionList.add("all");
        decisionList.add("any");
        decisionList.add("case");
        decisionList.add("coalesce");
        decisionList.add("match");
        decisionList.add("within");
        ExStringAdapter exStringAdapter = new ExStringAdapter(requireContext(), decisionList);
        rv_decision_list.setAdapter(exStringAdapter);
        exStringAdapter.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String des = decisionList.get(position);
        switch (des) {
            case "!":
                /**
                 * !
                 * 逻辑否定。如果输入为false，则返回true；如果输入为true，则返回false。
                 *
                 * ["!", boolean]: boolean
                 */
//                Expression.not();
                FillLayer fillLayer1 = new FillLayer("layer-id", "source-id");
                fillLayer1.setFilter(
                        not(get("keyToValue"))
                );
                DialogUtil.show(getActivity(),"!逻辑否定。如果输入为false，则返回true；如果输入为true，则返回false。"
                        +"\n\r"
                        +"                FillLayer fillLayer1 = new FillLayer(\"layer-id\", \"source-id\");\n" +
                        "                fillLayer1.setFilter(\n" +
                        "                        not(get(\"keyToValue\"))\n" +
                        "                );");
                break;
            case "!=":
                /**
                 *!=
                 * 如果输入值不相等，则返回true，否则返回false。比较是严格类型化的：不同运行时类型的值始终被认为是不相等的。
                 * 在解析时已知类型不同的情况将被视为无效，并会产生解析错误。
                 * 接受可选的排序规则参数来控制与语言环境有关的字符串比较。
                 *
                 * ["!=", value, value]: boolean
                 * ["!=", value, value, collator]: boolean
                 */
//                Expression.neq();
                FillLayer fillLayer2 = new FillLayer("layer-id", "source-id");
                fillLayer2.setFilter(
                        neq(get("keyToValue"), get("keyToOtherValue"))
                );
                DialogUtil.show(getActivity(),"!=如果输入值不相等，则返回true，否则返回false。比较是严格类型化的：不同运行时类型的值始终被认为是不相等的。\n" +
                        "在解析时已知类型不同的情况将被视为无效，并会产生解析错误。\n" +
                        "接受可选的排序规则参数来控制与语言环境有关的字符串比较。"
                        +"\n\r"
                        +" FillLayer fillLayer2 = new FillLayer(\"layer-id\", \"source-id\");\n" +
                        "                fillLayer2.setFilter(\n" +
                        "                        neq(get(\"keyToValue\"), get(\"keyToOtherValue\"))\n" +
                        "                );");
                break;
            case "<":
                /**
                 *<
                 * 如果第一个输入严格小于第二个输入，则返回true，否则返回false。
                 * 参数必须是字符串或数字。如果在评估过程中不是，则表达式评估会产生错误。
                 * 已知此约束在解析时不成立的情况被认为是有效的，并且会产生解析错误。
                 * 接受可选的排序规则参数来控制与语言环境有关的字符串比较。
                 *
                 * ["<", value, value]: boolean
                 * ["<", value, value, collator]: boolean
                 */
//                Expression.lt();
                FillLayer fillLayer3 = new FillLayer("layer-id", "source-id");
                fillLayer3.setFilter(
                        lt(get("keyToValue"), 2.0f)
                );
                DialogUtil.show(getActivity(),"<\n" +
                        "如果第一个输入严格小于第二个输入，则返回true，否则返回false。\n" +
                        "参数必须是字符串或数字。如果在评估过程中不是，则表达式评估会产生错误。\n" +
                        "已知此约束在解析时不成立的情况被认为是有效的，并且会产生解析错误。\n" +
                        "接受可选的排序规则参数来控制与语言环境有关的字符串比较。"
                        +"\n\r"
                        +"   FillLayer fillLayer3 = new FillLayer(\"layer-id\", \"source-id\");\n" +
                        "                fillLayer3.setFilter(\n" +
                        "                        lt(get(\"keyToValue\"), 2.0f)\n" +
                        "                );");
                break;
            case "<=":
                /**
                 *<=
                 * 如果第一个输入小于或等于第二个输入，则返回true，否则返回false。
                 * 参数必须是字符串或数字。如果在评估过程中不是，则表达式评估会产生错误。
                 * 已知此约束在解析时不成立的情况被认为是有效的，并且会产生解析错误。
                 * 接受可选的排序规则参数，以控制与语言环境有关的字符串比较。
                 *
                 * ["<=", value, value]: boolean
                 * ["<=", value, value, collator]: boolean
                 */
//                Expression.lte();
                FillLayer fillLayer4 = new FillLayer("layer-id", "source-id");
                fillLayer4.setFilter(
                        lte(get("keyToValue"), 2.0f)
                );
                DialogUtil.show(getActivity(),"<=\n" +
                        "                 * 如果第一个输入小于或等于第二个输入，则返回true，否则返回false。\n" +
                        "                 * 参数必须是字符串或数字。如果在评估过程中不是，则表达式评估会产生错误。\n" +
                        "                 * 已知此约束在解析时不成立的情况被认为是有效的，并且会产生解析错误。\n" +
                        "                 * 接受可选的排序规则参数，以控制与语言环境有关的字符串比较。"
                        +"\n\r"
                        +"  FillLayer fillLayer4 = new FillLayer(\"layer-id\", \"source-id\");\n" +
                        "                fillLayer4.setFilter(\n" +
                        "                        lte(get(\"keyToValue\"), 2.0f)\n" +
                        "                );");
                break;
            case "==":
                /**
                 *==
                 * 如果输入值相等，则返回true，否则返回false。
                 * 比较是严格类型化的：不同运行时类型的值始终被认为是不相等的。
                 * 在解析时已知类型不同的情况将被视为无效，并会产生解析错误。
                 * 接受可选的排序规则参数，以控制与语言环境有关的字符串比较。
                 *
                 * ["==", value, value]: boolean
                 * ["==", value, value, collator]: boolean
                 */
//                Expression.eq();
                FillLayer fillLayer5 = new FillLayer("layer-id", "source-id");
                fillLayer5.setFilter(
                        eq(get("keyToValue"), get("keyToOtherValue"))
                );
                DialogUtil.show(getActivity(),"==\n" +
                        "如果输入值相等，则返回true，否则返回false。\n" +
                        "比较是严格类型化的：不同运行时类型的值始终被认为是不相等的。\n" +
                        "在解析时已知类型不同的情况将被视为无效，并会产生解析错误。\n" +
                        "接受可选的排序规则参数，以控制与语言环境有关的字符串比较。"
                        +"\n\r"
                        +" FillLayer fillLayer5 = new FillLayer(\"layer-id\", \"source-id\");\n" +
                        "                fillLayer5.setFilter(\n" +
                        "                        eq(get(\"keyToValue\"), get(\"keyToOtherValue\"))\n" +
                        "                );");
                break;
            case ">":
                /**
                 *>
                 * 如果第一个输入严格大于第二个输入，则返回true，否则返回false。
                 * 参数必须是字符串或数字。如果在评估过程中不是，则表达式评估会产生错误。
                 * 已知此约束在解析时不成立的情况被认为是有效的，并且会产生解析错误。
                 * 接受可选的排序规则参数，以控制与语言环境有关的字符串比较。
                 *
                 * [">", value, value]: boolean
                 * [">", value, value, collator]: boolean
                 */
//                Expression.gt();
                FillLayer fillLayer6 = new FillLayer("layer-id", "source-id");
                fillLayer6.setFilter(
                        gt(get("keyToValue"), 2.0f)
                );
                DialogUtil.show(getActivity(),">\n" +
                        "如果第一个输入严格大于第二个输入，则返回true，否则返回false。\n" +
                        "参数必须是字符串或数字。如果在评估过程中不是，则表达式评估会产生错误。\n" +
                        "已知此约束在解析时不成立的情况被认为是有效的，并且会产生解析错误。\n" +
                        "接受可选的排序规则参数，以控制与语言环境有关的字符串比较。"
                        +"\n\r"
                        +" FillLayer fillLayer6 = new FillLayer(\"layer-id\", \"source-id\");\n" +
                        "                fillLayer6.setFilter(\n" +
                        "                        gt(get(\"keyToValue\"), 2.0f)\n" +
                        "                );");
                break;
            case ">=":
                /**
                 *>=
                 * 如果第一个输入大于或等于第二个输入，则返回true，否则返回false。
                 * 参数必须是字符串或数字。如果在评估过程中不是，则表达式评估会产生错误。
                 * 已知此约束在解析时不成立的情况被认为是有效的，并且会产生解析错误。
                 * 接受可选的排序规则参数，以控制与语言环境有关的字符串比较。
                 *
                 * [">=", value, value]: boolean
                 * [">=", value, value, collator]: boolean
                 */
//                Expression.gte();
                FillLayer fillLayer7 = new FillLayer("layer-id", "source-id");
                fillLayer7.setFilter(
                        gte(get("keyToValue"), 2.0f)
                );
                DialogUtil.show(getActivity(),">=\n" +
                        "如果第一个输入大于或等于第二个输入，则返回true，否则返回false。\n" +
                        "参数必须是字符串或数字。如果在评估过程中不是，则表达式评估会产生错误。\n" +
                        "已知此约束在解析时不成立的情况被认为是有效的，并且会产生解析错误。\n" +
                        "接受可选的排序规则参数，以控制与语言环境有关的字符串比较。"
                        +"\n\r"
                        +" FillLayer fillLayer7 = new FillLayer(\"layer-id\", \"source-id\");\n" +
                        "                fillLayer7.setFilter(\n" +
                        "                        gte(get(\"keyToValue\"), 2.0f)\n" +
                        "                );");
                break;
            case "all":
                /**
                 *all
                 * 如果所有输入均为true，则返回true，否则返回false。
                 * 输入是按顺序求值的，并且求值是短路的：输入表达式的求值为假时，结果为假，并且不再求值任何输入表达式。
                 *
                 * ["all", boolean, boolean]: boolean
                 * ["all", boolean, boolean, ...]: boolean
                 */
//                Expression.all();
                FillLayer fillLayer8 = new FillLayer("layer-id", "source-id");
                fillLayer8.setFilter(
                        all(get("keyToValue"), get("keyToOtherValue"))
                );
                DialogUtil.show(getActivity(),"all\n" +
                        "如果所有输入均为true，则返回true，否则返回false。\n" +
                        "输入是按顺序求值的，并且求值是短路的：输入表达式的求值为假时，结果为假，并且不再求值任何输入表达式。"
                        +"\n\r"
                        +" FillLayer fillLayer8 = new FillLayer(\"layer-id\", \"source-id\");\n" +
                        "                fillLayer8.setFilter(\n" +
                        "                        all(get(\"keyToValue\"), get(\"keyToOtherValue\"))\n" +
                        "                );");
                break;
            case "any":
                /**
                 *any
                 * 如果任何输入为true，则返回true，否则返回false。
                 * 输入是按顺序求值的，并且求值是短路的：一旦输入表达式的求值为真，则结果为真，并且不再对其他输入表达式求值。
                 *
                 * ["any", boolean, boolean]: boolean
                 * ["any", boolean, boolean, ...]: boolean
                 */
//                Expression.any();
                FillLayer fillLayer9 = new FillLayer("layer-id", "source-id");
                fillLayer9.setFilter(
                        any(get("keyToValue"), get("keyToOtherValue"))
                );
                DialogUtil.show(getActivity(),"any\n" +
                        "如果任何输入为true，则返回true，否则返回false。\n" +
                        "输入是按顺序求值的，并且求值是短路的：一旦输入表达式的求值为真，则结果为真，并且不再对其他输入表达式求值。\n"
                        +"\n\r"
                        +" FillLayer fillLayer9 = new FillLayer(\"layer-id\", \"source-id\");\n" +
                        "                fillLayer9.setFilter(\n" +
                        "                        any(get(\"keyToValue\"), get(\"keyToOtherValue\"))\n" +
                        "                );");
                break;
            case "case":
                /**
                 *case
                 * 选择第一个输出，其对应的测试条件评估为true，否则选择回退值。
                 *
                 * ["case",
                 *     condition: boolean, output: OutputType,
                 *     condition: boolean, output: OutputType,
                 *     ...,
                 *     fallback: OutputType
                 * ]: OutputType
                 */
//                Expression.switchCase();
                SymbolLayer symbolLayer10 = new SymbolLayer("layer-id", "source-id");
                symbolLayer10.setProperties(
                        iconSize(
                                switchCase(
                                        get("KEY_TO_BOOLEAN"), literal(3.0f),
                                        get("KEY_TO_OTHER_BOOLEAN"), literal(5.0f),
                                        literal(1.0f) // default value
                                )
                        )
                );
                DialogUtil.show(getActivity(),"case\n" +
                        "选择第一个输出，其对应的测试条件评估为true，否则选择回退值。"
                        +"\n\r"
                        +"SymbolLayer symbolLayer10 = new SymbolLayer(\"layer-id\", \"source-id\");\n" +
                        "                symbolLayer10.setProperties(\n" +
                        "                        iconSize(\n" +
                        "                                switchCase(\n" +
                        "                                        get(\"KEY_TO_BOOLEAN\"), literal(3.0f),\n" +
                        "                                        get(\"KEY_TO_OTHER_BOOLEAN\"), literal(5.0f),\n" +
                        "                                        literal(1.0f) // default value\n" +
                        "                                )\n" +
                        "                        )\n" +
                        "                );");
                break;
            case "coalesce":
                /**
                 *coalesce
                 * 依次评估每个表达式，直到获得第一个非空值，然后返回该值。
                 *
                 * ["coalesce", OutputType, OutputType, ...]: OutputType
                 */
//                Expression.coalesce();
                SymbolLayer symbolLayer11 = new SymbolLayer("layer-id", "source-id");
                symbolLayer11.setProperties(
                        textColor(
                                coalesce(
                                        get("keyToNullValue"),
                                        get("keyToNonNullValue")
                                )
                        )
                );
                DialogUtil.show(getActivity(),"coalesce\n" +
                        "依次评估每个表达式，直到获得第一个非空值，然后返回该值。"
                        +"\n\r"
                        +" SymbolLayer symbolLayer11 = new SymbolLayer(\"layer-id\", \"source-id\");\n" +
                        "                symbolLayer11.setProperties(\n" +
                        "                        textColor(\n" +
                        "                                coalesce(\n" +
                        "                                        get(\"keyToNullValue\"),\n" +
                        "                                        get(\"keyToNonNullValue\")\n" +
                        "                                )\n" +
                        "                        )\n" +
                        "                );");
                break;
            case "match":
                /**
                 *match
                 * 选择标签值与输入值匹配的输出，如果找不到匹配项，则选择回退值。
                 * 输入可以是任何表达式（例如[“ get”，“ building_type”]）。
                 * 每个标签必须是：
                 *
                 *单个文字值；或一个文字值数组，其值必须是所有字符串或所有数字（例如[100，101]或[“ c”，“ b”]）。
                 * 如果数组中的任何值匹配，则输入匹配，类似于“ in”运算符。
                 * 每个标签必须唯一。如果输入类型与标签类型不匹配，则结果将是后备值。
                 * ["match",
                 *     input: InputType (number or string),
                 *     label: InputType | [InputType, InputType, ...], output: OutputType,
                 *     label: InputType | [InputType, InputType, ...], output: OutputType,
                 *     ...,
                 *     fallback: OutputType
                 * ]: OutputType
                 */
//                Expression.match();
                SymbolLayer symbolLayer12 = new SymbolLayer("layer-id", "source-id");
                symbolLayer12.setProperties(
                        textColor(
                                match(get("keyToValue"),
                                        literal(1), rgba(255, 0, 0, 1.0f),
                                        literal(2), rgba(0, 0, 255.0f, 1.0f),
                                        rgba(0.0f, 255.0f, 0.0f, 1.0f)
                                )
                        )
                );
                DialogUtil.show(getActivity(),"match\n" +
                        "选择标签值与输入值匹配的输出，如果找不到匹配项，则选择回退值。\n" +
                        "输入可以是任何表达式（例如[“ get”，“ building_type”]）。\n" +
                        "每个标签必须是：\n" +
                        "\n" +
                        "单个文字值；或一个文字值数组，其值必须是所有字符串或所有数字（例如[100，101]或[“ c”，“ b”]）。\n" +
                        "如果数组中的任何值匹配，则输入匹配，类似于“ in”运算符。\n" +
                        "每个标签必须唯一。如果输入类型与标签类型不匹配，则结果将是后备值。"
                        +"\n\r"
                        +" SymbolLayer symbolLayer12 = new SymbolLayer(\"layer-id\", \"source-id\");\n" +
                        "                symbolLayer12.setProperties(\n" +
                        "                        textColor(\n" +
                        "                                match(get(\"keyToValue\"),\n" +
                        "                                        literal(1), rgba(255, 0, 0, 1.0f),\n" +
                        "                                        literal(2), rgba(0, 0, 255.0f, 1.0f),\n" +
                        "                                        rgba(0.0f, 255.0f, 0.0f, 1.0f)\n" +
                        "                                )\n" +
                        "                        )\n" +
                        "                );");
                break;
            case "within":
                /**
                 *within
                 * 如果评估的要素完全包含在输入几何的边界内，则返回true，否则返回false。
                 * 输入值是类型为Polygon的有效GeoJSON。
                 * 支持的评估功能：
                 *
                 * Point: 如果点在边界上或位于边界之外，则返回false。
                 * LineString: 如果线的任何部分落在边界之外，线与边界相交或线的端点在边界上，则返回false。
                 * ["within", object]: boolean
                 */
//                Expression.within(Polygon);
                DialogUtil.show(getActivity(),"within\n" +
                        "如果评估的要素完全包含在输入几何的边界内，则返回true，否则返回false。\n" +
                        "输入值是类型为Polygon的有效GeoJSON。\n" +
                        "支持的评估功能：\n" +
                        "                 *\n" +
                        "Point: 如果点在边界上或位于边界之外，则返回false。\n" +
                        "LineString: 如果线的任何部分落在边界之外，线与边界相交或线的端点在边界上，则返回false。"
                        +"\n\r"
                        +"Expression.within(Polygon);");
                break;
            default:
                break;
        }
    }
}
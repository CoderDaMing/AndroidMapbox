package com.ming.androidmapbox.expression;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.ming.androidmapbox.R;

import static com.mapbox.mapboxsdk.style.expressions.Expression.concat;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.has;
import static com.mapbox.mapboxsdk.style.expressions.Expression.interpolate;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.rgb;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineGradient;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textField;

public class ExLookUpFragment extends Fragment implements View.OnClickListener {
    private TextView tv_ex_title;
    private TextView tv_ex_msg;

    public ExLookUpFragment() {
        // Required empty public constructor
    }

    public static ExLookUpFragment newInstance() {
        ExLookUpFragment fragment = new ExLookUpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ex_look_up, container, false);

        tv_ex_title = view.findViewById(R.id.tv_ex_title);
        tv_ex_msg = view.findViewById(R.id.tv_ex_msg);

        View viewlook_up_at = view.findViewById(R.id.look_up_at);
        viewlook_up_at.setOnClickListener(this);
        viewlook_up_at.callOnClick();

        view.findViewById(R.id.look_up_get).setOnClickListener(this);
        view.findViewById(R.id.look_up_has).setOnClickListener(this);
        view.findViewById(R.id.look_up_in).setOnClickListener(this);
        view.findViewById(R.id.look_up_length).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.look_up_at:
                /**
                 * at
                 *从数组中检索一个项目。
                 *
                 * ["at", number, array]: ItemType
                 */
                Expression at = Expression.at(1, Expression.array(literal(new int[]{1, 3, 4})));
                setText("at从数组中检索一个项目。",
                        "Expression at = Expression.at(1, Expression.array(literal(new int[]{1, 3, 4})));");
                break;
            case R.id.look_up_get:
                /**
                 * get
                 * 从当前要素的属性或从另一个对象（如果提供了第二个自变量）中检索属性值。如果缺少所请求的属性，则返回null。
                 *
                 * ["get", string]: value
                 * ["get", string, object]: value
                 */
                SymbolLayer symbolLayer = new SymbolLayer("layer-id", "source-id");
                symbolLayer.setProperties(
                        textField(get("key-to-feature"))
                );
                setText(" get从当前要素的属性或从另一个对象（如果提供了第二个自变量）中检索属性值。如果缺少所请求的属性，则返回null。",
                        "SymbolLayer symbolLayer = new SymbolLayer(\"layer-id\", \"source-id\");\n" +
                                "                symbolLayer.setProperties(\n" +
                                "                        textField(get(\"key-to-feature\"))\n" +
                                "                );");
                break;
            case R.id.look_up_has:
                /**
                 * has
                 * 测试当前要素的属性中是否存在属性值，或者如果提供第二个参数，则从另一个对象中进行测试。
                 *
                 * ["has", string]: boolean
                 * ["has", string, object]: boolean
                 */
                FillLayer fillLayer = new FillLayer("layer-id", "source-id");
                fillLayer.setFilter(
                        has(get("keyToValue"))
                );
                setText("has测试当前要素的属性中是否存在属性值，或者如果提供第二个参数，则从另一个对象中进行测试。",
                        "FillLayer fillLayer = new FillLayer(\"layer-id\", \"source-id\");\n" +
                                "                fillLayer.setFilter(\n" +
                                "                        has(get(\"keyToValue\"))\n" +
                                "                );");
                break;
            case R.id.look_up_in:
                /**
                 * in
                 * 确定数组中是否存在项目或字符串中是否存在子字符串。
                 *
                 * ["in",
                 *     keyword: InputType (boolean, string, or number),
                 *     input: InputType (array or string)
                 * ]: boolean
                 *
                 * Expression.in(Expression, Expression)
                 * Expression.in(Number, Expression)
                 * Expression.in(String, Expression)
                 */
                Expression.in(1, Expression.array(Expression.literal(new int[]{2, 3, 4})));
                Expression.in("a", Expression.literal("abcde"));
                setText("in确定数组中是否存在项目或字符串中是否存在子字符串。",
                        "  Expression.in(1, Expression.array(Expression.literal(new int[]{2, 3, 4})));\n" +
                                "                Expression.in(\"a\", Expression.literal(\"abcde\"));");
                break;
            case R.id.look_up_length:
                /**
                 * length
                 * 获取数组或字符串的长度。
                 *
                 * ["length", string | array | value]: number
                 */
                Expression.length("abcdef");
                setText("length获取数组或字符串的长度。",
                        "Expression.length(\"abcdef\");");
                break;
            default:
                break;
        }
    }

    private void setText(String title, String msg){
        //设置文字---------------------------------------------------------------------------------------------------------------------------------
        tv_ex_title.setText(title);
        tv_ex_msg.setText(msg);
    }
}
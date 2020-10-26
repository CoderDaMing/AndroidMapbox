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

import com.mapbox.mapboxsdk.style.layers.CircleLayer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.utils.ColorUtils;
import com.ming.androidmapbox.R;

import java.util.Locale;

import static com.mapbox.mapboxsdk.style.expressions.Expression.collator;
import static com.mapbox.mapboxsdk.style.expressions.Expression.concat;
import static com.mapbox.mapboxsdk.style.expressions.Expression.downcase;
import static com.mapbox.mapboxsdk.style.expressions.Expression.eq;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.isSupportedScript;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.resolvedLocale;
import static com.mapbox.mapboxsdk.style.expressions.Expression.switchCase;
import static com.mapbox.mapboxsdk.style.expressions.Expression.upcase;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textField;

public class ExStringFragment extends Fragment implements View.OnClickListener {
    private TextView tv_ex_title;
    private TextView tv_ex_msg;

    public ExStringFragment() {
        // Required empty public constructor
    }

    public static ExStringFragment newInstance() {
        ExStringFragment fragment = new ExStringFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ex_string, container, false);

        tv_ex_title = view.findViewById(R.id.tv_ex_title);
        tv_ex_msg = view.findViewById(R.id.tv_ex_msg);

        View viewConcat = view.findViewById(R.id.string_concat);
        viewConcat.setOnClickListener(this);
        viewConcat.callOnClick();
        view.findViewById(R.id.string_upcase).setOnClickListener(this);
        view.findViewById(R.id.string_downcase).setOnClickListener(this);
        view.findViewById(R.id.string_is_supported_script).setOnClickListener(this);
        view.findViewById(R.id.string_resolved_locale).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.string_concat) {
            /**
             * concat
             *返回由输入的串联组成的字符串。每个输入都将转换为字符串，就像通过to-string一样。
             *
             * ["concat", value, value, ...]: string
             */
//            Expression.concat();
            SymbolLayer symbolLayer = new SymbolLayer("layer-id", "source-id");
            symbolLayer.setProperties(
                    textField(concat(get("key-to-string-value"), literal("other string")))
            );

            setText(" concat返回由输入的串联组成的字符串。每个输入都将转换为字符串，就像通过to-string一样。",
                    " SymbolLayer symbolLayer = new SymbolLayer(\"layer-id\", \"source-id\");\n" +
                            "            symbolLayer.setProperties(\n" +
                            "                    textField(concat(get(\"key-to-string-value\"), literal(\"other string\")))\n" +
                            "            );");
        } else if (viewId == R.id.string_upcase) {
            /**
             * upcase
             * 返回转换为大写的输入字符串。遵循Unicode默认大小写转换算法和Unicode字符数据库中不区分语言环境的大小写映射。
             *
             * ["upcase", string]: string
             */
//            Expression.upcase();
            SymbolLayer symbolLayer = new SymbolLayer("layer-id", "source-id");
            symbolLayer.setProperties(
                    textField(upcase(get("key-to-string-value")))
            );
            setText("upcase返回转换为大写的输入字符串。遵循Unicode默认大小写转换算法和Unicode字符数据库中不区分语言环境的大小写映射。",
                    " SymbolLayer symbolLayer = new SymbolLayer(\"layer-id\", \"source-id\");\n" +
                            "            symbolLayer.setProperties(\n" +
                            "                    textField(upcase(get(\"key-to-string-value\")))\n" +
                            "            );");
        } else if (viewId == R.id.string_downcase) {
            /**
             * downcase
             * 返回转换为小写的输入字符串。遵循Unicode默认大小写转换算法和Unicode字符数据库中不区分语言环境的大小写映射。
             *
             * ["downcase", string]: string
             */
//            downcase();
            SymbolLayer symbolLayer = new SymbolLayer("layer-id", "source-id");
            symbolLayer.setProperties(
                    textField(downcase(get("key-to-string-value")))
            );
            setText("downcase返回转换为小写的输入字符串。遵循Unicode默认大小写转换算法和Unicode字符数据库中不区分语言环境的大小写映射。",
                    " SymbolLayer symbolLayer = new SymbolLayer(\"layer-id\", \"source-id\");\n" +
                            "            symbolLayer.setProperties(\n" +
                            "                    textField(downcase(get(\"key-to-string-value\")))\n" +
                            "            );");
        } else if (viewId == R.id.string_is_supported_script) {
            /**
             * is-supported-script
             * 如果期望输入字符串清晰呈现，则返回true。如果输入字符串包含无法呈现而不会失去意义的部分，则返回false（例如，需要复杂文本整形的印度脚本，如果未使用mapbox-gl-rtl-text插件，则从右到左脚本） Mapbox GL JS）。
             *
             * ["is-supported-script", string]: boolean
             */
//            Expression.isSupportedScript();
            SymbolLayer symbolLayer = new SymbolLayer("layer-id", "source-id")
                    .withProperties(
                            textField(
                                    switchCase(
                                            isSupportedScript(get("name_property")), get("name_property"),
                                            literal("not-compatible")
                                    )
                            )
                    );
            setText("is-supported-script 如果期望输入字符串清晰呈现，则返回true。如果输入字符串包含无法呈现而不会失去意义的部分，则返回false（例如，需要复杂文本整形的印度脚本，如果未使用mapbox-gl-rtl-text插件，则从右到左脚本） Mapbox GL JS）。",
                    " SymbolLayer symbolLayer = new SymbolLayer(\"layer-id\", \"source-id\")\n" +
                            "                    .withProperties(\n" +
                            "                            textField(\n" +
                            "                                    switchCase(\n" +
                            "                                            isSupportedScript(get(\"name_property\")), get(\"name_property\"),\n" +
                            "                                            literal(\"not-compatible\")\n" +
                            "                                    )\n" +
                            "                            )\n" +
                            "                    );");
        } else if (viewId == R.id.string_resolved_locale) {
            /**
             * resolved-locale
             * 返回提供的整理程序正在使用的语言环境的IETF语言标记。这可用于确定默认系统区域设置，或确定是否成功加载了请求的区域设置。
             *["resolved-locale", collator]: string
             *
             */
//            Expression.resolvedLocale();
            CircleLayer circleLayer = new CircleLayer("layer-id", "source-id");
            circleLayer.setProperties(
                    circleColor(switchCase(
                            eq(literal("it"), resolvedLocale(collator(true, true, Locale.ITALY))), literal(ColorUtils.colorToRgbaString(Color.GREEN)),
                            literal(ColorUtils.colorToRgbaString(Color.RED))))
            );
            setText("resolved-locale返回提供的整理程序正在使用的语言环境的IETF语言标记。这可用于确定默认系统区域设置，或确定是否成功加载了请求的区域设置。",
                    " CircleLayer circleLayer = new CircleLayer(\"layer-id\", \"source-id\");\n" +
                    "            circleLayer.setProperties(\n" +
                    "                    circleColor(switchCase(\n" +
                    "                            eq(literal(\"it\"), resolvedLocale(collator(true, true, Locale.ITALY))), literal(ColorUtils.colorToRgbaString(Color.GREEN)),\n" +
                    "                            literal(ColorUtils.colorToRgbaString(Color.RED))))\n" +
                    "            );");
        }
    }

    private void setText(String title, String msg){
        //设置文字---------------------------------------------------------------------------------------------------------------------------------
        tv_ex_title.setText(title);
        tv_ex_msg.setText(msg);
    }
}

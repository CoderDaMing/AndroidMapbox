package com.ming.androidmapbox.expression;

import android.graphics.Color;
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
import com.ming.androidmapbox.R;
import com.ming.androidmapbox.util.DialogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Types
 * <p>
 * 用于测试不同的数据类型，例如字符串，数字和布尔值，并在它们之间进行转换。
 * <p>
 * 通常，这种测试和转换是不必要的，但是在某些子表达式的类型不明确的某些表达式中，它们可能是必需的。在要素数据类型不一致的情况下，它们也很有用；
 * 例如，您可以使用to-number来确保将“ 1.5”（而不是1.5）之类的值视为数字值。
 */
public class ExTypesFragment extends Fragment implements AdapterView.OnItemClickListener {
    private List<String> typeList = new ArrayList<>();

    public ExTypesFragment() {
        // Required empty public constructor
    }

    public static ExTypesFragment newInstance() {
        ExTypesFragment fragment = new ExTypesFragment();
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
        View view = inflater.inflate(R.layout.fragment_ex_types, container, false);
        RecyclerView rv_types_list = view.findViewById(R.id.rv_types_list);
        rv_types_list.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayout.VERTICAL));

        typeList.add("literal");
        typeList.add("array");
        typeList.add("boolean");
        typeList.add("collator");
        typeList.add("format");
        typeList.add("image");
        typeList.add("number");
        typeList.add("number-format");
        typeList.add("object");
        typeList.add("string");
        typeList.add("to-boolean");
        typeList.add("to-color");
        typeList.add("to-number");
        typeList.add("to-string");
        typeList.add("typeof");
        ExStringAdapter exStringAdapter = new ExStringAdapter(requireContext(), typeList);
        rv_types_list.setAdapter(exStringAdapter);
        exStringAdapter.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String type = typeList.get(position);
        switch (type) {
            case "literal":
                showLiteral();
                break;
            case "array":
                showArray();
                break;
            case "boolean":
                showBool();
                break;
            case "collator":
                showCollator();
                break;
            case "format":
                showFormat();
                break;
            case "image":
                showImage();
                break;
            case "number":
                showNumber();
                break;
            case "number-format":
                showNumberFormat();
                break;
            case "object":
                showObject();
                break;
            case "string":
                showString();
                break;
            case "to-boolean":
                showToBoolean();
                break;
            case "to-color":
                showToColor();
                break;
            case "to-number":
                showToNumber();
                break;
            case "to-string":
                showToString();
                break;
            case "typeof":
                showTypeOf();
                break;
            default:
                break;
        }
    }

    /**
     * typeof
     * 返回描述给定值类型的字符串。
     * <p>
     * ["typeof", value]: string
     */
    private void showTypeOf() {
        Expression expression = Expression.typeOf(Expression.literal(false));
        DialogUtil.show(getActivity(),"typeof返回描述给定值类型的字符串。"
                +"\n\r"
                +"Expression expression = Expression.typeOf(Expression.literal(false));");
    }

    /**
     * to-string
     * 将输入值转换为字符串。如果输入为空，则结果为“”。如果输入为布尔值，则结果为“ true”或“ false”。
     * 如果输入是数字，则将其转换为ECMAScript语言规范的“ NumberToString”算法指定的字符串。
     * 如果输入是颜色，则将其转换为格式为“ rgba（r，g，b，a）”的字符串，其中r，g和b是从0到255的数字，范围是从0到255的数字。1.否则，输入将转换为ECMAScript语言规范的JSON.stringify函数指定的格式的字符串。
     * <p>
     * ["to-string", value]: string
     */
    private void showToString() {
        Expression aaaaa = Expression.toString(Expression.literal("AAAAA"));
        DialogUtil.show(getActivity(),"to-string将输入值转换为字符串。如果输入为空，则结果为“”。如果输入为布尔值，则结果为“ true”或“ false”。\\n\" +\n" +
                "                \" 如果输入是数字，则将其转换为ECMAScript语言规范的“ NumberToString”算法指定的字符串。\\n\" +\n" +
                "                \" 如果输入是颜色，则将其转换为格式为“ rgba（r，g，b，a）”的字符串，其中r，g和b是从0到255的数字，范围是从0到255的数字。1.否则，输入将转换为ECMAScript语言规范的JSON.stringify函数指定的格式的字符串。"
                +"\n\r"
                +"Expression aaaaa = Expression.toString(Expression.literal(\"AAAAA\"));");
    }

    /**
     * to-number
     * 如果可能，将输入值转换为数字。如果输入为null或false，则结果为0。如果输入为true，则结果为1。如果输入为字符串，
     * 则将其转换为由“应用于字符串类型的ToNumber”算法指定的数字。 ECMAScript语言规范。如果提供了多个值，则会依次评估每个值，
     * 直到获得第一个成功的转换为止。如果没有任何输入可以转换，则该表达式为错误。
     * <p>
     * ["to-number", value, fallback: value, fallback: value, ...]: number
     */
    private void showToNumber() {
        Expression expression = Expression.toNumber(Expression.literal(1 + 1));
        DialogUtil.show(getActivity(),"to-number如果可能，将输入值转换为数字。如果输入为null或false，则结果为0。如果输入为true，则结果为1。如果输入为字符串，\n" +
                "则将其转换为由“应用于字符串类型的ToNumber”算法指定的数字。 ECMAScript语言规范。如果提供了多个值，则会依次评估每个值，\n" +
                " 直到获得第一个成功的转换为止。如果没有任何输入可以转换，则该表达式为错误。"
                +"\n\r"
                +"Expression expression = Expression.toNumber(Expression.literal(1 + 1));");
    }

    /**
     * to-color
     * 将输入值转换为颜色。如果提供了多个值，则会依次评估每个值，直到获得第一个成功的转换为止。如果没有任何输入可以转换，则该表达式为错误。
     * <p>
     * ["to-color", value, fallback: value, fallback: value, ...]: color
     */
    private void showToColor() {
        Expression expression = Expression.toColor(Expression.literal("#000000"));
        DialogUtil.show(getActivity()," to-color将输入值转换为颜色。如果提供了多个值，则会依次评估每个值，直到获得第一个成功的转换为止。如果没有任何输入可以转换，则该表达式为错误。"
                +"\n\r"
                +"Expression expression = Expression.toColor(Expression.literal(\"#000000\"));");
    }

    /**
     * to-boolean
     * 将输入值转换为布尔值。当输入为空字符串，0，false，null或NaN时，结果为false。否则是真的。
     * <p>
     * ["to-boolean", value]: boolean
     */
    private void showToBoolean() {
        Expression expression = Expression.toBool(Expression.literal(1 + 1 > 2));
        DialogUtil.show(getActivity(),"to-boolean将输入值转换为布尔值。当输入为空字符串，0，false，null或NaN时，结果为false。否则是真的。"
                +"\n\r"
                +"Expression expression = Expression.toBool(Expression.literal(1 + 1 > 2));");
    }

    /**
     * string
     * 断言输入值是一个字符串。如果提供了多个值，则会依次评估每个值，直到获得一个字符串为止。如果所有输入都不是字符串，则表达式为错误。
     * <p>
     * ["string", value]: string
     * ["string", value, fallback: value, fallback: value, ...]: string
     */
    private void showString() {
        Expression.string(Expression.literal(5), Expression.literal(true), Expression.literal(new Object()));
        DialogUtil.show(getActivity(),"string断言输入值是一个字符串。如果提供了多个值，则会依次评估每个值，直到获得一个字符串为止。如果所有输入都不是字符串，则表达式为错误。"
                +"\n\r"
                +"Expression.string(Expression.literal(5), Expression.literal(true), Expression.literal(new Object()));");
    }

    /**
     * object
     * 断言输入值是一个对象。如果提供多个值，则依次评估每个值，直到获得一个对象。如果所有输入都不是对象，则表达式为错误。
     * <p>
     * ["object", value]: object
     * ["object", value, fallback: value, fallback: value, ...]: object
     */
    private void showObject() {
        Expression expression = Expression.object(Expression.literal(new Object()));
        DialogUtil.show(getActivity()," object断言输入值是一个对象。如果提供多个值，则依次评估每个值，直到获得一个对象。如果所有输入都不是对象，则表达式为错误。"
                +"\n\r"
                +"Expression expression = Expression.object(Expression.literal(new Object()));");
    }

    /**
     * number-format
     * 使用提供的格式规则将输入数字转换为字符串表示形式。如果设置，则locale参数指定要用作BCP 47语言标记的语言环境。
     * 如果设置，currency参数指定用于货币样式格式的ISO 4217代码。
     * 如果设置，min-fraction-digits和max-fraction-digits参数指定要包括的小数位数的最小和最大数目。
     * <p>
     * ["number-format",
     * input: number,
     * options: { "locale": string, "currency": string, "min-fraction-digits": number, "max-fraction-digits": number }
     * ]: string
     */
    private void showNumberFormat() {
        Expression en = Expression.numberFormat(8888, Expression.NumberFormatOption.locale("en"));
        DialogUtil.show(getActivity(),"number-format 使用提供的格式规则将输入数字转换为字符串表示形式。如果设置，则locale参数指定要用作BCP 47语言标记的语言环境。\n" +
                "如果设置，currency参数指定用于货币样式格式的ISO 4217代码。\n" +
                "如果设置，min-fraction-digits和max-fraction-digits参数指定要包括的小数位数的最小和最大数目。"
                +"\n\r"
                +"Expression en = Expression.numberFormat(8888, Expression.NumberFormatOption.locale(\"en\"));");
    }

    /**
     * number
     * 断言输入值是一个数字。如果提供多个值，则依次评估每个值，直到获得一个数字。如果所有输入都不是数字，则表达式为错误。
     * <p>
     * ["number", value]: number
     * ["number", value, fallback: value, fallback: value, ...]: number
     */
    private void showNumber() {
        Expression number = Expression.number(Expression.literal("1"), Expression.literal(false), Expression.literal(1));
        DialogUtil.show(getActivity(),"number断言输入值是一个数字。如果提供多个值，则依次评估每个值，直到获得一个数字。如果所有输入都不是数字，则表达式为错误。"
                +"\n\r"
                +" Expression number = Expression.number(Expression.literal(\"1\"), Expression.literal(false), Expression.literal(1));");
    }

    /**
     * image
     * 返回图像类型，以用于图标图像，模式条目并用作格式表达式的一部分。
     * 如果设置，image参数将检查样式中是否存在所请求的图像，并将根据图像当前是否在样式中返回解析的图像名称或null。
     * 此验证过程是同步的，需要在image参数中请求图像之前将图像添加到样式中。
     * <p>
     * ["image", value]: image
     */
    private void showImage() {
        Expression uri = Expression.image(Expression.literal("uri"));
        DialogUtil.show(getActivity(),"image返回图像类型，以用于图标图像，模式条目并用作格式表达式的一部分。\n" +
                "如果设置，image参数将检查样式中是否存在所请求的图像，并将根据图像当前是否在样式中返回解析的图像名称或null。\n" +
                " 此验证过程是同步的，需要在image参数中请求图像之前将图像添加到样式中。"
                +"\n\r"
                +" Expression uri = Expression.image(Expression.literal(\"uri\"));");
    }

    /**
     * format
     * 返回一个格式化的字符串，用于在text-field属性中显示混合格式的文本。
     * 输入可以包含字符串文字或表达式，包括“图像”表达式。
     * 字符串后面可以跟随一个支持以下属性的样式覆盖对象：
     * <p>
     * "text-font": Overrides the font stack specified by the root layout property.
     * "text-color": Overrides the color specified by the root paint property.
     * "font-scale": Applies a scaling factor on text-size as specified by the root layout property.
     * ["format",
     * input_1: string | image, options_1: { "font-scale": number, "text-font": array<string>, "text-color": color },
     * ...,
     * input_n: string | image, options_n: { "font-scale": number, "text-font": array<string>, "text-color": color }
     * ]: formatted
     */
    private void showFormat() {
        Expression.format(Expression.formatEntry("ABCDE",
                Expression.FormatOption.formatFontScale(20)
                , Expression.FormatOption.formatTextColor(Color.RED)
//                , Expression.FormatOption.formatTextFont()
                )
        );
        DialogUtil.show(getActivity(),"format返回一个格式化的字符串，用于在text-field属性中显示混合格式的文本。\n" +
                "输入可以包含字符串文字或表达式，包括“图像”表达式。\n" +
                " 字符串后面可以跟随一个支持以下属性的样式覆盖对象：\n" +
                "<p>\n" +
                "\"text-font\": Overrides the font stack specified by the root layout property.\n" +
                "\"text-color\": Overrides the color specified by the root paint property.\n" +
                " \"font-scale\": Applies a scaling factor on text-size as specified by the root layout property."
                +"\n\r"
                +"  Expression.format(Expression.formatEntry(\"ABCDE\",\n" +
                "                Expression.FormatOption.formatFontScale(20)\n" +
                "                , Expression.FormatOption.formatTextColor(Color.RED)\n" +
                "//                , Expression.FormatOption.formatTextFont()\n" +
                "                )");
    }

    /**
     * collator
     * 返回一个整理器，用于与语言环境相关的比较操作。区分大小写和变音符号的选项默认为false。
     * locale参数指定要使用的语言环境的IETF语言标记。如果未提供，则使用默认语言环境。
     * 如果请求的语言环境不可用，则整理器将使用系统定义的后备语言环境。
     * 使用solved-locale测试语言环境回退行为的结果。
     * <p>
     * ["collator",
     * { "case-sensitive": boolean, "diacritic-sensitive": boolean, "locale": string }
     * ]: collator
     */
    private void showCollator() {
        Expression collator = Expression.collator(false, false);
        //相当于
        Expression collator1 = Expression.collator(Expression.literal(false), Expression.literal(false));

        Locale locale = Locale.ENGLISH;
        Expression collator2 = Expression.collator(false, false, locale);
        //相当于
        StringBuilder localeStringBuilder = new StringBuilder();
        String language = locale.getLanguage();
        if (language != null && !language.isEmpty()) {
            localeStringBuilder.append(language);
        }
        String country = locale.getCountry();
        if (country != null && !country.isEmpty()) {
            localeStringBuilder.append("-");
            localeStringBuilder.append(country);
        }
        Expression collator3 = Expression.collator(Expression.literal(false), Expression.literal(false), Expression.literal(localeStringBuilder.toString()));
        DialogUtil.show(getActivity(),"collator。返回一个整理器，用于与语言环境相关的比较操作。区分大小写和变音符号的选项默认为false。\n" +
                "locale参数指定要使用的语言环境的IETF语言标记。如果未提供，则使用默认语言环境。\n" +
                "如果请求的语言环境不可用，则整理器将使用系统定义的后备语言环境。\n" +
                "使用solved-locale测试语言环境回退行为的结果"
                +"\n\r"
                +" Expression collator = Expression.collator(false, false);\n" +
                "        //相当于\n" +
                "        Expression collator1 = Expression.collator(Expression.literal(false), Expression.literal(false));\n" +
                "\n" +
                "        Locale locale = Locale.ENGLISH;\n" +
                "        Expression collator2 = Expression.collator(false, false, locale);\n" +
                "        //相当于\n" +
                "        StringBuilder localeStringBuilder = new StringBuilder();\n" +
                "        String language = locale.getLanguage();\n" +
                "        if (language != null && !language.isEmpty()) {\n" +
                "            localeStringBuilder.append(language);\n" +
                "        }\n" +
                "        String country = locale.getCountry();\n" +
                "        if (country != null && !country.isEmpty()) {\n" +
                "            localeStringBuilder.append(\"-\");\n" +
                "            localeStringBuilder.append(country);\n" +
                "        }\n" +
                "        Expression collator3 = Expression.collator(Expression.literal(false), Expression.literal(false), Expression.literal(localeStringBuilder.toString()));");
    }

    /**
     * boolean
     * 断言输入值是布尔值。如果提供了多个值，则会依次评估每个值，直到获得布尔值为止。如果所有输入都不是布尔值，则该表达式为错误。
     * <p>
     * ["boolean", value]: boolean
     * ["boolean", value, fallback: value, fallback: value, ...]: boolean
     */
    private void showBool() {
        Expression boolExpression = Expression.bool(Expression.literal(true));
        Expression boolMoreExpression = Expression.bool(Expression.literal(1), Expression.literal("true"), Expression.literal(true));
        DialogUtil.show(getActivity(),"boolean断言输入值是布尔值。如果提供了多个值，则会依次评估每个值，直到获得布尔值为止。如果所有输入都不是布尔值，则该表达式为错误。"
                +"\n\r"
                +" Expression boolExpression = Expression.bool(Expression.literal(true));\n" +
                "        Expression boolMoreExpression = Expression.bool(Expression.literal(1), Expression.literal(\"true\"), Expression.literal(true));");
    }

    /**
     * array
     * 断言输入是一个数组（可以选择具有特定项目类型和长度的数组）。如果在计算输入表达式时，它不是断言类型，则此断言将导致整个表达式被中止。
     * <p>
     * ["array", value]: array
     * ["array", type: "string" | "number" | "boolean", value]: array<type>
     * ["array",
     * type: "string" | "number" | "boolean",
     * N: number (literal),
     * value
     * ]: array<type, N>
     */
    private void showArray() {
        Expression numberArray = Expression.array(Expression.literal(new int[]{1, 2, 3}));
        Expression stringArray = Expression.array(Expression.literal(new String[]{"1", "2", "3"}));
        Expression boolArray = Expression.array(Expression.literal(new boolean[]{true, false, true}));

        DialogUtil.show(getActivity(),"array断言输入是一个数组（可以选择具有特定项目类型和长度的数组）。如果在计算输入表达式时，它不是断言类型，则此断言将导致整个表达式被中止。"
                +"\n\r"
                +"  Expression numberArray = Expression.array(Expression.literal(new int[]{1, 2, 3}));\n" +
                "        Expression stringArray = Expression.array(Expression.literal(new String[]{\"1\", \"2\", \"3\"}));\n" +
                "        Expression boolArray = Expression.array(Expression.literal(new boolean[]{true, false, true}));");
    }

    /**
     * literal
     * 提供文字数组或对象值。
     * <p>
     * ["literal", [...] (JSON array literal)]: array<T, N>
     * ["literal", {...} (JSON object literal)]: object
     * 提供文字数组或对象值。
     * <p>
     * Expression.literal(@NonNull Number number);
     * Expression.literal(@NonNull String string);
     * Expression.literal(boolean bool);
     * Expression.literal(@NonNull Object object);
     * Expression.literal(@NonNull Object[] array);
     */
    private void showLiteral() {
//        Number number = new Number() {
//            @Override
//            public int intValue() {
//                return 1;
//            }
//
//            @Override
//            public long longValue() {
//                return 1L;
//            }
//
//            @Override
//            public float floatValue() {
//                return 1f;
//            }
//
//            @Override
//            public double doubleValue() {
//                return 1.d;
//            }
//        };
        Expression exNumber = Expression.literal(1);
        Expression exString = Expression.literal("haha");
        Expression exBoolean = Expression.literal(true);
        Expression exObject = Expression.literal(new Object());
//        Expression.literal(new ArrayList<Object>()) 识别为object;
        Expression exObjectArray = Expression.literal(new Object[]{});

        DialogUtil.show(getActivity(),"literal提供文字数组或对象值。"
                +"\n\r"
                +"  Expression exNumber = Expression.literal(1);\n" +
                "        Expression exString = Expression.literal(\"haha\");\n" +
                "        Expression exBoolean = Expression.literal(true);\n" +
                "        Expression exObject = Expression.literal(new Object());\n" +
                "//        Expression.literal(new ArrayList<Object>()) 识别为object;\n" +
                "        Expression exObjectArray = Expression.literal(new Object[]{});");
    }
}
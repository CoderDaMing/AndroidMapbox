package com.ming.androidmapbox.expression;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.ming.androidmapbox.R;

import static com.mapbox.mapboxsdk.style.expressions.Expression.accumulated;
import static com.mapbox.mapboxsdk.style.expressions.Expression.concat;
import static com.mapbox.mapboxsdk.style.expressions.Expression.geometryType;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.id;
import static com.mapbox.mapboxsdk.style.expressions.Expression.interpolate;
import static com.mapbox.mapboxsdk.style.expressions.Expression.lineProgress;
import static com.mapbox.mapboxsdk.style.expressions.Expression.linear;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.max;
import static com.mapbox.mapboxsdk.style.expressions.Expression.properties;
import static com.mapbox.mapboxsdk.style.expressions.Expression.rgb;
import static com.mapbox.mapboxsdk.style.expressions.Expression.stop;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineGradient;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textField;

public class ExFeatureDataFragment extends Fragment implements View.OnClickListener {
    private TextView tv_ex_title;
    private TextView tv_ex_msg;

    public ExFeatureDataFragment() {
        // Required empty public constructor
    }

    public static ExFeatureDataFragment newInstance() {
        ExFeatureDataFragment fragment = new ExFeatureDataFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ex_feature_data, container, false);

        tv_ex_title = view.findViewById(R.id.tv_ex_title);
        tv_ex_msg = view.findViewById(R.id.tv_ex_msg);

        View viewAccumulated = view.findViewById(R.id.accumulated);
        viewAccumulated.setOnClickListener(this);
        viewAccumulated.callOnClick();

        view.findViewById(R.id.geometry_type).setOnClickListener(this);
        view.findViewById(R.id.feature_id).setOnClickListener(this);
        view.findViewById(R.id.line_progress).setOnClickListener(this);
        view.findViewById(R.id.properties).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.accumulated:
                /**
                 * accumulated
                 * 获取到目前为止累积的集群属性的值。只能在集群化GeoJSON源的clusterProperties选项中使用。
                 *
                 * ["accumulated"]: value
                 */
                GeoJsonOptions options = new GeoJsonOptions()
                        .withCluster(true)
                        .withClusterProperty("max", literal(max(accumulated(), get("max")).toArray()), literal(get("mag").toArray()));
                setText("accumulated获取到目前为止累积的集群属性的值。只能在集群化GeoJSON源的clusterProperties选项中使用。",
                        " GeoJsonOptions options = new GeoJsonOptions()\n" +
                                "                        .withCluster(true)\n" +
                                "                        .withClusterProperty(\"max\", literal(max(accumulated(), get(\"max\")).toArray()), literal(get(\"mag\").toArray()));");
                break;
            case R.id.geometry_type:
                /**
                 * geometry-type
                 * 获取要素的几何类型: Point, MultiPoint, LineString, MultiLineString, Polygon, MultiPolygon.
                 *
                 * ["geometry-type"]: string
                 */
                SymbolLayer symbolLayer = new SymbolLayer("layer-id", "source-id");
                symbolLayer.setProperties(
                        textField(concat(get("key-to-value"), literal(" "), geometryType()))
                );
                setText("geometry-type获取要素的几何类型: Point, MultiPoint, LineString, MultiLineString, Polygon, MultiPolygon.",
                        " SymbolLayer symbolLayer = new SymbolLayer(\"layer-id\", \"source-id\");\n" +
                                "                symbolLayer.setProperties(\n" +
                                "                        textField(concat(get(\"key-to-value\"), literal(\" \"), geometryType()))\n" +
                                "                );");
                break;
            case R.id.feature_id:
                /**
                 * id
                 * 获取feature的ID（如果有的话）。
                 *
                 * ["id"]: value
                 */
                SymbolLayer idSymbolLayer = new SymbolLayer("layer-id", "source-id");
                idSymbolLayer.setProperties(
                        textField(id())
                );
                setText("id获取feature的ID（如果有的话）。",
                        " SymbolLayer idSymbolLayer = new SymbolLayer(\"layer-id\", \"source-id\");\n" +
                                "                idSymbolLayer.setProperties(\n" +
                                "                        textField(id())\n" +
                                "                );");
                break;
            case R.id.line_progress:
                /**
                 * line-progress
                 * 获取沿渐变线的进度。只能在line-gradient属性中使用。
                 *
                 * ["line-progress"]: number
                 */
                LineLayer layer = new LineLayer("layer-id", "source-id");
                layer.setProperties(
                        lineGradient(interpolate(
                                linear(), lineProgress(),
                                stop(0f, rgb(0, 0, 255)),
                                stop(0.5f, rgb(0, 255, 0)),
                                stop(1f, rgb(255, 0, 0)))
                        )
                );
                setText(" line-progress获取沿渐变线的进度。只能在line-gradient属性中使用。",
                        " LineLayer layer = new LineLayer(\"layer-id\", \"source-id\");\n" +
                                "                layer.setProperties(\n" +
                                "                        lineGradient(interpolate(\n" +
                                "                                linear(), lineProgress(),\n" +
                                "                                stop(0f, rgb(0, 0, 255)),\n" +
                                "                                stop(0.5f, rgb(0, 255, 0)),\n" +
                                "                                stop(1f, rgb(255, 0, 0)))\n" +
                                "                        )\n" +
                                "                );");
                break;
            case R.id.properties:
                /**
                 * properties
                 * 获取要素属性对象。请注意，在某些情况下，直接使用[“ get”，“ property_name”]可能更有效。
                 *
                 * ["properties"]: object
                 */
                SymbolLayer propertiesSymbolLayer = new SymbolLayer("layer-id", "source-id");
                propertiesSymbolLayer.setProperties(
                        textField(get("key-to-value", properties()))
                );
                setText("properties获取要素属性对象。请注意，在某些情况下，直接使用[“ get”，“ property_name”]可能更有效。",
                        "SymbolLayer propertiesSymbolLayer = new SymbolLayer(\"layer-id\", \"source-id\");\n" +
                                "                propertiesSymbolLayer.setProperties(\n" +
                                "                        textField(get(\"key-to-value\", properties()))\n" +
                                "                );");
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
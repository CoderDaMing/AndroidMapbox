package com.ming.androidmapbox.root;

import android.graphics.Color;
import android.graphics.PointF;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.MapboxConstants;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Projection;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.style.sources.Source;
import com.ming.androidmapbox.MapActivity;
import com.ming.androidmapbox.R;
import com.ming.androidmapbox.util.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillOpacity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

/**
 * bearing 相机指向的方向，以北为顺时针方向。
 * 以度为单位。预设为0。
 * 默认方位角，以度为单位。方位是指“罗盘”方向，即“向上”。例如，方位角为90°可使地图定向，以使东方朝上。仅当尚未通过其他方式（例如，地图选项或用户互动）定位地图时，才使用此值。
 * <p>
 * target 相机指向的位置(地图中心位置)
 * <p>
 * tilt  相机与最低点（直接面对地球）的夹角，以度为单位。
 * <p>
 * zoom
 * 默认缩放级别。仅当地图尚未通过其他方式（例如，地图选项或用户互动）定位时，才会使用样式缩放。
 * <p>
 * padding 以像素为单位的填充。按左，上，右，下顺序指定。
 */
public class CameraPositionActivity extends MapActivity implements View.OnClickListener {

    private EditText et_min_zoom, et_max_zoom;
    private Button btn_update, btn_easeCamera, btn_moveCamera, btn_animateCamera, btn_FitCameraInBoundingBox, btn_RestrictMapPanning;
    private TextView tv_currentCameraPosition;
    //FitCameraInBoundingBox
    private static final LatLng locationOne = new LatLng(36.532128, -93.489121);
    private static final LatLng locationTwo = new LatLng(25.837058, -106.646234);
    /*
     * 注意：latLngs的size必须大于一
     *  if (latLngList.size() < 2) {
     *         throw new InvalidLatLngBoundsException(latLngList.size());
     *  }
     */
    private static final LatLngBounds latLngBounds = new LatLngBounds.Builder()
            .include(locationOne) // Northeast
            .include(locationTwo) // Southwest
            .build();
    //RestrictMapPanning
    private static final LatLng BOUND_CORNER_NW = new LatLng(34.532128, -98.489121);
    private static final LatLng BOUND_CORNER_SE = new LatLng(32.837058, -100.646234);
    private static final LatLngBounds RESTRICTED_BOUNDS_AREA = new LatLngBounds.Builder()
            .include(BOUND_CORNER_NW)
            .include(BOUND_CORNER_SE)
            .build();

    private final List<List<Point>> points = new ArrayList<>();
    private final List<Point> outerPoints = new ArrayList<>();

    @Override
    public void onMapLoaded() {

        //获取可以显示地图的最小缩放级别
        et_min_zoom = findViewById(R.id.et_min_zoom);
        et_min_zoom.setText(String.valueOf(MapboxConstants.MINIMUM_ZOOM));
        mapboxMap.setMinZoomPreference(MapboxConstants.MINIMUM_ZOOM);
        //获取可以显示地图的最大缩放级别
        et_max_zoom = findViewById(R.id.et_max_zoom);
        et_max_zoom.setText(String.valueOf(MapboxConstants.MAXIMUM_ZOOM));
        mapboxMap.setMaxZoomPreference(MapboxConstants.MAXIMUM_ZOOM);

        btn_update = findViewById(R.id.btn_update);
        btn_update.setOnClickListener(this);
        tv_currentCameraPosition = findViewById(R.id.tv_currentCameraPosition);
        btn_easeCamera = findViewById(R.id.btn_easeCamera);
        btn_easeCamera.setOnClickListener(this);
        btn_moveCamera = findViewById(R.id.btn_moveCamera);
        btn_moveCamera.setOnClickListener(this);
        btn_animateCamera = findViewById(R.id.btn_animateCamera);
        btn_animateCamera.setOnClickListener(this);
        btn_FitCameraInBoundingBox = findViewById(R.id.btn_FitCameraInBoundingBox);
        btn_FitCameraInBoundingBox.setOnClickListener(this);
        btn_RestrictMapPanning = findViewById(R.id.btn_RestrictMapPanning);
        btn_RestrictMapPanning.setOnClickListener(this);

        //获取可用于在屏幕坐标和纬度/经度之间转换的Projection对象
        Projection projection = mapboxMap.getProjection();
        LatLng toScreenLocation = new LatLng(38.852924, -77.044211);
        PointF pointF = projection.toScreenLocation(toScreenLocation);
        LatLng fromScreenLocation = projection.fromScreenLocation(pointF);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_camera_position;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                Editable editableMin = et_min_zoom.getText();
                if (TextUtils.isEmpty(editableMin)) {
                    Toast.makeText(this, "minZoom 输入为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                Editable editableMax = et_max_zoom.getText();
                if (TextUtils.isEmpty(editableMax)) {
                    Toast.makeText(this, "maxZoom 输入为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                mapboxMap.setMinZoomPreference(Float.parseFloat(editableMin.toString().trim()));
                mapboxMap.setMaxZoomPreference(Float.parseFloat(editableMax.toString().trim()));

                //获取摄像机的当前位置
                CameraPosition currentCameraPosition = mapboxMap.getCameraPosition();
                double bearing = currentCameraPosition.bearing;
                LatLng target = currentCameraPosition.target;
                double tilt = currentCameraPosition.tilt;
                double zoom = currentCameraPosition.zoom;
                double[] padding = currentCameraPosition.padding;

                tv_currentCameraPosition.setText("currentCameraPosition:"
                        + "\n MinZoomLevel:" + mapboxMap.getMinZoomLevel()
                        + "\n MaxZoomLevel:" + mapboxMap.getMaxZoomLevel()
                        + "\n bearing:" + bearing
                        + "\n target:" + target
                        + "\n tilt:" + tilt
                        + "\n zoom:" + zoom
                        + "\n padding:" + Arrays.toString(padding));
                break;
            case R.id.btn_easeCamera:
                CameraPosition easeCameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(51.50550, -0.07520)) // Sets the new camera position
                        .zoom(17) // Sets the zoom
                        .bearing(180) // Rotate the camera
                        .tilt(30) // Set the camera tilt
                        .build(); // Creates a CameraPosition from the builder
//                mapboxMap.easeCamera(@NonNull CameraUpdate update);
//                mapboxMap.easeCamera(@NonNull CameraUpdate update, int durationMs);
//                mapboxMap.easeCamera(@NonNull CameraUpdate update, @Nullable final MapboxMap.CancelableCallback callback);
//                mapboxMap.easeCamera(@NonNull CameraUpdate update, int durationMs, @Nullable final MapboxMap.CancelableCallback callback);
//                mapboxMap.easeCamera(@NonNull CameraUpdate update, int durationMs, boolean easingInterpolator);
                /**
                 *逐步将相机移动指定的持续时间（以毫秒为单位），除非在{@link CameraUpdate}中指定，
                 * 否则缩放不会受到影响。缓动相机停止时，可以使用回调来通知。
                 * 如果在动画过程中调用{@link #getCameraPosition（）}，它将返回飞行中相机的当前位置。
                 *
                 * @param update             应该应用于相机的更改。
                 * @param durationMs         动画的持续时间（以毫秒为单位）。这必须严格为正，否则将抛出IllegalArgumentException。
                 * @param easingInterpolator True表示缓和插值器，false表示线性。
                 * @param callback           动画停止时从主线程通知的可选回调。如果动画由于自然完成而停止，则将通过onFinish（）通知回调。
                 *                           如果动画由于后来的相机移动或用户手势中断而停止播放，则将调用onCancel（）。
                 *                           不要在onCancel（）中更新或缓和相机。
                 */
                mapboxMap.easeCamera(CameraUpdateFactory.newCameraPosition(easeCameraPosition), 5000, true, new MapboxMap.CancelableCallback() {
                    @Override
                    public void onCancel() {
                        Toast.makeText(CameraPositionActivity.this, "easeCamera onCancel()", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {
                        Toast.makeText(CameraPositionActivity.this, "easeCamera onFinish()", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.btn_moveCamera:
                CameraPosition moveCameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(51.50550, -0.07520)) // Sets the new camera position
                        .zoom(17) // Sets the zoom
                        .bearing(180) // Rotate the camera
                        .tilt(30) // Set the camera tilt
                        .build(); // Creates a CameraPosition from the builder
//                mapboxMap.moveCamera(@NonNull CameraUpdate update);
                /**
                 *根据更新中定义的说明重新放置相机。
                 *该移动是瞬时的，随后的getCameraPosition（）将反映新位置。
                 *有关一组更新，请参见CameraUpdateFactory。
                 *
                 * @param update  应该应用于相机的更改
                 * @param callback 动画结束或取消时要调用的回调
                 */
                mapboxMap.moveCamera(CameraUpdateFactory.newCameraPosition(moveCameraPosition), new MapboxMap.CancelableCallback() {
                    @Override
                    public void onCancel() {
                        Toast.makeText(CameraPositionActivity.this, "moveCamera onCancel()", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {
                        Toast.makeText(CameraPositionActivity.this, "moveCamera onFinish()", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.btn_animateCamera:
                CameraPosition animateCameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(51.50550, -0.07520)) // Sets the new camera position
                        .zoom(17) // Sets the zoom
                        .bearing(180) // Rotate the camera
                        .tilt(30) // Set the camera tilt
                        .build(); // Creates a CameraPosition from the builder
//                mapboxMap.animateCamera(@NonNull CameraUpdate update);
//                mapboxMap.animateCamera(@NonNull CameraUpdate update, @Nullable MapboxMap.CancelableCallback callback);
//                mapboxMap.animateCamera(@NonNull CameraUpdate update, int durationMs);
                /**
                 * 使用唤起动力飞行的过渡动画，将摄像机动画到{@link CameraUpdate}中定义的新位置。
                 * 动画将持续指定的时间（以毫秒为单位）。动画停止时，可以使用回调来通知。
                 * 在动画期间，对{@link #getCameraPosition（）}的调用将返回飞行中相机的中间位置。
                 *
                 * @param update     应该应用于相机的更改。
                 * @param durationMs 动画的持续时间（以毫秒为单位）。这必须严格为正，否则将抛出IllegalArgumentException。
                 * @param callback   动画停止时从主线程通知的可选回调。如果动画由于自然完成而停止，则将通过onFinish（）通知回调。
                 *                   如果动画由于后来的相机移动或用户手势的干扰而停止，将调用onCancel（）。
                 *                   不要在onCancel（）中更新或设置摄像机动画。如果不需要回调，请将其保留为null。
                 * @see com.mapbox.mapboxsdk.camera.CameraUpdateFactory for a set of updates.
                 */
                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(animateCameraPosition), 5000, new MapboxMap.CancelableCallback() {
                    @Override
                    public void onCancel() {
                        Toast.makeText(CameraPositionActivity.this, "animateCamera onCancel()", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {
                        Toast.makeText(CameraPositionActivity.this, "animateCamera onFinish()", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.btn_FitCameraInBoundingBox:
                mapboxMap.easeCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 50), 5000);
                addMarkerIconsToMap(style);
                ToastUtil.show("2个标记所在位置就是我们所设置显示区域边界");
                break;
            case R.id.btn_RestrictMapPanning:
                // 设置地图相机的边界区域
                mapboxMap.setLatLngBoundsForCameraTarget(RESTRICTED_BOUNDS_AREA);
                showBoundsArea(style);
                break;
            default:
                break;
        }
    }

    private void addMarkerIconsToMap(@NonNull Style loadedMapStyle) {
        Source source = loadedMapStyle.getSource("Bounds-source-id");
        if (source != null && "Bounds-source-id".equals(source.getId())) {
            return;
        }
        loadedMapStyle.addImage("Bounds-icon-id", getResources().getDrawable(R.drawable.mapbox_marker_icon_default));

        loadedMapStyle.addSource(new GeoJsonSource("Bounds-source-id",
                FeatureCollection.fromFeatures(new Feature[]{
                        Feature.fromGeometry(Point.fromLngLat(locationOne.getLongitude(), locationOne.getLatitude())),
                        Feature.fromGeometry(Point.fromLngLat(locationTwo.getLongitude(), locationTwo.getLatitude())),
                })));

        loadedMapStyle.addLayer(new SymbolLayer("Bounds-layer-id",
                "Bounds-source-id").withProperties(
                iconImage("Bounds-icon-id"),
                iconOffset(new Float[]{0f, -8f})
        ));
    }

    /**
     * 添加填充层以显示边界区域
     *
     * @param loadedMapStyle 地图已加载的Style对象
     */
    private void showBoundsArea(@NonNull Style loadedMapStyle) {
        Source source = loadedMapStyle.getSource("BoundsArea-source-id");
        if (source != null && "BoundsArea-source-id".equals(source.getId())) {
            return;
        }
        outerPoints.add(Point.fromLngLat(RESTRICTED_BOUNDS_AREA.getNorthWest().getLongitude(),
                RESTRICTED_BOUNDS_AREA.getNorthWest().getLatitude()));
        outerPoints.add(Point.fromLngLat(RESTRICTED_BOUNDS_AREA.getNorthEast().getLongitude(),
                RESTRICTED_BOUNDS_AREA.getNorthEast().getLatitude()));
        outerPoints.add(Point.fromLngLat(RESTRICTED_BOUNDS_AREA.getSouthEast().getLongitude(),
                RESTRICTED_BOUNDS_AREA.getSouthEast().getLatitude()));
        outerPoints.add(Point.fromLngLat(RESTRICTED_BOUNDS_AREA.getSouthWest().getLongitude(),
                RESTRICTED_BOUNDS_AREA.getSouthWest().getLatitude()));
        outerPoints.add(Point.fromLngLat(RESTRICTED_BOUNDS_AREA.getNorthWest().getLongitude(),
                RESTRICTED_BOUNDS_AREA.getNorthWest().getLatitude()));
        points.add(outerPoints);

        loadedMapStyle.addSource(new GeoJsonSource("BoundsArea-source-id",
                Polygon.fromLngLats(points)));

        loadedMapStyle.addLayer(new FillLayer("BoundsArea-layer-id", "BoundsArea-source-id").withProperties(
                fillColor(Color.RED),
                fillOpacity(.25f)
        ));
    }
}

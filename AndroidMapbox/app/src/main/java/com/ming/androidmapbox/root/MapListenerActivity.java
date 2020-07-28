package com.ming.androidmapbox.root;

import android.util.Log;

import androidx.annotation.NonNull;

import com.mapbox.android.gestures.MoveGestureDetector;
import com.mapbox.android.gestures.RotateGestureDetector;
import com.mapbox.android.gestures.ShoveGestureDetector;
import com.mapbox.android.gestures.StandardScaleGestureDetector;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.ming.androidmapbox.MapActivity;

public class MapListenerActivity extends MapActivity {
    private static final String TAG = "MapListenerActivity ";

    private MapboxMap.OnFlingListener onFlingListener = new MapboxMap.OnFlingListener() {
        @Override
        public void onFling() {
            Log.d(TAG, "onFling() called");
        }
    };

    private MapboxMap.OnMoveListener onMoveListener = new MapboxMap.OnMoveListener() {
        @Override
        public void onMoveBegin(@NonNull MoveGestureDetector detector) {
            Log.d(TAG, "onMoveBegin() called with: detector X,Y = [" + detector.getLastDistanceX() + "," + detector.getLastDistanceY() + "]");
        }

        @Override
        public void onMove(@NonNull MoveGestureDetector detector) {
            Log.d(TAG, "onMove() called with: detector X,Y = [" + detector.getLastDistanceX() + "," + detector.getLastDistanceY() + "]");
        }

        @Override
        public void onMoveEnd(@NonNull MoveGestureDetector detector) {
            Log.d(TAG, "onMoveEnd() called with: detector X,Y = [" + detector.getLastDistanceX() + "," + detector.getLastDistanceY() + "]");
        }
    };

    private MapboxMap.OnRotateListener onRotateListener = new MapboxMap.OnRotateListener() {
        @Override
        public void onRotateBegin(@NonNull RotateGestureDetector detector) {
            Log.d(TAG, "onRotateBegin() called with: detector getDeltaSinceLast = [" + detector.getDeltaSinceLast() + "]");
        }

        @Override
        public void onRotate(@NonNull RotateGestureDetector detector) {
            Log.d(TAG, "onRotate() called with: detector getDeltaSinceLast = [" + detector.getDeltaSinceLast() + "]");
        }

        @Override
        public void onRotateEnd(@NonNull RotateGestureDetector detector) {
            Log.d(TAG, "onRotateEnd() called with: detector getDeltaSinceLast = [" + detector.getDeltaSinceLast() + "]");
        }
    };

    private MapboxMap.OnScaleListener onScaleListener = new MapboxMap.OnScaleListener() {
        @Override
        public void onScaleBegin(@NonNull StandardScaleGestureDetector detector) {
            Log.d(TAG, "onScaleBegin() called with: detector getScaleFactor = [" + detector.getScaleFactor() + "]");
        }

        @Override
        public void onScale(@NonNull StandardScaleGestureDetector detector) {
            Log.d(TAG, "onScale() called with: detector getScaleFactor = [" + detector.getScaleFactor() + "]");
        }

        @Override
        public void onScaleEnd(@NonNull StandardScaleGestureDetector detector) {
            Log.d(TAG, "onScaleEnd() called with: detector getScaleFactor = [" + detector.getScaleFactor() + "]");
        }
    };

    private MapboxMap.OnShoveListener onShoveListener = new MapboxMap.OnShoveListener() {
        @Override
        public void onShoveBegin(@NonNull ShoveGestureDetector detector) {
            Log.d(TAG, "onShoveBegin() called with: detector getDeltaPixelSinceLast = [" + detector.getDeltaPixelSinceLast() + "]");
        }

        @Override
        public void onShove(@NonNull ShoveGestureDetector detector) {
            Log.d(TAG, "onShove() called with: detector getDeltaPixelSinceLast = [" + detector.getDeltaPixelSinceLast() + "]");
        }

        @Override
        public void onShoveEnd(@NonNull ShoveGestureDetector detector) {
            Log.d(TAG, "onShoveEnd() called with: detector getDeltaPixelSinceLast = [" + detector.getDeltaPixelSinceLast() + "]");
        }
    };

    private MyOnMapClickListener myOnMapClickListener = new MyOnMapClickListener();

    private static class MyOnMapClickListener implements MapboxMap.OnMapClickListener,
            MapboxMap.OnMapLongClickListener {

        @Override
        public boolean onMapClick(@NonNull LatLng point) {
            Log.d(TAG, "onMapClick() called with: point = [" + point.toString() + "]");
            return false;
        }

        @Override
        public boolean onMapLongClick(@NonNull LatLng point) {
            Log.d(TAG, "onMapLongClick() called with: point = [" + point.toString() + "]");
            return false;
        }
    }

    private MyOnCameraMoveListener myOnCameraMoveListener = new MyOnCameraMoveListener();

    private static class MyOnCameraMoveListener implements
            MapboxMap.OnCameraMoveStartedListener,
            MapboxMap.OnCameraMoveListener,
            MapboxMap.OnCameraMoveCanceledListener,
            MapboxMap.OnCameraIdleListener {

        @Override
        public void onCameraMoveStarted(int reason) {
            switch (reason) {
                case MapboxMap.OnCameraMoveStartedListener.REASON_API_GESTURE:

                    break;
                case MapboxMap.OnCameraMoveStartedListener.REASON_DEVELOPER_ANIMATION:

                    break;
                case MapboxMap.OnCameraMoveStartedListener.REASON_API_ANIMATION:

                    break;
                default:
                    break;
            }
            Log.d(TAG, "onCameraMoveStarted() called with: reason = [" + reason + "]");
        }

        @Override
        public void onCameraMove() {
            Log.d(TAG, "onCameraMove() called");
        }

        @Override
        public void onCameraMoveCanceled() {
            Log.d(TAG, "onCameraMoveCanceled() called");
        }

        @Override
        public void onCameraIdle() {
            Log.d(TAG, "onCameraIdle() called");
        }
    }


    @Override
    public void onMapLoaded() {
        mapboxMap.addOnFlingListener(onFlingListener);
        mapboxMap.addOnMoveListener(onMoveListener);
        mapboxMap.addOnRotateListener(onRotateListener);
        mapboxMap.addOnScaleListener(onScaleListener);
        mapboxMap.addOnShoveListener(onShoveListener);

        mapboxMap.addOnMapClickListener(myOnMapClickListener);
        mapboxMap.addOnMapLongClickListener(myOnMapClickListener);

        mapboxMap.addOnCameraMoveStartedListener(myOnCameraMoveListener);
        mapboxMap.addOnCameraMoveListener(myOnCameraMoveListener);
        mapboxMap.addOnCameraMoveCancelListener(myOnCameraMoveListener);
        mapboxMap.addOnCameraIdleListener(myOnCameraMoveListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapboxMap.removeOnFlingListener(onFlingListener);
        mapboxMap.removeOnMoveListener(onMoveListener);
        mapboxMap.removeOnRotateListener(onRotateListener);
        mapboxMap.removeOnScaleListener(onScaleListener);
        mapboxMap.removeOnShoveListener(onShoveListener);

        mapboxMap.removeOnMapClickListener(myOnMapClickListener);
        mapboxMap.removeOnMapLongClickListener(myOnMapClickListener);

        mapboxMap.removeOnCameraMoveStartedListener(myOnCameraMoveListener);
        mapboxMap.removeOnCameraMoveListener(myOnCameraMoveListener);
        mapboxMap.removeOnCameraMoveCancelListener(myOnCameraMoveListener);
        mapboxMap.removeOnCameraIdleListener(myOnCameraMoveListener);
    }
}

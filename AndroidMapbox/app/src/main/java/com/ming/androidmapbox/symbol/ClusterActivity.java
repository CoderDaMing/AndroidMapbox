package com.ming.androidmapbox.symbol;

import android.view.View;

import com.ming.androidmapbox.MapActivity;
import com.ming.androidmapbox.R;
import com.ming.androidmapbox.symbol.bean.DemoObj;
import com.ming.androidmapbox.symbol.bean.MapGeoJson;
import com.ming.androidmapbox.symbol.manager.ClusteringLayerManager;

import java.util.ArrayList;
import java.util.List;

public class ClusterActivity extends MapActivity {
    private ClusteringLayerManager clusteringLayerManager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cluster;
    }

    @Override
    public void onMapLoaded() {
        super.onMapLoaded();
        clusteringLayerManager = new ClusteringLayerManager(style);
    }

    public void add(View view) {
        List<DemoObj> demoObjList = new ArrayList<>();
        double lat = mapboxMap.getCameraPosition().target.getLatitude();
        double lng = mapboxMap.getCameraPosition().target.getLongitude();
        for (int i = 0; i < 100; i++) {
            DemoObj demoObj = new DemoObj("id" + i, lat + 0.001 * i, lng - 0.001 * i);
            demoObjList.add(demoObj);
        }
        MapGeoJson mapGeoData = getMapGeoData(demoObjList);
        clusteringLayerManager.addClusteredGeoJsonSource(mapGeoData);
    }

    public void clear(View view) {
        clusteringLayerManager.removeAllLayers();
    }

    private MapGeoJson getMapGeoData(List<DemoObj> demoObjList) {
        MapGeoJson mapGeoJson = new MapGeoJson();
        List<MapGeoJson.FeaturesBean> featureList = new ArrayList<>();
        //填充数据
        for (DemoObj demoObj : demoObjList) {
            MapGeoJson.FeaturesBean fBean = new MapGeoJson.FeaturesBean();
            fBean.setProperties(getDevicePropertiesBeanX(demoObj.getId()));
            fBean.setGeometry(getGeometryBean(demoObj.getLat(), demoObj.getLng()));
            featureList.add(fBean);
        }
        mapGeoJson.setFeatures(featureList);
        return mapGeoJson;
    }

    private MapGeoJson.FeaturesBean.GeometryBean getGeometryBean(double lat, double lng) {
        MapGeoJson.FeaturesBean.GeometryBean geometryBean = new MapGeoJson.FeaturesBean.GeometryBean();
        List<Double> coordinates = new ArrayList<>();
        coordinates.add(lng);
        coordinates.add(lat);
        geometryBean.setCoordinates(coordinates);
        return geometryBean;
    }

    private MapGeoJson.FeaturesBean.PropertiesBeanX getDevicePropertiesBeanX(String id) {
        MapGeoJson.FeaturesBean.PropertiesBeanX pBeanX = new MapGeoJson.FeaturesBean.PropertiesBeanX();
        pBeanX.setId(id);
        return pBeanX;
    }
}

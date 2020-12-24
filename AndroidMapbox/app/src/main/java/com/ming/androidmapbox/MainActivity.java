package com.ming.androidmapbox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.ming.androidmapbox.expression.ExpressionActivity;
import com.ming.androidmapbox.expression.ExpressionMapActivity;
import com.ming.androidmapbox.layers.CircleCreateHotspotsActivity;
import com.ming.androidmapbox.layers.CircleLayerActivity;
import com.ming.androidmapbox.layers.CircleLayerClusteringActivity;
import com.ming.androidmapbox.layers.CircleToIconTransitionActivity;
import com.ming.androidmapbox.layers.FillLayerActivity;
import com.ming.androidmapbox.layers.LineLayerActivity;
import com.ming.androidmapbox.layers.MarkerFollowingRouteActivity;
import com.ming.androidmapbox.layers.MultipleHeatmapStylingActivity;
import com.ming.androidmapbox.layers.SymbolLayerActivity;
import com.ming.androidmapbox.layers.SymbolLayerIconsActivity;
import com.ming.androidmapbox.marker.CustomMarkerOneActivity;
import com.ming.androidmapbox.marker.CustomMarkerThreeActivity;
import com.ming.androidmapbox.marker.CustomMarkerTwoActivity;
import com.ming.androidmapbox.marker.InfoWindowAdapterActivity;
import com.ming.androidmapbox.root.CameraPositionActivity;
import com.ming.androidmapbox.root.MapListenerActivity;
import com.ming.androidmapbox.root.StyleActivity;
import com.ming.androidmapbox.root.UiSettingActivity;
import com.ming.androidmapbox.titlesource.AddWmsSourceActivity;
import com.ming.androidmapbox.titlesource.RasterTileSourceActivity;
import com.ming.androidmapbox.titlesource.VectorSourceActivity;
import com.ming.androidmapbox.symbol.ClusterActivity;
import com.ming.androidmapbox.symbol.GeoActivity;
import com.ming.androidmapbox.symbol.HideMapNameActivity;
import com.ming.androidmapbox.symbol.InfoWindowActivity;
import com.ming.androidmapbox.symbol.LocationComponentActivity;
import com.ming.androidmapbox.symbol.ReverseGeoActivity;
import com.ming.androidmapbox.symbol.SnapshotActivity;
import com.ming.androidmapbox.symbol.SymbolLayerOneActivity;
import com.ming.androidmapbox.symbol.SymbolLayerTwoActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private final ArrayList<ActivityData> mActivityDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv_activity_list = findViewById(R.id.rv_activity_list);
        rv_activity_list.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        ActivityDataAdapter activityDataAdapter = new ActivityDataAdapter(this, mActivityDataList);
        rv_activity_list.setAdapter(activityDataAdapter);

        getListActivity();
        activityDataAdapter.notifyDataSetChanged();
    }

    private void getListActivity() {
        // Generate a ListView with Sample Maps
        mActivityDataList.add(new ActivityData("*************Marker包下*************", ""));

        mActivityDataList.add(new ActivityData("CustomMarkerOneActivity", CustomMarkerOneActivity.class.getName()));
        mActivityDataList.add(new ActivityData("CustomMarkerTwoActivity", CustomMarkerTwoActivity.class.getName()));
        mActivityDataList.add(new ActivityData("CustomMarkerThreeActivity", CustomMarkerThreeActivity.class.getName()));
        mActivityDataList.add(new ActivityData("InfoWindowAdapterActivity", InfoWindowAdapterActivity.class.getName()));

        mActivityDataList.add(new ActivityData("*************symbol包下*************", ""));

        mActivityDataList.add(new ActivityData("GeoActivity", GeoActivity.class.getName()));
        mActivityDataList.add(new ActivityData("ReverseGeoActivity", ReverseGeoActivity.class.getName()));
        mActivityDataList.add(new ActivityData("SymbolLayerOneActivity", SymbolLayerOneActivity.class.getName()));
        mActivityDataList.add(new ActivityData("SymbolLayerTwoActivity", SymbolLayerTwoActivity.class.getName()));
        mActivityDataList.add(new ActivityData("ClusterActivity", ClusterActivity.class.getName()));
        mActivityDataList.add(new ActivityData("InfoWindowActivity", InfoWindowActivity.class.getName()));
        mActivityDataList.add(new ActivityData("SnapshotActivity", SnapshotActivity.class.getName()));
        mActivityDataList.add(new ActivityData("HideMapNameActivity", HideMapNameActivity.class.getName()));
        mActivityDataList.add(new ActivityData("LocationComponentActivity", LocationComponentActivity.class.getName()));

        mActivityDataList.add(new ActivityData("*************root包下*************", ""));

        mActivityDataList.add(new ActivityData("CameraPositionActivity", CameraPositionActivity.class.getName()));
        mActivityDataList.add(new ActivityData("MapListenerActivity", MapListenerActivity.class.getName()));
        mActivityDataList.add(new ActivityData("StyleActivity", StyleActivity.class.getName()));
        mActivityDataList.add(new ActivityData("UiSettingActivity", UiSettingActivity.class.getName()));

        mActivityDataList.add(new ActivityData("*************layers包下*************", ""));

        mActivityDataList.add(new ActivityData("CircleCreateHotspotsActivity", CircleCreateHotspotsActivity.class.getName()));
        mActivityDataList.add(new ActivityData("CircleLayerActivity", CircleLayerActivity.class.getName()));
        mActivityDataList.add(new ActivityData("CircleLayerClusteringActivity", CircleLayerClusteringActivity.class.getName()));
        mActivityDataList.add(new ActivityData("CircleToIconTransitionActivity", CircleToIconTransitionActivity.class.getName()));
        mActivityDataList.add(new ActivityData("FillLayerActivity", FillLayerActivity.class.getName()));
        mActivityDataList.add(new ActivityData("LineLayerActivity", LineLayerActivity.class.getName()));
        mActivityDataList.add(new ActivityData("MarkerFollowingRouteActivity", MarkerFollowingRouteActivity.class.getName()));
        mActivityDataList.add(new ActivityData("SymbolLayerActivity", SymbolLayerActivity.class.getName()));
        mActivityDataList.add(new ActivityData("SymbolLayerIconsActivity", SymbolLayerIconsActivity.class.getName()));
        mActivityDataList.add(new ActivityData("MultipleHeatmapStylingActivity", MultipleHeatmapStylingActivity.class.getName()));
        mActivityDataList.add(new ActivityData("*************expression包下*************", ""));
        mActivityDataList.add(new ActivityData("ExpressionActivity", ExpressionActivity.class.getName()));
        mActivityDataList.add(new ActivityData("ExpressionMapActivity", ExpressionMapActivity.class.getName()));
        mActivityDataList.add(new ActivityData("AddWmsSourceActivity", AddWmsSourceActivity.class.getName()));
        mActivityDataList.add(new ActivityData("RasterTileSourceActivity", RasterTileSourceActivity.class.getName()));
        mActivityDataList.add(new ActivityData("VectorSourceActivity", VectorSourceActivity.class.getName()));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            startActivity(new Intent(this, Class.forName(mActivityDataList.get(i).getClassName())));
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        }
    }

    static class ActivityData {
        private String name;
        private String className;

        public ActivityData(String name, String className) {
            this.name = name;
            this.className = className;
        }

        public String getName() {
            return name;
        }

        public String getClassName() {
            return className;
        }
    }

    static class ActivityDataAdapter extends RecyclerView.Adapter<ActivityDataAdapter.VH> {
        private Context context;
        private List<ActivityData> dataList;
        private AdapterView.OnItemClickListener onItemClickListener;

        public ActivityDataAdapter(Context context, List<ActivityData> list) {
            this.context = context;
            if (context instanceof AdapterView.OnItemClickListener) {
                onItemClickListener = (AdapterView.OnItemClickListener) context;
            }
            this.dataList = list;
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent,false);
            return new VH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            ActivityData activityData = dataList.get(position);
            holder.tvName.setText(activityData.getName());
            holder.itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(null, null, holder.getLayoutPosition(), holder.getItemId());
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        static class VH extends RecyclerView.ViewHolder {
            private TextView tvName;

            public VH(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(android.R.id.text1);
            }
        }
    }
}

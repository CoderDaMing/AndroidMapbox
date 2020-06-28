package com.ming.androidmapbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.ming.androidmapbox.marker.CustomMarkerOneActivity;
import com.ming.androidmapbox.marker.CustomMarkerThreeActivity;
import com.ming.androidmapbox.marker.CustomMarkerTwoActivity;
import com.ming.androidmapbox.marker.InfoWindowAdapterActivity;
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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private final ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Generate a ListView with Sample Maps
        list.add(CustomMarkerOneActivity.class.getName());
        list.add(CustomMarkerTwoActivity.class.getName());
        list.add(CustomMarkerThreeActivity.class.getName());
        list.add(InfoWindowAdapterActivity.class.getName());

        list.add(GeoActivity.class.getName());
        list.add(ReverseGeoActivity.class.getName());
        list.add(SymbolLayerOneActivity.class.getName());
        list.add(SymbolLayerTwoActivity.class.getName());
        list.add(ClusterActivity.class.getName());
        list.add(InfoWindowActivity.class.getName());
        list.add(SnapshotActivity.class.getName());
        list.add(HideMapNameActivity.class.getName());
        list.add(LocationComponentActivity.class.getName());

        ListView lv = findViewById(R.id.activitylist);
        lv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list));
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            startActivity(new Intent(this, Class.forName(list.get(i))));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
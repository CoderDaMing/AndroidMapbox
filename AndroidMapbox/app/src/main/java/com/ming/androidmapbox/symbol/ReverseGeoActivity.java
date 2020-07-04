package com.ming.androidmapbox.symbol;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ming.androidmapbox.MapActivity;
import com.ming.androidmapbox.R;
import com.ming.androidmapbox.ToastUtil;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReverseGeoActivity extends MapActivity {
    @Override
    public void onMapLoaded() {
        super.onMapLoaded();
//＃检索特定位置附近的位置
        String url1 = "https://api.mapbox.com/geocoding/v5/mapbox.places/-73.989,40.733.json?access_token=您的访问令牌";

//＃过滤结果仅包含兴趣点
        String url2 = "https://api.mapbox.com/geocoding/v5/mapbox.places/-73.989,40.733.json?types=poi&access_token=您的访问令牌";
        double latitude = mapboxMap.getCameraPosition().target.getLatitude();
        double longitude = mapboxMap.getCameraPosition().target.getLongitude();
        testReGeo(longitude, latitude);
    }

    public void testReGeo(double lng, double lat) {
        String language;
        if (Locale.getDefault().getLanguage().equals("zh")) {
            language = "zh-CN";
        } else {
            language = "en";
        }
        String url = "https://api.mapbox.com/geocoding/v5/mapbox.places/"
                + lng + "," + lat + ".json?language=" + language + "&access_token=" + getString(R.string.mapbox_access_token);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                ToastUtil.show("testMapQuest onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json = response.body().string();
                Log.d("testMapQuest", "==>返回结果：onResponse " + json);
                ToastUtil.show("testMapQuest onResponse: \n"  + json);
            }
        });
    }
}

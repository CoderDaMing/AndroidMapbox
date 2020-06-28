package com.ming.androidmapbox.symbol;

import androidx.annotation.NonNull;

import com.ming.androidmapbox.LogUtils;
import com.ming.androidmapbox.MapActivity;
import com.ming.androidmapbox.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GeoActivity extends MapActivity {
    @Override
    public void onMapLoaded() {
        super.onMapLoaded();
//        3.1 ＃在特定地区找到一个名为'Chester'的小镇
//＃使用本地坐标添加proximity参数
//＃这确保了新泽西州切斯特镇的成果
        String url1 = "https://api.mapbox.com/geocoding/v5/mapbox.places/chester.json?proximity=-74.70850,40.78375&access_token=your-access-token";

//        3.2 ＃指定types = country以仅搜索名为Georgia的国家/地区
//＃结果将排除美国佐治亚州
        String url2 = "https://api.mapbox.com/geocoding/v5/mapbox.places/georgia.json?types=country&access_token=your-access-token";

//        3.3 在华盛顿特区搜索”星巴克“
//＃使用边界框将结果限制在区域
        String url3 = "https://api.mapbox.com/geocoding/v5/mapbox.places/starbucks.json?bbox=-77.083056,38.908611,-76.997778,38.959167&access_token=your-access-token";

//        3.4使用限制选项将结果限制为2个结果
//＃尽管
//”华盛顿“ 有许多可能的匹配＃，但此查询只返回2个结果
        String url4 = "https://api.mapbox.com/geocoding/v5/mapbox.places/Washington.json?limit=2&access_token=your-access-token";

        geoAddress(url1.replace("your-access-token", getString(R.string.mapbox_access_token)));
    }

    private void geoAddress(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                LogUtils.d("testMapQuest==>返回结果：onFailure " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json = response.body().string();
                LogUtils.d("testMapQuest==>返回结果：onResponse " + json);
            }
        });
    }
}

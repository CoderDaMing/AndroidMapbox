package com.ming.androidmapbox;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.mapbox.mapboxsdk.Mapbox;

public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // Mapbox Access token
        Mapbox.getInstance(getApplicationContext(), getString(R.string.mapbox_access_token));
    }

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex
        MultiDex.install(this);
    }
}
package com.ming.androidmapbox.util;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.ming.androidmapbox.MyApplication;


public class ToastUtil {
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void show(String msg) {
        handler.post(() -> Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_SHORT).show());

    }
}

package com.ming.androidmapbox;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;


public class ToastUtil {
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void show(String msg) {
        handler.post(() -> Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_SHORT).show());

    }
}

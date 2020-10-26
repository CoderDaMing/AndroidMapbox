package com.ming.androidmapbox.util;

import android.app.Activity;
import android.app.AlertDialog;

public class DialogUtil {
    public static void show(Activity activity, String msg) {
        new AlertDialog.Builder(activity).setTitle("").setMessage(msg).setPositiveButton("朕知道了", null).create().show();

    }
}

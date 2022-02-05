/**
 * Copyright 2021. Happy codding ! :)
 * Author: Serhii Butryk
 */
package com.example.app;

import android.app.Application;
import android.util.Log;

import static com.example.app.utils.AppUtils.APP;

/**
 * Custom app's application class
 */
public class MyApplication extends Application {

    public static final String TAG = APP + "MyApplication";

    /**
     * no-op, logging only
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate() Application is created");
    }
}

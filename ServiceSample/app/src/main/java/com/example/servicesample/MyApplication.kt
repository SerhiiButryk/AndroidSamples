/**
 * Copyright 2022. Happy codding ! :)
 * Author: Serhii Butryk
 */
package com.example.servicesample

import android.app.Application
import android.util.Log

/**
 * App's application class. No-op, only prints logs.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate: application is created - $this")
    }

    companion object {
        /**
         * Tag for filtering logs of this sample app
         */
        const val APP_TAG = "ServiceSample"
        private const val TAG = "$APP_TAG-MyApplication"
    }

}
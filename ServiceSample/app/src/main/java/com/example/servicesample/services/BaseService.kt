/**
 * Copyright 2022. Happy codding ! :)
 * Author: Serhii Butryk
 */
package com.example.servicesample.services

import android.app.Service
import android.content.Intent
import android.content.res.Configuration
import android.util.Log

/**
 * Abstract service class. No-op, it only prints logs.
 */
abstract class BaseService(private val TAG: String) : Service() {

    override fun onConfigurationChanged(newConfig: Configuration) {
        Log.i(TAG, "onConfigurationChanged: called")
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        Log.i(TAG, "onTaskRemoved: called")
    }

    override fun onLowMemory() {
        Log.i(TAG, "onLowMemory: called")
    }

    override fun onTrimMemory(level: Int) {
        Log.i(TAG, "onTrimMemory: called")
    }

    override fun onCreate() {
        Log.i(TAG, "onCreate: service is created - $this")
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy: service is destroyed - $this")
    }

}
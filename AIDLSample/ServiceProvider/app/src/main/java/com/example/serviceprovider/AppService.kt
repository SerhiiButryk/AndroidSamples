package com.example.serviceprovider

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.service.IServiceInfo

class AppService : Service() {

    private val name = "My cool service"

    private val myService = object : IServiceInfo.Stub() {
        override fun getServiceName(): String {
            Log.i(TAG, "AppService::getServiceName: called from ${Thread.currentThread().name}")
            return name;
        }
    }

    override fun onCreate() {
        super.onCreate()

        Log.i(TAG, "AppService::onCreate: called")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.i(TAG, "AppService::onStartCommand: called")

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder {

        Log.i(TAG, "AppService::onBind: called ${myService.asBinder()}")

        return myService;
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.i(TAG, "AppService::onDestroy: called")
    }
}
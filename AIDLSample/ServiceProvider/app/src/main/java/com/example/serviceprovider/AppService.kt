package com.example.serviceprovider

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.service.IServiceInfo

class AppService : Service() {

    private val name = "My cool name"

    private val myService = object : IServiceInfo.Stub() {
        override fun getServiceName(): String {
            Log.i(tag, "AppService::getServiceName: called")
            return name;
        }
    }

    override fun onCreate() {
        super.onCreate()

        Log.i(tag, "AppService::onCreate: called")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.i(tag, "AppService::onStartCommand: called")

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {

        Log.i(tag, "AppService::onBind: called")

        return myService;
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.i(tag, "AppService::onDestroy: called")
    }
}
/**
 * Copyright 2022. Happy codding ! :)
 * Author: Serhii Butryk
 */
package com.example.servicesample

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.servicesample.MyApplication.Companion.APP_TAG
import com.example.servicesample.services.BackgroundService
import com.example.servicesample.services.ForegroundService

/**
 * Activity which displays UI and starts and stops Services.
 */
class MainActivity : AppCompatActivity() {

    private var isServiceBound = false
    private var isBindServiceRequested = false
    private var serviceMessenger: Messenger? = null
    private var serviceName: ComponentName? = null

    private val backgroundServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.i(TAG, "onServiceConnected: $name")
            serviceMessenger = Messenger(service)
            serviceName = name
            isServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.i(TAG, "onServiceDisconnected: $name")
            isServiceBound = false
            serviceMessenger = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initActivity()
        Log.i(TAG, "onCreate: activity is created - $this")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: activity is destroyed - $this")
    }

    private fun initActivity() {
        /**
         * Launch background service
         */
        findViewById<Button>(R.id.start_started_service_btn).setOnClickListener {
            val intent = BackgroundService.getIntentForServiceLaunch(this)
            startService(intent)
        }

        /**
         * Launch foreground service
         */
        findViewById<Button>(R.id.start_foreground_service).setOnClickListener {
            val intent = ForegroundService.getIntentForServiceLaunch(this)
            val componentName = startForegroundService(intent)
            Log.i(TAG, "Launched foreground service: $componentName")
        }

        /**
         * Stop foreground service
         */
        findViewById<Button>(R.id.stop_foreground_service).setOnClickListener {
            val intent = ForegroundService.getIntentForServiceLaunch(this)
            val success = stopService(intent)
            Log.i(TAG, "Stop foreground service: $success")
        }

        /**
         * Launch bound service
         */
        findViewById<Button>(R.id.start_boundh_service_btn).setOnClickListener {
            val intent = BackgroundService.getIntentForServiceLaunch(this)
            val result = bindService(intent, backgroundServiceConnection, Context.BIND_AUTO_CREATE)
            if (result) {
                Log.i(TAG, "Binding is successful")
            } else {
                Log.e(TAG, "Binding is failed")
            }
            // Telling that we need to call unbind. 'Unbind' must be called in any case (whether
            // result is true or false)
            isBindServiceRequested = true
        }

        /**
         * Send work to a bound service
         */
        findViewById<Button>(R.id.send_work_to_service).setOnClickListener {
            if (serviceMessenger != null) {
                // Send some message to service to start work
                val message = Message.obtain()
                message.what = BackgroundService.MESSAGE_DO_WORK
                // Send message to Service
                serviceMessenger!!.send(message)

                Log.i(TAG, "Message to service $serviceName is sent")
            } else {
                Log.e(TAG, "Failed to send message, ref is null")
            }
        }

        /**
         * Unbind service
         */
        findViewById<Button>(R.id.unbind_service).setOnClickListener{
            // If Service was not bound, 'IllegalArgumentException' exception is thrown
            // with message - "Service not registered: ..."
            if (isBindServiceRequested) {
                unbindService(backgroundServiceConnection)
                Log.i(TAG, "Unbind is called")
            } else {
                Log.i(TAG, "Service is not bound")
            }
        }

        /**
         * Log debug info
         */
        findViewById<Button>(R.id.log_service_info).setOnClickListener{
            logRunningServicesInfo()
        }
    }

    // Log currently running Services on Device
    private fun logRunningServicesInfo() {
        val activityManager = getSystemService(ACTIVITY_SERVICE)
        if (activityManager is ActivityManager) {
            // Get list of running Services on Android. Param is max number of services to return
            val runningServices = activityManager.getRunningServices(50);
            
            for (runningServiceInfo in runningServices) {
                Log.i(TAG, "logRunningServicesInfo: ${runningServiceInfo.clientLabel} " +
                        ": ${runningServiceInfo.clientPackage} : ${runningServiceInfo.service}")
            }
            
            if (runningServices.size == 0) {
                Log.i(TAG, "logRunningServicesInfo: there are no running services")
            }
        }
    }

    companion object {
        private const val TAG = "$APP_TAG-MainActivity"
    }
}
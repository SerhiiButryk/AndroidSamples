/**
 * Copyright 2022. Happy codding ! :)
 * Author: Serhii Butryk
 */
package com.example.servicesample.services

import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import com.example.servicesample.MyApplication.Companion.APP_TAG
import java.text.SimpleDateFormat
import java.util.*

/**
 * Service which client can launch using Context.startService() method
 */
class BackgroundService : AbstractServiceLogging(TAG) {

    private val handlerThread: HandlerThread = HandlerThread("StartedServiceHandlerThread",
        Process.THREAD_PRIORITY_BACKGROUND)
    private var serviceHandler: Handler? = null
    private var serviceUpTimeMillis: Long = 0
    private val timeFormatter = SimpleDateFormat("mm:ss.SSS")

    override fun onCreate() {

        // Launch handler thread
        handlerThread.start()

        serviceHandler = object : Handler(handlerThread.looper) {
            override fun handleMessage(msg: Message) {
                Log.i(TAG, "handleMessage: IN")

                // Simulate processing
                Log.i(TAG, "handleMessage: processing ...")
                try {
                    Thread.sleep(5*1000) // 5 seconds
                } catch (e: Exception) {}

                Log.i(TAG, "handleMessage: OUT")
            }
        }

        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.i(TAG, "onStartCommand: is called, intent: $intent, flags: $flags, id: $startId")

        initUpTime()

        // Do not recreate service if system kills it
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.i(TAG, "onBind: is called")
        // This service doesn't provide binding
        return null
    }

    override fun onDestroy() {

        // Stop handler thread
        val isStopped = handlerThread.quit()
        if (isStopped) {
            Log.i(TAG, "onDestroy: handler thread is stopped")
        } else {
            Log.e(TAG, "onDestroy: handler thread is NOT stopped")
        }

        logUpTime()

        super.onDestroy()
    }

    private fun initUpTime() {
        if (serviceUpTimeMillis == 0L) {
            serviceUpTimeMillis = System.currentTimeMillis()
        }
    }

    private fun logUpTime() {
        val difference = Date(System.currentTimeMillis() - serviceUpTimeMillis)
        Log.i(TAG, "logUpTime: alive for ${timeFormatter.format(difference)}")
    }

    companion object {
        private const val TAG = "$APP_TAG-StartedService"

        /**
         * Creates Intent for launching this service
         */
        fun getIntentForServiceLaunch(context: Context) : Intent {
            return Intent(context, BackgroundService::class.java)
        }
    }

}
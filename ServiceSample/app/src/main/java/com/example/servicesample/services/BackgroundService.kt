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
 * Service which client can launch using Context.startService() method or bind to it.
 * Note: this Service is created in a separate process.
 */
class BackgroundService : BaseService(TAG) {

    private val handlerThread: HandlerThread = HandlerThread("StartedServiceHandlerThread",
        Process.THREAD_PRIORITY_BACKGROUND)
    private var serviceHandler: Handler? = null
    private var messenger: Messenger? = null
    private var serviceUpTimeMillis: Long = 0
    private val timeFormatter = SimpleDateFormat("mm:ss.SSS")
    private val binder = ServiceBinder()

    /**
     * Service's binder
     */
    inner class ServiceBinder : Binder() {
        fun getService(): BackgroundService = this@BackgroundService
    }

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

        // Save creation time
        serviceUpTimeMillis = System.currentTimeMillis()

        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.i(TAG, "onStartCommand: is called, intent: $intent, flags: $flags, id: $startId")

        // Do not recreate service if system kills it
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.i(TAG, "onBind: is called")
        // Return Service's binder implementation. Client can use it to call Service's
        // public methods. Alternatively, here we can create and return a Messenger object
        // and communicate with Service throughout it.
        // {@code return Messenger(serviceHandler) }
        return Messenger(serviceHandler).binder
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

    // Returns Service's Handler
    fun getServiceHandler() : Handler = serviceHandler!!

    // Returns Service's Messenger
    fun getMessenger() : Messenger = messenger!!

    private fun logUpTime() {
        val difference = Date(System.currentTimeMillis() - serviceUpTimeMillis)
        Log.i(TAG, "logUpTime: alive for ${timeFormatter.format(difference)}")
    }

    companion object {
        private const val TAG = "$APP_TAG-StartedService"
        const val MESSAGE_DO_WORK = 1

        /**
         * Creates Intent for launching this service
         */
        fun getIntentForServiceLaunch(context: Context) : Intent {
            return Intent(context, BackgroundService::class.java)
        }
    }

}
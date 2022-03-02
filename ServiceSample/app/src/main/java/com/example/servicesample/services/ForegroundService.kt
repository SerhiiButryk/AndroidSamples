/**
 * Copyright 2022. Happy codding ! :)
 * Author: Serhii Butryk
 */
package com.example.servicesample.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.servicesample.MainActivity
import com.example.servicesample.MyApplication.Companion.APP_TAG

/**
 * Services which runs in foreground
 */
class ForegroundService : BaseService(TAG) {

    override fun onCreate() {
        Log.i(TAG, "onCreate: is called")
        // Need to create notification channel before showing Notifications.
        // It is safe to do this multiple times as it is no-op if channel is already created.
        createNotificationChannel()
        super.onCreate()
    }

    private fun createNotificationChannel() {
        val name: CharSequence = "101 Notification name"
        val description = "101 Notification decs"
        val importance = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
        channel.description = description

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand: is called")

        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
        val pendingIntent = PendingIntent.getActivity(this, PENDING_INTENT_REQUEST_CODE, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        val notification: Notification = Notification.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("My title")
            .setContentText("My content")
            .setSmallIcon(android.R.drawable.sym_def_app_icon)
            .setContentIntent(pendingIntent)
            .setTicker("Ticker text")
            .build()

        // Tell the system that this service needs to be run in foreground
        startForeground(NOTIFICATION_FOREGROUND_SERVICE_ID, notification)

        // Do not recreate service if system kills it
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.i(TAG, "onBind: return null as this service doesn't allow binding")
        return null
    }

    companion object {
        private const val TAG = "$APP_TAG-ForegroundService"
        private const val PENDING_INTENT_REQUEST_CODE = 101
        private const val NOTIFICATION_CHANNEL_ID = "1"
        private const val NOTIFICATION_FOREGROUND_SERVICE_ID = 2
        /**
         * Creates Intent for launching this service
         */
        fun getIntentForServiceLaunch(context: Context) : Intent {
            return Intent(context, ForegroundService::class.java)
        }
    }
}
package com.example.myapplication2

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import android.widget.Toast


class MyExternalService : Service() {

    companion object {
        const val MSG_SAY_HELLO = 1
        const val TAG = "APP_TEST2-MyService"
    }

    /**
     * Handler of incoming messages from clients.
     */
    private class IncomingHandler(context: Context) : Handler() {

        private val applicationContext: Context

        init {
            applicationContext = context.applicationContext
        }

        override fun handleMessage(msg: Message) {
            Log.i(TAG, "handleMessage: is called")
            when (msg.what) {

                MSG_SAY_HELLO -> Toast.makeText(applicationContext, "hello!", Toast.LENGTH_SHORT)
                    .show()

                else -> super.handleMessage(msg)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate: is called")

        //Log.i(TAG, "onCreate: starting activity")
        //val intent = Intent(this, ExternalActivityForLaunch::class.java)
        //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        //startActivity(intent)
    }

    /**
     * When binding to the service, we return an interface to our messenger
     * for sending messages to the service.
     */
    override fun onBind(intent: Intent?): IBinder? {
        Log.i(TAG, "onBind: is called")

        if (intent != null) {
            val pi = intent.getParcelableExtra("Intent") as? PendingIntent
            if (pi != null) {
                pi.send(100)
            }
        }

        val mMessenger = Messenger(IncomingHandler(this))
        return mMessenger.binder
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: is called")
    }
}
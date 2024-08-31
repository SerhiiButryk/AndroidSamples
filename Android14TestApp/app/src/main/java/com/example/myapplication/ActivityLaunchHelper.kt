package com.example.myapplication

import android.app.ActivityOptions
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.util.Log

object ActivityLaunchHelper {

    private const val INTENT_ACTION = "com.test.ACTION_BACKGROUND_START"

    fun launchActivityFromApp(context: Context) {
        val intent = Intent(INTENT_ACTION)
        //intent.setPackage("com.example.myapplication2")
        intent.setClassName("com.example.myapplication2", "com.example.myapplication2.ExternalActivityForLaunch")

        context.startActivity(intent)
    }

    fun launchActivityFromAppUsingPI(context: Context, forceNewTaskFlag: Boolean = false) {

        val intent = Intent(INTENT_ACTION)
        intent.setClassName("com.example.myapplication2", "com.example.myapplication2.ExternalActivityForLaunch")

        if (forceNewTaskFlag) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        val options = ActivityOptions.makeBasic()
        //if (Build.VERSION.SDK_INT >= 34) {
        // options.pendingIntentBackgroundActivityStartMode = 1
        //}

        val pi = PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE, options.toBundle())

        pi.send()
    }

    fun bindServiceFromApp(context: Context, serviceConnection: ServiceConnection) {

        val intent = Intent(INTENT_ACTION)
        intent.setClassName("com.example.myapplication2", "com.example.myapplication2.MyExternalService")

        /**
         * BIND_ALLOW_ACTIVITY_STARTS - If binding from an app that is visible, the bound service is allowed to start an activity from background.
         */
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE /*or Context.BIND_ALLOW_ACTIVITY_STARTS*/)
    }

    fun launchFromBackgroundUsingPI(context: Context) {
        Log.i("", "launchFromBackground: launching PI intent")

        launchActivityFromAppUsingPI(context, true)
    }

    fun launchFromBackgroundUsingIntent(context: Context) {
        Log.i("", "launchFromBackground: launching intent")

        launchActivityFromApp(context)
    }
}
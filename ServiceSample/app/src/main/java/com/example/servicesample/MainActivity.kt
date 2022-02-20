/**
 * Copyright 2022. Happy codding ! :)
 * Author: Serhii Butryk
 */
package com.example.servicesample

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.servicesample.MyApplication.Companion.APP_TAG
import com.example.servicesample.services.BackgroundService

/**
 * Activity which displays UI and starts services.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Launch background service
         */
        findViewById<Button>(R.id.start_started_service_btn).setOnClickListener {
            val intent = BackgroundService.getIntentForServiceLaunch(this)
            startService(intent)
        }

        /**
         * Not implemented yet
         */
        findViewById<Button>(R.id.start_foreground_service).setOnClickListener {
            TODO("Not yet implemented")
        }

        /**
         * Not implemented yet
         */
        findViewById<Button>(R.id.start_boundh_service_btn).setOnClickListener {
            TODO("Not yet implemented")
        }

        Log.i(TAG, "onCreate: activity is created - $this")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: activity is destroyed - $this")
    }

    companion object {
        private const val TAG = "$APP_TAG-MainActivity"
    }
}
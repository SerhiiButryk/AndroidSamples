package com.example.myapplication2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class ExternalActivityForLaunch : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_external_for_launch)
        Log.i(TAG, "onCreate: out")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: out")
    }

    companion object {
        const val TAG = "APP_TEST2-ExternalActivityForLaunch"
    }
}
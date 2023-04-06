package com.example.personcontactsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "onCreate: activity is created")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onCreate: activity is destroyed")
    }
}
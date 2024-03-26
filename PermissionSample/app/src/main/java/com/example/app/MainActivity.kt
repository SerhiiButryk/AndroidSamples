package com.example.app

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var pendingIntentForAlarm: PendingIntent

    private val REQUEST_CODE_PI = 1
    private val DELAY_TIME_MILLIS = 60 * 1000L
    private val ALARM_BROAD_CAST_ACTION = "broadcast alarm"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.alarm_btn).setOnClickListener{
            setExactAlarm()
        }

        initAlarm()
    }

    fun initAlarm() {
        Log.i(TAG, "initAlarm: init alarm")

        val alarmBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.i(TAG, "onReceive: alarm received")
            }
        }

        registerReceiver(alarmBroadcastReceiver, IntentFilter(ALARM_BROAD_CAST_ACTION), Context.RECEIVER_NOT_EXPORTED)

        val intent = Intent(ALARM_BROAD_CAST_ACTION)

        pendingIntentForAlarm = PendingIntent.getBroadcast(this, REQUEST_CODE_PI, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    fun setExactAlarm() {
        Log.i(TAG, "setExactAlarm: set alarm")

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME, DELAY_TIME_MILLIS, pendingIntentForAlarm)
    }
}
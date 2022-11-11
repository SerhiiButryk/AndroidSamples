package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.HandlerThread
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.EditText

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val myThread = ThreadLooper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_run_thread).setOnClickListener{
            Log.i(TAG, "onCreate: start thread")
            myThread.start()
        }

        findViewById<Button>(R.id.btn_stop_thread).setOnClickListener {
            Log.i(TAG, "onCreate: stop thread")
            myThread.quite()
        }

        findViewById<Button>(R.id.btn_send_message).setOnClickListener {
            Log.i(TAG, "onCreate: send message")
            val ms = Message.obtain()
            myThread.getHandler()?.sendMessage(ms)
        }

    }

}
package com.example.app

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log

private const val TAG = "ThreadLooper"

class ThreadLooper : Thread() {

    private lateinit var handler: Handler

    fun getHandler() = handler

    fun quite() {
        handler.post {
            Log.i(TAG, "quiting...")
            Looper.myLooper()?.quit()
        }
    }

    override fun run() {
        Log.i(TAG, "run: in")
        Looper.prepare()
        // At this point Looper cannot be null
        val looper = Looper.myLooper()!!
        val myMessageQueue = Looper.myQueue()
        Log.i(TAG, "run: mQueue: $myMessageQueue, looper: $looper")
        handler = MyHandler(looper)
        Looper.loop()
        Log.i(TAG, "run: out")
    }

    class MyHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            Log.i(TAG, "handleMessage: in")
            for (i in 0..5) {
                Log.i(TAG, "handleMessage: sleeping $i ...")
                Thread.sleep(i*1000L)
            }
            Log.i(TAG, "handleMessage: out")
        }
    }

}
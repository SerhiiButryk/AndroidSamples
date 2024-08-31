package com.example.myapp2

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.R

class ExposedToWholeWorldActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exposed_to_whole_world)

        Log.i("MyApp2", "ExposedToWholeWorldActivity.onCreate()")
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onResume() {
        super.onResume()

        isMyProcessInUIForeground()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun isMyProcessInUIForeground() {

        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        val appProcesses = activityManager.runningAppProcesses

        val listTasks = activityManager.appTasks
        for (task in listTasks) {

            val isRunning = task.taskInfo.isRunning
            Log.i("MyApp2", "isMyProcessInUIForeground: isRunning = $isRunning")
        }

        if (appProcesses != null && appProcesses.size > 0) {
            val iter = appProcesses.iterator()

            while (iter.hasNext()) {
                val appProcess = iter.next() as ActivityManager.RunningAppProcessInfo
                Log.i("MyApp2", "isMyProcessInUIForeground: ${appProcess.processName} ${appProcess.importance} ${appProcess.pid}")
            }
        }

    }

}
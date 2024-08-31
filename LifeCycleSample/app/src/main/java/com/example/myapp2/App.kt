package com.example.myapp2

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Log.i("MyApp2", "App.onCreate()")

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {

            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
                Log.i("MyApp2", "onActivityCreated: $p0")
            }

            override fun onActivityStarted(p0: Activity) {
                Log.i("MyApp2", "onActivityStarted: $p0")
            }

            override fun onActivityResumed(p0: Activity) {
                Log.i("MyApp2", "onActivityResumed: $p0")
            }

            override fun onActivityPaused(p0: Activity) {
                Log.i("MyApp2", "onActivityPaused: $p0")
            }

            override fun onActivityStopped(p0: Activity) {
                Log.i("MyApp2", "onActivityStopped: $p0")
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
                Log.i("MyApp2", "onActivitySaveInstanceState: $p0")
            }

            override fun onActivityDestroyed(p0: Activity) {
                Log.i("MyApp2", "onActivityDestroyed: $p0")
            }
        })

    }
}
package com.example.myapplication

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.ActivityLaunchHelper.launchFromBackgroundUsingPI

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.openActivityBtn).setOnClickListener {

            ActivityLaunchHelper.launchActivityFromApp(this)

            Log.i(TAG, "onCreate: called start activity")
        }

        findViewById<Button>(R.id.openActivityUsingPIBtn).setOnClickListener {

            ActivityLaunchHelper.launchActivityFromAppUsingPI(this)

            Log.i(TAG, "onCreate: called start activity")
        }

        findViewById<Button>(R.id.openServiceId).setOnClickListener {

            val serviceConnection = object : ServiceConnection {

                override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                    Log.i(TAG, "onServiceConnected $p0")
                }

                override fun onServiceDisconnected(p0: ComponentName?) {
                    Log.i(TAG, "onServiceDisconnected $p0")
                }
            }

            ActivityLaunchHelper.bindServiceFromApp(this, serviceConnection)

            Log.i(TAG, "onCreate: called start service")
        }

        Log.i(TAG, "onCreate: out")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: out")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop: in")

        if (!IS_INTENT_SENT) {

            Handler(Looper.getMainLooper()).postDelayed({

                launchFromBackgroundUsingPI(MainActivity@this)

                //launchFromBackgroundUsingIntent()

            }, 10 * 1000)

            IS_INTENT_SENT = true;
        }

        Log.i(TAG, "onStop: out")
    }

    companion object {
        const val TAG = "APP_TEST1-MainActivity"
        var IS_INTENT_SENT = false
    }
}
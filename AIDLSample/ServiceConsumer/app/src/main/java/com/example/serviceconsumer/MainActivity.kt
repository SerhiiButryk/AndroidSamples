package com.example.serviceconsumer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.IInterface
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.service.IServiceInfo

const val TAG: String = "ServiceConsumer"

class MainActivity : AppCompatActivity() {

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            myService = IServiceInfo.Stub.asInterface(service)
            Log.i(TAG, "onServiceConnected: called, binder = ${(myService as? IInterface)?.asBinder()}")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.i(TAG, "onServiceDisconnected: called")
        }
    }

    private var myService: IServiceInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.start_btn).setOnClickListener {

            Log.i(TAG, "onCreate: calling service")

            try {

                val name = myService?.serviceName ?: "null"

                Log.i(TAG, "Got result: $name")

            } catch (e: Exception) {
                Log.i(TAG, "ERROR: $e")
                e.printStackTrace()
            }
        }

        findViewById<Button>(R.id.bind_btn).setOnClickListener{
            val intent = Intent("com.example.BIND")
            intent.setClassName("com.example.serviceprovider", "com.example.serviceprovider.AppService")
            val result = bindService(intent, connection, Context.BIND_AUTO_CREATE)
            Log.i(TAG, "Bind result: $result")
        }

        findViewById<Button>(R.id.unbind_btn).setOnClickListener{
            unbindService(connection)
            Log.i(TAG, "Unbind")
        }

        Log.i(TAG, "onCreate: called")
    }
}
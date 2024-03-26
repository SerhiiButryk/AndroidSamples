package com.example.serviceconsumer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import com.example.service.IServiceInfo

const val tag: String = "ServiceConsumer"

class MainActivity : AppCompatActivity() {

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            myService = IServiceInfo.Stub.asInterface(service)



            Log.i(tag, "onServiceConnected: called $myService")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.i(tag, "onServiceDisconnected: called")
        }
    }

    private var myService: IServiceInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.start_btn).setOnClickListener {

            Log.i(tag, "onCreate: calling service")

            try {

                val name = myService?.serviceName ?: "null"

                Log.i(tag, "Got result: $name")

            } catch (e: Exception) {
                Log.i(tag, "ERROR: $e")
                e.printStackTrace()
            }
        }

        findViewById<Button>(R.id.bind_btn).setOnClickListener{
            val intent = Intent("com.example.BIND")
            intent.setClassName("com.example.serviceprovider", "com.example.serviceprovider.AppService")

            val result = bindService(intent, connection, Context.BIND_AUTO_CREATE)
            Log.i(tag, "Bind result: $result")
        }

        findViewById<Button>(R.id.unbind_btn).setOnClickListener{

            unbindService(connection)

            Log.i(tag, "Unbind")
        }

        Log.i(tag, "onCreate: called")
    }
}
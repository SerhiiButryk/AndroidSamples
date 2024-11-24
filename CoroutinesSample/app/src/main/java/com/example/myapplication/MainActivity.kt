package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.myapplication.network.NetworkTaskManager
import com.example.myapplication.ui.MainUI

const val TAG = "CoroutineSample"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val networkTaskManager = NetworkTaskManager()

        setContent {
            MainUI(networkTaskManager)
        }
    }

}


package com.example.myapplication

import android.util.Log
import com.example.myapplication.flow.SampleViewModel
import kotlinx.coroutines.runBlocking

class SampleViewModelTest {

    private val TAG = "SampleViewModelTest"

    private lateinit var sampleViewModel: SampleViewModel

    @org.junit.jupiter.api.BeforeEach
    fun setUp() {
        sampleViewModel = SampleViewModel()
    }

    @org.junit.jupiter.api.Test
    fun test01_flow(): Unit = runBlocking {

        Log.i(TAG, "test01_flow: started")

        sampleViewModel.countDownFlow.collect { it ->
            Log.i(TAG, "test01_flow: $it")
        }

        Log.i(TAG, "test01_flow: finished")

    }
}
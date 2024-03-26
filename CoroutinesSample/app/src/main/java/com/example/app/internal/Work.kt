package com.example.app.internal

import android.util.Log
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class Work : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private var runningJob: Job? = null

    fun startWork() {

        val job : Job = launch {
            Log.i("Work", "runWork: a coroutine is started")
            delay(10*1000)
            Log.i("Work", "runWork: a coroutine is finished")
        }

        runningJob = job

        Log.i("Work", "runWork OUT")
    }

    fun stopWork() {

        logState()

        runningJob?.cancel()

        logState()

        runningJob = null

        Log.i("Work", "stopWork OUT")
    }

    fun cancelAll() {
        // Calling on current coroutine scope
        this.cancel()

        Log.i("Work", "cancelAll OUT")

        logState()
    }

    private fun logState() {
        Log.i("Work", "stopWork isActive = ${runningJob?.isActive} " +
                "isCanceled = ${runningJob?.isCancelled} isCompleted = ${runningJob?.isCompleted}")
    }
}
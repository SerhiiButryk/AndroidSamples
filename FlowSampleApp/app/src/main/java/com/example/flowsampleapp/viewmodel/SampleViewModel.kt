package com.example.flowsampleapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.flowsampleapp.DefaultDispatchers
import com.example.flowsampleapp.DispatcherProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import log

class SampleViewModel(dispatchers: DispatcherProvider = DefaultDispatchers()) : ViewModel() {

    private val TAG: String = "SampleViewModel"

    companion object {
        private var hasUserData = false
    }

    // Used in tests
    val countDownFlow = flow {
        log("$TAG: FLOW started")
        val startValue = 5
        var currValue = startValue
        while (currValue > 0) {
            emit(currValue)
            currValue--
            delay(1000L)
        }

        log("$TAG: FLOW finished")
    }.flowOn(dispatchers.main)

    // Login simulation functions

    fun hasUserData() = hasUserData

    fun updateUserData() {
        hasUserData = true
    }

}
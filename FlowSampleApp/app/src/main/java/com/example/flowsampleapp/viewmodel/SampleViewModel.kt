package com.example.flowsampleapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.flowsampleapp.DispatcherProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import log

class SampleViewModel(dispatchers: DispatcherProvider) : ViewModel() {

    private val TAG: String = "SampleViewModel"

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

}
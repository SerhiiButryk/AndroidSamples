package com.example.myapplication.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SampleViewModel : ViewModel() {

    private val _stateFlow = MutableStateFlow(SampleData())
    val stateFlow = _stateFlow.asStateFlow()

    data class SampleData(val categoryTitles: List<String> = emptyList())

    val sampleDataFlow = flow {
        while (true) {
            // Generate new data
            emit(SampleData(listOf(
                "1. Text with button",
                "2. Column alignment"
            )))
            Log.i("MainActivity", "sampleDataFlow: sent new value")
            // Only once
            break
        }
        Log.i("MainActivity", "sampleDataFlow: done")
    }

    init {
        logFlowValues()
    }

    private fun logFlowValues() {

        sampleDataFlow.onEach { value ->
            Log.i("MainActivity", "logFlowValues: got new value $value")
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            sampleDataFlow.collect { list ->
                Log.i("MainActivity", "logFlowValues: got new value $list")
            }
        }
    }

}
package com.example.flowsampleapp

import app.cash.turbine.test
import com.example.flowsampleapp.viewmodel.SampleViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import log
import org.junit.Test

class SampleViewModelTest {

    private val TAG = "SampleViewModelTest"

    // Collect flow and check
    @Test
    fun test01_flow() = runTest {
        log("$TAG: test01_flow: started")

        // Setup test dispatcher and view model
        val dispatcher = StandardTestDispatcher(testScheduler)
        val sampleViewModel = SampleViewModel(object : DispatcherProvider {
            override val main: CoroutineDispatcher
                get() = dispatcher
            override val io: CoroutineDispatcher
                get() = dispatcher
            override val default: CoroutineDispatcher
                get() = dispatcher
        })

        var actualValue = 5

        sampleViewModel.countDownFlow.collect { it ->
            log("$TAG: test01_flow: $it")
            assertThat(it).isEqualTo(actualValue)
            actualValue--
        }

        log("$TAG: test01_flow: finished")
    }

    // Collect flow and consume unconsumed events
    @Test
    fun test02_flow() = runTest {
        log("$TAG: test02_flow: started")

        flowOf("one", "two").test {
            // Check that we receive a value !
            assertThat(awaitItem()).isEqualTo("one")
            // Good ! Ignore any other flow value as we don't want them.
            cancelAndIgnoreRemainingEvents()
        }

        log("$TAG: test02_flow: finished")
    }

    // Collect flow and check using Turbine lib
    @Test
    fun test03_flow() = runTest {
        log("$TAG: test03_flow: started")

        // Setup test dispatcher and view model
        val dispatcher = StandardTestDispatcher(testScheduler)
        val sampleViewModel = SampleViewModel(object : DispatcherProvider {
            override val main: CoroutineDispatcher
                get() = dispatcher
            override val io: CoroutineDispatcher
                get() = dispatcher
            override val default: CoroutineDispatcher
                get() = dispatcher
        })

        sampleViewModel.countDownFlow.test {
            for (i in 5 downTo 1) {
                val nextValue = awaitItem()
                log("$TAG: test03_flow: $nextValue")
                assertThat(nextValue).isEqualTo(i)
            }
        }

        // We don't need something like consume or cancel remaining events here
        // because after test block is finished, the flow gets canceled and we stop
        // emitting new value in flow
        //
        // If we receive an exception at the end of test block, then this could mean the code has changed
        // and we might need to update the test which is a good hint

        log("$TAG: test03_flow: finished")
    }

    // Collect flow and check using Turbine lib + StandardTestDispatcher
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test04_flow() = runTest {
        log("$TAG: test04_flow: started")

        // Setup test dispatcher and view model
        val dispatcher = StandardTestDispatcher(testScheduler)
        val sampleViewModel = SampleViewModel(object : DispatcherProvider {
            override val main: CoroutineDispatcher
                get() = dispatcher
            override val io: CoroutineDispatcher
                get() = dispatcher
            override val default: CoroutineDispatcher
                get() = dispatcher
        })

        sampleViewModel.countDownFlow.test {
            for (i in 5 downTo 1) {
                val nextValue = awaitItem()
                log("$TAG: test04_flow: $nextValue")
                assertThat(nextValue).isEqualTo(i)
            }
            cancelAndConsumeRemainingEvents()
        }

        // We don't need something like consume or cancel remaining events here
        // because after test block is finished, the flow gets canceled and we stop
        // emitting new value in flow
        //
        // If we receive an exception at the end of test block, then this could mean the code has changed
        // and we might need to update the test which is a good hint

        log("$TAG: test04_flow: finished")
    }

}